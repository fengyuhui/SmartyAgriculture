package cn.bupt.smartyagl.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import cn.bupt.smartyagl.entity.GoodsTypeList;
import cn.bupt.smartyagl.entity.autogenerate.Goods;
import cn.bupt.smartyagl.entity.autogenerate.GoodsType;
import cn.bupt.smartyagl.model.GoodsTypeModel;

/**
 * 
 *<p>Title:IGoodsType</p>
 *<p>Description:商品类型服务类</p>
 * @author waiting
 *@date 2016年6月2日 上午9:34:47
 */
public interface IGoodsTypeService {
	public void unDo(int id);
	public void setReviewed(int id);
	public void removeById(int id);
	public List<GoodsTypeModel> getUnreviewedTypes();
	/**********前台调用*************/
	/**
	 * 获得商品分类列表 前台调用
	 * @return
	 */
	public List<GoodsTypeList> getGoodsTypeList(HttpServletRequest request);
	
	/**
	 * 获得父类商品分类列表 前台调用
	 */
	public List<GoodsTypeList> getPartentTypeList();
	
	/**
	 * 获得二级商品分类列表 前台调用
	 */
	public List<GoodsTypeList> getChildTypeList(Integer partentId);
	
	/*********后台调用**************/
	/**
	 * 获取显示菜单 后台调用
	 * 一级菜单
	 * @author waiting
	 * @return
	 */
	public List<GoodsTypeModel> getDisplayGoodsType();
	/**
	 * 获取子菜单 后台调用
	 * parentId 
	 * @author waiting
	 */
	public  List<GoodsTypeModel> getSubType(Integer parentId);
	/**
	 * 根据两级类别获取商品 后台调用
	 * @param subId
	 * @param parentId
	 * @return
	 */
	public List<Goods> getGoodsList(Integer subId,Integer parentId);
	/**
	 * 获取商品类型列表
	 * @param level
	 * @param pageNumber
	 * @param pageSize
	 * @return 
	 * @author waiting
	 */
	public ModelAndView getTypeList(Integer level,Integer parentId, int pageNumber,int pageSize,ModelAndView modelAndView);
    /**
     * 添加商品类型
     * @param goodsType
     * @return
     * @author waiting
     */
	public boolean addType(GoodsType goodsType);
    /**
     * 获取商品类型
     * @author waiting
     */
    public GoodsType getGoodsTypeById(int id);
    /**
     * 更新商品类型
     * @param goodsType
     * @return
     * @author waiting
     */
    public boolean updateGoodsType(GoodsType goodsType);
    
    /**
     * 修改商品类型是否显示的状态位
     * @param isdsiplay
     * @param typeId
     * @return
     * @author waiting
     */
    public boolean updateGoodsISdisplay(boolean isdisplay,int typeId);
	
	
	
}
