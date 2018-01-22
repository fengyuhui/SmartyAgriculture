package cn.bupt.smartyagl.controller.inf;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bupt.smartyagl.service.IUserScoreService;
import cn.bupt.smartyagl.util.NetDataAccessUtil;

@Controller
@RequestMapping("/interface/userScore")
public class UserScoreController {

	@Autowired
	IUserScoreService userScoreService;
	@RequestMapping("/getUserScoreInfo")
	@ResponseBody
	public Object getUserScoreInfo(HttpServletRequest request) {
		NetDataAccessUtil nau = new NetDataAccessUtil();
		Integer userId = (Integer)request.getAttribute("userId");
		if(userId == null) {
			nau.setResult(0);
			nau.setResultDesp("未登录下无法查看积分信息，请先登陆");	
			return nau;
		}
		nau.setContent(userScoreService.getUserScoreList(userId));
		nau.setResult(1);
		nau.setResultDesp("获取用户积分记录成功");
		return nau;		
	}
}
