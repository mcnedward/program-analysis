package com.architecture_design.app.ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import com.architecture_design.app.classobject.ClassObject;
import com.architecture_design.app.parser.FileParser;
import com.architecture_design.app.shapes.ClassDiagram;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 18, 2015
 *
 */
public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	// Panels
	private DrawingPanel drawingPanel;
	private DrawingPanel filePanel;
	private DrawingPanel contentPanel;
	// Text Fields
	private JTextField txtFileLocation;
	// Labels
	private JLabel lblFileName;
	// Buttons
	private JButton btnBrowse;
	private JButton btnLoad;
	
	private FileParser fileParser;
	
	private String fileLocation;
	
	private ClassObject classObject;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		initialize();
		
		fileParser = new FileParser();
	}
	
	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
			System.out.println("Something went wrong when trying to use the System Look and Feel...");
		}
		setBounds(100, 100, 600, 500);
		drawingPanel = new DrawingPanel();
		drawingPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(drawingPanel);
		
		initializeComponents();
	}
	
	private void initializeComponents() {
		drawingPanel.setLayout(null);
		
		filePanel = new DrawingPanel();
		filePanel.setBounds(5, 5, 573, 23);
		drawingPanel.add(filePanel);
		filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.X_AXIS));
		
		JLabel lblFileLocation = new JLabel("File Location:");
		filePanel.add(lblFileLocation);
		
		txtFileLocation = new JTextField();
		filePanel.add(txtFileLocation);
		txtFileLocation.setColumns(10);
		
		btnBrowse = new JButton("Browse");
		filePanel.add(btnBrowse);
		
		btnLoad = new JButton("Load");
		filePanel.add(btnLoad);
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadAction();
			}
		});
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				browseAction();
			}
		});
		
		contentPanel = new DrawingPanel();
		contentPanel.setBounds(5, 27, 573, 429);
		drawingPanel.add(contentPanel);
		contentPanel.setLayout(null);
		
//		ClassDiagram classDiagram = new ClassDiagram(contentPanel, new ClassObject());
//		contentPanel.add(classDiagram);
		
		lblFileName = new JLabel("New label");
		lblFileName.setBounds(115, 5, 408, 14);
		contentPanel.add(lblFileName);
	}
	
	private void browseAction() {
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(MainWindow.this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			txtFileLocation.setText(selectedFile.getAbsolutePath());
			fileLocation = selectedFile.getAbsolutePath();
		}
	}
	
	private void loadAction() {
		fileLocation = txtFileLocation.getText();
		if (fileLocation == null || fileLocation == "") {
			lblFileName.setText("No file location has been set!");
			return;
		}
		List<String> file = readFile("resources/Account.java");
		fileParser.setFile(file);
		classObject = fileParser.createClassObject();
		lblFileName.setText(classObject.toString());
		
		contentPanel.addClassObject(classObject);
	}
	
	private static List<String> readFile(String fileLocation) {
		List<String> file = new ArrayList<String>();
		try {
			FileReader fr = new FileReader(fileLocation);
			BufferedReader reader = new BufferedReader(fr);
			String line = "";

			while ((line = reader.readLine()) != null) {
				file.add(line);
			}
			fr.close();
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Could not find the file located at: " + fileLocation + ".");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
}
