package com.djzhao.screen;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.djzhao.db.MySQLUtil;
import com.djzhao.model.Schindler;

import jxl.CellView;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * 
 * @author djzhao
 * @time 2015年11月10日
 */
public class ExportEXL implements ActionListener {

	DateChooser dateChooser1 = DateChooser.getInstance("yyyy-MM-dd");

	DateChooser dateChooser2 = DateChooser.getInstance("yyyy-MM-dd");

	JFrame frame = new JFrame("导出报表");// 框架布局
	JTabbedPane tabPane = new JTabbedPane();// 选项卡布局
	Container con = new Container();// 面板
	JLabel label1 = new JLabel("文件目录");
	JLabel label2 = new JLabel("\u4FDD\u5B58\u6587\u4EF6");
	JTextField text1 = new JTextField();// TextField 目录的路径
	JTextField text2 = new JTextField("\u5DF2\u6253\u5370\u6570\u636E");// 文件的路径
	JButton choseFolder = new JButton("...");// 选择
	JButton button2 = new JButton("...");// 选择
	JFileChooser jfc = new JFileChooser();// 文件选择器
	JButton exportData = new JButton("导出");//
	JLabel date2 = new JLabel("单击选择时间");
	JLabel date1 = new JLabel("单击选择时间");

	private static boolean time1 = false;
	private static boolean time2 = false;

	/** 目录路径 */
	private File directory = null;
	/** 文件路径 */
	private File file = null;
	
	private static ExportEXL exportEXL = null;
	
	public static ExportEXL getScreen(){
		if (exportEXL == null) {
			exportEXL = new ExportEXL();
		}
		return exportEXL;
	}

	ExportEXL() {

		dateChooser1.register(date1);

		dateChooser2.register(date2);

		jfc.setCurrentDirectory(new File("D://"));// 文件选择器的初始目录定为d盘

		double lx = Toolkit.getDefaultToolkit().getScreenSize().getWidth();

		double ly = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		frame.setResizable(false);

		frame.setLocation(new Point((int) (lx / 2) - 150, (int) (ly / 2) - 150));// 设定窗口出现位置
		frame.setIconImage(new ImageIcon(getClass().getResource("/images/Wittur_Logo.gif")).getImage());
		frame.setSize(382, 273);
		tabPane.setFont(new Font("宋体", Font.ITALIC, 12));
		tabPane.setBackground(new Color(204, 255, 204));
		frame.setContentPane(tabPane);// 设置布局
		label1.setBounds(25, 118, 70, 20);
		text1.setEditable(false);
		text1.setBounds(90, 118, 120, 20);
		choseFolder.setBounds(225, 118, 50, 20);
		label2.setBounds(25, 143, 70, 20);
		text2.setBounds(90, 143, 120, 20);
		button2.setEnabled(false);
		button2.setBounds(225, 143, 50, 20);
		exportData.setBounds(291, 175, 60, 20);
		choseFolder.addActionListener(this); // 添加事件处理
		button2.addActionListener(this); // 添加事件处理
		exportData.addActionListener(this); // 添加事件处理
		con.setBackground(new Color(255, 255, 255));
		con.add(label1);
		con.add(text1);
		con.add(choseFolder);
		con.add(label2);
		con.add(text2);
		con.add(button2);
		con.add(exportData);
		con.add(date1);
		con.add(date2);
		frame.setVisible(true);// 窗口可见
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// 使能关闭窗口，结束程序
		tabPane.add("导出已打印数据", con);// 添加布局1

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(ExportEXL.class.getResource("/images/Wittur_Logo.gif")));
		label.setBounds(10, 10, 100, 90);
		con.add(label);

		JLabel label_2 = new JLabel("\u5F00\u59CB\u65F6\u95F4\uFF1A");
		label_2.setFont(new Font("Dialog", Font.PLAIN, 15));
		label_2.setBounds(132, 24, 78, 22);
		con.add(label_2);

		JLabel label_3 = new JLabel("\u7ED3\u675F\u65F6\u95F4\uFF1A");
		label_3.setFont(new Font("Dialog", Font.PLAIN, 15));
		label_3.setBounds(132, 61, 78, 22);
		con.add(label_3);

