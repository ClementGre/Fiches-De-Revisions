package fr.themsou.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
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
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import fenetres.ThemeModify;
import fr.themsou.devices.MouseWhelListener;
import fr.themsou.devices.Tree;
import fr.themsou.devices.menuBar;
import fr.themsou.devices.themes;
import fr.themsou.panel.PCode;
import fr.themsou.panel.PResult;
import fr.themsou.panel.Panel;

public class main {
	
	
	public static JFrame fenetre;
	public static MouseListener MListener = new fr.themsou.devices.MouseListener();
	public static KeyListener KListener = new fr.themsou.devices.KeyListener();
	public static MouseWhelListener WListener = new fr.themsou.devices.MouseWhelListener();
	public static Panel panel = new Panel();
	public static BorderLayout BLayout = new BorderLayout();
	public static JFrame ThemeModify = new JFrame("Modifier le Thème");
	public static ThemeModify ThemeModifyPanel = new ThemeModify();
	public static JScrollPane SPaneModify = new JScrollPane(ThemeModifyPanel);
	
	public static JFrame Jsup = new JFrame("Supprimer");
	public static JFrame Jren= new JFrame("Renomer");
	public static JFrame Jdep = new JFrame("Déplacer");
	
	public static JMenuBar menubar = new JMenuBar();
	
	public static JMenu menu1 = new JMenu("Fiche");
	public static JMenuItem menu1arg1 = new JMenuItem("Supprimer");
	public static JMenuItem menu1arg2 = new JMenuItem("Renomer");
	public static JMenuItem menu1arg3 = new JMenuItem("Déplacer");
	public static JMenuItem menu1arg4 = new JMenuItem("Créer une autre Fiche");
	public static JMenuItem menu1arg5 = new JMenuItem("Ajouter une image");
	public static JMenuItem menu1arg6 = new JMenuItem("Exporter (Image)");
	
	
	
	public static JMenu menu2 = new JMenu("Matière");
	public static JMenuItem menu2arg1 = new JMenuItem("Supprimer");
	public static JMenuItem menu2arg2 = new JMenuItem("Renomer");
	public static JMenuItem menu2arg3 = new JMenuItem("Créer une autre Matière");
	public static JMenuItem menu2arg4 = new JMenuItem("Modifier le Thème");
	
	public static JMenu menu3 = new JMenu("Personalisation");
	public static JMenuItem menu3arg1 = new JMenuItem("Lieu de stockage");
	
