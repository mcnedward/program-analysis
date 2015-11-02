package com.architecture_design.app.visitor;

import java.util.List;

import com.architecture_design.app.classobject.ClassObject;
import com.architecture_design.app.classobject.ExpressionObject;
import com.architecture_design.app.classobject.method.MethodObject;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 22, 2015
 *
 */
public class ClassVisitor extends BaseVisitor<ClassObject> {

	private ClassObject classObject;

	private VariableVisitor variableVisitor;
	private MethodVisitor methodVisitor;
	private ExpressionVisitor expressionVisitor;

	public ClassVisitor() {
		super();
		classObject = new ClassObject();

		variableVisitor = new VariableVisitor();
		methodVisitor = new MethodVisitor();
		expressionVisitor = new ExpressionVisitor();
	}

	public void reset() {
		classObject = new ClassObject();
		variableVisitor.reset();
		methodVisitor.reset();
		expressionVisitor.reset();
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

		visitNodes(n.getChildrenNodes(), arg);

		classObject.setVariables(variableVisitor.getVariableObjects());

		List<ExpressionObject> nameExpressions = expressionVisitor.getNameExpressions();
		classObject.setExpressions(nameExpressions);

		List<MethodObject> methods = methodVisitor.getMethodObjects();
		for (MethodObject method : methods) {
			method.update();
		}
		classObject.setMethods(methods);
	}

	private void visitNodes(List<Node> nodes, ClassObject arg) {
		if (!nodes.isEmpty()) {
			for (Node node : nodes) {
				if (node instanceof NameExpr) {
					expressionVisitor.visit((NameExpr) node, arg);
				}
				if (node instanceof FieldAccessExpr) {
					expressionVisitor.visit((FieldAccessExpr) node, arg);
				}
				if (node instanceof AssignExpr) {
					expressionVisitor.visit((AssignExpr) node, arg);
				}
				if (node instanceof BooleanLiteralExpr) {
					expressionVisitor.visit((BooleanLiteralExpr) node, arg);
				}
				visitNodes(node.getChildrenNodes(), arg);
			}
		}
	}

	/**
	 * @return the classObject
	 */
	public ClassObject getClassObject() {
		return classObject;
	}

}
