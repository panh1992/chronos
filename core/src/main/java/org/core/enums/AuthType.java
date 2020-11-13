package org.core.enums;

/**
 * 认证访问方式
 *
 * @author panhong
 */
public enum AuthType {

    /**
     * 无认证
     */
    EMPTY,

    /**
     * 用户名密码认证
     */
    USER_PASSWORD,

    /**
     * ACCESS_ID 和 ACCESS_KEY 认证
     */
    ACCESS_ID_KEY,

    /**
     * ACCESS_TOKEN 令牌认证
     */
    ACCESS_TOKEN

}
