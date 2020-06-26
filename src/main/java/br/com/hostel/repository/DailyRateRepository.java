package br.com.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.hostel.domain.DailyRate;

public interface DailyRateRepository extends JpaRepository<DailyRate, Long> {

}
