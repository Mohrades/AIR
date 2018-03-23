package test;

import java.util.HashSet;

import connexions.AIRRequest;
import util.AccountDetails;
import util.AccumulatorInformation;
import util.BalanceAndDate;
import util.OfferInformation;

public class DoSomething {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String msisdn = "22997938994";

		// HashSet<BalanceAndDate> balances = new HashSet<BalanceAndDate>();
		// balances.add(new BalanceAndDate(0, 2500, 1));

		// boolean responseCode = new AIRRequest().updateBalanceAndDate(msisdn, balances, "TEST", "TEST", "ebafrique");

		BalanceAndDate balance = new AIRRequest().getBalanceAndDate(msisdn, 0);
		System.out.println(balance.getAccountID() + " " + balance.getAccountValue() + "  " + balance.getExpiryDate() + "  " + balance.getServiceFee());

		AccountDetails accountDetails = new AIRRequest().getAccountDetails(msisdn);
		if(accountDetails != null) {
			System.out.println(accountDetails.getAccountGroupID());
			System.out.println(accountDetails.getLanguageIDCurrent());
			System.out.println(accountDetails.getServiceClassCurrent());
			System.out.println(accountDetails.getServiceClassOriginal());
			System.out.println(accountDetails.getActivationDate().toLocaleString());
			System.out.println(accountDetails.getSupervisionExpiryDate().toLocaleString());
			System.out.println(accountDetails.isAccountFlags());
			System.out.println(accountDetails.getServiceOfferings().isActiveFlag(35));
		}

		HashSet<AccumulatorInformation> accumulators = new AIRRequest().getAccumulators(msisdn, new int[][] {{1,1},{2,3}});
		for (AccumulatorInformation ac : accumulators) {
			System.out.println(ac.getAccumulatorID() + "  " + ac.getAccumulatorValue() + "  " + ac.getAccumulatorStartDate()  + "  " + ac.getAccumulatorEndDate());
		}

		HashSet<OfferInformation> offers = new AIRRequest().getOffers(msisdn, new int[][] {{1,500}}, true, null, false);
		for (OfferInformation offer : offers) {
			System.out.println(offer.getOfferID() + "  " + offer.getOfferType() + "  " + offer.getStartDate()  + "  " + offer.getExpiryDate());
		}
	}

}
