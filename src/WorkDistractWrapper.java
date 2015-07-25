import javax.swing.*;

public class WorkDistractWrapper {
	private static WorkDistract wd = new WorkDistract(); 
	
	public static void main(String[] args){
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
			    wd.createGUI();
			}
		    });
	}
}
