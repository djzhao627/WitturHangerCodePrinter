package com.djzhao.model;

public class WitturCEAndSchindlerLabel {

	/** 自增主机 */
	private int ID;
	/** 证书编号 */
	private String certificateNo;
	/** 系列号 */
	private String series;
	/** 9位码（唯一码） */
	private String serivalNumber;
	/** 订单号 */
	private String orNo;
	/** 订单行 */
	private String poNo;
	/** 下层物料号 */
	private String item;
	/** 二维码数据 */
	private String witturCECode;
	/** 打印日期 */
	private String pDate;
	/** 状态标志 */
	private int state;
	/** 工人工位 */
	private String station;
	/** 箱号 */
	private String boxID;
	/** 下层物料描述 */
	private String dsce;
	/** 开门方式 */
	private String type;
	/** 迅达标签物料描述 */
	private String materialDes;
	/** 迅达标签ID号 */
	private String IDNumber;
	/** 迅达二维码内容 */
	private String schindlerCode;
	/** 是否出口 */
	private String NLD;

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
	 * @return the certificateNo
	 */
	public String getCertificateNo() {
		return certificateNo;
	}

	/**
	 * @param certificateNo
	 *            the certificateNo to set
	 */
	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	/**
	 * @return the series
	 */
	public String getSeries() {
		return series;
	}

	/**
	 * @param series
	 *            the series to set
	 */
	public void setSeries(String series) {
		this.series = series;
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
	 * @return the code
	 */
	public String getCode() {
		return witturCECode;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.witturCECode = code;
	}

	/**
	 * @return the pDate
	 */
	public String getpDate() {
		return pDate;
	}

	/**
	 * @param pDate
	 *            the pDate to set
	 */
	public void setpDate(String pDate) {
		this.pDate = pDate;
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
	 * @return the boxId
	 */
	public String getBoxId() {
		return boxID;
	}

	/**
	 * @param boxId
	 *            the boxId to set
	 */
	public void setBoxId(String boxId) {
		this.boxID = boxId;
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the witturCECode
	 */
	public String getWitturCECode() {
		return witturCECode;
	}

	/**
	 * @param witturCECode
	 *            the witturCECode to set
	 */
	public void setWitturCECode(String witturCECode) {
		this.witturCECode = witturCECode;
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
	 * @return the materialDes
	 */
	public String getMaterialDes() {
		return materialDes;
	}

	/**
	 * @param materialDes
	 *            the materialDes to set
	 */
	public void setMaterialDes(String materialDes) {
		this.materialDes = materialDes;
	}

	/**
	 * @return the iDNumber
	 */
	public String getIDNumber() {
		return IDNumber;
	}

	/**
	 * @param iDNumber
	 *            the iDNumber to set
	 */
	public void setIDNumber(String iDNumber) {
		IDNumber = iDNumber;
	}

	/**
	 * @return the schindlerCode
	 */
	public String getSchindlerCode() {
		return schindlerCode;
	}

	/**
	 * @param schindlerCode
	 *            the schindlerCode to set
	 */
	public void setSchindlerCode(String schindlerCode) {
		this.schindlerCode = schindlerCode;
	}

	/**
	 * @return the nLD
	 */
	public String getNLD() {
		return NLD;
	}

	/**
	 * @param nLD
	 *            the nLD to set
	 */
	public void setNLD(String nLD) {
		NLD = nLD;
	}

}
