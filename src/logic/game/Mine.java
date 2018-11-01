package logic.game;

public class Mine {

	private int x,y;
	private int spotValue;
	private boolean isBomb;	
	private boolean uncovered;
	private boolean protect;
	private boolean blewUp;
	private boolean hint;
	private boolean specialProtected;

	public Mine(int x, int y) {
		this.x = x;
		this.y = y;
		isBomb = false;
		uncovered = false;
		protect = false;
		blewUp = false;
		hint = false;
		spotValue = -1;
	}

	public void setBomb(boolean bomb) {
		isBomb = bomb;
	}

	public boolean isBomb() {
		return isBomb;
	}

	public void setProtected(boolean protect) {
		this.protect = protect;
	}

	public boolean isProtected() {
		return protect;
	}

	public void setUncovered(boolean uncovered) {
		this.uncovered = uncovered;
	}

	public boolean uncovered() {
		return uncovered;
	}

	public void setBlewUp(boolean blew) {
		blewUp = blew;
	}

	public boolean blewUp() {
		return blewUp;
	}

	public void setSpotValue(int spv) {
		spotValue = spv;
	}

	public int getSpotValue() {
		return spotValue;
	}

	public boolean isHint() {
		return hint;
	}

	public void setHint(boolean hint) {
		this.hint = hint;
	}

	public boolean isSpecialProtected() {
		return specialProtected;
	}

	public void setSpecialProtected(boolean special) {
		this.specialProtected = special;
	}

	public boolean getAnyProtected() {
		return specialProtected || protect;
	}

	public int x() { return x; }
	public int y() { return y; }
	public void x(int x) { this.x=x; }
	public void y(int y) { this.y=y; }

}