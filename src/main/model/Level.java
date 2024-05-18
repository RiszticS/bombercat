package main.model;

import main.controllers.configuration.ModelProperties;
import main.controllers.game.RenderTimer;
import main.model.fixedElements.*;
import main.model.movingElements.Monster;
import main.model.movingElements.Player;
import main.model.positions.MatrixPosition;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

public class Level {
    private final FixedElement[][] board;
    private final ArrayList<Player> players;
    private final ArrayList<Monster> monsters;
    private final int playerNumberProvidedInTheMenu;
    private int numberOfPlayerPositionsInTheFile;
    private final RenderTimer winCountdown;
    private boolean draw, win, winCheckingInProgress;
    private Player winner;

    public Level(int levelNumber, int playerNumberProvidedInTheMenu, boolean createdLevel) throws IOException {
        this.board = new FixedElement[15][15];
        this.players = new ArrayList<>();
        this.monsters = new ArrayList<>();
        this.playerNumberProvidedInTheMenu = playerNumberProvidedInTheMenu;
        numberOfPlayerPositionsInTheFile = 0;
        this.winCountdown = new RenderTimer(300);
        this.draw = false;
        this.win = false;
        this.winCheckingInProgress = false;
        this.winner = null;

        ArrayList<Chest> chests = new ArrayList<>();
        readLevelFromFile(levelNumber, board, players, monsters, chests,createdLevel);
        allocatePowerUpsToRandomChests(chests);
    }

    private void readLevelFromFile(int levelNumber, FixedElement[][] board, ArrayList<Player> players,
                                   ArrayList<Monster> monsters, ArrayList<Chest> chests, boolean createdLevel) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/main/assets/levels/" + (createdLevel ? "createdLevels/level" + levelNumber : "level" + levelNumber) + ".txt"));

        String currentLine;

