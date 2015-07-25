import java.awt.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*; 

import javax.swing.*;

public class WorkDistract{

	private static String name; 
	private static LinkedList<WorkDistractEntry> wdentries = new LinkedList<WorkDistractEntry>(); 
	private static long start = 0 ; 
	private static long end; 
	private static String word; 
	private static Container mainPane; 
	private static JPanel bottomPanel; 
	private static JTextField nameField; 
	private static JLabel lastTime = new JLabel(); 
	private static JLabel current = new JLabel(); 
	
	
	/**
	 * creates the GUI for the app. 
	 */
	public static void createGUI(){
		//names it
		JFrame frame = new JFrame("Work or Distract"); 
		mainPane = frame.getContentPane(); 
		createComponents(mainPane); 
		
		//set to specific size
		frame.setSize(315, 175); 
		frame.setVisible(true);
	}
	
	/**
	 * creates the look 
	 * @param pane
	 */
	public static void createComponents(Container pane){
		mainPane = pane; 
		//1 row, 2 columns for the entire thing
		pane.setLayout(new GridLayout(1,1)); //was 2,1
		
		//The left panel has two things in it: the label and the place a user can write what they were doing.
		JPanel leftPanel = new JPanel(new GridLayout(2,0)); 
		JLabel label= new JLabel("Name of Activity: "); 
		nameField = new JTextField(); 
	
		//adds to panel.
		leftPanel.add(label);
		leftPanel.add(nameField); 
		
		 
		//Panel of the two buttons on the right. 
		JPanel rightPanel = new JPanel(new GridLayout(3,1)); 		
		JButton workButton = new JButton("Work"); 
		JButton distractButton = new JButton("Distract");
		JButton exitButton = new JButton("Exit"); 
		//adds to panel
		rightPanel.add(workButton); 
		rightPanel.add(distractButton); 
		rightPanel.add(exitButton); 
		
		bottomPanel = new JPanel(new GridLayout(1,1));  
		//adds the two created panels to the main window. 
		pane.add(leftPanel,BorderLayout.WEST);
		pane.add(rightPanel, BorderLayout.EAST);
		pane.add(bottomPanel); 
		
		/////////////////////////////////////////
		//Gets and give the information needed. 
		//adds listeners to the three buttons.
		workButton.addMouseListener(new WDMouseListener(1)); 
		distractButton.addMouseListener(new WDMouseListener(2));
		exitButton.addMouseListener(new WDMouseListener(3));
	}
	
	private static class WDMouseListener extends MouseAdapter {
		
		private int toggle; 
		
		public void timer(){
			
		}
		
		public WDMouseListener(int toggle){
			this.toggle = toggle; 
		}
		public void mouseClicked(MouseEvent e) {
			//work related
		
			if(toggle == 1){
				
				end = System.currentTimeMillis()-start;
				//minutes
				float endMin = end/(60*1000F);
					
				WorkDistractEntry entry = new WorkDistractEntry(word, name, (double)endMin); 
								
				//add number to java app 
				bottomPanel.remove(lastTime); 
				
				bottomPanel.add(lastTime, BorderLayout.CENTER);
				
				while(wdentries.size()>0){
					lastTime = new JLabel((entry.minutes())); 
					bottomPanel.add(lastTime, BorderLayout.CENTER);
				}
				mainPane.validate(); 
				mainPane.repaint(); 
				wdentries.add(entry); 				
			
				name = nameField.getText(); 
				nameField.setText(""); 
				word = "work"; 
				start = System.currentTimeMillis(); 		
				
			}
			
			//distract related
			else if(toggle == 2){
				
				end = System.currentTimeMillis()-start;
				float endMin = end/(60*1000F);
				
			
				//gets the name after
				WorkDistractEntry entry = new WorkDistractEntry(word, name, endMin); 
				
				bottomPanel.remove(lastTime); 
				//nameField.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				
				//nameField.setCaretPosition(1);
				//nameField.getCaret().setVisible(true);
				if(wdentries.size()>0){
			    	lastTime = new JLabel(entry.minutes()); 
					bottomPanel.add(lastTime, BorderLayout.CENTER);
				}				
				mainPane.validate(); 
				mainPane.repaint(); 

				wdentries.add(entry); 		
			
				name = nameField.getText(); 
				nameField.setText(""); 
				word = "distract"; 

				start = System.currentTimeMillis(); 
			}
			//End  NEED TO HAVE AN OR EXIT WINDOW WITH X. (tried, but didn't work, may try another time. 
			else if(toggle == 3){
				end = System.currentTimeMillis()-start;
				float endMin = end/(60*1000F);

				WorkDistractEntry entry = new WorkDistractEntry(word, name, endMin); 
				wdentries.add(entry); 
				
				java.io.FileWriter fw = null;
				try {
					fw = new java.io.FileWriter( "efficiency.txt", true );
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        java.io.PrintWriter pw = new java.io.PrintWriter( fw, true );
		        //get date  
		        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		        Date d = new Date(); 	
		        pw.print(dateFormat.format(d));
		        pw.print("\n"); 
		        for(int i = 1; i < wdentries.size(); i++){
			        pw.print( wdentries.get(i).toPrint());
				}
		        pw.close();
				System.exit(0); 
				
			}
		}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
	}
}
