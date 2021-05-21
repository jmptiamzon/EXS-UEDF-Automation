package com.sprint.exs.uedf.model;

public class Queries {
	private final String QUERY_ERRORS = 
			"SELECT DISTINCT " + 
			"HDR.FILE_NAME " + 
			", COUNT(DISTINCT DET.SKU) OVER (PARTITION BY HDR.FILE_NAME, DET.SKU) CNT_SKU " + 
			", CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE " + 
			"ELSE 'Should Be '||SKUM.EDF_PRODUCT_TYPE||' not '||DET.EDF_SERIAL_TYPE " + 
			"END EDF_SERIAL " + 
			", HDR.UNIQUE_SEQUENCE_NUMBER " + 
			", HDR.DEVICE_COUNT " + 
			", COUNT(DET.ERROR_DESCRIPTION)  OVER (PARTITION BY HDR.FILE_NAME) COUNT_ERR_DET " + 
			", HDR.PHONE_TYPE " + 
			", HDR.STATUS " + 
			", HDR.CREATION_DATE " + 
			", HDR.UPDATE_DATE " + 
			//"--, DET.ERROR_DESCRIPTION " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR " + 
			"JOIN EDF.EDF_DETAILS_STG_INT DET " + 
			"ON HDR.UNIQUE_SEQUENCE_NUMBER = DET.UNIQUE_SEQUENCE_NUMBER " + 
			"LEFT JOIN EDF.SKU_MASTER SKUM " + 
			"ON SKUM.SKU_NUMBER = DET.SKU " + 
			"WHERE 1 = 1  " +
			//"AND HDR.FILE_NAME = 'rcasurion_20210416_097512.xml' " +
			"AND DET.ERROR_DESCRIPTION LIKE '%meid or esn or imeiDec doesnot exist in tables%' \r\n" + 
			"AND DET.ERROR_DESCRIPTION is not null\r\n" + 
			//"--AND trunc(HDR.creation_date) >= trunc(sysdate)-1 \r\n" + 
			"AND DET.status = 'ERROR' \r\n" + 
			//"--AND HDR.FILE_NAME = 'rcbrtstr_20210401_113755.xml'\r\n" + 
			"AND NOT EXISTS (SELECT 1 " + 
			"FROM EDF.EDF_INBOUND_HISTORY HIST " + 
			"WHERE 1=1 " + 
			"AND HIST.FILENAME = HDR.FILE_NAME " + 
			"AND STATUS = 'PROCESSED' " + 
			") " + 
			"ORDER BY HDR.CREATION_DATE DESC,FILE_NAME";
	
	private final String QUERY_A =  
			"SELECT " + 
			"HDR.FILE_NAME " + 
			", CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE " + 
			"ELSE 'Should Be '||SKUM.EDF_PRODUCT_TYPE||' not '||DET.EDF_SERIAL_TYPE " + 
			"END EDF_SERIAL " + 
			", DET.MANUFACTURER_ID \"MFG Location ID\" " + 
			", DET.MANUFACTURER_NAME \"MFG Name\" " + 
			", DET.SKU \"Model Number\" " + 
			", DET.SKU_NAME \"Model Name\" " + 
			", DET.EQUIPMENT_TYPE \"Equipment Type (Network Type)\" " + 
			", '12345' \"Software Version Number\" " + 
			", DET.MAC_ID \"MACID\" " + 
			", DET.SHARED_SECRET \"Shared Secret Code\" " + 
			", DET.ERROR_DESCRIPTION " + 
			", CASE WHEN DET.ERROR_DESCRIPTION like '%IMEI and IMEI2 are same%' " + 
			"THEN 'Need to Send Email with the IMEI_DEC to supply the IMEI2_DECIMAL to NMS, If NMS cannot supply send to Magenta Team' " + 
			"WHEN DET.ERROR_DESCRIPTION like '%CSN_OR_EID is a required column it cannot be NULL%' " + 
			"THEN 'Create SR Ticket in Pier 2.0 requesting for CSN_OR_EID of the following IMEI_DEC' " + 
			"ELSE 'IF no Column is Null, OK to create UEDF as NW normally, If there is null column investigate' " + 
			"END AS COMMENT_COL " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR " + 
			"JOIN EDF.EDF_DETAILS_STG_INT DET " + 
			"ON HDR.UNIQUE_SEQUENCE_NUMBER = DET.UNIQUE_SEQUENCE_NUMBER " + 
			"JOIN EDF.SKU_MASTER SKUM " + 
			"ON SKUM.SKU_NUMBER = DET.SKU " + 
			"WHERE 1 =1 " + 
			"and DET.ERROR_DESCRIPTION LIKE '%meid or esn or imeiDec doesnot exist in tables%' " + 
			"AND DET.ERROR_DESCRIPTION is not null " + 
			"AND trunc(HDR.creation_date) >= trunc(sysdate)-1 " + 
			"AND DET.status = 'ERROR' " + 
			"AND SKUM.EDF_PRODUCT_TYPE = 'A' " + 
			"AND HDR.FILE_NAME = ? " + 
			"AND NOT EXISTS (SELECT 1 " + 
			"FROM EDF.EDF_INBOUND_HISTORY HIST " + 
			"WHERE 1=1 " + 
			"AND HIST.FILENAME = HDR.FILE_NAME " + 
			"AND STATUS = 'PROCESSED' " + 
			") " + 
			"AND (HDR.UPDATE_DATE, FILE_NAME) IN (SELECT MAX(UPDATE_DATE), FILE_NAME " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR2 " + 
			"WHERE 1=1 " + 
			"AND HDR2.FILE_NAME = HDR.FILE_NAME " + 
			"AND STATUS = 'ERROR' " + 
			"GROUP BY FILE_NAME " + 
			")" + 
			"ORDER BY HDR.CREATION_DATE DESC, FILE_NAME,IMEI_DECIMAL ";
	
	private final String QUERY_B = 
			"SELECT " + 
			"HDR.FILE_NAME " + 
			", CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE " + 
			"ELSE 'Should Be '||SKUM.EDF_PRODUCT_TYPE||' not '||DET.EDF_SERIAL_TYPE " + 
			"END EDF_SERIAL " + 
			", DET.MANUFACTURER_ID \"MFG Location ID\" " + 
			", DET.MANUFACTURER_NAME \"MFG Name\" " + 
			", DET.SKU \"Model Number\" " + 
			", DET.SKU_NAME \"Model Name\" " + 
			", DET.EQUIPMENT_TYPE \"Equipment Type (Network Type)\" " + 
			", '12345' \"Software Version Number\" " + 
			", NVL(DET.MSL,'000000') \"Subsidy Lock Code 1 (MSL)\" " + 
			", NVL(DET.OTKSL,'000000')  \"Subsidy Lock Code 2 (OTKSL)\" " + 
			", DET.IMSI \"Imsi\" " + 
			", DET.AKEY \"Authentication Key\" " + 
			", DET.AKEY_CHECKSUM \"Authentication Key Checksum\" " + 
			", NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)) \"meidHex\" " + 
			", NVL(DET.MEID_DECIMAL, (LPAD(TO_NUMBER(SUBSTR(NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)),0,8),'XXXXXXXXXX'),10,'0')|| LPAD(TO_NUMBER(SUBSTR(NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)),9,6),'XXXXXXXX'),8,'0')))  \"meidHDec\" " + 
			", DET.PRL \"PRL\" " + 
			", DET.ERROR_DESCRIPTION " + 
			", CASE WHEN DET.ERROR_DESCRIPTION like '%IMEI and IMEI2 are same%' " + 
			"THEN 'Need to Send Email with the IMEI_DEC to supply the IMEI2_DECIMAL to NMS, If NMS cannot supply send to Magenta Team' " + 
			"WHEN DET.ERROR_DESCRIPTION like '%CSN_OR_EID is a required column it cannot be NULL%' " + 
			"THEN 'Create SR Ticket in Pier 2.0 requesting for CSN_OR_EID of the following IMEI_DEC' " + 
			"ELSE 'IF no Column is Null, OK to create UEDF as NW normally, If there is null column investigate' " + 
			"END AS COMMENT_COL " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR " + 
			"JOIN EDF.EDF_DETAILS_STG_INT DET " + 
			"ON HDR.UNIQUE_SEQUENCE_NUMBER = DET.UNIQUE_SEQUENCE_NUMBER " + 
			"LEFT JOIN EDF.SKU_MASTER SKUM " + 
			"ON SKUM.SKU_NUMBER = DET.SKU " + 
			"WHERE 1 =1 " + 
			"AND DET.ERROR_DESCRIPTION LIKE '%meid or esn or imeiDec doesnot exist in tables%' " + 
			"AND DET.ERROR_DESCRIPTION is not null " + 
			"AND DET.status = 'ERROR' " + 
			"AND SKUM.EDF_PRODUCT_TYPE = 'B' " + 
			"AND HDR.FILE_NAME = ? " + 
			"AND NOT EXISTS (SELECT 1 " + 
			"FROM EDF.EDF_INBOUND_HISTORY HIST " + 
			"WHERE 1=1 " + 
			"AND HIST.FILENAME = HDR.FILE_NAME " + 
			"AND STATUS = 'PROCESSED' " + 
			") " + 
			"AND (HDR.UPDATE_DATE, FILE_NAME) IN (SELECT MAX(UPDATE_DATE), FILE_NAME " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR2 " + 
			"WHERE 1=1 " + 
			"AND HDR2.FILE_NAME = HDR.FILE_NAME " + 
			"AND STATUS = 'ERROR' " + 
			"GROUP BY FILE_NAME " + 
			") " + 
			"ORDER BY HDR.CREATION_DATE DESC, FILE_NAME,IMEI_DECIMAL ";
	
	private final String QUERY_C =
			"SELECT " + 
			"HDR.FILE_NAME " + 
			", CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE " + 
			"ELSE 'Should Be '||SKUM.EDF_PRODUCT_TYPE||' not '||DET.EDF_SERIAL_TYPE " + 
			"END EDF_SERIAL " + 
			", DET.MANUFACTURER_ID \"MFG Location ID\" " + 
			", DET.MANUFACTURER_NAME \"MFG Name\" " + 
			", DET.SKU \"Model Number\" " + 
			", DET.SKU_NAME \"Model Name\" " + 
			", DET.EQUIPMENT_TYPE \"Equipment Type (Network Type)\" " + 
			", DET.IMSI \"Imsi\" " + 
			", DET.AKEY \"Authentication Key\" " + 
			", DET.AKEY_CHECKSUM \"Authentication Key Checksum\" " + 
			", DET.PRL \"PRL\" " + 
			", DET.ICCID \"ICCID\" " + 
			", DET.PUC1 \"PUC1\" " + 
			", DET.PUC2 \"PUC2\" " + 
			", DET.PIN1 \"PIN1\" " + 
			", DET.PIN2 \"PIN2\" " + 
			", DET.ADMIN1 \"Admin Code 1\" " + 
			", DET.KI \"Ki\" " + 
			", DET.IMSI_UICC \"IMSI UICC Card\" " + 
			", DET.ACC_UICC \"Acc UICC Card\" " + 
			", DET.SF_EQUIPMENT_ID \"SF Equiment Id\" " + 
			", DET.ERROR_DESCRIPTION " + 
			", CASE WHEN DET.ERROR_DESCRIPTION like '%IMEI and IMEI2 are same%' " + 
			"THEN 'Need to Send Email with the IMEI_DEC to supply the IMEI2_DECIMAL to NMS, If NMS cannot supply send to Magenta Team' " + 
			"WHEN DET.ERROR_DESCRIPTION like '%CSN_OR_EID is a required column it cannot be NULL%' " + 
			"THEN 'Create SR Ticket in Pier 2.0 requesting for CSN_OR_EID of the following IMEI_DEC' " + 
			"ELSE 'IF no Column is Null, OK to create UEDF as NW normally, If there is null column investigate' " + 
			"END AS COMMENT_COL " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR " + 
			"JOIN EDF.EDF_DETAILS_STG_INT DET " + 
			"ON HDR.UNIQUE_SEQUENCE_NUMBER = DET.UNIQUE_SEQUENCE_NUMBER " + 
			"LEFT JOIN EDF.SKU_MASTER SKUM " + 
			"ON SKUM.SKU_NUMBER = DET.SKU " + 
			"WHERE 1 =1 " + 
			"AND DET.ERROR_DESCRIPTION LIKE '%meid or esn or imeiDec doesnot exist in tables%' " + 
			"AND DET.ERROR_DESCRIPTION is not null " + 
			"AND DET.status = 'ERROR' " + 
			"AND SKUM.EDF_PRODUCT_TYPE = 'C' " + 
			"AND HDR.FILE_NAME = ? " + 
			"AND NOT EXISTS (SELECT 1 " + 
			"FROM EDF.EDF_INBOUND_HISTORY HIST " + 
			"WHERE 1=1 " + 
			"AND HIST.FILENAME = HDR.FILE_NAME " + 
			"AND STATUS = 'PROCESSED' " + 
			") " + 
			"AND (HDR.UPDATE_DATE, FILE_NAME) IN (SELECT MAX(UPDATE_DATE), FILE_NAME " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR2 " + 
			"WHERE 1=1 " + 
			"AND HDR2.FILE_NAME = HDR.FILE_NAME " + 
			"AND STATUS = 'ERROR' " + 
			"GROUP BY FILE_NAME " + 
			") " + 
			"ORDER BY HDR.CREATION_DATE DESC, FILE_NAME,IMEI_DECIMAL ";
	//REMOVED IMSI C1 - C4 because not needed
	private final String QUERY_C1 =
			"SELECT " + 
			"HDR.FILE_NAME " + 
			", CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE " + 
			"ELSE 'Should Be '||SKUM.EDF_PRODUCT_TYPE||' not '||DET.EDF_SERIAL_TYPE " + 
			"END EDF_SERIAL " + 
			", DET.MANUFACTURER_ID \"MFG Location ID\" " + 
			", DET.MANUFACTURER_NAME \"MFG Name\" " + 
			", DET.SKU \"Model Number\" " + 
			", DET.SKU_NAME \"Model Name\" " + 
			", DET.EQUIPMENT_TYPE \"Equipment Type (Network Type)\" " + 
			", DET.IMSI \"Imsi\" " + 
			", DET.AKEY \"Authentication Key\" " + 
			", DET.AKEY_CHECKSUM \"Authentication Key Checksum\" " + 
			", DET.PRL \"PRL\" " + 
			", DET.ICCID \"ICCID\" " + 
			", DET.PUC1 \"PUC1\" " + 
			", DET.PUC2 \"PUC2\" " + 
			", DET.PIN1 \"PIN1\" " + 
			", DET.PIN2 \"PIN2\" " + 
			", DET.ADMIN1 \"Admin Code 1\" " + 
			", DET.KI \"Ki\" " + 
			", DET.IMSI_UICC \"IMSI UICC Card\" " + 
			", DET.ACC_UICC \"Acc UICC Card\" " + 
			", DET.SF_EQUIPMENT_ID \"SF Equiment Id\" " + 
			", DET.ERROR_DESCRIPTION " + 
			", CASE WHEN DET.ERROR_DESCRIPTION like '%IMEI and IMEI2 are same%' " + 
			"THEN 'Need to Send Email with the IMEI_DEC to supply the IMEI2_DECIMAL to NMS, If NMS cannot supply send to Magenta Team' " + 
			"WHEN DET.ERROR_DESCRIPTION like '%CSN_OR_EID is a required column it cannot be NULL%' " + 
			"THEN 'Create SR Ticket in Pier 2.0 requesting for CSN_OR_EID of the following IMEI_DEC' " + 
			"ELSE 'IF no Column is Null, OK to create UEDF as NW normally, If there is null column investigate' " + 
			"END AS COMMENT_COL " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR " + 
			"JOIN EDF.EDF_DETAILS_STG_INT DET " + 
			"ON HDR.UNIQUE_SEQUENCE_NUMBER = DET.UNIQUE_SEQUENCE_NUMBER " + 
			"LEFT JOIN EDF.SKU_MASTER SKUM " + 
			"ON SKUM.SKU_NUMBER = DET.SKU " + 
			"WHERE 1 =1 " + 
			"AND DET.ERROR_DESCRIPTION LIKE '%meid or esn or imeiDec doesnot exist in tables%' " + 
			"AND DET.ERROR_DESCRIPTION is not null " + 
			"AND DET.status = 'ERROR' " + 
			"AND SKUM.EDF_PRODUCT_TYPE = 'C1' " + 
			"AND HDR.FILE_NAME = ? " + 
			"AND NOT EXISTS (SELECT 1 " + 
			"FROM EDF.EDF_INBOUND_HISTORY HIST " + 
			"WHERE 1=1 " + 
			"AND HIST.FILENAME = HDR.FILE_NAME " + 
			"AND STATUS = 'PROCESSED' " + 
			") " + 
			"AND (HDR.UPDATE_DATE, FILE_NAME) IN (SELECT MAX(UPDATE_DATE), FILE_NAME " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR2 " + 
			"WHERE 1=1 " + 
			"AND HDR2.FILE_NAME = HDR.FILE_NAME " + 
			"AND STATUS = 'ERROR' " + 
			"GROUP BY FILE_NAME " + 
			") " + 
			"ORDER BY HDR.CREATION_DATE DESC, FILE_NAME,IMEI_DECIMAL ";
	
