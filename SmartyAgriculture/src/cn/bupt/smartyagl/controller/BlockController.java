package cn.bupt.smartyagl.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.constant.ConstantsSql;
import cn.bupt.smartyagl.converter.Block2BlockModel;
import cn.bupt.smartyagl.entity.autogenerate.Block;
import cn.bupt.smartyagl.entity.autogenerate.Goods;
import cn.bupt.smartyagl.model.BlockManagerModel;
import cn.bupt.smartyagl.model.BlockModel;
import cn.bupt.smartyagl.model.GoodsTypeModel;
import cn.bupt.smartyagl.service.IBlockManagerService;
import cn.bupt.smartyagl.service.IBlockService;
import cn.bupt.smartyagl.service.IGoodsService;
import cn.bupt.smartyagl.service.IGoodsTypeService;

@Controller
@RequestMapping("/block")
public class BlockController extends BaseController {
	
	@Autowired
	IGoodsTypeService goodsTypeService;
    @Autowired
    IBlockService blockService;
    @Autowired
    IBlockManagerService blockManagerService;
    @Autowired
    IGoodsService goodsService;
    
    int pageSize=Constants.PAGESIZE;//，每一页的大小
    int pageSizeSmall=Constants.PAGESIZE;//分页，每一页数目比较少
    
    /**
     * 显示土地列表
     * @author wlz
     */
    @RequestMapping(value="/index/{allPages}/{currentPage}/{type}")
    public ModelAndView blockIndex(@PathVariable(value="allPages") int allPages,
            @PathVariable(value="currentPage")int currentPage,
            @PathVariable(value="type") String type) {
        
        if ("prvious".equals(type)) {
            if( currentPage > 1 ){//第一页不能往前翻页
                currentPage--;
            }
        } else if ("next".equals(type)) {
            currentPage++;
        } else if ("first".equals(type)) {
            currentPage = 1;
        } else if ("last".equals(type)) {
            currentPage = allPages;
        } else {
            currentPage = Integer.parseInt(type);
        }
        
        ModelAndView modelAndView=new ModelAndView(Constants.BLOCK_INDEX);
        
        Page page = PageHelper.startPage(currentPage, pageSize, "id");
        
        List<BlockModel> blockModelList=blockService.getBlockList();
        
        modelAndView.addObject("blockList",blockModelList);
        //总页数
        allPages = page.getPages();
        modelAndView.addObject("allPages", allPages);
        // 当前页码
        currentPage = page.getPageNum();
        modelAndView.addObject("currentPage", currentPage);
        return modelAndView;
    }
    
    /**
     * 添加土地(页面跳转)
     * 
     * @return
     */
    @RequestMapping("/addblock")
    public ModelAndView addBlock(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        List<BlockManagerModel> blockManagerModels = blockManagerService.getBlockManagerModelList();
		modelAndView.addObject("blockManagerList", blockManagerModels);
		List<GoodsTypeModel> goodsTypeList = goodsTypeService.getDisplayGoodsType();
		modelAndView.addObject("goodsTypeList", goodsTypeList);;
        modelAndView.setViewName(Constants.BLOCK_ADD);
        return modelAndView;
    }
    
    
    /**
     * 添加土地(添加信息)
     */
    @RequestMapping("/addBlockPost")
    public ModelAndView addBlockPost(BlockModel blockModel) {
        Block block = Block2BlockModel.converter(blockModel);
        Boolean flag=blockService.addBlocks(block);
        ModelAndView modelAndView = blockIndex(0, 1, "prvious");
        if(flag) 
            return modelAndView;
        else { 
            JOptionPane.showMessageDialog(null, "添加失败 ", "添加失败", JOptionPane.ERROR_MESSAGE);
            return modelAndView;
        }
    }
    
    
    /**
     * 查看土地信息
     */
    @RequestMapping(value="/detail/{blockId}")
    public ModelAndView blockInfo(@PathVariable(value="blockId") Integer blockId){
        
        ModelAndView modelAndView =new ModelAndView(Constants.BLOCK_DETAIL);
        BlockModel blockModel=new BlockModel();
        Block block = blockService.getBlockByBlockId(blockId);
        blockModel = Block2BlockModel.converter(block);
        modelAndView.addObject("blockInfo", blockModel);
        return modelAndView;
        
        
    }
    
    /**
     * 编辑土地信息(页面跳转)
     */
    @RequestMapping(value="/edit/{blockId}")
    public ModelAndView blockEdit(@PathVariable(value="blockId") Integer blockId
            ) {
    		
    		ModelAndView modelAndView = new ModelAndView(Constants.BLOCK_EDIT);
    		Block block = blockService.getBlockByBlockId(blockId);
    		BlockModel blockModel = Block2BlockModel.converter(block);
    		modelAndView.addObject("blockInfo", blockModel);
    		Goods goods = goodsService.findGoods(block.getGoodId());
    		modelAndView.addObject("good", goods);
    		List<BlockManagerModel> blockManagerModels = blockManagerService.getBlockManagerModelList();
    		modelAndView.addObject("blockManagerList", blockManagerModels);
    		List<GoodsTypeModel> goodsTypeList = goodsTypeService.getDisplayGoodsType();
    		modelAndView.addObject("goodsTypeList", goodsTypeList);;
    	
    		return modelAndView;
    	
    }
    
    /**
     * 编辑土地信息（编辑信息提交）
     */
    @RequestMapping(value="/editPost")
    public ModelAndView blockEditPost(BlockModel blockModel
            ) {
    		
    		Block block = Block2BlockModel.converter(blockModel);
    		block.setSourceId(block.getId());
    		block.setId(null);
    		block.setAuditStatus(ConstantsSql.BLOCK_AUDIT_DRAFT);
    		
    		boolean flag = blockService.addBlocks(block);
    		ModelAndView modelAndView = blockIndex(0, 1, "prvious");
            if(flag) 
                return modelAndView;
            else { 
                JOptionPane.showMessageDialog(null, "编辑失败 ", "编辑失败", JOptionPane.ERROR_MESSAGE);
                return modelAndView;
            }
    	
    }
    
    @RequestMapping(value="/delete/{blockId}")
    public ModelAndView blockDelete(@PathVariable(value="blockId") Integer blockId) {
    	
    		boolean flag = blockService.deleteBlockById(blockId);
    		
    		ModelAndView modelAndView = blockIndex(0,1,"prvious"); 
    		
    		if(flag) 
                return modelAndView;
            else { 
                JOptionPane.showMessageDialog(null, "删除失败 ", "删除失败", JOptionPane.ERROR_MESSAGE);
                return modelAndView;
            }
    	
    }

    
}
