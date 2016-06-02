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

	/** JTable����ͷ */
	private String[] heads = { "������", "���Ϻ�", "������", "���", "����", "������", "Ψһ��", "ID", "��ˮ��" };
	/** JTableѡ���� */
	private int[] selectedRows = null;
	/** JTable����ģ�� */
	DefaultTableModel tableModel = new DefaultTableModel(null, heads);

	/** ץȡ���ݴ洢�б� */
	ArrayList<String> printDataList = null;
	/** �Ѿ�ƥ��Ĵ�ӡ���������� */
	private String[] printers = null;
	/** V7�������б� */
	private static ArrayList<V7Model> v7List = null;
	/** �������� */
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
		UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("����", Font.ITALIC, 13)));
		UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("����", Font.ITALIC, 13)));
		addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent e) {
				int type = codeType.getSelectedIndex();
				// ���µ�ǰ��ӡ��
				getCurrentPrinter(type + 1);
				// ˢ�´�ӡ���б�
				getAllPrinter();
			}

			public void windowLostFocus(WindowEvent e) {
			}
		});
		setBackground(new Color(224, 255, 255));
		setResizable(false);
		// ���Ͻ�LOGO
		this.setIconImage(new ImageIcon(getClass().getResource("/images/Wittur_Logo.gif")).getImage());
		// ����title
		setTitle("\u8865\u6253\u5370");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 512);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 255, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		// ��ȡlogoͼ
		ImageIcon icon = new ImageIcon(getClass().getResource("/images/Wittur_Logo.gif"));
		// ����ͼƬ����
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
				case 5:// ��ӡ��6��7����Ѹ���ǩռ��
					getCurrentPrinter(8);
					break;
				default:
					break;
				}
			}

		});
		codeType.setIgnoreRepaint(true);
		codeType.setFont(new Font("����", Font.ITALIC, 12));
		codeType.setModel(new DefaultComboBoxModel<String>(new String[] { "Wittur CE",
				"\u8FC5\u8FBE\u4E0D\u51FA\u53E3 + CE", "\u8FC5\u8FBE\u51FA\u53E3 + CE", "\u6761\u5F62\u7801 + AMD",
				"\u6761\u5F62\u7801 + CE", "\u6761\u5F62\u7801 + \u539F\u6302\u4EF6" }));
		codeType.setBounds(523, 451, 104, 21);
		contentPane.add(codeType);

		JLabel label_1 = new JLabel("\u6807\u7B7E\u7C7B\u522B\uFF1A");
		label_1.setFont(new Font("����", Font.ITALIC, 12));
		label_1.setBounds(464, 449, 60, 22);
		contentPane.add(label_1);

		goPrint = new JButton("\u6253\u5370");
		goPrint.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				// �жϰ�ť״̬
				if (!goPrint.isEnabled()) {
					return;
				}

				// �ж������Ƿ��쳣
				if (v7List == null) {
					JOptionPane.showMessageDialog(null, "V7�������޷����أ���������������");
					return;
				}

				// �жϱ߾������Ƿ����
				if (!paddingLeft.getText().toString().trim().matches("^[-+]?(([0-9]+)(.[0-9]+)?|(.[0-9]+))$")) {
					JOptionPane.showMessageDialog(null, "��߾�����Ƿ����ݣ����飡");
					paddingLeft.setFocusable(true);
					return;
				}
				if (!paddingTop.getText().toString().trim().matches("^[-+]?(([0-9]+)(.[0-9]+)?|(.[0-9]+))$")) {
					JOptionPane.showMessageDialog(null, "�ϱ߾�����Ƿ����ݣ����飡");
					paddingTop.setFocusable(true);
					return;
				}

				// ��ȡѡ����
				selectedRows = table.getSelectedRows();

				// ���д�ӡ
				goPrint().execute();
			}

			/**
			 * ���д�ӡ��
			 * 
			 */
			private SwingWorker<Void, Void> goPrint() {
				SwingWorker<Void, Void> print = new SwingWorker<Void, Void>() {

					@Override
					protected Void doInBackground() {
						// ������ݲ����ڣ�����
						if (selectedRows == null) {
							return null;
						}
						// ������������
						codeType.setEnabled(false);
						goPrint.setEnabled(false);
						table.setEnabled(false);
						searchBtn.setEnabled(false);

						// ��ȡ����
						int count = selectedRows.length;
						// ���ý���������
						progressBar.setMaximum(count);
						// ���õ�ǰ��ӡ��
						int currentIndex = 0;

						// ��ʾ����
						progressBar.setVisible(true);
						progressLabel.setVisible(true);
						progressText.setVisible(true);

						// ��ά��/���������� ʵ����
						GenerateCode gc = new GenerateCode();

						// ѭ����ӡ��ѡ��������

						// ��ȡ�߾�
						double paddingLeftDouble = Double.parseDouble(paddingLeft.getText().toString().trim());
						double paddingTopDouble = Double.parseDouble(paddingTop.getText().toString().trim());

						// ѭ��������������
						for (int selectedRow : selectedRows) {
							currentIndex++;
							// ���ý�������ǰ������
							progressBar.setValue(currentIndex);
							// �������ֽ���
							progressText.setText("" + currentIndex + "/" + count);

							/** ����ID */
							String idStr = table.getValueAt(selectedRow, 7).toString();

							// boolean isPrinted = isPrinted(idStr);
							// �ж��Ƿ��Ѿ���ӡ��
							// if (isPrinted) {
							// int dialog = JOptionPane.showConfirmDialog(null,
							// "�ùҼ��Ѿ���ӡ��\n�Ƿ��ٴδ�ӡ��");
							// if (dialog != 0) {
							// return;
							// }
							// }

							/** 9λ�� */
							String serialNoStr = table.getValueAt(selectedRow, 6).toString();
							/** ���۵���(������) */
							String saleOrNoStr = table.getValueAt(selectedRow, 0).toString();
							/** ������ */
							String code = table.getValueAt(selectedRow, 5).toString();
							/** ������ */
							String poNoStr = table.getValueAt(selectedRow, 2).toString();
							/** ��Ŀ�� */
							String itemStr = table.getValueAt(selectedRow, 1).toString();
							/** ���� */
							String dsceStr = table.getValueAt(selectedRow, 4).toString();
							/** ��ˮ�� */
							String snoStr = table.getValueAt(selectedRow, 8).toString();
							/** ��� */
							String boxIdStr = table.getValueAt(selectedRow, 3).toString();

							String codeStr = "";

							// ����б�
							printDataList = null;
							// ����������
							printDataList = SourceData.getData(new String[] { itemStr, dsceStr, saleOrNoStr, poNoStr },
									v7List);

							// �б���
							int listSize = 0;
							if (printDataList == null || (listSize = printDataList.size()) == 0) {

								int response = JOptionPane
										.showConfirmDialog(null,
												"�����޷�������ȡ��" + "\n�����ţ�" + saleOrNoStr + "  �����У�" + poNoStr + "  ID��"
														+ idStr + "   \n�Ƿ�����������¼������ӡ��",
												"��������", JOptionPane.YES_NO_OPTION);
								if (response != 0) {
									// �ͷ�����������
									codeType.setEnabled(true);
									goPrint.setEnabled(true);
									table.setEnabled(true);
									searchBtn.setEnabled(true);
									return null;
								} else {
									continue;
								}
							}

							// ��ǩ���
							int type = Integer.parseInt(printDataList.get(listSize - 1));

							// [start]
							switch (type) {
							// ���������г�����CE��ǩ��5������
							case 0:
								int indexSharp = code.indexOf('#') + 1;
								String dateStr = "20" + code.substring(indexSharp, indexSharp + 2) + "/"
										+ code.substring(indexSharp + 2, indexSharp + 4) + "/"
										+ code.substring(indexSharp + 4, indexSharp + 6);
								codeStr = code + serialNoStr;

								// excelץȡ������
								String certStr = printDataList.get(0);
								String seriesStr = printDataList.get(1);
								String typeStr = printDataList.get(2);

								try {
									// ���ɶ�ά��
									gc.generateQRCode(codeStr, 69, 47, "C:\\toolsZ\\codeZ\\wittur.png");
								} catch (WriterException | IOException e1) {
									e1.printStackTrace();
								}

								// ��ӡ
								WitturCE witturCE = new WitturCE(printers[0], certStr, seriesStr, serialNoStr,
										saleOrNoStr, dateStr, typeStr, code, itemStr, dsceStr, snoStr);
								witturCE.setPadding(paddingLeftDouble, paddingTopDouble);
								System.out.println(witturCE.toString());
								witturCE.printcode();

								// TODO ����Ѿ���ӡ��
								// setAsPrinted(idStr);

								// �����¼
								saveWitturCEData(certStr, seriesStr, serialNoStr, saleOrNoStr, poNoStr, itemStr,
										codeStr, boxIdStr, dsceStr, typeStr);

								break;
							// Ѹ�ﲻ���ڱ�ǩ+CE��ǩ��8������
							case 1:
								// CE��ǩ
								indexSharp = code.indexOf('#') + 1;
								dateStr = "20" + code.substring(indexSharp, indexSharp + 2) + "/"
										+ code.substring(indexSharp + 2, indexSharp + 4) + "/"
										+ code.substring(indexSharp + 4, indexSharp + 6);
								codeStr = code + serialNoStr;
								certStr = printDataList.get(0);
								seriesStr = printDataList.get(1);
								typeStr = printDataList.get(2);
								// Ѹ���ǩ
								String materDsce = printDataList.get(3);
								String idNoStr = printDataList.get(4);
								String codeStr2 = materDsce + "NANA" + idNoStr + serialNoStr
										+ "---Wittur CN215214SuzhouCNNANANANA";
								gc = new GenerateCode();
								try {
									// ����CE��ά��
									gc.generateQRCode(codeStr, 69, 47, "C:\\toolsZ\\codeZ\\wittur.png");
									// ����Ѹ���ά��
									gc.generateQRCode(codeStr2, 36, 36, "C:\\toolsZ\\codeZ\\xd.png");
								} catch (WriterException | IOException e1) {
									e1.printStackTrace();
								}

								// ��ӡ
								WitturCEAndSchindler witturCEAndXD = new WitturCEAndSchindler(printers[1], certStr,
										seriesStr, serialNoStr, saleOrNoStr, dateStr, typeStr, code, itemStr, dsceStr,
										snoStr, materDsce, idNoStr);
								witturCEAndXD.setPadding(paddingLeftDouble, paddingTopDouble);
								witturCEAndXD.printcode();
								System.out.println(witturCEAndXD.toString());

								// TODO ����Ѿ���ӡ��
								// setAsPrinted(idStr);
								// �洢��¼
								saveWitturCEAndSchindlerData(materDsce, idNoStr, serialNoStr, saleOrNoStr, poNoStr,
										itemStr, codeStr, codeStr2, boxIdStr, dsceStr, "NO", certStr, seriesStr,
										typeStr);
								break;
							// Ѹ����ڱ�ǩ+CE��ǩ��8������
							case 2:
								// CE��ǩ
								indexSharp = code.indexOf('#') + 1;
								dateStr = "20" + code.substring(indexSharp, indexSharp + 2) + "/"
										+ code.substring(indexSharp + 2, indexSharp + 4) + "/"
										+ code.substring(indexSharp + 4, indexSharp + 6);
								codeStr = code + serialNoStr;
								certStr = printDataList.get(0);
								seriesStr = printDataList.get(1);
								typeStr = printDataList.get(2);
								// Ѹ���ǩ
								materDsce = printDataList.get(3);
								idNoStr = printDataList.get(4);
								codeStr2 = materDsce + "NANA" + idNoStr + serialNoStr
										+ "---Wittur CN215214SuzhouCNNANANANA";
								gc = new GenerateCode();
								try {
									// ����CE��ά��
									gc.generateQRCode(codeStr, 69, 47, "C:\\toolsZ\\codeZ\\wittur.png");
									// ����Ѹ���ά��
									gc.generateQRCode(codeStr2, 36, 36, "C:\\toolsZ\\codeZ\\xdc.png");
								} catch (WriterException | IOException e1) {
									e1.printStackTrace();
								}

								// ��ӡ
								WitturCEAndSchindler witturCEAndXDC = new WitturCEAndSchindler(printers[2], certStr,
										seriesStr, serialNoStr, saleOrNoStr, dateStr, typeStr, code, itemStr, dsceStr,
										snoStr, materDsce, idNoStr);
								witturCEAndXDC.setPadding(paddingLeftDouble, paddingTopDouble);
								witturCEAndXDC.printcode();
								System.out.println(witturCEAndXDC.toString());

								// ����Ѿ���ӡ��
								// setAsPrinted(idStr);
								// TODO �洢��¼
								saveWitturCEAndSchindlerData(materDsce, idNoStr, serialNoStr, saleOrNoStr, poNoStr,
										itemStr, codeStr, codeStr2, boxIdStr, dsceStr, "YES", certStr, seriesStr,
										typeStr);
								break;
							// ͨ�����ڣ�AMD��ǩ+�������ǩ��6��������
							case 3:
								// ������
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
									// ����һά��
									gc.generateCode_128(barcodeStr, 299, 15, "C:\\toolsZ\\codeZ\\Code128.png");
									// ����AMD��ά��
									gc.generateQRCode(codeStr, 40, 40, "C:\\toolsZ\\codeZ\\amd.png");
								} catch (WriterException | IOException e1) {
									e1.printStackTrace();
								}

								// ��ӡ
								AMDAndBarcode amdAndBarcode = new AMDAndBarcode(printers[3], productCodeStr, typeStr,
										certStr, serialNoStr, saleOrNoStr, dateStr, code, itemStr, dsceStr, snoStr,
										barcodeStr);
								amdAndBarcode.setPadding(paddingLeftDouble, paddingTopDouble);
								amdAndBarcode.printcode();
								System.out.println(amdAndBarcode.toString());

								// TODO ����Ѿ���ӡ��
								// setAsPrinted(idStr);

								// �洢��¼
								saveAMDAndBarcodeData(productCodeStr, typeStr, serialNoStr, saleOrNoStr, poNoStr,
										itemStr, codeStr, barcodeStr, boxIdStr, dsceStr, certStr, baseIDStr, partsStr,
										updateCodeStr, supplierCodeStr);
								break;
							// Augusta:CE��ǩ+�������ǩ��6��������
							case 4:
								// ������
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

								// CE��ǩ excelץȡ������
								certStr = printDataList.get(4);
								seriesStr = printDataList.get(5);
								typeStr = printDataList.get(6);

								try {
									// ����һά��
									gc.generateCode_128(barcodeStr, 299, 15, "C:\\toolsZ\\codeZ\\Code128.png");
									// ����CE��ά��
									gc.generateQRCode(codeStr, 40, 40, "C:\\toolsZ\\codeZ\\wittur.png");
								} catch (WriterException | IOException e1) {
									e1.printStackTrace();
								}

								// ��ӡ
								WitturCEAndBarcode witturCEAndBarcode = new WitturCEAndBarcode(printers[4], certStr,
										seriesStr, serialNoStr, saleOrNoStr, dateStr, typeStr, code, itemStr, dsceStr,
										snoStr, barcodeStr);
								witturCEAndBarcode.setPadding(paddingLeftDouble, paddingTopDouble);
								witturCEAndBarcode.printcode();
								System.out.println(witturCEAndBarcode.toString());

								// TODO ����Ѿ���ӡ��
								// setAsPrinted(idStr);

								// �洢����
								saveWitturCEAndBarcodeData(certStr, seriesStr, serialNoStr, saleOrNoStr, poNoStr,
										itemStr, codeStr, barcodeStr, boxIdStr, dsceStr, typeStr, baseIDStr, partsStr,
										updateCodeStr, supplierCodeStr);
								break;
							// ͨ��������:ԭ���ĹҼ���ǩ+�������ǩ��
							case 5:
								// ������
								baseIDStr = printDataList.get(0);
								partsStr = printDataList.get(1);
								updateCodeStr = printDataList.get(2);
								supplierCodeStr = printDataList.get(3);
								barcodeStr = baseIDStr + partsStr + "." + updateCodeStr + supplierCodeStr + serialNoStr;
								// ��ά��
								codeStr = code + serialNoStr;
								try {
									// ����һά��
									gc.generateCode_128(barcodeStr, 299, 15, "C:\\toolsZ\\codeZ\\Code128.png");
									// ����CE��ά��
									gc.generateQRCode(codeStr, 40, 40, "C:\\toolsZ\\codeZ\\qrcode.png");
								} catch (WriterException | IOException e1) {
									e1.printStackTrace();
								}
								// ��ӡ���б��±�Ϊ5��6�Ĵ�ӡ������Ѹ���ǩռ�á�
								BarcodeAndQRCode barcodeAndQRCode = new BarcodeAndQRCode(printers[7], barcodeStr,
										codeStr, itemStr, dsceStr, snoStr);
								barcodeAndQRCode.setPadding(paddingLeftDouble, paddingTopDouble);
								barcodeAndQRCode.printcode();
								System.out.println(barcodeAndQRCode.toString());
								// TODO ����Ѿ���ӡ��
								// setAsPrinted(idStr);

								// �洢����
								saveBarcodeAndQRCodeData(baseIDStr, partsStr, updateCodeStr, supplierCodeStr,
										serialNoStr, codeStr, barcodeStr, saleOrNoStr, poNoStr, boxIdStr, itemStr,
										dsceStr);
								break;
							default:
								JOptionPane.showInternalMessageDialog(null, "�����޷�ƥ�䣡\n��ӡ��ֹ��");
								// �ͷ�����������
								codeType.setEnabled(true);
								goPrint.setEnabled(true);
								table.setEnabled(true);
								searchBtn.setEnabled(true);
								return null;
							}

							// table ���Ƴ��Ѵ�ӡ����,ÿ���Ƴ��������еĵ�һ��
							// tableModel.removeRow(selectedRows[0]);
							// list ���Ƴ��Ѵ�ӡ����
							// list.remove(selectedRows[0]);
							// [end]
							// ����100����
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}

						// �ͷ�����������
						codeType.setEnabled(true);
						goPrint.setEnabled(true);
						table.setEnabled(true);
						searchBtn.setEnabled(true);
						// ѡ�������
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
		label_6.setFont(new Font("����", Font.PLAIN, 12));
		label_6.setBounds(637, 451, 72, 18);
		contentPane.add(label_6);

		JButton button_2 = new JButton("\u65B0\u5339\u914D");
		button_2.setFont(new Font("����", Font.ITALIC, 12));
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
		defaultPrinter.setFont(new Font("����", Font.ITALIC, 10));
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

		searchBtn = new JButton("��ѯ");
		searchBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (searchBtn.isEnabled()) {
					String searchStr = saleOrderNumber.getText().toString();
					if (searchStr.isEmpty()) {// ���δ��д����ԭ��������
						// �ָ��б�
						JOptionPane.showMessageDialog(null, "����д���ݣ�");
						return;
					}
					// ��ѯ
					getTypeLabelDataByORNO().execute();
				}
			}
		});
		searchBtn.setBounds(606, 10, 60, 23);
		contentPane.add(searchBtn);

		progressLabel = new JLabel("\u8FDB\u5EA6\uFF1A");
		progressLabel.setIcon(null);
		progressLabel.setForeground(Color.BLUE);
		progressLabel.setFont(new Font("����", Font.ITALIC, 12));
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
		progressText.setFont(new Font("����", Font.ITALIC, 11));
		progressText.setBounds(167, 14, 116, 15);
		progressText.setVisible(false);
		contentPane.add(progressText);
		icon2 = new ImageIcon(getClass().getResource("/images/wittur.png"));
		icon2.setImage(icon2.getImage().getScaledInstance(350, 180, Image.SCALE_DEFAULT));

		// Ĭ�ϻ�ȡ��һ����ӡ��
		getCurrentPrinter(1);

		// ��ʼ����ӡ���б�
		getAllPrinter();

	}

	/**
	 * �洢��������Ѵ�ӡ��Ϣ��
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
	 * �洢wittur��ӡ���ݡ�
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
	 * �洢Ѹ���ӡ���ݼ�¼��
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
	 * �洢AMD��ӡ���ݡ�
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
	 * �ж��Ƿ��Ѿ���ӡ��
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
	 * ���Ϊ�Ѵ�ӡ��
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
	 * ��ȡ��ǰƥ��Ĵ�ӡ����
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
	 * ��ȡ��ǰ�����Ѿ�ƥ��õĴ�ӡ����
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
	 * ��ȡ��Ҫ��ӡ�����ݡ�
	 * 
	 * @return
	 */
	private SwingWorker<Void, Void> getTypeLabelDataByORNO() {
		SwingWorker<Void, Void> getData = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {

				// ���ð�ť
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
						// ʹ������:rs.getInt(1)��Ч�ʣ�Զ����ʹ���ֶ�����rs.getInt("ID")
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
						JOptionPane.showMessageDialog(null, "����ѯ�����ݲ����ڣ�");
						// �ͷŰ�ť
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
					// ��Ԫ����Ⱦ
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
					// ��Ԫ����
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
					// Ĭ��ѡ�е�һ��
					table.setRowSelectionInterval(0, 0);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				// �ͷŰ�ť
				searchBtn.setEnabled(true);
				return null;
			}
		};

		return getData;
	}

	/**
	 * �洢���������г����ݣ���CE��ǩ��
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
	 * �洢Ѹ���ǩ+CE��ǩ���ݡ�
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
	 * �洢ͨ���������ݣ�AMD��ǩ+�������ǩ��
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
	 * �洢Augusta����:CE��ǩ+�������ǩ��
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
	 * ����ͨ������������:ԭ���ĹҼ���ǩ+�������ǩ��
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
