package cn.bupt.smartyagl.controller.inf;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.constant.ConstantsSql;
import cn.bupt.smartyagl.constant.weixinConstants;
import cn.bupt.smartyagl.entity.autogenerate.FarmVisitOrder;
import cn.bupt.smartyagl.entity.autogenerate.OrderView;
import cn.bupt.smartyagl.entity.autogenerate.PayLog;
import cn.bupt.smartyagl.entity.autogenerate.Address;
import cn.bupt.smartyagl.service.IAddressService;
import cn.bupt.smartyagl.service.IFarmVisitOrderService;
import cn.bupt.smartyagl.service.IGoodsCardService;
import cn.bupt.smartyagl.service.IGoodsCartService;
import cn.bupt.smartyagl.service.IGoodsService;
import cn.bupt.smartyagl.service.IOrderService;
import cn.bupt.smartyagl.service.IPayLogService;
import cn.bupt.smartyagl.service.IUserService;
import cn.bupt.smartyagl.service.impl.UserScoreService;
import cn.bupt.smartyagl.util.NetDataAccessUtil;
import cn.bupt.smartyagl.util.SendSMSUtil;
import cn.bupt.smartyagl.util.UserScoreUtil;
import cn.bupt.smartyagl.weixinPayUtils.PayCommonUtil;
import cn.bupt.smartyagl.weixinPayUtils.WeixinAppPay;
import cn.bupt.smartyagl.weixinPayUtils.XMLUtil;

/** 
 * @author zw
 * @version ：2016-7-12 上午11:46:48 
 */
@Controller
@RequestMapping("/interface/weixin")
public class WeixinPayController {
	@Autowired
	private IUserService userService;
	@Autowired
	private IOrderService  orderService;
	@Autowired
	IPayLogService payLogService;
	@Autowired
	IGoodsCartService goodsCartService;
	@Autowired
	IFarmVisitOrderService farmVisitOrderService;
	@Autowired
	IGoodsCardService goodsCardService;
	@Autowired
	IGoodsService goodsService;
	@Autowired 
	UserScoreService userScoreService;
	@Autowired
	IAddressService addressService;
	
