package fr.themsou.main;

import java.io.File;

import javax.swing.tree.DefaultMutableTreeNode;

public class CreateChildNodes implements Runnable {

    private DefaultMutableTreeNode root;

    private File fileRoot;

    public CreateChildNodes(File fileRoot, DefaultMutableTreeNode root) {
        this.fileRoot = fileRoot;
        this.root = root;
    }

    @Override
    public void run() {
        createChildren(fileRoot, root);
    }

    private void createChildren(File fileRoot, DefaultMutableTreeNode node) {
    	
        File[] files = fileRoot.listFiles();
        if (files == null) return;

        for (File file : files) {
            
            if(file.isDirectory()){
            	DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(new FileNode(file));
                node.add(childNode);
                createChildren(file, childNode);
            }else{
            	if(file.getName().contains(".txt")){
            		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(new FileNode(file));
                    node.add(childNode);
                    
            	}
            }
        }
    }



	public class FileNode {
	
	    private File file;
	
	    public FileNode(File file) {
	        this.file = file;
	    }
	
	    @Override
	    public String toString() {
	        String name = file.getName();
	        if(name.equals("")) {
	            return file.getAbsolutePath();
	        }else{
	            return name;
	        }
	    }
	}

}