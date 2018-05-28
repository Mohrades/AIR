package test;

import java.util.Date;
import java.util.HashSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import connexions.AIRRequest;
import util.AccountDetails;
import util.AccumulatorInformation;
import util.BalanceAndDate;
import util.DedicatedAccount;
import util.OfferInformation;
import util.PamInformation;
import util.PamInformationList;

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
		msisdn = "22961437076";
		msisdn = "22961437076";
		msisdn = "22962893693";
		msisdn = "22961437086";

		System.out.println("OK");

		Logger logger = LogManager.getLogger("logging.log4j.AirRequestLogger");
		// Logger logger = LogManager.getRootLogger();
		logger.warn("requete 0");logger.error("requete 1");

		logger = LogManager.getLogger("logging.log4j.AirAvailabilityLogger");
		// logger.error("HOST = ");
		
		System.out.println("OK");

		Date expires = new Date();
		expires.setHours(23);
		expires.setMinutes(59);
		expires.setSeconds(59);
		expires.setDate(31);
		expires.setMonth(4);
		expires.setYear(118);
		
		// System.out.println(new AIRRequest().deleteOffer(msisdn, 641, "eBA", false));
		// System.out.println(new AIRRequest().deleteOffer(msisdn, 642, "eBA", false));
		// System.out.println(new AIRRequest().deleteOffer(msisdn, 643, "eBA", false));
		// System.out.println(new AIRRequest().deleteOffer(msisdn, 644, "eBA", false));

		HashSet<BalanceAndDate> balances = new HashSet<BalanceAndDate>();
		balances.add(new DedicatedAccount(242, -new AIRRequest().getBalanceAndDate(msisdn, 242).getAccountValue(), null));
		balances.add(new DedicatedAccount(262, -new AIRRequest().getBalanceAndDate(msisdn, 262).getAccountValue(), null));

		//System.out.println(new AIRRequest().updateBalanceAndDate(msisdn, balances, "TEST", "TEST", "ebafrique"));

		BalanceAndDate balance = new AIRRequest().getBalanceAndDate(msisdn, 242);
		System.out.println(balance.getAccountID() + " " + balance.getAccountValue() + "  " + balance.getExpiryDate() + "  " + balance.getServiceFee());
		balance = new AIRRequest().getBalanceAndDate(msisdn, 262);
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
		
		PamInformationList pamInformationList = new PamInformationList();
		pamInformationList.add(new PamInformation(24, 524, 500));
		/*if(new AIRRequest().addPeriodicAccountManagementData(msisdn, pamInformationList, true, "eBA")) {
			System.out.println("arg0");
		}
		else {
			System.out.println("arg1");
		}
		
		if(new AIRRequest().deletePeriodicAccountManagementData(msisdn, pamInformationList, "eBA", true)) {
			System.out.println("arg2");
		}
		else {
			System.out.println("arg3");
		}*/
	}

}
