package cn.bupt.smartyagl.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bupt.smartyagl.constant.ConstantsSql;
import cn.bupt.smartyagl.converter.Block2BlockModel;
import cn.bupt.smartyagl.dao.autogenerate.BlockManagerMapper;
import cn.bupt.smartyagl.dao.autogenerate.BlockManagerViewMapper;
import cn.bupt.smartyagl.dao.autogenerate.BlockMapper;
import cn.bupt.smartyagl.dao.autogenerate.GoodsMapper;
import cn.bupt.smartyagl.entity.autogenerate.BlockManagerView;
import cn.bupt.smartyagl.entity.autogenerate.BlockManagerViewExample;
import cn.bupt.smartyagl.entity.autogenerate.BlockManagerViewExample.Criteria;
import cn.bupt.smartyagl.entity.autogenerate.Goods;
import cn.bupt.smartyagl.entity.autogenerate.GoodsExample;
import cn.bupt.smartyagl.entity.autogenerate.Block;
import cn.bupt.smartyagl.entity.autogenerate.BlockExample;
import cn.bupt.smartyagl.entity.autogenerate.BlockManager;
import cn.bupt.smartyagl.entity.autogenerate.BlockManagerExample;
import cn.bupt.smartyagl.model.BlockModel;
import cn.bupt.smartyagl.service.IBlockService;
import cn.bupt.smartyagl.util.DateTag;

@Service
public class BlockServiceImpl implements IBlockService {
	
	@Autowired
	BlockMapper blockMapper;
	@Autowired
	BlockManagerViewMapper blockManagerViewMapper;
	@Autowired
	GoodsMapper goodsMapper;

	@Override
	public List<BlockModel> getBlockList() {
		// TODO Auto-generated method stub
		BlockExample bme=new BlockExample();
		List<Block> blockList=blockMapper.selectByExample(bme);
		
		List<BlockModel> blockModelList = new ArrayList<BlockModel>();
		
		for (int i = 0; i < blockList.size(); i++) {
		    Block tempblock = blockList.get(i);
		    
		    BlockModel tempblockModel = Block2BlockModel.converter(tempblock);
  		    
  		    blockModelList.add(tempblockModel);
		}
		return blockModelList;
	}
    /**
     * 通过blockId获取blockname
     */
	@Override
	public String getBlockName(int blockId) {
		// TODO Auto-generated method stub
		BlockExample bme=new BlockExample();
		
		cn.bupt.smartyagl.entity.autogenerate.BlockExample.Criteria criteria=bme.createCriteria();
		criteria.andIdEqualTo(blockId);
		
		Block block=blockMapper.selectByPrimaryKey(blockId);
		if(block==null){
			return null;
		}
		return block.getName();
	}
    @Override
    public Boolean addBlocks(Block block) {
        int i =blockMapper.insert(block);
        try {
        		if(block.getGoodId() != null) {
		        Goods goods = goodsMapper.selectByPrimaryKey(block.getGoodId());
		        if(block.getStock() != null) {
			        goods.setStock(block.getStock());
			        goodsMapper.updateByPrimaryKeySelective(goods);
		        }
        		}
	        
        } catch (Exception e) {
        	e.printStackTrace();
			return false;
		}
        if(i!=0)
            return true;
        else
            return false;
            
    }
    @Override
    public Block getBlockByBlockId(int blockId) {
    	Block block =blockMapper.selectByPrimaryKey(blockId);
        return block;
    }
    @Override
    public List<BlockManagerView> getBlockManagerView(int blockId) {
        BlockManagerViewExample bmve=new BlockManagerViewExample();
        List<BlockManagerView> blockManagerViewList = new ArrayList<BlockManagerView>();
        List<BlockManagerView> blockManagerViewList2 = blockManagerViewMapper.selectByExample(bmve);
        for (BlockManagerView blockManagerView : blockManagerViewList2) {
            if (blockManagerView.getBlockId()==blockId) {
                blockManagerViewList.add(blockManagerView);
            }
        }
        return blockManagerViewList;
    }
	@Override
	public Boolean updateBlock(Block block) {
		int i = blockMapper.updateByPrimaryKeySelective(block);
        try {
        	
	        Goods goods = goodsMapper.selectByPrimaryKey(block.getGoodId());
	        goods.setStock(block.getStock());
	        goodsMapper.updateByPrimaryKey(goods);
	        
        } catch (Exception e) {
        	e.printStackTrace();
			return false;
		}
        if(i!=0)
            return true;
        else
            return false;
	}
	@Override
	public List<BlockModel> getBlockList(int managerId) {
		BlockExample blockExample = new BlockExample();
		cn.bupt.smartyagl.entity.autogenerate.BlockExample.Criteria criteria = blockExample.createCriteria();
		criteria.andManagerIdEqualTo(managerId);
		List<Block> blockList = blockMapper.selectByExample(blockExample);
		List<BlockModel> blockModels = new ArrayList<>();
		for(Block block : blockList) {
			
			BlockModel blockModel = new BlockModel();
			blockModel = Block2BlockModel.converter(block);
			blockModels.add(blockModel);
			
		}
		return blockModels;
	}
	@Override
	public List<BlockModel> getBlockAuditList() {
		BlockExample blockExample = new BlockExample();
		cn.bupt.smartyagl.entity.autogenerate.BlockExample.Criteria criteria = blockExample.createCriteria();
		criteria.andAuditStatusEqualTo(1);
		List<Block> blockList = blockMapper.selectByExample(blockExample);
		List<BlockModel> blockModels = new ArrayList<>();
		for(Block block : blockList) {
			
			BlockModel blockModel = new BlockModel();
			blockModel = Block2BlockModel.converter(block);
			blockModels.add(blockModel);
			
		}
		return blockModels;
	}
	
	@Override
	public boolean updateAuditStatus(int id, int auditStatus) {
		Block block = blockMapper.selectByPrimaryKey(id);
		if(block.getAuditStatus() == ConstantsSql.BLOCK_AUDIT_DRAFT) {
			
			return this.updateAuditEdit(id,auditStatus);
			
		}
		return false;
	}
	
	private boolean updateAuditEdit(int id,int status) {
		if(status == 1) {
			
			Block block = blockMapper.selectByPrimaryKey(id);
			Block blockSrc = blockMapper.selectByPrimaryKey(block.getSourceId());
			blockSrc.setDescription(block.getDescription());
			blockSrc.setGoodId(block.getGoodId());
			blockSrc.setManagerId(block.getManagerId());
			blockSrc.setName(block.getName());
			blockSrc.setSourceId(0);
			blockSrc.setStock(block.getStock());
			blockSrc.setStatus(block.getStatus());
			
			blockMapper.updateByPrimaryKeySelective(blockSrc);
		}
		int i = blockMapper.deleteByPrimaryKey(id);
		
		if(i==1){
			return true;
		}
		else{
			return false;
		}
	}
	@Override
	public boolean deleteBlockById(Integer blockId) {
		
		int i = blockMapper.deleteByPrimaryKey(blockId);
		GoodsExample example = new GoodsExample();
		cn.bupt.smartyagl.entity.autogenerate.GoodsExample.Criteria criteria = example.createCriteria();
		criteria.andBlockIdEqualTo(blockId);
		List<Goods> goods = goodsMapper.selectByExample(example);
		for(Goods item : goods) {
			item.setBlockId(null);
			goodsMapper.updateByPrimaryKey(item);
		}
		if(i==1){
			return true;
		}
		else{
			return false;
		}
	}
}
