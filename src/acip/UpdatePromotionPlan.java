package acip;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;

import util.DateTime_iso8601;
import util.PromotionPlanAction;
import util.PromotionPlanInformation;
import connexions.AIRRequest;

public class UpdatePromotionPlan {
	private StringBuffer requete;
    private AIRRequest air;
    
    public UpdatePromotionPlan(AIRRequest air){
        this.air=air;
    }
    
    public void formerRequete(String number,String promotionPlanAction,PromotionPlanInformation promotionPlanNew,PromotionPlanInformation promotionPlanOld,String originOperatorID){
    	StringBuffer modif=new StringBuffer("");
    	if(promotionPlanOld!=null){
    	if(promotionPlanOld.getPromotionStartDate()!=null){
    		modif.append("<member><name>promotionOldStartDate</name><value><dateTime.iso8601>");
    		modif.append((new DateTime_iso8601()).format(promotionPlanOld.getPromotionStartDate(),false));
    		modif.append("</dateTime.iso8601></value></member>");
    	}
    	if(promotionPlanOld.getPromotionEndDate()!=null){
    		modif.append("<member><name>promotionOldEndDate</name><value><dateTime.iso8601>");
    		modif.append((new DateTime_iso8601()).format(promotionPlanOld.getPromotionEndDate(),false));
    		modif.append("</dateTime.iso8601></value></member>");
    	}
    	}
    	if(promotionPlanNew!=null){
    	if(promotionPlanNew.getPromotionStartDate()!=null){
    		modif.append("<member><name>promotionStartDate</name><value><dateTime.iso8601>");
    		modif.append((new DateTime_iso8601()).format(promotionPlanNew.getPromotionStartDate(),false));
    		modif.append("</dateTime.iso8601></value></member>");
    	}
    	if(promotionPlanNew.getPromotionEndDate()!=null){
    		modif.append("<member><name>promotionEndDate</name><value><dateTime.iso8601>");
    		modif.append((new DateTime_iso8601()).format(promotionPlanNew.getPromotionEndDate(),false));
    		modif.append("</dateTime.iso8601></value></member>");
    	}
    	}
    	
    	requete=new StringBuffer("<?xml version=\"1.0\"?><methodCall><methodName>UpdatePromotionPlan</methodName><params><param><value><struct><member><name>originNodeType</name><value><string>EXT</string></value></member><member><name>originHostName</name><value><string>BJDTSRVAPP001</string></value></member><member><name>originTransactionID</name><value><string>");
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
    	
    	requete.append("<member><name>promotionPlanAction</name><value><string>");
    	requete.append(promotionPlanAction);
    	requete.append("</string></value></member>");
    	requete.append(modif);
    	requete.append("<member><name>promotionPlanID</name><value><string>");
    	if(!promotionPlanAction.equals(PromotionPlanAction.DELETE))requete.append(promotionPlanNew.getPromotionPlanID());
    	else requete.append(promotionPlanOld.getPromotionPlanID());
    	requete.append("</string></value></member>");
    	
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
