package com.architecture_design.app.ui.diagram;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.regex.Pattern;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.architecture_design.app.classobject.LineObject;
import com.architecture_design.app.classobject.LineType;
import com.architecture_design.app.classobject.method.MethodObject;
import com.architecture_design.app.classobject.statement.BaseStatement;
import com.architecture_design.app.classobject.statement.IfStatement;
import com.architecture_design.app.ui.panel.NodePanel;
import com.architecture_design.app.ui.panel.ifpanel.IfPanel;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 29, 2015
 *
 */
public class IfBuilder {

	private MethodObject methodObject;
	private boolean smallIcons;
	
	public IfBuilder(MethodObject methodObject, boolean smallIcons) {
		this.methodObject = methodObject;
		this.smallIcons = smallIcons;
	}
	
	public IfBuilder(MethodObject methodObject) {
		this(methodObject, false);
	}
	
	public void build(JPanel container) {
		BaseStatement methodStatement = methodObject.getStatement();
		IfStatement ifStatement = (IfStatement) methodStatement.getStatements().get(methodStatement.getStatements().size() - 1);
		IfPanel ifPanel = new IfPanel(methodObject, ifStatement);
		fillIfPanel(ifStatement, ifPanel);
		container.add(ifPanel);
		
		// Reset for reuse!
		methodObject.reset();
	}

	private BaseStatement fillIfPanel(IfStatement ifStatement, IfPanel ifPanel) {
		boolean isElse = false;
		for (LineObject line : ifStatement.getLines()) {
			if (!methodObject.isNodeCreatedAtLine(line.getLineNumber())) {
				if (line.getLineType() == null) {
					if ((isElse) || Pattern.compile("\\s*\\}?\\s*\\belse\\b\\s*\\{?").matcher(line.getLine()).find()) {
						JPanel nodeContainer = createNode(line, Color.GREEN);
						ifPanel.addToElsePanel(nodeContainer);
						methodObject.setNodeCreated(line.getLineNumber(), true);
					} else {
						JPanel nodeContainer = createNode(line, Color.GREEN);
						ifPanel.addToThenPanel(nodeContainer);
						methodObject.setNodeCreated(line.getLineNumber(), true);
					}
				} else if ((line.getLineType() != null && line.getLineType().equals(LineType.ELSE)) || isElse) {
					if (!methodObject.isParentElse(ifStatement)) {
						isElse = true;
						JPanel nodeContainer = createNode(line, Color.GREEN);
						ifPanel.addToElsePanel(nodeContainer);
						methodObject.setNodeCreated(line.getLineNumber(), true);
					} else {
						return null;
					}
				} else if (line.isStatement()) {
					BaseStatement statement = ifStatement.getStatementAtLineNumber(ifStatement, line.getLineNumber());
					if (statement instanceof IfStatement) {
						if (line.getLineType().equals(LineType.ELSE_IF)) {
							// New ElseIf Panel
							IfPanel newIfPanel = new IfPanel(methodObject, (IfStatement) statement);
							fillIfPanel((IfStatement) statement, newIfPanel);
							ifPanel.addToElseIfPanel(newIfPanel);
							isElse = true;
						} else if (isElse) {
							JPanel branchPanel = new JPanel();
							branchPanel.setLayout(new BorderLayout(0, 0));
							branchPanel.setBorder(new EmptyBorder(5, 0, 5, 0));
							branchPanel.setBorder(new LineBorder(Color.BLUE));
						} else {
							// New If Panel
							IfPanel newIfPanel = new IfPanel(methodObject, (IfStatement) statement);
							fillIfPanel((IfStatement) statement, newIfPanel);
							ifPanel.addToThenPanel(newIfPanel);
						}
					}
				}
			}
		}
		return null;
	}

	private JPanel createNode(LineObject line, Color color) {
		JPanel container = new JPanel();

		String nodeText = "m" + String.valueOf(line.getNodeNumber());
		NodePanel panel = new NodePanel(nodeText, color, smallIcons);

		container.add(panel);
		return container;
	}

}
