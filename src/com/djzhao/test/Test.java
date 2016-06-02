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

		// 判断字符串是否为数字
		// while (true) {
		// number = sc.nextLine();
		// // 我写的
		// System.out.println(number.trim().matches("^[-+]?(([0-9]+)(.[0-9]+)?|(.[0-9]+))$"));
		// // 网友建议
		// System.out.println(number.trim().matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$"));
		// if (number.equals("99")) {
		// break;
		// }
		// }
		// sc.close();

		/*
		 * JOptionPane option = new JOptionPane(); option.setFont(new Font("幼圆",
		 * Font.ITALIC, 40)); option.setForeground(Color.BLUE);
		 * option.setBackground(Color.BLACK); option.showMessageDialog(null,
		 * "好像没有反应"); // 设置按钮显示效果 UIManager.put("OptionPane.buttonFont", new
		 * FontUIResource(new Font("宋体", Font.ITALIC, 13))); // 设置文本显示效果
		 * UIManager.put("OptionPane.messageFont", new FontUIResource(new
		 * Font("宋体", Font.ITALIC, 13))); JOptionPane.showMessageDialog(null,
		 * "我是用UIManager设置过的Dialog");
		 */
		/*
		 * // 确定按钮 JButton btnYes = new JButton("可以了哦"); btnYes.setFont(new
		 * Font("幼圆", Font.BOLD, 16)); btnYes.setForeground(Color.MAGENTA); //
		 * 否定按钮 JButton btnNo = new JButton("不行不行"); btnNo.setFont(new
		 * Font("幼圆", Font.ITALIC, 18)); btnNo.setForeground(Color.PINK); //
		 * 按钮选项加入数组 Object[] options = { btnYes, btnNo }; // 文本内容 JLabel label =
		 * new JLabel("我是加入了Label和JButton的Dialog\n这样可以吗？");
		 * label.setForeground(Color.ORANGE); label.setFont(new Font("宋体",
		 * Font.ITALIC, 16)); // 显示Dialog JOptionPane.showOptionDialog(null,
		 * label, "标题", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
		 * null, options, options[0]);
		 */

		// int userFeedbackResult = JOptionPane.showConfirmDialog(null,
		// new JLabel("<html><h2><font color='blue'>你好</font><font
		// color='#cc22ff'> 我是HTML实现的哦~</font></h2></html>"), "使用HTML！",
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
