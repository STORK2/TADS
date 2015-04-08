package eu.stork.tads.EDS.currentStudies.bindings;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.CourseStructureDiagramType;
import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.CourseUnitType;
import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.CoursesGroupsType;
import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.CoursesUnitsType;
import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.GradingSchemeAndGradeDistributionGuidanceType;
import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.InformationOnTheContentsAndResultsGainedType;
import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.InformationOnTheContentsAndResultsGainedType.ModeOfStudy;
import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.ProgrammeDetailsType;
import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.ProgrammeRequirementsType;
import eu.stork.peps.complex.attributes.eu.stork.names.tc.stork._2_0.academic.generaldiploma.RichTextTagType;



public class InfoOnTheContentAndResults {
	
	static final String TAG = "Information On The Contents And Results Gained";
	
	// 4.1
	static final String MODE_STUDY = "Mode of study";
	// 4.2
	static final String PROGRAMME_REQ = "Programme requirements";
	// 4.3
	static final String PROGRAMME_DETAILS = "Programme details";
	// 4.4
	static final String GRADING = "Grading scheme and, if available, grade distribution guidance";
	// 4.5
	static final String CLASSIFICATION = "Overral classification of the qualification (in original language)";
	
	
	static final String MODEL_OF_STUDY = "Model of Study";
	static final String MODEL_OF_STUDY_ADDITIONAL = "Additional Information";
	
//	static final String PROGRAMME_REQ = "Programme Requirements";
	static final String PROGRAMME_REQ_REQ = "Requirements";
	static final String PROGRAMME_REQ_KEY_LEARNING = "Key Learning";
	
	static final String COURSES_GROUPS = "Courses Groups";
	static final String COURSES_GROUPS_NAME = "Name";
	static final String COURSES_GROUPS_HEADER_INFORMATION = "Header Information";
	static final String COURSES_GROUPS_FOOTER_INFORMATION = "Footer Information";
	
	static final String COURSES_GROUPS_GROUP = "Courses Group";
	static final String COURSES_GROUPS_GROUP_ID = "ID";
	static final String COURSES_GROUPS_GROUP_NAME = "Name";
	static final String COURSES_GROUPS_GROUP_HEADER_INFORMATION = "Header Information";
	static final String COURSES_GROUPS_GROUP_FOOTER_INFORMATION = "Footer Information";
	
	static final String COURSES_UNITS = "Courses Units";
	static final String COURSES_UNITS_GROUP_ID = "Group ID";
	static final String COURSES_UNITS_INSTITUTION_ADMIN_STUDIES_ID = "Institution ID";
	static final String COURSES_UNITS_REQUIRED_PROGRAMME = "Required by the Programme";
	static final String COURSES_UNITS_CODE = "Code";
	static final String COURSES_UNITS_SCIENTIFIC_AREA = "Scientific Area";
	
	static final String COURSES_UNITS_TITLE = "Title";
	static final String COURSES_UNITS_THEME = "Theme";
	static final String COURSES_UNITS_TYPE = "Type";
	static final String COURSES_UNITS_YEAR_OF_STUDY = "Year of Study";
	static final String COURSES_UNITS_LEVEL = "Level";
	
	static final String COURSES_UNITS_ECTS_CREDITS = "ECTS Credits";
	static final String COURSES_UNITS_LOCAL_CREDITS = "Local Credits";
	static final String COURSES_UNITS_HOURS = "Hours";
	static final String COURSES_UNITS_LANGUAGES_OF_INSTRUCTION = "LanguagesOfInstruction";
	
	static final String COURSES_UNITS_MODE_OF_DELIVERY = "Mode of Delivery";
	static final String COURSES_UNITS_WORKPLACEMENTS_COLLABORATING_INSTITUTION = "Collaborating Institution";
	static final String COURSES_UNITS_WORKPLACEMENTS_DATE_FROM = "DateFrom";
	static final String COURSES_UNITS_WORKPLACEMENTS_DATE_TO = "DateTo";
	static final String COURSES_UNITS_WORKPLACEMENTS_TRAINING_HOURS = "Training Hours";
	
	static final String COURSES_UNITS_STUDENT_PERFOMANCE_LOCAL_GRADE = "Local Grade";
	static final String COURSES_UNITS_STUDENT_PERFOMANCE_LOCAL_GRADE_YEAR = "Academic Year";
	static final String COURSES_UNITS_STUDENT_PERFOMANCE_LOCAL_GRADE_GRADE = "Grade";
	static final String COURSES_UNITS_STUDENT_PERFOMANCE_ECTS_GRADE = "ECTS Grade";
	
