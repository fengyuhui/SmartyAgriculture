package cn.bupt.smartyagl.service.impl;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.bupt.smartyagl.constant.ConstantsSql;
import cn.bupt.smartyagl.converter.OrderDetail2OrderDetailModel;
import cn.bupt.smartyagl.dao.autogenerate.AddressMapper;
import cn.bupt.smartyagl.dao.autogenerate.BlockMapper;
import cn.bupt.smartyagl.dao.autogenerate.FarmVisitOrderMapper;
import cn.bupt.smartyagl.dao.autogenerate.GoodsCardLogMapper;
import cn.bupt.smartyagl.dao.autogenerate.GoodsCardMapper;
import cn.bupt.smartyagl.dao.autogenerate.GoodsMapper;
import cn.bupt.smartyagl.dao.autogenerate.OrderDetailMapper;
import cn.bupt.smartyagl.dao.autogenerate.OrderDetailViewMapper;
import cn.bupt.smartyagl.dao.autogenerate.OrderListMapper;
import cn.bupt.smartyagl.dao.autogenerate.OrderMasterMapper;
import cn.bupt.smartyagl.dao.autogenerate.OrderViewMapper;
import cn.bupt.smartyagl.dao.autogenerate.PayLogMapper;
import cn.bupt.smartyagl.dao.dto.OrderDTO;
import cn.bupt.smartyagl.entity.autogenerate.Address;
import cn.bupt.smartyagl.entity.autogenerate.Block;
import cn.bupt.smartyagl.entity.autogenerate.BlockExample;
import cn.bupt.smartyagl.entity.autogenerate.FarmVisitOrder;
import cn.bupt.smartyagl.entity.autogenerate.FarmVisitOrderExample;
import cn.bupt.smartyagl.entity.autogenerate.Goods;
import cn.bupt.smartyagl.entity.autogenerate.GoodsCard;
import cn.bupt.smartyagl.entity.autogenerate.GoodsCardExample;
import cn.bupt.smartyagl.entity.autogenerate.GoodsCardLog;
import cn.bupt.smartyagl.entity.autogenerate.GoodsCardLogExample;
import cn.bupt.smartyagl.entity.autogenerate.OrderDetail;
import cn.bupt.smartyagl.entity.autogenerate.OrderDetailExample;
import cn.bupt.smartyagl.entity.autogenerate.OrderDetailView;
import cn.bupt.smartyagl.entity.autogenerate.OrderDetailViewExample;
import cn.bupt.smartyagl.entity.autogenerate.OrderList;
import cn.bupt.smartyagl.entity.autogenerate.OrderListExample;
import cn.bupt.smartyagl.entity.autogenerate.OrderListExample.Criteria;
import cn.bupt.smartyagl.entity.autogenerate.OrderMaster;
import cn.bupt.smartyagl.entity.autogenerate.OrderMasterExample;
import cn.bupt.smartyagl.entity.autogenerate.OrderView;
import cn.bupt.smartyagl.entity.autogenerate.OrderViewExample;
import cn.bupt.smartyagl.entity.autogenerate.PayLog;
import cn.bupt.smartyagl.entity.autogenerate.PayLogExample;
import cn.bupt.smartyagl.model.OrderDetailModel;
import cn.bupt.smartyagl.model.OrderExcelModel;
import cn.bupt.smartyagl.model.OrderViewModel;
import cn.bupt.smartyagl.service.IGoodsService;
import cn.bupt.smartyagl.service.IOrderService;
import cn.bupt.smartyagl.service.IPushService;
import cn.bupt.smartyagl.util.DateTag;
import cn.bupt.smartyagl.util.KeyUtils;
import cn.bupt.smartyagl.util.OrderDTOToOrderMaster;
import cn.bupt.smartyagl.util.RandomCodeUtil;
import cn.bupt.smartyagl.util.SFUtil;
import cn.bupt.smartyagl.util.SendEmailUtil;
import cn.bupt.smartyagl.util.excel.ExportExcel;
import cn.bupt.smartyagl.util.excel.ExportExcelUtil;
import cn.bupt.smartyagl.util.picture.JsonConvert;

