package acip;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;

import connexions.AIRConnector;
import util.DateTime_iso8601;

public class DeletePeriodicAccountManagementData {

	public StringBuffer formerRequete(String msisdn,String originOperatorID, int pamClassID, int pamServiceID, int pamScheduleID){
        
		StringBuffer requete=new StringBuffer("<?xml version=\"1.0\"?><methodCall><methodName>DeletePeriodicAccountManagementData</methodName><params><param><value><struct><member><name>originHostName</name><value><string>SRVPSAPP03mtnlocal</string></value></member><member><name>originNodeType</name><value><string>EXT</string></value></member>");
		if(originOperatorID!=null){
		        	requete.append("<member><name>originOperatorID</name><value><string>");
		        	requete.append(originOperatorID);
		        	requete.append("</string></value></member>");
		        }

		requete.append("<member><name>originTimeStamp</name><value><dateTime.iso8601>");
		requete.append((new DateTime_iso8601()).format(new Date(),true));
		requete.append("</dateTime.iso8601></value></member><member><name>originTransactionID</name><value><string>");
		requete.append((new SimpleDateFormat("yyMMddHHmmssS")).format(new Date()));
		requete.append("</string></value></member><member><name>pamInformationList</name><value><array><data><value><struct><member><name>pamClassID</name><value><i4>");
		requete.append(pamClassID);
		requete.append("</i4></value></member><member><name>pamServiceID</name><value><i4>");
		requete.append(pamServiceID);
		requete.append("</i4></value></member><member><name>scheduleID</name><value><i4>");
		requete.append(pamScheduleID);
		requete.append("</i4></value></member></struct></value></data></array></value></member><member><name>subscriberNumber</name><value><string>");
		requete.append(msisdn);
		requete.append("</string></value></member><member><name>subscriberNumberNAI</name><value><i4>1</i4></value></member></struct></value></param></params></methodCall>");
		
		return requete;
	} 

public boolean delete(AIRConnector air, String msisdn,String originOperatorID, int pamClassID, int pamServiceID, int pamScheduleID){
	boolean responseCode = false;
	
	try{
	if(air.isOpen()) {
		StringBuffer requete = formerRequete(msisdn,originOperatorID, pamClassID, pamServiceID, pamScheduleID);
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
	                responseCode = Integer.parseInt(code_reponse.substring(11, last)) == 0;
	                
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