	private final String QUERY_C2 = 
			"SELECT " + 
			"HDR.FILE_NAME " + 
			", CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE " + 
			"ELSE 'Should Be '||SKUM.EDF_PRODUCT_TYPE||' not '||DET.EDF_SERIAL_TYPE " + 
			"  END EDF_SERIAL " + 
			", DET.MANUFACTURER_ID \"MFG Location ID\" " + 
			", DET.MANUFACTURER_NAME \"MFG Name\" " + 
			", DET.SKU \"Model Number\" " + 
			", DET.SKU_NAME \"Model Name\" " + 
			", DET.EQUIPMENT_TYPE \"Equipment Type (Network Type)\" " + 
			", DET.IMSI \"Imsi\" " + 
			", DET.AKEY \"Authentication Key\" " + 
			", DET.AKEY_CHECKSUM \"Authentication Key Checksum\" " + 
			", DET.PRL \"PRL\" " + 
			", DET.ICCID \"ICCID\" " + 
			", DET.PUC1 \"PUC1\" " + 
			", DET.PUC2 \"PUC2\" " + 
			", DET.PIN1 \"PIN1\" " + 
			", DET.PIN2 \"PIN2\" " + 
			", DET.ADMIN1 \"Admin Code 1\" " + 
			", DET.KI \"Ki\" " + 
			", DET.IMSI_UICC \"IMSI UICC Card\" " + 
			", DET.ACC_UICC \"Acc UICC Card\" " + 
			", DET.SF_EQUIPMENT_ID \"SF Equiment Id\" " + 
			", DET.EF_IMPU \"EF_IMPU\" " + 
			", DET.EF_IMPI \"EF_IMPI\" " + 
			", DET.ERROR_DESCRIPTION " + 
			", CASE WHEN DET.ERROR_DESCRIPTION like '%IMEI and IMEI2 are same%' " + 
			"THEN 'Need to Send Email with the IMEI_DEC to supply the IMEI2_DECIMAL to NMS, If NMS cannot supply send to Magenta Team' " + 
			"WHEN DET.ERROR_DESCRIPTION like '%CSN_OR_EID is a required column it cannot be NULL%' " + 
			"THEN 'Create SR Ticket in Pier 2.0 requesting for CSN_OR_EID of the following IMEI_DEC' " + 
			"ELSE 'IF no Column is Null, OK to create UEDF as NW normally, If there is null column investigate' " + 
			"END AS COMMENT_COL " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR " + 
			"JOIN EDF.EDF_DETAILS_STG_INT DET " + 
			"ON HDR.UNIQUE_SEQUENCE_NUMBER = DET.UNIQUE_SEQUENCE_NUMBER " + 
			"LEFT JOIN EDF.SKU_MASTER SKUM " + 
			"ON SKUM.SKU_NUMBER = DET.SKU " + 
			"WHERE 1 =1 " + 
			"AND DET.ERROR_DESCRIPTION LIKE '%meid or esn or imeiDec doesnot exist in tables%' " + 
			"AND DET.ERROR_DESCRIPTION is not null " + 
			"AND DET.status = 'ERROR' " + 
			"AND SKUM.EDF_PRODUCT_TYPE = 'C2' " + 
			"AND HDR.FILE_NAME = ? " + 
			"AND NOT EXISTS (SELECT 1 " + 
			"FROM EDF.EDF_INBOUND_HISTORY HIST " + 
			"WHERE 1=1 " + 
			"AND HIST.FILENAME = HDR.FILE_NAME " + 
			"AND STATUS = 'PROCESSED' " + 
			") " + 
			"AND (HDR.UPDATE_DATE, FILE_NAME) IN (SELECT MAX(UPDATE_DATE), FILE_NAME\r\n" + 
			"FROM EDF.EDF_HEADER_STG_INT HDR2 " + 
			"WHERE 1=1 " + 
			"AND HDR2.FILE_NAME = HDR.FILE_NAME " + 
			"AND STATUS = 'ERROR' " + 
			"GROUP BY FILE_NAME " + 
			") " + 
			"ORDER BY HDR.CREATION_DATE DESC, FILE_NAME,IMEI_DECIMAL ";
	
	private final String QUERY_C4 = 
			"SELECT " + 
			"HDR.FILE_NAME " + 
			", CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE " + 
			"ELSE 'Should Be '||SKUM.EDF_PRODUCT_TYPE||' not '||DET.EDF_SERIAL_TYPE " + 
			"END EDF_SERIAL " + 
			", DET.MANUFACTURER_ID \"MFG Location ID\" " + 
			", DET.MANUFACTURER_NAME \"MFG Name\" " + 
			", DET.SKU \"Model Number\" " + 
			", DET.SKU_NAME \"Model Name\" " + 
			", DET.EQUIPMENT_TYPE \"Equipment Type (Network Type)\" " + 
			", DET.IMSI \"Imsi\" " + 
			", DET.AKEY \"Authentication Key\" " + 
			", DET.AKEY_CHECKSUM \"Authentication Key Checksum\" " + 
			", DET.PRL \"PRL\" " + 
			", DET.ICCID \"ICCID\" " + 
			", DET.PUC1 \"PUC1\" " + 
			", DET.PUC2 \"PUC2\" " + 
			", DET.PIN1 \"PIN1\" " + 
			", DET.PIN2 \"PIN2\" " + 
			", DET.ADMIN1 \"Admin Code 1\" " + 
			", DET.KI \"Ki\" " + 
			", DET.IMSI_UICC \"IMSI UICC Card\" " + 
			", DET.ACC_UICC \"Acc UICC Card\" " + 
			", DET.SF_EQUIPMENT_ID \"SF Equiment Id\" " + 
			", DET.EF_IMPU \"EF_IMPU\" " + 
			", DET.EF_IMPI \"EF_IMPI\" " + 
			", DET.ERROR_DESCRIPTION " + 
			", CASE WHEN DET.ERROR_DESCRIPTION like '%IMEI and IMEI2 are same%' " + 
			"THEN 'Need to Send Email with the IMEI_DEC to supply the IMEI2_DECIMAL to NMS, If NMS cannot supply send to Magenta Team' " + 
			"WHEN DET.ERROR_DESCRIPTION like '%CSN_OR_EID is a required column it cannot be NULL%' " + 
			"THEN 'Create SR Ticket in Pier 2.0 requesting for CSN_OR_EID of the following IMEI_DEC' " + 
			"ELSE 'IF no Column is Null, OK to create UEDF as NW normally, If there is null column investigate' " + 
			"END AS COMMENT_COL " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR " + 
			"JOIN EDF.EDF_DETAILS_STG_INT DET " + 
			"ON HDR.UNIQUE_SEQUENCE_NUMBER = DET.UNIQUE_SEQUENCE_NUMBER " + 
			"LEFT JOIN EDF.SKU_MASTER SKUM " + 
			"ON SKUM.SKU_NUMBER = DET.SKU " + 
			"WHERE 1 =1 " + 
			"AND DET.ERROR_DESCRIPTION LIKE '%meid or esn or imeiDec doesnot exist in tables%' " + 
			"AND DET.ERROR_DESCRIPTION is not null " + 
			"AND DET.status = 'ERROR' " + 
			"AND SKUM.EDF_PRODUCT_TYPE = 'C4' " + 
			"AND HDR.FILE_NAME = ? " + 
			"AND NOT EXISTS (SELECT 1 " + 
			"FROM EDF.EDF_INBOUND_HISTORY HIST " + 
			"WHERE 1=1 " + 
			"AND HIST.FILENAME = HDR.FILE_NAME " + 
			"AND STATUS = 'PROCESSED' " + 
			") " + 
			"AND (HDR.UPDATE_DATE, FILE_NAME) IN (SELECT MAX(UPDATE_DATE), FILE_NAME " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR2 " + 
			"WHERE 1=1 " + 
			"AND HDR2.FILE_NAME = HDR.FILE_NAME " + 
			"AND STATUS = 'ERROR' " + 
			"GROUP BY FILE_NAME " + 
			") " + 
			"ORDER BY HDR.CREATION_DATE DESC, FILE_NAME,IMEI_DECIMAL ";
	
	private final String QUERY_C5 =
			"SELECT " + 
			"HDR.FILE_NAME " + 
			", CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE " + 
			"ELSE 'Should Be '||SKUM.EDF_PRODUCT_TYPE||' not '||DET.EDF_SERIAL_TYPE " + 
			"END EDF_SERIAL " + 
			", DET.MANUFACTURER_ID \"MFG Location ID\" " + 
			", DET.MANUFACTURER_NAME \"MFG Name\" " + 
			", DET.SKU \"Model Number\" " + 
			", DET.SKU_NAME \"Model Name\" " + 
			", DET.EQUIPMENT_TYPE \"Equipment Type (Network Type)\" " +
			", DET.IMSI \"Imsi\" " + 
			", DET.AKEY \"Authentication Key\" " + 
			", DET.AKEY_CHECKSUM \"Authentication Key Checksum\" " + 
			", DET.PRL \"PRL\" " + 
			", DET.ICCID \"ICCID\" " + 
			", DET.PUC1 \"PUC1\" " + 
			", DET.PUC2 \"PUC2\" " + 
			", DET.PIN1 \"PIN1\" " + 
			", DET.PIN2 \"PIN2\" " + 
			", DET.ADMIN1 \"Admin Code 1\" " + 
			", DET.KI \"Ki\" " + 
			", DET.IMSI_UICC \"IMSI UICC Card\" " + 
			", DET.ACC_UICC \"Acc UICC Card\" " + 
			", DET.SF_EQUIPMENT_ID \"SF Equiment Id\" " + 
			", DET.EF_IMPU \"EF_IMPU\" " + 
			", DET.EF_IMPI \"EF_IMPI\" " + 
			", DET.ERROR_DESCRIPTION " + 
			", CASE WHEN DET.ERROR_DESCRIPTION like '%IMEI and IMEI2 are same%' " + 
			"THEN 'Need to Send Email with the IMEI_DEC to supply the IMEI2_DECIMAL to NMS, If NMS cannot supply send to Magenta Team' " + 
			"WHEN DET.ERROR_DESCRIPTION like '%CSN_OR_EID is a required column it cannot be NULL%' " + 
			"THEN 'Create SR Ticket in Pier 2.0 requesting for CSN_OR_EID of the following IMEI_DEC' " + 
			"ELSE 'IF no Column is Null, OK to create UEDF as NW normally, If there is null column investigate' " + 
			"END AS COMMENT_COL " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR " + 
			"JOIN EDF.EDF_DETAILS_STG_INT DET " + 
			"ON HDR.UNIQUE_SEQUENCE_NUMBER = DET.UNIQUE_SEQUENCE_NUMBER " + 
			"LEFT JOIN EDF.SKU_MASTER SKUM " + 
			"ON SKUM.SKU_NUMBER = DET.SKU " + 
			"WHERE 1 =1 " + 
			"AND DET.ERROR_DESCRIPTION LIKE '%meid or esn or imeiDec doesnot exist in tables%' " + 
			"AND DET.ERROR_DESCRIPTION is not null " + 
			"AND DET.status = 'ERROR' " + 
			"AND SKUM.EDF_PRODUCT_TYPE = 'C5' " + 
			"AND HDR.FILE_NAME = ? " + 
			"AND NOT EXISTS (SELECT 1 " + 
			"FROM EDF.EDF_INBOUND_HISTORY HIST " + 
			"WHERE 1=1 " + 
			"AND HIST.FILENAME = HDR.FILE_NAME " + 
			"AND STATUS = 'PROCESSED' " + 
			") " + 
			"AND (HDR.UPDATE_DATE, FILE_NAME) IN (SELECT MAX(UPDATE_DATE), FILE_NAME " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR2 " + 
			"WHERE 1=1 " + 
			"AND HDR2.FILE_NAME = HDR.FILE_NAME " + 
			"AND STATUS = 'ERROR' " + 
			"GROUP BY FILE_NAME " + 
			") " + 
			"ORDER BY HDR.CREATION_DATE DESC, FILE_NAME,IMEI_DECIMAL ";
	
	private final String QUERY_E = 
			"SELECT " + 
			"HDR.FILE_NAME " + 
			", CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE " + 
			"ELSE 'Should Be '||SKUM.EDF_PRODUCT_TYPE||' not '||DET.EDF_SERIAL_TYPE " + 
			"END EDF_SERIAL " + 
			", DET.MANUFACTURER_ID \"MFG Location ID\" " + 
			", DET.MANUFACTURER_NAME \"MFG Name\" " + 
			", DET.SKU \"Model Number\" " + 
			", DET.SKU_NAME \"Model Name\" " + 
			", DET.EQUIPMENT_TYPE \"Equipment Type (Network Type)\" " + 
			", '12345' \"Software Version Number\" " + 
			", NVL(DET.MSL,'000000') \"Subsidy Lock Code 1 (MSL)\" " + 
			", NVL(DET.OTKSL,'000000')  \"Subsidy Lock Code 2 (OTKSL)\" " + 
			", DET.AKEY \"Authentication Key\" " + 
			", DET.AKEY_CHECKSUM \"Authentication Key Checksum\" " + 
			", NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)) \"meidHex\" " + 
			", NVL(DET.MEID_DECIMAL, (LPAD(TO_NUMBER(SUBSTR(NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)),0,8),'XXXXXXXXXX'),10,'0')|| LPAD(TO_NUMBER(SUBSTR(NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)),9,6),'XXXXXXXX'),8,'0')))  \"meidHDec\" " + 
			", DET.PRL \"PRL\" " + 
			", DET.ERROR_DESCRIPTION " + 
			", CASE WHEN DET.ERROR_DESCRIPTION like '%IMEI and IMEI2 are same%' " + 
			"THEN 'Need to Send Email with the IMEI_DEC to supply the IMEI2_DECIMAL to NMS, If NMS cannot supply send to Magenta Team' " + 
			"WHEN DET.ERROR_DESCRIPTION like '%CSN_OR_EID is a required column it cannot be NULL%' " + 
			"THEN 'Create SR Ticket in Pier 2.0 requesting for CSN_OR_EID of the following IMEI_DEC' " + 
			"ELSE 'IF no Column is Null, OK to create UEDF as NW normally, If there is null column investigate' " + 
			"END AS COMMENT_COL " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR " + 
			"JOIN EDF.EDF_DETAILS_STG_INT DET \r\n" + 
			"ON HDR.UNIQUE_SEQUENCE_NUMBER = DET.UNIQUE_SEQUENCE_NUMBER " + 
			"LEFT JOIN EDF.SKU_MASTER SKUM " + 
			"ON SKUM.SKU_NUMBER = DET.SKU " + 
			"WHERE 1 =1 " + 
			"AND DET.ERROR_DESCRIPTION LIKE '%meid or esn or imeiDec doesnot exist in tables%' " + 
			"AND DET.ERROR_DESCRIPTION is not null " + 
			"AND DET.status = 'ERROR' " + 
			"AND SKUM.EDF_PRODUCT_TYPE = 'E' " + 
			"AND HDR.FILE_NAME = ? " + 
			"AND NOT EXISTS (SELECT 1 " + 
			"FROM EDF.EDF_INBOUND_HISTORY HIST " + 
			"WHERE 1=1 " + 
			"AND HIST.FILENAME = HDR.FILE_NAME " + 
			"AND STATUS = 'PROCESSED' " + 
			") " + 
			"AND (HDR.UPDATE_DATE, FILE_NAME) IN (SELECT MAX(UPDATE_DATE), FILE_NAME " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR2 " + 
			"WHERE 1=1 " + 
			"AND HDR2.FILE_NAME = HDR.FILE_NAME " + 
			"AND STATUS = 'ERROR' " + 
			"GROUP BY FILE_NAME " + 
			") " + 
			"ORDER BY HDR.CREATION_DATE DESC, FILE_NAME,IMEI_DECIMAL ";
	
