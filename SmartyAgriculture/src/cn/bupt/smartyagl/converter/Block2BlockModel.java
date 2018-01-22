package cn.bupt.smartyagl.converter;

import java.util.Date;

import cn.bupt.smartyagl.dao.autogenerate.BlockManagerMapper;
import cn.bupt.smartyagl.dao.autogenerate.BlockMapper;
import cn.bupt.smartyagl.dao.autogenerate.GoodsMapper;
import cn.bupt.smartyagl.entity.autogenerate.Block;
import cn.bupt.smartyagl.entity.autogenerate.BlockManager;
import cn.bupt.smartyagl.entity.autogenerate.Goods;
import cn.bupt.smartyagl.model.BlockModel;
import cn.bupt.smartyagl.util.SpringContextUtil;

public class Block2BlockModel {
	
	public static BlockModel converter(Block block) {
		BlockModel blockModel = new BlockModel();
		blockModel.setBlockId(block.getId());
		blockModel.setManagerId(block.getManagerId());
		GoodsMapper goodsMapper = (GoodsMapper) SpringContextUtil.getBean("goodsMapper");
		Goods goods = goodsMapper.selectByPrimaryKey(block.getGoodId());
		if(goods == null) {
			blockModel.setGoodName("空");
		}
		else{
			blockModel.setGoodName(goods.getName());
		}
		BlockManagerMapper blockManagerMapper = (BlockManagerMapper) SpringContextUtil.getBean("blockManagerMapper");

		BlockManager blockManager = blockManagerMapper.selectByPrimaryKey(block.getManagerId());
		if(blockManager == null) {
			blockModel.setManagerName("无");
		}
		else {
			blockModel.setManagerId(block.getManagerId());
			blockModel.setManagerName(blockManager.getName());
		}
		blockModel.setStock(block.getStock());
		blockModel.setCreateDate(block.getCreateTime());
		blockModel.setBlockName(block.getName());
		blockModel.setDescription(block.getDescription());
		blockModel.setStatus(block.getStatus());
		blockModel.setAuditStatus(block.getAuditStatus());
		
		return blockModel;
	}
	
	public static Block converter(BlockModel blockModel) {
		
		BlockMapper blockMapper = (BlockMapper) SpringContextUtil.getBean("blockMapper");
		Block block = blockMapper.selectByPrimaryKey(blockModel.getBlockId());
		//若增加地块，则传不过来block的id，需新建
		if(blockModel.getBlockId() == null) {
			block = new Block();
			block.setCreateTime(new Date());
		}
		block.setStock(blockModel.getStock());
		block.setName(blockModel.getBlockName());
		block.setDescription(blockModel.getDescription());
		block.setGoodId(blockModel.getGoodId());
		block.setManagerId(blockModel.getManagerId());
		block.setStatus(blockModel.getStatus());
		block.setAuditStatus(blockModel.getAuditStatus());
		
		return block;
		
	}
}
