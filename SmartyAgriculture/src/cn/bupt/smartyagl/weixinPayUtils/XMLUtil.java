package cn.bupt.smartyagl.weixinPayUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;


public class XMLUtil {
	/**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 * @param strxml
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Map<String,String> doXMLParse(String strxml) throws JDOMException, IOException {
		strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

		if(null == strxml || "".equals(strxml)) {
			return null;
		}
		Map<String,String> m = new HashMap<String,String>();
		
		InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		@SuppressWarnings("rawtypes")
		List list = root.getChildren();
		@SuppressWarnings("rawtypes")
		Iterator it = list.iterator();
		while(it.hasNext()) {
			Element e = (Element) it.next();
			String k = e.getName();
			String v = "";
			@SuppressWarnings("rawtypes")
			List children = e.getChildren();
			if(children.isEmpty()) {
				v = e.getTextNormalize();
			} else {
				v = XMLUtil.getChildrenText(children);
			}
			m.put(k, v);
		}
		//关闭流
		in.close();
		return m;
	}
	
	/**
	 * 获取子结点的xml
	 * @param children
	 * @return String
	 */
	public static String getChildrenText(@SuppressWarnings("rawtypes") List children) {
		StringBuffer sb = new StringBuffer();
		if(!children.isEmpty()) {
			@SuppressWarnings("rawtypes")
			Iterator it = children.iterator();
			while(it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				@SuppressWarnings("rawtypes")
				List list = e.getChildren();
				sb.append("<" + name + ">");
				if(!list.isEmpty()) {
					sb.append(XMLUtil.getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}
		return sb.toString();
	}
	/**
	 * 判断字符串是否是数字串
	 * @param str
	 * @return
	 */
    //用正则表达式判断字符串是否为数字（含负数）
    public static boolean isNumeric(String str) {
        String regEx = "^-?[0-9]+$";
        Pattern pat = Pattern.compile(regEx);
        Matcher mat = pat.matcher(str);
        if (mat.find()) {
            return true;
        }
        else {
            return false;
        }
    }
	/**
	 * 将SortedMap中对象转为xml字符串
	 * @param arr
	 * @return
	 */
	 public static String ArrayToXml(SortedMap<String, String> arr) {  
	        String xml = "<xml>";   
	        Iterator<Entry<String, String>> iter = arr.entrySet().iterator();  
	        while (iter.hasNext()) {  
	            Entry<String, String> entry = iter.next();  
	            String key = entry.getKey();  
	            String val = entry.getValue();  
	            if (isNumeric(val)) {  
	                xml += "<" + key + ">" + val + "</" + key + ">";  
	            } else  
	                xml += "<" + key + "><![CDATA[" + val + "]]></" + key + ">";  
	        }  
	        xml += "</xml>";
	        return xml;
	    }  
}
