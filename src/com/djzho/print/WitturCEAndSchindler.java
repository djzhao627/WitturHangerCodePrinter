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

public class WitturCEAndSchindler implements Printable {

	/** ��ӡ������ */
	private String printerName = "ZDesigner 105SLPlus-300dpi ZPL";

	/** ֤���� */
	private String certNO = "ATV JFGKIKJHJI";
	/** ���� */
	private String series = "SERIES:YYYY-AAAAAAAAA";
	/** ���кţ�9λ�룩 */
	private String serialNumber = "S/N: XXXXXX";
	/** ���۵��� */
	private String salesOrdNo = "SALES ORD.NO:XXXXXX";
	/** ��Ʒ���� */
	private String productDate = "2015/11/12";
	/** ��� */
	private String type = "TYPE: BB/T";
	/** ���ݿ��е������� */
	private String code = "C2L2M000M00N080#160409000000000000004101";
	/** ���Ϻ� */
	private String item = "2L2M000M00N080";
	/** �������� */
	private String desc = "AA01C�Ҽ�PL= 800SF50";
	/** ��ˮ�� */
	private String sno = "151502020";

	/** �������� */
	private String material = "Material description:������������";
	/** ID�� */
	private String idNumber = "ID.number:231231";

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

	public WitturCEAndSchindler(String printerName, String certNO, String series, String sn, String salesOrdNo,
			String productDate, String type, String code, String item, String desc, String sno, String material,
			String idNumber) {
		super();
		this.printerName = printerName;
		this.certNO = certNO;
		this.series = series;
		this.serialNumber = sn;
		this.salesOrdNo = salesOrdNo;
		this.productDate = productDate;
		this.type = type;
		this.code = code;
		this.item = item;
		this.desc = desc;
		this.sno = sno;
		this.material = material;
		this.idNumber = idNumber;
	}

	public WitturCEAndSchindler() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.printerName + "\t" + this.certNO + "\t" + this.series + "\t" + this.serialNumber + "\t"
				+ this.salesOrdNo + "\t" + this.productDate + "\t" + this.type + "\t" + this.code + "\t" + this.item
				+ "\t" + this.desc + "\t" + this.sno + "\t" + this.material + "\t" + this.idNumber;
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
			Font font = new Font("������", Font.BOLD, 7);
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
			Image codeImg = null, XDImage = null;
			try {
				codeImg = ImageIO.read(new File("C:\\toolsZ\\codeZ\\wittur.png"));
				XDImage = ImageIO.read(new File("C:\\toolsZ\\codeZ\\xd.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}

			// [start]����CE��ǩ
			g2.drawImage(codeImg, (int) (x + 170), (int) (y + 104), c);

			g2.drawString(certNO, (float) (x + 242), (float) (y + 56));
			g2.drawString(series, (float) (x + 269), (float) (y + 67));
			g2.drawString(serialNumber, (float) (x + 258), (float) (y + 78));
			g2.drawString(salesOrdNo, (float) (x + 300), (float) (y + 90));
			g2.drawString(type, (float) (x + 264), (float) (y + 101));
			g2.drawString(productDate, (float) (x + 283), (float) (y + 135));

			int indexSharp = code.indexOf('#');

			// ������Ŀ��
			g2.drawString("��Ŀ�ţ�", 406, 28);
			g2.drawString(code.substring(indexSharp + 7, indexSharp + 13), 406, 38);

			// ������ˮ��
			g2.drawString("��ˮ�ţ�", 406, 62);
			g2.drawString(sno, 406, 72);

			// �������Ϻ�
			g2.drawString("���Ϻţ�", 406, 96);
			g2.drawString(item, 406, 106);

			// ������������
			g2.drawString("����������", 406, 130);
			int len = desc.length();
			if (len > 15) {
				g2.drawString(desc.substring(0, 15), 406, 140);
				g2.drawString(desc.substring(15, len), 406, 148);
			} else {
				g2.drawString(desc, 406, 140);
			} // [end]

			// ����Ѹ���ǩ
			font = new Font("������", Font.BOLD, 5);
			g2.setFont(font);
			g2.drawString(material, (float) (x + 128), (float) (y + 30));
			g2.drawString(idNumber, (float) (x + 109), (float) (y + 38));
			g2.drawString(serialNumber, (float) (x + 115), (float) (y + 45));
			g2.drawImage(XDImage, (int) (x + 26), (int) (y + 67), c);

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
		Paper p = new Paper();
		p.setSize(156, 493);// ֽ�Ŵ�С
		p.setImageableArea(0, 0, 156, 493);// A4(595 X
											// 842)���ô�ӡ������ʵ0��0Ӧ����72��72����ΪA4ֽ��Ĭ��X,Y�߾���72
		pageFormat.setPaper(p);
		// �� PageFormat �� Printable ��ӵ����У����һ��ҳ��
		book.append(new WitturCEAndSchindler(), pageFormat);

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
			// boolean a=job.printDialog();
			// if(a)
			// {
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
		paper.setSize(156, 493);// ֽ�Ŵ�С
		// ��������ת
		paper.setImageableArea(paddingTop, -paddingLeft, 156, 493);// A4(595 X
		// 842)���ô�ӡ������ʵ0��0Ӧ����72��72����ΪA4ֽ��Ĭ��X,Y�߾���72
		pageFormat.setPaper(paper);
		// �� PageFormat �� Printable ��ӵ����У����һ��ҳ��
		book.append(new WitturCEAndSchindler(printerName, certNO, series, serialNumber, salesOrdNo, productDate, type, code,
				item, desc, sno, material, idNumber), pageFormat);

		// ��ȡ��ӡ�������
		PrinterJob printerJob = PrinterJob.getPrinterJob();

		HashAttributeSet hashAttributeSet = new HashAttributeSet();

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
			// boolean a=job.printDialog();
			// if(a)
			// {
			printerJob.print();
			// }
		} catch (PrinterException e) {
			e.printStackTrace();
		}

	}
}