/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ucip;

import java.util.Date;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;

import connexions.AIRRequest;
import util.DateTime_iso8601;
import util.ServiceOfferings;

/**
 *
 * @author TestAppli
 */
public class UpdateSubscriberSegmentation {
     private StringBuffer requete;
     private AIRRequest air;
    
    public UpdateSubscriberSegmentation(AIRRequest air){
        this.air=air;
    }
    public void formerRequete(String number,Integer accountGroupID,ServiceOfferings serviceOfferings,String originOperatorID){
    	StringBuffer groupID=new StringBuffer("");
        if(accountGroupID==null);
        else{
            groupID.append("<member><name>accountGroupID</name><value><i4>");
            groupID.append(accountGroupID);
            groupID.append("</i4></value></member>");
        }
        HashSet<Integer> offerings=serviceOfferings.getServiceOfferings();
        
        StringBuffer serviceOffering=new StringBuffer("<member><name>serviceOfferings</name><value><array><data>");
        for (Integer id:offerings){
        	int value = 0;
        	if(id<0){
        		id=-id;
        	}
        	else value=1;
            serviceOffering.append("<value><struct><member><name>serviceOfferingActiveFlag</name><value><boolean>");
            serviceOffering.append(value);
            serviceOffering.append("</boolean></value></member><member><name>serviceOfferingID</name><value><i4>");
            serviceOffering.append(id);
            serviceOffering.append("</i4></value></member></struct></value>");
        }
        serviceOffering.append("</data></array></value></member>"); 
        
        requete=new StringBuffer("<?xml version=\"1.0\"?><methodCall><methodName>UpdateSubscriberSegmentation</methodName><params><param><value><struct><member><name>originNodeType</name><value><string>EXT</string></value></member><member><name>originHostName</name><value><string>BJDTSRVAPP001</string></value></member><member><name>originTransactionID</name><value><string>");
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
    	
    	requete.append(groupID);
    	requete.append(serviceOffering);											
}
    
    public boolean update(){
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
