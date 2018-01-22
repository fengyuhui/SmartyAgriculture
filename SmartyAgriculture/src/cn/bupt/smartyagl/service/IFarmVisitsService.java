package cn.bupt.smartyagl.service;

import java.util.List;

import cn.bupt.smartyagl.entity.autogenerate.FarmVisits;

/**
 * 农场参观类型管理
 * @author TMing
 * 
 */
public interface IFarmVisitsService {
	
	boolean deleteFarmVisitTypeById(int id);
	FarmVisits getFarmVisitTypeById(int id);
	List<FarmVisits> getFarmVisitTypes();
	boolean updateFarmVisitType(FarmVisits farmVisits);
	boolean addFarmVisitBatch(List<FarmVisits> list);
	List<FarmVisits> getFarmVisitTypesBatch(List<Integer> list);
	boolean deleteFarmVisitTypeBatch(List<Integer> list);
	boolean batchUpdate(List<FarmVisits> list);
}