	static final String COURSES_UNITS_NAME_OF_LECTURES = "Name of Lectures";
	static final String COURSES_UNITS_LEARNING_OUTCOMES = "Learning Outcomes";
	static final String COURSES_UNITS_PREREQUISITES_AND_COREQUISITES = "Prerequisites And Corequisites";
	
	static final String COURSES_UNITS_REC_PROG_COMPONENTS = "Recomended Optional Programme Components";
	static final String COURSES_UNITS_COURSE_CONTENTS = "Course Contents";
	static final String COURSES_UNITS_RECOMENDED_REQ_READING = "Recomended Required Reading";
	
	static final String COURSES_UNITS_PLANED_LEARNING = "Planed Learning Activities And Teaching Method";
	static final String COURSES_UNITS_ASSESMENT = "Assesment Methods And Criteria";
	static final String COURSES_UNITS_OBSERVATIONS = "Observations";
	
	static final String MOBILITY_PROGRAMME = "Mobility Programme";
	static final String MOBILITY_PROGRAMME_TYPE = "Type";
	static final String MOBILITY_PROGRAMME_FIELDS_OF_STUDY = "Fields of Study";
	static final String MOBILITY_PROGRAMME_COUNTRY = "Country";
	static final String MOBILITY_PROGRAMME_REC_INSTI_NAME = "Receiving Institution Name";
	static final String MOBILITY_PROGRAMME_ACADEMIC_YEAR = "Academic Year";
	static final String MOBILITY_PROGRAMME_DATE_FROM = "Date From";
	static final String MOBILITY_PROGRAMME_DATE_TO = "Date To";
	static final String MOBILITY_PROGRAMME_COURSES_UNITS = "Courses Units";
	
	static final String MOBILITY_PROGRAMME_CU_CODE = "Code";
	static final String MOBILITY_PROGRAMME_CU_TITLE = "Title";
	static final String MOBILITY_PROGRAMME_CU_ECTS_CREDITS = "ECTS Credits";
	static final String MOBILITY_PROGRAMME_CU_CODE_IS_IN_LEARNING_AGREE = "Is in the Learning Agreement";
	static final String MOBILITY_PROGRAMME_CU_ADD_INFO = "Additional Information";
	
	static final String OVERRAL_CLASSIFICATION = "Overral Classification";
	static final String GRADING_SCHEME = "Grading Scheme";
	static final String GRADING_SCHEME_GUIDANCE = "Grading Scheme and Guidance";
	
