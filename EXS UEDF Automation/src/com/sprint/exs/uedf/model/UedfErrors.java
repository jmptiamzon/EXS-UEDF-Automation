package com.sprint.exs.uedf.model;

public class UedfErrors {
	private String fileName;
	private int cntSku;
	private String edfSerial;
	private long uniqueSeqNum;
	private int deviceCount;
	private int countErrDet;
	private String phoneType;
	private String status;
	private String creationDate;
	private String updateDate;
	
	public UedfErrors() {
		fileName = "";
		cntSku = 0;
		edfSerial = "";
		uniqueSeqNum = 0;
		deviceCount = 0;
		countErrDet = 0;
		phoneType = "";
		status = "";
		creationDate = "";
		updateDate = "";
	
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getCntSku() {
		return cntSku;
	}

	public void setCntSku(int cntSku) {
		this.cntSku = cntSku;
	}

	public String getEdfSerial() {
		return edfSerial;
	}

	public void setEdfSerial(String edfSerial) {
		this.edfSerial = edfSerial;
	}

	public Long getUniqueSeqNum() {
		return uniqueSeqNum;
	}

	public void setUniqueSeqNum(long uniqueSeqNum) {
		this.uniqueSeqNum = uniqueSeqNum;
	}

	public int getDeviceCount() {
		return deviceCount;
	}

	public void setDeviceCount(int deviceCount) {
		this.deviceCount = deviceCount;
	}

	public int getCountErrDet() {
		return countErrDet;
	}

	public void setCountErrDet(int countErrDet) {
		this.countErrDet = countErrDet;
	}

	public String getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	
	
}