        int rowIndex = 0;
        while ((currentLine = reader.readLine()) != null) {
            String[] elements = currentLine.split(";");
            for (int colIndex = 0; colIndex < elements.length; colIndex++) {
                String currentElement = elements[colIndex].trim();

                switch (currentElement) {
                    case "floor":
                        board[rowIndex][colIndex] = new EmptyTile(new MatrixPosition(rowIndex, colIndex), true);
                        break;
                    case "wall":
                        board[rowIndex][colIndex] = new Wall(new MatrixPosition(rowIndex, colIndex),currentElement);
                        break;
                    case "wallDoubleLeft":
                        board[rowIndex][colIndex] = new Wall(new MatrixPosition(rowIndex, colIndex),currentElement);
                        break;
                    case "wallDoubleRight":
                        board[rowIndex][colIndex] = new Wall(new MatrixPosition(rowIndex, colIndex),currentElement);
                        break;
                    case "wallLeft":
                        board[rowIndex][colIndex] = new Wall(new MatrixPosition(rowIndex, colIndex),currentElement);
                        break;
                    case "wallRight":
                        board[rowIndex][colIndex] = new Wall(new MatrixPosition(rowIndex, colIndex),currentElement);
                        break;
                    case "wallWindowLeft":
                        board[rowIndex][colIndex] = new Wall(new MatrixPosition(rowIndex, colIndex),currentElement);
                        break;
                    case "wallWindowRight":
                        board[rowIndex][colIndex] = new Wall(new MatrixPosition(rowIndex, colIndex),currentElement);
                        break;
                    case "wallWindow":
                        board[rowIndex][colIndex] = new Wall(new MatrixPosition(rowIndex, colIndex),currentElement);
                        break;
                    case "wallTop":
                        board[rowIndex][colIndex] = new Wall(new MatrixPosition(rowIndex, colIndex),currentElement);
                        break;
                    case "wallRightCornerBottom":
                        board[rowIndex][colIndex] = new Wall(new MatrixPosition(rowIndex, colIndex),currentElement);
                        break;
                    case "wallRightCornerTop":
                        board[rowIndex][colIndex] = new Wall(new MatrixPosition(rowIndex, colIndex),currentElement);
                        break;
                    case "wallLeftCornerBottom":
                        board[rowIndex][colIndex] = new Wall(new MatrixPosition(rowIndex, colIndex),currentElement);
                        break;
                    case "wallLeftCornerTop":
                        board[rowIndex][colIndex] = new Wall(new MatrixPosition(rowIndex, colIndex),currentElement);
                        break;
                    case "obstacle":
                        board[rowIndex][colIndex] = new Wall(new MatrixPosition(rowIndex, colIndex),currentElement);
                        break;
                    case "player":
                        if (numberOfPlayerPositionsInTheFile < playerNumberProvidedInTheMenu) {
                            players.add(new Player(new MatrixPosition(rowIndex, colIndex), board,numberOfPlayerPositionsInTheFile));
                        }
                        board[rowIndex][colIndex] = new EmptyTile(new MatrixPosition(rowIndex, colIndex), true);
                        numberOfPlayerPositionsInTheFile++;
                        break;
                    case "monster":
                        monsters.add(new Monster(new MatrixPosition(rowIndex, colIndex)));
                        board[rowIndex][colIndex] = new EmptyTile(new MatrixPosition(rowIndex, colIndex), true);
                        break;
                    case "chest":
                        Chest c = new Chest(new MatrixPosition(rowIndex, colIndex));
                        board[rowIndex][colIndex] = c;
                        chests.add(c);
                        break;
                }
            }
            rowIndex++;
        }
    }


    public void update() {
        updateBoard();
        updateMonsters();
        updatePlayers();
        removeDeadMonsters();
        removeDeadPlayers();
        checkForWin();
    }

    private void updateBoard() {
        for (FixedElement[] fixedBoardTiles : board) {
            for (int j = 0; j < board.length; j++) {
                fixedBoardTiles[j].update(board);
            }
        }
    }

    private void updateMonsters() {
        for (Monster monster : monsters) {
            monster.update(board);
        }
    }

    private void updatePlayers() {
        for (Player player : players) {
            player.update(board, monsters);
        }
    }
    private void removeDeadPlayers() {
        Predicate<Player> dead = p -> !p.isAlive();
        players.removeIf( dead );
    }

    private void removeDeadMonsters() {
        Predicate<Monster> dead = p -> !p.isAlive();
        monsters.removeIf( dead );
    }

    private void checkForWin() {
        if (playersAliveCount() == 1) {
            if (!winCheckingInProgress) {
                winCountdown.start();
                winCheckingInProgress = true;
            } else {
                if (winCountdown.finished() && playersAliveCount() == 1) {
                    this.winner = players.getFirst();
                    win = true;
                } else {
                    winCountdown.decrease();
                }
            }
        } else if (players.isEmpty()) {
            draw = true;
        }
    }

    private void allocatePowerUpsToRandomChests(ArrayList<Chest> chests) {
        if (!chests.isEmpty()) {
            ArrayList<PowerUp> powerUps = generateRandomPowerUpsInRange(chests.size(), chests.size());

            ArrayList<Integer> alreadyPickedIndices = new ArrayList<>();
            for (PowerUp p : powerUps) {
                int randomChestIndex = ThreadLocalRandom.current().nextInt(0, chests.size());
                while (alreadyPickedIndices.contains(randomChestIndex)) {
                    randomChestIndex = ThreadLocalRandom.current().nextInt(0, chests.size());
                }
                chests.get(randomChestIndex).addPowerUp(p);
                alreadyPickedIndices.add(randomChestIndex);
            }
        }
    }


    /**
     * Generates a random number of PowerUp objects of random PowerUp subtypes within the given range
     * and returns them in an ArrayList<>.
     * IMPORTANT: When new power up types are added, make sure to update the
     * number_of_types_of_power_ups attribute in the model.properties file, otherwise, the function is
     * going to ignore the new type. Obviously, also add a new case to the switch statement.
     * @param min An integer that represents the minimum number of power ups to be generated.
     * @param max An integer that represents the maximum number of power ups to be generated (inclusive).
     * @return An ArrayList of PowerUp objects.
     */
    private ArrayList<PowerUp> generateRandomPowerUpsInRange(int min, int max) {
        int numOfPowerUps = ThreadLocalRandom.current().nextInt(min, max + 1);
        ArrayList<PowerUp> powerUps = new ArrayList<>();
        for (int i = 0; i < numOfPowerUps; i++) {
            int typeOfPowerUp = ThreadLocalRandom.current().nextInt(1,
                    ModelProperties.getNumberOfTypesOfPowerUps() + 1);
            switch (typeOfPowerUp) {
                case 1:
                    powerUps.add(new PowerUpBombRange(new MatrixPosition(0,0)));
                    break;
                case 2:
                    powerUps.add(new PowerUpPlusBomb(new MatrixPosition(0, 0)));
                    break;
                case 3:
                    powerUps.add(new PowerUpBombBlocker(new MatrixPosition(0, 0)));
                    break;
                case 4:
                    powerUps.add(new PowerUpRangeReducer(new MatrixPosition(0, 0)));
                    break;
                case 5:
                    powerUps.add(new PowerUpSpeedReducer(new MatrixPosition(0, 0)));
                    break;
                case 6:
                    powerUps.add(new PowerUpInstantPlacement(new MatrixPosition(0, 0)));
                    break;
            }
        }
        return powerUps;
    }

    public boolean isDraw() {
        return draw;
    }

    public boolean isWin() {
        return win;
    }

    public Player getWinner() {
        return winner;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    private int playersAliveCount() {
        int alive = 0;
        for (Player p : players) {
            if (p.isAlive()) {
                alive++;
            }
        }
        return alive;
    }
}
