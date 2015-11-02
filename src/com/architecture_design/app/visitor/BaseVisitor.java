package com.architecture_design.app.visitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.architecture_design.app.classobject.LineObject;
import com.architecture_design.app.classobject.statement.BaseStatement;
import com.architecture_design.app.comparator.LineObjectComparator;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ModifierSet;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.ForeachStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
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

	/**
	 * Convert statement to line objects.
	 * 
	 * @param s
	 *            The statement to convert.
	 * @return A list of the line objects in this statement.
	 */
	protected List<LineObject> convertToLineObjects(Statement s) {
		List<LineObject> lines = new ArrayList<LineObject>();
		int lineNumber = s.getBeginLine();
		for (String line : s.toString().split("[\\r\\n\\t]\\s?")) {
			lines.add(new LineObject(line, lineNumber++));
		}
		return lines;
	}

	protected List<LineObject> convertToLineObjects2(Statement s) {
		List<LineObject> lines = new ArrayList<LineObject>();
		handleNodes(lines, s.getChildrenNodes());
		Collections.sort(lines, new LineObjectComparator());
		int currentLine, previousLine = 0;
		int y = lines.size();
		for (int x = 0; x < y; x++) {
			LineObject line = lines.get(x);
			currentLine = line.getLineNumber();
			if (x == 0) {
				previousLine = currentLine;
				continue;
			}
			if (currentLine != previousLine + 1) {
				while (previousLine != currentLine - 1) {
					lines.add(new LineObject("", ++previousLine));
				}
			}
			previousLine = currentLine;
		}
		Collections.sort(lines, new LineObjectComparator());
		return lines;
	}

	private void handleNodes(List<LineObject> lineSet, List<Node> nodes) {
		if (!nodes.isEmpty()) {
			for (Node node : nodes) {
				List<LineObject> lines = handleNode(node);
				if (lineSet.isEmpty()) {
					lineSet.addAll(lines);
				} else {
					for (LineObject line : lines) {
						while (lineSet.iterator().hasNext()) {
							if (lineSet.iterator().next().getLineNumber() != line.getLineNumber())
								lineSet.add(line);
							{
								System.out.println("STUCK");
							}
						}
					}
					if (!node.getChildrenNodes().isEmpty())
						handleNodes(lineSet, node.getChildrenNodes());
				}
			}
		}
	}

	private List<LineObject> handleNode(Node node) {
		List<LineObject> lineSet = new ArrayList<LineObject>();
		if (node instanceof IfStmt || node instanceof ForStmt || node instanceof ForeachStmt || node instanceof WhileStmt || node instanceof DoStmt
				|| node instanceof SwitchStmt || node instanceof ExpressionStmt) {
			lineSet.addAll(getLines(node.toString(), node.getBeginLine()));
		}
		return lineSet;
	}

	private List<LineObject> getLines(String statement, int beginLine) {
		List<LineObject> lines = new ArrayList<LineObject>();
		int lineNumber = beginLine;
		for (String line : statement.split("[\\n\\t]\\s?")) {
			lines.add(new LineObject(line, lineNumber++));
		}
		return lines;
	}

	protected void checkNodesForStatement(StatementVisitor statementVisitor, List<Node> nodes, BaseStatement arg) {
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
