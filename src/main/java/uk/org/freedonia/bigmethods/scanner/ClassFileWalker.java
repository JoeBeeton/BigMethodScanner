package uk.org.freedonia.bigmethods.scanner;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;

import uk.org.freedonia.bigmethods.results.IScanResults;

public class ClassFileWalker implements FileVisitor<Path> {

	private boolean isInsideZip;
	private IScanResults scanResults;

	public ClassFileWalker( boolean isInsideZip, IScanResults scanResults ) {
		this.isInsideZip = isInsideZip;
		this.scanResults = scanResults;
	}

	@Override
	public FileVisitResult postVisitDirectory( Path arg0, IOException arg1 ) throws IOException {
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult preVisitDirectory( Path arg0, BasicFileAttributes arg1 ) throws IOException {
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile( Path file, BasicFileAttributes attributes ) throws IOException {
		boolean isZip = isZip( file );
		if ( isZip ) {
			scanZip( file );
		} else if ( isClassFile( file ) ) {
			checkClass( file );
		}
		return FileVisitResult.CONTINUE;
	}
	
	private void checkClass( Path file ) {
		try ( InputStream fis = Files.newInputStream( file ) ) {
			ClassReader cr = new ClassReader(fis);
            ClassVisitor visitor = getClassVisitor();
            cr.accept(visitor, ClassReader.EXPAND_FRAMES);
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
	
	private ClassVisitor getClassVisitor() {
		return new CountingClassVisitor( scanResults );
	}

	private boolean isClassFile( Path file ) {
		boolean isClass = false;
		if ( file.toString().toLowerCase().endsWith( "class" ) ) {
			isClass = true;
		}
		return isClass;
	}
	
	private void scanZip( Path file ) throws IOException {
		if ( isInsideZip ) {
			scanInternalZipFile( file );
		} else {
			scanExternalZipFile( file );
		}
	}
	
	private void scanInternalZipFile( Path file ) throws IOException {
		Path tmpFile = getTmpFile();
		try ( InputStream is = Files.newInputStream( file ) ) {
			Files.copy( is, tmpFile, StandardCopyOption.REPLACE_EXISTING );
		}
		scanExternalZipFile( tmpFile );
	}
	
	private void scanExternalZipFile( Path file ) {
		Map<String, String> env = new HashMap<>(); 
		    env.put("create", "false");
	        URI zipURI = URI.create( String.format( "jar:file:/%s", file.toString().replaceAll("\\\\", "/") ) );
		try {
			FileSystem fs = FileSystems.newFileSystem( zipURI, env );
			RunnableJarScanner scanner = new RunnableJarScanner(  fs.getPath("/"), true, scanResults, fs );
			ProjectScanner.service.submit( scanner );
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private Path getTmpFile() throws IOException {
		Path tmpFile =  Files.createTempFile("tmpjar", "zip" );
		JarScanner.getTmpFiles().add(tmpFile);
		return tmpFile;
	}
	
	private boolean isZip( Path file ) {
		boolean isZip = false;
		String fileString = file.toString().toLowerCase();
		for ( String ext : getValidZipExtensions() ) {
			if ( fileString.endsWith(ext) ) {
				isZip = true;
				break;
			}
		}
		return isZip;
	}
	
	public List<String> getValidZipExtensions() {
		return Arrays.asList( "zip","jar","ear" );
	}

	public FileVisitResult visitFileFailed( Path arg0, IOException arg1 ) throws IOException {
		return FileVisitResult.CONTINUE;
	}

}
