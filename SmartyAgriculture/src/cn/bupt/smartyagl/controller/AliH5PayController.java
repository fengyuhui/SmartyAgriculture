package cn.bupt.smartyagl.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.bupt.smartyagl.alipay.config.AlipayConfig;
import cn.bupt.smartyagl.alipay.util.AlipayNotify;
import cn.bupt.smartyagl.alipay.util.AlipaySubmit;
import cn.bupt.smartyagl.constant.ConstantsSql;
import cn.bupt.smartyagl.entity.autogenerate.Address;
import cn.bupt.smartyagl.entity.autogenerate.OrderView;
import cn.bupt.smartyagl.entity.autogenerate.PayLog;
import cn.bupt.smartyagl.service.IAddressService;
import cn.bupt.smartyagl.service.IFarmVisitOrderService;
import cn.bupt.smartyagl.service.IGoodsCardService;
import cn.bupt.smartyagl.service.IGoodsService;
import cn.bupt.smartyagl.service.IOrderService;
import cn.bupt.smartyagl.service.IPayLogService;
import cn.bupt.smartyagl.service.IUserService;
import cn.bupt.smartyagl.service.impl.UserScoreService;
import cn.bupt.smartyagl.util.SendSMSUtil;
import cn.bupt.smartyagl.util.UserScoreUtil;

@Controller
@RequestMapping("/AliH5Pay")
public class AliH5PayController {
	@Autowired
	IPayLogService payLogService;
	
	@Autowired
	IGoodsCardService goodsCardService;
	
	@Autowired
	IFarmVisitOrderService farmVisitOrderService;
	
	@Autowired
	IOrderService orderService;
	@Autowired
	IUserService userService;
	@Autowired
	IGoodsService goodsService;
	@Autowired 
	UserScoreService userScoreService;
	@Autowired
	IAddressService  addressService;
	@RequestMapping("/prePay")
	public void prePay(HttpServletRequest request,HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
        //商户订单号，商户网站订单系统中唯一订单号，必填
        //String out_trade_no = new String(request.getParameter("WIDout_trade_no").getBytes("iso-8859-1"),"UTF-8");
        String out_trade_no = request.getParameter("WIDout_trade_no");
        System.out.println(out_trade_no);
        //订单名称，必填
        //String subject = new String(request.getParameter("WIDsubject").getBytes("gbk"),"UTF-8");
        String subject = request.getParameter("WIDsubject");
        System.out.println(subject);
        //付款金额，必填
        //String total_fee = new String(request.getParameter("WIDtotal_fee").getBytes("ISO-8859-1"),"UTF-8");
        String total_fee = request.getParameter("WIDtotal_fee");

        //商品描述，可空
//        String body = new String(request.getParameter("WIDbody").getBytes("gbk"),"UTF-8");
        String body = request.getParameter("WIDbody");
        System.out.println(body);
		
		//////////////////////////////////////////////////////////////////////////////////
		
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", AlipayConfig.service);
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("seller_id", AlipayConfig.seller_id);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", AlipayConfig.payment_type);
		sParaTemp.put("notify_url", AlipayConfig.h5pay_notify_url);
		sParaTemp.put("return_url", AlipayConfig.h5pay_return_url);
		sParaTemp.put("anti_phishing_key", AlipayConfig.anti_phishing_key);
		sParaTemp.put("exter_invoke_ip", AlipayConfig.exter_invoke_ip);
		sParaTemp.put("out_trade_no", out_trade_no);
		sParaTemp.put("subject", subject);
		sParaTemp.put("total_fee", total_fee);
		sParaTemp.put("body", body);
		//其他业务参数根据在线开发文档，添加参数.文档地址:https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.O9yorI&treeId=62&articleId=103740&docType=1
        //如sParaTemp.put("参数名","参数值");
		
		//建立请求
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"post","确认");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		//ModelAndView mv = new ModelAndView("AliH5Pay/prePay");
		//mv.addObject("sHtmlText", sHtmlText);
		PrintWriter out = response.getWriter();
		
