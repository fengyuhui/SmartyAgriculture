package cn.bupt.smartyagl.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bupt.smartyagl.converter.BlockManager2BlockManagerModel;
import cn.bupt.smartyagl.dao.autogenerate.BlockManagerMapper;
import cn.bupt.smartyagl.dao.autogenerate.BlockMapper;
import cn.bupt.smartyagl.entity.autogenerate.Block;
import cn.bupt.smartyagl.entity.autogenerate.BlockExample;
import cn.bupt.smartyagl.entity.autogenerate.BlockManager;
import cn.bupt.smartyagl.entity.autogenerate.BlockManagerExample;
import cn.bupt.smartyagl.entity.autogenerate.BlockManagerExample.Criteria;
import cn.bupt.smartyagl.model.BlockManagerModel;
import cn.bupt.smartyagl.service.IBlockManagerService;

@Service
public class BlockManagerServiceImpl implements IBlockManagerService {

	@Autowired
	BlockManagerMapper blockManagerMapper;
	
	@Autowired
	BlockMapper blockMapper;
	
	
	@Override
	public List<BlockManagerModel> getBlockManagerModelList() {
		BlockManagerExample blockManagerExample = new BlockManagerExample();
		List<BlockManager> blockManagers = blockManagerMapper.selectByExample(blockManagerExample);
		List<BlockManagerModel> blockManagerModels = new ArrayList<>();
		
		for (int i  = 0;i < blockManagers.size();i++) {
			
			BlockManager blockManager = blockManagers.get(i);
			BlockManagerModel blockManagerModel = BlockManager2BlockManagerModel.converter(blockManager);
			blockManagerModels.add(blockManagerModel);
			
		}
		
		return blockManagerModels;
	}

	@Override
	public Boolean addBlockManager(BlockManager blockManager) {
		int i = blockManagerMapper.insert(blockManager);
		
		 if(i!=0)
	            return true;
	        else
	            return false;
	}

	@Override
	public Boolean updateBlockManager(BlockManager blockManager) {
		int i = blockManagerMapper.updateByPrimaryKeySelective(blockManager);
		
		 if(i!=0)
	            return true;
	        else
	            return false;
	}

	@Override
	public BlockManager getBlockManagerById(Integer id) {
		BlockManager blockManager = blockManagerMapper.selectByPrimaryKey(id);
		return blockManager;
	}

	@Override
	public List<Block> getBlocksByManagerId(Integer id) {
		
		BlockExample blockExample = new BlockExample();
		cn.bupt.smartyagl.entity.autogenerate.BlockExample.Criteria criteria = blockExample.createCriteria();
		criteria.andManagerIdEqualTo(id);
		List<Block> blocks = blockMapper.selectByExample(blockExample);
		
		return blocks;
	}

	@Override
	public Boolean deleteBlockManagerById(Integer id) {
		
		int i = blockManagerMapper.deleteByPrimaryKey(id);
		BlockExample example = new BlockExample();
		cn.bupt.smartyagl.entity.autogenerate.BlockExample.Criteria criteria = example.createCriteria();
		criteria.andManagerIdEqualTo(id);
		List<Block> blocks = blockMapper.selectByExample(example);
		for(Block block : blocks) {
			block.setManagerId(0);
			blockMapper.updateByPrimaryKeySelective(block);
		}
		if(i!=0)
            return true;
        else
            return false;
	}

	@Override
	public BlockManager findBlockManagerByName(String name) {
		BlockManagerExample blockManagerExample = new BlockManagerExample();
		Criteria criteria = blockManagerExample.createCriteria();
		criteria.andNameEqualTo(name);
		List<BlockManager> blockManagers = blockManagerMapper.selectByExample(blockManagerExample);
		BlockManager blockManager = blockManagers.get(0);
		return blockManager;
	}

	@Override
	public List<BlockManager> getBlockManagerList() {
		BlockManagerExample blockManagerExample = new BlockManagerExample();
		List<BlockManager> blockManagers = blockManagerMapper.selectByExample(blockManagerExample);
		
		return blockManagers;
	}

}
