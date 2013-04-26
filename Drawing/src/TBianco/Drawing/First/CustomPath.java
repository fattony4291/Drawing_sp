package TBianco.Drawing.First;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

import TBianco.Drawing.First.CustomPath.PathAction.PathActionType;
import android.graphics.Path;

public class CustomPath extends Path implements Serializable {

	private static final long serialVersionUID = -5974912367682897467L;

	private ArrayList<PathAction> actions = new ArrayList<CustomPath.PathAction>();

	public CustomPath(CustomPath mPath) {
		// TODO Auto-generated constructor stub
		super(mPath);
	}

	public CustomPath() {
		// TODO Auto-generated constructor stub
		super();
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
		in.defaultReadObject();
		drawThisPath();
	}

	@Override
	public void moveTo(float x, float y) {
		actions.add(new ActionMove(x, y));
		super.moveTo(x, y);
	}

	@Override
	public void lineTo(float x, float y){
		actions.add(new ActionLine(x, y));
		super.lineTo(x, y);
	}

	@Override
	public void quadTo(float x1, float y1, float x2, float y2){
		actions.add(new ActionQuad(x1, y1, x2, y2));
		super.quadTo(x1, y1, x2, y2);
	}
	
	private void drawThisPath(){
		for(PathAction p : actions){
			if(p.getType().equals(PathActionType.MOVE_TO)){
				super.moveTo(p.getX1(), p.getY1());
			} else if(p.getType().equals(PathActionType.LINE_TO)){
				super.lineTo(p.getX1(), p.getY1());
			}
			else if(p.getType().equals(PathActionType.QUAD_TO)){
				super.quadTo(p.getX1(), p.getY1(), p.getX2(), p.getY2());
			}
		}
	}

	public interface PathAction {
		public enum PathActionType {LINE_TO,MOVE_TO,QUAD_TO};
		public PathActionType getType();
		public float getX1();
		public float getY1();
		public float getX2();
		public float getY2();
	}

	public class ActionMove implements PathAction, Serializable{
		private static final long serialVersionUID = -7198142191254133295L;

		private float x,y;

		public ActionMove(float x, float y){
			this.x = x;
			this.y = y;
		}

		@Override
		public PathActionType getType() {
			return PathActionType.MOVE_TO;
		}

		@Override
		public float getX1() {
			return x;
		}

		@Override
		public float getY1() {
			return y;
		}

		@Override
		public float getX2() {
			// TODO Auto-generated method stub
			return x;
		}

		@Override
		public float getY2() {
			// TODO Auto-generated method stub
			return y;
		}

	}

	public class ActionLine implements PathAction, Serializable{
		private static final long serialVersionUID = 8307137961494172589L;

		private float x,y;

		public ActionLine(float x, float y){
			this.x = x;
			this.y = y;
		}

		@Override
		public PathActionType getType() {
			return PathActionType.LINE_TO;
		}

		@Override
		public float getX1() {
			return x;
		}

		@Override
		public float getY1() {
			return y;
		}

		@Override
		public float getX2() {
			return x;
		}

		@Override
		public float getY2() {
			return y;
		}

	}

	public class ActionQuad implements PathAction, Serializable{
		private static final long serialVersionUID = 8307137361454172789L;

		private float x1,y1,x2,y2;

		public ActionQuad(float x1, float y1, float x2, float y2){
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}

		@Override
		public PathActionType getType() {
			return PathActionType.LINE_TO;
		}

		@Override
		public float getX1() {
			return x1;
		}

		@Override
		public float getY1() {
			return y1;
		}

		@Override
		public float getX2() {
			return x2;
		}

		@Override
		public float getY2() {
			return y2;
		}


	}


}

