package br.com.albergue.controller.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.albergue.domain.DailyRate;
import br.com.albergue.domain.Room;

public class RoomDto {

	private Long id;
	int number;
	double dimension;
	private DailyRate dailyRate;
	
	public RoomDto() {}

	public RoomDto(Room room) {
		this.id = room.getId();
		this.number = room.getNumber();
		this.dimension = room.getDimension();
		this.dailyRate = room.getDailyRate();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public double getDimension() {
		return dimension;
	}

	public DailyRate getDailyRate() {
		return dailyRate;
	}

	public static List<RoomDto> convert(List<Room> roomsList) {
	
		List<RoomDto> roomsDtoList = new ArrayList<>();
		for(Room r : roomsList) {
			roomsDtoList.add(new RoomDto(r));
		}
		
		return roomsDtoList;
	}
	
	public Room returnRoom() {
		return new Room(number, dimension, dailyRate);
	}
	
}
