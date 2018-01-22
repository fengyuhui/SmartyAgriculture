package cn.bupt.smartyagl.util;

import java.util.Random;

/**
 * 随机验证码
 * @author jm
 *
 */
public class RandomCodeUtil {
	/**
	 * 生成n位长随机英文数字组成代码
	 * @param length 位数
	 * @return
	 */
	public static String getRandomCode(int length){
		StringBuffer sb = new StringBuffer();
		String chars = "abcdefghijklmnopqrstuvwxyzZXCVBNMASDFGHJKLQWERTYUIOP0123456789";
		for(int i=0;i<length;i++){
			int j = new Random().nextInt(chars.length());
			sb.append(chars.charAt(j));
		}
		String str = sb.toString();
		return str;
	}
	
	/**
	 * 生成n位长随机数字组成代码
	 * @param length 位数
	 * @return
	 */
	public static String getNumberRandomCode(int length){
		StringBuffer sb = new StringBuffer();
		String chars = "0123456789";
		for(int i=0;i<length;i++){
			int j = new Random().nextInt(chars.length());
			sb.append(chars.charAt(j));
		}
		String str = sb.toString();
		return str;
	}
	
	/**
	 * 测试主函数
	 * @param args
	 */
	public static void main(String[] args) {
//		System.out.println(getRandomCode(6));
		Integer randomId = Integer.parseInt( RandomCodeUtil.getNumberRandomCode(6) );
		System.out.println(randomId);
	}
}
