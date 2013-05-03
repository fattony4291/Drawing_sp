package TBianco.Drawing.First;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;



public class DrawingActivity extends Activity
implements ColorPickerDialog.OnColorChangedListener, DeleteDialog.onDeleteSelectListener {

	private MySurface drawingArea;
    private Connection connection;
    private TextView pageTracker;
    private ImageButton colorButton;
    private int currentColor=0xFF000000;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.main);
        drawingArea = (MySurface)findViewById(R.id.drawingArea);
        drawingArea.act = this;
        pageTracker = (TextView) findViewById(R.id.pageTracker);
        colorButton = (ImageButton) findViewById(R.id.btnColor);
        colorButton.setBackgroundColor(currentColor);

		/*try {
			//connection = new Connection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
    }
    
    public void addLine(Line netLine, int page)
    {
    	drawingArea.addLine(netLine, page);
    }
    
    public void sendLine(Line line, int page) throws IOException
    {
    	
    	//connection.sendLine(line, page);
    }
   
    public void undoClick(View view)
    {
    	drawingArea.undo();
    }
    
    public void redoClick(View view)
    {
    	drawingArea.redo();
    }
    
    public void deleteClick(View view)
    {
    	   	//new DeleteDialog(this,this).show();
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	
    	builder.setTitle(R.string.DeleteTitle)
		.setMessage(R.string.DeleteTitle)
		.setPositiveButton(R.string.All,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						deleteSelect(true);
					}
				})
		.setNegativeButton(R.string.Single,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						deleteSelect(false);
					}
				});
    	
    	AlertDialog alert = builder.create();
    	alert.show();
    }
    
	@Override
	public void deleteSelect(boolean select) {
		if(select)//delete all pages
		{
			drawingArea.deleteAll();
			updatePageTracker();
		}
		else// delete only current lines
		{
			drawingArea.delete();
		}
		
	}
    
    public void colorPickerClick(View view)
    {
    	new ColorPickerDialog(this, this, currentColor).show();
    }
    
    public void sizeChangeClick(View view)
    {
    	    boolean checked = ((RadioButton) view).isChecked();
    	    
    	    // Check which radio button was clicked
    	    switch(view.getId()) {
    	        case R.id.rSmall:
    	            if (checked)
    	                drawingArea.changeSize(6);
    	            break;
    	        case R.id.rMedium:
    	            if (checked)
    	            	drawingArea.changeSize(12);
    	            break;
    	        case R.id.rLarge:
    	            if (checked)
    	            	drawingArea.changeSize(18);
    	            break;
    	}	
    }
    
    public void modeChangeClick(View view)
    {
    	    boolean checked = ((RadioButton) view).isChecked();
    	    
    	    // Check which radio button was clicked
    	    switch(view.getId()) {
    	        case R.id.rDraw:
    	            if (checked)
    	                drawingArea.changeMode(Mode.DRAW_MODE);
    	            break;
    	        case R.id.rText:
    	            if (checked)
    	            	drawingArea.changeMode(Mode.TEXT_MODE);
    	            break;
    	        case R.id.rShapes:
    	            if (checked)
    	            	drawingArea.changeMode(Mode.SHAPE_MODE);
    	            break;
    	        case R.id.rErase:
    	            if (checked)
    	            	drawingArea.changeMode(Mode.ERASE_MODE);
    	            break;
    	        case R.id.rScroll:
    	            if (checked)
    	            	drawingArea.changeMode(Mode.SCROLL_MODE);
    	            break;
    	            
    	}	
    }
    
    public void connectClick(View view)
    {
    	//connection.execute(this);
    }
    
    public void homeClick(View view)
    {
    	drawingArea.resetTranslate();
    }
    
    public void previousClick(View view)
    {
    	drawingArea.previousPage();
    	updatePageTracker();
    }
    
    public void nextClick(View view)
    {
    	drawingArea.nextPage();
    	updatePageTracker();
    }
    
    public void newClick(View view)
    {
    	drawingArea.addPage();
    	updatePageTracker();
    }

    private void updatePageTracker()
    {
    	pageTracker.setText(drawingArea.currentPageCount + 1 + "/" + drawingArea.pageCount);
    }

	@Override
	public void colorChanged(int color) {
		currentColor=color;
		drawingArea.setColor(color);
		colorButton.setBackgroundColor(currentColor);
	}


}

