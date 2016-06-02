package com.djzhao.screen;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
import javax.swing.table.TableColumnModel;

import com.djzhao.db.OracleUtils;
import com.djzhao.db.SQLiteJDBC;
import com.djzhao.model.Hanger;

public class PrinterScreen extends JFrame {

	private static final long serialVersionUID = 1936120881913522686L;
	private JPanel contentPane;
	private JTable table;
	private JTextField defaultPrinter;
	private JComboBox<String> codeType;
	private JButton goPrint;
	private JLabel exampleImage;
	private ImageIcon icon2;
	private JTextField filePath;
	private JLabel choseFile;
	private String[] heads = { "选择", "ID", "箱号", "物料号", "描述", "订单号", "订单行", "序列码", "唯一码" };
	private JButton updateBtn;
	private JLabel progressLabel;
	private JProgressBar progressBar;
	private JLabel progressText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrinterScreen frame = new PrinterScreen();
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
	public PrinterScreen() {
		setResizable(false);
		this.setIconImage(new ImageIcon(getClass().getResource("/images/Wittur_Logo.gif")).getImage());
		setTitle("Printer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1203, 529);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("");
		label.setBounds(20, 10, 100, 100);
		ImageIcon icon = new ImageIcon(getClass().getResource("/images/Wittur_Logo.gif"));
		icon.setImage(
				icon.getImage().getScaledInstance(icon.getIconWidth(), icon.getIconHeight(), Image.SCALE_DEFAULT));
		label.setIcon(icon);
		contentPane.add(label);

		JSeparator separator = new JSeparator();
		separator.setBounds(20, 226, 370, 8);
		contentPane.add(separator);

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
		codeType.setFont(new Font("宋体", Font.PLAIN, 16));
		codeType.setModel(new DefaultComboBoxModel<String>(new String[] { "Wittur", "\u8FC5\u8FBE\u4E0D\u51FA\u53E3",
				"\u8FC5\u8FBE\u51FA\u53E3", "AMD", "\u6761\u7801" }));
		codeType.setBounds(188, 127, 160, 21);
		contentPane.add(codeType);

		JLabel label_1 = new JLabel("\u9009\u62E9\u6807\u7B7E\u7C7B\u522B\uFF1A");
		label_1.setFont(new Font("宋体", Font.PLAIN, 16));
		label_1.setBounds(49, 128, 116, 18);
		contentPane.add(label_1);

		JLabel label_2 = new JLabel("\u6240\u9009\u6807\u7B7E");
		label_2.setForeground(SystemColor.controlShadow);
		label_2.setBackground(Color.LIGHT_GRAY);
		label_2.setBounds(30, 244, 54, 15);
		contentPane.add(label_2);

		goPrint = new JButton("\u6253\u5370");
		goPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		goPrint.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (goPrint.isEnabled()) {
					goToPrint().execute();
				}
			}

		});
		goPrint.setBounds(297, 188, 93, 23);
		contentPane.add(goPrint);

		exampleImage = new JLabel("");
		exampleImage.setBounds(27, 269, 363, 182);
		icon2 = new ImageIcon(getClass().getResource("/images/wittur.png"));
		icon2.setImage(icon2.getImage().getScaledInstance(350, 180, Image.SCALE_DEFAULT));
		exampleImage.setIcon(icon2);
		contentPane.add(exampleImage);

		JLabel label_5 = new JLabel("\u6807\u7B7E\u8BE6\u7EC6\u8BBE\u7F6E");
		label_5.setForeground(SystemColor.controlDkShadow);
		label_5.setBounds(302, 244, 87, 15);
		contentPane.add(label_5);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(414, 59, 762, 431);
		contentPane.add(scrollPane);

		table = new JTable();
		table.getTableHeader().setReorderingAllowed(false);
		table.setName("\u6253\u5370\u6E05\u5355");
		table.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setSurrendersFocusOnKeystroke(true);
		table.setAutoCreateRowSorter(true);
		table.setModel(
				new DefaultTableModel(new Object[][] { { null, null, null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null, null }, }, heads));

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
		// 单元格宽度
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
		table.getColumnModel().getColumn(4).setPreferredWidth(150);
		table.getColumnModel().getColumn(5).setPreferredWidth(150);
		table.getColumnModel().getColumn(6).setPreferredWidth(150);
		table.getColumnModel().getColumn(7).setPreferredWidth(150);
		scrollPane.setViewportView(table);

		JLabel label_6 = new JLabel("\u9ED8\u8BA4\u6253\u5370\u673A\uFF1A");
		label_6.setForeground(SystemColor.controlShadow);
		label_6.setFont(new Font("宋体", Font.PLAIN, 12));
		label_6.setBounds(135, 23, 116, 18);
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
		button_2.setBounds(303, 62, 87, 23);
		contentPane.add(button_2);

		defaultPrinter = new JTextField();
		defaultPrinter.setHorizontalAlignment(SwingConstants.CENTER);
		defaultPrinter.setEditable(false);
		defaultPrinter.setFont(new Font("宋体", Font.ITALIC, 10));
		defaultPrinter.setText("printer1");
		defaultPrinter.setBounds(203, 23, 187, 21);
		contentPane.add(defaultPrinter);
		defaultPrinter.setColumns(10);

		JLabel label_3 = new JLabel("\u9700\u8981\u6253\u5370\u7684\u6302\u4EF6");
		label_3.setForeground(SystemColor.controlShadow);
		label_3.setBackground(Color.LIGHT_GRAY);
		label_3.setBounds(414, 22, 113, 15);
		contentPane.add(label_3);

		updateBtn = new JButton("\u5237\u65B0");
		updateBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (updateBtn.isEnabled()) {
					getPrintData();
				}
			}
		});
		updateBtn.setBounds(532, 18, 93, 23);
		contentPane.add(updateBtn);

		filePath = new JTextField();
		filePath.setBounds(49, 189, 238, 21);
		contentPane.add(filePath);
		filePath.setColumns(10);

		choseFile = new JLabel("\u6570\u636E\u8DEF\u5F84\uFF1A");
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
				jfc.setCurrentDirectory(new File("C:\\"));
				// jfc.changeToParentDirectory();
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				jfc.setAcceptAllFileFilterUsed(false);
				jfc.setFileFilter(new FileFilter() {
					@Override
					public String getDescription() {
						return ".xls 和 .xlsx";
					}

					@Override
					public boolean accept(File f) {
						if (f.isDirectory() || f.getName().endsWith(".xls") || f.getName().endsWith(".xlsx")) {
							return true;
						}
						return false;
					}
				});
				jfc.showDialog(new JLabel(), "选择文件");
				File file = jfc.getSelectedFile();
				if (null != file) {
					filePath.setText(file.getPath());
				}
			}
		});
		choseFile.setFont(new Font("宋体", Font.PLAIN, 15));
		choseFile.setBounds(49, 164, 75, 15);
		contentPane.add(choseFile);

		progressLabel = new JLabel("\u6253\u5370\u8FDB\u5EA6\uFF1A");
		progressLabel.setFont(new Font("微软雅黑", Font.ITALIC, 13));
		progressLabel.setBounds(832, 25, 75, 15);
		progressLabel.setVisible(false);
		contentPane.add(progressLabel);

		progressBar = new JProgressBar();
		progressBar.setValue(34);
		progressBar.setBounds(910, 27, 146, 14);
		progressBar.setVisible(false);
		contentPane.add(progressBar);

		progressText = new JLabel("0/0");
		progressText.setForeground(Color.GRAY);
		progressText.setFont(new Font("微软雅黑", Font.ITALIC, 13));
		progressText.setBounds(1063, 25, 75, 15);
		progressText.setVisible(false);
		contentPane.add(progressText);

		// 默认获取第一个打印机
		getCurrentPrinter(1);

		// 获取数据库数据
		// getPrintData();

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
	 * 获取数据库数据。
	 */
	private void getPrintData() {
		try {
			Connection conn = OracleUtils.getConnection();
			Statement stat = conn.createStatement();
			String sql = "SELECT ID, BOXID, ITEM, DSCE, ORNO, PONO, CODE, PYNDATE, STATE, STATION, NINECODE FROM CBARCODE_PENDANT WHERE STATE = 1";
			ResultSet rs = stat.executeQuery(sql);
			List<Hanger> list = new ArrayList<Hanger>();
			Hanger hanger = null;
			while (rs.next()) {
				// 合并相同物料号
				// if (!item.equals(tempItem)) {
				// if (printNumber != 0) {
				// hanger = new Hanger();
				// hanger.setID(rs.getInt("ID"));
				// hanger.setBoxId(rs.getString("BOXID"));
				// hanger.setItem(rs.getString("ITEM"));
				// hanger.setDsce(rs.getString("DSCE"));
				// hanger.setOrNo(rs.getString("ORNO"));
				// hanger.setCode(rs.getString("CODE"));
				// hanger.setPynDate(rs.getString("PYNDATE"));
				// hanger.setState(rs.getInt("STATE"));
				// hanger.setStation(rs.getString("STATION"));
				// hanger.setNineCode("NINECODE");
				// hanger.setPrintNumber(printNumber);
				// list.add(hanger);
				// printNumber = 0;
				// } else {
				// item = tempItem;
				// }
				// } else {
				// printNumber++;
				// item = tempItem;
				// }

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
				list.add(hanger);

			}
			System.out.println("finish Load\tlist size is :" + list.size());
			rs.close();
			stat.close();
			conn.close();

			Object[][] data = new Object[list.size()][9];
			for (int i = 0; i < list.size(); i++) {
				data[i][1] = list.get(i).getID();
				data[i][2] = list.get(i).getBoxId();
				data[i][3] = list.get(i).getItem();
				data[i][4] = list.get(i).getDsce();
				data[i][5] = list.get(i).getOrNo();
				data[i][6] = list.get(i).getPoNo();
				data[i][7] = list.get(i).getCode();
				data[i][8] = list.get(i).getNineCode();
			}
			table.setModel(new DefaultTableModel(data, heads));
			TableColumnModel tcm = table.getColumnModel();
			tcm.getColumn(0).setCellEditor(new DefaultCellEditor(new JCheckBox()));
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
			// table.getColumnModel().getColumn(0).setPreferredWidth(100);
			table.getColumnModel().getColumn(2).setPreferredWidth(120);
			table.getColumnModel().getColumn(3).setPreferredWidth(150);
			table.getColumnModel().getColumn(4).setPreferredWidth(150);
			table.getColumnModel().getColumn(5).setPreferredWidth(100);
			table.getColumnModel().getColumn(6).setPreferredWidth(50);
			table.getColumnModel().getColumn(7).setPreferredWidth(350);
			table.getColumnModel().getColumn(8).setPreferredWidth(150);
			table.updateUI();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 打印。
	 */
	private SwingWorker<Void, Void> goToPrint() {

		SwingWorker<Void, Void> print = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {

				goPrint.setEnabled(false);
				updateBtn.setEnabled(false);

				// for (int i = 0; i < table.getRowCount(); i++) {
				//
				// int type = codeType.getSelectedIndex();
				// switch (type) {
				// case 0:
				// Wittur w = new Wittur("qqq", "qqq", "333", "1234", "00000");
				// w.printcode();
				// break;
				// case 1:
				// XD wax = new XD();
				// wax.printcode();
				// break;
				// case 2:
				// WitturAndXDC waxc = new WitturAndXDC();
				// waxc.printcode();
				// break;
				// case 3:
				// WitturAndAMD waa = new WitturAndAMD();
				// waa.printcode();
				// break;
				// case 4:
				// AMDAndCode aac = new AMDAndCode();
				// aac.printcode();
				// break;
				// default:
				// break;
				// }
				// }

				int count = table.getRowCount();
				progressLabel.setVisible(true);
				progressBar.setVisible(true);
				progressBar.setMaximum(count);
				progressText.setText("1/" + count);
				progressText.setVisible(true);
				for (int i = 0; i < table.getRowCount(); i++) {
					progressBar.setValue(i + 1);
					progressText.setText((i + 1) + "/" + count);
					Thread.sleep(100);
				}

				goPrint.setEnabled(true);
				updateBtn.setEnabled(true);

				return null;
			}

		};
		return print;
	}
}
