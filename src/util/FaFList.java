package util;

import java.util.Date;
import java.util.HashSet;

public class FaFList {
	private Date fafChangeUnbarDate;
    private boolean fafMaxAllowedNumbersReachedFlag;
    private HashSet<FafInformation>fafInformationList = new HashSet<FafInformation>();
    
	public Date getFafChangeUnbarDate() {
		return fafChangeUnbarDate;
	}
	
	public HashSet<FafInformation> getFafInformationList() {
		return fafInformationList;
	}

	public void setFafInformationList(HashSet<FafInformation> fafInformationList) {
		this.fafInformationList = fafInformationList;
	}

	public void setFafChangeUnbarDate(Date fafChangeUnbarDate) {
		this.fafChangeUnbarDate = fafChangeUnbarDate;
	}
	public boolean isFafMaxAllowedNumbersReachedFlag() {
		return fafMaxAllowedNumbersReachedFlag;
	}
	public void setFafMaxAllowedNumbersReachedFlag(boolean fafMaxAllowedNumbersReachedFlag) {
		this.fafMaxAllowedNumbersReachedFlag = fafMaxAllowedNumbersReachedFlag;
	}
	public FaFList(HashSet<FafInformation> fafInformationList){
		this.fafInformationList=fafInformationList;
	}
}
