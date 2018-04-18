
package falstad;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
public class SelectionPanel {
	public JPanel panel1;
    public JButton start;
    public JComboBox box1;
    public JComboBox box2;
    public JComboBox box3;
	
	public SelectionPanel(){
	     panel1 = new JPanel(new GridLayout(2, 2));
	     String[] driver = { "Wizard", "Wall Follower", "Pledge", "Explorer", "Manual Driver" };
         box1 = new JComboBox(driver);
         panel1.add(box1);
         String[] algorithm = {"DFS", "Prim", "Eller"};
         box2 = new JComboBox(algorithm);
         panel1.add(box2);
         String[] skillLevel = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
         box3 = new JComboBox(skillLevel);
         panel1.add(box3);
         start = new JButton("Start");
         panel1.add(start, null);
         
         
	 }
			 
		


	

}
