package com.sprint.exs.uedf.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.sprint.exs.uedf.controller.Controller;
import com.sprint.exs.uedf.model.UedfErrors;
import com.sprint.exs.uedf.model.UedfFile;

public class View extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable errorTable, uedfLogsTable;
	private JPanel leftPane, rightPane, bottomPane;
	private JButton generateUedf;
	private JScrollPane scrollPaneErr, scrollPaneUedf;
	private GridBagConstraints gbc;
	private Insets inset;
	private TitledBorder titledBorder;
	private DefaultTableModel errorModel, uedfModel;
	private static final String []ERROR_COLUMN = {"FILE_NAME", "CNT_SKU", "EDF_SERIAL", 
			"UNIQUE_SEQUENCE_NUMBER", "DEVICE_COUNT", "COUNT_ERR_DET", 
			"PHONE_TYPE", "STATUS", "CREATION_DATE", "UPDATE_DATE"};
	
	private static final String []UEDF_COLUMN = {"FILE_NAME", "EDF_SERIAL", "MFG Location ID", "MFG Name",
			"Model Number", "Model Name", "Equipment Type (Network Type)", "Software Version Number", 
			"Subsidy Lock Code 1 (MSL)", "Subsidy Lock Code 2 (OTKSL)", "meidHex", "meidHDec", "imeiDec", "iccID", 
			"Error Description", "Comment"};
	
	private Controller controller;
	
	public View() {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		controller = new Controller();
		
		setLayout(new BorderLayout());
		leftPane = new JPanel(new GridBagLayout());
		rightPane = new JPanel(new GridBagLayout());
		bottomPane = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
		inset = new Insets(5, 5, 5, 5);
		
		generateUedf = new JButton("Generate UEDF");
		generateUedf.addActionListener(this);
		errorModel = new DefaultTableModel(0, 0);
		//{
			/**
			 * 
			 */
		//	private static final long serialVersionUID = 1L;

		//	public boolean isCellEditable(int row, int column) {
		//		return false;
		//	}
		//};
		errorModel.setColumnIdentifiers(ERROR_COLUMN);
		errorTable = new JTable(errorModel);
		scrollPaneErr = new JScrollPane(errorTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		errorTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		uedfModel = new DefaultTableModel(0, 0);
		//{
			/**
			 * 
			 */
		//	private static final long serialVersionUID = 1L;

		//	public boolean isCellEditable(int row, int column) {
		//		return false;
		//	}
		//};
		uedfModel.setColumnIdentifiers(UEDF_COLUMN);
		uedfLogsTable = new JTable(uedfModel);
		scrollPaneUedf = new JScrollPane(uedfLogsTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		uedfLogsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		this.setResizable(false);
		setTitle("EXS UEDF Automation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new BorderLayout());
		add(leftPane, BorderLayout.NORTH);
		//add(rightPane, BorderLayout.CENTER);
		add(bottomPane, BorderLayout.SOUTH);
		
		titledBorder = new TitledBorder("UEDF Errors");
		leftPane.setBorder(titledBorder);
		setComponent(scrollPaneErr, leftPane, 1, 0, 0, 0, 0);
		
		titledBorder = new TitledBorder("UEDF Logs");
		rightPane.setBorder(titledBorder);
		setComponent(scrollPaneUedf, rightPane, 1, 0, 0, 0, 0);
		
		setComponent(generateUedf, bottomPane, 1, 0, 0, 0, 0);
		
		pack();
		setLocationRelativeTo(null);
		
	}
	
	public void setComponent(Component component, JPanel compPanel, double weightx, int gridx, int gridy, int ipady, int ipadx) {
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = inset;
		gbc.weightx = weightx;
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.ipady = ipady;
		compPanel.add(component, gbc);
	}
	
	public void setErrorTableData(Map<String, List<UedfErrors>> uedfErrors) {			
		errorModel.setRowCount(0);
		
		for (String key : uedfErrors.keySet()) {
			for (int ctr = 0; ctr < uedfErrors.get(key).size(); ctr++) {
				errorModel.addRow(new Object[] 
						{
							uedfErrors.get(key).get(ctr).getFileName(),
							uedfErrors.get(key).get(ctr).getCntSku(),
							uedfErrors.get(key).get(ctr).getEdfSerial(),
							uedfErrors.get(key).get(ctr).getUniqueSeqNum(),
							uedfErrors.get(key).get(ctr).getDeviceCount(),
							uedfErrors.get(key).get(ctr).getCountErrDet(),
							uedfErrors.get(key).get(ctr).getPhoneType(),
							uedfErrors.get(key).get(ctr).getStatus(),
							uedfErrors.get(key).get(ctr).getCreationDate(),
							uedfErrors.get(key).get(ctr).getUpdateDate()
						}
				);
			}
		}

	}
	
	public void setUedfLogsTableData(List<UedfFile> uedfFileRecord) {
		
		for (int ctr = 0; ctr < uedfFileRecord.size(); ctr++) {
			uedfModel.addRow(new Object[] 
				{
						uedfFileRecord.get(ctr).getFileName(),
						uedfFileRecord.get(ctr).getEdfSerial(),
						uedfFileRecord.get(ctr).getMfgLocationID(),
						uedfFileRecord.get(ctr).getMfgName(),
						uedfFileRecord.get(ctr).getModelNumber(),
						uedfFileRecord.get(ctr).getModelName(),
						uedfFileRecord.get(ctr).getEquipmentType(),
						uedfFileRecord.get(ctr).getSoftwareVersionNo(),
						uedfFileRecord.get(ctr).getLockCode1(),
						uedfFileRecord.get(ctr).getLockCode2(),
						uedfFileRecord.get(ctr).getMeidHex(),
						uedfFileRecord.get(ctr).getMeidDec(),
						uedfFileRecord.get(ctr).getImeiDec(),
						uedfFileRecord.get(ctr).getIccid(),
						uedfFileRecord.get(ctr).getErrorDescription(),
						uedfFileRecord.get(ctr).getComment()
				}
			);
		}
		
	}
	
	public DefaultTableModel getErrorModel() {
		return errorModel;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		
		Map<String, List<UedfErrors>> uedfErrors;
		Map<String, List<UedfFile>> uedfFileRecord = new HashMap<>();
 		
		if (event.getSource() == generateUedf) {
			uedfErrors = controller.getUedfErrors();
			setErrorTableData(uedfErrors);
			uedfFileRecord = controller.getUedfFileRecord(uedfErrors);
			
			/*uedfModel.setRowCount(0);
			for (String key : uedfFileRecord.keySet()) {
				setUedfLogsTableData(uedfFileRecord.get(key));
			}*/
			
			controller.createMultipleExcel(uedfFileRecord);
			
			JOptionPane.showMessageDialog(null, "File generation done! Please check Documents/exs_uedf folder.");
			
		}
		
	}
	
}
