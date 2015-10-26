package com.architecture_design.app.visitor;

import java.util.List;

import com.architecture_design.app.classobject.method.MethodObject;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ModifierSet;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.ForeachStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 23, 2015
 *
 */
public abstract class BaseVisitor<T> extends VoidVisitorAdapter<T> {

	protected StatementVisitor statementVisitor;

	public BaseVisitor() {

	}
	
	public BaseVisitor(StatementVisitor statementVisitor) {
		this.statementVisitor = statementVisitor;
	}

	public abstract void reset();

	protected void checkNodesForStatement(StatementVisitor statementVisitor, List<Node> nodes, MethodObject arg) {
		for (Node node : nodes) {
			if (node instanceof ForeachStmt)
				statementVisitor.visit((ForeachStmt) node, arg);
			else if (node instanceof ForStmt)
				statementVisitor.visit((ForStmt) node, arg);
			else if (node instanceof IfStmt)
				statementVisitor.visit((IfStmt) node, arg);
			else if (node instanceof WhileStmt)
				statementVisitor.visit((WhileStmt) node, arg);
			else if (node instanceof DoStmt)
				statementVisitor.visit((DoStmt) node, arg);
			else if (node instanceof SwitchStmt)
				statementVisitor.visit((SwitchStmt) node, arg);
			else if (node instanceof BlockStmt) {
				List<Node> childrenNodes = node.getChildrenNodes();
				if (!childrenNodes.isEmpty()) {
					checkNodesForStatement(statementVisitor, childrenNodes, arg);
				}
			}
		}
	}

	// Adapted from DumpVisitor
	protected String decodeModifiers(final int modifiers) {
		if (ModifierSet.isPrivate(modifiers)) {
			return ("private ");
		}
		if (ModifierSet.isProtected(modifiers)) {
			return ("protected ");
		}
		if (ModifierSet.isPublic(modifiers)) {
			return ("public ");
		}
		if (ModifierSet.isAbstract(modifiers)) {
			return ("abstract ");
		}
		if (ModifierSet.isStatic(modifiers)) {
			return ("static ");
		}
		if (ModifierSet.isFinal(modifiers)) {
			return ("final ");
		}
		if (ModifierSet.isNative(modifiers)) {
			return ("native ");
		}
		if (ModifierSet.isStrictfp(modifiers)) {
			return ("strictfp ");
		}
		if (ModifierSet.isSynchronized(modifiers)) {
			return ("synchronized ");
		}
		if (ModifierSet.isTransient(modifiers)) {
			return ("transient ");
		}
		if (ModifierSet.isVolatile(modifiers)) {
			return ("volatile ");
		}
		return "";
	}
}
