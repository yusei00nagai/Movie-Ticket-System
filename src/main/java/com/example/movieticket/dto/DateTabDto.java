package com.example.movieticket.dto;

import java.time.LocalDate;

import lombok.Data;

/**
 * 映画詳細画面の「日付タブ」を表示するための専用の箱（DTO）
 */
@Data
public class DateTabDto {
	
	//HTMLのid属性などで使用する一意の文字
	private String dateId;
	
	//画面表示用の日付
	private String monthDay;
	
	//画面表示用の曜日
	private String dayOfWeek;
	
	//計算や比較に使用するための生のLocalDateデータ
	private LocalDate rowDate;
}