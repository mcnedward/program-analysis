package com.architecture_design.app.ui.diagram;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import com.architecture_design.app.classobject.ClassObject;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 23, 2015
 *
 */
public class MetricsDiagram extends JPanel {
	private static final long serialVersionUID = -523408531976611140L;

	private JPanel metricContent;
	
	private ClassObject classObject;

	public MetricsDiagram() {
	}

	public MetricsDiagram(ClassObject classObject) {
		this.classObject = classObject;
		initialize();

		updateWMCPanel();
		updateDITPanel();
	}

	private void initialize() {
		setLayout(new BorderLayout(0, 0));

		JPanel headerPanel = new JPanel();
		add(headerPanel, BorderLayout.NORTH);
		headerPanel.add(new JLabel("Metrics"));
		headerPanel.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
		
		metricContent = new JPanel();
		add(metricContent, BorderLayout.CENTER);
		metricContent.setLayout(new BoxLayout(metricContent, BoxLayout.Y_AXIS));
	}

	private void updateWMCPanel() {
		JPanel wmcPanel = new JPanel();

		int wmc = classObject.getMethods().size();
		JLabel label = new JLabel("WMC = " + wmc);
		label.setBounds(0, 0, wmcPanel.getWidth(), 30);
		label.setHorizontalAlignment(SwingConstants.LEFT);
		wmcPanel.add(label);
		
		metricContent.add(wmcPanel);
	}

	private void updateDITPanel() {
		JPanel ditPanel = new JPanel();

		int dit = classObject.getExtendsList().size();
		JLabel label = new JLabel("DIT = " + dit);
		label.setBounds(0, 0, ditPanel.getWidth(), 30);
		label.setHorizontalAlignment(SwingConstants.LEFT);
		ditPanel.add(label);
		
		metricContent.add(ditPanel);
	}

}
