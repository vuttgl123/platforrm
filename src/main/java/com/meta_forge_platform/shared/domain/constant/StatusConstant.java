package com.meta_forge_platform.shared.domain.constant;

public final class StatusConstant {

    private StatusConstant() {}

    public static final String ACTIVE   = "ACTIVE";
    public static final String INACTIVE = "INACTIVE";
    public static final String DELETED  = "DELETED";
    public static final String DRAFT    = "DRAFT";
    public static final String ARCHIVED = "ARCHIVED";
    public static final String PENDING  = "PENDING";
    public static final String LOCKED   = "LOCKED";

    public static final String STATE_NORMAL = "NORMAL";
    public static final String STATE_START  = "START";
    public static final String STATE_END    = "END";
    public static final String STATE_ERROR  = "ERROR";

    public static final String STRATEGY_GENERIC    = "GENERIC";
    public static final String STRATEGY_DEDICATED  = "DEDICATED";

    public static final String STORAGE_SCALAR  = "SCALAR";
    public static final String STORAGE_MULTI   = "MULTI";
    public static final String STORAGE_JSON    = "JSON";

    public static final String RELATION_ONE_TO_ONE   = "ONE_TO_ONE";
    public static final String RELATION_ONE_TO_MANY  = "ONE_TO_MANY";
    public static final String RELATION_MANY_TO_ONE  = "MANY_TO_ONE";
    public static final String RELATION_MANY_TO_MANY = "MANY_TO_MANY";

    public static final String LINK_REFERENCE = "REFERENCE";
    public static final String LINK_EMBED     = "EMBED";
    public static final String LINK_TAG       = "TAG";

    public static final String MENU_SCREEN    = "SCREEN";
    public static final String MENU_GROUP     = "GROUP";
    public static final String MENU_EXTERNAL  = "EXTERNAL";

    public static final String STORAGE_LOCAL = "LOCAL";
    public static final String STORAGE_S3    = "S3";
    public static final String STORAGE_GCS   = "GCS";
}