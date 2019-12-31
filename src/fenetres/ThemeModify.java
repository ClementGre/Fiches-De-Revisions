package fenetres;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import javax.swing.JPanel;
import fr.themsou.main.main;

@SuppressWarnings("serial")
public class ThemeModify extends JPanel {
	
public static void close(){
		
		main.ThemeModify.dispose();
	}
	
	public void paintComponent(Graphics g){
		
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 1113, 625);
		g.setColor(Color.BLACK);
		
		centerString(g, 0, 1113, 0, 50, "Paramètres du thème pour la Matière " + main.OpenMatiere, new Font("Ubuntu", 1, 18));
		
		
		centerString(g, 410, 510, 70, 100, "Police", new Font("Ubuntu", 1, 10));
		centerString(g, 520, 570, 70, 100, "Taille", new Font("Ubuntu", 1, 10));
		centerString(g, 580, 630, 70, 100, "Alignement", new Font("Ubuntu", 1, 10));
		centerString(g, 640, 705, 70, 100, "Couleur", new Font("Ubuntu", 1, 10));
		
		
		centerString(g, 410, 460, 140, 170, "Taille", new Font("Ubuntu", 1, 10));
		
		centerString(g, 470, 545, 140, 155, "Couleur du", new Font("Ubuntu", 1, 10));
		centerString(g, 470, 545, 155, 170, "premier mot", new Font("Ubuntu", 1, 10));
		centerString(g, 555, 630, 140, 170, "Couleur", new Font("Ubuntu", 1, 10));
		
		centerString(g, 640, 690, 140, 170, "Alignement", new Font("Ubuntu", 1, 10));
		centerString(g, 700, 775, 140, 170, "Aligner au", new Font("Ubuntu", 1, 10));
		
		try{
			File file = new File(main.SaveLocation + "Fiches/" + main.OpenMatiere + "/theme.yml");
			if(!file.exists()){
				file.createNewFile();
				
				FileWriter writer = new FileWriter(file);
				BufferedWriter out = new BufferedWriter(writer);
				out.write("LAto;4;0;0");
				out.newLine();
				out.write("1;1;1);3;1;1;0;0;true;true");
				out.newLine();
				out.close();
				
				
			}
			
			BufferedReader in;
			if(System.getProperty("os.name").equals("Linux") || System.getProperty("os.name").equals("Mac OS X")){
				in = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
			}else{
				in = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.ISO_8859_1));
			}
			in.readLine();
			String line = in.readLine();
			int i = 170;
			while(line != null){
				
				centerString(g, 5, 40, i, i + 20, "Si", new Font("Ubuntu", 1, 15));
				centerString(g, 345, 410, i, i + 20, "Alors", new Font("Ubuntu", 1, 15));
						
					
				line = in.readLine();
				i = i + 30;
				
			}
			
			setPreferredSize(new Dimension(500, i + 100));
			
		}catch(IOException e1){ e1.printStackTrace(); }
		
		
		
		
		
	}
	
	public void centerString(Graphics g, int minX, int maxX, int minY, int maxY, String s, Font font) {
		
		
	    FontRenderContext frc = new FontRenderContext(null, true, true);

	    Rectangle2D r2D = font.getStringBounds(s, frc);
	    int rWidth = (int) Math.round(r2D.getWidth());
	    int rHeight = (int) Math.round(r2D.getHeight());
	    int rX = (int) Math.round(r2D.getX());
	    int rY = (int) Math.round(r2D.getY());

	    int a = ((maxX - minX) / 2) - (rWidth / 2) - rX;
	    int b = ((maxY - minY) / 2) - (rHeight / 2) - rY;

	    g.setFont(font);
	    g.drawString(s, minX + a, minY + b);
	}
}
