package cn.bupt.smartyagl.entity;

import java.util.List;

import cn.bupt.smartyagl.entity.autogenerate.GoodsType;

/** 
 * 商品分类 带子分类
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-6-1 下午3:14:08 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public class GoodsTypeList extends GoodsType{
	private List<GoodsTypeList> childList;
//	private String picture;

	public List<GoodsTypeList> getChildList() {
		return childList;
	}

	public void setChildList(List<GoodsTypeList> childList) {
		this.childList = childList;
	}
	
}