	private final String QUERY_G = 
			"SELECT " + 
			"HDR.FILE_NAME\r\n" + 
			", CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE " + 
			"ELSE 'Should Be '||SKUM.EDF_PRODUCT_TYPE||' not '||DET.EDF_SERIAL_TYPE " + 
			"END EDF_SERIAL " + 
			", DET.MANUFACTURER_ID \"MFG Location ID\" " + 
			", DET.MANUFACTURER_NAME \"MFG Name\" " + 
			", DET.SKU \"Model Number\" " + 
			", DET.SKU_NAME \"Model Name\" " + 
			", DET.EQUIPMENT_TYPE \"Equipment Type (Network Type)\" " + 
			", '12345' \"Software Version Number\" " + 
			", DET.MAC_ID \"MACID\" " + 
			", DET.ERROR_DESCRIPTION " + 
			", CASE WHEN DET.ERROR_DESCRIPTION like '%IMEI and IMEI2 are same%' " + 
			"THEN 'Need to Send Email with the IMEI_DEC to supply the IMEI2_DECIMAL to NMS, If NMS cannot supply send to Magenta Team' " + 
			"WHEN DET.ERROR_DESCRIPTION like '%CSN_OR_EID is a required column it cannot be NULL%' " + 
			"THEN 'Create SR Ticket in Pier 2.0 requesting for CSN_OR_EID of the following IMEI_DEC' " + 
			"ELSE 'IF no Column is Null, OK to create UEDF as NW normally, If there is null column investigate' " + 
			"END AS COMMENT_COL " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR " + 
			"JOIN EDF.EDF_DETAILS_STG_INT DET " + 
			"ON HDR.UNIQUE_SEQUENCE_NUMBER = DET.UNIQUE_SEQUENCE_NUMBER " + 
			"JOIN EDF.SKU_MASTER SKUM " + 
			"ON SKUM.SKU_NUMBER = DET.SKU " + 
			"WHERE 1 =1 " + 
			"and DET.ERROR_DESCRIPTION LIKE '%meid or esn or imeiDec doesnot exist in tables%' " + 
			"AND DET.ERROR_DESCRIPTION is not null " +  
			"AND DET.status = 'ERROR' " + 
			"AND SKUM.EDF_PRODUCT_TYPE = 'G' " + 
			"AND HDR.FILE_NAME = ? " + 
			"AND NOT EXISTS (SELECT 1 " + 
			"FROM EDF.EDF_INBOUND_HISTORY HIST " + 
			"WHERE 1=1 " + 
			"AND HIST.FILENAME = HDR.FILE_NAME " + 
			"AND STATUS = 'PROCESSED' " + 
			") " + 
			"AND (HDR.UPDATE_DATE, FILE_NAME) IN (SELECT MAX(UPDATE_DATE), FILE_NAME " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR2 " + 
			"WHERE 1=1 " + 
			"AND HDR2.FILE_NAME = HDR.FILE_NAME " + 
			"AND STATUS = 'ERROR' " + 
			"GROUP BY FILE_NAME " + 
			") " + 
			"ORDER BY HDR.CREATION_DATE DESC, FILE_NAME,IMEI_DECIMAL ";
	
	private final String QUERY_H = 
			"SELECT " + 
			"HDR.FILE_NAME " + 
			", CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE " + 
			"ELSE 'Should Be '||SKUM.EDF_PRODUCT_TYPE||' not '||DET.EDF_SERIAL_TYPE " + 
			"END EDF_SERIAL " + 
			", DET.MANUFACTURER_ID \"MFG Location ID\" " + 
			", DET.MANUFACTURER_NAME \"MFG Name\" " + 
			", DET.SKU \"Model Number\" " + 
			", DET.SKU_NAME \"Model Name\" " + 
			", DET.EQUIPMENT_TYPE \"Equipment Type (Network Type)\" " + 
			", '12345' \"Software Version Number\" " + 
			", NVL(DET.MSL,'000000') \"Subsidy Lock Code 1 (MSL)\" " + 
			", NVL(DET.OTKSL,'000000')  \"Subsidy Lock Code 2 (OTKSL)\" " + 
			", NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)) \"meidHex\" " + 
			", NVL(DET.MEID_DECIMAL, (LPAD(TO_NUMBER(SUBSTR(NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)),0,8),'XXXXXXXXXX'),10,'0')|| LPAD(TO_NUMBER(SUBSTR(NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)),9,6),'XXXXXXXX'),8,'0')))  \"meidHDec\" " + 
			", DET.ERROR_DESCRIPTION " + 
			", CASE WHEN DET.ERROR_DESCRIPTION like '%IMEI and IMEI2 are same%' " + 
			"THEN 'Need to Send Email with the IMEI_DEC to supply the IMEI2_DECIMAL to NMS, If NMS cannot supply send to Magenta Team' " + 
			"WHEN DET.ERROR_DESCRIPTION like '%ICCID is a required column it cannot be NULL%' " + 
			"THEN 'Create SR Ticket in Pier 2.0 requesting for ICCID of the following IMEI_DEC' " + 
			"ELSE 'IF no Column is Null, OK to create UEDF as NW normally, If there is null column investigate/sent email' " + 
			"END AS COMMENT_COL " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR " + 
			"JOIN EDF.EDF_DETAILS_STG_INT DET " + 
			"ON HDR.UNIQUE_SEQUENCE_NUMBER = DET.UNIQUE_SEQUENCE_NUMBER " + 
			"LEFT JOIN EDF.SKU_MASTER SKUM " + 
			"ON SKUM.SKU_NUMBER = DET.SKU " + 
			"WHERE 1 =1 " + 
			"and DET.ERROR_DESCRIPTION LIKE '%meid or esn or imeiDec doesnot exist in tables%' " + 
			"AND DET.ERROR_DESCRIPTION is not null " + 
			"and DET.status = 'ERROR' " + 
			"AND SKUM.EDF_PRODUCT_TYPE = 'H' " + 
			"AND HDR.FILE_NAME = ? " + 
			"AND NOT EXISTS (SELECT 1 " + 
			"FROM EDF.EDF_INBOUND_HISTORY HIST " + 
			"WHERE 1=1 " + 
			"AND HIST.FILENAME = HDR.FILE_NAME " + 
			"AND STATUS = 'PROCESSED' " + 
			") " + 
			"AND (HDR.UPDATE_DATE, FILE_NAME) IN (SELECT MAX(UPDATE_DATE), FILE_NAME " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR2 " + 
			"WHERE 1=1 " + 
			"AND HDR2.FILE_NAME = HDR.FILE_NAME " + 
			"AND STATUS = 'ERROR' " + 
			"GROUP BY FILE_NAME " + 
			") " + 
			"ORDER BY HDR.CREATION_DATE DESC, FILE_NAME,IMEI_DECIMAL ";
	
	private final String QUERY_H3 = 
			"SELECT " + 
			"HDR.FILE_NAME " + 
			", CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE " + 
			"ELSE 'Should Be '||SKUM.EDF_PRODUCT_TYPE||' not '||DET.EDF_SERIAL_TYPE " + 
			"END EDF_SERIAL " + 
			", DET.MANUFACTURER_ID \"MFG Location ID\" " + 
			", DET.MANUFACTURER_NAME \"MFG Name\" " + 
			", DET.SKU \"Model Number\" " + 
			", DET.SKU_NAME \"Model Name\" " + 
			", DET.EQUIPMENT_TYPE \"Equipment Type (Network Type)\" " + 
			", '12345' \"Software Version Number\" " + 
			", NVL(DET.MSL,'000000') \"Subsidy Lock Code 1 (MSL)\" " + 
			", NVL(DET.OTKSL,'000000')  \"Subsidy Lock Code 2 (OTKSL)\" " + 
			", DET.IMEI_DECIMAL \"imeiDec\" " + 
			", DET.ERROR_DESCRIPTION " + 
			", CASE WHEN DET.ERROR_DESCRIPTION like '%IMEI and IMEI2 are same%' " + 
			"THEN 'Need to Send Email with the IMEI_DEC to supply the IMEI2_DECIMAL to NMS, If NMS cannot supply send to Magenta Team' " + 
			"WHEN DET.ERROR_DESCRIPTION like '%ICCID is a required column it cannot be NULL%' " + 
			"THEN 'Create SR Ticket in Pier 2.0 requesting for ICCID of the following IMEI_DEC' " + 
			"ELSE 'IF no Column is Null, OK to create UEDF as NW normally, If there is null column investigate/sent email' " + 
			"END AS COMMENT_COL " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR " + 
			"JOIN EDF.EDF_DETAILS_STG_INT DET " + 
			"ON HDR.UNIQUE_SEQUENCE_NUMBER = DET.UNIQUE_SEQUENCE_NUMBER " + 
			"LEFT JOIN EDF.SKU_MASTER SKUM " + 
			"ON SKUM.SKU_NUMBER = DET.SKU " + 
			"WHERE 1 =1 " + 
			"and DET.ERROR_DESCRIPTION LIKE '%meid or esn or imeiDec doesnot exist in tables%' " + 
			"AND DET.ERROR_DESCRIPTION is not null " + 
			"and DET.status = 'ERROR' " + 
			"AND SKUM.EDF_PRODUCT_TYPE = 'H3' " + 
			"AND HDR.FILE_NAME = ? " + 
			"AND NOT EXISTS (SELECT 1 " + 
			"FROM EDF.EDF_INBOUND_HISTORY HIST " + 
			"WHERE 1=1 " + 
			"AND HIST.FILENAME = HDR.FILE_NAME " + 
			"AND STATUS = 'PROCESSED' " + 
			") " + 
			"AND (HDR.UPDATE_DATE, FILE_NAME) IN (SELECT MAX(UPDATE_DATE), FILE_NAME " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR2 " + 
			"WHERE 1=1 " + 
			"AND HDR2.FILE_NAME = HDR.FILE_NAME " + 
			"AND STATUS = 'ERROR' " + 
			"GROUP BY FILE_NAME " + 
			") " + 
			"ORDER BY HDR.CREATION_DATE DESC, FILE_NAME,IMEI_DECIMAL";
	
	private final String QUERY_H5 = 
			"SELECT " + 
			"HDR.FILE_NAME " + 
			", CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE " + 
			"ELSE 'Should Be '||SKUM.EDF_PRODUCT_TYPE||' not '||DET.EDF_SERIAL_TYPE " + 
			"END EDF_SERIAL " + 
			", DET.MANUFACTURER_ID \"MFG Location ID\" " + 
			", DET.MANUFACTURER_NAME \"MFG Name\" " + 
			", DET.SKU \"Model Number\" " + 
			", DET.SKU_NAME \"Model Name\" " + 
			", DET.EQUIPMENT_TYPE \"Equipment Type (Network Type)\" " + 
			", '12345' \"Software Version Number\" " + 
			", NVL(DET.MSL,'000000') \"Subsidy Lock Code 1 (MSL)\" " + 
			", NVL(DET.OTKSL,'000000')  \"Subsidy Lock Code 2 (OTKSL)\" " + 
			", NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)) \"meidHex\" " + 
			", NVL(DET.MEID_DECIMAL, (LPAD(TO_NUMBER(SUBSTR(NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)),0,8),'XXXXXXXXXX'),10,'0')|| LPAD(TO_NUMBER(SUBSTR(NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)),9,6),'XXXXXXXX'),8,'0')))  \"meidHDec\" " + 
			", DET.IMEI_DECIMAL \"imeiDec\" " + 
			", DET.IMEI2_DECIMAL \"imeiDec2\" " + 
			", DET.ERROR_DESCRIPTION " + 
			", CASE WHEN DET.ERROR_DESCRIPTION like '%IMEI and IMEI2 are same%' " + 
			"THEN 'Need to Send Email with the IMEI_DEC to supply the IMEI2_DECIMAL to NMS, If NMS cannot supply send to Magenta Team' " + 
			"WHEN DET.ERROR_DESCRIPTION like '%ICCID is a required column it cannot be NULL%' " + 
			"THEN 'Create SR Ticket in Pier 2.0 requesting for ICCID of the following IMEI_DEC' " + 
			"ELSE 'IF no Column is Null, OK to create UEDF as NW normally, If there is null column investigate/sent email' " + 
			"END AS COMMENT_COL " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR " + 
			"JOIN EDF.EDF_DETAILS_STG_INT DET " + 
			"ON HDR.UNIQUE_SEQUENCE_NUMBER = DET.UNIQUE_SEQUENCE_NUMBER " + 
			"LEFT JOIN EDF.SKU_MASTER SKUM " + 
			"ON SKUM.SKU_NUMBER = DET.SKU " + 
			"WHERE 1 =1 " + 
			"and DET.ERROR_DESCRIPTION LIKE '%meid or esn or imeiDec doesnot exist in tables%' " + 
			"AND DET.ERROR_DESCRIPTION is not null " + 
			"and DET.status = 'ERROR' " + 
			"AND SKUM.EDF_PRODUCT_TYPE = 'H5' " + 
			"AND HDR.FILE_NAME = ?" + 
			"AND NOT EXISTS (SELECT 1 " + 
			"FROM EDF.EDF_INBOUND_HISTORY HIST " + 
			"WHERE 1=1 " + 
			"AND HIST.FILENAME = HDR.FILE_NAME " + 
			"AND STATUS = 'PROCESSED' " + 
			") " + 
			"AND (HDR.UPDATE_DATE, FILE_NAME) IN (SELECT MAX(UPDATE_DATE), FILE_NAME " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR2 " + 
			"WHERE 1=1 " + 
			"AND HDR2.FILE_NAME = HDR.FILE_NAME " + 
			"AND STATUS = 'ERROR' " + 
			"GROUP BY FILE_NAME " + 
			") " + 
			"ORDER BY HDR.CREATION_DATE DESC, FILE_NAME,IMEI_DECIMAL ";
	
	private final String QUERY_K3 = 
			"SELECT " + 
			"HDR.FILE_NAME " + 
			", CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE " + 
			"ELSE 'Should Be '||SKUM.EDF_PRODUCT_TYPE||' not '||DET.EDF_SERIAL_TYPE " + 
			"END EDF_SERIAL " + 
			", DET.MANUFACTURER_ID \"MFG Location ID\" " + 
			", DET.MANUFACTURER_NAME \"MFG Name\" " + 
			", DET.SKU \"Model Number\" " + 
			", DET.SKU_NAME \"Model Name\" " + 
			", DET.EQUIPMENT_TYPE \"Equipment Type (Network Type)\" " + 
			", '12345' \"Software Version Number\" " + 
			", NVL(DET.MSL,'000000') \"Subsidy Lock Code 1 (MSL)\" " + 
			", NVL(DET.OTKSL,'000000')  \"Subsidy Lock Code 2 (OTKSL)\" " + 
			", DET.CSN_OR_EID \"CSN_OR_EID\" " + 
			", DET.IMEI_DECIMAL \"imeiDec\" " + 
			", DET.ERROR_DESCRIPTION " + 
			", CASE WHEN DET.ERROR_DESCRIPTION like '%IMEI and IMEI2 are same%' " + 
			"THEN 'Need to Send Email with the IMEI_DEC to supply the IMEI2_DECIMAL to NMS, If NMS cannot supply send to Magenta Team' " + 
			"WHEN DET.ERROR_DESCRIPTION like '%CSN_OR_EID is a required column it cannot be NULL%' " + 
			"THEN 'Create SR Ticket in Pier 2.0 requesting for CSN_OR_EID of the following IMEI_DEC' " + 
			"ELSE 'IF no Column is Null, OK to create UEDF as NW normally, If there is null column investigate' " + 
			"END AS COMMENT_COL " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR " + 
			"JOIN EDF.EDF_DETAILS_STG_INT DET " + 
			"ON HDR.UNIQUE_SEQUENCE_NUMBER = DET.UNIQUE_SEQUENCE_NUMBER " + 
			"LEFT JOIN EDF.SKU_MASTER SKUM " + 
			"ON SKUM.SKU_NUMBER = DET.SKU " + 
			"WHERE 1 =1 " + 
			"and DET.ERROR_DESCRIPTION LIKE '%meid or esn or imeiDec doesnot exist in tables%' " + 
			"AND DET.ERROR_DESCRIPTION is not null " + 
			"and DET.status = 'ERROR' " + 
			"AND CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE " + 
			"ELSE 'K3' " + 
			"END = 'K3' " + 
			"AND HDR.FILE_NAME = ? " + 
			"AND NOT EXISTS (SELECT 1 " + 
			"FROM EDF.EDF_INBOUND_HISTORY HIST " + 
			"WHERE 1=1 " + 
			"AND HIST.FILENAME = HDR.FILE_NAME " + 
			"AND STATUS = 'PROCESSED' " + 
			") " + 
			"AND (HDR.UPDATE_DATE, FILE_NAME) IN (SELECT MAX(UPDATE_DATE), FILE_NAME " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR2 " + 
			"WHERE 1=1 " + 
			"AND HDR2.FILE_NAME = HDR.FILE_NAME " + 
			"AND STATUS = 'ERROR' " + 
			"GROUP BY FILE_NAME " + 
			") " + 
			"ORDER BY HDR.CREATION_DATE DESC, FILE_NAME,IMEI_DECIMAL";
	
