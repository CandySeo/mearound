package com.candyseo.mearound.etl.scheduler;

import java.time.LocalDateTime;

import com.candyseo.mearound.etl.message.MessageReader;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DataMakeScheduler {

    private MessageReader messageReader;

    public DataMakeScheduler(MessageReader messageReader) {
        this.messageReader = messageReader;
    }

    @Scheduled(cron = "0/30 * * * * ?")
    public void MessageMakerSchedule() {
        log.info("messageMake: {}", LocalDateTime.now());
        messageReader.read();
    }
}
