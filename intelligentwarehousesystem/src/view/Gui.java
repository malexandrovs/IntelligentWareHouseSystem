package view;

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

public class Gui extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final JFileChooser openFileChooser;
	private JTextField txtNumberOfStates;
	private File fileWarehouse;
	private File fileOrder;
	private int algo;
	private JButton btnSubmitWarehouse;
	private JButton btnSubmitOrder;
	private JTextArea txtAreaSolution;
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Gui frame = new Gui();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
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
		
		JLabel textLabelOrder = new JLabel("Please select an order:");
		textLabelOrder.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textLabelOrder.setBounds(10, 83, 157, 25);
		contentPane.add(textLabelOrder);
		
		JLabel textLabelAlgo = new JLabel("Please select an algorithm:");
		textLabelAlgo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textLabelAlgo.setBounds(10, 139, 176, 19);
		contentPane.add(textLabelAlgo);
		
		JRadioButton rButtonHC = new JRadioButton("Hill-Climbing");
		rButtonHC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				algo = 0;
				txtNumberOfStates.setVisible(false);
				
			}
		});
		buttonGroup.add(rButtonHC);
		rButtonHC.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rButtonHC.setBounds(214, 138, 105, 21);
		contentPane.add(rButtonHC);
		
		JRadioButton rButtonFCHC = new JRadioButton("First Choice Hill Climbing");
		rButtonFCHC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				algo = 1;
				txtNumberOfStates.setVisible(false);
				
			}
		});
		buttonGroup.add(rButtonFCHC);
		rButtonFCHC.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rButtonFCHC.setBounds(214, 161, 194, 21);
		contentPane.add(rButtonFCHC);
		
		JRadioButton rButtonSA = new JRadioButton("Simulated Annealing");
		rButtonSA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				algo = 2;
				txtNumberOfStates.setVisible(false);
			}
		});
		buttonGroup.add(rButtonSA);
		rButtonSA.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rButtonSA.setBounds(214, 184, 157, 21);
		contentPane.add(rButtonSA);
		
		JRadioButton rButtonRR = new JRadioButton("Random Restart");
		rButtonRR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtNumberOfStates.setVisible(true);
				algo = 3;
			}
		});
		buttonGroup.add(rButtonRR);
		rButtonRR.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rButtonRR.setBounds(214, 207, 135, 21);
		contentPane.add(rButtonRR);
		
		JRadioButton rButtonLBS = new JRadioButton("Local Beam Search");
		rButtonLBS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtNumberOfStates.setVisible(true);
				algo = 4;
				
			}
		});
		buttonGroup.add(rButtonLBS);
		rButtonLBS.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rButtonLBS.setBounds(214, 230, 157, 21);
		contentPane.add(rButtonLBS);
		
		txtAreaSolution = new JTextArea();
		txtAreaSolution.setText("The Solution will appear here:");
		txtAreaSolution.setBounds(10, 299, 601, 134);
		contentPane.add(txtAreaSolution);
		
		JButton btnWarehouse = new JButton("Choose File");
		btnWarehouse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int returnValue = openFileChooser.showOpenDialog(openFileChooser);
				
				if( returnValue == JFileChooser.APPROVE_OPTION ){
					fileWarehouse = openFileChooser.getSelectedFile();
					
				} else {
					
				} 
						
			}
		});
		btnWarehouse.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnWarehouse.setBounds(234, 53, 115, 21);
		contentPane.add(btnWarehouse);
		
		JButton btnOrder = new JButton("Choose File");
		btnOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnValue = openFileChooser.showOpenDialog(openFileChooser);
				
				if (returnValue == JFileChooser.APPROVE_OPTION ){
					fileOrder = openFileChooser.getSelectedFile();
				} else {
					
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
		txtNumberOfStates.setText("Enter Number of States");
		txtNumberOfStates.setBounds(417, 220, 172, 19);
		contentPane.add(txtNumberOfStates);
		txtNumberOfStates.setColumns(10);
		
		btnSubmitWarehouse = new JButton("Submit Warehouse");
		btnSubmitWarehouse.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSubmitWarehouse.setBounds(417, 53, 165, 21);
		contentPane.add(btnSubmitWarehouse);
		
		
	}
	
	
	
	public void setSolution(String solution) {
		txtAreaSolution.setText(solution);
	}
	
	public int getAlgo() {
		return algo;
	}
	
	public File getWarehouse() {
		return fileWarehouse;
	}
	
	public File getOrder() {
		return fileOrder;
	}
	
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
	
	public void addWareHouseListener(ActionListener action) {
		btnSubmitWarehouse.addActionListener(action);
		
	}
	
	public void addOrderListener(ActionListener action) {
		btnSubmitOrder.addActionListener(action);
		
	}
	
	public void errorAlert(String s){
		   JOptionPane.showMessageDialog(null, s);
	}
	
	
	
}
