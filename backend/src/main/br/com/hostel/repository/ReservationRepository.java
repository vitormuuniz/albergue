package br.com.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.hostel.domain.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long>{
	
}
