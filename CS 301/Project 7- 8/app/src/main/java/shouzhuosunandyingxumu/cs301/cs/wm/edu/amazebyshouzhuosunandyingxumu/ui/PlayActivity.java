package shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

import shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.R;
import shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.falstad.BasicRobot;
import shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.falstad.Explorer;
import shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.falstad.MazeController;
import shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.falstad.MazePanel;
import shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.falstad.Pledge;
import shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.falstad.Robot;
import shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.falstad.WallFollower;
import shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.falstad.Wizard;
import shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.generation.CardinalDirection;
import shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.generation.Cells;
import shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.generation.SingleRandom;
import shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.helper.DataHolder;

import static shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.R.layout.activity_play;

public class PlayActivity extends AppCompatActivity {

    private Button mBtLaunchActivity;
    private Button mBtLaunchActivity2;
    private MazePanel mazePanel;
    private MazeController mazeController;
    private Handler handler = new Handler();
    int distanceToLeft;
    int distanceToFront;
    private boolean musicPlaying;
    private ImageButton musicPlayButton;
    private ImageButton musicPauseButton;
    BasicRobot robot;
    int counter2 = 0;
    int counter = 0;
    Cells cells;
    int[][] visits;
    List<List<int[]>> mainList = new ArrayList<List<int[]>>();
    List<int[]> doorList = new ArrayList<int[]>();
    SingleRandom random = SingleRandom.getRandom();

    /**
     * If back button is pressed, takes to the AMaze activity page during the game
     */
    @Override
    public void onBackPressed() {
        DataHolder.getSoundPlayer().pauseMusic();
        onStop();
        Intent intent = new Intent(this, AMazeActivity.class);
        startActivity(intent);
    }


