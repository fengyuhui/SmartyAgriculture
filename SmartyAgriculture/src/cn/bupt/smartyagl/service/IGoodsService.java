package cn.bupt.smartyagl.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.Page;

import cn.bupt.smartyagl.entity.GoodsListAndCollect;
import cn.bupt.smartyagl.entity.autogenerate.Collect;
import cn.bupt.smartyagl.entity.autogenerate.CollectView;
import cn.bupt.smartyagl.entity.autogenerate.Goods;
import cn.bupt.smartyagl.entity.autogenerate.GoodsList;
import cn.bupt.smartyagl.entity.autogenerate.OrderList;

/** 
 * 商品服务类接口
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-5-23 上午10:46:58 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public interface IGoodsService {
	
	public List<GoodsList> getGoodsList(GoodsList goods, Page page);//获取商品列表
	
	public List<GoodsList> getGoodsList(int status,int saleStatus,String productName);//获取商品列表（后台）@wlz
	
//	public GoodsList getGoodsDetail(Integer goodsId);//获取商品详情
	
	public boolean updateGoods(Goods goods);//修改商品状态
	
	public boolean addCollectGoods(Collect collect);//添加收藏商品
	
	public List<CollectView> getCollectGoodsList(CollectView CollectView);//获取收藏商品列表
	
	public boolean deleteCollectGoods(Integer collectId);//删除收藏商品
	
	public boolean deleteCollectGoodsBatch(String collectIds);//批量删除收藏商品
	
	public boolean isCollect(Integer goodId, Integer userId);//判断用户是否收藏该商品
	
	/**
	 * 根据产品状态计算商品价格
	 * @author jm
	 */
	public Double calculateGoodsPrice( Integer goodsId,OrderList or);
	
	/**
	 * 计算运费
	 * @author jm
	 * @param addressId
	 * @return
	 */
	public Double calculateFreightPrice(OrderList or);
	
	public int addGoods(Goods product);//添加商品 @waiting
	
	public boolean updateGoodsStatus(int status,int id);//修改商品状态 @waiting
	
	public boolean updateGoodsSaleStatus(int id,int saleStatus,Date endTime,Date openTime,Double specialprice);//修改商品销售状态 @waiting
	
	public GoodsListAndCollect convertGoods(GoodsList goods) throws IllegalArgumentException, IllegalAccessException;
	
	public boolean updateGoodsFavoriteRate(Integer goodsId,double rate);//修改商品的好评率
	/**
	 * 获取待审核的商品列表
	 * @return
	 * @author waiting
	 */
	public List<GoodsList> getGoodsLists();
	/**
	 * 修改商品审核状态
	 * @param id
	 * @return
	 * @author waiting
	 */
	public boolean updateAuditStatus(int id,int auditStatus,HttpServletRequest request);

	boolean deletePostGoods(Goods goods);

	boolean verifyAddGoods(Goods goods);

	boolean verifyGoodsStatus(Goods goods, Integer AuditStatus);

	boolean deleteGoods(Integer id);
	
//	int deleteGoods(Integer id,HttpServletRequest request);

	boolean verifyChangeGoods(Integer id);

//	GoodsList getGoodsCurrentDetail(Integer id);
	public GoodsList getGoodsDetail(Integer goodsId);
	
	public void updateGoodsBuyNum(int buyNum,int goodsId) ;
//	boolean verifyGoodsStatus(Integer id, Integer status);
	double calculateFreight(int addressId, String goodsIds,
			String nums);

	public Goods findGoods(Integer goodsId);

}
