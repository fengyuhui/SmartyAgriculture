package cn.bupt.smartyagl.service;

import org.springframework.web.servlet.ModelAndView;

/**
 * 
 *<p>Title:IGoodsRejectedService</p>
 *<p>Description:商品退货</p>
 * @author waiting
 *@date 2016年9月8日 上午10:26:35
 */
public interface IGoodsRejectedService {
    
	/**
	 * 退货列表
	 * @param modelAndView
	 * @param currentPage
	 * @param pageSize
	 */
	public void goodsRejectedIndex(ModelAndView modelAndView,int currentPage,int pageSize);
	/**
	 * 删除退货信息
	 * @param id
	 * @return
	 */
	public boolean deleteRejected(int id);
	 
}
