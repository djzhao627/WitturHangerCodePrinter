package com.djzhao.screen;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.SystemColor;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class LabelAdjustment extends JFrame {

	private static final long serialVersionUID = 8938068982886121868L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LabelAdjustment frame = new LabelAdjustment();
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
	public LabelAdjustment() {
		setResizable(false);
		this.setIconImage(new ImageIcon(getClass().getResource(
				"/images/Wittur_Logo.gif")).getImage());
		setTitle("\u6807\u7B7E\u8BBE\u7F6E");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 540, 488);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("");
		label.setBounds(414, 10, 110, 109);
		ImageIcon icon = new ImageIcon(getClass().getResource(
				"/images/Wittur_Logo.gif"));
		icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth(),
				icon.getIconHeight(), Image.SCALE_DEFAULT));
		label.setIcon(icon);
		contentPane.add(label);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 139, 664, 2);
		contentPane.add(separator);
		
		JLabel label_2 = new JLabel("\u6240\u9009\u6807\u7B7E");
		label_2.setForeground(SystemColor.controlShadow);
		label_2.setBackground(Color.LIGHT_GRAY);
		label_2.setBounds(20, 151, 54, 15);
		contentPane.add(label_2);
		ImageIcon icon2 = new ImageIcon(getClass().getResource(
				"/images/xunda.png"));
		icon2.setImage(icon2.getImage().getScaledInstance(350,
				180, Image.SCALE_DEFAULT));
		
		JLabel label_5 = new JLabel("\u6807\u7B7E\u8BE6\u7EC6\u8BBE\u7F6E");
		label_5.setForeground(SystemColor.controlDkShadow);
		label_5.setBounds(10, 10, 87, 15);
		contentPane.add(label_5);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaptionBorder);
		panel.setBounds(10, 166, 500, 284);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(2, 2, 496, 280);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel label_4 = new JLabel("");
		label_4.setBounds(72, 52, 331, 177);
		panel_1.add(label_4);
		label_4.setIcon(icon2);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(SystemColor.activeCaption);
		separator_1.setBounds(402, 152, 84, -12);
		panel_1.add(separator_1);
		
		JLabel label_1 = new JLabel("\u6807\u7B7E\u5927\u5C0F");
		label_1.setForeground(new Color(0, 0, 0));
		label_1.setBounds(10, 35, 54, 15);
		contentPane.add(label_1);
		
		JLabel label_3 = new JLabel("\u957F\uFF1A");
		label_3.setBounds(20, 62, 54, 15);
		contentPane.add(label_3);
		
		JLabel label_6 = new JLabel("\u5BBD\uFF1A");
		label_6.setBounds(20, 87, 54, 15);
		contentPane.add(label_6);
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setText("50");
		textField.setBounds(54, 59, 66, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setText("50");
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setColumns(10);
		textField_1.setBounds(54, 84, 66, 21);
		contentPane.add(textField_1);
		
		JLabel label_7 = new JLabel("\u8FB9\u8DDD\u8BBE\u7F6E\uFF1A");
		label_7.setForeground(Color.BLACK);
		label_7.setBounds(170, 35, 74, 15);
		contentPane.add(label_7);
		
		JLabel label_8 = new JLabel("\u4E0A\uFF1A");
		label_8.setBounds(180, 62, 54, 15);
		contentPane.add(label_8);
		
		textField_2 = new JTextField();
		textField_2.setText("50");
		textField_2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_2.setColumns(10);
		textField_2.setBounds(214, 59, 66, 21);
		contentPane.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setText("50");
		textField_3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_3.setColumns(10);
		textField_3.setBounds(214, 84, 66, 21);
		contentPane.add(textField_3);
		
		JLabel label_9 = new JLabel("\u4E0B\uFF1A");
		label_9.setBounds(180, 87, 54, 15);
		contentPane.add(label_9);
		
		JLabel label_10 = new JLabel("\u5DE6\uFF1A");
		label_10.setBounds(289, 59, 54, 15);
		contentPane.add(label_10);
		
		textField_4 = new JTextField();
		textField_4.setText("50");
		textField_4.setHorizontalAlignment(SwingConstants.CENTER);
		textField_4.setColumns(10);
		textField_4.setBounds(323, 56, 66, 21);
		contentPane.add(textField_4);
		
		textField_5 = new JTextField();
		textField_5.setText("50");
		textField_5.setHorizontalAlignment(SwingConstants.CENTER);
		textField_5.setColumns(10);
		textField_5.setBounds(323, 81, 66, 21);
		contentPane.add(textField_5);
		
		JLabel label_11 = new JLabel("\u53F3\uFF1A");
		label_11.setBounds(289, 84, 54, 15);
		contentPane.add(label_11);
	}
}
