package eu.stork.tads.EDS.currentStudies.bindings;

import org.json.JSONException;
import org.json.JSONObject;

import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.CertificationOfTheSupplementType;
import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.OfficialCertifyingType;
import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.OfficialStampType;
import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.OfficialsCertifyingType;



public class CertificationOfTheSupplement {
	
	// 7.1
	static final String DATE = "Date";
	// 7.2
	static final String NAME_AND_SIGNATURE = "Signature";
	// 7.3
	static final String CAPACITY = "Capacity";
	
	public JSONObject generate(CertificationOfTheSupplementType root){
		
		JSONObject jason = new JSONObject();
		
		try {
			// Required Types
			jason.put(DATE, root.getCertificationDate().toString());
			
			OfficialsCertifyingType officialsCertifying = root.getOfficialsCertifying();
			
			JSONObject jasonOfficials;
			for (OfficialCertifyingType official : officialsCertifying.getOfficialCertifying()) {
				jasonOfficials = new JSONObject();
		
				String name = new String();
				
				if (official.getGivenName() != null) {
					for (String givenName : official.getGivenName().getName()) {
						if(name.isEmpty()){
							name = givenName;
						} else {
							name = name + " " + givenName;
						}
						
					}
				}
				
				if (official.getFamilyName() != null) {
					for (String surname : official.getFamilyName().getSurname()) {
						if(surname.isEmpty()){
							name = surname;
						} else {
							name = name + " " + surname;
						}
					}
				}
				jasonOfficials.put("Name", name);
				jasonOfficials.put("Capacity", official.getCapacity());
				jason.accumulate(NAME_AND_SIGNATURE, jasonOfficials);
				
			}
			
			OfficialStampType officialStamp = root.getOfficialStamp();
			
			// OfficialStampType
			if(officialStamp != null){

			}

			
//			jasonTAG.put(TAG, jason);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return jason;
	}

}
