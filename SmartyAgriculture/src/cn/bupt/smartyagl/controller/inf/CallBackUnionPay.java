package cn.bupt.smartyagl.controller.inf;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bupt.smartyagl.unionpay.sdk.LogUtil;

@Controller
@RequestMapping("/CallBackUnionPay")
public class CallBackUnionPay {

	@RequestMapping("/getCallBack")
	public void getCallBack(HttpServletRequest request,HttpServletResponse response){
		LogUtil.writeErrorLog("银联支付回调");
		PrintWriter out=null;
		try {
			out=response.getWriter();
			Map<String, String> paramsMap=new HashMap<String, String>();
			Map requestMap=request.getParameterMap();
			//LogUtil.printRequestLog(requestMap);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
