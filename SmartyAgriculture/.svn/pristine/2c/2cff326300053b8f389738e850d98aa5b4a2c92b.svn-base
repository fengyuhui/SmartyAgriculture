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
public class WeixinH5Pay {
	//统一下单支付
	public static String getWxpayUnifiedOrder(String tradeNo, String attach, int totalMoney, String openId,String userIp){
	    try{
	      //生成sign签名
	      SortedMap<String,String> parameters = new TreeMap<String,String>();
	      parameters.put("appid", weixinConstants.appid_h5); 
	      parameters.put("attach", attach);
	      parameters.put("body", weixinConstants.body_description_h5);
	      parameters.put("mch_id", weixinConstants.mchid_h5);
	      parameters.put("nonce_str", PayCommonUtil.CreateNoncestr());
	      parameters.put("notify_url", weixinConstants.notify_url_h5);
	      parameters.put("out_trade_no", tradeNo);
	      parameters.put("total_fee", String.valueOf(totalMoney));
	      parameters.put("trade_type", weixinConstants.pay_type_h5);
	      parameters.put("spbill_create_ip", userIp);
	      parameters.put("openid", openId);
	      String sign = PayCommonUtil.createSign("UTF-8",parameters,weixinConstants.key_h5);
	      parameters.put("sign", sign);
	      //转为xml
	      String requestXml = XMLUtil.ArrayToXml(parameters);
	      return PayCommonUtil.httpsRequest(weixinConstants.createOrderURL_h5, "POST", requestXml);
		} catch (Exception e) {
	      e.printStackTrace();
		}
	    return null;
	}

	/**
	 * 构造js支付需要的json形式参数
	 * @param orderXml 统一下单接口返回的xml数据
	 * @return
	 */
	public static String getJsApiParameters(Map<String,String> map){
			//h5端调起支付，需要传入的参数为json
			SortedMap<String,String> params = new TreeMap<String,String>();
			params.put("appId", map.get("appid"));
			params.put("timeStamp",PayCommonUtil.getTimeStamp());
			params.put("nonceStr",PayCommonUtil.CreateNoncestr());
			params.put("package", "prepay_id="+map.get("prepay_id"));
			params.put("signType", "MD5");
			String paySign = PayCommonUtil.createSign("UTF-8", params,weixinConstants.key_h5);
			params.put("paySign",paySign);
			return JSONObject.toJSONString(params);
	}
}
