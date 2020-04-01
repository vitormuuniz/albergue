package br.com.albergue.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.albergue.domain.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long>{

}
