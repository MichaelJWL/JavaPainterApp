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
	
  	private int left, top;          					// �簢���� ���� ��� �𼭸� ��ǥ
  	private int width, height;         				// �簢���� �ʺ�� ����
  	private boolean isFillPaint;					// ��ä��� ����
  	public boolean BackGroundFill = false;
  	private Image img;

	// Ÿ�� ��ä��� ����
  	// ������
  	public ShapeImage(int left, int top, int width, int height, Color color, Image image)
  	{
    		super(color);
    		this.left = left;
    		this.top = top;
    		this.width = width;
    		this.height = height;
    		this.img = image;
  	}
  
  	// �簢���� �׸���.
  	public void draw(Graphics g)
  	{
		Graphics2D g2d = (Graphics2D) g;									//Graphics2D�� ĳ����
		g2d.drawImage(img,left,top,this);						// ���� ���� ä���.// ���� ä���.
  	}
	//	�簢�� �����ȿ� ���콺Ŀ���� ������ True�� �����Ͽ� ���� ä��� ��
	public boolean isFillPaint(Point p){return this.isFillPaint = false;}
	
  	// �簢�� �ȿ� ���� ��ġ�ϴ��� �˾Ƴ���.
  	public boolean containsPoint(Point p){return false;}
  
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
		width += deltaX;
		height += deltaY; 
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
		out.writeInt(width);
		out.writeInt(height);
		out.writeBoolean(isFillPaint);
	}
	//	��ü ����ȭ�� ���� ObjectInputStreamŬ������ writeObject �������̵�
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