	/**
	 * 以下为微信APP支付新接口，客户端需要传订单类别orderType,支付类型payType,订单金额orderFee,订单号orderNo
	 * 注：启用新接口后，删除旧接口，将新接口名称改为“preToPay"
	 */
	@RequestMapping("/preToPay") 
	@ResponseBody 
	public Object preToPay_New(HttpServletRequest request,HttpServletResponse response) 
			throws Exception{
		NetDataAccessUtil nda = new NetDataAccessUtil();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		//订单数据,orderNo为订单id组成的字符串，各订单id之间由逗号隔开，形如：“123，234，456”
		String orderNo= request.getParameter("orderList");
		//支付类型（1：微信支付:2：微信支付+购物卡）
		String payType = request.getParameter("payType");
		
		//订单分类:1：购买商品:2：参观农场）
		String orderType= request.getParameter("orderType");
		//需要支付的金额
		String payMoney = request.getParameter("totalFee");
		//校验客户端传的参数有效性
		if(orderNo==null||orderType==null||payType==null||payMoney==null){
			nda.setContent(null);
			nda.setResult(0);
			nda.setResultDesp("数据请求异常");
		    return nda;
		}
		//校验payType
		if(!payType.equals("1")&&!payType.equals("2")){
			nda.setContent(null);
			nda.setResult(0);
			nda.setResultDesp("请求数据不合法");
		    return nda;
		}
		//校验orderType
		if(!orderType.equals("1")&&!orderType.equals("2")){
			nda.setContent(null);
			nda.setResult(0);
			nda.setResultDesp("请求数据不合法");
		    return nda;
		}
		String[]  orderIdArray = orderNo.split(",");
		if(orderType.equals("2")){
			//判断订单号的有效性
			List<FarmVisitOrder> orderList=farmVisitOrderService.getOrderListByIdList(orderIdArray);//获取订单数据
			if(!PayCommonUtil.checkVisitOrderNo(orderIdArray,orderList)){
				nda.setContent(null);
				nda.setResult(0);
				nda.setResultDesp("VisitOrderNo中存在错误订单id");
			    return nda;
			}
		}
		else{
			//判断订单号的有效性
//				String[]  orderIdArray = orderNo.split(",");
			List<OrderView> orderList=orderService.getOrderListByIdList(orderIdArray);//获取订单数据
			if(!PayCommonUtil.checkOrderNo(orderIdArray,orderList)){
				nda.setContent(null);
				nda.setResult(0);
				nda.setResultDesp("orderNo中存在错误订单id");
			    return nda;
			}
	
		}
		//总金额以分为单位，不带小数点
		String finalmoney = String.format("%.2f", Float.valueOf(payMoney));
		finalmoney = finalmoney.replace(".", "");
		int intMoney = Integer.parseInt(finalmoney);		
		//attach字段记录支付类型payType+订单类别orderType+订单号orderNo
		Map<String,String> attachMap = new HashMap<String,String>();
		attachMap.put("payType", payType);
		attachMap.put("orderType", orderType);
		attachMap.put("orderNo", orderNo);
		String attachJson = JSONObject.toJSONString(attachMap).toString();
	    String attach = attachJson;
		//商户订单号(6位随机数+时间戳)
		int randNumber = (int) ((Math.random()*9+1)*1000000);
		String out_trade_no = randNumber+System.currentTimeMillis()+"";//用户id加时间戳			
		//订单生成的机器 IP
		String spbill_create_ip = request.getRemoteAddr();
		//生成一个随机字符串
		String nonce_str = PayCommonUtil.CreateNoncestr();
		//调用统一下单接口
		String orderXml = WeixinAppPay.getWxpayUnifiedOrder(out_trade_no, attach, intMoney,spbill_create_ip,nonce_str);
		Map<String,String> resultMap = XMLUtil.doXMLParse(orderXml);
		//判断统一下单接口调用结果
		if(resultMap.get("result_code").equals("SUCCESS"))
		{
			String prepay_id = (String) resultMap.get("prepay_id");
	        //获取到prepayid后对以下字段进行签名最终发送给app
	        SortedMap<String, String> responseMap = new TreeMap<String, String>();
			String timestamp = PayCommonUtil.getTimeStamp();
			responseMap.put("appid", weixinConstants.appid);  
			responseMap.put("timestamp", timestamp);  
			responseMap.put("noncestr", nonce_str);  
			responseMap.put("partnerid", weixinConstants.partner); 
			responseMap.put("package", "Sign=WXPay");  			
			responseMap.put("prepayid", prepay_id);  
			String finalsign = PayCommonUtil.createSign("utf-8", responseMap,weixinConstants.partnerkey);
			responseMap.put("sign", finalsign);
			//向前端返回数据
			nda.setContent(responseMap);
			nda.setResult(1);
			nda.setResultDesp("ok");
			return nda;
		}else{
			//调用出错，则返回出错原因
			nda.setContent(null);
			nda.setResult(0);
			nda.setResultDesp(resultMap.get("return_msg"));
			return nda;
		}		
	}

