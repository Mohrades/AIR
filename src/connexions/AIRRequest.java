package connexions;

import ucip.GetAccountDetails;
import ucip.GetAccumulators;
import ucip.GetBalanceAndDate;
import ucip.GetFaFList;
import ucip.GetOffers;
import ucip.GetUsageThresholdsAndCounters;
import ucip.Refill;
import ucip.UpdateBalanceAndDate;
import ucip.UpdateCommunityList;
import ucip.UpdateFaFList;
import ucip.UpdateOffer;
import ucip.UpdateServiceClass;
import ucip.UpdateSubscriberSegmentation;
import ucip.UpdateUsageThresholdsAndCounters;

import java.util.Date;
import java.util.HashSet;

import acip.AddPeriodicAccountManagementData;
import acip.DeleteAccumulators;
import acip.DeleteDedicatedAccounts;
import acip.DeleteOffer;
import acip.DeletePeriodicAccountManagementData;
import acip.DeleteSubscriber;
import acip.DeleteUsageThresholds;
import acip.GetPromotionCounters;
import acip.GetPromotionPlans;
import acip.InstallSubscriber;
import acip.LinkSubordinateSubscriber;
import acip.RunPeriodicAccountManagement;
import acip.UpdateAccumulators;
import acip.UpdatePromotionPlan;
import util.AccountDetails;
import util.AccumulatorInformation;
import util.BalanceAndDate;
import util.DedicatedAccount;
import util.FafInformationList;
import util.OfferInformation;
import util.PamInformationList;
import util.PromotionPlanInformation;
import util.ServiceOfferings;
import util.UsageCounterUsageThresholdInformation;
import util.UsageThreshold;

public class AIRRequest {

	private boolean successfully;

	public AIRRequest() {

	}

	public AIRConnector getConnection() {
		/*AIRConnector air = new AIRConnector("192.168.3.176", 10010, 5);
		if(air.isOpen()) return air;

		else {
			air = new AIRConnector("10.10.5.153", 10010, 5);
			if(air.isOpen()) return air;
		}

		return new AIRConnector("10.10.5.149", 10010, 5);*/

		AIRConnector air = new AIRConnector("10.10.5.157", 10010, 5);
		if(air.isOpen()) return air;

		else {
			air = new AIRConnector("10.10.5.158", 10010, 5);
			if(air.isOpen()) return air;
		}

		return new AIRConnector("10.10.40.96", 10010, 5);
	}

	public boolean isSuccessfully() {
		return successfully;
	}

	public void setSuccessfully(boolean successfully) {
		this.successfully = successfully;
	}

	public BalanceAndDate getBalanceAndDate(String msisdn, int dedicatedAccountID) {
		AIRConnector air = getConnection();

		BalanceAndDate result = new GetBalanceAndDate().getData(air, msisdn,dedicatedAccountID);
		setSuccessfully(air.isAvailable());

		return result;
	}

	public boolean updateBalanceAndDate(String msisdn, HashSet<BalanceAndDate> balancesAndDates, String transactionType, String transactionCode, String originOperatorID) {
		AIRConnector air = getConnection();

		boolean result = new UpdateBalanceAndDate().update(getConnection(), msisdn,balancesAndDates,transactionType,transactionCode,originOperatorID);
		setSuccessfully(air.isAvailable());
		return result;
	}

	public boolean updateServiceClass(String msisdn,String serviceClassAction,int serviceClassNew,int serviceClassTemporaryNew,Date serviceClassTemporaryNewExpiryDate,String originOperatorID) {
		AIRConnector air = getConnection();

		boolean result = new UpdateServiceClass().update(getConnection(), msisdn,serviceClassAction,serviceClassNew,serviceClassTemporaryNew,serviceClassTemporaryNewExpiryDate,originOperatorID);
		setSuccessfully(air.isAvailable());
		return result;
	}

	public AccountDetails getAccountDetails(String msisdn) {
		AIRConnector air = getConnection();

		AccountDetails result = new GetAccountDetails().getData(getConnection(), msisdn);
		setSuccessfully(air.isAvailable());
		return result;
	}

	public HashSet<AccumulatorInformation> getAccumulators(String msisdn,int[][]accumulatorSelection) {
		AIRConnector air = getConnection();

		HashSet<AccumulatorInformation> result = new GetAccumulators().getData(getConnection(), msisdn,accumulatorSelection);
		setSuccessfully(air.isAvailable());
		return result;
	}

