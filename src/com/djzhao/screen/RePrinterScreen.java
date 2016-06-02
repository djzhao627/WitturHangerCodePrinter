package com.djzhao.screen;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.djzhao.code.GenerateCode;
import com.djzhao.db.OracleUtils;
import com.djzhao.db.SQLiteJDBC;
import com.djzhao.model.Hanger;
import com.djzho.print.AMD;
import com.djzho.print.Barcode;
import com.djzho.print.WitturCE;
import com.djzho.print.XD;
import com.djzho.print.XDC;
import com.google.zxing.WriterException;

public class RePrinterScreen extends JFrame {

	private static final long serialVersionUID = -1758496198559209461L;
	private JPanel contentPane;
	private JTextField defaultPrinter;
	private JComboBox<String> codeType;
	private JButton goPrint;
	private ImageIcon icon2;
	private String[] heads = { "订单号", "物料号", "订单行", "箱号", "描述", "序列码", "唯一码", "ID", "流水号" };
	private JTextField textField;
	private JTextField tab01_cert;
	private JTextField tab01_series;
	private JTextField tab01_type;
	private JTextField tab02_material;
	private JTextField tab02_id;
	private JTextField tab04_productCode;
	private JTextField tab04_type;
	private JTextField tab04_certNo;
	private JTextField tab05_baseID;
	private JTextField tab05_parts;
	private JTextField tab05_updateCode;
	private JTextField tab05_supplierCode;
	private JTextField tab03_material;
	private JTextField tab03_id;
	private JTabbedPane tabbedPane;
	private JPanel tab01;
	private JPanel tab02;
	private JPanel tab04;
	private JPanel tab03;
	private JPanel tab05;
	private JTable table;
	private JLabel label;
	private JTextField paddingLeft;
	private JTextField paddingTop;
	private JLabel label_7;
	private JTextField saleOrderNumber;
	private JLabel progressLabel;
	private JProgressBar progressBar;
	private JLabel progressText;

	private int[] selectedRows;
	private JButton searchBtn;

	private static RePrinterScreen rps = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RePrinterScreen frame = new RePrinterScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 提供给外部获取实例的静态方法。
	 * 
	 * @return
	 */
	public static RePrinterScreen getScreen() {
		if (rps == null) {
			rps = new RePrinterScreen();
		}
		return rps;
	}

	/**
	 * Create the frame.
	 */
	private RePrinterScreen() {
		addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent e) {
				int type = codeType.getSelectedIndex();
				getCurrentPrinter(type + 1);
			}

