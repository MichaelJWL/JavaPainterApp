// DrawOval.java
package src;
import java.awt.*;

public class DrawOval extends Order{

	private Point first;	
	

	// 마우스를 누른 위치로부터 원을 생성한다. 
  	public void mousePressExe(Point p, Drawing Layer)
  	{
    		first = p;
    		ShapeOval o = new ShapeOval(p.x, p.y, 0, 0, Layer.getColor());
    		if(Layer.isEditing()==true)
    			Layer.deselect();
    		Layer.add(o); // 객체를 저장한다.
  	}

	// 마우스로 처음에 누른 점으로부터 드래그되는 점까지 원을 그린다.
  	public void mouseDragShiftExe(Point p, Drawing Layer)
  	{
    		ShapeOval o = new ShapeOval(Math.min(p.x, first.x), Math.min(p.y, first.y),
                      Math.abs(p.x - first.x), Math.abs(p.x - first.x), Layer.getColor());

		// 드래그 되는 동안 현재 커서위치에 있는 타원만을 보여준다.			                      
    		Layer.remainEndshape(o);
  	}
  	public void mouseDragExe(Point p, Drawing Layer)
  	{
    		ShapeOval o = new ShapeOval(Math.min(p.x, first.x), Math.min(p.y, first.y),
                      Math.abs(p.x - first.x), Math.abs(p.y - first.y), Layer.getColor());

		// 드래그 되는 동안 현재 커서위치에 있는 타원만을 보여준다.			                      
    		Layer.remainEndshape(o);
  	}
}
