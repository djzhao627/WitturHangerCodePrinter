package com.djzhao.model;

/**
 * 迅达标签模型类。
 * 
 * @author djzhao
 *
 */
public class Schindler {
	/** 主键ID */
	private int id;
	/** 产品名称 */
	private String productName;
	/**  */
	private String releaseNo;
	/**  */
	private String revisionNo;
	/**  */
	private String identificationNo;
	/**  */
	private String serialNo;
	/**  */
	private String batchNo;
	/**  */
	private String manufacture;
	/**  */
	private String importer;
	/** 打印时间 */
	private String time;
	/** 状态，默认1 */
	private int status = 1;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName
	 *            the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the releaseNo
	 */
	public String getReleaseNo() {
		return releaseNo;
	}

	/**
	 * @param releaseNo
	 *            the releaseNo to set
	 */
	public void setReleaseNo(String releaseNo) {
		this.releaseNo = releaseNo;
	}

	/**
	 * @return the revisionNo
	 */
	public String getRevisionNo() {
		return revisionNo;
	}

	/**
	 * @param revisionNo
	 *            the revisionNo to set
	 */
	public void setRevisionNo(String revisionNo) {
		this.revisionNo = revisionNo;
	}

	/**
	 * @return the identificationNo
	 */
	public String getIdentificationNo() {
		return identificationNo;
	}

	/**
	 * @param identificationNo
	 *            the identificationNo to set
	 */
	public void setIdentificationNo(String identificationNo) {
		this.identificationNo = identificationNo;
	}

	/**
	 * @return the serialNo
	 */
	public String getSerialNo() {
		return serialNo;
	}

	/**
	 * @param serialNo
	 *            the serialNo to set
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * @return the batchNo
	 */
	public String getBatchNo() {
		return batchNo;
	}

	/**
	 * @param batchNo
	 *            the batchNo to set
	 */
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	/**
	 * @return the manufacture
	 */
	public String getManufacture() {
		return manufacture;
	}

	/**
	 * @param manufacture
	 *            the manufacture to set
	 */
	public void setManufacture(String manufacture) {
		this.manufacture = manufacture;
	}

	/**
	 * @return the importer
	 */
	public String getImporter() {
		return importer;
	}

	/**
	 * @param importer
	 *            the importer to set
	 */
	public void setImporter(String importer) {
		this.importer = importer;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

}
