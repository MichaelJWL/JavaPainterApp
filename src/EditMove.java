package src;
import java.awt.*;

public class EditMove extends Order {
	
	private Point lastPoint;	// 마우스로 얻은 마지막 점
    private Shape s;			// 이동할 도형
 
  	public void mousePressExe(Point p, Drawing Layer)
  	{
		// 마우스를 눌렀을 때 얻은 점을 포함하는 도형중에
		// 가장 앞에 있는 도형을 찾는다.
		s = Layer.getTopShape(p);
    		if (s != null){		// 객체가 있으면
			
      		lastPoint = p;		
      			
    		}// 마우스를 눌렀을 때 커서의 위치를 저장
  	}
  
  	public void mouseDragExe(Point p, Drawing Layer)
  	{
    		if (s != null&&Layer.getEditIndex()==Layer.getNowIndex(s))  // 마우스를 눌렀을 때 도형이 있으면서 현재 객체의 인덱스와
    		{															// 현재 객체의 인덱스가 같으면
			// 도형을 deltaX, deltaY만큼 움직인다
      		s.move(p.x - lastPoint.x, p.y - lastPoint.y); 	
			// 움직인 후에 현재 마우스의 위치를 저장한다
      		lastPoint = p;
    		}
			
  	}
	
}
