package cn.bupt.smartyagl.util;

import java.util.Date;
import java.util.List;

import cn.bupt.smartyagl.entity.autogenerate.FarmVisits;
import cn.bupt.smartyagl.entity.autogenerate.Project;

public class ProjectDataUtil {

	private Integer id;
	private String projectTitle;
	private List<String> imgList;
	private List<FarmVisits> typeList;
	private Date createTime;
	private String urlStr;
	private String detail;
	
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getUrlStr() {
		return urlStr;
	}
	public void setUrlStr(String urlStr) {
		this.urlStr = urlStr;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getProjectTitle() {
		return projectTitle;
	}
	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}
	public List<String> getImgList() {
		return imgList;
	}
	public void setImgList(List<String> imgList) {
		this.imgList = imgList;
	}
	public List<FarmVisits> getTypeList() {
		return typeList;
	}
	public void setTypeList(List<FarmVisits> typeList2) {
		this.typeList = typeList2;
	}
	
	
}
