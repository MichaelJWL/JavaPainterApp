package src;
// ShapeSayung.java
import java.awt.*;
import java.awt.geom.*;
import java.io.*;

public class ShapeSayung extends Shape implements Serializable {	
	
	// 점(x1, y1)과 점(x2, y2)를 연결하는 직선
  	private int x1, y1, x2, y2;
		
	
  	private Line2D.Double line;
  
  	// Line 생성자
	public ShapeSayung(int x1, int y1, int x2, int y2, Color color)
  	{
  		super(color);				// 색깔, 선 굵기 초기화
    	this.x1 = x1;
    	this.y1 = y1;
    	this.x2 = x2;
    	this.y2 = y2;
  	}
	  
	// 직선을 그린다.
  	public void draw(Graphics g)
  	{
  		Graphics2D g2d = (Graphics2D) g;									// Graphics2D로 캐스팅
    	line = new Line2D.Double(x1, y1, x2, y2); 

    	g2d.setColor(getColor());											// 색을 설정하고
		g2d.draw(line);			
  	
    	
		if (super.getSelect() == true)		{	
		g.setColor(Color.BLACK);
		g.fillRect(this.x1,this.y1,2,2);
		g.fillRect(this.x1,this.y2,2,2);
		g.fillRect(this.x2,this.y2,2,2);
		g.fillRect(this.x2,this.y1,2,2);}// 원의 색을 채운다.// 선을 그린다.
  	}
 	
 	// 직선은 색 채우기가 필요하지 않지만 Shape의 추상메소드이므로 오버라이딩한다.
	public boolean isFillPaint(Point p) {return true;}
	public boolean isGradient(Point p) {return true;}
	

	// 원을 둘러싸는 가상의 사각형 안에 점이 위치하는지 알아낸다.
	public boolean containsPoint(Point p)
	{
		int left = Math.min(x1, x2);
		int top = Math.min(y1, y2);
		int width = Math.abs(x1 - x2);
		int height = Math.abs(y1 - y2);
		
		if (p.x >= left && p.x <= left + width && p.y >= top && p.y <= top + height)
			return true;		
		else
			return false;		
		
	}

  	// 직선을 X축으로부터 deltaX, Y축으로부터deltaY만큼 평행이동
  	public void move(int deltaX, int deltaY)
  	{
    		x1 += deltaX;
    		y1 += deltaY;
    		x2 += deltaX;
    		y2 += deltaY;
  	}
  	
  	// 직선의 크기를 조절한다.
	public void scale(int deltaX, int deltaY)
	{
		x2 += deltaX;
		y2 += deltaY; 
	}
	
	public int setLeftPoint(int x)		// 점(x1, y1)과 점(x2, y2)를 deltaX만큼 더해준다
	{
		int deltaX;
		deltaX = x - x1;
		x2 += deltaX;
		
		return x1 = x;
	}
	
	public int setTopPoint(int y)		// 점(x1, y1)과 점(x2, y2)를 deltaX만큼 더해준다
	{
		int deltaY;
		deltaY = y - y1;
		y2 += deltaY;
		
		return y1 = y;
	}
	
	//	객체 직렬화를 위한 ObjectOutputStream클래스의 writeObject 오버라이딩
	private void writeObject(ObjectOutputStream out) throws IOException
	{
		out.writeInt(x1);
		out.writeInt(y1);
		out.writeInt(x2);
		out.writeInt(y2);
	}
	
	//	객체 직렬화를 위한 ObjectInputStream클래스의 writeObject 오버라이딩	
	private void readObject (java.io.ObjectInputStream in)
						throws IOException, ClassNotFoundException
	{
		x1 = in.readInt();
		y1 = in.readInt();
		x2 = in.readInt();
		y2 = in.readInt();
	}
}

