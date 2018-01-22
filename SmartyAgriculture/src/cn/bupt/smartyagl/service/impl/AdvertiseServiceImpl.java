package cn.bupt.smartyagl.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bupt.smartyagl.constant.ConstantsSql;
import cn.bupt.smartyagl.dao.autogenerate.AdvertiseMapper;
import cn.bupt.smartyagl.entity.autogenerate.Advertise;
import cn.bupt.smartyagl.entity.autogenerate.AdvertiseExample;
import cn.bupt.smartyagl.service.IAdevertiseService;
import cn.bupt.smartyagl.util.picture.getImageFileUtil;

/** 
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-6-1 上午11:25:13 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
@Service
public class AdvertiseServiceImpl implements IAdevertiseService {
	@Autowired
	AdvertiseMapper advertiseMapper;
	
	@Override
	public boolean addAdvertise(Advertise advertise) {
		 advertise.setCreate_time(new Date());
		 advertise.setStatus(ConstantsSql.Audit_Publish_NoAuth);
		 int rs = advertiseMapper.insert(advertise);
		    if(rs>0){
		    	return true;
		    }
			return false;
	}

	@Override
	public boolean deleteAdvertise(Integer advertiseId) {
		//如果是草稿，改变原数据参数
		Advertise ad = advertiseMapper.selectByPrimaryKey(advertiseId);
		if( ad.getParentId() != null ){//删除的是草稿，修改原数据为无草稿状态
			Advertise ad_par = advertiseMapper.selectByPrimaryKey( ad.getParentId() );
			ad_par.setStatus( ConstantsSql.Audit_Finish );
			advertiseMapper.updateByPrimaryKey(ad_par);
		}
		int rs = advertiseMapper.deleteByPrimaryKey(advertiseId);
		AdvertiseExample ae = new AdvertiseExample();
		//删除草稿
		ae.createCriteria().andParentIdEqualTo(advertiseId);
		advertiseMapper.deleteByExample(ae);
		
		if(rs>0){
	    	return true;
	    }
		return false;
	}

	@Override
	public List<Advertise> getAdvertiseList() {
		AdvertiseExample ae = new AdvertiseExample();
		ae.setOrderByClause("top desc,create_time desc");
		List<Integer> auditList = new ArrayList<Integer>();
		auditList.add(ConstantsSql.Audit_Finish);
		auditList.add(ConstantsSql.Audit_Finish_hasDraft);
		ae.createCriteria().andStatusIn(auditList);
		List<Advertise> list = advertiseMapper.selectByExample(ae);
		for(Advertise tmp : list){
			tmp.setPicture( getImageFileUtil.getSrcFileImg( tmp.getPicture()) );
		}
		return list;
	}

	@Override
	public boolean updateAdvertise(Advertise advertise) {
		try{
			Integer id = advertise.getId();
			//int rs = advertiseMapper.updateByPrimaryKeySelective(advertise);
			//添加草稿文章数据
			advertise.setCreate_time(new Date());
			advertise.setStatus(ConstantsSql.Audit_Draft);
			//标记父文章标题
			advertise.setParentId(id);
			advertise.setId(null);
			int rs = advertiseMapper.insert(advertise);
			
			//更改原文章有草稿状态
			this.verifyAdvertiseStatus(id, ConstantsSql.Audit_Finish_hasDraft);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean verifyAdvertiseStatus(Integer advertiseId, Integer status) {
		Advertise advertise = new Advertise();
		advertise.setId(advertiseId);
		advertise.setStatus(status);
		int i = advertiseMapper.updateByPrimaryKeySelective(advertise);
		if(i>0)
			return true;
		return false;
	}
	
	@Override
	public boolean verifyAddAdvertise(Integer advertiseId) {
		return this.verifyAdvertiseStatus(advertiseId, ConstantsSql.Audit_Finish);
	}

	@Override
	public boolean verifyChangeAdvertise(Integer advertiseId) {
		Advertise ad_draft = advertiseMapper.selectByPrimaryKey( advertiseId );
		if( ad_draft.getParentId() == null || ad_draft.getStatus() != ConstantsSql.Audit_Draft){
			return false;
		}
		//更新原数据
		Advertise ad_yum  = advertiseMapper.selectByPrimaryKey( ad_draft.getParentId() );
		ad_yum.setPicture( ad_draft.getPicture() );
		ad_yum.setTitle( ad_draft.getTitle() );
		ad_yum.setTop( ad_draft.getTop() );
		ad_yum.setType( ad_draft.getType() );
		ad_yum.setStatus( ConstantsSql.Audit_Finish );
		advertiseMapper.updateByPrimaryKeySelective(ad_yum);
		//删除草稿
		advertiseMapper.deleteByPrimaryKey(advertiseId);
		return true;
	}

	@Override
	public List<Advertise> getAdvertiseDraftList() {
		AdvertiseExample ae = new AdvertiseExample();
		List<Integer> auditList = new ArrayList<Integer>();
		auditList.add(ConstantsSql.Audit_Draft);
		auditList.add(ConstantsSql.Audit_Publish_NoAuth);
		auditList.add(ConstantsSql.Audit_WaitDelete);
		ae.createCriteria().andStatusIn(auditList);
		List<Advertise> list = advertiseMapper.selectByExample(ae);
		for(Advertise tmp : list){
			tmp.setPicture( getImageFileUtil.getSrcFileImg( tmp.getPicture()) );
		}
		return list;
	}

	@Override
	public boolean changeTop(Integer advertiseId, Integer top) {
		Advertise advertise = new Advertise();
		advertise.setId(advertiseId);
		advertise.setTop(top);
		int i = advertiseMapper.updateByPrimaryKeySelective(advertise);
		if(i>0)
			return true;
		return false;
	}

	@Override
	public Advertise findAdvertise(Integer advertiseId) {
		Advertise ad =  advertiseMapper.selectByPrimaryKey( advertiseId );
		ad.setPicture( getImageFileUtil.getSrcFileImg( ad.getPicture()) );
		return ad;
	}
	@Override
	public boolean deletePostAdvertise(Advertise advertise) {
		int i = advertiseMapper.updateByPrimaryKeySelective(advertise);
		if( i >0 ){
			return true;
		}
		return false;
	}
	

}
