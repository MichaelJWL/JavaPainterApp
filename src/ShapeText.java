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

	private int left, top;          					// �簢���� ���� ��� �𼭸� ��ǥ
  	private int TextSize = 10;     
  	private String Text;
  	private String InputSize;
  	private int fontStyle = Font.PLAIN;				// ��ä��� ����

	// Ÿ�� ��ä��� ����
  	// ������
  	public ShapeText(int left, int top, Color color)
  	{
    		super(color);
    		this.left = left;
    		this.top = top;
    		while(true)
    		{
    			
    			if(Text==null || Text.equals("")) Text=JOptionPane.showInputDialog(null,"�ؽ�Ʈ�� �Է����ּ���");	//�׸� �ؽ�Ʈ�� �Է¹ޱ� ���� �ɼ���
    			if(InputSize==null || InputSize.equals("") || isNumber(InputSize)== true) InputSize=JOptionPane.showInputDialog(null,"�ؽ�Ʈũ�⸦ �Է����ּ���");	//�ؽ�Ʈ�� ũ�⸦ �����ϱ� ���� �ɼ���
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
     
  	// �簢���� �׸���.
  	public void draw(Graphics g)
  	{
		g.setColor(this.getColor());
		g.setFont(new Font(null,fontStyle,TextSize));
		g.drawString(Text, left, top);
		
  	}
	//	�簢�� �����ȿ� ���콺Ŀ���� ������ True�� �����Ͽ� ���� ä��� ��
	public boolean isFillPaint(Point p) {return true;};
	public boolean isGradient(Point p) {return true;}
  	// �簢�� �ȿ� ���� ��ġ�ϴ��� �˾Ƴ���.
  	public boolean containsPoint(Point p)
  	{
  		return false;
  	}
  
  	// �簢���� �̵��Ѵ�.
  	// ���� ����� �𼭸� ��ǥ�� deltaX, deltaY ��ŭ �̵���Ų��.
  	public void move(int deltaX, int deltaY)
  	{
    		left += deltaX;
    		top += deltaY;
  	}
  	
  	// �簢���� ũ�⸦ �����Ѵ�.
  	// �簢���� �ʺ�� ���̸� deltaX, deltaY ��ŭ �����Ѵ�.
	public void scale(int deltaX, int deltaY)
	{
		
	}

	public int setLeftPoint(int x)		// ���� ��� �𼭸� ��ǥ
	{
		return left = x;
	}
	
	public int setTopPoint(int y)
	{
		return top = y;
	}
	//	��ü ����ȭ�� ���� ObjectOutputStreamŬ������ writeObject �������̵�
	private void writeObject (ObjectOutputStream out) throws IOException 
	{
		out.writeInt(left);
		out.writeInt(top);
		out.writeInt(fontStyle);
		out.writeInt(TextSize);
		out.writeChars(Text);
	}
	//	��ü ����ȭ�� ���� ObjectInputStreamŬ������ writeObject �������̵�
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
