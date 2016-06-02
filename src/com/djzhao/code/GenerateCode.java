package com.djzhao.code;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class GenerateCode {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String text = "C2L2M040P59P080#160409000000000000000601123456789";
		int width = 60;
		int height = 60;
		// ��ά���ͼƬ��ʽ
		String format = "png";
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.MARGIN, 0);
		// ������ʹ�ñ���
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
		// ���ɶ�ά��
		File outputFile = new File("C:\\toolsZ\\codeZ\\temp1.png");
		MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
	}

	public void generateQRCode(String text, int width, int height, String filePath)
			throws WriterException, IOException {
		// ��ά���ͼƬ��ʽ
		String format = "png";
		Hashtable<EncodeHintType,Object> hints = new Hashtable<EncodeHintType,Object>();
		// �߾�����
		hints.put(EncodeHintType.MARGIN, 0);
		// ������ʹ�ñ���
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
		File outputFile = new File(filePath);
		MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
	}

	public void generateCode_128(String text, int width, int height, String filePath)
			throws WriterException, IOException {
		// ��ά���ͼƬ��ʽ
		String format = "png";
		Hashtable<EncodeHintType,Object> hints = new Hashtable<EncodeHintType,Object>();
		// �߾�����
		hints.put(EncodeHintType.MARGIN, 0);
		// ������ʹ�ñ���
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.CODE_128, width, height, hints);
		File outputFile = new File(filePath);
		MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
	}

}