package org.core.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 系统常量类
 *
 * @author panhong
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constant {

    /**
     * JWT token 请求头
     */
    public static final String X_AUTHORIZATION_HEADER = "X-Authorization";

    static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    static final String DATE_FORMAT = "yyyy-MM-dd";

}
