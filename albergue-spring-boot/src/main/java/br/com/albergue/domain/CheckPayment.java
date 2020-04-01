package br.com.albergue.domain;


public class CheckPayment extends Payment {
	
	private String bankId;
	private String bankName;
	private String branchNumber;
	
	public String toString( ) {
		String result = "Payment with check...: " +  "\n" +    
                 super.toString() + "\n" +
                "Bank ID...: " + this.bankId + "\n" +
                "Bank name...: " + this.bankName + "\n" +
                "Branch number...: " + this.branchNumber;
		return result;
	}

}
