
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
 
public class Biblio extends JPanel {
    private JTable table;

    private static String db = "jdbc:postgresql://localhost/postgres";
    private static String usr = "postgres";
    private static String pwd = "postgres";
    
    private static Bibliodb p;
 
    public Biblio() {

        String[] columnNames = {""};
 
        Object[][] data = {
        {""}
        };
 
        table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(600, 200));
        table.setFillsViewportHeight(true);
        table.setEnabled(false);

        JButton q1 = new JButton("Question 1");
        JButton q2 = new JButton("Question 2");
        JButton q3 = new JButton("Question 3");
        JButton q4 = new JButton("Question 4");
        
        JPanel buttons = new JPanel(new GridLayout(0, 1,0,10));

        JScrollPane scrollPane = new JScrollPane(table);
        
        q1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	
            	q1();
            }
        });
        
        q2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                q2();
            }
        });
        
        q3.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                q3();
            }
        });
        
        q4.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                q4();
            }
        });
        
        buttons.add(q1);
        buttons.add(q2);
        buttons.add(q3);
        buttons.add(q4);

        add(buttons);
        add(scrollPane);

    }
    
    private void q1() {
        	Object[] columnNames = p.getQ1Title();
        	Object[][] data = p.getQ1();
        	DefaultTableModel model1 = new DefaultTableModel(data, columnNames);
        
        	table.setModel(model1);
    }
 
    private void q2() {
    	Object[] columnNames = p.getQ2Title();
    	Object[][] data = p.getQ2();
    	DefaultTableModel model1 = new DefaultTableModel(data, columnNames);
    
    	table.setModel(model1);
    }
    
    private void q3() {
    	Object[] columnNames = p.getQ3Title();
    	Object[][] data = p.getQ3();
    	DefaultTableModel model1 = new DefaultTableModel(data, columnNames);
    
    	table.setModel(model1);
    }
    
    private void q4() {
    	Object[] columnNames = p.getQ4Title();
    	Object[][] data = p.getQ4();
    	DefaultTableModel model1 = new DefaultTableModel(data, columnNames);
    
    	table.setModel(model1);
    }
    
    private static void createAndShowGUI() {

        JFrame frame = new JFrame("Projet");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 

        Biblio newContentPane = new Biblio();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        frame.pack();
        frame.setVisible(true);
    }
 
    public static void main(String[] args) {

    	p =	new Bibliodb(db,usr,pwd);

		if(p.getConnection() != null) {
	        javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                createAndShowGUI();
	            }
	        });
		}
    }
}
