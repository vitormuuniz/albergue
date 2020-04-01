package br.com.albergue.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.albergue.domain.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
