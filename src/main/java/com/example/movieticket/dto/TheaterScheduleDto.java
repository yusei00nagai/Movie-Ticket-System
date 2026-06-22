package com.example.movieticket.dto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class TheaterScheduleDto {
    private Long theaterId;
    private String theaterName; // 例: "新宿ピカデリー"
    private String location;    // 例: "東京都"
    
    // ▼ここがマトリョーシカの要！
    // 「どの日付に」「どの時間枠(TimeSlotDto)があるか」を保持するMap
    // キーはLocalDate（例: 2026-06-19）、値は時間枠のリスト
    private Map<LocalDate, List<TimeSlotDto>> dailySchedules = new HashMap<>();
}