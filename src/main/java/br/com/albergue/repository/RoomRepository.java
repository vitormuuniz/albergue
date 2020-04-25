package br.com.albergue.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.albergue.domain.Room;

public interface RoomRepository extends JpaRepository<Room, Long>{

	Page<Room> findByNumber(int number, Pageable pagination);
	

}

