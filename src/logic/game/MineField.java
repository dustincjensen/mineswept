package logic.game;

import logic.util.RandomGen;
import logic.files.Preferences;
import gui.panel.header.ResetButton;

public class MineField {

	private static int currentPuzzle;
	private static int nextPuzzle;
	private static boolean isCustom;
	private static MineVector mines;

	private static final int[] w = {9, 16, 30};
	private static final int[] h = {9, 16, 16};
	private static final int[] numMines = {10, 40, 99};

	private static int customW = 16;
	private static int customH = 30;
	private static int customNumMines = 99;

	public static void init() {
		nextPuzzle = -1;
		isCustom = false;
		mines = new MineVector(w[2]*h[2]);
		setupField();
	}

	public static void setupField() {
		initMines();
		fillMines();
		fillNumbers();
	}

	public static void initMines() {
		for(int y=0; y<h[currentPuzzle]; y++) {
			for(int x=0; x<w[currentPuzzle]; x++) {
				mines.add(new Mine(x,y));
			}
		}
	}//End initMines

	public static void fillMines() {
		int randomLen = numMines[currentPuzzle];
		for(int i=0; i<randomLen; i++) {
			boolean set = false;
			do {
				int rand = RandomGen.getRandomInt(mines.size());
				if(!mines.get(rand).isBomb()) {
					mines.get(rand).setBomb(true);
					set = true;
				}
			} while(!set);
		}
	}//End fillMines

	public static void fillNumbers() {
		for(int i=0; i<mines.size(); i++) {
			if(mines.get(i).isBomb())
				continue;
			int count = 0;

			try { if(mines.get(i-w[currentPuzzle]  ).isBomb()) count++; } catch (Exception e) {} //ABOVE
			try { if(mines.get(i+w[currentPuzzle]  ).isBomb()) count++; } catch (Exception e) {} //BELOW
			if(i%w[currentPuzzle]!=0) { //if you aren't a left side spot
				try { if(mines.get(i-w[currentPuzzle]-1).isBomb()) count++; } catch (Exception e) {} //ABOVE LEFT	
				try { if(mines.get(i-1                 ).isBomb()) count++; } catch (Exception e) {} //LEFT
				try { if(mines.get(i+w[currentPuzzle]-1).isBomb()) count++; } catch (Exception e) {} //BELOW LEFT
			}
			if((i+1)%w[currentPuzzle]!=0) { //if you aren't a right side spot
				try { if(mines.get(i-w[currentPuzzle]+1).isBomb()) count++; } catch (Exception e) {} //ABOVE RIGHT
				try { if(mines.get(i+1                 ).isBomb()) count++; } catch (Exception e) {} //RIGHT
				try { if(mines.get(i+w[currentPuzzle]+1).isBomb()) count++; } catch (Exception e) {} //BELOW RIGHT
			}
			mines.get(i).setSpotValue(count);
		}
	}//End fillNumbers

	public static void reset() {
		mines.clear();
		if(nextPuzzle != -1) {
			currentPuzzle = nextPuzzle;
			nextPuzzle = -1;
		}
		setupField();
	}

	public static void leftClicked(int x, int y) {
		int i = mines.contains(x,y);

		if(mines.get(i).uncovered()) specialUncover(i); //if already uncovered return
		if(mines.get(i).getAnyProtected()) return;		//if protected by a flag return
		if(mines.get(i).isBomb()) {
			uncoverAll();						//if bomb uncover all
			mines.get(i).setBlewUp(true);		//record the cause of the failure
		}
		else uncover(i);						//otherwise uncover square(s)
		checkGameCondition();
	}

	public static Mine rightClicked(int x, int y) {
		return mines.get(x,y);
	}

