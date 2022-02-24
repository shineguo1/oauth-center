package pers.gxj.security.oauth2.framework.security.util;

import org.apache.ibatis.executor.loader.cglib.CglibProxyFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pers.gxj.security.oauth2.common.exception.BizException;

/**
 * 安全服务工具类
 *
 * @author casaba
 */
public class SecurityUtils {
    /**
     * 获取用户账户
     **/
    public static String getUsername() {
        try {
            return getLoginUser().getUsername();
        } catch (Exception e) {
            throw new BizException("获取用户信息异常");
        }
    }

    /**
     * 获取用户账户
     **/
    public static String getUsernameOrDefault(String s) {
        try {
            UserDetails user = getLoginUser();
            return user == null ? s : user.getUsername();
        } catch (Exception e) {
            return s;
        }
    }

    /**
     * 获取用户
     **/
    public static UserDetails getLoginUser() {
        try {
            return (UserDetails) getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new BizException("获取用户信息异常");
        }
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword     真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}
