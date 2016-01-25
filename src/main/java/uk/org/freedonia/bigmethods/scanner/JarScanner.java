package uk.org.freedonia.bigmethods.scanner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import uk.org.freedonia.bigmethods.results.IScanResults;

public class JarScanner {
	
	private static List<Path> tmpFiles = Collections.synchronizedList( new ArrayList<Path>() );
	private boolean isInsideZip;
	private IScanResults scanResults;
	private int minSize;
	
	public JarScanner( boolean isInsideZip, IScanResults scanResults, int minSize ) {
		this.isInsideZip = isInsideZip;
		this.scanResults = scanResults;
		this.minSize = minSize;
	}


	public static List<Path> getTmpFiles() {
		return tmpFiles;
	}
	


	public void scan( Path path ) throws IOException {
		Files.walkFileTree( path, new ClassFileWalker( isInsideZip, scanResults, minSize ) );
	}
	
	public List<String> getValidZipExtensions() {
		return Arrays.asList( "zip","jar","ear", "war" );
	}
	
	
	
	
}
