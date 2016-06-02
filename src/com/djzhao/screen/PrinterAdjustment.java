package com.djzhao.screen;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.djzhao.db.SQLiteJDBC;

public class PrinterAdjustment extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3083844395230283700L;
	private JPanel contentPane;
	private JLabel printer01;
	private JLabel printer02;
	private JLabel printer03;
	private JLabel printer04;
	private JLabel printer05;
	private JComboBox<String> printerList;
	private JComboBox<String> typeList;
	private JButton updatePrinterList;

	private static PrinterAdjustment printerAdjustment = null;
	private JLabel printer06;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrinterAdjustment frame = new PrinterAdjustment();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 
	 */
	public static PrinterAdjustment getScreen() {
		if (printerAdjustment == null) {
			printerAdjustment = new PrinterAdjustment();
		}
		return printerAdjustment;
	}

	/**
	 * Create the frame.
	 */
	private PrinterAdjustment() {
		setResizable(false);
		this.setIconImage(new ImageIcon(getClass().getResource("/images/Wittur_Logo.gif")).getImage());
		setTitle("\u6253\u5370\u673A\u5339\u914D");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 446, 485);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaptionBorder);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("");
		label.setBounds(302, 10, 110, 109);
		ImageIcon icon = new ImageIcon(getClass().getResource("/images/Wittur_Logo.gif"));
		icon.setImage(
				icon.getImage().getScaledInstance(icon.getIconWidth(), icon.getIconHeight(), Image.SCALE_DEFAULT));
		label.setIcon(icon);
		contentPane.add(label);

		JLabel lblamd = new JLabel("\u6253\u5370\u673A\u5339\u914D");
		lblamd.setFont(new Font("宋体", Font.ITALIC, 16));
		lblamd.setBounds(20, 10, 138, 25);
		contentPane.add(lblamd);

		JLabel label_2 = new JLabel("\u9009\u62E9\u7C7B\u522B\uFF1A");
		label_2.setFont(new Font("宋体", Font.PLAIN, 15));
		label_2.setBounds(23, 96, 116, 18);
		contentPane.add(label_2);

		typeList = new JComboBox<String>();
		typeList.setModel(new DefaultComboBoxModel<String>(new String[] {"Wittur CE", "\u8FC5\u8FBE\u4E0D\u51FA\u53E3 + CE", "\u8FC5\u8FBE\u51FA\u53E3 + CE", "\u6761\u5F62\u7801 + AMD", "\u6761\u5F62\u7801 + CE", "\u6761\u5F62\u7801 + \u539F\u6302\u4EF6"}));
		typeList.setIgnoreRepaint(true);
		typeList.setFont(new Font("宋体", Font.PLAIN, 16));
		typeList.setBounds(116, 94, 160, 21);
		contentPane.add(typeList);

		JLabel label_3 = new JLabel("\u5339\u914D\u6253\u5370\u673A\uFF1A");
		label_3.setFont(new Font("宋体", Font.PLAIN, 15));
		label_3.setBounds(23, 138, 116, 18);
		contentPane.add(label_3);

		printerList = new JComboBox<String>();
		printerList.setModel(
				new DefaultComboBoxModel<String>(new String[] { "printer1", "printer2", "printer3", "printer4", "printer5" }));
		printerList.setIgnoreRepaint(true);
		printerList.setFont(new Font("宋体", Font.ITALIC, 14));
		printerList.setBounds(116, 136, 296, 21);
		contentPane.add(printerList);

		JButton button = new JButton("\u5EFA\u7ACB\u5339\u914D");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {// 匹配按钮点击事件
				int type = typeList.getSelectedIndex();
				String print = printerList.getSelectedItem().toString();
				switch (type) {
				case 0:
					printer01.setText(print);
					updateSQLite(1, print);
					break;
				case 1:
					printer02.setText(print);
					updateSQLite(2, print);
					break;
				case 2:
					printer03.setText(print);
					updateSQLite(3, print);
					break;
				case 3:
					printer04.setText(print);
					updateSQLite(4, print);
					break;
				case 4:
					printer05.setText(print);
					updateSQLite(5, print);
					break;
				case 5:
					printer06.setText(print);
					updateSQLite(8, print);
					break;
				default:
					break;
				}
			}

		});
		button.setFont(new Font("宋体", Font.ITALIC, 13));
		button.setBounds(20, 48, 93, 23);
		contentPane.add(button);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 177, 420, 2);
		contentPane.add(separator);

		JLabel lblWittur = new JLabel("WitturCE");
		lblWittur.setHorizontalAlignment(SwingConstants.CENTER);
		lblWittur.setFont(new Font("宋体", Font.PLAIN, 16));
		lblWittur.setBounds(20, 220, 160, 15);
		contentPane.add(lblWittur);

		printer01 = new JLabel("Printer01");
		printer01.setForeground(SystemColor.activeCaption);
		printer01.setHorizontalAlignment(SwingConstants.CENTER);
		printer01.setFont(new Font("宋体", Font.ITALIC, 13));
		printer01.setBounds(190, 220, 223, 15);
		contentPane.add(printer01);

		JLabel label_1 = new JLabel("\u7C7B\u522B");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setForeground(SystemColor.windowBorder);
		label_1.setBounds(20, 189, 160, 15);
		contentPane.add(label_1);

		JLabel label_4 = new JLabel("\u6253\u5370\u673A");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setForeground(SystemColor.windowBorder);
		label_4.setBounds(190, 189, 222, 15);
		contentPane.add(label_4);

		JLabel lblWittur_1 = new JLabel("\u8FC5\u8FBE\u4E0D\u51FA\u53E3+CE");
		lblWittur_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblWittur_1.setFont(new Font("宋体", Font.PLAIN, 16));
		lblWittur_1.setBounds(20, 260, 160, 15);
		contentPane.add(lblWittur_1);

		printer02 = new JLabel("Printer02");
		printer02.setForeground(SystemColor.activeCaption);
		printer02.setHorizontalAlignment(SwingConstants.CENTER);
		printer02.setFont(new Font("宋体", Font.ITALIC, 13));
		printer02.setBounds(190, 260, 223, 15);
		contentPane.add(printer02);

		JLabel lblWittur_2 = new JLabel("\u8FC5\u8FBE\u51FA\u53E3+CE");
		lblWittur_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblWittur_2.setFont(new Font("宋体", Font.PLAIN, 16));
		lblWittur_2.setBounds(20, 300, 160, 15);
		contentPane.add(lblWittur_2);

		printer03 = new JLabel("Printer03");
		printer03.setForeground(SystemColor.activeCaption);
		printer03.setHorizontalAlignment(SwingConstants.CENTER);
		printer03.setFont(new Font("宋体", Font.ITALIC, 13));
		printer03.setBounds(190, 300, 223, 15);
		contentPane.add(printer03);

		JLabel lblWitturamd = new JLabel("\u6761\u5F62\u7801+AMD");
		lblWitturamd.setHorizontalAlignment(SwingConstants.CENTER);
		lblWitturamd.setFont(new Font("宋体", Font.PLAIN, 16));
		lblWitturamd.setBounds(20, 340, 160, 15);
		contentPane.add(lblWitturamd);

		printer04 = new JLabel("Printer04");
		printer04.setForeground(SystemColor.activeCaption);
		printer04.setHorizontalAlignment(SwingConstants.CENTER);
		printer04.setFont(new Font("宋体", Font.ITALIC, 13));
		printer04.setBounds(190, 340, 223, 15);
		contentPane.add(printer04);

		JLabel lblAmd = new JLabel("\u6761\u5F62\u7801+CE");
		lblAmd.setHorizontalAlignment(SwingConstants.CENTER);
		lblAmd.setFont(new Font("宋体", Font.PLAIN, 16));
		lblAmd.setBounds(20, 380, 160, 15);
		contentPane.add(lblAmd);

		printer05 = new JLabel("Printer05");
		printer05.setForeground(SystemColor.activeCaption);
		printer05.setHorizontalAlignment(SwingConstants.CENTER);
		printer05.setFont(new Font("宋体", Font.ITALIC, 13));
		printer05.setBounds(190, 380, 223, 15);
		contentPane.add(printer05);

		updatePrinterList = new JButton("\u5237\u65B0\u6253\u5370\u673A\u5217\u8868");
		updatePrinterList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				getPrinterList();
			}
		});
		updatePrinterList.setFont(new Font("宋体", Font.ITALIC, 13));
		updatePrinterList.setBounds(137, 48, 139, 23);
		contentPane.add(updatePrinterList);
		
		JLabel label_5 = new JLabel("\u6761\u5F62\u7801+\u539F\u6302\u4EF6");
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setFont(new Font("宋体", Font.PLAIN, 16));
		label_5.setBounds(20, 415, 160, 15);
		contentPane.add(label_5);
		
		printer06 = new JLabel("Printer05");
		printer06.setHorizontalAlignment(SwingConstants.CENTER);
		printer06.setForeground(SystemColor.activeCaption);
		printer06.setFont(new Font("宋体", Font.ITALIC, 13));
		printer06.setBounds(190, 415, 223, 15);
		contentPane.add(printer06);

		getPrinterAdjustment();
		getPrinterList();

	}

	/**
	 * 获取当前打印机匹配。
	 */
	private void getPrinterAdjustment() {
		Connection conn = new SQLiteJDBC().getConnection();
		try {
			Statement stat = conn.createStatement();
			String sql = "select ID, codeName, printName from Printer order by ID";
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				int i = rs.getInt("ID");
				switch (i) {
				case 1:
					printer01.setText(rs.getString("printName"));
					break;
				case 2:
					printer02.setText(rs.getString("printName"));
					break;
				case 3:
					printer03.setText(rs.getString("printName"));
					break;
				case 4:
					printer04.setText(rs.getString("printName"));
					break;
				case 5:
					printer05.setText(rs.getString("printName"));
					break;
				case 8:
					printer06.setText(rs.getString("printName"));
					break;
				default:
					break;
				}
			}
			stat.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取打印机列表。
	 */
	private void getPrinterList() {
		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		DocFlavor flavor = DocFlavor.BYTE_ARRAY.PNG;
		PrintService[] printService = PrintServiceLookup.lookupPrintServices(flavor, pras);
		String[] data = new String[printService.length];
		int i = 0;
		for (PrintService printer : printService) {
			data[i] = printer.getName();
			i++;
		}
		printerList.setModel(new DefaultComboBoxModel<String>(data));
	}

	/**
	 * 更新本地数据。
	 * 
	 * @param id
	 * @param printer
	 */
	private void updateSQLite(int id, String printer) {
		Connection conn = new SQLiteJDBC().getConnection();
		try {
			Statement stat = conn.createStatement();
			String sql = "update printer set printName = '" + printer + "' where ID = " + id;
			stat.executeUpdate(sql);
			stat.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
