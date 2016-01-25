package uk.org.freedonia.bigmethods.scanner;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import uk.org.freedonia.bigmethods.results.IScanResults;

public class ProjectScanner {

	public static ExecutorService service;
	private static List<Future<?>> futureTasks;
	private static Object lock = new Object();
	
	public ProjectScanner() {
		service = Executors.newFixedThreadPool(5);
		futureTasks = Collections.synchronizedList(new ArrayList<Future<?>>());
	}


	
	
	public void scanPath( Path path, IScanResults scanResults, int minSize ) throws InterruptedException, ExecutionException {
		submitTask( new RunnableJarScanner( path, false, scanResults,null, minSize ) );
		while ( true ) {
			List<Future<?>> futureCopyList = null;
			synchronized ( lock ) {
				futureCopyList = new ArrayList<>();
				for ( Future<?> fut : futureTasks ) {
					futureCopyList.add( fut );
				}
			}
			for ( Future<?> fut : futureCopyList ) {
				fut.get();
				futureTasks.remove(fut);
			}
			futureTasks.removeAll(futureCopyList);
			if ( futureTasks.isEmpty() ) {
				break;
			}
			
		}
		service.shutdown();	
	}
	
	public static void submitTask(  RunnableJarScanner runner ) {
		synchronized ( lock ) { 
			futureTasks.add(service.submit(runner));
		}
	}
	
	
	
}
