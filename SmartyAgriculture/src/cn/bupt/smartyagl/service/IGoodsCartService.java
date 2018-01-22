package cn.bupt.smartyagl.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.bupt.smartyagl.entity.autogenerate.GoodsCart;

/** 
 * 
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-5-13 上午10:46:58 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public interface IGoodsCartService {
	/**
	 * 添加购物车商品
	 * @param goodsCart
	 * @return
	 */
	public boolean addGoodsCart(GoodsCart goodsCart);
	/**
	 * 删除购物车商品
	 * @param goodsId
	 * @param userId
	 * @return
	 */
	public boolean deleteGoodsCart(Integer goodsId,Integer userId);
	/**
	 * 删除删除购物车
	 * @param goodsId eg. 1,2,3
	 * @param userId
	 * @return
	 */
	public boolean deleteGoodsCartBatch(List<Integer> goodsIds,Integer userId);
	/**
	 * 获取商品购物车列表
	 * @param userId
	 * @return
	 */
	public List getGoodsCartList(Integer userId,HttpServletRequest request);
	/**
	 * 获得购物车总数量
	 */
	public Integer getGoodsCartAll(Integer userId,HttpServletRequest request);
	/**
	 * 更新购物车
	 * @param goodsCart
	 * @return
	 */
	public boolean updateGoodsDetail(GoodsCart goodsCart);
	/**
	 * 根据商品id和用户id获得购物车物件信息
	 * @param goodsId
	 * @param userId
	 * @return
	 */
	public GoodsCart getGoodsByGoodsIdAndUserId(Integer goodsId,Integer userId);
	/**
	 * 未登录获取商品购物车列表
	 * @param goodsId
	 * @return
	 */
	public List getGoodsCartWithoutLog(String goodsIds[],String nums[],
			HttpServletRequest request);
	
}
