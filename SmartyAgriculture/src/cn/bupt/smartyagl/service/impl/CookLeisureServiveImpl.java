package cn.bupt.smartyagl.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.bupt.smartyagl.constant.ConstantsSql;
import cn.bupt.smartyagl.dao.autogenerate.CookLeisureMapper;
import cn.bupt.smartyagl.entity.CookLeisureAndHtml;
import cn.bupt.smartyagl.entity.autogenerate.CookLeisure;
import cn.bupt.smartyagl.entity.autogenerate.CookLeisureExample;
import cn.bupt.smartyagl.service.ICookLeisureService;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"classpath*:/spring-*.xml","classpath*:/spring.xml"})
//@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class})
@Service
public class CookLeisureServiveImpl implements ICookLeisureService{

	@Autowired
	private CookLeisureMapper cookLeisureMapper;
	
	@Test
	public void test(){
		//List<CookLeisure> list = this.getCookLeisureList(2, 10);
		System.out.println("t");
	}
	
	@Override
	public List<CookLeisure> getCookLeisureList(CookLeisure cookLeisureSelective) {
		CookLeisureExample ae=new CookLeisureExample();
		cn.bupt.smartyagl.entity.autogenerate.CookLeisureExample.Criteria ca =  ae.createCriteria();
		
		//标题名模糊搜索
		if(cookLeisureSelective != null && cookLeisureSelective.getMessageName() != null){
			ca.andMessageNameLike("%"+cookLeisureSelective.getMessageName()+"%");
		}
		
		ae.setOrderByClause("topOrNot desc,creatTime desc");
		List<Integer> publishList = new ArrayList<Integer>();
		publishList.add(ConstantsSql.Audit_Finish);
		publishList.add(ConstantsSql.Audit_Finish_hasDraft);
		ca.andStatusIn(publishList);
		List<CookLeisure> list = cookLeisureMapper.selectByExample(ae);
		return list;		
//		List<CookLeisure> cookLeisureList=cookLeisureMapper.selectByExample(ae);
//		return cookLeisureMapper.selectByExample(ae);
	}
	
	@Override
	public CookLeisure findCookLeisure(Integer id) {
		List<CookLeisure> cookLeisure = null;
		CookLeisureExample ge = new CookLeisureExample();
		 ge.createCriteria().andIdEqualTo(id);
		 cookLeisure = cookLeisureMapper.selectByExampleWithBLOBs(ge);
		CookLeisure ad =  cookLeisureMapper.selectByPrimaryKey( id );
		
		return ad;
	}
	
