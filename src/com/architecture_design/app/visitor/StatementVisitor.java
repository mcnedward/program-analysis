package com.architecture_design.app.visitor;

import java.util.ArrayList;
import java.util.List;

import com.architecture_design.app.classobject.LineObject;
import com.architecture_design.app.classobject.LineType;
import com.architecture_design.app.classobject.method.MethodObject;
import com.architecture_design.app.classobject.statement.BaseStatement;
import com.architecture_design.app.classobject.statement.DoStatement;
import com.architecture_design.app.classobject.statement.ForEachStatement;
import com.architecture_design.app.classobject.statement.ForStatement;
import com.architecture_design.app.classobject.statement.SwitchStatement;
import com.architecture_design.app.classobject.statement.WhileStatement;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.ForeachStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.SwitchEntryStmt;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.WhileStmt;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 24, 2015
 *
 */
public class StatementVisitor extends BaseVisitor<MethodObject> {

	public StatementVisitor() {

	}

	@Override
	public void reset() {

	}

	@Override
	public void visit(ForeachStmt f, MethodObject arg) {
		ForEachStatement statement = new ForEachStatement();
		statement.setVariableType(f.getVariable().getType().toString());
		statement.setVariableValue(f.getVariable().getVars().get(0).toString());
		addLines(statement, f.toString(), f.getBeginLine());
		arg.addStatement(statement);
	}

	@Override
	public void visit(ForStmt f, MethodObject arg) {
		ForStatement statement = new ForStatement();
		statement.setInit(f.getInit().get(0).toString());
		statement.setCompare(f.getCompare().toString());
		statement.setUpdate(f.getUpdate().get(0).toString());
		addLines(statement, f.toString(), f.getBeginLine());
		arg.addStatement(statement);
	}

	@Override
	public void visit(IfStmt i, MethodObject arg) {
		List<LineObject> lineObjects = new ArrayList<LineObject>();
		List<Node> childrenNodes = i.getChildrenNodes();

		// Convert statement to lines
		int lineNumber = i.getBeginLine();
		for (String line : i.toString().split("[\\r\\n\\t]\\s?")) {
			lineObjects.add(new LineObject(line, lineNumber++));
		}

		checkNodesForStatement(this, childrenNodes, arg);

		// First line of the IfStmt is the condition statement
		LineObject conditionLine = findLineByNumber(lineObjects, i.getBeginLine());
		conditionLine.setLineType(LineType.IF);

		Statement elseStatement = i.getElseStmt();
		// Else statements in a block have the "else" line included in the statement, so the "else line can be found
		// right away. Non-block else statements do not include the "else", so find the line directly above
		if (elseStatement != null) {
			LineObject elseLine = null;
			if (elseStatement instanceof BlockStmt) {
				elseLine = findLineByNumber(lineObjects, elseStatement.getBeginLine());
				elseLine.setLineType(LineType.ELSE);
			}
			else if (elseStatement instanceof IfStmt) {
				elseLine = findLineByNumber(lineObjects, elseStatement.getBeginLine());
				elseLine.setLineType(LineType.ELSE_IF);
			}
			else {
				elseLine = findLineByNumber(lineObjects, elseStatement.getBeginLine() - 1);
				elseLine.setLineType(LineType.ELSE);
			}
		}
		arg.addLines(lineObjects);
	}

	private LineObject findLineByNumber(List<LineObject> lines, int lineNumber) {
		for (LineObject line : lines) {
			if (line.getLineNumber() == lineNumber)
				return line;
		}
		return null;
	}

	@Override
	public void visit(WhileStmt w, MethodObject arg) {
		WhileStatement statement = new WhileStatement();
		statement.setCondition(w.getCondition().toString());
		addLines(statement, w.toString(), w.getBeginLine());
		arg.addStatement(statement);
	}

	@Override
	public void visit(DoStmt d, MethodObject arg) {
		DoStatement statement = new DoStatement();
		statement.setCondition(d.getCondition().toString());
		addLines(statement, d.toString(), d.getBeginLine());
		arg.addStatement(statement);
	}

	@Override
	public void visit(SwitchStmt s, MethodObject arg) {
		SwitchStatement statement = new SwitchStatement();
		statement.setSelector(s.getSelector().toString());
		List<SwitchEntryStmt> entries = s.getEntries();
		for (SwitchEntryStmt entry : entries) {
			Expression labelExpression = entry.getLabel();
			String label = "";
			boolean isDefault = false;
			if (labelExpression == null) {
				isDefault = true;
				label = "default";
			} else
				label = labelExpression.toString();
			statement.addSwitchEntry(label, entry.getBeginLine(), isDefault);
		}
		addLines(statement, s.toString(), s.getBeginLine());
		arg.addStatement(statement);
	}

	private void addLines(BaseStatement statement, String lines, int lineNumber) {
		for (String line : lines.split("[\\r\\n\\t]\\s?")) {
			statement.addLine(line, lineNumber++);
		}
	}

}
