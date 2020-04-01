package br.com.albergue.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("1")
public class CashPayment extends Payments {
	private double amountTendered;
	
	@Override
	public String toString( ) {
		String result = "Payment in cash...: " +  "\n" +  
	             super.toString() + "\n" +
                "Amount tendered..: " + amountTendered;
		return result;
	}

	public double getAmountTendered() {
		return amountTendered;
	}

	public void setAmountTendered(double amountTendered) {
		this.amountTendered = amountTendered;
	}
	
}
