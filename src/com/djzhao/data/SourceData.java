package com.djzhao.data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SourceData {

	/** ���������ɹ���־ */
	static boolean flag = false;
	static int countLoop = 0;

	public static void main(String[] args) {
		String[] s1 = new String[4];
		final String item = "2L2M040P59P090";
		final String dsce = "A-Arime�Ҽ�PL800";
		final String orno = "584076";
		final String pono = "10";
		s1[2] = orno;
		s1[3] = pono;
		SourceDataFiled.s1[0] = item;
		SourceDataFiled.s1[1] = dsce;
		SourceDataFiled.s1[2] = orno;
		SourceDataFiled.s1[3] = pono;
		XxlsAbstract x = new XxlsAbstract() {
			@Override
			public void optRows(int sheetIndex, int curRow, List<String> rowList) throws SQLException {
				// String itemfromv7 = rowList.get(12).trim();
				String ornofromv7 = rowList.get(5).trim();
				String ponofromv7 = rowList.get(6).trim();
				// String dscefromv7 = rowList.get(13).trim();
				if (orno.equals(ornofromv7) && pono.equals(ponofromv7)) {
					SourceDataFiled.titem = rowList.get(3).trim();
					SourceDataFiled.tdsce = rowList.get(4).trim();
					SourceDataFiled.customerID = rowList.get(0).trim();
					SourceDataFiled.nld = rowList.get(68).trim();
					flag = true;
					// return;
				}
				countLoop++;
			}
		};
		try {
			x.processOneSheet("C:\\witturcode\\�����������ƻ�_V7_1.xlsx", 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (flag == false) {
			return;
		}
		ArrayList<String> sourceList = new ArrayList<>();
		FreeProduct fre = new FreeProduct();
		if (SourceDataFiled.customerID.equals("CN0125")) {// ͨ����Ʒ
			KoneProduct k = new KoneProduct();
			if (SourceDataFiled.tdsce.contains("AMD") && !SourceDataFiled.tdsce.contains("Augusta")) {// AMD��Ʒ
				if (SourceDataFiled.nld.equals("����ŷ��")) {
					String onecode[] = k.getonecode(SourceDataFiled.tdsce, "AMD");
					for (int i = 0; i < onecode.length; i++) {
						sourceList.add(onecode[i]);
					}
					String[] s = k.getAMDData(s1);
					for (int i = 0; i < s.length; i++) {
						sourceList.add(s[i]);
						System.out.println(s[i]);
					}
					sourceList.add("ͨ������");
				} else {
					String onecode[] = k.getonecode(SourceDataFiled.tdsce, "AMD");
					for (int i = 0; i < onecode.length; i++) {
						sourceList.add(onecode[i]);
					}
					sourceList.add("ͨ��������");
					for (String s : sourceList) {
						System.out.println(s);
					}
				}

			} else {// Augusta��Ʒ
				String onecode[] = k.getonecode(SourceDataFiled.tdsce, "Augusta");
				for (int i = 0; i < onecode.length; i++) {
					sourceList.add(onecode[i]);
				}
				String[] s = fre.getFreeData(s1);
				if (s != null) {
					for (int i = 0; i < s.length; i++) {
						sourceList.add(s[i]);
						System.out.println(s[i]);
					}
					sourceList.add("Augusta");
					// System.out.println(sourceList.get(sourceList.size()).toString());
				}
			}

			// kone��Ʒ
		} else if (SourceDataFiled.customerID.equals("CN2611")) {// Ѹ��Ĳ�Ʒ
			SchindlerProduct sch = new SchindlerProduct();
			String[] s = sch.getSchindlerData(s1);
			if (s != null) {
				for (int i = 0; i < s.length; i++) {
					sourceList.add(s[i]);
					System.out.println(s[i]);
				}
				if (SourceDataFiled.nld.equals("����ŷ��")) {
					sourceList.add("Ѹ�����");
				} else {
					sourceList.add("Ѹ�ﲻ����");
				}
				System.out.println(sourceList.get(sourceList.size() - 1));
			}
		} else {// �����г�
			String[] s = fre.getFreeData(s1);
			if (s != null) {
				for (int i = 0; i < s.length; i++) {
					sourceList.add(s[i]);
					System.out.println(s[i]);
				}
				sourceList.add("�����г�");
			}
		}

		// System.out.println(getData(new String[]{"2L2M035M39KN080","A-A
		// N1�ؿ����PL800 300���Ű�","1248865","10"}));
	}

	public static ArrayList<String> getData(String[] s1, ArrayList<V7Model> v7List) {
		final String item = s1[0];
		final String dsce = s1[1];
		final String orno = s1[2];
		final String pono = s1[3];
		SourceDataFiled.s1[0] = item;
		SourceDataFiled.s1[1] = dsce;
		SourceDataFiled.s1[2] = orno;
		SourceDataFiled.s1[3] = pono;
		boolean flag = false;
		for (int i = 0; i < v7List.size(); i++) {
			// String itemfromv7 = v7List.get(i).getItem();
			String ornofromv7 = v7List.get(i).getOrno();
			String ponofromv7 = v7List.get(i).getPono();
			// String dscefromv7 = v7List.get(i).getDsce();
			if (/* item.equals(itemfromv7)&& */orno.equals(ornofromv7)
					&& pono.equals(ponofromv7)/* &&dsce.equals(dscefromv7) */) {
				SourceDataFiled.titem = v7List.get(i).getTitem();
				SourceDataFiled.tdsce = v7List.get(i).getTdsce();
				SourceDataFiled.customerID = v7List.get(i).getCustomerID();
				SourceDataFiled.nld = v7List.get(i).getNld();
				flag = true;
				break;
			}
		}
		if (flag == false) {
			return null;
		}
		ArrayList<String> sourceList = new ArrayList<>();
		FreeProduct fre = new FreeProduct();
		if (SourceDataFiled.customerID.equals("CN0125")) {// ͨ����Ʒ
			KoneProduct k = new KoneProduct();
			if (SourceDataFiled.tdsce.contains("AMD") && !SourceDataFiled.tdsce.contains("Augusta")) {// AMD��Ʒ
				if (SourceDataFiled.nld.equals("����ŷ��")) {
					String[] onecode = k.getonecode(SourceDataFiled.tdsce, "AMD");
					String[] s = k.getAMDData(s1);
					if (onecode != null && s != null) {
						for (int i = 0; i < onecode.length; i++) {
							sourceList.add(onecode[i]);
						}
						for (int i = 0; i < s.length; i++) {
							sourceList.add(s[i]);
						}
						sourceList.add("3");// ͨ������
					}
				} else {
					String onecode[] = k.getonecode(SourceDataFiled.tdsce, "AMD");
					if (onecode != null) {
						for (int i = 0; i < onecode.length; i++) {
							sourceList.add(onecode[i]);
						}
						sourceList.add("5");// ͨ��������
					}
				}

			} else {// Augusta��Ʒ
				String onecode[] = k.getonecode(SourceDataFiled.tdsce, "Augusta");
				String[] s = fre.getFreeData(s1);
				if (onecode != null && s != null) {
					for (int i = 0; i < onecode.length; i++) {
						sourceList.add(onecode[i]);
					}
					for (int i = 0; i < s.length; i++) {
						sourceList.add(s[i]);
					}
					sourceList.add("4");// Augusta
				}
			}

			// kone��Ʒ
		} else if (SourceDataFiled.customerID.equals("CN2611")) {// Ѹ��Ĳ�Ʒ
			SchindlerProduct sch = new SchindlerProduct();
			String[] s = sch.getSchindlerData(s1);
			if (s != null) {
				for (int i = 0; i < s.length; i++) {
					sourceList.add(s[i]);
				}
				if (SourceDataFiled.nld.equals("����ŷ��")) {
					sourceList.add("2");// Ѹ�����
				} else {
					sourceList.add("1");// "Ѹ�ﲻ����"
				}
			}
		} else {// �����г�
			String[] s = fre.getFreeData(s1);
			if (s != null) {
				for (int i = 0; i < s.length; i++) {
					sourceList.add(s[i]);
				}
				sourceList.add("0");// "�����г�"
			}
		}
		return sourceList;
	}

	V7Model v7;
	ArrayList<V7Model> v7List = new ArrayList<>();

	public ArrayList<V7Model> initializeV7() throws Exception {
		XxlsAbstract x = new XxlsAbstract() {
			@Override
			public void optRows(int sheetIndex, int curRow, List<String> rowList) throws SQLException {
				String itemfromv7 = rowList.get(12).trim();
				String ornofromv7 = rowList.get(5).trim();
				String ponofromv7 = rowList.get(6).trim();
				String dscefromv7 = rowList.get(13).trim();
				String titemfromv7 = rowList.get(3).trim();
				String tdscefromv7 = rowList.get(4).trim();
				String customerIDfromv7 = rowList.get(0).trim();
				String nldfromv7 = rowList.get(68).trim();
				v7 = new V7Model();
				v7.setCustomerID(customerIDfromv7);
				v7.setDsce(dscefromv7);
				v7.setItem(itemfromv7);
				v7.setNld(nldfromv7);
				v7.setOrno(ornofromv7);
				v7.setPono(ponofromv7);
				v7.setTdsce(tdscefromv7);
				v7.setTitem(titemfromv7);
				v7List.add(v7);
			}
		};
		x.processOneSheet("C:\\witturcode\\�����������ƻ�_V7_1.xlsx", 2);

		return v7List;

	}
}
