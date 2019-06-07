package il.ac.afeka.tomco.battleships.logic;

public class Game {

    private Board mOpponentBoard;
    private Board mPlayerBoard;
    private Turn mTurn;
    private ComputerPlayer mComputerPlayer;

    public Board getmOpponentBoard() {
        return mOpponentBoard;
    }

    public void setmOpponentBoard(Board mBoard) {
        this.mOpponentBoard = mBoard;
    }

    public Board getmPlayerBoard() {
        return mPlayerBoard;
    }

    public void setmPlayerBoard(Board mBoard) {
        this.mPlayerBoard = mBoard;
    }

    public Turn getmTurn() {
        return mTurn;
    }

    public Game() {

        mTurn = Turn.PLAYER;
        mComputerPlayer = new ComputerPlayer();
    }

    public void toggleTurn() {

        if (mTurn == Turn.PLAYER)
            mTurn = Turn.COMPUTER;
        else
            mTurn = Turn.PLAYER;

    }

    public boolean playTile(Board mBoard, int position) {
        return mBoard.checkTile(position);
        /*boolean playerPlayed =
        if (playerPlayed) {
            toggleTurn();
        }
         playerPlayed;*/
    }

    public Turn winCheck(Board mBoard) {
        if (mBoard.getShipSunk() == mBoard.getShipsOnBoard())
            return mTurn;
        else
            return Turn.NONE;
    }

    public void playComputer() {

        int positionToPlay = mComputerPlayer.playTurn(mPlayerBoard);
        playTile(mPlayerBoard, positionToPlay);
    }

}
