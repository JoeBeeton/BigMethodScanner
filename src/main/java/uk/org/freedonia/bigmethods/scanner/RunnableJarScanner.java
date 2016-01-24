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
	private int minSize;

	public RunnableJarScanner( Path path, boolean isInsideZip, IScanResults scanResults, FileSystem fs, int minSize ) {
		this.path = path;
		this.isInsideZip = isInsideZip;
		this.scanResults = scanResults;
		this.fs = fs;
		this.minSize = minSize;
	}

	@Override
	public void run() {
		JarScanner scanner = new JarScanner( isInsideZip, scanResults, minSize );
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
