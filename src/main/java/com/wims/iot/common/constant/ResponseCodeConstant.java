package com.wims.iot.common.constant;

/**
  * 返回编码常量
  */
public class ResponseCodeConstant {

    /**
     * 请求成功
     **/
    public static final int SUCCESS = 0;

    /**
     * 处理告警事件的备注为空
     */
    public static final int ALARM_EVENT_NOTE_IS_NULL = 1001;

    /**
     * 添加黑名单中设备号为空
     */
    public static final int BLACKLIST_EQ_CODE_IS_NULL = 1002;

    /**
     * 添加黑名单中备注为空
     */
    public static final int BLACKLIST_NOTE_IS_NULL = 1003;

    /**
     * 添加黑名单中该设备号已经在黑名单中
     */
    public static final int EQ_CODE_IS_EXIST_BLACKLIST = 1004;

    /**
     * 添加白名单中ip为空
     */
    public static final int WHITELIST_IP_IS_NULL = 1005;

    /**
     * 添加白名单中备注为空
     */
    public static final int WHITELIST_NOTE_IS_NULL = 1006;

    /**
     * 添加白名单中该ip已经存在白名单中
     */
    public static final int IP_IS_EXIST_WHITELIST = 1007;

    /**
     * 修改设备信息中设备名称为空
     */
    public static final int EQUIPMENT_EQ_NAME_IS_NULL = 1008;

    /**
     * 修改设备信息中所在站点为空
     */
    public static final int EQUIPMENT_ST_ID_IS_NULL = 1009;

    /**
     * 修改设备信息中设备名称已存在
     */
    public static final int EQUIPMENT_EQ_NAME_IS_EXIST = 1010;

    /**
     * 申请设备ID中协议为空
     */
    public static final int ID_ASSIGNMENT_PROTOCOL_IS_NULL = 1011;

    /**
     * 申请设备ID中数量为空
     */
    public static final int ID_ASSIGNMENT_COUNT_IS_NULL = 1012;

    /**
     * 申请设备ID中数量错误
     */
    public static final int ID_ASSIGNMENT_COUNT_ERROR = 1013;

    /**
     * 禁用/启用设备ID号中状态错误
     */
    public static final int ID_ASSIGNMENT_STATUS_ERROR = 1014;

    /**
     * 删除设备ID号中ID号为使用状态
     */
    public static final int ID_ASSIGNMENT_IS_USED = 1015;

    /**
     * 站点名称为空
     */
    public static final int STATION_ST_NAME_IS_NULL = 1016;

    /**
     * 站点编号为空
     */
    public static final int STATION_STCD_IS_NULL = 1017;

    /**
     * 站点类型为空
     */
    public static final int STATION_STTP_IS_NULL = 1018;

    /**
     * 站点经度为空
     */
    public static final int STATION_LONGITUDE_IS_NULL = 1019;

    /**
     * 站点纬度为空
     */
    public static final int STATION_LATITUDE_IS_NULL = 1020;

    /**
     * 站点名称已存在
     */
    public static final int STATION_ST_NAME_IS_EXIST = 1021;

    /**
     * 站点编号已存在
     */
    public static final int STATION_STCD_IS_EXIST = 1022;

    /**
     * 站点下还有设备
     */
    public static final int STATION_HAS_EQUIPMENTS = 1022;

    /**
     * 校准时间失败
     */
    public static final int ADJUST_DEVICE_TIME_FAILED = 1023;

    /**
     * 未找到该设备
     */
    public static final int DEVICE_NOT_FOUND = 1024;

    /**
     * 未收到执行结果
     */
    public static final int RECEIVE_EXECUTION_ERROR = 1025;

    /**
     * 获取实时数据失败
     */
    public static final int GET_CURRENT_POLLUTANT_DATA_FAILED = 1025;

    /**
     * 用户名或密码错误
     **/
    public static final int USERNAME_OR_PASSWORD_ERROR = 4001;

    /**
     * 账号过期
     **/
    public static final int ACCOUNT_EXPIRED = 4002;

    /**
     * 密码过期
     **/
    public static final int PASSWORD_EXPIRED = 4003;

    /**
     * 账号不可用
     **/
    public static final int ACCOUNT_DISABLED = 4004;

    /**
     * 账号锁定
     **/
    public static final int ACCOUNT_LOCKED = 4005;

    /**
     * 用户未登录
     **/
    public static final int USER_NOT_LOGIN = 4006;

    /**
     * token验证失败
     **/
    public static final int TOKEN_VERIFY_ERROR = 4007;

    /**
     * 账号在其他设备上登录
     **/
    public static final int REMOTE_LOGIN = 4008;

    /**
     * 权限错误
     **/
    public static final int AUTHORITY_ERROR = 4009;

    /**
     * 服务器内部异常
     **/
    public static final int SERVER_ERROR = 5000;

}
