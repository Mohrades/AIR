package util;

import java.util.HashSet;

public class PamInformationList {

	private HashSet<PamUpdateInformation> list;

	public PamInformationList() {
		list = new HashSet<PamUpdateInformation>();
	}
	
	public PamInformationList(HashSet<PamUpdateInformation> list) {
		this.list = list;
	}
	
	public void add(PamUpdateInformation pamUpdateInformation) {
		if(list != null) list.add(pamUpdateInformation);
	}

	public HashSet<PamUpdateInformation> getList() {
		return list;
	}

	public void setList(HashSet<PamUpdateInformation> list) {
		this.list = list;
	}

}
