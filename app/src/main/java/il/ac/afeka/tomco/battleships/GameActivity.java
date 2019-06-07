package il.ac.afeka.tomco.battleships;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import il.ac.afeka.tomco.battleships.logic.Board;
import il.ac.afeka.tomco.battleships.logic.Game;
import il.ac.afeka.tomco.battleships.logic.Ship;
import il.ac.afeka.tomco.battleships.logic.Turn;

public class GameActivity extends AppCompatActivity {
    final static String STRING_KEY = "STRING_KEY";
    final static String BUNDLE_KEY = "BUNDLE_KEY";

    public Ship[] playerShips = {new Ship(5, R.color.ship1),
            new Ship(4, R.color.ship2),
            new Ship(3, R.color.ship3),
            new Ship(3, R.color.ship4),
            new Ship(2, R.color.ship5)};

    public Ship[] opponentShips = {new Ship(5, R.color.ship1),
            new Ship(4, R.color.ship2),
            new Ship(3, R.color.ship3),
            new Ship(3, R.color.ship4),
            new Ship(2, R.color.ship5)};

    private Game mGame;

    private GridView mOpponentGrid;
    private Board mOpponentBoard;

    private GridView mPlayerGrid;
    private Board mPlayerBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
        mGame = new Game();
        mOpponentGrid = findViewById(R.id.opponentBoardView);
        mPlayerGrid = findViewById(R.id.playerBoardView);

        int boardSize = 0;
        Intent intent = getIntent();

        Bundle b = intent.getBundleExtra(MainActivity.BUNDLE_KEY);

        if (b != null) {
            String passedString = b.getString(MainActivity.STRING_KEY);
            if (passedString != null) {
                switch (passedString) {
                    case "EASY":
                        boardSize = 25;
                        mOpponentGrid.setNumColumns(5);
                        mPlayerGrid.setNumColumns(5);
                        break;
                    case "MEDIUM":
                        boardSize = 64;
                        mOpponentGrid.setNumColumns(8);
                        mPlayerGrid.setNumColumns(8);
                        break;
                    case "HARD":
                        boardSize = 100;
                        mOpponentGrid.setNumColumns(10);
                        mPlayerGrid.setNumColumns(10);
                        break;
                }
            }

            mOpponentBoard = new Board(boardSize);
            mGame.setmOpponentBoard(mOpponentBoard);
            mOpponentGrid.setAdapter(new OpponentTileAdapter(getApplicationContext(), mGame.getmOpponentBoard()));
            mOpponentBoard.randomizeBoard(opponentShips);

            mPlayerBoard = new Board(boardSize);
            mGame.setmPlayerBoard(mPlayerBoard);
            mPlayerGrid.setAdapter(new PlayerTileAdapter(getApplicationContext(), mGame.getmPlayerBoard()));
            mPlayerBoard.randomizeBoard(playerShips);

        }

        mOpponentGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if (mGame.getmTurn()==Turn.PLAYER && mGame.playTile(mOpponentBoard, position)) {
                    ((OpponentTileAdapter) mOpponentGrid.getAdapter()).notifyDataSetChanged();
                    ((TextView) findViewById(R.id.turns)).setText(R.string.computerTurn);
                    findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                    Turn turn = mGame.winCheck(mOpponentBoard);
                    mGame.toggleTurn();
                    if (turn != Turn.NONE) {
                        Intent intent = new Intent(GameActivity.this, WinnerActivity.class);
                        Bundle b = new Bundle();
                        b.putString(STRING_KEY, turn.toString());
                        intent.putExtra(BUNDLE_KEY, b);
                        startActivity(intent);
                    }
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mGame.playComputer();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ((PlayerTileAdapter) mPlayerGrid.getAdapter()).notifyDataSetChanged();
                                    ((TextView) findViewById(R.id.turns)).setText(R.string.playersTurn);
                                    findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
                                    Turn turn = mGame.winCheck(mPlayerBoard);
                                    mGame.toggleTurn();
                                    if (turn != Turn.NONE) {
                                        Intent intent = new Intent(GameActivity.this, WinnerActivity.class);
                                        Bundle b = new Bundle();
                                        b.putString(STRING_KEY, turn.toString());
                                        intent.putExtra(BUNDLE_KEY, b);
                                        startActivity(intent);
                                    }
                                }
                            });
                        }
                    });
                    t.start();

                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Restart Game")
                .setMessage("Are you sure you want to restart game?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
