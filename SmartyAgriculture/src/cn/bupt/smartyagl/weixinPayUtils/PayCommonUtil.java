package cn.bupt.smartyagl.weixinPayUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.constant.ConstantsSql;
import cn.bupt.smartyagl.constant.weixinConstants;
import cn.bupt.smartyagl.entity.autogenerate.FarmVisitOrder;
import cn.bupt.smartyagl.entity.autogenerate.OrderView;



public class PayCommonUtil {
	/**
	 * 生成随机字符串
	 * @param length 指定随机字符串长度
	 * @return
	 */
	public static String CreateNoncestr(int length) {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		for (int i = 0; i < length; i++) {
			Random rd = new Random();
			res += chars.charAt(rd.nextInt(chars.length()-1));
		}
		return res;
	}
	/**
	 * 生成随机字符串，默认32位
	 * @return
	 */
	public static String CreateNoncestr() {
		return PayCommonUtil.CreateNoncestr(32);
	}
	/**
	 * @author 
	 * @date 
	 * @Description：sign签名
	 * @param characterEncoding 编码格式
	 * @param parameters 请求参数
	 * @return
	 */
	public static String createSign(String characterEncoding,SortedMap<String,String> parameters,String key){
		StringBuffer sb = new StringBuffer();
		Set<Entry<String,String>> es = parameters.entrySet();
		Iterator<Entry<String,String>> it = es.iterator();
		while(it.hasNext()) {
			Map.Entry<String,String> entry = (Map.Entry<String,String>)it.next();
			String k = (String)entry.getKey();
			Object v = entry.getValue();
			if(null != v && !"".equals(v) 
					&& !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + key);
		String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
		return sign;
	}
    /** 
     * 验证签名是否正确,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。 
     * @return boolean 
     */  
    public static boolean checkSign(String characterEncoding, SortedMap<Object, Object> packageParams, String API_KEY) {  
        StringBuffer sb = new StringBuffer();  
        @SuppressWarnings("rawtypes")
		Set es = packageParams.entrySet();  
        @SuppressWarnings("rawtypes")
		Iterator it = es.iterator();  
        while(it.hasNext()) {  
            @SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry)it.next();  
            String k = (String)entry.getKey();  
            String v = (String)entry.getValue();  
            if(!"sign".equals(k) && null != v && !"".equals(v)) {  
                sb.append(k + "=" + v + "&");  
            }  
        }     
        sb.append("key=" + API_KEY);  
        //算出摘要  
        String mysign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toLowerCase();  
        String tenpaySign = ((String)packageParams.get("sign")).toLowerCase();   
        return tenpaySign.equals(mysign);  
    }  
	
