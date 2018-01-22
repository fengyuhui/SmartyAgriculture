package cn.bupt.smartyagl.service;

import cn.bupt.smartyagl.entity.autogenerate.PayLog;

/** 
 * 支付记录
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-7-14 下午3:49:46 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public interface IPayLogService {

	/**
	 * 添加支付记录
	 */
	public boolean addPayLog( PayLog payLog );
	
	/**
	 * 修改支付记录
	 */
	public boolean updatePayLog( PayLog payLog );
	
	/**
	 * 将商户号对应订单变为付款状态
	 */
	public boolean updateOrderByTradeNo(String tradeNO);
	
	/**
	 * 判断订单是否存在
	 */
	public boolean isExistTradeNo(String tradeNo);
	
	/*
	 * 获取支付状态
	 * @return 0：未支付，1：新建支付记录，2：其他
	 */
	public int getTradeOrderStauts(String tradeNo);
}
