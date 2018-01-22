package cn.bupt.smartyagl.service;

import java.util.List;

import org.omg.CORBA.INTERNAL;

import cn.bupt.smartyagl.entity.autogenerate.Fre;
import cn.bupt.smartyagl.entity.autogenerate.Freight;

public interface IFreightService {

	/**
	 * 添加运费模板
	 * @param freight
	 * @return
	 * @author waiting
	 */
	public int addFreight(Freight freight);
	/**
	 * 查找商品的运费模板
	 * @param goodsId
	 * @return
	 */
	public List<Freight> freightList(int goodsId);
	/**
	 * 查找待审核的商品运费模板
	 * @param goodsId
	 * @return
	 */
	public List<Freight> freightListAudit(int goodsId);
	/**
	 * 删除运费模板
	 * @param id
	 * @return
	 */
	public Boolean deleteFreight(int id);
	/**
	 * 修改运费模板
	 * @param goodsId
	 * @param money
	 * @return
	 */
	public Boolean updateFreight(Freight freight);
	/**
	 * 将运费模板设置成删除待审核状态
	 * @param id
	 * @return
	 */
	public Boolean deleteAuditFreight(int id);
	public List<Fre> getFreightList();
	public Fre getFreightById(int id);
	public void updateAuditStatusById(int yuanId, Integer freightHasdraft);
	public void addFreightDraft(Fre fre);
	public void doReview(int id);
	public void deleteFreightDraft(int id);
	public List<Fre> getUnreviewedFreightList();

}
