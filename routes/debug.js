var fs = require('fs');
var XMLTools = require('../tools/XMLTools');
var DBTools = require('../tools/DBTools');


exports.createDocumentFromSAMLFile = function(req, res){

	var readXML = fs.readFileSync('./resources/debug/saml1.xml', {encoding:'utf8'});

	XMLTools.getSAMLAttributes(readXML, function(data){

		if(data === null || data.size === 0){
			console.error('Error parsing xml');
			req.session.isAuth = false;
			res.render('error', {error: "Error parsing SAML Response xml"});
		}

		var attributes = data;
		req.session.attributes = attributes;
		var eIdentifier = attributes['eIdentifier'][0];

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
						res.redirect('/createdocument/createpdf');
					}
				});
			}else{
				//User already exists, no need to create it
				res.redirect('/createdocument/createpdf');
			}

		});

	});	

}