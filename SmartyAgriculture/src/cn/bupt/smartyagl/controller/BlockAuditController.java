package cn.bupt.smartyagl.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.converter.Block2BlockModel;
import cn.bupt.smartyagl.entity.autogenerate.Block;
import cn.bupt.smartyagl.model.BlockModel;
import cn.bupt.smartyagl.service.IBlockService;

@Controller
@RequestMapping("/blockAudit")
public class BlockAuditController {

	@Autowired
	IBlockService blockService;
	
	int pageSize = Constants.PAGESIZE;// 每一页的大小
	
	/**
	 * 地块申请的列表
	 * @param allPages
	 * @param currentPage
	 * @param type
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/auditIndex/{allPages}/{currentPage}/{type}")
	public ModelAndView auditIndex(
			@PathVariable(value = "allPages") int allPages,
			@PathVariable(value = "currentPage") int currentPage,
			@PathVariable(value = "type") String type)
			throws UnsupportedEncodingException {
		if ("prvious".equals(type)) {
			if (currentPage > 1) {// 第一页不能往前翻页
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
		ModelAndView modelAndView = new ModelAndView(Constants.BLOCK_AUDIT_INDEX);
		Page page = PageHelper.startPage(currentPage, pageSize, "id");
		List<BlockModel> blockModels = blockService.getBlockAuditList();
		modelAndView.addObject("blockList", blockModels);
		// 总页数
		allPages = page.getPages();
		modelAndView.addObject("allPages", allPages);
		// 当前页码
		currentPage = page.getPageNum();
		modelAndView.addObject("currentPage", currentPage);
		return modelAndView;
	}
	/**
	 * 查看地块申请详情
	 * 
	 * @param id
	 * @return
	 * @author waiting
	 */
	@RequestMapping("/auditBlockDetail/{blockId}")
	public ModelAndView getProductDetail(@PathVariable(value = "blockId") int id,
			HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView(Constants.BLOCK_AUDIT_DETAIL);
		Block block = blockService.getBlockByBlockId(id);
		BlockModel blockModel = Block2BlockModel.converter(block);
		modelAndView.addObject("block", blockModel);
		
		return modelAndView;
	}
	
	/**
	 * 审核地块
	 * @param id
	 * @param auditStatus
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/updateBlockAuditStatus/{id}/{auditStatus}")
	public Map<String, String> updateAuditStatus(@PathVariable(value="id")int id,
			@PathVariable(value="auditStatus")int auditStatus){
		Map<String, String> resultMap=new HashMap<String, String>();
		boolean flag=blockService.updateAuditStatus(id, auditStatus);
		if(flag){
			resultMap.put("msg", "审核成功");
		}
		else{
			resultMap.put("msg", "审核失败");
		}
		return resultMap;
	}
}
