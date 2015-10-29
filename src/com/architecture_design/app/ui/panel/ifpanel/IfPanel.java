package com.architecture_design.app.ui.panel.ifpanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.architecture_design.app.classobject.LineObject;
import com.architecture_design.app.classobject.method.MethodObject;
import com.architecture_design.app.classobject.statement.IfStatement;
import com.architecture_design.app.ui.panel.NodePanel;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 29, 2015
 *
 */
public class IfPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	public static int ID;

	private MethodObject methodObject;
	private IfStatement statement;
	
	private JPanel statementPanel;

	public IfPanel(MethodObject methodObject, IfStatement statement) {
		this.methodObject = methodObject;
		this.statement = statement;
		ID++;
		initialize();
	}

	private void initialize() {
		setLayout(new BorderLayout(0, 0));
		setBorder(new LineBorder(Color.GREEN));
		JPanel conditionPanel = new JPanel();
		// Create Statement panels
		statementPanel = new JPanel();
		statementPanel.setLayout(new GridLayout(1, 3));
		statementPanel.setBorder(new LineBorder(Color.GRAY));
		statementPanel.putClientProperty("statementPanel", true);
		add(conditionPanel, BorderLayout.NORTH);
		add(statementPanel, BorderLayout.CENTER);
		
		addConditionalStatementPanel("then");

		JPanel node = createNode(statement.getCondition(), Color.GREEN);
		conditionPanel.add(node);
		methodObject.setNodeCreated(statement.getBeginLine(), true);
	}

	public JPanel addConditionalStatementPanel(String panelCondition) {
		JPanel conditionalPanel = new JPanel();
		conditionalPanel.setBorder(new LineBorder(Color.GRAY));
		conditionalPanel.setLayout(new BoxLayout(conditionalPanel, BoxLayout.Y_AXIS));
		statementPanel.add(conditionalPanel);
		statementPanel.putClientProperty(panelCondition, conditionalPanel);
		return conditionalPanel;
	}
	
	public void addToThenPanel(JPanel node) {
		JPanel thenPanel = (JPanel) statementPanel.getClientProperty("then");
		thenPanel.add(node);
	}
	
	public void addToElsePanel(JPanel node) {
		JPanel elsePanel = (JPanel) statementPanel.getClientProperty("else");
		if (elsePanel == null)
			elsePanel = addConditionalStatementPanel("else");
		elsePanel.add(node);
	}
	
	public void addToElseIfPanel(JPanel node) {
		JPanel elseIfPanel = addConditionalStatementPanel("elseIf");
		elseIfPanel.add(node);		
	}

	private JPanel createNode(LineObject line, Color color) {
		JPanel container = new JPanel();

		String nodeText = "m" + String.valueOf(line.getNodeNumber());
		NodePanel panel = new NodePanel(nodeText, color);

		container.add(panel);
		return container;
	}

}
