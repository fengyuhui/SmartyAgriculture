package cn.bupt.smartyagl.model;

import java.util.List;

import cn.bupt.smartyagl.entity.autogenerate.Menu;
/**
 * 
 *<p>Title:MenuModel</p>
 *<p>Description:</p>
 * @author waiting
 *@date 2016年7月5日 上午9:46:56
 */
public class MenuModel extends Menu {
   
	List<MenuModel> subMenus;

	public List<MenuModel> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(List<MenuModel> subMenus) {
		this.subMenus = subMenus;
	}

	
	
}
