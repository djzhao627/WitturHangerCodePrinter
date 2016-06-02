package com.djzhao.data;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class WitturData {

	public static void main(String[] args) {
		String item = "2L2Z01C";
		String dsce = "挂件01C";
		String orno = "1121896";
		String pono = "20";
		String titem = "2L3AC"; // "2L3AC";
		String tdsce = "Hydra"; // "Hydra";
		String ATV = "";
		String BB = "";
		String T = "";
		String YYYY = "";
		String AAAAAAA = "";
		Jexcel je = new Jexcel();
		XSSFSheet v7sheet = je.jXSSFExcel("C:\\witturcode\\V7格式.xlsx");
		XSSFRow row;
		int rowsum = v7sheet.getLastRowNum();
		for (int i = 2; i <= rowsum; i++) {
			row = v7sheet.getRow(i);
			String itemfromv7 = row.getCell(12).getStringCellValue().trim();
			String ornofromv7 = String.valueOf((int) row.getCell(5).getNumericCellValue());
			String ponofromv7 = String.valueOf((int) row.getCell(6).getNumericCellValue());
			String dscefromv7 = row.getCell(13).getStringCellValue().trim();
			if (item.equals(itemfromv7) && orno.equals(ornofromv7) && pono.equals(ponofromv7)
					&& dsce.equals(dscefromv7)) {
				titem = row.getCell(3).getStringCellValue().trim();
				tdsce = row.getCell(4).getStringCellValue().trim();
				break;
			}

		}

		XSSFSheet dataOppsheet = je.jXSSFExcel("C:\\witturcode\\LD CODE DOORS-数据转换对照表.xlsx");
		rowsum = dataOppsheet.getLastRowNum();
		// System.out.println(rowsum);
		for (int i = 3; i <= rowsum; i++) {
			row = dataOppsheet.getRow(i);
			if (row == null) {
				break;
			}
			String tdscefromdata = row.getCell(2).getStringCellValue();
			boolean isok = false;
			String str1[] = tdscefromdata.split("/");
			for (int j = 0; j < str1.length; j++) {
				if (tdsce.contains(str1[j]) && !str1[j].equals("")) {
					isok = true;
					break;
				}
			}
			if (isok == true) {
				isok = false;
				String titemfromdata = row.getCell(3).getStringCellValue().trim();
				String str2[] = titemfromdata.split("/");
				for (int j = 0; j < str2.length; j++) {
					if (titem.contains(str2[j])) {
						isok = true;
						break;
					}
				}
			}
			if (isok == true) {
				isok = false;
				String dscefromdata = row.getCell(4).getStringCellValue().trim();
				String str3[] = dscefromdata.split("/");
				for (int j = 0; j < str3.length; j++) {
					if (dsce.contains(str3[j])) {
						isok = true;
						break;
					}
				}
			}
			if (isok == true) {
				isok = false;
				String itemfromdata = row.getCell(5).getStringCellValue().trim();
				String str3[] = itemfromdata.split("/");
				for (int j = 0; j < str3.length; j++) {
					if (item.contains(str3[j])) {
						isok = true;
						break;
					}
				}
			}

			if (isok == true) {
				ATV = row.getCell(6).getStringCellValue();
				BB = row.getCell(7).getStringCellValue();
				T = row.getCell(8).getStringCellValue();
				YYYY = row.getCell(9).getStringCellValue();
				AAAAAAA = row.getCell(10).getStringCellValue();
				break;
			}

		}

		System.out.println("ATV:" + ATV + "\tBB:" + BB + "\tT:" + T + "\tYYYY:" + YYYY + "\tAAAAAAA:" + AAAAAAA + "\t");
		if (ATV.equals("")) {
			System.out.println("匹配不到数据");
		}
	}

	public String[] getWitturData(String[] s1) throws Exception {
		String s2[] = new String[3];

		String item = s1[0];
		String dsce = s1[1];
		String orno = s1[2];
		String pono = s1[3];
		String titem = ""; // "2L3AC";
		String tdsce = ""; // "Hydra";
		String ATV = "";
		String BB = "";
		String T = "";
		String YYYY = "";
		String AAAAAAA = "";
		Jexcel je = new Jexcel();
		XSSFSheet v7sheet = je.jXSSFExcel("C:\\witturcode\\V7格式.xlsx");
		XSSFRow row;
		int rowsum = v7sheet.getLastRowNum();
		for (int i = 2; i < rowsum; i++) {
			row = v7sheet.getRow(i);
			String itemfromv7 = row.getCell(12).getStringCellValue().trim();
			String ornofromv7 = String.valueOf((int) row.getCell(5).getNumericCellValue());
			String ponofromv7 = String.valueOf((int) row.getCell(6).getNumericCellValue());
			String dscefromv7 = row.getCell(13).getStringCellValue().trim();
			if (item.equals(itemfromv7) && orno.equals(ornofromv7) && pono.equals(ponofromv7)
					&& dsce.equals(dscefromv7)) {
				titem = row.getCell(3).getStringCellValue().trim();
				tdsce = row.getCell(4).getStringCellValue().trim();
				break;
			}

		}

		XSSFSheet dataOppsheet = je.jXSSFExcel("C:\\witturcode\\LD CODE DOORS-数据转换对照表.xlsx");
		 
		rowsum = dataOppsheet.getLastRowNum();
		// System.out.println(rowsum);
		for (int i = 3; i < rowsum; i++) {
			row = dataOppsheet.getRow(i);
			if (row == null) {
				break;
			}
			String tdscefromdata = row.getCell(2).getStringCellValue();
			boolean isok = false;
			String str1[] = tdscefromdata.split("/");
			for (int j = 0; j < str1.length; j++) {
				if (tdsce.contains(str1[j]) && !str1[j].equals("")) {
					isok = true;
					break;
				}
			}
			if (isok == true) {
				isok = false;
				String titemfromdata = row.getCell(3).getStringCellValue().trim();
				String str2[] = titemfromdata.split("/");
				for (int j = 0; j < str2.length; j++) {
					if (titem.contains(str2[j])) {
						isok = true;
						break;
					}
				}
			}
			if (isok == true) {
				isok = false;
				String dscefromdata = row.getCell(4).getStringCellValue().trim();
				String str3[] = dscefromdata.split("/");
				for (int j = 0; j < str3.length; j++) {
					if (dsce.contains(str3[j])) {
						isok = true;
						break;
					}
				}
			}
			if (isok == true) {
				isok = false;
				String itemfromdata = row.getCell(5).getStringCellValue().trim();
				String str3[] = itemfromdata.split("/");
				for (int j = 0; j < str3.length; j++) {
					if (item.contains(str3[j])) {
						isok = true;
						break;
					}
				}
			}

			if (isok == true) {
				ATV = row.getCell(6).getStringCellValue();
				BB = row.getCell(7).getStringCellValue();
				T = row.getCell(8).getStringCellValue();
				YYYY = row.getCell(9).getStringCellValue();
				AAAAAAA = row.getCell(10).getStringCellValue();
				s2[0] = ATV;
				s2[1] = YYYY + "-" + AAAAAAA;
				s2[2] = BB + "/" + T;
				break;
			}

		}

		System.out.println("ATV:" + ATV + "\tBB:" + BB + "\tT:" + T + "\tYYYY:" + YYYY + "\tAAAAAAA:" + AAAAAAA);
		if (ATV.equals("")) {
			// System.out.println("匹配不到数据");
			throw new NoDataException("数据无法匹配！");
		}
		return s2;
	}

	class NoDataException extends Exception {
		
		private static final long serialVersionUID = -3171617790352594878L;

		public NoDataException(String msg) {
			super(msg);
		}
	}

}
