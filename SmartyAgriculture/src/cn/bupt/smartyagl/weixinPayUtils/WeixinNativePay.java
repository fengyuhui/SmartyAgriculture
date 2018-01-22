package cn.bupt.smartyagl.weixinPayUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

import cn.bupt.smartyagl.constant.weixinConstants;
import cn.bupt.smartyagl.weixinPayUtils.PayCommonUtil;
import cn.bupt.smartyagl.weixinPayUtils.XMLUtil;


/** 
 * 微信Native支付工具类(扫码支付)
 * @author  zw
 * @date 创建时间：2016-10-31 上午10:57:35 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public class WeixinNativePay {
	    
	//统一下单支付
	public static String getWxpayUnifiedOrder(String tradeNo, String attach, int totalMoney,String teminalIp, String productId){
	    try{
			//时间
			Date dt = new Date(); 
			SimpleDateFormat dtSdf = new SimpleDateFormat("yyyyMMddHHmmss"); 
			//开始时间
			Long timeOld = dt.getTime();
			Date timeStart = new Date(timeOld);
			Long timeNew = timeOld+1000*60*10;
			Date timeEnd = new Date(timeNew);
			String timeStartStr = dtSdf.format(timeStart).toString();
			String timeEndStr = dtSdf.format(timeEnd).toString();
		      //生成sign签名
		      SortedMap<String,String> parameters = new TreeMap<String,String>();
		      parameters.put("appid", weixinConstants.appid_native);
		      parameters.put("attach", attach);
		      parameters.put("body", weixinConstants.body_description_native);
		      parameters.put("mch_id", weixinConstants.mchid_native);
		      parameters.put("nonce_str", PayCommonUtil.CreateNoncestr());
		      parameters.put("notify_url", weixinConstants.notify_url_native);
		      parameters.put("out_trade_no", tradeNo);
		      parameters.put("product_id", productId);
		      parameters.put("spbill_create_ip", teminalIp);
		      parameters.put("total_fee", String.valueOf(totalMoney));
		      parameters.put("time_start", timeStartStr);
		      parameters.put("time_expire", timeEndStr);
		      parameters.put("trade_type", weixinConstants.pay_type_native);;
		      String sign = PayCommonUtil.createSign("UTF-8",parameters,weixinConstants.key_native);
		      parameters.put("sign", sign);
		      //转为xml
		      String requestXml = XMLUtil.ArrayToXml(parameters);
		      return  PayCommonUtil.httpsRequest(weixinConstants.createOrderURL_native, "POST", requestXml);
			} catch (Exception e) {
		      e.printStackTrace();
			}
		    return null;
	}
	/**
	 * 生成二维码链接
	 * @param chl
	 * @return
	 * @throws Exception
	 */
    public static String QRfromGoogle(String chl) throws Exception {  
//        int widhtHeight = 300;  
//        String EC_level = "L";  
//        int margin = 0;  
        chl = PayCommonUtil.UrlEncode(chl);  
//        String QRfromGoogle = "http://chart.apis.google.com/chart?chs=" + widhtHeight + "x" + widhtHeight  
//                + "&cht=qr&chld=" + EC_level + "|" + margin + "&chl=" + chl;  
//        return QRfromGoogle;  
        String QRfromGuoNei = "https://api.qrserver.com/v1/create-qr-code/?data="+chl+"&size=150x150";
        return QRfromGuoNei;
    }  
}
