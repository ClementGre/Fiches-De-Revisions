package fr.themsou.devices;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import fr.themsou.main.main;

public class themes {

	public static void setexecute(){
		
		
		
		main.add.addActionListener(new ActionListener(){ @SuppressWarnings({"resource"})
		@Override public void actionPerformed(ActionEvent e){
			try{
				
				File file = new File(main.SaveLocation + "Fiches/" + main.OpenMatiere + "/theme.yml");
				
				BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
				String[] lines = new String[100];
				String line = in.readLine();
				int i = 0;
				while(line != null){
					lines[i] = line;
					line = in.readLine();
					i++;
				}
				
				FileWriter writer = new FileWriter(file);
				BufferedWriter out = new BufferedWriter(writer);
				if(lines != null){
					for(String Wline : lines){
						if(Wline != null){
							out.write(Wline);
							out.newLine();
						}
					}
				}
				out.write("0;0;;4;0;0;0;0;false;false");
				out.newLine();
				out.close();
				
				main.ThemeModifyPanel.repaint();
				themes.paint();
				
			}catch(IOException e1){ e1.printStackTrace(); }
				
		}});
		
	}
	
	
	@SuppressWarnings({ "resource"})
	public static void paint(){
		    
		main.load = false;
		main.OpenMatiere = main.CurrentMatiereName;
		
		for(Component comp : main.ThemeModifyPanel.getComponents()){
			main.ThemeModifyPanel.remove(comp);
		}
		
		main.getters.clear();
		main.comparators.clear();
		main.comparatorTxt.clear();
		main.Sizes.clear();
		main.Colors1.clear();
		main.Colors2.clear();
		main.Esps1.clear();
		main.Esps2.clear();
		main.Bolds.clear();
		main.Underlines.clear();
		main.Remove.clear();
		
		
		int i = 170;
		try{
			File file = new File(main.SaveLocation + "Fiches/" + main.OpenMatiere + "/theme.yml");
			if(!file.exists()){
				file.createNewFile();
				
				FileWriter writer = new FileWriter(file);
				BufferedWriter out = new BufferedWriter(writer);
				out.write("Ubuntu;4;0;0");
				out.newLine();
				out.write("1;1;A);4;1;1;0;0;true;true");
				out.newLine();
				out.close();
			}
			
			BufferedReader in;
			if(System.getProperty("os.name").equals("Linux") || System.getProperty("os.name").equals("Mac OS X")){
				System.out.println("os.name is Linux or Mac");
				in = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
			}else{
				System.out.println("os.name is Windows");
				in = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.ISO_8859_1));
			}
			
			String line = in.readLine();
			String[] DefaultParam = line.split(";");
			
			main.Police.setBounds(410, 100, 100, 20);
			main.Size.setBounds(520, 100, 50, 20);
			main.Align.setBounds(580, 100, 50, 20);
			main.Colortext.setBounds(640, 100, 75, 20);
			
			main.Police.setText(DefaultParam[0]);
			main.Size.setSelectedIndex(Integer.parseInt(DefaultParam[1]));
			main.Align.setSelectedIndex(Integer.parseInt(DefaultParam[2]));
			main.Colortext.setSelectedIndex(Integer.parseInt(DefaultParam[3]));
			
			main.ThemeModifyPanel.add(main.Police);
			main.ThemeModifyPanel.add(main.Size);
			main.ThemeModifyPanel.add(main.Align);
			main.ThemeModifyPanel.add(main.Colortext);
			
