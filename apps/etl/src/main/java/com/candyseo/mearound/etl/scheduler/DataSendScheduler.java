package com.candyseo.mearound.etl.scheduler;

import java.time.LocalDateTime;

import com.candyseo.mearound.etl.message.MessageSender;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DataSendScheduler {

    private MessageSender messageSender;

    public DataSendScheduler(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Scheduled(cron = "0 0/2 * * * ?")
    public void MessageSenderSchedule() {
        log.info("messageSend: {}", LocalDateTime.now());
        messageSender.send();
    }
}
