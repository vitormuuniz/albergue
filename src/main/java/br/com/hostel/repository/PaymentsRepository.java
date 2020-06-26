package br.com.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.hostel.domain.Payments;

public interface PaymentsRepository extends JpaRepository<Payments, Long>{

}
