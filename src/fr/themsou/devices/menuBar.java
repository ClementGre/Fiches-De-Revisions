package fr.themsou.devices;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import fr.themsou.main.main;

@SuppressWarnings("serial")
public class menuBar extends JFrame{
	
	
	@SuppressWarnings("static-access")
	public static void setlistener(){
		
		
		main.menu1arg1.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { // Supprimer (Fiche)
			
			if(main.CurrentFile != null){
				int i = JOptionPane.showConfirmDialog(null, "Etes vous sur de vouloir supprimer '" + main.CurrentFileName + "' ?");
				if(i == 0){
					File file = new File(main.CurrentFile);
					file.delete();
					main.CurrentFile = null;
					main.CodeTextArea.setText("");
					new Tree().clearTree(main.TFichiers);
				}
			}else{
				JOptionPane.showMessageDialog(null, "Vous devez sélexionner une Fiche");
			}
		}});
		
		main.menu1arg2.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { // Renomer (Fiche)
			
			if(main.CurrentFile != null){
				String input = JOptionPane.showInputDialog(null, "Quel nom voullez attribuer à votre Fiche ?", main.CurrentFileName.replace(".txt", ""));
				if(input == null){
				}else if(input.isEmpty()){
					JOptionPane.showMessageDialog(null, "Vous devez saisir un nom");
					
				}else if(input != null){
					File file = new File(main.CurrentFile);
					file.renameTo(new File(main.SaveLocation + "Fiches/" + main.CurrentMatiereName + "/" + input + ".txt"));

					main.CurrentFile = main.SaveLocation + "/Fiches/" + main.CurrentMatiereName + "/" + input + ".txt";
					main.CurrentFileName = input + ".txt";
					
					new Tree().clearTree(main.TFichiers);
				}
			}else{
				JOptionPane.showMessageDialog(null, "Vous devez sélexionner une Fiche");
			}
						
		}});
		
		main.menu1arg3.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { // Déplacer (Fiche)

			
			if(main.CurrentFile != null){
				String[] matieres = new File(main.SaveLocation + "Fiches/").list();
				if(matieres != null){
					String input = (String) JOptionPane.showInputDialog(null, "choisissez une matière", "Déplacer une Fiche", JOptionPane.QUESTION_MESSAGE, null, matieres, matieres[0]); 
					
					if(input != null){
						File file = new File(main.CurrentFile);
						file.renameTo(new File(main.SaveLocation + "Fiches/" + input + "/" + main.CurrentFileName));

						main.CurrentFile = main.SaveLocation + "/Fiches/" + input + "/" + main.CurrentFileName;
						main.CurrentFileName = input + ".txt";
						
						new Tree().clearTree(main.TFichiers);
					
					}
				}else{
					JOptionPane.showMessageDialog(null, "Vous devez déjà créer une matière");
				}
					
					
				
			}else{
				JOptionPane.showMessageDialog(null, "Vous devez sélexionner une Fiche");
			}
				
		}});
		
		main.menu1arg4.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { // Créer une autre fiche
			try{
				
				String input = JOptionPane.showInputDialog(null, "Quel nom voullez attribuer à votre nouvelle Fiche ?", "Ma nouvelle Fiche");
				
				if(input == null){
				}else if(input.isEmpty()){
					JOptionPane.showMessageDialog(null, "Vous devez saisir un nom");
				}else if(input != null){
					String[] matieres = new File(main.SaveLocation + "Fiches/").list();
					if(matieres != null){
						String input2 = (String) JOptionPane.showInputDialog(null, "choisissez une matière", "Créer une autre fiche", JOptionPane.QUESTION_MESSAGE, null, matieres, matieres[0]); 
						
						if(input2 != null){
							File file = new File(main.SaveLocation + "Fiches/" + input2 + "/" + input + ".txt");
							file.createNewFile();
							
							new Tree().clearTree(main.TFichiers);
						}
					}else{
						JOptionPane.showMessageDialog(null, "Vous devez déjà créer une matière");
					}
				}
			}catch(IOException e1){e1.printStackTrace();}
		}});
		
		main.menu1arg5.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { // Ajouter une Image
				
			if(main.CurrentFile != null){
				String input = JOptionPane.showInputDialog(null, "Veuillez entrer l'URI(le chemin)", System.getProperty("user.home") + "/");
				if(input == null){
				}else if(!new File(input).exists()){
					JOptionPane.showMessageDialog(null, "Ce chemin ne mène à aucun fichier");
					
				}else if(input != null){
					File file = new File(input);
					
					if(file.getName().contains(".jpeg") || file.getName().contains(".png") || file.getName().contains(".jpg")){
						
						File file2 = new File(main.CurrentMatiere + "/" + file.getName());
						
						int i = 1;
						while(file2.exists()){
							file2 = new File(main.CurrentMatiere + "/" + i + file.getName());
							i++;
						}
						
						try{
							Files.copy(Paths.get(file.getPath()), Paths.get(file2.getPath()), (CopyOption) StandardCopyOption.REPLACE_EXISTING);
						}catch(IOException e1){ e1.printStackTrace(); }
						
						
						main.CodeTextArea.setText(main.CodeTextArea.getText() + "\n" + file2.getPath() + "SIZE=1");
						
					}else{
						JOptionPane.showMessageDialog(null, "Ce fichier n'est pas une Image ou le format n'est pas supproté");
					}
					
				}
			}else{
				JOptionPane.showMessageDialog(null, "Vous devez sélexionner une Fiche");
			}
			
			
		}});
		
		main.menu1arg6.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { // Exporter
				
			if(main.CurrentFile != null){
						
				String[] size = new String[]{"1  580px/861px", "2  1160px/1722px", "3  1740px/2583px", "4  2320px/3444px", "5  29000px/4305px"};
				String input = (String) JOptionPane.showInputDialog(null, "Choisissez une qualité", "Exporter", JOptionPane.QUESTION_MESSAGE, null, size, size[2]); 
				
				if(input != null){
					
					String input2 = (String) JOptionPane.showInputDialog(null, "Où voulez-vous exporter votre fiche ?", System.getProperty("user.home") + "/"); 
					
					if(input2 == null){
					}else if(input2.isEmpty()){
						JOptionPane.showMessageDialog(null, "Vous devez saisir un chemin (URI)");
					}else if(input2 != null){
						
						int IntSize = Integer.parseInt(input.substring(0, 1));
						
						ArrayList<String> rest = new ArrayList<>(); rest.add("Not null !");
						int i = 1;
						while(rest != null  && (!rest.isEmpty())){
							
							
							
							Image img = new BufferedImage(630 * IntSize, 891 * IntSize, BufferedImage.TYPE_INT_RGB);
							Graphics gImg = img.getGraphics();
							
							ExportImg CExportImg = new ExportImg();
							
							if(i == 1){
								rest = CExportImg.paintComponent(gImg, 30 * IntSize, 590 * IntSize, 30 * IntSize, 881 * IntSize, (int) IntSize, null);
							}else{
								rest = CExportImg.paintComponent(gImg, 30 * IntSize, 590 * IntSize, 30 * IntSize, 881 * IntSize, (int) IntSize, rest);
							}
							
							File file = new File(input2 + main.CurrentFileName.replace(".txt", "") + " - " + i +".jpg");
							
							if(file.exists()) file.delete();
							try{
								ImageIO.write((RenderedImage) img, "JPG", file);
							}catch (IOException e1){ e1.printStackTrace(); }
							
							i++;
					}
				}
			}

		}else{
			JOptionPane.showMessageDialog(null, "Vous devez sélexionner une Fiche");
		}
			
			
		}});
		
		
		main.menu2arg1.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { // Supprimer (Matière)
				
			if(main.CurrentMatiere != null){
				int i = JOptionPane.showConfirmDialog(null, "Etes vous sur de vouloir supprimer la matière '" + main.CurrentMatiereName + "' Avec toutes ses Fiches ??");
				if(i == 0){
					File file = new File(main.CurrentMatiere);
					for(File FaDel : file.listFiles()) FaDel.delete();
					file.delete();
					main.CurrentFile = null;
					main.CodeTextArea.setText("");
					new Tree().clearTree(main.TFichiers);
				}
			}else{
				JOptionPane.showMessageDialog(null, "Vous devez sélexionner une Fiche");
			}
		}});
		
		main.menu2arg2.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { // Renomer (Matière)
						
			if(main.CurrentMatiere != null){
				String input = JOptionPane.showInputDialog(null, "Quel nom voullez attribuer à votre Matière ?", main.CurrentMatiereName);
				if(input == null){
				}else if(input.isEmpty()){
					JOptionPane.showMessageDialog(null, "Vous devez saisir un nom");
					
				}else if(input != null){
					File file = new File(main.CurrentMatiere);
					file.renameTo(new File(main.SaveLocation + "Fiches/" + input));

					main.CurrentMatiere = main.SaveLocation + "/Fiches/" + input;
					main.CurrentMatiereName = input;
					
					main.CurrentFile = null;
					main.CodeTextArea.setText("");
					
					new Tree().clearTree(main.TFichiers);
				}
			}else{
				JOptionPane.showMessageDialog(null, "Vous devez sélexionner une matière");
			}
		}});
		
		
		main.menu2arg3.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { // Créer une autre Matière
			
			String input = JOptionPane.showInputDialog(null, "Quel nom voullez attribuer à votre nouvelle Matière ?", "Ma nouvelle Matière");
			
			if(input == null){
			}else if(input.isEmpty()){
				JOptionPane.showMessageDialog(null, "Vous devez saisir un nom");
			}else if(input != null){
				try{
					
					File file = new File(main.SaveLocation + "Fiches/" + input );
					file.mkdirs();
					
					file = new File(main.SaveLocation + "Fiches/" + input + "/Chapitre 1");
					file.createNewFile();
					FileWriter writer = new FileWriter(file);
					BufferedWriter out = new BufferedWriter(writer);
					out.write("Lato;4;0;0"); out.newLine();
					out.close();
					
				}catch(IOException e1){ e1.printStackTrace(); } 
				
				
				new Tree().clearTree(main.TFichiers);
			}
			
				
				
		}});
		main.menu2arg4.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { // Modifier le Thème
			
			if(main.CurrentMatiere != null){
				main.ThemeModify.setSize(1113, 625);
				main.ThemeModify.setResizable(false);
				main.ThemeModify.setLocationRelativeTo(null);
				main.ThemeModify.setContentPane(main.SPaneModify);
				main.ThemeModify.setVisible(true);
			    
				main.ThemeModifyPanel.setLayout(null);
			    
				main.ThemeModifyPanel.repaint();
				themes.paint();
			}else JOptionPane.showMessageDialog(null, "Vous devez sélexionner une Matière");
			
			
			
			
		    
		    
		}});
		
		
		
		main.menu3arg1.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { // Lieu de stockage

			String input = JOptionPane.showInputDialog(null, "Entrez le chemin du nouveau lieu de stockage", main.SaveLocation);
			boolean valid = false;
			if(input == null){
			}else if(input.isEmpty()){
				JOptionPane.showMessageDialog(null, "Vous devez saisir un nom");
				
			}else if(input != null){
				File file = new File(input);
				
				if(!file.exists()){
					int i = JOptionPane.showConfirmDialog(null, "Ce dossier n'existe pas, voullez vous que je le crée ?");
					if(i == 0){
						file.mkdirs();
						valid = true;
					}
				}else valid = true;
				
				if(valid){
					
					File config = new File(System.getProperty("user.home") + "/.FichesDeRévisions/config.txt");
					
					try{
						
						
						FileWriter writer = new FileWriter(config);
						BufferedWriter out = new BufferedWriter(writer);
						out.write(input); out.newLine();
						out.close();
						
						main.SaveLocation = input;
						
						
						file = new File(main.SaveLocation + "/Fiches");
						file.mkdirs();
						
					}catch(IOException e1){ e1.printStackTrace(); }
					
					main.CurrentMatiere = null;
					main.CurrentMatiereName = null;
					main.CurrentFileName = null;
					main.CurrentFile = null;
					main.CodeTextArea.setText("");
					
					new Tree().clearTree(main.TFichiers);
					
				}
			}	
		}});
			
	}
	
	
	public static void paintmenu(){
		
		
		
		main.menubar.setAlignmentX(RIGHT_ALIGNMENT);
		//main.menubar.setBackground(Color.GRAY);
		main.panel.add("North", main.menubar);
		
		main.menubar.add(main.menu1);
		main.menubar.add(main.menu2);
		main.menubar.add(main.menu3);
		
		
		main.menu1.add(main.menu1arg1);
        main.menu1.add(main.menu1arg2);
        main.menu1.add(main.menu1arg3);
        main.menu1.add(main.menu1arg4);
        main.menu1.add(main.menu1arg5);
        main.menu1.add(main.menu1arg6);
        
		main.menu2.add(main.menu2arg1);
        main.menu2.add(main.menu2arg2);
        main.menu2.add(main.menu2arg3);
        main.menu2.add(main.menu2arg4);
        
		main.menu3.add(main.menu3arg1);
        
        
		
	}

}
