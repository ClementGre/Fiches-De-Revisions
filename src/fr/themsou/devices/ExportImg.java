package fr.themsou.devices;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import fr.themsou.main.main;

public class ExportImg {

	public ArrayList<String> paintComponent(Graphics g, int minX, int maxX, int minY, int maxY, int size, ArrayList<String> Rest){
		
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, maxX + size * 50, maxY + size * 50);
		g.setColor(Color.BLACK);
		
		String CodeTxt;
		if(Rest != null){
			CodeTxt = String.join("\n", Rest);
		}else{
			CodeTxt = main.CodeTextArea.getText();
		}
		
		if(main.CurrentFile != null){
			if(!CodeTxt.isEmpty()){
				
				String[] CodeLines = CodeTxt.split("\n");
				ArrayList<String> ReturnCodeLines = new ArrayList<>();
				for(String CL : CodeLines) ReturnCodeLines.add(CL);
				String ThemeLines[] = new String[100];
				Font DefaultFont = new Font("Ubuntu", 0, 14);
				Color DefaultColor = Color.BLACK;
				int DefaultAlign = minX;
				int MinAlign = minX;
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				
				try{
					File file = new File(main.SaveLocation + "Fiches/" + main.CurrentMatiereName + "/theme.yml");

					if(!file.exists()){
						file.createNewFile();
						
					}
					
					BufferedReader in;
					if(System.getProperty("os.name").equals("Linux") || System.getProperty("os.name").equals("Mac OS X")){
						in = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
					}else{
						in = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.ISO_8859_1));
					}
					
					
					String line = in.readLine();
					if(line == null) return null;
					String DefaultParam[] = line.split(";");
					
					DefaultFont = new Font(DefaultParam[0], 0, getSize(Integer.parseInt(DefaultParam[1]), size));
					DefaultColor = getColor(Integer.parseInt(DefaultParam[3]));
					DefaultAlign = getEsp(Integer.parseInt(DefaultParam[2]), MinAlign, size);
					
					line = in.readLine();
					int i = 0;
					while(line != null){
						if(!line.isEmpty()){
							ThemeLines[i] = line;
						}
								
						line = in.readLine();
						i++;
					}
					
				}catch(IOException e){ e.printStackTrace(); }
				
