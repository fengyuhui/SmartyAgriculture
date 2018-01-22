package cn.bupt.smartyagl.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bupt.smartyagl.constant.ConstantsSql;
import cn.bupt.smartyagl.dao.autogenerate.CameraMapper;
import cn.bupt.smartyagl.entity.autogenerate.Camera;
import cn.bupt.smartyagl.entity.autogenerate.CameraExample;
import cn.bupt.smartyagl.service.ICameraService;

/**
 * 摄像头接口
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-9-22 下午2:30:11 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
@Service
public class CameraServiceImpl implements ICameraService{

	@Autowired
	CameraMapper cameraMapper;
	
	@Override
	public boolean addCamera(Camera camera) {
		if( camera.getStatus() == null ){
			camera.setStatus( ConstantsSql.Audit_Publish_NoAuth );
		}
		int rs =  cameraMapper.insert(camera);
		if(rs > 0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean deleteCamera(Integer cameraId) {
		int rs = cameraMapper.deleteByPrimaryKey(cameraId);
		if(rs > 0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public List<Camera> getCameraList() {
//		CameraExample ce = new CameraExample();
//		return cameraMapper.selectByExample( ce );
		/**
		 * 写死
		 */
		Camera camera = new Camera();
		camera.setIp(getRedirectInfo());
		camera.setIs_top(1);
		camera.setName("admin");
		camera.setPasswd("hnyl123456");
		camera.setDevice_port("8000");
		camera.setPort("85");
		camera.setStatus(1);
		camera.setTitle("默认摄像头");
		List list = new ArrayList<Camera>();
		list.add(camera);
		return list;
	}

	@Override
	public boolean changeTop(Integer cameraId, Integer topId) {
		Camera camera = new Camera();
		camera.setId( cameraId );
		camera.setIs_top( topId );
		int rs = cameraMapper.updateByPrimaryKeySelective( camera );
		if(rs > 0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Camera findCamera(Integer id) {
		return cameraMapper.selectByPrimaryKey(id);
	}

	public static String getRedirectInfo(){
		  String ip = ""; 
		  HttpClient httpClient = new DefaultHttpClient();
		  HttpContext httpContext = new BasicHttpContext();
		  HttpGet httpGet = new HttpGet("http://www.hik-online.com/hnyl123456");
		  try {
		   //将HttpContext对象作为参数传给execute()方法,则HttpClient会把请求响应交互过程中的状态信息存储在HttpContext中
		   HttpResponse response = httpClient.execute(httpGet, httpContext);
		   //获取重定向之后的主机地址信息,即"http://127.0.0.1:8088"
		   HttpHost targetHost = (HttpHost)httpContext.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
		   //获取实际的请求对象的URI,即重定向之后的"/blog/admin/login.jsp"
		   HttpUriRequest realRequest = (HttpUriRequest)httpContext.getAttribute(ExecutionContext.HTTP_REQUEST);
		   System.out.println("主机地址:" + targetHost);
		   System.out.println("URI信息:" + realRequest.getURI());
		   HttpEntity entity = response.getEntity();
		   if(null != entity){
			String content = EntityUtils.toString(entity, ContentType.getOrDefault(entity).getCharset());
		    EntityUtils.consume(entity);
		    String regex = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";  
		    Pattern p = Pattern.compile(regex);  
		    Matcher m = p.matcher(content);  
		    while (m.find()) { 
		    	ip = m.group();
		    }         
		   }
		  // EntityUtils.toString(entity, ContentType.getOrDefault(entity).getCharset());
		   
		  } catch (Exception e) {
		   e.printStackTrace();
		  }finally{
		   httpClient.getConnectionManager().shutdown();
		   return ip;
		  }
		
		 }
}
