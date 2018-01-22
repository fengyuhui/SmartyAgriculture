package cn.bupt.smartyagl.converter;

import java.util.Date;

import cn.bupt.smartyagl.entity.autogenerate.BlockManager;
import cn.bupt.smartyagl.model.BlockManagerModel;
import cn.bupt.smartyagl.util.DateTag;

public class BlockManager2BlockManagerModel {

	
	public static BlockManagerModel converter (BlockManager blockManager) {
		
		BlockManagerModel blockManagerModel = new BlockManagerModel();
		
		blockManagerModel.setId(blockManager.getId());
		blockManagerModel.setName(blockManager.getName());
		blockManagerModel.setCreateTime(DateTag.dateTimaFormat(blockManager.getCreateTime()));
		
		return blockManagerModel;
	}

	public static BlockManager converter(BlockManagerModel blockManagerModel) {
		BlockManager blockManager = new BlockManager();
		blockManager.setName(blockManagerModel.getName());
		blockManager.setPasswd(blockManagerModel.getPassword());
		blockManager.setCreateTime(new Date());
		return blockManager;
	}
	
}
