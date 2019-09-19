package ragegame;

import ragegame.objects.Wall;
import java.awt.Graphics;
import java.util.ArrayList;

public class ObjectHandler {
	private ArrayList<Wall> lista;
	
	public ObjectHandler() {
		this.lista = new ArrayList<>();
	}
	
	public void add(Wall wall) {
		this.lista.add(wall);
	}
	
	public ArrayList<Wall> getList() {
		return lista;
	}
	
	public void update() {
		for (Wall wall : lista) {
			wall.update();
		}
	}
	
	public void render(Graphics g) {
		for (Wall wall : lista) {
			wall.render(g);
		}
	}
}
