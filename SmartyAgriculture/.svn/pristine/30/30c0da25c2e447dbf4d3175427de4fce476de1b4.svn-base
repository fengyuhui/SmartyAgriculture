package cn.bupt.smartyagl.controller.inf;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bupt.smartyagl.dao.autogenerate.PayLogMapper;
import cn.bupt.smartyagl.entity.autogenerate.OrderView;
import cn.bupt.smartyagl.entity.autogenerate.PayLog;
import cn.bupt.smartyagl.service.IPayLogService;
import cn.bupt.smartyagl.service.impl.OrderServiceImpl;
import cn.bupt.smartyagl.unionpay.SendPayInfo;
import cn.bupt.smartyagl.util.NetDataAccessUtil;
/**
 *<p>Title:UnionPayController</p>
 *<p>Description:银联支付</p>
 * @author waiting
 * @date 2016年10月31日 上午11:33:18
 */
@Controller
@RequestMapping("/interface/UnionPay")
public class UnionPayController {
	@Autowired
	IPayLogService payLogService;
	@Autowired
	PayLogMapper payLogMapper;
	/**
	 * 银联支付接口
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@ResponseBody 
	@RequestMapping("/unionPay")
   public Object unionPay(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
	    request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String orderNo= request.getParameter("orderList");
		String money=request.getParameter("money");
		String type=request.getParameter("type");
		NetDataAccessUtil nda = new NetDataAccessUtil();
		if(orderNo==null||money==null||type==null){
			nda.setContent(null);
			nda.setResult(0);
			nda.setResultDesp("数据请求异常");
		    return nda;
		}
		else{
			//String[]  orderIdArray = orderNo.split(",");
			PayLog payLog=new PayLog();
			payLog.setOrderIds(orderNo);
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
			System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
			String tradeNo=df.format(new Date());
			payLog.setTradeNo(tradeNo);
			payLog.setStatus(0);
			int typeInteger=Integer.parseInt(type);
			payLog.setType(typeInteger);
			//payLogService.addPayLog(payLog);
			payLog.setCreateTime(new Date());
			payLogMapper.insert(payLog);
//			String orderId=String.valueOf(payLog.getId());
//			System.out.println(orderId);
			String tn=SendPayInfo.sendUnionPay(tradeNo, money, "08");//手机端 08  //07 pc
//			List<OrderView> orderList=OrderServiceImpl.getOrderListByIdList(orderIdArray);//获取订单数据
			//
			nda.setContent(tn);
			nda.setResult(1);
			nda.setResultDesp("订单提交成功");
			return nda;
		}
   }
}
