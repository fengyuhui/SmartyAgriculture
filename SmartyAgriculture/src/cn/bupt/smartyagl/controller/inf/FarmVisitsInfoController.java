package cn.bupt.smartyagl.controller.inf;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.bupt.smartyagl.entity.autogenerate.FarmVisits;
import cn.bupt.smartyagl.entity.autogenerate.Project;
import cn.bupt.smartyagl.service.IFarmVisitsService;
import cn.bupt.smartyagl.service.IProjectService;
import cn.bupt.smartyagl.service.impl.FarmVisitsServiceImpl;
import cn.bupt.smartyagl.service.impl.ProjectServiceImpl;
import cn.bupt.smartyagl.util.NetDataAccessUtil;
import cn.bupt.smartyagl.util.ProjectDataUtil;

/**
 * 农场参观相关的方法
 * @author TMing
 *
 */
@Controller
@RequestMapping("/interface/farmVisit")
public class FarmVisitsInfoController {
	
	@Autowired
	IFarmVisitsService farmVisitsService;
	
	@Autowired 
	IProjectService projectService;
	
	@RequestMapping("/getAllVisitTypes")
	@ResponseBody
	public Object getAllVisitTypes() {
		System.out.println("ereee");
		NetDataAccessUtil nau = new NetDataAccessUtil();
		List<ProjectDataUtil> projectDataList = new ArrayList<ProjectDataUtil>();
		//Integer d = projectService.getSourceIdById(ss);
		//System.out.println("d :"+d);
		
		List<Project> projectList = projectService.getAllType();//用Example代替可以获取
		
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("projectList"+projectList);
		for (Project project: projectList) {
			ProjectDataUtil pdu = new ProjectDataUtil();
			pdu.setId(project.getId());
			System.out.println("id "+project.getId());
			pdu.setProjectTitle(project.getTitle());
			pdu.setCreateTime(project.getCreateTime());
			String typeIdStr = project.getTypes();
			String imgPathStr = project.getImages();
			//String typeIdStr = "[43]";
			//String imgPathStr = "[\"/upload/farmVisits/20161223/910fcc9e2b0b453ab6b16d08d202d3a2.jpg\"]";
			
			List<Integer> typeIdList = new ArrayList<Integer>();
			List<String> imgPahtList = new ArrayList<String>();
			
			try {
				typeIdList = mapper.readValue(typeIdStr, new TypeReference<List<Integer>>() {});
				imgPahtList = mapper.readValue(imgPathStr, new TypeReference<List<String>>() {});
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("error");
				e.printStackTrace();
			} 
			List<FarmVisits> typeList = farmVisitsService.getFarmVisitTypesBatch(typeIdList);
			System.out.println("success");
			pdu.setTypeList(typeList);
			pdu.setImgList(imgPahtList);
			projectDataList.add(pdu);
		}
		List<FarmVisits> list = farmVisitsService.getFarmVisitTypes();
		nau.setContent(projectDataList);
		nau.setResult(1);
		if (null == list)
			
			nau.setResultDesp("没有类型信息");
		else 
			nau.setResultDesp("查询成功");
		return nau;
	}
	
}
	
	