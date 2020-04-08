package br.com.albergue.controller.form;

import javax.validation.constraints.NotNull;

import br.com.albergue.domain.Room;

public class RoomForm {

	@NotNull
	int number;
	
	@NotNull
	double dimension;

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
}
