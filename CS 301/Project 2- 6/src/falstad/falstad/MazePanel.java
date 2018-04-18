package shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.falstad;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.media.Image;
import android.util.AttributeSet;
import android.view.View;

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
	private final String CharSequences;
	/* Panel operates a double buffer see
         * http://www.codeproject.com/Articles/2136/Double-buffer-in-standard-Java-AWT
         * for details
         */
	// bufferImage can only be initialized if the container is displayable,
	// uses a delayed initialization and relies on client class to call initBufferImage()
	// before first use
	private Image bufferImage;  
	//private Graphics2D graphics; // obtained from bufferImage,
	private Color color;
	private boolean mShowText;
	// graphics is stored to allow clients to draw on same graphics object repeatedly
	// has benefits if color settings should be remembered for subsequent drawing operations
	
	/**
	 * Constructor. Object is not focusable.
	 */
	public MazePanel(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a;
		a = context.getTheme().obtainStyledAttributes(
				attrs,
				MazePanel,
				0, 0);
		CharSequences=a.getString(styleable.MazePanel_text);

		try {
			v("??","???");
			mShowText = a.getBoolean(MazePanel_showText, true);
		} finally {
			a.recycle();
		}
		//setFocusable(false);
		//bufferImage = null; // bufferImage initialized separately and later
		//graphics = null;
		
	}
	public boolean isShowText() {
		return mShowText;
	}

	public void setShowText(boolean showText) {
		mShowText = showText;
		invalidate();
		requestLayout();
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
	protected void setColorWhite() {
	    this.getBufferGraphics().setColor(Color.white);
	}
	protected void setColorBlack() {
	    this.getBufferGraphics().setColor(Color.black);
	}
	protected void setColorDarkGray() {
	    this.getBufferGraphics().setColor(Color.darkGray);
	}
	protected void setColorGray() {
		this.getBufferGraphics().setColor(Color.gray);
	}
	protected void setColorRed() {
		this.getBufferGraphics().setColor(Color.red);
	}
	protected void setColorYellow() {
		this.getBufferGraphics().setColor(Color.yellow);
	}
	public int getRGB() {
		return color.getRGB();
	}
	
	public Object getColor() {
		return color;
	}

	public void setColor(Object color) {
		this.color = (Color) color;
	}

	public void setColor(int rgbDef) {
		color = new Color(rgbDef);
	}
	public void setColor(int rgbDef1, int rgbDef2, int rgbDef3) {
		color = new Color(rgbDef1, rgbDef2, rgbDef3);
	}
    public Color objectToColor(Object col) {
    	    return (Color) col;
    }
    */
}
