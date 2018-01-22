package cn.bupt.smartyagl.util;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

/**
 * 常用获取客户端信息的工具
 * 
 */
public final class NetworkUtil {
	
	public final static String getIpAddress(HttpServletRequest request) throws IOException {
		 String ip = request.getHeader("x-forwarded-for");
		    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
		        ip = request.getHeader("Proxy-Client-IP");
		    }
		    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
		        ip = request.getHeader("WL-Proxy-Client-IP");
		    }
		    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
		        ip = request.getRemoteAddr();
		    }
		    return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
	}
}