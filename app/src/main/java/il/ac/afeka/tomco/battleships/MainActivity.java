package il.ac.afeka.tomco.battleships;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    final static String STRING_KEY = "STRING_KEY";
    final static String BUNDLE_KEY = "BUNDLE_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
    }

    public void onInstructionButtonCLicked(View view) {
        Intent intent = new Intent(MainActivity.this, InstructionsActivity.class);
        startActivity(intent);
    }

    public void onLevelButtonClicked(View view) {
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
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
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
