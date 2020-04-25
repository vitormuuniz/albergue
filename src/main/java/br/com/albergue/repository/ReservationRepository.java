package br.com.albergue.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.albergue.domain.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long>{
	
	Page<Reservation> findByReservationDate(LocalDate date, Pageable paginacao);

}
