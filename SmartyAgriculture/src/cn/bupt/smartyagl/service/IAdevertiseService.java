package cn.bupt.smartyagl.service;

import java.util.List;

import cn.bupt.smartyagl.entity.autogenerate.Advertise;

/** 
 * 广告列表
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-6-1 上午11:22:01 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public interface IAdevertiseService {
	/**
	 * 获得指定广告
	 * @param advertise
	 * @return
	 */
	public Advertise findAdvertise(Integer advertiseId);
	
	/**
	 * 添加广告草稿
	 * @param advertise
	 * @return
	 */
	public boolean addAdvertise(Advertise advertise);
	
	/**
	 * 更改广告草稿
	 * @param advertise
	 * @return
	 */
	public boolean updateAdvertise(Advertise advertise);
	
	/**
	 * 删除广告
	 * @param advertiseId
	 * @return
	 */
	public boolean deleteAdvertise(Integer advertiseId);
	
	/**
	 * 审核添加广告
	 * @param advertise
	 * @return
	 */
	public boolean verifyAddAdvertise(Integer advertiseId);
	
	/**
	 * 审核修改广告
	 * @param advertise
	 * @return
	 */
	public boolean verifyChangeAdvertise(Integer advertiseId);
	
	/**
	 * 获取广告列表（已审核）
	 * @return
	 */
	public List<Advertise> getAdvertiseList();
	
	/**
	 * 获取广告列表（待审核）
	 * @return
	 */
	public List<Advertise> getAdvertiseDraftList();
	
	/**
	 * 更改广告置顶状态
	 * @return
	 */
	public boolean changeTop(Integer advertiseId,Integer status);
	
	/**
	 * 更新广告状态
	 */
	public boolean verifyAdvertiseStatus(Integer advertiseId,Integer top);

	boolean deletePostAdvertise(Advertise advertise);
}
