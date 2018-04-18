package shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.falstad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.media.Image;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.R;

import static android.graphics.Color.parseColor;
import static android.util.Log.*;
import static android.util.Log.v;
import static shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.R.styleable.*;

/**
 * Add functionality for double buffering to an AWT Panel class.
 * Used for drawing a maze.
 * 
 * @author pk
 *
 */
public class MazePanel extends View  {
    private String text;
    private int color;
    private int size;
    private MazeController mazeController;
    public int state = 0;
    private int viewWidth = 720;
    private int viewHeight = 720;

    private int point1x;
    private int point1y;
    private int point2x;
    private int point2y;


    private Paint painter;
    private int counter = 0;
    private List<int[]> toDraw = new ArrayList<int[]>();
    private List<int[]> toDrawBackUp = new ArrayList<int[]>();
    private List<int[]> LinesNorth = new ArrayList<int[]>();
    private List<int[]> LinesWest = new ArrayList<int[]>();
    private List<int[]> currentPosition = new ArrayList<int[]>();
    private List<int[]> solutionLines = new ArrayList<int[]>();

    //private Graphics2D graphics; // obtained from bufferImage,
	//private Color color;
	//private boolean mShowText;
	// graphics is stored to allow clients to draw on same graphics object repeatedly
	// has benefits if color settings should be remembered for subsequent drawing operations
	
