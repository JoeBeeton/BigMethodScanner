package uk.org.freedonia.bigmethods.scanner;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Path;

import uk.org.freedonia.bigmethods.results.IScanResults;

public class RunnableJarScanner implements Runnable {
	
	private Path path;
	private boolean isInsideZip;
	private IScanResults scanResults;
	private FileSystem fs;

	public RunnableJarScanner( Path path, boolean isInsideZip, IScanResults scanResults, FileSystem fs ) {
		this.path = path;
		this.isInsideZip = isInsideZip;
		this.scanResults = scanResults;
		this.fs = fs;
	}

	@Override
	public void run() {
		JarScanner scanner = new JarScanner( isInsideZip, scanResults );
		try {
			scanner.scan(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if ( fs != null ) {
			try {
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
