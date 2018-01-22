package cn.bupt.smartyagl.controller.inf;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bupt.smartyagl.entity.autogenerate.RecentSearch;
import cn.bupt.smartyagl.service.IRecentSearchService;
import cn.bupt.smartyagl.util.NetDataAccessUtil;

@Controller
@RequestMapping("/interface/recentSearch")
public class RecentSearchController {
	@Autowired
	IRecentSearchService recentSearchService;
	@RequestMapping("/getRecentSearch")
	@ResponseBody
	public Object getRecentSearch(HttpServletRequest request) {
		NetDataAccessUtil nau = new NetDataAccessUtil();
		Integer userId = (Integer)request.getAttribute("userId");
		if(userId == null) {
			nau.setResult(0);
			nau.setResultDesp("未登录下无法获取搜索记录，请先登陆");	
			return nau;
		}
		nau.setContent(recentSearchService.getRecentSearchRecord(userId));
		nau.setResult(1);
		nau.setResultDesp("获取最近搜索记录成功");
		return nau;		
	}
	@RequestMapping("/deleteRecentSearch")
	@ResponseBody
	public Object deleteRecentSearch(HttpServletRequest request) {
		NetDataAccessUtil nau = new NetDataAccessUtil();
		Integer userId = (Integer)request.getAttribute("userId");
		if(userId == null) {
			nau.setResult(0);
			nau.setResultDesp("未登录下无法进行删除搜索记录操作，请先登陆");	
			return nau;
		}
		if(recentSearchService.deleteRecentSearch(userId)) {
			nau.setContent(new ArrayList<RecentSearch>());
			nau.setResult(1);
			nau.setResultDesp("删除最近搜索记录成功");
		}
		else {
			nau.setResult(0);
			nau.setResultDesp("删除最近搜索记录失败");
		}
		return nau;		
	}
}