				if(Rest == null){
					g.setColor(new Color(230, 0, 0));
					fullCenterString(g2d, minX, maxX, minX, minX + 25 * size,  main.CurrentFileName.replace(".txt", ""), new Font(DefaultFont.getFamily(), Font.BOLD, (int) (25 * size / 1.3)));
					minY = minY + 50 * size;
				}
				
				
				int Y = minY;
				int lastY = 20;
				int i = 0;
				while(Y < maxY - 20 * size && ReturnCodeLines.size() != 0){
					
					ReturnCodeLines.remove(0);
					String CodeLine = CodeLines[i];
					
					if(CodeLine.contains("SIZE=") && CodeLine.split("SIZE=").length == 2){
						Image img = new ImageIcon(CodeLine.split("SIZE=")[0]).getImage();
						try{
							g2d.drawImage(img, DefaultAlign, Y + 15 * size, (int) (img.getWidth(null) * Double.parseDouble(CodeLine.split("SIZE=")[1]) * size), (int) (img.getHeight(null) * Double.parseDouble(CodeLine.split("SIZE=")[1]) * size), null);
							Y = Y + (int) (img.getHeight(null) * Double.parseDouble(CodeLine.split("SIZE=")[1]) * size) + 20 * size;
						}catch(NumberFormatException e){}
						
						
						
					}else{
						boolean applyparam = false;
						String theme = "";
						
						for(String themeLine : ThemeLines){
							if(themeLine != null){
								
								
								String[] themeArg = themeLine.split(";");
								if(CodeLine != null){
									if(!CodeLine.isEmpty()){
										
										
										
										if(themeArg[0].equals("0")){
											if(CodeLine.contains(themeArg[2])){
												if(themeArg[1].equals("0")){
													applyparam = true;
													theme = themeLine;
												}else if(CodeLine.equalsIgnoreCase(themeArg[2])){
													applyparam = true;
													theme = themeLine;
												}
											}
										}else if(themeArg[0].equals("3") && CodeLine.substring(0, 1) != null){
											if(CodeLine.substring(0, 1).contains(themeArg[2])){
												applyparam = true;
												theme = themeLine;
											}
										}else if(themeArg[0].equals("4") && CodeLine.substring(0, 1) != null && CodeLine.substring(1, 2) != null){
											if(CodeLine.substring(1, 2).contains(themeArg[2])){
												applyparam = true;
												theme = themeLine;
											}
										}else if(themeArg[0].equals("1") && CodeLine.split(" ").length >= 1){
											if(CodeLine.split(" ")[0].contains(themeArg[2])){
												if(themeArg[1].equals("0")){
													applyparam = true;
													theme = themeLine;
												}else if(CodeLine.split(" ")[0].equalsIgnoreCase(themeArg[2])){
													applyparam = true;
													theme = themeLine;
												}
											}
										}else if(themeArg[0].equals("2") && CodeLine.split(" ").length >= 2){
											if(CodeLine.split(" ")[1].contains(themeArg[2])){
												if(themeArg[1].equals("0")){
													applyparam = true;
													theme = themeLine;
												}else if(CodeLine.split(" ")[1].equalsIgnoreCase(themeArg[2])){
													applyparam = true;
													theme = themeLine;
												}
											}
										}
										
										
										
									}else{
									}
								}else{
								}
							}
								
						}
						
						if(applyparam){
							
							String[] themeArg = theme.split(";");
							
							g.setColor(getColor(Integer.parseInt(themeArg[4])));
							
							int style = Font.PLAIN;
							if(getBoolean(themeArg[8]) && getBoolean(themeArg[9])) style = Font.BOLD + Font.ITALIC;
							else if(getBoolean(themeArg[8])) style = Font.BOLD;
							else if(getBoolean(themeArg[9])) style = Font.ITALIC;
							
							int[] location = centerString(g, getEsp(Integer.parseInt(themeArg[6]), MinAlign, size), Y, Y + lastY, CodeLine.split(" ")[0] + " ", new Font(DefaultFont.getFamily(), style, getSize(Integer.parseInt(themeArg[3]), size)));				
							
							
							boolean remove = true;
							int reprend = getEsp(Integer.parseInt(themeArg[6]), MinAlign, size) + location[0];
							for(String CodeLineSplit : CodeLine.split(" ")){
								if(!remove){
									
									g.setColor(Color.WHITE);
									int[] location2 = centerString(g2d, reprend, Y, Y + lastY, (CodeLineSplit + " ").replace("/r", ""), new Font(DefaultFont.getFamily(), style, getSize(Integer.parseInt(themeArg[3]), size)));				
									
									g.setColor(getColor(Integer.parseInt(themeArg[5])));
									
									if(reprend + location2[0] > maxX || CodeLineSplit.contains("/r")){
										
										lastY = location[1] + 2;
										Y = Y + lastY;
										
										if(Integer.parseInt(themeArg[7]) == 0){
											reprend = getEsp(Integer.parseInt(themeArg[6]), MinAlign, size);
										}else{
											reprend = getEsp(Integer.parseInt(themeArg[6]), MinAlign, size) + location[0];
										}
										
										int[] location3 = centerString(g2d, reprend, Y, Y + lastY, (CodeLineSplit + " ").replace("/r", ""), new Font(DefaultFont.getFamily(), style, getSize(Integer.parseInt(themeArg[3]), size)));
										reprend = reprend + location3[0];
										
									}else{
										
										if(CodeLineSplit.split("/").length == 2){
											
											g.setColor(Color.WHITE);
											String topbar = CodeLineSplit.split("/")[0];
											String botbar = CodeLineSplit.split("/")[1];
											int[] location3 = centerString(g2d, reprend, Y, Y + lastY, topbar, new Font(DefaultFont.getFamily(), style, getSize(Integer.parseInt(themeArg[3]), size)));
											int[] location4 = centerString(g2d, reprend, Y, Y + lastY, botbar, new Font(DefaultFont.getFamily(), style, getSize(Integer.parseInt(themeArg[3]), size)));
											int barlenght = 0;
											if(location3[0] >= location4[0]) barlenght += location3[0];
											else barlenght += location4[0];
											
											barlenght *= 1.04;
											g.setColor(getColor(Integer.parseInt(themeArg[5])));
											
											g.fillRect(reprend, Y + location2[1] / 2 + 1, barlenght, 2);
											
											if(location3[0] >= location4[0]){
												
												centerString(g2d, reprend, Y - location2[1] / 2, Y - location2[1] / 2 + lastY, CodeLineSplit.split("/")[0], new Font(DefaultFont.getFamily(), style, getSize(Integer.parseInt(themeArg[3]), size)));
												
												fullCenterString(g2d, reprend, reprend + location3[0], Y + location2[1] / 2, Y + location2[1] / 2 + lastY, CodeLineSplit.split("/")[1], new Font(DefaultFont.getFamily(), style, getSize(Integer.parseInt(themeArg[3]), size)));
												
											}else{
												
												centerString(g2d, reprend, Y + location2[1] / 2, Y + location2[1] / 2 + lastY, CodeLineSplit.split("/")[1], new Font(DefaultFont.getFamily(), style, getSize(Integer.parseInt(themeArg[3]), size)));
												
												fullCenterString(g2d, reprend, reprend + location4[0], Y - location2[1] / 2, Y - location2[1] / 2 + lastY, CodeLineSplit.split("/")[0], new Font(DefaultFont.getFamily(), style, getSize(Integer.parseInt(themeArg[3]), size)));
											
											}
											reprend += barlenght;
										
										}else{
											
											int[] location3 = centerString(g2d, reprend, Y, Y + lastY, CodeLineSplit + " ", new Font(DefaultFont.getFamily(), style, getSize(Integer.parseInt(themeArg[3]), size)));
											reprend = reprend + location3[0];
										}
									}
									
									
										
								}else remove = false;
							}
							
							
							
							
							lastY = location[1] + 2;
							Y = Y + lastY;
							
						}else{
							if(!CodeLine.isEmpty()){
								
								int reprend = DefaultAlign;
								for(String CodeLineSplit : CodeLine.split(" ")){
										
									g.setColor(Color.WHITE);
									int[] location2 = centerString(g2d, reprend, Y, Y + lastY, CodeLineSplit + " ", DefaultFont);				
									
									g.setColor(DefaultColor);
									
									if(reprend + location2[0] > maxX || CodeLineSplit.contains("/r")){
										
										lastY = location2[1] + 2;
										Y = Y + lastY;
										reprend = DefaultAlign;
										
										int[] location3 = centerString(g2d, reprend, Y, Y + lastY, CodeLineSplit + " ", DefaultFont);			
										reprend = reprend + location3[0];
										
									}else{
										
										if(CodeLineSplit.split("/").length == 2){
											
											g.setColor(Color.WHITE);
											String topbar = CodeLineSplit.split("/")[0];
											String botbar = CodeLineSplit.split("/")[1];
											int[] location3 = centerString(g2d, reprend, Y, Y + lastY, topbar, DefaultFont);
											int[] location4 = centerString(g2d, reprend, Y, Y + lastY, botbar, DefaultFont);
											int barlenght = 0;
											if(location3[0] >= location4[0]) barlenght += location3[0];
											else barlenght += location4[0];
											
											barlenght *= 1.04;
											g.setColor(DefaultColor);
											
											g.fillRect(reprend, Y + location2[1] / 2 + 1, barlenght, 2);
											
											if(location3[0] >= location4[0]){
												
												centerString(g2d, reprend, Y - location2[1] / 2, Y - location2[1] / 2 + lastY, CodeLineSplit.split("/")[0], DefaultFont);
												
												fullCenterString(g2d, reprend, reprend + location3[0], Y + location2[1] / 2, Y + location2[1] / 2 + lastY, CodeLineSplit.split("/")[1], DefaultFont);
												
											}else{
												
												centerString(g2d, reprend, Y + location2[1] / 2, Y + location2[1] / 2 + lastY, CodeLineSplit.split("/")[1], DefaultFont);
												
												fullCenterString(g2d, reprend, reprend + location4[0], Y - location2[1] / 2, Y - location2[1] / 2 + lastY, CodeLineSplit.split("/")[0], DefaultFont);
												
											}			
											reprend += barlenght;
										
										}else{
										
											int[] location3 = centerString(g2d, reprend, Y, Y + lastY, CodeLineSplit + " ", DefaultFont);			
											reprend = reprend + location3[0];
										
										}
									}
										
										
								}
								
								Y = Y + lastY;
								
							}else{
								Y = Y + 8;
							}
							
						}
					}
					i++;
				}
				
