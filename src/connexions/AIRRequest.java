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
		return new AIRConnector("172.30.206.86", 10010,5);
	}

	public BalanceAndDate getBalanceAndDate(String msisdn, int dedicatedAccountID){
		return new GetBalanceAndDate().getValues(getConnection(), msisdn,dedicatedAccountID);	
	}

	public boolean updateBalanceAndDate(String msisdn, HashSet<BalanceAndDate> balancesAndDates, String transactionType, String transactionCode, String originOperatorID){
		return new UpdateBalanceAndDate().update(getConnection(), msisdn,balancesAndDates,transactionType,transactionCode,originOperatorID);	
	}

	public boolean updateServiceClass(String msisdn,String serviceClassAction,int serviceClassNew,int serviceClassTemporaryNew,Date serviceClassTemporaryNewExpiryDate,String originOperatorID){
		return  new UpdateServiceClass().update(getConnection(), msisdn,serviceClassAction,serviceClassNew,serviceClassTemporaryNew,serviceClassTemporaryNewExpiryDate,originOperatorID);
	}

	public AccountDetails getAccountDetails(String msisdn){
		return new GetAccountDetails().getDonnees(getConnection(), msisdn);
	}

	public HashSet<AccumulatorInformation> getAccumulators(String msisdn,int[][]accumulatorSelection){
		return new GetAccumulators().getDonnees(getConnection(), msisdn,accumulatorSelection);
	}
	
	public FaFList getFaFList (String msisdn,int requestedOwner){
		return new GetFaFList().getDonnees(getConnection(), msisdn,requestedOwner);		
	}

	public boolean updateCommunityList(String msisdn,int[] communityOldIDs,int[] communityNewIDs,String originOperatorID){
		return new UpdateCommunityList().update(getConnection(), msisdn,communityOldIDs,communityNewIDs,originOperatorID);
	}
      
	public boolean updateSubscriberSegmentation(String number,Integer accountGroupID,ServiceOfferings serviceOfferings,String originOperatorID){
		UpdateSubscriberSegmentation update=new UpdateSubscriberSegmentation(this);
        update.formerRequete(number,accountGroupID,serviceOfferings,originOperatorID);
        return update.update();
	}
	
	public HashSet<OfferInformation> getOffers(String number,int[][] offerSelection,boolean requestInactiveOffersFlag,String offerRequestedTypeFlag,boolean requestedDedicatedAccountDeatilsFlag){
		GetOffers offers=new GetOffers(this);
  	  	offers.formerRequete(number, offerSelection,requestInactiveOffersFlag,offerRequestedTypeFlag, requestedDedicatedAccountDeatilsFlag);
  	  
  	  	return offers.getDonnees();
	}
        
	public boolean updateOffer(String number,int offerID,Object startDate,Object expiryDate,Integer offerType,String originOperatorID){
		UpdateOffer update=new UpdateOffer(this);
  	  	update.formerRequete(number, offerID,startDate,expiryDate,offerType,originOperatorID);
  	  
  	  	return update.update();
	}
  
	public boolean deleteOffer(String number,int offerID,String originOperatorID) {
		DeleteOffer offer=new DeleteOffer(this);
  	  	offer.formerRequete(number, offerID,originOperatorID);

  	  	return offer.delete();
	}

	public HashSet<UsageCounterUsageThresholdInformation> getUsageThresholdsAndCounters(String number,String originOperatorID){
		GetUsageThresholdsAndCounters counters=new GetUsageThresholdsAndCounters(this);
  	  	counters.formerRequete(number,originOperatorID);
  	  	
  	  	return counters.getValues();
	}

	public boolean updateUsageThresholdsAndCounters(String number,HashSet<UsageCounterUsageThresholdInformation> usageCounterUpdateInformation,HashSet<UsageThreshold>usageThresholdUpdateInformation,String originOperatorID){
		UpdateUsageThresholdsAndCounters update=new UpdateUsageThresholdsAndCounters(this);
  	  	update.formerRequete(number,usageCounterUpdateInformation,usageThresholdUpdateInformation,originOperatorID);
  	  	
  	  	return update.update();
	}
  
	public boolean updateFaFList(String number,String fafAction,FaFList fafInformation,String originOperatorID){
		UpdateFaFList update=new UpdateFaFList(this);
  	  	update.formerRequete(number, fafAction, fafInformation,originOperatorID);

  	  	return update.update();
	}
      
	public boolean deleteAccumulators(String number,Integer serviceClassCurrent ,HashSet<AccumulatorInformation> accumulatorIdentifier,String originOperatorID){
		DeleteAccumulators delete=new DeleteAccumulators(this);
  	  	delete.formerRequete(number, serviceClassCurrent, accumulatorIdentifier,originOperatorID);

  	  	return delete.delete();
	}

	public boolean deleteDedicatedAccounts(String number,Integer serviceClassCurrent ,HashSet<DedicatedAccount> dedicatedAccountIdentifier,String originOperatorID){
		DeleteDedicatedAccounts delete=new DeleteDedicatedAccounts(this);
  	  	delete.formerRequete(number, serviceClassCurrent, dedicatedAccountIdentifier,originOperatorID);

  	  	return delete.delete();
	}

	public boolean deleteUsageThresholds(String number,HashSet<UsageThreshold> usageThresholds,String originOperatorID){
		DeleteUsageThresholds delete=new DeleteUsageThresholds(this);
  	  	delete.formerRequete(number, usageThresholds,originOperatorID);

  	  	return delete.delete();
	}

	public void getPromotionCounters(String number){
		GetPromotionCounters promotionCounters = new GetPromotionCounters(this);
        promotionCounters.formerRequete(number);
        promotionCounters.getDonnees();
	}

	public HashSet<PromotionPlanInformation> getPromotionPlans(String number,String originOperatorID){
		GetPromotionPlans promotionPlans=new GetPromotionPlans(this);
        promotionPlans.formerRequete(number,originOperatorID);
        return promotionPlans.getDonnees();
	}

	public boolean updateAccumulators(String number,HashSet<AccumulatorInformation> accumulatorUpdateInformation,String originOperatorID){
		UpdateAccumulators update=new UpdateAccumulators(this);
  	  	update.formerRequete(number, accumulatorUpdateInformation,originOperatorID);

  	  	return update.update();
	}

	public boolean updatePromotionPlan(String number,String promotionPlanAction,PromotionPlanInformation promotionPlanNew,PromotionPlanInformation promotionPlanOld,String originOperatorID){
		UpdatePromotionPlan update=new UpdatePromotionPlan(this);
  	  	update.formerRequete(number, promotionPlanAction, promotionPlanNew,promotionPlanOld,originOperatorID);

  	  	return update.update();
	}
  
	public boolean installSubscriber (String number,int serviceClassNew,boolean temporaryBlockedFlag,String originOperatorID){
		InstallSubscriber installSubscriber = new InstallSubscriber(this);
  	  	installSubscriber.formerRequete(number, serviceClassNew, temporaryBlockedFlag, originOperatorID);

  	  	return installSubscriber.installSubscriber();
	}

	public boolean linkSubordinateSubscriber (String number,String masterAccountNumber,String originOperatorID){
		LinkSubordinateSubscriber linkSubordinateSubscriber = new LinkSubordinateSubscriber(this);
  	  	linkSubordinateSubscriber.formerRequete(number, masterAccountNumber, originOperatorID);

  	  	return linkSubordinateSubscriber.masterAccountNumber();
	}

	public boolean refill(String number,String transactionAmount,String transactionCurrency,String refillProfileID,String voucherActivationCode,String originOperatorID,String transactionType,String transactionCode){
		Refill refill = new Refill(this);
  	  	refill.formerRequete(number,transactionAmount,transactionCurrency,refillProfileID,voucherActivationCode,originOperatorID,transactionType,transactionCode);

  	  	return  refill.update();
	}

	public boolean deleteSubscriber(String number,String originOperatorID){
		DeleteSubscriber deleteSubscriber=new DeleteSubscriber(this);
  	  	deleteSubscriber.formerRequete(number,originOperatorID);

  	  	return deleteSubscriber.delete();
	}

	public boolean addPAM(String number,String originOperatorID, String pamClassID, String pamServiceID, String pamScheduleID){
		AddPeriodicAccountManagementData addPAM=new AddPeriodicAccountManagementData(this);
  	  	addPAM.formerRequete(number,originOperatorID, pamClassID, pamServiceID, pamScheduleID);

  	  	return addPAM.add();
	}

	public boolean deletePAM(String number,String originOperatorID, int pamClassID, int pamServiceID, int pamScheduleID){
		DeletePeriodicAccountManagementData deletePam=new DeletePeriodicAccountManagementData(this);
  	  	deletePam.formerRequete(number,originOperatorID, pamClassID, pamServiceID, pamScheduleID);

  	  	return deletePam.delete();
	}

	public boolean runPAM(String number,String originOperatorID, int pamServiceID){
		RunPeriodicAccountManagement runPam=new RunPeriodicAccountManagement(this);
  	  	runPam.formerRequete(number,originOperatorID, pamServiceID);

  	  	return runPam.runPAM();
	}
}
