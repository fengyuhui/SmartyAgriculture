package cn.bupt.smartyagl.model;

import java.util.List;

import cn.bupt.smartyagl.entity.autogenerate.Goods;
import cn.bupt.smartyagl.entity.autogenerate.GoodsType;
/**
 * 
 *<p>Title:GoodsTypeModel</p>
 *<p>Description:</p>
 * @author waiting
 *@date 2016年7月5日 上午9:47:02
 */
public class GoodsTypeModel extends GoodsType {
	
	List<GoodsTypeModel> subGoodsTypeList;
	List<Goods> goodsList;
	String parentName;

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public List<GoodsTypeModel> getSubGoodsTypeList() {
		return subGoodsTypeList;
	}

	public void setSubGoodsTypeList(List<GoodsTypeModel> subGoodsTypeList) {
		this.subGoodsTypeList = subGoodsTypeList;
	}

	public List<Goods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<Goods> goodsList) {
		this.goodsList = goodsList;
	}
	
}
