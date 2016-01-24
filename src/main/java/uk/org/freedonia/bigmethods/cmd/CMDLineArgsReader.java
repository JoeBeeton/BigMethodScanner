package uk.org.freedonia.bigmethods.cmd;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CMDLineArgsReader {
	
	public static final int DEFAULT_THRESHOLD_SIZE = 8000;

	private String[] args;

	public CMDLineArgsReader( String[] args )  {
		this.args = args;
	}
	
	
	public Path getPath() throws ArgException {
		for ( int i = 0; i < args.length; i++ ) {
			String arg = args[i];
			if ( arg != null && arg.trim().toLowerCase().equals("-p") && i < args.length ) {
				return Paths.get(args[i+1]);
			}
		}
		throw new ArgException("No path was specified.");
	}
	
	public int getThresholdByteCodeSize() throws ArgException {
		int byteCodeSize = DEFAULT_THRESHOLD_SIZE;
		for ( int i = 0; i < args.length; i++ ) {
			String arg = args[i];
			if ( arg != null && arg.trim().toLowerCase().equals("-b") && i < args.length ) {
				try {
					byteCodeSize =  Integer.parseInt( args[i+1] );
					break;
				} catch ( NumberFormatException e ) {
					throw new ArgException( "\""+args[i+1]+"\""+" is not a valid number" );
				}
				
			}
		}
		return byteCodeSize;
	}
	
}