	private final String QUERY_K4 = 
			"SELECT " + 
			"HDR.FILE_NAME " + 
			", CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE " + 
			"ELSE 'Should Be '||SKUM.EDF_PRODUCT_TYPE||' not '||DET.EDF_SERIAL_TYPE " + 
			"END EDF_SERIAL " + 
			", DET.MANUFACTURER_ID \"MFG Location ID\" " + 
			", DET.MANUFACTURER_NAME \"MFG Name\" " + 
			", DET.SKU \"Model Number\" " + 
			", DET.SKU_NAME \"Model Name\" " + 
			", DET.EQUIPMENT_TYPE \"Equipment Type (Network Type)\" " + 
			", '12345' \"Software Version Number\" " + 
			", NVL(DET.MSL,'000000') \"Subsidy Lock Code 1 (MSL)\" " + 
			", NVL(DET.OTKSL,'000000')  \"Subsidy Lock Code 2 (OTKSL)\" " + 
			", NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)) \"meidHex\" " + 
			", NVL(DET.MEID_DECIMAL, (LPAD(TO_NUMBER(SUBSTR(NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)),0,8),'XXXXXXXXXX'),10,'0')|| LPAD(TO_NUMBER(SUBSTR(NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)),9,6),'XXXXXXXX'),8,'0')))  \"meidHDec\" " + 
			", DET.IMEI_DECIMAL \"imeiDec\" " + 
			", DET.ERROR_DESCRIPTION " + 
			", CASE WHEN DET.ERROR_DESCRIPTION like '%IMEI and IMEI2 are same%' " + 
			"THEN 'Need to Send Email with the IMEI_DEC to supply the IMEI2_DECIMAL to NMS, If NMS cannot supply send to Magenta Team' " + 
			"WHEN DET.ERROR_DESCRIPTION like '%ICCID is a required column it cannot be NULL%' " + 
			"THEN 'Create SR Ticket in Pier 2.0 requesting for ICCID of the following IMEI_DEC' " + 
			"ELSE 'IF no Column is Null, OK to create UEDF as NW normally, If there is null column investigate/sent email' " + 
			"END AS COMMENT_COL " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR " + 
			"JOIN EDF.EDF_DETAILS_STG_INT DET " + 
			"ON HDR.UNIQUE_SEQUENCE_NUMBER = DET.UNIQUE_SEQUENCE_NUMBER " + 
			"LEFT JOIN EDF.SKU_MASTER SKUM " + 
			"ON SKUM.SKU_NUMBER = DET.SKU " + 
			"WHERE 1 =1 " + 
			"and DET.ERROR_DESCRIPTION LIKE '%meid or esn or imeiDec doesnot exist in tables%' " + 
			"AND DET.ERROR_DESCRIPTION is not null " + 
			"and DET.status = 'ERROR' " + 
			"AND SKUM.EDF_PRODUCT_TYPE = 'K4' " + 
			"AND HDR.FILE_NAME = ? " + 
			"AND NOT EXISTS (SELECT 1 " + 
			"FROM EDF.EDF_INBOUND_HISTORY HIST " + 
			"WHERE 1=1 " + 
			"AND HIST.FILENAME = HDR.FILE_NAME " + 
			"AND STATUS = 'PROCESSED' " + 
			") " + 
			"AND (HDR.UPDATE_DATE, FILE_NAME) IN (SELECT MAX(UPDATE_DATE), FILE_NAME " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR2 " + 
			"WHERE 1=1 " + 
			"AND HDR2.FILE_NAME = HDR.FILE_NAME " + 
			"AND STATUS = 'ERROR' " + 
			"GROUP BY FILE_NAME " + 
			") " + 
			"ORDER BY HDR.CREATION_DATE DESC, FILE_NAME,IMEI_DECIMAL ";
	
	private final String QUERY_L = 
			"SELECT " + 
			"HDR.FILE_NAME " + 
			", CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE " + 
			"ELSE 'Should Be '||SKUM.EDF_PRODUCT_TYPE||' not '||DET.EDF_SERIAL_TYPE " + 
			"END EDF_SERIAL " + 
			", DET.MANUFACTURER_ID \"MFG Location ID\" " + 
			", DET.MANUFACTURER_NAME \"MFG Name\" " + 
			", DET.SKU \"Model Number\" " + 
			", DET.SKU_NAME \"Model Name\" " + 
			", DET.EQUIPMENT_TYPE \"Equipment Type (Network Type)\" " + 
			", '12345' \"Software Version Number\" " +
			", NVL(DET.MSL,'000000') \"Subsidy Lock Code 1 (MSL)\" " + 
			", NVL(DET.OTKSL,'000000')  \"Subsidy Lock Code 2 (OTKSL)\" " +  
			", DET.IMSI \"Imsi\" " + 
			", DET.AKEY \"Authentication Key\" " + 
			", DET.AKEY_CHECKSUM \"Authentication Key Checksum\" " + 
			", NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)) \"meidHex\" " + 
			", NVL(DET.MEID_DECIMAL, (LPAD(TO_NUMBER(SUBSTR(NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)),0,8),'XXXXXXXXXX'),10,'0')|| LPAD(TO_NUMBER(SUBSTR(NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)),9,6),'XXXXXXXX'),8,'0')))  \"meidHDec\" " + 
			", DET.PRL \"PRL\" " + 
			", DET.CARD_SKU \"Card SKU\" " + 
			", DET.ERROR_DESCRIPTION " + 
			", CASE WHEN DET.ERROR_DESCRIPTION like '%IMEI and IMEI2 are same%' " + 
			"THEN 'Need to Send Email with the IMEI_DEC to supply the IMEI2_DECIMAL to NMS, If NMS cannot supply send to Magenta Team' " + 
			"WHEN DET.ERROR_DESCRIPTION like '%ICCID is a required column it cannot be NULL%' " + 
			"THEN 'Create SR Ticket in Pier 2.0 requesting for ICCID of the following IMEI_DEC' " + 
			"ELSE 'IF no Column is Null, OK to create UEDF as NW normally, If there is null column investigate/sent email' " + 
			"END AS COMMENT_COL " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR " + 
			"JOIN EDF.EDF_DETAILS_STG_INT DET " + 
			"ON HDR.UNIQUE_SEQUENCE_NUMBER = DET.UNIQUE_SEQUENCE_NUMBER " + 
			"LEFT JOIN EDF.SKU_MASTER SKUM " + 
			"ON SKUM.SKU_NUMBER = DET.SKU " + 
			"WHERE 1 =1 " + 
			"and DET.ERROR_DESCRIPTION LIKE '%meid or esn or imeiDec doesnot exist in tables%' " + 
			"AND DET.ERROR_DESCRIPTION is not null " + 
			"and DET.status = 'ERROR' " + 
			"AND SKUM.EDF_PRODUCT_TYPE = 'L' " + 
			"AND HDR.FILE_NAME = ? " + 
			"AND NOT EXISTS (SELECT 1 " + 
			"FROM EDF.EDF_INBOUND_HISTORY HIST " + 
			"WHERE 1=1 " + 
			"AND HIST.FILENAME = HDR.FILE_NAME " + 
			"AND STATUS = 'PROCESSED' " + 
			") " + 
			"AND (HDR.UPDATE_DATE, FILE_NAME) IN (SELECT MAX(UPDATE_DATE), FILE_NAME " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR2 " + 
			"WHERE 1=1 " + 
			"AND HDR2.FILE_NAME = HDR.FILE_NAME " + 
			"AND STATUS = 'ERROR' " + 
			"GROUP BY FILE_NAME " + 
			") " + 
			"ORDER BY HDR.CREATION_DATE DESC, FILE_NAME,IMEI_DECIMAL ";
	
	private final String QUERY_L1 = 
			"SELECT " + 
			"HDR.FILE_NAME " + 
			", CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE " + 
			"ELSE 'Should Be '||SKUM.EDF_PRODUCT_TYPE||' not '||DET.EDF_SERIAL_TYPE " + 
			"END EDF_SERIAL " + 
			", DET.MANUFACTURER_ID \"MFG Location ID\" " + 
			", DET.MANUFACTURER_NAME \"MFG Name\" " + 
			", DET.SKU \"Model Number\" " + 
			", DET.SKU_NAME \"Model Name\" " + 
			", DET.EQUIPMENT_TYPE \"Equipment Type (Network Type)\" " + 
			", '12345' \"Software Version Number\" " + 
			", NVL(DET.MSL,'000000') \"Subsidy Lock Code 1 (MSL)\" " + 
			", NVL(DET.OTKSL,'000000')  \"Subsidy Lock Code 2 (OTKSL)\" " + 
			", NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)) \"meidHex\" " + 
			", NVL(DET.MEID_DECIMAL, (LPAD(TO_NUMBER(SUBSTR(NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)),0,8),'XXXXXXXXXX'),10,'0')|| LPAD(TO_NUMBER(SUBSTR(NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)),9,6),'XXXXXXXX'),8,'0')))  \"meidHDec\" " + 
			", DET.ERROR_DESCRIPTION\r\n" + 
			", CASE WHEN DET.ERROR_DESCRIPTION like '%IMEI and IMEI2 are same%' " + 
			"THEN 'Need to Send Email with the IMEI_DEC to supply the IMEI2_DECIMAL to NMS, If NMS cannot supply send to Magenta Team' " + 
			"WHEN DET.ERROR_DESCRIPTION like '%ICCID is a required column it cannot be NULL%' " + 
			"THEN 'Create SR Ticket in Pier 2.0 requesting for ICCID of the following IMEI_DEC' " + 
			"ELSE 'IF no Column is Null, OK to create UEDF as NW normally, If there is null column investigate/sent email' " + 
			"END AS COMMENT_COL " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR " + 
			"JOIN EDF.EDF_DETAILS_STG_INT DET " + 
			"ON HDR.UNIQUE_SEQUENCE_NUMBER = DET.UNIQUE_SEQUENCE_NUMBER " + 
			"LEFT JOIN EDF.SKU_MASTER SKUM " + 
			"ON SKUM.SKU_NUMBER = DET.SKU " + 
			"WHERE 1 =1 " + 
			"and DET.ERROR_DESCRIPTION LIKE '%meid or esn or imeiDec doesnot exist in tables%' " + 
			"AND DET.ERROR_DESCRIPTION is not null " + 
			"and DET.status = 'ERROR' " + 
			"AND SKUM.EDF_PRODUCT_TYPE = 'L1' " + 
			"AND HDR.FILE_NAME = ? " + 
			"AND NOT EXISTS (SELECT 1 " + 
			"FROM EDF.EDF_INBOUND_HISTORY HIST " + 
			"WHERE 1=1 " + 
			"AND HIST.FILENAME = HDR.FILE_NAME " + 
			"AND STATUS = 'PROCESSED' " + 
			") " + 
			"AND (HDR.UPDATE_DATE, FILE_NAME) IN (SELECT MAX(UPDATE_DATE), FILE_NAME " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR2 " + 
			"WHERE 1=1 " + 
			"AND HDR2.FILE_NAME = HDR.FILE_NAME " + 
			"AND STATUS = 'ERROR' " + 
			"GROUP BY FILE_NAME " + 
			") " + 
			"ORDER BY HDR.CREATION_DATE DESC, FILE_NAME,IMEI_DECIMAL ";
	
	private final String QUERY_L3 = 
			"SELECT " + 
			"HDR.FILE_NAME " + 
			", CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE " + 
			"ELSE 'Should Be '||SKUM.EDF_PRODUCT_TYPE||' not '||DET.EDF_SERIAL_TYPE " + 
			"END EDF_SERIAL " + 
			", DET.MANUFACTURER_ID \"MFG Location ID\" " + 
			", DET.MANUFACTURER_NAME \"MFG Name\" " + 
			", DET.SKU \"Model Number\" " + 
			", DET.SKU_NAME \"Model Name\" " + 
			", DET.EQUIPMENT_TYPE \"Equipment Type (Network Type)\" " + 
			", '12345' \"Software Version Number\" " + 
			", NVL(DET.MSL,'000000') \"Subsidy Lock Code 1 (MSL)\" " + 
			", NVL(DET.OTKSL,'000000')  \"Subsidy Lock Code 2 (OTKSL)\" " + 
			", DET.IMEI_DECIMAL \"imeiDec\" " + 
			", DET.CSN_OR_EID \"CSN_OR_EID\" " + 
			", DET.ERROR_DESCRIPTION " + 
			", CASE WHEN DET.ERROR_DESCRIPTION like '%IMEI and IMEI2 are same%' " + 
			"THEN 'Need to Send Email with the IMEI_DEC to supply the IMEI2_DECIMAL to NMS, If NMS cannot supply send to Magenta Team' " + 
			"WHEN DET.ERROR_DESCRIPTION like '%ICCID is a required column it cannot be NULL%' " + 
			"THEN 'Create SR Ticket in Pier 2.0 requesting for ICCID of the following IMEI_DEC' " + 
			"ELSE 'IF no Column is Null, OK to create UEDF as NW normally, If there is null column investigate/sent email' " + 
			"END AS COMMENT_COL " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR " + 
			"JOIN EDF.EDF_DETAILS_STG_INT DET " + 
			"ON HDR.UNIQUE_SEQUENCE_NUMBER = DET.UNIQUE_SEQUENCE_NUMBER " + 
			"LEFT JOIN EDF.SKU_MASTER SKUM " + 
			"ON SKUM.SKU_NUMBER = DET.SKU " + 
			"WHERE 1 =1 " + 
			"and DET.ERROR_DESCRIPTION LIKE '%meid or esn or imeiDec doesnot exist in tables%' " + 
			"AND DET.ERROR_DESCRIPTION is not null " + 
			"and DET.status = 'ERROR' " + 
			"AND SKUM.EDF_PRODUCT_TYPE = 'L3' " + 
			"AND HDR.FILE_NAME = ? " + 
			"AND NOT EXISTS (SELECT 1 " + 
			"FROM EDF.EDF_INBOUND_HISTORY HIST " + 
			"WHERE 1=1 " + 
			"AND HIST.FILENAME = HDR.FILE_NAME " + 
			"AND STATUS = 'PROCESSED' " + 
			") " + 
			"AND (HDR.UPDATE_DATE, FILE_NAME) IN (SELECT MAX(UPDATE_DATE), FILE_NAME " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR2 " + 
			"WHERE 1=1 " + 
			"AND HDR2.FILE_NAME = HDR.FILE_NAME " + 
			"AND STATUS = 'ERROR' " + 
			"GROUP BY FILE_NAME " + 
			") " + 
			"ORDER BY HDR.CREATION_DATE DESC, FILE_NAME,IMEI_DECIMAL ";
	
	private final String QUERY_L4 = 
			"SELECT " + 
			"HDR.FILE_NAME " + 
			", CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE " + 
			"ELSE 'Should Be '||SKUM.EDF_PRODUCT_TYPE||' not '||DET.EDF_SERIAL_TYPE " + 
			"END EDF_SERIAL " + 
			", DET.MANUFACTURER_ID \"MFG Location ID\" " + 
			", DET.MANUFACTURER_NAME \"MFG Name\" " + 
			", DET.SKU \"Model Number\" " + 
			", DET.SKU_NAME \"Model Name\" " + 
			", DET.EQUIPMENT_TYPE \"Equipment Type (Network Type)\" " + 
			", '12345' \"Software Version Number\" " + 
			", NVL(DET.MSL,'000000') \"Subsidy Lock Code 1 (MSL)\" " + 
			", NVL(DET.OTKSL,'000000')  \"Subsidy Lock Code 2 (OTKSL)\" " + 
			", NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)) \"meidHex\" " + 
			", NVL(DET.MEID_DECIMAL, (LPAD(TO_NUMBER(SUBSTR(NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)),0,8),'XXXXXXXXXX'),10,'0')|| LPAD(TO_NUMBER(SUBSTR(NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)),9,6),'XXXXXXXX'),8,'0')))  \"meidHDec\" " + 
			", DET.IMEI_DECIMAL \"imeiDec\" " + 
			", DET.ERROR_DESCRIPTION " + 
			", CASE WHEN DET.ERROR_DESCRIPTION like '%IMEI and IMEI2 are same%' " + 
			"THEN 'Need to Send Email with the IMEI_DEC to supply the IMEI2_DECIMAL to NMS, If NMS cannot supply send to Magenta Team' " + 
			"WHEN DET.ERROR_DESCRIPTION like '%ICCID is a required column it cannot be NULL%' " + 
			"THEN 'Create SR Ticket in Pier 2.0 requesting for ICCID of the following IMEI_DEC' " + 
			"ELSE 'IF no Column is Null, OK to create UEDF as NW normally, If there is null column investigate/sent email' " + 
			"END AS COMMENT_COL " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR " + 
			"JOIN EDF.EDF_DETAILS_STG_INT DET " + 
			"ON HDR.UNIQUE_SEQUENCE_NUMBER = DET.UNIQUE_SEQUENCE_NUMBER " + 
			"LEFT JOIN EDF.SKU_MASTER SKUM " + 
			"ON SKUM.SKU_NUMBER = DET.SKU " + 
			"WHERE 1 =1 " + 
			"and DET.ERROR_DESCRIPTION LIKE '%meid or esn or imeiDec doesnot exist in tables%' " + 
			"AND DET.ERROR_DESCRIPTION is not null " + 
			"and DET.status = 'ERROR' " + 
			"AND SKUM.EDF_PRODUCT_TYPE = 'L4' " + 
			"AND HDR.FILE_NAME = ? " + 
			"AND NOT EXISTS (SELECT 1 " + 
			"FROM EDF.EDF_INBOUND_HISTORY HIST " + 
			"WHERE 1=1 " + 
			"AND HIST.FILENAME = HDR.FILE_NAME " + 
			"AND STATUS = 'PROCESSED' " + 
			") " + 
			"AND (HDR.UPDATE_DATE, FILE_NAME) IN (SELECT MAX(UPDATE_DATE), FILE_NAME " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR2 " + 
			"WHERE 1=1 " + 
			"AND HDR2.FILE_NAME = HDR.FILE_NAME " + 
			"AND STATUS = 'ERROR' " + 
			"GROUP BY FILE_NAME " + 
			") " + 
			"ORDER BY HDR.CREATION_DATE DESC, FILE_NAME,IMEI_DECIMAL ";
	
