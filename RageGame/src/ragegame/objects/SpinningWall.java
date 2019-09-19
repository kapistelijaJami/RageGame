package ragegame.objects;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import ragegame.Player;
import ragegame.RageGame;
import ragegame.Rect;

public class SpinningWall extends DiagonalWall {
	private double speed;
	private RageGame peli;
	
	public SpinningWall(int x, int y, int width, int height, int kulma, double speed, RageGame peli) {
		super(x, y, width, height, kulma);
		
		this.speed = speed;
		this.peli = peli;
	}
	
	@Override
	public void update() {
		kulma += speed;
		if (kulma >= 180) {
			kulma -= 360;
		} else if (kulma < -180) {
			kulma += 360;
		}
		
		collisionPlayer();
	}

	public void collisionPlayer() {
		Player player = peli.getPlayer();
		
		if (this.intersects(player)) {
			collisionTopRight(player);
			collisionBottomRight(player);
			collisionBottomLeft(player);
			collisionTopLeft(player);
		}
	}
	
	public void collisionTopRight(Player player) {
		double tarkkaX = 0;
		double tarkkaY = 0;
		int playerOriginalX = player.getX();
		int playerOriginalY = player.getY();
		
		while (this.intersects(player.getTopHitbox(), player.getRightHitbox())) {
			double locKulma = kulma;
			if (locKulma < -90) {
				locKulma += 180;
			} else if (locKulma > 90) {
				locKulma -= 180;
			}
			if (locKulma < 0) {
				locKulma = Math.abs(locKulma);
			}
			
			tarkkaX += Math.sin(Math.toRadians(locKulma)) * 1;
			tarkkaY += Math.cos(Math.toRadians(locKulma)) * 1;
			int locX = (int) Math.round(tarkkaX);
			int locY = (int) Math.round(tarkkaY);

			if (locX == 0 && locY == 0) {
				System.out.println("Error, molemmat nolla (oikea ylä)");
				locX = 1;
				locY = 1;
			}

			player.setX(playerOriginalX - locX);
			player.setY(playerOriginalY + locY);
		}
	}

	private void collisionBottomRight(Player player) {
		double tarkkaX = 0;
		double tarkkaY = 0;
		int playerOriginalX = player.getX();
		int playerOriginalY = player.getY();
		
		while (this.intersects(player.getBottomHitbox(), player.getRightHitbox())) {
			double locKulma = kulma;
			if (locKulma < -90) {
				locKulma += 180;
			} else if (locKulma > 90) {
				locKulma -= 180;
			}
			if (locKulma < 0) {
				locKulma = Math.abs(locKulma);
			}

			tarkkaX += Math.sin(Math.toRadians(locKulma)) * 1;
			tarkkaY += Math.cos(Math.toRadians(locKulma)) * 1;
			int locX = (int) Math.round(tarkkaX);
			int locY = (int) Math.round(tarkkaY);

			if (locX == 0 && locY == 0) {
				System.out.println("Error, molemmat nolla (oikea ala)");
				locX = 1;
				locY = 1;
			}
			
			player.setX(playerOriginalX - locX);
			player.setY(playerOriginalY - locY);
		}
	}

	private void collisionBottomLeft(Player player) {
		double tarkkaX = 0;
		double tarkkaY = 0;
		int playerOriginalX = player.getX();
		int playerOriginalY = player.getY();
		
		while (this.intersects(player.getBottomHitbox(), player.getLeftHitbox())) {
			double locKulma = kulma;
			if (locKulma < -90) {
				locKulma += 180;
			} else if (locKulma > 90) {
				locKulma -= 180;
			}
			if (locKulma < 0) {
				locKulma = Math.abs(locKulma);
			}

			tarkkaX += Math.sin(Math.toRadians(locKulma)) * 1;
			tarkkaY += Math.cos(Math.toRadians(locKulma)) * 1;
			int locX = (int) Math.round(tarkkaX);
			int locY = (int) Math.round(tarkkaY);

			if (locX == 0 && locY == 0) {
				System.out.println("Error, molemmat nolla (vasen ala)");
				locX = 1;
				locY = 1;
			}
			
			player.setX(playerOriginalX + locX);
			player.setY(playerOriginalY - locY);
		}
	}

	private void collisionTopLeft(Player player) {
		double tarkkaX = 0;
		double tarkkaY = 0;
		int playerOriginalX = player.getX();
		int playerOriginalY = player.getY();
		
		while (this.intersects(player.getTopHitbox(), player.getLeftHitbox())) {
			double locKulma = kulma;
			if (locKulma < -90) {
				locKulma += 180;
			} else if (locKulma > 90) {
				locKulma -= 180;
			}
			if (locKulma < 0) {
				locKulma = Math.abs(locKulma);
			}

			tarkkaX += Math.sin(Math.toRadians(locKulma)) * 1;
			tarkkaY += Math.cos(Math.toRadians(locKulma)) * 1;
			int locX = (int) Math.round(tarkkaX);
			int locY = (int) Math.round(tarkkaY);

			if (locX == 0 && locY == 0) {
				System.out.println("Error, molemmat nolla (vasen ylä)");
				locX = 1;
				locY = 1;
			}
			
			player.setX(playerOriginalX + locX);
			player.setY(playerOriginalY + locY);
		}
	}
}
