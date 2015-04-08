package eu.stork.tads.EDS.bindings;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import eu.stork.peps.complex.attributes.crue.academic.xsd.language.diplomasupplement.InformationOnTheFunctionOfTheQualificationType;
import eu.stork.peps.complex.attributes.crue.academic.xsd.language.diplomasupplement.InformationOnTheFunctionOfTheQualificationType.ProfessionalStatus;
import eu.stork.peps.complex.attributes.crue.academic.xsd.language.diplomasupplement.RichTextTagType;


public class InfoOnTheFunctionOfQualification {
	
	static final String TAG = "Information On The Function Of The Qualification";
	
	static final String ACCESS_TO_FURTHER_STUDY = "Access to Further Study";
	static final String PROFESSIONAL_STATUS = "Professional Status";
	static final String PROFESSIONAL_STATUS_REGULATED = "Regulated";
	
	public JSONObject generate(InformationOnTheFunctionOfTheQualificationType root){
		
//		JSONObject jasonFunctionOfQualification = new JSONObject();
		JSONObject jason = new JSONObject();
		
		try {
			// Access to further study type
			
			RichTextTagType study = root.getAccessToFurtherStudy();
			if(study != null){
				for (Serializable serial : study.getContent()) {
					if(!serial.toString().contains("javax")){
						jason.accumulate(ACCESS_TO_FURTHER_STUDY, serial.toString().trim());
					}
				}	
			}
			
			// Professional Status Type
			ProfessionalStatus professionalStatus = root.getProfessionalStatus();
			if (professionalStatus != null) {
//				jason.accumulate(PROFESSIONAL_STATUS_REGULATED,
//						professionalStatus.isIsRegulatedProfession());
				for (Serializable serial : professionalStatus.getContent()) {
					jason.accumulate(PROFESSIONAL_STATUS, serial.toString()
							.trim());
				}
			}
			
//			jasonFunctionOfQualification.put(TAG, jason);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jason;
	}

}
