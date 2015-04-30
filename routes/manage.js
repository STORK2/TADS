var fs = require('fs');
var url = require('url');

var DBTools = require('../tools/DBTools');
var XMLTools = require('../tools/XMLTools');
var SAMLTools = require('../tools/SAMLTools');
var Logger = require('../utils/Logger');

var configs = GLOBAL.configs;

var returnPath = 'managedocuments/samlauthreturn';
var samlReturnUrl = url.parse(configs.tads.url).href+returnPath;

exports.manageDocuments = function(req, res){
	if(req.session.isAuth === true){
		var attributes = req.session.attributes;
		var userId = attributes['eIdentifier'];

		DBTools.getUserData(userId, function(err, data){
			if(err){
				res.render('errorPage', {error: "Could not get user"});
			}else{
				if(data === null){
					res.redirect('/createdocument');
				}else{
					res.render('documentManagement', {nPDFs: data.pdfs.length,
						PDFs: data.pdfs});
				}
			}
		});
	}else{
		res.render('selectCountry', {countryCodes: configs.peps.cc});
	}
}


exports.SAMLAuthReq = function(req, res){
	var queryString = req.body;
	var targetPEPS = configs.peps.url;
	var pepsURL = configs.peps.url;
	var citizenCC = configs.peps.cc;
	var returnURL = configs.peps.manageSAMLEndpoint;
	var attrList = {};

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
		Logger.log('info', 'SAML Request created:', {req: 'eIdentifier', 
			SAMLReqId: samlReq.SAMLReqId});
		req.session.reqId = samlReq.SAMLReqId;
		res.render('postSAMLRequest', samlReq);
	});
}

exports.SAMLAuthRes = function(req, res){
	var samlRes = req.body.SAMLResponse;
	var remoteHostIP = req.headers['x-forwarded-for'] || req.connection.remoteAddress;
	SAMLTools.getSAMLResData(samlRes, remoteHostIP, function(err, samlResData){
		if(err){
			console.error(err);
			res.render('errorPage', {error : err});
		}
		if(samlResData.isFail){
			req.session.isAuth = false;
			var logMsg = 'Saml Res with id: '+samlResData.resId+' failed validation.'
			Logger.log('info', logMsg);
			res.render('errorPage', {error: "Authentication Failed"});
		}else{
			req.session.isAuth = true;

			XMLTools.getSAMLAttributes(samlResData.XML, function(data){
				if(data === null || data.size === 0){
					console.error('Error parsing xml');
					req.session.isAuth = false;
					res.render('error', {error: "Error parsing SAML Response xml"})
				}else{
					var attributes = data;
					req.session.attributes = attributes;
					var eIdentifier = attributes['eIdentifier'];
					var logMsg = 'Saml Res with id: '+samlResData.resId+' for '+
					'user: '+eIdentifier+' validated successfully.'
					Logger.log('info', logMsg);
					DBTools.getUserData(eIdentifier, function(err, data){
						if(err){
							res.render('errorPage', {error: "Could not get user"});
						} else {
							if(data === null){
								res.redirect('/createdocument'); 
							} else {
								res.render('documentManagement', {nPDFs: data.pdfs.length, 
									PDFs: data.pdfs});
							}
						}
					});
				}
			});
		}
	});
}

exports.readPDF = function(req, res){
	var attributes = req.session.attributes;
	var userId = attributes['eIdentifier'];  
	var pdfUuid = req.query.uuid;
	var pdfPath = './private/pdfs/' + userId + '/' + pdfUuid + '.pdf';

	fs.readFile(pdfPath, function(err, data){
		if(err){
			res.render('errorPage', {error: 'Could not Read PDF'}); 
		} else {
			res.type('application/pdf');
			res.end(data, 'binary');
		}     
	});
}

exports.deletePDF = function(req, res){
	var attributes = req.session.attributes;
	var userId = attributes['eIdentifier'];  
	var pdfUuid = req.query.uuid;

	console.log(req.query.uuid);

	if(typeof pdfUuid === 'undefined'){
		res.render('errorPage', {error: 'There was a problem with deleting your document'});
		return;
	}

	var pdfPath = './private/pdfs/' + userId + '/' + pdfUuid + '.pdf';

	DBTools.removeUserPdf(userId, pdfUuid, function(err, data){
		if(err){
			console.error(err);
			res.render('errorPage', {error: 'Could not remove PDF from DB'}); 
		}
		fs.unlink(pdfPath, function(err){
			if(err) console.error(err);
			else{
				var logMsg = 'User with id: '+userId+' deleted PDF: '+pdfPath;
				Logger.log('info', logMsg);
			}
		});
		res.redirect('/managedocuments');
	});
}

