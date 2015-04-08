package eu.stork.tads.EDS;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.json.JSONException;
import org.json.JSONObject;

import eu.stork.peps.complex.attributes.crue.academic.xsd.language.diplomasupplement.MultilingualDiplomaSupplementType;
import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._1_0.assertion.DiplomaSupplementType;
import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.GeneralDiplomaType;
import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.MultilingualGeneralDiplomaType;
import eu.stork.tads.EDS.bindings.Diploma;

public class DiplomaSupplement {

	/*
	 * 
	 	<xs:complexType name="diplomaSupplementType">
   			<xs:sequence>
     			<xs:choice>
       				<xs:element ref="dps:MultilingualDiplomaSupplement" />
       				<xs:element ref="dps:DiplomaSupplement" />       
       				<xs:element ref="gdp:MultilingualGeneralDiploma" />
       				<xs:element ref="gdp:GeneralDiploma" />
     			</xs:choice>
     			<xs:element name="AQAA"
      			type="stork:QualityAuthenticationAssuranceLevelType" />
     			<xs:any minOccurs="0"/>
  			</xs:sequence>
		</xs:complexType>
	 * 
	 */
	static Diploma diplomaType = new Diploma();


	public String getDiplomaSupplementJson(String xmlString){

		JAXBContext uContext;
		JSONObject jsonDiploma = new JSONObject();
		try{
			uContext = JAXBContext.newInstance(DiplomaSupplementType.class);
			Unmarshaller u = uContext.createUnmarshaller();

			StringReader file = new StringReader(xmlString);
			
			JAXBElement<DiplomaSupplementType> root = u.unmarshal(new StreamSource(file), DiplomaSupplementType.class);
			DiplomaSupplementType diplomaRoot = root.getValue();

			MultilingualGeneralDiplomaType multiGeneral = diplomaRoot.getMultilingualGeneralDiploma();
			MultilingualDiplomaSupplementType multi = diplomaRoot.getMultilingualDiplomaSupplement();
			GeneralDiplomaType general = diplomaRoot.getGeneralDiploma();
			eu.stork.peps.complex.attributes.crue.academic.xsd.language.diplomasupplement.DiplomaSupplementType diploma = diplomaRoot.getDiplomaSupplement();


			if(multiGeneral != null){
				// Call multiGeneral method
			}
			if(multi != null){
				// Call multi method
			}
			if(general != null){
				// Call general method
			}
			if(diploma != null){
				// Call diploma method
				jsonDiploma = diplomaType.generateDiploma(diploma);
			}

			return jsonDiploma.toString(2);

		}catch(JAXBException e){

			e.printStackTrace();

		}catch(JSONException e){

			e.printStackTrace();

		}
		return "error";

	}
}
