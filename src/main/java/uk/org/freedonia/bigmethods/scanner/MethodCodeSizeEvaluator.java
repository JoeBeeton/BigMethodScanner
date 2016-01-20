package uk.org.freedonia.bigmethods.scanner;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.CodeSizeEvaluator;

import uk.org.freedonia.bigmethods.results.IScanResults;

public class MethodCodeSizeEvaluator extends CodeSizeEvaluator {
	
	private String className;
	private String methodName;
	private IScanResults scanResults;

	public MethodCodeSizeEvaluator( MethodVisitor methodVisitor, String className, String methodName, IScanResults scanResults ) {
		super( methodVisitor );
		this.className = className;
		this.methodName = methodName;
		this.scanResults = scanResults;
	}

	@Override
    public void visitEnd() {
        super.visitEnd();
        if ( getMinSize() > 8000 ) {
        	String classWithDots = className.replaceAll("/", ".");
        	scanResults.submitLargeMethod(
        			classWithDots + "."  + methodName + "() size :  " + getMaxSize() );
        } 
    }

}
