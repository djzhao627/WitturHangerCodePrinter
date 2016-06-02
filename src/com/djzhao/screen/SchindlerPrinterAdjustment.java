package com.djzhao.screen;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.djzhao.db.SQLiteJDBC;
import java.awt.Toolkit;

public class SchindlerPrinterAdjustment extends JFrame {

	private static final long serialVersionUID = 9197182292218634400L;
	private JPanel contentPane;
	private JComboBox<String> printerList;
	private int ID = 6;

	public SchindlerPrinterAdjustment(int id) throws HeadlessException {
		super();
		this.ID = id;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SchindlerPrinterAdjustment frame = new SchindlerPrinterAdjustment();
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
	public SchindlerPrinterAdjustment() {
		setTitle("\u5339\u914D\u6253\u5370\u673A");
		this.setIconImage(Toolkit.getDefaultToolkit()
				.getImage(SchindlerPrinterAdjustment.class.getResource("/images/Wittur_Logo.gif")));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 390, 236);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(230, 230, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("\u8FC5\u8FBE\u6253\u5370\u673A\u5339\u914D");
		label.setBounds(5, 5, 359, 53);
		label.setForeground(new Color(0, 0, 0));
		label.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(label);

		printerList = new JComboBox<String>();
		printerList.setBackground(new Color(230, 230, 250));
		printerList.setBounds(57, 75, 255, 21);
		contentPane.add(printerList);

		JButton button = new JButton("\u786E\u5B9A");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (updateSQLite(ID, printerList.getSelectedItem().toString())) {
					JOptionPane.showMessageDialog(null, "打印机匹配成功!", "提示消息", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "打印机匹配失败\n请再试！", "提示消息", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		button.setBackground(new Color(230, 230, 250));
		button.setBounds(139, 140, 93, 23);
		contentPane.add(button);

		// 获取打印机
		getPrinterList();
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
	private boolean updateSQLite(int id, String printer) {
		Connection conn = new SQLiteJDBC().getConnection();
		int row = 0;
		try {
			Statement stat = conn.createStatement();
			String sql = "update printer set printName = '" + printer + "' where ID = " + id;
			row = stat.executeUpdate(sql);
			stat.close();
			conn.close();
			if (row > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void setID(int id) {
		this.ID = id;
	}

}
