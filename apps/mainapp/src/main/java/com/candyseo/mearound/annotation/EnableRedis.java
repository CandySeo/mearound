package com.candyseo.mearound.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import com.candyseo.mearound.config.RedisConfiguration;

import org.springframework.context.annotation.Import;

@Target(ElementType.TYPE)
@Import(RedisConfiguration.class)
public @interface EnableRedis {
    
}
