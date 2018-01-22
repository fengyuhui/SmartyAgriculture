package cn.bupt.smartyagl.model;

import java.util.List;

import cn.bupt.smartyagl.entity.autogenerate.BlockManagerMenu;

public class BlockManagerMenuModel extends BlockManagerMenu {
	
	List<BlockManagerMenuModel> subBlockManagerMenus;
	
	public List<BlockManagerMenuModel> getSubMenus() {
		return subBlockManagerMenus;
	}

	public void setSubMenus(List<BlockManagerMenuModel> subBlockManagerMenus) {
		this.subBlockManagerMenus = subBlockManagerMenus;
	}
	
}
