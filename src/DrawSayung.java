// DrawSayung.java
package src;
import java.awt.*;

public class DrawSayung extends Order {
    
  	private int x1, y1;	         		// 처음 시작점
  	
  	// 마우스로 누른 점으로부터 새로운 직선을 생성하고 
  	public void mousePressExe(Point p, Drawing Layer)
  	{
    		x1 = p.x;
    		y1 = p.y;
    		ShapeLine s = new ShapeLine(x1, y1, x1, y1, Layer.getColor());
    		if(Layer.isEditing()==true)
    		Layer.deselect();
    		Layer.add(s);	// 객체를 저장한다.
		}

  	// 처음 클릭된 위치에서 현재 위치까지 직선을 그린다.
  	public void mouseDragExe(Point p, Drawing Layer)
  	{
		ShapeSayung s = new ShapeSayung(x1, y1, x1, y1, Layer.getColor());
		if(Layer.isEditing()==true)
		Layer.deselect();
		Layer.add(s);	// 객체를 저장한다
		ShapeSayung Sa = new ShapeSayung(x1, y1, p.x, p.y, Layer.getColor());
    		
		// 드래그 되는 동안 현재 커서위치에 있는 직선만을 보여준다.	
    	Layer.remainEndshape(Sa);		// 직선을 shapeSave 백터의 맨 뒤로 보낸다.
  	}
}