package com.candyseo.mearound.controller;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import com.candyseo.mearound.model.dto.report.Report;
import com.candyseo.mearound.model.dto.report.ReportLevel;
import com.candyseo.mearound.model.dto.report.ReportRaw;
import com.candyseo.mearound.model.dto.report.ReportSummary;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/mearound/reports")
public class ReportController {
    
    @PostMapping(value="/", 
                consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String registUser(@RequestBody Report report) {

        log.info("Request to regist: {}", report);

        return UUID.randomUUID().toString();
    }

    @GetMapping(value="/{reportId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Report getReport(@PathVariable("reportId") String id) {

        log.info("Request to get: id[{}]", id);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime to = LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 0, 0, 0);
        LocalDateTime from = to.plusDays(1).minusSeconds(1);

        List<ReportSummary> summarys = new LinkedList<>();
        summarys.add(new ReportSummary("1", ReportLevel.GOOD, "TEMP", 20.8, 20.9, 20.6, LocalDateTime.now(), LocalDateTime.now()));

        List<ReportRaw> raws = new LinkedList<>();
        raws.add(new ReportRaw("sensorId1", 4, "TEMP", 20.9, now));
        raws.add(new ReportRaw("sensorId1", 3, "TEMP", 20.8, now.minusMinutes(2)));
        raws.add(new ReportRaw("sensorId1", 2, "TEMP", 20.7, now.minusMinutes(4)));
        raws.add(new ReportRaw("sensorId1", 1, "TEMP", 20.6, now.minusMinutes(8)));

        return Report.builder().id(id)
                               .title("daily")
                               .type("regulary")
                               .fromDateTime(from)
                               .toDateTime(to)
                               .registedDateTime(now)
                               .summarys(summarys)
                               .raws(raws)
                               .build();
    }
}
