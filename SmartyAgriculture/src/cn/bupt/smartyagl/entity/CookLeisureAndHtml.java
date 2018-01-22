package cn.bupt.smartyagl.entity;

import cn.bupt.smartyagl.entity.autogenerate.CookLeisure;

public class CookLeisureAndHtml extends CookLeisure {
	
	private String detailUrl;
	private String messageDetailHTML;
	
	public String getMessageDetailHTML() {
		return messageDetailHTML;
	}
	public void setMessageDetailHTML(String messageDetailHTML) {
		this.messageDetailHTML = messageDetailHTML;
	}
	
	
	public void setDetailUrl(String detailUrl){
		this.detailUrl = detailUrl;
	}
	public String getDetailUrl(){
		return detailUrl;
	}
}
