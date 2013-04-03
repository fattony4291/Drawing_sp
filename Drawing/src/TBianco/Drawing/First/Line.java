package TBianco.Drawing.First;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.Log;

public class Line {
	Path path;
	int color;
	float size;
	int lineID;
	int userID;
	LineType type;
	
	Line()
	{
		color=Color.BLACK;
		size=12;
		lineID=-1;
		userID=-1;
		type=LineType.TYPE_SOLID;
		path= new Path();
	}
	
	Line(float lSize, int lColor,Path lPath,LineType lType)
	{
		color=lColor;
		size=lSize;
		lineID=-1;
		userID=-1;
		path= lPath;
		type=lType;
	}
	
	Line(float lSize, int lColor,Path lPath, LineType lType,int uid, int lid)
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
			//Log.d("Erase.", "Set erase style");
			paint.setColor(Color.WHITE);
		}
		else {
		paint.setColor(color);
		paint.setStrokeWidth(size);
		}
		canvas.drawPath(path,paint);
		
	}
	

}
