package cn.bupt.smartyagl.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bouncycastle.ocsp.Req;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.entity.autogenerate.AdminUser;
import cn.bupt.smartyagl.entity.autogenerate.GeneratedCards;
import cn.bupt.smartyagl.entity.autogenerate.GeneratedCardsOper;
import cn.bupt.smartyagl.entity.autogenerate.GeneratedDetails;
import cn.bupt.smartyagl.entity.autogenerate.GoodsCard;
import cn.bupt.smartyagl.entity.autogenerate.GoodsCardLogView;
import cn.bupt.smartyagl.entity.autogenerate.HasBuyRecordGoodsCard;
import cn.bupt.smartyagl.service.IGoodsCardService;
import cn.jpush.api.common.connection.IHttpClient.RequestMethod;

/** 
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-9-7 上午11:23:07 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
@Controller
@RequestMapping("/goodsCard")
public class GoodsCardBackController extends BaseController{
	
	@Autowired
	IGoodsCardService goodsCardService;
	
	int pageSize=Constants.PAGESIZE;//，每一页的大小
	int pageSizeSmall=Constants.PAGESIZE;//分页，每一页数目比较少
	
	@RequestMapping(value="/index/{allPages}/{currentPage}/{pageType}")
	public ModelAndView getCardList(HttpServletRequest request,@PathVariable(value="allPages") int allPages,
            @PathVariable(value="currentPage")int currentPage,
            @PathVariable(value="pageType") String types){
		 	if ("prvious".equals(types)) {
	            if( currentPage > 1 ){//第一页不能往前翻页
	                currentPage--;
	            }
	        } else if ("next".equals(types)) {
	            currentPage++;
	        } else if ("first".equals(types)) {
	            currentPage = 1;
	        } else if ("last".equals(types)) {
	            currentPage = allPages;
	        } else {
	            currentPage = Integer.parseInt(types);
	        }
	        ModelAndView modelAndView = new ModelAndView(Constants.GoodsCard_Index);
	        
	        Page page = PageHelper.startPage(currentPage, pageSize, "");
	        List<HasBuyRecordGoodsCard> list = goodsCardService.gethasBuyRecordGoodsCard();
	        modelAndView.addObject("goodsCartList",list);
	        //总页数
	        allPages = page.getPages();
	        modelAndView.addObject("allPages", allPages);
	        // 当前页码
	        currentPage = page.getPageNum();
	        modelAndView.addObject("currentPage", currentPage);
	        modelAndView.addObject("resultMsg", request.getParameter("resultMsg"));
	        return modelAndView;
	}
	
	@RequestMapping(value="/reviewed/{allPages}/{currentPage}/{type}")
	public ModelAndView getReviewRecordList(HttpServletRequest request,@PathVariable(value="allPages") int allPages,
            @PathVariable(value="currentPage")int currentPage,
            @PathVariable(value="type") String types,GeneratedCards generatedCards){
		 	if ("prvious".equals(types)) {
	            if( currentPage > 1 ){//第一页不能往前翻页
	                currentPage--;
	            }
	        } else if ("next".equals(types)) {
	            currentPage++;
	        } else if ("first".equals(types)) {
	            currentPage = 1;
	        } else if ("last".equals(types)) {
	            currentPage = allPages;
	        } else {
	            currentPage = Integer.parseInt(types);
	        }
	        ModelAndView modelAndView = new ModelAndView("goodsCard/review");
	        
	        Page page = PageHelper.startPage(currentPage, pageSize, "");
	        List<GeneratedCardsOper> list = goodsCardService.getReviewedRecords();
	        modelAndView.addObject("records",list);
	        //总页数
	        allPages = page.getPages();
	        modelAndView.addObject("allPages", allPages);
	        // 当前页码
	        currentPage = page.getPageNum();
	        modelAndView.addObject("currentPage", currentPage);
	        return modelAndView;
	}
	@RequestMapping(value="/unreviewed/{allPages}/{currentPage}/{type}")
	public ModelAndView getUnreviewedRecordList(HttpServletRequest request,@PathVariable(value="allPages") int allPages,
            @PathVariable(value="currentPage")int currentPage,
            @PathVariable(value="type") String types,GeneratedCards generatedCards){
		 	if ("prvious".equals(types)) {
	            if( currentPage > 1 ){//第一页不能往前翻页
	                currentPage--;
	            }
	        } else if ("next".equals(types)) {
	            currentPage++;
	        } else if ("first".equals(types)) {
	            currentPage = 1;
	        } else if ("last".equals(types)) {
	            currentPage = allPages;
	        } else {
	            currentPage = Integer.parseInt(types);
	        }
	        ModelAndView modelAndView = new ModelAndView("goodsCard/unreviewRecords");
	        
	        Page page = PageHelper.startPage(currentPage, pageSize, "");
	        List<GeneratedCardsOper> list = goodsCardService.getUnReviewedRecords();
	        modelAndView.addObject("records",list);
	        //总页数
	        allPages = page.getPages();
	        modelAndView.addObject("allPages", allPages);
	        // 当前页码
	        currentPage = page.getPageNum();
	        modelAndView.addObject("currentPage", currentPage);
	        return modelAndView;
	}
	
	
	@RequestMapping(value="/cardLogIndex/{allPages}/{currentPage}/{pageType}")
	public ModelAndView getCardLogList(HttpServletRequest request,@PathVariable(value="allPages") int allPages,
            @PathVariable(value="currentPage")int currentPage,
            @PathVariable(value="pageType") String types,GoodsCardLogView goodsCardLog){
//		if ("prvious".equals(types)) {
//            if( currentPage > 1 ){//第一页不能往前翻页
//                currentPage--;
//            }
//        } else if ("next".equals(types)) {
//            currentPage++;
//        } else if ("first".equals(types)) {
//            currentPage = 1;
//        } else if ("last".equals(types)) {
//            currentPage = allPages;
//        } else {
//            currentPage = Integer.parseInt(types);
//        }
        ModelAndView modelAndView = new ModelAndView(Constants.GoodsCard_Log_Index);
        
        //Page page = PageHelper.startPage(currentPage, pageSize, "create_time desc");
        List<GoodsCardLogView> list = goodsCardService.getGoodsCardLogList(goodsCardLog);
        modelAndView.addObject("goodsCartLogList",list);
        //总页数
//        allPages = page.getPages();
//        modelAndView.addObject("allPages", allPages);
//        // 当前页码
//        currentPage = page.getPageNum();
//        modelAndView.addObject("currentPage", currentPage);
        modelAndView.addObject("resultMsg", request.getParameter("resultMsg"));
        return modelAndView;
	}
	 		
	@RequestMapping(value="/toGenerateCards")
	public String togeneratorCards(HttpServletRequest r) {
		return "goodsCard/generateCards";
	}
	@RequestMapping(value="/generateCards")
	public String generatorCards(HttpServletRequest request,Model m) {
		double deno = Double.parseDouble(request.getParameter("deno"));
		int num = Integer.parseInt(request.getParameter("num"));
		AdminUser user = (AdminUser)request.getSession().getAttribute("user");
		GeneratedCards s = new GeneratedCards();
		s.setOper_time(new Date());
		s.setCardNum(num);
		s.setDenomination(deno);
		s.setReviewed(false);
		s.setOperator(user.getId());
		goodsCardService.saveGeneratedCards(s);	
		return "goodsCard/generateCards";
	}
	
	@RequestMapping("/toReviewCards")
	public String reviewCards() {
		return "goodsCard/review";
	}
	
	
	@RequestMapping("/exportExcel/{id}")
	public void exportExcel(@PathVariable("id") int id,HttpServletResponse response){
		//System.out.println(id);
		//ModelAndView mv = new ModelAndView("redirect://goodsCard/reviewedDetails/0/1/prvious?id="+id);
		goodsCardService.exportExcel(id, response);
		//return mv;
	}
	
	
	/*
	@RequestMapping(value="/doReview")
	public ModelAndView doReview(@RequestParam("review") String[] rev,Model m) {
		long ss = System.currentTimeMillis();
		long gend = 0;
		long st = 0;
		for(String s:rev ) {
			int id = Integer.parseInt(s);
			goodsCardService.setReviewed(id, true);
			GeneratedCards gc = goodsCardService.getGeneratedRec(id);
			int cardNum = gc.getCardnum();
			double deno = gc.getDenomination();
			st = System.currentTimeMillis();
			for(int i = 0;i<cardNum;i++)
				goodsCardService.generateCard(deno,id);
			gend = System.currentTimeMillis();
		}
		
	
		ModelAndView mv = new ModelAndView("forward:/goodsCard/unreviewed/0/1/prvious");
		long ee = System.currentTimeMillis();
		System.out.println("总耗时："+(ee-ss)/1000 +"s" );
		System.out.println("生成耗时："+(gend-st)/1000 +"s" );
		return mv;
	}*/
	@RequestMapping(value="/doReview")
	public ModelAndView doReview(@RequestParam("review") String[] rev,Model m) {
		long ss = System.currentTimeMillis();
		long gend = 0;
		long st = 0;
		for(String s:rev ) {
			int id = Integer.parseInt(s);
			GeneratedCards gc = goodsCardService.getGeneratedRec(id);
			int cardNum = gc.getCardNum();
			double deno = gc.getDenomination();
			st = System.currentTimeMillis();
			goodsCardService.generateCard(deno,id,cardNum);
			goodsCardService.setReviewed(id, true);
			gend = System.currentTimeMillis();
		}
		
	
		ModelAndView mv = new ModelAndView("forward:/goodsCard/unreviewed/0/1/prvious");
		long ee = System.currentTimeMillis();
		System.out.println("总耗时："+(ee-ss)/1000 +"s" );
		System.out.println("生成耗时："+(gend-st)/1000 +"s" );
		return mv;
	}
	@RequestMapping(value="/reviewedDetails/{allPages}/{currentPage}/{type}")
	public ModelAndView getReviewedDetails(HttpServletRequest request,@PathVariable(value="allPages") int allPages,
            @PathVariable(value="currentPage")int currentPage,
            @PathVariable(value="type") String types, @RequestParam("id") int id){
		 	if ("prvious".equals(types)) {
	            if( currentPage > 1 ){//第一页不能往前翻页
	                currentPage--;
	            }
	        } else if ("next".equals(types)) {
	            currentPage++;
	        } else if ("first".equals(types)) {
	            currentPage = 1;
	        } else if ("last".equals(types)) {
	            currentPage = allPages;
	        } else {
	            currentPage = Integer.parseInt(types);
	        }
	        ModelAndView modelAndView = new ModelAndView("goodsCard/reviewDetails");
	       
	        Page page = PageHelper.startPage(currentPage, pageSize, "");
	        List<GeneratedDetails> list = goodsCardService.getReviewedCardsDetails(id);
	        modelAndView.addObject("records",list);
	        //总页数
	        allPages = page.getPages();
	        modelAndView.addObject("allPages", allPages);
	        // 当前页码
	        currentPage = page.getPageNum();
	        modelAndView.addObject("currentPage", currentPage);
	        modelAndView.addObject("id", String.valueOf(id));
	        return modelAndView;
	}
}
