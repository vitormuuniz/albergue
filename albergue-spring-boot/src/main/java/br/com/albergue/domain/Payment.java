package br.com.albergue.domain;

import java.time.LocalDateTime;

public abstract class Payment {
	protected double amount;
	protected LocalDateTime dateTime;
	
	public static Payment createPayment(String type) {
		Payment payment = null;
		switch (type) {
			case "Cash" :
				payment = new CashPayment();
				break;
			case "Credit Card" :
				payment = new CreditCardPayment();
				break;
			case "Check" :
				payment = new CheckPayment();
				break;
			default:
				payment = new CashPayment();
		}
	    return payment;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public LocalDateTime getDate() {
		return dateTime;
	}
	
	public void setDate(LocalDateTime date) {
		this.dateTime = date;
	}
	
	public String toString() {
		String result = "Amount...: " + this.amount + "\n" +
                                "Date & time..: " + this.dateTime;
		return result;
	}
	
	public static void main(String[] args) {
		
		Payment cashPayment = Payment.createPayment("Cash");
		cashPayment.setAmount(500.00);
		cashPayment.setDate(LocalDateTime.now());
		System.out.println(cashPayment);
		
		System.out.println("------------------------------");
		
		Payment cardPayment = Payment.createPayment("Credit Card");
		cardPayment.setAmount(1750.00);
		cardPayment.setDate(LocalDateTime.now());
		System.out.println(cardPayment);
		
		System.out.println("------------------------------");
		
		Payment checkPayment = Payment.createPayment("Check");
		checkPayment.setAmount(225.72);
		checkPayment.setDate(LocalDateTime.now());
		System.out.println(checkPayment);		
	}
}

