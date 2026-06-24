package com.example.movieticket.dto;

import lombok.Data;

@Data
public class TimeSlotDto {
	
	//次の座席選択画面へ遷移するためのリンクID
	private Long showtimeId;
	
	//画面表示用の時間
	private String timeRange;
	
	//画面表示用のスクリーン名
	private String screenName;
	
	//画面表示用の空席状況マーク
	private String statusMark;
	
	//色を変えるためのCSSクラス名
	private String statusCss;
}