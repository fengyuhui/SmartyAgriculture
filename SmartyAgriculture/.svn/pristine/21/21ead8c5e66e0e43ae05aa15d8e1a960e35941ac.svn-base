package cn.bupt.smartyagl.controller.inf;  
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;  
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Controller; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.ResponseBody;  

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.bupt.smartyagl.entity.autogenerate.GoodsList;
import cn.bupt.smartyagl.entity.autogenerate.HotSearch; 
import cn.bupt.smartyagl.entity.autogenerate.HotSearchExample; 
import cn.bupt.smartyagl.entity.autogenerate.LikeGoodsView;
import cn.bupt.smartyagl.entity.autogenerate.HotSearchExample.Criteria; 
import cn.bupt.smartyagl.service.impl.SearchServiceImpl; 
import cn.bupt.smartyagl.service.IMayBeLikeService;
import cn.bupt.smartyagl.service.ISearchService; 
import cn.bupt.smartyagl.util.NetDataAccessUtil;  
import cn.bupt.smartyagl.util.picture.JsonConvert;
@Controller 
@RequestMapping("/search") 
public class SearchController { 	
	/**
	 * 获取热搜词 	 
	 * @author zxy 	 
	 * @param SearchService  	 
	 * @param HotSearch 	 
	 * @return 	
	 */
	@Autowired 
	ISearchService searchService;	 
	@Autowired
	IMayBeLikeService mayBeLikeService;
	@RequestMapping("/hotSearch") 	
	@ResponseBody 	
	public Object hotSearch(HttpServletRequest request,Integer num) {  		
		NetDataAccessUtil nau = new NetDataAccessUtil();		 		
//		List<HotSearch> rst =  searchService.hotSearch(num);
		String rst=new String();
		rst = searchService.hotSearch(num);
		if (rst != null) {
//			rst = searchService.hotSearch(num);
			nau.setContent(rst);
			nau.setResult(1); 			
			nau.setResultDesp("获取热搜词成功");	
		}else{ 		
		nau.setResult(0); 		
		nau.setResultDesp("获取热搜词失败"); 		
		}		 	
		return nau; 	
	} 
	
//	@RequestMapping("/searchHistory") 	
//	@ResponseBody 	
//	public Object searchHistory(HttpServletRequest request,Integer num) {  		
//		NetDataAccessUtil nau = new NetDataAccessUtil();		 		
////		List<HotSearch> rst =  searchService.hotSearch(num);
//		String rst=new String();
//		rst = searchService.searchHistory();
//		if (rst != null) {
////			rst = searchService.hotSearch(num);
//			nau.setContent(rst);
//			nau.setResult(1); 			
//			nau.setResultDesp("获取搜索历史成功");	
//		}else{ 		
//		nau.setResult(0); 		
//		nau.setResultDesp("获取搜索历史失败"); 		
//		}		 	
//		return nau; 	
//	} 
//	
//	@RequestMapping("/deleteHistory") 	
//	@ResponseBody 	
//	public Object deleteHistory(HttpServletRequest request,Integer num) {  		
//		NetDataAccessUtil nau = new NetDataAccessUtil();		 		
////		List<HotSearch> rst =  searchService.hotSearch(num);
//		String rst=new String();
//		rst = searchService.deleteHistory();
//		if (rst != null) {
////			rst = searchService.hotSearch(num);
//			nau.setContent(rst);
//			nau.setResult(1); 			
//			nau.setResultDesp("删除搜索历史成功");	
//		}else{ 		
//		nau.setResult(0); 		
//		nau.setResultDesp("删除搜索历史失败"); 		
//		}		 	
//		return nau; 	
//	} 
	
}