	public JSONObject generate(InformationOnTheContentsAndResultsGainedType root){
		
		JSONObject jason = new JSONObject();
//		JSONObject jasonTAG = new JSONObject();
		
		try {
			// Model Of Study Type
			ModeOfStudy modeOfStudy = root.getModeOfStudy();
			
			// Required
			jason.put(MODE_STUDY, modeOfStudy.getModeOfStudy().value());
			
			// Programme Requirements Type
			ProgrammeRequirementsType programmeRequirements = root.getProgrammeRequirements();
			
			// Required
			if(programmeRequirements != null){
				RichTextTagType requirements = programmeRequirements.getRequirements();	
				if(requirements != null){
					if(requirements.getContent().isEmpty()){
						jason.accumulate(PROGRAMME_REQ, "");
					}
					for (Serializable serial : requirements.getContent()) {
						if(!serial.toString().contains("javax")){
							jason.accumulate(PROGRAMME_REQ, serial.toString().trim());
						}
					}	
				} else {
					jason.accumulate(PROGRAMME_REQ, "");
				}
				
				//Optional
				RichTextTagType keyLearnings = programmeRequirements.getKeyLearningOutcomes();	
				if(keyLearnings != null){
					if(keyLearnings.getContent().isEmpty()){
						jason.accumulate(PROGRAMME_REQ, "");
					}
					for (Serializable serial : keyLearnings.getContent()) {
						if(!serial.toString().contains("javax")){
							jason.accumulate(PROGRAMME_REQ, serial.toString().trim());
						}
					}	
				} else {
					jason.accumulate(PROGRAMME_REQ, "");
				}

			} else {
				jason.accumulate(PROGRAMME_REQ, "");
			}
			
			
			
						
			// Programme Details Type
			ProgrammeDetailsType programmeDetails = root.getProgrammeDetails();
			
			CourseStructureDiagramType coursesDiagram = programmeDetails.getCourseStructureDiagram();
			
			// Required
			CoursesGroupsType coursesGroups = coursesDiagram.getCoursesGroups();
			JSONObject jasonCoursesGroups = new JSONObject();
			
			if(coursesGroups != null){
				RichTextTagType footerInfo = coursesGroups.getFooterInformation();	
				if(footerInfo != null){
					for (Serializable serial : footerInfo.getContent()) {
						if(!serial.toString().contains("javax")){
							jasonCoursesGroups.accumulate(COURSES_GROUPS_FOOTER_INFORMATION, serial.toString().trim());
						}
					}	
				}
				RichTextTagType headerInfo = coursesGroups.getHeaderInformation();	
				if(headerInfo != null){
					for (Serializable serial : headerInfo.getContent()) {
						if(!serial.toString().contains("javax")){
							jasonCoursesGroups.accumulate(COURSES_GROUPS_HEADER_INFORMATION, serial.toString().trim());
						}
					}	
				}
				if(coursesGroups.getName() != null){
					jasonCoursesGroups.put(COURSES_GROUPS_NAME, coursesGroups.getName());	
				}
			}
			
			
			
			
			
			// Note that this is single Group not Groups
			// There is a group list inside of group?!?!!?
//			JSONObject jasonCoursesGroup = new JSONObject();
//			for (CoursesGroupType groups : coursesGroups.getCoursesGroup()) {
//				jasonCoursesGroup.accumulate(COURSES_GROUPS_GROUP_ID, groups.getGroupID());
//				jasonCoursesGroup.accumulate(COURSES_GROUPS_GROUP_NAME, groups.getName());
//				
//				footerInfo = coursesGroups.getFooterInformation();	
//				if(footerInfo != null){
//					for (Serializable serial : footerInfo.getContent()) {
//						if(!serial.toString().contains("javax")){
//							jasonCoursesGroup.accumulate(COURSES_GROUPS_FOOTER_INFORMATION, serial.toString().trim());
//						}
//					}	
//				}
//				
//				headerInfo = coursesGroups.getHeaderInformation();	
//				if(headerInfo != null){
//					for (Serializable serial : headerInfo.getContent()) {
//						if(!serial.toString().contains("javax")){
//							jasonCoursesGroup.accumulate(COURSES_GROUPS_HEADER_INFORMATION, serial.toString().trim());
//						}
//					}	
//				}
//			}
//			
//			jason.put(COURSES_GROUPS, jasonCoursesGroups);
//			jason.put(COURSES_GROUPS_GROUP, jasonCoursesGroup);
//			
			
			// Optional
			CoursesUnitsType coursesUnits = coursesDiagram.getCoursesUnits();
			
			JSONObject jasonCoursesUnits;
//			JSONArray jasonCoursesUnitsArray = new JSONArray();
			for (CourseUnitType courseUnit : coursesUnits.getCourseUnit()) {		
				jasonCoursesUnits = new JSONObject();
				
				if(courseUnit.getCode() != null){
//					jasonCoursesUnits.accumulate(COURSES_UNITS_GROUP_ID, courseUnit.getCode());
					jasonCoursesUnits.put("Code", courseUnit.getCode());
				}
				
//				jasonCoursesUnits.accumulate(COURSES_UNITS_GROUP_ID, courseUnit.getGroupID());
//				jasonCoursesUnits.accumulate(COURSES_UNITS_INSTITUTION_ADMIN_STUDIES_ID, courseUnit.getInstitutionAdministeringStudiesID());
				
//				jasonCoursesUnits.accumulate(COURSES_UNITS_GROUP_ID, courseUnit.isIsRequiredByTheProgramme());
				jasonCoursesUnits.put("Required", courseUnit.isIsRequiredByTheProgramme());
				
				
//				jasonCoursesUnits.accumulate(COURSES_UNITS_SCIENTIFIC_AREA, courseUnit.getScientificArea());
//				jasonCoursesUnits.accumulate(COURSES_UNITS_TITLE, courseUnit.getTitle());
				jasonCoursesUnits.put("Name", courseUnit.getTitle());
//				jasonCoursesUnits.accumulate(COURSES_UNITS_THEME, courseUnit.getTheme());
				jasonCoursesUnits.accumulate(COURSES_UNITS_TYPE, courseUnit.getType().getType().value());
				
//				if(courseUnit.getYearOfStudy() != null){
//					jasonCoursesUnits.accumulate(COURSES_UNITS_YEAR_OF_STUDY, courseUnit.getYearOfStudy().getYear());
//					//courseUnit.getYearOfStudy().getValue();
//				}
//				
//				if(courseUnit.getLevel() != null){
//					jasonCoursesUnits.accumulate(COURSES_UNITS_LEVEL, courseUnit.getLevel().getValue());
//					//courseUnit.getLevel().getEheaFramework().value();
//				}
				
//				jasonCoursesUnits.accumulate(COURSES_UNITS_ECTS_CREDITS, courseUnit.getECTSCredits());
				if(courseUnit.getECTSCredits() != null){
					jasonCoursesUnits.put(COURSES_UNITS_ECTS_CREDITS, courseUnit.getECTSCredits());	
				} else {
					jasonCoursesUnits.put(COURSES_UNITS_ECTS_CREDITS, "");
				}
				
				
//				jasonCoursesUnits.accumulate(COURSES_UNITS_LOCAL_CREDITS, courseUnit.getLocalCredits());
				
				

//				jasonCoursesUnits.accumulate(COURSES_UNITS_HOURS, courseUnit.getHours());
				jasonCoursesUnits.put(COURSES_UNITS_HOURS, courseUnit.getHours());
				
//				if(courseUnit.getLanguagesOfInstruction() != null){
//					for (CourseUnitLanguageOfInstructionType language : courseUnit.getLanguagesOfInstruction().getLanguage()) {
//						jasonCoursesUnits.accumulate(COURSES_UNITS_LANGUAGES_OF_INSTRUCTION, language.getValue());		
//					}
//				}
//				
//				if(courseUnit.getModeOfDelivery() != null){
//					jasonCoursesUnits.accumulate(COURSES_UNITS_MODE_OF_DELIVERY, courseUnit.getModeOfDelivery().getValue());
//				}
//				
//				if(courseUnit.getWorkPlacements() != null){
//					for (CourseUnitWorkPlacementType workPlacement : courseUnit.getWorkPlacements().getWorkPlacement()) {
//						jasonCoursesUnits.accumulate(COURSES_UNITS_WORKPLACEMENTS_COLLABORATING_INSTITUTION, workPlacement.getCollaboratingInstitution());
//						jasonCoursesUnits.accumulate(COURSES_UNITS_WORKPLACEMENTS_DATE_FROM, workPlacement.getDateFrom());
//						jasonCoursesUnits.accumulate(COURSES_UNITS_WORKPLACEMENTS_DATE_TO, workPlacement.getDateTo());
//						jasonCoursesUnits.accumulate(COURSES_UNITS_WORKPLACEMENTS_TRAINING_HOURS, workPlacement.getTrainingHours());
//					}
//				}
				
				if(courseUnit.getStudentPerformance() != null){
					if(courseUnit.getStudentPerformance().getECTSGrade() != null){ 
						jasonCoursesUnits.put(COURSES_UNITS_STUDENT_PERFOMANCE_ECTS_GRADE, courseUnit.getStudentPerformance().getECTSGrade().value());
					}
					if(courseUnit.getStudentPerformance().getLocalGrade() != null){
						jasonCoursesUnits.put(COURSES_UNITS_STUDENT_PERFOMANCE_LOCAL_GRADE, courseUnit.getStudentPerformance().getLocalGrade().getGrade());
//						jasonCoursesUnits.accumulate(COURSES_UNITS_STUDENT_PERFOMANCE_LOCAL_GRADE, courseUnit.getStudentPerformance().getLocalGrade().getSource().getValue());
					}
					
					jasonCoursesUnits.put(COURSES_UNITS_STUDENT_PERFOMANCE_LOCAL_GRADE_YEAR, courseUnit.getStudentPerformance().getLocalGrade().getAcademicYear());
						
				} else {
					jasonCoursesUnits.put(COURSES_UNITS_STUDENT_PERFOMANCE_ECTS_GRADE, "");
					jasonCoursesUnits.put(COURSES_UNITS_STUDENT_PERFOMANCE_LOCAL_GRADE, "");
					jasonCoursesUnits.put(COURSES_UNITS_STUDENT_PERFOMANCE_LOCAL_GRADE_YEAR, "");
				}
				jason.accumulate(PROGRAMME_DETAILS, jasonCoursesUnits);
				// Teachers
//				RichTextTagType nameOfLectures = courseUnit.getNameOfLecturers();	
//				if(nameOfLectures != null){
//					for (Serializable serial : nameOfLectures.getContent()) {
//						if(!serial.toString().contains("javax")){
//							jasonCoursesUnits.accumulate(COURSES_UNITS_NAME_OF_LECTURES, serial.toString().trim());
//						}
//					}	
//				}
				
				// Observations
//				RichTextTagType learningOutcomes = courseUnit.getLearningOutcomes();	
//				if(learningOutcomes != null){
//					for (Serializable serial : learningOutcomes.getContent()) {
//						if(!serial.toString().contains("javax")){
//							jasonCoursesUnits.accumulate(COURSES_UNITS_LEARNING_OUTCOMES, serial.toString().trim());
//						}
//					}	
//				}
				
//				RichTextTagType prerequisites = courseUnit.getPrerequisitesAndCorequisites();	
//				if(prerequisites != null){
//					for (Serializable serial : prerequisites.getContent()) {
//						if(!serial.toString().contains("javax")){
//							jasonCoursesUnits.accumulate(COURSES_UNITS_PREREQUISITES_AND_COREQUISITES, serial.toString().trim());
//						}
//					}	
//				}
//				
//				RichTextTagType recomendedProgramme = courseUnit.getRecomendedOptionalProgrammeComponents();	
//				if(recomendedProgramme != null){
//					for (Serializable serial : recomendedProgramme.getContent()) {
//						if(!serial.toString().contains("javax")){
//							jasonCoursesUnits.accumulate(COURSES_UNITS_REC_PROG_COMPONENTS, serial.toString().trim());
//						}
//					}	
//				}
//				
//				RichTextTagType courseContents = courseUnit.getCourseContents();	
//				if(courseContents != null){
//					for (Serializable serial : courseContents.getContent()) {
//						if(!serial.toString().contains("javax")){
//							jasonCoursesUnits.accumulate(COURSES_UNITS_COURSE_CONTENTS, serial.toString().trim());
//						}
//					}	
//				}
//				
//				RichTextTagType reqReading = courseUnit.getRecomendedOrRequiredReading();	
//				if(reqReading != null){
//					for (Serializable serial : reqReading.getContent()) {
//						if(!serial.toString().contains("javax")){
//							jasonCoursesUnits.accumulate(COURSES_UNITS_RECOMENDED_REQ_READING, serial.toString().trim());
//						}
//					}	
//				}
//				
//				RichTextTagType planed = courseUnit.getPlanedLearningActivitiesAndTeachingMethod();	
//				if(planed != null){
//					for (Serializable serial : planed.getContent()) {
//						if(!serial.toString().contains("javax")){
//							jasonCoursesUnits.accumulate(COURSES_UNITS_PLANED_LEARNING, serial.toString().trim());
//						}
//					}	
//				}
//				
//				RichTextTagType assesment = courseUnit.getAssesmentMethodsAndCriteria();	
//				if(assesment != null){
//					for (Serializable serial : assesment.getContent()) {
//						if(!serial.toString().contains("javax")){
//							jasonCoursesUnits.accumulate(COURSES_UNITS_ASSESMENT, serial.toString().trim());
//						}
//					}	
//				}
//				
//				RichTextTagType obs = courseUnit.getObservations();	
//				if(obs != null){
//					for (Serializable serial : obs.getContent()) {
//						if(!serial.toString().contains("javax")){
//							jasonCoursesUnits.accumulate(COURSES_UNITS_OBSERVATIONS, serial.toString().trim());
//						}
//					}	
//				}	
				
//				jasonCoursesUnitsArray.put(jasonCoursesUnits);
//				jason.accumulate(PROGRAMME_DETAILS, jasonCoursesUnits);
			}
			
//			jason.put(COURSES_UNITS, jasonCoursesUnitsArray);
			
			
			
//			// Mobility Programme Type
//			CoursesAttendedInOtherInstitutionInMobilityProgramsType coursesAttended = programmeDetails.getCoursesAttendedInOtherInstitutionInMobilityPrograms();
//			GradingSchemeAndGradeDistributionGuidanceType gradingScheme = root.getGradingSchemeAndGradeDistributionGuidance();
//			
//			
//			JSONObject jasonMobility;
//			JSONArray jasonMobilitysArray = new JSONArray();
//			if(coursesAttended != null){
//				for (MobilityProgrammeType programme : coursesAttended.getMobilityProgramme()) {
//					jasonMobility = new JSONObject();
//					jasonMobility.accumulate(MOBILITY_PROGRAMME_TYPE, programme.getType());
//					jasonMobility.accumulate(MOBILITY_PROGRAMME_COUNTRY, programme.getCountry().getValue());
//					jasonMobility.accumulate(MOBILITY_PROGRAMME_ACADEMIC_YEAR, programme.getAcademicYear());
//					jasonMobility.accumulate(MOBILITY_PROGRAMME_REC_INSTI_NAME, programme.getReceivingInstitutionName());
//					
//					if(programme.getFieldOfStudy() != null){
//						jasonMobility.accumulate(MOBILITY_PROGRAMME_FIELDS_OF_STUDY, programme.getFieldOfStudy());
//					}
//					if(programme.getDateFrom() != null){
//						jasonMobility.accumulate(MOBILITY_PROGRAMME_DATE_FROM, programme.getDateFrom());
//					}
//					if(programme.getDateTo() != null){
//						jasonMobility.accumulate(MOBILITY_PROGRAMME_DATE_TO, programme.getDateTo());
//					}
//				
//					MobilityProgrammeCoursesUnitsType courseUnits = programme.getCoursesUnits();
//					
//					JSONObject jasonMobilityCU;
//					for (MobilityProgrammeCourseUnitType mobilityUnit : courseUnits.getCourseUnit()) {
//						jasonMobilityCU = new JSONObject();
//						
//						jasonMobilityCU.accumulate(MOBILITY_PROGRAMME_CU_CODE_IS_IN_LEARNING_AGREE, mobilityUnit.isIsInTheLearningAgreement());
//						
//						if(mobilityUnit.getCode() != null){
//							jasonMobilityCU.accumulate(MOBILITY_PROGRAMME_CU_CODE, mobilityUnit.getCode());
//						}
//						if(mobilityUnit.getECTSCredits() != null){
//							jasonMobilityCU.accumulate(MOBILITY_PROGRAMME_CU_ECTS_CREDITS, mobilityUnit.getECTSCredits());
//						}
//						if(mobilityUnit.getTitle().getValue() != null){
//							jasonMobilityCU.accumulate(MOBILITY_PROGRAMME_CU_TITLE, mobilityUnit.getTitle().getValue());
//						}
//			
//						RichTextTagType mob = mobilityUnit.getAdditionalInformation();	
//						if(mob != null){
//							for (Serializable serial : mob.getContent()) {
//								if(!serial.toString().contains("javax")){
//									jasonMobilityCU.accumulate(MOBILITY_PROGRAMME_CU_ADD_INFO, serial.toString().trim());
//								}
//							}	
//						}
//		
//						jasonMobility.accumulate(MOBILITY_PROGRAMME_COURSES_UNITS, jasonMobilityCU);
//					}
//					
//					jasonMobilitysArray.put(jasonMobility);
//				}
//			}			
//			jason.put(MOBILITY_PROGRAMME, jasonMobilitysArray);
//			
//			
			// OverallClassificationOfTheQualification
			RichTextTagType overral = root.getOverallClassificationOfTheQualification();
			if(overral != null){
				for (Serializable serial : overral.getContent()) {
					if(!serial.toString().contains("javax")){
						jason.accumulate(CLASSIFICATION, serial.toString().trim());
					}
				}	
			} else {
				jason.accumulate(CLASSIFICATION, "");
			}
			
			// GradingSchemeAndGradeDistributionGuidanceType
			GradingSchemeAndGradeDistributionGuidanceType gradingScheme = root.getGradingSchemeAndGradeDistributionGuidance();
			
			RichTextTagType gradingDist = gradingScheme.getGradeDistributionGuidance();
			if(gradingDist != null){
				for (Serializable serial : gradingDist.getContent()) {
					if(!serial.toString().contains("javax")){
//						jason.accumulate(GRADING_SCHEME_GUIDANCE, serial.toString().trim());
						jason.accumulate(GRADING, serial.toString().trim());
					}
				}	
			} else {
				jason.accumulate(GRADING, "");
			}
			
			RichTextTagType grading = gradingScheme.getGradingScheme();
			if(grading != null){
				for (Serializable serial : grading.getContent()) {
					if(!serial.toString().contains("javax")){
//						jason.accumulate(GRADING_SCHEME, serial.toString().trim());
						jason.accumulate(GRADING, serial.toString().trim());
					}
				}	
			} else {
				jason.accumulate(GRADING, "");
			}
			
	
//			jasonTAG.put(TAG, jason);
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
	
		
		return jason;
	}


}
