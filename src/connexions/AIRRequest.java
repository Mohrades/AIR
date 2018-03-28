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
import util.FaFList;
import util.OfferInformation;
import util.PromotionPlanInformation;
import util.ServiceOfferings;
import util.UsageCounterUsageThresholdInformation;
import util.UsageThreshold;

public class AIRRequest {

	public AIRRequest() {

	}

	public AIRConnector getConnection() {
		AIRConnector air = new AIRConnector("10.10.5.149", 10010, 5);
		if(air.isOpen()) return air;
		
		else {
			air = new AIRConnector("192.168.3.176", 10010, 5);
			if(air.isOpen()) return air;
		}

		return new AIRConnector("10.10.5.153", 10010, 5);
	}

	public BalanceAndDate getBalanceAndDate(String msisdn, int dedicatedAccountID){
		return new GetBalanceAndDate().getData(getConnection(), msisdn,dedicatedAccountID);	
	}

	public boolean updateBalanceAndDate(String msisdn, HashSet<BalanceAndDate> balancesAndDates, String transactionType, String transactionCode, String originOperatorID){
		return new UpdateBalanceAndDate().update(getConnection(), msisdn,balancesAndDates,transactionType,transactionCode,originOperatorID);	
	}

	public boolean updateServiceClass(String msisdn,String serviceClassAction,int serviceClassNew,int serviceClassTemporaryNew,Date serviceClassTemporaryNewExpiryDate,String originOperatorID){
		return  new UpdateServiceClass().update(getConnection(), msisdn,serviceClassAction,serviceClassNew,serviceClassTemporaryNew,serviceClassTemporaryNewExpiryDate,originOperatorID);
	}

	public AccountDetails getAccountDetails(String msisdn){
		return new GetAccountDetails().getData(getConnection(), msisdn);
	}

	public HashSet<AccumulatorInformation> getAccumulators(String msisdn,int[][]accumulatorSelection){
		return new GetAccumulators().getData(getConnection(), msisdn,accumulatorSelection);
	}

	public FaFList getFaFList (String msisdn,int requestedOwner){
		return new GetFaFList().getData(getConnection(), msisdn,requestedOwner);		
	}

	public boolean updateCommunityList(String msisdn,int[] communityOldIDs,int[] communityNewIDs,String originOperatorID){
		return new UpdateCommunityList().update(getConnection(), msisdn,communityOldIDs,communityNewIDs,originOperatorID);
	}

	public boolean updateSubscriberSegmentation(String msisdn,Integer accountGroupID,ServiceOfferings serviceOfferings,String originOperatorID){
		return new UpdateSubscriberSegmentation().update(getConnection(),msisdn,accountGroupID,serviceOfferings,originOperatorID);
	}

	public HashSet<OfferInformation> getOffers(String msisdn,int[][] offerSelection,boolean requestInactiveOffersFlag,String offerRequestedTypeFlag,boolean requestDedicatedAccountDetailsFlag){
		return new GetOffers().getData(getConnection(), msisdn, offerSelection,requestInactiveOffersFlag,offerRequestedTypeFlag, requestDedicatedAccountDetailsFlag);
	}

	public boolean updateOffer(String msisdn,int offerID,Object startDate,Object expiryDate,Integer offerType,String originOperatorID){
		return new UpdateOffer().update(getConnection(), msisdn, offerID,startDate,expiryDate,offerType,originOperatorID);
	}

	public boolean deleteOffer(String msisdn,int offerID,String originOperatorID) {
		return new DeleteOffer().delete(getConnection(), msisdn, offerID,originOperatorID);
	}

	public HashSet<UsageCounterUsageThresholdInformation> getUsageThresholdsAndCounters(String msisdn,String originOperatorID){
		return new GetUsageThresholdsAndCounters().getData(getConnection(), msisdn,originOperatorID);
	}

	public boolean updateUsageThresholdsAndCounters(String msisdn,HashSet<UsageCounterUsageThresholdInformation> usageCounterUpdateInformation,HashSet<UsageThreshold>usageThresholdUpdateInformation,String originOperatorID){
		return new UpdateUsageThresholdsAndCounters().update(getConnection(), msisdn,usageCounterUpdateInformation,usageThresholdUpdateInformation,originOperatorID);
	}

