package cn.bupt.smartyagl.util.picture;

import java.io.File;
import java.util.UUID;

import javax.print.attribute.standard.RequestingUserName;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.sun.org.apache.bcel.internal.generic.ReturnaddressType;

import cn.bupt.smartyagl.alipay.util.httpClient.HttpRequest;
import cn.bupt.smartyagl.util.IPUtil;

/** 
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-9-1 下午2:50:48 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public class getImageFileUtil {
	/**
	 * 不需要传参获得httpRequest，只有在spring框架下可用
	 */
	public static String getSrcFileImg(String fileNames){
		if(fileNames == null){
			return "";
		}
		HttpServletRequest requests = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
		String path = "http://" + IPUtil.getIpAddr(requests) +":"+requests.getLocalPort()+"/upload/";
		//
		return path + fileNames;
	}
	
//	public static String getSrcFileImg(HttpServletRequest request,String fileNames){
//		HttpServletRequest requests = ((ServletRequestAttributes) RequestContextHolder
//                .getRequestAttributes()).getRequest();
//		String path = "http://" + IPUtil.getIpAddr(request) +":"+requests.getLocalPort()+"/upload/";
//		return path + fileNames;
//	}
	
//	public static String get240FileImg(HttpServletRequest request,String fileNames){
//		String path = "http://" + IPUtil.getIpAddr(request) +":"+request.getLocalPort()+"/upload/"+ fileNames;
//		return path.replace("src.", "240.");
//	} 
	
	public static String get240FileImg(String fileNames){
		if(fileNames == null){
			return "";
		}
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
		String path = "http://" + IPUtil.getIpAddr(request) +":"+request.getLocalPort()+"/upload/"+ fileNames;
		return path+"/240.jpg";
	} 
	
	/**
	 * 使用去除"-"的UUID作为文件名，
	 * @param file
	 * @return
	 */
	public static String rename(MultipartFile file) {
		String originalName = file.getOriginalFilename();
		String suffix = originalName.substring(originalName.lastIndexOf('.'));
		String newName = UUID.randomUUID().toString().replaceAll("\\-", "");
		return newName+suffix;		
	}
	
	public static String webPath(HttpServletRequest request) {
		String path = request.getScheme() + "://" + IPUtil.getIpAddr(request) +":"+request.getLocalPort()+ request.getContextPath();
		return path;
	}
	public static void uploadFile(MultipartFile file, String fileName, String filePath) {
		if (!file.isEmpty()) {
			try {
				File localfile = new File(filePath, fileName);
				if (!localfile.exists()) {
					localfile.mkdirs();
				}
				file.transferTo(localfile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
