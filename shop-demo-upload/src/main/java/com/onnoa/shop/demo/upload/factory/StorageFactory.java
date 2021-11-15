package com.onnoa.shop.demo.upload.factory;

import com.onnoa.shop.demo.upload.service.UploadService;
import com.onnoa.shop.demo.upload.service.impl.FastDFSStorageServiceImpl;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import java.util.HashMap;
import java.util.Map;

public class StorageFactory implements FactoryBean<UploadService> {

    @Autowired
    private AutowireCapableBeanFactory acbf;

    /**
     * 存储服务的类型，目前仅支持fastdfs
     */
    @Value("${storage.type}")
    private String type;

    private Map<String, Class<? extends UploadService>> classMap;

    public StorageFactory() {
        classMap = new HashMap<>();
        classMap.put("fastdfs", FastDFSStorageServiceImpl.class);
    }

    @Override
    public UploadService getObject() throws Exception {
        Class<? extends UploadService> clazz = classMap.get(type);
        if (clazz == null) {
            throw new RuntimeException("Unsupported storage type [" + type + "], valid are " + classMap.keySet());
        }

        UploadService bean = clazz.newInstance();
        acbf.autowireBean(bean);
        acbf.initializeBean(bean, bean.getClass().getSimpleName());
        return bean;
    }

    @Override
    public Class<?> getObjectType() {
        return UploadService.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
