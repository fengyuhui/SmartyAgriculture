package cn.bupt.smartyagl.controller.inf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.bupt.smartyagl.entity.autogenerate.GoodsCard;
import cn.bupt.smartyagl.entity.autogenerate.GoodsCardLog;
import cn.bupt.smartyagl.entity.autogenerate.GoodsCart;
import cn.bupt.smartyagl.entity.autogenerate.OrderView;
import cn.bupt.smartyagl.service.IGoodsCardService;
import cn.bupt.smartyagl.service.IGoodsCartService;
import cn.bupt.smartyagl.service.IGoodsService;
import cn.bupt.smartyagl.service.IUserService;
import cn.bupt.smartyagl.service.impl.OrderServiceImpl;
import cn.bupt.smartyagl.service.impl.PayLogServiceImpl;
import cn.bupt.smartyagl.service.impl.UserScoreService;
import cn.bupt.smartyagl.util.NetDataAccessUtil;
import cn.bupt.smartyagl.util.UserScoreUtil;

/** 
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-5-13 下午2:27:01 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
@Controller
@RequestMapping("interface/goodsCart")
public class GoodsCartController extends AppBaseController{
	@Autowired
	IGoodsCartService goodsCartService;
	
	@Autowired
    IGoodsCardService goodsCardService;
	
	@Autowired
    OrderServiceImpl orderService;
	@Autowired
	PayLogServiceImpl payLogService;
	@Autowired
	IUserService userService;
	@Autowired
	IGoodsService goodsService;
	@Autowired 
	UserScoreService userScoreService;
	/**
	 * 添加购物车
	 * @param request
	 * @param goodsCart
	 * @return
	 */
	@RequestMapping("/addGoodsCart")
	@ResponseBody
	public Object addGoodsCart(HttpServletRequest request,GoodsCart goodsCart) {
		Integer userId = (Integer) request.getAttribute("userId");
		goodsCart.setUserId(userId);
		boolean rst =  goodsCartService.addGoodsCart(goodsCart);
		NetDataAccessUtil nau = new NetDataAccessUtil();
		if(rst){
			nau.setResult(1);
			nau.setResultDesp("添加购物车成功");
		}else{
			nau.setResult(0);
			nau.setResultDesp("添加购物车失败");
		}
		return nau;
	}
	
	/**
	 * 批量增加购物车
	 * @param request
	 * @param goodsCartList
	 * @return
	 */
	@Deprecated
	@RequestMapping("/addShopCartList")
	@ResponseBody
	public Object addShopCartList(HttpServletRequest request,List<GoodsCart>goodsCartList){
		Integer userId = (Integer) request.getAttribute("userId");
		
		boolean rst = false;
		for (GoodsCart goodsCart : goodsCartList) {
			goodsCart.setUserId(userId);
			rst =  goodsCartService.addGoodsCart(goodsCart);
			if(!rst){
				break;
			}
		}
		NetDataAccessUtil nau = new NetDataAccessUtil();
		if(rst){
			nau.setResult(1);
			nau.setResultDesp("添加购物车成功");
		}else{
			nau.setResult(0 );
			nau.setResultDesp("添加购物车失败");
		}
		return nau;
	}
	
	/**
	 * 本地同步购物车
	 * @param request
	 * @param goodsCartList
	 * @return
	 */
	@RequestMapping("/cograShopCart")
	@ResponseBody
	public Object cograShopCart(HttpServletRequest request,String goodsCartMessage) 
			throws JsonParseException, JsonMappingException, IOException{
		NetDataAccessUtil nau = new NetDataAccessUtil();
		Integer userId = (Integer) request.getAttribute("userId");
		try {
			ObjectMapper om = new ObjectMapper();
			
			List<GoodsCart> goodsCarts= om.readValue(goodsCartMessage, new TypeReference<List<GoodsCart>>() {} );
//			goodsCart goodsCart;
//			goodsCart.setUserId(userId);
//			boolean rst =  goodsCartService.cograShopCart(goodsCart);
			StringBuffer sb = new StringBuffer();//购物车id数组
			boolean rst = true;
			
			for(GoodsCart tmp : goodsCarts){//批量插入购物车
				tmp.setUserId(userId);
				boolean tmp_rs = goodsCartService.addGoodsCart(tmp);
				if(!tmp_rs){
					rst = false;
					break;
				}
				sb.append(tmp.getId()+",");
			}
			
//			boolean rst =  goodsCartService.addGoodsCart(goodsCart);
			if( goodsCarts.size()>0 && rst){
				
				List<Integer> goodsIds = new ArrayList<Integer>();
				List<Integer> nums = new ArrayList<Integer>();
				for( GoodsCart tmp : goodsCarts){
					goodsIds.add( tmp.getGoodsId() );
					nums.add( tmp.getNum());
				}
				String ids = sb.substring(0,sb.lastIndexOf(","));
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("goodsIds",goodsIds);
				map.put("nums",nums);
				nau.setContent(map);
				nau.setResult(1);
				nau.setResultDesp("同步购物车成功");
			}else{
				nau.setResult(0 );
				nau.setResultDesp("同步购物车失败");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			nau.setResult(1);
			nau.setResultDesp("没有需要同步的商品");
		}
		return nau;
	}
	
	/**
	 * 删除购物车
	 * @param request
	 * @param goodsId
	 * @return
	 */
	@RequestMapping("/deleteGoodsCart")
	@ResponseBody
	public Object deleteGoodsCart(HttpServletRequest request,Integer goodsId) {
		Integer userId = (Integer) request.getAttribute("userId");
		boolean rst =  goodsCartService.deleteGoodsCart(goodsId,userId);
		NetDataAccessUtil nau = new NetDataAccessUtil();
		if(rst){
			nau.setResult(1);
			nau.setResultDesp("删除购物车成功");
		}else{
			nau.setResult(0 );
			nau.setResultDesp("删除购物车失败");
		}
		return nau;
	}
	
	/**
	 * 批量删除购物车
	 * @param request
	 * @param goodsId
	 * @return
	 */
	@RequestMapping("/deleteGoodsCartBatch")
	@ResponseBody
	public Object deleteGoodsCartBatch(HttpServletRequest request,String goodsIds) {
		Integer userId = (Integer) request.getAttribute("userId");
		//类型转换
		String[] goodsIdList = goodsIds.split(",");
		List<Integer> list = new ArrayList<Integer>();
		for(String goodsId : goodsIdList){
			if( !goodsId.equals("") ){
				list.add( Integer.parseInt( goodsId  ) );
			}
		}
		
		boolean rst =  goodsCartService.deleteGoodsCartBatch(list, userId);
		NetDataAccessUtil nau = new NetDataAccessUtil();
		if(rst){
			nau.setResult(1);
			nau.setResultDesp("删除购物车成功");
		}else{
			nau.setResult(0 );
			nau.setResultDesp("删除购物车失败");
		}
		return nau;
	}
	
	/*
	 * 获取购物车列表
	 */
	@RequestMapping("/getGoodsCartList")
	@ResponseBody
	public Object getGoodsCartList(HttpServletRequest request) {
		Integer userId = (Integer) request.getAttribute("userId");
		List rst =  goodsCartService.getGoodsCartList(userId,request);
		
		NetDataAccessUtil nau = new NetDataAccessUtil();
		nau.setContent(rst);
		nau.setResult(1);
		nau.setResultDesp("获取购物车列表成功");
		return nau;
	}
	
	/*
	 * 未登录获取购物车列表
	 */
	@RequestMapping("/getGoodsCartWithoutLog")
	@ResponseBody
	public Object getGoodsCartWithoutLog(String goodsIds[],String nums[],HttpServletRequest request) {
		NetDataAccessUtil nau = new NetDataAccessUtil();
//		Map<String,Object> nau_map = new HashMap<String,Object >();				
//		Map<String,Object> map = new HashMap<String,Object>();
		if (goodsIds.length!=nums.length||goodsIds==null) {
			nau.setContent(null);
			nau.setResult(0);
			nau.setResultDesp("获取购物车列表失败");
		} else {
			List rst =  goodsCartService.getGoodsCartWithoutLog( goodsIds, nums, request);	
			
			nau.setContent(rst);
			nau.setResult(1);
			nau.setResultDesp("获取购物车列表成功");
		}
		return nau;		
	}
	/**
	 * 修改购物车列表
	 * @param request
	 * @param goodsCart
	 * @return
	 */
	@RequestMapping("/updateGoodsDetail")
	@ResponseBody
	public Object updateGoodsDetail(HttpServletRequest request,GoodsCart goodsCart) {
		Integer userId = (Integer) request.getAttribute("userId");
		goodsCart.setUserId(userId);
		boolean rst =  goodsCartService.updateGoodsDetail(goodsCart);
		NetDataAccessUtil nau = new NetDataAccessUtil();
		if(rst){
			nau.setResult(1);
			nau.setResultDesp("修改购物车成功");
		}else{
			nau.setResult(0 );
			nau.setResultDesp("修改购物车失败");
		}
		return nau;
	}
	
	/**
     * 购物卡进行购物
     */
    @RequestMapping("/payByCard")
    @ResponseBody
    public NetDataAccessUtil buyGoodsByCard(GoodsCard goodsCard,String orderIds){
        NetDataAccessUtil nu = new NetDataAccessUtil();
        boolean rs = goodsCardService.valideCard(goodsCard);
        //验证账号密码
        if(!rs){
            nu.setResult(0);
            nu.setResultDesp("购物卡账号密码错误");
            return nu;
        }
        //计算订单总价
        String[] orderIdList = orderIds.split(",");
        List<OrderView> orderList = orderService.getOrderListByIdList(orderIdList);
        if( orderList.size() < orderIdList.length){
            nu.setResult(0);
            nu.setResultDesp("有订单不存在");
            return nu;
        }
        Double allPrice = 0.0;
        for (OrderView orderView : orderList) {
            allPrice += orderView.getMoney() /*+ orderView.getFreight()*/;
        }
        
        //判断订单余额是否足够
        Double reamin = goodsCardService.reduceMoney(goodsCard.getNumber(), allPrice);
//        System.out.println(reamin);
        System.out.println(reamin*-1);
        if( reamin == null){
        	nu.setContent(null);
            nu.setResult(0);
            nu.setResultDesp("该购物卡不存在");
            return nu;
        }
        if( reamin < 0 ){  
        	Double pay = allPrice;
        	//添加购物卡消费记录，金币均摊
        	goodsCardService.addGoodsCartLogByAverage(orderList, allPrice-(-1*reamin),goodsCard.getNumber() );
        	Map map = new HashMap<String, Object>();
        	map.put("remainder", 0);
        	map.put("overdueMoney", reamin * -1);
//        	System.out.println(reamin*-1);
        	nu.setContent(map);
            nu.setResult(1);
            nu.setResultDesp("购物卡余额不足,还需其他支付方式支付");
            return nu;
        }
        Map<String,Object> rsContent = new HashMap<String, Object>();
        //扣款成功，更改订单状态
        rs = orderService.updateOrdersByIdList(orderIdList);
        for(String tmp : orderIdList) {
        	OrderView or = orderService.getOrderById(Integer.parseInt(tmp));
        	goodsService.updateGoodsBuyNum(or.getNum(), or.getGoodsId());
        	UserScoreUtil.addScore(Integer.parseInt(tmp), userService, orderService, goodsService, userScoreService);
        }
        //添加购物记录
        Date date = new Date();
        for(cn.bupt.smartyagl.entity.autogenerate.OrderView orderView : orderList){
            GoodsCardLog gl = new GoodsCardLog();
            gl.setMoney( orderView.getMoney() + orderView.getFreight()  );
            gl.setOrderId(orderView.getId());
            gl.setCardId( goodsCard.getNumber() );
            gl.setCreate_time(date);
            goodsCardService.addGoodsCartLog(gl);
        }
        
        //余额  
        rsContent.put("remainder", reamin);
        rsContent.put("overdueMoney", 0);
        nu.setContent(rsContent);
        nu.setResult(1);
        nu.setResultDesp("购买成功");
        return nu;
    }
	
    /**
     * 查询购物卡余额接口
     */
    @RequestMapping("/findCardRemainder")
    @ResponseBody
    public NetDataAccessUtil findCardRemainder(Integer number, String passwd){
    	 NetDataAccessUtil na = new NetDataAccessUtil();
    	 GoodsCard card = goodsCardService.getGoodsCart(number, passwd);
    	 if( card == null ){
    		 na.setContent(null);
             na.setResult(0);
             na.setResultDesp("购物卡用户名或密码填写错误");
             return na;
    	 }
    	 Map<String,Object> map = new HashMap<String, Object>();
    	 map.put("remainder", card.getMoney() );
    	 na.setContent( map );
         na.setResult(1);
         na.setResultDesp("查询成功");
    	 return na;
    }
    
}
