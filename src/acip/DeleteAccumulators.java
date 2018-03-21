package acip;

import java.util.Date;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;

import util.AccumulatorInformation;
import util.DateTime_iso8601;
import connexions.AIRRequest;

public class DeleteAccumulators {
	private StringBuffer requete;
    private AIRRequest air;
    
    public DeleteAccumulators(AIRRequest air){
        this.air=air;
    }
    
    public void formerRequete(String number,Integer serviceClassCurrent ,HashSet<AccumulatorInformation> accumulatorIdentifier,String originOperatorID){
    	StringBuffer serviceClass=new StringBuffer("");
    	StringBuffer accumulatorIDs=new StringBuffer("");
    	if(serviceClassCurrent!=null){
    		serviceClass.append("<member><name>serviceClassCurrent</name><value><i4>");
    		serviceClass.append(serviceClassCurrent);
    		serviceClass.append("</i4></value></member>");
    	}
    	for (AccumulatorInformation accumulator:accumulatorIdentifier){
    		accumulatorIDs.append("<value><struct><member><name>accumulatorID</name><value><int>");
    		accumulatorIDs.append(accumulator.getAccumulatorID());
    		accumulatorIDs.append("</int></value></member>");
    		if(accumulator.getAccumulatorEndDate()!=null){
    			accumulatorIDs.append("<member><name>accumulatorEndDate</name><value><dateTime.iso8601>");
    			accumulatorIDs.append((new DateTime_iso8601()).format(accumulator.getAccumulatorEndDate(),false));
    			accumulatorIDs.append("</dateTime.iso8601></value></member>");
    		}
    		accumulatorIDs.append("</struct></value>");
    	}
    	accumulatorIDs.append("</data></array></value></member>");
    	StringBuffer entete=new StringBuffer("<member><name>accumulatorIdentifier</name><value><array><data>");
    	accumulatorIDs=entete.append(accumulatorIDs);
    	
    	
    	requete=new StringBuffer("<?xml version=\"1.0\"?><methodCall><methodName>DeleteAccumulators</methodName><params><param><value><struct><member><name>originNodeType</name><value><string>EXT</string></value></member><member><name>originHostName</name><value><string>BJDTSRVAPP001</string></value></member><member><name>originTransactionID</name><value><string>");
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
    	requete.append(accumulatorIDs);
    	
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
