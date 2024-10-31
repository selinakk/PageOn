package com.tmtb.pageon;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;


@Slf4j
@Configuration
public class FileWebConfig implements WebMvcConfigurer {

    @Value("${file.dir}")
    private String relativePath;

    private String connectPath = "/uploadimgPath/**";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String absolutePath = Paths.get(relativePath).toAbsolutePath().toString();
        registry.addResourceHandler(connectPath).addResourceLocations("file:///" + absolutePath + "/");
    }

}
