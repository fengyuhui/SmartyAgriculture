package cn.bupt.smartyagl.entity;
/** 
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-6-24 上午9:44:47 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public class JpushBean {
	/**
	 * 推送消息内容
	 */
	private String content;
	
	/**
	 * 推送消息类型
	 */
	private Integer type;
	/**
	 * 消息类型
	 */
	private String title;
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