	private final String QUERY_M3 = 
			"SELECT " + 
			"HDR.FILE_NAME " + 
			", CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE " + 
			"ELSE 'Should Be '||SKUM.EDF_PRODUCT_TYPE||' not '||DET.EDF_SERIAL_TYPE " + 
			"END EDF_SERIAL " + 
			", DET.MANUFACTURER_ID \"MFG Location ID\" " + 
			", DET.MANUFACTURER_NAME \"MFG Name\" " + 
			", DET.SKU \"Model Number\" " + 
			", DET.SKU_NAME \"Model Name\" " + 
			", DET.EQUIPMENT_TYPE \"Equipment Type (Network Type)\" " + 
			", '12345' \"Software Version Number\" " + 
			", NVL(DET.MSL,'000000') \"Subsidy Lock Code 1 (MSL)\" " + 
			", NVL(DET.OTKSL,'000000')  \"Subsidy Lock Code 2 (OTKSL)\" " + 
			", DET.IMEI_DECIMAL \"imeiDec\" " + 
			", DET.ICCID \"iccId\" " + 
			", DET.ERROR_DESCRIPTION " + 
			", CASE WHEN DET.ERROR_DESCRIPTION like '%IMEI and IMEI2 are same%' " + 
			"THEN 'Need to Send Email with the IMEI_DEC to supply the IMEI2_DECIMAL to NMS, If NMS cannot supply send to Magenta Team' " + 
			"WHEN DET.ERROR_DESCRIPTION like '%ICCID is a required column it cannot be NULL%' " + 
			"THEN 'Create SR Ticket in Pier 2.0 requesting for ICCID of the following IMEI_DEC' " + 
			"ELSE 'IF no Column is Null, OK to create UEDF as NW normally, If there is null column investigate/sent email' " + 
			"END AS COMMENT_COL " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR " + 
			"JOIN EDF.EDF_DETAILS_STG_INT DET " + 
			"ON HDR.UNIQUE_SEQUENCE_NUMBER = DET.UNIQUE_SEQUENCE_NUMBER " + 
			"LEFT JOIN EDF.SKU_MASTER SKUM " + 
			"ON SKUM.SKU_NUMBER = DET.SKU " + 
			"WHERE 1 =1 " + 
			"and DET.ERROR_DESCRIPTION LIKE '%meid or esn or imeiDec doesnot exist in tables%' " + 
			"AND DET.ERROR_DESCRIPTION is not null " + 
			"and DET.status = 'ERROR' " + 
			"AND SKUM.EDF_PRODUCT_TYPE = 'M3' " + 
			"AND HDR.FILE_NAME = ? " + 
			"AND NOT EXISTS (SELECT 1 " + 
			"FROM EDF.EDF_INBOUND_HISTORY HIST " + 
			"WHERE 1=1 " + 
			"AND HIST.FILENAME = HDR.FILE_NAME " + 
			"AND STATUS = 'PROCESSED' " + 
			") " + 
			"AND (HDR.UPDATE_DATE, FILE_NAME) IN (SELECT MAX(UPDATE_DATE), FILE_NAME " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR2 " + 
			"WHERE 1=1 " + 
			"AND HDR2.FILE_NAME = HDR.FILE_NAME " + 
			"AND STATUS = 'ERROR' " + 
			"GROUP BY FILE_NAME " + 
			") " + 
			"ORDER BY HDR.CREATION_DATE DESC, FILE_NAME,IMEI_DECIMAL ";
	
	private final String QUERY_P = 
			"SELECT " + 
			"HDR.FILE_NAME " + 
			", CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE " + 
			"ELSE 'Should Be '||SKUM.EDF_PRODUCT_TYPE||' not '||DET.EDF_SERIAL_TYPE " + 
			"END EDF_SERIAL " + 
			", DET.MANUFACTURER_ID \"MFG Location ID\" " + 
			", DET.MANUFACTURER_NAME \"MFG Name\" " + 
			", DET.SKU \"Model Number\" " + 
			", DET.SKU_NAME \"Model Name\" " + 
			", DET.EQUIPMENT_TYPE \"Equipment Type (Network Type)\" " + 
			", '12345' \"Software Version Number\" " + 
			", NVL(DET.MSL,'000000') \"Subsidy Lock Code 1 (MSL)\" " + 
			", NVL(DET.OTKSL,'000000')  \"Subsidy Lock Code 2 (OTKSL)\" " + 
			", NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)) \"meidHex\" " + 
			", NVL(DET.MEID_DECIMAL, (LPAD(TO_NUMBER(SUBSTR(NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)),0,8),'XXXXXXXXXX'),10,'0')|| LPAD(TO_NUMBER(SUBSTR(NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)),9,6),'XXXXXXXX'),8,'0')))  \"meidHDec\" " + 
			", DET.ICCID \"iccID\" " + 
			", DET.ERROR_DESCRIPTION " + 
			", CASE WHEN DET.ERROR_DESCRIPTION like '%IMEI and IMEI2 are same%' " + 
			"THEN 'Need to Send Email with the IMEI_DEC to supply the IMEI2_DECIMAL to NMS, If NMS cannot supply send to Magenta Team' " + 
			"WHEN DET.ERROR_DESCRIPTION like '%CSN_OR_EID is a required column it cannot be NULL%' " + 
			"THEN 'Create SR Ticket in Pier 2.0 requesting for CSN_OR_EID of the following IMEI_DEC' " + 
			"ELSE 'IF no Column is Null, OK to create UEDF as NW normally, If there is null column investigate' " + 
			"END AS COMMENT_COL " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR " + 
			"JOIN EDF.EDF_DETAILS_STG_INT DET " + 
			"ON HDR.UNIQUE_SEQUENCE_NUMBER = DET.UNIQUE_SEQUENCE_NUMBER " + 
			"LEFT JOIN EDF.SKU_MASTER SKUM " + 
			"ON SKUM.SKU_NUMBER = DET.SKU " + 
			"WHERE 1 =1 " + 
			"and DET.ERROR_DESCRIPTION LIKE '%meid or esn or imeiDec doesnot exist in tables%' " + 
			"AND DET.ERROR_DESCRIPTION is not null " + 
			"and DET.status = 'ERROR' " + 
			"AND CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE " + 
			"ELSE 'P' " + 
			"END = 'P' " + 
			"AND HDR.FILE_NAME = ? " + 
			"AND NOT EXISTS (SELECT 1 " + 
			"FROM EDF.EDF_INBOUND_HISTORY HIST " + 
			"WHERE 1=1 " + 
			"AND HIST.FILENAME = HDR.FILE_NAME " + 
			"AND STATUS = 'PROCESSED' " + 
			") " + 
			"AND (HDR.UPDATE_DATE, FILE_NAME) IN (SELECT MAX(UPDATE_DATE), FILE_NAME " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR2 " + 
			"WHERE 1=1 " + 
			"AND HDR2.FILE_NAME = HDR.FILE_NAME " + 
			"AND STATUS = 'ERROR' " + 
			"GROUP BY FILE_NAME " + 
			") " + 
			"ORDER BY HDR.CREATION_DATE DESC, FILE_NAME,IMEI_DECIMAL ";
	
	private final String QUERY_P3 = 
			"SELECT " + 
			"HDR.FILE_NAME " + 
			", CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE " + 
			"ELSE 'Should Be '||SKUM.EDF_PRODUCT_TYPE||' not '||DET.EDF_SERIAL_TYPE " + 
			"END EDF_SERIAL " + 
			", DET.MANUFACTURER_ID \"MFG Location ID\" " + 
			", DET.MANUFACTURER_NAME \"MFG Name\" " + 
			", DET.SKU \"Model Number\" " + 
			", DET.SKU_NAME \"Model Name\" " + 
			", DET.EQUIPMENT_TYPE \"Equipment Type (Network Type)\" " + 
			", '12345' \"Software Version Number\" " + 
			", NVL(DET.MSL,'000000') \"Subsidy Lock Code 1 (MSL)\" " + 
			", NVL(DET.OTKSL,'000000')  \"Subsidy Lock Code 2 (OTKSL)\" " + 
			", NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)) \"meidHex\" " + 
			", NVL(DET.MEID_DECIMAL, (LPAD(TO_NUMBER(SUBSTR(NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)),0,8),'XXXXXXXXXX'),10,'0')|| LPAD(TO_NUMBER(SUBSTR(NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)),9,6),'XXXXXXXX'),8,'0')))  \"meidHDec\" " + 
			", DET.IMEI_DECIMAL \"imeiDec\" " + 
			", DET.ICCID \"iccID\" " + 
			", DET.ERROR_DESCRIPTION " + 
			", CASE WHEN DET.ERROR_DESCRIPTION like '%IMEI and IMEI2 are same%' " + 
			"THEN 'Need to Send Email with the IMEI_DEC to supply the IMEI2_DECIMAL to NMS, If NMS cannot supply send to Magenta Team' " + 
			"WHEN DET.ERROR_DESCRIPTION like '%ICCID is a required column it cannot be NULL%' " + 
			"THEN 'Create SR Ticket in Pier 2.0 requesting for ICCID of the following IMEI_DEC' " + 
			"ELSE 'IF no Column is Null, OK to create UEDF as NW normally, If there is null column investigate/sent email' " + 
			"END AS COMMENT_COL " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR " + 
			"JOIN EDF.EDF_DETAILS_STG_INT DET " + 
			"ON HDR.UNIQUE_SEQUENCE_NUMBER = DET.UNIQUE_SEQUENCE_NUMBER " + 
			"LEFT JOIN EDF.SKU_MASTER SKUM " + 
			"ON SKUM.SKU_NUMBER = DET.SKU " + 
			"WHERE 1 =1 " + 
			"and DET.ERROR_DESCRIPTION LIKE '%meid or esn or imeiDec doesnot exist in tables%' " + 
			"AND DET.ERROR_DESCRIPTION is not null " + 
			"and DET.status = 'ERROR' " + 
			"AND CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE " + 
			"ELSE 'P3' " + 
			"END = 'P3' " + 
			"AND HDR.FILE_NAME = ? " + 
			"AND NOT EXISTS (SELECT 1 " + 
			"FROM EDF.EDF_INBOUND_HISTORY HIST " + 
			"WHERE 1=1 " + 
			"AND HIST.FILENAME = HDR.FILE_NAME " + 
			"AND STATUS = 'PROCESSED' " + 
			") " + 
			"AND (HDR.UPDATE_DATE, FILE_NAME) IN (SELECT MAX(UPDATE_DATE), FILE_NAME " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR2 " + 
			"WHERE 1=1 " + 
			"AND HDR2.FILE_NAME = HDR.FILE_NAME " + 
			"AND STATUS = 'ERROR' " + 
			"GROUP BY FILE_NAME " + 
			") " + 
			"ORDER BY HDR.CREATION_DATE DESC, FILE_NAME,IMEI_DECIMAL";
	
	private final String QUERY_P4 = 
			"SELECT " + 
			"HDR.FILE_NAME " + 
			", CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE " + 
			"ELSE 'Should Be '||SKUM.EDF_PRODUCT_TYPE||' not '||DET.EDF_SERIAL_TYPE " + 
			"END EDF_SERIAL " + 
			", DET.MANUFACTURER_ID \"MFG Location ID\" " + 
			", DET.MANUFACTURER_NAME \"MFG Name\" " + 
			", DET.SKU \"Model Number\" " + 
			", DET.SKU_NAME \"Model Name\" " + 
			", DET.EQUIPMENT_TYPE \"Equipment Type (Network Type)\" " + 
			", '12345' \"Software Version Number\" " + 
			", NVL(DET.MSL,'000000') \"Subsidy Lock Code 1 (MSL)\" " + 
			", NVL(DET.OTKSL,'000000')  \"Subsidy Lock Code 2 (OTKSL)\" " + 
			", NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)) \"meidHex\" " + 
			", NVL(DET.MEID_DECIMAL, (LPAD(TO_NUMBER(SUBSTR(NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)),0,8),'XXXXXXXXXX'),10,'0')|| LPAD(TO_NUMBER(SUBSTR(NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)),9,6),'XXXXXXXX'),8,'0')))  \"meidHDec\" " + 
			", DET.ICCID \"iccID\" " + 
			", DET.ERROR_DESCRIPTION " + 
			", CASE WHEN DET.ERROR_DESCRIPTION like '%IMEI and IMEI2 are same%' " + 
			"THEN 'Need to Send Email with the IMEI_DEC to supply the IMEI2_DECIMAL to NMS, If NMS cannot supply send to Magenta Team' " + 
			"WHEN DET.ERROR_DESCRIPTION like '%IMEI2_DECIMAL is a required column it cannot be NULL%' " + 
			"THEN 'Create SR Ticket in Pier 2.0 requesting for IMEI2_DECIMAL of the following IMEI_DEC' " + 
			"ELSE 'IF no Column is Null, OK to create UEDF as NW normally, If there is null column investigate' " + 
			"END AS COMMENT_COL " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR " + 
			"JOIN EDF.EDF_DETAILS_STG_INT DET " + 
			"ON HDR.UNIQUE_SEQUENCE_NUMBER = DET.UNIQUE_SEQUENCE_NUMBER " + 
			"LEFT JOIN EDF.SKU_MASTER SKUM " + 
			"ON SKUM.SKU_NUMBER = DET.SKU " + 
			"WHERE 1 =1 " + 
			"and DET.ERROR_DESCRIPTION LIKE '%meid or esn or imeiDec doesnot exist in tables%' " + 
			"AND DET.ERROR_DESCRIPTION is not null " + 
			"and DET.status = 'ERROR' " + 
			"AND SKUM.EDF_PRODUCT_TYPE = 'P4' " + 
			"AND HDR.FILE_NAME = ? " + 
			"AND NOT EXISTS (SELECT 1 " + 
			"FROM EDF.EDF_INBOUND_HISTORY HIST " + 
			"WHERE 1=1 " + 
			"AND HIST.FILENAME = HDR.FILE_NAME " + 
			"AND STATUS = 'PROCESSED' " + 
			") " + 
			"AND (HDR.UPDATE_DATE, FILE_NAME) IN (SELECT MAX(UPDATE_DATE), FILE_NAME " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR2 " + 
			"WHERE 1=1 " + 
			"AND HDR2.FILE_NAME = HDR.FILE_NAME " + 
			"AND STATUS = 'ERROR' " + 
			"GROUP BY FILE_NAME " + 
			") " + 
			"ORDER BY HDR.CREATION_DATE DESC, FILE_NAME,IMEI_DECIMAL ";
	
	private final String QUERY_P5 = 
			"SELECT " + 
			"HDR.FILE_NAME " + 
			", CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE " + 
			"ELSE 'Should Be '||SKUM.EDF_PRODUCT_TYPE||' not '||DET.EDF_SERIAL_TYPE " + 
			"END EDF_SERIAL " + 
			", DET.MANUFACTURER_ID \"MFG Location ID\" " + 
			", DET.MANUFACTURER_NAME \"MFG Name\" " + 
			", DET.SKU \"Model Number\" " + 
			", DET.SKU_NAME \"Model Name\" " + 
			", DET.EQUIPMENT_TYPE \"Equipment Type (Network Type)\" " + 
			", '12345' \"Software Version Number\" " + 
			", NVL(DET.MSL,'000000') \"Subsidy Lock Code 1 (MSL)\" " + 
			", NVL(DET.OTKSL,'000000')  \"Subsidy Lock Code 2 (OTKSL)\" " + 
			", NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)) \"meidHex\" " + 
			", NVL(DET.MEID_DECIMAL, (LPAD(TO_NUMBER(SUBSTR(NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)),0,8),'XXXXXXXXXX'),10,'0')|| LPAD(TO_NUMBER(SUBSTR(NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)),9,6),'XXXXXXXX'),8,'0')))  \"meidHDec\" " + 
			", DET.IMEI_DECIMAL \"imeiDec\" " + 
			", DET.ICCID \"iccID\" " + 
			", DET.IMEI2_DECIMAL \"imeiDec2\" " + 
			", DET.ERROR_DESCRIPTION " + 
			", CASE WHEN DET.ERROR_DESCRIPTION like '%IMEI and IMEI2 are same%' " + 
			"THEN 'Need to Send Email with the IMEI_DEC to supply the IMEI2_DECIMAL to NMS, If NMS cannot supply send to Magenta Team' " + 
			"WHEN DET.ERROR_DESCRIPTION like '%IMEI2_DECIMAL is a required column it cannot be NULL%' " + 
			"THEN 'Create SR Ticket in Pier 2.0 requesting for IMEI2_DECIMAL of the following IMEI_DEC' " + 
			"ELSE 'IF no Column is Null, OK to create UEDF as NW normally, If there is null column investigate' " + 
			"END AS COMMENT_COL " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR " + 
			"JOIN EDF.EDF_DETAILS_STG_INT DET " + 
			"ON HDR.UNIQUE_SEQUENCE_NUMBER = DET.UNIQUE_SEQUENCE_NUMBER " + 
			"LEFT JOIN EDF.SKU_MASTER SKUM " + 
			"ON SKUM.SKU_NUMBER = DET.SKU " + 
			"WHERE 1 =1 " + 
			"and DET.ERROR_DESCRIPTION LIKE '%meid or esn or imeiDec doesnot exist in tables%' " + 
			"AND DET.ERROR_DESCRIPTION is not null " + 
			"and DET.status = 'ERROR' " + 
			"AND CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE " + 
			"ELSE 'P5' " + 
			"END = 'P5' " + 
			"AND HDR.FILE_NAME = ? " + 
			"AND NOT EXISTS (SELECT 1 " + 
			"FROM EDF.EDF_INBOUND_HISTORY HIST " + 
			"WHERE 1=1 " + 
			"AND HIST.FILENAME = HDR.FILE_NAME " + 
			"AND STATUS = 'PROCESSED' " + 
			") " + 
			"AND (HDR.UPDATE_DATE, FILE_NAME) IN (SELECT MAX(UPDATE_DATE), FILE_NAME " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR2 " + 
			"WHERE 1=1 " + 
			"AND HDR2.FILE_NAME = HDR.FILE_NAME " + 
			"AND STATUS = 'ERROR' " + 
			"GROUP BY FILE_NAME " + 
			") " + 
			"ORDER BY HDR.CREATION_DATE DESC, FILE_NAME,IMEI_DECIMAL ";
	