	//old
	public Object preToPay(HttpServletRequest request,HttpServletResponse response) throws Exception{
		NetDataAccessUtil nda = new NetDataAccessUtil();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		//订单数据,orderNo为订单id组成的字符串，各订单id之间由逗号隔开，形如：“123，234，456”
		String orderNo= request.getParameter("orderList");
		if(orderNo==null){
			nda.setContent(null);
			nda.setResult(0);
			nda.setResultDesp("数据请求异常");
		    return nda;
		}
		//拆分orderId列表
		String[]  orderIdArray = orderNo.split(",");
		List<OrderView> orderList=orderService.getOrderListByIdList(orderIdArray);//获取订单数据
	     //验证订单
	     if(orderList.isEmpty()){
	    	nda.setContent(null);
			nda.setResult(0);
			nda.setResultDesp("数据请求异常");
		    return nda;
		}
	    float totalMoney = 0.0f;//记录总金额
	    for(int i=0;i<orderList.size();i++){
	    	OrderView order = orderList.get(i);
	    	if(order==null){
	    		continue;
	    	}
			if(!order.getStatus().equals(Constants.ORDER_NO_PAY)){
				//验证订单状态
		    	nda.setContent(null);
				nda.setResult(0);
				nda.setResultDesp("订单状态异常");
			    return nda;
			}
			totalMoney+=order.getMoney();
	    }
		//总金额以分为单位，不带小数点
//	    String finalmoney = String.format("%.2f", Float.valueOf(totalMoney));
		String finalmoney = String.format("%.2f", totalMoney);
		finalmoney = finalmoney.replace(".", "");
		int intMoney = Integer.parseInt(finalmoney);	
		//附加数据
		String attach = orderNo;
		//商户订单号(6位随机数+时间戳)
		int randNumber = (int) ((Math.random()*9+1)*1000000);
		String out_trade_no = randNumber+System.currentTimeMillis()+"";//用户id加时间戳			
		//订单生成的机器 IP
		String spbill_create_ip = request.getRemoteAddr();
		//生成一个随机字符串
		String nonce_str = PayCommonUtil.CreateNoncestr();
		//调用统一下单接口
		String orderXml = WeixinAppPay.getWxpayUnifiedOrder(out_trade_no, attach, intMoney,spbill_create_ip,nonce_str);
		Map<String,String> resultMap = XMLUtil.doXMLParse(orderXml);
		//判断统一下单接口调用结果
		if(resultMap.get("return_code").equals("SUCCESS")&&resultMap.get("result_code").equals("SUCCESS"))
		{
			String prepay_id = (String) resultMap.get("prepay_id");
	        //获取到prepayid后对以下字段进行签名最终发送给app
	        SortedMap<String, String> responseMap = new TreeMap<String, String>();
			String timestamp = PayCommonUtil.getTimeStamp();
			responseMap.put("appid", weixinConstants.appid);  
			responseMap.put("timestamp", timestamp);  
			responseMap.put("noncestr", nonce_str);  
			responseMap.put("partnerid", weixinConstants.partner); 
			responseMap.put("package", "Sign=WXPay");  			
			responseMap.put("prepayid", prepay_id);  
			String finalsign = PayCommonUtil.createSign("utf-8", responseMap,weixinConstants.partnerkey);
			responseMap.put("sign", finalsign);
			//向前端返回数据
			nda.setContent(responseMap);
			nda.setResult(1);
			nda.setResultDesp("ok");
			return nda;
		}else{
			//调用出错，则返回出错原因
			nda.setContent(null);
			nda.setResult(0);
			if(resultMap.containsKey("return_msg"))
			{//通信出错
				nda.setResultDesp(resultMap.get("return_msg"));
			}else{//业务错误
				nda.setResultDesp(resultMap.get("err_code_des"));
			}
		    return nda;
		}
	}
	
	
	//微信异步通知
	@RequestMapping("/notify") 
	@ResponseBody 
	public void notify(HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*"); 
	    //读取参数  
        InputStream inputStream ;  
        StringBuffer sb = new StringBuffer();  
        inputStream = request.getInputStream();  
        String s ;  
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));  
        while ((s = in.readLine()) != null){  
            sb.append(s);  
        }  
        in.close();  
        inputStream.close(); 
	    //解析xml成map
        Map map = new HashMap<String,String>();
        map = XMLUtil.doXMLParse(sb.toString());
	    //过滤空 ,将参数排序
        SortedMap<Object,Object> packageParams =  PayCommonUtil.constructSortMap(map);
        //判断签名是否正确  
        if(PayCommonUtil.checkSign("UTF-8", packageParams,weixinConstants.partnerkey)) { 
    	    //判断回调结果
    	    String result_code=(String) map.get("result_code");
            String resXml = "";
    		if(result_code.equals("SUCCESS")){
    			//------------------------------  
                //处理业务开始  
                //------------------------------  
    			// 这里是支付成功  ,执行自己的业务逻辑
                String out_trade_no = (String)packageParams.get("out_trade_no");    
                //从attach中获取payType和orderType
                String attach = (String) packageParams.get("attach");
                Map<String,String> attachMap = PayCommonUtil.toHashMap(attach);
                int payType = Integer.parseInt(attachMap.get("payType"));
                int orderType = Integer.parseInt(attachMap.get("orderType"));
                String orderNo = attachMap.get("orderNo");
                //向订单交易表中写入一条记录
				this.dealOrder(out_trade_no,orderNo,payType,orderType);  
                //通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
                resXml = PayCommonUtil.setXML("SUCCESS", "OK");
    		}else{
    			resXml = PayCommonUtil.setXML("FAIL", "ERROR"); 
    		}
            //------------------------------  
            //处理业务完毕  
            //------------------------------  
            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());  
            out.write(resXml.getBytes());  
            out.flush();  
            out.close(); 
        } else{  
            //记录日志：签名错误
        	System.out.println("签名错误");
        }
	}
	
	/**
	 * 向订单记录表中插入一条交易记录，清空已支付的购物车订单
	 */
	private void dealOrder(String tradeNo,String orderIds,int payType,int orderType){
		if(orderType == 1){//商品购买
			//支付订单里没有记录，则添加，如果有记录，说明数据库里已经记录了，只不过支付宝又再次发了订单消息
			if(!payLogService.isExistTradeNo(tradeNo)){//订单号不存在
				PayLog payLog = new  PayLog();
				payLog.setTradeNo(tradeNo);
				payLog.setStatus(ConstantsSql.PayStatus_HasPay);
				//设置商品支付类型
				if(payType==1){//微信支付类型
					payLog.setType( ConstantsSql.PayType_WeixinPay);
				}else if(payType==2){//微信+购物卡支付类型
					payLog.setType( ConstantsSql.PayTyep_WeixinPayAndCard);
				}
				payLog.setOrderIds(orderIds);
				payLogService.addPayLog(payLog);
				payLogService.updateOrderByTradeNo(tradeNo);
				//订单判断是购买购物卡，是的话生成购物卡信息发送到用户手机
				String[] orderIdList = orderIds.split(",");
				OrderView or = null;
				StringBuilder sb = new StringBuilder();
				for(String tmp : orderIdList){
					or = orderService.getOrderById(Integer.parseInt(tmp));
					sb.append(or.getId());
					sb.append(',');
					goodsService.updateGoodsBuyNum(or.getNum(), or.getGoodsId());
					UserScoreUtil.addScore(Integer.parseInt(tmp), userService, orderService, goodsService, userScoreService);
//					goodsCardService.allLogic( Integer.parseInt(tmp) );
				}
				//发送短信通知未登录用户购买成功以及订单号
				if(or != null && or.getUserId() == null) {
					SendSMSUtil sendSms = new SendSMSUtil();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
					Address addr = addressService.getAddressDetail(or.getAddressId());
					sendSms.SendSMS(addr.getPhone(), new String[]{addr.getName(),sdf.format(or.getBuyTime()),
							sb.substring(0, sb.length()-1)}, "162605");
				}
			}
		}else if(orderType == 2){//参观农场支付
			farmVisitOrderService.payOrder(Integer.parseInt(orderIds),tradeNo);
		}
	}
	
/*	*//**
	 * 向订单记录表中插入一条交易记录，清空已支付的购物车订单
	 *//*
	private boolean dealOrder(String tradeNo,String orderIds){
		Date date = new Date();
		PayLog payLog = new PayLog();
		payLog.setOrderIds(orderIds);
		payLog.setTradeNo(tradeNo);
		payLog.setType(2);
		payLog.setStatus(1);
		payLog.setCreateTime(date);
		boolean ret = payLogService.addPayLog(payLog);
		//当订单支付记录中的状态为已支付成功时，再更新订单表中订单的状态，否则不处理订单表状态
		if(ret){
			String[] orderIdArr = orderIds.split(",");
			boolean res = orderService.updateOrdersByIdList(orderIdArr);
			return res;
		}
		return false;
	}*/
}
 