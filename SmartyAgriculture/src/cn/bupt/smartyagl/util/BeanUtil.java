package cn.bupt.smartyagl.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class BeanUtil {
     static public void fatherToChild (Object father,Object child)throws Exception{  
        if(!(child.getClass().getSuperclass()==father.getClass())){  
            throw new Exception("child不是father的子类");  
        }  
        Class<? extends Object> fatherClass= father.getClass();  
        Field ff[]= fatherClass.getDeclaredFields();  
        for(int i=0;i<ff.length;i++){
        	Field f=ff[i];//取出每一个属性
        	f.setAccessible(true);
//            Class<?> type=f.getType();  
            Method m=fatherClass.getMethod("get"+upperHeadChar(f.getName()));//方法
            Object obj=m.invoke(father);//取出属性值                
            f.set(child,obj);  
        }  
    }  
    
    static private String upperHeadChar(String in){
    	String head=in.substring(0,1);
    	String out = head.toUpperCase()+in.substring(1,in.length());
    	return out;
    }
}


