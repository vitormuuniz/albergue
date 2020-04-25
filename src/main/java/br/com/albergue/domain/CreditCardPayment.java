package br.com.albergue.domain;

import java.time.LocalDate;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("3")
public class CreditCardPayment extends Payments{
	private double amount;  
	private String issuer;
	private String number;
	private String nameOnCard;
	private LocalDate expirationDate;
	private String securityCode;
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public String getIssuer() {
		return issuer;
	}
	
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getNameOnCard() {
		return nameOnCard;
	}
	
	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}
	
	public LocalDate getExpirationDate() {
		return expirationDate;
	}
	
	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}
	
	public String getSecurityCode() {
		return securityCode;
	}
	
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
	
	public boolean authorize() {
		System.out.println("Requesting authorization to " + issuer);
		return true;
	}
	
	public String toString() {
		String result = "Payment with credit card...: " +  "\n" +  
								super.toString() + "\n" +
								"Issuer...: "+ this.issuer + "\n" +
				                 "Credit card number...: " + this.number + "\n" +
				                 "Name on card...: " + this.nameOnCard + "\n" +
				                 "Expiration date...: " + this.expirationDate + "\n" +
				                 "Security code...: " + this.securityCode;
		return result;
	}
	
}
