// ShapeRect.java
package src;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

//public class ShapeRect extends Shape {
public class ShapeImage extends Shape implements Serializable, ImageObserver {
	
  	private int left, top;          					// 사각형의 좌측 상단 모서리 좌표
  	private int width, height;         				// 사각형의 너비와 높이
  	private boolean isFillPaint;					// 색채우기 여부
  	public boolean BackGroundFill = false;
  	private Image img;

	// 타원 색채우기 여부
  	// 생성자
  	public ShapeImage(int left, int top, int width, int height, Color color, Image image)
  	{
    		super(color);
    		this.left = left;
    		this.top = top;
    		this.width = width;
    		this.height = height;
    		this.img = image;
  	}
  
  	// 사각형을 그린다.
  	public void draw(Graphics g)
  	{
		Graphics2D g2d = (Graphics2D) g;									//Graphics2D로 캐스팅
		g2d.drawImage(img,left,top,this);						// 원의 색을 채운다.// 색을 채운다.
  	}
	//	사각형 영역안에 마우스커서가 들어오면 True를 리턴하여 색을 채우게 함
	public boolean isFillPaint(Point p){return this.isFillPaint = false;}
	
  	// 사각형 안에 점이 위치하는지 알아낸다.
  	public boolean containsPoint(Point p){return false;}
  
  	// 사각형을 이동한다.
  	// 좌측 상단의 모서리 좌표를 deltaX, deltaY 만큼 이동시킨다.
  	public void move(int deltaX, int deltaY)
  	{
    		left += deltaX;
    		top += deltaY;
  	}
  	
  	// 사각형의 크기를 조절한다.
  	// 사각형의 너비와 높이를 deltaX, deltaY 만큼 가감한다.
	public void scale(int deltaX, int deltaY)
	{
		width += deltaX;
		height += deltaY; 
	}

	public int setLeftPoint(int x)		// 좌측 상단 모서리 좌표
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

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		return false;
	}
}

