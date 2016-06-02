package com.djzhao.data;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class SchindlerProduct {

	public static void main(String[] args) {

		String item = "2L2Z01C";
		String dsce = "挂件01C";
		String orno = "1121896";
		String pono = "20";
		String titem ="2L3AC"; //"2L3AC";
		String tdsce ="Hydra"; //"Hydra";
		String ATV = "";
		String BB = "";
		String T = "";
		String YYYY = "";
		String AAAAAAA = "";
		String materialdes = "";
		String idnumber = "";
		String customerID = "";
		String nld = "";
		Jexcel je = new Jexcel();
		XSSFSheet v7sheet = je.jXSSFExcel("C:\\witturcode\\V7.xlsx");
		XSSFRow row;
		int rowsum = v7sheet.getLastRowNum();
		for(int i = 2;i<=rowsum;i++){
			row = v7sheet.getRow(i);
			String itemfromv7 = row.getCell(12).getStringCellValue().trim();
			String ornofromv7 = String.valueOf((int)row.getCell(5).getNumericCellValue());
			String ponofromv7 = String.valueOf((int)row .getCell(6).getNumericCellValue());
			String dscefromv7 = row.getCell(13).getStringCellValue().trim();
			if(item.equals(itemfromv7)&&orno.equals(ornofromv7)&&pono.equals(ponofromv7)&&dsce.equals(dscefromv7)){
				titem = row.getCell(3).getStringCellValue().trim();
				tdsce = row.getCell(4).getStringCellValue().trim();
				customerID = row.getCell(0).getStringCellValue().trim();
				nld = row.getCell(67).getStringCellValue().trim();
				break;
			}
			
		}
		
		XSSFSheet dataOppsheet = je.jXSSFExcel("C:\\witturcode\\LD CODE DOORS-数据转换对照表.xlsx");
		rowsum = dataOppsheet.getLastRowNum();
		System.out.println(rowsum);
		for(int i = 3;i<=rowsum;i++){
			row = dataOppsheet.getRow(i);
			if(row == null){
				break;
			}
			String tdscefromdata = row.getCell(2).getStringCellValue();
			boolean isok = false;
			String str1[] = tdscefromdata.split("/");
			for(int j = 0;j<str1.length;j++){
				if(tdsce.contains(str1[j])&&!str1[j].equals("")){
					isok = true;
					break;
				}
			}
			if(isok ==true){
				isok = false;
				String titemfromdata = row.getCell(3).getStringCellValue().trim();
				String str2[] = titemfromdata.split("/");
				for(int j = 0;j<str2.length;j++){
					if(titem.contains(str2[j])){
						isok = true;
						break;
					}
				}
			}
			if(isok == true){
				isok = false;
				String dscefromdata = row.getCell(4).getStringCellValue().trim();
				String str3[] = dscefromdata.split("/");
				for(int j = 0;j<str3.length;j++){
					if(dsce.contains(str3[j])){
						isok = true;
						break;
					}
				}
			}
			if(isok == true){
				isok = false;
				String itemfromdata = row.getCell(5).getStringCellValue().trim();
				String str3[] = itemfromdata.split("/");
				for(int j = 0;j<str3.length;j++){
					if(item.contains(str3[j])){
						isok = true;
						break;
					}
				}
			}
			
			if(isok == true){
				ATV = row.getCell(6).getStringCellValue();
				BB = row.getCell(7).getStringCellValue();
				T = row.getCell(8).getStringCellValue();
				YYYY = row.getCell(9).getStringCellValue();
				AAAAAAA = row.getCell(10).getStringCellValue();
				materialdes = row.getCell(11).getStringCellValue();
				idnumber = row.getCell(12).getStringCellValue();
				break;
			}
			
		}
		
		System.out.println("customerid:"+customerID+"\tATV:"+ATV+"\tBB:"+BB+"\tT:"+T+
				"\tYYYY:"+YYYY+"\tAAAAAAA:"+AAAAAAA+"\tMaterial Description:"+materialdes+"\tID Number:"+idnumber
				+"\tnld:"+nld);
		if(ATV.equals("")){
			System.out.println("匹配不到数据");
		}
	

	}
	
	public String[] getSchindlerData(String[] s1){
		String s2[] = new String[5];
		String item = SourceDataFiled.s1[0];
		String dsce = SourceDataFiled.s1[1];
		// String orno = SourceDataFiled.s1[2];
		// String pono = SourceDataFiled.s1[3];
		String ATV = "";
		String BB = "";
		String T = "";
		String YYYY = "";
		String AAAAAAA = "";
		String materialdes = "";
		String idnumber = "";
		Jexcel je = new Jexcel();
		XSSFRow row;
		int rowsum = 0;
		XSSFSheet dataOppsheet = je.jXSSFExcel("C:\\witturcode\\LD CODE DOORS-数据转换对照表.xlsx");
		rowsum = dataOppsheet.getLastRowNum();
		for(int i = 3;i<rowsum;i++){
			row = dataOppsheet.getRow(i);
			if(row == null){
				break;
			}
			String tdscefromdata = row.getCell(2).getStringCellValue();
			boolean isok = false; //isok？表示所有匹配是否都成功，只要一个匹配失败就重新匹配
			String str1[] ;
			if(tdscefromdata.contains("&")){
				String tdscebefore = tdscefromdata.substring(0,tdscefromdata.indexOf("&"));  //"&"之前的字串
				String tdsceafter = tdscefromdata.substring(tdscefromdata.indexOf("&"));//"&"之后的字串
				str1 = tdscebefore.split("/");
				tdsceafter = tdsceafter.substring(2); //去除&和回车字符
				for(int j = 0;j<str1.length;j++){
					if(SourceDataFiled.tdsce.contains(str1[j])&&!str1[j].equals("")&&SourceDataFiled.tdsce.contains(tdsceafter)){
						isok = true;
						break;
					}
				}
			}else{
				str1 = tdscefromdata.split("/");
				for(int j = 0;j<str1.length;j++){
					if(SourceDataFiled.tdsce.contains(str1[j])&&!str1[j].equals("")){
						isok = true;
						break;
					}
				}
			}
			if(isok ==true){
				isok = false;
				String titemfromdata = row.getCell(3).getStringCellValue().trim();
				String str2[] = titemfromdata.split("/");
				for(int j = 0;j<str2.length;j++){
					if(SourceDataFiled.titem.contains(str2[j])){
						isok = true;
						break;
					}
				}
			}
			if(isok == true){
				isok = false;
				String dscefromdata = row.getCell(4).getStringCellValue().trim();
				String str3[] = dscefromdata.split("/");
				for(int j = 0;j<str3.length;j++){
					if(dsce.contains(str3[j])){
						isok = true;
						break;
					}
				}
			}
			if(isok == true){
				isok = false;
				String itemfromdata = row.getCell(5).getStringCellValue().trim();
				String str3[] = itemfromdata.split("/");
				for(int j = 0;j<str3.length;j++){
					if(item.contains(str3[j])){
						isok = true;
						break;
					}
				}
			}
			
			if(isok == true){
				ATV = row.getCell(6).getStringCellValue();
				BB = row.getCell(7).getStringCellValue();
				T = row.getCell(8).getStringCellValue();
				YYYY = row.getCell(9).getStringCellValue();
				AAAAAAA = row.getCell(10).getStringCellValue();
				materialdes = row.getCell(11).getStringCellValue();
				idnumber = row.getCell(12).getStringCellValue();
				s2[0] = ATV;
				s2[1] = YYYY+"-"+AAAAAAA;
				s2[2] = BB+"/"+T;
				s2[3] = materialdes;
				s2[4] = idnumber;
 				return s2;
			}
			
		}
		
		return null;
	}

}
