package cn.bupt.smartyagl.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.bupt.smartyagl.constant.ConstantsSql;
import cn.bupt.smartyagl.dao.autogenerate.FarmMessageMapper;
import cn.bupt.smartyagl.entity.FarmMessageAndHtml;
import cn.bupt.smartyagl.entity.autogenerate.FarmMessage;
import cn.bupt.smartyagl.entity.autogenerate.FarmMessageExample;
import cn.bupt.smartyagl.service.IFarmMessageService;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"classpath*:/spring-*.xml","classpath*:/spring.xml"})
//@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class})
@Service
public class FarmMessageServiveImpl implements IFarmMessageService{

	@Autowired
	private FarmMessageMapper farmMessageMapper;
	
	@Test
	public void test(){
		//List<FarmMessage> list = this.getFarmMessageList(2, 10);
		System.out.println("t");
	}
	
	@Override
	public List<FarmMessage> getFarmMessageList(FarmMessage farmMessageSelective) {
		FarmMessageExample ae=new FarmMessageExample();
		cn.bupt.smartyagl.entity.autogenerate.FarmMessageExample.Criteria ca =  ae.createCriteria();
		
		//标题名模糊搜索
		if(farmMessageSelective != null && farmMessageSelective.getMessageName() != null){
			ca.andMessageNameLike("%"+farmMessageSelective.getMessageName()+"%");
		}
		
		ae.setOrderByClause("topOrNot desc,creatTime desc");
		List<Integer> publishList = new ArrayList<Integer>();
		publishList.add(ConstantsSql.Audit_Finish);
		publishList.add(ConstantsSql.Audit_Finish_hasDraft);
		ca.andStatusIn(publishList);
		List<FarmMessage> list = farmMessageMapper.selectByExample(ae);
		return list;		
//		List<FarmMessage> farmMessageList=farmMessageMapper.selectByExample(ae);
//		return farmMessageMapper.selectByExample(ae);
	}
	
	@Override
	public List<FarmMessage> getFarmMessageList(Integer typeId,Integer pageSize ,Integer pageNum) {
		//限制查询个数
		
		Page page = PageHelper.startPage( pageNum, pageSize, "topOrNot desc,creatTime desc");
		FarmMessageExample fe = new FarmMessageExample();
		cn.bupt.smartyagl.entity.autogenerate.FarmMessageExample.Criteria ca = fe.createCriteria();
		//状态
		ArrayList<Integer> status = new ArrayList<Integer>();
		status.add( ConstantsSql.Audit_Finish );
		status.add( ConstantsSql.Audit_Finish_hasDraft );
		ca.andStatusIn( status );
		//类型
		ca.andMessageTypeEqualTo(typeId);
		
		return farmMessageMapper.selectByExample( fe );
	}
	
	@Override
	public FarmMessage findFarmMessage(Integer id) {
		return farmMessageMapper.selectByPrimaryKey( id );
	}
	
