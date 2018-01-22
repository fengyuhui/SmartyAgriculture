package cn.bupt.smartyagl.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bupt.smartyagl.dao.autogenerate.FarmVisitsMapper;
import cn.bupt.smartyagl.entity.autogenerate.FarmVisits;
import cn.bupt.smartyagl.entity.autogenerate.FarmVisitsExample;
import cn.bupt.smartyagl.service.IFarmVisitsService;
import cn.bupt.smartyagl.util.NetDataAccessUtil;

/**
 * 农场参观管理方法实现
 * @author TMing
 *
 */
@Service
public class FarmVisitsServiceImpl implements IFarmVisitsService{

	@Autowired
	private  FarmVisitsMapper farmVisitsMapper;
	
	@Override
	public boolean addFarmVisitBatch(List<FarmVisits> list) {
        
        int i = farmVisitsMapper.insertBatch(list);
        if (0 != i) 
        	return true;
        else 
        	return false;
	}

	@Override
	public boolean deleteFarmVisitTypeById(int id) {
		int i = farmVisitsMapper.deleteByPrimaryKey(id);
		if (0 != i)
			return true;
		else
			return false;
	}

	@Override
	public FarmVisits getFarmVisitTypeById(int id) {
		// 没有对应的对象怎么处理？
		FarmVisits farmVisits = farmVisitsMapper.selectByPrimaryKey(id);
		return farmVisits;
	}

	@Override
	public List<FarmVisits> getFarmVisitTypes() {
		//更改？！！！！！！！！！！
		List<FarmVisits> list = new ArrayList<FarmVisits>();
		FarmVisitsExample farmVisitsExample = new FarmVisitsExample();
		list = farmVisitsMapper.selectByExample(farmVisitsExample);
		return list;
	}

	@Override
	public boolean updateFarmVisitType(FarmVisits farmVisits) {
		int i = farmVisitsMapper.updateByPrimaryKey(farmVisits);
		if (0 != i)
			return true;
		else
			return false;
	}

	@Override
	public List<FarmVisits> getFarmVisitTypesBatch(List<Integer> list) {
		System.out.println("list"+list);
		List<FarmVisits> typeList = farmVisitsMapper.selectByIds(list);
		return typeList;
	}

	@Override
	public boolean deleteFarmVisitTypeBatch(List<Integer> list) {
		int i = farmVisitsMapper.deleteBatch(list);
		if (0 != i)
			return true;
		else
			return false;
	}

	@Override
	public boolean batchUpdate(List<FarmVisits> list) {
		int i = farmVisitsMapper.batchUpdate(list);
		if (0 != i)
			return true;
		else
			return false;
	}
	

}
