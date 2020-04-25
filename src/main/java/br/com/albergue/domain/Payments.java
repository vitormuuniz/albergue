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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="payment_type", 
  discriminatorType = DiscriminatorType.INTEGER)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({@Type(value = CashPayment.class, name = "cash"), @Type(value = CheckPayment.class, name = "check"), @Type(value = CreditCardPayment.class, name = "creditCard") })
public abstract class Payments {
	
	@Id
	@Column(nullable = false)
	@GeneratedValue(generator = "PaymentIdGenerator")
	private Long id;
	
	@NotNull
	protected double amount;
	@NotNull
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
}