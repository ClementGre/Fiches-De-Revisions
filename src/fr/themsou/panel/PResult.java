package fr.themsou.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
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

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import fr.themsou.main.main;

@SuppressWarnings("serial")
public class PResult extends JPanel{
	
	public void paintComponent(Graphics g){
		
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 1500, 10000);
		
		String CodeTxt = main.CodeTextArea.getText();
		if(main.CurrentFile != null){
			if(!CodeTxt.isEmpty()){
				
				String[] CodeLines = CodeTxt.split("\n");
				String ThemeLines[] = new String[100];
				Font DefaultFont = new Font("Ubuntu", 0, 14);
				Color DefaultColor = Color.BLACK;
				int DefaultAlign = 10;
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
					if(line == null) return;
					String DefaultParam[] = line.split(";");
					
					DefaultFont = new Font(DefaultParam[0], 0, getSize(Integer.parseInt(DefaultParam[1])));
					DefaultColor = getColor(Integer.parseInt(DefaultParam[3]));
					DefaultAlign = getEsp(Integer.parseInt(DefaultParam[2]));
					
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
				
				g.setColor(new Color(200, 0, 0));
				fullCenterString(g2d, 0, main.SPaneResult.getWidth(), 0, 50,  main.CurrentFileName.replace(".txt", ""), new Font(DefaultFont.getFamily(), Font.BOLD, 20));
				
				
				int Y = 55;
				int lastY = 20;
				for(String CodeLine : CodeLines){
					
					if(CodeLine.contains("SIZE=") && CodeLine.split("SIZE=").length == 2){
						Image img = new ImageIcon(CodeLine.split("SIZE=")[0]).getImage();
						try{
							g2d.drawImage(img, DefaultAlign, Y + 15, (int) (img.getWidth(null) * Double.parseDouble(CodeLine.split("SIZE=")[1])), (int) (img.getHeight(null) * Double.parseDouble(CodeLine.split("SIZE=")[1])), null);
							Y = Y + (int) (img.getHeight(null) * Double.parseDouble(CodeLine.split("SIZE=")[1])) + 20;
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
							
							int[] location = centerString(g, getEsp(Integer.parseInt(themeArg[6])), Y, Y + lastY, CodeLine.split(" ")[0] + " ", new Font(DefaultFont.getFamily(), style, getSize(Integer.parseInt(themeArg[3]))));				
							
							
							boolean remove = true;
							int reprend = getEsp(Integer.parseInt(themeArg[6])) + location[0];
							for(String CodeLineSplit : CodeLine.split(" ")){
								if(!remove){
									
									g.setColor(Color.WHITE);
									int[] location2 = centerString(g2d, reprend, Y, Y + lastY, (CodeLineSplit + " ").replace("/r", ""), new Font(DefaultFont.getFamily(), style, getSize(Integer.parseInt(themeArg[3]))));				
									
									g.setColor(getColor(Integer.parseInt(themeArg[5])));
									
									if(reprend + location2[0] > main.SPaneResult.getWidth() - 20 || CodeLineSplit.contains("/r")){
										
										lastY = location[1] + 2;
										Y = Y + lastY;
										
										if(Integer.parseInt(themeArg[7]) == 0){
											reprend = getEsp(Integer.parseInt(themeArg[6]));
										}else{
											reprend = getEsp(Integer.parseInt(themeArg[6])) + location[0];
										}
										
										int[] location3 = centerString(g2d, reprend, Y, Y + lastY, (CodeLineSplit + " ").replace("/r", ""), new Font(DefaultFont.getFamily(), style, getSize(Integer.parseInt(themeArg[3]))));
										reprend = reprend + location3[0];
										
									}else{
										
										if(CodeLineSplit.split("/").length == 2){
											
											g.setColor(Color.WHITE);
											String topbar = CodeLineSplit.split("/")[0];
											String botbar = CodeLineSplit.split("/")[1];
											int[] location3 = centerString(g2d, reprend, Y, Y + lastY, topbar, new Font(DefaultFont.getFamily(), style, getSize(Integer.parseInt(themeArg[3]))));
											int[] location4 = centerString(g2d, reprend, Y, Y + lastY, botbar, new Font(DefaultFont.getFamily(), style, getSize(Integer.parseInt(themeArg[3]))));
											int barlenght = 0;
											if(location3[0] >= location4[0]) barlenght += location3[0];
											else barlenght += location4[0];
											
											barlenght *= 1.04;
											g.setColor(getColor(Integer.parseInt(themeArg[5])));
											
											g.fillRect(reprend, Y + location2[1] / 2 + 1, barlenght, 2);
											
											if(location3[0] >= location4[0]){
												
												centerString(g2d, reprend, Y - location2[1] / 2, Y - location2[1] / 2 + lastY, CodeLineSplit.split("/")[0], new Font(DefaultFont.getFamily(), style, getSize(Integer.parseInt(themeArg[3]))));
												
												fullCenterString(g2d, reprend, reprend + location3[0], Y + location2[1] / 2, Y + location2[1] / 2 + lastY, CodeLineSplit.split("/")[1], new Font(DefaultFont.getFamily(), style, getSize(Integer.parseInt(themeArg[3]))));
												
											}else{
												
												centerString(g2d, reprend, Y + location2[1] / 2, Y + location2[1] / 2 + lastY, CodeLineSplit.split("/")[1], new Font(DefaultFont.getFamily(), style, getSize(Integer.parseInt(themeArg[3]))));
												
												fullCenterString(g2d, reprend, reprend + location4[0], Y - location2[1] / 2, Y - location2[1] / 2 + lastY, CodeLineSplit.split("/")[0], new Font(DefaultFont.getFamily(), style, getSize(Integer.parseInt(themeArg[3]))));
											
											}
											reprend += barlenght;
										
										}else{
											
											int[] location3 = centerString(g2d, reprend, Y, Y + lastY, CodeLineSplit + " ", new Font(DefaultFont.getFamily(), style, getSize(Integer.parseInt(themeArg[3]))));			
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
									
									if(reprend + location2[0] > main.SPaneResult.getWidth() - 20 || CodeLineSplit.contains("/r")){
										
										lastY = location2[1] + 2;
										Y = Y + lastY;
										reprend = DefaultAlign;
										
										if(CodeLineSplit.split("/").length == 2){
											
											g.setColor(Color.WHITE);
											String topbar = CodeLineSplit.split("/")[0];
											String botbar = CodeLineSplit.split("/")[1];
											int[] location3 = centerString(g2d, reprend, Y, Y + lastY, topbar, DefaultFont);
											int[] location4 = centerString(g2d, reprend, Y, Y + lastY, botbar, DefaultFont);
											int barlenght = 0;
											if(location3[0] >= location4[0]) barlenght += location3[0];
											else barlenght += location4[0];
											
											barlenght *= 1.05;
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
											
											barlenght *= 1.05;
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
				}
				
				
			}
			if(CodeTxt.isEmpty()){
				try{
					
					FileWriter writer = new FileWriter(main.CurrentFile);
					
					BufferedWriter out = new BufferedWriter(writer);
					out.close();
					
				}catch (IOException e) {e.printStackTrace();}
			}else{
				String[] CodeLines = CodeTxt.split("\n");
				try{
					
					FileWriter writer = new FileWriter(main.CurrentFile);
					
					BufferedWriter out = new BufferedWriter(writer);
					
					for(String Line : CodeLines){
						
						out.write(Line);
						out.newLine();
						
					}
					out.close();
					
				}catch (IOException e) {e.printStackTrace();}
				
			}
			
		}
		
	}
	
	
	public Color getColor(int section){
		
		if(section == 0){
			return Color.BLACK;
		}else if(section == 1){
			return new Color(200, 0, 0);
		}else if(section == 2){
			return new Color(0, 200, 0);
		}else{
			return new Color(0, 0, 200);
		}
		
	}
	public int getEsp(int section){
		
		return section * 20 + 10;
		
	}
	public int getSize(int section){
		
		
		if(section == 0){
			return 12;
		}else if(section == 1){
			return 14;
		}else if(section == 2){
			return 15;
		}else if(section == 3){
			return 16;
		}else if(section == 4){
			return 17;
		}else if(section == 5){
			return 18;
		}else if(section == 6){
			return 19;
		}else{
			return 20;
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