		//response.setContentType("text/html;charset=UTF-8");
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>支付宝即时到账交易接口");
		out.println("</title>");
		out.println("</head>");
		out.println("<body>");
		out.print("支付跳转中，请勿关闭窗口！");
		out.println(sHtmlText);
		out.println("</body>");
		out.println("</html>");
		//return mv;
	}
	@RequestMapping("/processAliH5Pay")
	public void processAliH5Pay(HttpServletRequest request,HttpServletResponse response) throws IOException{
		System.out.println("aliH5支付回调接收到请求了!");
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			if(name.equals("pAction"))
				continue; 
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");
			params.put(name, valueStr);
		}
		
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//商户订单号	
		String out_trade_no = request.getParameter("out_trade_no");
		
		//支付宝交易号	
		String trade_no = request.getParameter("trade_no");
		//消息体
		String body = request.getParameter("body");
		//交易状态
		String trade_status = request.getParameter("trade_status");
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		
		if(AlipayNotify.verify(params)){//验证成功
			System.out.println("h5也验证成功了");
			ObjectMapper om = new ObjectMapper();
			Map<String,String> map = om.readValue(processBody(body), Map.class); 
			if(Integer.parseInt( map.get("type") ) == 1){//商品购买
				//支付订单里没有记录，则添加，如果有记录，说明数据库里已经记录了，只不过支付宝又再次发了订单消息
				if(!payLogService.isExistTradeNo(trade_no)){//订单号不存在
					System.out.println(map.get("overdueMoney"));
					PayLog payLog = new  PayLog();
					payLog.setTradeNo(trade_no);
					payLog.setStatus(ConstantsSql.PayStatus_HasPay);
					//设置商品类型
					if(map.get("overdueMoney")!= null &&  Double.parseDouble( map.get("overdueMoney") )> 0 ){
						payLog.setType( ConstantsSql.PayType_AliPayAndCard );
					}else{
						payLog.setType( ConstantsSql.PayType_AliPay );
					}
					
					//payLog.setOrderIds(map.get("orderList"));
					payLog.setOrderIds(out_trade_no);
					payLogService.addPayLog(payLog);
					payLogService.updateOrderByTradeNo(trade_no);
					//订单判断是购买购物卡，是的话生成购物卡信息发送到用户手机
					String[] tmpList = out_trade_no.split(",");
					OrderView or = null;
					StringBuilder sb = new StringBuilder();
					for(String tmp : tmpList){
						or = orderService.getOrderById(Integer.parseInt(tmp));
						sb.append(or.getId());
						sb.append(',');
						goodsService.updateGoodsBuyNum(or.getNum(), or.getGoodsId());
						UserScoreUtil.addScore(Integer.parseInt(tmp), userService, orderService, goodsService, userScoreService);
//						goodsCardService.allLogic( Integer.parseInt(tmp) );
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
			}else if(Integer.parseInt( map.get("type") ) == 2){//参观农场支付
				farmVisitOrderService.payOrder( Integer.parseInt(map.get("orderList")) , trade_no);
			}
			
			if(trade_status.equals("TRADE_FINISHED")){
				//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//如果有做过处理，不执行商户的业务程序
					
				//注意：
				//退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
				//请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
			} else if (trade_status.equals("TRADE_SUCCESS")){
				//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//如果有做过处理，不执行商户的业务程序
					
				//注意：
				//付款完成后，支付宝系统发送该交易状态通知
				//请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
			}

			//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
			response.setContentType("text/json; charset=utf-8");
			response.setHeader("Cache-Control", "no-cache"); //取消浏览器缓存
			PrintWriter out = response.getWriter();
			out.println("success");	//请不要修改或删除
			out.flush();
			out.close();
			//////////////////////////////////////////////////////////////////////////////////////////
		}else{//验证失败response.setContentType("text/json; charset=utf-8");
			//记录失败时间
			response.setHeader("Cache-Control", "no-cache"); //取消浏览器缓存
			PrintWriter out = response.getWriter();
			out.println("fail");	//请不要修改或删除
			out.flush();
			out.close();
		}
	}
	
	public static String processBody(String body) {
		String[] ss = body.substring(1,body.length()-1).split(",");
		StringBuilder sb = new StringBuilder();
		for(int i = 0;i<ss.length;i++){
			String[] x = ss[i].split(":");
			sb.append('"');
			sb.append(x[0]);
			sb.append('"');
			x[0] = sb.toString();
			sb.delete(0, sb.length());
			sb.append('"');
			sb.append(x[1]);
			sb.append('"');
			x[1] = sb.toString();
			sb.delete(0, sb.length());
			ss[i] = x[0] + ":"+x[1];
			
		}
		sb.append('{');
		for(int i = 0;i<ss.length;i++) {
			if(i != ss.length - 1) {
				sb.append(ss[i]);
				sb.append(",");
			}
			else
				sb.append(ss[i]);
		}
		sb.append('}');
		return sb.toString();
	}
}
