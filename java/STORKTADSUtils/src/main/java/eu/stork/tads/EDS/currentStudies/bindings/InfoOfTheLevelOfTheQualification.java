package eu.stork.tads.EDS.currentStudies.bindings;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.InformationOnTheLevelOfTheQualificationType;
import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.InformationOnTheLevelOfTheQualificationType.Level;
import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.InformationOnTheLevelOfTheQualificationType.OfficialLengthOfProgramme;
import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.RichTextTagType;




public class InfoOfTheLevelOfTheQualification {
	
	static final String TAG = "Information On The Level Of The Qualification";
	
	// 3.1
	static final String LEVEL_QUALIFICATION = "Level of qualification";
	// 3.2
	static final String PROGRAMME_LENGHT = "Official length of programme";
	// 3.3
	static final String ACCESS_REQUIREMENTS = "Access Requirements";
	
	static final String LEVEL = "Level";
	static final String LEVEL_ISCED1997 = "ISCED 1997";
	static final String LEVEL_ISCED2011 = "ISCED 2011";
	static final String LEVEL_EHEA_FRAMEWORK = "EHEA Framework";
	static final String LEVEL_NFQ = "NFQ";
	static final String LEVEL_NFQ_ADDITIONAL = "Additional Information";
	
	static final String LENGTH_OF_PROGRAMME = "Official Length Of Programme";
	static final String LENGTH_OF_PROGRAMME_ECTS_CREDITS = "ECTS Credits";
	static final String LENGTH_OF_PROGRAMME_YEARS = "Years";
	static final String LENGTH_OF_PROGRAMME_SEMESTERS = "Semesters";
	static final String LENGTH_OF_PROGRAMME_ADDITIONAL = "Additional Information";
	
	
	
	
	public JSONObject generate(InformationOnTheLevelOfTheQualificationType root){
		
		
//		JSONObject jasonLevelOfQualification = new JSONObject();
		JSONObject jason = new JSONObject();
		try {
			
			// Level Type
			Level level = root.getLevel();

			// Required
//			jason.put(LEVEL_EHEA_FRAMEWORK, level.getEheaFramework().value());
			
			// Optional
//			if(level.getIsced1997() != null){
//				jason.put(LEVEL_ISCED1997, level.getIsced1997());
//			}
//			
//			if(level.getIsced2011() != null){
//				jason.put(LEVEL_ISCED2011, level.getIsced2011());
//			}
//			
//			if(level.getNfq() != null){
//				jason.put(LEVEL_NFQ, level.getNfq());
//			}
			
			if(level.getContent() != null){
				for (Serializable serial : level.getContent()) {
//					jason.accumulate(LEVEL_NFQ_ADDITIONAL, serial.toString().trim());
					jason.accumulate(LEVEL_QUALIFICATION, serial.toString().trim());
				}	
			}
			
			// Official Length Programme Type
			OfficialLengthOfProgramme official = root.getOfficialLengthOfProgramme();
			
			// Required
//			jason.put(LENGTH_OF_PROGRAMME_YEARS, official.getYears());
			
			// Optional
			
//			if(official.getEctsCredits() != null){
//				jason.put(LENGTH_OF_PROGRAMME_ECTS_CREDITS, official.getEctsCredits());
//			}
//			
//			if(official.getSemesters() != null){
//				jason.put(LENGTH_OF_PROGRAMME_SEMESTERS, official.getSemesters());
//			}
			
			if(official.getContent() != null){
				if(official.getContent().isEmpty()){
					jason.put(PROGRAMME_LENGHT, official.getYears());
				}
				for (Serializable serial : official.getContent()) {
					jason.accumulate(PROGRAMME_LENGHT, serial.toString().trim());
					
				}
			}
			
			
			// Access Requirements Type
			RichTextTagType accessRequirements = root.getAccessRequirements();

			if(accessRequirements != null){
				if(accessRequirements.getContent().isEmpty()){
					jason.put(ACCESS_REQUIREMENTS, "");
				}
				for (Serializable serial : accessRequirements.getContent()) {
					jason.accumulate(ACCESS_REQUIREMENTS, serial.toString().trim());
				}
				
			} else {
				jason.put(ACCESS_REQUIREMENTS, "");
			}
//			jasonLevelOfQualification.put(TAG, jason);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	
		return jason;
	}

}
