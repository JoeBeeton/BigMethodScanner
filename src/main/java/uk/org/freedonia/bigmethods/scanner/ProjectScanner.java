package uk.org.freedonia.bigmethods.scanner;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import uk.org.freedonia.bigmethods.results.IScanResults;

public class ProjectScanner {

	public static ExecutorService service;
	private static List<Future<?>> futureTasks;
	
	
	public ProjectScanner() {
		service = Executors.newFixedThreadPool(5);
		futureTasks = Collections.synchronizedList(new ArrayList<Future<?>>());
	}


	
	
	public void scanPath( Path path, IScanResults scanResults, int minSize ) throws InterruptedException, ExecutionException {
		submitTask( new RunnableJarScanner( path, false, scanResults,null, minSize ) );
		for ( Future<?> future : futureTasks ) {
			try {
				future.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		service.shutdown();	
	}
	
	public static void submitTask(  RunnableJarScanner runner ) {
		futureTasks.add(service.submit(runner));
	}
	
	
	
}
