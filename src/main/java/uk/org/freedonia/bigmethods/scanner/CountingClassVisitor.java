package uk.org.freedonia.bigmethods.scanner;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import uk.org.freedonia.bigmethods.results.IScanResults;

public class CountingClassVisitor extends ClassVisitor {
	
	private String className="";
	private IScanResults scanResults;
	private int minSize;
	
	public CountingClassVisitor(IScanResults scanResults, int minSize) {
		super( Opcodes.ASM5 );
		this.scanResults = scanResults;
		this.minSize = minSize;
	}
	
	
	@Override
    public void visit(int version, int access, String name, String signature,
            String superName, String[] interfaces) {
		className = name;
    }

	@Override
    public MethodVisitor visitMethod(int access, String name, String desc,
            String signature, String[] exceptions) {
        	MethodVisitor visitor = super.visitMethod(access, name, desc, signature, exceptions);
        	MethodCodeSizeEvaluator eval = new MethodCodeSizeEvaluator( visitor, className, name, scanResults, minSize );
        	return eval;
    }

}
