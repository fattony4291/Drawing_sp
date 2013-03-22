package TBianco.Drawing.First;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MySurface extends View {

	private Path mPath;
	private Paint mPaint;
	private List<Line> lineList;
	private Deque<Line> undoStack;
	private int count;
	private int currentColor = 0xFF000000;
	private float currentSize = 12;
	private float dx = 0;
	private float dy = 0;
	private boolean redoable = false;
	private Mode currentMode = Mode.DRAW_MODE;
	
	public int pageCount = 1;
	public int currentPage = 1;
	

	public MySurface(Context c) {
		super(c);

		init();
	}

	public MySurface(Context context, AttributeSet attrs) {
		super(context, attrs);

		init();
	}

	public MySurface(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		init();
	}

	private void init() {
		mPath = new Path();
		lineList = new ArrayList<Line>();
		undoStack = new LinkedList<Line>();
		count = 0;
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setColor(currentColor);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(currentSize);

	}

	private void resetPaint() {
		mPaint.setColor(currentColor);
		mPaint.setStrokeWidth(currentSize);
		if(currentMode != Mode.ERASE_MODE )
			mPaint.setXfermode(null);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		// mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		// mCanvas = new Canvas(mBitmap);
		// mCanvas = new Canvas();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(0xFFFFFFFF);

		// canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
		canvas.translate(dx, dy);

		for (Line x : lineList) {
			x.drawLine(canvas, mPaint);
			resetPaint();
		}

		canvas.drawPath(mPath, mPaint);

	}

	private float mX, mY;
	private static final float TOUCH_TOLERANCE = 4;

	private void touch_start(float x, float y) {
		mPath.reset();
		mPath.moveTo(x + dx, y + dy);
		mX = x + dx;
		mY = y + dy;
	}

	private void touch_move(float x, float y) {
		float dxt = Math.abs(x + dx - mX);
		float dyt = Math.abs(y + dy - mY);
		if (dxt >= TOUCH_TOLERANCE || dyt >= TOUCH_TOLERANCE) {
			mPath.quadTo(mX, mY, (x + dx + mX) / 2, (y + dy + mY) / 2);
			mX = x + dx;
			mY = y + dy;
		}
	}

	private void touch_up() {
		mPath.lineTo(mX + dx, mY + dy);
		// commit the path to our offscreen
		// mCanvas.drawPath(mPath, mPaint);
		// kill this so we don't double draw
		// mPath.reset();
		lineList.add(new Line(currentSize, currentColor, new Path(mPath),currentMode == Mode.ERASE_MODE ? LineType.TYPE_ERASE:LineType.TYPE_SOLID));
		mPath.reset();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();

		if (redoable) {
			redoable = false;
			undoStack.clear();
		}

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			touch_start(x, y);
			invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
			touch_move(x, y);
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			touch_up();
			invalidate();
			break;
		}
		return true;
	}

	// Handle toolbar commmands from main activity
	public void changeSize(float to) {
		currentSize = to;
		mPaint.setStrokeWidth(currentSize);
	}

	public void delete() {
		lineList.clear();
		invalidate();
	}

	public void undo() {
		if (!lineList.isEmpty()) {
			undoStack.addFirst(lineList.get(lineList.size() - 1));
			lineList.remove(lineList.size() - 1);
			redoable = true;
			invalidate();
		}
	}

	public void redo() {
		if (redoable && !undoStack.isEmpty()) {
			lineList.add(undoStack.removeFirst());
			invalidate();
		}
	}

	public void resetTranslate() {
		dx = 0;
		dy = 0;
		invalidate();
	}
	
	public void changeMode(Mode to)
	{
		Log.d("ModeChange","Changed mode to " + to);
		currentMode = to;
		if (to == Mode.ERASE_MODE)
		{
			mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		}
		else
		{
			mPaint.setXfermode(null);
		}
	}

}
