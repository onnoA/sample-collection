package com.onnoa.shop.demo.video.constant;

/**
 * @Description: 自定义的全局常量
 * @Author: onnoA
 * @Date: 2020/6/6 11:46
 */
public class GlobalConstant {

    public static final String ROOT_PREFIX = "shop";

    public static final String ZK_REGISTRY_ID_ROOT_PATH = "/shop/registry/id";
    public static final String ZK_REGISTRY_SEQ = "/shop/seq";

    public static final String USER_TOKEN_AUTH_DTO = "USER_TOKEN_AUTH_DTO";

    public static final String SLOW_SQL_LOG = "slowSqlLog";


    public static final class Symbol {
        private Symbol() {
        }

        /**
         * The constant COMMA.
         */
        public static final String COMMA = ",";
        public static final String SPOT = ".";
        /**
         * The constant UNDER_LINE.
         */
        public final static String UNDER_LINE = "_";
        /**
         * The constant PER_CENT.
         */
        public final static String PER_CENT = "%";
        /**
         * The constant AT.
         */
        public final static String AT = "@";
        /**
         * The constant PIPE.
         */
        public final static String PIPE = "||";
        public final static String SHORT_LINE = "-";
        public final static String SPACE = " ";
        public static final String SLASH = "/";
        public static final String MH = ":";

    }


}
