package br.com.hostel.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DailyRate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private double price;
	
	public DailyRate() {}
	
	public DailyRate(double price) {
		 this.price = price;
	}

	public double getPrice() {
		return price;
	}

	public void setValor(double price) {
		this.price = price;
	}	
}
