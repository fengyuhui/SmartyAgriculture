package cn.bupt.smartyagl.util.picture;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.util.IPUtil;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/** 
 * 将json图片转为对象数组
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-6-29 上午9:21:22 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public class JsonConvert {
	/**
	 * 获得list
	 * @return
	 */
	public static String convertToList(String picture,HttpServletRequest request){
		List<String> beanList = new ArrayList<String>();
		ObjectMapper mapper = new ObjectMapper();  
	    try {
	    	beanList = mapper.readValue(picture, new TypeReference<List<String>>() {});
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	    String path;
			path = "http://" + IPUtil.getIpAddr(request) +":"+request.getLocalPort()+"/upload/";
	    String str = "";
	    for(String tmp : beanList){
	    	 str += path+tmp +",";
	    }
	    String end = str.substring(0, str.length()-1);
		return end;
	}
	
	/**
	 * 获得json数组中，第一张图片
	 * @return
	 */
	public static String getOnePicture(String picture,HttpServletRequest request){
		String path;
		path = "http://" + IPUtil.getIpAddr(request) +":"+request.getLocalPort()+"/upload/";
		List<String> beanList = new ArrayList<String>();
		ObjectMapper mapper = new ObjectMapper();  
	    try {
	    	beanList = mapper.readValue(picture, new TypeReference<List<String>>() {});
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return path+beanList.get(0);
	}
	
	/**
	 * 商品图片返回给后台显示
	 * 一个数组的形式
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 * 
	 */
	public static List<String> getProductPicture(String picture,HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException{
		String path = "http://" + IPUtil.getIpAddr(request) +":"+request.getLocalPort()+"/upload/";
		ObjectMapper mapper = new ObjectMapper(); 
		List<String> beanList = new ArrayList<String>();
		beanList=mapper.readValue(picture, new TypeReference<List<String>>() {});
		for(int i=0;i<beanList.size();i++){
		  String temp=beanList.get(i);
		  beanList.set(i, path+temp);
		}
		return beanList;
	}
	
	/**
     * 农场信息图片返回给后台显示
     * 一个数组的形式
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     * 
     */
    public static List<String> getCookLeisurePicture(String picture,HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException{
        String path = "http://" + IPUtil.getIpAddr(request) +":"+request.getLocalPort()+"/../"+"/upload/";
        ObjectMapper mapper = new ObjectMapper();
        List<String> beanList = new ArrayList<String>();
        beanList=mapper.readValue(picture, new TypeReference<List<String>>() {});
        for(int i=0;i<beanList.size();i++){
          String temp=beanList.get(i);
          beanList.set(i, path+temp);
        }
        return beanList;
    }
    
    /**
     * 获取图片地址，绝对地址，不是网络地址
     * @param picture
     * @param request
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static List<String> getCookLeisurePictureAbsolute(String picture,HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException{
        String path=request.getSession().getServletContext().getRealPath("")+"/../"+ Constants.FILE_PATH;
        ObjectMapper mapper = new ObjectMapper(); 
        List<String> beanList = new ArrayList<String>();
        beanList=mapper.readValue(picture, new TypeReference<List<String>>() {});
        for(int i=0;i<beanList.size();i++){
          String temp=beanList.get(i);
          beanList.set(i, path+temp);
        }
        return beanList;
    }

    /**
     * 农场信息图片返回给后台显示
     * 一个数组的形式
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     * 
     */
    public static List<String> getFarmMessagePicture(String picture,HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException{
        String path = "http://" + IPUtil.getIpAddr(request) +":"+request.getLocalPort()+"/../"+"/upload/";
        ObjectMapper mapper = new ObjectMapper();
        List<String> beanList = new ArrayList<String>();
        beanList=mapper.readValue(picture, new TypeReference<List<String>>() {});
        for(int i=0;i<beanList.size();i++){
          String temp=beanList.get(i);
          beanList.set(i, path+temp);
        }
        return beanList;
    }
    
    /**
     * 获取图片地址，绝对地址，不是网络地址
     * @param picture
     * @param request
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static List<String> getFarmMessagePictureAbsolute(String picture,HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException{
        String path=request.getSession().getServletContext().getRealPath("")+"/../"+ Constants.FILE_PATH;
        ObjectMapper mapper = new ObjectMapper(); 
        List<String> beanList = new ArrayList<String>();
        beanList=mapper.readValue(picture, new TypeReference<List<String>>() {});
        for(int i=0;i<beanList.size();i++){
          String temp=beanList.get(i);
          beanList.set(i, path+temp);
        }
        return beanList;
    }
    
	/**
	 * 获取图片地址，绝对地址，不是网络地址
	 * @param picture
	 * @param request
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static List<String> getProductPictureAbsolute(String picture,HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException{
		String path=request.getSession().getServletContext().getRealPath("")+"/../"+ Constants.FILE_PATH;
		ObjectMapper mapper = new ObjectMapper(); 
		List<String> beanList = new ArrayList<String>();
		beanList=mapper.readValue(picture, new TypeReference<List<String>>() {});
		for(int i=0;i<beanList.size();i++){
		  String temp=beanList.get(i);
		  beanList.set(i, path+temp);
		}
		return beanList;
	}

	

}
