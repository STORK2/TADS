package eu.stork.tads.EDS.bindings;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import eu.stork.peps.complex.attributes.crue.academic.xsd.language.diplomasupplement.FamilyNameType;
import eu.stork.peps.complex.attributes.crue.academic.xsd.language.diplomasupplement.GivenNameType;
import eu.stork.peps.complex.attributes.crue.academic.xsd.language.diplomasupplement.InformationIdentifyingTheHolderOfTheQualificationType;
import eu.stork.peps.complex.attributes.crue.academic.xsd.language.diplomasupplement.InformationIdentifyingTheHolderOfTheQualificationType.CountryOfBirth;
import eu.stork.peps.complex.attributes.crue.academic.xsd.language.diplomasupplement.InformationIdentifyingTheHolderOfTheQualificationType.Gender;



public class InfoOfTheHolder {
	
	static final String TAG = "Information Identifying TheHolder Of The Qualification";
	
	//1.1
	static final String FIRST_NAME = "First Name";
	
	//1.2
	static final String SURNAME = "Surname";
	
	//1.3
	static final String DATE_OF_BIRTH = "Date Of Birth";
	
	//1.4
	static final String PLACE_OF_BIRTH = "PlaceOfBirth";
	
	//1.5
	static final String STUDENT_NUMBER = "Student identification number";
	
	
	
	static final String COUNTRY_OF_BIRTH = "Country of Birth";
	
	static final String GENDER = "Gender";
	
	public JSONObject generate(InformationIdentifyingTheHolderOfTheQualificationType root){
		
		
		JSONObject jason = new JSONObject();
		try {
			
			// Required Types
			FamilyNameType familyName = root.getFamilyName();
			List<String> surnames = familyName.getSurname();
			String surname = new String();
			for (String name : surnames) {
				if(surname.isEmpty()){
					surname = name;
				} else {
					surname = surname + " " + name;
				}
			}
			
			jason.put(SURNAME, surname);
			
			
			
			GivenNameType givenName = root.getGivenName();	    
			List<String> givenNames = givenName.getName();
			String firstName = new String();
			for (String name : givenNames) {
				if(firstName.isEmpty()){
					firstName = name;
				} else {
					firstName = firstName + " " + name;	
				}	
			}
			jason.put(FIRST_NAME, firstName);
				
			//String dateOfBirth = root.getDateOfBirth().toString();
			String dateOfBirth = root.getDateOfBirth().getYear() + "/"
					+ root.getDateOfBirth().getMonth() + "/"
					+ root.getDateOfBirth().getDay();
			jason.accumulate(DATE_OF_BIRTH, dateOfBirth);
			
			// Optional Types
			
			String studentIdentificationNumber = root.getStudentIdentificationNumber();	    
			if(studentIdentificationNumber != null){
				jason.put(STUDENT_NUMBER, studentIdentificationNumber);
			} else {
				jason.put(STUDENT_NUMBER, "");
			}
			
			CountryOfBirth countryOfBirth = root.getCountryOfBirth();
			if(countryOfBirth != null){
				String countryValue = countryOfBirth.getValue();
			    String countryTypeValue = countryOfBirth.getCountry().value();
			    jason.accumulate(COUNTRY_OF_BIRTH, countryValue);
			}
			
			String placeOfBirth = root.getPlaceOfBirth();
			if(placeOfBirth != null){
				jason.accumulate(PLACE_OF_BIRTH, placeOfBirth);
			}
			
			if(placeOfBirth != null && countryOfBirth != null){
				String date = jason.get(DATE_OF_BIRTH).toString();
				String s = date + " " + "(" + placeOfBirth + "-" + countryOfBirth .getValue()+ ")";	
				jason.put(DATE_OF_BIRTH, s);
				jason.remove(COUNTRY_OF_BIRTH);
				jason.remove(PLACE_OF_BIRTH);
			}
			
			Gender gender = root.getGender();
			if(gender != null){
				String genderValue = gender.getValue();
			    String genderTypeValue = gender.getGender().value();
			    //jason.accumulate(GENDER, genderValue);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    
	    return jason;
	    
	}

}
