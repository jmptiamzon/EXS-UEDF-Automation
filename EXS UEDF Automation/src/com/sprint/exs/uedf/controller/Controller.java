package com.sprint.exs.uedf.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sprint.exs.uedf.model.Model;
import com.sprint.exs.uedf.model.Queries;
import com.sprint.exs.uedf.model.UedfErrors;
import com.sprint.exs.uedf.model.UedfFile;

public class Controller {
	private Model model;
	private Queries queries;
	
	public Controller() {
		this.model = new Model();
		this.queries = new Queries();
	}
	
	public Map<String, List<UedfErrors>> getUedfErrors() {
		return model.executeErrorQuery(queries.getQUERY_ERRORS());
	}
	
	public Map<String, List<UedfFile>> getUedfFileRecord(Map<String, List<UedfErrors>> uedfErrors) {
		Map<String, List<UedfFile>> uedfFileRecord = new HashMap<>();
		
		for (String key : uedfErrors.keySet()) {
				
			if (key.split("-")[1].equalsIgnoreCase("p")) {
				uedfFileRecord.put(key.split("-")[0], model.executeEdfSerialQueries(key, queries.getQUERY_P()));
				
			} else if (key.split("-")[1].equalsIgnoreCase("p5")) {
				uedfFileRecord.put(key.split("-")[0], model.executeEdfSerialQueries(key, queries.getQUERY_P5()));
					
			} else if (key.split("-")[1].equalsIgnoreCase("p3")) {
				uedfFileRecord.put(key.split("-")[0], model.executeEdfSerialQueries(key, queries.getQUERY_P3()));
					
			} else if (key.split("-")[1].equalsIgnoreCase("k3")) {
				uedfFileRecord.put(key.split("-")[0], model.executeEdfSerialQueries(key, queries.getQUERY_K3()));
					
			} else {
				uedfFileRecord.put(key.split("-")[0], model.executeEdfSerialQueries(key, queries.getQUERY_OTHER()));
					
			}
			
		}

		return uedfFileRecord;
	}
	
	public void createMultipleExcel(Map<String, List<UedfFile>> uedfFileRecord) {
		
		for (String key : uedfFileRecord.keySet()) {
			createExcel(uedfFileRecord.get(key));
		}
		
	}
	//20 column or 21
	public void createExcel(List<UedfFile> uedfFileRecordList) {
		String filepath = System.getProperty("user.home") + "\\Documents\\exs_uedf\\UEDF_Template.xlsx";
		String outputFile = System.getProperty("user.home") + "\\Documents\\exs_uedf\\spbsist_" + uedfFileRecordList.get(0).getFileName().split("\\.")[0] + ".xlsx";
		
		try {
			FileInputStream xlsxFile = new FileInputStream(new File(filepath));
			Workbook workbook = new XSSFWorkbook(xlsxFile);
			Sheet sheet = workbook.getSheetAt(0);
			
			
			
			for (int ctr = 0; ctr < uedfFileRecordList.size(); ctr++) {
				Row row = sheet.createRow(ctr + 1);
				
				Cell cell = row.createCell(2); //20
				cell.setCellValue(uedfFileRecordList.get(ctr).getMfgLocationID());
				
				cell = row.createCell(3);
				cell.setCellValue(uedfFileRecordList.get(ctr).getMfgName());
				
				cell = row.createCell(4);
				cell.setCellValue(uedfFileRecordList.get(ctr).getModelNumber());
				
				cell = row.createCell(5);
				cell.setCellValue(uedfFileRecordList.get(ctr).getModelName());
				
				cell = row.createCell(6);
				cell.setCellValue(uedfFileRecordList.get(ctr).getEquipmentType());
				
				cell = row.createCell(11);
				cell.setCellValue(uedfFileRecordList.get(ctr).getLockCode1());
				
				cell = row.createCell(12);
				cell.setCellValue(uedfFileRecordList.get(ctr).getLockCode2());
				
				cell = row.createCell(14);
				cell.setCellValue(uedfFileRecordList.get(ctr).getSoftwareVersionNo());
				
				if (uedfFileRecordList.get(ctr).getEdfSerial().equalsIgnoreCase("p")) {
					cell = row.createCell(15);
					cell.setCellValue(uedfFileRecordList.get(ctr).getMeidHex());
					
					cell = row.createCell(16);
					cell.setCellValue(uedfFileRecordList.get(ctr).getMeidDec());
				}
				
				cell = row.createCell(20);
				cell.setCellValue(uedfFileRecordList.get(ctr).getEdfSerial());
				
				if (uedfFileRecordList.get(ctr).getEdfSerial().equalsIgnoreCase("p3")) {
					cell = row.createCell(25);
					cell.setCellValue(uedfFileRecordList.get(ctr).getImeiDec());
				}
				
				cell = row.createCell(26);
				cell.setCellValue(uedfFileRecordList.get(ctr).getIccid());
				
			}
			
			xlsxFile.close();
			
			FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
			workbook.write(fileOutputStream);
			workbook.close();
			fileOutputStream.close();
			
		} catch (IOException e) {
			
			System.out.println("File issues: " + e.getMessage());
			
		}
		
		
		
	}
	 

}