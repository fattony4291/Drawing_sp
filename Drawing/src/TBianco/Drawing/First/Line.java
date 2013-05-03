package TBianco.Drawing.First;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.Log;

public class Line {
	CustomPath path;
	int color;
	float size;
	int lineID;
	int userID;
	LineType type;
	private int lineCount=0;
	
	Line()
	{
		color=Color.BLACK;
		size=12;
		lineID=lineCount++;
		userID=-1;
		type=LineType.TYPE_SOLID;
		path= new CustomPath();
	}
	
	Line(float lSize, int lColor,CustomPath lPath,LineType lType, int uid)
	{
		color=lColor;
		size=lSize;
		lineID=lineCount++;
		userID=uid;
		path= lPath;
		type=lType;
	}
	
	Line(float lSize, int lColor,CustomPath lPath, LineType lType,int uid, int lid)
	{
		color= lColor;
		size= lSize;
		lineID= lid;
		userID= uid;
		path= lPath;
		type = lType;
	}
	
	public void drawLine(Canvas canvas,Paint paint)
	{
		
		if (type == LineType.TYPE_ERASE){
			//paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
			Log.d("Erase.", "Set erase style");
			paint.setColor(Color.WHITE);
		}
		else {
			paint.setColor(color);
			Log.d("Draw.", "Set Draw style");
		}
		paint.setStrokeWidth(size);
		canvas.drawPath(path,paint);
		
	}
	

}
