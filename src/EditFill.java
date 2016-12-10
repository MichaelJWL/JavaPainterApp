// OrderFill.java
package src;
import java.awt.*;

public class EditFill extends Order {

  	// 도형의 색깔을 바꾼다
	public void mouseClickExe(Point p, Drawing Layer)
  	{
  		// 마우스를 클릭해서 얻는 점을 포함하는 도형중에
  		// 가장 앞에 있는 도형을 찾는다.
    		Shape s = Layer.getTopShape(p);
		if (s != null)									// 도형이 있으면
		{
    			if(s.containsPoint(p))// 점을 포함했는지 확인한다.
    			{
				s.setColor(Layer.getColor());    // 도형의 색깔을 설정한다.
				s.isFillPaint(p); // 색을 채운다.
				
    			}
		}      		
    }
}
