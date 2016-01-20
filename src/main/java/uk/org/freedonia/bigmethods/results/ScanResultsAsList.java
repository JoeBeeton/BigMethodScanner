package uk.org.freedonia.bigmethods.results;

import java.util.ArrayList;
import java.util.List;

public class ScanResultsAsList implements IScanResults {
	
	private List<String> largeMethodLists;
	
	public ScanResultsAsList() {
		largeMethodLists = new ArrayList<String>();
	}

	public List<String> getLargeMethodLists() {
		return largeMethodLists;
	}

	@Override
	public synchronized void submitLargeMethod( String method ) {
		largeMethodLists.add(method);	
	}


	

}
