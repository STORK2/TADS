package eu.stork.tads.EDS.currentStudies.bindings;

import java.io.Serializable;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.AddressType;
import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.ContactInformationType;
import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.InformationIdentifyingTheQualificationType;
import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.LanguagesOfInstructionAndExaminationType;
import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.LanguagesOfInstructionAndExaminationType.Language;
import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.NameAndStatusOfAwardingInstitutionType;
import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.NameAndStatusOfAwardingInstitutionType.AwardingInstitution;
import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.NameAndStatusOfInstitutionAdministeringStudiesType;
import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.NameAndStatusOfInstitutionAdministeringStudiesType.InstitutionAdministeringStudies;
import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.QualificationType;
import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.RichTextTagType;
import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.TitleConferredType;




public class InfoOfTheQualification {
	
	static final String TAG = "Information Identifying The Qualification";
	
	// 2.1
	static final String NAME_AND_TITLE = "Name of qualification and title conferred";
	// 2.2
	static final String MAIN_FIELDS_STUDY_TAG = "Main field(s) of study for the qualification";
	// 2.3
	static final String NAME_AND_STATUS = "Name and status of awarding institution";
	// 2.4
	static final String NAME_AND_AWARD = "Name and status of institution administering studies";
	// 2.5
	static final String LANGUAGE_TAG = "Language(s) of instruction/examination";
	
	
	static final String LOCAL_ID = "Local ID";
	static final String NATIONAL_ID = "National ID";
	static final String QUALIFICATION_NAME = "Qualification Name";
	static final String TITLE_CONFERRED = "Title Conferred";
	
	static final String MAIN_FIELDS_STUDY = "Main Field(s) of Study";
	
	static final String AWARDING_INSTITUTION = "Awarding Institution";
	static final String AWARDING_INSTITUTION_NAME = "Name";
	static final String AWARDING_INSTITUTION_STATUS = "Status";
	static final String AWARDING_INSTITUTION_COUNTRY = "Country";
	static final String AWARDING_INSTITUTION_ID = "ID";
	static final String AWARDING_INSTITUTION_NATIONAL_ID = "National ID";
	static final String AWARDING_INSTITUTION_ERASMUS_ID = "Erasmus ID";
	static final String AWARDING_INSTITUTION_ADDITIONAL_INFO = "Additional Information";
	
	
	static final String CONTACT_LINE = "Line";
	static final String CONTACT_CITY = "City";
	static final String CONTACT_STATE_OR_REGION = "State Or Region";
	static final String CONTACT_POSTAL_CODE = "Postal Code";
	static final String CONTACT_COUNTRY = "Country";
	static final String CONTACT_EMAIL = "Email";
	static final String CONTACT_WEBSITE = "WebSite";
	static final String CONTACT_PHONE_NUMBER = "Phone Number";
	
	static final String ADMINSTERING_STUDIES = "Name And Status Of Institution Administering Studies";
	static final String ADMINSTERING_STUDIES_NATIONAL_ID = "National ID";
	static final String ADMINSTERING_STUDIES_ERASMUS_ID = "Erasmus ID";
	static final String ADMINSTERING_STUDIES_NAME = "Name";
	static final String ADMINSTERING_STUDIES_STATUS = "Status";
	static final String ADMINSTERING_STUDIES_COUNTRY = "Country";
	static final String ADMINSTERING_STUDIES_ID = "ID";
	static final String ADMINSTERING_STUDIES_ADDITIONAL_INFO = "Additional Information";
	
	static final String LANGUAGE_NAME = "Name";
	static final String LANGUAGE_NAME_VALUE = "Name Value";
	static final String LANGUAGE_PERCENT = "Percent";
	static final String LANGUAGE = "Language";
	
