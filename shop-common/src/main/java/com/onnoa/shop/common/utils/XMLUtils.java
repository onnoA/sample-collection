package com.onnoa.shop.common.utils;

import com.onnoa.shop.common.dto.TestDto;
import com.onnoa.shop.common.dto.User;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.xml.sax.ContentHandler;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class XMLUtils {



    /**
     * 基于JAXB的注解的Bean to xml
     *
     * @param bean
     * @return
     * @throws JAXBException
     */
    public static String bean2XmlString(Object bean) throws JAXBException {

        StringWriter writer = new StringWriter();
        bean2Xml(bean, writer);

        return writer.toString();
    }

    /**
     * 基于JAXB的注解的Bean to xml
     *
     * @param bean
     * @return
     * @throws JAXBException
     */
    public static void bean2Xml(Object bean, StringWriter writer) throws JAXBException {
        Marshaller marshaller = createMarshaller(bean);
        marshaller.marshal(bean, writer);
    }

    /**
     * 基于JAXB的注解的Bean to xml
     *
     * @param bean
     * @return
     * @throws JAXBException
     */
    public static void bean2Xml(Object bean, ContentHandler handler) throws JAXBException {
        Marshaller marshaller = createMarshaller(bean);
        marshaller.marshal(bean, handler);
    }

    /**
     * 读取XML到bean
     *
     * @param xml
     * @param clazz
     * @return
     * @throws JAXBException
     */
    public static <T> T xml2Bean(String xml, Class<T> clazz) throws JAXBException {
        try {
            return xml2Bean(xml, clazz, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取XML到bean
     *
     * @param xml
     * @param clazz
     * @return
     * @throws JAXBException
     * @throws UnsupportedEncodingException
     */
    public static <T> T xml2Bean(String xml, Class<T> clazz, String charsetName)
            throws JAXBException, UnsupportedEncodingException {
        InputStream is = new ByteArrayInputStream(xml.getBytes(charsetName));
        return xml2Bean(is, clazz);
    }

    /**
     * 读取XML到bean
     *
     * @param is
     * @param clazz
     * @return
     * @throws JAXBException
     */
    @SuppressWarnings("unchecked")
    public static <T> T xml2Bean(InputStream is, Class<T> clazz) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Object unmarshal = unmarshaller.unmarshal(is);

        return (T) unmarshal;
    }

    private static Marshaller createMarshaller(Object bean) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(bean.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        return marshaller;
    }

    /**
     * 将对象根据路径转换成xml文件
     *
     * @param obj
     * @param path
     * @return
     */
    public static void convertToXml(Object obj, String path) {
        try {
            // 利用jdk中自带的转换类实现
            JAXBContext context = JAXBContext.newInstance(obj.getClass());

            Marshaller marshaller = context.createMarshaller();
            // 格式化xml输出的格式
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    Boolean.TRUE);
            // 将对象转换成输出流形式的xml
            // 创建输出流
            FileWriter fw = null;
            try {
                fw = new FileWriter(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            marshaller.marshal(obj, fw);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws JAXBException {
        // 创建需要转换的对象
        String path = "D:\\user.xml";
        List<TestDto> list = Lists.newArrayList();
        TestDto testDto = new TestDto();
        testDto.setDis_seq("73011101972821306");
        list.add(testDto);
        User user = new User("qryBusPortInfo", list);
        System.out.println("---将对象转换成string类型的xml Start---");
        // 将对象转换成string类型的xml
        convertToXml(user, path);
        String s = bean2XmlString(user);
        System.out.println(s);
        User result = xml2Bean(s, User.class);
        System.out.println(result);
    }


}
