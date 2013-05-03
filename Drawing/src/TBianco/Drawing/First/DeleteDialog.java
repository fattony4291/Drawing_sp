package TBianco.Drawing.First;

import TBianco.Drawing.First.ColorPickerDialog.OnColorChangedListener;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

public class DeleteDialog extends AlertDialog {

	public interface onDeleteSelectListener {
		void deleteSelect(boolean select);
	}

	private onDeleteSelectListener mListener;
	
	public DeleteDialog(Context context) {
		super(context);
	}

	public DeleteDialog(Context c, onDeleteSelectListener listen){
		super(c);
		
		mListener = listen;
		
		
		/*AlertDialog.Builder builder = new AlertDialog.Builder((Context) listen).setTitle(R.string.DeleteTitle)
		.setMessage(R.string.DeleteTitle)
		.setPositiveButton(R.string.All,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						mListener.deleteSelect(true);
					}
				})
		.setNegativeButton(R.string.Single,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						mListener.deleteSelect(false);
					}
				});
*/	}
	

	


}
