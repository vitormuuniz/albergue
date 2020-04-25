package br.com.albergue.domain;

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
	
	public DailyRate(double price) {
		 this.price = price;
	}

	public double getValor() {
		return price;
	}

	public void setValor(double price) {
		this.price = price;
	}	
}
