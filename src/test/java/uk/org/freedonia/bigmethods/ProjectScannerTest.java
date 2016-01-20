
package uk.org.freedonia.bigmethods;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import org.junit.Test;

import uk.org.freedonia.bigmethods.results.ScanResultsAsList;
import uk.org.freedonia.bigmethods.scanner.ProjectScanner;

public class ProjectScannerTest {
	
	
	@Test
	public void testScanner() throws IOException, InterruptedException, ExecutionException, URISyntaxException {
		Path path = getPathToResources();
		ProjectScanner scanner = new ProjectScanner();
		ScanResultsAsList list = new ScanResultsAsList();
		scanner.scanPath( path, list );
		assertEquals( 2, list.getLargeMethodLists().size() );
		assertTrue( list.getLargeMethodLists().contains(
				"org.apache.commons.compress.compressors.snappy.PureJavaCrc32C.<clinit>() size :  16243") );
		assertTrue( list.getLargeMethodLists().contains(
				"uk.org.freedonia.bigmethods.testdata.TestData.largeMethod() size :  46333") );
	}
	
	private Path getPathToResources() throws URISyntaxException {
		return Paths.get( ProjectScannerTest.class.getResource("/dataset.jar").toURI() ).getParent();
	}

}