			public void windowLostFocus(WindowEvent e) {
			}
		});
		setBackground(new Color(224, 255, 255));
		setResizable(false);
		this.setIconImage(new ImageIcon(getClass().getResource("/images/Wittur_Logo.gif")).getImage());
		setTitle("\u8865\u6253\u5370");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 432);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 255, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		ImageIcon icon = new ImageIcon(getClass().getResource("/images/Wittur_Logo.gif"));
		icon.setImage(
				icon.getImage().getScaledInstance(icon.getIconWidth(), icon.getIconHeight(), Image.SCALE_DEFAULT));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(408, 62, 576, 332);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getTableHeader().setReorderingAllowed(false);

		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "\u8BA2\u5355\u53F7", "\u7269\u6599\u53F7", "\u8BA2\u5355\u884C", "\u7BB1\u53F7",
						"\u63CF\u8FF0", "\u5E8F\u5217\u7801", "\u552F\u4E00\u7801", "ID" }));
		scrollPane.setViewportView(table);

		codeType = new JComboBox<String>();
		codeType.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				int type = codeType.getSelectedIndex();
				switch (type) {
				case 0:
					getCurrentPrinter(1);
					tabbedPane.setSelectedComponent(tab01);
					break;
				case 1:
					getCurrentPrinter(2);
					tabbedPane.setSelectedComponent(tab02);
					break;
				case 2:
					getCurrentPrinter(3);
					tabbedPane.setSelectedComponent(tab03);
					break;
				case 3:
					getCurrentPrinter(4);
					tabbedPane.setSelectedComponent(tab04);
					break;
				case 4:
					getCurrentPrinter(5);
					tabbedPane.setSelectedComponent(tab05);
					break;
				default:
					break;
				}
			}

		});
		codeType.setIgnoreRepaint(true);
		codeType.setFont(new Font("宋体", Font.PLAIN, 16));
		codeType.setModel(new DefaultComboBoxModel<String>(new String[] { "Wittur", "\u8FC5\u8FBE\u4E0D\u51FA\u53E3",
				"\u8FC5\u8FBE\u51FA\u53E3", "AMD", "\u6761\u5F62\u7801" }));
		codeType.setBounds(169, 69, 160, 21);
		contentPane.add(codeType);

		JLabel label_1 = new JLabel("\u9009\u62E9\u6807\u7B7E\u7C7B\u522B\uFF1A");
		label_1.setFont(new Font("宋体", Font.PLAIN, 16));
		label_1.setBounds(30, 70, 116, 18);
		contentPane.add(label_1);

		goPrint = new JButton("\u6253\u5370");
		goPrint.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (!goPrint.isEnabled()) {
					return;
				}
				// 获取选中行
				selectedRows = table.getSelectedRows();
				// 进行打印
				goPrint().execute();
			}

			/**
			 * 进行打印。
			 * 
			 */
			private SwingWorker<Void, Void> goPrint() {
				SwingWorker<Void, Void> print = new SwingWorker<Void, Void>() {

					@Override
					protected Void doInBackground() throws Exception {
						// 如果数据不存在，返回
						if (selectedRows == null) {
							return null;
						}
						// 禁用其他操作
						codeType.setEnabled(false);
						goPrint.setEnabled(false);
						table.setEnabled(false);
						searchBtn.setEnabled(false);
						// 获取总数
						int count = selectedRows.length;
						// 设置进度条总数
						progressBar.setMaximum(count);
						// 设置当前打印数
						int currentIndex = 0;

						// 显示进度
						progressBar.setVisible(true);
						progressLabel.setVisible(true);
						progressText.setVisible(true);

						// 循环打印已选的数据行
						for (int selectedRow : selectedRows) {
							currentIndex++;
							// 设置进度条当前进度数
							progressBar.setValue(currentIndex);
							// 设置文字进度
							progressText.setText("" + currentIndex + "/" + count);

							/** 数据ID */
							// String idStr = table.getValueAt(selectedRow,
							// 7).toString();

							// boolean isPrinted = isPrinted(idStr);
							// 判断是否已经打印过
							// if (isPrinted) {
							// int dialog = JOptionPane.showConfirmDialog(null,
							// "该挂件已经打印过\n是否再次打印？");
							// if (dialog != 0) {
							// return;
							// }
							// }

							// 二维码/条码生成类 实例化
							GenerateCode gc = new GenerateCode();
							/** 打印机名 */
							String printNameStr = defaultPrinter.getText().toString();

							/** 9位码 */
							String serialNoStr = table.getValueAt(selectedRow, 6).toString();
							/** 销售单号 */
							String saleOrNoStr = table.getValueAt(selectedRow, 0).toString();
							/** 序列码 */
							String code = table.getValueAt(selectedRow, 5).toString();
							/** 订单行 */
							// String poNoStr = table.getValueAt(selectedRow,
							// 2).toString();
							/** 项目号 */
							String itemStr = table.getValueAt(selectedRow, 1).toString();
							/** 箱号 */
							// String boxIdStr = table.getValueAt(selectedRow,
							// 3).toString();
							/** 描述 */
							String dsceStr = table.getValueAt(selectedRow, 4).toString();
							/** 流水号 */
							String snoStr = table.getValueAt(selectedRow, 8).toString();

							String codeStr = "";
							int type = codeType.getSelectedIndex();

							double paddingLeftDouble = Double.parseDouble(paddingLeft.getText().toString());
							double paddingTopDouble = Double.parseDouble(paddingTop.getText().toString());

							switch (type) {
							case 0:
								int indexSharp = code.indexOf('#') + 1;
								String dateStr = "20" + code.substring(indexSharp, indexSharp + 2) + "/"
										+ code.substring(indexSharp + 2, indexSharp + 4) + "/"
										+ code.substring(indexSharp + 4, indexSharp + 6);
								codeStr = code + serialNoStr;
								String certStr = tab01_cert.getText().toString();
								String seriesStr = tab01_series.getText().toString();
								String typeStr = tab01_type.getText().toString();

								try {
									// 生成二维码
									gc.generateQRCode(codeStr, 69, 47, "C:\\toolsZ\\codeZ\\wittur.png");
								} catch (WriterException | IOException e1) {
									e1.printStackTrace();
								}

								// 打印
								WitturCE w = new WitturCE(printNameStr, certStr, seriesStr, serialNoStr, saleOrNoStr,
										dateStr, typeStr, code, itemStr, dsceStr, snoStr);
								w.setPadding(paddingLeftDouble, paddingTopDouble);
								w.printcode();

								// 标记已经打印过
								// setAsPrinted(idStr);

								// 储存记录
								// saveWitturData(certStr, seriesStr,
								// serialNoStr, saleOrNoStr, poNoStr, itemStr,
								// code,
								// boxIdStr, dsceStr);

								break;
							case 1:
								String materDsce = tab02_material.getText().toString();
								String idNoStr = tab02_id.getText().toString();
								codeStr = materDsce + "NANA" + idNoStr + serialNoStr
										+ "---Wittur CN215214SuzhouCNNANANANA";
								gc = new GenerateCode();
								try {
									// 生成二维码
									gc.generateQRCode(codeStr, 36, 36, "C:\\toolsZ\\codeZ\\xd.png");
								} catch (WriterException | IOException e1) {
									e1.printStackTrace();
								}

								// 打印
								XD xd = new XD(printNameStr, materDsce, idNoStr, serialNoStr);
								xd.setPadding(paddingLeftDouble, paddingTopDouble);
								xd.printcode();

								// 标记已经打印过
								// setAsPrinted(idStr);
								// 存储记录
								// saveSchindlerData(materDsce, idNoStr,
								// serialNoStr, saleOrNoStr, poNoStr, itemStr,
								// codeStr, boxIdStr, dsceStr, "NO");
								break;
							case 2:
								String materDsce2 = tab03_material.getText().toString();
								String idNoStr2 = tab03_id.getText().toString();
								codeStr = materDsce2 + "NANA" + idNoStr2 + serialNoStr
										+ "---Wittur CN215214SuzhouCNWittur HoldingGmbH85259WiedenzhausenDE";
								try {
									// 生成二维码
									gc.generateQRCode(codeStr, 37, 37, "C:\\toolsZ\\codeZ\\xdc.png");
								} catch (WriterException | IOException e1) {
									e1.printStackTrace();
								}

								// 打印
								XDC xdc = new XDC(printNameStr, materDsce2, idNoStr2, serialNoStr);
								xdc.setPadding(paddingLeftDouble, paddingTopDouble);
								xdc.printcode();

								// 标记已经打印过
								// setAsPrinted(idStr);
								// 存储记录
								// saveSchindlerData(materDsce2, idNoStr2,
								// serialNoStr, saleOrNoStr, poNoStr, itemStr,
								// codeStr, boxIdStr, dsceStr, "YES");
								break;
							case 3:
								String productCodeStr = tab04_productCode.getText().toString();
								String typeStr2 = tab04_type.getText().toString();
								String certStr2 = tab04_certNo.getText().toString();
								int indexSharp2 = code.indexOf('#') + 1;
								String dateStr2 = "20" + code.substring(indexSharp2, indexSharp2 + 2) + "/"
										+ code.substring(indexSharp2 + 2, indexSharp2 + 4) + "/"
										+ code.substring(indexSharp2 + 4, indexSharp2 + 6);
								codeStr = code + serialNoStr;
								try {
									// 生成二维码
									gc.generateQRCode(codeStr, 40, 40, "C:\\toolsZ\\codeZ\\amd.png");
								} catch (WriterException | IOException e1) {
									e1.printStackTrace();
								}

								// 打印
								AMD amd = new AMD(printNameStr, productCodeStr, typeStr2, certStr2, serialNoStr,
										saleOrNoStr, dateStr2, code, itemStr, dsceStr, snoStr);
								amd.setPadding(paddingLeftDouble, paddingTopDouble);
								amd.printcode();

								// 标记已经打印过
								// setAsPrinted(idStr);

								// 存储记录
								// saveAMDData(productCodeStr, typeStr2,
								// serialNoStr, saleOrNoStr, poNoStr, itemStr,
								// codeStr, boxIdStr, dsceStr, certStr2);

								break;
							case 4:
								String baseIDStr = tab05_baseID.getText().toString();
								String partsStr = tab05_parts.getText().toString();
								String updateCodeStr = tab05_updateCode.getText().toString();
								String supplierCodeStr = tab05_supplierCode.getText().toString();
								codeStr = "KM" + baseIDStr + partsStr + "." + updateCodeStr + supplierCodeStr
										+ serialNoStr;
								try {
									// 生成128维码
									gc.generateCode_128(codeStr, 299, 15, "C:\\toolsZ\\codeZ\\Code128.png");
								} catch (WriterException | IOException e1) {
									e1.printStackTrace();
								}

								// 打印
								Barcode c = new Barcode(printNameStr, codeStr);
								c.setPadding(paddingLeftDouble, paddingTopDouble);
								c.printcode();

								// 标记已经打印过
								// setAsPrinted(idStr);

								// 存储数据
								// saveCode128Data(baseIDStr, partsStr,
								// updateCodeStr, supplierCodeStr, serialNoStr,
								// codeStr);

								break;
							default:
								break;
							}
							// 休眠50毫秒
							Thread.sleep(50);

						}

						// 释放用其他操作
						codeType.setEnabled(true);
						goPrint.setEnabled(true);
						table.setEnabled(true);
						searchBtn.setEnabled(true);
						// 选中行清空
						selectedRows = null;
						return null;
					}
				};

				return print;
			}
		});
		goPrint.setBounds(236, 113, 93, 23);
		contentPane.add(goPrint);
		icon2 = new ImageIcon(getClass().getResource("/images/wittur.png"));
		icon2.setImage(icon2.getImage().getScaledInstance(350, 180, Image.SCALE_DEFAULT));

		JLabel label_6 = new JLabel("\u9ED8\u8BA4\u6253\u5370\u673A\uFF1A");
		label_6.setForeground(SystemColor.controlShadow);
		label_6.setFont(new Font("宋体", Font.PLAIN, 12));
		label_6.setBounds(30, 22, 116, 18);
		contentPane.add(label_6);

		JButton button_2 = new JButton("\u65B0\u5339\u914D");
		button_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PrinterAdjustment pa = PrinterAdjustment.getScreen();
				pa.setVisible(true);
			}
		});
		button_2.setActionCommand("\u65B0\u5339\u914D");
		button_2.setBounds(305, 20, 93, 23);
		contentPane.add(button_2);

		defaultPrinter = new JTextField();
		defaultPrinter.setHorizontalAlignment(SwingConstants.CENTER);
		defaultPrinter.setFont(new Font("宋体", Font.ITALIC, 10));
		defaultPrinter.setText("printer1");
		defaultPrinter.setBounds(98, 22, 187, 21);
		contentPane.add(defaultPrinter);
		defaultPrinter.setColumns(10);

		JLabel label_3 = new JLabel("\u624B\u52A8\u586B\u5199\u6570\u636E");
		label_3.setFont(new Font("宋体", Font.ITALIC, 15));
		label_3.setForeground(Color.DARK_GRAY);
		label_3.setBackground(Color.LIGHT_GRAY);
		label_3.setBounds(10, 145, 113, 21);
		contentPane.add(label_3);

		JLabel label_2 = new JLabel("\u6570\u91CF\uFF1A");
		label_2.setFont(new Font("宋体", Font.PLAIN, 16));
		label_2.setBounds(30, 117, 48, 18);
		contentPane.add(label_2);

		textField = new JTextField();
		textField.setEnabled(false);
		textField.setEditable(false);
		textField.setText("1");
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBounds(84, 114, 93, 22);
		contentPane.add(textField);
		textField.setColumns(10);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setEnabled(false);
		tabbedPane.setBounds(10, 170, 388, 191);
		contentPane.add(tabbedPane);

		tab01 = new JPanel();
		tabbedPane.addTab("Wittur", null, tab01, "Wittur 标签设置");
		tab01.setLayout(null);

		JLabel lblDemo = new JLabel("\u8BC1\u4E66\u7F16\u53F7\uFF1A");
		lblDemo.setFont(new Font("宋体", Font.ITALIC, 14));
		lblDemo.setBounds(22, 34, 76, 15);
		tab01.add(lblDemo);

		tab01_cert = new JTextField();
		tab01_cert.setFont(new Font("宋体", Font.PLAIN, 11));
		tab01_cert.setBounds(108, 34, 155, 15);
		tab01.add(tab01_cert);
		tab01_cert.setColumns(10);

		JLabel lblSeries = new JLabel("SERIES\uFF1A");
		lblSeries.setFont(new Font("宋体", Font.ITALIC, 14));
		lblSeries.setBounds(22, 73, 76, 15);
		tab01.add(lblSeries);

		tab01_series = new JTextField();
		tab01_series.setFont(new Font("宋体", Font.PLAIN, 12));
		tab01_series.setColumns(10);
		tab01_series.setBounds(108, 73, 155, 15);
		tab01.add(tab01_series);

		JLabel lblType = new JLabel("TYPE\uFF1A");
		lblType.setFont(new Font("宋体", Font.ITALIC, 14));
		lblType.setBounds(22, 115, 91, 15);
		tab01.add(lblType);

		tab01_type = new JTextField();
		tab01_type.setFont(new Font("宋体", Font.PLAIN, 12));
		tab01_type.setColumns(10);
		tab01_type.setBounds(108, 115, 155, 15);
		tab01.add(tab01_type);

		tab02 = new JPanel();
		tabbedPane.addTab("迅达不出口", null, tab02, "迅达不出口 标签设置");
		tab02.setLayout(null);

		JLabel lblMaterialDescription = new JLabel("Material description\uFF1A");
		lblMaterialDescription.setFont(new Font("宋体", Font.PLAIN, 14));
		lblMaterialDescription.setBounds(10, 53, 166, 15);
		tab02.add(lblMaterialDescription);

		tab02_material = new JTextField();
		tab02_material.setFont(new Font("宋体", Font.PLAIN, 14));
		tab02_material.setColumns(10);
		tab02_material.setBounds(165, 53, 190, 15);
		tab02.add(tab02_material);

		JLabel lblId = new JLabel("ID. number\uFF1A");
		lblId.setFont(new Font("宋体", Font.ITALIC, 14));
		lblId.setBounds(10, 92, 119, 15);
		tab02.add(lblId);

		tab02_id = new JTextField();
		tab02_id.setFont(new Font("宋体", Font.PLAIN, 14));
		tab02_id.setColumns(10);
		tab02_id.setBounds(165, 93, 190, 15);
		tab02.add(tab02_id);

		tab03 = new JPanel();
		tabbedPane.addTab("迅达出口", null, tab03, "迅达出口 标签设置");
		tab03.setLayout(null);

		JLabel label_4 = new JLabel("Material description\uFF1A");
		label_4.setFont(new Font("宋体", Font.PLAIN, 14));
		label_4.setBounds(10, 53, 166, 15);
		tab03.add(label_4);

		tab03_material = new JTextField();
		tab03_material.setFont(new Font("宋体", Font.PLAIN, 14));
		tab03_material.setColumns(10);
		tab03_material.setBounds(164, 53, 190, 15);
		tab03.add(tab03_material);

		JLabel label_5 = new JLabel("ID. number\uFF1A");
		label_5.setFont(new Font("宋体", Font.ITALIC, 14));
		label_5.setBounds(10, 92, 119, 15);
		tab03.add(label_5);

		tab03_id = new JTextField();
		tab03_id.setFont(new Font("宋体", Font.PLAIN, 14));
		tab03_id.setColumns(10);
		tab03_id.setBounds(164, 93, 190, 15);
		tab03.add(tab03_id);

		tab04 = new JPanel();
		tabbedPane.addTab("AMD", null, tab04, "AMD 标签设置");
		tab04.setLayout(null);

		JLabel lblProductCode = new JLabel("PRODUCT CODE\uFF1A");
		lblProductCode.setFont(new Font("宋体", Font.ITALIC, 14));
		lblProductCode.setBounds(10, 42, 134, 15);
		tab04.add(lblProductCode);

		tab04_productCode = new JTextField();
		tab04_productCode.setFont(new Font("宋体", Font.PLAIN, 14));
		tab04_productCode.setColumns(10);
		tab04_productCode.setBounds(154, 41, 218, 15);
		tab04.add(tab04_productCode);

		JLabel lblType_1 = new JLabel("Type\uFF1A");
		lblType_1.setFont(new Font("宋体", Font.ITALIC, 14));
		lblType_1.setBounds(10, 75, 76, 15);
		tab04.add(lblType_1);

		tab04_type = new JTextField();
		tab04_type.setFont(new Font("宋体", Font.PLAIN, 14));
		tab04_type.setColumns(10);
		tab04_type.setBounds(154, 74, 218, 15);
		tab04.add(tab04_type);

		JLabel lblCretNo = new JLabel("Cert. No\uFF1A");
		lblCretNo.setFont(new Font("宋体", Font.ITALIC, 14));
		lblCretNo.setBounds(10, 108, 76, 15);
		tab04.add(lblCretNo);

		tab04_certNo = new JTextField();
		tab04_certNo.setFont(new Font("宋体", Font.PLAIN, 14));
		tab04_certNo.setColumns(10);
		tab04_certNo.setBounds(154, 107, 218, 15);
		tab04.add(tab04_certNo);

		tab05 = new JPanel();
		tabbedPane.addTab("条形码", null, tab05, "条形码 标签设置");
		tab05.setLayout(null);

		JLabel label_20 = new JLabel("\u57FA\u672C\u7F16\u53F7\uFF1A");
		label_20.setFont(new Font("宋体", Font.ITALIC, 14));
		label_20.setBounds(10, 10, 76, 15);
		tab05.add(label_20);

		tab05_baseID = new JTextField();
		tab05_baseID.setFont(new Font("宋体", Font.PLAIN, 14));
		tab05_baseID.setColumns(10);
		tab05_baseID.setBounds(96, 10, 155, 15);
		tab05.add(tab05_baseID);

		JLabel label_21 = new JLabel("\u7EC4\u88C5\u4EF6\uFF1A");
		label_21.setFont(new Font("宋体", Font.ITALIC, 14));
		label_21.setBounds(10, 49, 76, 15);
		tab05.add(label_21);

		tab05_parts = new JTextField();
		tab05_parts.setFont(new Font("宋体", Font.PLAIN, 14));
		tab05_parts.setColumns(10);
		tab05_parts.setBounds(96, 49, 155, 15);
		tab05.add(tab05_parts);

		JLabel label_22 = new JLabel("\u66F4\u65B0\u7801\uFF1A");
		label_22.setFont(new Font("宋体", Font.ITALIC, 14));
		label_22.setBounds(10, 87, 76, 15);
		tab05.add(label_22);

		tab05_updateCode = new JTextField();
		tab05_updateCode.setFont(new Font("宋体", Font.PLAIN, 14));
		tab05_updateCode.setColumns(10);
		tab05_updateCode.setBounds(96, 87, 155, 15);
		tab05.add(tab05_updateCode);

		JLabel label_23 = new JLabel("\u4F9B\u5E94\u5546\u7801\uFF1A");
		label_23.setFont(new Font("宋体", Font.ITALIC, 14));
		label_23.setBounds(10, 125, 91, 15);
		tab05.add(label_23);

		tab05_supplierCode = new JTextField();
		tab05_supplierCode.setFont(new Font("宋体", Font.PLAIN, 14));
		tab05_supplierCode.setColumns(10);
		tab05_supplierCode.setBounds(96, 125, 155, 15);
		tab05.add(tab05_supplierCode);

		searchBtn = new JButton("\u67E5\u8BE2");
		searchBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 判断是否填写单号
				if (saleOrderNumber.getText().toString().isEmpty()) {
					JOptionPane.showMessageDialog(null, "请填写需要查询的订单号！");
					return;
				} else {
					// 查询
					getTypeLabelDataByORNO().execute();
				}
			}
		});
		searchBtn.setBounds(684, 29, 93, 23);
		contentPane.add(searchBtn);

		label = new JLabel("\u5DE6\u8FB9\u8DDD\uFF1A");
		label.setBounds(20, 371, 54, 23);
		contentPane.add(label);

		paddingLeft = new JTextField();
		paddingLeft.setText("0");
		paddingLeft.setHorizontalAlignment(SwingConstants.CENTER);
		paddingLeft.setColumns(10);
		paddingLeft.setBounds(78, 374, 66, 21);
		contentPane.add(paddingLeft);

		paddingTop = new JTextField();
		paddingTop.setText("0");
		paddingTop.setHorizontalAlignment(SwingConstants.CENTER);
		paddingTop.setColumns(10);
		paddingTop.setBounds(227, 374, 66, 21);
		contentPane.add(paddingTop);

		label_7 = new JLabel("\u4E0A\u8FB9\u8DDD\uFF1A");
		label_7.setBounds(169, 374, 54, 20);
		contentPane.add(label_7);

		JLabel label_8 = new JLabel("\u8BA2\u5355\u53F7\uFF1A");
		label_8.setBounds(434, 31, 54, 21);
		contentPane.add(label_8);

		saleOrderNumber = new JTextField();
		saleOrderNumber.setHorizontalAlignment(SwingConstants.CENTER);
		saleOrderNumber.setBounds(485, 31, 174, 21);
		contentPane.add(saleOrderNumber);
		saleOrderNumber.setColumns(10);

		progressLabel = new JLabel("\u8FDB\u5EA6\uFF1A");
		progressLabel.setFont(new Font("宋体", Font.ITALIC, 12));
		progressLabel.setBounds(189, 146, 36, 15);
		progressLabel.setVisible(false);
		contentPane.add(progressLabel);

		progressBar = new JProgressBar();
		progressBar.setBounds(231, 146, 104, 14);
		progressBar.setVisible(false);
		contentPane.add(progressBar);

		progressText = new JLabel("1/20");
		progressText.setFont(new Font("宋体", Font.ITALIC, 11));
		progressText.setBounds(344, 148, 54, 15);
		progressText.setVisible(false);
		contentPane.add(progressText);

		// 默认获取第一个打印机
		getCurrentPrinter(1);

		// 获取打印数据
		// getLabelData().execute();
	}

	protected void saveCode128Data(String baseIDStr, String partsStr, String updateCodeStr, String supplierCodeStr,
			String serialNoStr, String codeStr) {
		try {
			Connection conn = OracleUtils.getConnection();
			Statement stat = conn.createStatement();
			String sql = "insert into cbarcode_onecode (baseid, parts, updatecode, suppliercode, serialnumber, code) values('"
					+ baseIDStr + "', '" + partsStr + "', '" + updateCodeStr + "', '" + supplierCodeStr + "', '"
					+ serialNoStr + "', '" + codeStr + "')";
			stat.executeUpdate(sql);
			stat.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 存储wittur打印数据。
	 * 
	 * @param certStr
	 * @param seriesStr
	 * @param serialNoStr
	 * @param saleOrNoStr
	 * @param poNoStr
	 * @param itemStr
	 * @param code
	 * @param boxIdStr
	 * @param dsceStr
	 */
	protected void saveWitturData(String certStr, String seriesStr, String serialNoStr, String saleOrNoStr,
			String poNoStr, String itemStr, String code, String boxIdStr, String dsceStr) {
		try {
			Connection conn = OracleUtils.getConnection();
			Statement stat = conn.createStatement();
			String sql = "insert into cbarcode_wittur (certificateno, series, serialnumber, orno, pono, item, code, boxid, dsce) values('"
					+ certStr + "', '" + seriesStr + "', '" + serialNoStr + "', '" + saleOrNoStr + "', '" + poNoStr
					+ "', '" + itemStr + "', '" + code + "', '" + boxIdStr + "', '" + dsceStr + "') ";
			stat.executeUpdate(sql);
			stat.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 存储迅达打印数据记录。
	 * 
	 * @param materialStr
	 * @param idNoStr
	 * @param serialNoStr
	 * @param saleOrNoStr
	 * @param poNoStr
	 * @param itemStr
	 * @param code
	 * @param boxIdStr
	 * @param dsceStr
	 * @param nld
	 */
	protected void saveSchindlerData(String materialStr, String idNoStr, String serialNoStr, String saleOrNoStr,
			String poNoStr, String itemStr, String code, String boxIdStr, String dsceStr, String nld) {
		try {
			Connection conn = OracleUtils.getConnection();
			Statement stat = conn.createStatement();
			String sql = "insert into cbarcode_schindler (materialdes, idnumber, serialnumber, orno, pono, item, code, boxid, dsce, nld) values('"
					+ materialStr + "', '" + idNoStr + "', '" + serialNoStr + "', '" + saleOrNoStr + "', '" + poNoStr
					+ "', '" + itemStr + "', '" + code + "', '" + boxIdStr + "', '" + dsceStr + "','" + nld + "') ";
			stat.executeUpdate(sql);
			stat.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 存储AMD打印数据。
	 * 
	 * @param productCode
	 * @param type
	 * @param serialNoStr
	 * @param saleOrNoStr
	 * @param poNoStr
	 * @param itemStr
	 * @param code
	 * @param boxIdStr
	 * @param dsceStr
	 * @param certNo
	 */
	protected void saveAMDData(String productCode, String type, String serialNoStr, String saleOrNoStr, String poNoStr,
			String itemStr, String code, String boxIdStr, String dsceStr, String certNo) {
		try {
			Connection conn = OracleUtils.getConnection();
			Statement stat = conn.createStatement();
			String sql = "insert into cbarcode_amdcode (productcode, componenttype, serialnumber, orno, pono, item, code, boxid, dsce, certificatnum) values('"
					+ productCode + "', '" + type + "', '" + serialNoStr + "', '" + saleOrNoStr + "', '" + poNoStr
					+ "', '" + itemStr + "', '" + code + "', '" + boxIdStr + "', '" + dsceStr + "','" + certNo + "') ";
			stat.executeUpdate(sql);
			stat.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断是否已经打印。
	 * 
	 * @param idStr
	 * @return
	 */
	protected boolean isPrinted(String idStr) {
		try {
			Connection conn = OracleUtils.getConnection();
			Statement stat = conn.createStatement();
			String sql = "select NINECODE from cbarcode_pendant where STATE = 3 AND ID = " + idStr;
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				return true;
			}
			rs.close();
			stat.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 标记为已打印。
	 * 
	 * @param idStr
	 */
	protected void setAsPrinted(String idStr) {
		try {
			Connection conn = OracleUtils.getConnection();
			Statement stat = conn.createStatement();
			String sql = "update cbarcode_pendant set STATE = 3 where ID = '" + idStr + "'";
			stat.executeUpdate(sql);
			stat.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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

	/**
	 * 获取需要打印的数据。
	 * 
	 * @return
	 */
	private SwingWorker<Void, Void> getTypeLabelDataByORNO() {
		SwingWorker<Void, Void> getData = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				try {
					Connection conn = OracleUtils.getConnection();
					PreparedStatement ps = conn.prepareStatement(
							"SELECT ID, BOXID, ITEM, DSCE, ORNO, PONO, CODE, PYNDATE, STATE, STATION, NINECODE, SNO "
									+ "FROM CBARCODE_PENDANT WHERE ORNO = ?");
					ps.setString(1, saleOrderNumber.getText().toString());
					ResultSet rs = ps.executeQuery();
					List<Hanger> list = new ArrayList<Hanger>();
					Hanger hanger = null;
					while (rs.next()) {
						hanger = new Hanger();
						// 使用索引:rs.getInt(1)的效率，远高于使用字段名：rs.getInt("ID")
						hanger.setID(rs.getInt(1));
						hanger.setBoxId(rs.getString(2));
						hanger.setItem(rs.getString(3));
						hanger.setDsce(rs.getString(4));
						hanger.setOrNo(rs.getString(5));
						hanger.setPoNo(rs.getString(6));
						hanger.setCode(rs.getString(7));
						hanger.setPynDate(rs.getString(8));
						hanger.setState(rs.getInt(9));
						hanger.setStation(rs.getString(10));
						hanger.setNineCode(rs.getString(11));
						hanger.setSno(rs.getString(12));
						list.add(hanger);

					}
					rs.close();
					ps.close();
					conn.close();
					System.out.println("finish Load\tlist size is :" + list.size());
					if (list.size() == 0) {
						JOptionPane.showMessageDialog(null, "所查询的数据不存在！");
					}

					Object[][] data = new Object[list.size()][9];
					for (int i = 0; i < list.size(); i++) {
						data[i][0] = list.get(i).getOrNo();
						data[i][1] = list.get(i).getItem();
						data[i][2] = list.get(i).getPoNo();
						data[i][3] = list.get(i).getBoxId();
						data[i][4] = list.get(i).getDsce();
						data[i][5] = list.get(i).getCode();
						data[i][6] = list.get(i).getNineCode();
						data[i][7] = list.get(i).getID();
						data[i][8] = list.get(i).getSno();
					}
					table.setModel(new DefaultTableModel(data, heads));
					// 单元格渲染
					DefaultTableCellRenderer render = new DefaultTableCellRenderer();
					render.setHorizontalAlignment(SwingConstants.CENTER);
					table.getColumnModel().getColumn(0).setCellRenderer(render);
					table.getColumnModel().getColumn(1).setCellRenderer(render);
					table.getColumnModel().getColumn(2).setCellRenderer(render);
					table.getColumnModel().getColumn(3).setCellRenderer(render);
					table.getColumnModel().getColumn(4).setCellRenderer(render);
					table.getColumnModel().getColumn(5).setCellRenderer(render);
					table.getColumnModel().getColumn(6).setCellRenderer(render);
					table.getColumnModel().getColumn(7).setCellRenderer(render);
					table.getColumnModel().getColumn(8).setCellRenderer(render);
					// 单元格宽度
					table.getColumnModel().getColumn(0).setPreferredWidth(100);
					table.getColumnModel().getColumn(1).setPreferredWidth(150);
					table.getColumnModel().getColumn(2).setPreferredWidth(50);
					table.getColumnModel().getColumn(3).setPreferredWidth(120);
					table.getColumnModel().getColumn(4).setPreferredWidth(150);
					table.getColumnModel().getColumn(5).setPreferredWidth(350);
					table.getColumnModel().getColumn(6).setPreferredWidth(100);
					table.getColumnModel().getColumn(7).setPreferredWidth(50);
					table.getColumnModel().getColumn(8).setPreferredWidth(110);
					table.updateUI();
					// 默认选中第一行
					table.setRowSelectionInterval(0, 0);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return null;
			}

		};
		return getData;
	}
}
