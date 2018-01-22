package cn.bupt.smartyagl.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.expr.NewArray;

import javax.servlet.http.HttpServletRequest;

import oracle.net.aso.g;
import oracle.net.aso.i;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;

import cn.bupt.smartyagl.dao.autogenerate.GoodsCartViewMapper;
import cn.bupt.smartyagl.dao.autogenerate.GoodsListMapper;
import cn.bupt.smartyagl.dao.autogenerate.GoodsCartMapper;
import cn.bupt.smartyagl.entity.GoodsTypeParent;
import cn.bupt.smartyagl.entity.autogenerate.GoodsCartView;
import cn.bupt.smartyagl.entity.autogenerate.GoodsCartViewExample;
import cn.bupt.smartyagl.entity.autogenerate.GoodsList;
import cn.bupt.smartyagl.entity.autogenerate.GoodsListExample;
import cn.bupt.smartyagl.entity.autogenerate.Address;
import cn.bupt.smartyagl.entity.autogenerate.AddressExample;
import cn.bupt.smartyagl.entity.autogenerate.CollectExample;
import cn.bupt.smartyagl.entity.autogenerate.Goods;
import cn.bupt.smartyagl.entity.autogenerate.GoodsCart;
import cn.bupt.smartyagl.entity.autogenerate.AddressExample.Criteria;
import cn.bupt.smartyagl.entity.autogenerate.GoodsCartExample;
import cn.bupt.smartyagl.model.GoodsCartModel;
import cn.bupt.smartyagl.service.IGoodsCartService;
import cn.bupt.smartyagl.util.picture.JsonConvert;

