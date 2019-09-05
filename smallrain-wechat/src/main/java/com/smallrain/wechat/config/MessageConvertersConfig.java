package com.smallrain.wechat.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

@Configuration
public class MessageConvertersConfig {

  @Bean
  public HttpMessageConverters fastJsonHttpMessageConverters() {
    // 1.定义一个converters转换消息的对象
    FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
    // 2.添加fastjson的配置信息，比如: 是否需要格式化返回的json数据
    FastJsonConfig fastJsonConfig = new FastJsonConfig();
    fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat, SerializerFeature.WriteNullListAsEmpty, // List字段如果为null,输出为[],而非null
        SerializerFeature.WriteNullStringAsEmpty, // 字符类型字段如果为null,输出为"",而非null
        SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null,输出为falseJ,而非null
        SerializerFeature.DisableCircularReferenceDetect, // 消除对同一对象循环引用的问题，默认为false（如果不配置有可能会进入死循环）
        SerializerFeature.WriteMapNullValue // 是否输出值为null的字段,默认为false。
    );
    List<MediaType> fastMediaTypes = new ArrayList<MediaType>();
    fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
    fastConverter.setSupportedMediaTypes(fastMediaTypes);
    
    // 3.在converter中添加配置信息
    fastConverter.setFastJsonConfig(fastJsonConfig);
    // 4.将converter赋值给HttpMessageConverter
    HttpMessageConverter<?> converter = fastConverter;
    // 5.返回HttpMessageConverters对象
    return new HttpMessageConverters(converter);
  }

}
