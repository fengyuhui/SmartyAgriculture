package cn.bupt.smartyagl.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import com.swetake.util.Qrcode;

public class QRCodeUtil {
	/**
	 * 传入二维码代表的网址字符串和生成图片的磁盘路径，并在指定的路径下生成二维码图片，若生成成功，返回true
	 * @param srcValue
	 * @param qrcodePicfilePath
	 * @return
	 */
	public static boolean encode(String srcValue, String qrcodePicfilePath) {
		System.out.println(qrcodePicfilePath);
		int MAX_DATA_LENGTH = 800;
		     byte[] d = srcValue.getBytes();
		     System.out.println("d的length:"+d.length);
		     int dataLength = d.length;
		     int version = 6;
		     int imageWidth = 67 + 12 * (version - 1); ; 
		     int imageHeight = imageWidth;
		     BufferedImage bi = new BufferedImage(imageWidth, imageHeight,BufferedImage.TYPE_INT_RGB);
		     Graphics2D g = bi.createGraphics();
		     g.setBackground(Color.WHITE);
		     g.clearRect(0, 0, imageWidth, imageHeight);
		     g.setColor(Color.BLACK);
		     if (dataLength > 0 && dataLength <= MAX_DATA_LENGTH) {
		        Qrcode qrcode = new Qrcode();
		        qrcode.setQrcodeErrorCorrect('M'); 
		        qrcode.setQrcodeEncodeMode('B'); 
		        qrcode.setQrcodeVersion(version);
		        boolean[][] b = qrcode.calQrcode(d);
		        int qrcodeDataLen = b.length;
		        for (int i = 0; i < qrcodeDataLen; i++) {
		           for (int j = 0; j < qrcodeDataLen; j++) {
		              if (b[j][i]) {
		                 g.fillRect(j * 3 + 2, i * 3 + 2, 3, 3); 
		              }
		           }
		        }
		        //System.out.println("二维码成功生成！！");
		     } else {
		        System.out.println( dataLength +"大于"+ MAX_DATA_LENGTH);
		        return false;
		     }
		     g.dispose();
		     bi.flush();
		     System.out.println(qrcodePicfilePath);
		     File f = new File(qrcodePicfilePath);
		     //f.setWritable(true);
		     //System.out.println(f.getName());
		     //System.out.println(f.getAbsolutePath());
		     String suffix = f.getName().substring(f.getName().indexOf(".")+1, f.getName().length());
		    // System.out.println("二维码输出成功！！");
		     try {
		        ImageIO.write(bi, suffix, f);
		     } catch (IOException ioe) {
		        System.out.println("二维码生成失败" + ioe.getMessage());
		        return false;
		     }
		return true;
	}

}
