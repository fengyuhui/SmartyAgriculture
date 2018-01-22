package cn.bupt.smartyagl.alipay.config;

import cn.bupt.smartyagl.util.propertiesUtil;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {
	static{
		propertiesUtil.changePath("pay.properties");
	}
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "2088221950144213";
	
	public static String app_id = "2016052901457747";
	
	// 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
	public static String seller_id = partner;
	// 商户的私钥
	public static String private_key = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJ6cNtWaVe3B4Nnoow0RG549hCQNt8YeUueGN1kCD1tCoR3nHrzK96mtYhTE4mYiW1BotZh8YDp/4ezDtRx7PvGf13zjfPCEK4JfUIwIG7BFkVbPOwXmp+6Z04P7JPdrnIQkmrKovAzB3ijiLIWnvc5lXtL4pOT7k9PkS7nAZgvjAgMBAAECgYEAinjXZqTXYDtjktsHfuDwu7X5J4DScKUIvBjxOUQTJBmHkBO3QhvxHLHCb190SLa2Af1ojTgIywnatFooRDVVPT26oTwNrL2ZTchLxK/6cRO6hffxnQqLGauvIarRoJzAYZPLjWrzxaS6Ej/pGwgKCxUyU3VFj5QPrbGFpvPe1AECQQDRAB1h4qtuI/1LSZ3DpjBiQ362O0JzxAlWm0q9Ml4J0kDTBWlqVhCUqU0WQs6f3VRAR0LjtNiIgCMBvZuAc31nAkEAwkcy4HZLDI17em1jHmC79W+E1E/ZOpXYavu+R72Slp4sCKW++j2tmyrgZ7XDsmCtjHosQOyXN2AjtRThkmQ0JQJABfKT43o42KyLg733kF7FV7aF/5eWH4c/oMhQiyvIw0zJbXfk9RK37BMZT33dNW1t/VpJzAMUemXzGRSVPUWMFwJAeIngYTBEBDfMQvuXogN22yMkEO6x4w2Rx18GglB2oQjmXT8pLO4TxtBlhzNZhlVBmIU869NfRzZZMUMRadjOGQJAHyS9/3ULw7gf8pWKiDc+KW9IKuqZ8wGRRg6QMtMsbLewkH3kMbWsXghc/OHGtT9Y74ZU4OwKNZ0Jkx+t7+/Dug==";
	// 支付宝的公钥，无需修改该值
	public static String ali_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	//同上，模版里有名字不一样，方便使用
	public static String alipay_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	
	
	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	
	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://118.190.41.235:8080/SmartyAgriculture/interface/AliPay/payMessage";
	public static String h5pay_notify_url = "http://118.190.41.235:8080/SmartyAgriculture/AliH5Pay/processAliH5Pay";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://118.190.41.235:8080/SmartyAgriculture/interface/AliPay/returnURL";
	public static String h5pay_return_url = "http://118.190.41.235:8080/SmartyAgriculture/AliH5Pay/return_url";
	
	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "D:\\";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
	
	// 签名方式 不需修改
	public static String sign_type = "RSA";
	
	// 支付类型 ，无需修改
	public static String payment_type = "1";
		
	// 调用的接口名，无需修改
	public static String service = "create_direct_pay_by_user";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	
//↓↓↓↓↓↓↓↓↓↓ 请在这里配置防钓鱼信息，如果没开通防钓鱼功能，为空即可 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	
	// 防钓鱼时间戳  若要使用请调用类文件submit中的query_timestamp函数
	public static String anti_phishing_key = "";
	
	// 客户端的IP地址 非局域网的外网IP地址，如：221.0.0.1
	public static String exter_invoke_ip = "";
		
//↑↑↑↑↑↑↑↑↑↑请在这里配置防钓鱼信息，如果没开通防钓鱼功能，为空即可 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	

}
