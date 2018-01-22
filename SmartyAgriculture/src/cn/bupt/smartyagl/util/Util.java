package cn.bupt.smartyagl.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.bupt.smartyagl.constant.Constants;

public class Util {

    /**
     * 显示信息，错误信息
     * 
     * @param action
     *            操作名
     * @param content
     *            显示内容
     * @param redirect
     *            显示内容后跳转页面
     * @param timeout
     *            等待跳转时间
     * @return
     */
    public static String message(String action, String content, String redirect,
            int timeout, HttpServletRequest request) {
        if ("".equals(redirect)) {
            redirect = "home/home";
        }
        String title = "操作未完成";
        String className = "error";
        String status = "✘";
        String script = "";
        if ("error".equals(action)) {
            script = "<div class=\"go\">系统自动跳转在 <span id=\"time\">"
                    + timeout
                    + "</span> 秒钟后，如果不想等待 > <a href=\""
                    + redirect
                    + "\">点击这里跳转</a><script>function redirect(url) {window.location.href = url;}setTimeout(\"redirect(\'"
                    + redirect + "\');\"," + timeout * 1000 + ");</script>";
        }
        if ("errorBack".equals(action)) {
            script = "<a href=\"" + redirect + "\" >[点这里返回上一页]</a>;";
        }

        request.setAttribute("title", title);
        request.setAttribute("className", className);
        request.setAttribute("status", status);
        request.setAttribute("script", script);
        request.setAttribute("content", content);
        return Constants.ERROR_PAGE;
    }

    /**
     * 
     * 获取对象属性，返回一个字符串数组
     * 
     * @param o对象
     * @return String[] 字符串数组
     */

    private static String[] getFiledName(Object o) {
        try {
            Field[] fields = o.getClass().getDeclaredFields();
            String[] fieldNames = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                fieldNames[i] = fields[i].getName();
            }
            return fieldNames;
        } catch (SecurityException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
        return null;

    }

    /**
     * 
     * 使用反射根据属性名称获取属性值
     * 
     * 
     * 
     * @param fieldName
     *            属性名称
     * 
     * @param o
     *            操作对象
     * 
     * @return Object 属性值
     */

    private static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
            System.out.println("属性不存在");
            return null;
        }
    }

    /**
     * 检测对象的属性是否为空
     * 
     * @param o
     * @return
     */
    public static boolean checkIsNull(Object o) {
        String[] str = Util.getFiledName(o);
        for (int i = 0; i < str.length; i++) {
            if (Util.getFieldValueByName(str[i], o) != null)
                return false;
        }
        return true;
    }

    /**
     * @Description:把数组转换为一个用逗号分隔的字符串 ，以便于用in+String 查询
     */
    public static String converToString(String[] ig) {
        String str = "";
        if (ig != null && ig.length > 0) {
            for (int i = 0; i < ig.length; i++) {
                str += ig[i] + ",";
            }
        }
        str = str.substring(0, str.length() - 1);
        return str;
    }

    /**
     * @Description:把list转换为一个用逗号分隔的字符串
     */
    public static String listToString(List list) {
        StringBuilder sb = new StringBuilder();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (i < list.size() - 1) {
                    sb.append(list.get(i) + ",");
                } else {
                    sb.append(list.get(i));
                }
            }
        }
        return sb.toString();
    }

    /**
     * 去除list中重复的值
     * 
     * @param list
     */
    public static List removeDuplicate(List list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }
}
