package com.djzho.print;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashAttributeSet;
import javax.print.attribute.standard.PrinterName;

public class AMDAndBarcode implements Printable {

	/** ��ӡ������ */
	private String printerName = "ZDesigner 105SLPlus-300dpi ZPL";

	/** ��Ʒ�� */
	private String productCode = "AMDL2R-800-222-1FF";
	/** �������� */
	private String type = "Certifcation Documents CE";
	/** ֤���� */
	private String certNo = "Certifcation Documents CE";
	/** ���к� */
	private String sn = "162000001";
	/** ���۵��� */
	private String salesNo = "CNSIHG123456";
	/** ���� */
	private String dateStr = "2015/02/23";
	/** ���ݿ��е������� */
	private String code = "C2L2M000M00N080#160409000000000000004101";
	/** ���Ϻ� */
	private String item = "2L2M000M00N080";
	/** �������� */
	private String desc = "AA01C�Ҽ�PL= 800SF50";
	/** ��ˮ�� */
	private String sno = "151502020";

	/** �������� */
	private String barCode = "KM12345678G01.100AAA123412345";

	/** ��߾� */
	private double paddingLeft = 0;

	/** �ϱ߾� */
	private double paddingTop = 0;

	/**
	 * ���ñ߾ࡣ
	 * 
	 * @param paddingLeft
	 * @param paddingTop
	 */
	public void setPadding(double paddingLeft, double paddingTop) {
		this.paddingLeft = paddingLeft;
		this.paddingTop = paddingTop;
	}

	public AMDAndBarcode(String printerName, String productCode, String type, String certNo, String sn, String salesNo,
			String dateStr, String code, String item, String desc, String sno, String barCode) {
		super();
		this.printerName = printerName;
		this.productCode = productCode;
		this.type = type;
		this.certNo = certNo;
		this.sn = sn;
		this.salesNo = salesNo;
		this.dateStr = dateStr;
		this.code = code;
		this.item = item;
		this.desc = desc;
		this.sno = sno;
		this.barCode = barCode;
	}