	@Override
	public CookLeisure getCookLeisureDetail(Integer id) {
		List<CookLeisure> cookLeisure = null;
		try{
			 CookLeisureExample ge = new CookLeisureExample();
			 ge.createCriteria().andIdEqualTo(id);
			 cookLeisure = cookLeisureMapper.selectByExampleWithBLOBs(ge);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		if(cookLeisure.size() <= 0){
			return null;
		}
		
		return cookLeisure.get(0);
	}
	
	@Override
	public CookLeisureAndHtml convertMessage(CookLeisure cookLeisure) throws IllegalArgumentException, IllegalAccessException {
		CookLeisureAndHtml gc = new CookLeisureAndHtml();
		Field[] message_fields = CookLeisure.class.getDeclaredFields();
		
		for(Field field : message_fields){
			field.setAccessible(true);//可强行访问
			field.set(gc, field.get(cookLeisure));
		}
		return gc;
	}
	
	@Override
	public String getMessageName(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean addCookLeisures(CookLeisure cookLeisure) throws IllegalArgumentException, IllegalAccessException{
//		if(cookLeisure.getTopOrNot()!= null && cookLeisure.getMessageType() != null 
//				&& cookLeisure.getCurrentMessage()!=null){
			cookLeisure.setCreatTime(new Date());
			cookLeisure.setStatus(ConstantsSql.Audit_Publish_NoAuth);
			cookLeisure.setOriginalMessageId(0);
//			cookLeisure.setTopOrNot(0);
			int i =cookLeisureMapper.insert(cookLeisure);
			// TODO Auto-generated method stub
			if(i!=0)
	            return true;
	        else
	            return false;
//			}
//		else {
//			return false;
//		}		
	}

	@Override
	public CookLeisure getCookLeisureById(int id) {
		// TODO Auto-generated method stub
		return cookLeisureMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean deleteCookLeisure(Integer id) {
//农场信息list中，扫描每种类型的信息个数，判断数量等于1时不能删除，营养与健康等于4时不能删除
	    
//		CookLeisureExample be = new CookLeisureExample();        
//        be.createCriteria().andMessageTypeEqualTo(4);
//        int healthNum=cookLeisureMapper.countByExample(be);
//	    if(healthNum<=4 ){
//	        return false;
//		}
	    
//	    CookLeisureExample ce = new CookLeisureExample();      
//	    CookLeisureExample de = new CookLeisureExample();
//	    CookLeisureExample fe = new CookLeisureExample();
//        ce.createCriteria().andMessageTypeEqualTo(1);
//        de.createCriteria().andMessageTypeEqualTo(2);
//        fe.createCriteria().andMessageTypeEqualTo(3);
//        int num1=cookLeisureMapper.countByExample(ce);
//        int num2=cookLeisureMapper.countByExample(de);
//        int num3=cookLeisureMapper.countByExample(fe);
//        if(num1<=1 ||num2<=1||num3<=1){
//            return false;
//        }
		//如果是草稿，改变原数据参数
		CookLeisure fm = cookLeisureMapper.selectByPrimaryKey(id);
		if( fm.getOriginalMessageId() != 0 ){//删除的是草稿，修改原数据为无草稿状态
			CookLeisure ad_par = cookLeisureMapper.selectByPrimaryKey( fm.getOriginalMessageId() );
			ad_par.setStatus( ConstantsSql.Audit_Finish );
			cookLeisureMapper.updateByPrimaryKey(ad_par);
		}
		int rs = cookLeisureMapper.deleteByPrimaryKey(id);
		CookLeisureExample ae = new CookLeisureExample();
		//删除草稿
		ae.createCriteria().andOriginalMessageIdEqualTo(id);
		cookLeisureMapper.deleteByExample(ae);
		if(rs>0){
	    	return true;
	    }
		return false;
	}

	@Override
	public boolean verifyAddCookLeisure(Integer id) {
		return this.verifyCookLeisureStatus(id, ConstantsSql.Audit_Finish);
	}

	@Override
	public boolean verifyChangeCookLeisure(Integer id) {
		CookLeisure ad_draft = cookLeisureMapper.selectByPrimaryKey( id );
		if( ad_draft.getOriginalMessageId() == 0 || ad_draft.getStatus() != ConstantsSql.Audit_Draft){
			return false;
		}
		
		//更新原数据
		CookLeisure ad_yum  = cookLeisureMapper.selectByPrimaryKey( ad_draft.getOriginalMessageId() );
//		String oldPath = ad_yum.getIcon();
		ad_yum.setMessageName( ad_draft.getMessageName() );
		ad_yum.setStatus(ConstantsSql.Audit_Finish);
		ad_yum.setIcon(ad_draft.getIcon());
		ad_yum.setCurrentMessage(ad_draft.getCurrentMessage());
		ad_yum.setMessageName(ad_draft.getMessageName());
		ad_yum.setSubTitle(ad_draft.getSubTitle());
		ad_yum.setDietitian(ad_draft.getDietitian());
		ad_yum.setTopOrNot(ad_draft.getTopOrNot());
		cookLeisureMapper.updateByPrimaryKeySelective(ad_yum);
		//删除草稿
//		ImageUtil.deleteFileByPath(oldPath);
		cookLeisureMapper.deleteByPrimaryKey(id);
		return true;
	}

	@Override
	public List<CookLeisure> getCookLeisureDraftList() {
		CookLeisureExample ae = new CookLeisureExample();
		List<Integer> auditList = new ArrayList<Integer>();
		auditList.add(ConstantsSql.Audit_Draft);
		auditList.add(ConstantsSql.Audit_Publish_NoAuth);
		auditList.add(ConstantsSql.Audit_WaitDelete);
		ae.createCriteria().andStatusIn(auditList);
		List<CookLeisure> list = cookLeisureMapper.selectByExample(ae);
		return list;
	}


	@Override
	public boolean changeTop(Integer id, Integer top) {
		CookLeisure cookLeisure = new CookLeisure();
		cookLeisure.setId(id);
		cookLeisure.setTopOrNot(top);
		int i = cookLeisureMapper.updateByPrimaryKeySelective(cookLeisure);
		if(i>0)
			return true;
		return false;
	}

//	@Override
//	public CookLeisure updateCookLeisure(Integer id) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	@Override
	public boolean updateCookLeisure(CookLeisure cookLeisure) {
		try{
			Integer id = cookLeisure.getId();
//			int rs = cookLeisureMapper.updateByPrimaryKeySelective(cookLeisure);
			//添加草稿文章数据
			cookLeisure.setCreatTime(new Date());
			cookLeisure.setStatus(ConstantsSql.Audit_Draft);
			//标记父文章标题
			cookLeisure.setOriginalMessageId(id);
			cookLeisure.setId(null);
			int rs = cookLeisureMapper.insert(cookLeisure);
			
			//更改原文章有草稿状态
			this.verifyCookLeisureStatus(id, ConstantsSql.Audit_Finish_hasDraft);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public boolean verifyCookLeisureStatus(Integer id, Integer status) {
		CookLeisure cookLeisure = new CookLeisure();
		cookLeisure.setId(id);
		cookLeisure.setStatus(status);
		int i = cookLeisureMapper.updateByPrimaryKeySelective(cookLeisure);
		if(i>0)
			return true;
		return false;
	}	
	/*@Override
	public boolean updateCookLeisure(CookLeisure cookLeisure) {
		try{
			Date createTimeDate=new Date();//设置商品创建时间
			cookLeisure.setCreatTime(createTimeDate);
//			cookLeisure.setOriginalMessageId(0);
//			cookLeisure.setCurrentMessage("56");
//			int rs = cookLeisureMapper.insert(cookLeisure);//cookLeisureMapper.updateByPrimaryKeySelective(cookLeisure);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}*/
	
	@Override
	public boolean updateAuditStatus(int id,int status,HttpServletRequest request) {
		// TODO Auto-generated method stub
		CookLeisure cookLeisure=cookLeisureMapper.selectByPrimaryKey(id);
		if (cookLeisure.getStatus()==ConstantsSql.Audit_Publish_NoAuth) {
			//发布未审核
			return this.updateAuditAdd(id, status, request);
		}
		else if(cookLeisure.getStatus()==ConstantsSql.Audit_WaitDelete){
			//删除未审核
			return this.updateAuditDelete(id,status, request);
		}
		else{
			//编辑未审核
			return this.updateAuditEdit(id, status, request);
		}
	}
	
	/**
	 * 添加 修改商品审核状态
	 * @return
	 */
	private boolean updateAuditAdd(int id,int status,HttpServletRequest request) {
		CookLeisure cookLeisure=cookLeisureMapper.selectByPrimaryKey(id);
		int i=0;
		if(status==1){
			//审核通过
			cookLeisure.setStatus(ConstantsSql.Audit_Finish);
			 i=cookLeisureMapper.updateByPrimaryKey(cookLeisure);
		}
		else{
			i=this.deleteCookLeisure(id, request);
		}
		if(i==1){
			return true;
		}
		else{
			return false;
		}	
	}
	
	/**
	 * 编辑后修改审核状态
	 * @param id
	 * @param status
	 * @param request
	 * @return
	 */
	private boolean updateAuditEdit(int id,int status,HttpServletRequest request){
		CookLeisure cookLeisure=cookLeisureMapper.selectByPrimaryKey(id);
		int i=0;
		if(status==1){
			//审核通过
			CookLeisure goodsSrc=cookLeisureMapper.selectByPrimaryKey(cookLeisure.getOriginalMessageId());
			//i=this.deleteGoods(goodsSrc, request);
			cookLeisure.setId(goodsSrc.getId());
			cookLeisure.setStatus(ConstantsSql.Audit_Finish);
			cookLeisure.setOriginalMessageId(0);
			cookLeisureMapper.updateByPrimaryKey(cookLeisure);
			i=cookLeisureMapper.deleteByPrimaryKey(id);
		}
		else{
			//审核不通过
			CookLeisure goodsSrc=cookLeisureMapper.selectByPrimaryKey(cookLeisure.getOriginalMessageId());
			i=this.deleteCookLeisure(id, request);//删除编辑的信息
			
			goodsSrc.setStatus(ConstantsSql.Audit_Finish);
			i=cookLeisureMapper.updateByPrimaryKey(goodsSrc);
		}
		if(i==1){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * 删除后修改审核状态
	 * @return
	 */
	private boolean updateAuditDelete(int id,int status,HttpServletRequest request) {
		CookLeisure cookLeisure=cookLeisureMapper.selectByPrimaryKey(id);
		int i=0;
		if(status==1){
			//审核通过
			i=this.deleteCookLeisure(id, request);			 
		}
		else{
			cookLeisure.setStatus(ConstantsSql.Audit_Finish);
			i=cookLeisureMapper.updateByPrimaryKey(cookLeisure);
		}
		if(i==1){
			return true;
		}
		else{
			return false;
		}	
	}
	
	@Override
	public List<CookLeisure> getCookLeisureList(Integer typeId,Integer pageSize ,Integer pageNum,Map<String,Object> m) {
		//限制查询个数
		Page page = PageHelper.startPage( pageNum, pageSize, "topOrNot desc,creatTime desc");
		
		CookLeisureExample fe = new CookLeisureExample();
		cn.bupt.smartyagl.entity.autogenerate.CookLeisureExample.Criteria ca = fe.createCriteria();
		//商品状态
		ArrayList<Integer> status = new ArrayList<Integer>();
		status.add( ConstantsSql.Audit_Finish );
		status.add( ConstantsSql.Audit_Finish_hasDraft );
		ca.andStatusIn( status );
		//商品类型
		ca.andMessageTypeEqualTo(typeId);
		List<CookLeisure> list = cookLeisureMapper.selectByExample( fe );
		m.put("allPages",page.getPages());
		return list;
	}

	@Override
    public boolean deletePostCookLeisure(CookLeisure cookLeisure) {
        int i = cookLeisureMapper.updateByPrimaryKeySelective(cookLeisure);
        if( i >0 ){
            return true;
        }
        return false;
    }
	/**
	 * 删除商品
	 * @param 
	 * @param request
	 * @return
	 */
	private int deleteCookLeisure(Integer id,HttpServletRequest request) {
		CookLeisure cookLeisure=cookLeisureMapper.selectByPrimaryKey(id);
		int i=cookLeisureMapper.deleteByPrimaryKey(cookLeisure.getId());
		return i;
	}
	
}

