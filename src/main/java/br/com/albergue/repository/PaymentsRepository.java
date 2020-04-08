package br.com.albergue.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.albergue.domain.Payments;

public interface PaymentsRepository extends JpaRepository<Payments, Long>{

}