	public static JTextArea CodeTextArea = new JTextArea("");
	public static String CodeText = " ";
	
	
	//public static PFichiers pfichiers = new PFichiers();
	public static PResult presult = new PResult();  
    public static PCode pcode = new PCode(); 
    public static JScrollPane SPaneCode = new JScrollPane(pcode);
    public static JScrollPane SPaneResult = new JScrollPane(presult);
	public static JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, SPaneResult, SPaneCode);
	
	
	public static String SaveLocation = System.getProperty("user.home") + "/.FichesDeRévisions/";
	public static String CurrentFile = null;
	public static String CurrentMatiere = null;
	public static String CurrentFileName = null;
	public static String CurrentMatiereName = null;
	
	public static DefaultMutableTreeNode TreeRacine;
	public static DefaultTreeModel TreeModel;
	public static JTree TFichiers;
	public static JScrollPane SPaneFichiers;
	
	public static int CompX = 0;
	public static int CompY = 0;
	public static boolean load = false;
	public static int reload = 10;
	
	
	public static JTextField Police = new JTextField();
	public static JComboBox<String> Size = new JComboBox<>(new String[]{"6", "8", "10", "12", "14", "16", "18", "20"});
	public static JComboBox<String> Align = new JComboBox<>(new String[]{"0", "10", "20", "30", "40", "50", "60", "70", "80", "90"});
	public static JComboBox<String> Colortext = new JComboBox<>(new String[]{"Noir", "Rouge", "Vert", "Bleu"});
	
	
	public static ArrayList<JComboBox<String>> getters = new ArrayList<>();
	public static ArrayList<JComboBox<String>> comparators = new ArrayList<>();
	public static ArrayList<JTextField> comparatorTxt = new ArrayList<>();
	 
	public static ArrayList<JComboBox<String>> Sizes = new ArrayList<>();
	public static ArrayList<JComboBox<String>> Colors1 = new ArrayList<>();
	public static ArrayList<JComboBox<String>> Colors2 = new ArrayList<>();
	public static ArrayList<JComboBox<String>> Esps1 = new ArrayList<>();
	public static ArrayList<JComboBox<String>> Esps2 = new ArrayList<>();
	public static ArrayList<JCheckBox> Bolds = new ArrayList<>();
	public static ArrayList<JCheckBox> Underlines = new ArrayList<>();
	
	public static ArrayList<JButton> Remove = new ArrayList<>();
	
	public static String OpenMatiere = null;
	
	public static JButton add = new JButton("Ajouter une règle");
	
	public static void main(String[] args){
		
		initialise();
		
		
		TreeRacine = new DefaultMutableTreeNode(new File(SaveLocation + "/Fiches/"));
		TreeModel = new DefaultTreeModel(TreeRacine);
		TFichiers = new JTree(TreeModel);
		SPaneFichiers = new JScrollPane(TFichiers);
		
		TFichiers.setShowsRootHandles(true);
		CreateChildNodes ccn = new CreateChildNodes(new File(SaveLocation + "/Fiches/"), TreeRacine);
        new Thread(ccn).start();
		
		fenetre = new JFrame("Fiches De Révisions");
		fenetre.setSize(1300, 750);
		fenetre.setResizable(true);
		fenetre.setLocationRelativeTo(null);
		fenetre.setVisible(true);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.addKeyListener(KListener);
		fenetre.addMouseListener(MListener);
		fenetre.addMouseWheelListener(WListener);
		fenetre.setContentPane(panel);
		
		panel.setLayout(BLayout);
		
		
		//TFichiers.setPreferredSize(new Dimension(200, 0));
		
		
		panel.add("West", SPaneFichiers);
		panel.add("Center", split);
		
		SPaneCode.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		SPaneResult.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		SPaneResult.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		SPaneFichiers.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		SPaneFichiers.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		SPaneModify.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		SPaneResult.setMinimumSize(new Dimension(300, 5000));
		SPaneCode.setMinimumSize(new Dimension(300, 1000));
		
		SPaneFichiers.setPreferredSize(new Dimension(250, 5000));
		
		
		
		
		
		
		pcode.add(CodeTextArea);
		SPaneCode.setBackground(Color.WHITE);
		SPaneResult.setBackground(Color.WHITE);
		CodeTextArea.setPreferredSize(new Dimension(1500, 11000));
		CodeTextArea.setFont(new Font("Ubuntu", 1, 16));
		
		split.setDividerLocation(550);
		
		
		
		menuBar.paintmenu();
		menuBar.setlistener();
		
		
		new Tree().addActionListener();
		
		themes.setexecute();
		presult.setPreferredSize(new Dimension(5000, 10000));
		Run.run();
		
		
		
		
	}
	
	public static void initialise(){
		try{
			
			
			
			File file = new File(System.getProperty("user.home") + "/.FichesDeRévisions");
			file.mkdirs();
			
			file = new File(System.getProperty("user.home") + "/.FichesDeRévisions/config.txt");
			
			
			if(!file.exists()){
				
				file.createNewFile();
				FileWriter writer = new FileWriter(file);
				BufferedWriter out = new BufferedWriter(writer);
				out.write(System.getProperty("user.home") + "/.FichesDeRévisions/");
				out.close();
				
				file = new File(main.SaveLocation + "/Fiches");
				file.mkdirs();
				
				file = new File(System.getProperty("user.home") + "/.FichesDeRévisions/Fiches/Mathématiques");
				file.mkdirs();
				file = new File(System.getProperty("user.home") + "/.FichesDeRévisions/Fiches/Français");
				file.mkdirs();
				file = new File(System.getProperty("user.home") + "/.FichesDeRévisions/Fiches/Histoire-Géo");
				file.mkdirs();
				
				file = new File(System.getProperty("user.home") + "/.FichesDeRévisions/Fiches/Mathématiques/Chapitre 1.txt");
				file.createNewFile();
				writer = new FileWriter(file);
				out = new BufferedWriter(writer);
				out.write("I- Les diférents types de balises :"); out.newLine(); out.newLine();
				out.write("Théorème:"); out.newLine();
				out.write("Vous pouvez marquer tous vos théorèmes de cette façon si !"); out.newLine(); out.newLine();
				out.write("Exemple:"); out.newLine();
				out.write("Vous pouvez toujours modifier le thème dans Matière -> Modifier le Thème"); out.newLine();
				out.close();
				
				file = new File(System.getProperty("user.home") + "/.FichesDeRévisions/Fiches/Français/Chapitre 1.txt");
				file.createNewFile();
				writer = new FileWriter(file);
				out = new BufferedWriter(writer);
				out.write("Séance 1 : Personalisation"); out.newLine(); out.newLine();
				out.write("Hey, n'oubliez pas que vous pouvez modifier le thème de la matière dans Matière -> Modifier le Thème"); out.newLine(); out.newLine();
				out.write("Séance 2 : Image"); out.newLine(); out.newLine();
				out.write("Vous pouvez aussi ajouter des images avec Fiche -> Ajouter une Image"); out.newLine(); out.newLine();
				out.close();
				
				file = new File(System.getProperty("user.home") + "/.FichesDeRévisions/Fiches/Histoire-Géo/Chapitre 1.txt");
				file.createNewFile();
				writer = new FileWriter(file);
				out = new BufferedWriter(writer);
				out.write("I- Informations"); out.newLine(); out.newLine();
				out.write("A) Vous pouvez faire un retour a la ligne sans effacer le formatage en marquant /r et voila"); out.newLine(); out.newLine();
				out.write("B) Je vous ai préconfiguré plusieurs autres choses :"); out.newLine(); out.newLine();
				out.write("Définition: Vous pouvez aussi marquer vos définitions comme cela ! /r et encore slash + r pour faire un retour a la ligne "); out.newLine();
				out.close();
				
				file = new File(System.getProperty("user.home") + "/.FichesDeRévisions/Fiches/Mathématiques/theme.yml");
				file.createNewFile();
				writer = new FileWriter(file);
				out = new BufferedWriter(writer);
				out.write("Lato;4;6;0"); out.newLine();
				out.write("1;1;I-;5;1;1;0;1;true;false"); out.newLine();
				out.write("1;1;II-;5;1;1;0;1;true;false"); out.newLine();
				out.write("1;1;III-;5;1;1;0;1;true;false"); out.newLine();
				out.write("1;1;IV-;5;1;1;0;1;true;false"); out.newLine();
				out.write("1;1;Théorème:;4;1;0;2;1;true;false"); out.newLine();
				out.write("1;1;Exemple:;4;2;0;2;1;true;false"); out.newLine();
				out.write("1;1;Ex_type:;4;2;0;2;1;true;false"); out.newLine();
				out.write("1;1;Définition:;4;1;0;2;0;true;false"); out.newLine();
				out.write("1;1;Propriété:;4;1;0;2;0;true;false"); out.newLine();
				out.close();
				
				file = new File(System.getProperty("user.home") + "/.FichesDeRévisions/Fiches/Français/theme.yml");
				file.createNewFile();
				writer = new FileWriter(file);
				out = new BufferedWriter(writer);
				out.write("Lato;4;2;0"); out.newLine();
				out.write("1;0;Séance;5;1;1;0;0;true;false"); out.newLine();
				out.close();
				
				file = new File(System.getProperty("user.home") + "/.FichesDeRévisions/Fiches/Histoire-Géo/theme.yml");
				file.createNewFile();
				writer = new FileWriter(file);
				out = new BufferedWriter(writer);
				out.write("Lato;4;4;0"); out.newLine();
				out.write("1;1;Introduction:;5;1;1;0;1;true;false"); out.newLine();
				out.write("1;1;I-;5;1;1;0;1;true;false"); out.newLine();
				out.write("1;1;II-;5;1;1;0;1;true;false"); out.newLine();
				out.write("1;1;III-;5;1;1;0;1;true;false"); out.newLine();
				out.write("1;1;IV-;5;1;1;0;1;true;false"); out.newLine();
				out.write("1;1;A);5;1;1;2;1;true;false"); out.newLine();
				out.write("1;1;B);5;1;1;2;1;true;false"); out.newLine();
				out.write("1;1;C);5;1;1;2;1;true;false"); out.newLine();
				out.write("1;0;:;4;2;0;4;1;false;true"); out.newLine();
				out.close();
				
				
			}else{
				
				BufferedReader in;
				if(System.getProperty("os.name").equals("Linux") || System.getProperty("os.name").equals("Mac OS X")){
					System.out.println("os.name is Linux or Mac");
					in = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
				}else{
					System.out.println("os.name is Windows");
					in = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.ISO_8859_1));
				}
				
				main.SaveLocation = in.readLine();
				
				file = new File(main.SaveLocation + "/Fiches");
				file.mkdirs();
				
			}
			
			
			
			
		
		}catch(IOException e1){e1.printStackTrace();}
		
		
	}

}
