package cn.bupt.smartyagl.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.constant.ConstantsSql;
import cn.bupt.smartyagl.dao.autogenerate.GoodsMapper;
import cn.bupt.smartyagl.entity.autogenerate.Freight;
import cn.bupt.smartyagl.entity.autogenerate.Goods;
import cn.bupt.smartyagl.entity.autogenerate.GoodsList;
import cn.bupt.smartyagl.service.IBlockService;
import cn.bupt.smartyagl.service.IFreightService;
import cn.bupt.smartyagl.service.IGoodsService;
import cn.bupt.smartyagl.util.picture.JsonConvert;

@Controller
@RequestMapping("/productAudit")
public class ProductAuditController extends BaseController {
	
	@Autowired
	IGoodsService goodsService;
	@Autowired
	IFreightService freightService;
	@Autowired
	IBlockService blockService;
	@Autowired
	GoodsMapper goodsMapper;
	
	int pageSize = Constants.PAGESIZE;// 每一页的大小
	
	/**
	 * 商品审核列表
	 * @param allPages
	 * @param currentPage
	 * @param type
	 * @param status
	 * @param saleStatus
	 * @param productName
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
		ModelAndView modelAndView = new ModelAndView(Constants.PRODUCT_AUDIT_INDEX);
		Page page = PageHelper.startPage(currentPage, pageSize, "id");
		List<GoodsList> goodsList =goodsService.getGoodsLists();
		modelAndView.addObject("goodsList", goodsList);
		// 总页数
		allPages = page.getPages();
		modelAndView.addObject("allPages", allPages);
		// 当前页码
		currentPage = page.getPageNum();
		modelAndView.addObject("currentPage", currentPage);
		return modelAndView;
	}
	/**
	 * 查看商品详情
	 * 
	 * @param id
	 * @return
	 * @author waiting
	 */
	@RequestMapping("/getProductDetail/{id}")
	public ModelAndView getProductDetail(@PathVariable(value = "id") int id,
			HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView(Constants.PRODUCT_AUDIT_DETAIL);
		GoodsList goodsList = goodsService.getGoodsDetail(id);
		GoodsList yuan = goodsService.getGoodsDetail(goodsList.getSourceId());
		modelAndView.addObject("goodsList", goodsList);
		
		
		if(goodsList.getPicture()!=null){
			// 处理商品图片
			List<String> pictureList = new ArrayList<String>();
			try {
				pictureList = JsonConvert.getProductPicture(goodsList.getPicture(),request);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			modelAndView.addObject("pictureList", pictureList);
		}
		else {
			// 处理商品图片
			List<String> pictureList = new ArrayList<String>();
			try {
				pictureList = JsonConvert.getProductPicture(yuan.getPicture(),request);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			modelAndView.addObject("pictureList", pictureList);
		}
		// 销售状态
		int salestatus = goodsList.getSaleStatus();
		String saleString = null;
		switch (salestatus) {
		case ConstantsSql.SPECIALPRICE:
			saleString = "特价";
			break;
		case ConstantsSql.LIMITATIONTIME:
			saleString = "限时";
			break;
		case ConstantsSql.NEWGOODS:
			saleString = "新品";
			break;
		case ConstantsSql.HOTGOODS:
			saleString = "热卖";
			break;
		default:
			saleString = "新品";
			break;
		}
		// 地块名字
		String blockName = blockService.getBlockName(goodsList.getBlockId());
		// 商品状态
		int status = goodsList.getStatus();
		String statusString = null;
		switch (status) {
		case ConstantsSql.ONSALE:
			statusString = "在售";
			break;
		case ConstantsSql.OFFTHESHELVES:
			statusString = "下架";
			break;
		}
		//运费模板
	    GoodsList goods2=goodsService.getGoodsDetail(goodsList.getSourceId());
	    if(goods2!=null){
	    	List<Freight> freightList=freightService.freightListAudit(goods2.getId());
			modelAndView.addObject("freightList", freightList);
			int freightListLength=freightList.size();
			if(freightListLength!=0){
				modelAndView.addObject("flag", true);
			}
			else{
				modelAndView.addObject("flag", false);
			}
	    }
	
		modelAndView.addObject("saleStatus", saleString);
		modelAndView.addObject("blockName", blockName);
		modelAndView.addObject("status", statusString);
		modelAndView.addObject("goodsDetail", goodsList.getGoodsDetail());
		modelAndView.addObject("goodsList",goodsList);
		return modelAndView;
	}
	/**
	 * 审核商品
	 * @param id
	 * @param auditStatus
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/updateAuditStatus/{id}/{auditStatus}")
	public Map<String, String> updateAuditStatus(@PathVariable(value="id")int id,
			@PathVariable(value="auditStatus")int auditStatus,
			HttpServletRequest request){
		Map<String, String> resultMap=new HashMap<String, String>();
		boolean flag=goodsService.updateAuditStatus(id, auditStatus, request);
		if(flag){
			resultMap.put("msg", "审核成功");
		}
		else{
			resultMap.put("msg", "审核失败");
		}
		return resultMap;
	}
	
		@RequestMapping("/delete")
		public ModelAndView deleteGoods(HttpServletRequest request,Integer id){
			ModelAndView mv = new ModelAndView("");
			boolean rs = goodsService.deleteGoods(id);
			if(rs){
				mv.addObject("resultMsg", "删除成功");
	  	}else{
	  		mv.addObject("resultMsg", "删除失败");
	  	}
			RedirectView redirectView = new RedirectView( "auditIndex/0/1/prvious" );
			mv.setView(redirectView);
	  	return mv;
		}
		
		/*//审核发布
		@RequestMapping("/judgePublish")
		public ModelAndView judgePublish(HttpServletRequest request,goods goods){
			ModelAndView mv = new ModelAndView("");
			boolean rs = goodsService.verifyAddGoods(goods);
			if(rs){
				mv.addObject("resultMsg", "审核发布成功");
	  	}else{
	  		mv.addObject("resultMsg", "审核发布失败");
	  	}
			RedirectView redirectView = new RedirectView( "auditIndex/0/1/prvious" );
			mv.setView(redirectView);
	  	return mv;
		}*/
		
		/*//审核草稿
		@RequestMapping("/judgeDraft")
			public ModelAndView judgeDraft(HttpServletRequest request, Integer id){
				ModelAndView mv = new ModelAndView("");
				boolean rs = goodsService.verifyChangeGoods(id);
				if(rs){
					mv.addObject("resultMsg", "审核草稿成功");
		  	}else{
		  		mv.addObject("resultMsg", "审核草稿失败");
		  	}
				RedirectView redirectView = new RedirectView( "auditIndex/0/1/prvious" );
				mv.setView(redirectView);
		  	return mv;
			}*/
		

//	    /**
//	     * 提交删除申请，并非真的删除
//	     * @param request
//	     * @param id
//	     * @param topId
//	     * @return
//	     */
//	    @RequestMapping("/deleteRequest")
//	    public ModelAndView deleteRequest(HttpServletRequest request,Integer id){
//	        ModelAndView mv = new ModelAndView("");
//	        Map<String, String>map = new HashMap<String, String>();
//	        goods fa = new goods();
//	        fa.setId( id );
//	        fa.setAuditStatus( ConstantsSql.Audit_WaitDelete );
//	        boolean rs = goodsService.deletePostGoods(fa);
//	        if(rs){
//	            mv.addObject("resultMsg", "申请删除成功");
//	        }else{
//	            mv.addObject("resultMsg", "申请删除失败");
//	        }
//	        RedirectView redirectView = new RedirectView( "auditIndex/0/1/prvious" );
//	        mv.setView(redirectView);
//	        return mv;
//	    }
	    
	    /**
	     * 拒绝删除申请
	     * @param request
	     * @param id
	     * @param topId
	     * @return
	     */
	    @RequestMapping("/rebackDeleteRequest")
	    public ModelAndView rebackDeleteRequest(HttpServletRequest request,Integer id){
	        ModelAndView mv = new ModelAndView("");
	        Map<String, String>map = new HashMap<String, String>();
	        Goods fa = new Goods();
	        fa.setId( id );
	        fa.setAuditStatus( ConstantsSql.Audit_Finish );
	        boolean rs = goodsService.deletePostGoods(fa);
	        if(rs){
	            mv.addObject("resultMsg", "撤销删除成功");
	        }else{
	            mv.addObject("resultMsg", "撤销删除失败");
	        }
	        RedirectView redirectView = new RedirectView( "auditIndex/0/1/prvious" );
	        mv.setView(redirectView);
	        return mv;
	    }
	
}
