package shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.ui;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import static android.app.PendingIntent.getActivity;
import static android.widget.Toast.*;

import shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.R;
import shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.falstad.*;
import shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.generation.MazeBuilder;
import shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.generation.MazeConfiguration;
import shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.helper.DataHolder;
import shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.helper.SoundHelper;


public class AMazeActivity extends AppCompatActivity {

    private Button LaunchRevisitActivity;
    private Button LaunchExploreActivity;
    private ViewGroup mContentView;
    public String mode;
    private MazeController mazeController;
    private SoundHelper mSoundHelper;
    private ImageButton musicPlayButton;
    private ImageButton musicPauseButton;
    private boolean musicPlaying;
    private String load;

    /**
     * We use method findViewById() to find the spinners and seekbar to set the objects, and
     * use getSelectedItem() to get what user choose when they click on the button. The Log.v()
     * method put the selected items into Logcat, and the makeText method makes a toast to show
     * what user choose. And we call different methods depend on the choice of user.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amaze);

        mSoundHelper = new SoundHelper();
        DataHolder.setSoundPlayer(mSoundHelper);
        DataHolder.setContext(getApplicationContext());
        mSoundHelper.preparePreGameMusicPlayer(this);
        mSoundHelper.playMusic();
        musicPlaying = true;
        musicPlayButton = findViewById(R.id.playMusicButton);
        musicPauseButton = findViewById(R.id.pauseMusicButton);
        musicPlayButton.setVisibility(View.INVISIBLE);
        musicPauseButton.setVisibility(View.VISIBLE);




        //draw background
        getWindow().setBackgroundDrawableResource(R.drawable.little_boy);
        //setup map spinner
        Spinner mapSpinner = findViewById(R.id.map);
        List<String> list = new ArrayList<String>();
        list.add("Backtracking");
        list.add("Prim");
        list.add("Eller");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item,list);
        mapSpinner.setAdapter(adapter);
        //setup mode spinner
        Spinner modeSpinner = findViewById(R.id.mode);
        list = new ArrayList<String>();
        list.add("Manual");
        list.add("WallFollower");
        list.add("Wizard");
        list.add("Pledge");
        list.add("Explorer");
        adapter = new ArrayAdapter<String>(this, R.layout.spinner_item,list);
        modeSpinner.setAdapter(adapter);


        musicPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(musicPlaying){
                    Toast.makeText(AMazeActivity.this,"Music Stopped",Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(AMazeActivity.this,"Music Playing",Toast.LENGTH_SHORT).show();
                    musicPauseButton.setVisibility(View.VISIBLE);
                    DataHolder.getSoundPlayer().playMusic();
                    musicPlayButton.setVisibility(View.INVISIBLE);
                    musicPlaying = true;
                }
            }
        });


        LaunchRevisitActivity = findViewById(R.id.revisit);
        LaunchExploreActivity = findViewById(R.id.explore);
        LaunchRevisitActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SeekBar seekbar = findViewById(R.id.level);
                int level = seekbar.getProgress();
                String str_level = String.valueOf(level);
                if ( 0 <= level && level <= 3) {
                    switch (str_level) {
                        case "0":
                            load = "maze0";
                            break;
                        case "1":
                            load = "maze1";
                            break;
                        case "2":
                            load = "maze2";
                            break;
                        default:
                            load = "maze3";

                    }


                    Spinner spinner = findViewById(R.id.map);
                    String text = spinner.getSelectedItem().toString();
                    Log.v("map", text);
                    Spinner spinner2 = findViewById(R.id.mode);
                    String text2 = spinner2.getSelectedItem().toString();

                    Log.v("mode", text2);
                    mode = text2;


                    Log.v("level", str_level);
                    makeText(AMazeActivity.this, "level: " + (String) str_level + "\nmap: " + (String) text + "\nmode: " + (String) text2,
                            LENGTH_SHORT).show();
                    mazeController = new MazeController(load, getApplicationContext());
                    DataHolder.setBuilderString(text);
                    DataHolder.setRobotDriverString(mode);
                    DataHolder.setDifficultyLevel(level);
                    makeText(AMazeActivity.this, "Load maze from level" + str_level, LENGTH_SHORT).show();

                    mazeController.setSkillLevel(DataHolder.getDifficultyLevel());
                    mazeController.deliver(loadMazeConfigurationFromFile(load));
                    DataHolder.setMazeController(mazeController);

                    launchRevisitActivity();
                }
                else{
                    Spinner spinner = findViewById(R.id.map);
                    String text = spinner.getSelectedItem().toString();
                    Log.v("map",text);
                    Spinner spinner2 = findViewById(R.id.mode);
                    String text2 = spinner2.getSelectedItem().toString();
                    Log.v("mode",text2);
                    mode = text2;


                    Log.v("level", str_level);
                    makeText(AMazeActivity.this, "level: " + (String) str_level + "\nmap: " + (String) text + "\nmode: " + (String) text2,
                            LENGTH_SHORT).show();
                    makeText(AMazeActivity.this, "Level too high to revisit. Generate new maze instead.", LENGTH_SHORT).show();
                    DataHolder.setBuilderString(text);
                    DataHolder.setRobotDriverString(mode);
                    DataHolder.setDifficultyLevel(level);
                    DataHolder.setReadFromFile(false);
                    launchExploreActivity();
                }
            }
        });
        LaunchExploreActivity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Spinner spinner = findViewById(R.id.map);
                String text = spinner.getSelectedItem().toString();
                Log.v("map",text);
                Spinner spinner2 = findViewById(R.id.mode);
                String text2 = spinner2.getSelectedItem().toString();
                Log.v("mode",text2);
                mode = text2;

                SeekBar seekbar = findViewById(R.id.level);
                int level = seekbar.getProgress();
                String str_level = String.valueOf(level);
                Log.v("level", str_level);
                makeText(AMazeActivity.this, "level: " + (String) str_level + "\nmap: " + (String) text + "\nmode: " + (String) text2,
                        LENGTH_SHORT).show();
                DataHolder.setBuilderString(text);
                DataHolder.setRobotDriverString(mode);
                DataHolder.setDifficultyLevel(level);
                DataHolder.setReadFromFile(false);
                launchExploreActivity();
            }
        });
    }


    /**
     * If user selects revisit, we reload the maze from this method. If user choose mode as
     * manual, then we should set the driver to manualDriver here, and should go to ManualDriverActivity
     * after generation. We store the mode into a bundle and pass it to generatingActivity, in which we
     * have to decide whether it will go to PlayActivity or ManualDriverActivity.
     */
    private void launchRevisitActivity() {

        Intent intent = new Intent(this, GeneratingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("Mode", mode);
        intent.putExtras(bundle);
        DataHolder.setReadFromFile(true);
        startActivity(intent);


    }


    /**
     * if user selects explore, we generate the maze from this method. If user choose mode as
     * manual, then we should set the driver to manualDriver here, and should go to ManualDriverActivity
     * after generation. We store the mode into a bundle and pass it to generatingActivity, in which we
     * have to decide whether it will go to PlayActivity or ManualDriverActivity.
     */
    private void launchExploreActivity(){

        Intent intent = new Intent(this, GeneratingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("Mode", mode);
        intent.putExtras(bundle);
        DataHolder.setReadFromFile(false);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
    }
    private MazeConfiguration loadMazeConfigurationFromFile(String filename) {
        // load maze from file
        MazeFileReader mfr = new MazeFileReader(filename,getApplicationContext()) ;
        // obtain MazeConfiguration
        return mfr.getMazeConfiguration();
    }

}