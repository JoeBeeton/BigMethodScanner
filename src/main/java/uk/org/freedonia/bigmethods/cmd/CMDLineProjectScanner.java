package uk.org.freedonia.bigmethods.cmd;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;

import uk.org.freedonia.bigmethods.results.PrintScanResults;
import uk.org.freedonia.bigmethods.scanner.ProjectScanner;

public class CMDLineProjectScanner {
	
	public static void main( String[] args ) throws InterruptedException, ExecutionException {
		Path path = getPathFromArgs( args );
		if ( path == null ) {
			printHelpMsg();
		} else {
			ProjectScanner scanner = new ProjectScanner();
			scanner.scanPath( path, new PrintScanResults()  );
		}
	}

	private static void printHelpMsg() {
		System.out.println(" Usage : -P /path/to/dir/or/jar ");		
	}

	private static Path getPathFromArgs(String[] args) {
		for ( int i = 0; i < args.length; i++ ) {
			String arg = args[i];
			if ( arg != null && arg.trim().toLowerCase().equals("-p") && i < args.length ) {
				return Paths.get(args[i+1]);
			}
		}
		return null;
	}

}
