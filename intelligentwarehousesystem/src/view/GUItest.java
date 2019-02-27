//most of this code is auto-generated using eclipse
// i will proceed to mark the stuff that isn't auto-generated with comments for your viewing pleasure
//things that are still missing:
// - proper error-handling
// - an idea how to send all the data previously collected through buttons n' stuff when pressing the single submit button
//(tho im currently trying to figure that out)


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
import javax.swing.fileChooser.FileNameExtensionFilter;

public class guitest extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final javax.swing.JFileChooser openFileChooser;
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
	public guitest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 666, 472);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
    //added the filechooser manuelly to pop up when a certain button is activated, see below
		openFileChooser = new JFileChooser();
		openFileChooser.setFileFilter(new FileNameExtensionFilter("TXT files", "txt"));
		
		JLabel TextLabelWelcome = new JLabel("Welcome to the IntelligentWareHouseApp!");
		TextLabelWelcome.setFont(new Font("Tahoma", Font.PLAIN, 18));
		TextLabelWelcome.setBounds(142, 10, 346, 24);
		contentPane.add(TextLabelWelcome);
		
		JLabel TextLabelWarehouse = new JLabel("Please select a Warehouse:");
		TextLabelWarehouse.setFont(new Font("Tahoma", Font.PLAIN, 14));
		TextLabelWarehouse.setBounds(10, 55, 176, 13);
		contentPane.add(TextLabelWarehouse);
		
		JLabel TextLabelOrder = new JLabel("Please select an order:");
		TextLabelOrder.setFont(new Font("Tahoma", Font.PLAIN, 14));
		TextLabelOrder.setBounds(10, 83, 157, 25);
		contentPane.add(TextLabelOrder);
		
		JLabel TextLabelAlgo = new JLabel("Please select an algorithm:");
		TextLabelAlgo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		TextLabelAlgo.setBounds(10, 139, 176, 19);
		contentPane.add(TextLabelAlgo);
		
		JRadioButton RButtonHC = new JRadioButton("Hill-Climbing");
		buttonGroup.add(RButtonHC);
		RButtonHC.setFont(new Font("Tahoma", Font.PLAIN, 14));
		RButtonHC.setBounds(214, 138, 105, 21);
		contentPane.add(RButtonHC);
		
		JRadioButton RButtonFCHC = new JRadioButton("First Choice Hill Climbing");
		buttonGroup.add(RButtonFCHC);
		RButtonFCHC.setFont(new Font("Tahoma", Font.PLAIN, 14));
		RButtonFCHC.setBounds(214, 161, 194, 21);
		contentPane.add(RButtonFCHC);
		
		JRadioButton RButtonSA = new JRadioButton("Simulated Annealing");
		buttonGroup.add(RButtonSA);
		RButtonSA.setFont(new Font("Tahoma", Font.PLAIN, 14));
		RButtonSA.setBounds(214, 184, 157, 21);
		contentPane.add(RButtonSA);
		
		JRadioButton RButtonRR = new JRadioButton("Random Restart");
		RButtonRR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		buttonGroup.add(RButtonRR);
		RButtonRR.setFont(new Font("Tahoma", Font.PLAIN, 14));
		RButtonRR.setBounds(214, 207, 135, 21);
		contentPane.add(RButtonRR);
		
		JRadioButton RButtonLBS = new JRadioButton("Local Beam Search");
		RButtonLBS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		buttonGroup.add(RButtonLBS);
		RButtonLBS.setFont(new Font("Tahoma", Font.PLAIN, 14));
		RButtonLBS.setBounds(214, 230, 157, 21);
		contentPane.add(RButtonLBS);
		
		JTextArea TxtAreaSolution = new JTextArea();
		TxtAreaSolution.setText("The Solution will appear here:");
		TxtAreaSolution.setBounds(10, 299, 601, 134);
		contentPane.add(TxtAreaSolution);
		
		JButton btnWarehouse = new JButton("Choose File");
		btnWarehouse.addActionListener(new ActionListener() {
    
    //open filechooser to get a warehouse file
			public void actionPerformed(ActionEvent e) {
				
				int returnValue = openFileChooser.showOpenDialog(this);
				
				if returnValue == JFileChooser.APPROVE_OPTION {
					//they have selected a file, which means I need to save that file
					//and later on send it to the cotroller using submit?
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
      
      //open filechooser to get an order file
				int returnValue = openFileChooser.showOpenDialog(this);
				
				if returnValue == JFileChooser.APPROVE_OPTION {
					//they have selected a file, which means I need to save that file
					//and later on send it to the cotroller using submit?
				} else {
					
				}
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
	
	
	//i recieve the solution string and display it on my textarea
	public void printsolution(String solution) {
		TxtAreaSolution.setText(solution);
	}
	
	
}
