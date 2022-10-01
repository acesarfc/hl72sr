package org.txt2dcmsr;

import java.util.ArrayList;

public class dataSequence {
	dataElement tag;
	ArrayList<dataElement> seq;
	
	public dataElement getTag() {
		return tag;
	}
	public void setTag(dataElement tag) {
		this.tag = tag;
	}
	public ArrayList<dataElement> getSeq() {
		return seq;
	}
	public void setSeq(ArrayList<dataElement> seq) {
		this.seq = seq;
	}
	
	
	
}