	public FafInformationList getFaFList (String msisdn, int requestedOwner) {
		AIRConnector air = getConnection();

		FafInformationList result = new GetFaFList().getData(getConnection(), msisdn,requestedOwner);
		setSuccessfully(air.isAvailable());
		return result;
	}

	public boolean updateCommunityList(String msisdn,int[] communityOldIDs,int[] communityNewIDs,String originOperatorID) {
		AIRConnector air = getConnection();

		boolean result = new UpdateCommunityList().update(getConnection(), msisdn,communityOldIDs,communityNewIDs,originOperatorID);
		setSuccessfully(air.isAvailable());
		return result;
	}

	public boolean updateSubscriberSegmentation(String msisdn, Integer accountGroupID, ServiceOfferings serviceOfferings, String originOperatorID) {
		AIRConnector air = getConnection();

		boolean result = new UpdateSubscriberSegmentation().update(getConnection(),msisdn,accountGroupID,serviceOfferings,originOperatorID);
		setSuccessfully(air.isAvailable());
		return result;
	}

	public HashSet<OfferInformation> getOffers(String msisdn,int[][] offerSelection,boolean requestInactiveOffersFlag,String offerRequestedTypeFlag,boolean requestDedicatedAccountDetailsFlag) {
		AIRConnector air = getConnection();

		HashSet<OfferInformation> result = new GetOffers().getData(getConnection(), msisdn, offerSelection,requestInactiveOffersFlag,offerRequestedTypeFlag, requestDedicatedAccountDetailsFlag);
		setSuccessfully(air.isAvailable());
		return result;
	}

	public boolean updateOffer(String msisdn,int offerID,Object startDate,Object expiryDate,Integer offerType,String originOperatorID) {
		AIRConnector air = getConnection();

		boolean result =  new UpdateOffer().update(getConnection(), msisdn, offerID,startDate,expiryDate,offerType,originOperatorID);
		setSuccessfully(air.isAvailable());
		return result;
	}

	public boolean deleteOffer(String msisdn, int offerID, String originOperatorID, boolean acceptOfferNotFound) {
		AIRConnector air = getConnection();

		boolean result = new DeleteOffer().delete(getConnection(), msisdn, offerID, originOperatorID, acceptOfferNotFound);
		setSuccessfully(air.isAvailable());
		return result;
	}

	public HashSet<UsageCounterUsageThresholdInformation> getUsageThresholdsAndCounters(String msisdn,String originOperatorID) {
		AIRConnector air = getConnection();

		HashSet<UsageCounterUsageThresholdInformation> result = new GetUsageThresholdsAndCounters().getData(getConnection(), msisdn,originOperatorID);
		setSuccessfully(air.isAvailable());
		return result;
	}

	public boolean updateUsageThresholdsAndCounters(String msisdn,HashSet<UsageCounterUsageThresholdInformation> usageCounterUpdateInformation,HashSet<UsageThreshold>usageThresholdUpdateInformation,String originOperatorID) {
		AIRConnector air = getConnection();

		boolean result = new UpdateUsageThresholdsAndCounters().update(getConnection(), msisdn,usageCounterUpdateInformation,usageThresholdUpdateInformation,originOperatorID);
		setSuccessfully(air.isAvailable());
		return result;
	}

	public boolean updateFaFList(String msisdn, String fafAction, FafInformationList fafInformation, String originOperatorID) {
		AIRConnector air = getConnection();

		boolean result = new UpdateFaFList().update(getConnection(), msisdn, fafAction, fafInformation,originOperatorID);
		setSuccessfully(air.isAvailable());
		return result;
	}

	public boolean deleteAccumulators(String msisdn,Integer serviceClassCurrent ,HashSet<AccumulatorInformation> accumulatorIdentifier,String originOperatorID) {
		AIRConnector air = getConnection();

		boolean result = new DeleteAccumulators().delete(getConnection(), msisdn, serviceClassCurrent, accumulatorIdentifier,originOperatorID);
		setSuccessfully(air.isAvailable());
		return result;
	}

	public boolean deleteDedicatedAccounts(String msisdn,Integer serviceClassCurrent ,HashSet<DedicatedAccount> dedicatedAccountIdentifier,String originOperatorID) {
		AIRConnector air = getConnection();

		boolean result = new DeleteDedicatedAccounts().delete(getConnection(), msisdn, serviceClassCurrent, dedicatedAccountIdentifier,originOperatorID);
		setSuccessfully(air.isAvailable());
		return result;
	}

