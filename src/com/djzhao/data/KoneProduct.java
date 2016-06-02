package com.djzhao.data;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class KoneProduct {

	public static void main(String[] args) {
		String orno = "1206475";
		String pono = "10";
		String doorType = "";
		String railingType = "";
		String elev = "";
		String productCode = "";
		String producttype = "";
		String componentType = "";
		String certificateNum = "";
		String leibie = "";
		Jexcel je = new Jexcel();
		XSSFSheet AMDsheet = je.jXSSFExcel("C:\\witturcode\\≈∑√À∂©µ•.xlsx");
		int rowsum = AMDsheet.getLastRowNum();
		XSSFRow row;
		for(int i = 1;i<=rowsum;i++){
			row = AMDsheet.getRow(i);
			if(row ==null){
				break;
			}
			String so = String.valueOf((int)row.getCell(0).getNumericCellValue());
			String wpono = String.valueOf((int)row.getCell(1).getNumericCellValue());
			if(so.equals(orno)&&pono.equals(wpono)){
				producttype = row.getCell(13).getStringCellValue();
				leibie = row.getCell(12).getStringCellValue()+"L";
				doorType = row.getCell(15).getStringCellValue();
				String doorMeter = String.valueOf((int)row.getCell(16).getNumericCellValue());
				railingType = String.valueOf((int)row.getCell(17).getNumericCellValue());
				int pannelI = (int) row.getCell(18).getNumericCellValue();
				int railingI = (int) row.getCell(19).getNumericCellValue();
				String s = "";
				elev = row.getCell(20).getStringCellValue();
				if(elev.equals("3")){
					elev = "3";
					s = "AS";
				}else{
					elev = "1";
					s = "EN";
				}
				String couplerPos = row.getCell(21).getStringCellValue();
				String emergency = row.getCell(22).getStringCellValue();
				String packageType = row.getCell(23).getStringCellValue();
				if(producttype.substring(0,2).equals("KM")){
					productCode = leibie+doorType+"-"+doorMeter+"-"+railingType+pannelI+railingI+"-"+elev+couplerPos+emergency;
					componentType = leibie+doorType.substring(0,1)+"-R"+railingType+"-"+s;
					
				}else if(producttype.substring(0,2).equals("EN")){
					if(railingType.equals("1")){
						productCode = "REN200L"+"-"+doorType+"-"+doorMeter+"-"+couplerPos+"-"+packageType;
						
					}else if(railingType.equals("2")){
						productCode = "REN800L"+"-"+doorType+"-"+doorMeter+"-"+couplerPos+"-"+packageType;
						
					}
					componentType = "REN"+doorType.substring(0,1)+"-R"+railingType+"-"+s;
				}
				break;
			}
			
		}
		
		XSSFSheet excerptsheet = je.jXSSFExcel("C:\\witturcode\\AMDL_excerpt.xlsx");
		rowsum = excerptsheet.getLastRowNum();
		for(int i = 1;i<=rowsum;i++){
			row = excerptsheet.getRow(i);
			String str = row.getCell(0).getStringCellValue();
			if(str.equals(componentType)){
				certificateNum = row.getCell(2).getStringCellValue();
				break;
			}
		}
		if(!productCode.equals("")&&!componentType.equals("")&&!certificateNum.equals("")){
			System.out.println(productCode+"\t"+componentType+"\t"+certificateNum);
		}else{
			System.out.println("∆•≈‰ ß∞‹");
		}
		
	}
	
	public String[] getonecode(String tdsce,String productCus){
		String doortype = "";
		String[] onecode = new String[4];
		if(tdsce.contains("41C")){
			doortype = "3C";
		}else if(tdsce.contains("11R")){
			doortype = "2R";
		}else if(tdsce.contains("11L")){
			doortype = "2L";
		}else if(tdsce.contains("01C")){
			doortype = "1C";
		}
		Jexcel je = new Jexcel();
		XSSFSheet koneonecodeSheett = je.jXSSFExcel("C:\\witturcode\\koneonecode.xlsx");
		XSSFRow row;
		int rowsum = koneonecodeSheett.getLastRowNum();
		for(int i = 1;i<=rowsum;i++){
			row = koneonecodeSheett.getRow(i);
			String doorTypeFromSheet = row.getCell(3).getStringCellValue();
			String productCusFromSheet = row.getCell(4).getStringCellValue();
			if(doortype.equals(doorTypeFromSheet)&&productCus.equals(productCusFromSheet)){
				String eight_digits = row.getCell(1).getStringCellValue();
				String suffix_Gxx  =row.getCell(2).getStringCellValue();
				String three_digit = row.getCell(5).getStringCellValue();
				String PDM = row.getCell(6).getStringCellValue();
				onecode[0] = eight_digits;
				onecode[1] = suffix_Gxx;
				onecode[2] = three_digit;
				onecode[3] = PDM;
				return onecode;
			}
		}
		return null;
	}
	
	public String[] getAMDData(String[] s1){
		String s2[] = new String[3];
		String orno = s1[2];
		String pono = s1[3];
		String doorType = "";
		String railingType = "";
		String elev = "";
		String productCode = "";
		String producttype = "";
		String componentType = "";
		String certificateNum = "";
		String leibie = "";
		Jexcel je = new Jexcel();
		XSSFSheet AMDsheet = je.jXSSFExcel("C:\\witturcode\\≈∑√À∂©µ•.xlsx");
		int rowsum = AMDsheet.getLastRowNum();
		XSSFRow row;
		for(int i = 1;i<=rowsum;i++){
			row = AMDsheet.getRow(i);
			if(row ==null){
				break;
			}
			String so = String.valueOf((int)row.getCell(0).getNumericCellValue());
			String wpono = String.valueOf((int)row.getCell(1).getNumericCellValue());
			if(so.equals(orno)&&pono.equals(wpono)){
				producttype = row.getCell(13).getStringCellValue();
				leibie = row.getCell(12).getStringCellValue()+"L";
				doorType = row.getCell(15).getStringCellValue();
				String doorMeter = String.valueOf((int)row.getCell(16).getNumericCellValue());
				railingType = String.valueOf((int)row.getCell(17).getNumericCellValue());
				int pannelI = (int) row.getCell(18).getNumericCellValue();
				int railingI = (int) row.getCell(19).getNumericCellValue();
				String s = "";
				elev = row.getCell(20).getStringCellValue();
				if(elev.equals("3")){
					elev = "3";
					s = "AS";
				}else{
					elev = "1";
					s = "EN";
				}
				String couplerPos = row.getCell(21).getStringCellValue();
				String emergency = row.getCell(22).getStringCellValue();
				String packageType = row.getCell(23).getStringCellValue();
				if(producttype.substring(0,2).equals("KM")){
					productCode = leibie+doorType+"-"+doorMeter+"-"+railingType+pannelI+railingI+"-"+elev+couplerPos+emergency;
					componentType = leibie+doorType.substring(0,1)+"-R"+railingType+"-"+s;
					
				}else if(producttype.substring(0,2).equals("EN")){
					if(railingType.equals("1")){
						productCode = "REN200L"+"-"+doorType+"-"+doorMeter+"-"+couplerPos+"-"+packageType;
						
					}else if(railingType.equals("2")){
						productCode = "REN800L"+"-"+doorType+"-"+doorMeter+"-"+couplerPos+"-"+packageType;
						
					}
					componentType = "REN"+doorType.substring(0,1)+"-R"+railingType+"-"+s;
				}
				
				s2[0] = productCode;
				s2[1] = componentType;
				break;
			}
			
		}
		
		XSSFSheet excerptsheet = je.jXSSFExcel("C:\\witturcode\\AMDL_excerpt.xlsx");
		rowsum = excerptsheet.getLastRowNum();
		for(int i = 1;i<=rowsum;i++){
			row = excerptsheet.getRow(i);
			String str = row.getCell(0).getStringCellValue();
			if(str.equals(componentType)){
				certificateNum = row.getCell(2).getStringCellValue();
				s2[2] = certificateNum;
				break;
			}
		}
		if(!productCode.equals("")&&!componentType.equals("")&&!certificateNum.equals("")){
			return s2;
		}
		return null;
	}

}
