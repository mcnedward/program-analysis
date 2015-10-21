package com.architecture_design.app.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import com.architecture_design.app.classobject.ClassObject;
import com.architecture_design.app.classobject.MethodObject;
import com.architecture_design.app.shapes.ClassDiagram;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 20, 2015
 *
 */
public class ContentPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JPanel parent;
	private JPanel diagramPanel;
	private JPanel methodPanel;
	private ClassDiagram classDiagram;
	private MethodDiagram methodDiagram;

	public ContentPanel() {
	}

	public ContentPanel(JPanel parent) {
		this();
		this.parent = parent;

		initialize();
	}

	private void initialize() {
		setBounds(parent.getX(), parent.getY(), parent.getWidth(), parent.getHeight());

		GridBagLayout g = new GridBagLayout();
		g.columnWidths = new int[] { parent.getWidth() / 2 - 1, parent.getWidth() / 2 - 1 };
		g.rowHeights = new int[] { parent.getHeight() - 2 };
		g.columnWeights = new double[] { 1.0, 1.0 };
		g.rowWeights = new double[] { 1.0 };
		setLayout(g);
		
		addDiagramPanel();
		addMethodPanel();
	}

	public void addClassObject(ClassObject classObject) {
		if (classDiagram != null) {
			System.out.println("Removing class diagram for: " + classObject.getName());
			diagramPanel.remove(classDiagram);
		}
		
		classDiagram = new ClassDiagram(this, classObject);
		diagramPanel.add(classDiagram);
		
		revalidate();
		repaint();
		System.out.println("Loading class diagram for: " + classObject.getName());
	}
	
	public void showMethod(MethodObject methodObject) {
		if (methodDiagram != null) {
			System.out.println("Removing method diagram for: " + methodObject.getName());
			methodPanel.remove(methodDiagram);
		}
		methodDiagram = new MethodDiagram(this, methodObject);
		methodPanel.add(methodDiagram);
		
		revalidate();
		repaint();
		System.out.println("Loading method diagram for: " + methodObject.getName());
	}

	private void addDiagramPanel() {
		diagramPanel = new JPanel();
		setPanelBounds(diagramPanel);
		diagramPanel.setLayout(null);	// Set to AbsoluteLayout
		
		GridBagConstraints g = new GridBagConstraints();
		g.gridheight = 1;
		g.gridwidth = 1;
		g.gridx = 0;
		g.gridy = 0;
		g.fill = GridBagConstraints.BOTH;
		
		add(diagramPanel, g);
	}
	
	private void addMethodPanel() {
		methodPanel = new JPanel();
		setPanelBounds(methodPanel);
		methodPanel.setLayout(null);	// Set to AbsoluteLayout
		
		GridBagConstraints g = new GridBagConstraints();
		g.gridheight = 1;
		g.gridwidth = 1;
		g.gridx = 1;
		g.gridy = 0;
		g.fill = GridBagConstraints.BOTH;
		
		add(methodPanel, g);
	}
	
	private void setPanelBounds(JPanel panel) {
		int width = (getWidth() / 2);
		int height = getHeight();
		int x = (width / 2) - (width / 4);
		int y = (height / 2) - (height / 4);
		panel.setBounds(x, y, width, height);
	}

	/**
	 * @return the diagramPanel
	 */
	public JPanel getDiagramPanel() {
		return diagramPanel;
	}

	/**
	 * @param diagramPanel the diagramPanel to set
	 */
	public void setDiagramPanel(JPanel diagramPanel) {
		this.diagramPanel = diagramPanel;
	}

	/**
	 * @return the methodPanel
	 */
	public JPanel getMethodPanel() {
		return methodPanel;
	}

	/**
	 * @param methodPanel the methodPanel to set
	 */
	public void setMethodPanel(JPanel methodPanel) {
		this.methodPanel = methodPanel;
	}

	/**
	 * @return the classDiagram
	 */
	public ClassDiagram getClassDiagram() {
		return classDiagram;
	}

	/**
	 * @param classDiagram the classDiagram to set
	 */
	public void setClassDiagram(ClassDiagram classDiagram) {
		this.classDiagram = classDiagram;
	}
	
}
