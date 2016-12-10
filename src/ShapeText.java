package src;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.JOptionPane;

public class ShapeText extends Shape implements Serializable {

	private int left, top;          					// 사각형의 좌측 상단 모서리 좌표
  	private int TextSize = 10;     
  	private String Text;
  	private String InputSize;
  	private int fontStyle = Font.PLAIN;				// 색채우기 여부

	// 타원 색채우기 여부
  	// 생성자
  	public ShapeText(int left, int top, Color color)
  	{
    		super(color);
    		this.left = left;
    		this.top = top;
    		while(true)
    		{
    			
    			if(Text==null || Text.equals("")) Text=JOptionPane.showInputDialog(null,"텍스트를 입력해주세요");	//그릴 텍스트를 입력받기 위한 옵션팬
    			if(InputSize==null || InputSize.equals("") || isNumber(InputSize)== true) InputSize=JOptionPane.showInputDialog(null,"텍스트크기를 입력해주세요");	//텍스트의 크기를 지정하기 위한 옵션팬
    			TextSize = Integer.parseInt(InputSize);
    			if(Text!=null && !Text.equals("")) break;
    		}
  	}
  
    public static boolean isNumber(String str){
        boolean result = false; 
        try{
            Double.parseDouble(str) ;
            result = true ;
        }catch(Exception e){}
         
         
        return result ;
    }
     
  	// 사각형을 그린다.
  	public void draw(Graphics g)
  	{
		g.setColor(this.getColor());
		g.setFont(new Font(null,fontStyle,TextSize));
		g.drawString(Text, left, top);
		
  	}
	//	사각형 영역안에 마우스커서가 들어오면 True를 리턴하여 색을 채우게 함
	public boolean isFillPaint(Point p) {return true;};
	public boolean isGradient(Point p) {return true;}
  	// 사각형 안에 점이 위치하는지 알아낸다.
  	public boolean containsPoint(Point p)
  	{
  		return false;
  	}
  
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
		out.writeInt(fontStyle);
		out.writeInt(TextSize);
		out.writeChars(Text);
	}
	//	객체 직렬화를 위한 ObjectInputStream클래스의 writeObject 오버라이딩
	private void readObject (ObjectInputStream in) 
					throws IOException, ClassNotFoundException
	{
		left = in.readInt();
		top = in.readInt();
		fontStyle = in.readInt();
		TextSize = in.readInt();
		Text = in.readUTF();
	}

}
