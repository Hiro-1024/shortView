package com.shortview.disposal.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        // 对 key 进行序列
        RedisSerializer<String> keySerializer = new StringRedisSerializer();
        // 创建value的序列化器
        Jackson2JsonRedisSerializer<Object> valueRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.activateDefaultTypingAsProperty(mapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL, "@class");
        valueRedisSerializer.setObjectMapper(mapper);

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory);
        template.setKeySerializer(keySerializer);// key序列化
        template.setValueSerializer(valueRedisSerializer);// value序列化
        template.setHashKeySerializer(keySerializer);// Hash key序列化
        template.setHashValueSerializer(valueRedisSerializer);// Hash value序列化
        template.afterPropertiesSet();

        return template;
    }
}
