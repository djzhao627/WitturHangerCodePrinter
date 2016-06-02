package com.djzhao.data;

public class V7Model {
	/** 物料号 */
	public String item;
	/** 物料描述 */
	public String dsce;
	/** 订单号 */
	public String orno;
	/** 订单行 */
	public String pono;
	/** 上层物料号 */
	public String titem;
	/** 上曾物料描述 */
	public String tdsce;
	/** 客户代码 */
	public String customerID;
	/** 是否发往欧盟 */
	public String nld;

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getDsce() {
		return dsce;
	}

	public void setDsce(String dsce) {
		this.dsce = dsce;
	}

	public String getOrno() {
		return orno;
	}

	public void setOrno(String orno) {
		this.orno = orno;
	}

	public String getPono() {
		return pono;
	}

	public void setPono(String pono) {
		this.pono = pono;
	}

	public String getTitem() {
		return titem;
	}

	public void setTitem(String titem) {
		this.titem = titem;
	}

	public String getTdsce() {
		return tdsce;
	}

	public void setTdsce(String tdsce) {
		this.tdsce = tdsce;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getNld() {
		return nld;
	}

	public void setNld(String nld) {
		this.nld = nld;
	}

}
