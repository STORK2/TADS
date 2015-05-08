var properties = require('properties');
var url = require('url');
var propertiesPath = './resources/tads.properties';
var configs = null;

//SAML Endpoints paths
var createReturnPath = 'createdocument/samlreturn';
var manageReturnPath = 'managedocuments/samlauthreturn';

var options = {
  	path: true,
  	sections: true,
  	namespaces: true
};

exports.getConfigs = function(callback){
	properties.parse(propertiesPath, options, function(err, confs){
		if(err) return callback(err);
		
		var appUrl = confs.tads.url;
		var parsedUrl = url.parse(confs.tads.url);
		confs.tads.parsedURL = parsedUrl;
		var protocol = parsedUrl.protocol || 'http:';
		var listeningPort = parsedUrl.port;
		
		confs.tads.protocol = protocol;
		confs.tads.port = listeningPort || 80;
		confs.tads.sslPort = listeningPort || 443;
		configs = confs;
		confs.peps.createSAMLEndpoint = getSAMLEndpointForPath(createReturnPath);
		confs.peps.manageSAMLEndpoint = getSAMLEndpointForPath(manageReturnPath);

		return callback(null, confs);

  });
}

function getSAMLEndpointForPath(returnPath){

	var url;
	var returnUrl = configs.tads.samlReturnUrl;
    
  if(returnUrl.slice(-1) === '/'){
    url = returnUrl + returnPath;
  }else{
    url = returnUrl + '/' + returnPath;
  }
  
  return url;
}

