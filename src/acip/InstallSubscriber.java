/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package acip;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;

import connexions.AIRRequest;
import util.DateTime_iso8601;

/**
 *
 * @author TestAppli
 */
public class InstallSubscriber {
    private StringBuffer requete;
    private AIRRequest air;
            
            /**
     *
     * @param air
     */
    public InstallSubscriber(AIRRequest air){
         this.air=air;
    }
    
    public void formerRequete(String number,int serviceClassNew,boolean temporaryBlockedFlag,String originOperatorID){ 

        requete=new StringBuffer("<?xml version=\"1.0\"?><methodCall><methodName>InstallSubscriber</methodName><params><param><value><struct><member><name>originNodeType</name><value><string>EXT</string></value></member><member><name>originHostName</name><value><string>BJDTSRVAPP001</string></value></member><member><name>originTransactionID</name><value><string>");
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
    	requete.append("<member><name>serviceClassNew</name><value><i4>");
    	requete.append(serviceClassNew);
    	requete.append("</i4></value></member>");
    	
    	if(temporaryBlockedFlag) requete.append("<member><name>temporaryBlockedFlag</name><value><boolean>1</boolean></value></member>");
    	else requete.append("<member><name>temporaryBlockedFlag</name><value><boolean>0</boolean></value></member>");
        					
    }
    
    public boolean installSubscriber(){
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
                    int respCode=Integer.parseInt(code_reponse.substring(11, last));
                    return respCode==0 || respCode==1 || respCode==2;
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