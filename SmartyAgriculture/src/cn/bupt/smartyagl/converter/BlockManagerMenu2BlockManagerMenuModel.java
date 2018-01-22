package cn.bupt.smartyagl.converter;

import cn.bupt.smartyagl.entity.autogenerate.BlockManagerMenu;
import cn.bupt.smartyagl.model.BlockManagerMenuModel;

public class BlockManagerMenu2BlockManagerMenuModel {
	
	public static BlockManagerMenuModel converter(BlockManagerMenu blockManagerMenu) {
		
		BlockManagerMenuModel blockManagerMenuModel = new BlockManagerMenuModel();
		
		blockManagerMenuModel.setId(blockManagerMenu.getId());
		blockManagerMenuModel.setParentid(blockManagerMenu.getId());
		blockManagerMenuModel.setController(blockManagerMenu.getController());
		blockManagerMenuModel.setMethod(blockManagerMenu.getMethod());
		blockManagerMenuModel.setName(blockManagerMenu.getName());
		
		return blockManagerMenuModel;
		
	}
	
}
