package cn.bupt.smartyagl.weixinPayUtils;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.jdom.JDOMException;

import cn.bupt.smartyagl.constant.weixinConstants;
import cn.bupt.smartyagl.weixinPayUtils.PayCommonUtil;
import cn.bupt.smartyagl.weixinPayUtils.XMLUtil;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayOpenPublicTemplateMessageIndustryModifyRequest;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.response.AlipayOpenPublicTemplateMessageIndustryModifyResponse;
import com.alipay.api.response.AlipayTradePayResponse;
import com.google.gson.JsonObject;

/** 
 * 微信h5支付工具类
 * @author  zw
 * @date 创建时间：2016-6-27 上午10:57:35 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public class WeixinAppPay {
	//统一下单支付
	public static String getWxpayUnifiedOrder(String tradeNo, String attach, int totalMoney,String userIp,String nonce_str){
	    try{
	      //生成sign签名
	        SortedMap<String, String> packageParams = new TreeMap<String, String>();
			packageParams.put("appid", weixinConstants.appid);  
			packageParams.put("attach", attach); 
			packageParams.put("body", weixinConstants.body_description);  
			packageParams.put("mch_id", weixinConstants.partner);  	
			packageParams.put("nonce_str", nonce_str);  
			packageParams.put("notify_url", weixinConstants.notify_url);  
			packageParams.put("out_trade_no", tradeNo);  	
			packageParams.put("spbill_create_ip", userIp); 
			packageParams.put("total_fee", String.valueOf(totalMoney));
			packageParams.put("trade_type", weixinConstants.pay_type_app);  
		    String sign = PayCommonUtil.createSign("UTF-8",packageParams,weixinConstants.partnerkey);
		    packageParams.put("sign", sign);
		    //转为xml
		    String requestXml = XMLUtil.ArrayToXml(packageParams);
	      return PayCommonUtil.httpsRequest(weixinConstants.createOrderURL, "POST", requestXml);
		} catch (Exception e) {
	      e.printStackTrace();
		}
	    return null;
	}
}
