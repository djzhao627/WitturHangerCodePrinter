package com.djzhao.code;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;

import org.jbarcode.JBarcode;
import org.jbarcode.encode.Code128Encoder;
import org.jbarcode.paint.BaseLineTextPainter;
import org.jbarcode.paint.WidthCodedPainter;
import org.jbarcode.util.ImageUtil;

public class OneBarcodeUtil {

	public static void main(String[] paramArrayOfString) {

		// try {
		// JBarcode localJBarcode = new JBarcode(EAN13Encoder.getInstance(),
		// WidthCodedPainter.getInstance(),
		// EAN13TextPainter.getInstance());
		// // 生成. 欧洲商品条码(=European Article Number)
		// // 这里我们用作图书条码
		// String str = "788515004012";
		// localJBarcode.setBarHeight(5);
		// localJBarcode.setWideRatio(10);
		// localJBarcode.setShowText(false);
		// BufferedImage localBufferedImage = localJBarcode.createBarcode(str);
		// saveToPNG(localBufferedImage, "EAN13.png");
		// localJBarcode.setEncoder(Code128Encoder.getInstance());
		// localJBarcode.setShowText(false);
		// localJBarcode.setTextPainter(BaseLineTextPainter.getInstance());
		// localJBarcode.setShowCheckDigit(false);
		// // xx
		// str = "CB_WA221A36837478";
		// localBufferedImage = localJBarcode.createBarcode(str);
		// saveToPNG(localBufferedImage, "Code128.png");
		//
		// } catch (Exception localException) {
		// localException.printStackTrace();
		// }

		new OneBarcodeUtil().getCode128("KM12345678G01.100AAA131912345");

	}

	public void getCode128(String str) {

		try {
			JBarcode localJBarcode = new JBarcode(Code128Encoder.getInstance(), WidthCodedPainter.getInstance(),
					BaseLineTextPainter.getInstance());
			localJBarcode.setShowText(false);
			localJBarcode.setBarHeight(5);
			BufferedImage localBufferedImage = localJBarcode.createBarcode(str);
			saveToPNG(localBufferedImage, "Code1281.png");
		} catch (Exception localException) {
			localException.printStackTrace();
		}

	}

	static void saveToJPEG(BufferedImage paramBufferedImage, String paramString) {
		saveToFile(paramBufferedImage, paramString, "jpeg");
	}

	static void saveToPNG(BufferedImage paramBufferedImage, String paramString) {
		saveToFile(paramBufferedImage, paramString, "png");
	}

	static void saveToGIF(BufferedImage paramBufferedImage, String paramString) {
		saveToFile(paramBufferedImage, paramString, "gif");
	}

	static void saveToFile(BufferedImage paramBufferedImage, String paramString1, String paramString2) {
		try {
			FileOutputStream localFileOutputStream = new FileOutputStream("C:\\toolsZ\\codeZ\\" + paramString1);
			ImageUtil.encodeAndWrite(paramBufferedImage, paramString2, localFileOutputStream, 96, 96);
			localFileOutputStream.close();
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}

}