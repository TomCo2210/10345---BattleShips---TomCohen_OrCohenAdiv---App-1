package il.ac.afeka.tomco.battleships;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WinnerActivity extends AppCompatActivity {
    final static String STRING_KEY = "STRING_KEY";
    final static String BUNDLE_KEY = "BUNDLE_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);
        Intent intent = getIntent();
        Bundle b = intent.getBundleExtra(MainActivity.BUNDLE_KEY);
        String passedString = b.getString(MainActivity.STRING_KEY);
        ((TextView) findViewById(R.id.winnerBanner)).setText("PLAYER".equalsIgnoreCase(passedString) ? "Player Wins!" : "Computer Wins!");
    }

    public void onInstructionButtonCLicked(View view) {
        Intent intent = new Intent(WinnerActivity.this, InstructionsActivity.class);
        startActivity(intent);
    }

    public void onLevelButtonClicked(View view) {
        Intent intent = new Intent(WinnerActivity.this, GameActivity.class);
        Bundle b = new Bundle();
        b.putString(STRING_KEY, view.getTag().toString());
        intent.putExtra(BUNDLE_KEY, b);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exiting Game")
                .setMessage("Are you sure you want to Exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.putExtra("EXIT", true);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}