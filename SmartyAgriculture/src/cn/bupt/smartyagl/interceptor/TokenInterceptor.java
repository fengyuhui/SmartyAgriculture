package cn.bupt.smartyagl.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONArray;

import cn.bupt.smartyagl.util.NetDataAccessUtil;
import cn.bupt.smartyagl.util.RedisUtil;


/**
 * token拦截器,用于接口方法拦截
 * 
 * @author jm
 * 
 */
@Component
@ResponseBody
public class TokenInterceptor extends HandlerInterceptorAdapter {

	public boolean preHandle(HttpServletRequest request,
		HttpServletResponse response, Object handler) throws Exception {
		String token = request.getParameter("token");
//		String or = request.getParameter("orderId");
//		System.out.println(token);
		if("".equals(token)){
			//没有传token的情况
			String message = "no token";
			this.printMessage(response,message);
			return false;
		}
		//判断token是否存在或有效
		boolean result = RedisUtil.exist(token);
		if(result){
			//如果该token有效，取出对应的UserId信息
			int userId = Integer.parseInt((String)RedisUtil.get(token));
			request.setAttribute("userId", userId);
//			String message = "token is valid";
//			this.printMessage(response,message);
			return true;
		}else{
			//token无效   向前端返回消息
			String message = "请登录";
			this.printMessage(response,message);
			return false;
		}
	}
	//向前端回写消息
	private void printMessage(HttpServletResponse response,String message) throws IOException{
		response.setContentType("text/json; charset=utf-8");
		response.setHeader("Cache-Control", "no-cache"); //取消浏览器缓存
		NetDataAccessUtil nda = new NetDataAccessUtil();
		nda.setContent("");
		nda.setResult(-1);
		nda.setResultDesp(message);
		PrintWriter out = response.getWriter();
		out.println(JSONArray.toJSONString(nda));
		out.flush();
		out.close();
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
		
		// 防止登录操作user为空
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
	
	

}
