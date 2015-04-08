package eu.stork.tads.EDS;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.json.JSONException;
import org.json.JSONObject;

import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._1_0.assertion.CurrentStudiesType;
import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.GeneralDiplomaType;
import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.MultilingualGeneralDiplomaType;
import eu.stork.tads.EDS.currentStudies.bindings.CurrentStudiesDiploma;

/* 	

<xs:complexType name="currentStudiesType">
<xs:sequence>
 <xs:choice>
   <xs:element ref="gdp:MultilingualGeneralDiploma" />
   <xs:element ref="gdp:GeneralDiploma" />
 </xs:choice>
 <xs:element name="yearOfStudy" type="xs:integer" />
 <xs:element name="isEligibleForInternship" type="xs:boolean" />
 <xs:element name="AQAA"
  type="stork:QualityAuthenticationAssuranceLevelType" />
 <xs:any minOccurs="0"/>
</xs:sequence>
</xs:complexType>

*/


public class CurrentStudies {
	
	static CurrentStudiesDiploma currentDiplomaType = new CurrentStudiesDiploma();
	

	public String getCurrentStudiesJson(String xmlString){
	
		JAXBContext uContext;
		JSONObject jsonCurrent = new JSONObject();
		
		try {
				
			uContext = JAXBContext.newInstance(CurrentStudiesType.class);
		
		    Unmarshaller u = uContext.createUnmarshaller();

			StringReader file = new StringReader(xmlString);
			
			JAXBElement<CurrentStudiesType> root = u.unmarshal(new StreamSource(file), CurrentStudiesType.class);
			CurrentStudiesType currentStudiesRoot = root.getValue();
		
			MultilingualGeneralDiplomaType multiGeneral = currentStudiesRoot.getMultilingualGeneralDiploma();
			GeneralDiplomaType generalDiploma = currentStudiesRoot.getGeneralDiploma();
			
			//int AQAA = currentStudiesRoot.getAQAA();
			//String yearOfStudy = currentStudiesRoot.getYearOfStudy().toString();
			
			// General Diploma Type
			if(generalDiploma != null){
				jsonCurrent = currentDiplomaType.generateDiploma(generalDiploma);
			}
			
			return jsonCurrent.toString(2);
			
			
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return "error";
	}
	

}