	@Override
	public FarmMessage getFarmMessageDetail(Integer id) {
		List<FarmMessage> farmMessageList = null;
		try{
			 FarmMessageExample ge = new FarmMessageExample();
			 ge.createCriteria().andIdEqualTo(id);
			 farmMessageList = farmMessageMapper.selectByExampleWithBLOBs(ge);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		if(farmMessageList.size() <= 0){
			return null;
		}
		
		return farmMessageList.get(0);
	}
	
	@Override
	public FarmMessageAndHtml convertMessage(FarmMessage farmMessage) throws IllegalArgumentException, IllegalAccessException {
		FarmMessageAndHtml gc = new FarmMessageAndHtml();
		Field[] message_fields = FarmMessage.class.getDeclaredFields();
		for(Field field : message_fields){
			field.setAccessible(true);//可强行访问

			field.set(gc, field.get(farmMessage));
		}
		return gc;
	}
	
	@Override
	public String getMessageName(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean addFarmMessages(FarmMessage farmMessage) {
		farmMessage.setCreatTime(new Date());
		farmMessage.setStatus(ConstantsSql.Audit_Publish_NoAuth);
		farmMessage.setOriginalMessageId(0);
//		farmMessage.setTopOrNot(0);
		int i =farmMessageMapper.insert(farmMessage);
		// TODO Auto-generated method stub
		if(i!=0)
            return true;
        else
            return false;
	}

	
	@Override
	public FarmMessage getFarmMessageById(int id) {
		return farmMessageMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean deleteFarmMessage(Integer id) {
	    //农场信息list中，扫描每种类型的信息个数，判断数量等于1时不能删除，营养与健康等于4时不能删除
	    
//	    FarmMessageExample be = new FarmMessageExample();        
//        be.createCriteria().andMessageTypeEqualTo(4);
//        int healthNum=farmMessageMapper.countByExample(be);
//	    if(healthNum<=4 ){
//	        return false;
//		}
	    
//	    FarmMessageExample ce = new FarmMessageExample();      
//	    FarmMessageExample de = new FarmMessageExample();
//	    FarmMessageExample fe = new FarmMessageExample();
//        ce.createCriteria().andMessageTypeEqualTo(1);
//        de.createCriteria().andMessageTypeEqualTo(2);
//        fe.createCriteria().andMessageTypeEqualTo(3);
//        int num1=farmMessageMapper.countByExample(ce);
//        int num2=farmMessageMapper.countByExample(de);
//        int num3=farmMessageMapper.countByExample(fe);
//        if(num1<=1 ||num2<=1||num3<=1){
//            return false;
//        }
	    
	    //如果是草稿，改变原数据参数
		FarmMessage fm = farmMessageMapper.selectByPrimaryKey(id);
		if( fm.getOriginalMessageId() != 0 ){//删除的是草稿，修改原数据为无草稿状态
			FarmMessage ad_par = farmMessageMapper.selectByPrimaryKey( fm.getOriginalMessageId() );
			ad_par.setStatus( ConstantsSql.Audit_Finish );
			farmMessageMapper.updateByPrimaryKey(ad_par);
		}
		//getTypebyId 删除相应类型的信息前判断剩余信息数量是否足够
		int rs = farmMessageMapper.deleteByPrimaryKey(id);
		FarmMessageExample ae = new FarmMessageExample();
		//删除草稿
		ae.createCriteria().andOriginalMessageIdEqualTo(id);
		farmMessageMapper.deleteByExample(ae);
		if(rs>0){
	    	return true;
	    }
		return false;
	}
	

	@Override
	public boolean updateFarmMessage(FarmMessage farmMessage) {
		try{
			Integer id = farmMessage.getId();
			//int rs = farmMessageMapper.updateByPrimaryKeySelective(farmMessage);
			//添加草稿文章数据
			farmMessage.setCreatTime(new Date());
			farmMessage.setStatus(ConstantsSql.Audit_Draft);
			//标记父文章标题
			farmMessage.setOriginalMessageId(id);
			farmMessage.setId(0);
			int rs = farmMessageMapper.insert(farmMessage);
			
			//更改原文章有草稿状态
			this.verifyFarmMessageStatus(id, ConstantsSql.Audit_Finish_hasDraft);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean verifyAddFarmMessage(Integer id) {
		return this.verifyFarmMessageStatus(id, ConstantsSql.Audit_Finish);
	}

	@Override
	public boolean verifyChangeFarmMessage(Integer id) {
		FarmMessage ad_draft = farmMessageMapper.selectByPrimaryKey( id );
		if( ad_draft.getOriginalMessageId() == 0 || ad_draft.getStatus() != ConstantsSql.Audit_Draft){
			return false;
		}
		//更新原数据
		FarmMessage ad_yum  = farmMessageMapper.selectByPrimaryKey( ad_draft.getOriginalMessageId() );
		ad_yum.setMessageName( ad_draft.getMessageName() );
		ad_yum.setSubTitle(ad_draft.getSubTitle());
		ad_yum.setCurrentMessage(ad_draft.getCurrentMessage());
		ad_yum.setStatus(0);
		farmMessageMapper.updateByPrimaryKeySelective(ad_yum);
		//删除草稿
		farmMessageMapper.deleteByPrimaryKey(id);
		return true;
	}

	@Override
	public List<FarmMessage> getFarmMessageDraftList() {
		FarmMessageExample ae = new FarmMessageExample();
		List<Integer> auditList = new ArrayList<Integer>();
		auditList.add(ConstantsSql.Audit_Draft);
		auditList.add(ConstantsSql.Audit_Publish_NoAuth);
		auditList.add(ConstantsSql.Audit_WaitDelete);
		ae.createCriteria().andStatusIn(auditList);
		List<FarmMessage> list = farmMessageMapper.selectByExample(ae);
		return list;
	}

	@Override
	public boolean verifyFarmMessageStatus(Integer id, Integer status) {
		FarmMessage farmMessage = new FarmMessage();
		farmMessage.setId(id);
		farmMessage.setStatus(status);
		int i = farmMessageMapper.updateByPrimaryKeySelective(farmMessage);
		if(i>0)
			return true;
		return false;
	}

	@Override
	public boolean changeTop(Integer id, Integer top) {
		FarmMessage farmMessage = new FarmMessage();
		farmMessage.setId(id);
		farmMessage.setTopOrNot(top);
		int i = farmMessageMapper.updateByPrimaryKeySelective(farmMessage);
		if(i>0)
			return true;
		return false;
	}

	@Override
	public FarmMessage updateFarmMessage(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
    public boolean deletePostFarmMessage(FarmMessage farmMessage) {
        int i = farmMessageMapper.updateByPrimaryKeySelective(farmMessage);
        if( i >0 ){
            return true;
        }
        return false;
    }   
	
}

