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
	
	public JarScanner( boolean isInsideZip, IScanResults scanResults ) {
		this.isInsideZip = isInsideZip;
		this.scanResults = scanResults;
	}


	public static List<Path> getTmpFiles() {
		return tmpFiles;
	}
	


	public void scan( Path path ) throws IOException {
		Files.walkFileTree( path, new ClassFileWalker( isInsideZip, scanResults ) );
	}
	
	public List<String> getValidZipExtensions() {
		return Arrays.asList( "zip","jar","ear" );
	}
	
	
	
	
}
