package fr.themsou.panel;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PFichiers  extends JPanel{
	
	public void paintComponent(Graphics g){
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 200, 200);
		
	}

}
