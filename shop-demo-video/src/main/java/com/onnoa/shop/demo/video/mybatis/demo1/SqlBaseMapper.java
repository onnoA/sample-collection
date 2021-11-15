package com.onnoa.shop.demo.video.mybatis.demo1;

import java.util.List;
import java.util.Map;

/**
 * @className: SqlBaseMapper
 * @description:
 * @author: onnoA
 * @date: 2021/10/9
 **/
public interface SqlBaseMapper {


    /**
     * 查询单条数据返回Map<String, Object>
     *
     * @param sql sql语句
     * @return Map<String, Object>
     */
    Map<String, Object> sqlSelectOne(String sql);

    /**
     * 查询单条数据返回Map<String, Object>
     *
     * @param sql   sql语句
     * @param value 参数
     * @return Map<String, Object>
     */
    Map<String, Object> sqlSelectOne(String sql, Object value);

    /**
     * 查询单条数据返回实体类型
     *
     * @param sql        sql语句
     * @param resultType 具体类型
     * @return 定义的实体类型
     */
    <T> T sqlSelectOne(String sql, Class<T> resultType);

    /**
     * 查询单条数据返回实体类型
     *
     * @param sql        sql语句
     * @param value      参数
     * @param resultType 具体类型
     * @return 定义的实体类型
     */
    <T> T sqlSelectOne(String sql, Object value, Class<T> resultType);

    /**
     * 查询数据返回
     *
     * @param sql sql语句
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> sqlSelectList(String sql);

    /**
     * 查询数据返回
     *
     * @param sql   sql语句
     * @param value 参数
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> sqlSelectList(String sql, Object value);

    /**
     * 查询数据返回
     *
     * @param sql        sql语句
     * @param resultType 具体类型
     * @return List<T>
     */
    <T> List<T> sqlSelectList(String sql, Class<T> resultType);

    /**
     * 查询数据返回
     *
     * @param sql        sql语句
     * @param value      参数
     * @param resultType 具体类型
     * @return List<T>
     */
    <T> List<T> sqlSelectList(String sql, Object value, Class<T> resultType);

    /**
     * 插入数据
     *
     * @param sql sql语句
     * @return int
     */
    int sqlInsert(String sql);

    /**
     * 插入数据
     *
     * @param sql   sql语句
     * @param value 参数
     * @return int
     */
    int sqlInsert(String sql, Object value);

    /**
     * 更新数据
     *
     * @param sql sql语句
     * @return int
     */
    int sqlUpdate(String sql);

    /**
     * 更新数据
     *
     * @param sql   sql语句
     * @param value 参数
     * @return int
     */
    int sqlUpdate(String sql, Object value);

    /**
     * 删除数据
     *
     * @param sql sql语句
     * @return int
     */
    int sqlDelete(String sql);

    /**
     * 查询数据返回List<T>
     *
     * @param sql   sql语句
     * @param value 参数
     * @return int
     */
    int sqlDelete(String sql, Object value);


}
