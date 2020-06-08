package br.com.albergue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.albergue.domain.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long>{
	
}
