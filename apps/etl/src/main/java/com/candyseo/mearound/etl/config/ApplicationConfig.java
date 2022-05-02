package com.candyseo.mearound.etl.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.candyseo.mearound.etl.message.ConcurrentMessageBuffer;
import com.candyseo.mearound.etl.message.ConsoleMessageSender;
import com.candyseo.mearound.etl.message.DataParsorPolicy;
import com.candyseo.mearound.etl.message.FileMessageReader;
import com.candyseo.mearound.etl.message.Message;
import com.candyseo.mearound.etl.message.MessageBuffer;
import com.candyseo.mearound.etl.message.MessageReader;
import com.candyseo.mearound.etl.message.MessageSender;
import com.candyseo.mearound.etl.message.TemplateToMessageParsorPolicy;
import com.candyseo.mearound.etl.message.amqp.AmqpMessageConverter;
import com.candyseo.mearound.etl.message.amqp.AmqpMessageQueue;
import com.candyseo.mearound.etl.message.amqp.AmqpMessageSender;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.FileCopyUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableScheduling
@EnableRabbit
@Configuration
@ComponentScan("com.candyseo.mearound.etl")
@Import(value=RedisConfig.class)
public class ApplicationConfig {
    
    @Value("${message.reader.template-path:data/template.txt}")
	private String templatePath;

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		return objectMapper;
	}

	@Bean
	public MessageReader messageReader(MessageBuffer messageBuffer) throws IOException {
		log.info("templatePath: {}", templatePath);
		FileMessageReader messageReader = new FileMessageReader(messageBuffer, dataParsorPolicy());
		File template = File.createTempFile("template", ".txt");
		try (InputStream inputStream = new ClassPathResource(templatePath).getInputStream()) {					
			FileCopyUtils.copy(inputStream, new FileOutputStream(template));
		}

		messageReader.setFile(template);
        return messageReader;	
	}

	@Bean
	public DataParsorPolicy<Message, String> dataParsorPolicy() {
		return new TemplateToMessageParsorPolicy();
	}

	@Profile("test")
	public static class testConfiguration {

		@Bean
		public MessageSender messageSender() {
			ConsoleMessageSender messageSender = new ConsoleMessageSender();
			messageSender.setMessageBuffer(messageBuffer());

			return messageSender;
		}

		@Bean
		public MessageBuffer messageBuffer() {
			return new ConcurrentMessageBuffer();
		}
	}

	@Profile("prod")
	public static class prodConfiguration {

		@Value("${rabbitmq.topic.exchange:mearound-exchange}")
		private String topicExchangeName;

		@Value("${rabbitmq.topic.sensorvalue:sensor-values}")
		private String sensorValueQueueName;

		@Value("${rabbitmq.topic.routing-key:mearound.sensor.values}")
		private String routingKey;

		@Bean
		public MessageSender messageSender() {
			AmqpMessageSender messageSender = new AmqpMessageSender();
			messageSender.setMessageBuffer(amqpMessageQueue());

			return messageSender;
		}

		@Bean
		public AmqpMessageQueue amqpMessageQueue() {
			return new AmqpMessageQueue(sensorValueQueueName);
		}

		@Bean
		public TopicExchange topicExchange() {
			return new TopicExchange(topicExchangeName);
		}

		@Bean
		public Binding binding() {
			return BindingBuilder.bind(amqpMessageQueue())
								 .to(topicExchange())
								 .with(routingKey);
		}

		@Bean
		public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, ObjectMapper objectMapper) {
			RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
			rabbitTemplate.setMessageConverter(amqpMessageConverter(objectMapper));
			return rabbitTemplate;
		}

		@Bean
		public AmqpMessageConverter amqpMessageConverter(ObjectMapper objectMapper) {
			return new AmqpMessageConverter(messageConverter(), objectMapper);
		}

		@Bean
		public MessageConverter messageConverter() {
			return new Jackson2JsonMessageConverter();
		}
	}
}
