package com.candyseo.mearound.etl;

import com.candyseo.mearound.etl.message.AmqpMessageQueue;
import com.candyseo.mearound.etl.message.AmqpMessageSender;
import com.candyseo.mearound.etl.message.ConcurrentMessageBuffer;
import com.candyseo.mearound.etl.message.ConsoleMessageSender;
import com.candyseo.mearound.etl.message.DataParsorPolicy;
import com.candyseo.mearound.etl.message.Message;
import com.candyseo.mearound.etl.message.MessageBuffer;
import com.candyseo.mearound.etl.message.MessageSender;
import com.candyseo.mearound.etl.message.StringToMessageParsorPolicy;

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

@EnableRabbit
@Configuration
@ComponentScan("com.candyseo.mearound.etl")
@SpringBootApplication
public class EtlApplication {

	public static void main(String[] args) {
		SpringApplication.run(EtlApplication.class, args);
	}

	@Bean
	public DataParsorPolicy<Message, String> dataParsorPolicy() {
		return new StringToMessageParsorPolicy();
	}

	@Profile("test")
	public static class testConfiguration {

		@Autowired
		private MessageBuffer messageBuffer;

		@Bean
		public MessageSender messageSender() {
			ConsoleMessageSender messageSender = new ConsoleMessageSender();
			messageSender.setMessageBuffer(messageBuffer);

			return messageSender;
		}

		@Bean
		public MessageBuffer messageBuffeR() {
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