    /**
     *take to the succeed activity is succeed, take to the failure page if failed by calling the appropriate activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_play);

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
                    Toast.makeText(PlayActivity.this,"Music Stopped",Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(PlayActivity.this,"Music Playing",Toast.LENGTH_SHORT).show();
                    musicPauseButton.setVisibility(View.VISIBLE);
                    DataHolder.getSoundPlayer().playMusic();
                    musicPlayButton.setVisibility(View.INVISIBLE);
                    musicPlaying = true;
                }
            }
        });


        mazePanel = (MazePanel)findViewById(R.id.mazePanel);
        mazeController = DataHolder.getMazeController();
        mazePanel.setMazeController(mazeController);
        mazeController.setMazePanel(mazePanel);
        robot = mazeController.getBasicRobot();
        cells = mazeController.getMazeConfiguration().getMazecells();

        initVisits();
        try {
            System.out.println("first notifyViewerRedraw");
            mazeController.notifyViewerRedraw() ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        final Vibrator v = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);



    }

    public void pauseStart(View view){
        if(((ToggleButton) view).isChecked()) {
            handler.post(robotRun);
        } else {
            handler.removeCallbacks(robotRun);
        }


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
    private Runnable robotRun = new Runnable() {
        @Override
        public void run() {
            if (mazeController.getRobotDriver().getClass() == WallFollower.class){


                if(robot.getBatteryLevel()>=1) {
                    distanceToLeft = robot.distanceToObstacle(Robot.Direction.LEFT);
                }

                if(robot.getBatteryLevel()>=1) {
                    distanceToFront = robot.distanceToObstacle(Robot.Direction.FORWARD);
                }

                if (robot.getOdometerReading() == 0 && distanceToLeft != 0){
                    robot.rotate(Robot.Turn.LEFT);
                    robot.move(robot.distanceToObstacle(Robot.Direction.FORWARD),true);
                    robot.rotate(Robot.Turn.RIGHT);
                }

                else if(distanceToLeft == 0) {
                    if(distanceToFront == 0) {
                        if(robot.getBatteryLevel()>=3) {
                            robot.rotate(Robot.Turn.RIGHT);
                        }
                    }
                    else {
                        if(robot.getBatteryLevel()>=5) {
                            robot.move(1, true);
                            System.out.println("move mvoe moe");
                        }
                    }
                }

                //if distance to left is not zero, then turn left and follow the wall
                else {
                    if(robot.getBatteryLevel()>=3) {
                        robot.rotate(Robot.Turn.LEFT);
                    }

                    if(robot.getBatteryLevel()>=5) {
                        robot.move(1, true);
                    }
                }


            }

            if (mazeController.getRobotDriver().getClass() == Wizard.class){
                int[] projectPosition;
                if (robot.getBatteryLevel() >= 3) {
                    try {
                        projectPosition = mazeController.getMazeConfiguration().getNeighborCloserToExit(robot.getCurrentPosition()[0], robot.getCurrentPosition()[1]);
                        CardinalDirection projectCD = CardinalDirection.getDirection(projectPosition[0] - robot.getCurrentPosition()[0],projectPosition[1] - robot.getCurrentPosition()[1]);
                        performRotation(robot.getCurrentDirection(),projectCD);
                        if (robot.getBatteryLevel() >= 5) {
                            robot.move(1, true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            if (mazeController.getRobotDriver().getClass() == Pledge.class) {
                if (counter == 0) {
                    if (robot.distanceToObstacle(Robot.Direction.FORWARD) == 0) {
                        robot.rotate(Robot.Turn.LEFT);
                        counter -= 1;
                    } else {

                        robot.move(1, true);
                    }
                } else if (counter < 0) {
                    if (robot.distanceToObstacle(Robot.Direction.RIGHT) != 0) {
                        robot.rotate(Robot.Turn.RIGHT);
                        counter += 1;
                        robot.move(1, true);
                    } else if (robot.distanceToObstacle(Robot.Direction.FORWARD) != 0) {
                        robot.move(1, true);
                    } else {
                        robot.rotate(Robot.Turn.LEFT);
                        counter -= 1;
                    }
                } else if (counter > 0) {
                    if (robot.distanceToObstacle(Robot.Direction.LEFT) != 0) {
                        robot.rotate(Robot.Turn.LEFT);
                        counter -= 1;
                        robot.move(1, true);
                    } else if (robot.distanceToObstacle(Robot.Direction.FORWARD) != 0) {
                        robot.move(1, true);
                    } else {
                        robot.rotate(Robot.Turn.RIGHT);
                        counter += 1;
                    }
                }
            }

            if (mazeController.getRobotDriver().getClass() == Explorer.class) {

                try {
                    visits[robot.getCurrentPosition()[0]][robot.getCurrentPosition()[1]]++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                    if(!robot.isInsideRoom()) {
                        try {
                            rotateToNextMove(robot.getCurrentPosition(),robot.getCurrentDirection());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        //find the position of the four corners of the room
                        int xRight = 0;
                        try {
                            xRight = findBorderOfRoom1(robot.getCurrentPosition()[0], robot.getCurrentPosition()[1]);
                            int xLeft = findBorderOfRoom2(robot.getCurrentPosition()[0], robot.getCurrentPosition()[1]);
                            int yUp = findBorderOfRoom3(robot.getCurrentPosition()[0], robot.getCurrentPosition()[1]);
                            int yDown = findBorderOfRoom4(robot.getCurrentPosition()[0], robot.getCurrentPosition()[1]);
                            //add all the doors in the south/north side to the doorList
                            for (int a = xLeft; a <= xRight; a ++) {
                                if (cells.hasNoWall(a, yDown, CardinalDirection.North)) {
                                    int[] door = {a, yDown, 0};
                                    if (checkIfInList(mainList, door)) {
                                        doorList.add(door);
                                    }
                                    else {
                                        doorList = mainList.get(indexOfDoorList(mainList, door));
                                    }
                                }
                                if (cells.hasNoWall(a, yUp, CardinalDirection.South)) {
                                    int[] door = {a, yUp, 0};
                                    if (checkIfInList(mainList, door)) {
                                        doorList.add(door);
                                    }
                                    else {
                                        doorList = mainList.get(indexOfDoorList(mainList, door));
                                    }
                                }
                            }
                            //add all the door on the east/west side to the doorList
                            for (int b = yDown; b <= yUp; b ++) {
                                if (cells.hasNoWall(xLeft, b, CardinalDirection.West)) {
                                    int[] door = {xLeft, b, 0};
                                    if (checkIfInList(mainList, door)) {
                                        doorList.add(door);
                                    }
                                    else {
                                        doorList = mainList.get(indexOfDoorList(mainList, door));
                                    }
                                }
                                if (cells.hasNoWall(xRight, b, CardinalDirection.East)) {
                                    int[] door = {xRight, b, 0};
                                    if (checkIfInList(mainList, door)) {
                                        doorList.add(door);
                                    }
                                    else {
                                        doorList = mainList.get(indexOfDoorList(mainList, door));
                                    }
                                }
                            }
                            if (addDoorListToMainList(mainList, doorList)) {
                                mainList.add(doorList);
                            }

                            //add 1 to the door where it went through in
                            for (int j = 0; j < doorList.size(); j ++) {
                                if (doorList.get(j)[0] == robot.getCurrentPosition()[0] && doorList.get(j)[1] == robot.getCurrentPosition()[1]) {
                                    doorList.get(j)[2] += 1;
                                    break;
                                }
                            }
                            //select the door to escape room
                            int min = doorList.get(0)[2];

                            for (int m = 0; m < doorList.size(); m++) {
                                if (doorList.get(m)[2] < min){
                                    min = doorList.get(m)[2];
                                }
                            }
                            List<int[]> doorUsageList = new ArrayList<int[]>(doorList);
                            for (int n = 0; n < doorUsageList.size(); n ++) {
                                if (doorUsageList.get(n)[2] > min) {
                                    doorUsageList.remove(n);
                                    n -= 1;
                                }
                            }

                            int r = random.nextIntWithinInterval(0, doorUsageList.size() - 1);
                            int q = doorList.indexOf(doorUsageList.get(r));

                            //move robot to the selected door
                            int doorx = doorList.get(q)[0];
                            int doory = doorList.get(q)[1];
                            int initx = robot.getCurrentPosition()[0];
                            int inity = robot.getCurrentPosition()[1];

                            //add 1 to the visit where it went out
                            visits[doorx][doory] += 1;
                            if (initx > doorx) {
                                performRotation(robot.getCurrentDirection(), CardinalDirection.West);
                                robot.move(initx - doorx, true);
                                if (inity > doory) {
                                    performRotation(robot.getCurrentDirection(), CardinalDirection.North);
                                    robot.move(inity - doory, true);
                                }
                                if (doory > inity) {
                                    performRotation(robot.getCurrentDirection(), CardinalDirection.South);
                                    robot.move(doory - inity, true);
                                }
                            }
                            else if (initx < doorx) {
                                performRotation(robot.getCurrentDirection(), CardinalDirection.East);
                                robot.move(doorx - initx, true);
                                if (inity > doory) {
                                    performRotation(robot.getCurrentDirection(), CardinalDirection.North);
                                    robot.move(inity - doory, true);
                                }
                                if (doory > inity) {
                                    performRotation(robot.getCurrentDirection(), CardinalDirection.South);
                                    robot.move(doory - inity, true);
                                }
                            }
                            else {
                                if (inity > doory) {
                                    performRotation(robot.getCurrentDirection(), CardinalDirection.North);
                                    robot.move(inity - doory, true);
                                }
                                else if (doory > inity) {
                                    performRotation(robot.getCurrentDirection(), CardinalDirection.South);
                                    robot.move(doory - inity, true);
                                }
                            }
                            for (CardinalDirection cdd : CardinalDirection.values()) {
                                if (mazeController.getMazeConfiguration().getMazecells().hasNoWall(robot.getCurrentPosition()[0], robot.getCurrentPosition()[1], cdd) && !mazeController.getMazeConfiguration().getMazecells().isInRoom(robot.getCurrentPosition()[0] + cdd.getDirection()[0], robot.getCurrentPosition()[1] + cdd.getDirection()[1])){
                                    if (robot.getBatteryLevel() >= 3) {
                                        performRotation(robot.getCurrentDirection(), cdd);
                                    }
                                    else {
                                        throw new Exception();
                                    }
                                    if (robot.getBatteryLevel() >= 5) {
                                        for (int k = 0; k < doorList.size(); k ++) {
                                            if (doorList.get(k)[0] == robot.getCurrentPosition()[0] && doorList.get(k)[1] == robot.getCurrentPosition()[1]) {
                                                doorList.get(k)[2] += 1;
                                                break;
                                            }
                                        }
                                        //walk out of the room
                                        robot.move(1, true);
                                        visits[robot.getCurrentPosition()[0]][robot.getCurrentPosition()[1]] += 1;



                                        break;
                                    }
                                    else {
                                        throw new Exception();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }










                }
            }







            if(mazeController.getEnergyConsumption()>= 2996) {
                DataHolder.setEnergyConsumption(mazeController.getEnergyConsumption());
                DataHolder.setPathLength(mazeController.getPathLength());
                launchActivity();
                onStop();
            }

            try {

                if (robot.isAtExit()){
                    onStop();
                    counter2 += 1;
                    System.out.println("nmmsl");
                    if (counter2 == 1){
                        DataHolder.setEnergyConsumption(mazeController.getEnergyConsumption()+5);
                        DataHolder.setPathLength(mazeController.getPathLength()+1);
                        launchActivity2();
                    }

                }
            } catch (Exception e) {}
            handler.postDelayed(robotRun, 500);
            }
        };






    /**
     * If success, the screen will change to success screen (FinishSucceedActivity).
     */
    private void launchActivity2(){
        Intent intent = new Intent(this, FinishSucceedActivity.class);
        startActivity(intent);
    }

    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(robotRun);
    }

    private void performRotation(CardinalDirection current, CardinalDirection project) {
        if(current == CardinalDirection.North) {
            if(project == CardinalDirection.North) {
                return;
            }
            if(project == CardinalDirection.South) {
                robot.rotate(Robot.Turn.AROUND);
            }
            if(project == CardinalDirection.West) {
                robot.rotate(Robot.Turn.RIGHT);
            }
            if(project == CardinalDirection.East) {
                robot.rotate(Robot.Turn.LEFT);
            }
        }
        if(current == CardinalDirection.South) {
            if(project == CardinalDirection.North) {
                robot.rotate(Robot.Turn.AROUND);
            }
            if(project == CardinalDirection.South) {
                return;
            }
            if(project == CardinalDirection.West) {
                robot.rotate(Robot.Turn.LEFT);
            }
            if(project == CardinalDirection.East) {
                robot.rotate(Robot.Turn.RIGHT);
            }
        }
        if(current == CardinalDirection.West) {
            if(project == CardinalDirection.North) {
                robot.rotate(Robot.Turn.LEFT);
            }
            if(project == CardinalDirection.South) {
                robot.rotate(Robot.Turn.RIGHT);
            }
            if(project == CardinalDirection.West) {
                return;
            }
            if(project == CardinalDirection.East) {
                robot.rotate(Robot.Turn.AROUND);
            }
        }
        if(current == CardinalDirection.East) {
            if(project == CardinalDirection.North) {
                robot.rotate(Robot.Turn.RIGHT);
            }
            if(project == CardinalDirection.South) {
                robot.rotate(Robot.Turn.LEFT);
            }
            if(project == CardinalDirection.West) {
                robot.rotate(Robot.Turn.AROUND);
            }
            if(project == CardinalDirection.East) {
                return;
            }
        }
    }
    private void rotateToNextMove(int[] currentPosition, CardinalDirection currentDirection) throws Exception {
        int minVisit = Integer.MAX_VALUE;
        ArrayList<Robot.Direction> directionList = new ArrayList<Robot.Direction>();
        int canGo = 0;
        //put the cells that cango into the list
        for(Robot.Direction d: Robot.Direction.values()) {
            if (robot.distanceToObstacle(d) != 0) {
                directionList.add(d);
                canGo += 1;
                if(visits[directionToCellValue(d,currentDirection,currentPosition)[0]][directionToCellValue(d,currentDirection,currentPosition)[1]]<minVisit) {
                    minVisit = visits[directionToCellValue(d,currentDirection,currentPosition)[0]][directionToCellValue(d,currentDirection,currentPosition)[1]];
                }
            }
        }
        //extract the minimum visited go-able cells to be candidates
        int candidates = 0;
        ArrayList<Robot.Direction> directionCandidate = new ArrayList<Robot.Direction>();
        for(Robot.Direction d: directionList) {
            if(visits[directionToCellValue(d,currentDirection,currentPosition)[0]][directionToCellValue(d,currentDirection,currentPosition)[1]]==minVisit) {
                directionCandidate.add(d);
                candidates += 1;
            }
        }
        //rotate to that direction
        Robot.Direction direction = directionCandidate.get(random.nextIntWithinInterval(0, candidates-1));
        if(direction == Robot.Direction.BACKWARD) {
            robot.rotate(Robot.Turn.AROUND);
        }
        else if(direction == Robot.Direction.LEFT) {
            robot.rotate(Robot.Turn.LEFT);
        }
        else if(direction == Robot.Direction.RIGHT) {
            robot.rotate(Robot.Turn.RIGHT);
        }

        //move to that cell
        robot.move(1, true);

        //increase the number of times that the robot passed that cell
        if(canGo == 1) {
            visits[currentPosition[0]][currentPosition[1]]++;
        }
        visits[robot.getCurrentPosition()[0]][robot.getCurrentPosition()[1]]++;

    }

    /**
     * Increase the number of times that the cell is passed.
     * @param direction
     * @param currentDirection
     * @param currentPosition
     * @return
     */
    private int[] directionToCellValue(Robot.Direction direction, CardinalDirection currentDirection, int[] currentPosition) {
        if(currentDirection == CardinalDirection.East) {
            if(direction == Robot.Direction.FORWARD) {
                int[] position = {currentPosition[0]+1, currentPosition[1]};
                return position;
            }
            else if(direction == Robot.Direction.BACKWARD) {
                int[] position = {currentPosition[0]-1, currentPosition[1]};
                return position;
            }
            else if(direction == Robot.Direction.LEFT) {
                int[] position = {currentPosition[0], currentPosition[1]+1};
                return position;
            }
            else {
                int[] position = {currentPosition[0], currentPosition[1]-1};
                return position;
            }
        }
        else if(currentDirection == CardinalDirection.West) {
            if(direction == Robot.Direction.FORWARD) {
                int[] position = {currentPosition[0]-1, currentPosition[1]};
                return position;
            }
            else if(direction == Robot.Direction.BACKWARD) {
                int[] position = {currentPosition[0]+1, currentPosition[1]};
                return position;
            }
            else if(direction == Robot.Direction.LEFT) {
                int[] position = {currentPosition[0], currentPosition[1]-1};
                return position;
            }
            else {
                int[] position = {currentPosition[0], currentPosition[1]+1};
                return position;
            }
        }
        else if(currentDirection == CardinalDirection.North) {
            if(direction == Robot.Direction.FORWARD) {
                int[] position = {currentPosition[0], currentPosition[1]-1};
                return position;
            }
            else if(direction == Robot.Direction.BACKWARD) {
                int[] position = {currentPosition[0], currentPosition[1]+1};
                return position;
            }
            else if(direction == Robot.Direction.LEFT) {
                int[] position = {currentPosition[0]+1, currentPosition[1]};
                return position;
            }
            else {
                int[] position = {currentPosition[0]-1, currentPosition[1]};
                return position;
            }
        }
        else {
            if(direction == Robot.Direction.FORWARD) {
                int[] position = {currentPosition[0], currentPosition[1]+1};
                return position;
            }
            else if(direction == Robot.Direction.BACKWARD) {
                int[] position = {currentPosition[0], currentPosition[1]-1};
                return position;
            }
            else if(direction == Robot.Direction.LEFT) {
                int[] position = {currentPosition[0]-1, currentPosition[1]};
                return position;
            }
            else {
                int[] position = {currentPosition[0]+1, currentPosition[1]};
                return position;
            }
        }
    }


    public int findBorderOfRoom1(int x, int y) {
        for (int i = x; i < 401; i ++) {
            if (!cells.isInRoom(i, y)) {
                return i - 1;
            }
        }
        return -1;
    }
    public int findBorderOfRoom2(int x, int y) {
        for (int i = x; i >= 0; i --) {
            if (!cells.isInRoom(i, y)) {
                return i + 1;
            }
        }
        return -1;
    }
    public int findBorderOfRoom3(int x, int y) {
        for (int i = y; i < 401; i ++) {
            if (!cells.isInRoom(x, i)) {
                return i - 1;
            }
        }
        return -1;
    }
    public int findBorderOfRoom4(int x, int y) {
        for (int i = y; i >= 0; i --) {
            if (!cells.isInRoom(x, i)) {
                return i + 1;
            }
        }
        return -1;
    }

    public boolean checkIfInList(List<List<int[]>> mainList, int[] door) {
        for (int i = 0; i < mainList.size(); i ++) {
            for (int j = 0; j < mainList.get(i).size(); j ++) {
                if (mainList.get(i).get(j)[0] == door[0] && mainList.get(i).get(j)[1] == door[1]) {
                    return false;
                }
            }
        }
        return true;
    }

    public int indexOfDoorList(List<List<int[]>> mainList, int[] door) {
        for (int i = 0; i < mainList.size(); i ++) {
            for (int j = 0; j < mainList.get(i).size(); j ++) {
                if (mainList.get(i).get(j)[0] == door[0] && mainList.get(i).get(j)[1] == door[1]) {
                    return i;
                }
            }
        }
        return -1;
    }

    public boolean addDoorListToMainList(List<List<int[]>> mainList, List<int[]> doorList) {
        for (int i = 0; i < mainList.size(); i ++) {
            for (int j = 0; j < mainList.get(i).size() && j < doorList.size(); j ++) {
                if (mainList.get(i).get(j).equals(doorList.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
    private void initVisits() {
        visits = new int[mazeController.getMazeConfiguration().getWidth()][mazeController.getMazeConfiguration().getHeight()];
        for (int i = 0; i < mazeController.getMazeConfiguration().getWidth(); i++) {
            for (int j = 0; j < mazeController.getMazeConfiguration().getHeight(); j++) {
                visits[i][j] = 0;
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