package com.example.movieticket.service.user;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.movieticket.entity.Theater;
import com.example.movieticket.repository.TheaterRepository;

@Service
public class TheaterService {
	
	//使いたいRepositoryを用意
	private final TheaterRepository theaterRepository;
	
	//用意したツールをspringから受け取る（DI：依存性の注入）
	public TheaterService(TheaterRepository theaterRepository) {
		this.theaterRepository = theaterRepository;
	}
	
	// ==========================================
	// ① 劇場のエリア割り振りメソッド
	// ==========================================
	public Map<String, List<Theater>> getTheatersGroupedByArea() {
		
		//Map箱を用意
		Map<String, List<Theater>> theaterMap = new LinkedHashMap<>();
		//キー：空Listを用意
		theaterMap.put("北海道", new ArrayList<>());
		theaterMap.put("東北", new ArrayList<>());
		theaterMap.put("関東", new ArrayList<>());
		theaterMap.put("中部", new ArrayList<>());
		theaterMap.put("関西", new ArrayList<>());
		theaterMap.put("中国", new ArrayList<>());
		theaterMap.put("四国", new ArrayList<>());
		theaterMap.put("九州", new ArrayList<>());
		
		//全劇場を取得
		List<Theater> allTheater = theaterRepository.findAll();
		
		//劇場をMap箱に振り分ける
		for(Theater theater : allTheater) {
			switch(theater.getArea()) {
				case 1:
					theaterMap.get("北海道").add(theater);
					break;
				case 2:
					theaterMap.get("東北").add(theater);
					break;
				case 3:
					theaterMap.get("関東").add(theater);
					break;
				case 4:
					theaterMap.get("中部").add(theater);
					break;
				case 5:
					theaterMap.get("関西").add(theater);
					break;
				case 6:
					theaterMap.get("中国").add(theater);
					break;
				case 7:
					theaterMap.get("四国").add(theater);
					break;
				case 8:
					theaterMap.get("九州").add(theater);
					break;
				default:
					break;
			}
		}
		
		//振り分けたmapをcontrollerへ渡す
		return theaterMap;
	}
}