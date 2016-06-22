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
 * @time 2015��11��10��
 */
public class ExportEXL implements ActionListener {

	DateChooser dateChooser1 = DateChooser.getInstance("yyyy-MM-dd");

	DateChooser dateChooser2 = DateChooser.getInstance("yyyy-MM-dd");

	JFrame frame = new JFrame("��������");// ��ܲ���
	JTabbedPane tabPane = new JTabbedPane();// ѡ�����
	Container con = new Container();// ���
	JLabel label1 = new JLabel("�ļ�Ŀ¼");
	JLabel label2 = new JLabel("\u4FDD\u5B58\u6587\u4EF6");
	JTextField text1 = new JTextField();// TextField Ŀ¼��·��
	JTextField text2 = new JTextField("\u5DF2\u6253\u5370\u6570\u636E");// �ļ���·��
	JButton choseFolder = new JButton("...");// ѡ��
	JButton button2 = new JButton("...");// ѡ��
	JFileChooser jfc = new JFileChooser();// �ļ�ѡ����
	JButton exportData = new JButton("����");//
	JLabel date2 = new JLabel("����ѡ��ʱ��");
	JLabel date1 = new JLabel("����ѡ��ʱ��");

	private static boolean time1 = false;
	private static boolean time2 = false;

	/** Ŀ¼·�� */
	private File directory = null;
	/** �ļ�·�� */
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

		jfc.setCurrentDirectory(new File("D://"));// �ļ�ѡ�����ĳ�ʼĿ¼��Ϊd��

		double lx = Toolkit.getDefaultToolkit().getScreenSize().getWidth();

		double ly = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		frame.setResizable(false);

		frame.setLocation(new Point((int) (lx / 2) - 150, (int) (ly / 2) - 150));// �趨���ڳ���λ��
		frame.setIconImage(new ImageIcon(getClass().getResource("/images/Wittur_Logo.gif")).getImage());
		frame.setSize(382, 273);
		tabPane.setFont(new Font("����", Font.ITALIC, 12));
		tabPane.setBackground(new Color(204, 255, 204));
		frame.setContentPane(tabPane);// ���ò���
		label1.setBounds(25, 118, 70, 20);
		text1.setEditable(false);
		text1.setBounds(90, 118, 120, 20);
		choseFolder.setBounds(225, 118, 50, 20);
		label2.setBounds(25, 143, 70, 20);
		text2.setBounds(90, 143, 120, 20);
		button2.setEnabled(false);
		button2.setBounds(225, 143, 50, 20);
		exportData.setBounds(291, 175, 60, 20);
		choseFolder.addActionListener(this); // ����¼�����
		button2.addActionListener(this); // ����¼�����
		exportData.addActionListener(this); // ����¼�����
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
		frame.setVisible(true);// ���ڿɼ�
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// ʹ�ܹرմ��ڣ���������
		tabPane.add("�����Ѵ�ӡ����", con);// ��Ӳ���1

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
	 * �¼������ķ�����
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(choseFolder)) {// �жϴ��������İ�ť���ĸ�
			jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);// �趨ֻ��ѡ���ļ���
			int state = jfc.showOpenDialog(null);// �˾��Ǵ��ļ�ѡ��������Ĵ������
			if (state == 1) {
				return;
			} else {
				directory = jfc.getSelectedFile();// directoryΪѡ�񵽵�Ŀ¼
				text1.setText(directory.getAbsolutePath());
			}
		}
		// �󶨵�ѡ���ļ���ѡ���ļ��¼�
		if (e.getSource().equals(button2)) {
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);// �趨ֻ��ѡ���ļ�
			int state = jfc.showOpenDialog(null);// �˾��Ǵ��ļ�ѡ��������Ĵ������
			if (state == 1) {
				return;// �����򷵻�
			} else {
				file = jfc.getSelectedFile();// fileΪѡ�񵽵��ļ�
				text2.setText(file.getAbsolutePath());
			}
		}
		if (e.getSource().equals(exportData)) {// �����ݶ�ȡ���ݣ�д��Excel

			if (text1.getText().length() < 2) {
				JOptionPane.showMessageDialog(null, "��ѡ��Ŀ��·����", "����", 0);
			} else if (time1 && time2) {

				boolean flag = true;

				/** 1.��ȡ���� */
				// �����б�洢����
				List<Schindler> list = new ArrayList<>();
				try {
					// �����ݿ�ȡ����
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

				/** 2.д��Excel */
				try {
					// ����һ��������
					WritableWorkbook wwb = Workbook
							.createWorkbook(new File(directory.getAbsolutePath() + "\\" + text2.getText() + ".xls"));
					if (wwb != null) {
						// ����һ��������
						WritableSheet ws = wwb.createSheet("�����ѯ", 0);
						// ����cell��ʽ
						CellView cv = new CellView();
						cv.setAutosize(true);
						// ���ñ���
						String[] head = new String[] { "id", "productName", "releaseNo", "revisionNo",
								"identificationNo", "serialNo", "batchNo", "manufacture", "importer", "time" };

						// �������ݿ�����
						int row = 1;// �ӵڶ��п�ʼ����
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
							// ����һ��
							row++;
						}

						// ��ʼ��ӵ�һ�б��ⵥԪ��
						for (int j = 0; j < head.length; j++) {
							// ��һ��������ʾ�У��ڶ�����ʾ��
							Label labelC = new Label(j, 0, head[j]);
							// ����Ԫ����ӵ���������
							ws.addCell(labelC);
							switch (j) {
							case 7:
								// �����п�
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
						// ���ڴ���д�뵽�ļ���
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
					JOptionPane.showMessageDialog(null, "���ɱ���ɹ���\n�뵽���Ŀ¼�鿴��", "��ʾ", 1);
				} else {
					JOptionPane.showMessageDialog(null, "����ʱ�������������ԣ�", "����", 0);
				}
			} else {
				JOptionPane.showMessageDialog(null, "��ѡ��ʱ�䣡", "����", 0);
			}
		}
	}

	public static void main(String[] args) {
		new ExportEXL();
	}

}