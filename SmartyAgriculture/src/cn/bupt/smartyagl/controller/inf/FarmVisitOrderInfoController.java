package cn.bupt.smartyagl.controller.inf;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.bupt.smartyagl.entity.autogenerate.FarmVisitOrder;
import cn.bupt.smartyagl.entity.autogenerate.FarmVisitOrderView;
import cn.bupt.smartyagl.entity.autogenerate.FarmVisits;
import cn.bupt.smartyagl.entity.autogenerate.User;
import cn.bupt.smartyagl.service.FarmVisitOrderViewService;
import cn.bupt.smartyagl.service.impl.FarmVisitOrderServiceImpl;
import cn.bupt.smartyagl.service.impl.FarmVisitsServiceImpl;
import cn.bupt.smartyagl.service.impl.UserServiceImpl;
import cn.bupt.smartyagl.util.NetDataAccessUtil;
import cn.bupt.smartyagl.util.RedisUtil;

/**
 * 参观农场订单下单信息
 * @author TMing
 *
 */

@Controller
@RequestMapping("/interface/farmVisitOrder")
public class FarmVisitOrderInfoController {
	@Autowired
	private FarmVisitOrderViewService farmVisitOrderViewService;
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	FarmVisitsServiceImpl farmVisitsService;
	
	@Autowired
	private FarmVisitOrderServiceImpl farmVisitOrderService;
	
	@RequestMapping("/createOrder")
	@ResponseBody
	public Object createOrder(HttpServletRequest request, @Valid FarmVisitOrder farmVisitOrder, BindingResult result, 
								@Valid @Length(min=10, max=10)String visit_time, BindingResult result2) {	
		NetDataAccessUtil nau = new NetDataAccessUtil();

		// 对num、visitId、visit_time，验证是否空、是否错误类型、是否错误格式（比如长度、精度）
		// 验证异常和格式错误
		if (result.hasErrors()) {		
			/*Hibernate校验，校验人数num和visitId
			 * num：1-99999，包括边界，超过报错
			 * visitId：0和Integer最大值之间，包括边界 
			 */
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();    
	        Validator validator = factory.getValidator();
	        Set<ConstraintViolation<FarmVisitOrder>> constraintViolations = validator.validate(farmVisitOrder); 
	        // 一些输入类型错误
	        if (constraintViolations.size() == 0) {
	        	System.out.println("输入数据类型错误！");
	        	
	        	nau.setResult(0);
	        	nau.setResultDesp("输入数据类型错误！");
	        	return nau;
	        }
	        // 数据类型没有问题，可能格式不满足规范
	        StringBuilder sb = new StringBuilder();
	        for (ConstraintViolation<FarmVisitOrder> constraintViolation : constraintViolations) { 
	        	sb.append(constraintViolation.getPropertyPath() + ":");
	        	sb.append(constraintViolation.getMessage() + ";");  
	        } 
			 
	        nau.setResult(0);
	        nau.setResultDesp(sb.toString());
	        return nau;
		}
 
		if (result2.hasErrors()) {
			nau.setResult(0);
			nau.setResultDesp("时间输入错误");
		}
		
		// 验证VisitId是否为空，验证有无该参观类型
		Integer id = farmVisitOrder.getVisitId();
		if (id == null) {
			nau.setResult(0);
			nau.setResultDesp("参观类型id为空！");
			return nau;
		}
		FarmVisits farmVisits = farmVisitsService.getFarmVisitTypeById(id);
		if (farmVisits == null) {
			nau.setResult(0);
			nau.setResultDesp("没有该参观类型！");
			return nau;
		}	
		
		// 验证num是够为空，生成订单金额
		Integer num = farmVisitOrder.getNum();
		if (num == null) {
			nau.setResult(0);
			nau.setResultDesp("人数不能为空！");
			return nau;
		}
		String phone = farmVisitOrder.getPhone();
//		System.out.println("phone=++++ewrf"+phone);
		if(phone.trim().length()== 0){
			nau.setResult(0);
			nau.setResultDesp("手机号不能为空！");
			return nau;
		}
		farmVisitOrder.setPhone(phone);
		Double price = farmVisits.getPrice();
		double money = price*num;
		farmVisitOrder.setMoney(money);
		farmVisitOrder.setOrderStatus(1);
		
		// 参观时间	
		long time = Long.parseLong(visit_time);
		if (time < 0L || time > 4102415999L) {
			nau.setResult(0);
			nau.setResultDesp("时间超出范围！");
			return nau;
		}
		
		long timeStamp = (time)*1000;
		Date visitTime = new Date(timeStamp);
		farmVisitOrder.setVisitTime(visitTime);		

		
		// 购买时间
		Date buyTime = new Date();
		farmVisitOrder.setBuyTime(buyTime);
		farmVisitOrder.setIsDelete(false);
		// 用户信息
		Integer userId = (Integer)request.getAttribute("userId");
		User User = userService.getUserInfoById(userId);
		farmVisitOrder.setName(User.getName());
//		farmVisitOrder.setPhone(User.getPhone());
		farmVisitOrder.setUserId(userId);
		boolean isDone = farmVisitOrderService.createOrder(farmVisitOrder);
		
		nau.setContent(farmVisitOrder);
		if (isDone) {
			nau.setResult(1);
			nau.setResultDesp("下单成功");			
		} else {
			nau.setResult(0);
			nau.setResultDesp("下单失败");
		}
		return nau;

	}
	private String length(String trim) {
		// TODO Auto-generated method stub
		return null;
	}
	@RequestMapping("/getOrderList")
	@ResponseBody
	public Object getOrderList(HttpServletRequest request,Integer pageSize,Integer pageNum) {
		Integer userId = (Integer) request.getAttribute("userId");
		if(pageSize == null || pageSize <= 0){//默认一页10个
			pageSize = 10;
		}
		if(pageNum == null || pageNum <= 0){//默认从第1页开始
			pageNum = 1;
		}
		//分页，默认按购买数量排序
		Page page = PageHelper.startPage(pageNum, pageSize, "id DESC");
		List<FarmVisitOrderView> rst =  farmVisitOrderViewService.getOrderListByUserId(userId);
		// 总页数
     	int allPages = page.getPages();
     	int currentPage = page.getPageNum();
     	Map<String,Object> map = new HashMap<String,Object>();
     	map.put("currentPage", currentPage);
     	map.put("allPages", allPages);
     	map.put("orderList", rst);
		NetDataAccessUtil nau = new NetDataAccessUtil();
		nau.setContent(map);
		nau.setResult(1);
		nau.setResultDesp("获取订单列表成功");
		return nau;
	}
	