	public JSONObject generate(InformationIdentifyingTheQualificationType root){
		
		JSONObject jason = new JSONObject();
//		JSONObject jasonTAG = new JSONObject();
		try {
			
			// Qualification Type
			// Required types
			
			JSONObject jasonQualification = new JSONObject();
			QualificationType qualification = root.getQualification();
			jason.accumulate(NAME_AND_TITLE, qualification.getName());
			
//			jasonQualification.accumulate(QUALIFICATION_NAME, qualification.getName());
			TitleConferredType titleConferred = root.getTitleConferred();
			if(titleConferred != null){
				jason.accumulate(NAME_AND_TITLE, titleConferred.getName());	
			} else {
				jason.accumulate(NAME_AND_TITLE, "");
			}
			
			
//			jasonQualification.accumulate(TITLE_CONFERRED, titleConferredName);
			
			// Optional types
			String qualificationLocalID = qualification.getLocalID();
			if(qualificationLocalID != null){
//				jasonQualification.accumulate(LOCAL_ID, qualificationLocalID);	
			}
			
			String qualificationNationalID = qualification.getNationalID();
			if(qualificationNationalID != null){
//				jasonQualification.accumulate(NATIONAL_ID, qualificationNationalID);	
			}
			
			RichTextTagType qualificationAdditionalInfo = qualification.getAdditionalInformation();
			if (qualificationAdditionalInfo != null) {
				for (Serializable serial : qualificationAdditionalInfo
						.getContent()) {
					if (!serial.toString().contains("javax")) {
						jason.accumulate(NAME_AND_TITLE, serial.toString().trim());
//						jasonQualification.accumulate("AdditionalInformation",
//								serial.toString().trim());
					}
				}
			}
//			jason.put(NAME_AND_TITLE, jasonQualification);
			
			// MainFieldsOfStudy Type
			// Required types
			// Check to see if mainFieldsOfStudy is null
			
			RichTextTagType mainFieldOfStudy = root.getMainFieldsOfStudy();
			for (Serializable serial : mainFieldOfStudy.getContent()) {
				if(!serial.toString().contains("javax")){
					jason.accumulate(MAIN_FIELDS_STUDY_TAG, serial.toString().trim());
				}
			}
			
			
			
			// AwardingInstitution Type
			// Required types
			// NAME_AND_STATUS
			NameAndStatusOfAwardingInstitutionType nameAndStatusOfAwardingInstitution = root.getNameAndStatusOfAwardingInstitution();
			List<AwardingInstitution> awardingInstitution =  nameAndStatusOfAwardingInstitution.getAwardingInstitution();
			
			JSONObject jasonAwardingInstituion = new JSONObject();
			if(awardingInstitution.size() != 0){
				for (AwardingInstitution institution : awardingInstitution) {
					jason.accumulate(NAME_AND_STATUS, institution.getName());
					if(institution.getStatus() != null){
						jason.accumulate(NAME_AND_STATUS, institution.getStatus());	
					}
					
					
//					jasonAwardingInstituion.accumulate(AWARDING_INSTITUTION_NAME, institution.getName());
//					jasonAwardingInstituion.accumulate(AWARDING_INSTITUTION_STATUS, institution.getStatus());
//					jasonAwardingInstituion.accumulate(AWARDING_INSTITUTION_ID, institution.getAwardingInstitutionID());
//					String nationalID = institution.getNationalID();
//					if(nationalID != null){
//						jasonAwardingInstituion.accumulate(AWARDING_INSTITUTION_NATIONAL_ID, nationalID);
//					}
//					String erasmusID = institution.getErasmusID();
//					if(erasmusID != null){
//						jasonAwardingInstituion.accumulate(AWARDING_INSTITUTION_ERASMUS_ID, erasmusID);
//					}
//					jasonAwardingInstituion.accumulate(AWARDING_INSTITUTION_COUNTRY, institution.getCountry().getCountry());
					RichTextTagType instituteAdditional = institution.getAdditionalInformation();
					if(instituteAdditional != null){
						for (Serializable serial : instituteAdditional.getContent()) {
							if(!serial.toString().contains("javax")){
//								jasonAwardingInstituion.accumulate(AWARDING_INSTITUTION_ADDITIONAL_INFO, serial.toString().trim());
								jason.accumulate(NAME_AND_STATUS, serial.toString().trim());
							}
						}	
					}
//					
//					ContactInformationType contacts = institution.getContactInformation();
//					if(contacts != null){
//						JSONObject jasonAwardingInstituionContacts = new JSONObject();
//						generateContacts(contacts, jasonAwardingInstituionContacts);
//						jasonAwardingInstituion.put("Contacts", jasonAwardingInstituionContacts);	
//					}
					
				}
			}
//			jason.put(AWARDING_INSTITUTION, jasonAwardingInstituion);
			
			// NameAndStatusOfInstitutionAdministeringStudies Type
			// Required types
			// NAME_AND_AWARD
			NameAndStatusOfInstitutionAdministeringStudiesType nameAndStatusOfInstitutionAdministeringStudies = root.getNameAndStatusOfInstitutionAdministeringStudies();
			List<InstitutionAdministeringStudies> administeringStudies = nameAndStatusOfInstitutionAdministeringStudies.getInstitutionAdministeringStudies();
			
			JSONObject jasonAdministeringStudies = new JSONObject();
			if(administeringStudies.size() != 0){
				for (InstitutionAdministeringStudies institution : administeringStudies) {
					jason.accumulate(NAME_AND_AWARD, institution.getName());
					if(institution.getStatus() != null){
						jason.accumulate(NAME_AND_AWARD, institution.getStatus());	
					}
					
					
//					jasonAdministeringStudies.accumulate(ADMINSTERING_STUDIES_NAME, institution.getName());
//					jasonAdministeringStudies.accumulate(ADMINSTERING_STUDIES_STATUS, institution.getStatus());
//					
//					jasonAdministeringStudies.accumulate(ADMINSTERING_STUDIES_COUNTRY, institution.getCountry().getCountry());
//					jasonAdministeringStudies.accumulate(ADMINSTERING_STUDIES_ID, institution.getInstitutionAdministeringStudiesID());
//					
//					String nationalID = institution.getNationalID();
//					if(nationalID != null){
//						jasonAdministeringStudies.accumulate(ADMINSTERING_STUDIES_NATIONAL_ID, nationalID);
//					}
//					
//					String erasmusID = institution.getErasmusID();
//					if(erasmusID != null){
//						jasonAdministeringStudies.accumulate(ADMINSTERING_STUDIES_ERASMUS_ID, erasmusID);
//					}
					
					RichTextTagType instituteAdditional = institution.getAdditionalInformation();
					if(instituteAdditional != null){
						for (Serializable serial : instituteAdditional.getContent()) {
							if(!serial.toString().contains("javax")){
//								jasonAdministeringStudies.accumulate(ADMINSTERING_STUDIES_ADDITIONAL_INFO, serial.toString().trim());
								jason.accumulate(NAME_AND_AWARD, serial.toString().trim());
							}
						}	
					}
					
//					ContactInformationType contacts = institution.getContactInformation();
//					if(contacts != null){
//						JSONObject jasonAdministeringStudiesContacts = new JSONObject();
//						generateContacts(contacts, jasonAdministeringStudiesContacts);
//						jasonAdministeringStudies.put("Contacts", jasonAdministeringStudiesContacts);	
//					}
				}
			}
//			jason.put(ADMINSTERING_STUDIES, jasonAdministeringStudies);

			// Language Type
			// LANGUAGE_TAG
			LanguagesOfInstructionAndExaminationType languagesOfInstructionAndExamination = root.getLanguagesOfInstructionAndExamination();
			List<Language> languages = languagesOfInstructionAndExamination.getLanguage();
			
			if(languages.size() != 0){
//				JSONObject jasonLanguage = new JSONObject();
				for (Language language : languages) {
//					jasonLanguage.accumulate(LANGUAGE_NAME_VALUE, language.getLanguage().value());
//					if(language.getPercent() != null){
//						jasonLanguage.accumulate(LANGUAGE_PERCENT, language.getPercent().toString());
//					}
					
					if(language.getContent().isEmpty()){
						jason.put(LANGUAGE_TAG, "");
					}
					for (Serializable content : language.getContent()) {
//						jasonLanguage.accumulate(LANGUAGE_NAME, content.toString().trim());
						jason.accumulate(LANGUAGE_TAG, content.toString().trim());
					}
							
				}
//				jason.put(LANGUAGE, jasonLanguage);
			}
			

//			jasonTAG.put(TAG, jason);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return jason;
	}
	
	public void generateContacts(ContactInformationType contacts, JSONObject jObject){
		
		try {
			AddressType address = contacts.getAddress();
			List<String> lines = address.getLine();
			for (String line : lines) {
				jObject.accumulate(CONTACT_LINE, line);
			}
			
			jObject.accumulate(CONTACT_CITY, address.getCity());
			jObject.accumulate(CONTACT_POSTAL_CODE, address.getPostalCode());
			jObject.accumulate(CONTACT_STATE_OR_REGION, address.getStateOrRegion());
			jObject.accumulate(CONTACT_COUNTRY, address.getCountry().getValue());
			
			
			for (String email : contacts.getEmail()) {
				jObject.accumulate(CONTACT_EMAIL, email);
			}
			for (String phoneNumber : contacts.getPhoneNumber()) {
				jObject.accumulate(CONTACT_PHONE_NUMBER, phoneNumber);
			}			
			for (String webSite : contacts.getWebSite()) {
				jObject.accumulate(CONTACT_WEBSITE, webSite);		
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
