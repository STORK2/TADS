var fs = require('fs');
var url = require('url');
var mkdirp = require('mkdirp');

var XMLTools = require('../tools/XMLTools');
var DBTools = require('../tools/DBTools');
var PDFTools = require('../tools/PDFTools');
var SAMLTools = require('../tools/SAMLTools');
var Logger = require('../utils/Logger');

var configs = GLOBAL.configs;

var returnPath = 'createdocument/samlreturn';

exports.selectAttr = function(req, res){
	var personalAttrs = configs.personal;
	var academiaAttrs = configs.academia;
	var legalAttrs = configs.legal;
	var renderAttrs = {
		pepsUrl: configs.peps.url,
		countryCodes: configs.peps.cc,
		personalAttributes: personalAttrs,
		academiaAttributes: academiaAttrs,
		legalAttributes: legalAttrs 
	}
	res.render('selectAttributes', renderAttrs);
}

exports.SAMLReq = function(req, res){
	var queryString = req.body;
	var targetPEPS = configs.peps.url;
	var citizenCC = queryString.citizenCC;
	var pepsURL = configs.peps.url;
	var returnURL;
	var parsedURL = url.parse(configs.tads.url);
	
	if(configs.proxy.reverseProxy === true){
		if(configs.tads.protocol === 'https:'){
			returnURL = 'https://'+parsedURL.hostname+'/'+returnPath;
		}else{
			returnURL = parsedURL.protocol+'//'+parsedURL.hostname+'/'+returnPath;
		}
	}else{
		returnURL = parsedURL.href+returnPath;
	}
	
	//Sanitize attributes in queryString
	delete queryString.citizenCC;
	delete queryString.personalAttributes;
	delete queryString.academiaAttributes;
	delete queryString.legalAttributes;
	var attrReqList = queryString;
	var attrList = {};

	for(var attr in attrReqList){
		if(attrReqList[attr] === 'not') continue;
		if(attrReqList[attr] === 'req'){
			attrList[attr] = true;
		}else{
			attrList[attr] = false;
		}
	}

	//Mandatory to identify the citizen
	attrList['eIdentifier'] = true;

	samlAttrs = {
		pepsURL : pepsURL,
		qaa : '3',
		citizenCC : citizenCC,
		returnURL : returnURL,
		attrList : attrList
	}

	SAMLTools.createSAMLRequest(samlAttrs, function(err, samlReq){
		if(err){
			console.error(err);
			res.render('errorPage', {error: err});
		}
		var reqId = samlReq.SAMLReqId;
		Logger.log('info', 'SAML Request created:', {SAMLReqId: reqId});
		req.session.reqId = reqId;
		res.render('postSAMLRequest', samlReq);
	});
}

exports.SAMLRes = function(req, res){
	var samlRes = req.body.SAMLResponse;
	var remoteHostIP = req.headers['x-forwarded-for'] || req.connection.remoteAddress;

	SAMLTools.getSAMLResData(samlRes, remoteHostIP, function(err, samlResData){

		if(err){
			console.error(err);
			res.render('errorPage', {error: err});
		}

		if(samlResData.isFail){
			req.session.isAuth = false;
			Logger.log('info', 'Saml Res with id: %s failed validation.', samlResData.resId);
			res.render('errorPage', {error: "The SAML Response validation failed"});
		}else{
			req.session.isAuth = true;

			var reqId = req.session.reqId;
			var resId = samlResData.resId;

			XMLTools.getSAMLAttributes(samlResData.XML, function(data){
				
				if(data === null || data.size === 0){
					console.error('Error parsing xml');
					req.session.isAuth = false;
					res.render('error', {error: "Error parsing SAML Response xml"});
				}else{
					var attributes = data;
					req.session.attributes = attributes;
					
					var eIdentifier = attributes['eIdentifier'][0];
					var logMsg = 'Saml response with id: '+samlResData.resId+' for user: '+eIdentifier+' validated successfully.'
					Logger.log('info', logMsg);

					DBTools.getUserData(eIdentifier, function(err, data){
						if(err){
							console.error(err);
							res.render('errorPage', {error: err});
						}
						if(data === null){
							//User does not exist, create a new one in DB.
							DBTools.createUser(eIdentifier, function(err){
								if(err){
									console.error(err);
									res.render('errorPage', {error: "couldn't create user"})
								}else{
									Logger.log('info', 'New user created into DB with id: %s', eIdentifier);
									res.redirect('/createdocument/createpdf');
								}
							});
						}else{
							//User already exists, no need to create it
							res.redirect('/createdocument/createpdf');
						}
					});
				}
		 	});
		}
	});
}

exports.generatePDF = function(req, res){

	var attributes = req.session.attributes;

	if(attributes === undefined || attributes === null){
		res.render('errorPage', {error: "Could not get attributes"});
	}

	var userId = attributes['eIdentifier'][0];

	//Create user PDF directory
	var pdfDirPath = './private/pdfs/';
	var pdfDir = pdfDirPath + userId + '/';
	mkdirp.sync(pdfDir);

	PDFTools.create(userId, attributes, function(err, pdfUuid){
		var logMsg = 'New PDF with uuid "'+pdfUuid+'" was created for user "'+userId+'"';
		Logger.log('info', logMsg);
		if(err){
			console.error(err);
			res.render('errorPage', {error: "Couldn't create pdf: "+err});
		}

		//Document Signature
		if(configs.signserver.signDocuments === true){
			PDFTools.signPDF(userId, pdfUuid, function(err){
				if(err) {
					console.error(err);
					res.render('errorPage', {error: "Couldn't sign PDF: "+err});
				}else{
					var logMsg = 'PDF with uuid "'+pdfUuid+'" was successfully signed';
					Logger.log('info', logMsg);
				}
			});
		}

		DBTools.addUserPdf(userId, pdfUuid, function(err, data){
			if(err){
				res.render('errorPage', {error: "Could not add PDF to user database: " + error});
			}
			if(data === null){
				res.render('errorPage', {error: "User not found: " + error});
			}else{
				var logMsg = 'PDF with uuid "'+pdfUuid+'" was added to user "'+userId+'" database'
				Logger.log('info', logMsg);
				var QRCodePath = './private/pdfs/'+data.electronicId+'/'+pdfUuid+'.png';
				fs.unlink(QRCodePath, function(err){
					if(err) console.error(err);
				});
				res.redirect('/managedocuments');
			}
		});  
	});
}
