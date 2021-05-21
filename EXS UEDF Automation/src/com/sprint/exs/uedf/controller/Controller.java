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
			if (key.split("-")[1].equalsIgnoreCase("a")) {
				uedfFileRecord.put(key.split("-")[0], model.executeEdfSerialQueries(key, queries.getQUERY_A()));
				
			} else if (key.split("-")[1].equalsIgnoreCase("b")) {
				uedfFileRecord.put(key.split("-")[0], model.executeEdfSerialQueries(key, queries.getQUERY_B()));
				
			} else if (key.split("-")[1].equalsIgnoreCase("c")) {
				uedfFileRecord.put(key.split("-")[0], model.executeEdfSerialQueries(key, queries.getQUERY_C()));
				
			} else if (key.split("-")[1].equalsIgnoreCase("p")) {
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
			System.out.println(key);
			System.out.println(uedfFileRecord.get(key).size());
			//createExcel(uedfFileRecord.get(key));
		}
		
	}
	//20 column or 21
	public void createExcel(List<UedfFile> uedfFileRecordList) {
		String filepath = System.getProperty("user.home") + "\\Documents\\exs_uedf\\UEDF_Template.xlsx";
		String outputFile = System.getProperty("user.home") + "\\Documents\\exs_uedf\\spbsist_" + uedfFileRecordList.get(0).getFileName().split("\\.")[0] + ".xlsx";
		String serialType = "";
		
		try {
			FileInputStream xlsxFile = new FileInputStream(new File(filepath));
			Workbook workbook = new XSSFWorkbook(xlsxFile);
			Sheet sheet = workbook.getSheetAt(0);
			
			for (int ctr = 0; ctr < uedfFileRecordList.size(); ctr++) {
				Row row = sheet.createRow(ctr + 1);
				serialType = uedfFileRecordList.get(ctr).getEdfSerial();
				
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
				
				// P P3 P5 K3 B E H H5 K4 L L1 L3 L4 M3 R T Y
				if (!serialType.equalsIgnoreCase("a")) {
					cell = row.createCell(11);
					cell.setCellValue(uedfFileRecordList.get(ctr).getLockCode1());
				
					cell = row.createCell(12);
					cell.setCellValue(uedfFileRecordList.get(ctr).getLockCode2());
				}
				
				if (serialType.equalsIgnoreCase("a") || serialType.equalsIgnoreCase("r2")
						|| serialType.equalsIgnoreCase("v")) {
					cell = row.createCell(14);
					cell.setCellValue(uedfFileRecordList.get(ctr).getSharedSecretCode());
					
					cell = row.createCell(18);
					cell.setCellValue(uedfFileRecordList.get(ctr).getMacID());
				}
				
				if (serialType.equalsIgnoreCase("b") || serialType.toLowerCase().contains("c")
						|| serialType.equalsIgnoreCase("l") || serialType.equalsIgnoreCase("r")
						|| serialType.equalsIgnoreCase("t") || serialType.equalsIgnoreCase("w")
						|| serialType.equalsIgnoreCase("w1") || serialType.equalsIgnoreCase("w4")) {
					
					if (!serialType.equalsIgnoreCase("w1") || !serialType.equalsIgnoreCase("w4")) {
						cell = row.createCell(8);
						cell.setCellValue(uedfFileRecordList.get(ctr).getImsi());
					}
					
					cell = row.createCell(9);
					cell.setCellValue(uedfFileRecordList.get(ctr).getAuthKey());
					
					cell = row.createCell(10);
					cell.setCellValue(uedfFileRecordList.get(ctr).getAuthKeyChecksum());

					cell = row.createCell(17);
					cell.setCellValue(uedfFileRecordList.get(ctr).getPrl());
					
					if (serialType.equalsIgnoreCase("r")) {
						cell = row.createCell(36);
						cell.setCellValue(uedfFileRecordList.get(ctr).getTransceiverSKU());
					}
					
				}
				
				if (serialType.contains("c") || serialType.equalsIgnoreCase("t")
						|| serialType.contains("u") || serialType.equalsIgnoreCase("w")
						|| serialType.equalsIgnoreCase("w1") || serialType.equalsIgnoreCase("w3")
						|| serialType.equalsIgnoreCase("w4")) {
					
					if (serialType.equalsIgnoreCase("c") || serialType.equalsIgnoreCase("c1")
							|| serialType.equalsIgnoreCase("t") || serialType.equalsIgnoreCase("u")
							|| serialType.equalsIgnoreCase("u1") || serialType.equalsIgnoreCase("u2")
							|| serialType.equalsIgnoreCase("u3") || serialType.equalsIgnoreCase("w")
							|| serialType.equalsIgnoreCase("w1") || serialType.equalsIgnoreCase("w3")
							|| serialType.equalsIgnoreCase("w4")) {
						
						if (!serialType.equalsIgnoreCase("u2")) {
							cell = row.createCell(26);
							cell.setCellValue(uedfFileRecordList.get(ctr).getIccid());
						}
						
						cell = row.createCell(27);
						cell.setCellValue(uedfFileRecordList.get(ctr).getPuc1());
	
						cell = row.createCell(28);
						cell.setCellValue(uedfFileRecordList.get(ctr).getPuc2());
	
						cell = row.createCell(29);
						cell.setCellValue(uedfFileRecordList.get(ctr).getPin1());
	
						cell = row.createCell(30);
						cell.setCellValue(uedfFileRecordList.get(ctr).getPin2());
						
						cell = row.createCell(31);
						cell.setCellValue(uedfFileRecordList.get(ctr).getAdminCode1());
						
						cell = row.createCell(35);
						cell.setCellValue(uedfFileRecordList.get(ctr).getKi());
						
						cell = row.createCell(46);
						cell.setCellValue(uedfFileRecordList.get(ctr).getImsiUiccCard());
						
						cell = row.createCell(47);
						cell.setCellValue(uedfFileRecordList.get(ctr).getAccUiccCard());
						
						if (serialType.equalsIgnoreCase("t")) {
							cell = row.createCell(18);
							cell.setCellValue(uedfFileRecordList.get(ctr).getMacID());
							
							cell = row.createCell(45);
							cell.setCellValue(uedfFileRecordList.get(ctr).getUiccCardIndicator());

						} 
						
						if (serialType.equalsIgnoreCase("c") || serialType.equalsIgnoreCase("c1")
								|| serialType.equalsIgnoreCase("w") || serialType.equalsIgnoreCase("w1")
								|| serialType.equalsIgnoreCase("w4")){
							cell = row.createCell(48);
							cell.setCellValue(uedfFileRecordList.get(ctr).getSfEquipmentID());
							
						}
						
						if (serialType.equalsIgnoreCase("u2") || serialType.equalsIgnoreCase("w")) {
							cell = row.createCell(32);
							cell.setCellValue(uedfFileRecordList.get(ctr).getManuEncryptKeyIndex());
						}
						
						if (serialType.equalsIgnoreCase("u3") || serialType.equalsIgnoreCase("w1")
								|| serialType.equalsIgnoreCase("w3") || serialType.equalsIgnoreCase("w4")) {
							cell = row.createCell(32);
							cell.setCellValue(uedfFileRecordList.get(ctr).getProfileType());
						}
						
					} 
					
					
					if (serialType.equalsIgnoreCase("c2") || serialType.equalsIgnoreCase("c4")
							|| serialType.equalsIgnoreCase("c5") || serialType.equalsIgnoreCase("u3")
							|| serialType.equalsIgnoreCase("w3") || serialType.equalsIgnoreCase("w4")) {
						cell = row.createCell(50);
						cell.setCellValue(uedfFileRecordList.get(ctr).getEfImpu());
						
						cell = row.createCell(51);
						cell.setCellValue(uedfFileRecordList.get(ctr).getEfImpi());
						
					}
				
				}
				
				if (serialType.equalsIgnoreCase("e")) {
					cell = row.createCell(17);
					cell.setCellValue(uedfFileRecordList.get(ctr).getPrl());
				}
				
				if (serialType.equalsIgnoreCase("g")) {
					cell = row.createCell(18);
					cell.setCellValue(uedfFileRecordList.get(ctr).getMacID());
				}
				
				if (serialType.equalsIgnoreCase("l") || serialType.equalsIgnoreCase("r2")
						|| serialType.equalsIgnoreCase("t")) {
					cell = row.createCell(22);
					cell.setCellValue(uedfFileRecordList.get(ctr).getCardSKU());
					
				}
				
				
				
				
				/* P P3 P5 K3 A B E G H H3 H5 K4 L L1 L3 L4 M3 R R2 T V Y
				cell = row.createCell(14);
				cell.setCellValue(uedfFileRecordList.get(ctr).getSoftwareVersionNo());
				*/
				
				/* P B E H H5 K4 L L1 L4 R T
				if (uedfFileRecordList.get(ctr).getEdfSerial().equalsIgnoreCase("p")) {
					cell = row.createCell(15);
					cell.setCellValue(uedfFileRecordList.get(ctr).getMeidHex());
					
					cell = row.createCell(16);
					cell.setCellValue(uedfFileRecordList.get(ctr).getMeidDec());
				}*/
				
				cell = row.createCell(20);
				cell.setCellValue(uedfFileRecordList.get(ctr).getEdfSerial());
				
				if (serialType.equalsIgnoreCase("p3") || serialType.equalsIgnoreCase("k3") 
						|| serialType.equalsIgnoreCase("h3") || serialType.equalsIgnoreCase("h5")
						|| serialType.equalsIgnoreCase("k4") || serialType.equalsIgnoreCase("l3")
						|| serialType.equalsIgnoreCase("l4") || serialType.equalsIgnoreCase("m3")) {
					cell = row.createCell(25);
					cell.setCellValue(uedfFileRecordList.get(ctr).getImeiDec());
					
					if (serialType.equalsIgnoreCase("h5")) {
						cell = row.createCell(53);
						cell.setCellValue(uedfFileRecordList.get(ctr).getImeiDec2());
					}
				}
				
				if (serialType.equalsIgnoreCase("k3") || serialType.equalsIgnoreCase("l3")) {
					cell = row.createCell(52);
					cell.setCellValue(uedfFileRecordList.get(ctr).getCsnOrEid());
				}
				
				if (serialType.equalsIgnoreCase("y")) {
					cell = row.createCell(9);
					cell.setCellValue(uedfFileRecordList.get(ctr).getAuthKey());
					
					cell = row.createCell(10);
					cell.setCellValue(uedfFileRecordList.get(ctr).getAuthKeyChecksum());
					
					cell = row.createCell(36);
					cell.setCellValue(uedfFileRecordList.get(ctr).getTransceiverSKU());

				}
				
				/* P3, P5, P, M3 R R2 Y
				if (uedfFileRecordList.get(ctr).getEdfSerial().equalsIgnoreCase("p") || uedfFileRecordList.get(ctr).getEdfSerial().equalsIgnoreCase("p3")) {
				
					cell = row.createCell(26);
					cell.setCellValue(uedfFileRecordList.get(ctr).getIccid());
				}*/
				
				
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
