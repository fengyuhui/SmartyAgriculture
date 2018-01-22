package cn.bupt.smartyagl.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alipay.api.domain.Data;
import com.github.pagehelper.Page;





import cn.bupt.smartyagl.entity.FarmMessageAndHtml;
import cn.bupt.smartyagl.entity.autogenerate.FarmMessage;
import cn.bupt.smartyagl.model.FarmMessageModel;

/** 
 * 生态技术信息接口
 * @author  zxy
 * @date 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */

public interface IFarmMessageService {
	
	/**
	 * 获取生态技术信息列表
	 * @return
	 */
	List<FarmMessage> getFarmMessageList(FarmMessage farmMessageSelective);
	/**
	 * 通过FarmMessageId获取FarmMessagename
	 */
//	public String getTitle(int id);
	
	/**
	 * 添加
	 */
	public Boolean addFarmMessages(FarmMessage farmMessage);
	
	/**
	 * 通过
	 */
	public FarmMessage getFarmMessageById(int id);
	/**
	 * 通过FarmMessageId获取ManagerView
	 */
	public FarmMessage getFarmMessageDetail(Integer id);//查看信息详情	
	
	List<FarmMessage> getFarmMessageDraftList();//获取信息审核列表
	
	FarmMessage updateFarmMessage(Integer id);//修改农场信息发布草稿
	
	boolean updateFarmMessage(FarmMessage farmMessage);//修改农场信息发布草稿
	
	boolean deleteFarmMessage(Integer farmMessageId);//删除信息
	
	boolean verifyAddFarmMessage(Integer farmMessageId);//审核发布
	
	boolean verifyChangeFarmMessage(Integer farmMessageId);//审核草稿
	
	boolean changeTop(Integer farmMessageId, Integer topId);//设置置顶
	
	boolean verifyFarmMessageStatus(Integer farmMessageId, Integer status);//审核信息状态
	
	FarmMessage findFarmMessage(Integer farmMessageId);//查看信息
	
	/**
	 * 根据类型，和数量获得对应部分商品信息(前台接口用）
	 * @author wjm
	 */
	public List<FarmMessage> getFarmMessageList( Integer typeId,Integer pageSize ,Integer pageNum );
    boolean deletePostFarmMessage(FarmMessage farmMessage);
	String getMessageName(int id);

	FarmMessageAndHtml convertMessage(FarmMessage farmMessage)
			throws IllegalArgumentException, IllegalAccessException;
	
}
