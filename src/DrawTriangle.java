// DrawTriangle.java
package src;
import java.awt.*;

public class DrawTriangle extends Order {
    
  	private int x1, y1;	         		// 처음 시작점

  	// 마우스로 처음에 누른 점으로부터 드래그되는 점까지 삼각형을 그린다.
  	public void mouseDragExe(Point p, Drawing Layer)
  	{
		ShapeTriangle s = new ShapeTriangle(x1, y1, x1, y1, Layer.getColor());
		if(Layer.isEditing()==true)
		Layer.deselect();
		Layer.add(s);	// 객체를 저장한다
    	ShapeTriangle tri = new ShapeTriangle(p.x,p.y,p.x,p.y, Layer.getColor());
    		
		// 드래그 되는 동안 현재 커서위치에 있는 직선만을 보여준다.	
    	Layer.remainEndshape(tri);		// 직선을 shapeSave 백터의 맨 뒤로 보낸다.
  	}
}