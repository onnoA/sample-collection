package com.onnoa.shop.demo.video.config;

import com.onnoa.shop.demo.video.properties.VideoFFMpegProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	@Value("${shop.video.fileMaxSize}")
	private Long fileMaxSize;

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver getCommonsMultipartResolver() {
		VideoFFMpegProperties videoFFMpegProperties = new VideoFFMpegProperties();
		//videoFFMpegProperties.getFileMaxSize()
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(fileMaxSize);
		//大于此值则在硬盘上的生成临时文件，否则保存在内存中
		multipartResolver.setMaxInMemorySize(0);
		return multipartResolver;
	}

}
