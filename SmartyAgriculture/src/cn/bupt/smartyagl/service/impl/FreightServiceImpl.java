package cn.bupt.smartyagl.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.constant.ConstantsSql;
import cn.bupt.smartyagl.dao.autogenerate.FreMapper;
import cn.bupt.smartyagl.dao.autogenerate.FreightMapper;
import cn.bupt.smartyagl.entity.autogenerate.Fre;
import cn.bupt.smartyagl.entity.autogenerate.FreExample;
import cn.bupt.smartyagl.entity.autogenerate.Freight;
import cn.bupt.smartyagl.entity.autogenerate.FreightExample;
import cn.bupt.smartyagl.entity.autogenerate.FreightExample.Criteria;
import cn.bupt.smartyagl.service.IFreightService;

@Service
public class FreightServiceImpl implements IFreightService {

	@Autowired
	FreightMapper freightMapper;
	@Autowired 
	FreMapper freMapper;
	@Override
	public int addFreight(Freight freight) {
		// TODO Auto-generated method stub
		freight.setIsdisplay(1);
		int i=freightMapper.insert(freight);
		return i;
	}

	@Override
	public List<Freight> freightList(int goodsId) {
		// TODO Auto-generated method stub
		FreightExample freightExample=new FreightExample();
		Criteria criteria=freightExample.createCriteria();
		criteria.andIsdisplayEqualTo(1);
		criteria.andAuditStatusEqualTo(ConstantsSql.Audit_Finish);
		criteria.andGoodsIdEqualTo(goodsId);
		List<Freight> returnList=freightMapper.selectByExample(freightExample);
		return returnList;
	}

	@Override
	public Boolean deleteFreight(int id) {
		// TODO Auto-generated method stub
		Freight freight=freightMapper.selectByPrimaryKey(id);
		freight.setIsdisplay(0);
		int i=freightMapper.updateByPrimaryKey(freight);
		if(i!=0){
			return true;
		}
		else{
			return false;
		}
	}
	@Override
	public Boolean updateFreight(Freight freight) {
		// TODO Auto-generated method stub
		FreightExample freightExample=new FreightExample();
		Criteria criteria=freightExample.createCriteria();
		criteria.andIsdisplayEqualTo(1);
		criteria.andGoodsIdEqualTo(freight.getGoodsId());
		criteria.andProvinceEqualTo(freight.getProvince());
		criteria.andCityEqualTo(freight.getCity());
		List<Freight> returnList=freightMapper.selectByExample(freightExample);
		Freight freight2=returnList.get(0);
		freight2.setMoney(freight.getMoney());
		int i=freightMapper.updateByPrimaryKey(freight2);
		if(i!=0){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public Boolean deleteAuditFreight(int id) {
		// TODO Auto-generated method stub
		Freight freight=freightMapper.selectByPrimaryKey(id);
		freight.setAuditStatus(ConstantsSql.Audit_WaitDelete);
		int i=freightMapper.updateByPrimaryKey(freight);
		if(i!=0){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public List<Freight> freightListAudit(int goodsId) {
		// TODO Auto-generated method stub
		List<Integer> selectList=new ArrayList<Integer>();
		selectList.add(ConstantsSql.Audit_Finish);
		selectList.add(ConstantsSql.Audit_Draft);
		FreightExample freightExample=new FreightExample();
		Criteria criteria=freightExample.createCriteria();
		criteria.andIsdisplayEqualTo(1);
		criteria.andAuditStatusIn(selectList);
		criteria.andGoodsIdEqualTo(goodsId);
		List<Freight> returnList=freightMapper.selectByExample(freightExample);
		return returnList;
	}

	@Override
	public List<Fre> getFreightList() {
		FreExample example = new FreExample();
		List<Integer> list = new ArrayList<Integer>();
		list.add(Constants.FREIGHT_NORMAL);
		list.add(Constants.FREIGHT_HASDRAFT);
		example.createCriteria().andAuditStatusIn(list);
		return freMapper.selectByExample(example);
	}

	@Override
	public Fre getFreightById(int id) {
		return freMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateAuditStatusById(int yuanId, Integer freightHasdraft) {
		Fre fre = new Fre();
		fre.setId(yuanId);
		fre.setAuditStatus(freightHasdraft);
		freMapper.updateByPrimaryKeySelective(fre);
	}

	@Override
	public void addFreightDraft(Fre fre) {
		freMapper.insert(fre);
	}

	@Override
	public void doReview(int id) {
		Fre dra = this.getFreightById(id);
		Fre yuan = this.getFreightById(dra.getSourceId());
		yuan.setAdditionweight(dra.getAdditionweight());
		yuan.setAdditionweightprice(dra.getAdditionweightprice());
		yuan.setFirstweight(dra.getFirstweight());
		yuan.setFirstweightprice(dra.getFirstweightprice());
		yuan.setAuditStatus(Constants.FREIGHT_NORMAL);	
		freMapper.updateByPrimaryKeySelective(yuan);
		freMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void deleteFreightDraft(int id) {
		Fre dra = this.getFreightById(id);
		Fre yuan = this.getFreightById(dra.getSourceId());
		yuan.setAuditStatus(Constants.FREIGHT_NORMAL);	
		freMapper.updateByPrimaryKeySelective(yuan);
		freMapper.deleteByPrimaryKey(id);	
	}

	@Override
	public List<Fre> getUnreviewedFreightList() {
		FreExample fe = new FreExample();
		fe.createCriteria().andAuditStatusEqualTo(Constants.FREIGHT_DRAFT);
		return freMapper.selectByExample(fe);
	}

}
