package br.com.albergue.controller.form;

import javax.validation.constraints.NotNull;

import br.com.albergue.domain.DailyRate;
import br.com.albergue.domain.Room;
import br.com.albergue.repository.DailyRateRepository;

public class RoomForm {

	@NotNull
	int number;
	
	@NotNull
	double dimension;
	
	@NotNull
	private	DailyRate dailyRate;

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
	
	public DailyRate getDailyRate() {
		return dailyRate;
	}

	public void setDailyRate(DailyRate dailyRate) {
		this.dailyRate = dailyRate;
	}
	
	public Room returnRoom(DailyRateRepository dailyRateRepository) {
		dailyRateRepository.save(getDailyRate());
		return new Room(getNumber(), getDimension(), getDailyRate());
	}
}
