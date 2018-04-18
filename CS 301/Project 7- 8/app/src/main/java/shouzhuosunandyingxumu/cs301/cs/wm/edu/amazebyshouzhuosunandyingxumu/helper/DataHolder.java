package shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.helper;

import shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.falstad.*;

import android.content.Context;
import android.media.SoundPool;


/**
 * Created by twentyseventeen on 11/22/2017.
 */

public class DataHolder {
    private static MazeController mazeController;
    public static MazeController getMazeController() {return mazeController;}
    public static void setMazeController(MazeController mazeController){DataHolder.mazeController = mazeController;}

    private static String builderString;
    public static String getBuilderString() {return builderString;}
    public static void setBuilderString(String builder) {DataHolder.builderString = builder;}

    private static int difficultyLevel;
    public static int getDifficultyLevel(){return difficultyLevel;}
    public static void setDifficultyLevel(int difficultyLevel){DataHolder.difficultyLevel = difficultyLevel;}

    private static String robotDriverString;
    public static String getRobotDriverString(){return robotDriverString;}
    public static void setRobotDriverString(String robotDriver){DataHolder.robotDriverString = robotDriver;}

    private static float energyConsumption;
    public static float getEnergyConsumption(){return energyConsumption;}
    public static void setEnergyConsumption(float energyConsumption){DataHolder.energyConsumption = energyConsumption;}

    private static float pathLength;
    public static float getPathLength(){return pathLength;}
    public static void setPathLength(float pathLength){DataHolder.pathLength = pathLength;}

    private static boolean readFromFile;
    public static boolean getReadFromFile(){return readFromFile;}
    public static void setReadFromFile(boolean readFromFile){DataHolder.readFromFile = readFromFile;}

    private static SoundHelper soundPlayer;
    public static SoundHelper getSoundPlayer(){return soundPlayer;}
    public static void setSoundPlayer(SoundHelper soundPlayer) {DataHolder.soundPlayer = soundPlayer;}

    private static Context context;
    public static Context getContext(){return context;}
    public static void setContext(Context context){DataHolder.context = context;}
}


