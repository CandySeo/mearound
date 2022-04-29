package com.candyseo.mearound.model.dto.report;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReportSummary {
    
    // Report.id
    private String id;

    private ReportLevel level;

    private String type;

    private double average;

    private double maximum;

    private double minimum;

    private LocalDateTime maximumDateTime;

    private LocalDateTime minimumDateTime;

}
