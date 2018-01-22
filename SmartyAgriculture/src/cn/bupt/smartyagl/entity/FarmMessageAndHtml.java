package cn.bupt.smartyagl.entity;

import cn.bupt.smartyagl.entity.autogenerate.FarmMessage;

/** 
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-9-13 下午2:14:36 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public class FarmMessageAndHtml extends FarmMessage{
	/**
	 * 页面详情url
	 */
	private String detailUrl;
	private String messageDetailHTML;

	public String getMessageDetailHTML() {
		return messageDetailHTML;
	}

	public void setMessageDetailHTML(String messageDetailHTML) {
		this.messageDetailHTML = messageDetailHTML;
	}

	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}
}
