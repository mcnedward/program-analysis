package com.architecture_design.app.shapes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

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
import com.architecture_design.app.ui.DrawingPanel;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 18, 2015
 *
 */
public class ClassDiagram extends JScrollPane {
	private static final long serialVersionUID = -523408531976611140L;

	private DrawingPanel parent;
	private JPanel mainPanel;
	private ClassObject classObject;

	public ClassDiagram() {

	}

	private static int WIDTH = 200;
	private static int HEIGHT = 300;

	public ClassDiagram(DrawingPanel parent, ClassObject classObject) {
		this.parent = parent;
		this.classObject = classObject;

		initialize();
	}

	private void initialize() {
		setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		int x = (parent.getWidth() / 2) - WIDTH / 2;
		int y = (parent.getHeight() / 2) - HEIGHT / 2;
		setBounds(x, y, WIDTH, HEIGHT);

		JPanel mainPanel = createMainPanel();
		createHeaderPanel(mainPanel);
		createMethodPanels(mainPanel);
	}

	private JPanel createMainPanel() {
		JPanel mainPanel = new JPanel();
		
		int x = (parent.getWidth() / 2) - (WIDTH / 2);
		int y = (parent.getHeight() / 2) - (HEIGHT / 2);
		mainPanel.setBounds(x, y, WIDTH, HEIGHT);
		setViewportView(mainPanel);
		mainPanel.setLayout(new BorderLayout(0, 0));
		
		return mainPanel;
	}
	
	private JPanel createHeaderPanel(JPanel mainPanel) {
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.BLACK));
		mainPanel.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel label = new JLabel(classObject.getClassName());
		label.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(label);
		return panel;
	}

	private void createMethodPanels(JPanel mainPanel) {
		JPanel panel = new JPanel();
		mainPanel.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		for (MethodObject methodObject : classObject.getMethods()) {
			JPanel p = new JPanel();
			p.setLayout(new BorderLayout());
			JLabel label = new JLabel(methodObject.getName() + "()");
			label.setHorizontalAlignment(SwingConstants.LEFT);
			p.setBorder(new EmptyBorder(2, 5, 2, 5));
			p.add(label);
			panel.add(p);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
}
