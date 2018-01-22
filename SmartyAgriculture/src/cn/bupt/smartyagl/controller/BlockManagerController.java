package cn.bupt.smartyagl.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Model;
import javax.servlet.http.HttpServletRequest;
import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.constant.ConstantsSql;
import cn.bupt.smartyagl.converter.Block2BlockModel;
import cn.bupt.smartyagl.converter.BlockManager2BlockManagerModel;
import cn.bupt.smartyagl.entity.autogenerate.Block;
import cn.bupt.smartyagl.entity.autogenerate.BlockManager;
import cn.bupt.smartyagl.entity.autogenerate.Goods;
import cn.bupt.smartyagl.model.BlockManagerModel;
import cn.bupt.smartyagl.model.BlockModel;
import cn.bupt.smartyagl.service.IBlockManagerService;
import cn.bupt.smartyagl.util.MD5Util;

@Controller
@RequestMapping("/blockManager")
public class BlockManagerController {
	
	@Autowired
	IBlockManagerService blockManagerService;
	
	int pageSize=Constants.PAGESIZE;//，每一页的大小
    int pageSizeSmall=Constants.PAGESIZE;//分页，每一页数目比较少
    
    /**
     * 显示管理员列表
     * @author wlz
     */
    @RequestMapping(value="/index/{allPages}/{currentPage}/{type}")
    public ModelAndView blockManagerIndex(@PathVariable(value="allPages") int allPages,
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
        
        ModelAndView modelAndView=new ModelAndView(Constants.BLOCK_MANAGER_INDEX);
        
        Page page = PageHelper.startPage(currentPage, pageSize, "id");
        
        List<BlockManager> blockManagerList=blockManagerService.getBlockManagerList();
        
        modelAndView.addObject("blockManagerList",blockManagerList);
        //总页数
        allPages = page.getPages();
        modelAndView.addObject("allPages", allPages);
        // 当前页码
        currentPage = page.getPageNum();
        modelAndView.addObject("currentPage", currentPage);
        return modelAndView;
    }
    
    /**
     * 添加土地管理员(页面跳转)
     * 
     * @return
     */
    @RequestMapping("/addBlockManager")
    public ModelAndView addBlockManager(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(Constants.BLOCK_MANAGER_ADD);
        return modelAndView;
    }
    
    
    /**
     * 添加土地管理员(添加信息)
     */
    @RequestMapping("/addBlockManagerPost")
    public ModelAndView addBlockManagerPost(BlockManagerModel blockManagerModel) {
    		BlockManager blockManager = BlockManager2BlockManagerModel.converter(blockManagerModel);
    		blockManager.setPasswd(MD5Util.MD5(blockManager.getPasswd()));
        Boolean flag=blockManagerService.addBlockManager(blockManager);
        ModelAndView modelAndView = blockManagerIndex(0, 1, "prvious");
        if(flag) 
            return modelAndView;
        else { 
            JOptionPane.showMessageDialog(null, "添加失败 ", "添加失败", JOptionPane.ERROR_MESSAGE);
            return modelAndView;
        }
    }
    
    /**
     * 编辑土地管理员信息(页面跳转)
     */
    @RequestMapping(value="/edit/{blockManagerId}")
    public ModelAndView blockManagerEdit(@PathVariable(value="blockManagerId") Integer blockManagerId
            ) {
    		
    		ModelAndView modelAndView = new ModelAndView(Constants.BLOCK_MANAGER_EDIT);
    		BlockManager blockManager = blockManagerService.getBlockManagerById(blockManagerId);
    		BlockManagerModel blockManagerModel = BlockManager2BlockManagerModel.converter(blockManager);
    		modelAndView.addObject("blockManager", blockManagerModel);
    	
    		return modelAndView;
    	
    }
    
    /**
     * 编辑土地信息（编辑信息提交）
     */
    @RequestMapping(value="/editPost")
    public ModelAndView blockManagerEditPost(Integer id,
    									String password
            ) {
    		
    		BlockManager blockManager = blockManagerService.getBlockManagerById(id);
    		blockManager.setPasswd(password);
    		boolean flag = blockManagerService.updateBlockManager(blockManager);
    		ModelAndView modelAndView = blockManagerIndex(0, 1, "prvious");
            if(flag) 
                return modelAndView;
            else { 
                JOptionPane.showMessageDialog(null, "编辑失败 ", "编辑失败", JOptionPane.ERROR_MESSAGE);
                return modelAndView;
            }
    	
    }
    
    /**
     * 查看某一管理员的详细信息
     * @param blockManagerId
     * @return
     */
    @RequestMapping(value="/detail/{blockManagerId}")
    public ModelAndView blockManagerDetail(@PathVariable(value="blockManagerId") Integer blockManagerId) {
    	
    		ModelAndView modelAndView = new ModelAndView(Constants.BLOCK_MANAGER_DETAIL);
    		
    		BlockManager blockManager = blockManagerService.getBlockManagerById(blockManagerId);
    		List<Block> blocks = blockManagerService.getBlocksByManagerId(blockManagerId);
    		BlockManagerModel blockManagerModel = BlockManager2BlockManagerModel.converter(blockManager);
    		List<BlockModel> blockModels = new ArrayList<>();
    		for(int i = 0;i < blocks.size();i++) {
    			
    			BlockModel blockModel = Block2BlockModel.converter(blocks.get(i));
    			blockModels.add(blockModel);
    			
    		}
    		modelAndView.addObject("blockManager", blockManagerModel);
    		modelAndView.addObject("blocks", blockModels);
    		
    		return modelAndView;
    	
    }
    
    @RequestMapping("/delete/{blockManagerId}")
    public ModelAndView deleteRequest(@PathVariable(value="blockManagerId") Integer id){
        
        boolean rs = blockManagerService.deleteBlockManagerById(id);
        ModelAndView modelAndView = blockManagerIndex(0, 1, "prvious");
        if(rs){
        		modelAndView.addObject("resultMsg", "申请删除成功");
        }else{
        		modelAndView.addObject("resultMsg", "申请删除失败");
        }
      
        return modelAndView;
    }
	
}
