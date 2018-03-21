package acip;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;

import connexions.AIRRequest;
import util.DateTime_iso8601;

public class RunPeriodicAccountManagement {
	
	private StringBuffer requete;
    private AIRRequest air;
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
	public RunPeriodicAccountManagement(AIRRequest air) {
		super();
		this.air = air;
	}
    

	public void formerRequete(String number,String originOperatorID, int pamServiceID){
        
		requete=new StringBuffer("<?xml version=\"1.0\"?><methodCall><methodName>RunPeriodicAccountManagement</methodName><params><param><value><struct><member><name>originHostName</name><value><string>BJDTSRVAPP001</string></value></member><member><name>originNodeType</name><value><string>EXT</string></value></member>");
		if(originOperatorID!=null){
					requete.append("<member><name>originOperatorID</name><value><string>");
					requete.append(originOperatorID);
					requete.append("</string></value></member>");
				}
		requete.append("<member><name>originTimeStamp</name><value><dateTime.iso8601>");
		requete.append((new DateTime_iso8601()).format(new Date(),true));
		requete.append("</dateTime.iso8601></value></member><member><name>originTransactionID</name><value><string>");
		requete.append(number);
		requete.append("</string></value></member><member><name>pamServiceID</name><value><i4>");
		requete.append(pamServiceID);
		requete.append("</i4></value></member><member><name>subscriberNumber</name><value><string>");
		requete.append(number);
		requete.append("</string></value></member><member><name>subscriberNumberNAI</name><value><i4>1</i4></value></member></struct></value></param></params></methodCall>");

} 

public boolean runPAM(){
try{
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

    

}