	public boolean deleteUsageThresholds(String msisdn,HashSet<UsageThreshold> usageThresholds,String originOperatorID) {
		AIRConnector air = getConnection();

		boolean result = new DeleteUsageThresholds().delete(getConnection(), msisdn, usageThresholds,originOperatorID);
		setSuccessfully(air.isAvailable());
		return result;
	}

	public void getPromotionCounters(String msisdn) {
		AIRConnector air = getConnection();

		new GetPromotionCounters().getData(getConnection(), msisdn);
		setSuccessfully(air.isAvailable());
	}

	public HashSet<PromotionPlanInformation> getPromotionPlans(String msisdn,String originOperatorID) {
		AIRConnector air = getConnection();

		HashSet<PromotionPlanInformation> result = new GetPromotionPlans().getData(getConnection(), msisdn,originOperatorID);
		setSuccessfully(air.isAvailable());
		return result;
	}

	public boolean updateAccumulators(String msisdn,HashSet<AccumulatorInformation> accumulatorUpdateInformation,String originOperatorID) {
		AIRConnector air = getConnection();

		boolean result = new UpdateAccumulators().update(getConnection(), msisdn, accumulatorUpdateInformation,originOperatorID);
		setSuccessfully(air.isAvailable());
		return result;
	}

	public boolean updatePromotionPlan(String msisdn,String promotionPlanAction,PromotionPlanInformation promotionPlanNew,PromotionPlanInformation promotionPlanOld,String originOperatorID) {
		AIRConnector air = getConnection();

		boolean result = new UpdatePromotionPlan().update(getConnection(), msisdn, promotionPlanAction, promotionPlanNew,promotionPlanOld,originOperatorID);
		setSuccessfully(air.isAvailable());
		return result;
	}

	public boolean installSubscriber (String msisdn,int serviceClassNew,boolean temporaryBlockedFlag,String originOperatorID) {
		AIRConnector air = getConnection();

		boolean result = new InstallSubscriber().install(getConnection(), msisdn, serviceClassNew, temporaryBlockedFlag, originOperatorID);
		setSuccessfully(air.isAvailable());
		return result;
	}

	public boolean linkSubordinateSubscriber (String msisdn,String masterAccountNumber,String originOperatorID) {
		AIRConnector air = getConnection();

		boolean result = new LinkSubordinateSubscriber().link(getConnection(), msisdn, masterAccountNumber, originOperatorID);
		setSuccessfully(air.isAvailable());
		return result;
	}

	public boolean refill(String msisdn,String transactionAmount,String transactionCurrency,String refillProfileID,String voucherActivationCode,String transactionType,String transactionCode,String originOperatorID) {
		AIRConnector air = getConnection();

		boolean result = new Refill().update(getConnection(), msisdn,transactionAmount,transactionCurrency,refillProfileID,voucherActivationCode,originOperatorID,transactionType,transactionCode);
		setSuccessfully(air.isAvailable());
		return result;
	}

	public boolean deleteSubscriber(String msisdn,String originOperatorID) {
		AIRConnector air = getConnection();

		boolean result = new DeleteSubscriber().delete(getConnection(), msisdn,originOperatorID);
		setSuccessfully(air.isAvailable());
		return result;
	}

	public boolean addPeriodicAccountManagementData(String msisdn, PamInformationList pamInformationList, String originOperatorID) {
		AIRConnector air = getConnection();

		boolean result = new AddPeriodicAccountManagementData().add(getConnection(), msisdn, pamInformationList, originOperatorID);
		setSuccessfully(air.isAvailable());
		return result;
	}

	public boolean deletePeriodicAccountManagementData(String msisdn, PamInformationList pamInofmrationList, String originOperatorID) {
		AIRConnector air = getConnection();

		boolean result = new DeletePeriodicAccountManagementData().delete(getConnection(), msisdn,originOperatorID, pamInofmrationList);
		setSuccessfully(air.isAvailable());
		return result;
	}

	public boolean runPeriodicAccountManagement(String msisdn, int pamServiceID, String originOperatorID ) {
		AIRConnector air = getConnection();

		boolean result = new RunPeriodicAccountManagement().run(getConnection(), msisdn, pamServiceID, originOperatorID);
		setSuccessfully(air.isAvailable());
		return result;
	}
}
