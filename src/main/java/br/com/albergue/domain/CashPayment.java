package br.com.albergue.domain;


public class CashPayment extends Payment {
	private double amountTendered;
	
	@Override
	public String toString( ) {
		String result = "Payment in cash...: " +  "\n" +  
	             super.toString() + "\n" +
                "Amount tendered..: " + amountTendered;
		return result;
	}
}
