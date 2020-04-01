package br.com.albergue.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("2")
public class CheckPayment extends Payments {
	
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

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchNumber() {
		return branchNumber;
	}

	public void setBranchNumber(String branchNumber) {
		this.branchNumber = branchNumber;
	}

}
