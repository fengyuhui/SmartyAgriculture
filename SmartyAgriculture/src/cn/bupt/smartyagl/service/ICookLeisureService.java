package cn.bupt.smartyagl.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.alipay.api.domain.Data;
import com.github.pagehelper.Page;









import cn.bupt.smartyagl.entity.CookLeisureAndHtml;
import cn.bupt.smartyagl.entity.autogenerate.CookLeisure;
import cn.bupt.smartyagl.model.CookLeisureModel;

/** 
 * 生态技术信息接口
 * @author  zxy
 * @date 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */

public interface ICookLeisureService {
	
	/**
	 * 获取生态技术信息列表
	 * @return
	 */
	List<CookLeisure> getCookLeisureList(CookLeisure cookLeisureSelective);
	/**
	 * 通过CookLeisureId获取CookLeisurename
	 */
	public String getMessageName(int id);
	
	/**
	 * 添加
	 */
//	public Boolean addCookLeisures(CookLeisure cookLeisure);
	
	/**
	 * 通过
	 */
	public CookLeisure getCookLeisureById(int id);
	/**
	 * 通过CookLeisureId获取ManagerView
	 */
//	public CookLeisure getCookLeisureDetail(Integer id);//查看信息详情	
	
	List<CookLeisure> getCookLeisureDraftList();//获取信息审核列表
	
//	CookLeisure updateCookLeisure(Integer id);//修改农场信息发布草稿
	
	boolean updateCookLeisure(CookLeisure cookLeisure);//修改农场信息发布草稿
	
	boolean deleteCookLeisure(Integer cookLeisureId);//删除信息
	
	boolean verifyAddCookLeisure(Integer cookLeisureId);//审核发布
	
	boolean verifyChangeCookLeisure(Integer cookLeisureId);//审核草稿
	
	boolean changeTop(Integer cookLeisureId, Integer topId);//设置置顶
	
	boolean verifyCookLeisureStatus(Integer cookLeisureId, Integer status);//审核信息状态
	
	CookLeisure findCookLeisure(Integer cookLeisureId);//查看信息
	
	/**
	 * 根据类型，和数量获得对应部分商品信息(前台接口用）
	 * @author wjm
	 */
	public List<CookLeisure> getCookLeisureList( Integer typeId,Integer pageSize ,Integer pageNum ,Map<String,Object> m);
	
	boolean deletePostCookLeisure(CookLeisure fa);
	
	boolean updateAuditStatus(int id, int status, HttpServletRequest request);
	
//	Boolean addCookLeisures(int id);
	
	Boolean addCookLeisures(CookLeisure cookLeisure) throws IllegalArgumentException, IllegalAccessException;
	
//	CookLeisure getCurrentMessage(Integer id);
	
	CookLeisure getCookLeisureDetail(Integer id);
	
	CookLeisureAndHtml convertMessage(CookLeisure cookLeisure)
			throws IllegalArgumentException, IllegalAccessException;
	
}
