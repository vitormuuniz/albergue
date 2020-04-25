package br.com.albergue.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Room {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int number;
	private double dimension;
	@OneToOne
	private DailyRate dailyRate;
	
	public Room() {
		
	}
	
	public Room(int number, double dimension) {
		this.number = number;
		this.dimension = dimension;
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

	public String toString( ) {
		String resultado = "Room number...: " + this.number + "\n" +
	                                    "Room dimension (m2)...: " + this.dimension + "\n";
		return resultado;
	}

}
