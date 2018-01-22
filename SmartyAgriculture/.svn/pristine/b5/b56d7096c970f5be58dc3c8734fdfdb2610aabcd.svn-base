package cn.bupt.smartyagl.util;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;

/**
* 类名: CommmonUtil </br>
* 描述: 通用工具类 </br>
 */
public class CommonUtil {
	
		/**
	    * 方法名：byteToStr</br>
	    * 详述：将字节数组转换为十六进制字符串</br>
	    * @param byteArray
	    * @return
	    * @throws
	     */
	    public static String byteToStr(byte[] byteArray) {
	        String strDigest = "";
	        for (int i = 0; i < byteArray.length; i++) {
	            strDigest += byteToHexStr(byteArray[i]);
	        }
	        return strDigest;
	    }

	    /**
	    * 方法名：byteToHexStr</br>
	    * 详述：将字节转换为十六进制字符串</br>
	    * @param mByte
	    * @return
	    * @throws
	     */
	    public static String byteToHexStr(byte mByte) {
	        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A','B', 'C', 'D', 'E', 'F' };
	        char[] tempArr = new char[2];
	        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
	        tempArr[1] = Digit[mByte & 0X0F];
	        String s = new String(tempArr);
	        return s;
	    }
	    
	    /**
	     * URL编码
	     * 
	     * @param source
	     * @return
	     */
	    public static String urlEncodeUTF8(String source, String type) {
	        String result = source;
	        try {
	            result = java.net.URLEncoder.encode(source, type);
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        }
	        return result;
	    }
	    
	    /**
	     * 根据内容类型判断文件扩展名
	     * 
	     * @param contentType 内容类型
	     * @return
	     */
	    public static String getFileExt(String contentType) {
	        String fileExt = "";
	        if ("image/jpeg".equals(contentType))
	            fileExt = ".jpg";
	        else if ("audio/mpeg".equals(contentType))
	            fileExt = ".mp3";
	        else if ("audio/amr".equals(contentType))
	            fileExt = ".amr";
	        else if ("video/mp4".equals(contentType))
	            fileExt = ".mp4";
	        else if ("video/mpeg4".equals(contentType))
	            fileExt = ".mp4";
	        return fileExt;
	    }
	    
	    
	    /**
	     * 方法名：byteToHex</br>
	     * 详述：字符串加密辅助方法 </br>
	     * @param hash
	     * @return 说明返回值含义
	     * @throws 说明发生此异常的条件
	      */
	     public static String byteToHex(final byte[] hash) {
	         Formatter formatter = new Formatter();
	         for (byte b : hash) {
	             formatter.format("%02x", b);
	         }
	         String result = formatter.toString();
	         formatter.close();
	         return result;

	     }
	     
	    
	    /**
	     * 获取当前时间，"yyyy-MM-dd HH:mm:ss"格式	
	     * 
	     * @param 
	     * @return
	     */
	    public static String getTime(){
	    	Date date=new Date();
	    	DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	String time=format.format(date);
	    	return time;
	    }

}