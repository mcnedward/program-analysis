package com.architecture_design.app.shapes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.architecture_design.app.classobject.ClassObject;
import com.architecture_design.app.classobject.MethodObject;
import com.architecture_design.app.classobject.MethodParameter;
import com.architecture_design.app.classobject.VariableObject;
import com.architecture_design.app.ui.DrawingPanel;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 18, 2015
 *
 */
public class ClassDiagram extends JScrollPane {
	private static final long serialVersionUID = -523408531976611140L;

	private static int WIDTH = 500;
	private static int MAX_HEIGHT = 300;

	private DrawingPanel parent;
	private JPanel mainPanel;
	private ClassObject classObject;

	public ClassDiagram() {

	}

	public ClassDiagram(DrawingPanel parent, ClassObject classObject) {
		this.parent = parent;
		this.classObject = classObject;

		initialize();
	}

	private void initialize() {
		int height = ((classObject.getVariables().size() + classObject.getMethods().size()) * 30) + 30;
		if (height > MAX_HEIGHT)
			height = MAX_HEIGHT;
		int x = (parent.getWidth() / 2) - WIDTH / 2;
		int y = (parent.getHeight() / 2) - height / 2;
		setBounds(x, y, WIDTH, height);
		setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		createMainPanel();
		createClassPanel();
		createVariablePanels();
		createMethodPanels();
	}

	private void createMainPanel() {
		mainPanel = new JPanel();
		setViewportView(mainPanel);
		GridBagLayout layout = new GridBagLayout();

		layout.columnWidths = new int[] { 0 };
		layout.rowHeights = new int[] { 26, 0, 0, 0 };
		layout.columnWeights = new double[] { 1.0, 1.0, 1.0 };
		layout.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		mainPanel.setLayout(layout);
		mainPanel.setBorder(new LineBorder(Color.BLACK));
	}

	private void createClassPanel() {
		JPanel classNamePanel = new JPanel();
		classNamePanel.setBorder(new LineBorder(Color.BLACK));
		JLabel lblClassName = new JLabel(classObject.getClassName());
		classNamePanel.add(lblClassName);
		GridBagConstraints constraints1 = new GridBagConstraints();
		constraints1.gridwidth = 1;
		constraints1.gridx = 0;
		constraints1.gridy = 0;
		constraints1.fill = GridBagConstraints.BOTH;
		mainPanel.add(classNamePanel, constraints1);
	}

	private void createVariablePanels() {
		List<VariableObject> variables = classObject.getVariables();
		if (variables.isEmpty())
			return;
		JPanel variablePanel = new JPanel();
		variablePanel.setLayout(new BoxLayout(variablePanel, BoxLayout.Y_AXIS));
		variablePanel.setBorder(new LineBorder(Color.BLACK));
		GridBagConstraints constraints2 = new GridBagConstraints();
		constraints2.gridwidth = 1;
		constraints2.gridx = 0;
		constraints2.gridy = 1;
		constraints2.fill = GridBagConstraints.BOTH;
		for (VariableObject variableObject : variables) {
			String variable = "- " + variableObject.getName() + ": " + variableObject.getReturnType();
			JLabel label = new JLabel(variable);
			label.setHorizontalAlignment(SwingConstants.LEFT);
			variablePanel.add(label);
		}
		mainPanel.add(variablePanel, constraints2);
	}

	private void createMethodPanels() {
		List<MethodObject> methods = classObject.getMethods();
		if (methods.isEmpty())
			return;
		JPanel methodPanel = new JPanel();
		methodPanel.setLayout(new BoxLayout(methodPanel, BoxLayout.Y_AXIS));
		methodPanel.setBorder(new LineBorder(Color.BLACK));
		GridBagConstraints constraints3 = new GridBagConstraints();
		constraints3.gridwidth = 1;
		constraints3.gridx = 0;
		constraints3.gridy = 2;
		constraints3.fill = GridBagConstraints.BOTH;
		for (MethodObject methodObject : methods) {
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());

			String method = methodObject.getName();
			List<MethodParameter> parameters = methodObject.getMethodParameters();
			method += "(";
			if (!parameters.isEmpty()) {
				for (int x = 0; x < parameters.size(); x++) {
					MethodParameter methodParameter = parameters.get(x);
					String parameter = methodParameter.getParameterName() + ":" + methodParameter.getParameterType();
					method += parameter;
					if (x != parameters.size() - 1)
						method += ", ";
				}
			}
			method += ")";
			JLabel label = new JLabel(method);
			label.setHorizontalAlignment(SwingConstants.LEFT);
			panel.setBorder(new EmptyBorder(2, 5, 2, 5));
			panel.add(label);
			methodPanel.add(panel);
		}
		mainPanel.add(methodPanel, constraints3);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
}
