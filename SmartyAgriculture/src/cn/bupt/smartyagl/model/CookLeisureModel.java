package cn.bupt.smartyagl.model;

import cn.bupt.smartyagl.entity.autogenerate.FarmMessage;
	/**
	 * 
	 *<p>Title:FarmMessageModel</p>
	 *<p>Description:农场信息视图 </p>
	 * @date 
	 * @author 
	 */
public class CookLeisureModel {
	    public String dateString;
	    public FarmMessage FarmMessage;
	    public String adminName;
	    public int status;
	    
	    public String getAdminName() {
	        return adminName;
	    }
	    public void setAdminName(String adminName) {
	        this.adminName = adminName;
	    }
	    public int getStatus() {
	        return status;
	    }
	    public void setStatus(int status) {
	        this.status = status;
	    }
	    public String getDateString() {
	        return dateString;
	    }
	    public void setDateString(String dateString) {
	        this.dateString = dateString;
	    }
	    public FarmMessage getFarmMessage() {
	        return FarmMessage;
	    }
	    public void setFarmMessage(FarmMessage farmMessage) {
	        this.FarmMessage = farmMessage;
	    }
	}

