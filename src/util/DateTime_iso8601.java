/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TestAppli
 */
public class DateTime_iso8601 {
	
    @SuppressWarnings("deprecation")
	public String format(Date date,boolean time){
        // TODO Auto-generated method stub
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	String timestamp=dateFormat.format(date)+"T";
	if(time){
		dateFormat = new SimpleDateFormat("HH:mm:ssZ");
		timestamp+=dateFormat.format(date);
	}
    else{
    	date.setHours(12);
    	date.setMinutes(0);
    	date.setSeconds(0);
    	dateFormat = new SimpleDateFormat("HH:mm:ss");
    	timestamp+=dateFormat.format(date);
    	timestamp+="+0000";
    }
	return timestamp;
    }
    
    public Date parse(String date){
        date=date.replaceFirst("T","");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHH:mm:ssZ");
        try {
            return dateFormat.parse(date);
        } catch (ParseException ex) {
            Logger.getLogger(DateTime_iso8601.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