	/**
	 * Constructor. Object is not focusable.
	 */
	public MazePanel(Context context, AttributeSet attrs) {
		super(context, attrs);
		painter = new Paint();

        v("??", "???");

		}
		//setFocusable(false);
		//bufferImage = null; // bufferImage initialized separately and later
		//graphics = null;
    @Override
    protected void onDraw(Canvas canvas) {
	    System.out.println("Real mazePanel: " + this);
        Log.v("ondraw", String.valueOf(counter));
        System.out.println("toDraw memory location in onDraw" + toDraw);

        counter += 1;
        painter.setColor(Color.BLACK);
        painter.setStyle(Paint.Style.FILL);
        canvas.drawRect(15, 0, 15 + viewWidth, viewHeight / 2, painter);


        painter.setColor(Color.DKGRAY);
        painter.setStyle(Paint.Style.FILL);
        canvas.drawRect(15, viewHeight / 2, 15 + viewWidth, viewHeight, painter);


        if (toDraw.isEmpty()) {
            for (int i = 0; 2 * i < toDrawBackUp.size(); i++) {
                painter.setColor(getResources().getColor(R.color.colorAccent));
                painter.setStyle(Paint.Style.FILL);
                Path path = new Path();
                path.reset();

                path.moveTo(toDrawBackUp.get(2 * i)[0] + 15, toDrawBackUp.get(2 * i + 1)[0]);
                path.lineTo(toDrawBackUp.get(2 * i)[1] + 15, toDrawBackUp.get(2 * i + 1)[1]);
                path.lineTo(toDrawBackUp.get(2 * i)[2] + 15, toDrawBackUp.get(2 * i + 1)[2]);
                path.lineTo(toDrawBackUp.get(2 * i)[3] + 15, toDrawBackUp.get(2 * i + 1)[3]);
                path.lineTo(toDrawBackUp.get(2 * i)[0] + 15, toDrawBackUp.get(2 * i + 1)[0]);
                canvas.drawPath(path, painter);
            }

        }


        for (int i = 0; 2 * i < toDraw.size(); i++) {
            painter.setColor(getResources().getColor(R.color.colorAccent));
            painter.setStyle(Paint.Style.FILL);
            Path path = new Path();
            path.reset();
            System.out.println("toDraw memory location in onDraw" + toDraw);
            System.out.println("nmslllllll");
            System.out.println(toDraw.get(0)[0] + 15);
            System.out.println(toDraw.get(0)[1] + 15);
            System.out.println(toDraw.get(0)[2] + 15);
            System.out.println(toDraw.get(0)[3] + 15);

            path.moveTo(toDraw.get(2 * i)[0] + 15, toDraw.get(2 * i + 1)[0]);
            path.lineTo(toDraw.get(2 * i)[1] + 15, toDraw.get(2 * i + 1)[1]);
            path.lineTo(toDraw.get(2 * i)[2] + 15, toDraw.get(2 * i + 1)[2]);
            path.lineTo(toDraw.get(2 * i)[3] + 15, toDraw.get(2 * i + 1)[3]);
            path.lineTo(toDraw.get(2 * i)[0] + 15, toDraw.get(2 * i + 1)[0]);
            canvas.drawPath(path, painter);
        }


        if (mazeController.isInMapMode()) {
            Log.v("4", "4map");

            for (int j = 0; j < LinesNorth.size(); j++) {
                painter.setColor(Color.WHITE);


                canvas.drawLine(LinesNorth.get(j)[0] + 15, LinesNorth.get(j)[1], LinesNorth.get(j)[2] + 15, LinesNorth.get(j)[3], painter);
            }


            for (int m = 0; m < LinesWest.size(); m++) {
                painter.setColor(Color.WHITE);
                canvas.drawLine(LinesWest.get(m)[0] + 15, LinesWest.get(m)[1], LinesWest.get(m)[2] + 15, LinesWest.get(m)[3], painter);
            }
            painter.setColor(Color.RED);
            painter.setStyle(Paint.Style.FILL);
            canvas.drawOval(currentPosition.get(0)[0], currentPosition.get(0)[1], currentPosition.get(0)[2], currentPosition.get(0)[3], painter);
            canvas.drawLine(currentPosition.get(1)[0], currentPosition.get(1)[1], currentPosition.get(1)[2], currentPosition.get(1)[3], painter);
            canvas.drawLine(currentPosition.get(2)[0], currentPosition.get(2)[1], currentPosition.get(2)[2], currentPosition.get(2)[3], painter);
            canvas.drawLine(currentPosition.get(3)[0], currentPosition.get(3)[1], currentPosition.get(3)[2], currentPosition.get(3)[3], painter);
            if (mazeController.isInShowSolutionMode()) {
                Log.v("draw solution", "now");
                for (int n = 0; n < solutionLines.size(); n++) {
                    painter.setColor(Color.YELLOW);
                    canvas.drawLine(solutionLines.get(n)[0], solutionLines.get(n)[1], solutionLines.get(n)[2], solutionLines.get(n)[3], painter);
                }
            }
        }
        if (!toDraw.isEmpty()) {
            toDrawBackUp.clear();
            for (int k = 0; k < toDraw.size(); k++) {
                toDrawBackUp.add(toDraw.get(k));
            }
        }
        toDraw.clear();
        LinesNorth.clear();
        LinesWest.clear();
        currentPosition.clear();
        solutionLines.clear();
        System.out.println("toDraw: " + toDraw.isEmpty() );
        System.out.println("west: " + LinesWest.isEmpty());
        System.out.println("north: " + LinesNorth.isEmpty());
    }





    public String getText(){
	    return text;
    }
    public int getSize(){
        return size;
    }


    public void setText(String newText){
        text = newText;
        invalidate();
        requestLayout();
    }
    public void setSize(int newSize){
        size = newSize;
        invalidate();
        requestLayout();
    }

    public void setMazeController(MazeController mazeController){
        this.mazeController = mazeController;
    }

    public void moveForward(){
        Log.v("move!","now!");
        mazeController.getBasicRobot().move(1,true);

    }
    public void rotateLeft(){
        Log.v("now left","now left");
        mazeController.getBasicRobot().rotate(Robot.Turn.LEFT);

    }
    public void rotateRight(){
        Log.v("now right", "now right");
        mazeController.getBasicRobot().rotate(Robot.Turn.RIGHT);

    }
    public void rotateBackward(){
        Log.v("旋转跳跃我闭着眼","go go go");
        mazeController.getBasicRobot().rotate(Robot.Turn.AROUND);

    }

    public void map(){
        Log.v("map","is in the truth");
        mazeController.keyDown(MazeController.UserInput.ToggleLocalMap,0);
    }
    public void wholeMap(){
        Log.v("whole map","help you get to exit");
        mazeController.keyDown(MazeController.UserInput.ToggleFullMap,0);
    }
    public void showSolution(){
        Log.v("show solution","this is too easy huh");
        mazeController.keyDown(MazeController.UserInput.ToggleSolution,0);
    }



