package com.djzhao.test;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;  
  
/** 
 * Created with IntelliJ IDEA. 
 * Date: 10/9/13 
 * Time: 11:31 AM 
 */  
public class QRCodeTest {  
  
  
    private static int DEFAULT_WIDTH;  
    private static int UNIT_WIDTH = 10;  
  
    public static void main(String args[]) throws Exception{  
        createImg();  
  
    }  
  
    public static void createImg(){  
        Qrcode qrcode=new Qrcode();  
        //������������   
        //Lˮƽ   7%������ɱ�����  
        //Mˮƽ   15%������ɱ�����  
        //Qˮƽ   25%������ɱ�����  
        //Hˮƽ   30%������ɱ�����  
        //QR�����ݴ�������QR��ͼ�������������Ȼ���Ա�������ȡ���ݣ���߿��Ե�7%~30%��������Կɱ���ȡ��  
        //��Զ��ԣ��ݴ������ߣ�QR��ͼ�������������һ������ʹ��15%�ݴ�������  
        qrcode.setQrcodeErrorCorrect('M');/* L','M','Q','H' */  
        qrcode.setQrcodeEncodeMode('B');/* "N","A" or other */  
        qrcode.setQrcodeVersion(3);/* 0-20 */  
  
        String testString = "5677777ghjjjjj";  
  
        byte[] buff = null;  
        try {  
            buff = testString.getBytes("utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        boolean[][] bRect = qrcode.calQrcode(buff);  
        DEFAULT_WIDTH = bRect.length * UNIT_WIDTH;  
  
        BufferedImage bi = new BufferedImage(DEFAULT_WIDTH, DEFAULT_WIDTH, BufferedImage.TYPE_INT_RGB);  
//        int unitWidth = DEFAULT_WIDTH / bRect.length;  
  
// createGraphics  
        Graphics2D g = bi.createGraphics();  
  
// set background  
        g.setBackground(Color.WHITE);  
        g.clearRect(0, 0, DEFAULT_WIDTH, DEFAULT_WIDTH);  
        g.setColor(Color.BLACK);  
  
        if (buff.length>0 && buff.length <123){  
  
            for (int i=0;i<bRect.length;i++){  
  
                for (int j=0;j<bRect.length;j++){  
                    if (bRect[j][i]) {  
                        g.fillRect(j*UNIT_WIDTH, i*UNIT_WIDTH, UNIT_WIDTH-1, UNIT_WIDTH-1);  
                    }  
                }  
  
            }  
        }  
  
        g.dispose();  
        bi.flush();  
  
        String FilePath="D:\\QRCode.png";  
        File f = new File(FilePath);  
  
        try {  
            ImageIO.write(bi, "png", f);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        System.out.println("Create QRCode finished!");  
    }  
}  