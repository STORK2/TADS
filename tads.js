//
//Module dependencies
//
var fs = require('fs');
var express = require('express');
var path = require('path');
var bodyParser = require('body-parser');
var session = require('express-session');
var http = require('http');
var https = require('https');
var url = require('url');
var helmet = require('helmet');
var app = express();

var Configs = require('./utils/Configs');

Configs.getConfigs(function(err, confs){
	if(err) return console.error(err);

	GLOBAL.configs = confs;

	var XMLTools = require('./tools/XMLTools');
	var SAMLTools = require('./tools/SAMLTools');

	//Routes
	var create = require('./routes/create');
	var manage = require('./routes/manage');
	var verify = require('./routes/verify');

	if(process.env.NODE_ENV === 'development'){
		var debugRoute = require('./routes/debug');
	}

	//Path port and protocol configurations
	var listeningPort = confs.tads.port;
	var protocol = confs.tads.protocol;
	var sslPort = confs.tads.sslPort;
	
	// Express App Setup
	app.set('views', path.join(__dirname, 'views'));
	app.set('view engine', 'ejs');
	
	//Middleware Setup
	app.use(bodyParser.json());
	app.use(bodyParser.urlencoded({
		extended: true
	}));
	
	var sessionOptions = {
		secret: configs.tads.secret,
		resave: false,
		saveUninitialized: false,
		cookie: { secure: true }
	}

	if(configs.proxy.reverseProxy === true){
		app.set('trust proxy', 1);
		sessionOptions['proxy'] = true;
	}

	app.use(session(sessionOptions));
	
	app.use(express.static(path.join(__dirname, 'public')));
	var httpRedirection = confs.https.httpRedirection;
	if(protocol === 'https:' && httpRedirection === true){
		app.use(function(req, res, next) {
			if(!req.secure) {
				res.redirect(['https://', req.get('Host'), ':', listeningPort].join(''));
				return;
			}
			next();
		});
	}

	//HSTS Setup
	var ninetyDaysInMilliseconds = 7776000000;
	app.use(helmet.hsts({ maxAge: ninetyDaysInMilliseconds }));
	
	//Index Page
	app.get('/', function(req, res, next){
		res.render('home');
	});

	//Create a new document
	app.get('/createdocument', create.selectAttr);
	app.post('/createdocument/samlreq', create.SAMLReq);
	app.post('/createdocument/samlreturn', function(req, res, next){
		req.session.lastPage = 'SAMLRETURN';
		create.SAMLRes(req, res);
	});
	
	app.get('/createdocument/createpdf', function(req, res, next){
		if(req.session.lastPage === 'SAMLRETURN'){
			req.session.lastPage = '';
			create.generatePDF(req, res);
		}else{
			if(process.env.NODE_ENV === 'development'){
				create.generatePDF(req, res);
			}else{
				res.redirect('/');
			}
		}
	});

	//Manage Documents
	app.get('/managedocuments', function(req, res, next){
		req.session.lastPage = 'ROOTMANAGEDOCUMENTS';
		manage.manageDocuments(req, res);
	}); 
	app.post('/managedocumentsauth/samlreq', function(req, res, next){
		if(req.session.lastPage === 'ROOTMANAGEDOCUMENTS'){
			req.session.lastPage = 'MANAGESAMLREQ';
			manage.SAMLAuthReq(req, res);
		}else{
			if(process.env.NODE_ENV === 'development'){
				manage.SAMLAuthReq(req, res);
			}else{
				res.redirect('/');
			}
		}
	});
	app.post('/managedocuments/samlauthreturn', function(req, res, next){
		if(req.session.lastPage === 'MANAGESAMLREQ'){
			req.session.lastPage = 'SAMLAUTHRETURN'
			manage.SAMLAuthRes(req, res);
		}else{
			if(process.env.NODE_ENV === 'development'){
				manage.SAMLAuthRes(req, res);
			}else{
				res.redirect('/');
			}
		}
	});
	app.get('/managedocuments/display', manage.readPDF);
	app.get('/managedocuments/delete', manage.deletePDF);

	//Verify Documents
	app.get('/verifydocument', verify.getEmail);
	app.post('/verifydocument/sendmail', verify.sendEmail);
	app.get('/verifydocument/checkurl', verify.checkVerifierUrl);
	app.post('/verifydocument/getpdf', verify.getPDFPath);
	app.get('/verifydocument/display', verify.displayPDF);
	app.get('/logout', function(req, res){
		req.session.destroy(function(){
			res.render('logout');
		});
	});

	if(process.env.NODE_ENV === 'development'){
		app.get('/createdocfromfile', debugRoute.createDocumentFromSAMLFile);
	}

	app.get('*', function(req, res){
		res.render('errorPage', {error: '404, Page not Found'});
	});

	
	///////////////////////////
	//Starting the web server//
	///////////////////////////

	if(protocol === 'https:'){
		if(httpRedirection === true){
			http.createServer(app).listen(80);
			console.log('Server started with http protocol on port: 80');
		}
		
		var HTTPSopts = {
			key: fs.readFileSync(configs.https.privKeyPath),
			cert: fs.readFileSync(configs.https.certPath)
		};
		https.createServer(HTTPSopts, app).listen(sslPort);
		console.log('Server started with https protocol on port: '+sslPort);
	
	}else if(protocol === 'http:'){
		http.createServer(app).listen(listeningPort);
		console.log('Server started with http protocol on port: '+listeningPort);
	}else{
		console.error('The provided protocol in the url configuration of the file \
			properties/tads.properties is invalid. Value must be "http://" or "https://"');
	}
});
