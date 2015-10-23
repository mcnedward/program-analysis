package com.architecture_design.app.visitor;

import com.architecture_design.app.classobject.ClassObject;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

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
	
	public void reset() {
		classObject = new ClassObject();
		variableVisitor.reset();
		methodVisitor.reset();
	}

	@Override
	public void visit(ClassOrInterfaceDeclaration n, ClassObject arg) {
		classObject.setName(n.getName());

		if (n.getExtends() != null && !n.getExtends().isEmpty())
			for (ClassOrInterfaceType coi : n.getExtends())
				classObject.addExtends(coi.getName());
		if (n.getImplements() != null && !n.getImplements().isEmpty())
			for (ClassOrInterfaceType coi : n.getImplements())
				classObject.addInterface(coi.getName());

		for (BodyDeclaration member : n.getMembers()) {
			if (member instanceof FieldDeclaration) {
				variableVisitor.visit((FieldDeclaration) member, null);
			}
			if (member instanceof MethodDeclaration) {
				methodVisitor.visit((MethodDeclaration) member, null);
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
