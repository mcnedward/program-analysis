package com.architecture_design.app.ui.diagram;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.architecture_design.app.classobject.method.MethodObject;
import com.architecture_design.app.ui.MainWindow;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 29, 2015
 *
 */
public class FlowGraphFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private MethodObject methodObject;

	private JPanel mainPanel;
	private JPanel flowGraphPanel;
	protected JLabel lblHeader;

	private IfBuilder ifBuilder;

	public FlowGraphFrame(MethodObject methodObject) {
		this.methodObject = methodObject;
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
			System.out.println("Something went wrong when trying to use the System Look and Feel...");
		}
		setBounds(100, 100, MainWindow.WIDTH / 2, MainWindow.HEIGHT);
		
		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainPanel);
		createHeaderPanel();
		updateHeaderPanel("If-Green | For&ForEach-Blue | While-Yellow | Switch-Red");
		
		ifBuilder = new IfBuilder(methodObject);
		
		createFlowGraphDiagram();
	}

	private void createHeaderPanel() {
		mainPanel.setLayout(new BorderLayout(0, 0));
		JPanel headerPanel = new JPanel();
		headerPanel.setBorder(new LineBorder(Color.BLACK));

		lblHeader = new JLabel(methodObject.getName());
		headerPanel.add(lblHeader);

		GridBagConstraints constraints1 = new GridBagConstraints();
		constraints1.gridwidth = 1;
		constraints1.gridx = 0;
		constraints1.gridy = 0;
		constraints1.fill = GridBagConstraints.BOTH;

		mainPanel.add(headerPanel, BorderLayout.NORTH);
	}

	protected void updateHeaderPanel(String text) {
		lblHeader.setText(text);
	}

	private void createFlowGraphDiagram() {
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

		ifBuilder.build(container);
		
		flowGraphPanel.add(container);

		mainPanel.add(flowGraphPanel);
	}
}
