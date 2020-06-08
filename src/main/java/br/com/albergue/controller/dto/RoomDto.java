package br.com.albergue.controller.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import br.com.albergue.domain.Reservation;
import br.com.albergue.domain.Room;

public class RoomDto {

	int number;
	double dimension;
	
	public RoomDto() {
	}

	public RoomDto(Room room) {
		this.number = room.getNumber();
		this.dimension = room.getDimension();
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public double getDimension() {
		return dimension;
	}

	public void setDimension(double dimension) {
		this.dimension = dimension;
	}
	
	public Room returnRoom() {
		return new Room(number, dimension);
	}
	
	public static List<RoomDto> converter(List<Room> roomsList) {
		//fazendo um map de topico para topicoDto
		//TopicoDto::new -> recebe o proprio construtor que recebe um topico como parametro
		//collect() -> transforma essa saida em uma lista
//		return topicos.stream().map(TopicoDto::new).collect(Collectors.toList());
	
		List<RoomDto> roomsDtoList = new ArrayList<>();
		for(Room r : roomsList) {
			roomsDtoList.add(new RoomDto(r));
		}
		return roomsDtoList;
	}
	
}
