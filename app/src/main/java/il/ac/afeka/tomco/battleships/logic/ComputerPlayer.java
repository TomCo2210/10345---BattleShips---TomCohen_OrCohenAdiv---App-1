package il.ac.afeka.tomco.battleships.logic;

import java.util.Random;

class ComputerPlayer {
    private void think() {
        Random random = new Random();
        final int MIN = 500, MAX=3500;
        try {
            Thread.sleep(MIN +random.nextInt(MAX-MIN+1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    int playTurn(Board board) {

        think();

        Random random = new Random();
        int positionToPlay;
        do {
            positionToPlay = random.nextInt(board.getBoardSize());
        }
        while (board.getTile(positionToPlay).getStatus() == Board.TileState.HIT || board.getTile(positionToPlay).getStatus() == Board.TileState.MISS);
        return positionToPlay;
    }
}
