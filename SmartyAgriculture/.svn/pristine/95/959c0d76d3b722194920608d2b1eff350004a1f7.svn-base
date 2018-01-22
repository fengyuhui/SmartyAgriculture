package cn.bupt.smartyagl.controller;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.entity.autogenerate.Project;
import cn.bupt.smartyagl.service.IProjectService;

@Controller
@RequestMapping("/farmVisitsAudit")
public class FarmVisitsAudit {
	private int pageSize = Constants.PAGESIZE;
	@Autowired 
	private IProjectService projectService;
	
	/**
	 * 列出所有的已审核参观类型
	 * @return
	 */
	@RequestMapping(value="/auditIndex/{allPages}/{currentPage}/{type}")
	public ModelAndView auditPage(
			@PathVariable(value="allPages" ) int allPages,
			@PathVariable(value="currentPage") int currentPage,
			@PathVariable(value="type") String type) {
		
		if ("prvious".equals(type)) {
			if (currentPage > 1) {// 第一页不能往前翻页
				currentPage--;
			}
		} else if ("next".equals(type)) {
			currentPage++;
		} else if ("first".equals(type)) {
			currentPage = 1;
		} else if ("last".equals(type)) {
			currentPage = allPages;
		} else {
			currentPage = Integer.parseInt(type);
		}
		ModelAndView mv = new ModelAndView("farmVisits/audit");
		Page page = PageHelper.startPage(currentPage, pageSize);
		ArrayList<Project> list1 = (ArrayList<Project>) projectService.getAllByStatus(Constants.ADDED_ON_WAIT);
		ArrayList<Project> list2 = (ArrayList<Project>) projectService.getAllByStatus(Constants.EDITED);
		ArrayList<Project> list4 = (ArrayList<Project>) projectService.getAllByStatus(Constants.DELETE_ON_WAIT);
		List<Project> list = new ArrayList<Project>();
		list.addAll(list1);
		list.addAll(list2);
		list.addAll(list4);
		mv.addObject("project", list);
		allPages = page.getPages();
		mv.addObject("allPages", allPages);
		// 当前页码
		currentPage = page.getPageNum();
		mv.addObject("currentPage", currentPage);
		return mv;		
	}
	
	/**
	 * 同意新增项目，更改审计状态为正常
	 * @param id
	 * @return
	 */
	@RequestMapping("/acceptAdd/{id}")
	public ModelAndView acceptAdd(@PathVariable Integer id, RedirectAttributes attr) {
		ModelAndView mv = new ModelAndView("redirect:/farmVisits/index/0/1/prvious");
		boolean isDone = projectService.updateStatus(id, Constants.NORMAL);
		if (isDone)
			attr.addFlashAttribute("resultMsg", "删除成功等待审核！");
		else
			attr.addFlashAttribute("resultMsg", "删除失败！");
		return mv;
	}
	
	/**
	 * 拒绝修改请求，将原项目状态设为正常
	 * 拒绝新增请求
	 * 同意删除项目请求
	 * 将会删除掉这些项目信息
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	public ModelAndView denyAdd(@PathVariable Integer id, RedirectAttributes attr) {
		ModelAndView mv = new ModelAndView("redirect:/farmVisitsAudit/auditIndex/0/1/prvious");
		Integer auditStatus = projectService.getAuditStatusById(id);
		// 将编辑审核失败项目的源项目状态设为正常
		if (auditStatus.equals(Constants.EDITED)) {
			Integer sourceId = projectService.getSourceIdById(id);
			boolean isDone = projectService.updateStatus(sourceId, Constants.NORMAL);
			if (isDone)
				attr.addFlashAttribute("resultMsg", "源项目未状态恢复正常！");
			else
				attr.addFlashAttribute("resultMsg", "源项目未状态未恢复正常！");
			return mv;
		}
		boolean isDone2 = projectService.deleteType(id);
		if (isDone2)
			attr.addFlashAttribute("resultMsg", "删除成功等待审核！");
		else
			attr.addFlashAttribute("resultMsg", "删除失败！");
		return mv;
	}
	
	/**
	 * 同意修改项目请求，将修改信息审计状态设为正常，删除原项目
	 * @param id
	 * @param attr
	 * @return
	 */
	@RequestMapping("/acceptEdit/{id}")
	public ModelAndView acceptEdit(@PathVariable Integer id, RedirectAttributes attr) {
		ModelAndView mv = new ModelAndView("redirect:/farmVisits/index/0/1/prvious");
		// 将修改信息审计状态设为正常
		boolean isDone1 = projectService.updateStatus(id, Constants.NORMAL);
		// 删除原项目
		Integer sourceId = projectService.getSourceIdById(id);
		boolean isDone2 = projectService.deleteType(sourceId);
		if (isDone1 && isDone2)
			attr.addFlashAttribute("resultMsg", "编辑参观项目通过！");
		else
			attr.addFlashAttribute("resultMsg", "编辑参观项目未能正常通过，请检查错误！");
		return mv;
	}
	
	/**
	 * 撤销删除项目请求
	 * @param id
	 * @param attr
	 * @return
	 */
	@RequestMapping("/cancelDeleteRequest/{id}")
	public ModelAndView acceptDelete(@PathVariable Integer id, RedirectAttributes attr) {
		ModelAndView mv = new ModelAndView("redirect:/farmVisits/index/0/1/prvious");
		boolean isDone = projectService.updateStatus(id, Constants.NORMAL);
		if (isDone)
			attr.addFlashAttribute("resultMsg", "撤回删除项目请求成功！");
		else
			attr.addFlashAttribute("resultMsg", "撤回删除项目请求失败！");
		return mv;
	}
	
	
	
}
