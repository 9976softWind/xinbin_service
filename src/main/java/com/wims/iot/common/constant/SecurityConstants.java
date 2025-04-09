package com.wims.iot.common.constant;

/**
 * Security 常量
 *
 * @author haoxr
 * @since 2.0.0
 */
public interface SecurityConstants {

    /**
     * 登录接口路径
     */
    String LOGIN_PATH = "/api/v1/auth/login";

    /**
     * 登录接口路径
     */
    String LOGOUT_PATH = "/api/v1/auth/logout";
    /**
     * Token 前缀
     */
    String TOKEN_PREFIX = "Bearer ";

    /**
     * 请求头Token的Key
     */
    String TOKEN_KEY = "Authorization";

    /**
     * 验证码缓存前缀
     */
    String CAPTCHA_CODE_CACHE_PREFIX = "captcha_code:";

    /**
     * 用户权限集合缓存前缀
     */
    String USER_PERMS_CACHE_PREFIX = "user_perms:";

    /**
     * 黑名单Token缓存前缀
     */
    String BLACK_TOKEN_CACHE_PREFIX = "blacklist_token:";

    /**
     * 设备管理--黑白名单选择
     */
    String BLACK_WHITE_LIST_CHOOSE = "blacklist_choose";
}