		date2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				time2 = true;
			}
		});
		date2.setForeground(new Color(0, 0, 0));
		date2.setFont(new Font("Dialog", Font.PLAIN, 15));
		date2.setBounds(225, 61, 100, 22);

		date1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				time1 = true;
			}
		});
		date1.setForeground(new Color(0, 0, 0));
		date1.setFont(new Font("Dialog", Font.PLAIN, 15));
		date1.setBounds(225, 26, 100, 19);

	}

	/**
	 * 事件监听的方法。
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(choseFolder)) {// 判断触发方法的按钮是哪个
			jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);// 设定只能选择到文件夹
			int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
			if (state == 1) {
				return;
			} else {
				directory = jfc.getSelectedFile();// directory为选择到的目录
				text1.setText(directory.getAbsolutePath());
			}
		}
		// 绑定到选择文件，选择文件事件
		if (e.getSource().equals(button2)) {
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);// 设定只能选择到文件
			int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
			if (state == 1) {
				return;// 撤销则返回
			} else {
				file = jfc.getSelectedFile();// file为选择到的文件
				text2.setText(file.getAbsolutePath());
			}
		}
		if (e.getSource().equals(exportData)) {// 从数据读取数据，写入Excel

			if (text1.getText().length() < 2) {
				JOptionPane.showMessageDialog(null, "请选择目的路径！", "错误", 0);
			} else if (time1 && time2) {

				boolean flag = true;

				/** 1.获取数据 */
				// 创建列表存储数据
				List<Schindler> list = new ArrayList<>();
				try {
					// 从数据库取数据
					Connection coon = MySQLUtil.getConn();
					Statement state = coon.createStatement();
					String sql = "select id, productName, releaseNo, revisionNo, identificationNo, serialNo, batchNo, manufacture, importer, time  from printrecord where status = 1 and time between '"
							+ date1.getText().toString() + "' and '" + date2.getText().toString() + " 23:59:59'";
					ResultSet rs = state.executeQuery(sql);
					while (rs.next()) {
						Schindler s = new Schindler();
						s.setId(rs.getInt("id"));
						s.setProductName(rs.getString("productName"));
						s.setReleaseNo(rs.getString("releaseNo"));
						s.setRevisionNo(rs.getString("revisionNo"));
						s.setIdentificationNo(rs.getString("identificationNo"));
						s.setSerialNo(rs.getString("serialNo"));
						s.setBatchNo(rs.getString("batchNo"));
						s.setManufacture(rs.getString("manufacture"));
						s.setImporter(rs.getString("importer"));
						s.setTime(rs.getString("time"));
						list.add(s);
					}
				} catch (SQLException e2) {
					e2.printStackTrace();
				}

				/** 2.写入Excel */
				try {
					// 创建一个工作簿
					WritableWorkbook wwb = Workbook
							.createWorkbook(new File(directory.getAbsolutePath() + "\\" + text2.getText() + ".xls"));
					if (wwb != null) {
						// 创建一个工作表
						WritableSheet ws = wwb.createSheet("情况查询", 0);
						// 设置cell格式
						CellView cv = new CellView();
						cv.setAutosize(true);
						// 设置标题
						String[] head = new String[] { "id", "productName", "releaseNo", "revisionNo",
								"identificationNo", "serialNo", "batchNo", "manufacture", "importer", "time" };

						// 插入数据库数据
						int row = 1;// 从第二行开始插入
						for (int i = 0; i < list.size(); i++) {
							Label labelC1 = new Label(0, row, list.get(i).getId() + "");
							ws.addCell(labelC1);
							Label labelC2 = new Label(1, row, list.get(i).getProductName());
							ws.addCell(labelC2);
							Label labelC3 = new Label(2, row, list.get(i).getReleaseNo());
							ws.addCell(labelC3);
							Label labelC4 = new Label(3, row, list.get(i).getRevisionNo());
							ws.addCell(labelC4);
							Label labelC5 = new Label(4, row, list.get(i).getIdentificationNo());
							ws.addCell(labelC5);
							Label labelC6 = new Label(5, row, list.get(i).getSerialNo());
							ws.addCell(labelC6);
							Label labelC7 = new Label(6, row, list.get(i).getBatchNo());
							ws.addCell(labelC7);
							Label labelC8 = new Label(7, row, list.get(i).getManufacture());
							ws.addCell(labelC8);
							Label labelC9 = new Label(8, row, list.get(i).getImporter());
							ws.addCell(labelC9);
							Label labelC10 = new Label(9, row, list.get(i).getTime());
							ws.addCell(labelC10);
							// 增加一行
							row++;
						}

						// 开始添加第一行标题单元格
						for (int j = 0; j < head.length; j++) {
							// 第一个参数表示列，第二个表示行
							Label labelC = new Label(j, 0, head[j]);
							// 将单元格添加到工作表中
							ws.addCell(labelC);
							switch (j) {
							case 7:
								// 设置列宽
								ws.setColumnView(j, 60);
								break;
							case 8:
								ws.setColumnView(j, 40);
								break;
							case 9:
								ws.setColumnView(j, 25);
								break;
							default:
								ws.setColumnView(j, 15);
								break;
							}
						}
						// 从内存中写入到文件中
						wwb.write();
						wwb.close();

					}
				} catch (IOException e1) {
					flag = false;
					e1.printStackTrace();
				} catch (RowsExceededException e1) {
					flag = false;
					e1.printStackTrace();
				} catch (WriteException e1) {
					flag = false;
					e1.printStackTrace();
				}
				if (flag) {
					JOptionPane.showMessageDialog(null, "生成报表成功！\n请到相关目录查看。", "提示", 1);
				} else {
					JOptionPane.showMessageDialog(null, "导出时发生错误，请重试！", "错误", 0);
				}
			} else {
				JOptionPane.showMessageDialog(null, "请选择时间！", "错误", 0);
			}
		}
	}

	public static void main(String[] args) {
		new ExportEXL();
	}

}