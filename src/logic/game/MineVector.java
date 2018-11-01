package logic.game;

import gui.panel.mines.MineButton;
import logic.game.Mine;
import java.util.Vector;

public class MineVector {

	Vector<Mine> mines;

	public MineVector(int size) {
		mines = new Vector(size);
	}

	public Mine get(int i) {
		return mines.get(i);
	}

	public Mine get(int x, int y) {
		for(int i=0; i<this.size(); i++) {
			if(x == this.get(i).x() &&
			   y == this.get(i).y() ) {
				return this.get(i);
			}
		}
		return null;		
	}

	public void add(Mine mine) {
		mines.add(mine);
	}

	public int size() {
		return mines.size();
	}

	public void clear() {
		mines.clear();
	}

	public int contains(int x, int y) {
		for(int i=0; i<this.size(); i++) {
			if(x == this.get(i).x() &&
			   y == this.get(i).y() ) {
				return i;
			}
		}
		return -1;
	}

	public int capacity() {
		return mines.capacity();
	}

}