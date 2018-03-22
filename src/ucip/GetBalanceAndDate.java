package ucip;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;

import connexions.AIRConnector;
import util.BalanceAndDate;
import util.DateTime_iso8601;
import util.DedicatedAccount;

public class GetBalanceAndDate {
            
public StringBuffer formerRequete(String msisdn, int dedicatedAccountID){

    StringBuffer requete = new StringBuffer("<?xml version=\"1.0\"?><methodCall><methodName>GetBalanceAndDate</methodName><params><param><value><struct><member><name>originNodeType</name><value><string>EXT</string></value></member><member><name>originHostName</name><value><string>BJDTSRVAPP001</string></value></member><member><name>originTransactionID</name><value><string>");
	requete.append(msisdn);
	requete.append("</string></value></member><member><name>originTimeStamp</name><value><dateTime.iso8601>");
	requete.append((new DateTime_iso8601()).format(new Date(),true));
	requete.append("</dateTime.iso8601></value></member><member><name>subscriberNumberNAI</name><value><int>1</int></value></member>");
	requete.append("<member><name>subscriberNumber</name><value><string>");
	requete.append(msisdn);
	requete.append("</string></value></member>");
	
	requete.append("<member><name>dedicatedAccountSelection</name><value><array><data><value><struct><member><name>dedicatedAccountIDFirst</name><value><int>");
	if(dedicatedAccountID<1)requete.append(1);
	else requete.append(dedicatedAccountID);
	requete.append("</int></value></member></struct></value></data></array></value></member>");
       
	return requete;
}

    public BalanceAndDate getValues(AIRConnector air, String msisdn, int dedicatedAccountID){
    	BalanceAndDate balance = null;
    	
    	try{
    	   if(air.isOpen()){
    		   StringBuffer requete = formerRequete(msisdn, dedicatedAccountID);
    	       requete.append("</struct></value></param></params></methodCall>");   
    	       String reponse=air.execute(requete.toString());
    	       Scanner sortie= new Scanner(reponse);

    	       if(dedicatedAccountID<1){
    	    	   BalanceAndDate balanceAndDate=new BalanceAndDate();
    	           balanceAndDate.setRelative(false);
    	    	   balanceAndDate.setAccountID(0);
    	            while(true){
    	                String ligne=sortie.nextLine();
    	                if(ligne==null) {
    	                	sortie.close();
    	                    break;
    	                }
    	                else{
    	                if(ligne.equals("<name>accountValue1</name>")){
    	                    String price=sortie.nextLine();
    	                    int last=price.indexOf("</string></value>");
    	                    balanceAndDate.setAccountValue(Long.parseLong(price.substring(15, last)));
    	                }
    	                else if(ligne.equals("<name>supervisionExpiryDate</name>")){
    	                    String date=sortie.nextLine();
    	                    int last=date.indexOf("</dateTime.iso8601></value>");
    	                    date=date.substring(25, last);
    	                    balanceAndDate.setExpiryDate((new DateTime_iso8601()).parse(date));
    	                    
    	                    sortie.close();
    	                    balance = balanceAndDate;
    	                }
    	            }
    	            }
    	       }
    	       else{
    	    	   long val = 0;
    	            while(true){
    	                String ligne=sortie.nextLine();
    	                if(ligne==null) {
    	                	sortie.close();
    	                    break;
    	                }
    	                else{
    	                if(ligne.equals("<name>dedicatedAccountValue1</name>")){
    	                    String price=sortie.nextLine();
    	                    int last=price.indexOf("</string></value>");
    	                    val=Long.parseLong(price.substring(15, last));
    	                }
    	                else if(ligne.equals("<name>expiryDate</name>")){
    	                    String date=sortie.nextLine();
    	                    int last=date.indexOf("</dateTime.iso8601></value>");
    	                    date=date.substring(25, last);
    	                    DedicatedAccount DA= new DedicatedAccount(dedicatedAccountID,val,(new DateTime_iso8601()).parse(date));
    	                    DA.setRelative(false);
    	                    
    	                    sortie.close();
    	                    balance = DA;
    	                }
    	            }
    	            } 
    	         }
    	       }

       } catch(NoSuchElementException e){
    	   
       } finally {
       	air.fermer();

       }

       return balance;
}

}
