package com.onnoa.shop.demo.video.mybatis.demo1;

/**
 * @className: MyBatisController
 * @description:
 * @author: onnoA
 * @date: 2021/10/9
 **/
public class MyBatisController {

//    @Autowired
//    private SqlMapper sqlMapper;
//
//###selectList
//
//    //查询，返回List<Map>
//    List<Map<String, Object>> list = sqlMapper.selectList("select * from country where id < 11");
//
//    //查询，返回指定的实体类
//    List<Country> countryList = sqlMapper.selectList("select * from country where id < 11", Country.class);
//
////查询，带参数
//    countryList = sqlMapper.selectList("select * from country where id < #{id}", 11, Country.class);
//
//    //复杂点的查询，这里参数和上面不同的地方，在于传入了一个对象
//    Country country = new Country();
//country.setId(11);
//    countryList = sqlMapper.selectList("<script>" +
//            "select * from country " +
//            "   <where>" +
//            "       <if test=\"id != null\">" +
//            "           id &lt; #{id}" +
//            "       </if>" +
//            "   </where>" +
//            "</script>", country, Country.class);
//##复杂查询使用map传入参数
//    Map<String,String> map=new HashMap<>();
//map.put("id","21321312312312312");
//map.put("status","1");
//sqlMapper.sqlSelectList("select * from tb_admin where id=#{id} and status=#{status}",map,Admin.class);
//
//
//###selectOne 查询单条数据
//
//    Map<String, Object> map = sqlMapper.selectOne("select * from country where id = 35");
//
//    map = sqlMapper.selectOne("select * from country where id = #{id}", 35);
//
//    Country country = sqlMapper.selectOne("select * from country where id = 35", Country.class);
//
//    country = sqlMapper.selectOne("select * from country where id = #{id}", 35, Country.class);
//###insert,update,delete
//
//###insert 插入数据
//    int result = sqlMapper.insert("insert into country values(1921,'天朝','TC')");
//
//    Country tc = new Country();
//tc.setId(1921);
//tc.setCountryname("天朝");
//tc.setCountrycode("TC");
////注意这里的countrycode和countryname故意写反的
//    result = sqlMapper.insert("insert into country values(#{id},#{countrycode},#{countryname})"
//            , tc);
//
//
//###update 更新使用
//    result = sqlMapper.update("update country set countryname = '天朝' where id = 35");
//
//    tc = new Country();
//tc.setId(35);
//tc.setCountryname("天朝");
//
//    int result = sqlMapper.update("update country set countryname = #{countryname}" +
//            " where id in(select id from country where countryname like 'A%')", tc);
//
//
//##delete 删除使用
//    result = sqlMapper.delete("delete from country where id = 35");
//    result = sqlMapper.delete("delete from country where id = #{id}", 35);
}