	private final String QUERY_R = 
			"SELECT " + 
			"HDR.FILE_NAME " + 
			", CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE " + 
			"ELSE 'Should Be '||SKUM.EDF_PRODUCT_TYPE||' not '||DET.EDF_SERIAL_TYPE " + 
			"END EDF_SERIAL " + 
			", DET.MANUFACTURER_ID \"MFG Location ID\" " + 
			", DET.MANUFACTURER_NAME \"MFG Name\" " + 
			", DET.SKU \"Model Number\" " + 
			", DET.SKU_NAME \"Model Name\" " + 
			", DET.EQUIPMENT_TYPE \"Equipment Type (Network Type)\" " + 
			", '12345' \"Software Version Number\" " + 
			", NVL(DET.MSL,'000000') \"Subsidy Lock Code 1 (MSL)\" " + 
			", NVL(DET.OTKSL,'000000')  \"Subsidy Lock Code 2 (OTKSL)\" " + 
			", DET.IMSI \"Imsi\" " + 
			", DET.AKEY \"Authentication Key\" " + 
			", DET.AKEY_CHECKSUM \"Authentication Key Checksum\" " + 
			", NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)) \"meidHex\" " + 
			", NVL(DET.MEID_DECIMAL, (LPAD(TO_NUMBER(SUBSTR(NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)),0,8),'XXXXXXXXXX'),10,'0')|| LPAD(TO_NUMBER(SUBSTR(NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)),9,6),'XXXXXXXX'),8,'0')))  \"meidHDec\" " + 
			", DET.PRL \"PRL\" " + 
			", DET.ICCID \"iccId\" " + 
			", DET.TRANSCEIVER_SKU \"Transceiver Sku\" " + 
			", DET.ERROR_DESCRIPTION " + 
			", CASE WHEN DET.ERROR_DESCRIPTION like '%IMEI and IMEI2 are same%' " + 
			"THEN 'Need to Send Email with the IMEI_DEC to supply the IMEI2_DECIMAL to NMS, If NMS cannot supply send to Magenta Team' " + 
			"WHEN DET.ERROR_DESCRIPTION like '%CSN_OR_EID is a required column it cannot be NULL%' " + 
			"THEN 'Create SR Ticket in Pier 2.0 requesting for CSN_OR_EID of the following IMEI_DEC' " + 
			"ELSE 'IF no Column is Null, OK to create UEDF as NW normally, If there is null column investigate' " + 
			"END AS COMMENT_COL " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR " + 
			"JOIN EDF.EDF_DETAILS_STG_INT DET " + 
			"ON HDR.UNIQUE_SEQUENCE_NUMBER = DET.UNIQUE_SEQUENCE_NUMBER " + 
			"LEFT JOIN EDF.SKU_MASTER SKUM " + 
			"ON SKUM.SKU_NUMBER = DET.SKU " + 
			"WHERE 1 =1 " + 
			"AND DET.ERROR_DESCRIPTION LIKE '%meid or esn or imeiDec doesnot exist in tables%' " + 
			"AND DET.ERROR_DESCRIPTION is not null " + 
			"AND DET.status = 'ERROR' " + 
			"AND SKUM.EDF_PRODUCT_TYPE = 'R' " + 
			"AND HDR.FILE_NAME = ? " + 
			"AND NOT EXISTS (SELECT 1 " + 
			"FROM EDF.EDF_INBOUND_HISTORY HIST " + 
			"WHERE 1=1 " + 
			"AND HIST.FILENAME = HDR.FILE_NAME " + 
			"AND STATUS = 'PROCESSED' " + 
			") " + 
			"AND (HDR.UPDATE_DATE, FILE_NAME) IN (SELECT MAX(UPDATE_DATE), FILE_NAME " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR2 " + 
			"WHERE 1=1 " + 
			"AND HDR2.FILE_NAME = HDR.FILE_NAME " + 
			"AND STATUS = 'ERROR' " + 
			"GROUP BY FILE_NAME " + 
			") " + 
			"ORDER BY HDR.CREATION_DATE DESC, FILE_NAME,IMEI_DECIMAL ";
	
	private final String QUERY_R2 =
			"SELECT " + 
			"HDR.FILE_NAME " + 
			", CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE " + 
			"ELSE 'Should Be '||SKUM.EDF_PRODUCT_TYPE||' not '||DET.EDF_SERIAL_TYPE " + 
			"END EDF_SERIAL " + 
			", DET.MANUFACTURER_ID \"MFG Location ID\" " + 
			", DET.MANUFACTURER_NAME \"MFG Name\" " + 
			", DET.SKU \"Model Number\" " + 
			", DET.SKU_NAME \"Model Name\" " + 
			", DET.EQUIPMENT_TYPE \"Equipment Type (Network Type)\" " + 
			", '12345' \"Software Version Number\" " + 
			", DET.MAC_ID \"MACID\" " + 
			", DET.CARD_SKU \"Card SKU\" " + 
			", DET.ICCID \"iccId\" " + 
			", DET.SHARED_SECRET \"Shared Secret Code\" " + 
			", DET.ERROR_DESCRIPTION " + 
			", CASE WHEN DET.ERROR_DESCRIPTION like '%IMEI and IMEI2 are same%' " + 
			"THEN 'Need to Send Email with the IMEI_DEC to supply the IMEI2_DECIMAL to NMS, If NMS cannot supply send to Magenta Team' " + 
			"WHEN DET.ERROR_DESCRIPTION like '%CSN_OR_EID is a required column it cannot be NULL%' " + 
			"THEN 'Create SR Ticket in Pier 2.0 requesting for CSN_OR_EID of the following IMEI_DEC' " + 
			"ELSE 'IF no Column is Null, OK to create UEDF as NW normally, If there is null column investigate' " + 
			"END AS COMMENT_COL " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR " + 
			"JOIN EDF.EDF_DETAILS_STG_INT DET " + 
			"ON HDR.UNIQUE_SEQUENCE_NUMBER = DET.UNIQUE_SEQUENCE_NUMBER " + 
			"JOIN EDF.SKU_MASTER SKUM " + 
			"ON SKUM.SKU_NUMBER = DET.SKU " + 
			"WHERE 1 =1 " + 
			"and DET.ERROR_DESCRIPTION LIKE '%meid or esn or imeiDec doesnot exist in tables%' " + 
			"AND DET.ERROR_DESCRIPTION is not null " + 
			"AND DET.status = 'ERROR' " + 
			"AND SKUM.EDF_PRODUCT_TYPE = 'R2' " + 
			"AND HDR.FILE_NAME = ? " + 
			"AND NOT EXISTS (SELECT 1 " + 
			"FROM EDF.EDF_INBOUND_HISTORY HIST " + 
			"WHERE 1=1 " + 
			"AND HIST.FILENAME = HDR.FILE_NAME " + 
			"AND STATUS = 'PROCESSED' " + 
			") " + 
			"AND (HDR.UPDATE_DATE, FILE_NAME) IN (SELECT MAX(UPDATE_DATE), FILE_NAME " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR2 " + 
			"WHERE 1=1 " + 
			"AND HDR2.FILE_NAME = HDR.FILE_NAME " + 
			"AND STATUS = 'ERROR' " + 
			"GROUP BY FILE_NAME " + 
			") " + 
			"ORDER BY HDR.CREATION_DATE DESC, FILE_NAME,IMEI_DECIMAL ";
	
	private final String QUERY_T = 
			"SELECT " + 
			"HDR.FILE_NAME " + 
			", CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE " + 
			"ELSE 'Should Be '||SKUM.EDF_PRODUCT_TYPE||' not '||DET.EDF_SERIAL_TYPE " + 
			"END EDF_SERIAL " + 
			", DET.MANUFACTURER_ID \"MFG Location ID\" " + 
			", DET.MANUFACTURER_NAME \"MFG Name\" " + 
			", DET.SKU \"Model Number\" " + 
			", DET.SKU_NAME \"Model Name\" " + 
			", DET.EQUIPMENT_TYPE \"Equipment Type (Network Type)\" " + 
			", '12345' \"Software Version Number\" " + 
			", NVL(DET.MSL,'000000') \"Subsidy Lock Code 1 (MSL)\" " + 
			", NVL(DET.OTKSL,'000000')  \"Subsidy Lock Code 2 (OTKSL)\" " +
			", DET.IMSI \"Imsi\" " + 
			", DET.AKEY \"Authentication Key\" " + 
			", DET.AKEY_CHECKSUM \"Authentication Key Checksum\" " +  
			", NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)) \"meidHex\" " + 
			", NVL(DET.MEID_DECIMAL, (LPAD(TO_NUMBER(SUBSTR(NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)),0,8),'XXXXXXXXXX'),10,'0')|| LPAD(TO_NUMBER(SUBSTR(NVL(DET.MEID_HEX, (SELECT SUBSTR(DET.IMEI_DECIMAL,1,14) FROM DUAL)),9,6),'XXXXXXXX'),8,'0')))  \"meidHDec\" " + 
			", DET.PRL \"PRL\" " + 
			", DET.MAC_ID \"MACID\" " + 
			", DET.CARD_SKU \"Card SKU\" " + 
			", DET.ICCID \"iccId\" " + 
			", DET.PUC1 \"PUC1\" " + 
			", DET.PUC2 \"PUC2\" " + 
			", DET.PIN1 \"PIN1\" " + 
			", DET.PIN2 \"PIN2\" " + 
			", DET.ADMIN1 \"Admin Code 1\" " + 
			", DET.KI \"Ki\" " + 
			", DET.UICC_IDENTIFIER \"UICC Card Indicator\" " + 
			", DET.IMSI_UICC \"IMSI UICC Card\" " + 
			", DET.ACC_UICC \"Acc UICC Card\" " + 
			", DET.ERROR_DESCRIPTION " + 
			", CASE WHEN DET.ERROR_DESCRIPTION like '%IMEI and IMEI2 are same%' " + 
			"THEN 'Need to Send Email with the IMEI_DEC to supply the IMEI2_DECIMAL to NMS, If NMS cannot supply send to Magenta Team' " + 
			"WHEN DET.ERROR_DESCRIPTION like '%CSN_OR_EID is a required column it cannot be NULL%' " + 
			"THEN 'Create SR Ticket in Pier 2.0 requesting for CSN_OR_EID of the following IMEI_DEC' " + 
			"ELSE 'IF no Column is Null, OK to create UEDF as NW normally, If there is null column investigate' " + 
			"END AS COMMENT_COL " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR " + 
			"JOIN EDF.EDF_DETAILS_STG_INT DET " + 
			"ON HDR.UNIQUE_SEQUENCE_NUMBER = DET.UNIQUE_SEQUENCE_NUMBER " + 
			"JOIN EDF.SKU_MASTER SKUM " + 
			"ON SKUM.SKU_NUMBER = DET.SKU " + 
			"WHERE 1 =1 " + 
			"and DET.ERROR_DESCRIPTION LIKE '%meid or esn or imeiDec doesnot exist in tables%' " + 
			"AND DET.ERROR_DESCRIPTION is not null " + 
			"AND DET.status = 'ERROR' " + 
			"AND SKUM.EDF_PRODUCT_TYPE = 'T' " + 
			"AND HDR.FILE_NAME = ? " + 
			"AND NOT EXISTS (SELECT 1 " + 
			"FROM EDF.EDF_INBOUND_HISTORY HIST " + 
			"WHERE 1=1 " + 
			"AND HIST.FILENAME = HDR.FILE_NAME " + 
			"AND STATUS = 'PROCESSED' " + 
			") " + 
			"AND (HDR.UPDATE_DATE, FILE_NAME) IN (SELECT MAX(UPDATE_DATE), FILE_NAME " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR2 " + 
			"WHERE 1=1 " + 
			"AND HDR2.FILE_NAME = HDR.FILE_NAME " + 
			"AND STATUS = 'ERROR' " + 
			"GROUP BY FILE_NAME " + 
			") " + 
			"ORDER BY HDR.CREATION_DATE DESC, FILE_NAME,IMEI_DECIMAL ";
	
	private final String QUERY_U = 
			"SELECT " + 
			"HDR.FILE_NAME " + 
			", CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE " + 
			"ELSE 'Should Be '||SKUM.EDF_PRODUCT_TYPE||' not '||DET.EDF_SERIAL_TYPE " + 
			"END EDF_SERIAL " + 
			", DET.MANUFACTURER_ID \"MFG Location ID\" " + 
			", DET.MANUFACTURER_NAME \"MFG Name\" " + 
			", DET.SKU \"Model Number\" " + 
			", DET.SKU_NAME \"Model Name\" " + 
			", DET.EQUIPMENT_TYPE \"Equipment Type (Network Type)\" " + 
			", DET.ICCID \"iccId\" " + 
			", DET.PUC1 \"PUC1\" " + 
			", DET.PUC2 \"PUC2\" " + 
			", DET.PIN1 \"PIN1\" " + 
			", DET.PIN2 \"PIN2\" " + 
			", DET.ADMIN1 \"Admin Code 1\" " + 
			", DET.KI \"Ki\" " + 
			", DET.IMSI_UICC \"IMSI UICC Card\" " + 
			", DET.ACC_UICC \"Acc UICC Card\" " + 
			", DET.ERROR_DESCRIPTION " + 
			", CASE WHEN DET.ERROR_DESCRIPTION like '%IMEI and IMEI2 are same%' " + 
			"THEN 'Need to Send Email with the IMEI_DEC to supply the IMEI2_DECIMAL to NMS, If NMS cannot supply send to Magenta Team' " + 
			"WHEN DET.ERROR_DESCRIPTION like '%CSN_OR_EID is a required column it cannot be NULL%' " + 
			"THEN 'Create SR Ticket in Pier 2.0 requesting for CSN_OR_EID of the following IMEI_DEC' " + 
			"ELSE 'IF no Column is Null, OK to create UEDF as NW normally, If there is null column investigate' " + 
			"END AS COMMENT_COL " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR " + 
			"JOIN EDF.EDF_DETAILS_STG_INT DET " + 
			"ON HDR.UNIQUE_SEQUENCE_NUMBER = DET.UNIQUE_SEQUENCE_NUMBER " + 
			"JOIN EDF.SKU_MASTER SKUM " + 
			"ON SKUM.SKU_NUMBER = DET.SKU " + 
			"WHERE 1 =1 " + 
			"and DET.ERROR_DESCRIPTION LIKE '%meid or esn or imeiDec doesnot exist in tables%' " + 
			"AND DET.ERROR_DESCRIPTION is not null " + 
			"AND DET.status = 'ERROR' " + 
			"AND SKUM.EDF_PRODUCT_TYPE = 'U' " + 
			"AND HDR.FILE_NAME = ? " + 
			"AND NOT EXISTS (SELECT 1 " + 
			"FROM EDF.EDF_INBOUND_HISTORY HIST " + 
			"WHERE 1=1 " + 
			"AND HIST.FILENAME = HDR.FILE_NAME " + 
			"AND STATUS = 'PROCESSED' " + 
			") " + 
			"AND (HDR.UPDATE_DATE, FILE_NAME) IN (SELECT MAX(UPDATE_DATE), FILE_NAME " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR2 " + 
			"WHERE 1=1 " + 
			"AND HDR2.FILE_NAME = HDR.FILE_NAME " + 
			"AND STATUS = 'ERROR' " + 
			"GROUP BY FILE_NAME " + 
			") " + 
			"ORDER BY HDR.CREATION_DATE DESC, FILE_NAME,IMEI_DECIMAL ";
	
