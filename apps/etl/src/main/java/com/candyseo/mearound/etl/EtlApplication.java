package com.candyseo.mearound.etl;

import java.io.IOException;

import com.candyseo.mearound.etl.message.ConcurrentMessageBuffer;
import com.candyseo.mearound.etl.message.ConsoleMessageSender;
import com.candyseo.mearound.etl.message.DataParsorPolicy;
import com.candyseo.mearound.etl.message.FileMessageReader;
import com.candyseo.mearound.etl.message.Message;
import com.candyseo.mearound.etl.message.MessageBuffer;
import com.candyseo.mearound.etl.message.MessageReader;
import com.candyseo.mearound.etl.message.MessageSender;
import com.candyseo.mearound.etl.message.TemplateToMessageParsorPolicy;
import com.candyseo.mearound.etl.message.amqp.AmqpMessageQueue;
import com.candyseo.mearound.etl.message.amqp.AmqpMessageSender;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableScheduling
@EnableRabbit
@RestController
@Configuration
@ComponentScan("com.candyseo.mearound.etl")
@SpringBootApplication
public class EtlApplication {

	public static void main(String[] args) {
		SpringApplication.run(EtlApplication.class, args);
	}

	@RequestMapping("/")
	public String hello() {
		return "Hello! ETL service.";
	}

	@Value("${message.reader.template-path:data/template.txt}")
	private String templatePath;

	@Autowired
	private MessageBuffer messageBuffer;

	@Bean
	public MessageReader messageReader() {
		
		FileMessageReader messageReader = new FileMessageReader(messageBuffer, dataParsorPolicy());
		try {
			messageReader.setFile(new ClassPathResource(templatePath).getFile());
			return messageReader;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
			RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
			rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
			return rabbitTemplate;
		}
	}
}
