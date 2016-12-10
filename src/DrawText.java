package src;

import java.awt.Point;

public class DrawText extends Order {
	    
	  	private int x1, y1;	         		// 처음 시작점
	    private Shape temp;
	  	// 마우스로 누른 점으로부터 새로운 직선을 생성하고 
	  	public void mousePressExe(Point p, Drawing Layer)
	  	{
	    		x1 = p.x;
	    		y1 = p.y;	//Text의 좌측 상단 좌표를 현재 마우스 위치로
	    		ShapeText s = new ShapeText(x1, y1, Layer.getColor()); //Text를 그린다.
	    		if(Layer.isEditing()==true)
	    		Layer.deselect();
	    		Layer.add(s);	// 객체를 저장한다.
	    		Layer.remainEndshape(s);		// 직선을 shapeSave 백터의 맨 뒤로 보낸다.
	  	}
}
