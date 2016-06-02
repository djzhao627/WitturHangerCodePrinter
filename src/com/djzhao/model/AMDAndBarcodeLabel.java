package com.djzhao.model;

public class AMDAndBarcodeLabel {

	/** 自增主键 */
	private int ID;
	/** 产品代码 */
	private String productCode;
	/** 产品类型 */
	private String componentType;
	/** 证书编号 */
	private String certificatNum;
	/** AMD条码内容 */
	private String AMDCode;
	/** 订单号 */
	private String orNo;
	/** 订单行 */
	private String poNo;
	/** 箱号 */
	private String boxID;
	/** 下层物料号 */
	private String item;
	/** 下层物料描述 */
	private String dsce;
	/** 9位码 */
	private String serivalNumber;
	/** 工位 */
	private String station;
	/** 打印时间 */
	private String pdate;
	/** 状态标志 */
	private int state;
	/** 基本编号 */
	private String baseID;
	/** 后缀 */
	private String parts;
	/** 版本更新码 */
	private String updateCode;
	/** 供应商代码 */
	private String supplierCode;
	/** 一维码内容 */
	private String barcode;

	/**
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}

	/**
	 * @param iD
	 *            the iD to set
	 */
	public void setID(int iD) {
		ID = iD;
	}

	/**
	 * @return the productCode
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * @param productCode
	 *            the productCode to set
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * @return the componentType
	 */
	public String getComponentType() {
		return componentType;
	}

	/**
	 * @param componentType
	 *            the componentType to set
	 */
	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}

	/**
	 * @return the certificatNum
	 */
	public String getCertificatNum() {
		return certificatNum;
	}

	/**
	 * @param certificatNum
	 *            the certificatNum to set
	 */
	public void setCertificatNum(String certificatNum) {
		this.certificatNum = certificatNum;
	}

	/**
	 * @return the aMDCode
	 */
	public String getAMDCode() {
		return AMDCode;
	}

	/**
	 * @param aMDCode
	 *            the aMDCode to set
	 */
	public void setAMDCode(String aMDCode) {
		AMDCode = aMDCode;
	}

	/**
	 * @return the orNo
	 */
	public String getOrNo() {
		return orNo;
	}

	/**
	 * @param orNo
	 *            the orNo to set
	 */
	public void setOrNo(String orNo) {
		this.orNo = orNo;
	}

	/**
	 * @return the poNo
	 */
	public String getPoNo() {
		return poNo;
	}

	/**
	 * @param poNo
	 *            the poNo to set
	 */
	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	/**
	 * @return the boxID
	 */
	public String getBoxID() {
		return boxID;
	}

	/**
	 * @param boxID
	 *            the boxID to set
	 */
	public void setBoxID(String boxID) {
		this.boxID = boxID;
	}

	/**
	 * @return the item
	 */
	public String getItem() {
		return item;
	}

	/**
	 * @param item
	 *            the item to set
	 */
	public void setItem(String item) {
		this.item = item;
	}

	/**
	 * @return the dsce
	 */
	public String getDsce() {
		return dsce;
	}

	/**
	 * @param dsce
	 *            the dsce to set
	 */
	public void setDsce(String dsce) {
		this.dsce = dsce;
	}

	/**
	 * @return the serivalNumber
	 */
	public String getSerivalNumber() {
		return serivalNumber;
	}

	/**
	 * @param serivalNumber
	 *            the serivalNumber to set
	 */
	public void setSerivalNumber(String serivalNumber) {
		this.serivalNumber = serivalNumber;
	}

	/**
	 * @return the station
	 */
	public String getStation() {
		return station;
	}

	/**
	 * @param station
	 *            the station to set
	 */
	public void setStation(String station) {
		this.station = station;
	}

	/**
	 * @return the pdate
	 */
	public String getPdate() {
		return pdate;
	}

	/**
	 * @param pdate
	 *            the pdate to set
	 */
	public void setPdate(String pdate) {
		this.pdate = pdate;
	}

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * @return the baseID
	 */
	public String getBaseID() {
		return baseID;
	}

	/**
	 * @param baseID
	 *            the baseID to set
	 */
	public void setBaseID(String baseID) {
		this.baseID = baseID;
	}

	/**
	 * @return the parts
	 */
	public String getParts() {
		return parts;
	}

	/**
	 * @param parts
	 *            the parts to set
	 */
	public void setParts(String parts) {
		this.parts = parts;
	}

	/**
	 * @return the updateCode
	 */
	public String getUpdateCode() {
		return updateCode;
	}

	/**
	 * @param updateCode
	 *            the updateCode to set
	 */
	public void setUpdateCode(String updateCode) {
		this.updateCode = updateCode;
	}

	/**
	 * @return the supplierCode
	 */
	public String getSupplierCode() {
		return supplierCode;
	}

	/**
	 * @param supplierCode
	 *            the supplierCode to set
	 */
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	/**
	 * @return the barcode
	 */
	public String getBarcode() {
		return barcode;
	}

	/**
	 * @param barcode
	 *            the barcode to set
	 */
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

}
