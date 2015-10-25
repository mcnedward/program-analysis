package com.architecture_design.app.ui.diagram;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
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
import com.architecture_design.app.classobject.statement.DoStatement;
import com.architecture_design.app.classobject.statement.ForEachStatement;
import com.architecture_design.app.classobject.statement.ForStatement;
import com.architecture_design.app.classobject.statement.IfStatement;
import com.architecture_design.app.classobject.statement.SwitchStatement;
import com.architecture_design.app.classobject.statement.WhileStatement;
import com.architecture_design.app.ui.panel.ContentPanel;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 25, 2015
 *
 */
public class FlowGraphDiagram extends BaseDiagram<MethodObject> {
	private static final long serialVersionUID = -8591669327778156068L;

	private JPanel flowGraphPanel;

	public FlowGraphDiagram(ContentPanel parent, MethodObject methodObject) {
		super(parent, methodObject);

		updateHeaderPanel("If-Green | For&ForEach-Blue | While-Yellow | Switch-Red");

		createFlowGraphDiagram();
	}

	private void createFlowGraphDiagram() {
		flowGraphPanel = new JPanel();
		flowGraphPanel.setLayout(new BoxLayout(flowGraphPanel, BoxLayout.Y_AXIS));

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = 1;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.fill = GridBagConstraints.BOTH;
		create();

		mainPanel.add(flowGraphPanel, constraints);
	}

	private void create() {
		List<LineObject> lineObjects = typeObject.getLineObjects();
		List<BaseStatement> statements = typeObject.getStatements();

		if (!lineObjects.isEmpty()) {
			int startingLine = lineObjects.get(0).getLineNumber();
			int endingLine = lineObjects.get(lineObjects.size() - 1).getLineNumber();
			for (int currentLine = startingLine; currentLine <= endingLine; currentLine++) {
				// Check if the current line is part of a statement
				if (!statements.isEmpty()) {
					for (BaseStatement statement : statements) {
						List<LineObject> statementLines = statement.getLines();
						if (!statementLines.isEmpty()) {
							LineObject firstLineInStatement = statementLines.get(0);
							if (firstLineInStatement.getLineNumber() == currentLine) {
								buildNodes(statement);
								currentLine = statementLines.get(statementLines.size() - 1).getLineNumber() + 1;
								continue;
							}
						}
					}
				}
				// If nothing found in statements, then find line in single line objects
				for (LineObject line : lineObjects) {
					if (line.getLineNumber() == currentLine) {
						createNode(flowGraphPanel, line, Color.BLACK);
					}
				}
			}
		}
	}

	private void buildNodes(BaseStatement statement) {
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout(0, 0));
		container.setBorder(new EmptyBorder(5, 0, 5, 0));

		if (statement instanceof IfStatement) {
			createIfPanel((IfStatement) statement, container);
		} else if (statement instanceof ForEachStatement || statement instanceof ForStatement) {
			createGenericStatement(statement, container, Color.BLUE);
		} else if (statement instanceof WhileStatement || statement instanceof DoStatement) {
			createGenericStatement(statement, container, Color.YELLOW);
		} else if (statement instanceof SwitchStatement) {
			createGenericStatement(statement, container, Color.RED);
		} else {
			// createNode()
		}
		flowGraphPanel.add(container);
	}

	private void createGenericStatement(BaseStatement statement, JPanel container, Color color) {
		JPanel whilePanel = new JPanel();
		whilePanel.setLayout(new BoxLayout(whilePanel, BoxLayout.Y_AXIS));

		for (LineObject line : statement.getLines()) {
			createNode(whilePanel, line, color);
		}

		container.add(whilePanel, SwingConstants.CENTER);
	}

	private void createIfPanel(IfStatement statement, JPanel container) {
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

		statement.updateNodeNumbers();
		BaseStatement thenStatement = statement.getThenStatement();
		BaseStatement elseStatement = statement.getElseStatement();

		// Add if condition
		JPanel condPanel = new JPanel();
		condPanel.setBorder(new LineBorder(Color.GREEN));
		JLabel lblCondition = new JLabel("m" + statement.getLines().get(0).getNodeNumber());
		condPanel.add(lblCondition);
		conditionPanel.add(condPanel);

		for (LineObject line : thenStatement.getLines()) {
			createNode(thenPanel, line, Color.GREEN);
		}
		for (LineObject line : elseStatement.getLines()) {
			createNode(elsePanel, line, Color.GREEN);
		}

		container.add(ifPanel, SwingConstants.CENTER);
	}

	private void createNode(JPanel parent, LineObject line, Color color) {
		JPanel container = new JPanel();
		parent.setBorder(new EmptyBorder(2, 0, 2, 0));
		JPanel panel = new JPanel();
		container.add(panel);
		panel.setBorder(new LineBorder(color));

		JLabel label = new JLabel("m" + String.valueOf(line.getNodeNumber()));
		panel.add(label);
		parent.add(container);
	}

	@Override
	protected void setDimension() {
		height = parent.getDiagramPanel().getHeight();
		width = 450;
	}

}
