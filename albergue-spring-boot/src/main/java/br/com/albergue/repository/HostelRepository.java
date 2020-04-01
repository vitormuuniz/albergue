package br.com.albergue.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.albergue.domain.Hostel;

public interface HostelRepository extends JpaRepository<Hostel, Long>{
	Optional<Hostel> findByName(String name);

}
