package cn.bupt.smartyagl.entity;
/** 
 * 商品一级分类
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-6-21 下午2:49:35 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public class GoodsTypeParent {
	private String typeParent;//分类名称 如水果
	private Integer typeIdParent;//分类id 
	
	public String getTypeParent() {
		return typeParent;
	}
	public void setTypeParent(String typeParent) {
		this.typeParent = typeParent;
	}
	public Integer getTypeIdParent() {
		return typeIdParent;
	}
	public void setTypeIdParent(Integer typeIdParent) {
		this.typeIdParent = typeIdParent;
	}
		
	
}