			line = in.readLine();
			int x = 0;
			while(line != null){
				
				String[] prop = line.split(";");
				
				main.getters.add(x, new JComboBox<>(new String[]{"La ligne", "Le 1er mot", "Le 2em mot", "La 1er lettre", "Le 2em lettre"}));
				main.comparators.add(x, new JComboBox<>(new String[]{"contient", "est"}));
				main.comparatorTxt.add(x, new JTextField());
				
				main.getters.get(x).setBounds(40, i, 100, 20);
			    main.comparators.get(x).setBounds(150, i, 85, 20);
			    main.comparatorTxt.get(x).setBounds(245, i, 100, 20);
				
			    main.getters.get(x).setSelectedIndex(Integer.parseInt(prop[0]));
			    main.comparators.get(x).setSelectedIndex(Integer.parseInt(prop[1]));
			    main.comparatorTxt.get(x).setText(prop[2]);
			    
			    main.ThemeModifyPanel.add(main.getters.get(x));
			    main.ThemeModifyPanel.add(main.comparators.get(x));
			    main.ThemeModifyPanel.add(main.comparatorTxt.get(x));
			    
			    
			    
			    main.Sizes.add(x, new JComboBox<>(new String[]{"6", "8", "10", "12", "14", "16", "18", "20"}));
			    main.Colors1.add(x, new JComboBox<>(new String[]{"Noir", "Rouge", "Vert", "Bleu"}));
			    main.Colors2.add(x, new JComboBox<>(new String[]{"Noir", "Rouge", "Vert", "Bleu"}));
			    main.Esps1.add(x, new JComboBox<>(new String[]{"0", "10", "20", "30", "40", "50", "60", "70", "80", "90"}));
			    main.Esps2.add(x, new JComboBox<>(new String[]{"début", "fin"}));
			    main.Bolds.add(new JCheckBox("Gras"));
			    main.Underlines.add(new JCheckBox("Italique"));
			    
			    main.Sizes.get(x).setBounds(410, i, 50, 20);
			    main.Colors1.get(x).setBounds(470, i, 75, 20);
			    main.Colors2.get(x).setBounds(555, i, 75, 20);
			    main.Esps1.get(x).setBounds(640, i, 50, 20);
			    main.Esps2.get(x).setBounds(700, i, 75, 20);
			    main.Bolds.get(x).setBounds(785, i, 75, 20);
			    main.Underlines.get(x).setBounds(870, i, 100, 20);
			    
				main.Sizes.get(x).setSelectedIndex(Integer.parseInt(prop[3]));
				main.Colors1.get(x).setSelectedIndex(Integer.parseInt(prop[4]));
				main.Colors2.get(x).setSelectedIndex(Integer.parseInt(prop[5]));
				main.Esps1.get(x).setSelectedIndex(Integer.parseInt(prop[6]));
				main.Esps2.get(x).setSelectedIndex(Integer.parseInt(prop[7]));
				main.Bolds.get(x).setSelected(getBoolean(prop[8]));
				main.Underlines.get(x).setSelected(getBoolean(prop[9]));
			    main.ThemeModifyPanel.add(main.Sizes.get(x));
			    main.ThemeModifyPanel.add(main.Colors1.get(x));
			    main.ThemeModifyPanel.add(main.Colors2.get(x));
			    main.ThemeModifyPanel.add(main.Esps1.get(x));
			    main.ThemeModifyPanel.add(main.Esps2.get(x));
			    main.ThemeModifyPanel.add(main.Bolds.get(x));
			    main.ThemeModifyPanel.add(main.Underlines.get(x));
			    
			    
			    main.Remove.add(x, new JButton("Supprimer la règle :" + x));
			    main.Remove.get(x).setBounds(980, i, 93, 20);
			    main.ThemeModifyPanel.add(main.Remove.get(x));
			    
			    main.Remove.get(x).addActionListener(new ActionListener(){@Override public void actionPerformed(ActionEvent e){
			    	main.load = false;
					try{
						
						JButton btn =  (JButton) e.getSource();
						int org = Integer.parseInt(btn.getText().split(":")[1]);
						
						File file = new File(main.SaveLocation + "Fiches/" + main.OpenMatiere + "/theme.yml");
						BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
						ArrayList<String> lines = new ArrayList<>();
						
						String line = in.readLine();
						while(line != null){
							lines.add(line);
							line = in.readLine();
						}
						lines.remove(org + 1);
						FileWriter writer = new FileWriter(file);
						BufferedWriter out = new BufferedWriter(writer);
						if(lines != null){
							for(String Wline : lines){
								if(Wline != null){
									out.write(Wline);
									out.newLine();
								}
							}
						}
						out.close();
						
						main.ThemeModifyPanel.repaint();
						themes.paint();
						
					}catch(IOException e1){ e1.printStackTrace(); }
						
				}});
			    
			    
			    i = i + 30;
				line = in.readLine();
				x++;
				
			}
		
		}catch(IOException e){ e.printStackTrace(); }
		
		
		main.add.setBounds(441, i + 20, 200, 20);
	    main.ThemeModifyPanel.add(main.add);
		
	    
	    main.load = true;
	}
	
	public static boolean getBoolean(String str){
		if(str.equals("true")) return true;
		else return false;
		
	}
	
}
