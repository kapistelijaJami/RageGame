package ragegame;

import java.awt.Graphics;
import java.util.ArrayList;
import ragegame.traps.Trap;

public class TrapHandler {
	private ArrayList<Trap> lista;
	
	public TrapHandler() {
		this.lista = new ArrayList<>();
	}
	
	public void add(Trap trap) {
		this.lista.add(trap);
	}
	
	public ArrayList<Trap> getList() {
		return lista;
	}
	
	public void update() {
		for (Trap trap : lista) {
			trap.update();
		}
	}
	
	public void render(Graphics g) {
		for (Trap trap : lista) {
			trap.render(g);
		}
	}
}
