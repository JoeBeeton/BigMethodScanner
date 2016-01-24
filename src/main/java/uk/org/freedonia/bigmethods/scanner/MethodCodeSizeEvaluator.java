package uk.org.freedonia.bigmethods.scanner;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.CodeSizeEvaluator;

import uk.org.freedonia.bigmethods.results.IScanResults;

public class MethodCodeSizeEvaluator extends CodeSizeEvaluator {
	
	private String className;
	private String methodName;
	private IScanResults scanResults;
	private int minSize;

	public MethodCodeSizeEvaluator( MethodVisitor methodVisitor, String className, String methodName, IScanResults scanResults, int minSize ) {
		super( methodVisitor );
		this.className = className;
		this.methodName = methodName;
		this.scanResults = scanResults;
		this.minSize = minSize;
	}

	@Override
    public void visitEnd() {
        super.visitEnd();
        if ( getMinSize() > minSize ) {
        	String classWithDots = className.replaceAll("/", ".");
        	scanResults.submitLargeMethod(
        			classWithDots + "."  + methodName + "() size :  " + getMaxSize() );
        } 
    }

}
