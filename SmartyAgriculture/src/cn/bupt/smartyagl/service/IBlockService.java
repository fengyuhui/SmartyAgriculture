package cn.bupt.smartyagl.service;

import java.util.List;

import cn.bupt.smartyagl.entity.autogenerate.Block;
import cn.bupt.smartyagl.entity.autogenerate.BlockManagerView;
import cn.bupt.smartyagl.model.BlockModel;

/**
 * 
 *<p>Title:IBlockService</p>
 *<p>Description:地块服务类</p>
 * @author waiting
 *@date 2016年6月2日 上午11:16:21
 */
public interface IBlockService {
  
	/**
	 * 获取地块列表
	 * @return
	 */
	public List<BlockModel> getBlockList();
	/**
	 * 通过managerId获取地块列表
	 * @return
	 */
	public List<BlockModel> getBlockList(int managerId);
	/**
	 * 通过blockId获取blockname
	 */
	public String getBlockName(int blockId);
	
	/**
	 * 添加土地
	 */
	public Boolean addBlocks(Block Block);
	
	/**
	 * 通过blockId获取block
	 */
	public Block getBlockByBlockId(int blockId);
	/**
	 * 通过blockId获取blockManagerView
	 */
	public List<BlockManagerView> getBlockManagerView(int blockId);     
	
	public Boolean updateBlock(Block block);
	
	public List<BlockModel> getBlockAuditList();
	
	public boolean updateAuditStatus(int id, int auditStatus);
	public boolean deleteBlockById(Integer blockId);
    
}
