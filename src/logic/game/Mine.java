package logic.game;

public class Mine {
	private int x, y;
	private int spotValue;
	private boolean isBomb;
	private boolean uncovered;
	private boolean protect;
	private boolean blewUp;
	private boolean hint;
	private boolean specialProtected;

	public Mine(int xPos, int yPos) {
		x = xPos;
		y = yPos;
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

	public void setProtected(boolean value) {
		protect = value;
	}

	public boolean isProtected() {
		return protect;
	}

	public void setUncovered(boolean value) {
		uncovered = value;
	}

	public boolean uncovered() {
		return uncovered;
	}

	public void setBlewUp(boolean value) {
		blewUp = value;
	}

	public boolean blewUp() {
		return blewUp;
	}

	public void setSpotValue(int value) {
		spotValue = value;
	}

	public int getSpotValue() {
		return spotValue;
	}

	public boolean isHint() {
		return hint;
	}

	public void setHint(boolean value) {
		hint = value;
	}

	public boolean isSpecialProtected() {
		return specialProtected;
	}

	public void setSpecialProtected(boolean special) {
		specialProtected = special;
	}

	public boolean getAnyProtected() {
		return specialProtected || protect;
	}

	public int x() {
		return x;
	}

	public int y() {
		return y;
	}
}