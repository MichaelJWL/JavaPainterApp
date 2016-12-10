// DrawMagic.java

package src;
import java.awt.*;

public class DrawMagic extends Order {
    
  	private int x1, y1;	         		// ó�� ������
  	

  	// ���콺�� ó���� ���� �����κ��� �巡�׵Ǵ� ������ ��ǥ ����� �׸���.
  	public void mouseDragExe(Point p, Drawing Layer)
  	{
		ShapeMagic s = new ShapeMagic(x1, y1, x1, y1, Layer.getColor());
		if(Layer.isEditing()==true)
		Layer.deselect();
		Layer.add(s);	// ��ü�� �����Ѵ�
    	ShapeMagic point = new ShapeMagic(p.x,p.y,p.x,p.y, Layer.getColor());
    		
		// �巡�� �Ǵ� ���� ���� Ŀ����ġ�� �ִ� �������� �����ش�.	
    	Layer.remainEndshape(point);		// ������ shapeSave ������ �� �ڷ� ������.
  	}
}