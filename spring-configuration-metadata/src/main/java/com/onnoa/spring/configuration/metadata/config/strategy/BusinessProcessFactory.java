package com.onnoa.spring.configuration.metadata.config.strategy;


public interface BusinessProcessFactory<R, T> {

    R businessProcess(T t);
}
