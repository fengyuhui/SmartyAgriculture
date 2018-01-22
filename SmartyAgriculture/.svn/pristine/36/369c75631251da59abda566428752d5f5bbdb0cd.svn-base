package cn.bupt.smartyagl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bupt.smartyagl.dao.autogenerate.UserScoreMapper;
import cn.bupt.smartyagl.entity.autogenerate.UserScore;
import cn.bupt.smartyagl.entity.autogenerate.UserScoreExample;
import cn.bupt.smartyagl.service.IUserScoreService;
@Service
public class UserScoreService implements IUserScoreService {

	@Autowired
	UserScoreMapper userScoreMapper;
	@Override
	public void addUserScore(int userId, int goodsId,int score) {
		UserScore userScore = new UserScore();
		userScore.setGoodsId(goodsId);
		userScore.setUserId(userId);
		userScore.setScore(score);
		userScore.setTimes(1);
		userScoreMapper.insert(userScore);
	}

	@Override
	public UserScore getUserScore(Integer userId,Integer goodsId) {
		UserScoreExample use = new UserScoreExample();
		use.createCriteria().andGoodsIdEqualTo(goodsId).andUserIdEqualTo(userId);
		List<UserScore> list = userScoreMapper.selectByExample(use);
		if(list.size() != 0) 
			return list.get(0);
		else
			return null;
	}

	@Override
	public void updateUserScore(UserScore userScore, int score) {
		userScore.setScore(score+userScore.getScore());
		userScore.setTimes(userScore.getTimes() + 1);
		userScoreMapper.updateByPrimaryKeySelective(userScore);
	}

	@Override
	public List<UserScore> getUserScoreList(Integer userId) {
		UserScoreExample use = new UserScoreExample();
		use.createCriteria().andUserIdEqualTo(userId);
		return userScoreMapper.selectByExample(use);
	}


}
