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

public class BarcodeAndQRCode implements Printable {

	/** 打印机名称 */
	private String printerName = "ZDesigner 105SLPlus-300dpi ZPL";

	/** 条码数据 */
	private String barCode = "KM12345678G01.100AAA123412345";

	/** 数据库中的序列码+9位码 */
	private String qrCode = "C2L2M000M00N080#160409000000000000004101";
	/** 物料号 */
	private String item = "2L2M000M00N080";
	/** 物料描述 */
	private String desc = "AA01C挂件PL= 800SF50";
	/** 流水号 */
	private String sno = "151502020";

	/** 左边距 */
	private double paddingLeft = 0;

	/** 上边距 */
	private double paddingTop = 0;

	public BarcodeAndQRCode(String printerName, String barCode, String qrCode, String item, String desc, String sno) {
		super();
		this.printerName = printerName;
		this.barCode = barCode;
		this.qrCode = qrCode;
		this.item = item;
		this.desc = desc;
		this.sno = sno;
	}

	/**
	 * 设置边距。
	 * 
	 * @param paddingLeft
	 * @param paddingTop
	 */
	public void setPadding(double paddingLeft, double paddingTop) {
		this.paddingLeft = paddingLeft;
		this.paddingTop = paddingTop;
	}

	public BarcodeAndQRCode() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.printerName + "\t" + this.barCode + "\t" + this.qrCode + "\t" + this.item + "\t" + this.desc + "\t"
				+ this.sno;
	}

	/**
	 * @param Graphic指明打印的图形环境
	 * @param PageFormat指明打印页格式（页面大小以点为计量单位，1点为1英才的1/72，1英寸为25.4毫米。A4纸大致为595×
	 *            842点）
	 * @param pageIndex指明页号
	 **/
	public int print(Graphics gra, PageFormat pf, int pageIndex) throws PrinterException {
		Component c = null;
		// 转换成Graphics2D
		Graphics2D g2 = (Graphics2D) gra;
		// 设置打印颜色为黑色
		g2.setColor(Color.black);

		// 打印起点坐标
		double x = pf.getImageableX();
		double y = pf.getImageableY();

		switch (pageIndex) {
		case 0:
			// 设置打印字体（字体名称、样式和点大小）（字体名称可以是物理或者逻辑名称）
			// Java平台所定义的五种字体系列：Serif、SansSerif、Monospaced、Dialog 和 DialogInput
			Font font = new Font("新宋体", Font.BOLD, 10);
			g2.setFont(font);// 设置字体
			// BasicStroke bs_3=new BasicStroke(0.5f);
			// float[] dash1 = {2.0f};
			// 设置打印线的属性。
			// 1.线宽 2、3、不知道，4、空白的宽度，5、虚线的宽度，6、偏移量
			// g2.setStroke(new BasicStroke(0.5f, BasicStroke.CAP_BUTT,
			// BasicStroke.JOIN_MITER, 2.0f, dash1, 0.0f));
			// g2.setStroke(bs_3);//设置线宽
			// float heigth = font.getSize2D();// 字体高度
			// -1- 用Graphics2D直接输出
			// 首字符的基线(右下部)位于用户空间中的 (x, y) 位置处
			// g2.drawLine(10,10,200,300);
			Image codeImg = null, qrCodeImg = null;
			try {
				codeImg = ImageIO.read(new File("C:\\toolsZ\\codeZ\\Code128.png"));
				qrCodeImg = ImageIO.read(new File("C:\\toolsZ\\codeZ\\qrcode.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}

			// 绘制条形码标签
			g2.drawString(barCode, (float) (x + 20), (float) (y + 30));
			g2.drawImage(codeImg, (int) (x + 14), (int) (y + 38), (int) (codeImg.getWidth(null) * 0.56), (int) (15), c);

			// 绘制原来的挂件标签
			font = new Font("新宋体", Font.BOLD, 8);
			g2.setFont(font);// 设置字体
			/** #号索引 */
			int indexSharp = qrCode.indexOf('#');
			g2.drawImage(qrCodeImg, 10, 110, c);

			g2.drawString("物料类别：挂件", 80, 110);
			g2.drawString("项目号：", 80f, 121f);
			g2.drawString(qrCode.substring(indexSharp + 7, indexSharp + 13), 114f, 121f);
			g2.drawString("流水号：", 80f, 132f);
			g2.drawString(sno, 114f, 132f);
			g2.drawString("物料号：", 80f, 143f);
			g2.drawString(item, 114f, 143f);
			g2.drawString("描述：", 80f, 154f);
			int len = desc.length();
			if (len > 15) {
				g2.drawString(desc.substring(0, 15), 105.5f, 154f);
				g2.drawString(desc.substring(15, len), 105.5f, 165f);
			} else {
				g2.drawString(desc, 105.5f, 154f);
			}
			return PAGE_EXISTS;
		default:
			return NO_SUCH_PAGE;
		}

	}

	public static void main(String[] args) {

		// 通俗理解就是书、文档
		Book book = new Book();
		// 设置成竖打
		PageFormat pf = new PageFormat();
		pf.setOrientation(PageFormat.PORTRAIT);
		// 通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。
		Paper p = new Paper();
		p.setSize(198, 198);// 纸张大小
		p.setImageableArea(0, 0, 198, 198);// A4(595 X
											// 842)设置打印区域，其实0，0应该是72，72，因为A4纸的默认X,Y边距是72
		pf.setPaper(p);
		// 把 PageFormat 和 Printable 添加到书中，组成一个页面
		book.append(new BarcodeAndQRCode(), pf);

		// 获取打印服务对象
		PrinterJob job = PrinterJob.getPrinterJob();

		HashAttributeSet hs = new HashAttributeSet();

		String printerName = "ZDesigner 105SLPlus-300dpi ZPL";

		hs.add(new PrinterName(printerName, null));

		PrintService[] pss = PrintServiceLookup.lookupPrintServices(null, hs);

		try {
			job.setPrintService(pss[0]);
		} catch (PrinterException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// 设置打印类
		job.setPageable(book);

		try {
			// 可以用printDialog显示打印对话框，在用户确认后打印；也可以直接打印
			// boolean a=job.printDialog();
			// if(a)
			// {
			job.print();
			// }
		} catch (PrinterException e) {
			e.printStackTrace();
		}
	}

	public void printcode() {

		// 通俗理解就是书、文档
		Book book = new Book();

		PageFormat pageFormat = new PageFormat();
		// 打印方向
		pageFormat.setOrientation(PageFormat.PORTRAIT);
		// 通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。
		Paper paper = new Paper();
		paper.setSize(198, 198);// 纸张大小
		paper.setImageableArea(paddingLeft, paddingTop, 198, 198);// A4(595 X
		// 842)设置打印区域，其实0，0应该是72，72，因为A4纸的默认X,Y边距是72
		pageFormat.setPaper(paper);
		// 把 PageFormat 和 Printable 添加到书中，组成一个页面
		book.append(new BarcodeAndQRCode(printerName, barCode, qrCode, item, desc, sno), pageFormat);

		// 获取打印服务对象
		PrinterJob printJob = PrinterJob.getPrinterJob();

		HashAttributeSet hashAttributeSet = new HashAttributeSet();

		hashAttributeSet.add(new PrinterName(printerName, null));

		PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, hashAttributeSet);

		try {
			printJob.setPrintService(printServices[0]);
		} catch (PrinterException e1) {
			e1.printStackTrace();
		}

		// 设置打印类
		printJob.setPageable(book);
		try {
			// 可以用printDialog显示打印对话框，在用户确认后打印；也可以直接打印
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