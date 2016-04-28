/** GUI.java
 * 
 * Styles the interface :)
 * Most of this code is refereced from my previous GUI work in 240 class
 * as well as referencing the Oracle tutorials for Radio Buttons, Scroll Pane
 * and JTextArea.
 * 
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GUI extends JFrame implements ActionListener, TextAreaWrite {
	
	private int number;
	private MainDriver driver;

	//input panel
	private JPanel inputPane;	
	private JTextField txtField;
	private JButton bakeryButton;
	private JButton ALockButton;
	private JButton TTASButton;

	// output panel
	private JPanel contentPane;  
	protected JTextField textField;
	protected JTextArea textArea;

	//radio Button
	ButtonGroup group;
	static int twoString = 2;
	static int fourString = 4;
	static int eightString = 8;
	static int sixteenString = 16;

	/** GUI()
	 * 
	 * This is the constructor that sets up a new GUI
	 * 
	 */
	
	public GUI() {
		//System.out.println("Counter Increments display here to show that the threads are running");
		driver = new MainDriver();
		number = 2;
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 200);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Default font for the user interface
		Font defaultFont = new Font("Arial", Font.PLAIN, 12);

		//input Pane
		inputPane = new JPanel();		
		inputPane.setBounds(10, 32, 574, 160);		
		contentPane.add(inputPane);
		inputPane.setLayout(null);

		// labels for input fields
		JLabel lblThreadCount = new JLabel("Number of Threads :");
		lblThreadCount.setFont(defaultFont);
		lblThreadCount.setHorizontalAlignment(SwingConstants.RIGHT);
		lblThreadCount.setBounds(8, 11, 140, 14);
		inputPane.add(lblThreadCount);
		
		JLabel lblCounter = new JLabel("Each Threads Count :");
		lblCounter.setFont(defaultFont);
		lblCounter.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCounter.setBounds(8, 55, 140, 14);
		inputPane.add(lblCounter);
		
		//counter input field
		txtField = new JTextField();
		txtField.setFont(defaultFont);
		txtField.setText("5000");
		txtField.setHorizontalAlignment(SwingConstants.LEFT);
		txtField.setBounds(180,50,360,25);
		inputPane.add(txtField);

		// Add Lock Buttons
		bakeryButton = new JButton("Bakery Lock");
		bakeryButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//get thread number
				driver.setThreadCount(number);
				//set attempt number
				int attempts = Integer.parseInt(txtField.getText());
				driver.setAttempts(attempts);
				//start bakery Lock
				driver.runLock(new Bakery(number));
				
			}
		});
		bakeryButton.setFont(defaultFont);
		bakeryButton.setBounds(30, 100, 150, 28);
		inputPane.add(bakeryButton);

		ALockButton = new JButton("ALock");
		ALockButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//get thread number
				driver.setThreadCount(number);
				//set attempt number
				int attempts = Integer.parseInt(txtField.getText());
				driver.setAttempts(attempts);
				//start ALock
				driver.runLock(new ALock(number));
			}
		});
		ALockButton.setFont(defaultFont);
		ALockButton.setBounds(390, 100, 150, 28);
		inputPane.add(ALockButton);

		TTASButton = new JButton("TTAS Lock");
		TTASButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//get thread number
				driver.setThreadCount(number);
				//set attempt number
				int attempts = Integer.parseInt(txtField.getText());
				driver.setAttempts(attempts);
				//start TTAS Lock
				driver.runLock(new TTAS());
				
			}
		});
		TTASButton.setFont(defaultFont);
		TTASButton.setBounds(210, 100, 150, 28);
		inputPane.add(TTASButton);

		// output table
		textArea = new JTextArea("");
		textArea.setFont(new Font("Consolas", Font.PLAIN, 12));
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(10, 200, 570, 303);
		contentPane.add(scrollPane);

		//radio button
		JRadioButton twoButton = new JRadioButton(Integer.toString(twoString));
		twoButton.setMnemonic(KeyEvent.VK_B);
		twoButton.setActionCommand(Integer.toString(twoString));
		twoButton.addActionListener(this);
		twoButton.setSelected(true);

		JRadioButton fourButton = new JRadioButton(Integer.toString(fourString));
		fourButton.setMnemonic(KeyEvent.VK_C);
		fourButton.setActionCommand(Integer.toString(fourString));
		fourButton.addActionListener(this);

		JRadioButton eightButton = new JRadioButton(Integer.toString(eightString));
		eightButton.setMnemonic(KeyEvent.VK_D);
		eightButton.setActionCommand(Integer.toString(eightString));
		eightButton.addActionListener(this);

		JRadioButton sixteenButton = new JRadioButton(Integer.toString(sixteenString));
		sixteenButton.setMnemonic(KeyEvent.VK_R);
		sixteenButton.setActionCommand(Integer.toString(sixteenString));
		sixteenButton.addActionListener(this);

		//Group the radio buttons.
		group = new ButtonGroup();
		group.add(twoButton);
		group.add(fourButton);
		group.add(eightButton);
		group.add(sixteenButton);

		//Put the radio buttons in a column in a panel.
		JPanel radioPanel = new JPanel(new GridLayout(1, 0));
		radioPanel.add(twoButton);
		radioPanel.add(fourButton);
		radioPanel.add(eightButton);
		radioPanel.add(sixteenButton);

		add(radioPanel, BorderLayout.LINE_START);
		inputPane.add(radioPanel);
		radioPanel.setBounds(180,10,420,20);
		radioPanel.setVisible(true);
	}

	/** actionPerformed(ActionEvent arg0)
	 * 
	 * This method listens to the group of radio buttons
	 * to see which one was selected. when one is selected
	 * it sets the current value of number to the value of 
	 * the radio button that was selected.
	 * 
	 */
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		 ButtonModel b = group.getSelection();
	      String t = b.getActionCommand();
	      number = Integer.parseInt(t);
	}
	
	/** writeText(String text)
	 * 
	 * This method is used for when there needs to be something written 
	 * to the TextAread for displaying for the user. 
	 * 
	 */
	
	public void writeText(String text) {
		String txt = textArea.getText();
		txt = txt.length() < 1000 ? txt : txt.substring(txt.length() - 1000);
		textArea.setText(txt);		
		textArea.append("\n" + text);
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}
}