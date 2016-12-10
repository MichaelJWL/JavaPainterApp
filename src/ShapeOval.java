// ShapeOval.java
package src;
import java.awt.*;
import java.awt.geom.*;
import java.io.*;

//public class ShapeOval extends Shape {
public class ShapeOval extends Shape implements Serializable {
	
	private int left, top;          				// 원의 좌측 상단 모서리 좌표
  	private int width, height;      			// 원의 너비와 높이
  	private Ellipse2D.Double ellipse;
  	private boolean isFillPaint;				// 타원 색채우기 여부
  	public boolean BackGroundFill = false;
				// 타원 색채우기 여부
  	// 생성자
  	public ShapeOval(int left, int top, int width, int height, Color color)
  	{
  		super(color);
		this.left = left;
    	this.top = top;
   		this.width = width;
   		this.height = height;
  	}
  
  	// 원을 그린다
  	public void draw(Graphics g)
  	{
  		Graphics2D g2d = (Graphics2D) g;								// Graphics2D로 캐스팅

  		g2d.setColor(getColor());	
  		// 색을 설정하고
  		ellipse = new Ellipse2D.Double(left, top, width, height);	// 타원을 생성



  		if(isGradient==false){

  			g2d.setColor(getColor());											// 색을 설정하고
  			g2d.draw(ellipse);			
  		}
  		else if(isGradient==true){
  			if(GradientDirect==1)
  				g2d.setPaint(new GradientPaint(left,top,G_color1,left+(width)/2,top+height,G_color2,true));
  			g2d.fill(ellipse);	
  			if(GradientDirect==2)
  				g2d.setPaint(new GradientPaint(left,top+height,G_color1,left+(width)/2,top,G_color2,true));
  			g2d.fill(ellipse);	
  			if(GradientDirect==3)
  				g2d.setPaint(new GradientPaint(left+width,top,G_color1,left+width-(width)/2,top+height,G_color2,true));
  			g2d.fill(ellipse);	
  			if(GradientDirect==4)
  				g2d.setPaint(new GradientPaint(left+width,top+height,G_color1,left+width-(width)/2,top,G_color2,true));
  			g2d.fill(ellipse);	


  		}

  		// 타원을 그린다.
  		if (isFillPaint == true)												// 색채우기 명령이 들어오면
  			g2d.fill(ellipse);	
  		if (BackGroundFill == true)
  			g2d.fill(ellipse);

  		if (super.getSelect() == true)			{	
  			g.setColor(Color.BLACK);
  			g.fillRect(this.left-5,this.top-5,5,5);
  			g.fillRect(this.left-5,this.top+height,5,5);
  			g.fillRect(this.left+width,this.top-5,5,5);
  			g.fillRect(this.left+width,this.top+height,5,5);	
  		}											// 원의 색을 채운다.

  	}
	// 타원 영역안에 마우스커서가 들어오면 True를 리턴하여 색을 채우게 함 
	public boolean isFillPaint(Point p)
	{
		if (p.x >= left && p.x <= left + width && p.y >= top && p.y <= top + height)
			return this.isFillPaint = true;
		else
			return this.isFillPaint = false;
	}
	

	// 타원을 둘러싸는 가상의 사각형 안에 점이 위치하는지 알아낸다.
	public boolean containsPoint(Point p)
	{
		if (p.x >= left && p.x <= left + width && p.y >= top && p.y <= top + height)
			return true;
		else
			return false;
	}	

	// 원을 평행 이동한다.
	// 좌측 상단의 모서리 좌표를 deltaX, deltaY 만큼 이동시킨다.
  	public void move(int deltaX, int deltaY)
  	{
    		left += deltaX;
    		top += deltaY;
  	}
  	
	//	원의 지름의 크기를 조절하여 크기를 편집
	public void scale(int deltaX, int deltaY)
	{
		width += deltaX;
		height += deltaY; 
	}
	
	public int setLeftPoint(int x)		// 좌측 상단 모서리 좌표를 설정한다.
	{
		return left = x;
	}
	
	public int setTopPoint(int y)
	{
		return top = y;
	}
	//	객체 직렬화를 위한 ObjectOutputStream클래스의 writeObject 오버라이딩
	private void writeObject (ObjectOutputStream out) throws IOException
	{
		out.writeInt(left);
		out.writeInt(top);
		out.writeInt(width);
		out.writeInt(height);
		out.writeBoolean(isFillPaint);
	}
	//	객체 직렬화를 위한 ObjectInputStream클래스의 writeObject 오버라이딩
	private void readObject (ObjectInputStream in) 
					throws IOException, ClassNotFoundException
	{
		left = in.readInt();
		top = in.readInt();
		width = in.readInt();
		height = in.readInt();
		isFillPaint = in.readBoolean();
	}
}

