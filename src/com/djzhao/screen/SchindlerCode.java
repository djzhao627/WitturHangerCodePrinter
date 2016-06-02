package com.djzhao.screen;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.djzhao.code.GenerateCode;
import com.djzhao.db.MySQLUtil;
import com.djzhao.db.SQLiteJDBC;
import com.djzho.print.SchindlerPrint;
import com.google.zxing.WriterException;

public class SchindlerCode extends JFrame {

	private static final long serialVersionUID = 2419546250459524297L;
	private JPanel contentPane;
	private JPanel panel01;
	private JCheckBoxMenuItem check01;
	private JCheckBoxMenuItem check02;
	private JCheckBoxMenuItem check03;
	private JComboBox<String> comboBox01;
	private JComboBox<String> comboBox02;
	private JComboBox<String> comboBox03;
	private JTextField serialNo;
	private JLabel printer;
	private JCheckBox nld;
	private JLabel defaultPrinter;
	private JTextField paddingLeft;
	private JTextField paddingTop;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SchindlerCode frame = new SchindlerCode();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SchindlerCode() {
		addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent e) {
				getCurrentPrinter(6);
			}

			public void windowLostFocus(WindowEvent e) {
			}
		});
		setResizable(false);
		setTitle("\u8FC5\u8FBE\u6807\u7B7E");
		this.setIconImage(new ImageIcon(getClass().getResource("/images/Wittur_Logo.gif")).getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 373);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaptionBorder);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JRadioButton radioButton = new JRadioButton("\u6807\u7B7E1");
		radioButton.setBackground(SystemColor.inactiveCaptionBorder);
		radioButton.setBounds(20, 15, 66, 23);
		radioButton.setSelected(true);
		contentPane.add(radioButton);

		JRadioButton radioButton_1 = new JRadioButton("\u6807\u7B7E2");
		radioButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SchindlerCode02 sc02 = new SchindlerCode02();
				sc02.setVisible(true);
				dispose();
			}
		});
		radioButton_1.setBackground(SystemColor.inactiveCaptionBorder);
		radioButton_1.setBounds(88, 15, 66, 23);
		contentPane.add(radioButton_1);

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(radioButton);
		buttonGroup.add(radioButton_1);

		panel01 = new JPanel();
		panel01.setBorder(new TitledBorder(new LineBorder(new Color(128, 128, 128)), "\u8BE6\u7EC6\u8BBE\u7F6E",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(128, 128, 128)));
		panel01.setBounds(10, 44, 414, 233);
		panel01.setBackground(SystemColor.inactiveCaptionBorder);
		contentPane.add(panel01);
		panel01.setLayout(null);

		JLabel lblSaWol = new JLabel("SA WOL 35 - 59344490");
		lblSaWol.setFont(new Font("Arial", Font.PLAIN, 15));
		lblSaWol.setBounds(27, 22, 214, 18);
		panel01.add(lblSaWol);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(SchindlerCode.class.getResource("/images/schindler-logo.png")));
		label.setBounds(309, 22, 83, 65);
		panel01.add(label);

		JLabel lblWittur = new JLabel("WITTUR");
		lblWittur.setFont(new Font("Arial", Font.PLAIN, 15));
		lblWittur.setBounds(27, 44, 77, 18);
		panel01.add(lblWittur);

		check01 = new JCheckBoxMenuItem(" OL35 6.0       59344436");
		check01.setFont(new Font("Arial", Font.PLAIN, 12));
		check01.setBackground(SystemColor.inactiveCaptionBorder);
		check01.setBounds(27, 65, 168, 18);
		check01.setSelected(true);
		panel01.add(check01);

		check02 = new JCheckBoxMenuItem(" OL35E 6.0    59344436");
		check02.setFont(new Font("Arial", Font.PLAIN, 12));
		check02.setBackground(SystemColor.inactiveCaptionBorder);
		check02.setBounds(27, 83, 168, 18);
		panel01.add(check02);

		check03 = new JCheckBoxMenuItem(" OL35M 6.0    59344436");
		check03.setFont(new Font("Arial", Font.PLAIN, 12));
		check03.setBackground(SystemColor.inactiveCaptionBorder);
		check03.setBounds(27, 101, 168, 18);
		panel01.add(check03);

		ButtonGroup buttonGroup2 = new ButtonGroup();
		buttonGroup2.add(check01);
		buttonGroup2.add(check02);
		buttonGroup2.add(check03);

		JLabel label_1 = new JLabel("00.");
		label_1.setFont(new Font("Arial", Font.PLAIN, 12));
		label_1.setBounds(205, 65, 23, 22);
		panel01.add(label_1);

		JLabel label_2 = new JLabel("00.");
		label_2.setFont(new Font("Arial", Font.PLAIN, 12));
		label_2.setBounds(205, 81, 23, 22);
		panel01.add(label_2);

		JLabel label_3 = new JLabel("00.");
		label_3.setFont(new Font("Arial", Font.PLAIN, 12));
		label_3.setBounds(205, 97, 23, 22);
		panel01.add(label_3);

		comboBox01 = new JComboBox<String>();
		comboBox01.setModel(new DefaultComboBoxModel<String>(new String[] { "03", "05" }));
		comboBox01.setBounds(227, 65, 40, 18);
		panel01.add(comboBox01);

		comboBox02 = new JComboBox<String>();
		comboBox02.setModel(new DefaultComboBoxModel<String>(new String[] { "03", "05" }));
		comboBox02.setBounds(227, 83, 40, 18);
		panel01.add(comboBox02);

		comboBox03 = new JComboBox<String>();
		comboBox03.setModel(new DefaultComboBoxModel<String>(new String[] { "03", "05" }));
		comboBox03.setBounds(227, 101, 40, 18);
		panel01.add(comboBox03);

		JLabel lblV = new JLabel("V");
		lblV.setFont(new Font("Arial", Font.PLAIN, 12));
		lblV.setBounds(75, 130, 10, 15);
		panel01.add(lblV);

		JLabel lblvkn = new JLabel("=VKN");
		lblvkn.setFont(new Font("Arial", Font.PLAIN, 12));
		lblvkn.setBounds(96, 130, 31, 15);
		panel01.add(lblvkn);

		JLabel lblNs = new JLabel("NS");
		lblNs.setFont(new Font("Arial", Font.PLAIN, 10));
		lblNs.setBounds(82, 130, 23, 16);
		panel01.add(lblNs);

		JLabel label_4 = new JLabel("V");
		label_4.setFont(new Font("Arial", Font.PLAIN, 12));
		label_4.setBounds(75, 145, 10, 15);
		panel01.add(label_4);

		JLabel lblCs = new JLabel("CS");
		lblCs.setFont(new Font("Arial", Font.PLAIN, 10));
		lblCs.setBounds(82, 145, 23, 16);
		panel01.add(lblCs);

		JLabel lblvck = new JLabel("=VCK");
		lblvck.setFont(new Font("Arial", Font.PLAIN, 12));
		lblvck.setBounds(96, 145, 31, 15);
		panel01.add(lblvck);

		JLabel label_7 = new JLabel("V");
		label_7.setFont(new Font("Arial", Font.PLAIN, 12));
		label_7.setBounds(75, 159, 10, 15);
		panel01.add(label_7);

		JLabel lblvca = new JLabel("=VCA");
		lblvca.setFont(new Font("Arial", Font.PLAIN, 12));
		lblvca.setBounds(96, 159, 31, 15);
		panel01.add(lblvca);

		JLabel lblTs = new JLabel("TS");
		lblTs.setFont(new Font("Arial", Font.PLAIN, 10));
		lblTs.setBounds(82, 159, 23, 16);
		panel01.add(lblTs);

		JLabel label_10 = new JLabel("V");
		label_10.setFont(new Font("Arial", Font.PLAIN, 12));
		label_10.setBounds(75, 174, 10, 15);
		panel01.add(label_10);

		JLabel lblBrake = new JLabel("Brake");
		lblBrake.setFont(new Font("Arial", Font.PLAIN, 9));
		lblBrake.setBounds(82, 174, 31, 16);
		panel01.add(lblBrake);

		JLabel lblfc = new JLabel("=FC");
		lblfc.setFont(new Font("Arial", Font.PLAIN, 12));
		lblfc.setBounds(106, 174, 31, 15);
		panel01.add(lblfc);

		JLabel lblSerialNo = new JLabel("Serial no.");
		lblSerialNo.setFont(new Font("Arial", Font.PLAIN, 14));
		lblSerialNo.setBounds(31, 205, 73, 18);
		panel01.add(lblSerialNo);

		serialNo = new JTextField();
		serialNo.setBounds(96, 205, 171, 18);
		panel01.add(serialNo);
		serialNo.setColumns(10);

		JLabel lblSchindlerTracebility = new JLabel("SChindler Tracebility");
		lblSchindlerTracebility.setFont(new Font("宋体", Font.BOLD, 12));
		lblSchindlerTracebility.setBounds(262, 120, 152, 15);
		panel01.add(lblSchindlerTracebility);

		JLabel label_5 = new JLabel("");
		label_5.setIcon(new ImageIcon(SchindlerCode.class.getResource("/images/demoQRCode.png")));
		label_5.setBounds(294, 140, 100, 78);
		panel01.add(label_5);

		nld = new JCheckBox("NLD");
		nld.setBounds(264, 15, 50, 23);
		nld.setBackground(SystemColor.inactiveCaptionBorder);
		contentPane.add(nld);

		JButton button = new JButton("\u6253\u5370");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				String sNo = serialNo.getText().toString();

				if (sNo.isEmpty()) {
					JOptionPane.showMessageDialog(null, "请填写Serial No！");
					return;
				}

				GenerateCode gc = new GenerateCode();

				String productName = "SA WOL 35";
				String release = "00";
				String revision = "03";
				String idNo = "59344490";
				String defaultStr = "---WITTUR Elevator Components(Suzhou)Co.,LTD.215214SuzhouCN";
				String codeStr = "";
				int hookWhich = 0;
				if (check01.isSelected()) {
					revision = (String) comboBox01.getSelectedItem();
					hookWhich = 0;
				} else if (check02.isSelected()) {
					revision = (String) comboBox02.getSelectedItem();
					hookWhich = 1;
				} else if (check03.isSelected()) {
					revision = (String) comboBox03.getSelectedItem();
					hookWhich = 2;
				}
				String importer = "Wittur Holding GmbH85259WiedenzhausenDE";
				if (nld.isSelected()) {
					codeStr = productName + release + revision + idNo + sNo + defaultStr + importer;
				} else {
					importer = " ";
					codeStr = productName + release + revision + idNo + sNo + defaultStr;
				}
				try {// 生成二维码
					gc.generateQRCode(codeStr, 65, 65, "C:\\toolsZ\\codeZ\\schindler01.png");
				} catch (WriterException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				// 打印
				SchindlerPrint sp = new SchindlerPrint(defaultPrinter.getText().toString(), release, revision, sNo,
						hookWhich);
				double paddingLeftDouble = Double.parseDouble(paddingLeft.getText().toString());
				double paddingTopDouble = Double.parseDouble(paddingTop.getText().toString());
				sp.setPadding(paddingLeftDouble, paddingTopDouble);
				sp.printcode();

				// 存储到本地
				Connection conn = null;
				Statement state = null;
				String sql = "insert into printRecord (productName, releaseNo, revisionNo, identificationNo, serialNo, importer, time) values ('"
						+ productName + "', '" + release + "', '" + revision + "', '" + idNo + "', '" + sNo + "', '"
						+ importer + "', now())";
				try {
					conn = MySQLUtil.getConn();
					state = conn.createStatement();
					state.executeUpdate(sql);
					state.close();
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		button.setBounds(320, 15, 93, 23);
		button.setBackground(SystemColor.inactiveCaptionBorder);
		contentPane.add(button);

		printer = new JLabel("\u6253\u5370\u673A");
		printer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				printer.setForeground(Color.red);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				printer.setForeground(Color.gray);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				SchindlerPrinterAdjustment spa = new SchindlerPrinterAdjustment();
				spa.setID(6);
				spa.setVisible(true);
			}
		});
		printer.setForeground(new Color(128, 128, 128));
		printer.setHorizontalAlignment(SwingConstants.CENTER);
		printer.setFont(new Font("宋体", Font.PLAIN, 12));
		printer.setBounds(20, 284, 54, 15);
		contentPane.add(printer);

		defaultPrinter = new JLabel("default printer");
		defaultPrinter.setForeground(new Color(192, 192, 192));
		defaultPrinter.setFont(new Font("宋体", Font.ITALIC, 11));
		defaultPrinter.setBounds(88, 284, 226, 15);
		contentPane.add(defaultPrinter);

		JLabel label_6 = new JLabel("\u5DE6\u8FB9\u8DDD\uFF1A");
		label_6.setBounds(30, 306, 54, 23);
		contentPane.add(label_6);

		paddingLeft = new JTextField();
		paddingLeft.setHorizontalAlignment(SwingConstants.CENTER);
		paddingLeft.setText("0");
		paddingLeft.setBounds(88, 309, 66, 21);
		contentPane.add(paddingLeft);
		paddingLeft.setColumns(10);

		JLabel label_8 = new JLabel("\u4E0A\u8FB9\u8DDD\uFF1A");
		label_8.setBounds(179, 309, 54, 20);
		contentPane.add(label_8);

		paddingTop = new JTextField();
		paddingTop.setHorizontalAlignment(SwingConstants.CENTER);
		paddingTop.setText("0");
		paddingTop.setColumns(10);
		paddingTop.setBounds(237, 309, 66, 21);
		contentPane.add(paddingTop);
		
		JButton btnNewButton = new JButton("导出数据");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				@SuppressWarnings("unused")
				ExportEXL export = new ExportEXL();
			}
		});
		btnNewButton.setFont(new Font("宋体", Font.ITALIC, 12));
		btnNewButton.setBounds(331, 308, 93, 23);
		contentPane.add(btnNewButton);

		getCurrentPrinter(6);

	}

	/**
	 * 获取当前匹配的打印机。
	 * 
	 * @param id
	 */
	private void getCurrentPrinter(int id) {
		try {
			Connection conn = new SQLiteJDBC().getConnection();
			Statement stat = conn.createStatement();
			String sql = "select printName from Printer where ID = " + id;
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				defaultPrinter.setText(rs.getString("printName"));
				break;
			}
			rs.close();
			stat.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
