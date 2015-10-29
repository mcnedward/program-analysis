package com.architecture_design.app.ui.diagram;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.architecture_design.app.classobject.method.MethodObject;
import com.architecture_design.app.ui.panel.ContentPanel;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 25, 2015
 *
 */
public class FlowGraphDiagram extends BaseDiagram<MethodObject> {
	private static final long serialVersionUID = -8591669327778156068L;

	private JPanel flowGraphPanel;
	
	private IfBuilder ifBuilder;

	public FlowGraphDiagram(ContentPanel parent, MethodObject methodObject) {
		super(parent, methodObject, 8);

		updateHeaderPanel("If-Green | For&ForEach-Blue | While-Yellow | Switch-Red");

		ifBuilder = new IfBuilder(methodObject, true);
		create();
	}
	
	// Put this in BaseDiagram as abstract method?
	private void create() {
		flowGraphPanel = new JPanel();
		flowGraphPanel.setLayout(new BoxLayout(flowGraphPanel, BoxLayout.Y_AXIS));

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = 1;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.fill = GridBagConstraints.BOTH;
		
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout(0, 0));
		container.setBorder(new EmptyBorder(5, 0, 5, 0));
		
		JButton expandButton = new JButton("EXPAND");
		flowGraphPanel.add(expandButton);

		ifBuilder.build(container);

		flowGraphPanel.add(container);
		mainPanel.add(flowGraphPanel, constraints);
	}

	@Override
	protected void setDimension() {
		height = parent.getDiagramPanel().getHeight();
		width = 450;
	}

}
