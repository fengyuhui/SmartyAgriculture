package cn.bupt.smartyagl.controller.inf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.dao.dto.OrderDTO;
import cn.bupt.smartyagl.dao.dto.OrderForm;
import cn.bupt.smartyagl.entity.autogenerate.Address;
import cn.bupt.smartyagl.entity.autogenerate.Goods;
import cn.bupt.smartyagl.entity.autogenerate.GoodsExample;
import cn.bupt.smartyagl.entity.autogenerate.OrderDetail;
import cn.bupt.smartyagl.entity.autogenerate.OrderList;
import cn.bupt.smartyagl.entity.autogenerate.OrderMaster;
import cn.bupt.smartyagl.entity.autogenerate.OrderView;
import cn.bupt.smartyagl.service.IAddressService;
import cn.bupt.smartyagl.service.IGoodsCartService;
import cn.bupt.smartyagl.service.IGoodsService;
import cn.bupt.smartyagl.service.IOrderService;
import cn.bupt.smartyagl.util.DateTag;
import cn.bupt.smartyagl.util.KeyUtils;
import cn.bupt.smartyagl.util.NetDataAccessUtil;
import cn.bupt.smartyagl.util.OrderFormToOrderDTO;
import oracle.net.aso.c;

/**
 * @author jm E-mail:740869614@qq.com
 * @date 创建时间：2016-5-13 下午2:27:15
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
@Controller
@RequestMapping("interface/order")
public class OrderController extends AppBaseController {
	@Autowired
	IOrderService orderService;
	@Autowired
	IGoodsService goodService;
	@Autowired
	IGoodsCartService goodsCartService;
	@Autowired
	IAddressService addressService;

	// 添加订单
	@RequestMapping("/addOrder")
	@ResponseBody
	public Object addOrder(HttpServletRequest request, OrderForm orderForm) {
		OrderDTO orderDTO = OrderFormToOrderDTO.convert(orderForm);
		orderDTO.setAddressId(orderForm.getAddressId());
		Integer userId = (Integer) request.getAttribute("userId");
		String orderId = KeyUtils.genUniqueKey();
		orderDTO.setUserId(userId);
		orderDTO.setOrderId(orderId);
		boolean rst = orderService.addOrder(orderDTO);
		NetDataAccessUtil nau = new NetDataAccessUtil();
		
		//清空购物车
		for(OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
			goodsCartService.deleteGoodsCart(orderDetail.getGoodsId(), orderDTO.getUserId());
		}
		if (rst) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("orderId", orderDTO.getOrderId());
			System.out.println(orderDTO.getOrderId());
			nau.setContent(map);
			nau.setResult(1);
			nau.setResultDesp("添加订单成功");
		} else {
			nau.setResult(0);
			nau.setResultDesp("添加订单失败");
		}
		return nau;
	}

	// 实时查询运费
	@RequestMapping("/searchFright")
	@ResponseBody
	public Object searchFright(HttpServletRequest request, int addressId, String goodsIds, String nums) {
		NetDataAccessUtil nau = new NetDataAccessUtil();
		try {
			double price = goodService.calculateFreight(addressId, goodsIds, nums);
			// double price = goodService.calculateFreight( addressId, goodsId, num);

			if (price != 0) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("Price", price);
				nau.setContent(map);
				nau.setResult(1);
				nau.setResultDesp("获取运费成功");
			} else {
				nau.setResult(0);
				nau.setResultDesp("获取运费失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
			nau.setResult(0);
			nau.setResultDesp("获取运费失败");
		}

		return nau;
	}

	// //实时查询运费
	// @RequestMapping("/searchFright")
	// @ResponseBody
	// public Object searchFright(HttpServletRequest request,int addressId,int
	// goodsId,Integer num){
	// double price = goodService.calculateFreight( addressId, goodsId, num);//类型转换
	//
	// NetDataAccessUtil nau = new NetDataAccessUtil();
	// if(price!=0){
	// HashMap<String, Object> map = new HashMap<String, Object>();
	// map.put("frightPrice", price);
	// nau.setContent(map);
	// nau.setResult(1);
	// nau.setResultDesp("获取运费成功");
	// }else{
	// nau.setResult(0 );
	// nau.setResultDesp("添加运费失败");
	// }
	// return nau;
	// }

	// 未登录添加订单
	@RequestMapping("/addOrderWithoutlog")
	@ResponseBody
	public Object addOderWithoutLog(HttpServletRequest request, String orderMessage, String address, String email,
			String phone) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper om = new ObjectMapper();
		// json转为订单列表
		List<OrderList> orderLists = om.readValue(orderMessage, new TypeReference<List<OrderList>>() {
		});
		// json转为地址对象
		Address addressObj = om.readValue(address, Address.class);
		addressObj.setIsUsed(true);
		boolean rs = addressService.addAddress(addressObj);
		// 订单列表添加地址id
		if (!rs) {// 地址添加失败
			NetDataAccessUtil nau = new NetDataAccessUtil();
			nau.setResult(0);
			nau.setResultDesp("添加地址失败");
			return nau;
		}
		boolean rst = true;
		Double all_price = 0.0;// 订单总价
		StringBuffer sb = new StringBuffer();// 订单id数组
		StringBuffer sb_goodsId = new StringBuffer();// 订单id数组
		for (OrderList tmp : orderLists) {// 批量插入订单
			tmp.setUserId(null);
			tmp.setAddressId(addressObj.getId());
			boolean tmp_rs = orderService.addOrder(new OrderDTO());
			if (!tmp_rs) {
				rst = false;
				break;
			}
			all_price += tmp.getMoney();
			sb.append(tmp.getId() + ",");
			sb_goodsId.append(tmp.getGoodsId() + ",");
		}
		NetDataAccessUtil nau = new NetDataAccessUtil();
		if (orderLists.size() > 0 && rst) {
			// 删除成功，删除购物车
			List<Integer> goodsIds = new ArrayList<Integer>();
			for (OrderList tmp : orderLists) {
				goodsIds.add(tmp.getGoodsId());
			}
			// //清除购物车
			// goodsCartService.deleteGoodsCartBatch(goodsIds, userId);
			String orderIds = sb.substring(0, sb.lastIndexOf(","));
			String goodIds = sb_goodsId.substring(0, sb_goodsId.lastIndexOf(","));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderIds", orderIds);
			map.put("allPrice", all_price);
			map.put("goodsIds", goodIds);
			nau.setContent(map);
			nau.setResult(1);
			nau.setResultDesp("添加订单成功");
			/**
			 * 发送订单号
			 */
			if (phone != null || email != null) {
				orderService.sendOrderMessage(phone, email, orderLists, all_price);
			}
		} else {
			nau.setResult(0);
			nau.setResultDesp("添加订单失败");
		}
		return nau;
	}

	// 批量增加订单
	@RequestMapping("/addOrderList")
	@ResponseBody
	public Object addOrderList(HttpServletRequest request, String orderMessage)
			throws JsonParseException, JsonMappingException, IOException {
		Integer userId = (Integer) request.getAttribute("userId");
		ObjectMapper om = new ObjectMapper();
		List<OrderList> orderLists = om.readValue(orderMessage, new TypeReference<List<OrderList>>() {
		});
		boolean rst = true;
		Double all_price = 0.0;// 订单总价
		StringBuffer sb = new StringBuffer();// 订单id数组
		for (OrderList tmp : orderLists) {// 批量插入订单
			tmp.setUserId(userId);

			boolean tmp_rs = orderService.addOrder(new OrderDTO());
			if (!tmp_rs) {
				rst = false;
				break;
			}
			all_price += tmp.getMoney();
			sb.append(tmp.getId() + ",");
		}
		NetDataAccessUtil nau = new NetDataAccessUtil();
		if (orderLists.size() > 0 && rst) {
			// 删除成功，删除购物车
			List<Integer> goodsIds = new ArrayList<Integer>();
			for (OrderList tmp : orderLists) {
				goodsIds.add(tmp.getGoodsId());
			}
			// 清除购物车
			goodsCartService.deleteGoodsCartBatch(goodsIds, userId);
			String orderIds = sb.substring(0, sb.lastIndexOf(","));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderIds", orderIds);
			map.put("allPrice", all_price);
			nau.setContent(map);
			nau.setResult(1);
			nau.setResultDesp("添加订单成功");
		} else {
			nau.setResult(0);
			nau.setResultDesp("添加订单失败");
		}
		return nau;
	}

	// 根据用户id获取订单列表
	@RequestMapping("/getOrderList")
	@ResponseBody
	public Object getOrderList(HttpServletRequest request, OrderList orderList, Integer pageSize, Integer pageNum) {
		Integer userId = (Integer) request.getAttribute("userId");
		orderList.setUserId(userId);
		if (pageSize == null || pageSize <= 0) {// 默认一页10个
			pageSize = 10;
		}
		if (pageNum == null || pageNum <= 0) {// 默认从第10个开始
			pageNum = 1;
		}
		// 分页，默认按购买数量排序
		Page page = PageHelper.startPage(pageNum, pageSize, "orderId DESC");
		List<OrderMaster> rst = orderService.getOrderList(orderList);
		// for(OrderMaster order : rst ){
		// order.setPicture( JsonConvert.getOnePicture(order.getPicture(), request) );
		// }
		// 总页数
		int allPages = page.getPages();
		int currentPage = page.getPageNum();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("currentPage", currentPage);
		map.put("allPages", allPages);
		map.put("orderList", rst);
		NetDataAccessUtil nau = new NetDataAccessUtil();
		nau.setContent(map);
		nau.setResult(1);
		nau.setResultDesp("获取订单列表成功");
		return nau;
	}

	/**
	 * 获取订单详情列表
	 * 
	 * @param request
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/getOrderDetailList")
	@ResponseBody
	/*public ModelAndView getOrderDetailList(HttpServletRequest request, String orderId) {
		System.out.println("orderId: "+orderId);
		ModelAndView modelAndView = new ModelAndView(Constants.ORDERDETAILLIST);
		OrderMaster orderMaster = orderService.getOrderById(orderId);
		List<OrderDetail> detailList = orderService.getOrderDetailList(orderId);
		List<Map<String, Object>> orderDetailList = new ArrayList<Map<String, Object>>();
		for(OrderDetail orderDetail : detailList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderDetail", orderDetail);
			Goods goods = goodService.findGoods(orderDetail.getGoodsId());
			map.put("goods", goods);
			orderDetailList.add(map);
		}
		//modelAndView.addObject("orderMaster", orderMaster);
		//modelAndView.addObject("orderDetailList", orderDetailList);
		modelAndView.addObject("orderMaster", "orderMaster");
		modelAndView.addObject("orderDetailList", "orderDetailList");
		System.out.println("orderMaster:   kkk"+orderMaster.getOrderId());
		System.out.println("orderDetailList:"+orderDetailList.toString());
		System.out.println("modelAndView: "+modelAndView);
		return modelAndView;
	}*/
	public Object getOrderDetailList(HttpServletRequest request, String orderId) {
		System.out.println("orderId: "+orderId);
		ModelAndView modelAndView = new ModelAndView(Constants.ORDERDETAILLIST);
		OrderMaster orderMaster = orderService.getOrderById(orderId);
		List<OrderDetail> detailList = orderService.getOrderDetailList(orderId);
		List<Map<String, Object>> orderDetailList = new ArrayList<Map<String, Object>>();
		for(OrderDetail orderDetail : detailList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderDetail", orderDetail);
			Goods goods = goodService.findGoods(orderDetail.getGoodsId());
			map.put("goods", goods);
			orderDetailList.add(map);
			System.out.println(" goods "+goods.getName());
			
		}
		System.out.println("length "+orderDetailList.size());
		//modelAndView.addObject("orderMaster", orderMaster);
		//modelAndView.addObject("orderDetailList", orderDetailList);
		modelAndView.addObject("orderMaster", "orderMaster");
		modelAndView.addObject("orderDetailList", "orderDetailList");
		
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("orderMaster", orderMaster);
		map2.put("orderDetailList", orderDetailList);
		
		System.out.println("orderMaster:   kkk"+orderMaster.getOrderId());
		System.out.println("orderDetailList:"+orderDetailList.toString());
		System.out.println("modelAndView: "+modelAndView);
		//return modelAndView;
		return orderDetailList;
	}

	
	
	/*public ModelAndView getOrderDetailList(HttpServletRequest request, String orderId) {
		System.out.println("orderId: "+orderId);
		ModelAndView modelAndView = new ModelAndView(Constants.ORDERDETAILLIST);
		OrderMaster orderMaster = orderService.getOrderById(orderId);
		List<OrderDetail> detailList = orderService.getOrderDetailList(orderId);
		List<Map<String, Object>> orderDetailList = new ArrayList<Map<String, Object>>();
		for(OrderDetail orderDetail : detailList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderDetail", orderDetail);
			Goods goods = goodService.findGoods(orderDetail.getGoodsId());
			map.put("goods", goods);
			orderDetailList.add(map);
		}
		modelAndView.addObject("orderMaster", orderMaster);
		modelAndView.addObject("orderDetailList", orderDetailList);
		System.out.println("orderMaster:   kkk"+orderMaster.getOrderId());
		System.out.println("orderDetailList:"+orderDetailList.toString());
		//return "ok";
		return modelAndView;
	}*/
	
	

	// 获取订单详情
		@RequestMapping("/getOrderMaster")
		@ResponseBody
		public Object getOrderMaster(HttpServletRequest request, String orderId) {
			Map<String, Object> map = orderService.getOrderMaster(orderId);
			NetDataAccessUtil nau = new NetDataAccessUtil();
			if( map.get("orderView") == null || map.get("address") == null ){
				nau.setContent(null);
				nau.setResult(0);
				nau.setResultDesp("获取订单详情失败");
			}else{
				nau.setContent(map);
				nau.setResult(1);
				nau.setResultDesp("获取订单详情成功");
			}
			return nau;
		}
	
	// 获取订单细目详情
	@RequestMapping("/getOrderDetail")
	@ResponseBody
	public Object getOrderDetail(HttpServletRequest request, Integer orderId) {
		Map<String, Object> map = orderService.getOrderDetail(orderId);
		NetDataAccessUtil nau = new NetDataAccessUtil();
		if (map == null || map.get("orderDetail") == null) {
			nau.setContent(null);
			nau.setResult(0);
			nau.setResultDesp("获取订单详情失败");
			System.out.println("fail");
		} else {
			nau.setContent(map);
			nau.setResult(1);
			nau.setResultDesp("获取订单详情成功");
		}
		return nau;
	}

	// 更改订单状态
	@RequestMapping("/changeOrderStatus")
	@ResponseBody
	public Object changeOrderStatus(HttpServletRequest request, OrderList order) {
		boolean rst = orderService.updateOrder(order);
		NetDataAccessUtil nau = new NetDataAccessUtil();
		if (rst) {
			nau.setResult(1);
			nau.setResultDesp("修改订单成功");
		} else {
			nau.setResult(0);
			nau.setResultDesp("修改订单失败");
		}
		return nau;
	}

	// 未登录查询订单状态
	@RequestMapping("/getOrderStatus")
	@ResponseBody
	public NetDataAccessUtil getOrderStatus(HttpServletRequest request, String orderId) {
		Integer id = Integer.parseInt(orderId.replace("2151311", ""));
		OrderView ov = orderService.getOrderById(id);
		Integer status = ov.getStatus();
		NetDataAccessUtil nau = new NetDataAccessUtil();
		if (status != null) {
			nau.setContent(status);
			nau.setResult(1);
			nau.setResultDesp("查询订单状态成功");
		} else {
			nau.setContent(null);
			nau.setResult(0);
			nau.setResultDesp("查询订单状态失败");
		}
		return nau;
	}

	// 订单查询页以及订单查询结果展示
	@RequestMapping("/getOrderDetailToView")
	public ModelAndView getOrderDetailToView(HttpServletRequest request, String orderId) {
		ModelAndView modelAndView = new ModelAndView(Constants.ORDER_LIST);
		Date starttimeDate = (Date) DateTag.stringConvertDate("0000-00-00");
		Date endtimeDate = (Date) new java.util.Date();
		orderService.getOrderList(0, modelAndView, 1, Constants.PAGESIZE, "buyTime desc", starttimeDate, endtimeDate,
				orderId);
		return modelAndView;
		// ModelAndView mv = new ModelAndView(Constants.ORDER_FindOrder);
		// try {
		// mv.setViewName(Constants.ORDER_FindOrderDetail);
		// Map<String, Object> map = new HashMap<String, Object>();
		// OrderMaster orderMaster = orderService.getOrderById(orderId);
		// Address address =
		// addressService.getAddressDetail(orderMaster.getAddressId());
		// map.put("address", address);
		// map.put("orderView", orderMaster);
		// if (map.get("orderView") != null) {
		// mv.addObject("map", map);
		// } else {
		// mv.setViewName(Constants.ORDER_FindOrder);
		// mv.addObject("message", "该订单号不存在");
		// }
		// } catch (Exception e) {
		// mv.setViewName(Constants.ORDER_FindOrder);
		// mv.addObject("message", "该订单号不存在");
		// e.printStackTrace();
		// }
		//
		// return mv;
	}

	/**
	 * 订单物流查询
	 */
	@RequestMapping("/getOrderLogistics")
	@ResponseBody
	public NetDataAccessUtil getOrderLogistics(HttpServletRequest request, Integer orderId) {
		OrderView ov = orderService.getOrderById(orderId);
		NetDataAccessUtil nau = new NetDataAccessUtil();
		if (ov == null || ov.getTracking() == null) {
			nau.setContent(null);
			nau.setResult(0);
			nau.setResultDesp("获取订单物流失败");
		} else {
			Map map = orderService.getMap(ov.getTracking());
			if (map == null) {
				nau.setContent(null);
				nau.setResult(0);
				nau.setResultDesp("获取订单物流失败");
			} else {
				nau.setContent(map);
				nau.setResult(1);
				nau.setResultDesp("获取订单物流情成功");
			}
		}
		return nau;
	}

	/**
	 * 订单物流查询
	 */
	@RequestMapping("/getOrderNums")
	@ResponseBody
	public NetDataAccessUtil getOrderNums(HttpServletRequest request) {
		NetDataAccessUtil nau = new NetDataAccessUtil();
		Integer userId = (Integer) request.getAttribute("userId");
		Map<Integer, Integer> map = orderService.getOrderNums(userId);
		if (map == null) {
			nau.setContent(null);
			nau.setResult(0);
			nau.setResultDesp("获取订单失败");
		} else {
			List<Integer> list = new LinkedList<Integer>();
			for (Integer key : map.keySet()) {
				list.add(map.get(key));
			}
			nau.setContent(list);
			nau.setResult(1);
			nau.setResultDesp("获取订单数量成功");
		}
		return nau;
	}

	/**
	 * 取消购物卡订单
	 */
	@RequestMapping("/cancleCardPaySomeOrder")
	@ResponseBody
	public NetDataAccessUtil cancleCardPaySomeOrder(String orderIds) {
		NetDataAccessUtil na = new NetDataAccessUtil();
		boolean rs = orderService.cancleCardPaySomeOrder(orderIds);
		if (rs) {
			na.setContent(null);
			na.setResult(1);
			na.setResultDesp("取消订单成功");
		} else {
			na.setContent(null);
			na.setResult(0);
			na.setResultDesp("取消订单失败，请确认传入订单状态是否正确");
		}
		return na;
	}

	@RequestMapping("/orderSearchByName")
	@ResponseBody
	public Object searchOrderByName(String goodsName, HttpServletRequest request) {
		Integer userId = (Integer) request.getAttribute("userId");
		List<OrderView> orders = orderService.getOrderByName(goodsName, userId);
		NetDataAccessUtil nau = new NetDataAccessUtil();
		if (orders.size() == 0) {
			nau.setContent(null);
			nau.setResult(1);
			nau.setResultDesp("暂无相关订单");
		} else {
			nau.setContent(orders);
			nau.setResult(1);
			nau.setResultDesp("搜索订单成功");
		}
		return nau;
	}

	private boolean isTheUserOrder(List<OrderList> or, Integer orderId) {
		for (OrderList o : or) {
			if (orderId.equals(o.getId()))
				return true;
		}
		return false;
	}

	@RequestMapping("/deleteOrder")
	@ResponseBody
	public Object deleteOrder(String orderId1, HttpServletRequest request) {
		System.out.println("23333 "+orderId1);
		Integer userId = (Integer) request.getAttribute("userId");
		System.out.println("userid "+userId);
		List<OrderList> or = orderService.getOrdersByUserId(userId);
		NetDataAccessUtil nau = new NetDataAccessUtil();
		System.out.println("id "+orderId1);
		Integer orderId = Integer.parseInt(orderId1);
		System.out.println("id "+orderId);
		if (isTheUserOrder(or, orderId)) {
			int i = orderService.deleteOrderById(orderId);
			if (i == 1) {
				nau.setResult(1);
				nau.setResultDesp("删除订单成功");
			} else {
				nau.setResult(0);
				nau.setResultDesp("删除订单失败");
			}
		} else {
			nau.setResult(0);
			nau.setResultDesp("该订单不属于该用户，无法删除");
		}

		return nau;
	}

	@RequestMapping("/updateOrderStatus")
	@ResponseBody
	public Map<String, String> updateOrderStatus(String id, Integer status) {
		NetDataAccessUtil netDataAccessUtil = new NetDataAccessUtil();
		boolean flag = orderService.updateOrderStatus(id, status);
		Map<String, String> resultMap = new HashMap<String, String>();
		System.out.println("here id "+id);
		if (flag == true) {
			resultMap.put("msg", "修改成功");
		} else {
			resultMap.put("msg", "修改失败");
		}
		return resultMap;
	}
}