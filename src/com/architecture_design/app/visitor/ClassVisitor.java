package com.architecture_design.app.visitor;

import com.architecture_design.app.classobject.ClassObject;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 22, 2015
 *
 */
public class ClassVisitor extends BaseVisitor<ClassObject> {

	private ClassObject classObject;
	
	private VariableVisitor variableVisitor;
	private MethodVisitor methodVisitor;
	
	public ClassVisitor() {
		super();
		classObject = new ClassObject();
		
		variableVisitor = new VariableVisitor();
		methodVisitor = new MethodVisitor();
	}

	@Override
	public void visit(ClassOrInterfaceDeclaration n, ClassObject arg) {
		classObject.setName(n.getName());
		
		for (BodyDeclaration member : n.getMembers()) {
			System.out.println(member.getClass());
			if (member instanceof FieldDeclaration) {
//				System.out.println("Visiting FieldDeclaration member: " + member);
				variableVisitor.visit((FieldDeclaration)member, null);
			}
			if (member instanceof MethodDeclaration) {
//				System.out.println("Visiting MethodDeclaration member: " + member);
				methodVisitor.visit((MethodDeclaration)member, null);
			}
		}
		
		classObject.setVariables(variableVisitor.getVariableObjects());
		classObject.setMethods(methodVisitor.getMethodObjects());
	}

	/**
	 * @return the classObject
	 */
	public ClassObject getClassObject() {
		return classObject;
	}
	
}
