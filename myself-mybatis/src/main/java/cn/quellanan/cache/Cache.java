package cn.quellanan.cache;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * 缓存容器接口
 *
 * 注意，它是一个容器，有点类似 HashMap ，可以往其中添加各种缓存。
 *
 * @author Clinton Begin
 */
public interface Cache {

    /** @return The identifier of this cache 标识*/
    String getId();

    /**添加指定键的值*/
    void putObject(Object key, Object value);

    /**获得指定键的值*/
    Object getObject(Object key);

    /** 移除指定键的值*/
    Object removeObject(Object key);

    /**清空缓存*/
    void clear();

    /** 获得容器中缓存的数量*/
    int getSize();


}