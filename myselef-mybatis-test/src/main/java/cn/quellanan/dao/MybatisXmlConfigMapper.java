package cn.quellanan.dao;

import cn.quellanan.pojo.MybatisXmlConfig;
import cn.quellanan.pojo.User;

import java.util.List;

/**
 * @className: MybatisXmlConfigMapper mybatis xml 配置mapper接口
 * @description:
 * @author: onnoA
 * @date: 2021/11/1
 **/
public interface MybatisXmlConfigMapper {

    List<MybatisXmlConfig> selectAll();

    List<MybatisXmlConfig> selectByMappingRel(MybatisXmlConfig xml);


}
