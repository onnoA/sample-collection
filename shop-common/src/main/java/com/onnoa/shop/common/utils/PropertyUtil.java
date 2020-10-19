package com.onnoa.shop.common.utils;

import com.onnoa.shop.common.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置工具类
 */
public class PropertyUtil {
    private static final Logger logger = LoggerFactory.getLogger(PropertyUtil.class);

    private static Properties properties;

    public PropertyUtil(String fileName) {
        if (properties == null) {
            loadProperties(fileName);
        }
    }

    private static synchronized void loadProperties(String fileName) {
        properties = new Properties();
        InputStream in = null;
        if (fileName != null) {
            try {
                in = new BufferedInputStream(new FileInputStream(new File(fileName)));
            } catch (FileNotFoundException var16) {
                logger.error("{} 包外配置文件不存在", fileName);
                try {
                    if (in == null) {
                        in = PropertyUtil.class.getClassLoader().getResourceAsStream(fileName);
                    }
                    properties.load(in);
                } catch (FileNotFoundException var14) {
                    logger.error("{} 配置文件不存在", fileName);
                    throw ServiceException.PROFILE_NOT_EXIST.format(fileName);
                } catch (IOException var15) {
                    var15.printStackTrace();
                } finally {
                    if (in != null) {
                        try {
                            (in).close();
                        } catch (IOException var13) {
                            var13.printStackTrace();
                        }
                    }

                }
            }
        }


    }
}
