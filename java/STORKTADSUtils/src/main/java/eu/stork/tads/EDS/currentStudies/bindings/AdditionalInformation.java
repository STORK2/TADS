package eu.stork.tads.EDS.currentStudies.bindings;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.AdditionalInformationType;
import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.RichTextTagType;




public class AdditionalInformation {
	
	static final String TAG = "Additional Information";
	
	static final String SPECIFIC_INFORMATION = "Specific information";
	static final String OTHER_SOURCES = "Further information sources";
	
	public JSONObject generate(AdditionalInformationType root){
		
		JSONObject jason = new JSONObject();
//		JSONObject jasonTAG = new JSONObject();
		
		try {
			
			RichTextTagType specific = root.getSpecificInformation();
			if(specific != null){
				for (Serializable serial : specific.getContent()) {
					if(!serial.toString().contains("javax")){
						jason.accumulate(TAG, serial.toString().trim());
					}
				}	
			} else {
				jason.accumulate(TAG, "");
			}
		
			RichTextTagType other = root.getOtherSources();
			if(other != null){
				for (Serializable serial : other.getContent()) {
					if(!serial.toString().contains("javax")){
						jason.accumulate(OTHER_SOURCES, serial.toString().trim());
					}
				}	
			}
			
//			jasonTAG.put(TAG, jason);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return jason;
	}

}
