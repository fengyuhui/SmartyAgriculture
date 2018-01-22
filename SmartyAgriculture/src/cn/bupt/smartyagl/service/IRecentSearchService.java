package cn.bupt.smartyagl.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.bupt.smartyagl.entity.autogenerate.GoodsList;

@Service
public interface IRecentSearchService {
	/**
	 * 根据userId和商品（名）添加用户搜索信息
	 * @param userId
	 * @param goods
	 */
	public void addSearchRecord(int userId, GoodsList goods);

	public List<String> getRecentSearchRecord(Integer userId);

	public boolean deleteRecentSearch(Integer userId);

}
