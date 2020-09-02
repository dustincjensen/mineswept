package models;

public class Mine {
	private int x, y;
	private int spotValue;
	private boolean isBomb;
	private boolean uncovered;
	private boolean isProtected;
	private MineState mineState;
	private boolean blewUp;

	public Mine(int xPos, int yPos) {
		x = xPos;
		y = yPos;
		isBomb = false;
		uncovered = false;
		isProtected = false;
		mineState = MineState.Empty;
		blewUp = false;
		spotValue = -1;
	}

	public void setBomb(boolean bomb) {
		isBomb = bomb;
	}

	public boolean isBomb() {
		return isBomb;
	}

	// TODO re-evaluate
	public MineState updateMineState() {
		if (mineState == MineState.Empty) {
			setProtected(true);
			return mineState = MineState.Flag;
		}

		setProtected(false);
		if (mineState == MineState.Flag) {
			return mineState = MineState.QuestionMark;
		}
		return mineState = MineState.Empty;
	}

	public MineState getMineState() {
		return mineState;
	}

	public void setProtected(boolean value) {
		isProtected = value;
	}

	public boolean isProtected() {
		return isProtected;
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

	public int x() {
		return x;
	}

	public int y() {
		return y;
	}
}