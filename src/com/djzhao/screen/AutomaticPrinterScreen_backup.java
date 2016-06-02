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
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.djzhao.code.GenerateCode;
import com.djzhao.data.WitturData;
import com.djzhao.db.OracleUtils;
import com.djzhao.db.SQLiteJDBC;
import com.djzhao.model.Hanger;
import com.djzho.print.AMD;
import com.djzho.print.Barcode;
import com.djzho.print.WitturCE;
import com.djzho.print.XD;
import com.djzho.print.XDC;
import com.google.zxing.WriterException;

public class AutomaticPrinterScreen_backup extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1815589088801108279L;
	private JPanel contentPane;
	private JTextField defaultPrinter;
	private JComboBox<String> codeType;
	private JButton goPrint;
	private ImageIcon icon2;
	/** JTable����ͷ */
	private String[] heads = { "������", "���Ϻ�", "������", "���", "����", "������", "Ψһ��", "ID", "��ˮ��" };
	private JTable table;
	private JLabel label;
	private JTextField paddingLeft;
	private JTextField paddingTop;
	private JLabel label_7;
	private JButton reprintBtn;

	/** �Ҽ������б� */
	private List<Hanger> list = null;
	private JTextField searchData;
	private JButton searchBtn;

	/** JTableѡ���� */
	private int[] selectedRows = null;
	private JLabel progressLabel;
	private JProgressBar progressBar;
	private JLabel progressText;
	private JButton updateData;
	private JLabel exampleImage;
	private JTextField filePath;
	private JLabel choseFile;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AutomaticPrinterScreen_backup frame = new AutomaticPrinterScreen_backup();
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
	public AutomaticPrinterScreen_backup() {
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
		// ���Ͻ�LOGO
		this.setIconImage(new ImageIcon(getClass().getResource("/images/Wittur_Logo.gif")).getImage());
		// ����title
		setTitle("Printer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 449);
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
		scrollPane.setBounds(408, 38, 576, 371);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getTableHeader().setReorderingAllowed(false);

		table.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null }, }, heads));
		scrollPane.setViewportView(table);

		codeType = new JComboBox<String>();
		codeType.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				int type = codeType.getSelectedIndex();
				switch (type) {
				case 0:
					icon2 = new ImageIcon(getClass().getResource("/images/wittur.png"));
					exampleImage.setIcon(icon2);
					getCurrentPrinter(1);
					break;
				case 1:
					icon2 = new ImageIcon(getClass().getResource("/images/wittur_shindler.png"));
					exampleImage.setIcon(icon2);
					getCurrentPrinter(2);
					break;
				case 2:
					icon2 = new ImageIcon(getClass().getResource("/images/wittur_shindler_export.png"));
					exampleImage.setIcon(icon2);
					getCurrentPrinter(3);
					break;
				case 3:
					icon2 = new ImageIcon(getClass().getResource("/images/wittur_AMD.png"));
					exampleImage.setIcon(icon2);
					getCurrentPrinter(4);
					break;
				case 4:
					icon2 = new ImageIcon(getClass().getResource("/images/code_AMD.png"));
					exampleImage.setIcon(icon2);
					getCurrentPrinter(5);
					break;
				default:
					break;
				}
			}

		});
		codeType.setIgnoreRepaint(true);
		codeType.setFont(new Font("����", Font.PLAIN, 16));
		codeType.setModel(new DefaultComboBoxModel<String>(new String[] { "Wittur", "\u8FC5\u8FBE\u4E0D\u51FA\u53E3",
				"\u8FC5\u8FBE\u51FA\u53E3", "AMD", "\u6761\u5F62\u7801" }));
		codeType.setBounds(169, 63, 160, 21);
		contentPane.add(codeType);

		JLabel label_1 = new JLabel("\u9009\u62E9\u6807\u7B7E\u7C7B\u522B\uFF1A");
		label_1.setFont(new Font("����", Font.PLAIN, 16));
		label_1.setBounds(30, 64, 116, 18);
		contentPane.add(label_1);

		goPrint = new JButton("\u6253\u5370");
		goPrint.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!goPrint.isEnabled()) {
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
					protected Void doInBackground() throws Exception {
						// ������ݲ����ڣ�����
						if (selectedRows == null) {
							return null;
						}
						// ������������
						codeType.setEnabled(false);
						goPrint.setEnabled(false);
						table.setEnabled(false);
						updateData.setEnabled(false);
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

						// ѭ����ӡ��ѡ��������
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

							// ��ά��/���������� ʵ����
							GenerateCode gc = new GenerateCode();
							/** ��ӡ���� */
							String printNameStr = defaultPrinter.getText().toString();

							/** EXCEl����·�� */
							String excelPath = filePath.getText().toString();

							/** 9λ�� */
							String serialNoStr = table.getValueAt(selectedRow, 6).toString();
							/** ���۵��� */
							String saleOrNoStr = table.getValueAt(selectedRow, 0).toString();
							/** ������ */
							String code = table.getValueAt(selectedRow, 5).toString();
							/** ������ */
							String poNoStr = table.getValueAt(selectedRow, 2).toString();
							/** ��Ŀ�� */
							String itemStr = table.getValueAt(selectedRow, 1).toString();
							/** ��� */
							String boxIdStr = table.getValueAt(selectedRow, 3).toString();
							/** ���� */
							String dsceStr = table.getValueAt(selectedRow, 4).toString();
							/** ��ˮ�� */
							String snoStr = table.getValueAt(selectedRow, 8).toString();

							String codeStr = "";
							int type = codeType.getSelectedIndex();

							double paddingLeftDouble = Double.parseDouble(paddingLeft.getText().toString());
							double paddingTopDouble = Double.parseDouble(paddingTop.getText().toString());

							// [start]
							switch (type) {
							case 0:
								int indexSharp = code.indexOf('#') + 1;
								String dateStr = "20" + code.substring(indexSharp, indexSharp + 2) + "/"
										+ code.substring(indexSharp + 2, indexSharp + 4) + "/"
										+ code.substring(indexSharp + 4, indexSharp + 6);
								codeStr = code + serialNoStr;
								WitturData wd = new WitturData();
								String[] requestedData = { itemStr, dsceStr, saleOrNoStr, poNoStr, excelPath };
								String[] feedbackData = null;
								try {
									feedbackData = wd.getWitturData(requestedData);
								} catch (Exception e) {
									JOptionPane.showMessageDialog(null, e.getMessage());
									// �ͷ�����������
									codeType.setEnabled(true);
									goPrint.setEnabled(true);
									table.setEnabled(true);
									updateData.setEnabled(true);
									searchBtn.setEnabled(true);
									return null;
								}
								// TODO δ��ֵ
								String certStr = feedbackData[0] + " ";
								String seriesStr = feedbackData[1] + " ";
								String typeStr = feedbackData[2] + " ";

								try {
									// ���ɶ�ά��
									gc.generateQRCode(codeStr, 69, 47, "C:\\toolsZ\\codeZ\\wittur.png");
								} catch (WriterException | IOException e1) {
									e1.printStackTrace();
								}
								System.out.println(">>>>i am here :line 310");

								// ��ӡ
								WitturCE w = new WitturCE(printNameStr, certStr, seriesStr, serialNoStr, saleOrNoStr,
										dateStr, typeStr, code, itemStr, dsceStr, snoStr);
								w.setPadding(paddingLeftDouble, paddingTopDouble);
								w.printcode();

								// ����Ѿ���ӡ��
								setAsPrinted(idStr);

								// �����¼
								saveWitturData(certStr, seriesStr, serialNoStr, saleOrNoStr, poNoStr, itemStr, code,
										boxIdStr, dsceStr);

								break;
							case 1:
								// TODO δ��ֵ
								String materDsce = null;
								String idNoStr = null;
								codeStr = materDsce + "NANA" + idNoStr + serialNoStr
										+ "---Wittur CN215214SuzhouCNNANANANA";
								gc = new GenerateCode();
								try {
									// ���ɶ�ά��
									gc.generateQRCode(codeStr, 36, 36, "C:\\toolsZ\\codeZ\\xd.png");
								} catch (WriterException | IOException e1) {
									e1.printStackTrace();
								}

								// ��ӡ
								XD xd = new XD(printNameStr, materDsce, idNoStr, serialNoStr);
								xd.setPadding(paddingLeftDouble, paddingTopDouble);
								xd.printcode();

								// ����Ѿ���ӡ��
								setAsPrinted(idStr);
								// �洢��¼
								saveSchindlerData(materDsce, idNoStr, serialNoStr, saleOrNoStr, poNoStr, itemStr,
										codeStr, boxIdStr, dsceStr, "NO");
								break;
							case 2:
								// TODO δ��ֵ
								String materDsce2 = null;
								String idNoStr2 = null;
								codeStr = materDsce2 + "NANA" + idNoStr2 + serialNoStr
										+ "---Wittur CN215214SuzhouCNWittur HoldingGmbH85259WiedenzhausenDE";
								try {
									// ���ɶ�ά��
									gc.generateQRCode(codeStr, 37, 37, "C:\\toolsZ\\codeZ\\xdc.png");
								} catch (WriterException | IOException e1) {
									e1.printStackTrace();
								}

								// ��ӡ
								XDC xdc = new XDC(printNameStr, materDsce2, idNoStr2, serialNoStr);
								xdc.setPadding(paddingLeftDouble, paddingTopDouble);
								xdc.printcode();

								// ����Ѿ���ӡ��
								setAsPrinted(idStr);
								// �洢��¼
								saveSchindlerData(materDsce2, idNoStr2, serialNoStr, saleOrNoStr, poNoStr, itemStr,
										codeStr, boxIdStr, dsceStr, "YES");
								break;
							case 3:
								// TODO δ��ֵ
								String productCodeStr = null;
								String typeStr2 = null;
								String certStr2 = null;
								int indexSharp2 = code.indexOf('#') + 1;
								String dateStr2 = "20" + code.substring(indexSharp2, indexSharp2 + 2) + "/"
										+ code.substring(indexSharp2 + 2, indexSharp2 + 4) + "/"
										+ code.substring(indexSharp2 + 4, indexSharp2 + 6);
								codeStr = code + serialNoStr;
								try {
									// ���ɶ�ά��
									gc.generateQRCode(codeStr, 40, 40, "C:\\toolsZ\\codeZ\\amd.png");
								} catch (WriterException | IOException e1) {
									e1.printStackTrace();
								}

								// ��ӡ
								AMD amd = new AMD(printNameStr, productCodeStr, typeStr2, certStr2, serialNoStr,
										saleOrNoStr, dateStr2, code, itemStr, dsceStr, snoStr);
								amd.setPadding(paddingLeftDouble, paddingTopDouble);
								amd.printcode();

								// ����Ѿ���ӡ��
								setAsPrinted(idStr);

								// �洢��¼
								saveAMDData(productCodeStr, typeStr2, serialNoStr, saleOrNoStr, poNoStr, itemStr,
										codeStr, boxIdStr, dsceStr, certStr2);

								break;
							case 4:
								// TODO δ��ֵ
								String baseIDStr = null;
								String partsStr = null;
								String updateCodeStr = null;
								String supplierCodeStr = null;
								codeStr = "KM" + baseIDStr + partsStr + "." + updateCodeStr + supplierCodeStr
										+ serialNoStr;
								try {
									// ����128ά��
									gc.generateCode_128(codeStr, 299, 15, "C:\\toolsZ\\codeZ\\Code128.png");
								} catch (WriterException | IOException e1) {
									e1.printStackTrace();
								}

								// ��ӡ
								Barcode c = new Barcode(printNameStr, codeStr);
								c.setPadding(paddingLeftDouble, paddingTopDouble);
								c.printcode();

								// ����Ѿ���ӡ��
								setAsPrinted(idStr);

								// �洢����
								saveCode128Data(baseIDStr, partsStr, updateCodeStr, supplierCodeStr, serialNoStr,
										codeStr);

								break;
							default:
								break;
							}
							// [end]

							// ����50����
							Thread.sleep(50);

						}

						// �ͷ�����������
						codeType.setEnabled(true);
						goPrint.setEnabled(true);
						table.setEnabled(true);
						updateData.setEnabled(true);
						searchBtn.setEnabled(true);
						// ѡ�������
						selectedRows = null;
						return null;
					}
				};

				return print;
			}
		});
		goPrint.setBounds(286, 123, 93, 23);
		contentPane.add(goPrint);
		icon2 = new ImageIcon(getClass().getResource("/images/wittur.png"));
		icon2.setImage(icon2.getImage().getScaledInstance(350, 180, Image.SCALE_DEFAULT));

		JLabel label_6 = new JLabel("\u9ED8\u8BA4\u6253\u5370\u673A\uFF1A");
		label_6.setForeground(SystemColor.controlShadow);
		label_6.setFont(new Font("����", Font.PLAIN, 12));
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
		defaultPrinter.setFont(new Font("����", Font.ITALIC, 10));
		defaultPrinter.setText("printer1");
		defaultPrinter.setBounds(98, 22, 187, 21);
		contentPane.add(defaultPrinter);
		defaultPrinter.setColumns(10);

		updateData = new JButton("\u5237\u65B0\u6570\u636E");
		updateData.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// ��ȡ��ӡ����
				getLabelData().execute();
			}
		});
		updateData.setBounds(425, 10, 93, 23);
		contentPane.add(updateData);

		label = new JLabel("\u5DE6\u8FB9\u8DDD\uFF1A");
		label.setBounds(20, 386, 54, 23);
		contentPane.add(label);

		paddingLeft = new JTextField();
		paddingLeft.setText("0");
		paddingLeft.setHorizontalAlignment(SwingConstants.CENTER);
		paddingLeft.setColumns(10);
		paddingLeft.setBounds(78, 386, 66, 24);
		contentPane.add(paddingLeft);

		paddingTop = new JTextField();
		paddingTop.setText("0");
		paddingTop.setHorizontalAlignment(SwingConstants.CENTER);
		paddingTop.setColumns(10);
		paddingTop.setBounds(227, 386, 66, 24);
		contentPane.add(paddingTop);

		label_7 = new JLabel("\u4E0A\u8FB9\u8DDD\uFF1A");
		label_7.setBounds(169, 388, 54, 21);
		contentPane.add(label_7);

		reprintBtn = new JButton("\u8865\u6253\u5370");
		reprintBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				RePrinterScreen rps = RePrinterScreen.getScreen();
				rps.setVisible(true);
			}
		});
		reprintBtn.setBounds(305, 386, 93, 23);
		contentPane.add(reprintBtn);

		JLabel label_8 = new JLabel("\u8BA2\u5355\u53F7\uFF1A");
		label_8.setBounds(578, 12, 54, 21);
		contentPane.add(label_8);

		searchData = new JTextField();
		searchData.setHorizontalAlignment(SwingConstants.CENTER);
		searchData.setColumns(10);
		searchData.setBounds(629, 12, 174, 21);
		contentPane.add(searchData);

		searchBtn = new JButton("\u67E5\u8BE2");
		searchBtn.setEnabled(false);
		searchBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (searchBtn.isEnabled()) {
					String searchStr = searchData.getText().toString();
					if (searchStr.isEmpty()) {// ���δ��д����ԭ��������
						// �ָ��б�
						updateTable(list);
						return;
					}
					List<Hanger> tempList = new ArrayList<>();
					for (Hanger h : list) {
						if (searchStr.equals(h.getOrNo())) {
							// ���ݴ�����ʱ�б�
							tempList.add(h);
						}
					}
					if (tempList.size() == 0) {
						JOptionPane.showMessageDialog(null, "�����ݣ�");
						return;
					} else {
						// ����table
						updateTable(tempList);
					}
				}
			}
		});
		searchBtn.setBounds(828, 10, 93, 23);
		contentPane.add(searchBtn);

		progressLabel = new JLabel("\u8FDB\u5EA6\uFF1A");
		progressLabel.setFont(new Font("����", Font.ITALIC, 12));
		progressLabel.setBounds(138, 171, 45, 17);
		progressLabel.setVisible(false);
		contentPane.add(progressLabel);

		progressBar = new JProgressBar();
		progressBar.setBounds(189, 174, 104, 14);
		progressBar.setVisible(false);
		contentPane.add(progressBar);

		progressText = new JLabel("0/0");
		progressText.setHorizontalAlignment(SwingConstants.CENTER);
		progressText.setFont(new Font("����", Font.ITALIC, 11));
		progressText.setBounds(296, 174, 83, 15);
		progressText.setVisible(false);
		contentPane.add(progressText);

		JLabel label_4 = new JLabel("\u6240\u9009\u6807\u7B7E");
		label_4.setForeground(SystemColor.controlShadow);
		label_4.setBackground(Color.LIGHT_GRAY);
		label_4.setBounds(30, 172, 54, 15);
		contentPane.add(label_4);

		exampleImage = new JLabel("");
		exampleImage.setHorizontalAlignment(SwingConstants.CENTER);
		exampleImage.setBounds(20, 195, 363, 182);
		icon2 = new ImageIcon(getClass().getResource("/images/wittur.png"));
		icon2.setImage(icon2.getImage().getScaledInstance(350, 180, Image.SCALE_DEFAULT));
		exampleImage.setIcon(icon2);
		contentPane.add(exampleImage);

		choseFile = new JLabel("\u6570\u636E\u8DEF\u5F84\uFF1A");
		choseFile.setFont(new Font("����", Font.PLAIN, 15));
		choseFile.setBounds(30, 100, 75, 15);
		choseFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				choseFile.setForeground(Color.RED);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				choseFile.setForeground(Color.BLACK);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser jfc = new JFileChooser();
				// Ĭ��Ŀ¼��C��
				jfc.setCurrentDirectory(new File("C:\\"));
				// jfc.changeToParentDirectory();
				// ֻ����ѡ���ļ�
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				// �����ļ����ˣ��ر�
				jfc.setAcceptAllFileFilterUsed(false);
				// �Զ������
				jfc.setFileFilter(new FileFilter() {
					@Override
					public String getDescription() {
						return ".xls �� .xlsx";
					}

					@Override
					public boolean accept(File f) {
						// ����ֻ���ļ��к�excel�ļ��ɼ�
						if (f.isDirectory() || f.getName().endsWith(".xls") || f.getName().endsWith(".xlsx")) {
							return true;
						}
						return false;
					}
				});
				// ��������
				jfc.showDialog(new JLabel(), "ѡ���ļ�");
				// ��ȡѡ�е��ļ�
				File file = jfc.getSelectedFile();
				if (null != file) {
					filePath.setText(file.getPath());
				}
			}
		});
		contentPane.add(choseFile);

		filePath = new JTextField();
		filePath.setColumns(10);
		filePath.setBounds(30, 125, 238, 21);
		contentPane.add(filePath);

		JSeparator separator = new JSeparator();
		separator.setBounds(30, 159, 355, 5);
		contentPane.add(separator);

		// Ĭ�ϻ�ȡ��һ����ӡ��
		getCurrentPrinter(1);

		// ��ȡ��ӡ����
		getLabelData().execute();
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
	 * ��ȡ��Ҫ��ӡ�����ݡ�
	 * 
	 * @return
	 */
	private SwingWorker<Void, Void> getLabelData() {
		SwingWorker<Void, Void> getData = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				try {
					Connection conn = OracleUtils.getConnection();
					Statement stat = conn.createStatement();
					String sql = "SELECT ID, BOXID, ITEM, DSCE, ORNO, PONO, CODE, PYNDATE, STATE, STATION, NINECODE, SNO FROM CBARCODE_PENDANT WHERE STATE = 1";
					ResultSet rs = stat.executeQuery(sql);
					list = new ArrayList<Hanger>();
					Hanger hanger = null;
					while (rs.next()) {
						hanger = new Hanger();
						hanger.setID(rs.getInt("ID"));
						hanger.setBoxId(rs.getString("BOXID"));
						hanger.setItem(rs.getString("ITEM"));
						hanger.setDsce(rs.getString("DSCE"));
						hanger.setOrNo(rs.getString("ORNO"));
						hanger.setPoNo(rs.getString("PONO"));
						hanger.setCode(rs.getString("CODE"));
						hanger.setPynDate(rs.getString("PYNDATE"));
						hanger.setState(rs.getInt("STATE"));
						hanger.setStation(rs.getString("STATION"));
						hanger.setNineCode(rs.getString("NINECODE"));
						hanger.setSno(rs.getString("SNO"));
						list.add(hanger);

					}
					rs.close();
					stat.close();
					conn.close();
					System.out.println("finish Load\tlist size is :" + list.size());

					// ���±��
					updateTable(list);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return null;
			}

		};
		return getData;
	}

	/**
	 * ˢ�±�����ݡ�
	 * 
	 * @param list
	 */
	private void updateTable(List<Hanger> list) {
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
		// ������ť����Ϊ����
		searchBtn.setEnabled(true);
	}
}
