package com.module.product.configuration;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class GoogleCaptchaConfiguration {

    @Bean
    DefaultKaptcha captchaProducer(){
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");
        properties.setProperty("kaptcha.obscurificator.impl", "com.module.product.common.BlankGimpy");
        properties.setProperty("kaptcha.border", "no");
        properties.setProperty("kaptcha.border.color", "85,131,219");
        properties.setProperty("kaptcha.textproducer.font.color", "33,166,46");
        properties.setProperty("kaptcha.images.width", "125");
        properties.setProperty("kaptcha.session.key", "code");
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        properties.setProperty("kaptcha.textproducer.font.names", "宋体");
        properties.setProperty("kaptcha.textproducer.font.color", "33,166,46");
        properties.setProperty("kaptcha.background.clear.from", "WHITE");
        properties.setProperty("kaptcha.background.clear.to", "WHITE");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

}