	@RequestMapping("/getOrderDetail")
	@ResponseBody
	public Object getOrderDetail(HttpServletRequest request,Integer orderId) {
		Integer id = (Integer) request.getAttribute("id");
		
     	
		NetDataAccessUtil nau = new NetDataAccessUtil();
		String token = request.getParameter("token");
		Integer userId = null;
		if(token !=null && !token.equals("") && RedisUtil.exist(token)){
			userId = Integer.parseInt((String)RedisUtil.get(token));
		}
		FarmVisitOrderView obj =  farmVisitOrderViewService.getOrderDetail(orderId);
		if(obj == null){
			nau.setContent(null);
			nau.setResult(0);
			nau.setResultDesp("该订单不存在");
		}else{
//			String goodsDtail = obj.getOrderDetail();
//			if( goodsDtail == null ){
//				goodsDtail = "";
//			}
			//图片类型扎转换
//			obj.setPicture( JsonConvert.convertToList( obj.getPicture(),request ) );
			//图片
//			obj.setGoodsDetail( "http://" + IPUtil.getIpAddr(request) +":"+request.getLocalPort()+"/SmartyAgriculture/interface/goods/detailView?goodsId="+goodsId );
//			//判断是否是收藏商品
//			boolean rs = iGoodsService.isCollect(goodsId, userId);
//			GoodsListAndCollect gc = iGoodsService.convertGoods(obj);
//			gc.setGoodsDetailHTML(goodsDtail.replace("../upload", "http://" + IPUtil.getIpAddr(request) +":"+request.getLocalPort()+"/upload/"));
//			gc.setCollect(rs);
////			//获取评论列表
//			//分页，默认按购买数量排序
//			Page page = PageHelper.startPage(1, 1, "");
//			List<CommentAndReply> commentList = commentService.getCommentList(gc.getId(), 1);
//			if( commentList.size() > 0){
//				gc.setComment( commentList.get(0).getContent() );
//			}
//			//获取评论总数
//			List<Integer> commentCountList = commentService.countComment(gc.getId());
//			Integer commentCount = commentCountList.get(0);
//			
//			//计算好评率
//			if( commentCountList.get(0) > 0 ){
//				gc.setFavorableRate(  100*( commentCountList.get(2)+commentCountList.get(3) )/commentCountList.get(0)*1.0 );
//			}else{
//				gc.setFavorableRate( 100.0 );
//			}
			nau.setContent(obj);
			nau.setResult(1);
			nau.setResultDesp("查询商品成功");
		}
		return nau;
	}
	
    @RequestMapping("/deleteFarmVisitOrder")
    @ResponseBody
    public Object deleteOrder(Integer orderId,HttpServletRequest request) {
    	Integer userId = (Integer)request.getAttribute("userId");
    	NetDataAccessUtil nau = new NetDataAccessUtil();
    	if(isTheUserOrder(farmVisitOrderViewService.getOrderListByUserId(userId),orderId)) {
    		boolean f =  farmVisitOrderService.deleteOrder(orderId);
			if(f){
				nau.setResult(1);
				nau.setResultDesp("删除订单成功");
			}else{
				nau.setResult(0);
				nau.setResultDesp("删除订单失败");
			}
    	}
    	else {
			nau.setResult(0);
			nau.setResultDesp("该订单不属于该用户，无法删除");
    	}
		return nau;    	
    }
	private boolean isTheUserOrder(List<FarmVisitOrderView> orderLists, Integer orderId) {
		for(FarmVisitOrderView or:orderLists)
			if(or.getId().equals(orderId))
				return true;
		return false;
	}
}
