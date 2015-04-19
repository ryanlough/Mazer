package ryan.mazer;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @author impaler
 * This is the main surface that handles the ontouch events and draws
 * the image to the screen.
 */
@SuppressLint("ViewConstructor")
public class MainGamePanel extends SurfaceView implements
		SurfaceHolder.Callback {

	private static final String TAG = MainGamePanel.class.getSimpleName();
	public Maze maze;
	public MainThread thread;
	//private Droid droid;
	private float[] a;
	private float[] path;
	private Paint p;
	private Paint green;
	private Paint black;
	float width;
	float height;
	float roomWidth;
	float roomHeight;
	float remove;
	float add;
	float dec;
	public int mazeSize;
	Posn curr;
	Room currRoom;
	Room pastRoom;
	ArrayList<Float> mazePath;
	//private ArrayList<Float> a;
	DroidzActivity context;

	public MainGamePanel(DroidzActivity context, int width, int height, int n) {
		super(context);
		this.context = context;
		this.maze = new Maze(n, 200);
		a = new float[100];
		p = new Paint();
		this.mazeSize = n;
		p.setColor(Color.CYAN);
		green = new Paint();
		green.setColor(Color.GREEN);
		black = new Paint();
        black.setColor(Color.BLACK);
        black.setTextSize(150);
		this.width = width;
		this.height = height;
		this.roomWidth = width / n;
        this.roomHeight = height / 10 + 100;
        this.remove = 0f;
        this.add = 1f;
        this.dec = .01f;
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);

		
		
		// make the GamePanel focusable so it can handle events
		setFocusable(true);
		
		
		this.mazePath = new ArrayList<Float>();
		int i = 0;
		for (Room r: this.maze.rooms) {
            
            if (r.north.isWall()) {
                mazePath.add(r.coord.x * this.roomWidth);
                mazePath.add(r.coord.y * this.roomHeight + remove - this.roomHeight*this.maze.height+ this.height - 1000);
                mazePath.add(r.coord.x * this.roomWidth + this.roomWidth);
                mazePath.add(r.coord.y * this.roomHeight + remove - this.roomHeight*this.maze.height+ this.height - 1000);
            }
            if (r.east.isWall()) {
                mazePath.add(r.coord.x * this.roomWidth + this.roomWidth);
                mazePath.add(r.coord.y * this.roomHeight + remove - this.roomHeight*this.maze.height+ this.height - 1000);
                mazePath.add(r.coord.x * this.roomWidth + this.roomWidth);
                mazePath.add(r.coord.y * this.roomHeight + this.roomHeight + remove - this.roomHeight*this.maze.height+ this.height - 1000);
            }
            if (r.south.isWall() && i != 4*(this.maze.rooms.size()-1)) {
                mazePath.add(r.coord.x * this.roomWidth);
                mazePath.add(r.coord.y * this.roomHeight + this.roomHeight + remove - this.roomHeight*this.maze.height+ this.height - 1000);
                mazePath.add(r.coord.x * this.roomWidth + this.roomWidth);
                mazePath.add(r.coord.y * this.roomHeight + this.roomHeight + remove - this.roomHeight*this.maze.height+ this.height - 1000);
            }
            if (r.west.isWall()) {
                mazePath.add(r.coord.x * this.roomWidth);
                mazePath.add(r.coord.y * this.roomHeight + remove - this.roomHeight*this.maze.height+ this.height - 1000);
                mazePath.add(r.coord.x * this.roomWidth);
                mazePath.add(r.coord.y * this.roomHeight + this.roomHeight + remove - this.roomHeight*this.maze.height+ this.height - 1000);
            }
            i+=4;
        }
		
		this.currRoom = this.maze.rooms.get(this.maze.rooms.size() - 1);
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
	 // create the game loop thread
        thread = new MainThread(getHolder(), this);
		// at this point the surface is created and
		// we can safely start the game loop
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	    thread.setRunning(false);
		Log.d(TAG, "Surface is being destroyed");
		// tell the thread to shut down and wait for it to finish
		// this is a clean shutdown
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
			}
		}
		
		Log.d(TAG, "Thread was shut down cleanly");
	}
	
	@Override
	 public boolean onTouchEvent(MotionEvent event) {
	    curr = new Posn(event.getX(), event.getY());
	    if (!this.thread.started && curr.y > this.height - 400) {
            this.thread.started = true;
            this.thread.tickCount = 0;
        }
	    else if (this.thread.started) {
	    if (event.getAction() == MotionEvent.ACTION_DOWN) {
	        for(int i = 0; i < a.length-3; i++) {
	            if(i<a.length-4) {
	                a[i] = a[i + 4];
	            }
	            else {
	                a[i+2] = event.getX();
	                a[i+3] = event.getY();
	                a[i] = event.getX();
	                a[i+1] = event.getY();
	            }
	        }
	    }
	    else if (event.getAction() == MotionEvent.ACTION_UP) {
	        DroidzActivity.currScore = ((float)this.thread.tickCount)/100;
	        if (this.mazeSize == 3 && DroidzActivity.currScore > DroidzActivity.high3) {
	            DroidzActivity.high3 = DroidzActivity.currScore;
	        }
	        else if (this.mazeSize == 4 && DroidzActivity.currScore > DroidzActivity.high4){
	            DroidzActivity.high4 = DroidzActivity.currScore;
	        }
	        else if (this.mazeSize == 5 && DroidzActivity.currScore > DroidzActivity.high5) {
	            DroidzActivity.high5 = DroidzActivity.currScore;
	        }
	        this.context.newRestart();
	    }
	    else {
	        for(int i = 0; i < a.length-3; i++) {
	            if(i<a.length-4) {
	                a[i] = a[i + 4];
	            }
	            else {
	                a[i+2] = event.getX();
	                a[i+3] = event.getY();
	                a[i] = a[i-2];
	                a[i+1] = a[i-1];
	            }
	        }
	    }
	    }
	  return true;
	 }
	@Override
	protected void onDraw(Canvas canvas) {
	    try {
	        canvas.drawColor(Color.BLACK);
	        if (this.thread.started) {
	            if (a[0] != 0 && DroidzActivity.showLine){
	                canvas.drawLines(a, p);
	            }
	            canvas.drawLines(path, p);
	        }
	        else {
	            p.setTextSize(1000);
	            canvas.drawText("" + this.mazeSize, 2*this.width/7, this.height/2, p);
	            canvas.drawRect(0, this.height - 400, this.width, this.height, green);
	            black.setTextSize(this.width/10);
	            canvas.drawText("Hold to Start", this.width/5, this.height - this.width/10, black);
	            black.setTextSize(150);
	        }
	    }
	    catch(NullPointerException e) {
	        System.out.println("Error: Wat?");
	    }
	}
	public void updateGame(ArrayList<Float> a, long count) {
	    float[] f = new float[a.size()];
	    // Changes maze values
	    for(int i = 0; i < a.size(); i++) {
	        if (i%2 != 0) {
	            f[i] = a.get(i) + remove;
	        }
	        else {
	            f[i] = a.get(i);
	        }
	    }
	    // Changes line values
	    for(int i = 0; i < this.a.length; i++) {
            if(i%2 != 0)
            this.a[i] += this.add;
        }
	    remove+=add;
	    if (count < 1800){
	        add += .01;
	    }
	    path = f;
	}
	public boolean inersect() {
	    this.pastRoom = this.currRoom;
	    try {
	    this.currRoom = this.maze.getRoom(this.maze.rooms, new Posn((float)Math.floor(this.curr.x / this.roomWidth), (float)Math.floor((this.curr.y - remove + this.roomHeight*this.maze.height - this.height + 1000) / this.roomHeight)));
	    }
	    catch(RuntimeException e) {
	        
	    }
	    return !this.currRoom.reachable(this.pastRoom);
	}
}
