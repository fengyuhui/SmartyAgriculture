package cn.bupt.smartyagl.service;

import java.util.List;

import cn.bupt.smartyagl.entity.autogenerate.InputGoods;
import cn.bupt.smartyagl.entity.autogenerate.InputGoodsView;
/**
 * 
 *<p>Title:IGoodsInputService</p>
 *<p>Description:商品进货</p>
 * @author waiting
 *@date 2016年10月20日 下午2:54:23
 */
public interface IGoodsInputService {
	/**
	 * 商品进货
	 * @param InputGoods
	 * @return
	 */
	public boolean inputGoods(InputGoods InputGoods);
	/**
	 * 获取进货列表
	 * @param status
	 * @return
	 */
	public List<InputGoodsView> getInputGoods(int status);
	/**
	 * 审核进货
	 * @param id
	 * @param status
	 * @return
	 */
	public boolean auditInputGoods(int id,int status);
}
