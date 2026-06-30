package com.example.movieticket.service.user;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.example.movieticket.dto.DateTabDto;
import com.example.movieticket.dto.TheaterScheduleDto;
import com.example.movieticket.dto.TimeSlotDto;
import com.example.movieticket.entity.Showtime;
import com.example.movieticket.entity.Theater;
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
		//LocalDate today = LocalDate.of(2026, 6, 23); // デバッグ用
		
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
	
	// ==========================================
	// ② 特定映画の上映スケジュール仕分けメソッド
	// ==========================================
	public Map<String, List<TheaterScheduleDto>> getScheduleMapByMovie(Long movieId) {
		
		//Map箱を用意
		Map<String, List<TheaterScheduleDto>> targetShowtimes = new LinkedHashMap<>();
		//キー：空Listを用意
		targetShowtimes.put("北海道", new ArrayList<>());
		targetShowtimes.put("東北", new ArrayList<>());
		targetShowtimes.put("関東", new ArrayList<>());
		targetShowtimes.put("中部", new ArrayList<>());
		targetShowtimes.put("関西", new ArrayList<>());
		targetShowtimes.put("中国", new ArrayList<>());
		targetShowtimes.put("四国", new ArrayList<>());
		targetShowtimes.put("九州", new ArrayList<>());
		
		//今日の日付を取得
		//LocalDate today = LocalDate.now();
		// デバッグ用
		LocalDate today = LocalDate.of(2026, 6, 23);
		
		//今日から6日後の日付
		LocalDate endDay = today.plusDays(6);
		
		//特定映画の上映スケジュールを全件取得
		List<Showtime> showtimes = showtimeRepository.findByMovieIdOrderByScreenTheaterIdAscStartTimeAsc(movieId);
		
		for(Showtime showtime : showtimes) {
			
			//日付＋時間の型を日付のみに変換
			LocalDate targetDate = showtime.getStartTime().toLocalDate();
			
			//期間外であれば、スキップ
			if(targetDate.isBefore(today) || targetDate.isAfter(endDay)) {
				continue;
			}
			
			//エリア名の判定
			String areaName = "";
			switch (showtime.getScreen().getTheater().getArea()) {
				case 1:
					areaName = "北海道";
					break;
				case 2:
					areaName = "東北";
					break;
				case 3:
					areaName = "関東";
					break;
				case 4:
					areaName = "中部";
					break;
				case 5:
					areaName = "関西";
					break;
				case 6:
					areaName = "中国";
					break;
				case 7:
					areaName = "四国";
					break;
				case 8:
					areaName = "九州";
					break;
			}
			
			//保険：エリア名が取得できなかったらスキップ
			if(areaName.isEmpty()) {
				continue;
			}
			
			//マトリョーシカの組み立て
			//１． 一番小さな時間箱(TimeSlotDto)を作成
			TimeSlotDto timeSlot = new TimeSlotDto();
			
			//作成した時間箱に値を格納
			//showtimeIdをセット
			timeSlot.setShowtimeId(showtime.getId());
			
			//時間形式を成形し、timeRangeをセット
			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
			timeSlot.setTimeRange(showtime.getStartTime().format(timeFormatter) + "～" + showtime.getEndTime().format(timeFormatter));
			
			//スクリーン名をセット
			timeSlot.setScreenName(showtime.getScreen().getName());
			
			//statusMark, statusCssをセット 残席情報は一旦仮置き
			timeSlot.setStatusMark("〇");
			timeSlot.setStatusCss("ok");
			
			//２． 大きな箱から、該当エリアの「劇場箱リスト」を取り出す
			List<TheaterScheduleDto> theaterList = targetShowtimes.get(areaName);
			
			//３． リストの中に、今回の劇場箱があるかをチェック
			Theater theater = showtime.getScreen().getTheater();
			TheaterScheduleDto targetTheaterDto = null;
			for(TheaterScheduleDto dto : theaterList) {
				if(dto.getTheaterId().equals(theater.getId())) {
					targetTheaterDto = dto;
					break;
				}
			}
			
			//４． 劇場箱がまだ存在しない場合、新しく作成し、エリアリストに追加
			if(targetTheaterDto == null) {
				targetTheaterDto = new TheaterScheduleDto();
				
				//targetTheaterDtoにTheater情報を詰める
				targetTheaterDto.setTheaterId(theater.getId());
				targetTheaterDto.setTheaterName(theater.getName());
				targetTheaterDto.setLocation(theater.getLocation());
				//空の日付マップを作成
				targetTheaterDto.setDailySchedules(new java.util.TreeMap<>());
				
				theaterList.add(targetTheaterDto);
			}
			
			//５． 劇場箱の日付カレンダーを開き、手順１で作成した時間枠箱を追加
			//対象の日付Mapがなければ作成
			if (!targetTheaterDto.getDailySchedules().containsKey(targetDate)) {
				// スクリーン名を数値順にソートするComparatorを追加
				targetTheaterDto.getDailySchedules().put(targetDate, new TreeMap<>(Comparator.comparingInt(s -> {
					// "SCREEN1" → 1, "SCREEN10" → 10 のように数値部分だけ抽出
					String num = s.replaceAll("[^0-9]", "");
					return num.isEmpty() ? 0 : Integer.parseInt(num);
				})));
			}
			
			//スクリーン名を取得
			String screenName = showtime.getScreen().getName();
		
			//スクリーン名のListがなければ作成
			Map<String, List<TimeSlotDto>> screenMap = targetTheaterDto.getDailySchedules().get(targetDate);
			if (!screenMap.containsKey(screenName)) {
			    screenMap.put(screenName, new ArrayList<>());
			}
		
			//時間枠箱を追加
			screenMap.get(screenName).add(timeSlot);
		}
		return targetShowtimes;
	}
}