package fr.themsou.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Run {
	
	public static void run(){
		
		
		
		while(true){
			
			
			
			try {Thread.sleep(25);} catch (InterruptedException e) {e.printStackTrace();}
			
			if(main.reload > 0){
				main.reload --;
				
			}else if(main.reload == 0){
				main.reload --;
				main.fenetre.setSize(1301, 751);
				main.panel.repaint();
			}
			
			
			if(main.CodeTextArea.getText() != main.CodeText){
				main.CodeText = main.CodeTextArea.getText();
				main.presult.repaint();
			}
			
			try {Thread.sleep(25);} catch (InterruptedException e) {e.printStackTrace();}
			
			

			if(main.load){
				try{
					File file = new File(main.SaveLocation + "Fiches/" + main.OpenMatiere + "/theme.yml");
					
					if(!file.exists()){
						file.createNewFile();
						
					}
					
					FileWriter writer = new FileWriter(file);
					BufferedWriter out = new BufferedWriter(writer);
				
					
					out.write(main.Police.getText() + ";" + main.Size.getSelectedIndex() + ";" + main.Align.getSelectedIndex() + ";" + main.Colortext.getSelectedIndex());
					out.newLine();
					
					for(int i = 0; i < main.getters.size(); i++){
						
						out.write(main.getters.get(i).getSelectedIndex() + ";"
						+ main.comparators.get(i).getSelectedIndex() + ";"
						+ main.comparatorTxt.get(i).getText() + ";"
						+ main.Sizes.get(i).getSelectedIndex() + ";"
						+ main.Colors1.get(i).getSelectedIndex() + ";"
						+ main.Colors2.get(i).getSelectedIndex() + ";"
						+ main.Esps1.get(i).getSelectedIndex() + ";"
						+ main.Esps2.get(i).getSelectedIndex() + ";"
						+ main.Bolds.get(i).isSelected() + ";"
						+ main.Underlines.get(i).isSelected());
						out.newLine();
					}
				
					out.close();
					
				}catch(IOException e1){ e1.printStackTrace(); }
			}
			
			
		}
	}

}
