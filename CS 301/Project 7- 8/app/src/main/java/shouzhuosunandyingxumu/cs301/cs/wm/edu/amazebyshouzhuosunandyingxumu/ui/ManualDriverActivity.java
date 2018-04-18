package shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.os.Vibrator;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.R;
import shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.falstad.*;
import shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.helper.*;




public class ManualDriverActivity extends AppCompatActivity {

    private MazePanel mazePanel;
    private MazeController mazeController;
    private boolean musicPlaying;
    private ImageButton musicPlayButton;
    private ImageButton musicPauseButton;


    /**
     *If back button pressed, AMaze Activity will be displayed (return to title screen).
     */
    @Override
    public void onBackPressed() {
        DataHolder.getSoundPlayer().pauseMusic();
        Intent intent = new Intent(this, AMazeActivity.class);
        startActivity(intent);
    }


    /**
     * take to the succeed activity is succeed, take to the failure page if failed by calling the appropriate activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_driver);
        TextView orientation = findViewById(R.id.textView8);
        orientation.setText("E");
        DataHolder.getSoundPlayer().prepareInGameMusicPlayer(this);
        DataHolder.getSoundPlayer().playMusic();
        musicPlaying = true;
        musicPlayButton = findViewById(R.id.playMusicButton);
        musicPlayButton.setVisibility(View.INVISIBLE);
        musicPauseButton = findViewById(R.id.pauseMusicButton);
        musicPauseButton.setVisibility(View.VISIBLE);
        musicPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(musicPlaying){
                    Toast.makeText(ManualDriverActivity.this,"Music Stopped",Toast.LENGTH_SHORT).show();
                    musicPlayButton.setVisibility(View.VISIBLE);
                    DataHolder.getSoundPlayer().pauseMusic();
                    musicPauseButton.setVisibility(View.INVISIBLE);
                    musicPlaying = false;
                }
            }
        });
        musicPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!musicPlaying){
                    Toast.makeText(ManualDriverActivity.this,"Music Playing",Toast.LENGTH_SHORT).show();
                    musicPauseButton.setVisibility(View.VISIBLE);
                    DataHolder.getSoundPlayer().playMusic();
                    musicPlayButton.setVisibility(View.INVISIBLE);
                    musicPlaying = true;
                }
            }
        });

        mazePanel = (MazePanel)findViewById(R.id.mazePanel);
        mazeController = DataHolder.getMazeController();

        System.out.println("mezeController memory location: " + DataHolder.getMazeController());
        mazePanel.setMazeController(mazeController);
        mazeController.setMazePanel(mazePanel);
        mazeController.notifyViewerRedraw();
    }

    /**
     * If failure, the screen will change to failure screen (FinishFailureActivity), and the device
     * will vibrate for 1 second.
     */
    private void launchActivity() {

        Intent intent = new Intent(this, FinishFailureActivity.class);
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(1000);
        startActivity(intent);
    }

    /**
     * If success, the screen will change to success screen (FinishSucceedActivity).
     */
    private void launchActivity2(){
        Intent intent = new Intent(this, FinishSucceedActivity.class);
        startActivity(intent);
    }

    public void moveForward(View view) {
        Log.v("forward", "forward");
        mazePanel.moveForward();
        Log.v("energy consumption",String.valueOf(mazeController.getEnergyConsumption()));
        if(mazeController.getEnergyConsumption()>= 2996){
            DataHolder.setEnergyConsumption(mazeController.getEnergyConsumption());
            DataHolder.setPathLength(mazeController.getPathLength());
            launchActivity();
        }
        if (mazeController.getState() == Constants.StateGUI.STATE_FINISH) {
            DataHolder.setEnergyConsumption(mazeController.getEnergyConsumption());
            DataHolder.setPathLength(mazeController.getPathLength());
            launchActivity2();

        }
    }
    public void rotateLeft(View view){
        Log.v("left", "left");
        mazePanel.rotateLeft();
        TextView orientation = findViewById(R.id.textView8);
        switch (mazeController.getBasicRobot().getCurrentDirection()){
            case East: orientation.setText("E"); break;
            case West: orientation.setText("W"); break;
            case North: orientation.setText("S"); break;
            default: orientation.setText("N");
        }
        if(mazeController.getEnergyConsumption()>= 2996){
            DataHolder.setEnergyConsumption(mazeController.getEnergyConsumption());
            DataHolder.setPathLength(mazeController.getPathLength());
            launchActivity();
        }
    }
    public void rotateRight(View view){
        Log.v("right", "right");
        mazePanel.rotateRight();
        TextView orientation = findViewById(R.id.textView8);
        switch (mazeController.getBasicRobot().getCurrentDirection()){
            case East: orientation.setText("E"); break;
            case West: orientation.setText("W"); break;
            case North: orientation.setText("S"); break;
            default: orientation.setText("N");
        }
        if(mazeController.getEnergyConsumption()>= 2996){
            DataHolder.setEnergyConsumption(mazeController.getEnergyConsumption());
            DataHolder.setPathLength(mazeController.getPathLength());
            launchActivity();
        }
    }

    public void rotateBackward(View view){
        Log.v("back", "back");
        mazePanel.rotateBackward();
        TextView orientation = findViewById(R.id.textView8);
        switch (mazeController.getBasicRobot().getCurrentDirection()) {
            case East:
                orientation.setText("E");
                break;
            case West:
                orientation.setText("W");
                break;
            case North:
                orientation.setText("S");
                break;
            default:
                orientation.setText("N");
        }
        if (mazeController.getState() == Constants.StateGUI.STATE_FINISH) {
            if(mazeController.getEnergyConsumption()>= 2996){
                launchActivity();
            }
            else{
                launchActivity2();
            }
        }
    }
    public void map(View view){
        Log.v("map","map");
        if(((ToggleButton) view).isChecked()) {
            mazePanel.map();
        } else {
            mazePanel.map();
        }
    }
    public void wholemap(View view){
        Log.v("whole map","whole map");
        if (((ToggleButton)view).isChecked()){
            mazePanel.wholeMap();
        }
        else{
            mazePanel.wholeMap();
        }
    }
    public void showSolution(View view){
        Log.v("show solution","show solution");
        if (((ToggleButton)view).isChecked()){
            mazePanel.showSolution();
        }
        else{
            mazePanel.showSolution();
        }
    }

}
