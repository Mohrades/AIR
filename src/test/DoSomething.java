package test;

import java.util.Date;
import java.util.HashSet;

import connexions.AIRRequest;
import util.AccountDetails;
import util.AccumulatorInformation;
import util.BalanceAndDate;
import util.DedicatedAccount;
import util.OfferInformation;

public class DoSomething {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String msisdn = "22997938994";
		msisdn = "22967075745";
		msisdn = "22967191973";
		msisdn = "22966857792";
		msisdn = "22997379819";
		msisdn = "22966857792";
		msisdn = "22962893693";
		msisdn = "22961437066";
		msisdn = "22961000002";
		msisdn = "22961437066";
		
		System.out.println("D".hashCode() + "   " + "d".hashCode());

		Date expires = new Date();
		expires.setHours(23);
		expires.setMinutes(59);
		expires.setSeconds(59);
		expires.setDate(31);
		expires.setMonth(4);
		expires.setYear(118);

		HashSet<BalanceAndDate> balances = new HashSet<BalanceAndDate>();
		balances.add(new DedicatedAccount(263, 0, expires));

		// System.out.println(new AIRRequest().updateBalanceAndDate(msisdn, balances, "TEST", "TEST", "ebafrique"));

		// BalanceAndDate balance = new AIRRequest().getBalanceAndDate(msisdn, 263);
		// System.out.println(balance.getAccountID() + " " + balance.getAccountValue() + "  " + balance.getExpiryDate() + "  " + balance.getServiceFee());

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
			System.out.println("ACC " + ac.getAccumulatorID() + "  " + ac.getAccumulatorValue() + "  " + ac.getAccumulatorStartDate()  + "  " + ac.getAccumulatorEndDate());
		}

		// Date now = new Date();
		// now.setDate(now.getDate() + 3);
		// System.out.println(new AIRRequest().updateOffer(msisdn, 2, 1, 3, null, "ebafrique"));
		// System.out.println(new AIRRequest().deleteOffer(msisdn, 2, "ebafrique"));

		// HashSet<OfferInformation> offers = new AIRRequest().getOffers(msisdn, new int[][] {{2,500}}, false, "10000000", false);
		HashSet<OfferInformation> offers = new AIRRequest().getOffers(msisdn, new int[][] {{2,500}}, false, null, false);
		for (OfferInformation offer : offers) {
			System.out.println("OFF " + offer.getOfferID() + "  " + offer.getOfferType() + "  " + offer.getStartDate()  + "  " + offer.getExpiryDate());
		}
	}

}
