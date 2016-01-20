package uk.org.freedonia.bigmethods.results;

public class PrintScanResults implements IScanResults {

	@Override
	public void submitLargeMethod(String method) {
		System.out.println( "Large Method : " + method );
	}

}
