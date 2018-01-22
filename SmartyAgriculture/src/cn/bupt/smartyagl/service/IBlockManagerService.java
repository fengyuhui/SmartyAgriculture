package cn.bupt.smartyagl.service;

import java.util.List;

import cn.bupt.smartyagl.entity.autogenerate.Block;
import cn.bupt.smartyagl.entity.autogenerate.BlockManager;
import cn.bupt.smartyagl.model.BlockManagerModel;

public interface IBlockManagerService {

	public List<BlockManagerModel> getBlockManagerModelList();
	
	public List<BlockManager> getBlockManagerList();
	
	public Boolean addBlockManager(BlockManager blockManager);
	
	public Boolean updateBlockManager(BlockManager blockManager);
	
	public BlockManager getBlockManagerById(Integer id);
	
	public List<Block> getBlocksByManagerId(Integer id);
	
	public Boolean deleteBlockManagerById(Integer id);
	
	public BlockManager findBlockManagerByName(String name);
}
