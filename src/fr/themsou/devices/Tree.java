package fr.themsou.devices;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import fr.themsou.main.CreateChildNodes;
import fr.themsou.main.main;

public class Tree {
	
	public static void clearTree(JTree tree) {
	    if (tree.toString() == null) { return; }
	    DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
	    DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
	    root.removeAllChildren();
	    model.reload();
	    
	    CreateChildNodes ccn = new CreateChildNodes(new File(main.SaveLocation + "/Fiches/"), main.TreeRacine);
        new Thread(ccn).start();
	}
	
	public void addActionListener(){
		
		
		main.TFichiers.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
		          
		    	  	TreePath tp = main.TFichiers.getPathForLocation(me.getX(), me.getY());
		    	  	if(tp != null){
		    		  	String[] tps = tp.toString().replace("]", "").split(",");
		    		  	if(tps.length == 3){
		    			  
		    			  	
							try{
								System.out.println();
								BufferedReader in;
								if(System.getProperty("os.name").equals("Linux") || System.getProperty("os.name").equals("Mac OS X")){
									in = new BufferedReader(new InputStreamReader(new FileInputStream((main.SaveLocation + "Fiches/" + tps[1] + "/" + tps[2]).replace("/ ", "/")), StandardCharsets.UTF_8));
								}else{
									in = new BufferedReader(new InputStreamReader(new FileInputStream((main.SaveLocation + "Fiches/" + tps[1] + "/" + tps[2]).replace("/ ", "/")), StandardCharsets.ISO_8859_1));
								}
								String line = in.readLine();
								String total = "";
			    				while(line != null){
			    					
			    					total = total + line + "\n";
			    					line = in.readLine();
			    				}
			    				if(!total.isEmpty()) main.CodeTextArea.setText(total);
			    				else main.CodeTextArea.setText("");
			    				
								main.CurrentFile = (main.SaveLocation + "Fiches/" + tps[1] + "/" + tps[2]).replace("/ ", "/");
								main.CurrentMatiere = (main.SaveLocation + "Fiches/" + tps[1]).replace("/ ", "/");
								
								main.CurrentFileName = ("/" + tps[2]).replace("/ ", "");
								main.CurrentMatiereName = ("/" + tps[1]).replace("/ ", "");
								
								main.CompX = me.getXOnScreen();
								main.CompY = me.getYOnScreen();
								
							}catch(IOException e) {e.printStackTrace();}
		    				
		    		  }else if(tps.length == 2){
		    			  
		    			  main.CurrentMatiere = (main.SaveLocation + "Fiches/" + tps[1]).replace("/ ", "/");
		    			  main.CurrentMatiereName = ("/" + tps[1]).replace("/ ", "");
		    		  }
		    	  }
		        }
		      });
		
	}

}
