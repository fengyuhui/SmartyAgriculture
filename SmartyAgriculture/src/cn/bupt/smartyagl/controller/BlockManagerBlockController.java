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
import cn.bupt.smartyagl.dao.autogenerate.BlockMapper;
import cn.bupt.smartyagl.entity.autogenerate.Block;
import cn.bupt.smartyagl.entity.autogenerate.BlockManager;
import cn.bupt.smartyagl.model.BlockModel;
import cn.bupt.smartyagl.service.IBlockService;

/**
 * 区域管理员系统的地块管理模块
 * @author waekessi
 *
 */
@Controller
@RequestMapping("/blockManagerSystem/block")
public class BlockManagerBlockController {

	@Autowired
    IBlockService blockService;
    
    int pageSize=Constants.PAGESIZE;//，每一页的大小
    int pageSizeSmall=Constants.PAGESIZE;//分页，每一页数目比较少
    
    /**
     * 显示土地列表
     * @author wlz
     */
    @RequestMapping(value="/index/{allPages}/{currentPage}/{type}")
    public ModelAndView blockIndex(HttpServletRequest request,
    			@PathVariable(value="allPages") int allPages,
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
        
        ModelAndView modelAndView=new ModelAndView(Constants.BLOCK_MANAGER_SYSTEM_PUBLIC_MANAGEMENT);
        
        Page page = PageHelper.startPage(currentPage, pageSize, "id");
        BlockManager blockManager = (BlockManager) request.getSession().getAttribute(Constants.SESSION_BLOCK_MANAGER);
        int managerId = blockManager.getId();
        List<BlockModel> blockModelList=blockService.getBlockList(managerId);
        
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
     * 编辑土地信息(页面跳转)
     */
    @RequestMapping(value="/edit/{blockId}")
    public ModelAndView blockEdit(@PathVariable(value="blockId") Integer blockId
            ) {
    		
    		ModelAndView modelAndView = new ModelAndView(Constants.BLOCK_MANAGER_SYSTEM_PUBLIC_EDIT);
    		Block block = blockService.getBlockByBlockId(blockId);
    		BlockModel blockModel = Block2BlockModel.converter(block);
    		modelAndView.addObject("blockInfo", blockModel);
    	
    		return modelAndView;
    	
    }
    
    /**
     * 编辑土地信息（编辑信息提交）
     */
    @RequestMapping(value="/editPost")
    public ModelAndView blockEditPost(HttpServletRequest request,
    			 Integer blockId,
    			 Integer stock
            ) {
    		
    		Block block = blockService.getBlockByBlockId(blockId);
		block.setSourceId(block.getId());
		block.setId(null);
		block.setStock(stock);
		block.setAuditStatus(ConstantsSql.BLOCK_AUDIT_DRAFT);
    		
    		boolean flag = blockService.addBlocks(block);
    		ModelAndView modelAndView = blockIndex(request,0, 1, "prvious");
            if(flag) 
                return modelAndView;
            else { 
                JOptionPane.showMessageDialog(null, "编辑失败 ", "编辑失败", JOptionPane.ERROR_MESSAGE);
                return modelAndView;
            }
    	
    }
    
	
}