	private final String QUERY_U1 =
			"SELECT " + 
			"HDR.FILE_NAME " + 
			", CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE\r\n" + 
			"ELSE 'Should Be '||SKUM.EDF_PRODUCT_TYPE||' not '||DET.EDF_SERIAL_TYPE " + 
			"END EDF_SERIAL " + 
			", DET.MANUFACTURER_ID \"MFG Location ID\" " + 
			", DET.MANUFACTURER_NAME \"MFG Name\" " + 
			", DET.SKU \"Model Number\" " + 
			", DET.SKU_NAME \"Model Name\" " + 
			", DET.EQUIPMENT_TYPE \"Equipment Type (Network Type)\" " + 
			", DET.ICCID \"iccId\" " + 
			", DET.PUC1 \"PUC1\" " + 
			", DET.PUC2 \"PUC2\" " + 
			", DET.PIN1 \"PIN1\" " + 
			", DET.PIN2 \"PIN2\" " + 
			", DET.ADMIN1 \"Admin Code 1\" " + 
			", DET.KI \"Ki\" " + 
			", DET.IMSI_UICC \"IMSI UICC Card\" " + 
			", DET.ACC_UICC \"Acc UICC Card\" " + 
			", DET.ERROR_DESCRIPTION " + 
			", CASE WHEN DET.ERROR_DESCRIPTION like '%IMEI and IMEI2 are same%' " + 
			"THEN 'Need to Send Email with the IMEI_DEC to supply the IMEI2_DECIMAL to NMS, If NMS cannot supply send to Magenta Team' " + 
			"WHEN DET.ERROR_DESCRIPTION like '%CSN_OR_EID is a required column it cannot be NULL%' " + 
			"THEN 'Create SR Ticket in Pier 2.0 requesting for CSN_OR_EID of the following IMEI_DEC' " + 
			"ELSE 'IF no Column is Null, OK to create UEDF as NW normally, If there is null column investigate' " + 
			"END AS COMMENT_COL " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR " + 
			"JOIN EDF.EDF_DETAILS_STG_INT DET " + 
			"ON HDR.UNIQUE_SEQUENCE_NUMBER = DET.UNIQUE_SEQUENCE_NUMBER " + 
			"JOIN EDF.SKU_MASTER SKUM " + 
			"ON SKUM.SKU_NUMBER = DET.SKU " + 
			"WHERE 1 =1 " + 
			"and DET.ERROR_DESCRIPTION LIKE '%meid or esn or imeiDec doesnot exist in tables%' " + 
			"AND DET.ERROR_DESCRIPTION is not null " + 
			"AND DET.status = 'ERROR' " + 
			"AND SKUM.EDF_PRODUCT_TYPE = 'U1' " + 
			"AND HDR.FILE_NAME = ? " + 
			"AND NOT EXISTS (SELECT 1 " + 
			"FROM EDF.EDF_INBOUND_HISTORY HIST " + 
			"WHERE 1=1 " + 
			"AND HIST.FILENAME = HDR.FILE_NAME " + 
			"AND STATUS = 'PROCESSED' " + 
			") " + 
			"AND (HDR.UPDATE_DATE, FILE_NAME) IN (SELECT MAX(UPDATE_DATE), FILE_NAME " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR2 " + 
			"WHERE 1=1 " + 
			"AND HDR2.FILE_NAME = HDR.FILE_NAME " + 
			"AND STATUS = 'ERROR' " + 
			"GROUP BY FILE_NAME " + 
			") " + 
			"ORDER BY HDR.CREATION_DATE DESC, FILE_NAME,IMEI_DECIMAL ";
	
	private final String QUERY_U2 = 
			"SELECT " + 
			"HDR.FILE_NAME " + 
			", CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE " + 
			"ELSE 'Should Be '||SKUM.EDF_PRODUCT_TYPE||' not '||DET.EDF_SERIAL_TYPE " + 
			"END EDF_SERIAL " + 
			", DET.MANUFACTURER_ID \"MFG Location ID\" " + 
			", DET.MANUFACTURER_NAME \"MFG Name\" " + 
			", DET.SKU \"Model Number\" " + 
			", DET.SKU_NAME \"Model Name\" " + 
			", DET.EQUIPMENT_TYPE \"Equipment Type (Network Type)\" " + 
			", DET.PUC1 \"PUC1\" " + 
			", DET.PUC2 \"PUC2\" " + 
			", DET.PIN1 \"PIN1\" " + 
			", DET.PIN2 \"PIN2\" " + 
			", DET.ADMIN1 \"Admin Code 1\" " + 
			", DET.KI \"Ki\"\r\n" + 
			", DET.IMSI_UICC \"IMSI UICC Card\" " + 
			", DET.ACC_UICC \"Acc UICC Card\" " + 
			", DET.MANF_ENCRYPT_KEY_INDEX   \"manuEncryptKeyIndex\" " + 
			", DET.ERROR_DESCRIPTION " + 
			", CASE WHEN DET.ERROR_DESCRIPTION like '%IMEI and IMEI2 are same%' " + 
			"THEN 'Need to Send Email with the IMEI_DEC to supply the IMEI2_DECIMAL to NMS, If NMS cannot supply send to Magenta Team' " + 
			"WHEN DET.ERROR_DESCRIPTION like '%CSN_OR_EID is a required column it cannot be NULL%' " + 
			"THEN 'Create SR Ticket in Pier 2.0 requesting for CSN_OR_EID of the following IMEI_DEC' " + 
			"ELSE 'IF no Column is Null, OK to create UEDF as NW normally, If there is null column investigate' " + 
			"END AS COMMENT_COL " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR " + 
			"JOIN EDF.EDF_DETAILS_STG_INT DET " + 
			"ON HDR.UNIQUE_SEQUENCE_NUMBER = DET.UNIQUE_SEQUENCE_NUMBER " + 
			"JOIN EDF.SKU_MASTER SKUM " + 
			"ON SKUM.SKU_NUMBER = DET.SKU " + 
			"WHERE 1 =1 " + 
			"and DET.ERROR_DESCRIPTION LIKE '%meid or esn or imeiDec doesnot exist in tables%' " + 
			"AND DET.ERROR_DESCRIPTION is not null " +  
			"AND DET.status = 'ERROR' " + 
			"AND SKUM.EDF_PRODUCT_TYPE = 'U2' " + 
			"AND HDR.FILE_NAME = ? " + 
			"AND NOT EXISTS (SELECT 1 " + 
			"FROM EDF.EDF_INBOUND_HISTORY HIST " + 
			"WHERE 1=1 " + 
			"AND HIST.FILENAME = HDR.FILE_NAME " + 
			"AND STATUS = 'PROCESSED' " + 
			") " + 
			"AND (HDR.UPDATE_DATE, FILE_NAME) IN (SELECT MAX(UPDATE_DATE), FILE_NAME " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR2 " + 
			"WHERE 1=1 " + 
			"AND HDR2.FILE_NAME = HDR.FILE_NAME " + 
			"AND STATUS = 'ERROR' " + 
			"GROUP BY FILE_NAME " + 
			") " + 
			"ORDER BY HDR.CREATION_DATE DESC, FILE_NAME,IMEI_DECIMAL ";
	
	private final String QUERY_U3 =
			"SELECT " + 
			"HDR.FILE_NAME " + 
			", CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE " + 
			"ELSE 'Should Be '||SKUM.EDF_PRODUCT_TYPE||' not '||DET.EDF_SERIAL_TYPE " + 
			"END EDF_SERIAL " + 
			", DET.MANUFACTURER_ID \"MFG Location ID\" " + 
			", DET.MANUFACTURER_NAME \"MFG Name\" " + 
			", DET.SKU \"Model Number\" " + 
			", DET.SKU_NAME \"Model Name\" " + 
			", DET.EQUIPMENT_TYPE \"Equipment Type (Network Type)\" " + 
			", DET.ICCID \"iccId\" " + 
			", DET.PUC1 \"PUC1\" " + 
			", DET.PUC2 \"PUC2\" " + 
			", DET.PIN1 \"PIN1\" " + 
			", DET.PIN2 \"PIN2\" " + 
			", DET.ADMIN1 \"Admin Code 1\" " + 
			", DET.KI \"Ki\" " + 
			", DET.IMSI_UICC \"IMSI UICC Card\" " + 
			", DET.ACC_UICC \"Acc UICC Card\" " + 
			", DET.PROFILE_TYPE \"Profile Type\" " + 
			", DET.EF_IMPU \"EF_IMPU\" " + 
			", DET.EF_IMPI \"EF_IMPI\" " + 
			", DET.ERROR_DESCRIPTION " + 
			", CASE WHEN DET.ERROR_DESCRIPTION like '%IMEI and IMEI2 are same%'\r\n" + 
			"THEN 'Need to Send Email with the IMEI_DEC to supply the IMEI2_DECIMAL to NMS, If NMS cannot supply send to Magenta Team' " + 
			"WHEN DET.ERROR_DESCRIPTION like '%CSN_OR_EID is a required column it cannot be NULL%' " + 
			"THEN 'Create SR Ticket in Pier 2.0 requesting for CSN_OR_EID of the following IMEI_DEC' " + 
			"ELSE 'IF no Column is Null, OK to create UEDF as NW normally, If there is null column investigate' " + 
			"END AS COMMENT_COL " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR " + 
			"JOIN EDF.EDF_DETAILS_STG_INT DET " + 
			"ON HDR.UNIQUE_SEQUENCE_NUMBER = DET.UNIQUE_SEQUENCE_NUMBER " + 
			"JOIN EDF.SKU_MASTER SKUM " + 
			"ON SKUM.SKU_NUMBER = DET.SKU " + 
			"WHERE 1 =1 " + 
			"and DET.ERROR_DESCRIPTION LIKE '%meid or esn or imeiDec doesnot exist in tables%' " + 
			"AND DET.ERROR_DESCRIPTION is not null " +  
			"AND DET.status = 'ERROR' " + 
			"AND SKUM.EDF_PRODUCT_TYPE = 'U3' " + 
			"AND HDR.FILE_NAME = ? " + 
			"AND NOT EXISTS (SELECT 1 " + 
			"FROM EDF.EDF_INBOUND_HISTORY HIST " + 
			"WHERE 1=1 " + 
			"AND HIST.FILENAME = HDR.FILE_NAME " + 
			"AND STATUS = 'PROCESSED' " + 
			") " + 
			"AND (HDR.UPDATE_DATE, FILE_NAME) IN (SELECT MAX(UPDATE_DATE), FILE_NAME " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR2 " + 
			"WHERE 1=1 " + 
			"AND HDR2.FILE_NAME = HDR.FILE_NAME " + 
			"AND STATUS = 'ERROR' " + 
			"GROUP BY FILE_NAME " + 
			") " + 
			"ORDER BY HDR.CREATION_DATE DESC, FILE_NAME,IMEI_DECIMAL ";
	
	private final String QUERY_V = 
			"SELECT " + 
			"HDR.FILE_NAME " + 
			", CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE " + 
			"ELSE 'Should Be '||SKUM.EDF_PRODUCT_TYPE||' not '||DET.EDF_SERIAL_TYPE " + 
			"END EDF_SERIAL " + 
			", DET.MANUFACTURER_ID \"MFG Location ID\" " + 
			", DET.MANUFACTURER_NAME \"MFG Name\" " + 
			", DET.SKU \"Model Number\" " + 
			", DET.SKU_NAME \"Model Name\" " + 
			", DET.EQUIPMENT_TYPE \"Equipment Type (Network Type)\" " + 
			", '12345' \"Software Version Number\" " + 
			", DET.MAC_ID \"MACID\" " + 
			", DET.SHARED_SECRET \"Shared Secret Code\" " + 
			", DET.ERROR_DESCRIPTION " + 
			", CASE WHEN DET.ERROR_DESCRIPTION like '%IMEI and IMEI2 are same%' " + 
			"THEN 'Need to Send Email with the IMEI_DEC to supply the IMEI2_DECIMAL to NMS, If NMS cannot supply send to Magenta Team' " + 
			"WHEN DET.ERROR_DESCRIPTION like '%CSN_OR_EID is a required column it cannot be NULL%' " + 
			"THEN 'Create SR Ticket in Pier 2.0 requesting for CSN_OR_EID of the following IMEI_DEC' " + 
			"ELSE 'IF no Column is Null, OK to create UEDF as NW normally, If there is null column investigate' " + 
			"END AS COMMENT_COL " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR " + 
			"JOIN EDF.EDF_DETAILS_STG_INT DET " + 
			"ON HDR.UNIQUE_SEQUENCE_NUMBER = DET.UNIQUE_SEQUENCE_NUMBER " + 
			"JOIN EDF.SKU_MASTER SKUM " + 
			"ON SKUM.SKU_NUMBER = DET.SKU " + 
			"WHERE 1 =1 " + 
			"and DET.ERROR_DESCRIPTION LIKE '%meid or esn or imeiDec doesnot exist in tables%' " + 
			"AND DET.ERROR_DESCRIPTION is not null " + 
			"AND DET.status = 'ERROR' " + 
			"AND SKUM.EDF_PRODUCT_TYPE = 'V' " + 
			"AND HDR.FILE_NAME = ? " + 
			"AND NOT EXISTS (SELECT 1 " + 
			"FROM EDF.EDF_INBOUND_HISTORY HIST " + 
			"WHERE 1=1 " + 
			"AND HIST.FILENAME = HDR.FILE_NAME " + 
			"AND STATUS = 'PROCESSED' " + 
			") " + 
			"AND (HDR.UPDATE_DATE, FILE_NAME) IN (SELECT MAX(UPDATE_DATE), FILE_NAME " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR2 " + 
			"WHERE 1=1 " + 
			"AND HDR2.FILE_NAME = HDR.FILE_NAME " + 
			"AND STATUS = 'ERROR' " + 
			"GROUP BY FILE_NAME " + 
			") " + 
			"ORDER BY HDR.CREATION_DATE DESC, FILE_NAME,IMEI_DECIMAL ";
	
	private final String QUERY_W = 
			"SELECT " + 
			"HDR.FILE_NAME " + 
			", CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE " + 
			"ELSE 'Should Be '||SKUM.EDF_PRODUCT_TYPE||' not '||DET.EDF_SERIAL_TYPE " + 
			"END EDF_SERIAL " + 
			", DET.MANUFACTURER_ID \"MFG Location ID\" " + 
			", DET.MANUFACTURER_NAME \"MFG Name\" " + 
			", DET.SKU \"Model Number\" " + 
			", DET.SKU_NAME \"Model Name\" " + 
			", DET.EQUIPMENT_TYPE \"Equipment Type (Network Type)\" " + 
			", DET.IMSI \"Imsi\" " + 
			", DET.AKEY \"Authentication Key\" " + 
			", DET.AKEY_CHECKSUM \"Authentication Key Checksum\" " + 
			", DET.PRL \"PRL\" " + 
			", DET.ICCID \"iccId\" " + 
			", DET.PUC1 \"PUC1\" " + 
			", DET.PUC2 \"PUC2\" " + 
			", DET.PIN1 \"PIN1\" " + 
			", DET.PIN2 \"PIN2\" " + 
			", DET.ADMIN1 \"Admin Code 1\" " + 
			", DET.KI \"Ki\" " + 
			", DET.IMSI_UICC \"IMSI UICC Card\" " + 
			", DET.ACC_UICC \"Acc UICC Card\" " + 
			", DET.SF_EQUIPMENT_ID \"SF Equiment Id\" " + 
			", DET.MANF_ENCRYPT_KEY_INDEX   \"manuEncryptKeyIndex\" " + 
			", DET.ERROR_DESCRIPTION " + 
			", CASE WHEN DET.ERROR_DESCRIPTION like '%IMEI and IMEI2 are same%' " + 
			"THEN 'Need to Send Email with the IMEI_DEC to supply the IMEI2_DECIMAL to NMS, If NMS cannot supply send to Magenta Team' " + 
			"WHEN DET.ERROR_DESCRIPTION like '%CSN_OR_EID is a required column it cannot be NULL%' " + 
			"THEN 'Create SR Ticket in Pier 2.0 requesting for CSN_OR_EID of the following IMEI_DEC' " + 
			"ELSE 'IF no Column is Null, OK to create UEDF as NW normally, If there is null column investigate' " + 
			"END AS COMMENT_COL " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR " + 
			"JOIN EDF.EDF_DETAILS_STG_INT DET " + 
			"ON HDR.UNIQUE_SEQUENCE_NUMBER = DET.UNIQUE_SEQUENCE_NUMBER " + 
			"JOIN EDF.SKU_MASTER SKUM " + 
			"ON SKUM.SKU_NUMBER = DET.SKU " + 
			"WHERE 1 =1 " + 
			"and DET.ERROR_DESCRIPTION LIKE '%meid or esn or imeiDec doesnot exist in tables%' " + 
			"AND DET.ERROR_DESCRIPTION is not null " + 
			"AND DET.status = 'ERROR' " + 
			"AND SKUM.EDF_PRODUCT_TYPE = 'W' " + 
			"AND HDR.FILE_NAME = ? " + 
			"AND NOT EXISTS (SELECT 1 " + 
			"FROM EDF.EDF_INBOUND_HISTORY HIST " + 
			"WHERE 1=1 " + 
			"AND HIST.FILENAME = HDR.FILE_NAME " + 
			"AND STATUS = 'PROCESSED' " + 
			") " + 
			"AND (HDR.UPDATE_DATE, FILE_NAME) IN (SELECT MAX(UPDATE_DATE), FILE_NAME " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR2 " + 
			"WHERE 1=1 " + 
			"AND HDR2.FILE_NAME = HDR.FILE_NAME " + 
			"AND STATUS = 'ERROR' " + 
			"GROUP BY FILE_NAME " + 
			") " + 
			"ORDER BY HDR.CREATION_DATE DESC, FILE_NAME,IMEI_DECIMAL ";
	
