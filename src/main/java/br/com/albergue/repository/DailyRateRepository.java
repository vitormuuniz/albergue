package br.com.albergue.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.albergue.domain.DailyRate;

public interface DailyRateRepository extends JpaRepository<DailyRate, Long> {

}
