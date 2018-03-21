package acip;

import java.util.Date;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;

import util.DateTime_iso8601;
import util.DedicatedAccount;
import connexions.AIRRequest;

public class DeleteDedicatedAccounts {
	private StringBuffer requete;
    private AIRRequest air;
    
    public DeleteDedicatedAccounts(AIRRequest air){
        this.air=air;
    }
    
    public void formerRequete(String number,Integer serviceClassCurrent ,HashSet<DedicatedAccount> dedicatedAccountIdentifier,String originOperatorID){
    	StringBuffer serviceClass=new StringBuffer("");
    	StringBuffer dedicatedAccountIDs=new StringBuffer("");
    	if(serviceClassCurrent!=null){
    		serviceClass.append("<member><name>serviceClassCurrent</name><value><i4>");
    		serviceClass.append(serviceClassCurrent);
    		serviceClass.append("</i4></value></member>");
    	}
    	for (DedicatedAccount DA:dedicatedAccountIdentifier){
    		dedicatedAccountIDs.append("<value><struct><member><name>dedicatedAccountID</name><value><int>");
    		dedicatedAccountIDs.append(DA.getAccountID());
    		dedicatedAccountIDs.append("</int></value></member>");
    		if(DA.getExpiryDate()!=null){
    			dedicatedAccountIDs.append("<member><name>expiryDate</name><value><dateTime.iso8601>");
    			dedicatedAccountIDs.append((new DateTime_iso8601()).format((Date)DA.getExpiryDate(),false));
    			dedicatedAccountIDs.append("</dateTime.iso8601></value></member>");
    		}
    		dedicatedAccountIDs.append("</struct></value>");
    	}
    	StringBuffer entete=new StringBuffer("<member><name>dedicatedAccountIdentifier</name><value><array><data>");
    	dedicatedAccountIDs.append("</data></array></value></member>");
    	dedicatedAccountIDs=entete.append(dedicatedAccountIDs);
    	
    	requete=new StringBuffer("<?xml version=\"1.0\"?><methodCall><methodName>DeleteDedicatedAccounts</methodName><params><param><value><struct><member><name>originNodeType</name><value><string>EXT</string></value></member><member><name>originHostName</name><value><string>BJDTSRVAPP001</string></value></member><member><name>originTransactionID</name><value><string>");
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
    	
    	requete.append(serviceClass);
    	requete.append(dedicatedAccountIDs);	
        			
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
