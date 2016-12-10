// DrawTriangle.java
package src;
import java.awt.*;

public class DrawTriangle extends Order {
    
  	private int x1, y1;	         		// ó�� ������

  	// ���콺�� ó���� ���� �����κ��� �巡�׵Ǵ� ������ �ﰢ���� �׸���.
  	public void mouseDragExe(Point p, Drawing Layer)
  	{
		ShapeTriangle s = new ShapeTriangle(x1, y1, x1, y1, Layer.getColor());
		if(Layer.isEditing()==true)
		Layer.deselect();
		Layer.add(s);	// ��ü�� �����Ѵ�
    	ShapeTriangle tri = new ShapeTriangle(p.x,p.y,p.x,p.y, Layer.getColor());
    		
		// �巡�� �Ǵ� ���� ���� Ŀ����ġ�� �ִ� �������� �����ش�.	
    	Layer.remainEndshape(tri);		// ������ shapeSave ������ �� �ڷ� ������.
  	}
}