var fs = require('fs');
var uuid = require('node-uuid');
var nodemailer = require('nodemailer');
var smtpTransport = require('nodemailer-smtp-transport');

var DBTools = require('../tools/DBTools');
var Logger = require('../utils/Logger');

var configs = GLOBAL.configs;

exports.getEmail = function(req, res){
	res.render('verifierEmail.ejs', {key: null});
}

exports.sendEmail = function(req, res){
	var userEmail = req.body.email;
	var newUuid = uuid.v4();
	req.session.uuid = newUuid;

	DBTools.addVerifierUrl(newUuid, function(err, url){
		var tadsURL = configs.tads.url;

		if(err){
			res.render('errorPage', {error: "There was an error creating url: " + err});
		}
		if(tadsURL.substr(tadsURL.length-1) != '/'){
			tadsURL = tadsURL + '/';
		}

		var smtpOptions = {
			host: configs.email.host,
			port: configs.email.port,
			secure: true,
			debug: true,
			auth: {
				user: configs.email.user,
				pass: configs.email.pass
			}
		}

		var transporter = nodemailer.createTransport(smtpTransport(smtpOptions));

		var msgtxt = 'Please follow the URL below to get access to the document verification page.\n\n'+tadsURL+'verifydocument/checkurl/?uuid='+newUuid;

		var mailOptions = {
			from: 'TADS Service '+'<'+configs.email.addr+'>',
			to: userEmail,
			subject: 'TADS Document Verification Link',
			text: msgtxt
		}

		transporter.sendMail(mailOptions, function(err, info){
			if(err){
				console.error(err);
				Logger.log('Error', 'There was an error sending the email to '+userEmail+': '+err);
				res.render('verifierEmail', {key: 'failure'});
			}else{
				Logger.log('Info', 'Verification url email was succesfully sent to: '+userEmail);
				res.render('verifierEmail', {key: 'success'});	
			}
		});
	});
}

exports.checkVerifierUrl = function(req, res){
	
	if(typeof req.session.uuid === 'undefined'){
		res.render('errorPage', {error: "404, Page not Found"});
	}

	var verifierSessUUID = req.session.uuid;
	var verifierUUID = req.query.uuid;

	DBTools.checkVerifierUrl(verifierUUID, function(err, exists){
		if(err) res.render('errorPage', {error: 'There was an error: '+ err});
		if(!exists){
			res.render('errorPage', {error: 'This session is no longer valid'});
		}else{
			if(verifierSessUUID === verifierUUID){
				DBTools.delVerifierUrl(verifierUUID, function(err, result){
					if(err) res.render('errorPage', 'There was an error: '+err);
					Logger.log('info', 'Url with uuid "'+verifierUUID+'" successfully validated');
					res.render('qrcodePage', {country: configs.tads.country});
				});
			}else{
				res.render('errorPage', {error: 'This session is no longer valid'});
			}
		}
	});
}

exports.getPDFPath = function(req, res){
	var uuid = req.body.uuid;
	
	DBTools.getPdfUser(uuid, function(err, user){
		if(err){
			console.error(err);
			res.render('errorPage', {error: "There was an error: "+err});
		}
		if(user === null){
			console.log("No matching PDF was found in database");
			res.render('errorPage', {error: "No matching PDF was found"});
		}else{
			
			var path = "./private/pdfs/" + user.electronicId + "/" + uuid + ".pdf";

			if(fs.existsSync(path)){
				req.session.pdfPath = path;
				res.redirect('/verifydocument/display');
			}else{
				console.error("PDF File not found");
				res.render('errorPage', {error: "PDF File not found"});
			}
		}
	});
}

exports.displayPDF = function(req, res){

	var location = req.session.pdfPath;
	
	if(typeof location === 'string'){
		fs.readFile(location, function(err, data){
			if(err){
				res.render('errorPage', {error: "Could not Read PDF"}); 
			}else{
				res.type('application/pdf');
				res.end(data, 'binary');
			}   
		});
	}else{
		res.render('errorPage', {error: "Could not Read PDF"}); 
	}
}
