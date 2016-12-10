package src;
// OrderResize.java
import java.awt.*;

public class EditResize extends Order {
	
	private int x1, y1; // 중심이 되는 좌표
	private Shape temp; // 사이즈 변경을 위해 사용하는 임시 객체

	// 도형의 크기를 조절한다.
	public void mousePressExe(Point p, Drawing Layer)
	{
		Shape s = Layer.getTopShape(p); // 최상위 객체를 얻어온다.
		temp = s; 
		x1 = p.x;
		y1 = p.y;
	}
	public void mouseDragExe(Point p, Drawing Layer)
	{	
		if (temp != null&&Layer.getEditIndex()==Layer.getNowIndex(temp)) // 도형이 있고 선택된 객체의 인덱스와
		{																 // 현재 인덱스가 같으면
			temp.scale(p.x - x1, p.y - y1);			// 도형을 마우스 드래그한 만큼 크기조절한다.
			x1 = p.x;
			y1 = p.y;
		}      		
	}
}
