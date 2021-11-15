package com.onnoa.spring.configuration.metadata.config.strategy;

import com.google.common.collect.Maps;
import com.onnoa.spring.configuration.metadata.config.utils.ReflectUtil;
import org.springframework.core.annotation.AnnotationUtils;


import java.util.List;
import java.util.Map;

public class BusinessHandlerChoose {

    private Map<BusinessProcessAnnotation, BusinessProcessFactory> factoryMap;


    public void setBusinessHandlerMap(List<BusinessProcessFactory> businessProcessFactoryList){
        Map<BusinessProcessAnnotation, BusinessProcessFactory> factoryHashMap = Maps.newHashMap();
        for (BusinessProcessFactory businessProcessFactory : businessProcessFactoryList) {
            BusinessProcessAnnotation annotation = AnnotationUtils.findAnnotation(businessProcessFactory.getClass(), BusinessProcessAnnotation.class);
            System.out.println("每个annotation hashCode :" + annotation.hashCode());
            factoryHashMap.put(annotation, businessProcessFactory);
        }
        factoryMap = factoryHashMap;
        for (BusinessProcessAnnotation businessProcessAnnotation : factoryMap.keySet()) {
            System.out.println("annontation:" + businessProcessAnnotation.hashCode());
        }
        for (Map.Entry<BusinessProcessAnnotation, BusinessProcessFactory> entry : factoryMap.entrySet()) {
            System.out.println("entry :" + entry);
        }
    }

    public <R, T> BusinessProcessFactory<R, T> businessHandlerChooser(String type, String source){
        BusinessProcessAnnotation businessProcessAnnotation = new BusinessProcessImpl(type, source);
        return factoryMap.get(businessProcessAnnotation);

    }



    public static void main(String[] args) throws Exception {
        BusinessProcessImpl businessProcess = new BusinessProcessImpl();
        ReflectUtil.getObjAttrValue(BusinessProcessImpl.class);
//        BusinessProcessImpl businessProcess = new BusinessProcessImpl();
//        ReflectUtil.getObjectValue(businessProcess);
    }


}
