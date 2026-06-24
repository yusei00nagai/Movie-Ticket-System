package com.example.movieticket.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lombok.Data;

@Data
public class TheaterScheduleDto {
    
    private Long theaterId;
    private String theaterName;
    private String location;
    
    // ▼ 日付 -> スクリーン名 -> 時間枠リスト の3階層に変更し、日付順になるようTreeMapで初期化
    private Map<LocalDate, Map<String, List<TimeSlotDto>>> dailySchedules = new TreeMap<>();

}