/** 
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-5-13 下午2:23:57 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
@Service
public class GoodsCartServiceImpl implements IGoodsCartService{
	@Autowired
	GoodsCartMapper goodsCartMapper;
	@Autowired
	GoodsCartViewMapper goodsCartViewMapper;
	@Autowired
	private GoodsListMapper goodsListMapper;
	
	@Override
	public boolean addGoodsCart(GoodsCart goodsCart) {
		GoodsCart tmp = this.getGoodsByGoodsIdAndUserId(goodsCart.getGoodsId(), goodsCart.getUserId());
		if(tmp == null){//购物车不存在该商品
			int rs = goodsCartMapper.insert(goodsCart);
			if(rs>0){
		    	return true;
		    }
			return false;
		}else{//购物车已存在改商品
			goodsCart.setNum(tmp.getNum() + goodsCart.getNum());
			System.out.println(goodsCart.getNum());
			return this.updateGoodsDetail(goodsCart);
		}
	}

	@Override
	public boolean deleteGoodsCart(Integer goodsId,Integer userId) {
		GoodsCartExample ae = new GoodsCartExample();
		cn.bupt.smartyagl.entity.autogenerate.GoodsCartExample.Criteria cta = ae.createCriteria();
		cta.andGoodsIdEqualTo(goodsId);
		cta.andUserIdEqualTo(userId);
		int rs = goodsCartMapper.deleteByExample(ae);
		if(rs>0){
	    	return true;
	    }
		return false;
	}

	@Override
	public List getGoodsCartList(Integer userId,HttpServletRequest request) {
		GoodsCartViewExample ae = new GoodsCartViewExample();
		cn.bupt.smartyagl.entity.autogenerate.GoodsCartViewExample.Criteria cta = ae.createCriteria();
		cta.andUserIdEqualTo(userId);
		List<GoodsCartView> list = goodsCartViewMapper.selectByExample(ae);
		//获取类型
		List<GoodsTypeParent> typeList = new ArrayList<GoodsTypeParent>();
		for(GoodsCartView tmp : list){
			//更改图片格式
			String picture = JsonConvert.getOnePicture(tmp.getPicture(), request);
			tmp.setPicture(picture);
			//类型分类
			boolean exist = false;
			for(GoodsTypeParent type : typeList){
				if(tmp.getTypeParent().equals(type.getTypeParent())){
					exist = true;
				}
			}
			if(!exist){
				GoodsTypeParent gyp = new GoodsTypeParent();
				gyp.setTypeParent(tmp.getTypeParent());
				gyp.setTypeIdParent(tmp.getTypeIdParent());
				typeList.add(gyp);
			}
				
		}
		//分类存放到map中
		List ar = new ArrayList();
		for(GoodsTypeParent type : typeList){
			Map<String,Object> tmp_map = new HashMap<String,Object >();
			List<GoodsCartView> typeCartList = new ArrayList<GoodsCartView>();
			tmp_map.put("typeParent", type.getTypeParent());
			tmp_map.put("typeIdParent", type.getTypeIdParent());
			tmp_map.put("list", typeCartList);
			for(GoodsCartView tmp : list){
				if(tmp.getTypeParent().equals( type.getTypeParent() )){
					typeCartList.add( tmp );
				}
			}
			ar.add(tmp_map);
		}
		return ar;
	}

	//未登录时添加商品返回购物车列表 "1,2，3"
	@Override
	public List getGoodsCartWithoutLog(String goodsIds[],String nums[],HttpServletRequest request) {
		
		    List<Integer> goodsIdsList=new ArrayList<Integer >();
		    List<Integer> numsList=new ArrayList<Integer >();		    
		   
		    for (int i = 0 ; i< goodsIds.length;i++)
			{				
				int goodsId = Integer.parseInt(goodsIds[i]);
				goodsIdsList.add(goodsId );
				int num = Integer.parseInt(nums[i]);
				numsList.add(num);
			}
			GoodsListExample ae = new GoodsListExample();
			cn.bupt.smartyagl.entity.autogenerate.GoodsListExample.Criteria cta = ae.createCriteria();		
			cta.andIdIn(goodsIdsList);
			List<GoodsList> listGoods = goodsListMapper.selectByExample(ae);
			List<GoodsCartView> listCart = new ArrayList<GoodsCartView>();
					
			for(int j=0; j<goodsIdsList.size() ; j++){
				GoodsCartView gv = new GoodsCartView();
				GoodsList gl = listGoods.get(j);
				gv.setGoodsId(goodsIdsList.get(j));
				gv.setName( gl.getName());
				gv.setNum(numsList.get(j));
				gv.setPrice(gl.getPrice());
				gv.setTypeParent(gl.getTypeParent());
				gv.setPicture(gl.getPicture());
				gv.setTypeIdParent(gl.getTypeIdParent());
				gv.setTypeIdChild(gl.getTypeIdChild());
				gv.setTypeChild(gl.getTypeChild());
				gv.setTitle(gl.getTitle());
				gv.setSpecialPrice(gl.getSpecialPrice());
				gv.setUnit(gl.getUnit());
				gv.setSaleStatus(gl.getSaleStatus());
				gv.setEndTime(gl.getEndTime());
				gv.setStatus(gl.getStatus());
				listCart.add(gv);
			}
		
		//获取类型
		List<GoodsTypeParent> typeList = new ArrayList<GoodsTypeParent>();
		for(GoodsCartView tmp : listCart){
			//更改图片格式
			String picture = JsonConvert.getOnePicture(tmp.getPicture(), request);
			tmp.setPicture(picture);
			//类型分类
			boolean exist = false;
			for(GoodsTypeParent type : typeList){
				if(tmp.getTypeParent().equals(type.getTypeParent())){
					exist = true;
				}
			}
			if(!exist){
				GoodsTypeParent gyp = new GoodsTypeParent();
				gyp.setTypeParent(tmp.getTypeParent());
				gyp.setTypeIdParent(tmp.getTypeIdParent());
				typeList.add(gyp);
			}
				
		}
		//分类存放到map中				
		List ar = new ArrayList();
		for(GoodsTypeParent type : typeList){
			Map<String,Object> tmp_map = new HashMap<String,Object >();
			List<GoodsCartView> typeCartList = new ArrayList<GoodsCartView>();
			tmp_map.put("typeParent", type.getTypeParent());
			tmp_map.put("typeIdParent", type.getTypeIdParent());
			tmp_map.put("list", typeCartList);
			for(GoodsCartView tmp : listCart){
				if(tmp.getTypeParent().equals( type.getTypeParent() )){
					typeCartList.add( tmp );
				}
			}
			ar.add(tmp_map);
		}		
		return ar;
	}

	@Override
	public boolean updateGoodsDetail(GoodsCart goodsCart) {
		try{
			if(goodsCart.getId() != null){
				int rs = goodsCartMapper.updateByPrimaryKeySelective(goodsCart);
			}else{
				GoodsCartExample ae = new GoodsCartExample();
				cn.bupt.smartyagl.entity.autogenerate.GoodsCartExample.Criteria cta = ae.createCriteria();
				cta.andGoodsIdEqualTo(goodsCart.getGoodsId());
				cta.andUserIdEqualTo(goodsCart.getUserId());
				int rs = goodsCartMapper.updateByExampleSelective(goodsCart, ae);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public GoodsCart getGoodsByGoodsIdAndUserId(Integer goodsId, Integer userId) {
		GoodsCartExample ae = new GoodsCartExample();
		cn.bupt.smartyagl.entity.autogenerate.GoodsCartExample.Criteria cta = ae.createCriteria();
		cta.andGoodsIdEqualTo(goodsId);
		cta.andUserIdEqualTo(userId);
		List<GoodsCart> rs = goodsCartMapper.selectByExample(ae);
		if(rs == null || rs.size()<=0){
			return null;
		}
		return rs.get(0);
	}
	
	@Override
	public boolean deleteGoodsCartBatch(List<Integer> goodsIds, Integer userId) {
		GoodsCartExample ae = new GoodsCartExample();
		cn.bupt.smartyagl.entity.autogenerate.GoodsCartExample.Criteria cta = ae.createCriteria();
		cta.andGoodsIdIn(goodsIds);
		cta.andUserIdEqualTo(userId);
		int rs = goodsCartMapper.deleteByExample(ae);
		if(rs>0){
	    	return true;
	    }
		return false;
	}

	@Override
	public Integer getGoodsCartAll(Integer userId, HttpServletRequest request) {
		Integer num = 0;
		GoodsCartExample ae = new GoodsCartExample();
		ae.createCriteria().andUserIdEqualTo(userId);
		List<GoodsCart> list = goodsCartMapper.selectByExample( ae );
		for(GoodsCart tmp : list){
			num += tmp.getNum();
		}
		return num;
	}

}
