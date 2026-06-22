package com.example.movieticket.service.user;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.example.movieticket.dto.DateTabDto;
import com.example.movieticket.repository.ShowtimeRepository;

@Service
public class ShowtimeService {
	
	//使いたいRepositoryを用意
	private final ShowtimeRepository showtimeRepository;
	
	//用意したツールをSpringから受け取る（DI：依存性の注入）
	public ShowtimeService(ShowtimeRepository showtimeRepository) {
		this.showtimeRepository = showtimeRepository;
	}
	
	// ==========================================
	// ① 7日間分の日付生成メソッド
	// ==========================================
	public List<DateTabDto> getSevenDaysTabs() {
		
		//今日の日付を取得
		LocalDate today = LocalDate.now();
		
		//画面表示用のフォーマットを用意(日付・曜日)
		DateTimeFormatter monthDayFormatter = DateTimeFormatter.ofPattern("M/d");
		DateTimeFormatter dayOfWeekFormatter = DateTimeFormatter.ofPattern("(E)",Locale.JAPANESE );
		
		//ループ処理（7日間分）
		List<DateTabDto> tabs = IntStream.range(0, 7).mapToObj(i -> {
			
			//iで対象日を7日分取得
			LocalDate targetDate = today.plusDays(i);
			
			//DTOを用意し、データを詰める
			DateTabDto dto = new DateTabDto();
			
			//HTMLのID用
			dto.setDateId("date-" + targetDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			
			//画面表示用
			dto.setMonthDay(targetDate.format(monthDayFormatter));
			dto.setDayOfWeek(targetDate.format(dayOfWeekFormatter));
			
			//後の計算用に生データも取得
			dto.setRowDate(targetDate);
			
			return dto;
		})
		.collect(Collectors.toList());
		
		return tabs;
	}
}