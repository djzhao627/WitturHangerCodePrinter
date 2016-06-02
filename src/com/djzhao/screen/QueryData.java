package com.djzhao.screen;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.djzhao.db.OracleUtils;
import javax.swing.JPopupMenu;
import java.awt.Component;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Choice;
import javax.swing.JRadioButtonMenuItem;

public class QueryData extends JFrame {

	private static final long serialVersionUID = 3747900183958878605L;
	private JPanel contentPane;
	private JTextField textCode;
	private JTable table;
	private JComboBox<String> cboxLabelType;
	private JButton btnSearch;
	private JScrollPane tableResult;

	/** 类的对象（单利模式使用） */
	private static QueryData queryData = null;

	/** 数据库连接 */
	private Connection conn;
	/** 预处理语句 */
	private PreparedStatement ps;
	/** 结果集 */
	private ResultSet rs;
	/** SQL语句 */
	private String sql = "";

	/** 条码内容 */
	private String codeStr;
	/** JTable数据头-witturCE */
	private String[] headsOfWitturCE = { "ID", "证书编号", "系列号", "9位码", "订单号 ", "订单行", "下层物料号", "下层物料描述", "箱号", "开门方式",
			"二维码内容", "打印日期" };

	/** JTable数据头-witturCE_schindler */
	private String[] headsOfWitturCEAndSchindler = { "ID", "证书编号", "系列号", "9位码", "订单号 ", "订单行", "下层物料号", "下层物料描述", "箱号",
			"开门方式", "WitturCE二维码内容", "迅达标签物料描述", "迅达标签ID号", "迅达二维码内容", "出口", "打印日期" };

	/** JTable数据头-AMD_Barcode */
	private String[] headsOfAMDAndBarcode = { "ID", "产品代码", "产品类型", "证书编号", "9位码", "订单号", "订单行", "箱号", "下层物料号",
			"下层物料描述", "AMD条码内容", "基本编号", "后缀", "版本更新码", "供应商代码", "一维码内容", "打印日期" };

	/** JTable数据头-witturCE_barcode */
	private String[] headsOfWitturCEAndBarcode = { "ID", "证书编号", "系列号", "9位码", "订单号 ", "订单行", "下层物料号", "下层物料描述", "箱号",
			"开门方式", "WitturCE二维码内容", "基本编号", "后缀", "版本更新码", "供应商代码", "一维码内容", "打印日期" };

	/** JTable数据头-barcode_qrcode */
	private String[] headsOfBarcodeAndQRCode = { "ID", "9位码", "订单号 ", "订单行", "下层物料号", "下层物料描述", "箱号", "二维码内容", "基本编号",
			"后缀", "版本更新码", "供应商代码", "一维码内容", "打印日期" };
	/** JTable数据模型 */
	DefaultTableModel tableModel = new DefaultTableModel(null, new String[] { "暂无数据" });

	/** 单元格渲染 */
	DefaultTableCellRenderer render = new DefaultTableCellRenderer();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QueryData frame = new QueryData();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 获取QueryData对象。
	 * 
	 * @return
	 */
	public static QueryData getScreen() {
		if (queryData == null) {
			queryData = new QueryData();
		}
		return queryData;

	}

