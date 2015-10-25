package com.architecture_design.app.ui.diagram;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.architecture_design.app.classobject.LineObject;
import com.architecture_design.app.classobject.method.MethodObject;
import com.architecture_design.app.ui.panel.ContentPanel;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 20, 2015
 *
 */
public class MethodDiagram extends BaseDiagram<MethodObject> {
	private static final long serialVersionUID = 1L;

	public MethodDiagram() {
		super();
	}

	public MethodDiagram(ContentPanel parent, MethodObject methodObject) {
		super(parent, methodObject, 10);

		createMethodLines();
	}

	private void createMethodLines() {
		List<LineObject> methodLines = typeObject.getLineObjects();
		if (methodLines.isEmpty())
			return;
		JPanel methodLinePanel = new JPanel();
		methodLinePanel.setLayout(new BoxLayout(methodLinePanel, BoxLayout.Y_AXIS));
		methodLinePanel.setBorder(new LineBorder(Color.BLACK));
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = 1;
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.fill = GridBagConstraints.BOTH;

		for (LineObject lineObject : methodLines) {
			String line = lineObject.getLine();
			int lineNumber = lineObject.getLineNumber();

			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout(0, 0));

			JPanel linePanel = new JPanel();
			linePanel.setBorder(new LineBorder(Color.BLACK));
			JLabel lblLinePosition = new JLabel(String.valueOf(lineNumber));
			lblLinePosition.setFont(font);
			lblLinePosition.setBorder(new EmptyBorder(0, 2, 0, 2));
			linePanel.add(lblLinePosition);
			panel.add(linePanel, BorderLayout.WEST);

			JLabel label = new JLabel(line);
			label.setFont(font);
			label.setBorder(new EmptyBorder(0, 5, 0, 5));
			label.setHorizontalAlignment(SwingConstants.LEFT);
			panel.add(label, BorderLayout.CENTER);
			methodLinePanel.add(panel);
		}
		mainPanel.add(methodLinePanel, constraints);
	}

	@Override
	protected void setDimension() {
		height = parent.getDiagramPanel().getHeight();
		width = 450;
	}

}
