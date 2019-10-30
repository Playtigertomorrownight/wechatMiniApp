package com.smallrain.wechat.utils;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import com.smallrain.wechat.models.user.entity.SysUser;

public class AuthUtil {

  private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
  public static final String ALGORITHM_NAME = "md5"; // 基础散列算法
  public static final int HASH_ITERATIONS = 2; // 自定义散列次数


  /**
   * 密码加密
   * 
   * @param user
   */
  public static void encryptPassword(SysUser user, boolean init) {
    // User对象包含最基本的字段Username和Password
    if (init) {
      user.setSalt(randomNumberGenerator.nextBytes().toHex());
    }
    // 将用户的注册密码经过散列算法替换成一个不可逆的新密码保存进数据，散列过程使用了盐
    user.setPassword(encryptPassword(user.getPassword(),user.getCredentialsSalt()));
  }

  public static String encryptPassword(String password, String salt) {
    // 将用户的注册密码经过散列算法替换成一个不可逆的新密码保存进数据，散列过程使用了盐
    String newPassword = new SimpleHash(ALGORITHM_NAME, password, ByteSource.Util.bytes(salt), HASH_ITERATIONS).toHex();
    return newPassword;
  }

}