	/**
	 * @author 
	 * @date 
	 * @Description：将请求参数转换为xml格式的string
	 * @param parameters  请求参数
	 * @return
	 */
	public static String getRequestXml(SortedMap<String,String> parameters){
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set<Entry<String,String>> es = parameters.entrySet();
		Iterator<Entry<String,String>> it = es.iterator();
		while(it.hasNext()) {
			Map.Entry<String,String> entry = (Map.Entry<String,String>)it.next();
			String k = (String)entry.getKey();
			String v = (String)entry.getValue();
			if ("attach".equalsIgnoreCase(k)||"body".equalsIgnoreCase(k)||"sign".equalsIgnoreCase(k)) {
				sb.append("<"+k+">"+"<![CDATA["+v+"]]></"+k+">");
			}else {
				sb.append("<"+k+">"+v+"</"+k+">");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}
	/**
	 * 把对象转换成字符串
	 * @param obj
	 * @return String 转换成字符串,若对象为null,则返回空字符串.
	 */
	public static String toString(Object obj) {
		if(obj == null)
			return "";
		
		return obj.toString();
	}
	
	/**
	 * 把对象转换为int数值.
	 * 
	 * @param obj
	 *            包含数字的对象.
	 * @return int 转换后的数值,对不能转换的对象返回0。
	 */
	public static int toInt(Object obj) {
		int a = 0;
		try {
			if (obj != null)
				a = Integer.parseInt(obj.toString());
		} catch (Exception e) {

		}
		return a;
	}
	/**
	 * 获取当前时间 yyyyMMddHHmmss
	 * @return String
	 */ 
	public static String getCurrTime() {
		Date now = new Date();
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String s = outFormat.format(now);
		return s;
	}
	/**
	 * 获取当前日期 yyyyMMdd
	 * @param date
	 * @return String
	 */
	public static String formatDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String strDate = formatter.format(date);
		return strDate;
	}
	/**
	 * 获取unix时间，从1970-01-01 00:00:00开始的秒数
	 * @param date
	 * @return long
	 */
	public static long getUnixTime(Date date) {
		if( null == date ) {
			return 0;
		}	
		return date.getTime()/1000;
	}
	/**
	 * 获取当前时间戳字符串
	 * @return
	 */
	public static String getTimeStamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}
	/**
	 * 时间转换成字符串
	 * @param date 时间
	 * @param formatType 格式化类型
	 * @return String
	 */
	public static String date2String(Date date, String formatType) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatType);
		return sdf.format(date);
	}
	
	/**
	 * 取出一个指定长度大小的随机正整数.
	 * 
	 * @param length
	 *            int 设定所取出随机数的长度。length小于11
	 * @return int 返回生成的随机数。
	 */
	public static int buildRandom(int length) {
		int num = 1;
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		for (int i = 0; i < length; i++) {
			num = num * 10;
		}
		return (int) ((random * num));
	}
	
	/**
	 * URL编码（utf-8）
	 * 
	 * @param source
	 * @return
	 */
	public static String urlEncodeUTF8(String source) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 获取编码字符集
	 * @param request
	 * @param response
	 * @return String
	 */

	public static String getCharacterEncoding(HttpServletRequest request,
			HttpServletResponse response) {
		if(null == request || null == response) {
			return "utf-8";
		}
		String enc = request.getCharacterEncoding();
		if(null == enc || "".equals(enc)) {
			enc = response.getCharacterEncoding();
		}
		if(null == enc || "".equals(enc)) {
			enc = "utf-8";
		}
		return enc;
	}
    // 特殊字符处理  
    public static String UrlEncode(String src)  throws UnsupportedEncodingException {  
        return URLEncoder.encode(src, "UTF-8").replace("+", "%20");  
    }  
		
	/**
	 * @author 
	 * @date 
	 * @Description：返回给微信的参数
	 * @param return_code 返回编码
	 * @param return_msg  返回信息
	 * @return
	 */
	public static String setXML(String return_code, String return_msg) {
		return "<xml><return_code><![CDATA[" + return_code
				+ "]]></return_code><return_msg><![CDATA[" + return_msg
				+ "]]></return_msg></xml>";
	}
	
	/**
	 * 发送https请求
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return String (xml字符串）
	 */
	public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);
			
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);

			// 当outputStr不为null时向输出流写数据
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			return buffer.toString();
		} catch (ConnectException ce) {
		} catch (Exception e) {
		}
		return null;
	}
	/**
	 * 将json字符串转为hashMap
	 * @param object
	 * @return
	 */
    public static HashMap<String, String> toHashMap(String jsonStr)  
    {  
        HashMap<String, String> data = new HashMap<String, String>();  
        // 将json字符串转换成jsonObject  
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);  
        Set<Entry<String,Object>> es = jsonObject.entrySet(); 
        Iterator<Entry<String,Object>> it = es.iterator();
        // 遍历jsonObject数据，添加到Map对象  
        while (it.hasNext())  
        {  
        	Entry<String,Object> en = it.next();
            String key = en.getKey();  
            String value = (String)en.getValue();  
            data.put(key, value);  
        }  
        return data;
     }  
    /**
     * 过滤空值，将map中参数进行排序
     */
    public static SortedMap<Object,Object> constructSortMap(Map<String,String> map)
    {
        SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();        
        Set<Entry<String,String>> es = map.entrySet();
        Iterator<Entry<String,String>> it = es.iterator();  
        while (it.hasNext()) { 
        	Entry<String,String> en = it.next();
            String key =en.getKey();  
            String v = en.getValue();
            String value = "";  
            if(null != v) {  
                value = v.trim();  
            }  
            packageParams.put(key, value);  
        }
		return packageParams;
    }
    
	/**
	 * 校验订单有效性
	 * @param orderIdArray 请求数据中订单id数组
	 * @param orderList 根据请求数据中的id数组，查询数据库所得的订单对象列表
	 * @return
	 */
	public static boolean checkOrderNo(String[] orderIdArray,List<OrderView> orderList)
	{
		if(orderIdArray.length!=orderList.size())
		{//存在无效订单号
			return false;
		}
	    for(int i=0;i<orderList.size();i++){
	    	OrderView order = orderList.get(i);
			if(!order.getStatus().equals(ConstantsSql.PayStatus_NoPay)&&!order.getStatus().equals(ConstantsSql.PayStatus_CardPaySome)){
			//如果存在订单的状态不是未支付状态或购物卡部分支付状态 17.03.29
				return false;
			}
	    }
		return true;
	}
	public static boolean checkVisitOrderNo(String[] orderIdArray,List<FarmVisitOrder> visitOrder) {
		// TODO Auto-generated method stub
		if(orderIdArray.length!=visitOrder.size())
		{//存在无效订单号
			return false;
		}
	    for(int i=0;i<visitOrder.size();i++){
	    	FarmVisitOrder order = visitOrder.get(i);
			if(!order.getOrderStatus().equals(Constants.ORDER_NO_PAY)){
			//如果存在订单的状态不是未支付状态
				return false;
			}
	    }
		return true;
	}
	
}
