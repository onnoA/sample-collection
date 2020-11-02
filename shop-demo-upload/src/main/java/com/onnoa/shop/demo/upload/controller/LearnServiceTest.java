package com.onnoa.shop.demo.upload.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.onnoa.shop.common.result.ResultBean;
import com.onnoa.shop.common.utils.DowloadZipUtil;
import com.onnoa.shop.common.utils.HttpClient;
import com.onnoa.shop.common.utils.ImageUtil;
import com.onnoa.shop.common.utils.MD5Util;
import com.onnoa.shop.common.utils.XMLUtils;
import com.onnoa.shop.common.utils.ZipUtil;
import com.onnoa.shop.demo.upload.dto.Company;
import com.onnoa.shop.demo.upload.dto.HuaWei;
import com.onnoa.shop.demo.upload.dto.OcrCustomerOrderAttrDTO;
import com.onnoa.shop.demo.upload.dto.OcsRmInterfaceResponse;
import com.onnoa.shop.demo.upload.dto.Project;
import com.onnoa.shop.demo.upload.dto.QryBusPortInfoDto;
import com.onnoa.shop.demo.upload.dto.ResponseDto;
import com.onnoa.shop.demo.upload.dto.UserDto;
import com.onnoa.shop.demo.upload.service.AbilityOpenApiService;
import com.onnoa.shop.demo.upload.service.AnZhenTongApiService;
import com.onnoa.shop.demo.upload.service.CrmFileService;
import com.onnoa.shop.demo.upload.service.DcoosApiService;
import com.onnoa.shop.demo.upload.service.OcsRmApiService;
import com.onnoa.shop.demo.upload.service.OrderCutImageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static com.onnoa.shop.demo.upload.service.impl.OrderCutImageServiceImpl.RESULT_DATA;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LearnServiceTest {

    @Autowired
    CrmFileService crmFileService;
    @Autowired
    OrderCutImageService orderCutImageService;
    @Autowired
    private AbilityOpenApiService abilityOpenApiService;
    @Autowired
    private OcsRmApiService ocsRmApiService;

    @Autowired
    private DcoosApiService dcoosApiService;

    @Test
    public void getLearn() {
        System.out.println(UUID.randomUUID());
        String fileId = "260c8bd26f2209cec38d5b4b1f9592d8_0";
        byte[] download = crmFileService.download(fileId);
        System.out.println(Base64.getEncoder().encodeToString(download));
        if (download != null) {
            System.out.println("下载成功，长度： " + download.length);
            // 2.上传文件测试
            String file_id = crmFileService.upload(download, "pdf");
            if (file_id != null) {
                System.out.println("上传成功，返回文件ID:" + file_id);
            }
        }
    }


    @Test
    public void cutImage() {
        OcrCustomerOrderAttrDTO dto = new OcrCustomerOrderAttrDTO();
        dto.setCertiNumber("430611198608041512");
        dto.setLanId("730");
        dto.setLivingImage("4d04502ebad01a245979446ad9cfe087_0,b7516684abac624c6d9bc7d6d1f80989_0");
        dto.setCustOrderId("8730200923985677442");
        Map<String, Object> stringObjectMap = orderCutImageService.callbackImageClassify(dto);
        System.out.println(stringObjectMap);


    }


    @Test
    public void aiCheck() {
        Map<String, Object> params = Maps.newHashMap();
        params.put("cust_order_id", "873120101255757238");
        params.put("auth_flag", "F");
        params.put("ai_result", "身份照片模糊");
        params.put("staff_no", "大数据稽核工号");
        Map<String, String> header = Maps.newHashMap();
        header.put("XAPPID", "f4751dc73f5f9914ca955b6649d8fde8");
        header.put("XAPPKEY", "131b9d55683a537d1a4c9e46ccfa673b");
        header.put("XCTGRequestID", UUID.randomUUID().toString());
        String url = "http://134.176.102.33:9080/api/openapi/INFSaveAuditResult/INFSaveAuditResult";
        Map<String, Object> stringObjectMap = orderCutImageService.callbackAiCheck(params, header, url);
        System.out.println(stringObjectMap);
    }

    @Test
    public void abilityPost() {
        String url = "http://134.176.102.33:8081/api/rest";
        Map<String, Object> requestMap = Maps.newHashMap();
        String configStr = "{\n" +
                "\t\"method\": \"qry.contract.QryContractFile\",\n" +
                "\t\"access_token\": \"OTBkMjJiM2Y0NmVjNzdmOTc0NWFkZWMyZGU1MThhMmI=\",\n" +
                "\t\"reqSystem\": \"YWWB\",\n" +
                "\t\"reqPwd\": \"HNYWWB\",\n" +
                "\t\"status\": \"1\"\n" +
                "}";
        Map<String, Object> contentMap = new HashMap<>();
        Map configParams = JSON.parseObject(configStr, Map.class);
        contentMap.put("reqSystem", MapUtils.getString(configParams, "reqSystem"));
        contentMap.put("reqPwd", MapUtils.getString(configParams, "reqPwd"));
        contentMap.put("contractCode", "HNCSA2006921CGN00");
        requestMap.put("content", contentMap);
        requestMap.put("status", MapUtils.getString(configParams, "status"));
        requestMap.put("access_token", MapUtils.getString(configParams, "access_token"));
        requestMap.put("method", MapUtils.getString(configParams, "method"));
        //Map<String, Object> resultMap = abilityOpenApiService.commonRequest(requestMap, url);
        Map<String, Object> resultMap = abilityOpenApiService.postAbility(requestMap, url);
        System.out.println("请求结束返回的结果====> :" + resultMap);
    }

    @Test
    public void abilityPost2() {
        String url = "http://134.176.102.33:8081/api/rest";
        Map<String, Object> requestMap = Maps.newHashMap();
        String configStr = "{\n" +
                "\t\"method\": \"qry.contract.QryContractFile\",\n" +
                "\t\"access_token\": \"OTBkMjJiM2Y0NmVjNzdmOTc0NWFkZWMyZGU1MThhMmI=\",\n" +
                "\t\"reqSystem\": \"YWWB\",\n" +
                "\t\"reqPwd\": \"HNYWWB\",\n" +
                "\t\"status\": \"0\"\n" +
                "}";
        Map<String, Object> contentMap = new HashMap<>();
        Map configParams = JSON.parseObject(configStr, Map.class);
        contentMap.put("reqSystem", MapUtils.getString(configParams, "reqSystem"));
        contentMap.put("reqPwd", MapUtils.getString(configParams, "reqPwd"));
        contentMap.put("contractCode", "HNCSA2006921CGN00");
        requestMap.put("content", contentMap);
        requestMap.put("status", MapUtils.getString(configParams, "status"));
        requestMap.put("access_token", MapUtils.getString(configParams, "access_token"));
        requestMap.put("method", MapUtils.getString(configParams, "method"));
        Map<String, Object> resultMap = abilityOpenApiService.postAbility(requestMap, url);
        System.out.println("请求结束返回的结果====> :" + resultMap);
    }

    @Test
    public void postAbility() throws JAXBException {
        String url = "http://134.176.102.33:8081/api/rest";
        Map<String, Object> requestMap = Maps.newHashMap();
        String configStr = "{\n" +
                "\t\"method\": \"qry.resinfo.qryBusPortInfo\",\n" +
                "\t\"access_token\": \"OTBkMjJiM2Y0NmVjNzdmOTc0NWFkZWMyZGU1MThhMmI=\",\n" +
                "\t\"reqSystem\": \"YWWB\",\n" +
                "\t\"reqPwd\": \"HNYWWB\",\n" +
                "\t\"status\": \"0\"\n" +
                "}";
        Map configParams = JSON.parseObject(configStr, Map.class);
        QryBusPortInfoDto.OrderDto qryDto = new QryBusPortInfoDto.OrderDto();
        //qryDto.setDisSeq("7331310295033085");
        //QryBusPortInfoDto user = new QryBusPortInfoDto("qryBusPortInfo", qryDto);
        //String inputXml = XMLUtils.bean2XmlString(user);
        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("reqSystem", MapUtils.getString(configParams, "reqSystem"));
        contentMap.put("reqPwd", MapUtils.getString(configParams, "reqPwd"));
        contentMap.put("DIS_SEQ", "7331310294998776");
        //requestMap.put("inputXml",inputXml);
        requestMap.put("content", contentMap);
        requestMap.put("status", MapUtils.getString(configParams, "status"));
        requestMap.put("access_token", MapUtils.getString(configParams, "access_token"));
        requestMap.put("method", MapUtils.getString(configParams, "method"));
        Map<String, Object> returnMap = abilityOpenApiService.postAbility(requestMap, url);
        System.out.println("请求返回的结果：" + returnMap);
    }

    @Test
    public void postAbility2() throws JAXBException {
        String url = "http://134.176.102.33:8081/api/rest";
        Map<String, Object> requestMap = Maps.newHashMap();
        String configStr = "{\n" +
                "\t\"method\": \"qry.resinfo.qryCodeBarPortInfo\",\n" +
                "\t\"access_token\": \"OTBkMjJiM2Y0NmVjNzdmOTc0NWFkZWMyZGU1MThhMmI=\",\n" +
                "\t\"reqSystem\": \"YWWB\",\n" +
                "\t\"reqPwd\": \"HNYWWB\",\n" +
                "\t\"status\": \"0\"\n" +
                "}";
        Map configParams = JSON.parseObject(configStr, Map.class);
        QryBusPortInfoDto.CodeDto codeDto = new QryBusPortInfoDto.CodeDto();
        codeDto.setCodeBar("7321010000021");
        QryBusPortInfoDto user = new QryBusPortInfoDto("qryBusPortInfo", null, codeDto);
        String inputXml = XMLUtils.bean2XmlString(user);
        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("reqSystem", MapUtils.getString(configParams, "reqSystem"));
        contentMap.put("reqPwd", MapUtils.getString(configParams, "reqPwd"));
        contentMap.put("CODE_BAR", "7321010000021");
        //requestMap.put("inputXml",inputXml);
        requestMap.put("content", contentMap);
        requestMap.put("status", MapUtils.getString(configParams, "status"));
        requestMap.put("access_token", MapUtils.getString(configParams, "access_token"));
        requestMap.put("method", MapUtils.getString(configParams, "method"));
        Map<String, Object> returnMap = abilityOpenApiService.postAbility(requestMap, url);
        System.out.println("请求返回的结果：" + returnMap);
        Object data = returnMap.get("result");
        String jsonString = JSONObject.toJSONString(data);
        ResponseDto response1 = JSON.parseObject(jsonString, ResponseDto.class);
        log.info("json转实体后输出:{}", response1);
    }

    @Test
    public void test() {
        ArrayList<String> list = Lists.newArrayList();
        //7442010255457631
        list.add("7431027206565945"); // 7311310295102577
//        list.add("7442010255457631");
//        list.add("7372010234069260");
//        list.add("7352010242127249");
//        list.add("7462010256426018");
//        list.add("7462010256426020");
//        list.add("7392010225559134");
//        list.add("7312010256502707");
//        list.add("7342010256823341");
//        list.add("7312010256580704");
//        list.add("7392010225559144");
        list.parallelStream().forEach(entity -> {
            OcsRmInterfaceResponse response = ocsRmApiService.qryBusPortInfo(entity);
            log.info("请求参数：{}，返回数据:{}", entity, response);
        });
    }


    @Test
    public void qryCodeTest() {
        String obdScanCode = "743101200374166";
        OcsRmInterfaceResponse response = ocsRmApiService.qryCodeBarPortInfo(obdScanCode);
        log.info("请求参数：{},返回数据:{}", obdScanCode, response);
    }

    @Test
    public void protocolTest() {
        String configParamsStr = "{\"basicUrl\":\"http://134.176.102.33:9080/api/openapi\", \"XAPPID\":\"8f2782be113374f3d9b9d49cdd050427\", \"XAPPKEY\":\"75776fb5f128928dfefec6697b285e1e\", \"clientId\":\"AI_APR\"}";
        String callbackMethod = "acceptMssContactAnalysisResult/acceptMssContactAnalysisResul/acceptMssContactAnalysisResulacceptMssContactAnalysisResult/acceptMssContactAnalysisResul/acceptMssContactAnalysisResul";
        Map<String, Object> params = new HashMap<>();
        Map contractInstVO = new HashMap();
        List<Map> contractInfoVOList = new ArrayList<>();
        //contractInfoVOList.add("")
        contractInstVO.put("request_code", "3232324343243432");
        contractInstVO.put("accept_date", "2018-03-24 18:36:36");
        contractInstVO.put("contract_code", "HNCSA2006921CGN00");
        contractInstVO.put("contract_info", contractInfoVOList);
        params.put("params", "{\"accept_date\":\"2018-03-24 18:36:36\",\"contract_code\":\"HNCSA2006921CGN00\",\"request_code\":\"3232324343243432\",\"contract_info\":[{\"order_attr_inst\":[{\"attr_value\":\"100\",\"attr_id\":\"一次性开通调测费用X元\"},{\"attr_value\":\"300000\",\"attr_id\":\"专线速率\"},{\"attr_value\":\"订购\",\"attr_id\":\"业务类型\"},{\"attr_value\":\"4000\",\"attr_id\":\"互联网专线协议月使用费\"},{\"attr_value\":\"互联网专线\",\"attr_id\":\"产品\"},{\"attr_value\":\"2020/10/15\",\"attr_id\":\"合同服务开始时间\"},{\"attr_value\":\"2021/10/14\",\"attr_id\":\"合同服务结束时间\"},{\"attr_value\":\"\",\"attr_id\":\"合同编号\"},{\"attr_value\":\"湖南省公安厅\",\"attr_id\":\"客户名称\"},{\"attr_value\":\"731234567890037971\",\"attr_id\":\"客户证件\"},{\"attr_value\":\"183\",\"attr_id\":\"序号\"},{\"attr_value\":\"3\",\"attr_id\":\"开通条数\"},{\"attr_value\":\"\",\"attr_id\":\"接入号\"},{\"attr_value\":\"2\",\"attr_id\":\"类型标识\"}],\"index_id\":\"1\"}]}");
        params.put("method", callbackMethod);
        Map configParams = JSON.parseObject(configParamsStr, Map.class);
        Map resultMap = dcoosApiService.post(params, configParams);
        log.info("返回结果:{}", resultMap);
    }

    @Test
    public void optionalTest() {
//        OptionalTestDto dto = new OptionalTestDto();
//        OcrCustomerOrderAttrDTO attrDto = new OcrCustomerOrderAttrDTO();
//        attrDto.setCustOrderId(null).setLivingImage("2555");
//        dto.setDto(attrDto);
//        String str = Optional.ofNullable(dto)
//                .map(entity -> entity.getDto())
//                .map(entity -> entity.getCustOrderId())
//                .orElse(null);
//
//        if (dto != null) {
//            OcrCustomerOrderAttrDTO attrDto1 = dto.getDto();
//            if (attrDto1 != null) {
//                String custOrderId = attrDto1.getCustOrderId();
//            }
//        }
//        System.out.println(str);

        Company company = new Company();
        HuaWei huaWei = new HuaWei();
        Project pro = new Project();
        pro.setSpecificItem("手机行业");
        huaWei.setProject(pro);
        company.setHuawei(huaWei);
        String result = Optional.ofNullable(company)
                .map(Company::getHuawei)
                .map(HuaWei::getProject)
                .map(Project::getSpecificItem)
                //.orElseThrow(()->new RuntimeException("抛异常处理。。。"))
                .orElse("默认项目 ");
        log.info("result : {}", result);

        // 返回结果 : 当 company 、huawei 、project、specificItem 为空时： result : 默认项目
        // 当对象都不为空时： result : 手机行业

        // --------------------------分割线-----------------------------------------
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> project = new HashMap<>();
        project.put("phone", "mate40pro");
        project.put("comm", "5G");

        Map<String, Object> huawei = new HashMap<>();
        huawei.put("project", project);

        Map<String, Object> org = new HashMap<>();
        org.put("huawei", huawei);
        map.put("org", org);
        Object o = Optional.ofNullable(map)
                .map(map1 -> map1.get("org"))
                .map(map1 -> ((Map) map1).get("huawei"))
                .map(map1 -> ((Map) map1).get("project"))
                .map(map1 -> ((Map) map1).get("phone"))
                //.orElse("默认值 。。。");
                .orElseThrow(() -> new RuntimeException("参数异常 。。。"));
        log.info("返回:{}", o);


        //Comparator.comparing(Project::getSpecificItem)

    }


    @Test
    public void comTest() {
        UserDto dto = new UserDto();
        dto.setUsername("zh");
        dto.setAge(19);
        dto.setMobile("13411111111");
        UserDto dto1 = new UserDto();
        dto1.setUsername("zhh");
        dto1.setAge(20);
        dto1.setMobile("13411111112");
        UserDto dto2 = new UserDto();
        dto2.setUsername("zhhh");
        dto2.setAge(19);
        dto2.setMobile("13411111112");
        UserDto dto3 = new UserDto();
        dto3.setUsername("zh");
        dto3.setAge(21);
        dto3.setMobile("13411111112");
        ArrayList<UserDto> list = Lists.newArrayList();
        list.add(dto);
        list.add(dto1);
        list.add(dto2);
        list.add(dto3);

        log.info("排序前的集合:{}", list);
//        Collections.sort(employees, (o1, o2) -> o1.getName().compareTo(o2.getName()));
        //list.sort((o1, o2) -> o1.getUsername().compareTo(o1.getUsername()));
        // 根据年龄升序
        //Collections.sort(list, (o1, o2) -> Integer.valueOf(o1.getAge()).compareTo(Integer.valueOf(o2.getAge())));
        // 根据年龄升序
//        Collections.sort(list, Comparator.comparing(UserDto::getAge, (o1, o2) -> {
//            return o1.compareTo(o2);
//        }));
        // 根据年龄降序
        //list.sort(Comparator.comparing(UserDto::getAge, Comparator.reverseOrder()));
        // 根据年龄升序
        //list.sort(Comparator.comparing(UserDto::getAge, Comparator.reverseOrder()).reversed());
        // 根据年龄降序
//        Collections.sort(list, Comparator.comparing(UserDto::getAge, (o1, o2) -> {
//            return o2.compareTo(o1);
//        }));
        // 升序 先根据年龄升序，在根据名字降序
        list.sort(Comparator.comparingInt(UserDto::getAge).thenComparing(UserDto::getUsername));
        // 降序
        list.sort(Comparator.comparingInt(UserDto::getAge).reversed());
        log.info("排序后的集合:{}", list);

        String[] strArr = {"1", "3", "5", "7"};
        Stream.of(strArr).forEach(str -> System.out.println(str));

    }

    @Test
    public void java8Test() {
//        String creator = "";
//        User dto = new User();
//        Stream<String> stream = Stream.of("zh", "zhh", "zzhh");
//        dto.setLoginName("登录名称");
//        dto.setUsername("用户名");
//        stream.forEach(entity -> System.out.println(entity));
//        creator = Optional
//                .ofNullable(dto)
//                .map(user -> Stream.of(user.getUsername(), user.getLoginName(), user.getNickName()))
//                .orElse(Stream.empty())
//                .filter(Objects::nonNull)
//                .findFirst()
//                .orElse(creator);
//
//        System.out.println(creator);
//
//        ArrayList<User> list = Lists.newArrayList();
//        Optional<User> first = Optional.ofNullable(list)
//                .map(Collection::stream)
//                .orElse(Stream.empty())
//                .findFirst();
//        ArrayList<Long> numbers = Lists.newArrayList();
//        numbers.add(1L);
//        numbers.add(5L);
//        long reduce = numbers.parallelStream().map(x -> new BigDecimal(x.toString())).reduce(BigDecimal.ZERO, BigDecimal::add).longValue();
//        log.info("求和:{}", reduce);

        ArrayList<String> strList = new ArrayList<>(Arrays.asList("zh", "zz", "onnoA"));
        String str = strList.stream().reduce("", (a, b) -> a + b);
        log.info("str:{}", str);
        Integer startIndex = null;
        startIndex = Optional.ofNullable(startIndex)
                .orElse(1);

        log.info("开始标志位:{}", startIndex);


    }

    @Test
    public void md5Test() throws IOException {
        String md5 = MD5Util.MD5("TEST20201030115359" + "1603941569000" + "!@##@!");
        log.info("md5：{}", md5);
        //File zipp = File.createTempFile("zipp", "");
        File file = DowloadZipUtil.downloadFile("http://202.103.124.84:7201/portal/foreign/downZip/11ffa288cf7140f58b9c39ad53f1b225.zip", "F:\\code\\MyGitHub\\sample-collection\\shop-demo-upload\\zip");

        //FileInputStream fileInputStream = new FileInputStream(file);

        //        ZipUtil.unZip("F:\\code\\MyGitHub\\sample-collection\\shop-demo-upload\\zip\\portal\\foreign\\downZip\\5ba230d733034bbe81aaee666d68b46d.zip");

        List<File> files = ZipUtil.upzipFile(file, "F:\\code\\MyGitHub\\sample-collection\\shop-demo-upload\\zip\\portal\\foreign\\downZip");
        files.stream().forEach(file1 -> {
            //System.out.println(file);
            try {
                FileInputStream inputStream = new FileInputStream(file1);
                String base64 = ImageUtil.getBase64(inputStream);
                log.info("base64:{}", base64);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });


//        String s = DowloadZipUtil.downloadFromUrl("http://202.103.124.84:7201/portal/foreign/downZip/f81b97059caf473ba8f0a9de61b16cc0.zip", "F:\\code\\MyGitHub\\sample-collection\\shop-demo-upload\\zip");


    }


    @Test
    public void hunanTest() throws IOException {
        //StringBuffer sb = new StringBuffer();
        File file = new File("F:\\code\\MyGitHub\\sample-collection\\shop-demo-upload\\pic\\15BB2C8C4D83F8CB86E74BAFD2DBD297.jpg");
        FileInputStream fileInputStream = new FileInputStream(file);
        String base64 = ImageUtil.getBase64(fileInputStream);
        String abilityPlatformHttp = "http://port-check-hunan-group.namespace11601101654625.134.176.170.168.xip.io/main/service";
        Map params = new HashMap();
        Map postMap = new HashMap();
        ArrayList<Integer> portIdWorkList = Lists.newArrayList();
        portIdWorkList.add(1);
        ArrayList<Integer> portIdAllList = Lists.newArrayList();
        portIdAllList.add(2);
        portIdAllList.add(3);
        portIdAllList.add(4);
        postMap.put("service_name", "port_check_hunan");
        params.put("image", base64);
        params.put("port_id_work", portIdWorkList);
        params.put("port_id_all", portIdAllList);
        postMap.put("params", params);
        String param = JSON.toJSONString(postMap);
        log.info("OBD端口稽核请求地址：" + abilityPlatformHttp);
        ResultBean response = new ResultBean();
        String result = "";
        try {
            result = HttpClient.post(abilityPlatformHttp, param, 50000);
            //HttpClient.
            //result = HttpUtils.doPost(abilityPlatformHttp, postMap);
            log.info("OBD端口稽核返回报文:{}", result);
            response = JSON.parseObject(result, ResultBean.class);
        } catch (RuntimeException e) {
            response.setCode(-1);
            response.setMessage(result + "\n" + e);
            log.info("调用算法平台OBD端口稽核失败！{}", e.getMessage());

        }
        //成功时，将返回报文装入message
        response.setMessage(result);
        log.info("响应结果：{}", response);
    }

    @Autowired
    AnZhenTongApiService anZhenTongApiService;

    @Test
    public void anzhentongTest() {
        OcsRmInterfaceResponse<List<String>> list = anZhenTongApiService.getImageList("TEST20201030115359");
        log.info("请求结果:{}", JSONObject.toJSON(list));
    }

    @Test
    public void subTest(){
        String url = "http://202.103.124.84:7201/portal/foreign/downZip/e04cdb8bd769479a8270eb6cafac39d2";
        // 测试： http://134.176.46.8
        // 生产： http://134.175.22.219
        String replaceUrl = url.replace(url.substring(0, url.lastIndexOf(":")),"http://134.175.22.219");
        System.out.println(replaceUrl);
    }



}