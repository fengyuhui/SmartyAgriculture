package cn.bupt.smartyagl.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 组织发送的数据 
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-5-13 下午2:56:42 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public class NetDataAccessUtil {
	/**
	 * 返回结果内容
	 */
	private Object content;
	/**
	 * 结果标识符
	 */
	private int result;
	/**
	 * 结果标识符
	 */
	private Object resultDesp;
	
	public Map generateResultSet(){
		Map<String, Object>map = new HashMap<String, Object>();
		map.put("content", this.content);
		map.put("resultDesp", this.resultDesp);
		map.put("result", this.result);
		return map;
	}
	
	public void setContent(Object content) {
		this.content = content;
	}
	
	public void setResult(int result) {
		this.result = result;
	}
	
	public void setResultDesp(Object resultDesp) {
		this.resultDesp = resultDesp;
	}

	public Object getContent() {
		return content;
	}

	public int getResult() {
		return result;
	}

	public Object getResultDesp() {
		return resultDesp;
	}
	
	
}
