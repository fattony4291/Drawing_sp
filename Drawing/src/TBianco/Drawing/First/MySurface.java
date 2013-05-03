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

	private CustomPath mPath;
	private Paint mPaint;
	private List<ArrayList<Line>> pageList;
	private Deque<Line> undoStack;
	private int count;
	private int currentColor = 0xFF000000;
	private float currentSize = 12;
	private float dx = 0;
	private float dy = 0;
	private boolean redoable = false;
	private Mode currentMode = Mode.DRAW_MODE;
	private ArrayList<Line> currentPage;
	public DrawingActivity act;
	private int userID = (int) (Math.random() * 1000);

	public int pageCount = 1;
	public int currentPageCount = 0;

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
		mPath = new CustomPath();
		pageList = new ArrayList<ArrayList<Line>>();
		pageList.add(new ArrayList<Line>());
		currentPage = pageList.get(currentPageCount);
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
		if (currentMode != Mode.ERASE_MODE)
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

		for (Line x : currentPage) {
			x.drawLine(canvas, mPaint);
			resetPaint();
		}

		canvas.drawPath(mPath, mPaint);

	}

	private float mX, mY;
	private static final float TOUCH_TOLERANCE = 4;
	private float previousX, previousY;

	private void touch_start(float x, float y) {
		previousX = x;
		previousY = y;
		mPath.reset();
		mPath.moveTo(x - dx, y - dy);
		mX = x - dx;
		mY = y - dy;
	}

	private void touch_move(float x, float y) {
		previousX = x;
		previousY = y;
		float dxt = Math.abs(x - dx - mX);
		float dyt = Math.abs(y - dy - mY);
		if (dxt >= TOUCH_TOLERANCE || dyt >= TOUCH_TOLERANCE) {
			mPath.quadTo(mX, mY, (x - dx + mX) / 2, (y - dy + mY) / 2);
			mX = x - dx;
			mY = y - dy;
		}
	}

	private void touch_up() {
		mPath.lineTo(mX, mY);
		// commit the path to our offscreen
		// mCanvas.drawPath(mPath, mPaint);
		// kill this so we don't double draw
		// mPath.reset();
		currentPage.add(new Line(currentSize, currentColor, new CustomPath(
				mPath), currentMode == Mode.ERASE_MODE ? LineType.TYPE_ERASE
				: LineType.TYPE_SOLID, userID));
		mPath.reset();

	}

	private void scroll_start(float x, float y) {
		previousX = x;
		previousY = y;
	}

	private void scroll_move(float x, float y) {
		dx += x - previousX;
		dy += y - previousY;

		previousX = x;
		previousY = y;
	}

	boolean scrollEntered = false;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX(0);
		float y = event.getY(0);

		if (redoable) {
			redoable = false;
			undoStack.clear();
		}

		int pointerCount = event.getPointerCount();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (currentMode == Mode.DRAW_MODE && pointerCount == 1)
				touch_start(x, y);
			else if (currentMode == Mode.SCROLL_MODE) {
				scrollEntered = true;
				scroll_start(x, y);
			}
			invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
			if (currentMode == Mode.DRAW_MODE && pointerCount == 1
					&& !scrollEntered)
				touch_move(x, y);
			else if (currentMode == Mode.SCROLL_MODE || pointerCount > 1
					|| scrollEntered) {
				scrollEntered = true;
				scroll_move(x, y);
			}
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			scrollEntered = false;
			if (currentMode == Mode.DRAW_MODE && pointerCount == 1
					&& !scrollEntered)
				touch_up();
			else if (currentMode == Mode.SCROLL_MODE || pointerCount > 1
					|| scrollEntered)
				scroll_move(x, y);
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
		currentPage.clear();
		invalidate();
	}

	public void deleteAll() {
		pageCount = 1;
		currentPageCount = 0;

		redoable = false;
		undoStack.clear();

		pageList.clear();
		pageList.add(currentPage = new ArrayList<Line>());

		invalidate();
	}

	public void undo() {
		if (!currentPage.isEmpty()) {
			undoStack.addFirst(currentPage.get(currentPage.size() - 1));
			currentPage.remove(currentPage.size() - 1);
			redoable = true;
			invalidate();
		}
	}

	public void redo() {
		if (redoable && !undoStack.isEmpty()) {
			currentPage.add(undoStack.removeFirst());
			invalidate();
		}
	}

	public void resetTranslate() {
		dx = 0;
		dy = 0;
		invalidate();
	}

	public void changeMode(Mode to) {
		Log.d("ModeChange", "Changed mode to " + to);
		currentMode = to;
		if (to == Mode.ERASE_MODE) {
			mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		} else {
			mPaint.setXfermode(null);
		}
	}

	public void previousPage() {
		if (currentPageCount > 0)
			currentPageCount--;

		if (redoable) {
			redoable = false;
			undoStack.clear();
		}

		currentPage = pageList.get(currentPageCount);

		invalidate();
	}

	public void nextPage() {
		if (currentPageCount < pageCount - 1)
			currentPageCount++;

		if (redoable) {
			redoable = false;
			undoStack.clear();
		}

		currentPage = pageList.get(currentPageCount);

		invalidate();
	}

	public void addPage() {
		pageCount++;
		currentPageCount = pageCount - 1;

		pageList.add(new ArrayList<Line>());

		if (redoable) {
			redoable = false;
			undoStack.clear();
		}

		currentPage = pageList.get(currentPageCount);

		invalidate();
	}

	public void addLine(Line line, int page) {
		Line x;
		for (int i = 0; i < pageList.get(page).size(); i++) {
			x = pageList.get(page).get(i);
			if (x.lineID == line.lineID && x.userID == line.userID) {
				pageList.get(page).set(i, line);
				return;
			}
		}
		pageList.get(page).add(line);
	}

	public void setColor(int color) {
		currentColor = color;
	}

}
