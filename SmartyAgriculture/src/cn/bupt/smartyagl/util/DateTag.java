package cn.bupt.smartyagl.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class DateTag extends TagSupport {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String value;

    @Override
    public int doStartTag() throws JspException {
        String vv = "" + value;
        long time = Long.valueOf(vv);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        SimpleDateFormat dateformat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String s = dateformat.format(c.getTime());
        try {
            pageContext.getOut().write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.doStartTag();
    }

    public void setValue(String value) {
        this.value = value;
    }
    /**
	 * 将date时间格式化成字符串
	 * @param date
	 * @return
	 * @author waiting
	 */
	public static String dateTimaFormat(Date date){
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str=sdf.format(date);
        return str;
	}
	/**
	 * 将字符串转成Date
	 * @param date
	 * @return
	 */
	public static  Date stringConvertDate(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date1=null;
		try {
			date1 = (Date) formatter.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date1;
	}
}
