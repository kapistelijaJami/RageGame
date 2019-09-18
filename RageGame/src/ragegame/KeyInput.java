package ragegame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
	private Player player;
	private RageGame peli;
	
	public KeyInput(Player player, RageGame peli) {
		this.player = player;
		this.peli = peli;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_A) {
			player.setIsPressedA(true);
		} else if (key == KeyEvent.VK_D) {
			player.setIsPressedD(true);
		} else if (key == KeyEvent.VK_W) {
			player.jump();
		} else if (key == KeyEvent.VK_S) {
			player.setIsSmall(true);
		} else if (key == KeyEvent.VK_SPACE) {
			player.jump();
		} else if (key == KeyEvent.VK_ENTER) {
			peli.restartLevel();
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_A) {
			player.setIsPressedA(false);
		} else if (key == KeyEvent.VK_D) {
			player.setIsPressedD(false);
		} else if (key == KeyEvent.VK_W) {
			
		} else if (key == KeyEvent.VK_S) {
			player.setIsSmall(false);
		}
	}
}
