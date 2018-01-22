package cn.bupt.smartyagl.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import cn.bupt.smartyagl.entity.autogenerate.GeneratedCards;
import cn.bupt.smartyagl.entity.autogenerate.GeneratedCardsOper;
import cn.bupt.smartyagl.entity.autogenerate.GeneratedDetails;
import cn.bupt.smartyagl.entity.autogenerate.GoodsCard;
import cn.bupt.smartyagl.entity.autogenerate.GoodsCardLog;
import cn.bupt.smartyagl.entity.autogenerate.GoodsCardLogView;
import cn.bupt.smartyagl.entity.autogenerate.HasBuyRecordGoodsCard;
import cn.bupt.smartyagl.entity.autogenerate.OrderView;

/** 
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-9-2 下午3:21:27 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public interface IGoodsCardService {
	public void exportExcel(int id, HttpServletResponse response);
	public void generateCard(Double deno,int genId,int num);
	public GeneratedCards getGeneratedRec(int id);
	/**
	 * 根据id得到生成的张数
	 * @param id
	 * @return
	 */
	public int getCardNum(int id);
	
	void setReviewed(int id, boolean rev);
	public double getDeno(int id);
	/**
	 * 获取已审核的购物卡记录
	 * @return
	 */
	public List<GeneratedCardsOper> getReviewedRecords();
	/**
	 * 获取未审核的购物卡记录
	 * @return
	 */
	public List<GeneratedCardsOper> getUnReviewedRecords();
	/**
	 * 获取已审核的购物卡记录的详情
	 * @return
	 */
	public List<GeneratedDetails> getReviewedCardsDetails(int id);
	/**
	 * 将生成的购物卡记录插入到GeneratedCard表中
	 * s保存了记录的信息
	 * @param s
	 */
	public void saveGeneratedCards(GeneratedCards s);
	/**
	 * 根据面值和生成记录的主键genId作为外键来生成购物卡
	 * @param deno
	 * @param genId
	 */
	public void generateCard(Double deno,int genId);
	/**
	 * 生成购物卡,这里返回的密码是不带MD5加密的
	 * @return
	 */
	public GoodsCard  generateCode(Integer goodsId);
	
	/**
	 * 购物减额
	 * 返回false说明余额不足
	 */
	public Double reduceMoney(Integer cardNumber,Double money);
	
	/**
	 * 购物卡账号验证
	 * @param goodsCard
	 * @return
	 */
	public boolean valideCard(GoodsCard goodsCard);
	
	/**
	 * 整个通知逻辑，支付完成后直接调用这个方法完成购物卡生成及通知
	 * @param goodsCard
	 * @return
	 */
	public boolean allLogic(Integer orderId);
	
	/**
	 * 获取购物卡列表
	 * @param condition
	 * @return
	 */
	public List<GoodsCard>  getGoodsCardList(GoodsCard condition);
	
	/**
	 * 获取购物卡购买记录
	 * @param condition
	 * @return
	 */
	public List<GoodsCardLogView>  getGoodsCardLogList(GoodsCardLogView condition);
	
	/**
	 * 添加购物记录
	 */
	public boolean  addGoodsCartLog(GoodsCardLog condition);
	
	/**
	 * 获取购物卡详情
	 */
	public GoodsCard  getGoodsCart(Integer number,String passwd );
	
	/**
	 * 购物卡未完全支付，添加购物记录
	 */
	public boolean  addGoodsCartLogByAverage( List<OrderView> orderList , Double payMoney,Integer cardId );
	public List<HasBuyRecordGoodsCard> gethasBuyRecordGoodsCard();
}
