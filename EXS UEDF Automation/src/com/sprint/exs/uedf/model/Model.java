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

import javax.swing.JOptionPane;

public class Model {
	
	public Map<String, List<UedfErrors>> executeErrorQuery(String query) {
		UedfErrors tempUedfErrors;
		Map<String, List<UedfErrors>> uedfErrors = new HashMap<>();
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = 
					DriverManager.getConnection("jdbc:oracle:thin:@poc0368.corp.sprint.com:1523/EDFP101.CORP.SPRINT.COM", "edf_query", "edf_query");
			
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
			JOptionPane.showMessageDialog(null, "Driver error: " + e.getMessage());
			//System.out.println("Driver error: " + e.getMessage());
			
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "SQL error: " + e1.getMessage());
			//System.out.println("SQL error: " + e1.getMessage());
			
		}
		

		return uedfErrors;

	}
	
	
	public List<UedfFile> executeEdfSerialQueries(String filename, String query) {
		UedfFile tempUedfFile;
		List<UedfFile> uedfFile = new ArrayList<>();
		String serialType = filename.split("-")[1];
		

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");		
			Connection conn = 
					DriverManager.getConnection("jdbc:oracle:thin:@poc0368.corp.sprint.com:1523/EDFP101.CORP.SPRINT.COM", "edf_query", "edf_query");
			
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, filename.split("-")[0]);
			//System.out.println(filename.split("-")[0]);
			
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
				// if P P3 P5 K3 A B E G H H3 H5 K4 L L1 L3 L4 M3 P4 R R2 T V Y
				
				if (serialType.equalsIgnoreCase("p") || serialType.equalsIgnoreCase("p3")
						|| serialType.equalsIgnoreCase("p5") || serialType.equalsIgnoreCase("k3")
						|| serialType.equalsIgnoreCase("a") || serialType.equalsIgnoreCase("b")
						|| serialType.equalsIgnoreCase("e") || serialType.equalsIgnoreCase("g")
						|| serialType.equalsIgnoreCase("h") || serialType.equalsIgnoreCase("h3")
						|| serialType.equalsIgnoreCase("h5") || serialType.equalsIgnoreCase("k4")
						|| serialType.equalsIgnoreCase("l") || serialType.equalsIgnoreCase("l1")
						|| serialType.equalsIgnoreCase("l3") || serialType.equalsIgnoreCase("l4")
						|| serialType.equalsIgnoreCase("m3") || serialType.equalsIgnoreCase("r")
						|| serialType.equalsIgnoreCase("r2") || serialType.equalsIgnoreCase("t")
						|| serialType.equalsIgnoreCase("v") || serialType.equalsIgnoreCase("y")
						|| serialType.equalsIgnoreCase("p4")) {
					
					tempUedfFile.setSoftwareVersionNo(rs.getString(8) == null ? "(null)" : rs.getString(8)); 
					
				}
				
				
				// P, P3, P5, K3 E H H5 K4 L L1 L3 L4 B M3 P4 R T V Y
				if (serialType.equalsIgnoreCase("p") || serialType.equalsIgnoreCase("p3")
						|| serialType.equalsIgnoreCase("p5") || serialType.equalsIgnoreCase("k3")
						|| serialType.equalsIgnoreCase("e") || serialType.equalsIgnoreCase("h")
						|| serialType.equalsIgnoreCase("h5") || serialType.equalsIgnoreCase("k4")
						|| serialType.equalsIgnoreCase("l") || serialType.equalsIgnoreCase("l1")
						|| serialType.equalsIgnoreCase("l3") || serialType.equalsIgnoreCase("l4")
						|| serialType.equalsIgnoreCase("b") || serialType.equalsIgnoreCase("m3")
						|| serialType.equalsIgnoreCase("p4") || serialType.equalsIgnoreCase("l4")
						|| serialType.equalsIgnoreCase("m3") || serialType.equalsIgnoreCase("r")
						|| serialType.equalsIgnoreCase("t") || serialType.equalsIgnoreCase("v")
						|| serialType.equalsIgnoreCase("y")) {
					
					tempUedfFile.setLockCode1(rs.getString(9) == null ? "(null)" : rs.getString(9));
					tempUedfFile.setLockCode2(rs.getString(10) == null ? "(null)" : rs.getString(10));
				} 
				
				if (serialType.equalsIgnoreCase("a")) {
					tempUedfFile.setMacID(rs.getString(9) == null ? "(null)" : rs.getString(9));
					tempUedfFile.setSharedSecretCode(rs.getString(10) == null ? "(null)" : rs.getString(10));
					tempUedfFile.setErrorDescription(rs.getString(11) == null ? "(null)" : rs.getString(11));
					tempUedfFile.setComment(rs.getString(12) == null ? "(null)" : rs.getString(12));
					
				}
				
				if (serialType.equalsIgnoreCase("b") || serialType.equalsIgnoreCase("l")
						|| serialType.equalsIgnoreCase("r") || serialType.equalsIgnoreCase("t")) {
					tempUedfFile.setImsi(rs.getString(11) == null ? "(null)" : rs.getString(11));
					tempUedfFile.setAuthKey(rs.getString(12) == null ? "(null)" : rs.getString(12));
					tempUedfFile.setAuthKeyChecksum(rs.getString(13) == null ? "(null)" : rs.getString(13));
					tempUedfFile.setMeidHex(rs.getString(14) == null ? "(null)" : rs.getString(14));
					tempUedfFile.setMeidDec(rs.getString(15) == null ? "(null)" : rs.getString(15));
					tempUedfFile.setPrl(rs.getString(16) == null ? "(null)" : rs.getString(16));
					
					if (serialType.equalsIgnoreCase("b")) {
						tempUedfFile.setErrorDescription(rs.getString(17) == null ? "(null)" : rs.getString(17));
						tempUedfFile.setComment(rs.getString(18) == null ? "(null)" : rs.getString(18));
						
					} 
					
					if (serialType.equalsIgnoreCase("l")) {
						tempUedfFile.setCardSKU(rs.getString(17) == null ? "(null)" : rs.getString(17));
						tempUedfFile.setErrorDescription(rs.getString(18) == null ? "(null)" : rs.getString(18));
						tempUedfFile.setComment(rs.getString(19) == null ? "(null)" : rs.getString(19));
					
					}
					
					if (serialType.equalsIgnoreCase("r")) {
						tempUedfFile.setIccid(rs.getString(17) == null ? "(null)" : rs.getString(17));
						tempUedfFile.setTransceiverSKU(rs.getString(18) == null ? "(null)" : rs.getString(18));
						tempUedfFile.setErrorDescription(rs.getString(19) == null ? "(null)" : rs.getString(19));
						tempUedfFile.setComment(rs.getString(20) == null ? "(null)" : rs.getString(20));
					}
					
					if (serialType.equalsIgnoreCase("t")) {
						tempUedfFile.setMacID(rs.getString(17) == null ? "(null)" : rs.getString(17));
						tempUedfFile.setCardSKU(rs.getString(18) == null ? "(null)" : rs.getString(18));
						tempUedfFile.setIccid(rs.getString(19) == null ? "(null)" : rs.getString(19));
						tempUedfFile.setPuc1(rs.getString(20) == null ? "(null)" : rs.getString(20));
						tempUedfFile.setPuc2(rs.getString(21) == null ? "(null)" : rs.getString(21));
						tempUedfFile.setPin1(rs.getString(22) == null ? "(null)" : rs.getString(22));
						tempUedfFile.setPin2(rs.getString(23) == null ? "(null)" : rs.getString(23));
						tempUedfFile.setAdminCode1(rs.getString(24) == null ? "(null)" : rs.getString(24));
						tempUedfFile.setKi(rs.getString(25) == null ? "(null)" : rs.getString(25));
						tempUedfFile.setUiccCardIndicator(rs.getString(26) == null ? "(null)" : rs.getString(26));
						tempUedfFile.setImsiUiccCard(rs.getString(27) == null ? "(null)" : rs.getString(27));
						tempUedfFile.setAccUiccCard(rs.getString(28) == null ? "(null)" : rs.getString(28));
						tempUedfFile.setErrorDescription(rs.getString(29) == null ? "(null)" : rs.getString(29));
						tempUedfFile.setComment(rs.getString(30) == null ? "(null)" : rs.getString(30));
						
					}
					
				}
				
				if (serialType.contains("c") || serialType.equalsIgnoreCase("w")) {
					tempUedfFile.setImsi(rs.getString(8) == null ? "(null)" : rs.getString(8));
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
					
					if (serialType.equalsIgnoreCase("c")) {
						tempUedfFile.setErrorDescription(rs.getString(22) == null ? "(null)" : rs.getString(22));
						tempUedfFile.setComment(rs.getString(23) == null ? "(null)" : rs.getString(23));
					} 
					
					if (!serialType.equalsIgnoreCase("c") && !serialType.equalsIgnoreCase("w"))  {
						tempUedfFile.setEfImpu(rs.getString(22) == null ? "(null)" : rs.getString(22));
						tempUedfFile.setEfImpi(rs.getString(23) == null ? "(null)" : rs.getString(23));
						tempUedfFile.setErrorDescription(rs.getString(24) == null ? "(null)" : rs.getString(24));
						tempUedfFile.setComment(rs.getString(25) == null ? "(null)" : rs.getString(25));
					}
					
					if (serialType.equalsIgnoreCase("w")) {
						tempUedfFile.setManuEncryptKeyIndex(rs.getString(22) == null ? "(null)" : rs.getString(25));
					}
					
				}
				
				if (serialType.equalsIgnoreCase("e")) {
					tempUedfFile.setAuthKey(rs.getString(11) == null ? "(null)" : rs.getString(11));
					tempUedfFile.setAuthKeyChecksum(rs.getString(12) == null ? "(null)" : rs.getString(12));
					tempUedfFile.setMeidHex(rs.getString(13) == null ? "(null)" : rs.getString(13));
					tempUedfFile.setMeidDec(rs.getString(14) == null ? "(null)" : rs.getString(14));
					tempUedfFile.setPrl(rs.getString(15) == null ? "(null)" : rs.getString(15));
					tempUedfFile.setErrorDescription(rs.getString(16) == null ? "(null)" : rs.getString(16));
					tempUedfFile.setComment(rs.getString(17) == null ? "(null)" : rs.getString(17));
				}
				
				if (serialType.equalsIgnoreCase("g")) {
					tempUedfFile.setMacID(rs.getString(9) == null ? "(null)" : rs.getString(9));
					tempUedfFile.setErrorDescription(rs.getString(10) == null ? "(null)" : rs.getString(10));
					tempUedfFile.setComment(rs.getString(11) == null ? "(null)" : rs.getString(11));
				}
				
				if (serialType.contains("h")) {
					if (filename.split("-")[1].equalsIgnoreCase("h") || filename.split("-")[1].equalsIgnoreCase("h5")) {
						tempUedfFile.setMeidHex(rs.getString(11) == null ? "(null)" : rs.getString(11));
						tempUedfFile.setMeidDec(rs.getString(12) == null ? "(null)" : rs.getString(12));
					}
					
					if (serialType.equalsIgnoreCase("h")) {
						tempUedfFile.setErrorDescription(rs.getString(13) == null ? "(null)" : rs.getString(13));
						tempUedfFile.setComment(rs.getString(14) == null ? "(null)" : rs.getString(14));
					}
					
					if (serialType.equalsIgnoreCase("h3")) {
						tempUedfFile.setImeiDec(rs.getString(9) == null ? "(null)" : rs.getString(9));
						tempUedfFile.setErrorDescription(rs.getString(10) == null ? "(null)" : rs.getString(10));
						tempUedfFile.setComment(rs.getString(11) == null ? "(null)" : rs.getString(11));
					}
					
					if (serialType.equalsIgnoreCase("h5")) {
						tempUedfFile.setImeiDec(rs.getString(13) == null ? "(null)" : rs.getString(13));
						tempUedfFile.setImeiDec2(rs.getString(14) == null ? "(null)" : rs.getString(14));
						tempUedfFile.setErrorDescription(rs.getString(15) == null ? "(null)" : rs.getString(15));
						tempUedfFile.setComment(rs.getString(16) == null ? "(null)" : rs.getString(16));
					}
					

				}

				if (serialType.equalsIgnoreCase("k3")) {
					tempUedfFile.setCsnOrEid(rs.getString(11) == null ? "(null)" : rs.getString(11));
					tempUedfFile.setImeiDec(rs.getString(12) == null ? "(null)" : rs.getString(12));
					tempUedfFile.setErrorDescription(rs.getString(13) == null ? "(null)" : rs.getString(13));
					tempUedfFile.setComment(rs.getString(14) == null ? "(null)" : rs.getString(14));
					
				}
				
				if (serialType.equalsIgnoreCase("k4")) {
					tempUedfFile.setMeidHex(rs.getString(11) == null ? "(null)" : rs.getString(11));
					tempUedfFile.setMeidDec(rs.getString(12) == null ? "(null)" : rs.getString(12));
					tempUedfFile.setImeiDec(rs.getString(13) == null ? "(null)" : rs.getString(13));
					tempUedfFile.setErrorDescription(rs.getString(14) == null ? "(null)" : rs.getString(14));
					tempUedfFile.setComment(rs.getString(15) == null ? "(null)" : rs.getString(15));
				}	
				
				if (serialType.contains("l")) {
					if (serialType.equalsIgnoreCase("l1") || serialType.equalsIgnoreCase("l4")) {
						tempUedfFile.setMeidHex(rs.getString(11) == null ? "(null)" : rs.getString(11));
						tempUedfFile.setMeidDec(rs.getString(12) == null ? "(null)" : rs.getString(12));
							
					} 

					if (serialType.equalsIgnoreCase("l3")) {
						tempUedfFile.setImeiDec(rs.getString(11) == null ? "(null)" : rs.getString(11));
						tempUedfFile.setCsnOrEid(rs.getString(12) == null ? "(null)" : rs.getString(12));
							
					}
					
					if (serialType.equalsIgnoreCase("l1") || serialType.equalsIgnoreCase("l3")) {
						tempUedfFile.setErrorDescription(rs.getString(13) == null ? "(null)" : rs.getString(13));
						tempUedfFile.setComment(rs.getString(14) == null ? "(null)" : rs.getString(14));
					
					}
						
					if (serialType.equalsIgnoreCase("l4")) {
						tempUedfFile.setImeiDec(rs.getString(13) == null ? "(null)" : rs.getString(13));
						tempUedfFile.setErrorDescription(rs.getString(14) == null ? "(null)" : rs.getString(14));
						tempUedfFile.setComment(rs.getString(15) == null ? "(null)" : rs.getString(15));
					}

				}
				
				if (serialType.equalsIgnoreCase("m3")) {
					tempUedfFile.setImeiDec(rs.getString(11) == null ? "(null)" : rs.getString(11));
					tempUedfFile.setIccid(rs.getString(12) == null ? "(null)" : rs.getString(12));
					tempUedfFile.setErrorDescription(rs.getString(13) == null ? "(null)" : rs.getString(13));
					tempUedfFile.setComment(rs.getString(14) == null ? "(null)" : rs.getString(14));
					
				}
				
				if (serialType.equalsIgnoreCase("r2")) {
					tempUedfFile.setMacID(rs.getString(9) == null ? "(null)" : rs.getString(9));
					tempUedfFile.setCardSKU(rs.getString(10) == null ? "(null)" : rs.getString(10));
					tempUedfFile.setIccid(rs.getString(11) == null ? "(null)" : rs.getString(11));
					tempUedfFile.setSharedSecretCode(rs.getString(12) == null ? "(null)" : rs.getString(12));
					tempUedfFile.setErrorDescription(rs.getString(13) == null ? "(null)" : rs.getString(13));
					tempUedfFile.setComment(rs.getString(14) == null ? "(null)" : rs.getString(14));
				}
				
				if (serialType.contains("u")) {
					if (serialType.equalsIgnoreCase("u") || serialType.equalsIgnoreCase("u1")
							|| serialType.equalsIgnoreCase("u3")) {
						tempUedfFile.setIccid(rs.getString(8) == null ? "(null)" : rs.getString(8));
						tempUedfFile.setPuc1(rs.getString(9) == null ? "(null)" : rs.getString(9));
						tempUedfFile.setPuc2(rs.getString(10) == null ? "(null)" : rs.getString(10));
						tempUedfFile.setPin1(rs.getString(11) == null ? "(null)" : rs.getString(11));
						tempUedfFile.setPin2(rs.getString(12) == null ? "(null)" : rs.getString(12));
						tempUedfFile.setAdminCode1(rs.getString(13) == null ? "(null)" : rs.getString(13));
						tempUedfFile.setKi(rs.getString(14) == null ? "(null)" : rs.getString(14));
						tempUedfFile.setImsiUiccCard(rs.getString(15) == null ? "(null)" : rs.getString(15));
						tempUedfFile.setAccUiccCard(rs.getString(16) == null ? "(null)" : rs.getString(16));
						
						if (serialType.equalsIgnoreCase("u3")) {
							tempUedfFile.setProfileType(rs.getString(17) == null ? "(null)" : rs.getString(17));
							tempUedfFile.setEfImpu(rs.getString(18) == null ? "(null)" : rs.getString(18));
							tempUedfFile.setEfImpi(rs.getString(19) == null ? "(null)" : rs.getString(19));
							tempUedfFile.setErrorDescription(rs.getString(20) == null ? "(null)" : rs.getString(20));
							tempUedfFile.setComment(rs.getString(21) == null ? "(null)" : rs.getString(21));
							
						} else {
							tempUedfFile.setErrorDescription(rs.getString(17) == null ? "(null)" : rs.getString(17));
							tempUedfFile.setComment(rs.getString(18) == null ? "(null)" : rs.getString(18));
							
						}

					}
					
					if (serialType.equalsIgnoreCase("u2")) {
						tempUedfFile.setPuc1(rs.getString(8) == null ? "(null)" : rs.getString(8));
						tempUedfFile.setPuc2(rs.getString(9) == null ? "(null)" : rs.getString(9));
						tempUedfFile.setPin1(rs.getString(10) == null ? "(null)" : rs.getString(10));
						tempUedfFile.setPin2(rs.getString(11) == null ? "(null)" : rs.getString(11));
						tempUedfFile.setAdminCode1(rs.getString(12) == null ? "(null)" : rs.getString(12));
						tempUedfFile.setKi(rs.getString(13) == null ? "(null)" : rs.getString(13));
						tempUedfFile.setImsiUiccCard(rs.getString(14) == null ? "(null)" : rs.getString(14));
						tempUedfFile.setAccUiccCard(rs.getString(15) == null ? "(null)" : rs.getString(15));
						tempUedfFile.setManuEncryptKeyIndex(rs.getString(16) == null ? "(null)" : rs.getString(16));
						tempUedfFile.setErrorDescription(rs.getString(17) == null ? "(null)" : rs.getString(17));
						tempUedfFile.setComment(rs.getString(18) == null ? "(null)" : rs.getString(18));
						
					}
				}
				
				if (serialType.equalsIgnoreCase("v")) {
					tempUedfFile.setMacID(rs.getString(9) == null ? "(null)" : rs.getString(9));
					tempUedfFile.setSharedSecretCode(rs.getString(10) == null ? "(null)" : rs.getString(10));
					tempUedfFile.setErrorDescription(rs.getString(11) == null ? "(null)" : rs.getString(11));
					tempUedfFile.setComment(rs.getString(12) == null ? "(null)" : rs.getString(12));
				}
				
				if (serialType.equalsIgnoreCase("w1") || serialType.equalsIgnoreCase("w4")) {
					tempUedfFile.setAuthKey(rs.getString(8) == null ? "(null)" : rs.getString(8));
					tempUedfFile.setAuthKeyChecksum(rs.getString(9) == null ? "(null)" : rs.getString(9));
					tempUedfFile.setPrl(rs.getString(10) == null ? "(null)" : rs.getString(10));
					tempUedfFile.setIccid(rs.getString(11) == null ? "(null)" : rs.getString(11));
					tempUedfFile.setPuc1(rs.getString(12) == null ? "(null)" : rs.getString(12));
					tempUedfFile.setPuc2(rs.getString(13) == null ? "(null)" : rs.getString(13));
					tempUedfFile.setPin1(rs.getString(14) == null ? "(null)" : rs.getString(14));
					tempUedfFile.setPin2(rs.getString(15) == null ? "(null)" : rs.getString(15));
					tempUedfFile.setAdminCode1(rs.getString(16) == null ? "(null)" : rs.getString(16));
					tempUedfFile.setKi(rs.getString(17) == null ? "(null)" : rs.getString(17));
					tempUedfFile.setImsiUiccCard(rs.getString(18) == null ? "(null)" : rs.getString(18));
					tempUedfFile.setAccUiccCard(rs.getString(19) == null ? "(null)" : rs.getString(19));
					tempUedfFile.setSfEquipmentID(rs.getString(20) == null ? "(null)" : rs.getString(20));
					tempUedfFile.setProfileType(rs.getString(21) == null ? "(null)" : rs.getString(21));
					
					if (serialType.equalsIgnoreCase("w1")) {
						tempUedfFile.setErrorDescription(rs.getString(22) == null ? "(null)" : rs.getString(22));
						tempUedfFile.setComment(rs.getString(23) == null ? "(null)" : rs.getString(23));
					}
					
					if (serialType.equalsIgnoreCase("w4")) {
						tempUedfFile.setEfImpu(rs.getString(22) == null ? "(null)" : rs.getString(22));
						tempUedfFile.setEfImpi(rs.getString(23) == null ? "(null)" : rs.getString(23));
						tempUedfFile.setErrorDescription(rs.getString(24) == null ? "(null)" : rs.getString(24));
						tempUedfFile.setComment(rs.getString(25) == null ? "(null)" : rs.getString(25));
						
					}
				}
				
				if (serialType.equalsIgnoreCase("w3")) {
					tempUedfFile.setIccid(rs.getString(8) == null ? "(null)" : rs.getString(8));
					tempUedfFile.setPuc1(rs.getString(9) == null ? "(null)" : rs.getString(9));
					tempUedfFile.setPuc2(rs.getString(10) == null ? "(null)" : rs.getString(10));
					tempUedfFile.setPin1(rs.getString(11) == null ? "(null)" : rs.getString(11));
					tempUedfFile.setPin2(rs.getString(12) == null ? "(null)" : rs.getString(12));
					tempUedfFile.setAdminCode1(rs.getString(13) == null ? "(null)" : rs.getString(13));
					tempUedfFile.setKi(rs.getString(14) == null ? "(null)" : rs.getString(14));
					tempUedfFile.setImsiUiccCard(rs.getString(15) == null ? "(null)" : rs.getString(15));
					tempUedfFile.setAccUiccCard(rs.getString(16) == null ? "(null)" : rs.getString(16));
					tempUedfFile.setProfileType(rs.getString(17) == null ? "(null)" : rs.getString(17));
					tempUedfFile.setEfImpu(rs.getString(18) == null ? "(null)" : rs.getString(18));
					tempUedfFile.setEfImpi(rs.getString(19) == null ? "(null)" : rs.getString(19));
					tempUedfFile.setErrorDescription(rs.getString(20) == null ? "(null)" : rs.getString(20));
					tempUedfFile.setComment(rs.getString(21) == null ? "(null)" : rs.getString(21));
					
				}
				
				if (serialType.equalsIgnoreCase("Y")) {
					tempUedfFile.setAuthKey(rs.getString(8) == null ? "(null)" : rs.getString(8));
					tempUedfFile.setAuthKeyChecksum(rs.getString(9) == null ? "(null)" : rs.getString(9));
					tempUedfFile.setIccid(rs.getString(10) == null ? "(null)" : rs.getString(10));
					tempUedfFile.setTransceiverSKU(rs.getString(11) == null ? "(null)" : rs.getString(11));
					tempUedfFile.setErrorDescription(rs.getString(12) == null ? "(null)" : rs.getString(12));
					tempUedfFile.setComment(rs.getString(13) == null ? "(null)" : rs.getString(13));
					
				}
				

				
				// P3, P5, P  P4
				if (serialType.equalsIgnoreCase("p3") || serialType.equalsIgnoreCase("p5")
						|| serialType.equalsIgnoreCase("p") || serialType.equalsIgnoreCase("p4")) {
					tempUedfFile.setMeidHex(rs.getString(11) == null ? "(null)" : rs.getString(11));
					tempUedfFile.setMeidDec(rs.getString(12) == null ? "(null)" : rs.getString(12));
					
					if (serialType.equalsIgnoreCase("p3")) {
						tempUedfFile.setImeiDec(rs.getString(13) == null ? "(null)" : rs.getString(13));
						tempUedfFile.setIccid(rs.getString(14) == null ? "(null)" : rs.getString(14));
						tempUedfFile.setErrorDescription(rs.getString(15) == null ? "(null)" : rs.getString(15));
						tempUedfFile.setComment(rs.getString(16) == null ? "(null)" : rs.getString(16));
						
					} else if (serialType.equalsIgnoreCase("h")) {
						tempUedfFile.setErrorDescription(rs.getString(13) == null ? "(null)" : rs.getString(13));
						tempUedfFile.setComment(rs.getString(14) == null ? "(null)" : rs.getString(14));
						
					} else {
						tempUedfFile.setIccid(rs.getString(13) == null ? "(null)" : rs.getString(13));
						tempUedfFile.setErrorDescription(rs.getString(14) == null ? "(null)" : rs.getString(14));
						tempUedfFile.setComment(rs.getString(15) == null ? "(null)" : rs.getString(15));
					}
				}

				uedfFile.add(tempUedfFile);
			}
			
			conn.close();
			
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Driver error: " + e.getMessage());
			//System.out.println("Driver error: " + e.getMessage());
			
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "SQL error: " + e1.getMessage());
			//System.out.println("SQL error: " + e1.getMessage());
			
		}

		return uedfFile;
		
	}

	
}
