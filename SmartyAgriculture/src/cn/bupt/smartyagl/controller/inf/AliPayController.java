package cn.bupt.smartyagl.controller.inf;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.bupt.smartyagl.alipay.config.AlipayConfig;
import cn.bupt.smartyagl.alipay.util.AlipayNotify;
import cn.bupt.smartyagl.constant.Constants;
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
import cn.bupt.smartyagl.util.NetDataAccessUtil;
import cn.bupt.smartyagl.util.SendSMSUtil;
import cn.bupt.smartyagl.util.UserScoreUtil;
/** 
 * 支付宝支付相关接口
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-7-12 下午2:51:46 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
@Controller
@RequestMapping("interface/AliPay")
public class AliPayController extends AppBaseController{
	
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
	IAddressService addressService;
	/**
	 * 支付宝支付成功后收到的回调接口
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/payMessage")
	public void payMessage(HttpServletRequest request,HttpServletResponse response) throws IOException {
		//获取支付宝POST过来反馈信息
//		System.out.println("试试啊啊啊啊啊 ");
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
		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		System.out.println(out_trade_no);
		//支付宝交易号	
		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
		System.out.println(trade_no);
		//消息体
		String body = new String(request.getParameter("body").getBytes("ISO-8859-1"),"UTF-8");
		System.out.println(body);
		//交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		
		if(AlipayNotify.verify(params)){//验证成功
//			System.out.println("验证成功");
			ObjectMapper om = new ObjectMapper();
			Map<String,String> map = om.readValue(body, Map.class); 
			if(Integer.parseInt( map.get("type") ) == 1){//商品购买
				//支付订单里没有记录，则添加，如果有记录，说明数据库里已经记录了，只不过支付宝又再次发了订单消息
				System.out.println(!payLogService.isExistTradeNo(trade_no));
				if(!payLogService.isExistTradeNo(trade_no)){//订单号不存在
					System.out.println("开始加paylog");
					PayLog payLog = new  PayLog();
					payLog.setTradeNo(trade_no);
					payLog.setStatus(ConstantsSql.PayStatus_HasPay);
					//设置商品类型
					System.out.println("overdueMoney:"+map.get("overdueMoney"));
					if(map.get("overdueMoney")!= null &&  Double.parseDouble ( map.get("overdueMoney") )> 0 ){
						payLog.setType( ConstantsSql.PayType_AliPayAndCard );
						System.out.println("支付宝+购物卡支付");

					}
					else{
						System.out.println("支付宝支付");
						payLog.setType( ConstantsSql.PayType_AliPay );
					}
					payLog.setOrderIds(map.get("orderList"));
					payLogService.addPayLog(payLog);
					System.out.println("添加payLog成功");
					payLogService.updateOrderByTradeNo(trade_no);
					System.out.println("更新状态");
					//订单判断是购买购物卡，是的话生成购物卡信息发送到用户手机
					String[] tmpList = map.get("orderList").split(",");
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
	
	
	/**
	 * 添加支付宝起始支付记录
	 */
	@RequestMapping("/addPayLog")
	@ResponseBody
	public Object addPayLog(HttpServletRequest request,PayLog payLog){
		payLog.setStatus(ConstantsSql.PayStatus_NoPay);
		boolean rs = payLogService.addPayLog(payLog);
		NetDataAccessUtil nau = new NetDataAccessUtil();
		if( rs ){
			nau.setResult(1);
			nau.setResultDesp("添加成功");
		}else{
			nau.setResult(0 );
			nau.setResultDesp("添加失败");
		}
		return nau;  
	}
	
	/**
	 * 跳转到支付页面
	 * @return
	 * @throws AlipayApiException
	 */
	@RequestMapping("/alipayapi")
	public ModelAndView  alipayapi() throws AlipayApiException{
		ModelAndView mv = new ModelAndView(Constants.PAY_API);
		return mv;
	}
	
	/**
	 * 支付成功
	 * @return
	 * @throws AlipayApiException
	 */
	@RequestMapping("/returnURL")
	public ModelAndView  returnURL() throws AlipayApiException{
		ModelAndView mv = new ModelAndView(Constants.PAY_RETURN_URL);
		return mv;
	}
	
	@Test
	public void fef() throws AlipayApiException{
		/**
		 * 
		 */
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",AlipayConfig.app_id,AlipayConfig.private_key,"json","u",AlipayConfig.ali_public_key);
		AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
		request.setBizContent("{" +
		"    \"trade_no\":\"20150320010101001\"," +
		"    \"out_trade_no\":\"2014112611001004680073956707\"," +
		"    \"out_request_no\":\"2014112611001004680073956707\"" +
		"  }");
		AlipayTradeFastpayRefundQueryResponse response = alipayClient.execute(request);
		System.out.println(response.getErrorCode());
		if(response.isSuccess()){
		System.out.println("调用成功");
		} else {
		System.out.println("调用失败");
		}
	}
	
}
