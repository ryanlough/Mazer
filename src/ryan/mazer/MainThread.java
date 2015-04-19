/**
 * 
 */
package ryan.mazer;
import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;


/**
 * @author impaler
 *
 * The Main thread which contains the game loop. The thread must have access to 
 * the surface view and holder to trigger events every game tick.
 */
public class MainThread extends Thread {
	
	private static final String TAG = MainThread.class.getSimpleName();
	
	
	// Surface holder that can access the physical surface
    private SurfaceHolder surfaceHolder;
	// The actual view that handles inputs
	// and draws to the surface
    private MainGamePanel gamePanel;
    
    public long tickCount;

	// flag to hold game state 
	private boolean running;


    public boolean started;
	public void setRunning(boolean running) {
		this.running = running;
		this.started = false;
		
	}

	public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
	}



    @SuppressLint("WrongCall")
    @Override
	public void run() {
	    Canvas canvas;
	    Log.d(TAG, "Starting game loop");
	    tickCount = 0L;
	    while(running) {
	        canvas = null;
	        try {
	            canvas = this.surfaceHolder.lockCanvas();
	                if(started) {
	                    this.gamePanel.updateGame(this.gamePanel.mazePath, this.tickCount);
	                }
	            synchronized (surfaceHolder) {
	                Paint p = new Paint();
                    p.setColor(Color.RED);
                    p.setTextSize(100);
	                if (canvas != null) {
	                    this.gamePanel.onDraw(canvas);
	                }
	                if (this.gamePanel.curr != null && started && this.gamePanel.inersect()) {
	                    canvas.drawText("YOU HIT THE EDGE!", this.gamePanel.width/10, this.gamePanel.height/3, p);
	                    
	                    running = false;
	                    
	                }
	            }
	            }
	        
	        catch (NullPointerException e) { }
	            finally {
	            if (canvas != null) {
	                surfaceHolder.unlockCanvasAndPost(canvas);
	            }
	        }
	        tickCount++;
	    }
		Log.d(TAG, "Game loop executed " + tickCount + " times");
	}
}
