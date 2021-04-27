package com.sprint.exs.uedf.model;

public class UedfFile {
	private String mfgLocationID;
	private String mfgName;
	private String modelNumber;
	private String modelName;
	private String equipmentType;
	private String lockCode1;
	private String lockCode2;
	private String csnOrEid;
	private String softwareVersionNo;
	private String meidHex;
	private String meidDec;
	private String edfSerial;
	private String imeiDec;
	private String iccid;
	private String errorDescription;
	private String comment;
	private String fileName;
	
	public UedfFile() {
		mfgLocationID = "";
		mfgName = "";
		modelNumber = "";
		modelName = "";
		equipmentType = "";
		lockCode1 = "";
		lockCode2 = "";
		csnOrEid = "";
		softwareVersionNo = "";
		meidHex = "";
		meidDec = "";
		edfSerial = "";
		imeiDec = "";
		iccid = "";
		fileName = "";
		errorDescription = "";
		comment = "";
		
	}

	public String getMfgLocationID() {
		return mfgLocationID;
	}

	public void setMfgLocationID(String mfgLocationID) {
		this.mfgLocationID = mfgLocationID;
	}

	public String getMfgName() {
		return mfgName;
	}

	public void setMfgName(String mfgName) {
		this.mfgName = mfgName;
	}

	public String getModelNumber() {
		return modelNumber;
	}

	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}

	public String getLockCode1() {
		return lockCode1;
	}

	public void setLockCode1(String lockCode1) {
		this.lockCode1 = lockCode1;
	}

	public String getLockCode2() {
		return lockCode2;
	}

	public void setLockCode2(String lockCode2) {
		this.lockCode2 = lockCode2;
	}
	
	public String getCsnOrEid() {
		return csnOrEid;
	}

	public void setCsnOrEid(String csnOrEid) {
		this.csnOrEid = csnOrEid;
	}

	public String getSoftwareVersionNo() {
		return softwareVersionNo;
	}

	public void setSoftwareVersionNo(String softwareVersionNo) {
		this.softwareVersionNo = softwareVersionNo;
	}

	public String getMeidHex() {
		return meidHex;
	}

	public void setMeidHex(String meidHex) {
		this.meidHex = meidHex;
	}

	public String getMeidDec() {
		return meidDec;
	}

	public void setMeidDec(String meidDec) {
		this.meidDec = meidDec;
	}

	public String getEdfSerial() {
		return edfSerial;
	}

	public void setEdfSerial(String edfSerial) {
		this.edfSerial = edfSerial;
	}
	
	public String getImeiDec() {
		return imeiDec;
	}

	public void setImeiDec(String imeiDec) {
		this.imeiDec = imeiDec;
	}

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	
	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}