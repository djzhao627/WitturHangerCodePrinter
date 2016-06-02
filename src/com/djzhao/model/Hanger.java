package com.djzhao.model;

public class Hanger {

	/** id */
	private int ID;
	/** 箱号 */
	private String boxId;
	/** 物料号 */
	private String item;
	/** 描述 */
	private String dsce;
	/** 订单号 */
	private String orNo;
	/** 订单行 */
	private String poNo;
	/** 条码 */
	private String code;
	/** 打印日期 */
	private String pynDate;
	/** 状态 */
	private int state;
	/** 工位 */
	private String station;
	/** 9位唯一码 */
	private String nineCode;
	/** 流水号 */
	private String sno;

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
	 * @return the boxId
	 */
	public String getBoxId() {
		return boxId;
	}

	/**
	 * @param boxId
	 *            the boxId to set
	 */
	public void setBoxId(String boxId) {
		this.boxId = boxId;
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
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the pynDate
	 */
	public String getPynDate() {
		return pynDate;
	}

	/**
	 * @param pynDate
	 *            the pynDate to set
	 */
	public void setPynDate(String pynDate) {
		this.pynDate = pynDate;
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
	 * @return the nineCode
	 */
	public String getNineCode() {
		return nineCode;
	}

	/**
	 * @param nineCode
	 *            the nineCode to set
	 */
	public void setNineCode(String nineCode) {
		this.nineCode = nineCode;
	}

	/**
	 * @return the sno
	 */
	public String getSno() {
		return sno;
	}

	/**
	 * @param sno
	 *            the sno to set
	 */
	public void setSno(String sno) {
		this.sno = sno;
	}

}
