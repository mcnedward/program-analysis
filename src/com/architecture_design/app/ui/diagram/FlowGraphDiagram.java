package com.architecture_design.app.ui.diagram;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.architecture_design.app.classobject.LineObject;
import com.architecture_design.app.classobject.method.MethodObject;
import com.architecture_design.app.classobject.statement.BaseStatement;
import com.architecture_design.app.classobject.statement.ForStatement;
import com.architecture_design.app.classobject.statement.ForeachStatement;
import com.architecture_design.app.classobject.statement.IfStatement;
import com.architecture_design.app.ui.panel.ContentPanel;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 25, 2015
 *
 */
public class FlowGraphDiagram extends BaseDiagram<MethodObject> {
	private static final long serialVersionUID = -8591669327778156068L;

	private JPanel flowGraphPanel;

	public FlowGraphDiagram(ContentPanel parent, MethodObject methodObject) {
		super(parent, methodObject, false);

		createFlowGraphDiagram();
	}

	private void createFlowGraphDiagram() {
		flowGraphPanel = new JPanel();
		flowGraphPanel.setLayout(new BoxLayout(flowGraphPanel, BoxLayout.Y_AXIS));

		create();

		mainPanel.add(flowGraphPanel);
	}

	private void create() {
		List<LineObject> lineObjects = typeObject.getLineObjects();
		List<BaseStatement> statements = typeObject.getStatements();

		int startingLine = lineObjects.get(0).getLineNumber();
		int endingLine = lineObjects.get(lineObjects.size() - 1).getLineNumber();
		int mNumber = 1;
		for (int currentLine = startingLine; currentLine < endingLine; currentLine++) {
			// Check if the current line is part of a statement
			boolean breakOut = false, valueFound = false;
			for (BaseStatement statement : statements) {
				if (breakOut)
					continue;
				LineObject firstLineInStatement = statement.getLines().get(0);
				if (firstLineInStatement.getLineNumber() == currentLine) {
					handleStatementBranching(statement);
					createNode(statement, mNumber);
					for (LineObject statementLine : statement.getLines()) {
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

	private void createNode(BaseStatement statement, int nodeNumber) {
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout(0, 0));
		container.setBorder(new EmptyBorder(5, 0, 5, 0));

//		JPanel nodePanel = new JPanel();
//		nodePanel.setBorder(new EmptyBorder(5, 0, 5, 0));
//		nodePanel.setBorder(new LineBorder(Color.BLACK));

		if (statement instanceof IfStatement) {
			JPanel ifPanel = new JPanel();
			ifPanel.setLayout(new BorderLayout(0, 0));
			JPanel conditionPanel = new JPanel();
			// Create Statement panels
			JPanel statementPanel = new JPanel();
			statementPanel.setLayout(new GridLayout(1, 2));
			JPanel thenPanel = new JPanel();
			thenPanel.setLayout(new BoxLayout(thenPanel, BoxLayout.Y_AXIS));
			JPanel elsePanel = new JPanel();
			elsePanel.setLayout(new BoxLayout(elsePanel, BoxLayout.Y_AXIS));
			statementPanel.add(thenPanel);
			statementPanel.add(elsePanel);
			ifPanel.add(conditionPanel, BorderLayout.NORTH);
			ifPanel.add(statementPanel, BorderLayout.CENTER);
			
			IfStatement ifStatement = (IfStatement) statement;
			ifStatement.updateNodeNumbers();
			BaseStatement thenStatement = ifStatement.getThenStatement();
			BaseStatement elseStatement = ifStatement.getElseStatement();
			
			// Add if condition
			JPanel condPanel = new JPanel();
			condPanel.setBorder(new LineBorder(Color.BLACK));
			JLabel lblCondition = new JLabel("m" + ifStatement.getLines().get(0).getNodeNumber());
			condPanel.add(lblCondition);
			conditionPanel.add(condPanel);
			
			for (LineObject line : thenStatement.getLines()) {
				createIfBranchNode(thenPanel, line);
			}
			for (LineObject line : elseStatement.getLines()) {
				createIfBranchNode(elsePanel, line);
			}
			
			container.add(ifPanel, SwingConstants.CENTER);
		}
		flowGraphPanel.add(container);
	}
	
	private void createIfBranchNode(JPanel parent, LineObject line) {
		JPanel container = new JPanel();
		parent.setBorder(new EmptyBorder(2, 0, 2, 0));
		JPanel panel = new JPanel();
		container.add(panel);
		panel.setBorder(new LineBorder(Color.BLACK));
		JLabel label = new JLabel("m" + String.valueOf(line.getNodeNumber()));
		panel.add(label);
		parent.add(container);
	}

	private void handleStatementBranching(BaseStatement statement) {
		if (statement instanceof ForeachStatement) {
			// System.out.println("For Each Statement starting...");
		} else if (statement instanceof ForStatement) {

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

	@Override
	protected void setDimension() {
		height = parent.getDiagramPanel().getHeight();
		width = 450;
	}

}