				return ReturnCodeLines;
				
			}
			
		}
		
		return null;
		
	}
	
	
	public Color getColor(int section){
		
		if(section == 0){
			return Color.BLACK;
		}else if(section == 1){
			return new Color(230, 0, 0);
		}else if(section == 2){
			return new Color(0, 230, 0);
		}else{
			return new Color(0, 0, 200);
		}
		
	}
	public int getEsp(int section, int DefaultAlign, int size){
		
		return (int) ((section * (20 * size)) / 1.5 + DefaultAlign);
		
	}
	public int getSize(int section, int size){
		
		
		if(section == 0){
			return (int) (12  * size / 1.3);
		}else if(section == 1){
			return (int) (14  * size / 1.3);
		}else if(section == 2){
			return (int) (15  * size / 1.3);
		}else if(section == 3){
			return (int) (16  * size / 1.3);
		}else if(section == 4){
			return (int) (17 * size / 1.3);
		}else if(section == 5){
			return (int) (18 * size / 1.3);
		}else if(section == 6){
			return (int) (19 * size / 1.3);
		}else{
			return (int) (20 * size / 1.3);
		}
		
	}
	
	
	
	
	public int[] centerString(Graphics g, int X, int minY, int maxY, String s, Font font) {
		
		
	    FontRenderContext frc = new FontRenderContext(null, true, true);

	    Rectangle2D r2D = font.getStringBounds(s, frc);
	    int rWidth = (int) Math.round(r2D.getWidth());
	    int rHeight = (int) Math.round(r2D.getHeight());
	    int rY = (int) Math.round(r2D.getY());

	    int b = ((maxY - minY) / 2) - (rHeight / 2) - rY;

	    g.setFont(font);
	    g.drawString(s, X, minY + b);
	    
	    int retur[] = { rWidth, rHeight };
	    
	    return retur;
	}
	
	public int[] fullCenterString(Graphics g, int minX, int maxX, int minY, int maxY, String s, Font font) {
		
		
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
	    
	    int retur[] = { rWidth, rHeight };
	    return retur;
	}
	
	public static boolean getBoolean(String str){
		if(str.equals("true")) return true;
		else return false;
		
	}
	
}
