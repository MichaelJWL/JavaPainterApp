// DrawRect.java
package src;
import java.awt.*;

public class DrawRect extends Order {
	
  	private Point first;       // 사각형의 좌측 상단 모서리 좌표
  	
  	public void mousePressExe(Point p, Drawing Layer)
  	{
    		first = p;
    		ShapeRect r = new ShapeRect(p.x, p.y, 0, 0, Layer.getColor());
    		if(Layer.isEditing()==true)
    			Layer.deselect();
    		Layer.add(r);	// 객체를 저장한다.
  	}

  	// 마우스로 처음에 누른 점으로부터 드래그되는 점까지 사각형을 그린다.
  	public void mouseDragShiftExe(Point p, Drawing Layer)
  	{
    		ShapeRect r = new ShapeRect(Math.min(p.x, first.x), Math.min(p.y, first.y),
            	Math.abs(p.x - first.x), Math.abs(p.x - first.x), Layer.getColor());
    		
		// 드래그 되는 동안 현재 커서위치에 있는 사각형만을 보여준다.	
    		Layer.remainEndshape(r);	
  	}
  	
  	public void mouseDragExe(Point p, Drawing Layer)
  	{
    		ShapeRect r = new ShapeRect(Math.min(p.x, first.x), Math.min(p.y, first.y),
            	Math.abs(p.x - first.x), Math.abs(p.y - first.y), Layer.getColor());
    		
		// 드래그 되는 동안 현재 커서위치에 있는 사각형만을 보여준다.	
    		Layer.remainEndshape(r);	
  	}
}