	private final String QUERY_W1 = 
			"SELECT " + 
			"HDR.FILE_NAME " + 
			", CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE " + 
			"ELSE 'Should Be '||SKUM.EDF_PRODUCT_TYPE||' not '||DET.EDF_SERIAL_TYPE " + 
			"END EDF_SERIAL " + 
			", DET.MANUFACTURER_ID \"MFG Location ID\" " + 
			", DET.MANUFACTURER_NAME \"MFG Name\" " + 
			", DET.SKU \"Model Number\" " + 
			", DET.SKU_NAME \"Model Name\" " + 
			", DET.EQUIPMENT_TYPE \"Equipment Type (Network Type)\" " + 
			", DET.AKEY \"Authentication Key\" " + 
			", DET.AKEY_CHECKSUM \"Authentication Key Checksum\" " + 
			", DET.PRL \"PRL\" " + 
			", DET.ICCID \"iccId\" " + 
			", DET.PUC1 \"PUC1\" " + 
			", DET.PUC2 \"PUC2\" " + 
			", DET.PIN1 \"PIN1\" " + 
			", DET.PIN2 \"PIN2\" " + 
			", DET.ADMIN1 \"Admin Code 1\" " + 
			", DET.KI \"Ki\" " + 
			", DET.IMSI_UICC \"IMSI UICC Card\" " + 
			", DET.ACC_UICC \"Acc UICC Card\" " + 
			", DET.SF_EQUIPMENT_ID \"SF Equiment Id\" " + 
			", DET.PROFILE_TYPE \"Profile Type\" " + 
			", DET.ERROR_DESCRIPTION " + 
			", CASE WHEN DET.ERROR_DESCRIPTION like '%IMEI and IMEI2 are same%' " + 
			"THEN 'Need to Send Email with the IMEI_DEC to supply the IMEI2_DECIMAL to NMS, If NMS cannot supply send to Magenta Team' " + 
			"WHEN DET.ERROR_DESCRIPTION like '%CSN_OR_EID is a required column it cannot be NULL%' " + 
			"THEN 'Create SR Ticket in Pier 2.0 requesting for CSN_OR_EID of the following IMEI_DEC' " + 
			"ELSE 'IF no Column is Null, OK to create UEDF as NW normally, If there is null column investigate' " + 
			"END AS COMMENT_COL " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR " + 
			"JOIN EDF.EDF_DETAILS_STG_INT DET " + 
			"ON HDR.UNIQUE_SEQUENCE_NUMBER = DET.UNIQUE_SEQUENCE_NUMBER " + 
			"JOIN EDF.SKU_MASTER SKUM " + 
			"ON SKUM.SKU_NUMBER = DET.SKU " + 
			"WHERE 1 =1 " + 
			"and DET.ERROR_DESCRIPTION LIKE '%meid or esn or imeiDec doesnot exist in tables%' " + 
			"AND DET.ERROR_DESCRIPTION is not null " +  
			"AND DET.status = 'ERROR' " + 
			"AND SKUM.EDF_PRODUCT_TYPE = 'W1' " + 
			"AND HDR.FILE_NAME = ? " + 
			"AND NOT EXISTS (SELECT 1 " + 
			"FROM EDF.EDF_INBOUND_HISTORY HIST " + 
			"WHERE 1=1 " + 
			"AND HIST.FILENAME = HDR.FILE_NAME " + 
			"AND STATUS = 'PROCESSED' " + 
			") " + 
			"AND (HDR.UPDATE_DATE, FILE_NAME) IN (SELECT MAX(UPDATE_DATE), FILE_NAME " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR2 " + 
			"WHERE 1=1 " + 
			"AND HDR2.FILE_NAME = HDR.FILE_NAME " + 
			"AND STATUS = 'ERROR' " + 
			"GROUP BY FILE_NAME " + 
			") " + 
			"ORDER BY HDR.CREATION_DATE DESC, FILE_NAME,IMEI_DECIMAL ";
	
	private final String QUERY_W3 =
			"SELECT\r\n" + 
			"HDR.FILE_NAME\r\n" + 
			", CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE\r\n" + 
			"        THEN SKUM.EDF_PRODUCT_TYPE\r\n" + 
			"        ELSE 'Should Be '||SKUM.EDF_PRODUCT_TYPE||' not '||DET.EDF_SERIAL_TYPE\r\n" + 
			"  END EDF_SERIAL\r\n" + 
			", DET.MANUFACTURER_ID \"MFG Location ID\"\r\n" + 
			", DET.MANUFACTURER_NAME \"MFG Name\"\r\n" + 
			", DET.SKU \"Model Number\"\r\n" + 
			", DET.SKU_NAME \"Model Name\"\r\n" + 
			", DET.EQUIPMENT_TYPE \"Equipment Type (Network Type)\"\r\n" + 
			", DET.ICCID \"iccId\"\r\n" + 
			", DET.PUC1 \"PUC1\"\r\n" + 
			", DET.PUC2 \"PUC2\"\r\n" + 
			", DET.PIN1 \"PIN1\"\r\n" + 
			", DET.PIN2 \"PIN2\"\r\n" + 
			", DET.ADMIN1 \"Admin Code 1\" " + 
			", DET.KI \"Ki\" " + 
			", DET.IMSI_UICC \"IMSI UICC Card\" " + 
			", DET.ACC_UICC \"Acc UICC Card\" " + 
			", DET.PROFILE_TYPE \"Profile Type\" " + 
			", DET.EF_IMPU \"EF_IMPU\" " + 
			", DET.EF_IMPI \"EF_IMPI\" " + 
			", DET.ERROR_DESCRIPTION " + 
			", CASE WHEN DET.ERROR_DESCRIPTION like '%IMEI and IMEI2 are same%' " + 
			"THEN 'Need to Send Email with the IMEI_DEC to supply the IMEI2_DECIMAL to NMS, If NMS cannot supply send to Magenta Team' " + 
			"WHEN DET.ERROR_DESCRIPTION like '%CSN_OR_EID is a required column it cannot be NULL%' " + 
			"THEN 'Create SR Ticket in Pier 2.0 requesting for CSN_OR_EID of the following IMEI_DEC' " + 
			"ELSE 'IF no Column is Null, OK to create UEDF as NW normally, If there is null column investigate' " + 
			"END AS COMMENT_COL " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR " + 
			"JOIN EDF.EDF_DETAILS_STG_INT DET " + 
			"ON HDR.UNIQUE_SEQUENCE_NUMBER = DET.UNIQUE_SEQUENCE_NUMBER " + 
			"JOIN EDF.SKU_MASTER SKUM " + 
			"ON SKUM.SKU_NUMBER = DET.SKU " + 
			"WHERE 1 =1 " + 
			"and DET.ERROR_DESCRIPTION LIKE '%meid or esn or imeiDec doesnot exist in tables%' " + 
			"AND DET.ERROR_DESCRIPTION is not null " + 
			"AND DET.status = 'ERROR' " + 
			"AND SKUM.EDF_PRODUCT_TYPE = 'W3' " + 
			"AND HDR.FILE_NAME = ? " + 
			"AND NOT EXISTS (SELECT 1 " + 
			"FROM EDF.EDF_INBOUND_HISTORY HIST " + 
			"WHERE 1=1 " + 
			"AND HIST.FILENAME = HDR.FILE_NAME " + 
			"AND STATUS = 'PROCESSED' " + 
			") " + 
			"AND (HDR.UPDATE_DATE, FILE_NAME) IN (SELECT MAX(UPDATE_DATE), FILE_NAME " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR2 " + 
			"WHERE 1=1 " + 
			"AND HDR2.FILE_NAME = HDR.FILE_NAME " + 
			"AND STATUS = 'ERROR' " + 
			"GROUP BY FILE_NAME " + 
			") " + 
			"ORDER BY HDR.CREATION_DATE DESC, FILE_NAME,IMEI_DECIMAL ";
	
	private final String QUERY_W4 =
			"SELECT " + 
			"HDR.FILE_NAME " + 
			", CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE " + 
			"ELSE 'Should Be '||SKUM.EDF_PRODUCT_TYPE||' not '||DET.EDF_SERIAL_TYPE " + 
			"END EDF_SERIAL " + 
			", DET.MANUFACTURER_ID \"MFG Location ID\" " + 
			", DET.MANUFACTURER_NAME \"MFG Name\" " + 
			", DET.SKU \"Model Number\" " + 
			", DET.SKU_NAME \"Model Name\" " + 
			", DET.EQUIPMENT_TYPE \"Equipment Type (Network Type)\" " + 
			", DET.AKEY \"Authentication Key\" " + 
			", DET.AKEY_CHECKSUM \"Authentication Key Checksum\" " + 
			", DET.PRL \"PRL\" " + 
			", DET.ICCID \"iccId\" " + 
			", DET.PUC1 \"PUC1\" " + 
			", DET.PUC2 \"PUC2\" " + 
			", DET.PIN1 \"PIN1\" " + 
			", DET.PIN2 \"PIN2\" " + 
			", DET.ADMIN1 \"Admin Code 1\" " + 
			", DET.KI \"Ki\" " + 
			", DET.IMSI_UICC \"IMSI UICC Card\" " + 
			", DET.ACC_UICC \"Acc UICC Card\" " + 
			", DET.SF_EQUIPMENT_ID \"SF Equiment Id\" " + 
			", DET.PROFILE_TYPE \"Profile Type\" " + 
			", DET.EF_IMPU \"EF_IMPU\" " + 
			", DET.EF_IMPI \"EF_IMPI\" " + 
			", DET.ERROR_DESCRIPTION " + 
			", CASE WHEN DET.ERROR_DESCRIPTION like '%IMEI and IMEI2 are same%' " + 
			"THEN 'Need to Send Email with the IMEI_DEC to supply the IMEI2_DECIMAL to NMS, If NMS cannot supply send to Magenta Team' " + 
			"WHEN DET.ERROR_DESCRIPTION like '%CSN_OR_EID is a required column it cannot be NULL%' " + 
			"THEN 'Create SR Ticket in Pier 2.0 requesting for CSN_OR_EID of the following IMEI_DEC' " + 
			"ELSE 'IF no Column is Null, OK to create UEDF as NW normally, If there is null column investigate' " + 
			"END AS COMMENT_COL " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR " + 
			"JOIN EDF.EDF_DETAILS_STG_INT DET " + 
			"ON HDR.UNIQUE_SEQUENCE_NUMBER = DET.UNIQUE_SEQUENCE_NUMBER " + 
			"JOIN EDF.SKU_MASTER SKUM " + 
			"ON SKUM.SKU_NUMBER = DET.SKU " + 
			"WHERE 1 =1 " + 
			"and DET.ERROR_DESCRIPTION LIKE '%meid or esn or imeiDec doesnot exist in tables%' " + 
			"AND DET.ERROR_DESCRIPTION is not null " + 
			"AND DET.status = 'ERROR' " + 
			"AND SKUM.EDF_PRODUCT_TYPE = 'W1' " + 
			"AND HDR.FILE_NAME = ? " + 
			"AND NOT EXISTS (SELECT 1 " + 
			"FROM EDF.EDF_INBOUND_HISTORY HIST " + 
			"WHERE 1=1 " + 
			"AND HIST.FILENAME = HDR.FILE_NAME " + 
			"AND STATUS = 'PROCESSED' " + 
			") " + 
			"AND (HDR.UPDATE_DATE, FILE_NAME) IN (SELECT MAX(UPDATE_DATE), FILE_NAME " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR2 " + 
			"WHERE 1=1 " + 
			"AND HDR2.FILE_NAME = HDR.FILE_NAME " + 
			"AND STATUS = 'ERROR' " + 
			"GROUP BY FILE_NAME " + 
			") " + 
			"ORDER BY HDR.CREATION_DATE DESC, FILE_NAME,IMEI_DECIMAL ";
	
	private final String QUERY_Y = //okz
			"SELECT " + 
			"HDR.FILE_NAME " + 
			", CASE WHEN DET.EDF_SERIAL_TYPE = SKUM.EDF_PRODUCT_TYPE " + 
			"THEN SKUM.EDF_PRODUCT_TYPE " + 
			"ELSE 'Should Be '||SKUM.EDF_PRODUCT_TYPE||' not '||DET.EDF_SERIAL_TYPE " + 
			"END EDF_SERIAL " + 
			", DET.MANUFACTURER_ID \"MFG Location ID\" " + 
			", DET.MANUFACTURER_NAME \"MFG Name\" " + 
			", DET.SKU \"Model Number\" " + 
			", DET.SKU_NAME \"Model Name\" " + 
			", DET.EQUIPMENT_TYPE \"Equipment Type (Network Type)\" " + 
			", '12345' \"Software Version Number\" " + 
			", NVL(DET.MSL,'000000') \"Subsidy Lock Code 1 (MSL)\" " + 
			", NVL(DET.OTKSL,'000000')  \"Subsidy Lock Code 2 (OTKSL)\" " + 
			", DET.AKEY \"Authentication Key\" " + 
			", DET.AKEY_CHECKSUM \"Authentication Key Checksum\" " + 
			", DET.ICCID \"iccId\" " + 
			", DET.TRANSCEIVER_SKU \"Transceiver Sku\" " + 
			", DET.ERROR_DESCRIPTION " + 
			", CASE WHEN DET.ERROR_DESCRIPTION like '%IMEI and IMEI2 are same%' " + 
			"THEN 'Need to Send Email with the IMEI_DEC to supply the IMEI2_DECIMAL to NMS, If NMS cannot supply send to Magenta Team' " + 
			"WHEN DET.ERROR_DESCRIPTION like '%CSN_OR_EID is a required column it cannot be NULL%' " + 
			"THEN 'Create SR Ticket in Pier 2.0 requesting for CSN_OR_EID of the following IMEI_DEC' " + 
			"ELSE 'IF no Column is Null, OK to create UEDF as NW normally, If there is null column investigate' " + 
			"END AS COMMENT_COL " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR " + 
			"JOIN EDF.EDF_DETAILS_STG_INT DET " + 
			"ON HDR.UNIQUE_SEQUENCE_NUMBER = DET.UNIQUE_SEQUENCE_NUMBER " + 
			"JOIN EDF.SKU_MASTER SKUM " + 
			"ON SKUM.SKU_NUMBER = DET.SKU " + 
			"WHERE 1 =1 " + 
			"and DET.ERROR_DESCRIPTION LIKE '%meid or esn or imeiDec doesnot exist in tables%' " + 
			"AND DET.ERROR_DESCRIPTION is not null " + 
			"AND DET.status = 'ERROR' " + 
			"AND SKUM.EDF_PRODUCT_TYPE = 'Y' " + 
			"AND HDR.FILE_NAME = ? " + 
			"AND NOT EXISTS (SELECT 1 " + 
			"FROM EDF.EDF_INBOUND_HISTORY HIST " + 
			"WHERE 1=1 " + 
			"AND HIST.FILENAME = HDR.FILE_NAME " + 
			"AND STATUS = 'PROCESSED' " + 
			") " + 
			"AND (HDR.UPDATE_DATE, FILE_NAME) IN (SELECT MAX(UPDATE_DATE), FILE_NAME " + 
			"FROM EDF.EDF_HEADER_STG_INT HDR2 " + 
			"WHERE 1=1 " + 
			"AND HDR2.FILE_NAME = HDR.FILE_NAME " + 
			"AND STATUS = 'ERROR' " + 
			"GROUP BY FILE_NAME " + 
			") " + 
			"ORDER BY HDR.CREATION_DATE DESC, FILE_NAME,IMEI_DECIMAL ";


	public String getQUERY_ERRORS() {
		return QUERY_ERRORS;
	}

	public String getQUERY_A() {
		return QUERY_A;
	}

	public String getQUERY_B() {
		return QUERY_B;
	}

	public String getQUERY_C() {
		return QUERY_C;
	}

	public String getQUERY_C1() {
		return QUERY_C1;
	}

	public String getQUERY_C2() {
		return QUERY_C2;
	}

	public String getQUERY_C4() {
		return QUERY_C4;
	}

	public String getQUERY_C5() {
		return QUERY_C5;
	}

	public String getQUERY_P() {
		return QUERY_P;
	}

	public String getQUERY_P5() {
		return QUERY_P5;
	}

	public String getQUERY_K3() {
		return QUERY_K3;
	}

	public String getQUERY_P3() {
		return QUERY_P3;
	}

	public String getQUERY_E() {
		return QUERY_E;
	}

	public String getQUERY_G() {
		return QUERY_G;
	}

	public String getQUERY_H() {
		return QUERY_H;
	}

	public String getQUERY_H3() {
		return QUERY_H3;
	}

	public String getQUERY_H5() {
		return QUERY_H5;
	}

	public String getQUERY_K4() {
		return QUERY_K4;
	}

	public String getQUERY_L() {
		return QUERY_L;
	}

	public String getQUERY_L1() {
		return QUERY_L1;
	}

	public String getQUERY_L3() {
		return QUERY_L3;
	}

	public String getQUERY_L4() {
		return QUERY_L4;
	}

	public String getQUERY_M3() {
		return QUERY_M3;
	}

	public String getQUERY_P4() {
		return QUERY_P4;
	}

	public String getQUERY_R() {
		return QUERY_R;
	}

	public String getQUERY_R2() {
		return QUERY_R2;
	}

	public String getQUERY_T() {
		return QUERY_T;
	}

	public String getQUERY_U() {
		return QUERY_U;
	}

	public String getQUERY_U1() {
		return QUERY_U1;
	}

	public String getQUERY_U2() {
		return QUERY_U2;
	}

	public String getQUERY_U3() {
		return QUERY_U3;
	}

	public String getQUERY_V() {
		return QUERY_V;
	}

	public String getQUERY_W() {
		return QUERY_W;
	}

	public String getQUERY_W1() {
		return QUERY_W1;
	}

	public String getQUERY_W3() {
		return QUERY_W3;
	}

	public String getQUERY_W4() {
		return QUERY_W4;
	}

	public String getQUERY_Y() {
		return QUERY_Y;
	}

}
