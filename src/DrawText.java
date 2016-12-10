package src;

import java.awt.Point;

public class DrawText extends Order {
	    
	  	private int x1, y1;	         		// ó�� ������
	    private Shape temp;
	  	// ���콺�� ���� �����κ��� ���ο� ������ �����ϰ� 
	  	public void mousePressExe(Point p, Drawing Layer)
	  	{
	    		x1 = p.x;
	    		y1 = p.y;	//Text�� ���� ��� ��ǥ�� ���� ���콺 ��ġ��
	    		ShapeText s = new ShapeText(x1, y1, Layer.getColor()); //Text�� �׸���.
	    		if(Layer.isEditing()==true)
	    		Layer.deselect();
	    		Layer.add(s);	// ��ü�� �����Ѵ�.
	    		Layer.remainEndshape(s);		// ������ shapeSave ������ �� �ڷ� ������.
	  	}
}
