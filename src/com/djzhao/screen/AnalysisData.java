package com.djzhao.screen;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class AnalysisData extends JFrame {

	private static final long serialVersionUID = 688314925099937310L;
	private JPanel contentPane;
	private JTable table;
	private JTable table_1;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AnalysisData frame = new AnalysisData();
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
	public AnalysisData() {
		setResizable(false);
		this.setIconImage(new ImageIcon(getClass().getResource("/images/Wittur_Logo.gif")).getImage());
		setTitle("\u6570\u636E\u6821\u5BF9");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1080, 488);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaptionBorder);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("");
		label.setBounds(954, 10, 110, 109);
		ImageIcon icon = new ImageIcon(getClass().getResource("/images/Wittur_Logo.gif"));
		icon.setImage(
				icon.getImage().getScaledInstance(icon.getIconWidth(), icon.getIconHeight(), Image.SCALE_DEFAULT));
		label.setIcon(icon);
		contentPane.add(label);

		JLabel lblamd = new JLabel("AMD+\u901A\u529B\u6570\u636E \u6821\u5BF9");
		lblamd.setFont(new Font("ËÎÌו", Font.PLAIN, 16));
		lblamd.setBounds(478, 10, 138, 25);
		contentPane.add(lblamd);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 132, 1054, 9);
		contentPane.add(separator);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 169, 514, 280);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(
				new Object[][] { { null, null, null, null, null, null }, { null, null, null, null, null, null },
						{ null, null, null, null, null, null }, { null, null, null, null, null, null },
						{ null, null, null, null, null, null }, { null, null, null, null, null, null },
						{ null, null, null, null, null, null }, { null, null, null, null, null, null },
						{ null, null, null, null, null, null }, { null, null, null, null, null, null }, },
				new String[] { "New column", "New column", "New column", "New column", "New column", "New column" })
		// {
		// Class[] columnTypes = new Class[] {
		// Boolean.class, Object.class, Object.class, Object.class,
		// Object.class, Object.class
		// };
		// public Class<Object> getColumnClass(int columnIndex) {
		// return columnTypes[columnIndex];
		// }
		// }
		);
		scrollPane.setViewportView(table);

		JLabel label_1 = new JLabel("\u8868\u683C\u6570\u636E");
		label_1.setForeground(new Color(153, 204, 204));
		label_1.setBounds(10, 144, 54, 15);
		contentPane.add(label_1);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(550, 169, 514, 280);
		contentPane.add(scrollPane_1);

		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
				new Object[][] { { null, null, null, null, null, null }, { null, null, null, null, null, null },
						{ null, null, null, null, null, null }, { null, null, null, null, null, null },
						{ null, null, null, null, null, null }, { null, null, null, null, null, null },
						{ null, null, null, null, null, null }, { null, null, null, null, null, null },
						{ null, null, null, null, null, null }, { null, null, null, null, null, null }, },
				new String[] { "New column", "New column", "New column", "New column", "New column", "New column" }));
		scrollPane_1.setViewportView(table_1);

		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(535, 169, 9, 280);
		contentPane.add(separator_1);

		JLabel label_2 = new JLabel("\u6570\u636E\u5E93\u6570\u636E");
		label_2.setForeground(new Color(153, 204, 204));
		label_2.setBounds(555, 144, 70, 15);
		contentPane.add(label_2);

		JButton button = new JButton("\u5237\u65B0\u6570\u636E\u5E93");
		button.setBounds(550, 84, 103, 23);
		contentPane.add(button);

		JLabel label_3 = new JLabel("\u6570\u636E\u8DEF\u5F84\uFF1A");
		label_3.setFont(new Font("ËÎÌו", Font.PLAIN, 15));
		label_3.setBounds(25, 54, 86, 21);
		contentPane.add(label_3);

		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(25, 85, 138, 21);
		contentPane.add(textField);

		JButton button_1 = new JButton("...");
		button_1.setBounds(186, 84, 48, 23);
		contentPane.add(button_1);

		JButton button_2 = new JButton("\u5237\u65B0");
		button_2.setBounds(262, 84, 93, 23);
		contentPane.add(button_2);
		ImageIcon icon2 = new ImageIcon(getClass().getResource("/images/xunda.png"));
		icon2.setImage(icon2.getImage().getScaledInstance(350, 180, Image.SCALE_DEFAULT));
	}
}
