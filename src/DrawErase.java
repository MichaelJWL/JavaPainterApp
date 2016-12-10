// DrawErase.java
package src;
import java.awt.*;

public class DrawErase extends Order {
    
  	private int x1, y1;	         		// 처음 시작점

  	// 마우스로 처음에 누른 점으로부터 드래그되는 점까지 흰색 직선을 통해 지우개 역할을 하도록 만든다.
  	public void mouseDragExe(Point p, Drawing Layer)
  	{
		ShapeErase s = new ShapeErase(x1, y1, x1, y1, Layer.getColor());
		
		if(Layer.isEditing()==true)
		Layer.deselect();
		Layer.add(s);	// 객체를 저장한다
		ShapeErase point = new ShapeErase(p.x,p.y,p.x,p.y, Layer.getColor());
    		
		// 드래그 되는 동안 현재 커서위치에 있는 직선만을 보여준다.	
    	Layer.remainEndshape(point);	// 직선을 shapeSave 백터의 맨 뒤로 보낸다.
  	}
}