	public boolean updateFaFList(String msisdn,String fafAction,FaFList fafInformation,String originOperatorID){
		return new UpdateFaFList().update(getConnection(), msisdn, fafAction, fafInformation,originOperatorID);
	}

	public boolean deleteAccumulators(String msisdn,Integer serviceClassCurrent ,HashSet<AccumulatorInformation> accumulatorIdentifier,String originOperatorID){
		return new DeleteAccumulators().delete(getConnection(), msisdn, serviceClassCurrent, accumulatorIdentifier,originOperatorID);
	}

	public boolean deleteDedicatedAccounts(String msisdn,Integer serviceClassCurrent ,HashSet<DedicatedAccount> dedicatedAccountIdentifier,String originOperatorID){
		return new DeleteDedicatedAccounts().delete(getConnection(), msisdn, serviceClassCurrent, dedicatedAccountIdentifier,originOperatorID);
	}

	public boolean deleteUsageThresholds(String msisdn,HashSet<UsageThreshold> usageThresholds,String originOperatorID){
		return new DeleteUsageThresholds().delete(getConnection(), msisdn, usageThresholds,originOperatorID);
	}

	public void getPromotionCounters(String msisdn){
		new GetPromotionCounters().getData(getConnection(), msisdn);
	}

	public HashSet<PromotionPlanInformation> getPromotionPlans(String msisdn,String originOperatorID){
		return new GetPromotionPlans().getData(getConnection(), msisdn,originOperatorID);
	}

	public boolean updateAccumulators(String msisdn,HashSet<AccumulatorInformation> accumulatorUpdateInformation,String originOperatorID){
		return new UpdateAccumulators().update(getConnection(), msisdn, accumulatorUpdateInformation,originOperatorID);
	}

	public boolean updatePromotionPlan(String msisdn,String promotionPlanAction,PromotionPlanInformation promotionPlanNew,PromotionPlanInformation promotionPlanOld,String originOperatorID){
		return new UpdatePromotionPlan().update(getConnection(), msisdn, promotionPlanAction, promotionPlanNew,promotionPlanOld,originOperatorID);
	}

	public boolean installSubscriber (String msisdn,int serviceClassNew,boolean temporaryBlockedFlag,String originOperatorID){
		return new InstallSubscriber().install(getConnection(), msisdn, serviceClassNew, temporaryBlockedFlag, originOperatorID);
	}

	public boolean linkSubordinateSubscriber (String msisdn,String masterAccountNumber,String originOperatorID){
		return new LinkSubordinateSubscriber().link(getConnection(), msisdn, masterAccountNumber, originOperatorID);
	}

	public boolean refill(String msisdn,String transactionAmount,String transactionCurrency,String refillProfileID,String voucherActivationCode,String transactionType,String transactionCode,String originOperatorID){
		return new Refill().update(getConnection(), msisdn,transactionAmount,transactionCurrency,refillProfileID,voucherActivationCode,originOperatorID,transactionType,transactionCode);
	}

	public boolean deleteSubscriber(String msisdn,String originOperatorID){
		return new DeleteSubscriber().delete(getConnection(), msisdn,originOperatorID);
	}

	public boolean addPAM(String msisdn, String pamClassID, String pamServiceID, String pamScheduleID, String originOperatorID){
		return new AddPeriodicAccountManagementData().add(getConnection(), msisdn,originOperatorID, pamClassID, pamServiceID, pamScheduleID);
	}

	public boolean deletePAM(String msisdn, int pamClassID, int pamServiceID, int pamScheduleID, String originOperatorID){
		return new DeletePeriodicAccountManagementData().delete(getConnection(), msisdn,originOperatorID, pamClassID, pamServiceID, pamScheduleID);
	}

	public boolean runPAM(String msisdn, int pamServiceID, String originOperatorID ){
		return new RunPeriodicAccountManagement().run(getConnection(), msisdn,originOperatorID, pamServiceID);
	}
}
