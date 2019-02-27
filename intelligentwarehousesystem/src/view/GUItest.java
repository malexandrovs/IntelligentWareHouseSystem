//most of this code is auto-generated using eclipse
// some things are in comments because they should work in theory but eclipse won't let me look at the GUI if they are there
//things that are still missing:
// - proper error-handling



package guitest;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JFileChooser;
//import javax.swing.fileChooser.FileNameExtensionFilter;

public class Guitest extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final JFileChooser openFileChooser;
	private fileWarehouse;
	private fileOrder;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					guitest frame = new guitest();
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
	public Guitest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 666, 472);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//openFileChooser = new JFileChooser();
		//openFileChooser.setFileFilter(new FileNameExtensionFilter("TXT files", "txt"));
		
		JLabel TextLabelWelcome = new JLabel("Welcome to the IntelligentWareHouseApp!");
		TextLabelWelcome.setFont(new Font("Tahoma", Font.PLAIN, 18));
		TextLabelWelcome.setBounds(142, 10, 346, 24);
		contentPane.add(TextLabelWelcome);
		
		JLabel textLabelWarehouse = new JLabel("Please select a Warehouse:");
		textLabelWarehouse.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textLabelWarehouse.setBounds(10, 55, 176, 13);
		contentPane.add(textLabelWarehouse);
		
		JLabel textLabelOrder = new JLabel("Please select an order:");
		textLabelOrder.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textLabelOrder.setBounds(10, 83, 157, 25);
		contentPane.add(textLabelOrder);
		
		JLabel textLabelAlgo = new JLabel("Please select an algorithm:");
		textLabelAlgo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textLabelAlgo.setBounds(10, 139, 176, 19);
		contentPane.add(textLabelAlgo);
		
		JRadioButton rButtonHC = new JRadioButton("Hill-Climbing");
		buttonGroup.add(rButtonHC);
		rButtonHC.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rButtonHC.setBounds(214, 138, 105, 21);
		contentPane.add(rButtonHC);
		
		JRadioButton rButtonFCHC = new JRadioButton("First Choice Hill Climbing");
		buttonGroup.add(rButtonFCHC);
		rButtonFCHC.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rButtonFCHC.setBounds(214, 161, 194, 21);
		contentPane.add(rButtonFCHC);
		
		JRadioButton rButtonSA = new JRadioButton("Simulated Annealing");
		buttonGroup.add(rButtonSA);
		rButtonSA.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rButtonSA.setBounds(214, 184, 157, 21);
		contentPane.add(rButtonSA);
		
		JRadioButton rButtonRR = new JRadioButton("Random Restart");
		rButtonRR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		buttonGroup.add(rButtonRR);
		rButtonRR.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rButtonRR.setBounds(214, 207, 135, 21);
		contentPane.add(rButtonRR);
		
		JRadioButton rButtonLBS = new JRadioButton("Local Beam Search");
		rButtonLBS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		buttonGroup.add(rButtonLBS);
		rButtonLBS.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rButtonLBS.setBounds(214, 230, 157, 21);
		contentPane.add(rButtonLBS);
		
		JTextArea txtAreaSolution = new JTextArea();
		txtAreaSolution.setText("The Solution will appear here:");
		txtAreaSolution.setBounds(10, 299, 601, 134);
		contentPane.add(txtAreaSolution);
		
		JButton btnWarehouse = new JButton("Choose File");
		btnWarehouse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			//	int returnValue = openFileChooser.showOpenDialog(this);
				
			//	if returnValue == JFileChooser.APPROVE_OPTION {
					//fileWarehouse = openFileChooser.getSelectedFile();
					
			//	} else {
					
			//	} 
						
			}
		});
		btnWarehouse.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnWarehouse.setBounds(234, 53, 115, 21);
		contentPane.add(btnWarehouse);
		
		JButton btnOrder = new JButton("Choose File");
		btnOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//int returnValue = openFileChooser.showOpenDialog(this);
				
			//	if returnValue == JFileChooser.APPROVE_OPTION {
					//fileOrder = openFileChooser.getSelectedFile();
			//	} else {
					
			//	}
			}
		});
		btnOrder.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnOrder.setBounds(234, 87, 115, 21);
		contentPane.add(btnOrder);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSubmit.setBounds(234, 268, 115, 21);
		contentPane.add(btnSubmit);
	}
	
	
	
	public void printsolution(String solution) {
		txtAreaSolution.setText(solution);
	}
	
	
}
