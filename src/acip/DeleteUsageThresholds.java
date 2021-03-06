package acip;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;

import util.DateTime_iso8601;
import util.UsageThreshold;
import connexions.AIRConnector;

public class DeleteUsageThresholds {

    public StringBuffer formerRequete(String msisdn,HashSet<UsageThreshold> usageThresholds,String originOperatorID){
    	StringBuffer usageThresholdIDs=new StringBuffer("");
    	for(UsageThreshold threshold:usageThresholds){
    		usageThresholdIDs.append("<value><struct><member><name>usageThresholdID</name><value><int>");
    		usageThresholdIDs.append(threshold.getUsageThresholdID());
    		usageThresholdIDs.append("</int></value></member></struct></value>");
    	}
    	usageThresholdIDs.append("</data></array></value></member>");
    	StringBuffer entete=new StringBuffer("<member><name>usageThresholds</name><value><array><data>");
    	usageThresholdIDs=entete.append(usageThresholdIDs);
    	
    	StringBuffer requete =new StringBuffer("<?xml version=\"1.0\"?><methodCall><methodName>DeleteUsageThresholds</methodName><params><param><value><struct><member><name>originNodeType</name><value><string>EXT</string></value></member><member><name>originHostName</name><value><string>SRVPSAPP03mtnlocal</string></value></member><member><name>originTransactionID</name><value><string>");
    	requete.append((new SimpleDateFormat("yyMMddHHmmssS")).format(new Date()));
    	requete.append("</string></value></member><member><name>originTimeStamp</name><value><dateTime.iso8601>");
    	requete.append((new DateTime_iso8601()).format(new Date()));
    	requete.append("</dateTime.iso8601></value></member><member><name>subscriberNumberNAI</name><value><int>1</int></value></member>");
    	requete.append("<member><name>subscriberNumber</name><value><string>");
    	requete.append(msisdn);
    	requete.append("</string></value></member>");
    	if(originOperatorID!=null){
        	requete.append("<member><name>originOperatorID</name><value><string>");
        	requete.append(originOperatorID);
        	requete.append("</string></value></member>");
        }
    	
    	requete.append(usageThresholdIDs);
    	
    	return requete;
    }
    
    public boolean delete(AIRConnector air, String msisdn,HashSet<UsageThreshold> usageThresholds,String originOperatorID){
    	boolean responseCode = false;
    	
    	try{
        	if(air.isOpen()) {
            	StringBuffer requete = formerRequete(msisdn, usageThresholds,originOperatorID);
                    	requete.append("</struct></value></param></params></methodCall>");
                    	String reponse=air.execute(requete.toString());
                        @SuppressWarnings("resource")
						Scanner sortie= new Scanner(reponse);
                            while(true){
                                String ligne=sortie.nextLine();
                                if(ligne==null) {
                                    break;
                                }
                                else if(ligne.equals("<name>responseCode</name>")){
                                    String code_reponse=sortie.nextLine();
                                    int last=code_reponse.indexOf("</i4></value>");
                                    responseCode = Integer.parseInt(code_reponse.substring(11, last))==0;
                                    
                                    break;
                                }
                            }        		
        	}
}
        catch(NoSuchElementException ex){
        	
        } finally {
           	air.fermer();

        }
        
    	return responseCode;
    }
    
}
