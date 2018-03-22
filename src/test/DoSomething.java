package test;

import java.util.HashSet;

import connexions.AIRRequest;
import util.BalanceAndDate;

public class DoSomething {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String msisdn = "22997938994";

		// HashSet<BalanceAndDate> balances = new HashSet<BalanceAndDate>();
		// balances.add(new BalanceAndDate(0, 2500, 1));

		// boolean responseCode = new AIRRequest().updateBalanceAndDate(msisdn, balances, "TEST", "TEST", "ebafrique");
		
		BalanceAndDate balance = new AIRRequest().getBalanceAndDate(msisdn, 0);
		
		System.out.println(balance.getAccountID() + " " + balance.getAccountValue() + "  " + balance.getExpiryDate() + "  " + balance.getServiceFee());
	}

}
