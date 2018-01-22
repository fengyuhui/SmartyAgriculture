package cn.bupt.smartyagl.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bupt.smartyagl.dao.autogenerate.ProjectMapper;
import cn.bupt.smartyagl.entity.autogenerate.AdditionExample.Criteria;
import cn.bupt.smartyagl.entity.autogenerate.GoodsList;
import cn.bupt.smartyagl.entity.autogenerate.Project;
import cn.bupt.smartyagl.entity.autogenerate.ProjectExample;
import cn.bupt.smartyagl.service.IProjectService;

/**
 * 
 * @author TMing
 *
 */
@Service
public class ProjectServiceImpl implements IProjectService {
	
	@Autowired
	private ProjectMapper projectMapper;

	@Override
	public boolean addType(Project project) {
		int i = projectMapper.insertSelective(project);
		if (i > 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean deleteType(Integer id) {
		int i = projectMapper.deleteByPrimaryKey(id);
		if (i > 0)
			return true;
		else
			return false;
	}

	@Override
	public Project getType(int id) {
		Project project = projectMapper.selectByPrimaryKey(id);
		return project;
	}

	@Override
	public List<Project> getAllType() {
		System.out.println("getAllType");
		List<Project> list = new ArrayList<Project>();
		
//		ProjectExample example = new ProjectExample();
//		list = projectMapper.selectByExample(example);
//		cn.bupt.smartyagl.entity.autogenerate.ProjectExample.Criteria ctr = example.createCriteria();
//		List<Project> projectList = projectMapper.selectByExample(example);
        
		list = projectMapper.selectAll();
		if(list == null){
			System.out.println("list here null");
		}
		else
			System.out.println("list here");
//		return projectList;
		return list;
	}

	@Override
	public boolean updateType(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Project> getAllByStatus(Integer status) {
		List<Project> list = new ArrayList<Project>();
		ProjectExample example = new ProjectExample();
		cn.bupt.smartyagl.entity.autogenerate.ProjectExample.Criteria criteria = example.createCriteria();
		criteria.andAuditStatusEqualTo(status);
	
		list = projectMapper.selectByExample(example);
		return list;
	}

	@Override
	public boolean updateStatus(Integer id, Integer status) {
		Project project = new Project();
		project.setId(id);
		project.setAuditStatus(status);
		int i = projectMapper.updateByPrimaryKeySelective(project);
		if (i > 0)
			return true;
		else
			return false;
	
	}

	@Override
	public Integer getSourceIdById(Integer id) {
		return projectMapper.getSourceIdById(id);
	}

	@Override
	public Integer getAuditStatusById(Integer id) {
		return projectMapper.getAuditStatusById(id);
	}

}
