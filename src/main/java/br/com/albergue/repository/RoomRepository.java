package br.com.albergue.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.albergue.domain.Room;

public interface RoomRepository extends JpaRepository<Room, Long>{

	List<Room> findByNumber(int number, Pageable pagination);
	

}

