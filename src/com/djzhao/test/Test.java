package com.djzhao.test;

import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
		String s = "1234567890";
		int l = s.length();
		System.out.println(s.substring(0, 5));
		System.out.println(s.substring(5, l));

		int sum = devi();
		System.out.println(">>>>" + sum);

		System.out.println(Integer.toBinaryString(Integer.valueOf("02", 16)));
		System.out.println(Double.parseDouble("0"));

		String number = "0-";
		// Scanner sc = new Scanner(System.in);

		// �ж��ַ����Ƿ�Ϊ����
		// while (true) {
		// number = sc.nextLine();
		// // ��д��
		// System.out.println(number.trim().matches("^[-+]?(([0-9]+)(.[0-9]+)?|(.[0-9]+))$"));
		// // ���ѽ���
		// System.out.println(number.trim().matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$"));
		// if (number.equals("99")) {
		// break;
		// }
		// }
		// sc.close();

		/*
		 * JOptionPane option = new JOptionPane(); option.setFont(new Font("��Բ",
		 * Font.ITALIC, 40)); option.setForeground(Color.BLUE);
		 * option.setBackground(Color.BLACK); option.showMessageDialog(null,
		 * "����û�з�Ӧ"); // ���ð�ť��ʾЧ�� UIManager.put("OptionPane.buttonFont", new
		 * FontUIResource(new Font("����", Font.ITALIC, 13))); // �����ı���ʾЧ��
		 * UIManager.put("OptionPane.messageFont", new FontUIResource(new
		 * Font("����", Font.ITALIC, 13))); JOptionPane.showMessageDialog(null,
		 * "������UIManager���ù���Dialog");
		 */
		/*
		 * // ȷ����ť JButton btnYes = new JButton("������Ŷ"); btnYes.setFont(new
		 * Font("��Բ", Font.BOLD, 16)); btnYes.setForeground(Color.MAGENTA); //
		 * �񶨰�ť JButton btnNo = new JButton("���в���"); btnNo.setFont(new
		 * Font("��Բ", Font.ITALIC, 18)); btnNo.setForeground(Color.PINK); //
		 * ��ťѡ��������� Object[] options = { btnYes, btnNo }; // �ı����� JLabel label =
		 * new JLabel("���Ǽ�����Label��JButton��Dialog\n����������");
		 * label.setForeground(Color.ORANGE); label.setFont(new Font("����",
		 * Font.ITALIC, 16)); // ��ʾDialog JOptionPane.showOptionDialog(null,
		 * label, "����", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
		 * null, options, options[0]);
		 */

		// int userFeedbackResult = JOptionPane.showConfirmDialog(null,
		// new JLabel("<html><h2><font color='blue'>���</font><font
		// color='#cc22ff'> ����HTMLʵ�ֵ�Ŷ~</font></h2></html>"), "ʹ��HTML��",
		// JOptionPane.YES_NO_OPTION);
		// System.out.println(">>>>" + userFeedbackResult);

	}

	private static int devi() {
		int sum = 0;
		try {
			sum = 10 / 1;
		} catch (Exception e) {
			e.printStackTrace();
			sum = 5;
		}
		return sum;
	}

}