	public AMDAndBarcode() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.printerName + "\t" + this.productCode + "\t" + this.type + "\t" + this.certNo + "\t" + this.sn
				+ "\t" + this.salesNo + "\t" + this.dateStr + "\t" + this.code + "\t" + this.item + "\t" + this.desc
				+ "\t" + this.sno + "\t" + this.barCode;
	}

	/**
	 * @param Graphicָ����ӡ��ͼ�λ���
	 * @param PageFormatָ����ӡҳ��ʽ��ҳ���С�Ե�Ϊ������λ��1��Ϊ1Ӣ�ŵ�1/72��1Ӣ��Ϊ25.4���ס�A4ֽ����Ϊ595��
	 *            842�㣩
	 * @param pageIndexָ��ҳ��
	 **/
	public int print(Graphics gra, PageFormat pf, int pageIndex) throws PrinterException {
		Component c = null;
		// ת����Graphics2D
		Graphics2D g2 = (Graphics2D) gra;
		// ���ô�ӡ��ɫΪ��ɫ
		g2.setColor(Color.black);

		// ��ӡ�������
		double x = pf.getImageableX();
		double y = pf.getImageableY();

		switch (pageIndex) {
		case 0:
			// ���ô�ӡ���壨�������ơ���ʽ�͵��С�����������ƿ�������������߼����ƣ�
			// Javaƽ̨���������������ϵ�У�Serif��SansSerif��Monospaced��Dialog �� DialogInput

			// [start]��ӡAMD��ǩ
			Font font = new Font("������", Font.BOLD, 12);
			g2.setFont(font);// ��������
			g2.drawString(productCode, (float) (x + 270.5), (float) (y + 25));

			font = new Font("������", Font.BOLD, 7);
			g2.setFont(font);// ��������
			// BasicStroke bs_3=new BasicStroke(0.5f);
			// float[] dash1 = {2.0f};
			// ���ô�ӡ�ߵ����ԡ�
			// 1.�߿� 2��3����֪����4���հ׵Ŀ�ȣ�5�����ߵĿ�ȣ�6��ƫ����
			// g2.setStroke(new BasicStroke(0.5f, BasicStroke.CAP_BUTT,
			// BasicStroke.JOIN_MITER, 2.0f, dash1, 0.0f));
			// g2.setStroke(bs_3);//�����߿�
			// float heigth = font.getSize2D();// ����߶�
			// -1- ��Graphics2Dֱ�����
			// ���ַ��Ļ���(���²�)λ���û��ռ��е� (x, y) λ�ô�
			// g2.drawLine(10,10,200,300);
			Image codeImg = null;
			Image barCodeImg = null;
			try {
				codeImg = ImageIO.read(new File("C:\\toolsZ\\codeZ\\amd.png"));
				barCodeImg = ImageIO.read(new File("C:\\toolsZ\\codeZ\\Code128.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g2.drawString(type, (float) (x + 300.5), (float) (y + 47));
			g2.drawString(certNo, (float) (x + 300.5), (float) (y + 56));
			g2.drawString(sn, (float) (x + 300.5), (float) (y + 66));
			g2.drawString(salesNo, (float) (x + 300.5), (float) (y + 76));
			g2.drawString(dateStr, (float) (x + 300.5), (float) (y + 87));

			g2.drawImage(codeImg, (int) (x + 454.5), (int) (y + 48), c);

			int indexSharp = code.indexOf('#');

			font = new Font("������", Font.BOLD, 6);
			g2.setFont(font);// ��������

			// ������Ŀ��
			g2.drawString("��Ŀ�ţ�", 505.5f, 22);
			g2.drawString(code.substring(indexSharp + 7, indexSharp + 13), 505.5f, 30);

			// ������ˮ��
			g2.drawString("��ˮ�ţ�", 505.5f, 41);
			g2.drawString(sno, 505.5f, 48);

			// �������Ϻ�
			g2.drawString("���Ϻţ�", 505.5f, 59);
			g2.drawString(item, 505.5f, 66);

			// ������������
			g2.drawString("����������", 505.5f, 77);
			int len = desc.length();
			if (len > 15) {
				g2.drawString(desc.substring(0, 15), 505.5f, 84);
				g2.drawString(desc.substring(15, len), 505.5f, 90);
			} else {
				g2.drawString(desc, 505.5f, 84);
			} // [end]

			// �����������ǩ
			font = new Font("������", Font.BOLD, 10);
			g2.setFont(font);
			g2.drawString(barCode, (float) (x + 20), (float) (y + 30));
			g2.drawImage(barCodeImg, (int) (x + 14), (int) (y + 38), (int) (barCodeImg.getWidth(null) * 0.6),
					(int) (20), c);

			return PAGE_EXISTS;
		default:
			return NO_SUCH_PAGE;
		}

	}

	public static void main(String[] args) {

		// ͨ���������顢�ĵ�
		Book book = new Book();
		// ���ó�����
		PageFormat pageFormat = new PageFormat();
		pageFormat.setOrientation(PageFormat.LANDSCAPE);
		// ͨ��Paper����ҳ��Ŀհױ߾�Ϳɴ�ӡ���򡣱�����ʵ�ʴ�ӡֽ�Ŵ�С�����
		Paper paper = new Paper();
		paper.setSize(95, 592);// ֽ�Ŵ�С
		paper.setImageableArea(0, 0, 95, 592);// A4(595 X
												// 842)���ô�ӡ������ʵ0��0Ӧ����72��72����ΪA4ֽ��Ĭ��X,Y�߾���72
		pageFormat.setPaper(paper);
		// �� PageFormat �� Printable ��ӵ����У����һ��ҳ��
		book.append(new AMDAndBarcode(), pageFormat);

		// ��ȡ��ӡ�������
		PrinterJob printerJob = PrinterJob.getPrinterJob();

		HashAttributeSet hashAttributeSet = new HashAttributeSet();

		String printerName = "ZDesigner 105SLPlus-300dpi ZPL";

		hashAttributeSet.add(new PrinterName(printerName, null));

		PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, hashAttributeSet);

		try {
			printerJob.setPrintService(printServices[0]);
		} catch (PrinterException e1) {
			e1.printStackTrace();
		}

		// ���ô�ӡ��
		printerJob.setPageable(book);

		try {
			// ������printDialog��ʾ��ӡ�Ի������û�ȷ�Ϻ��ӡ��Ҳ����ֱ�Ӵ�ӡ
			// boolean a = job.printDialog();
			// if (a) {
			printerJob.print();
			// }
		} catch (PrinterException e) {
			e.printStackTrace();
		}
	}

	public void printcode() {

		// ͨ���������顢�ĵ�
		Book book = new Book();
		// ���ó�����
		PageFormat pageFormat = new PageFormat();
		pageFormat.setOrientation(PageFormat.LANDSCAPE);
		// ͨ��Paper����ҳ��Ŀհױ߾�Ϳɴ�ӡ���򡣱�����ʵ�ʴ�ӡֽ�Ŵ�С�����
		Paper paper = new Paper();
		paper.setSize(95, 592);// ֽ�Ŵ�С
		// ��������ת
		paper.setImageableArea(paddingTop, -paddingLeft, 95, 592);// A4(595 X
		// 842)���ô�ӡ������ʵ0��0Ӧ����72��72����ΪA4ֽ��Ĭ��X,Y�߾���72
		pageFormat.setPaper(paper);
		// �� PageFormat �� Printable ��ӵ����У����һ��ҳ��
		book.append(new AMDAndBarcode(printerName, productCode, type, certNo, sn, salesNo, dateStr, code, item, desc,
				sno, barCode), pageFormat);

		// ��ȡ��ӡ�������
		PrinterJob printJob = PrinterJob.getPrinterJob();

		HashAttributeSet hashAttributeSet = new HashAttributeSet();

		hashAttributeSet.add(new PrinterName(printerName, null));

		PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, hashAttributeSet);

		try {
			printJob.setPrintService(printServices[0]);
		} catch (PrinterException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// ���ô�ӡ��
		printJob.setPageable(book);

		try {
			// ������printDialog��ʾ��ӡ�Ի������û�ȷ�Ϻ��ӡ��Ҳ����ֱ�Ӵ�ӡ
			// boolean a=job.printDialog();
			// if(a)
			// {
			printJob.print();
			// }
		} catch (PrinterException e) {
			e.printStackTrace();
		}

	}
}