package view;

/**
 * In this class, the GUI is created and its functions initialized
 */

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
import javax.swing.JTextField;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JScrollBar;

public class Gui extends JFrame {

	
	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final JFileChooser openFileChooser;
	private JTextField txtNumberOfStates;
	private File fileWarehouse;
	private File fileOrder;
	private int algo;
	private JButton btnWarehouse;
	private JButton btnOrder;
	private JButton btnSubmitWarehouse;
	private JButton btnSubmitOrder;
	private JTextArea txtAreaSolution;
	private JLabel lblabelParam;
	private JOptionPane errPane;
	private JScrollPane jsp;
	private JRadioButton rButtonHC;
	private JRadioButton rButtonFCHC;
	private JRadioButton rButtonSA;
	private JRadioButton rButtonRR;
	private JRadioButton rButtonLBS;


	/**
	 * Creates the Frame
	 * 
	 */
	public Gui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 666, 472);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		openFileChooser = new JFileChooser();
		openFileChooser.setFileFilter(new FileNameExtensionFilter("TXT files", "txt"));
		
		JLabel TextLabelWelcome = new JLabel("Welcome to the IntelligentWareHouseApp!");
		TextLabelWelcome.setFont(new Font("Tahoma", Font.PLAIN, 18));
		TextLabelWelcome.setBounds(142, 10, 346, 24);
		contentPane.add(TextLabelWelcome);
		
		JLabel textLabelWarehouse = new JLabel("Please select a Warehouse:");
		textLabelWarehouse.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textLabelWarehouse.setBounds(10, 55, 176, 13);
		contentPane.add(textLabelWarehouse);
		
		JLabel textLabelOrder = new JLabel("Please select an Order:");
		textLabelOrder.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textLabelOrder.setBounds(10, 83, 157, 25);
		contentPane.add(textLabelOrder);
		
		JLabel textLabelAlgo = new JLabel("Please select an Algorithm:");
		textLabelAlgo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textLabelAlgo.setBounds(10, 139, 176, 19);
		contentPane.add(textLabelAlgo);
		
		rButtonHC = new JRadioButton("Hill-Climbing");
		rButtonHC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				algo = 0;
				txtNumberOfStates.setVisible(false);
				lblabelParam.setVisible(false);
				
			}
		});
		buttonGroup.add(rButtonHC);
		rButtonHC.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rButtonHC.setBounds(214, 138, 105, 21);
		contentPane.add(rButtonHC);
		
		rButtonFCHC = new JRadioButton("First Choice Hill Climbing");
		rButtonFCHC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				algo = 4;
				txtNumberOfStates.setVisible(false);
				lblabelParam.setVisible(false);
				
			}
		});
		buttonGroup.add(rButtonFCHC);
		rButtonFCHC.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rButtonFCHC.setBounds(214, 161, 194, 21);
		contentPane.add(rButtonFCHC);
		
		rButtonSA = new JRadioButton("Simulated Annealing");
		rButtonSA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				algo = 1;
				txtNumberOfStates.setVisible(false);
				lblabelParam.setVisible(false);
			}
		});
		buttonGroup.add(rButtonSA);
		rButtonSA.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rButtonSA.setBounds(214, 184, 157, 21);
		contentPane.add(rButtonSA);
		
		rButtonRR = new JRadioButton("Random Restart");
		rButtonRR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtNumberOfStates.setVisible(true);
				lblabelParam.setVisible(true);
				algo = 3;
			}
		});
		buttonGroup.add(rButtonRR);
		rButtonRR.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rButtonRR.setBounds(214, 207, 135, 21);
		contentPane.add(rButtonRR);
		
		rButtonLBS = new JRadioButton("Local Beam Search");
		rButtonLBS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtNumberOfStates.setVisible(true);
				lblabelParam.setVisible(true);
				algo = 2;
				
			}
		});
		buttonGroup.add(rButtonLBS);
		rButtonLBS.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rButtonLBS.setBounds(214, 230, 157, 21);
		contentPane.add(rButtonLBS);
		
		txtAreaSolution = new JTextArea();
		txtAreaSolution.setEditable(true);
		txtAreaSolution.setText("The Solution will appear here: ");
		txtAreaSolution.setBounds(10, 299, 618, 124);
		contentPane.add(txtAreaSolution);
		
		jsp = new JScrollPane (txtAreaSolution, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		jsp.setBounds(10, 299, 618, 124);
	    contentPane.add(jsp);
		
		btnWarehouse = new JButton("Choose File");
		btnWarehouse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int returnValue = openFileChooser.showOpenDialog(openFileChooser);
				
				if( returnValue == JFileChooser.APPROVE_OPTION ){
					fileWarehouse = openFileChooser.getSelectedFile();
				} 		
			}
		});
		btnWarehouse.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnWarehouse.setBounds(234, 53, 115, 21);
		contentPane.add(btnWarehouse);
		
		btnOrder = new JButton("Choose File");
		btnOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnValue = openFileChooser.showOpenDialog(openFileChooser);
				
				if (returnValue == JFileChooser.APPROVE_OPTION ){
					fileOrder = openFileChooser.getSelectedFile();
				} 
			}
		});
		btnOrder.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnOrder.setBounds(234, 87, 115, 21);
		contentPane.add(btnOrder);
		
		btnSubmitOrder = new JButton("Submit Order");
		btnSubmitOrder.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSubmitOrder.setBounds(234, 268, 137, 21);
		contentPane.add(btnSubmitOrder);
		
		txtNumberOfStates = new JTextField();
		txtNumberOfStates.setVisible(false);
		txtNumberOfStates.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtNumberOfStates.setBounds(462, 231, 55, 19);
		contentPane.add(txtNumberOfStates);
		txtNumberOfStates.setColumns(10);
		
		btnSubmitWarehouse = new JButton("Submit Warehouse");
		btnSubmitWarehouse.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSubmitWarehouse.setBounds(417, 53, 165, 21);
		contentPane.add(btnSubmitWarehouse);
		
		lblabelParam = new JLabel("Enter Number of States:");
		lblabelParam.setVisible(false);
		lblabelParam.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblabelParam.setBounds(417, 211, 157, 13);
		contentPane.add(lblabelParam);
		
		
	}
	//The following methods are the foundation of communication between GUI and Controller
	
	/**
	 * Puts the solution into the TextArea as a String
	 * @param solution is the solution given to the GUI by the Controller, containing the number of PSUs used including their identifiers and the items stored in them
	 */
	public void setSolution(String solution) {
		txtAreaSolution.setText(solution);
	}
	
	/**
	 * 
	 * @return returns the ID-Number of the algorithm chosen by the user
	 */
	public int getAlgo() {
		return algo;
	}
	
	/**
	 * 
	 * @return returns the File chosen by the user to be the designated warehouse
	 */
	public File getWarehouse() {
		return fileWarehouse;
	}
	
	/**
	 * 
	 * @return returns the File chosen by the user to be the designated Order
	 */
	public File getOrder() {
		return fileOrder;
	}
	
	/**
	 * 
	 * @return returns the NumberOfStates Parameter which is needed for Random Restart or Local Beam Search
	 * If no parameter is given because the user chose a different algorithm, the return is -1
	 */
	public int getParam() {
		int i = -1;
		try {
			i = Integer.parseInt(txtNumberOfStates.getText());
		}
		catch (NumberFormatException e) {
			i = -1;
		}
		return i;
	}
	
	/**
	 * 
	 * checks if Submit-Warehouse-Button has been activated by user for Controller to receive the Warehouse-File
	 */
	public void addWareHouseListener(ActionListener action) {
		btnSubmitWarehouse.addActionListener(action);
		
	}
	
	/**
	 * 
	 * checks if Submit-Order-Button has been activated by user for Controller to receive the Order-File
	 */
	public void addOrderListener(ActionListener action) {
		btnSubmitOrder.addActionListener(action);
		
	}
	
	/**
	 * 
	 * @param s is an ErrorMessage created by the Controller to be displayed in case of an error
	 */
	public void errorAlert(String errmsg){
		 errPane.showMessageDialog(null, errmsg);
	}
}
