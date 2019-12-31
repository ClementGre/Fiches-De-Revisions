package fr.themsou.devices;

import java.awt.event.KeyEvent;

public class KeyListener implements java.awt.event.KeyListener{

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println(e.getKeyCode());
		
	}@Override
	public void keyReleased(KeyEvent e) {
		System.out.println(e.getKeyCode());
		
	}@Override
	public void keyTyped(KeyEvent e) {
		System.out.println(e.getKeyCode());
	}
}
