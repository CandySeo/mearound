package com.candyseo.mearound.model.dto.report;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Report {
    
    private String id;

    private String title;

    private String type;

    private LocalDateTime fromDateTime;

    private LocalDateTime toDateTime;

    private LocalDateTime registedDateTime;

    private List<ReportSummary> summarys;

    private List<ReportRaw> raws;
}
