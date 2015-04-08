package eu.stork.tads.EDS.currentStudies.bindings;

import org.json.JSONException;
import org.json.JSONObject;

import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.AdditionalInformationType;
import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.CertificationOfTheSupplementType;
import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.GeneralDiplomaType;
import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.InformationIdentifyingTheHolderOfTheQualificationType;
import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.InformationIdentifyingTheQualificationType;
import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.InformationOnTheContentsAndResultsGainedType;
import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.InformationOnTheFunctionOfTheQualificationType;
import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.InformationOnTheLevelOfTheQualificationType;

public class CurrentStudiesDiploma {
	
	static final String TAG1 = "InformationIdentifyingTheHolderOfTheQualification";
	static final String TAG2 = "InformationIdentifyingTheQualification";
	static final String TAG3 = "InformationOnTheLevelOfTheQualification";
	static final String TAG4 = "InformationOnTheContentsAndResultsGained";
	static final String TAG5 = "InformationOnTheFunctionOfTheQualification";
	static final String TAG6 = "AdditionalInformation";
	static final String TAG7 = "CertificationoftheSupplement";
	static final String TAG8 = "InformationOnTheNationalHigherEducationSystem";
	
	
	public JSONObject generateDiploma(GeneralDiplomaType diploma){
			
		JSONObject jsonDiploma = new JSONObject();
		
	    InformationIdentifyingTheHolderOfTheQualificationType infoOfTheHolder = diploma.getInformationIdentifyingTheHolderOfTheQualification();
		InformationIdentifyingTheQualificationType infoOfTheQualification = diploma.getInformationIdentifyingTheQualification();
		InformationOnTheLevelOfTheQualificationType infoLevel = diploma.getInformationOnTheLevelOfTheQualification();
		InformationOnTheContentsAndResultsGainedType infoContents = diploma.getInformationOnTheContentsAndResultsGained();
		InformationOnTheFunctionOfTheQualificationType infoFunction = diploma.getInformationOnTheFunctionOfTheQualification();
		AdditionalInformationType additionalInformation = diploma.getAdditionalInformation();
		CertificationOfTheSupplementType certificationOfTheSupplement = diploma.getCertificationOfTheSupplement();
	    
	    
	    InfoOfTheHolder number1 = new InfoOfTheHolder();
	    InfoOfTheQualification number2 = new InfoOfTheQualification();
	    InfoOfTheLevelOfTheQualification number3 = new InfoOfTheLevelOfTheQualification();
	    InfoOnTheContentAndResults number4 = new InfoOnTheContentAndResults();
	    InfoOnTheFunctionOfQualification number5 = new InfoOnTheFunctionOfQualification();
	    AdditionalInformation number6 = new AdditionalInformation();
	    CertificationOfTheSupplement number7 = new CertificationOfTheSupplement();
	    InfoOnTheNationalHigherEducationSystem number8 = new InfoOnTheNationalHigherEducationSystem();
	    
	    try {
			jsonDiploma.put(TAG1, number1.generate(infoOfTheHolder));
			jsonDiploma.put(TAG2, number2.generate(infoOfTheQualification));
			jsonDiploma.put(TAG3, number3.generate(infoLevel));
			jsonDiploma.put(TAG4, number4.generate(infoContents));
			jsonDiploma.put(TAG5, number5.generate(infoFunction));
			jsonDiploma.put(TAG6, number6.generate(additionalInformation));
	    	if(certificationOfTheSupplement != null){
	    		jsonDiploma.put(TAG7, number7.generate(certificationOfTheSupplement));	
	    	} else {
	    		jsonDiploma.put(TAG7, "");
	    	}
			
			jsonDiploma.put(TAG8, number8.generate(diploma.getInformationOnTheNationalHigherEducationSystem()));
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    
	    return jsonDiploma;
	}

}