	public static void specialUncover(int i) {
		Mine now = mines.get(i);
		int flagCount = 0;
		
		try { if(mines.get(i-w[currentPuzzle]).getAnyProtected()) flagCount++; } catch (Exception e) {} //ABOVE
		try { if(mines.get(i+w[currentPuzzle]).getAnyProtected()) flagCount++; } catch (Exception e) {} //BELOW
		if(i%w[currentPuzzle]!=0) { //if you aren't a left side spot
			try { if(mines.get(i-w[currentPuzzle]-1).getAnyProtected()) flagCount++; } catch (Exception e) {} //ABOVE LEFT	
			try { if(mines.get(i-1).getAnyProtected())                  flagCount++; } catch (Exception e) {} //LEFT
			try { if(mines.get(i+w[currentPuzzle]-1).getAnyProtected()) flagCount++; } catch (Exception e) {} //BELOW LEFT
		}
		if((i+1)%w[currentPuzzle]!=0) { //if you aren't a right side spot
			try { if(mines.get(i-w[currentPuzzle]+1).getAnyProtected()) flagCount++; } catch (Exception e) {} //ABOVE RIGHT
			try { if(mines.get(i+1).getAnyProtected())                  flagCount++; } catch (Exception e) {} //RIGHT
			try { if(mines.get(i+w[currentPuzzle]+1).getAnyProtected()) flagCount++; } catch (Exception e) {} //BELOW RIGHT
		}

		/* If the spot you clicked has appropriate flags marked do the uncovering */
		if(flagCount >= now.getSpotValue()) {
			try { specialUncoverOne(i-w[currentPuzzle]); } catch (Exception e) {} //ABOVE
			try { specialUncoverOne(i+w[currentPuzzle]); } catch (Exception e) {} //BELOW
			if(i%w[currentPuzzle]!=0) { //if you aren't a left side spot
				try { specialUncoverOne(i-w[currentPuzzle]-1);   } catch (Exception e) {} //ABOVE LEFT	
				try { specialUncoverOne(i-1);                    } catch (Exception e) {} //LEFT
				try { specialUncoverOne(i+w[currentPuzzle]-1);   } catch (Exception e) {} //BELOW LEFT
			}
			if((i+1)%w[currentPuzzle]!=0) { //if you aren't a right side spot
				try { specialUncoverOne(i-w[currentPuzzle]+1); } catch (Exception e) {} //ABOVE RIGHT
				try { specialUncoverOne(i+1);                  } catch (Exception e) {} //RIGHT
				try { specialUncoverOne(i+w[currentPuzzle]+1); } catch (Exception e) {} //BELOW RIGHT
			}
		}

	}

	public static void specialUncoverOne(int i) {
		Mine now = mines.get(i);
		if(now.isBomb() && !now.getAnyProtected()) {
			mines.get(i).setBlewUp(true);
			uncoverAll();
			return;
		}
		if(now.uncovered()) 
			return;

		if(now.getSpotValue() == 0) {
			if(!now.getAnyProtected())
				now.setUncovered(true);
			try { specialUncoverOne(i-w[currentPuzzle]); } catch (Exception e) {} //ABOVE
			try { specialUncoverOne(i+w[currentPuzzle]); } catch (Exception e) {} //BELOW
			if(i%w[currentPuzzle]!=0) { //if you aren't a left side spot
				try { specialUncoverOne(i-w[currentPuzzle]-1);   } catch (Exception e) {} //ABOVE LEFT	
				try { specialUncoverOne(i-1);                    } catch (Exception e) {} //LEFT
				try { specialUncoverOne(i+w[currentPuzzle]-1);   } catch (Exception e) {} //BELOW LEFT
			}
			if((i+1)%w[currentPuzzle]!=0) { //if you aren't a right side spot
				try { specialUncoverOne(i-w[currentPuzzle]+1); } catch (Exception e) {} //ABOVE RIGHT
				try { specialUncoverOne(i+1);                  } catch (Exception e) {} //RIGHT
				try { specialUncoverOne(i+w[currentPuzzle]+1); } catch (Exception e) {} //BELOW RIGHT
			}
		} else
			if(!now.getAnyProtected())
				now.setUncovered(true);		
	}

	public static void uncoverAll() {
		for(int i=0; i<mines.size(); i++) {
			if( (mines.get(i).isBomb() && !mines.get(i).getAnyProtected()) ||
			    (mines.get(i).getAnyProtected() && !mines.get(i).isBomb()) )
				mines.get(i).setUncovered(true);
		}
		GameFeatures.setGameOver(true);
		ClockTimer.stop();
		ResetButton.setSmileyIcon(2);
	}

