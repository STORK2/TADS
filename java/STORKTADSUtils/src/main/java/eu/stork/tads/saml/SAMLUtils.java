package eu.stork.tads.saml;

import eu.stork.peps.auth.commons.PEPSUtil;
import eu.stork.peps.auth.commons.PersonalAttributeList;
import eu.stork.peps.auth.commons.STORKAuthnRequest;
import eu.stork.peps.auth.commons.STORKAuthnResponse;
import eu.stork.peps.auth.engine.STORKSAMLEngine;
import eu.stork.peps.exceptions.STORKSAMLEngineException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SAMLUtils{
	
	static final Logger logger = LoggerFactory.getLogger(SAMLUtils.class.getName());
	
	/*Service Provider Parameters*/
	private static String spId;
	private static String providerName;
	private static String spCountry;
	private static String spSector;
	private static String spApplication;
	private static String issuer;
	private static STORKSAMLEngine sAMLEngine; 
	
	
	public SAMLUtils(String issuer, String spId, String providerName, String spCountry, String spSector, 
			String spApplication){
		SAMLUtils.spId = spId;
		SAMLUtils.providerName = providerName;
		SAMLUtils.spCountry = spCountry;
		SAMLUtils.spSector = spSector;
		SAMLUtils.spApplication = spApplication;
		SAMLUtils.issuer = issuer;
		SAMLUtils.sAMLEngine = STORKSAMLEngine.getInstance("SP");	
	}
	
	public STORKAuthnRequest generateSAMLRequest(String pepsUrl, String qaa, String citizen, 
			String returnUrl, PersonalAttributeList attrs){
		
		STORKAuthnRequest authnRequest = new STORKAuthnRequest();
		
		authnRequest.setIssuer(issuer);
		authnRequest.setDestination(pepsUrl);
		authnRequest.setCitizenCountryCode(citizen);
		authnRequest.setProviderName(providerName);	
		authnRequest.setQaa(Integer.parseInt(qaa));
		authnRequest.setAssertionConsumerServiceURL(returnUrl);
		authnRequest.setSpSector(spSector);
		//authnRequest.setSpInstitution(spInstitution);
		authnRequest.setSpApplication(spApplication);
		authnRequest.setSpCountry(spCountry);
		authnRequest.setSPID(spId);
		authnRequest.setPersonalAttributeList(attrs);
		
		try {
			authnRequest = SAMLUtils.sAMLEngine.generateSTORKAuthnRequest(authnRequest);
		} catch (STORKSAMLEngineException e) {
			System.out.println("Could not generate token for SAML Request: " + e.getMessage());
			authnRequest = null;
			return authnRequest;
		}
		
		return authnRequest;
	}
	
	public STORKAuthnResponse validateSAMLResponse(String SAMLResponse, String remoteHostIP){
		
		STORKAuthnResponse authnResponse = null;
		byte[] decSamlToken = PEPSUtil.decodeSAMLToken(SAMLResponse);		
		
		try {
			//validate SAML Token
			authnResponse = sAMLEngine.validateSTORKAuthnResponse(decSamlToken, remoteHostIP);				
		}catch(STORKSAMLEngineException e){	
			logger.error(e.getMessage());
			System.out.println("Could not validate SAML Response: " + e.getMessage());
			return authnResponse;
		}
		
		return authnResponse;
	}
}
