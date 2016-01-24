package uk.org.freedonia.bigmethods.cmd;

import java.nio.file.Path;
import java.util.concurrent.ExecutionException;

import uk.org.freedonia.bigmethods.results.PrintScanResults;
import uk.org.freedonia.bigmethods.scanner.ProjectScanner;

public class CMDLineProjectScanner {
	
	public static void main( String[] args ) throws InterruptedException, ExecutionException {
		Path path;
		try {
			path = getPathFromArgs( args );
			ProjectScanner scanner = new ProjectScanner();
			scanner.scanPath( path, new PrintScanResults(), getByteCodeThresholdSize( args )  );
		} catch (ArgException e) {
			System.out.println(e.getMessage());
			printHelpMsg();
		}
	}

	private static void printHelpMsg() {
		System.out.println(" Usage : -P /path/to/dir/or/jar ");
		System.out.println(" Optional : -b 5000 ( byte code size threshold size ) ");		
	}

	private static Path getPathFromArgs(String[] args) throws ArgException {
		CMDLineArgsReader reader = new CMDLineArgsReader( args );
		return reader.getPath();
	}
	
	private static int getByteCodeThresholdSize( String[] args ) throws ArgException {
		CMDLineArgsReader reader = new CMDLineArgsReader( args );
		return reader.getThresholdByteCodeSize();
	}

}
