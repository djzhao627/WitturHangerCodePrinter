package com.djzhao.test;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TableDeleteTest extends JFrame{
	
	private static final long serialVersionUID = -2584565326042836792L;
	//�������
	private JTable table;
	private DefaultTableModel model;
	private JButton deleteButton;
	private JPanel panel;
	
	public TableDeleteTest() {
		// TODO Auto-generated constructor stub
		
		//��ʼ�����
		panel = new JPanel();
		String[] columnNames = {"���","�û���","����"};
		String[][]data={{"1","zhangsan","123456"},{"2","lisi","4567"}};
		model = new DefaultTableModel(data, columnNames);
		table = new JTable(model);
		deleteButton = new JButton("ɾ��");
		panel = new JPanel();
		JScrollPane scrollPane = new JScrollPane(table);
		
		//������
		panel.add(scrollPane,BorderLayout.CENTER);
		panel.add(deleteButton,BorderLayout.SOUTH);
		
		this.add(panel);
		//���ô��ڵĻ�������.
		this.setVisible(true);
		this.setSize(500,500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//����¼�������
		deleteButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//��ȡҪɾ������,û��ѡ����-1
				int row = table.getSelectedColumn();
				if(row == -1){
					JOptionPane.showMessageDialog(TableDeleteTest.this,"��ѡ��Ҫɾ������!");
				}else{
					model.removeRow(row-1);
				}
			}
		});
	}

public static void main(String[] args) {
		new TableDeleteTest();
	}
}