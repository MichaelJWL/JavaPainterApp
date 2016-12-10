// DrawSayung.java
package src;
import java.awt.*;

public class DrawSayung extends Order {
    
  	private int x1, y1;	         		// ó�� ������
  	
  	// ���콺�� ���� �����κ��� ���ο� ������ �����ϰ� 
  	public void mousePressExe(Point p, Drawing Layer)
  	{
    		x1 = p.x;
    		y1 = p.y;
    		ShapeLine s = new ShapeLine(x1, y1, x1, y1, Layer.getColor());
    		if(Layer.isEditing()==true)
    		Layer.deselect();
    		Layer.add(s);	// ��ü�� �����Ѵ�.
		}

  	// ó�� Ŭ���� ��ġ���� ���� ��ġ���� ������ �׸���.
  	public void mouseDragExe(Point p, Drawing Layer)
  	{
		ShapeSayung s = new ShapeSayung(x1, y1, x1, y1, Layer.getColor());
		if(Layer.isEditing()==true)
		Layer.deselect();
		Layer.add(s);	// ��ü�� �����Ѵ�
		ShapeSayung Sa = new ShapeSayung(x1, y1, p.x, p.y, Layer.getColor());
    		
		// �巡�� �Ǵ� ���� ���� Ŀ����ġ�� �ִ� �������� �����ش�.	
    	Layer.remainEndshape(Sa);		// ������ shapeSave ������ �� �ڷ� ������.
  	}
}