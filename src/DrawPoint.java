// DrawPoint.java
package src;
import java.awt.*;

public class DrawPoint extends Order {
    
  	private int x1, y1;	         		// ó�� ������
  	
  	// ���콺�� ó���� ���� �����κ��� �巡�׵Ǵ� ������ ������ �׸���.
  	public void mouseDragExe(Point p, Drawing Layer)
  	{
		ShapePoint s = new ShapePoint(x1, y1, x1, y1, Layer.getColor());
		if(Layer.isEditing()==true)
		Layer.deselect();
		Layer.add(s);	// ��ü�� �����Ѵ�
    	ShapePoint point = new ShapePoint(p.x,p.y,p.x,p.y, Layer.getColor());
    		
		// �巡�� �Ǵ� ���� ���� Ŀ����ġ�� �ִ� �������� �����ش�.	
    	Layer.remainEndshape(point);		// ������ shapeSave ������ �� �ڷ� ������.
  	}
}