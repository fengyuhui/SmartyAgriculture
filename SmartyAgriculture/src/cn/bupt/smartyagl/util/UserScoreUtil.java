package cn.bupt.smartyagl.util;

import cn.bupt.smartyagl.entity.autogenerate.User;
import cn.bupt.smartyagl.entity.autogenerate.UserScore;
import cn.bupt.smartyagl.service.IGoodsService;
import cn.bupt.smartyagl.service.IOrderService;
import cn.bupt.smartyagl.service.IUserScoreService;
import cn.bupt.smartyagl.service.IUserService;

public class UserScoreUtil {
	public static void addScore(int orderId,IUserService userService,IOrderService orderService,
			IGoodsService goodsService,IUserScoreService userScoreService) {
		Integer userId = orderService.getSharerIdByOrderId(orderId);
		Integer goodsId = orderService.getOrderById(orderId).getGoodsId();
		if(userId != null) {
			User user = userService.getUserInfoById(userId);
			int score = goodsService.getGoodsDetail(goodsId).getScore();
			user.setScore(user.getScore() + score);
			userService.updateMyuserInfo(user);
			UserScore userScore = userScoreService.getUserScore(userId,goodsId);
			if(userScore == null) {
				userScoreService.addUserScore(userId, goodsId,score);
			}
			else {
				userScoreService.updateUserScore(userScore,score);
			}
		}		
	}
}
