package cn.bupt.smartyagl.controller.inf;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
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
import cn.bupt.smartyagl.util.NetworkUtil;
import cn.bupt.smartyagl.util.SendSMSUtil;
import cn.bupt.smartyagl.util.UserScoreUtil;
import cn.bupt.smartyagl.weixinPayUtils.PayCommonUtil;
import cn.bupt.smartyagl.weixinPayUtils.WeixinH5Pay;
import cn.bupt.smartyagl.weixinPayUtils.XMLUtil;

/** 
 * @author zw
 * @version ：2016-7-12 上午11:46:48 
 */
@Controller
@RequestMapping("/interface/weixinh5")
public class WeixinH5PayController {
	@Autowired
	private IUserService userService;
	@Autowired
	private IOrderService  orderService;
	@Autowired
	IPayLogService payLogService;
	@Autowired
	IGoodsCartService goodsCartService;
	@Autowired
	IGoodsCardService goodsCardService;
	@Autowired
	IFarmVisitOrderService farmVisitOrderService;
	@Autowired
	IGoodsService goodsService;
	@Autowired 
	UserScoreService userScoreService;
	@Autowired
	IAddressService addressService;
	@RequestMapping("/preToPay") 
	@ResponseBody 
	public Object preToPay(HttpServletRequest request,HttpServletResponse response) throws Exception{
		NetDataAccessUtil nda = new NetDataAccessUtil();
		//订单数据,orderNo为订单id组成的字符串，各订单id之间由逗号隔开，形如：“123，234，456”
		String orderNo= request.getParameter("orderList");
		//微信用户openId
		String openId = request.getParameter("openId");
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
		//判断订单号的有效性
		String[]  orderIdArray = orderNo.split(",");
		if(orderType.equals("1")){
			List<OrderView> orderList=orderService.getOrderListByIdList(orderIdArray);//获取订单数据
			if(!PayCommonUtil.checkOrderNo(orderIdArray,orderList)){
				nda.setContent(null);
				nda.setResult(0);
				nda.setResultDesp("orderNo中存在错误订单id");
			    return nda;
			}
		}
		else {
			List<FarmVisitOrder> orderList=farmVisitOrderService.getOrderListByIdList(orderIdArray);//获取订单数据
			if(!PayCommonUtil.checkVisitOrderNo(orderIdArray,orderList)){
				nda.setContent(null);
				nda.setResult(0);
				nda.setResultDesp("VisitOrderNo中存在错误订单id");
			    return nda;
			}
		}
		//对支付金额进行处理，调整为分单位整数
		float totalFee =Float.parseFloat(payMoney);
//		String finalmoney = String.format("%.2f", Float.valueOf(totalFee));
		String finalmoney = String.format("%.2f", totalFee);
		finalmoney = finalmoney.replace(".", "");
		int intMoney = Integer.parseInt(finalmoney);
	    //生成tradeNo,以用户id+四位随机数+时间戳
	    int randNumber = (int) ((Math.random()*9+1)*1000);
	    String tradeNo = randNumber+System.currentTimeMillis()+"";
		//记录支付类型payType+订单类别orderType+订单号orderNo
		Map<String,String> attachMap = new HashMap<String,String>();
		attachMap.put("payType", payType);
		attachMap.put("orderType", orderType);
		attachMap.put("orderNo", orderNo);
		String attachJson = JSONObject.toJSONString(attachMap).toString();
	    String attach = attachJson;
	    //获取用户Ip
	    String userIp = NetworkUtil.getIpAddress(request);
	    //统一下单
	    String orderXml = WeixinH5Pay.getWxpayUnifiedOrder(tradeNo, attach, intMoney, openId, userIp);
		Map<String,String> resultMap= XMLUtil.doXMLParse(orderXml);
		//判断统一下单调用是否成功
		if(resultMap.get("return_code").equals("SUCCESS")&&resultMap.get("result_code").equals("SUCCESS"))
		{
		    //获取支付参数
		    String jsParams = WeixinH5Pay.getJsApiParameters(resultMap);
		    //支付页面需要显示的数据
		    Map<Object,Object> responseMap = new HashMap<Object,Object>();
		    responseMap.put("jsApiParameters", jsParams);
		    nda.setContent(responseMap);
			nda.setResult(1);
			nda.setResultDesp("获取支付参数成功！");
		    return nda;
		}else{
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
	
	@RequestMapping("/wxPayH5Notify") 
	@ResponseBody 
	public void wxPayH5Notify(HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*"); 
	    InputStream in=request.getInputStream();
	    ByteArrayOutputStream out=new ByteArrayOutputStream();
	    byte[] buffer =new byte[1024];
	    int len=0;
	    while((len=in.read(buffer))!=-1){
	    	out.write(buffer, 0, len);
	    }
	    out.close();
	    in.close();
	    //微信服务器返回的xml数据  
	    String msgxml=new String(out.toByteArray(),"UTF-8");
		Map<String,String> map =  XMLUtil.doXMLParse(msgxml);
		//过滤空 ,将参数排序
        SortedMap<Object,Object> packageParams =  PayCommonUtil.constructSortMap(map);
		if(PayCommonUtil.checkSign("UTF-8", packageParams, weixinConstants.key_h5)){//验证签名是否正确  官方签名工具地址：https://pay.weixin.qq.com/wiki/tools/signverify/ 
		    String result_code=(String) map.get("result_code");
			if(result_code.equals("SUCCESS")){
				//这里是支付成功  ,执行自己的业务逻辑
				//------------------------------  
	            //处理业务开始  
	            //-------------------------------
				String out_trade_no  = (String)packageParams.get("out_trade_no");
				//从attach中获取payType和orderType
                String attach = (String)packageParams.get("attach");
                Map<String,String> attachMap = PayCommonUtil.toHashMap(attach);
                int payType = Integer.parseInt(attachMap.get("payType"));
                int orderType = Integer.parseInt(attachMap.get("orderType"));
				String orderNo= attachMap.get("orderNo");
				//向订单交易表中写入一条记录
				this.dealOrder(out_trade_no,orderNo,payType,orderType);
				response.getWriter().write(PayCommonUtil.setXML("SUCCESS", "OK"));	//告诉微信已经收到通知了
			}else{
				response.getWriter().write(PayCommonUtil.setXML("FAIL", "ERROR"));
			}
		}else{
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
				if(payType==1){
					payLog.setType( ConstantsSql.PayType_WeixinPay);
				}else if(payType==2)
				{
					payLog.setType( ConstantsSql.PayTyep_WeixinPayAndCard);
				}
				payLog.setOrderIds(orderIds);
				payLogService.addPayLog(payLog);
				payLogService.updateOrderByTradeNo(tradeNo);
				//订单判断是购买购物卡，是的话生成购物卡信息发送到用户手机
				String[] orderIdList = orderIds.split(",");
				StringBuilder sb = new StringBuilder();
				OrderView or = null;
				for(String tmp : orderIdList){
					or = orderService.getOrderById(Integer.parseInt(tmp));
					sb.append(or.getId());
					sb.append(',');
					goodsService.updateGoodsBuyNum(or.getNum(), or.getGoodsId());
					UserScoreUtil.addScore(Integer.parseInt(tmp), userService, orderService,goodsService,userScoreService);
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
}
 