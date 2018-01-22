package cn.bupt.smartyagl.util;

import java.security.MessageDigest;

/**
 *
 * Copyright 2015 YunRang Technology Co. Ltd., Inc. All rights reserved.
 *
 * @Author : lily
 *
 * @Description :
 *
 */

/**
 * 
 * @author ohaha
 *
 */
public class MD5Util {

  /**
   * Generate MD5 of a string
   *
   * @param str
   * @return
   */
  /* md5 加密 */
  public static String MD5(String str) {
    try {
      MessageDigest digest = MessageDigest.getInstance("MD5");
      digest.update(str.getBytes());
      // return getEncode16(digest);
      return getEncode32(digest);
    } catch (Exception e) {
    }
    return null;

  }

  /**
   * 32位加密
   *
   * @param digest
   * @return
   */
  private static String getEncode32(MessageDigest digest) {
    StringBuilder builder = new StringBuilder();
    for (byte b : digest.digest()) {
      builder.append(Integer.toHexString((b >> 4) & 0xf));
      builder.append(Integer.toHexString(b & 0xf));
    }
    return builder.toString();
  }

  /**
   * 16位加密
   *
   * @param digest
   * @return
   */
  private static String getEncode16(MessageDigest digest) {
    StringBuilder builder = new StringBuilder();
    for (byte b : digest.digest()) {
      builder.append(Integer.toHexString((b >> 4) & 0xf));
      builder.append(Integer.toHexString(b & 0xf));
    }
    // 16位加密，从第9位到25位
    return builder.substring(8, 24).toString();
  }

}
