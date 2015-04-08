var java = require('java');
var url = require('url');

var configs = GLOBAL.configs;
var spCountry = configs.tads.country;

var spId = configs.tads.id;
var spName = configs.tads.name;
var spSector = configs.tads.sector;
var spApplication = configs.tads.application;
var issuer = url.parse(configs.tads.url).hostname;

java.classpath.push('./java/STORKTADSUtils-1.4.2.jar');

STORKSAMLUtils = java.newInstanceSync('eu.stork.tads.saml.SAMLUtils', 
	issuer, spId, spName, spCountry, spSector, spApplication);

exports.createSAMLRequest = function(samlAttrs, callback){

	var attrList = java.newInstanceSync("eu.stork.peps.auth.commons.PersonalAttributeList");
	var pepsURL = samlAttrs.pepsURL;
	var qaa = samlAttrs.qaa;
	var citizen = samlAttrs.citizenCC;
	var returnURL = samlAttrs.returnURL;
	var reqAttrList = samlAttrs.attrList;

	for (var attr in reqAttrList){
		var pAttr = java.newInstanceSync("eu.stork.peps.auth.commons.PersonalAttribute");
		pAttr.setNameSync(attr);
		pAttr.setIsRequiredSync(reqAttrList[attr]);
		attrList.addSync(pAttr);
	}

	STORKSAMLUtils.generateSAMLRequest(pepsURL, qaa, citizen, returnURL, 
		attrList, function(err, STORKAuthnReq){
			if(err) return callback(err);

			var sAMLRequestToken = STORKAuthnReq.getTokenSamlSync();
			var sAMLRequestId = STORKAuthnReq.getSamlIdSync();
			var sAMLRequest = new Buffer(sAMLRequestToken).toString('base64');

			var samlReq = {
				country: spCountry,
				SAMLRequest: sAMLRequest,
				destination: pepsURL,
				SAMLReqId: sAMLRequestId
			};

			return callback(null, samlReq);
	});
}

exports.getSAMLResData = function(samlRes, remoteHostIP, callback){
	STORKSAMLUtils.validateSAMLResponse(samlRes, remoteHostIP, function(err, sAMLRes){

		if(err) return callback(err);
		var samlResponseXML = new Buffer(samlRes, 'base64').toString();
		var samlResData = {
			resId : sAMLRes.getSamlIdSync(),
			XML : samlResponseXML,
			isFail : sAMLRes.isFailSync()
		}
		return callback(null, samlResData);
	});
}



