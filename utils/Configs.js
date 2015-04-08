var configs = null;
var properties = require('properties');
var url = require('url');
var propertiesPath = './resources/tads.properties';

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
    var protocol = parsedUrl.protocol || 'http:';
    var listeningPort = parsedUrl.port;

    confs.tads.protocol = protocol;
    confs.tads.port = listeningPort || 80;
    confs.tads.sslPort = listeningPort || 443;

    return callback(null, confs);
  
  });
}
