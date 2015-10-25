package com.architecture_design.app.ui.diagram;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

import com.architecture_design.app.classobject.BaseObject;
import com.architecture_design.app.ui.panel.ContentPanel;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 21, 2015
 *
 */
public abstract class BaseDiagram<T extends BaseObject> extends JScrollPane {
	private static final long serialVersionUID = 1L;

	protected ContentPanel parent;
	protected JLayeredPane mainPanel;
	protected T typeObject;

	protected int width, height;
	protected Font font;

	public BaseDiagram() {
		font = new Font("Segoe UI", Font.PLAIN, 12);
	}
	
	public BaseDiagram(ContentPanel parent, T typeObject, int fontSize) {
		font = new Font("Segoe UI", Font.PLAIN, fontSize);
		this.parent = parent;
		this.typeObject = typeObject;
		initialize();
	}

	public BaseDiagram(ContentPanel parent, T typeObject) {
		this();
		this.parent = parent;
		this.typeObject = typeObject;
		initialize();
	}

	protected abstract void setDimension();

	protected void initialize() {
		setDimension();
		int x = (parent.getDiagramPanel().getWidth() / 2) - (width / 2);
		int y = (parent.getDiagramPanel().getHeight() / 2) - (height / 2);
		setBounds(x, y, width, height);
		setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		createMainPanel();
		createHeaderPanel();
	}

	private void createMainPanel() {
		mainPanel = new JLayeredPane();
		setViewportView(mainPanel);

		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[] { 0 };
		layout.rowHeights = new int[] { 26, 0, 0, 0 };
		layout.columnWeights = new double[] { 1.0, 1.0, 1.0 };
		layout.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		mainPanel.setLayout(layout);
	}

	private void createHeaderPanel() {
		JPanel classNamePanel = new JPanel();
		classNamePanel.setBorder(new LineBorder(Color.BLACK));
		JLabel lblClassName = new JLabel(typeObject.getName());
		classNamePanel.add(lblClassName);
		GridBagConstraints constraints1 = new GridBagConstraints();
		constraints1.gridwidth = 1;
		constraints1.gridx = 0;
		constraints1.gridy = 0;
		constraints1.fill = GridBagConstraints.BOTH;
		mainPanel.add(classNamePanel, constraints1);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
}
