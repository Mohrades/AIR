package util;

import java.util.HashSet;

public class ServiceOfferings {
HashSet<Integer> serviceOfferings =new HashSet<Integer>();
public ServiceOfferings(){
}
public void ajouter(Integer serviceOfferingID){
	serviceOfferings.add(serviceOfferingID);
}
public HashSet<Integer> getServiceOfferings() {
	return serviceOfferings;
}
public void setServiceOfferings(HashSet<Integer> serviceOfferings) {
	this.serviceOfferings = serviceOfferings;
}

}
