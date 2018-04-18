package shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.os.Handler;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.R;
import shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.falstad.*;
import shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.generation.*;
import shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.helper.*;


public class GeneratingActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView text;
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();
    private long fileSize = 0;
    private Thread thread;
    private MazeController mazeController;
    private MazeBuilder mazeBuilder;
    private MazeFactory mazeFactory;
    private MazePanel mazePanel;
    private String manual;
    private BasicRobot robot;
    private String filename;


    /**
     *Generating activity will show the progress bar of generation of maze after user chosed the
     *level, mode, and map of the game. After generating completes, automatically directs user to
     *the playing activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generating);
        if (DataHolder.getReadFromFile() == false) {
            Log.v("write file","here");
            progressBar = findViewById(R.id.progressBar);
            progressBar.setProgress(0);
            progressBar.setMax(100);
            progressBarStatus = 0;
            fileSize = 0;
            robot = new BasicRobot();
            mazeController = new MazeController();
            mazeController.setBasicRobot(robot);
            DataHolder.setMazeController(mazeController);
            if (DataHolder.getBuilderString().equals("Backtracking")) {
                mazeBuilder = new MazeBuilder();
            } else if (DataHolder.getBuilderString().equals("Prim")) {
                mazeBuilder = new MazeBuilderPrim();
            } else {
                mazeBuilder = new MazeBuilderEller();
            }
            if (DataHolder.getRobotDriverString().equals("Manual")) {
                ManualDriver manualDriver = new ManualDriver();
                manualDriver.setRobot(robot);
                mazeController.setDriver(manualDriver);
            } else if (DataHolder.getRobotDriverString().equals("Pledge")) {
                Pledge pledge = new Pledge();
                pledge.setRobot(robot);
                mazeController.setDriver(pledge);
            } else if (DataHolder.getRobotDriverString().equals("WallFollower")) {
                WallFollower wallFollower = new WallFollower();
                wallFollower.setRobot(robot);
                mazeController.setDriver(wallFollower);
            } else if (DataHolder.getRobotDriverString().equals("Wizard")) {
                Wizard wizard = new Wizard();
                wizard.setRobot(robot);
                mazeController.setDriver(wizard);
            } else {
                Explorer explorer = new Explorer();
                explorer.setRobot(robot);
                mazeController.setDriver(explorer);
            }

            //get the mode from the bundle that is passed in through AMazeActivity
            Intent i = getIntent();
            Bundle extra = i.getExtras();
            manual = extra.getString("Mode");
            text = findViewById(R.id.textView);
            text.setTextSize(15);

            robot.setMaze(mazeController);
            mazeController.setSkillLevel(DataHolder.getDifficultyLevel());


            Log.v("text", "difficulty Level: " + DataHolder.getDifficultyLevel());


            thread = new Thread(new Runnable() {
                public void run() {
                    mazeController.setState(Constants.StateGUI.STATE_GENERATING);
                    mazeController.rset = new RangeSet();
                    if (DataHolder.getReadFromFile() == false) {
                        mazeController.factory.order(mazeController);
                        while (progressBarStatus < 100) {

                            Log.v("percentage done", String.valueOf(progressBarStatus));

                            // process some tasks
                            progressBarStatus = mazeController.getPercentDone();

                            // your computer is too fast, sleep 1 second
                            try {
                                thread.sleep(1);
                            } catch (InterruptedException e) {
                                progressBar.setProgress(0);
                                progressBar.setMax(100);
                                progressBarStatus = 0;
                                fileSize = 0;

                                e.printStackTrace();
                                return;
                            }

                            // Update the progress bar
                            progressBarHandler.post(new Runnable() {
                                public void run() {
                                    progressBar.setProgress(progressBarStatus);
                                }
                            });
                        }

                        // ok, file is downloaded,
                        if (progressBarStatus >= 100) {

                            // sleep 2 seconds, so that you can see the 100%
                            try {

                                Log.v("percentage done: ", String.valueOf(progressBarStatus));
                                thread.sleep(1000);
                                robot.setMaze(mazeController);
                                mazeController.getRobotDriver().setDimensions(mazeController.getMazeConfiguration().getWidth(), mazeController.getMazeConfiguration().getHeight());
                                mazeController.getRobotDriver().setDistance(mazeController.getMazeConfiguration().getMazedists());
                                if (mazeController.getSkillLevel() <= 3) {
                                    String filename = "maze" + String.valueOf(mazeController.getSkillLevel());
                                    writeMazeFile(filename);
                                }


                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                    }

                    //if the mode is manual, the screen goes to ManualDriverActivity; goes to PlayActivity otherwise.
                    if (manual.equals("Manual")) {
                        launchManualActivity();
                    } else {
                        launchPlayActivity();
                    }


                }
            });
            if (DataHolder.getReadFromFile() == false) {
                thread.start();
            }
        }

        else{
            Log.v("read from file","go here");

            robot = new BasicRobot();
            mazeController = DataHolder.getMazeController();
            mazeController.setBasicRobot(robot);
            if (DataHolder.getRobotDriverString().equals("Manual")) {
                ManualDriver manualDriver = new ManualDriver();
                manualDriver.setRobot(robot);
                mazeController.setDriver(manualDriver);
            } else if (DataHolder.getRobotDriverString().equals("Pledge")) {
                Pledge pledge = new Pledge();
                pledge.setRobot(robot);
                mazeController.setDriver(pledge);
            } else if (DataHolder.getRobotDriverString().equals("WallFollower")) {
                WallFollower wallFollower = new WallFollower();
                wallFollower.setRobot(robot);
                mazeController.setDriver(wallFollower);
            } else if (DataHolder.getRobotDriverString().equals("Wizard")) {
                Wizard wizard = new Wizard();
                wizard.setRobot(robot);
                mazeController.setDriver(wizard);
            } else {
                Explorer explorer = new Explorer();
                explorer.setRobot(robot);
                mazeController.setDriver(explorer);
            }

            Intent i = getIntent();
            Bundle extra = i.getExtras();
            manual = extra.getString("Mode");
            text = findViewById(R.id.textView);
            text.setTextSize(15);

            robot.setMaze(mazeController);
            mazeController.setSkillLevel(DataHolder.getDifficultyLevel());
            mazeController.getRobotDriver().setDimensions(mazeController.getMazeConfiguration().getWidth(), mazeController.getMazeConfiguration().getHeight());
            mazeController.getRobotDriver().setDistance(mazeController.getMazeConfiguration().getMazedists());
            DataHolder.setMazeController(mazeController);
            if (manual.equals("Manual")){

                launchManualActivity();
            }
            else{
                launchPlayActivity();
            }
        }

    }

    private void writeMazeFile(String filename) {
        File file = new File(getApplicationContext().getFilesDir(), filename);
        MazeFileWriter mazeFileWriter = new MazeFileWriter();
        mazeFileWriter.store(filename, mazeController.getMazeConfiguration().getWidth(),
                mazeController.getMazeConfiguration().getHeight(),0,0,
                mazeController.getMazeConfiguration().getRootnode(),
                mazeController.getMazeConfiguration().getMazecells(),
                mazeController.getMazeConfiguration().getMazedists().getDists(),
                mazeController.getMazeConfiguration().getStartingPosition()[0],
                mazeController.getMazeConfiguration().getStartingPosition()[1],
                DataHolder.getContext()
        );
    }


    /**
     * If mode is not manual, then this method will be called and direct the screen to
     * automatic play screen.
     */
    private void launchPlayActivity() {
        DataHolder.getSoundPlayer().pauseMusic();
        Intent intent = new Intent(this, PlayActivity.class);
        startActivity(intent);
    }

    /**
     * If user select manual mode, then this method will be called and direct the screen to
     * manual play screen.
     */
    private void launchManualActivity(){
        DataHolder.getSoundPlayer().pauseMusic();
        Intent intent = new Intent(this, ManualDriverActivity.class);
        startActivity(intent);
    }

    /**
     *if back button is pressed, game stops generating
     *returns to AMaze activity.
     */
    @Override
    public void onBackPressed() {
        DataHolder.getSoundPlayer().pauseMusic();
        thread.interrupt();
        Intent intent = new Intent(this, AMazeActivity.class);
        startActivity(intent);
    }
}