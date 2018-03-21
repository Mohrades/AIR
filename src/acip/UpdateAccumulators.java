package acip;

import java.util.Date;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;

import util.AccumulatorInformation;
import util.DateTime_iso8601;
import connexions.AIRRequest;

public class UpdateAccumulators {
	private StringBuffer requete;
    private AIRRequest air;
    
    public UpdateAccumulators(AIRRequest air){
        this.air=air;
    }
    
    public void formerRequete(String number,HashSet<AccumulatorInformation> accumulatorUpdateInformation,String originOperatorID){
    	StringBuffer accumulatorInformation=new StringBuffer("<member><name>accumulatorUpdateInformation</name><value><array><data>");
    	for (AccumulatorInformation accumulator:accumulatorUpdateInformation){
    		accumulatorInformation.append("<value><struct><member><name>accumulatorID</name><value><i4>");
    		accumulatorInformation.append(accumulator.getAccumulatorID());
    		accumulatorInformation.append("</i4></value></member>");
    		if(accumulator.getAccumulatorStartDate()!=null){
    			accumulatorInformation.append("<member><name>accumulatorStartDate</name><value><dateTime.iso8601>");
    			accumulatorInformation.append((new DateTime_iso8601()).format(accumulator.getAccumulatorStartDate(),false));
    			accumulatorInformation.append("</dateTime.iso8601></value></member>");
    		}
    		if(accumulator.isAccumulatorValueRelative()){
    			accumulatorInformation.append("<member><name>accumulatorValueRelative</name><value><i4>");
    			accumulatorInformation.append(accumulator.getAccumulatorValue());
    			accumulatorInformation.append("</i4></value></member>");
    		}
    		else{
    			accumulatorInformation.append("<member><name>accumulatorValueAbsolute</name><value><i4>");
    			accumulatorInformation.append(accumulator.getAccumulatorValue());
    			accumulatorInformation.append("</i4></value></member>");
    		}  					
    		accumulatorInformation.append("</struct></value>");
}
    	accumulatorInformation.append("</data></array></value></member>");
    	
    	requete=new StringBuffer("<?xml version=\"1.0\"?><methodCall><methodName>UpdateAccumulators</methodName><params><param><value><struct><member><name>originNodeType</name><value><string>EXT</string></value></member><member><name>originHostName</name><value><string>BJDTSRVAPP001</string></value></member><member><name>originTransactionID</name><value><string>");
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
    	
    	requete.append(accumulatorInformation);
    	
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
