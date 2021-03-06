package shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.R;
import shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.helper.DataHolder;

public class FinishSucceedActivity extends AppCompatActivity {

    /**
     *If directed to this activity, means the gamed had successfully exited the maze
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_succeed);
        TextView energyConsumption = (TextView) findViewById(R.id.textView14);
        DataHolder.getSoundPlayer().pauseMusic();
        DataHolder.getSoundPlayer().prepareWinningMusicPlayer(this);
        DataHolder.getSoundPlayer().playMusic();
        energyConsumption.setText(String.valueOf(DataHolder.getEnergyConsumption()));
        TextView pathLength = (TextView) findViewById(R.id.textView15);
        pathLength.setText(String.valueOf(DataHolder.getPathLength()));
    }

    /**
     *If back button pressed, AMaze Activity will be displayed (return to title screen).
     */
    @Override
    public void onBackPressed() {
        DataHolder.getSoundPlayer().pauseMusic();
        Intent intent = new Intent(this, AMazeActivity.class);
        startActivity(intent);
    }
}
