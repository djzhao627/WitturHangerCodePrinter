package com.djzhao.screen;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import com.djzho.print.SchindlerPrint02;
import com.google.zxing.WriterException;

public class SchindlerCode02 extends JFrame {

	private static final long serialVersionUID = -6901780832148795787L;
	private JPanel contentPane;
	private JPanel panel02;
	private JLabel printer;
	private JTextField serialNo;
	private JLabel idNO;
	private JComboBox<String> productName;
	private JComboBox<String> revision;
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
					SchindlerCode02 frame = new SchindlerCode02();
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
	public SchindlerCode02() {
		addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent e) {
				getCurrentPrinter(7);
			}

			public void windowLostFocus(WindowEvent e) {
			}
		});
		setResizable(false);
		setTitle("\u8FC5\u8FBE\u6807\u7B7E");
		this.setIconImage(new ImageIcon(getClass().getResource("/images/Wittur_Logo.gif")).getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 369);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaptionBorder);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JRadioButton radioButton = new JRadioButton("\u6807\u7B7E1");
		radioButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SchindlerCode sc = new SchindlerCode();
				sc.setVisible(true);
				dispose();
			}
		});
		radioButton.setBackground(SystemColor.inactiveCaptionBorder);
		radioButton.setBounds(20, 15, 66, 23);
		contentPane.add(radioButton);

		JRadioButton radioButton_1 = new JRadioButton("\u6807\u7B7E2");
		radioButton_1.setBackground(SystemColor.inactiveCaptionBorder);
		radioButton_1.setBounds(88, 15, 66, 23);
		radioButton_1.setSelected(true);
		contentPane.add(radioButton_1);

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(radioButton);
		buttonGroup.add(radioButton_1);

		nld = new JCheckBox("NLD");
		nld.setBounds(264, 15, 50, 23);
		nld.setBackground(SystemColor.inactiveCaptionBorder);
		contentPane.add(nld);

		JButton button = new JButton("\u6253\u5370");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				String serialNoStr = serialNo.getText().toString();

				if (serialNoStr.isEmpty()) {
					JOptionPane.showMessageDialog(null, "请填写Serial No！");
					return;
				}

				GenerateCode gc = new GenerateCode();

				String productNameStr = "SA WSB " + productName.getSelectedItem().toString();
				String releaseStr = "01";
				String revisionStr = revision.getSelectedItem().toString();
				String idNoStr = idNO.getText().toString();
				String defaultStr = "---WITTUR Elevator Components(Suzhou)Co.,LTD.215214SuzhouCN";

				String codeStr = "";
				String importer = "Wittur Holding GmbH85259WiedenzhausenDE";
				if (nld.isSelected()) {
					codeStr = productNameStr + releaseStr + revisionStr + idNoStr + serialNoStr + defaultStr + importer;
				} else {
					importer = " ";
					codeStr = productNameStr + releaseStr + revisionStr + idNoStr + serialNoStr + defaultStr;
				}

				try {
					// 生成二维码图片
					gc.generateQRCode(codeStr, 65, 65, "C:\\toolsZ\\codeZ\\schindler02.png");
				} catch (WriterException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				double paddingLeftDouble = Double.parseDouble(paddingLeft.getText().toString());
				double paddingTopDouble = Double.parseDouble(paddingTop.getText().toString());

				// 打印
				SchindlerPrint02 sp02 = new SchindlerPrint02(defaultPrinter.getText().toString(), productNameStr,
						idNoStr, releaseStr, revisionStr, serialNoStr);
				sp02.setPadding(paddingLeftDouble, paddingTopDouble);
				sp02.printcode();

				// 存储到本地
				Connection conn = null;
				Statement state = null;
				String sql = "insert into printRecord (productName, releaseNo, revisionNo, identificationNo, serialNo, importer, time) values ('"
						+ productNameStr + "', '" + releaseStr + "', '" + revisionStr + "', '" + idNoStr + "', '"
						+ serialNoStr + "', '" + importer + "', now())";
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
				spa.setID(7);
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
		defaultPrinter.setBounds(88, 284, 266, 15);
		contentPane.add(defaultPrinter);

		panel02 = new JPanel();
		panel02.setBorder(new TitledBorder(new LineBorder(new Color(128, 128, 128)), "\u8BE6\u7EC6\u8BBE\u7F6E",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(128, 128, 128)));
		panel02.setBounds(10, 44, 414, 230);
		panel02.setBackground(SystemColor.inactiveCaptionBorder);
		contentPane.add(panel02);
		panel02.setLayout(null);

		JLabel lblSaWsb = new JLabel("SA WSB");
		lblSaWsb.setFont(new Font("Arial", Font.PLAIN, 14));
		lblSaWsb.setBounds(10, 47, 58, 15);
		panel02.add(lblSaWsb);

		productName = new JComboBox<String>();
		productName.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				int index = productName.getSelectedIndex();
				switch (index) {
				case 0:
					idNO.setText("59344653");
					break;
				case 1:
					idNO.setText("59344650");
					break;
				case 2:
					idNO.setText("59344651");
					break;
				case 3:
					idNO.setText("59344652");
					break;

				default:
					break;
				}
			}
		});
		productName.setModel(new DefaultComboBoxModel<String>(new String[] { "05", "01", "02", "03" }));
		productName.setFont(new Font("Arial", Font.PLAIN, 12));
		productName.setBounds(65, 44, 40, 21);
		panel02.add(productName);

		idNO = new JLabel("59344653");
		idNO.setForeground(Color.BLACK);
		idNO.setFont(new Font("Arial", Font.PLAIN, 13));
		idNO.setBounds(10, 87, 58, 15);
		panel02.add(idNO);

		JLabel lblWittur = new JLabel("WITTUR");
		lblWittur.setFont(new Font("Arial", Font.PLAIN, 12));
		lblWittur.setBounds(10, 120, 54, 15);
		panel02.add(lblWittur);

		JLabel label_1 = new JLabel("01");
		label_1.setFont(new Font("Arial", Font.PLAIN, 13));
		label_1.setBounds(75, 87, 18, 15);
		panel02.add(label_1);

		revision = new JComboBox<String>();
		revision.setFont(new Font("Arial", Font.PLAIN, 13));
		revision.setModel(new DefaultComboBoxModel<String>(new String[] { "02", "03" }));
		revision.setBounds(93, 85, 40, 18);
		panel02.add(revision);

		JLabel lblF = new JLabel("MAX");
		lblF.setFont(new Font("Arial", Font.PLAIN, 10));
		lblF.setBounds(143, 47, 23, 15);
		panel02.add(lblF);

		JLabel lblgguguk = new JLabel("=GGU/GUK");
		lblgguguk.setFont(new Font("Arial", Font.PLAIN, 13));
		lblgguguk.setBounds(166, 47, 72, 15);
		panel02.add(lblgguguk);

		JLabel label_2 = new JLabel("F");
		label_2.setFont(new Font("Arial", Font.PLAIN, 14));
		label_2.setBounds(135, 47, 9, 15);
		panel02.add(label_2);

		JLabel lblbfk = new JLabel("=BFK");
		lblbfk.setFont(new Font("Arial", Font.PLAIN, 13));
		lblbfk.setBounds(150, 72, 72, 15);
		panel02.add(lblbfk);

		JLabel lblK = new JLabel("K");
		lblK.setFont(new Font("Arial", Font.PLAIN, 10));
		lblK.setBounds(143, 72, 23, 15);
		panel02.add(lblK);

		JLabel lblvkn = new JLabel("=VKN");
		lblvkn.setFont(new Font("Arial", Font.PLAIN, 13));
		lblvkn.setBounds(151, 99, 72, 15);
		panel02.add(lblvkn);

		JLabel lblV = new JLabel("V");
		lblV.setFont(new Font("Arial", Font.PLAIN, 14));
		lblV.setBounds(143, 99, 9, 15);
		panel02.add(lblV);

		JLabel lblvca = new JLabel("=VCA");
		lblvca.setFont(new Font("Arial", Font.PLAIN, 13));
		lblvca.setBounds(173, 120, 72, 15);
		panel02.add(lblvca);

		JLabel label_4 = new JLabel("MAX");
		label_4.setFont(new Font("Arial", Font.PLAIN, 10));
		label_4.setBounds(150, 120, 23, 15);
		panel02.add(label_4);

		JLabel lblV_1 = new JLabel("V");
		lblV_1.setFont(new Font("Arial", Font.PLAIN, 14));
		lblV_1.setBounds(142, 120, 9, 15);
		panel02.add(lblV_1);

		JLabel label_3 = new JLabel("");
		label_3.setIcon(new ImageIcon(SchindlerCode02.class.getResource("/images/schindler-logo.png")));
		label_3.setBounds(233, 47, 72, 67);
		panel02.add(label_3);

		JLabel label_5 = new JLabel("");
		label_5.setIcon(new ImageIcon(SchindlerCode02.class.getResource("/images/demoQRCode.png")));
		label_5.setBounds(315, 63, 80, 85);
		panel02.add(label_5);

		JLabel lblSchindlerTracebility = new JLabel("Schindler Tracebility");
		lblSchindlerTracebility.setFont(new Font("Arial", Font.BOLD, 11));
		lblSchindlerTracebility.setBounds(295, 47, 119, 15);
		panel02.add(lblSchindlerTracebility);

		JLabel lblNewLabel_1 = new JLabel("Serial no.");
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(39, 172, 80, 15);
		panel02.add(lblNewLabel_1);

		serialNo = new JTextField();
		serialNo.setBounds(107, 169, 198, 21);
		panel02.add(serialNo);
		serialNo.setColumns(10);

		JLabel label = new JLabel("\u5DE6\u8FB9\u8DDD\uFF1A");
		label.setBounds(30, 309, 54, 23);
		contentPane.add(label);

		paddingLeft = new JTextField();
		paddingLeft.setText("0");
		paddingLeft.setHorizontalAlignment(SwingConstants.CENTER);
		paddingLeft.setColumns(10);
		paddingLeft.setBounds(88, 312, 66, 21);
		contentPane.add(paddingLeft);

		JLabel label_6 = new JLabel("\u4E0A\u8FB9\u8DDD\uFF1A");
		label_6.setBounds(179, 312, 54, 20);
		contentPane.add(label_6);

		paddingTop = new JTextField();
		paddingTop.setText("0");
		paddingTop.setHorizontalAlignment(SwingConstants.CENTER);
		paddingTop.setColumns(10);
		paddingTop.setBounds(237, 312, 66, 21);
		contentPane.add(paddingTop);

		JButton button_1 = new JButton("\u5BFC\u51FA\u6570\u636E");
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				@SuppressWarnings("unused")
				ExportEXL export = new ExportEXL();
			}
		});
		button_1.setFont(new Font("宋体", Font.ITALIC, 12));
		button_1.setBounds(331, 310, 93, 23);
		contentPane.add(button_1);

		getCurrentPrinter(7);

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
