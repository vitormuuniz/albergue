package br.com.albergue.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.albergue.domain.Address;
import br.com.albergue.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

	//como curso é outra tabela, se fizer concatenado findByTABELAATRIBUTO
	//o spring ja gera a query automatico 
	
	//se houver uma variavel customerNome, para pegar a variavel nome dentro de Curso
	//seria findByCustomer_Nome
	Page<Customer> findByName(String name, Pageable paginacao);

	Optional<Customer> findByEmail(String email);
	
	Address findByAddressId(Long id);
	
	
}
