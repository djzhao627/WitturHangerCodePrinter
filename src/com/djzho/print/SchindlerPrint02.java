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

public class SchindlerPrint02 implements Printable {

	private String printerName = "ZDesigner 105SLPlus-300dpi ZPL";

	private String productName = "SA WSB 05";
	private String idNO = "59344653";
	private String release = "00";
	private String revision = "03";
	private String serialNO = "WITTUR Serial No.(123456-123)";

	/** 左边距 */
	private double paddingLeft = 0;

	/** 上边距 */
	private double paddingTop = 0;

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

	public SchindlerPrint02(String printerName, String productName, String idNO, String release, String revision,
			String serialNO) {
		super();
		this.printerName = printerName;
		this.productName = productName;
		this.idNO = idNO;
		this.release = release;
		this.revision = revision;
		this.serialNO = serialNO;
	}

	public SchindlerPrint02() {
		super();
	}

	/**
	 * @param Graphic指明打印的图形环境
	 * @param PageFormat指明打印页格式（页面大小以点为计量单位，1点为1英才的1/72，1英寸为25.4毫米。A4纸大致为595×
	 *            842点 1点约为0.35277778mm）
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
			Font font = new Font("Arial", Font.BOLD, 10);
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
			Image qrcode = null;
			try {
				qrcode = ImageIO.read(new File("C:\\toolsZ\\codeZ\\schindler02.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}

			g2.drawString(productName, (float) (x + 7), (float) (y + 20));
			g2.drawString(idNO, (float) (x + 7), (float) (y + 30));
			g2.drawString(release + "." + revision, (float) (x + 55), (float) (y + 30));
			g2.drawString(serialNO, (float) (x + 20), (float) (y + 90));

			g2.drawImage(qrcode, (int) (x + 210), (int) (y + 25), c);
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
		p.setSize(400, 400);// 纸张大小
		p.setImageableArea(0, 0, 400, 400);// A4(595 X
											// 842)设置打印区域，其实0，0应该是72，72，因为A4纸的默认X,Y边距是72
		pf.setPaper(p);
		// 把 PageFormat 和 Printable 添加到书中，组成一个页面
		book.append(new SchindlerPrint02(), pf);

		// 获取打印服务对象
		PrinterJob job = PrinterJob.getPrinterJob();

		HashAttributeSet hs = new HashAttributeSet();

		String printerName = "ZDesigner 105SLPlus-300dpi ZPL";

		hs.add(new PrinterName(printerName, null));

		PrintService[] pss = PrintServiceLookup.lookupPrintServices(null, hs);

		try {
			job.setPrintService(pss[0]);
		} catch (PrinterException e1) {
			e1.printStackTrace();
		}

		// 设置打印类
		job.setPageable(book);

		try {
			// 可以用printDialog显示打印对话框，在用户确认后打印；也可以直接打印
			// boolean a = job.printDialog();
			// if (a) {
			job.print();
			// }
		} catch (PrinterException e) {
			e.printStackTrace();
		}
	}

	public void printcode() {

		// 通俗理解就是书、文档
		Book book = new Book();
		// 设置成竖打
		PageFormat pf = new PageFormat();
		pf.setOrientation(PageFormat.PORTRAIT);
		// 通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。
		Paper p = new Paper();
		p.setSize(295, 104);// 纸张大小
		p.setImageableArea(paddingLeft, paddingTop, 295, 104);// A4(595 X
		// 842)设置打印区域，其实0，0应该是72，72，因为A4纸的默认X,Y边距是72
		pf.setPaper(p);
		// 把 PageFormat 和 Printable 添加到书中，组成一个页面
		book.append(new SchindlerPrint02(printerName, productName, idNO, release, revision, serialNO), pf);

		// 获取打印服务对象
		PrinterJob job = PrinterJob.getPrinterJob();

		HashAttributeSet hs = new HashAttributeSet();

		hs.add(new PrinterName(printerName, null));

		PrintService[] pss = PrintServiceLookup.lookupPrintServices(null, hs);

		try {
			job.setPrintService(pss[0]);
		} catch (PrinterException e1) {
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
}