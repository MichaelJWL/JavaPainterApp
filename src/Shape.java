// Shape.java
package src;
import java.awt.*;
import java.io.*;

//public abstract class Shape implements Cloneable {
public abstract class Shape implements Cloneable, Serializable{
  
	public Color G_color1, G_color2;
	private Color color;      // 도형의 색깔
	public int GradientDirect=1;
    private boolean isSelected;
    public boolean isGradient;
  	// 도형을 그린다.
  	public abstract void draw(Graphics g);           
    
  	// 도형이 점 p를 포함했으면 true, 포함하지 않았으면 false
 	public abstract boolean containsPoint(Point p); 
  	
  	// 도형을 평행이동한다.
  	public abstract void move(int deltaX, int deltaY);
  	
  	// 도형의 크기를 편집한다.
	public abstract void scale(int deltaX, int deltaY);
	
	public abstract int setLeftPoint(int left); // 좌측 상단 모서리 좌표
	public abstract int setTopPoint(int top);
  	
	public abstract boolean isFillPaint(Point p);	// 도형 속을 채울 것인지 True False 리턴
	public void setGradient()
	{
		isGradient = true;
	}
	
  	// 생성자. 도형의 처음 색깔을 설정한다.
  	public Shape(Color initColor)
  	{
  		color = initColor;
  	}
  
   	// 도형의 색깔을 얻는다.
   	public Color getColor()
  	{
    		return color;
  	}
   	public void setGradientColor(Color c1, Color c2)
   	{
   		G_color1=c1;
   		G_color2=c2;
   	}
    public void setGradientDirect(int i)
    {
    	GradientDirect = i;
    }
  	// 도형의 색깔을 설정한다
  	public void setColor(Color newColor)
  	{
   		color = newColor;
  	}
   
	// Clone 메소드를 쓰기 위해서 Super버전을 오버라이딩
	public Object clone() throws CloneNotSupportedException 
	{
		return super.clone();
	}
	public boolean getSelect()
	{
		return isSelected;
	}
    public void setSelect(boolean b)
    {
    	isSelected = b;
    }
	
}

  