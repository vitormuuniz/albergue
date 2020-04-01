package br.com.albergue.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="payment_type", 
  discriminatorType = DiscriminatorType.INTEGER)
public abstract class Payments {
	
	@Id
	@Column(nullable = false)
	@GeneratedValue(generator = "PaymentIdGenerator")
	private Long id;
	
	protected double amount;
	protected LocalDateTime dateTime;
	
	public static Payments createPayment(String type) {
		Payments payment = null;
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
	
	public Long getId() {
	    return id;
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
		
		Payments cashPayment = Payments.createPayment("Cash");
		cashPayment.setAmount(500.00);
		cashPayment.setDate(LocalDateTime.now());
		System.out.println(cashPayment);
		
		System.out.println("------------------------------");
		
		Payments cardPayment = Payments.createPayment("Credit Card");
		cardPayment.setAmount(1750.00);
		cardPayment.setDate(LocalDateTime.now());
		System.out.println(cardPayment);
		
		System.out.println("------------------------------");
		
		Payments checkPayment = Payments.createPayment("Check");
		checkPayment.setAmount(225.72);
		checkPayment.setDate(LocalDateTime.now());
		System.out.println(checkPayment);		
	}
}

