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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.djzhao.code.GenerateCode;
import com.djzhao.data.SourceData;
import com.djzhao.data.V7Model;
import com.djzhao.db.OracleUtils;
import com.djzhao.db.SQLiteJDBC;
import com.djzhao.model.Hanger;
import com.djzho.print.AMDAndBarcode;
import com.djzho.print.BarcodeAndQRCode;
import com.djzho.print.WitturCE;
import com.djzho.print.WitturCEAndBarcode;
import com.djzho.print.WitturCEAndSchindler;
import com.google.zxing.WriterException;

public class AutomaticRePrinterScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField defaultPrinter;
	private JComboBox<String> codeType;
	private JButton goPrint;
	private ImageIcon icon2;
	private JTable table;
	private JLabel label;
	private JTextField paddingLeft;
	private JTextField paddingTop;
	private JLabel label_7;
	private JTextField saleOrderNumber;
	private JButton searchBtn;
	private JLabel progressLabel;
	private JProgressBar progressBar;
	private JLabel progressText;

	/** JTable数据头 */
	private String[] heads = { "订单号", "物料号", "订单行", "箱号", "描述", "序列码", "唯一码", "ID", "流水号" };
	/** JTable选中行 */
	private int[] selectedRows = null;
	/** JTable数据模型 */
	DefaultTableModel tableModel = new DefaultTableModel(null, heads);

	/** 抓取数据存储列表 */
	ArrayList<String> printDataList = null;
	/** 已经匹配的打印机名称数组 */
	private String[] printers = null;
	/** V7表数据列表 */
	private static ArrayList<V7Model> v7List = null;
	/** 单利对象 */
	private static AutomaticRePrinterScreen automaticRePrinterScreen = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AutomaticRePrinterScreen frame = new AutomaticRePrinterScreen();
					frame.setLocation(110, 127);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static AutomaticRePrinterScreen getScreen(ArrayList<V7Model> v7) {
		if (automaticRePrinterScreen == null) {
			v7List = v7;
			automaticRePrinterScreen = new AutomaticRePrinterScreen();
		}
		return automaticRePrinterScreen;
	}

	/**
	 * Create the frame.
	 */
	private AutomaticRePrinterScreen() {
		UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("宋体", Font.ITALIC, 13)));
		UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("宋体", Font.ITALIC, 13)));
		addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent e) {
				int type = codeType.getSelectedIndex();
				// 更新当前打印机
				getCurrentPrinter(type + 1);
				// 刷新打印机列表
				getAllPrinter();
			}

			public void windowLostFocus(WindowEvent e) {
			}
		});
		setBackground(new Color(224, 255, 255));
		setResizable(false);
		// 左上角LOGO
		this.setIconImage(new ImageIcon(getClass().getResource("/images/Wittur_Logo.gif")).getImage());
		// 界面title
		setTitle("\u8865\u6253\u5370");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 512);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 255, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		// 获取logo图
		ImageIcon icon = new ImageIcon(getClass().getResource("/images/Wittur_Logo.gif"));
		// 设置图片缩放
		icon.setImage(
				icon.getImage().getScaledInstance(icon.getIconWidth(), icon.getIconHeight(), Image.SCALE_DEFAULT));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 43, 974, 396);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getTableHeader().setReorderingAllowed(false);

		table.setModel(
				new DefaultTableModel(new Object[][] {},
						new String[] { "\u8BA2\u5355\u53F7", "\u7269\u6599\u53F7", "\u8BA2\u5355\u884C", "\u7BB1\u53F7",
								"\u63CF\u8FF0", "\u5E8F\u5217\u7801", "\u552F\u4E00\u7801", "ID",
								"\u6D41\u6C34\u53F7" }));
		scrollPane.setViewportView(table);

		codeType = new JComboBox<String>();
		codeType.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				int type = codeType.getSelectedIndex();
				switch (type) {
				case 0:
					getCurrentPrinter(1);
					break;
				case 1:
					getCurrentPrinter(2);
					break;
				case 2:
					getCurrentPrinter(3);
					break;
				case 3:
					getCurrentPrinter(4);
					break;
				case 4:
					getCurrentPrinter(5);
					break;
				case 5:// 打印机6，7被单迅达标签占用
					getCurrentPrinter(8);
					break;
				default:
					break;
				}
			}

		});
		codeType.setIgnoreRepaint(true);
		codeType.setFont(new Font("宋体", Font.ITALIC, 12));
		codeType.setModel(new DefaultComboBoxModel<String>(new String[] { "Wittur CE",
				"\u8FC5\u8FBE\u4E0D\u51FA\u53E3 + CE", "\u8FC5\u8FBE\u51FA\u53E3 + CE", "\u6761\u5F62\u7801 + AMD",
				"\u6761\u5F62\u7801 + CE", "\u6761\u5F62\u7801 + \u539F\u6302\u4EF6" }));
		codeType.setBounds(523, 451, 104, 21);
		contentPane.add(codeType);

		JLabel label_1 = new JLabel("\u6807\u7B7E\u7C7B\u522B\uFF1A");
		label_1.setFont(new Font("宋体", Font.ITALIC, 12));
		label_1.setBounds(464, 449, 60, 22);
		contentPane.add(label_1);

		goPrint = new JButton("\u6253\u5370");
		goPrint.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				// 判断按钮状态
				if (!goPrint.isEnabled()) {
					return;
				}

				// 判断数据是否异常
				if (v7List == null) {
					JOptionPane.showMessageDialog(null, "V7表数据无法加载，请重新启动程序！");
					return;
				}

				// 判断边距输入是否合理
				if (!paddingLeft.getText().toString().trim().matches("^[-+]?(([0-9]+)(.[0-9]+)?|(.[0-9]+))$")) {
					JOptionPane.showMessageDialog(null, "左边距输入非法内容！请检查！");
					paddingLeft.setFocusable(true);
					return;
				}
				if (!paddingTop.getText().toString().trim().matches("^[-+]?(([0-9]+)(.[0-9]+)?|(.[0-9]+))$")) {
					JOptionPane.showMessageDialog(null, "上边距输入非法内容！请检查！");
					paddingTop.setFocusable(true);
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
					protected Void doInBackground() {
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

						// 二维码/条码生成类 实例化
						GenerateCode gc = new GenerateCode();

						// 循环打印已选的数据行

						// 获取边距
						double paddingLeftDouble = Double.parseDouble(paddingLeft.getText().toString().trim());
						double paddingTopDouble = Double.parseDouble(paddingTop.getText().toString().trim());

						// 循环进行逐条解析
						for (int selectedRow : selectedRows) {
							currentIndex++;
							// 设置进度条当前进度数
							progressBar.setValue(currentIndex);
							// 设置文字进度
							progressText.setText("" + currentIndex + "/" + count);

							/** 数据ID */
							String idStr = table.getValueAt(selectedRow, 7).toString();

							// boolean isPrinted = isPrinted(idStr);
							// 判断是否已经打印过
							// if (isPrinted) {
							// int dialog = JOptionPane.showConfirmDialog(null,
							// "该挂件已经打印过\n是否再次打印？");
							// if (dialog != 0) {
							// return;
							// }
							// }

							/** 9位码 */
							String serialNoStr = table.getValueAt(selectedRow, 6).toString();
							/** 销售单号(订单号) */
							String saleOrNoStr = table.getValueAt(selectedRow, 0).toString();
							/** 序列码 */
							String code = table.getValueAt(selectedRow, 5).toString();
							/** 订单行 */
							String poNoStr = table.getValueAt(selectedRow, 2).toString();
							/** 项目号 */
							String itemStr = table.getValueAt(selectedRow, 1).toString();
							/** 描述 */
							String dsceStr = table.getValueAt(selectedRow, 4).toString();
							/** 流水号 */
							String snoStr = table.getValueAt(selectedRow, 8).toString();
							/** 箱号 */
							String boxIdStr = table.getValueAt(selectedRow, 3).toString();

							String codeStr = "";

							// 清空列表
							printDataList = null;
							// 存入新数据
							printDataList = SourceData.getData(new String[] { itemStr, dsceStr, saleOrNoStr, poNoStr },
									v7List);

							// 列表长度
							int listSize = 0;
							if (printDataList == null || (listSize = printDataList.size()) == 0) {

								int response = JOptionPane
										.showConfirmDialog(null,
												"数据无法正常读取！" + "\n订单号：" + saleOrNoStr + "  订单行：" + poNoStr + "  ID："
														+ idStr + "   \n是否跳过此条记录继续打印？",
												"发生错误！", JOptionPane.YES_NO_OPTION);
								if (response != 0) {
									// 释放用其他操作
									codeType.setEnabled(true);
									goPrint.setEnabled(true);
									table.setEnabled(true);
									searchBtn.setEnabled(true);
									return null;
								} else {
									continue;
								}
							}

							// 标签类别
							int type = Integer.parseInt(printDataList.get(listSize - 1));

							// [start]
							switch (type) {
							// 威特自由市场：单CE标签。5个变量
							case 0:
								int indexSharp = code.indexOf('#') + 1;
								String dateStr = "20" + code.substring(indexSharp, indexSharp + 2) + "/"
										+ code.substring(indexSharp + 2, indexSharp + 4) + "/"
										+ code.substring(indexSharp + 4, indexSharp + 6);
								codeStr = code + serialNoStr;

								// excel抓取的数据
								String certStr = printDataList.get(0);
								String seriesStr = printDataList.get(1);
								String typeStr = printDataList.get(2);

								try {
									// 生成二维码
									gc.generateQRCode(codeStr, 69, 47, "C:\\toolsZ\\codeZ\\wittur.png");
								} catch (WriterException | IOException e1) {
									e1.printStackTrace();
								}

								// 打印
								WitturCE witturCE = new WitturCE(printers[0], certStr, seriesStr, serialNoStr,
										saleOrNoStr, dateStr, typeStr, code, itemStr, dsceStr, snoStr);
								witturCE.setPadding(paddingLeftDouble, paddingTopDouble);
								System.out.println(witturCE.toString());
								witturCE.printcode();

								// TODO 标记已经打印过
								// setAsPrinted(idStr);

								// 储存记录
								saveWitturCEData(certStr, seriesStr, serialNoStr, saleOrNoStr, poNoStr, itemStr,
										codeStr, boxIdStr, dsceStr, typeStr);

								break;
							// 迅达不出口标签+CE标签。8个变量
							case 1:
								// CE标签
								indexSharp = code.indexOf('#') + 1;
								dateStr = "20" + code.substring(indexSharp, indexSharp + 2) + "/"
										+ code.substring(indexSharp + 2, indexSharp + 4) + "/"
										+ code.substring(indexSharp + 4, indexSharp + 6);
								codeStr = code + serialNoStr;
								certStr = printDataList.get(0);
								seriesStr = printDataList.get(1);
								typeStr = printDataList.get(2);
								// 迅达标签
								String materDsce = printDataList.get(3);
								String idNoStr = printDataList.get(4);
								String codeStr2 = materDsce + "NANA" + idNoStr + serialNoStr
										+ "---Wittur CN215214SuzhouCNNANANANA";
								gc = new GenerateCode();
								try {
									// 生成CE二维码
									gc.generateQRCode(codeStr, 69, 47, "C:\\toolsZ\\codeZ\\wittur.png");
									// 生成迅达二维码
									gc.generateQRCode(codeStr2, 36, 36, "C:\\toolsZ\\codeZ\\xd.png");
								} catch (WriterException | IOException e1) {
									e1.printStackTrace();
								}

								// 打印
								WitturCEAndSchindler witturCEAndXD = new WitturCEAndSchindler(printers[1], certStr,
										seriesStr, serialNoStr, saleOrNoStr, dateStr, typeStr, code, itemStr, dsceStr,
										snoStr, materDsce, idNoStr);
								witturCEAndXD.setPadding(paddingLeftDouble, paddingTopDouble);
								witturCEAndXD.printcode();
								System.out.println(witturCEAndXD.toString());

								// TODO 标记已经打印过
								// setAsPrinted(idStr);
								// 存储记录
								saveWitturCEAndSchindlerData(materDsce, idNoStr, serialNoStr, saleOrNoStr, poNoStr,
										itemStr, codeStr, codeStr2, boxIdStr, dsceStr, "NO", certStr, seriesStr,
										typeStr);
								break;
							// 迅达出口标签+CE标签。8个变量
							case 2:
								// CE标签
								indexSharp = code.indexOf('#') + 1;
								dateStr = "20" + code.substring(indexSharp, indexSharp + 2) + "/"
										+ code.substring(indexSharp + 2, indexSharp + 4) + "/"
										+ code.substring(indexSharp + 4, indexSharp + 6);
								codeStr = code + serialNoStr;
								certStr = printDataList.get(0);
								seriesStr = printDataList.get(1);
								typeStr = printDataList.get(2);
								// 迅达标签
								materDsce = printDataList.get(3);
								idNoStr = printDataList.get(4);
								codeStr2 = materDsce + "NANA" + idNoStr + serialNoStr
										+ "---Wittur CN215214SuzhouCNNANANANA";
								gc = new GenerateCode();
								try {
									// 生成CE二维码
									gc.generateQRCode(codeStr, 69, 47, "C:\\toolsZ\\codeZ\\wittur.png");
									// 生成迅达二维码
									gc.generateQRCode(codeStr2, 36, 36, "C:\\toolsZ\\codeZ\\xdc.png");
								} catch (WriterException | IOException e1) {
									e1.printStackTrace();
								}

								// 打印
								WitturCEAndSchindler witturCEAndXDC = new WitturCEAndSchindler(printers[2], certStr,
										seriesStr, serialNoStr, saleOrNoStr, dateStr, typeStr, code, itemStr, dsceStr,
										snoStr, materDsce, idNoStr);
								witturCEAndXDC.setPadding(paddingLeftDouble, paddingTopDouble);
								witturCEAndXDC.printcode();
								System.out.println(witturCEAndXDC.toString());

								// 标记已经打印过
								// setAsPrinted(idStr);
								// TODO 存储记录
								saveWitturCEAndSchindlerData(materDsce, idNoStr, serialNoStr, saleOrNoStr, poNoStr,
										itemStr, codeStr, codeStr2, boxIdStr, dsceStr, "YES", certStr, seriesStr,
										typeStr);
								break;
							// 通力出口：AMD标签+条形码标签。6个变量。
							case 3:
								// 条形码
								String baseIDStr = printDataList.get(0);
								String partsStr = printDataList.get(1);
								String updateCodeStr = printDataList.get(2);
								String supplierCodeStr = printDataList.get(3);
								String barcodeStr = baseIDStr + partsStr + "." + updateCodeStr + supplierCodeStr
										+ serialNoStr;
								// AMD
								String productCodeStr = printDataList.get(4);
								typeStr = printDataList.get(5);
								certStr = printDataList.get(6);
								indexSharp = code.indexOf('#') + 1;
								dateStr = "20" + code.substring(indexSharp, indexSharp + 2) + "/"
										+ code.substring(indexSharp + 2, indexSharp + 4) + "/"
										+ code.substring(indexSharp + 4, indexSharp + 6);
								codeStr = code + serialNoStr;
								try {
									// 生成一维码
									gc.generateCode_128(barcodeStr, 299, 15, "C:\\toolsZ\\codeZ\\Code128.png");
									// 生成AMD二维码
									gc.generateQRCode(codeStr, 40, 40, "C:\\toolsZ\\codeZ\\amd.png");
								} catch (WriterException | IOException e1) {
									e1.printStackTrace();
								}

								// 打印
								AMDAndBarcode amdAndBarcode = new AMDAndBarcode(printers[3], productCodeStr, typeStr,
										certStr, serialNoStr, saleOrNoStr, dateStr, code, itemStr, dsceStr, snoStr,
										barcodeStr);
								amdAndBarcode.setPadding(paddingLeftDouble, paddingTopDouble);
								amdAndBarcode.printcode();
								System.out.println(amdAndBarcode.toString());

								// TODO 标记已经打印过
								// setAsPrinted(idStr);

								// 存储记录
								saveAMDAndBarcodeData(productCodeStr, typeStr, serialNoStr, saleOrNoStr, poNoStr,
										itemStr, codeStr, barcodeStr, boxIdStr, dsceStr, certStr, baseIDStr, partsStr,
										updateCodeStr, supplierCodeStr);
								break;
							// Augusta:CE标签+条形码标签。6个变量。
							case 4:
								// 条形码
								baseIDStr = printDataList.get(0);
								partsStr = printDataList.get(1);
								updateCodeStr = printDataList.get(2);
								supplierCodeStr = printDataList.get(3);
								barcodeStr = baseIDStr + partsStr + "." + updateCodeStr + supplierCodeStr + serialNoStr;

								indexSharp = code.indexOf('#') + 1;
								dateStr = "20" + code.substring(indexSharp, indexSharp + 2) + "/"
										+ code.substring(indexSharp + 2, indexSharp + 4) + "/"
										+ code.substring(indexSharp + 4, indexSharp + 6);
								codeStr = code + serialNoStr;

								// CE标签 excel抓取的数据
								certStr = printDataList.get(4);
								seriesStr = printDataList.get(5);
								typeStr = printDataList.get(6);

								try {
									// 生成一维码
									gc.generateCode_128(barcodeStr, 299, 15, "C:\\toolsZ\\codeZ\\Code128.png");
									// 生成CE二维码
									gc.generateQRCode(codeStr, 40, 40, "C:\\toolsZ\\codeZ\\wittur.png");
								} catch (WriterException | IOException e1) {
									e1.printStackTrace();
								}

								// 打印
								WitturCEAndBarcode witturCEAndBarcode = new WitturCEAndBarcode(printers[4], certStr,
										seriesStr, serialNoStr, saleOrNoStr, dateStr, typeStr, code, itemStr, dsceStr,
										snoStr, barcodeStr);
								witturCEAndBarcode.setPadding(paddingLeftDouble, paddingTopDouble);
								witturCEAndBarcode.printcode();
								System.out.println(witturCEAndBarcode.toString());

								// TODO 标记已经打印过
								// setAsPrinted(idStr);

								// 存储数据
								saveWitturCEAndBarcodeData(certStr, seriesStr, serialNoStr, saleOrNoStr, poNoStr,
										itemStr, codeStr, barcodeStr, boxIdStr, dsceStr, typeStr, baseIDStr, partsStr,
										updateCodeStr, supplierCodeStr);
								break;
							// 通力不出口:原来的挂件标签+条形码标签。
							case 5:
								// 条形码
								baseIDStr = printDataList.get(0);
								partsStr = printDataList.get(1);
								updateCodeStr = printDataList.get(2);
								supplierCodeStr = printDataList.get(3);
								barcodeStr = baseIDStr + partsStr + "." + updateCodeStr + supplierCodeStr + serialNoStr;
								// 二维码
								codeStr = code + serialNoStr;
								try {
									// 生成一维码
									gc.generateCode_128(barcodeStr, 299, 15, "C:\\toolsZ\\codeZ\\Code128.png");
									// 生成CE二维码
									gc.generateQRCode(codeStr, 40, 40, "C:\\toolsZ\\codeZ\\qrcode.png");
								} catch (WriterException | IOException e1) {
									e1.printStackTrace();
								}
								// 打印机列表下标为5，6的打印机被单迅达标签占用。
								BarcodeAndQRCode barcodeAndQRCode = new BarcodeAndQRCode(printers[7], barcodeStr,
										codeStr, itemStr, dsceStr, snoStr);
								barcodeAndQRCode.setPadding(paddingLeftDouble, paddingTopDouble);
								barcodeAndQRCode.printcode();
								System.out.println(barcodeAndQRCode.toString());
								// TODO 标记已经打印过
								// setAsPrinted(idStr);

								// 存储数据
								saveBarcodeAndQRCodeData(baseIDStr, partsStr, updateCodeStr, supplierCodeStr,
										serialNoStr, codeStr, barcodeStr, saleOrNoStr, poNoStr, boxIdStr, itemStr,
										dsceStr);
								break;
							default:
								JOptionPane.showInternalMessageDialog(null, "数据无法匹配！\n打印终止！");
								// 释放用其他操作
								codeType.setEnabled(true);
								goPrint.setEnabled(true);
								table.setEnabled(true);
								searchBtn.setEnabled(true);
								return null;
							}

							// table 中移除已打印的行,每次移除新序列中的第一行
							// tableModel.removeRow(selectedRows[0]);
							// list 中移除已打印的行
							// list.remove(selectedRows[0]);
							// [end]
							// 休眠100毫秒
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
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
		goPrint.setBounds(788, 11, 93, 23);
		contentPane.add(goPrint);
		icon2 = new ImageIcon(getClass().getResource("/images/wittur.png"));
		icon2.setImage(icon2.getImage().getScaledInstance(350, 180, Image.SCALE_DEFAULT));

		JLabel label_6 = new JLabel("\u5BF9\u5E94\u6253\u5370\u673A\uFF1A");
		label_6.setForeground(SystemColor.controlShadow);
		label_6.setFont(new Font("宋体", Font.PLAIN, 12));
		label_6.setBounds(637, 451, 72, 18);
		contentPane.add(label_6);

		JButton button_2 = new JButton("\u65B0\u5339\u914D");
		button_2.setFont(new Font("宋体", Font.ITALIC, 12));
		button_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PrinterAdjustment pa = PrinterAdjustment.getScreen();
				pa.setVisible(true);
			}
		});
		button_2.setActionCommand("\u65B0\u5339\u914D");
		button_2.setBounds(912, 449, 72, 23);
		contentPane.add(button_2);

		defaultPrinter = new JTextField();
		defaultPrinter.setHorizontalAlignment(SwingConstants.CENTER);
		defaultPrinter.setFont(new Font("宋体", Font.ITALIC, 10));
		defaultPrinter.setText("printer1");
		defaultPrinter.setBounds(705, 451, 187, 21);
		contentPane.add(defaultPrinter);
		defaultPrinter.setColumns(10);

		label = new JLabel("\u5DE6\u8FB9\u8DDD\uFF1A");
		label.setBounds(10, 449, 54, 23);
		contentPane.add(label);

		paddingLeft = new JTextField();
		paddingLeft.setText("0");
		paddingLeft.setHorizontalAlignment(SwingConstants.CENTER);
		paddingLeft.setColumns(10);
		paddingLeft.setBounds(68, 449, 66, 24);
		contentPane.add(paddingLeft);

		paddingTop = new JTextField();
		paddingTop.setText("0");
		paddingTop.setHorizontalAlignment(SwingConstants.CENTER);
		paddingTop.setColumns(10);
		paddingTop.setBounds(217, 449, 66, 24);
		contentPane.add(paddingTop);

		label_7 = new JLabel("\u4E0A\u8FB9\u8DDD\uFF1A");
		label_7.setBounds(159, 451, 54, 21);
		contentPane.add(label_7);

		JLabel label_8 = new JLabel("\u8BA2\u5355\u53F7\uFF1A");
		label_8.setBounds(356, 12, 54, 21);
		contentPane.add(label_8);

		saleOrderNumber = new JTextField();
		saleOrderNumber.setHorizontalAlignment(SwingConstants.CENTER);
		saleOrderNumber.setColumns(10);
		saleOrderNumber.setBounds(407, 12, 174, 21);
		contentPane.add(saleOrderNumber);

		searchBtn = new JButton("查询");
		searchBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (searchBtn.isEnabled()) {
					String searchStr = saleOrderNumber.getText().toString();
					if (searchStr.isEmpty()) {// 如果未填写，还原所有数据
						// 恢复列表
						JOptionPane.showMessageDialog(null, "请填写内容！");
						return;
					}
					// 查询
					getTypeLabelDataByORNO().execute();
				}
			}
		});
		searchBtn.setBounds(606, 10, 60, 23);
		contentPane.add(searchBtn);

		progressLabel = new JLabel("\u8FDB\u5EA6\uFF1A");
		progressLabel.setIcon(null);
		progressLabel.setForeground(Color.BLUE);
		progressLabel.setFont(new Font("宋体", Font.ITALIC, 12));
		progressLabel.setBounds(12, 11, 42, 17);
		progressLabel.setVisible(false);
		contentPane.add(progressLabel);

		progressBar = new JProgressBar();
		progressBar.setBounds(61, 14, 104, 14);
		progressBar.setVisible(false);
		contentPane.add(progressBar);

		progressText = new JLabel("0/0");
		progressText.setForeground(Color.BLUE);
		progressText.setHorizontalAlignment(SwingConstants.CENTER);
		progressText.setFont(new Font("宋体", Font.ITALIC, 11));
		progressText.setBounds(167, 14, 116, 15);
		progressText.setVisible(false);
		contentPane.add(progressText);
		icon2 = new ImageIcon(getClass().getResource("/images/wittur.png"));
		icon2.setImage(icon2.getImage().getScaledInstance(350, 180, Image.SCALE_DEFAULT));

		// 默认获取第一个打印机
		getCurrentPrinter(1);

		// 初始化打印机列表
		getAllPrinter();

	}

	/**
	 * 存储条形码的已打印信息。
	 * 
	 * @param baseIDStr
	 * @param partsStr
	 * @param updateCodeStr
	 * @param supplierCodeStr
	 * @param serialNoStr
	 * @param codeStr
	 */
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
				defaultPrinter.setText(rs.getString(1));
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
	 * 获取当前所有已经匹配好的打印机。
	 * 
	 */
	private void getAllPrinter() {
		try {
			Connection conn = new SQLiteJDBC().getConnection();
			Statement stat = conn.createStatement();
			String sql = "select printName from Printer";
			ResultSet rs = stat.executeQuery(sql);
			printers = new String[10];
			int index = 0;
			while (rs.next()) {
				printers[index++] = rs.getString(1);
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

				// 禁用按钮
				searchBtn.setEnabled(false);
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
					if (list.size() == 0) {
						JOptionPane.showMessageDialog(null, "所查询的数据不存在！");
						// 释放按钮
						 searchBtn.setEnabled(true);
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
				// 释放按钮
				searchBtn.setEnabled(true);
				return null;
			}
		};

		return getData;
	}

	/**
	 * 存储威特自由市场数据：单CE标签。
	 * 
	 * @param certStr
	 * @param seriesStr
	 * @param serialNoStr
	 * @param saleOrNoStr
	 * @param poNoStr
	 * @param itemStr
	 * @param codeStr
	 * @param boxIdStr
	 * @param dsceStr
	 * @param typeStr
	 */
	protected void saveWitturCEData(String certStr, String seriesStr, String serialNoStr, String saleOrNoStr,
			String poNoStr, String itemStr, String codeStr, String boxIdStr, String dsceStr, String typeStr) {
		try {
			Connection conn = OracleUtils.getConnection();
			Statement stat = conn.createStatement();
			String sql = "insert into cbarcode_witturce (certificateno, series, serialnumber, orno, pono, item, code, boxid, dsce, type, pdate) "
					+ "values('" + certStr + "', '" + seriesStr + "', '" + serialNoStr + "', '" + saleOrNoStr + "', '"
					+ poNoStr + "', '" + itemStr + "', '" + codeStr + "', '" + boxIdStr + "', '" + dsceStr + "', '"
					+ typeStr + "', sysdate) ";
			stat.executeUpdate(sql);
			stat.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 存储迅达标签+CE标签数据。
	 * 
	 * @param materialStr
	 * @param idNoStr
	 * @param serialNoStr
	 * @param saleOrNoStr
	 * @param poNoStr
	 * @param itemStr
	 * @param codeStr
	 * @param codeStr2
	 * @param boxIdStr
	 * @param dsceStr
	 * @param nld
	 * @param certStr
	 * @param seriesStr
	 * @param typeStr
	 */
	protected void saveWitturCEAndSchindlerData(String materialStr, String idNoStr, String serialNoStr,
			String saleOrNoStr, String poNoStr, String itemStr, String codeStr, String codeStr2, String boxIdStr,
			String dsceStr, String nld, String certStr, String seriesStr, String typeStr) {
		try {
			Connection conn = OracleUtils.getConnection();
			Statement stat = conn.createStatement();
			String sql = "insert into cbarcode_witturceandschindler "
					+ "(materialdes, idnumber, serialnumber, orno, pono, item, witturcecode, schindlercode, boxid, dsce, nld, certificateno, series, type, pdate) "
					+ "values('" + materialStr + "', '" + idNoStr + "', '" + serialNoStr + "', '" + saleOrNoStr + "', '"
					+ poNoStr + "', '" + itemStr + "', '" + codeStr + "', '" + codeStr2 + "', '" + boxIdStr + "', '"
					+ dsceStr + "','" + nld + "', '" + certStr + "', '" + seriesStr + "', '" + typeStr + "', sysdate) ";
			stat.executeUpdate(sql);
			stat.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 存储通力出口数据：AMD标签+条形码标签。
	 * 
	 * @param productCode
	 * @param type
	 * @param serialNoStr
	 * @param saleOrNoStr
	 * @param poNoStr
	 * @param itemStr
	 * @param codeStr
	 * @param barcodeStr
	 * @param boxIdStr
	 * @param dsceStr
	 * @param certNo
	 * @param baseIDStr
	 * @param partsStr
	 * @param updateCodeStr
	 * @param supplierCodeStr
	 */
	protected void saveAMDAndBarcodeData(String productCode, String type, String serialNoStr, String saleOrNoStr,
			String poNoStr, String itemStr, String codeStr, String barcodeStr, String boxIdStr, String dsceStr,
			String certNo, String baseIDStr, String partsStr, String updateCodeStr, String supplierCodeStr) {
		try {
			Connection conn = OracleUtils.getConnection();
			Statement stat = conn.createStatement();
			String sql = "insert into cbarcode_amdandbarcode "
					+ "(productcode, componenttype, serialnumber, orno, pono, item, amdcode, barcode, boxid, dsce, certificatnum, baseid, parts, updatecode, suppliercode, pdate) "
					+ "values('" + productCode + "', '" + type + "', '" + serialNoStr + "', '" + saleOrNoStr + "', '"
					+ poNoStr + "', '" + itemStr + "', '" + codeStr + "', '" + barcodeStr + "', '" + boxIdStr + "', '"
					+ dsceStr + "','" + certNo + "', '" + baseIDStr + "', '" + partsStr + "', '" + updateCodeStr
					+ "', '" + supplierCodeStr + "', sysdate) ";
			stat.executeUpdate(sql);
			stat.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 存储Augusta数据:CE标签+条形码标签。
	 * 
	 * @param certStr
	 * @param seriesStr
	 * @param serialNoStr
	 * @param saleOrNoStr
	 * @param poNoStr
	 * @param itemStr
	 * @param codeStr
	 * @param boxIdStr
	 * @param dsceStr
	 * @param typeStr
	 */
	protected void saveWitturCEAndBarcodeData(String certStr, String seriesStr, String serialNoStr, String saleOrNoStr,
			String poNoStr, String itemStr, String codeStr, String barcode, String boxIdStr, String dsceStr,
			String typeStr, String baseIDStr, String partsStr, String updateCodeStr, String supplierCodeStr) {
		try {
			Connection conn = OracleUtils.getConnection();
			Statement stat = conn.createStatement();
			String sql = "insert into cbarcode_witturceandbarcode "
					+ "(certificateno, series, serialnumber, orno, pono, item, witturcecode, barcode, boxid, dsce, type, pdate, baseid, parts, updatecode, suppliercode) "
					+ "values('" + certStr + "', '" + seriesStr + "', '" + serialNoStr + "', '" + saleOrNoStr + "', '"
					+ poNoStr + "', '" + itemStr + "', '" + codeStr + "', '" + barcode + "', '" + boxIdStr + "', '"
					+ dsceStr + "', '" + typeStr + "', sysdate, '" + baseIDStr + "', '" + partsStr + "', '"
					+ updateCodeStr + "', '" + supplierCodeStr + "') ";
			stat.executeUpdate(sql);
			stat.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 储存通力不出口数据:原来的挂件标签+条形码标签。
	 * 
	 * @param baseIDStr
	 * @param partsStr
	 * @param updateCodeStr
	 * @param supplierCodeStr
	 * @param serialNoStr
	 * @param codeStr
	 * @param saleOrNoStr
	 * @param poNoStr
	 * @param itemStr
	 * @param dsceStr
	 */
	protected void saveBarcodeAndQRCodeData(String baseIDStr, String partsStr, String updateCodeStr,
			String supplierCodeStr, String serialNoStr, String codeStr, String barcodeStr, String saleOrNoStr,
			String poNoStr, String boxIdStr, String itemStr, String dsceStr) {
		try {
			Connection conn = OracleUtils.getConnection();
			String sql = "insert into cbarcode_barcodeandqrcode "
					+ "(baseid, parts, updatecode, suppliercode, serialnumber, qrcode, barcode, orno, pono, boxid, item, dsce, pdate) "
					+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, baseIDStr);
			ps.setString(2, partsStr);
			ps.setString(3, updateCodeStr);
			ps.setString(4, supplierCodeStr);
			ps.setString(5, serialNoStr);
			ps.setString(6, codeStr);
			ps.setString(7, barcodeStr);
			ps.setString(8, saleOrNoStr);
			ps.setString(9, poNoStr);
			ps.setString(10, boxIdStr);
			ps.setString(11, itemStr);
			ps.setString(12, dsceStr);
			ps.executeUpdate();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