	public static void uncover(int i) {
		Mine now = mines.get(i);
		if(now.isBomb())
			return;
		if(now.uncovered())
			return;

		if(now.getSpotValue() == 0) {
			if(!now.getAnyProtected())
				now.setUncovered(true);
			try { uncover(i-w[currentPuzzle]); } catch (Exception e) {} //ABOVE
			try { uncover(i+w[currentPuzzle]); } catch (Exception e) {} //BELOW
			if(i%w[currentPuzzle]!=0) { //if you aren't a left side spot
				try { uncover(i-w[currentPuzzle]-1);   } catch (Exception e) {} //ABOVE LEFT	
				try { uncover(i-1);                    } catch (Exception e) {} //LEFT
				try { uncover(i+w[currentPuzzle]-1);   } catch (Exception e) {} //BELOW LEFT
			}
			if((i+1)%w[currentPuzzle]!=0) { //if you aren't a right side spot
				try { uncover(i-w[currentPuzzle]+1); } catch (Exception e) {} //ABOVE RIGHT
				try { uncover(i+1);                  } catch (Exception e) {} //RIGHT
				try { uncover(i+w[currentPuzzle]+1); } catch (Exception e) {} //BELOW RIGHT
			}
		} else
			if(!now.getAnyProtected())
				now.setUncovered(true);
	}

	public static void checkGameCondition() {
		int uncoveredPieces = 0;
		boolean bombBlew = false;
		for(int i=0; i<mines.size(); i++) {
			if(mines.get(i).blewUp())
				bombBlew = true;
			if(mines.get(i).uncovered())
				uncoveredPieces++;
		}

		//Winning condition
		if(uncoveredPieces == (w[currentPuzzle]*h[currentPuzzle])-numMines[currentPuzzle]
			&& !bombBlew)
		{
			GameFeatures.setGameOver(true);
			ClockTimer.stop();
			ResetButton.setSmileyIcon(4);
		}
	}

	public static void getHint() {
		// Get empty spaces first
		boolean emptySpace = false;
		for (int i=0; i<mines.size(); i++) {
			Mine m = mines.get(i);

			// Empty space
			if (m.getSpotValue() == 0 && !m.uncovered()) {
				m.setHint(true);
				m.setProtected(false);
				emptySpace = true;
				break;
			}
		}

		// if we found an empty space return OTHERWISE
		if (emptySpace)
			return;

		// Find bombs ...
		// will use a blue flag and make it unchangeable
		for (int i=0; i<mines.size(); i++) {
			Mine m = mines.get(i);

			if (m.isBomb() && !m.getAnyProtected()) {
				m.setHint(true);
				m.setSpecialProtected(true);
				break;
			}
		}
	}// End getHint

	public static Mine getMine(int x, int y) {
		return mines.get(x,y);
	}

	public static int getMineCount() {
		int count = 0;
		for(int i=0; i<mines.size(); i++) {
			if(mines.get(i).getAnyProtected()) count++;
		}
		return getNumMines()-count;
	}

	public static void setCustomWidth(int w) {
		customW = w;
	}

	public static void setCustomHeight(int h) {
		customH = h;
	}

	public static void setCustomNumMines(int numMines) {
		customNumMines = numMines;
	}

	public static void setCurrentPuzzle(int puzzle) {
		currentPuzzle = puzzle;
	}

	public static void setNextPuzzle(int puzzle) {
		nextPuzzle = puzzle;
	}

	public static void setIsCustom(boolean custom) {
		isCustom = custom;
	}

	public static int getWidth() {
		if(!isCustom)
			return w[currentPuzzle];
		return customW;
	}

	public static int getHeight() {
		if(!isCustom)
			return h[currentPuzzle];
		return customH;
	}

	public static int getNumMines() {
		if(!isCustom)
			return numMines[currentPuzzle];
		return customNumMines;
	}

	public static int getCurrentPuzzle() {
		return currentPuzzle;
	}

	public static boolean isCustom() {
		return isCustom;
	}

	public static void parseDifficulty(String d) {
		d = d.toLowerCase();
		if(d.matches("easy")) setCurrentPuzzle(0);
		else if(d.matches("medium")) setCurrentPuzzle(1);
		else if(d.matches("hard")) setCurrentPuzzle(2);
		else {
			setCurrentPuzzle(0);
			Preferences.writeWarning("Possible Corrupted Preferences File:\n  Difficulty is not set to easy, medium, hard or custom.");
			return;
		}
		//else if(d.matches("custom")) setCurrentPuzzle(3);
	}

}