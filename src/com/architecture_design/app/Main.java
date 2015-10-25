package com.architecture_design.app;

import java.io.File;
import java.util.List;

import com.architecture_design.app.classobject.ClassObject;
import com.architecture_design.app.classobject.LineObject;
import com.architecture_design.app.classobject.method.MethodObject;
import com.architecture_design.app.classobject.statement.BaseStatement;
import com.architecture_design.app.classobject.statement.ForStatement;
import com.architecture_design.app.classobject.statement.ForEachStatement;
import com.architecture_design.app.classobject.statement.IfStatement;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 16, 2015
 *
 */
public class Main {

	private static String FILE_LOCATION = "resources/Account.java";
	@SuppressWarnings("unused")
	private static String DIRECTORY_LOCATION = "C:\\users\\edward\\dev\\workspace\\eatingcinci-spring";

	private static Analyser analyser;

	public static void main(String[] args) {

		analyser = new Analyser();
		List<ClassObject> classObjects = analyser.analyse(new File(FILE_LOCATION));

		ClassObject classObject = classObjects.get(0);
		System.out.println(classObject.getName());

		MethodObject method = classObject.getMethods().get(1);
		System.out.println(method);

		List<LineObject> lineObjects = method.getLineObjects();
		List<BaseStatement> statements = method.getStatements();

		int startingLine = lineObjects.get(0).getLineNumber();
		int endingLine = lineObjects.get(lineObjects.size() - 1).getLineNumber();
		int mNumber = 1;
		for (int currentLine = startingLine; currentLine < endingLine; currentLine++) {
			// Check if the current line is part of a statement
			boolean breakOut = false, valueFound = false;;
			for (BaseStatement statement : statements) {
				if (breakOut)
					continue;
				LineObject firstLineInStatement = statement.getLines().get(0);
				if (firstLineInStatement.getLineNumber() == currentLine) {
					handleStatementBranching(statement);
					for (LineObject statementLine : statement.getLines()) {
						System.out.println("m" + mNumber + " [" + statementLine.getLineNumber() + "]: " + statementLine.getLine());
						mNumber++;
						currentLine = statementLine.getLineNumber();
						valueFound = true;
					}
					breakOut = true;
					continue;
				}
			}
			// If nothing found in statements, then find line in single line objects
			if (!valueFound) {
				for (LineObject line : lineObjects) {
					if (line.getLineNumber() == currentLine) {
						System.out.println("m" + mNumber + " [" + currentLine + "]: " + line.getLine());
					}
				}
			}
		}
	}
	
	private static void handleStatementBranching(BaseStatement statement) {
		if (statement instanceof ForEachStatement) {
//			System.out.println("For Each Statement starting...");
		}
		else if (statement instanceof ForStatement) {
			
		} else if (statement instanceof IfStatement) {
			IfStatement ifStatement = (IfStatement) statement;
			System.out.println("Branching off for If Statement");
			System.out.print("IF... ");
			System.out.println(ifStatement.getCondition());
			printLines(ifStatement.getThenStatement());
			System.out.println("ELSE...");
			printLines(ifStatement.getElseStatement());
		}
	}
	
	private static void printLines(BaseStatement statement) {
		for (LineObject line : statement.getLines()) {
			System.out.println(line);
		}
	}
}
