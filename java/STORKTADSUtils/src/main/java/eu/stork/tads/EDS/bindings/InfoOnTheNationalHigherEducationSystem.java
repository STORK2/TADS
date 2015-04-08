package eu.stork.tads.EDS.bindings;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import eu.stork.peps.complex.attributes.crue.academic.xsd.language.diplomasupplement.RichTextTagType;

public class InfoOnTheNationalHigherEducationSystem {
	
	static final String TAG = "Certification of the Supplement";
	static final String CERT = "Certification Info";
	
	public JSONObject generate(RichTextTagType content){
		
		JSONObject jason = new JSONObject();
//		JSONObject jasonTAG = new JSONObject();
		
		try {
			if(content != null){
				for (Serializable serial : content.getContent()) {
					if(!serial.toString().contains("javax")){
						jason.put(CERT, serial.toString().trim());
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
