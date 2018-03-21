package acip;

import java.util.Date;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;

import util.DateTime_iso8601;
import util.UsageThreshold;
import connexions.AIRRequest;

public class DeleteUsageThresholds {
	private StringBuffer requete;
    private AIRRequest air;
    
    public DeleteUsageThresholds(AIRRequest air){
        this.air=air;
    }
    
    public void formerRequete(String number,HashSet<UsageThreshold> usageThresholds,String originOperatorID){
    	StringBuffer usageThresholdIDs=new StringBuffer("");
    	for(UsageThreshold threshold:usageThresholds){
    		usageThresholdIDs.append("<value><struct><member><name>usageThresholdID</name><value><int>");
    		usageThresholdIDs.append(threshold.getUsageThresholdID());
    		usageThresholdIDs.append("</int></value></member></struct></value>");
    	}
    	usageThresholdIDs.append("</data></array></value></member>");
    	StringBuffer entete=new StringBuffer("<member><name>usageThresholds</name><value><array><data>");
    	usageThresholdIDs=entete.append(usageThresholdIDs);
    	
    	requete=new StringBuffer("<?xml version=\"1.0\"?><methodCall><methodName>DeleteUsageThresholds</methodName><params><param><value><struct><member><name>originNodeType</name><value><string>EXT</string></value></member><member><name>originHostName</name><value><string>BJDTSRVAPP001</string></value></member><member><name>originTransactionID</name><value><string>");
    	requete.append(number);
    	requete.append("</string></value></member><member><name>originTimeStamp</name><value><dateTime.iso8601>");
    	requete.append((new DateTime_iso8601()).format(new Date(),true));
    	requete.append("</dateTime.iso8601></value></member><member><name>subscriberNumberNAI</name><value><int>1</int></value></member>");
    	requete.append("<member><name>subscriberNumber</name><value><string>");
    	requete.append(number);
    	requete.append("</string></value></member>");
    	if(originOperatorID!=null){
        	requete.append("<member><name>originOperatorID</name><value><string>");
        	requete.append(originOperatorID);
        	requete.append("</string></value></member>");
        }
    	
    	requete.append(usageThresholdIDs);
    	
    }
    
    public boolean delete(){
        try{
        	requete.append("</struct></value></param></params></methodCall>");
        	String reponse=air.executerRequetes(requete.toString());
            Scanner sortie= new Scanner(reponse);
                while(true){
                    String ligne=sortie.nextLine();
                    if(ligne==null) {
                        break;
                    }
                    else if(ligne.equals("<name>responseCode</name>")){
                        String code_reponse=sortie.nextLine();
                        int last=code_reponse.indexOf("</i4></value>");
                        return (Integer.parseInt(code_reponse.substring(11, last))==0);
                    }
                }}
        catch(NoSuchElementException ex){
        }
    	return false;
    }

	public StringBuffer getRequete() {
		return requete;
	}

	public void setRequete(StringBuffer requete) {
		this.requete = requete;
	}

	public AIRRequest getAir() {
		return air;
	}

	public void setAir(AIRRequest air) {
		this.air = air;
	}
    
}
