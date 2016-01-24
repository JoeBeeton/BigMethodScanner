package uk.org.freedonia.bigmethods.cmd;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CMDLineArgsReaderTest {
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	
	@Test
	public void testWithValidThresholdSize() throws ArgException {
		String[] args = new String[]{"-b","15000"};
		CMDLineArgsReader reader = new CMDLineArgsReader( args );
		int result = reader.getThresholdByteCodeSize();
		assertEquals( 15000, result );
	}
	
	@Test
	public void testWithValidPathSize() throws ArgException {
		String tmpPath = System.getProperty("java.io.tmpdir");
		String[] args = new String[]{ "-p", tmpPath };
		CMDLineArgsReader reader = new CMDLineArgsReader( args );
		Path result = reader.getPath();
		assertEquals( Paths.get(tmpPath), result );
	}
	
	@Test
	public void testWithNoPath() throws ArgException {
		expectedEx.expect(ArgException.class);
	    expectedEx.expectMessage("No path was specified.");
		String[] args = new String[]{"-b","15000"};
		CMDLineArgsReader reader = new CMDLineArgsReader( args );
		reader.getPath();
	}
	
	@Test
	public void testWithNoThresholdSize() throws ArgException {
		String[] args = new String[]{ "-p", System.getProperty("java.io.tmpdir") };
		CMDLineArgsReader reader = new CMDLineArgsReader( args );
		int result = reader.getThresholdByteCodeSize();
		assertEquals( CMDLineArgsReader.DEFAULT_THRESHOLD_SIZE, result );
	}
	
	@Test
	public void testWithInvalidThresholdSize() throws ArgException {
		expectedEx.expect( ArgException.class );
	    expectedEx.expectMessage( "\"Cheese\" is not a valid number" );
		String[] args = new String[]{"-b","Cheese"};
		CMDLineArgsReader reader = new CMDLineArgsReader( args );
		reader.getThresholdByteCodeSize();
		
	}
	
	
}
