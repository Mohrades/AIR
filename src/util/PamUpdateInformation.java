package util;

public class PamUpdateInformation {
	
	private int pamServiceID;
	private int pamClassID;
	private int scheduleID;

	public PamUpdateInformation() {

	}
	
	public PamUpdateInformation(int pamServiceID, int pamClassID, int scheduleID) {
		this.pamServiceID = pamServiceID;
		this.pamClassID = pamClassID;
		this.scheduleID = scheduleID;
	}

	public int getPamServiceID() {
		return pamServiceID;
	}

	public void setPamServiceID(int pamServiceID) {
		this.pamServiceID = pamServiceID;
	}

	public int getPamClassID() {
		return pamClassID;
	}

	public void setPamClassID(int pamClassID) {
		this.pamClassID = pamClassID;
	}

	public int getScheduleID() {
		return scheduleID;
	}

	public void setScheduleID(int scheduleID) {
		this.scheduleID = scheduleID;
	}

}
