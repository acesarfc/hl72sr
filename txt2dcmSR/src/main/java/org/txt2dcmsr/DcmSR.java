package org.txt2dcmsr;


// SR Configuration Class

public class DcmSR {
	
	dataElement studyDate; // Study Date 00080020:	
	dataElement contentDate;	// Content Date 00080023:19910919
	dataElement studyTime; //# Study Time    00080030:
	dataElement contentTime; //# Content Time     00080033:1215
	dataElement refPhysicianName;// # Referring Physician's Name   00080090:
	dataElement patientName; //# Patient's Name 00100010:Homo^Tyrolensis
	dataElement patientID; // # Patient ID	00100020:OETZI
	dataElement patientBrithDate; // # Patient's Birth Date 00100030:
	dataElement patientSex; // # Patient's Sex 	00100040:M
	dataElement StudyUID;// # Study Instance UID 0020000D:1.113654.1.2001.6111
    dataElement documentTitle; //# Document Title   # (s. DICOM Part 16, Context ID 7000: Diagnostic Imaging Report Document Titles)
    
    dataSequence conceptNameCodeSeq; // 0040A043
    dataElement conceptName; //# Concept Name Code Value    0040A043/00080100:11528-7
    dataElement codValue; // Code Value    0040A043/00080100:11528-7
    dataElement conceptNameCodingSchemeDesignator;//# Concept Name Coding Scheme Designator     0040A043/00080102:LN
    dataElement conceptNameCodeMeaning; //# Concept Name Code Meaning   0040A043/00080104:Radiology Report
    dataElement verifyingOrganization; // # Verifying Organization   0040A073/0040A027:Universit�t Innsbruck
    dataElement verificationDateTime; //# Verification DateTime  0040A073/0040A030:19931022184000
    dataElement verifyingObserverName; //# Verifying Observer Name  0040A073/0040A075:Spindler^Konrad
    dataElement verificationFlag; //# Verification Flag  0040A493:VERIFIED
    dataSequence languageCodeValue; //# Language Code Value    0040A730[1]/0040A168/00080100:de
    dataElement languageCodeMeaning; //# Language Code Meaning   0040A730[1]/0040A168/00080104:Deutsch
    dataElement personObserverName; // # Person Observer Name   0040A730[2]/0040A123:Simon^Helmut
	public dataElement getStudyDate() {
		return studyDate;
	}
	public void setStudyDate(dataElement studyDate) {
		this.studyDate = studyDate;
	}
	public dataElement getContentDate() {
		return contentDate;
	}
	public void setContentDate(dataElement contentDate) {
		this.contentDate = contentDate;
	}
	public dataElement getStudyTime() {
		return studyTime;
	}
	public void setStudyTime(dataElement studyTime) {
		this.studyTime = studyTime;
	}
	public dataElement getContentTime() {
		return contentTime;
	}
	public void setContentTime(dataElement contentTime) {
		this.contentTime = contentTime;
	}
	public dataElement getRefPhysicianName() {
		return refPhysicianName;
	}
	public void setRefPhysicianName(dataElement refPhysicianName) {
		this.refPhysicianName = refPhysicianName;
	}
	public dataElement getPatientName() {
		return patientName;
	}
	public void setPatientName(dataElement patientName) {
		this.patientName = patientName;
	}
	public dataElement getPatientID() {
		return patientID;
	}
	public void setPatientID(dataElement patientID) {
		this.patientID = patientID;
	}
	public dataElement getPatientBrithDate() {
		return patientBrithDate;
	}
	public void setPatientBrithDate(dataElement patientBrithDate) {
		this.patientBrithDate = patientBrithDate;
	}
	public dataElement getPatientSex() {
		return patientSex;
	}
	public void setPatientSex(dataElement patientSex) {
		this.patientSex = patientSex;
	}
	public dataElement getStudyUID() {
		return StudyUID;
	}
	public void setStudyUID(dataElement studyUID) {
		StudyUID = studyUID;
	}
	public dataElement getDocumentTitle() {
		return documentTitle;
	}
	public void setDocumentTitle(dataElement documentTitle) {
		this.documentTitle = documentTitle;
	}
	public dataSequence getConceptNameCodeSeq() {
		return conceptNameCodeSeq;
	}
	public void setConceptNameCodeSeq(dataSequence conceptNameCodeSeq) {
		this.conceptNameCodeSeq = conceptNameCodeSeq;
	}
	public dataElement getConceptName() {
		return conceptName;
	}
	public void setConceptName(dataElement conceptName) {
		this.conceptName = conceptName;
	}
	public dataElement getCodValue() {
		return codValue;
	}
	public void setCodValue(dataElement codValue) {
		this.codValue = codValue;
	}
	public dataElement getConceptNameCodingSchemeDesignator() {
		return conceptNameCodingSchemeDesignator;
	}
	public void setConceptNameCodingSchemeDesignator(dataElement conceptNameCodingSchemeDesignator) {
		this.conceptNameCodingSchemeDesignator = conceptNameCodingSchemeDesignator;
	}
	public dataElement getConceptNameCodeMeaning() {
		return conceptNameCodeMeaning;
	}
	public void setConceptNameCodeMeaning(dataElement conceptNameCodeMeaning) {
		this.conceptNameCodeMeaning = conceptNameCodeMeaning;
	}
	public dataElement getVerifyingOrganization() {
		return verifyingOrganization;
	}
	public void setVerifyingOrganization(dataElement verifyingOrganization) {
		this.verifyingOrganization = verifyingOrganization;
	}
	public dataElement getVerificationDateTime() {
		return verificationDateTime;
	}
	public void setVerificationDateTime(dataElement verificationDateTime) {
		this.verificationDateTime = verificationDateTime;
	}
	public dataElement getVerifyingObserverName() {
		return verifyingObserverName;
	}
	public void setVerifyingObserverName(dataElement verifyingObserverName) {
		this.verifyingObserverName = verifyingObserverName;
	}
	public dataElement getVerificationFlag() {
		return verificationFlag;
	}
	public void setVerificationFlag(dataElement verificationFlag) {
		this.verificationFlag = verificationFlag;
	}
	public dataSequence getLanguageCodeValue() {
		return languageCodeValue;
	}
	public void setLanguageCodeValue(dataSequence languageCodeValue) {
		this.languageCodeValue = languageCodeValue;
	}
	public dataElement getLanguageCodeMeaning() {
		return languageCodeMeaning;
	}
	public void setLanguageCodeMeaning(dataElement languageCodeMeaning) {
		this.languageCodeMeaning = languageCodeMeaning;
	}
	public dataElement getPersonObserverName() {
		return personObserverName;
	}
	public void setPersonObserverName(dataElement personObserverName) {
		this.personObserverName = personObserverName;
	}
    
    


}
