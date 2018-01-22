package cn.bupt.smartyagl.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.bupt.smartyagl.entity.autogenerate.UserScore;

@Service
public interface IUserScoreService {
	/**
	 * 加入一个新的userscore记录
	 * @param userId
	 * @param score 
	 * @param orderId
	 */
	public void addUserScore(int userId,int goodsId, int score);
	/**
	 * 根据用户号和商品编号查询特定的一个userscore记录
	 * @param userId
	 * @param goodsId
	 * @return
	 */
	public UserScore getUserScore(Integer userId,Integer goodsId);
	/**
	 * 更新已有的userScore记录
	 * @param userScore
	 * @param score
	 */
	public void updateUserScore(UserScore userScore, int score);
	/**
	 * 根据userIdv查询该用户的积分详情
	 * @param userId
	 * @return
	 */
	public List<UserScore> getUserScoreList(Integer userId);
}
