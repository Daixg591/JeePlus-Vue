package com.jeeplus.sys.constant;

/**
 * 缓存名称定义
 * 缓存设置的标准：
 * 1 频繁读取，但是很少修改的内容，例如用户菜单，用户，字典
 * 2 读取频率不高，但是数据量很大也很少修改的内容，比如机构，区域
 */
public interface CacheNames {

    /**
     * 系统缓存
     */
     String SYS_CACHE_CONFIG = "sys:cache:config"; //系统配置

     String SYS_CACHE_AREA_LIST = "sys:cache:areaList"; //区域

     String SYS_CACHE_DICT_MAP = "sys:cache:dictMap"; // 字典

     String SYS_CACHE_MENU = "sys:cache:menu";

    /**
     * 用户缓存--属于用户，和用户绑定
     */

     String USER_CACHE_TOP_MENU = "user:cache:topMenu"; //用户的顶部菜单

     String USER_CACHE_MENU_LIST = "user:cache:menuList"; //用户的左侧菜单

     String USER_CACHE_DATA_RULE_LIST = "user:cache:dataRuleList"; // 用户的数据权限

     String USER_CACHE_ROLE_LIST = "user:cache:roleList"; //用户角色列表

     String USER_CACHE_USER_ID = "user:cache:userId"; //根据id关联用户

     String USER_CACHE_LOGIN_NAME = "user:cache:loginName"; //根据登录名关联用户


}
