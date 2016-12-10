// DrawErase.java
package src;
import java.awt.*;

public class DrawErase extends Order {
    
  	private int x1, y1;	         		// ó�� ������

  	// ���콺�� ó���� ���� �����κ��� �巡�׵Ǵ� ������ ��� ������ ���� ���찳 ������ �ϵ��� �����.
  	public void mouseDragExe(Point p, Drawing Layer)
  	{
		ShapeErase s = new ShapeErase(x1, y1, x1, y1, Layer.getColor());
		
		if(Layer.isEditing()==true)
		Layer.deselect();
		Layer.add(s);	// ��ü�� �����Ѵ�
		ShapeErase point = new ShapeErase(p.x,p.y,p.x,p.y, Layer.getColor());
    		
		// �巡�� �Ǵ� ���� ���� Ŀ����ġ�� �ִ� �������� �����ش�.	
    	Layer.remainEndshape(point);	// ������ shapeSave ������ �� �ڷ� ������.
  	}
}