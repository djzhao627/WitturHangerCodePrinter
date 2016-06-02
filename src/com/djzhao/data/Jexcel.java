package com.djzhao.data;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Jexcel {

	public static void main(String[] args){
		XSSFSheet sheet=new Jexcel().jXSSFExcel("materiel.xlsx");
		int rownum=sheet.getLastRowNum();
		int colnum=sheet.getRow(0).getPhysicalNumberOfCells();
		XSSFRow row;
		for(int i=1;i<=rownum;i++){
			for(int j=0;j<colnum;j++){
				row=sheet.getRow(i);
				System.out.print(row.getCell(j).getStringCellValue()+"\t");
			}
			System.out.println();
		}
	}
	
	public HSSFSheet jHSSFExcel(String url){
		File file = new File(url);		
		HSSFSheet sheet = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			HSSFWorkbook wbs;
			try {
				wbs = new HSSFWorkbook(fis);
				sheet=wbs.getSheetAt(0);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return sheet;
	}
	
	public XSSFSheet jXSSFExcel(String url){
		File file = new File(url);
		XSSFSheet sheet = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			XSSFWorkbook wbs;
			try {
				wbs = new XSSFWorkbook(fis);
				sheet=wbs.getSheetAt(0);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return sheet;
	}
}
