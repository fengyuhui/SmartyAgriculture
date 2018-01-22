package cn.bupt.smartyagl.service;
import java.util.List;

import cn.bupt.smartyagl.entity.AdditionAndHtml;
import cn.bupt.smartyagl.entity.autogenerate.Addition;
import cn.bupt.smartyagl.entity.autogenerate.AdditionView;
import cn.bupt.smartyagl.entity.autogenerate.FarmMessage;

public interface IAdditionService {
	/**
	 * 将新添加的记录持久化到addition表中,成功则返回true
	 * @param add
	 */
	public boolean save(Addition add);
	/**
	 * 根据title获取附属信息记录
	 * @param title
	 * @return
	 */
	public Addition getAdditionByTitle(String title);
	/**
	 * 将id对应的记录修改为正常有草稿
	 * @param id
	 */
	public void setModified(int id);
	/**
	 * 获取未审核的草稿
	 * @return
	 */
	public List<AdditionView> getUnreviewedMsg();
	/**
	 * 获取4个附属信息
	 * @return
	 */
	public List<Addition> getAllFourAddition();
	public Addition getAdditionById(int id);
	public boolean setReviewed(int id);
	public boolean unDo(int id);
	
	AdditionAndHtml convertMessage(Addition addition)
			throws IllegalArgumentException, IllegalAccessException;
	Addition getAdditionDetail(Integer id);
	List<Addition> getAdditionListById(Integer id);

}
