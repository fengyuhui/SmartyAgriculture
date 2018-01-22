package cn.bupt.smartyagl.util;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayOpenPublicTemplateMessageIndustryModifyRequest;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayOpenPublicTemplateMessageIndustryModifyResponse;
import com.alipay.api.response.AlipayTradePayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;

/** 
 * 支付宝支付工具类
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-6-27 上午10:57:35 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public class AliPay {

	/**
	 * @param args
	 * @throws AlipayApiException 
	 */
	public static void main(String[] args) throws AlipayApiException {
//		propertiesUtil.changePath("pay.properties");
//		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",propertiesUtil.getValue("APPID"),propertiesUtil.getValue("RSA"),"json","GBK",propertiesUtil.getValue("PUBLIC"));
//		AlipayOpenPublicTemplateMessageIndustryModifyRequest request = new AlipayOpenPublicTemplateMessageIndustryModifyRequest();
//		//SDK已经封装掉了公共参数，这里只需要传入业务参数
//		//此次只是参数展示，未进行字符串转义，实际情况下请转义
//		//request.setBizContent("{\"primary_industry_name\":\"IT科技/IT软件与服务\",\"primary_industry_code\":\"10001/20102\",\"secondary_industry_code\":\"10001/20102\",\"secondary_industry_name\":\"IT科技/IT软件与服务\"}");
//		AlipayOpenPublicTemplateMessageIndustryModifyResponse response = alipayClient.execute(request); 
//		//调用成功，则处理业务逻辑
//		if(response.isSuccess()){
//		    //.....
//		}
		 propertiesUtil.changePath("pay.properties");
		String partner = "2016062201543257";
			// 商户的私钥
			String private_key = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJ6cNtWaVe3B4Nnoow0RG549hCQNt8YeUueGN1kCD1tCoR3nHrzK96mtYhTE4mYiW1BotZh8YDp/4ezDtRx7PvGf13zjfPCEK4JfUIwIG7BFkVbPOwXmp+6Z04P7JPdrnIQkmrKovAzB3ijiLIWnvc5lXtL4pOT7k9PkS7nAZgvjAgMBAAECgYEAinjXZqTXYDtjktsHfuDwu7X5J4DScKUIvBjxOUQTJBmHkBO3QhvxHLHCb190SLa2Af1ojTgIywnatFooRDVVPT26oTwNrL2ZTchLxK/6cRO6hffxnQqLGauvIarRoJzAYZPLjWrzxaS6Ej/pGwgKCxUyU3VFj5QPrbGFpvPe1AECQQDRAB1h4qtuI/1LSZ3DpjBiQ362O0JzxAlWm0q9Ml4J0kDTBWlqVhCUqU0WQs6f3VRAR0LjtNiIgCMBvZuAc31nAkEAwkcy4HZLDI17em1jHmC79W+E1E/ZOpXYavu+R72Slp4sCKW++j2tmyrgZ7XDsmCtjHosQOyXN2AjtRThkmQ0JQJABfKT43o42KyLg733kF7FV7aF/5eWH4c/oMhQiyvIw0zJbXfk9RK37BMZT33dNW1t/VpJzAMUemXzGRSVPUWMFwJAeIngYTBEBDfMQvuXogN22yMkEO6x4w2Rx18GglB2oQjmXT8pLO4TxtBlhzNZhlVBmIU869NfRzZZMUMRadjOGQJAHyS9/3ULw7gf8pWKiDc+KW9IKuqZ8wGRRg6QMtMsbLewkH3kMbWsXghc/OHGtT9Y74ZU4OwKNZ0Jkx+t7+/Dug==";
			// 支付宝的公钥，无需修改该值
		   String ali_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
		   propertiesUtil.changePath("pay.properties");
		   //↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
		   System.out.println( propertiesUtil.getValue("RSA") );
	       AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", propertiesUtil.getValue("APPID"), propertiesUtil.getValue("RSA"), "json", "gbk");//获得初始化的AlipayClient
			AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();//创建API对应的request类
			request.setBizContent("{" +
			"    \"out_trade_no\":\"20150320010101002\"," +
			"    \"total_amount\":88.88," +
			"    \"subject\":\"Iphone6 16G\"," +
			"    \"store_id\":\"NJ_001\"," +
			"    \"timeout_express\":\"90m\"," +
			"  }");//设置业务参数
			AlipayTradePrecreateResponse response = alipayClient.execute(request);
//			// TODO 根据response中的结果继续业务逻辑处理
//			System.out.println(response.getQrCode());
	}

}
