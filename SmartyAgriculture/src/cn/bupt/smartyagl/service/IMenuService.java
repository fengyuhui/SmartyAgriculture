package cn.bupt.smartyagl.service;

import java.util.List;

import cn.bupt.smartyagl.model.MenuModel;

public interface IMenuService {
	/**
	 * 获取显示菜单
	 * @return
	 */
	public List<MenuModel> getDisplayMenu();
	/**
	 * 获取子菜单
	 * parentId 
	 */
	public  List<MenuModel> getSubMenu(short parentId);
}