	/**
	 * Create the frame.
	 */
	private QueryData() {
		setResizable(false);
		this.setIconImage(new ImageIcon(getClass().getResource("/images/Wittur_Logo.gif")).getImage());
		setTitle("\u6570\u636E\u67E5\u8BE2");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 436);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaptionBorder);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("");
		label.setBounds(874, 10, 110, 109);
		ImageIcon icon = new ImageIcon(getClass().getResource("/images/Wittur_Logo.gif"));
		icon.setImage(
				icon.getImage().getScaledInstance(icon.getIconWidth(), icon.getIconHeight(), Image.SCALE_DEFAULT));
		label.setIcon(icon);
		contentPane.add(label);

		JLabel label_1 = new JLabel("\u6570\u636E\u67E5\u8BE2");
		label_1.setForeground(SystemColor.controlDkShadow);
		label_1.setFont(new Font("宋体", Font.ITALIC, 16));
		label_1.setBounds(10, 10, 110, 30);
		contentPane.add(label_1);

		textCode = new JTextField();
		textCode.setForeground(new Color(0, 0, 102));
		textCode.setFont(new Font("宋体", Font.ITALIC, 11));
		textCode.setBounds(420, 85, 249, 21);
		contentPane.add(textCode);
		textCode.setColumns(10);

		btnSearch = new JButton("\u67E5\u8BE2");
		btnSearch.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (!btnSearch.isEnabled()) {
					return;
				}
				codeStr = textCode.getText().toString().trim();
				if (codeStr.isEmpty() || codeStr == null) {
					JOptionPane.showMessageDialog(null, "请先输入要查询的数据！");
					return;
				}
				int selectedIndex = cboxLabelType.getSelectedIndex();
				// 冻结操作
				btnSearch.setEnabled(false);
				btnSearch.setText("查询...");
				switch (selectedIndex) {
				case 0:
					searchWitturCEData().execute();
					break;
				case 1:
				case 2:
					searchWitturCEAndSchindlerData().execute();
					break;
				case 3:
					searchAMDAndBarcodeData().execute();
					break;
				case 4:
					searchWitturCEAndBarcodeData().execute();
					break;
				case 5:
					searchBarcodeAndQRCodeData().execute();
					break;

				default:
					break;
				}
			}

		});
		btnSearch.setBounds(679, 83, 81, 23);
		contentPane.add(btnSearch);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 117, 974, 15);
		contentPane.add(separator);

		tableResult = new JScrollPane();
		tableResult.setBounds(10, 154, 974, 247);
		contentPane.add(tableResult);

		table = new JTable();
		table.setModel(tableModel);
		tableResult.setViewportView(table);

		JLabel label_3 = new JLabel("\u67E5\u8BE2\u7ED3\u679C");
		label_3.setForeground(SystemColor.controlShadow);
		label_3.setBounds(10, 129, 54, 15);
		contentPane.add(label_3);

		cboxLabelType = new JComboBox<String>();
		cboxLabelType.setFont(new Font("宋体", Font.PLAIN, 12));
		cboxLabelType.setModel(new DefaultComboBoxModel<String>(new String[] { "Wittur CE",
				"\u8FC5\u8FBE\u4E0D\u51FA\u53E3 + CE", "\u8FC5\u8FBE\u51FA\u53E3 + CE", "\u6761\u5F62\u7801 + AMD",
				"\u6761\u5F62\u7801 + CE", "\u6761\u5F62\u7801 + \u539F\u6302\u4EF6" }));
		cboxLabelType.setBounds(86, 85, 164, 21);
		contentPane.add(cboxLabelType);

		JLabel label_2 = new JLabel("\u6807\u7B7E\u7C7B\u522B\uFF1A");
		label_2.setFont(new Font("宋体", Font.PLAIN, 12));
		label_2.setBounds(10, 85, 66, 18);
		contentPane.add(label_2);

		JLabel label_4 = new JLabel("\u6761\u7801\u5185\u5BB9\uFF1A");
		label_4.setFont(new Font("宋体", Font.PLAIN, 12));
		label_4.setBounds(356, 86, 73, 18);
		contentPane.add(label_4);
		ImageIcon icon2 = new ImageIcon(getClass().getResource("/images/xunda.png"));
		icon2.setImage(icon2.getImage().getScaledInstance(350, 180, Image.SCALE_DEFAULT));
	}

	/**
	 * 搜索WitturCE数据。
	 * 
	 * @return
	 */
	public SwingWorker<Void, Void> searchWitturCEData() {
		SwingWorker<Void, Void> goSearch = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				// 新实例化一个
				tableModel = new DefaultTableModel(null, headsOfWitturCE);
				sql = "select id, certificateno, series, serialnumber, orno, pono, item, dsce, "
						+ "boxid, type, code, pdate from cbarcode_witturce where code = ?";
				conn = OracleUtils.getConnection();
				ps = conn.prepareStatement(sql);
				ps.setString(1, codeStr);
				rs = ps.executeQuery();
				/** 计数器 */
				int count = 0;
				// "ID", "证书编号", "系列号", "9位码", "订单号 ", "订单行", "下层物料号", "下层物料描述",
				// "箱号", "开门方式", "二维码内容",
				// "打印日期"
				while (rs.next()) {
					count++;
					tableModel.addRow(new String[] { rs.getInt(1) + "", rs.getString(2), rs.getString(3),
							rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
							rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12) });
				}
				rs.close();
				ps.close();
				conn.close();
				if (count == 0) {
					JOptionPane.showMessageDialog(null, "没有数据！");
				}
				table.setModel(tableModel);
				// 单元格渲染(居中)
				render.setHorizontalAlignment(SwingConstants.CENTER);
				// 获取列数
				int columnCount = table.getColumnCount();
				for (int i = 0; i < columnCount; i++) {
					table.getColumnModel().getColumn(i).setCellRenderer(render);
				}
				// 禁用JTable自动调整列宽
				table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				// 设置表格列宽
				table.getColumnModel().getColumn(0).setPreferredWidth(50);
				table.getColumnModel().getColumn(1).setPreferredWidth(150);
				table.getColumnModel().getColumn(2).setPreferredWidth(150);
				table.getColumnModel().getColumn(3).setPreferredWidth(120);
				table.getColumnModel().getColumn(4).setPreferredWidth(150);
				table.getColumnModel().getColumn(5).setPreferredWidth(50);
				table.getColumnModel().getColumn(6).setPreferredWidth(180);
				table.getColumnModel().getColumn(7).setPreferredWidth(300);
				table.getColumnModel().getColumn(8).setPreferredWidth(150);
				table.getColumnModel().getColumn(9).setPreferredWidth(60);
				table.getColumnModel().getColumn(10).setPreferredWidth(400);
				table.getColumnModel().getColumn(11).setPreferredWidth(180);
				table.updateUI();
				// 释放操作
				btnSearch.setEnabled(true);
				btnSearch.setText("查询");
				return null;
			}

		};
		return goSearch;
	}

	/**
	 * 搜索WitturCE和迅达数据。
	 * 
	 * @return
	 */
	public SwingWorker<Void, Void> searchWitturCEAndSchindlerData() {
		SwingWorker<Void, Void> goSearch = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				// 新实例化一个
				tableModel = new DefaultTableModel(null, headsOfWitturCEAndSchindler);
				sql = "select id, certificateno, series, serialnumber, orno, pono, item, dsce, "
						+ "boxid, type, witturcecode, materialDes, IDNumber, schindlerCode, NLD, pdate "
						+ "from cbarcode_witturceandschindler where witturcecode = ? or schindlerCode = ?";
				conn = OracleUtils.getConnection();
				ps = conn.prepareStatement(sql);
				ps.setString(1, codeStr);
				ps.setString(2, codeStr);
				rs = ps.executeQuery();
				/** 计数器 */
				int count = 0;
				// "ID", "证书编号", "系列号", "9位码", "订单号 ", "订单行", "下层物料号", "下层物料描述",
				// "箱号", "开门方式", "二维码内容","WitturCE二维码内容", "迅达标签物料描述", "迅达标签ID号",
				// "迅达二维码内容", "出口", "打印日期"
				while (rs.next()) {
					count++;
					tableModel.addRow(new String[] { rs.getInt(1) + "", rs.getString(2), rs.getString(3),
							rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
							rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13),
							rs.getString(14), rs.getString(15), rs.getString(16) });
				}
				rs.close();
				ps.close();
				conn.close();
				if (count == 0) {
					JOptionPane.showMessageDialog(null, "没有数据！");
				}
				table.setModel(tableModel);
				// 单元格渲染(居中)
				render.setHorizontalAlignment(SwingConstants.CENTER);
				// 获取列数
				int columnCount = table.getColumnCount();
				for (int i = 0; i < columnCount; i++) {
					table.getColumnModel().getColumn(i).setCellRenderer(render);
				}
				// 禁用JTable自动调整列宽
				table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				// 设置表格列宽
				table.getColumnModel().getColumn(0).setPreferredWidth(50);
				table.getColumnModel().getColumn(1).setPreferredWidth(150);
				table.getColumnModel().getColumn(2).setPreferredWidth(150);
				table.getColumnModel().getColumn(3).setPreferredWidth(120);
				table.getColumnModel().getColumn(4).setPreferredWidth(150);
				table.getColumnModel().getColumn(5).setPreferredWidth(50);
				table.getColumnModel().getColumn(6).setPreferredWidth(180);
				table.getColumnModel().getColumn(7).setPreferredWidth(300);
				table.getColumnModel().getColumn(8).setPreferredWidth(150);
				table.getColumnModel().getColumn(9).setPreferredWidth(60);
				table.getColumnModel().getColumn(10).setPreferredWidth(400);
				table.getColumnModel().getColumn(11).setPreferredWidth(200);
				table.getColumnModel().getColumn(12).setPreferredWidth(150);
				table.getColumnModel().getColumn(13).setPreferredWidth(420);
				table.getColumnModel().getColumn(14).setPreferredWidth(40);
				table.getColumnModel().getColumn(15).setPreferredWidth(180);
				table.updateUI();
				// 释放操作
				btnSearch.setEnabled(true);
				btnSearch.setText("查询");
				return null;
			}

		};
		return goSearch;
	}

	/**
	 * 搜索AMD和条形码数据。
	 * 
	 * @return
	 */
	public SwingWorker<Void, Void> searchAMDAndBarcodeData() {
		SwingWorker<Void, Void> goSearch = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				// 新实例化一个
				tableModel = new DefaultTableModel(null, headsOfAMDAndBarcode);
				sql = "select id, productcode, componenttype, certificatnum, serialnumber, orno, pono, "
						+ "boxid, item, dsce, amdcode, baseid, parts, updatecode, suppliercode, barcode, pdate "
						+ "from cbarcode_amdandbarcode where amdcode = ? or barcode = ?";
				conn = OracleUtils.getConnection();
				ps = conn.prepareStatement(sql);
				ps.setString(1, codeStr);
				ps.setString(2, codeStr);
				rs = ps.executeQuery();
				/** 计数器 */
				int count = 0;
				// "ID", "产品代码", "产品类型", "证书编号", "9位码", "订单号", "订单行", "箱号",
				// "下层物料号","下层物料描述", "AMD条码内容", "基本编号", "后缀", "版本更新码", "供应商代码",
				// "一维码内容","打印日期"
				while (rs.next()) {
					count++;
					tableModel.addRow(new String[] { rs.getInt(1) + "", rs.getString(2), rs.getString(3),
							rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
							rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13),
							rs.getString(14), rs.getString(15), rs.getString(16), rs.getString(17) });
				}
				rs.close();
				ps.close();
				conn.close();
				if (count == 0) {
					JOptionPane.showMessageDialog(null, "没有数据！");
				}
				table.setModel(tableModel);
				// 单元格渲染(居中)
				render.setHorizontalAlignment(SwingConstants.CENTER);
				// 获取列数
				int columnCount = table.getColumnCount();
				for (int i = 0; i < columnCount; i++) {
					table.getColumnModel().getColumn(i).setCellRenderer(render);
				}
				// 禁用JTable自动调整列宽
				table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				// 设置表格列宽
				table.getColumnModel().getColumn(0).setPreferredWidth(50);
				table.getColumnModel().getColumn(1).setPreferredWidth(200);
				table.getColumnModel().getColumn(2).setPreferredWidth(150);
				table.getColumnModel().getColumn(3).setPreferredWidth(180);
				table.getColumnModel().getColumn(4).setPreferredWidth(120);
				table.getColumnModel().getColumn(5).setPreferredWidth(150);
				table.getColumnModel().getColumn(6).setPreferredWidth(40);
				table.getColumnModel().getColumn(7).setPreferredWidth(150);
				table.getColumnModel().getColumn(8).setPreferredWidth(150);
				table.getColumnModel().getColumn(9).setPreferredWidth(200);
				table.getColumnModel().getColumn(10).setPreferredWidth(400);
				table.getColumnModel().getColumn(11).setPreferredWidth(120);
				table.getColumnModel().getColumn(12).setPreferredWidth(70);
				table.getColumnModel().getColumn(13).setPreferredWidth(80);
				table.getColumnModel().getColumn(14).setPreferredWidth(80);
				table.getColumnModel().getColumn(15).setPreferredWidth(250);
				table.getColumnModel().getColumn(16).setPreferredWidth(180);
				table.updateUI();
				// 释放操作
				btnSearch.setEnabled(true);
				btnSearch.setText("查询");
				return null;
			}

		};
		return goSearch;
	}

	/**
	 * 搜索WitturCE和条形码数据。
	 * 
	 * @return
	 */
	public SwingWorker<Void, Void> searchWitturCEAndBarcodeData() {
		SwingWorker<Void, Void> goSearch = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				// 新实例化一个
				tableModel = new DefaultTableModel(null, headsOfWitturCEAndBarcode);
				sql = "select id, certificateno, series, serialnumber, orno, pono, item, dsce, "
						+ "boxid, type, witturcecode, baseid, parts, updatecode, suppliercode, barcode, pdate "
						+ "from cbarcode_witturceandbarcode where witturcecode = ? or barcode = ?";
				conn = OracleUtils.getConnection();
				ps = conn.prepareStatement(sql);
				ps.setString(1, codeStr);
				ps.setString(2, codeStr);
				rs = ps.executeQuery();
				/** 计数器 */
				int count = 0;
				// "ID", "证书编号", "系列号", "9位码", "订单号 ", "订单行", "下层物料号", "下层物料描述",
				// "箱号","开门方式", "WitturCE二维码内容", "基本编号", "后缀", "版本更新码", "供应商代码",
				// "一维码内容", "打印日期"
				while (rs.next()) {
					count++;
					tableModel.addRow(new String[] { rs.getInt(1) + "", rs.getString(2), rs.getString(3),
							rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
							rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13),
							rs.getString(14), rs.getString(15), rs.getString(16), rs.getString(17) });
				}
				rs.close();
				ps.close();
				conn.close();
				if (count == 0) {
					JOptionPane.showMessageDialog(null, "没有数据！");
				}
				table.setModel(tableModel);
				// 单元格渲染(居中)
				render.setHorizontalAlignment(SwingConstants.CENTER);
				// 获取列数
				int columnCount = table.getColumnCount();
				for (int i = 0; i < columnCount; i++) {
					table.getColumnModel().getColumn(i).setCellRenderer(render);
				}
				// 禁用JTable自动调整列宽
				table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				// 设置表格列宽
				table.getColumnModel().getColumn(0).setPreferredWidth(50);
				table.getColumnModel().getColumn(1).setPreferredWidth(150);
				table.getColumnModel().getColumn(2).setPreferredWidth(150);
				table.getColumnModel().getColumn(3).setPreferredWidth(120);
				table.getColumnModel().getColumn(4).setPreferredWidth(150);
				table.getColumnModel().getColumn(5).setPreferredWidth(50);
				table.getColumnModel().getColumn(6).setPreferredWidth(180);
				table.getColumnModel().getColumn(7).setPreferredWidth(300);
				table.getColumnModel().getColumn(8).setPreferredWidth(150);
				table.getColumnModel().getColumn(9).setPreferredWidth(60);
				table.getColumnModel().getColumn(10).setPreferredWidth(400);
				table.getColumnModel().getColumn(11).setPreferredWidth(150);
				table.getColumnModel().getColumn(12).setPreferredWidth(70);
				table.getColumnModel().getColumn(13).setPreferredWidth(80);
				table.getColumnModel().getColumn(14).setPreferredWidth(80);
				table.getColumnModel().getColumn(15).setPreferredWidth(250);
				table.getColumnModel().getColumn(16).setPreferredWidth(180);
				table.updateUI();
				// 释放操作
				btnSearch.setEnabled(true);
				btnSearch.setText("查询");
				return null;
			}

		};
		return goSearch;
	}

	/**
	 * 搜索条形码 + 原挂件数据。
	 * 
	 * @return
	 */
	public SwingWorker<Void, Void> searchBarcodeAndQRCodeData() {
		SwingWorker<Void, Void> goSearch = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				// 新实例化一个
				tableModel = new DefaultTableModel(null, headsOfBarcodeAndQRCode);
				sql = "select id, serialnumber, orno, pono, item, dsce, boxid, qrcode, "
						+ "baseid, parts, updatecode, suppliercode, barcode, pdate "
						+ "from cbarcode_barcodeandqrcode where qrcode = ? or barcode = ?";
				conn = OracleUtils.getConnection();
				ps = conn.prepareStatement(sql);
				ps.setString(1, codeStr);
				ps.setString(2, codeStr);
				rs = ps.executeQuery();
				/** 计数器 */
				int count = 0;
				// "ID", "9位码", "订单号 ", "订单行", "下层物料号", "下层物料描述", "箱号",
				// "二维码内容","基本编号", "后缀", "版本更新码", "供应商代码", "一维码内容",
				// "打印日期"
				while (rs.next()) {
					count++;
					tableModel.addRow(new String[] { rs.getInt(1) + "", rs.getString(2), rs.getString(3),
							rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
							rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13),
							rs.getString(14) });
				}
				rs.close();
				ps.close();
				conn.close();
				if (count == 0) {
					JOptionPane.showMessageDialog(null, "没有数据！");
				}
				table.setModel(tableModel);
				// 单元格渲染(居中)
				render.setHorizontalAlignment(SwingConstants.CENTER);
				// 获取列数
				int columnCount = table.getColumnCount();
				for (int i = 0; i < columnCount; i++) {
					table.getColumnModel().getColumn(i).setCellRenderer(render);
				}
				// 禁用JTable自动调整列宽
				table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				// 设置表格列宽
				table.getColumnModel().getColumn(0).setPreferredWidth(50);
				table.getColumnModel().getColumn(1).setPreferredWidth(120);
				table.getColumnModel().getColumn(2).setPreferredWidth(130);
				table.getColumnModel().getColumn(3).setPreferredWidth(50);
				table.getColumnModel().getColumn(4).setPreferredWidth(150);
				table.getColumnModel().getColumn(5).setPreferredWidth(250);
				table.getColumnModel().getColumn(6).setPreferredWidth(150);
				table.getColumnModel().getColumn(7).setPreferredWidth(400);
				table.getColumnModel().getColumn(8).setPreferredWidth(120);
				table.getColumnModel().getColumn(9).setPreferredWidth(60);
				table.getColumnModel().getColumn(10).setPreferredWidth(80);
				table.getColumnModel().getColumn(11).setPreferredWidth(80);
				table.getColumnModel().getColumn(12).setPreferredWidth(250);
				table.getColumnModel().getColumn(13).setPreferredWidth(180);
				table.updateUI();
				// 释放操作
				btnSearch.setEnabled(true);
				btnSearch.setText("查询");
				return null;
			}

		};
		return goSearch;
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
