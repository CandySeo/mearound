package com.candyseo.mearound.model.entity.report;

import java.time.LocalDateTime;

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
public class ReportEntity {
    
    private String id;

    private String title;

    private String type;

    private LocalDateTime fromDateTime;

    private LocalDateTime toDateTime;

    private LocalDateTime registedDateTime;
}
