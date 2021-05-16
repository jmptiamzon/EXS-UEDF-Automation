package com.sprint.exs.uedf.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {
	
	public Map<String, List<UedfErrors>> executeErrorQuery(String query) {
		UedfErrors tempUedfErrors;
		Map<String, List<UedfErrors>> uedfErrors = new HashMap<>();
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = 
					DriverManager.getConnection("", "", "");
			
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			
			while (rs.next()) {
				tempUedfErrors = new UedfErrors();
				if (!uedfErrors.containsKey(rs.getString(1))) {
					uedfErrors.put(rs.getString(1) + "-" + rs.getString(3), new ArrayList<>());
				}
				
				tempUedfErrors.setFileName(rs.getString(1));
				tempUedfErrors.setCntSku(rs.getInt(2));
				tempUedfErrors.setEdfSerial(rs.getString(3));
				tempUedfErrors.setUniqueSeqNum(rs.getLong(4));
				tempUedfErrors.setDeviceCount(rs.getInt(5));
				tempUedfErrors.setCountErrDet(rs.getInt(6));
				tempUedfErrors.setPhoneType(rs.getString(7));
				tempUedfErrors.setStatus(rs.getString(8));
				tempUedfErrors.setCreationDate(rs.getString(9));
				tempUedfErrors.setUpdateDate(rs.getString(10));
				
				uedfErrors.get(rs.getString(1) + "-" + rs.getString(3)).add(tempUedfErrors);
			}
			
			conn.close();
			
		} catch (ClassNotFoundException e) {
			System.out.println("Driver error: " + e.getMessage());
			
		} catch (SQLException e1) {
			System.out.println("SQL error: " + e1.getMessage());
			
		}
		

		return uedfErrors;

	}
	
	
	public List<UedfFile> executeEdfSerialQueries(String filename, String query) {
		UedfFile tempUedfFile;
		List<UedfFile> uedfFile = new ArrayList<>();
		

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");		
			Connection conn = 
					DriverManager.getConnection("","","");
			
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, filename.split("-")[0]);
			System.out.println(filename.split("-")[0]);
			
			ResultSet rs = statement.executeQuery();
			
			while (rs.next()) {
				tempUedfFile = new UedfFile();
				tempUedfFile.setFileName(rs.getString(1) == null ? "(null)" : rs.getString(1));
				tempUedfFile.setEdfSerial(rs.getString(2) == null ? "(null)" : rs.getString(2));
				tempUedfFile.setMfgLocationID(rs.getString(3) == null ? "(null)" : rs.getString(3));
				tempUedfFile.setMfgName(rs.getString(4) == null ? "(null)" : rs.getString(4));
				tempUedfFile.setModelNumber(rs.getString(5) == null ? "(null)" : rs.getString(5));
				tempUedfFile.setModelName(rs.getString(6) == null ? "(null)" : rs.getString(6));
				tempUedfFile.setEquipmentType(rs.getString(7) == null ? "(null)" : rs.getString(7));
				/* if P P3 P5 K3 A B
				tempUedfFile.setSoftwareVersionNo(rs.getString(8) == null ? "(null)" : rs.getString(8)); */
				
				/* P, P3, P5, K3
				if (!filename.split("-")[1].equalsIgnoreCase("a")) {
					tempUedfFile.setLockCode1(rs.getString(9) == null ? "(null)" : rs.getString(9));
					tempUedfFile.setLockCode2(rs.getString(10) == null ? "(null)" : rs.getString(10));
				} */
				
				if (filename.split("-")[1].equalsIgnoreCase("a")) {
					tempUedfFile.setMacID(rs.getString(9) == null ? "(null)" : rs.getString(9));
					tempUedfFile.setSharedSecretCode(rs.getString(10) == null ? "(null)" : rs.getString(10));
					tempUedfFile.setErrorDescription(rs.getString(11) == null ? "(null)" : rs.getString(11));
					tempUedfFile.setComment(rs.getString(12) == null ? "(null)" : rs.getString(12));
					
				}
				
				if (filename.split("-")[1].equalsIgnoreCase("b")) {
					tempUedfFile.setImsi(rs.getString(9) == null ? "(null)" : rs.getString(9));
					tempUedfFile.setAuthKey(rs.getString(10) == null ? "(null)" : rs.getString(10));
					tempUedfFile.setAuthKeyChecksum(rs.getString(11) == null ? "(null)" : rs.getString(11));
					tempUedfFile.setLockCode1(rs.getString(12) == null ? "(null)" : rs.getString(12));
					tempUedfFile.setLockCode2(rs.getString(13) == null ? "(null)" : rs.getString(13));
					tempUedfFile.setMeidHex(rs.getString(14) == null ? "(null)" : rs.getString(14));
					tempUedfFile.setMeidDec(rs.getString(15) == null ? "(null)" : rs.getString(15));
					tempUedfFile.setPrl(rs.getString(16) == null ? "(null)" : rs.getString(16));
					tempUedfFile.setErrorDescription(rs.getString(17) == null ? "(null)" : rs.getString(17));
					tempUedfFile.setComment(rs.getString(18) == null ? "(null)" : rs.getString(18));
				}
				
				if (filename.split("-")[1].contains("c")) {
					if(filename.split("-")[1].equalsIgnoreCase("c")) {
						tempUedfFile.setImsi(rs.getString(8) == null ? "(null)" : rs.getString(8));
					}
					
					tempUedfFile.setAuthKey(rs.getString(9) == null ? "(null)" : rs.getString(9));
					tempUedfFile.setAuthKeyChecksum(rs.getString(10) == null ? "(null)" : rs.getString(10));
					tempUedfFile.setPrl(rs.getString(11) == null ? "(null)" : rs.getString(11));
					tempUedfFile.setIccid(rs.getString(12) == null ? "(null)" : rs.getString(12));
					tempUedfFile.setPuc1(rs.getString(13) == null ? "(null)" : rs.getString(13));
					tempUedfFile.setPuc2(rs.getString(14) == null ? "(null)" : rs.getString(14));
					tempUedfFile.setPin1(rs.getString(15) == null ? "(null)" : rs.getString(15));
					tempUedfFile.setPin2(rs.getString(16) == null ? "(null)" : rs.getString(16));
					tempUedfFile.setAdminCode1(rs.getString(17) == null ? "(null)" : rs.getString(17));
					tempUedfFile.setKi(rs.getString(18) == null ? "(null)" : rs.getString(18));
					tempUedfFile.setImsiUiccCard(rs.getString(19) == null ? "(null)" : rs.getString(19));
					tempUedfFile.setAccUiccCard(rs.getString(20) == null ? "(null)" : rs.getString(20));
					tempUedfFile.setSfEquipmentID(rs.getString(21) == null ? "(null)" : rs.getString(21));
					tempUedfFile.setErrorDescription(rs.getString(22) == null ? "(null)" : rs.getString(22));
					tempUedfFile.setComment(rs.getString(23) == null ? "(null)" : rs.getString(23));
				}
				
				if (filename.split("-")[1].equalsIgnoreCase("k3")) {
					tempUedfFile.setCsnOrEid(rs.getString(11) == null ? "(null)" : rs.getString(11));
					tempUedfFile.setImeiDec(rs.getString(12) == null ? "(null)" : rs.getString(12));
					tempUedfFile.setComment(rs.getString(13) == null ? "(null)" : rs.getString(13));
				}
				
				/*
				else { // P3, P5, P
					tempUedfFile.setMeidHex(rs.getString(11) == null ? "(null)" : rs.getString(11));
					tempUedfFile.setMeidDec(rs.getString(12) == null ? "(null)" : rs.getString(12));
					
					if (filename.split("-")[1].equalsIgnoreCase("p3")) {
						tempUedfFile.setImeiDec(rs.getString(13) == null ? "(null)" : rs.getString(13));
						tempUedfFile.setIccid(rs.getString(14) == null ? "(null)" : rs.getString(14));
						tempUedfFile.setErrorDescription(rs.getString(15) == null ? "(null)" : rs.getString(15));
						tempUedfFile.setComment(rs.getString(16) == null ? "(null)" : rs.getString(16));
						
					} else {
						tempUedfFile.setIccid(rs.getString(13) == null ? "(null)" : rs.getString(13));
						tempUedfFile.setErrorDescription(rs.getString(14) == null ? "(null)" : rs.getString(14));
						tempUedfFile.setComment(rs.getString(15) == null ? "(null)" : rs.getString(15));
					}
				}*/

				uedfFile.add(tempUedfFile);
			}
			
			conn.close();
			
		} catch (ClassNotFoundException e) {
			System.out.println("Driver error: " + e.getMessage());
			
		} catch (SQLException e1) {
			System.out.println("SQL error: " + e1.getMessage());
			
		}

		return uedfFile;
		
	}

	
}