	/*
	@Override
	public void update(Graphics g) {
		paint(g);
	}
	/**
	 * Method to draw the buffer image on a graphics object that is
	 * obtained from the superclass. The method is used in the MazeController.
	 * Warning: do not override getGraphics() or drawing might fail. 
	 */
	/*public void update() {
		paint(getGraphics());
	}
	

	/**
	 * Draws the buffer image to the given graphics object.
	 * This method is called when this panel should redraw itself.
	 */
	/*
	@Override
	public void paint(Graphics g) {
		if (null == g) {
			System.out.println("MazePanel.paint: no graphics object, skipping drawImage operation");
		}
		else {
			g.drawImage(bufferImage,0,0,null);
		}
	}

	public void initBufferImage() {
		bufferImage = createImage(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT);
		if (null == bufferImage)
		{
			System.out.println("Error: creation of buffered image failed, presumedly container not displayable");
		}
	}
	/**
	 * Obtains a graphics object that can be used for drawing.
	 * The object internally stores the graphics object and will return the
	 * same graphics object over multiple method calls. 
	 * To make the drawing visible on screen, one needs to trigger 
	 * a call of the paint method, which happens 
	 * when calling the update method. 
	 * @return graphics object to draw on, null if impossible to obtain image
	 */
	/*
	public Graphics getBufferGraphics() {
		if (null == graphics) {
			// instantiate and store a graphics object for later use
			if (null == bufferImage)
				initBufferImage();
			if (null == bufferImage)
				return null;
			graphics = (Graphics2D) bufferImage.getGraphics();
			if (null == graphics) {
				System.out.println("Error: creation of graphics for buffered image failed, presumedly container not displayable");
			}
			// success case
			
			//System.out.println("MazePanel: Using Rendering Hint");
			// For drawing in FirstPersonDrawer, setting rendering hint
			// became necessary when lines of polygons 
			// that were not horizontal or vertical looked ragged
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		}
		
		return graphics;
	}
	*/
	protected void setColorWhite() {

	    color = Color.WHITE;
	}
	protected void setColorBlack() {
	    color = Color.BLACK;
	}
	protected void setColorDarkGray() {
	    color = Color.DKGRAY;
	}
	protected void setColorGray() {
        color = Color.GRAY;
	}
	protected void setColorRed() {
        color = Color.RED;
	}
	protected void setColorYellow() {
        color = Color.YELLOW;
	}
	public int getRGB() {
		return color;
	}
	
	public int getColor() {
		return color;
	}

	public void setColor(Object col) {
		color = parseColor(col.toString());
	}

	public void setColor(int rgbDef) {
		color = rgbDef;
	}
	public void setColor(int rgbDef1, int rgbDef2, int rgbDef3) {
		color = Color.rgb(rgbDef1, rgbDef2, rgbDef3);
	}

    public void setLine(int[] line){
        point1x = line[0];
        point1y = line[1];
        point2x = line[2];
        point2y = line[3];
    }
    public void addToDraw(int[] walls){

        toDraw.add(walls);
        System.out.println(walls[0]);
        System.out.println(toDraw.get(0)[0]);
        System.out.println(toDraw);
    }
    public void addLineNorth(int[] mapLineNorth){
        LinesNorth.add(mapLineNorth);
    }
    public void addLineWest(int[] mapLineWest){
        LinesWest.add(mapLineWest);
    }
    public void setCounter(int counter){
        this.counter = counter;
    }
    public void setCurrentPosition(int[] currentPositionElement){
        currentPosition.add(currentPositionElement);
    }
    public void addSolutionLine(int[] soluitonLine){
        solutionLines.add(soluitonLine);
    }
    public List<int[]> getToDraw(){return toDraw;}
    public List<int[]> getLinesNorth(){return LinesNorth;}
    public List<int[]> getLinesWest(){return LinesWest;}
    public List<int[]> getCurrentPosition(){return currentPosition;}
    public List<int[]> getSolutionLines(){return solutionLines;}



}
