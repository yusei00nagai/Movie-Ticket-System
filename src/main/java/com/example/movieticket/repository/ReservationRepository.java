package com.example.movieticket.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.movieticket.entity.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	
	//座席選択画面のAPI用 特定の上映回の全座席ステータスを取得
	List<Reservation> findByShowtimeId(Long showtimeId);
	
	//排他制御用 特定の上映回・座席の予約状況をピンポイントで確認
	Optional<Reservation> findByShowtimeIdAndSeatId(Long showtimeId, Long seatId);
	
	//マイページ用。購入履歴を新しい順に取得
	List<Reservation> findByUserIdOrderByCreatedAtDesc(Long userId);
	
	//バッチ用。期限切れの仮押さえを削除する独自のSQL
	@Modifying
	@Query("DELETE FROM Reservation r WHERE r.status = 'TEMPORARY' AND r.expiresAt < CURRENT_TIMESTAMP")
	void deleteExpiredTemporaryReservations();
}
