package com.example.one.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

public class MyEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private final Properties properties = new Properties();

    /**
     * 存放properties文件名
     */
    private final String[] profiles = {
            "prometheus.properties"
    };


    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        //遍历profiles，加载配置文件
        for (String profile : profiles) {
            Resource resource = new ClassPathResource(profile);
            environment.getPropertySources().addLast(loadProfiles(resource));
        }
        String s = environment.getProperty("management.server.port",String.class);
        System.out.println("mine port="+s);
    }
    //加载配置文件
    private PropertySource<?> loadProfiles(Resource resource) {
        if (!resource.exists()) {
            throw new IllegalArgumentException("file" + resource + "not exist");
        }
        try {
            properties.load(resource.getInputStream());
            return new PropertiesPropertySource(resource.getFilename(), properties);
        } catch (IOException ex) {
            throw new IllegalStateException("load resource exception" + resource, ex);
        }
    }
}
