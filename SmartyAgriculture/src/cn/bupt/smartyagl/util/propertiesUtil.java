package cn.bupt.smartyagl.util;

import java.beans.PropertyDescriptor;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

/**
 * 
 *properties的工具类，用来取数据
 */
@SuppressWarnings("unused")
public class propertiesUtil {
	
	 //加载配置对象(单例)
   	 private static Properties proper = new Properties();
   	 
	 //io流
	 private static InputStream is = null;
	 
	 //存放根路径
	 public static String path;

	 //构造函数，传入路径
	 @SuppressWarnings("static-access")
	private propertiesUtil(String path){
		this.path = path;
	 }

	 //把proper变为单例,返回对象
	 private static  Properties getProPer() throws IOException{
		 if(is!=null && proper!=null)return proper;
		 loadPro();
		 return proper;
	 }
	
	//取出值
	public static  String getValue(String key){
		try {
			Properties pro = getProPer();
			return pro.getProperty(key);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//获得path
	public static String getPath() {
		return path;
	}
	
	//改变路径
	public static void changePath(String path){
		propertiesUtil.path = path;
		loadPro();
	}
	//Properties根据路径加载文件
	private static void loadPro(){
		is = propertiesUtil.class.getClassLoader().getResourceAsStream(path);
		try {
			propertiesUtil.proper.load(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		//使用例子
		@SuppressWarnings("static-access")
		String value = new propertiesUtil("pay.properties").getValue("APPID");
		System.out.println(value);
	}
}
