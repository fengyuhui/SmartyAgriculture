package cn.bupt.smartyagl.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.dao.autogenerate.RecentSearchMapper;
import cn.bupt.smartyagl.entity.autogenerate.GoodsList;
import cn.bupt.smartyagl.entity.autogenerate.RecentSearch;
import cn.bupt.smartyagl.entity.autogenerate.RecentSearchExample;
import cn.bupt.smartyagl.entity.autogenerate.RecentSearchExample.Criteria;
import cn.bupt.smartyagl.service.IRecentSearchService;
@Service
public class RecentSearchServiceImpl implements IRecentSearchService {

	@Autowired
	RecentSearchMapper recentSearchMapper;
	@Override
	public void addSearchRecord(int userId, GoodsList goods) {
		if(goods == null || goods.getName() == null || goods.getName().trim().equals(""))
			return;
		String name = goods.getName().trim();
		RecentSearch rs = new RecentSearch();
		RecentSearchExample rse = new RecentSearchExample();
		Criteria cre = rse.createCriteria();
		cre.andUserIdEqualTo(userId);
		List<RecentSearch> l = recentSearchMapper.selectByExample(rse);
		if(l.size() < Constants.RECENT_SEARCH_NUM) {
			int id = isExist(l, name);
			if(id == -1) {
				rs.setUserId(userId);
				rs.setWord(name);
				recentSearchMapper.insert(rs);
			}
			else {
				cre.andIdEqualTo(id);
				recentSearchMapper.deleteByExample(rse);
				rs.setUserId(userId);
				rs.setWord(name);
				recentSearchMapper.insert(rs);				
			}
		}
		else {
			int id = isExist(l, name);
			if(id == -1) {
				int min = Integer.MAX_VALUE;
				for(RecentSearch tmp : l) {
					min = Math.min(tmp.getId(),min);
				}
				cre.andIdEqualTo(min);
				recentSearchMapper.deleteByExample(rse);
				rs.setUserId(userId);
				rs.setWord(name);
				recentSearchMapper.insert(rs);
			}
			else {
				cre.andIdEqualTo(id);
				recentSearchMapper.deleteByExample(rse);
				rs.setUserId(userId);
				rs.setWord(name);
				recentSearchMapper.insert(rs);					
			}
		}
	}
	@Override
	public List<String> getRecentSearchRecord(Integer userId) {
		RecentSearchExample rse = new RecentSearchExample();
		rse.createCriteria().andUserIdEqualTo(userId);
		rse.setOrderByClause("id desc");
		List<RecentSearch> l = recentSearchMapper.selectByExample(rse);
		List<String> ans = new ArrayList<String>();
		for(RecentSearch rs:l)
			ans.add(rs.getWord());
		return ans;
	}
	private int isExist(List<RecentSearch> l,String word) {
		for(RecentSearch tmp : l) {
			if(word.equals(tmp.getWord()))
				return tmp.getId();
		}
		return -1;
	}
	@Override
	public boolean deleteRecentSearch(Integer userId) {
		RecentSearchExample rse = new RecentSearchExample();
		rse.createCriteria().andUserIdEqualTo(userId);
		recentSearchMapper.deleteByExample(rse);
		return true;
	}
}
