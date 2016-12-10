// Shape.java
package src;
import java.awt.*;
import java.io.*;

//public abstract class Shape implements Cloneable {
public abstract class Shape implements Cloneable, Serializable{
  
	public Color G_color1, G_color2;
	private Color color;      // ������ ����
	public int GradientDirect=1;
    private boolean isSelected;
    public boolean isGradient;
  	// ������ �׸���.
  	public abstract void draw(Graphics g);           
    
  	// ������ �� p�� ���������� true, �������� �ʾ����� false
 	public abstract boolean containsPoint(Point p); 
  	
  	// ������ �����̵��Ѵ�.
  	public abstract void move(int deltaX, int deltaY);
  	
  	// ������ ũ�⸦ �����Ѵ�.
	public abstract void scale(int deltaX, int deltaY);
	
	public abstract int setLeftPoint(int left); // ���� ��� �𼭸� ��ǥ
	public abstract int setTopPoint(int top);
  	
	public abstract boolean isFillPaint(Point p);	// ���� ���� ä�� ������ True False ����
	public void setGradient()
	{
		isGradient = true;
	}
	
  	// ������. ������ ó�� ������ �����Ѵ�.
  	public Shape(Color initColor)
  	{
  		color = initColor;
  	}
  
   	// ������ ������ ��´�.
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
  	// ������ ������ �����Ѵ�
  	public void setColor(Color newColor)
  	{
   		color = newColor;
  	}
   
	// Clone �޼ҵ带 ���� ���ؼ� Super������ �������̵�
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

  