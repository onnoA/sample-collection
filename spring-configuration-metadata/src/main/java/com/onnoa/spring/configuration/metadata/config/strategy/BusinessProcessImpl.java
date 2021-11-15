package com.onnoa.spring.configuration.metadata.config.strategy;

import com.google.common.collect.Maps;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;

@Slf4j
@Data
@NoArgsConstructor
public class BusinessProcessImpl implements BusinessProcessAnnotation {

    private String type;

    private String source;

    public BusinessProcessImpl(String type, String source) {
        this.type = type;
        this.source = source;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return BusinessProcessAnnotation.class;
    }

    @Override
    public String type() {
        return null;
    }

    @Override
    public String source() {
        return null;
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        int typeCode1 = (127 * "type".hashCode());
        int typeCode2 = type.hashCode();
        hashCode = typeCode1 ^ typeCode2;
        hashCode += (127 * "source".hashCode()) ^ source.hashCode();
        log.info("hashCode() called - Computed hash: " + hashCode);
        return hashCode;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BusinessProcessAnnotation)) {
            return false;
        }
        BusinessProcessAnnotation businessProcessAnnotation = (BusinessProcessAnnotation) o;
        return type.equals(businessProcessAnnotation.type()) && source.equals(businessProcessAnnotation.source());


    }
}