/**
 * @author jm E-mail:740869614@qq.com
 * @date 创建时间：2016-5-13 下午2:24:11
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
@Service
@RunWith(SpringJUnit4ClassRunner.class)
// 配置文件路径 ,可用通配符，注意两个变量之间用逗号隔开
@ContextConfiguration(locations = { "classpath*:/spring-*.xml",
		"classpath*:/spring.xml" })
public class OrderServiceImpl implements IOrderService {
	// 订单表
//	@Autowired
//	OrderViewWithAdressMapper orderViewWithAdressMapper;
	@Autowired
	OrderListMapper orderListMapper;
	
	@Autowired
	private OrderMasterMapper orderMasterMapper;
	
	@Autowired
	private OrderDetailMapper orderDetailMapper;
	// 订单表视图
	@Autowired
	OrderViewMapper orderViewMapper;
	
	@Autowired
	private OrderDetailViewMapper orderDetailViewMapper;
	
	@Autowired
	FarmVisitOrderMapper farmVisitOrderMapper;
	
	// 地址
	@Autowired
	AddressMapper addressMapper;
	// 商品表
	@Autowired
	GoodsMapper goodsMapper;
	// 地址服务
	@Autowired
	AddressServiceImpl addressService;
	// 购物卡服务类
	@Autowired
	IGoodsService goodsService;
	// 推送服务
	@Autowired
	IPushService pushService;
	
	@Autowired
	PayLogMapper payLogMapper;
	@Autowired
	GoodsCardMapper goodsCardMapper;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	GoodsCardLogMapper goodsCardLogMapper;
	
	@Autowired
	BlockMapper blockMapper;
	
	@Override
	public Integer getSharerIdByOrderId(int id) {
		OrderList or =  orderListMapper.selectByPrimaryKey(id);
		return or == null || or.getSharer_id() == null?null:or.getSharer_id();
	}
	@Override
	public boolean addOrder(OrderDTO orderDTO) {
		if(orderDTO == null || orderDTO.getOrderDetailList() == null || orderDTO.getOrderDetailList().size() == 0) {
			return false;
		}
		
		OrderMaster orderMaster = OrderDTOToOrderMaster.convert(orderDTO);
		Date date = new Date();
		orderMaster.setPick_up(orderDTO.getPickUp());
		orderMaster.setBuyTime(date);
		
		BigDecimal amount= new BigDecimal("0");
		BigDecimal freight = new BigDecimal("0");
		
		try {
			for(OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
				orderDetail.setOrderId(orderMaster.getOrderId());
				Goods goods = goodsService.findGoods(orderDetail.getGoodsId());
				if(goods == null) {
					return false;
				}
				orderDetail.setBuyTime(date);
				orderDetail.setGoodsId(goods.getId());
				orderDetail.setPrice(new BigDecimal(goods.getPrice()));
				orderDetail.setDetailId(Integer.parseInt(RandomCodeUtil.getNumberRandomCode(9)));
				
				amount = amount.add(new BigDecimal(goods.getPrice()).multiply(new BigDecimal(orderDetail.getNum())));
				freight = freight.add(
						new BigDecimal(goodsService.calculateFreight(orderMaster.getAddressId(), ""+orderDetail.getGoodsId(), ""+orderDetail.getNum())));
			}
			//订单金额
			orderMaster.setAmount(amount);
			
			//订单运费
			orderMaster.setFreight(freight);
			
			//添加总订单
			orderMasterMapper.insertSelective(orderMaster);
			//添加订单详情
			for(OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
				orderDetailMapper.insertSelective(orderDetail);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		
		
		
		
//		if (order.getNum() == null || order.getNum() < 0) {// 购买数量为0，返回false
//			return false;
//		}
//		order.setBuyTime(new Date());
//		if (order.getStatus() == null) {// 默认订单状态未付款
//			order.setStatus(ConstantsSql.SatusNoPay);
//		}
//		int rs;
////		double yunfei = 0;
//		try {
//			if (order.getMoney() == null) {// 设置钱
//				// 计算订单价格
////				yunfei = goodsService.calculateFreightPrice(
////						order.getGoodsId(), order.getAddressId());
//				order.setFreight(goodsService.calculateFreightPrice(order));
//				order.setMoney(goodsService.calculateGoodsPrice(order
//						.getGoodsId()  ,order) * order.getNum()+order.getFreight()
//						);
//
//			}
//			rs = orderMapper.insertSelective(order);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//		System.out.println("yunfei="+yunfei);
//		System.out.println("money="+order.getMoney());
//		if (rs > 0) {
//			return true;
//		}
//		return false;
	}

	@Override
	public List<OrderMaster> getOrderList(OrderList orderList) {
		OrderMasterExample ae = new OrderMasterExample();
		cn.bupt.smartyagl.entity.autogenerate.OrderMasterExample.Criteria cta = ae
				.createCriteria();
		cta.andUserIdEqualTo(orderList.getUserId());
		// 只显示可以显示的订单
		cta.andIs_showEqualTo(1);
		if (orderList.getStatus() != null && orderList.getStatus() > 0) {
			cta.andStatusEqualTo(orderList.getStatus());
		}
		//只显示用户未删除的订单
		cta.andIsDeleteEqualTo(false);
		List<OrderMaster> list = orderMasterMapper.selectByExample(ae);
		return list;
	}

	@Override
	public Map<String, Object> getOrderDetail(Integer orderId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 订单详情搜搜
			OrderDetailViewExample ae = new OrderDetailViewExample();
			cn.bupt.smartyagl.entity.autogenerate.OrderDetailViewExample.Criteria cta = ae
					.createCriteria();
			cta.andIdEqualTo(orderId);
			List<OrderDetailView> list = orderDetailViewMapper.selectByExample(ae);
			// 格式化图片地址
			for (OrderDetailView orderDetailView : list) {
				orderDetailView.setPicture(JsonConvert.convertToList(
						orderDetailView.getPicture(), request));
			}
			
			map.put("orderDetail", list.get(0));

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return map;
	}

	@Override
	public boolean updateOrder(OrderList order) {
		try {
			OrderList or = orderListMapper.selectByPrimaryKey(order.getId());
			if(order.getStatus() == 9 && or.getStatus() != 2)
				return false;
			int rs = orderListMapper.updateByPrimaryKeySelective(order);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@SuppressWarnings("null")
	public List<OrderViewModel> formatOrderList(List<OrderMaster> orderView) {
		List<OrderViewModel> OrderViewModel = new ArrayList<OrderViewModel>();

		for(OrderMaster orderMaster : orderView) {
			OrderViewModel tmpOrderViewModel = new OrderViewModel();
			tmpOrderViewModel.setOrderView(orderMaster);
			// 时间转化
			String dateString = DateTag.dateTimaFormat(orderMaster
					.getBuyTime());
			tmpOrderViewModel.setDateString(dateString);
			// 获取地址
			Address Address = new Address();
			Address = addressService.getAddressDetail(orderMaster
					.getAddressId());
			if (Address != null) {
				tmpOrderViewModel.setAddressname(Address.getName());
				tmpOrderViewModel.setPhone(Address.getPhone());
				tmpOrderViewModel.setProvince(Address.getProvince());
				tmpOrderViewModel.setDetailAddress(Address.getDetailAddress());
			}

			OrderViewModel.add(tmpOrderViewModel);
		}
		return OrderViewModel;
	}

	/**
	 * 修改订单状态
	 * 
	 * @author waiting
	 */
	@Override
	public boolean updateOrderStatus(String id, int status, String tracking) {

		// TODO Auto-generated method stub
	
		OrderMaster order = orderMasterMapper.selectByPrimaryKey(id + "");
		order.setStatus(status);
		order.setBuyTime(new Date());
		// 修改运单号
		if (status == ConstantsSql.SatusSending) {
			order.setTracking(tracking);
		}
		int i = orderMasterMapper.updateByPrimaryKey(order);
		//System.out.println(i);
		if (i != 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public OrderView getOrderById(Integer orderId) {
		try {
			// 订单详情搜搜
			System.out.println("getOrderById: "+orderId);
			OrderViewExample ae = new OrderViewExample();
			cn.bupt.smartyagl.entity.autogenerate.OrderViewExample.Criteria cta = ae
					.createCriteria();
			cta.andIdEqualTo(orderId);
			OrderView orderView = orderViewMapper.selectByExample(ae).get(0);
			return orderView;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public OrderMaster getOrderById(String orderId) {
		try {
			// 订单详情搜搜
			System.out.println("getOrderById: "+orderId);
			OrderMasterExample ae = new OrderMasterExample();
			cn.bupt.smartyagl.entity.autogenerate.OrderMasterExample.Criteria cta = ae
					.createCriteria();
			cta.andOrderIdEqualTo(orderId);
			OrderMaster orderMaster = orderMasterMapper.selectByExample(ae).get(0);
			System.out.println("orderMaster.orderId: "+orderMaster.getOrderId());
			return orderMaster;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public FarmVisitOrder getVisitOrderById(Integer orderId) {
		try {
			// 订单详情搜索
			FarmVisitOrderExample ae = new FarmVisitOrderExample();
			cn.bupt.smartyagl.entity.autogenerate.FarmVisitOrderExample.Criteria cta = ae
					.createCriteria();
			cta.andIdEqualTo(orderId);
			FarmVisitOrder farmVisitOrder = farmVisitOrderMapper.selectByExample(ae).get(0);
			return farmVisitOrder;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<OrderView> getOrderListByIdList(String[] orderIdArray) {
		// TODO Auto-generated method stub
		List<OrderView> orderList = new ArrayList<OrderView>();
		if (orderIdArray.length == 0) {
			return null;
		} else {
			for (int i = 0; i < orderIdArray.length; i++) {
				OrderView orderView = this.getOrderById(Integer
						.parseInt(orderIdArray[i]));
				if (orderView != null) {
					orderList.add(orderView);
				}
			}
		}
		return orderList;
	}

	@Override
	public List<FarmVisitOrder> getVisitOrderListByIdList(String[] orderIdArray) {
		// TODO Auto-generated method stub
		List<FarmVisitOrder> orderList = new ArrayList<FarmVisitOrder>();
		if (orderIdArray.length == 0) {
			return null;
		} else {
			for (int i = 0; i < orderIdArray.length; i++) {
				FarmVisitOrder orderView = this.getVisitOrderById(Integer
						.parseInt(orderIdArray[i]));
				if (orderView != null) {
					orderList.add(orderView);
				}
			}
		}
		return orderList;
	}
	
	@Override
	public boolean updateOrdersByIdList(String[] orderIdArray) {
		// TODO Auto-generated method stub
		boolean flag = true;
		for (int i = 0; i < orderIdArray.length; i++) {
			if (orderIdArray[i] != null) {
				boolean ret = this.updateOrderStatus(
						orderIdArray[i], 2, "");
				if (!ret) {
					flag = false;
				}
			}
		}
		return flag;
	}

	@Override
	public List<Integer> getGoodsIdList(String[] orderIdArray) {
		List<Integer> goodsIdList = new ArrayList<Integer>();
		if (orderIdArray.length == 0) {
			return null;
		} else {
			for (int i = 0; i < orderIdArray.length; i++) {
				if (orderIdArray[i] != null) {
					OrderView orderView = this.getOrderById(Integer
							.parseInt(orderIdArray[i]));
					if (orderView != null) {
						Integer goodsId = orderView.getGoodsId();
						goodsIdList.add(goodsId);
					}
				}
			}
		}
		return goodsIdList;
	}

	@Test
	public void test() {
		List<OrderList> orderLists = new ArrayList<OrderList>();
		OrderList order = new OrderList();
		order.setId(123);
		orderLists.add(order);
		sendOrderMessage("", "740869614@qq.com", orderLists, 100.0);
	}

	@Override
	public boolean sendOrderMessage(String phone, String email,
			List<OrderList> orderLists, Double all_price) {
		String sendMsg = "尊敬的用户您好<br>&nbsp;&nbsp;你刚购买总价为" + all_price
				+ "的商品,商品订单号分别为";
		for (OrderList orders : orderLists) {
			sendMsg += " 2151311" + orders.getId() + ",";
		}
		sendMsg += ",您可以到<a href='http://www.baidu.com'>http://www.baidu.com</a> ,查询订单详情";
		System.out.println(sendMsg);
		if (email != null) {
			SendEmailUtil.send("740869614@qq.com", "【中苑农场】恭喜您下单成功", sendMsg);
		}

		// if( ){
		//
		// }
		return true;
	}

	@Override
	public void getOrderList(int userID, ModelAndView modelAndView,
			int pageNumber, int pageSize, String orderBy, Date starttime,
			Date endtime,String orderId) {
		// TODO Auto-generated method stub
//		OrderViewExample orderViewExample = new OrderViewExample();
//		cn.bupt.smartyagl.entity.autogenerate.OrderViewExample.Criteria criteria = orderViewExample
//				.createCriteria();
		OrderMasterExample orderMasterExample = new OrderMasterExample();
		cn.bupt.smartyagl.entity.autogenerate.OrderMasterExample.Criteria criteria = orderMasterExample.createCriteria();
		if (userID != 0) {
			// 按照用户的id搜索
			criteria.andUserIdEqualTo(userID);
		}
		if(!"".equals(orderId) && orderId != null) {
			criteria.andOrderIdEqualTo(orderId);
		}
		if (starttime != null && endtime != null) {
			criteria.andBuyTimeBetween(starttime, endtime);
		}

		// 分页
		Page page = PageHelper.startPage(pageNumber, pageSize, orderBy);
		
		List<OrderMaster> orderview = orderMasterMapper.selectByExample(orderMasterExample); 
	
//		List<OrderView> orderView = orderViewMapper
//				.selectByExample(orderViewExample);
		List<OrderViewModel> OrderViewModel = this.formatOrderList(orderview);
		modelAndView.addObject("orderView", OrderViewModel);
		// 总页数
		int allPages = page.getPages();
		modelAndView.addObject("allPages", allPages);
		// 当前页码
		int currentPage = page.getPageNum();
		modelAndView.addObject("currentPage", currentPage);
	}
	/**
	 * 根据物流的xml返回成map对象
	 * @param trackingNumber
	 * @return
	 */
	public Map getMap(String trackingNumber){
	  List list = new ArrayList<Map>();
	  String phone = "";
//	      测试关闭
//	  String xml = this.getLogisticXml(trackingNumber);
//	  if(xml == null){
//		  return list;
//	  }
	  DocumentBuilderFactory dbFactory  = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder;
	      try {
			dBuilder = dbFactory.newDocumentBuilder();
			
			String xml="<?xml version='1.0' encoding='UTF-8'?>" +
					"<Response service='RouteService'>" +
					"<Head>OK</Head>" +
					"<Body>" +
					"<RouteResponse mailno='444736831765'>" +
					"<Route remark='顺丰速运 已收取快件' accept_time='2016-08-07 14:43:06' accept_address='深圳市' opcode='50'/>" +
					"<Route remark='正在派送途中,请您准备签收(派件人:刘爽,电话:687894897)' accept_time='2016-08-07 14:43:06' accept_address='深圳市' opcode='44'/>" +
					"<Route remark='快件派送不成功(因收方客户拒收快件),待进一步处理' accept_time='2016-08-07 14:43:07' accept_address='深圳市' opcode='70'/>" +
					"<Route remark='已签收,感谢使用顺丰,期待再次为您服务' accept_time='2016-08-07 14:43:07' accept_address='深圳市' opcode='80'/>" +
					"</RouteResponse></Body></Response>";
		    //测试用
			InputStream is=new ByteArrayInputStream(xml.getBytes("UTF-8"));
		    //正式用
//			InputStream is = new ByteArrayInputStream( this.getLogisticXml(trackingNumber).getBytes("UTF-8") );
			File file= ResourceUtils.getFile("classpath:shufeng.xml");
			 Document doc = dBuilder.parse(is);
	         doc.getDocumentElement().normalize();
	         XPath xPath =  XPathFactory.newInstance().newXPath();
	         String expression = "/Response/Body/RouteResponse/Route";	        
	         NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
	         for (int i = 0; i < nodeList.getLength(); i++) {
	            Node nNode = nodeList.item(i);
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	               Element eElement = (Element) nNode;
	               Map<String, String> map = new HashMap<String, String>();
	               String[] atrs = {"remark","accept_time","accept_address","opcode"};
	               String[] map_ats = {"remark","acceptTime","acceptAddress","opcode"};
	               for(int j=0;j<atrs.length;j++){
		               map.put(map_ats[j], eElement.getAttribute(atrs[j]));
		               String atr = eElement.getAttribute(atrs[j]);
		               int start_phone = atr.indexOf("电话:");
		               if(start_phone >=0){
		            	   int end = atr.indexOf(")",start_phone );
		            	   String temp_phone = atr.substring(start_phone+3,end);
		            	   if( temp_phone != null || !temp_phone.equals("") ){
		            		   phone = temp_phone;
		            	   }
		               }
		               
	               }
	               list.add(map);
	            }
	         }
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		} catch (XPathExpressionException e) {
			e.printStackTrace();
			return null;
		} catch (SAXException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	      
	    if(list.size() == 0){
	    	return null;
	    }
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("routList", list);
	    map.put("phone", phone);
	    return map;
	}
	/**
	 * 访问，返回物流xml
	 * @param trackingNumber
	 * @return
	 */
	public String getLogisticXml(String trackingNumber){
		String url = "http://bsp-oisp.sf-express.com/bsp-oisp/sfexpressService";//外网地址
		int port = 443;
		String checkword = "trCFqOcNnFlquv0Ze3tWyWHMsOWr9pWx";
		String xml="<Request service='RouteService' lang='zh-CN'>" +
				"<Head>" +
				"3711071743" +
				"</Head>" +
				"<Body>" +
				"<RouteRequest tracking_type='1' method_type='1' tracking_number='"+trackingNumber+"'/>" +
				"</Body>" +
				"</Request>";
		String verifyCode=SFUtil.md5EncryptAndBase64(xml + checkword);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("xml", xml));
		nvps.add(new BasicNameValuePair("verifyCode", verifyCode));
		HttpClient httpclient=getHttpClient(port);
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		HttpResponse response;
		try {
			response = httpclient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == 200){
				try {
					return EntityUtils.toString(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				EntityUtils.consume(response.getEntity());
				throw new RuntimeException("response status error: " + response.getStatusLine().getStatusCode());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			return null;
		}
	}
	
	private HttpClient getHttpClient(int port){
		PoolingClientConnectionManager pcm = new PoolingClientConnectionManager();
		SSLContext ctx=null;
		try{
			ctx = SSLContext.getInstance("TLS");
			X509TrustManager x509=new X509TrustManager(){
				public void checkClientTrusted(X509Certificate[] xcs, String string)
					throws CertificateException {
				}
				public void checkServerTrusted(X509Certificate[] xcs, String string)
					throws CertificateException {
				}
				public X509Certificate[] getAcceptedIssuers(){
					return null;
				}
			};
			ctx.init(null, new TrustManager[]{x509}, null);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		Scheme sch = new Scheme("https", port, ssf);
		pcm.getSchemeRegistry().register(sch);
		return new DefaultHttpClient(pcm);
	}


	@Override
	public boolean exportExcelOrder(Date starttime, Date endtime,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		ExportExcel<OrderExcelModel> excel = new ExportExcel<OrderExcelModel>();
		String[] headers = { "订单编号", "商品名称", "价格", "购买次数", "收货人姓名", "联系方式",
				"省市县", " 详细地址" ,"订单状态"};
		OrderViewExample orderViewExample = new OrderViewExample();
		cn.bupt.smartyagl.entity.autogenerate.OrderViewExample.Criteria criteria = orderViewExample
				.createCriteria();
		if (starttime!=null) {
			criteria.andBuyTimeBetween(starttime, endtime);
		}
        Long st = System.currentTimeMillis();
		
		
		List<OrderView> orderView = orderViewMapper
				.selectByExample(orderViewExample);
		long end = System.currentTimeMillis();
		System.out.println("查询耗时："+(end-st)/1000 +"s");
		List<OrderExcelModel> OrderExcelModel = new ArrayList<OrderExcelModel>();
		// 转变成OrderExcelModel格式的
		OrderView tmpOrderView = null;
		long st3 = System.currentTimeMillis();
		for (int i = 0; i < orderView.size(); i++) {
			OrderExcelModel tmpOrderExcelModel = new OrderExcelModel();
			tmpOrderView = orderView.get(i);
			//tmpOrderViewModel.setOrderView(tmpOrderView);
			tmpOrderExcelModel.setId(tmpOrderView.getId());
			tmpOrderExcelModel.setProductName(tmpOrderView.getName());
			tmpOrderExcelModel.setPrice(tmpOrderView.getPrice());
			tmpOrderExcelModel.setNum(tmpOrderView.getNum());
			switch(tmpOrderView.getStatus()) {
				case 1:tmpOrderExcelModel.setOrderStatus("未付款");break;
				case 2:tmpOrderExcelModel.setOrderStatus("待发货");break;
				default:tmpOrderExcelModel.setOrderStatus("交易完成");
			}
			// 时间转化
//			String dateString = DateTag.dateTimaFormat(tmpOrderView
//					.getBuyTime());
//			tmpOrderViewModel.setDateString(dateString);
			// 获取地址
			Address Address = new Address();
			Address = addressService.getAddressDetail(tmpOrderView
					.getAddressId());
			if (Address != null) {
				tmpOrderExcelModel.setAddressname(Address.getName());
				tmpOrderExcelModel.setPhone(Address.getPhone());
				tmpOrderExcelModel.setProvince(Address.getProvince());
				tmpOrderExcelModel.setDetailAddress(Address.getDetailAddress());
			}
			OrderExcelModel.add(tmpOrderExcelModel);
		}
		long en3 = System.currentTimeMillis();
		System.out.println("转换model耗时："+(en3-st3)/1000 +"s");
		try {
			String deskPath = ExportExcelUtil.getDeskPath();
			System.out.println(deskPath);
			String filePath = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
			if(starttime != null) {
				filePath = deskPath + File.separator+sdf.format(starttime) + "-" +sdf.format(endtime)+ "订单统计.xls";
			}
			else
				filePath = deskPath + File.separator +  "所有订单.xls";
			OutputStream out = new FileOutputStream(filePath);
	        Long st1 = System.currentTimeMillis();
	        excel.exportExcel(headers, OrderExcelModel, out);
			long end1 = System.currentTimeMillis();
			System.out.println("导出excel耗时："+(end1-st1)/1000 +"s");
			
			//System.out.println("aaa");
			out.close();
	        Long st2 = System.currentTimeMillis();
			ExportExcelUtil.download(filePath, response);
			long end2 = System.currentTimeMillis();
			System.out.println("传输excel耗时："+(end2-st2)/1000 +"s");
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public boolean rebackMondy(Integer orderId) {
		//获取退货订单
		OrderView ov = getOrderById(orderId);
		Double money = ov.getMoney() + ov.getFreight();
		//获取退货内容
		PayLogExample pe = new PayLogExample();
		pe.createCriteria().andOrderIdsLike("%"+orderId+"%");
		PayLog payLog = payLogMapper.selectByExample(pe).get(0);
		return false;
	}

	@Override
	public Map<Integer, Integer> getOrderNums(Integer userId) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		OrderListExample oe = new OrderListExample();
		Criteria cta = oe.createCriteria();
		cta.andIdEqualTo(userId);
		Integer all = orderListMapper.countByExample(oe);
		
		cta.andStatusEqualTo( ConstantsSql.SatusNoPay );
		Integer noPay = orderListMapper.countByExample(oe);
		
		cta.andStatusEqualTo( ConstantsSql.SatusNoSend );
		Integer noSend = orderListMapper.countByExample(oe);
		
		cta.andStatusEqualTo( ConstantsSql.SatusSending );
		Integer sending = orderListMapper.countByExample(oe);
		
		cta.andStatusEqualTo( ConstantsSql.SatusReceive );
		Integer satusReceive = orderListMapper.countByExample(oe);
		
		map.put( 0, all );
		map.put(ConstantsSql.SatusNoPay, noPay);
		map.put(ConstantsSql.SatusNoSend, noSend);
		map.put(ConstantsSql.SatusSending, sending);
		map.put(ConstantsSql.SatusReceive, satusReceive);
		return map;
	}

	@Override
	public boolean cancleCardPaySomeOrder(String orderIds) {
		//先获得订单号
        String[] orderIdList = orderIds.split(",");
        //没有传订单号，返回false
        if(orderIdList.length ==0 )
        	return false;
        List<OrderList> orderList = new ArrayList<OrderList>();
		for (int i = 0; i < orderIdList.length; i++) {
			OrderList tmp = orderListMapper.selectByPrimaryKey( Integer.parseInt(orderIdList[i]));
			orderList.add(tmp);
			if(tmp.getStatus() != ConstantsSql.PayStatus_CardPaySome)
				return false;
		}
		//遍历订单列表，取消订单
		try{
  			 for(OrderList orList : orderList){
		    		//修改订单状态为未支付
		    		orList.setStatus( ConstantsSql.PayStatus_NoPay );
		    		orderListMapper.updateByPrimaryKeySelective(orList);
		    		//获取购物卡记录
		    		GoodsCardLogExample ge = new GoodsCardLogExample();
		    		ge.createCriteria().andOrderIdEqualTo( orList.getId() );
		    		List<GoodsCardLog> goodsCardLogs = goodsCardLogMapper.selectByExample(ge);
		    		GoodsCardLog gl = goodsCardLogs.get( goodsCardLogs.size()-1 );
		    		//退还购物卡对应价格
		    		GoodsCardExample gce = new GoodsCardExample();
		    		gce.createCriteria().andNumberEqualTo( gl.getCardId() );
		    		List<GoodsCard> gcList = goodsCardMapper.selectByExample(gce);
		    		GoodsCard gc = new GoodsCard(); ;
		    		gc.setMoney( gl.getMoney()+ gcList.get(0).getMoney() );
		    		GoodsCardExample gcExample = new GoodsCardExample();
		    		gcExample.createCriteria().andNumberEqualTo( gl.getCardId() );
		    		goodsCardMapper.updateByExampleSelective( gc , gcExample );
		    		//删除购物卡记录
		    		goodsCardLogMapper.deleteByPrimaryKey( gl.getId() );
		        }
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	@Override
	public List<OrderView> getOrderByName(String goodsName, Integer userId) {
		OrderViewExample ore = new OrderViewExample();
		ore.createCriteria().andNameLike("%"+goodsName+"%").andUserIdEqualTo(userId).andIsDeleteEqualTo(false);
		List<OrderView> list = orderViewMapper.selectByExample(ore);
		for (OrderView orderView : list) {
			orderView.setPicture(JsonConvert.convertToList(
					orderView.getPicture(), request));
		}
		return list;
	}
	@Override
	public int deleteOrderById(Integer orderId) {
		OrderList or = new OrderList();
		or.setId(orderId);
		or.setIsDelete(true);
		return orderListMapper.updateByPrimaryKeySelective(or);
	}
	public List<OrderList> getOrdersByUserId(Integer userId) {
		OrderListExample ore = new OrderListExample();
		ore.createCriteria().andUserIdEqualTo(userId);
		return orderListMapper.selectByExample(ore);
	}
	
	@Override
	public boolean updateOrderStatus(String id, Integer status) {
		OrderMaster order = orderMasterMapper.selectByPrimaryKey(id);
		order.setStatus(status);
		order.setBuyTime(new Date());
		
		int i = orderMasterMapper.updateByPrimaryKey(order);
		//System.out.println(i);
		if (i != 0) {
			return true;
		} else {
			return false;
		}
	}
	@Override
	public List<OrderDetail> getOrderDetailList(String orderId) {
		OrderDetailExample orderDetailExample = new OrderDetailExample();
		cn.bupt.smartyagl.entity.autogenerate.OrderDetailExample.Criteria criteria = orderDetailExample.createCriteria();
		criteria.andOrderIdEqualTo(orderId);
		return orderDetailMapper.selectByExample(orderDetailExample);
	}
	@Override
	public Map<String, Object> getOrderMaster(String orderId) {
		OrderMasterExample orderMasterExample = new OrderMasterExample();
		cn.bupt.smartyagl.entity.autogenerate.OrderMasterExample.Criteria criteria = orderMasterExample.createCriteria();
		criteria.andOrderIdEqualTo(orderId);
		OrderMaster orderMaster = orderMasterMapper.selectByPrimaryKey(orderId);
		Address address = addressMapper.selectByPrimaryKey(orderMaster.getAddressId());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderView", orderMaster);
		map.put("address", address);
		return map;
	}
	
	public List<OrderDetailModel> getOrderDetailByBlockManagerIdAndIsSolved(Integer blockManagerId,Integer isSolved){
		BlockExample example = new BlockExample();
		cn.bupt.smartyagl.entity.autogenerate.BlockExample.Criteria criteria = example.createCriteria();
		criteria.andManagerIdEqualTo(blockManagerId);
		List<Block> blocks = blockMapper.selectByExample(example);
		List<Integer> goodsId = new ArrayList<>();
		for(Block block : blocks) {
			
			goodsId.add(block.getGoodId());
			
		}
		
		OrderDetailExample orderExample = new OrderDetailExample();
		cn.bupt.smartyagl.entity.autogenerate.OrderDetailExample.Criteria orderCriteria = orderExample.createCriteria();
		orderCriteria.andGoodsIdIn(goodsId);
		System.out.println("goodsId: "+goodsId);
		List<OrderDetail> orderDetails = orderDetailMapper.selectByExample(orderExample);
		List<OrderDetailModel> orderDetailModels = new ArrayList<>();
		int i = 0;
		for(OrderDetail orderDetail : orderDetails) {
			System.out.println("orderDetail: "+orderDetail+"  i: "+i);
			OrderDetailModel orderDetailModel = OrderDetail2OrderDetailModel.conventer(orderDetail);
			System.out.println("orderModel: "+orderDetailModel+"  i: "+i);
			if(orderDetailModel.getStatus() == isSolved) {
				orderDetailModels.add(orderDetailModel);
			}
		}
		
		return orderDetailModels;
	}
	
	@Override
	public List<OrderDetailModel> getOrderDetailByBlockManagerIdAndUnSolved(Integer blockManagerId) {
		
		return this.getOrderDetailByBlockManagerIdAndIsSolved(blockManagerId, ConstantsSql.SatusNoSend);
		
	}
	@Override
	public List<OrderDetailModel> getOrderDetailByBlockManagerIdAndSolved(Integer blockManagerId) {
		
		return this.getOrderDetailByBlockManagerIdAndIsSolved(blockManagerId, ConstantsSql.SatusSending);
		
	}
	
}
