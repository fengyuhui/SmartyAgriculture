package cn.bupt.smartyagl.service;

import java.util.List;

import cn.bupt.smartyagl.model.BlockManagerMenuModel;

public interface IBlockManagerMenuService {
	
	/**
	 * 获取显示菜单
	 * @return
	 */
	public List<BlockManagerMenuModel> getDisplayMenu();
	/**
	 * 获取子菜单
	 * parentId 
	 */
	public  List<BlockManagerMenuModel> getSubMenu(short parentId);

}
