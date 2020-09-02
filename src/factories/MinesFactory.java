package factories;

import models.Mine;
import models.Mines;
import services.OctoCheckService;
import utils.RandomGen;

/**
 * Factory to create an instance of the models.Mines class.
 */
public class MinesFactory {
    private OctoCheckService octoService;

    public MinesFactory(OctoCheckService octoService) {
        this.octoService = octoService;
    }

    /**
     * Create a new field of mines.
     * 
     * @param width the width of the field.
     * @param height the height of the field.
     */
    public Mines create(int width, int height, int numberOfMines) {
        var mines = new Mines(width * height);
        createMines(mines, width, height);
        fillMines(mines, numberOfMines);
        fillNumbers(mines, width);
        return mines;
    }

    private void createMines(Mines mines, int width, int height) {
        for (var y = 0; y < height; y++) {
            for (var x = 0; x < width; x++) {
                mines.add(new Mine(x, y));
            }
        }
    }
    
    private void fillMines(Mines mines, int numberOfMines) {
		for (var i=0; i < numberOfMines; i++) {
			while (true) {
				var randomNumber = RandomGen.getRandomInt(mines.size());
				var mine = mines.get(randomNumber);

				if (!mine.isBomb()) {
					mine.setBomb(true);
					break;
				}
			}
		}
	}

    private void fillNumbers(Mines mines, int width) {
		for (var i=0; i < mines.size(); i++) {
			var mine = mines.get(i);

			// If we are a bomb, we don't need a number.
			if (mine.isBomb()) {
				continue;
			}

            var count = octoService.getPositionsToCheck(i, width, mines.size())
				.stream()
				.map(position -> mines.get(position).isBomb())
				.filter(isBomb -> isBomb)
				.count();

			mine.setSpotValue((int)count);
		}
    }
}
