package src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.Serializable;
// Drawing.java
import java.util.Vector;

public class Drawing implements Serializable {

	private Vector saveSpace[] = new Vector[100]; // Shape�� ������ Vector���� Layer�� ǥ���ϱ� ���� �迭�� ǥ��
	private int CurrentVector = -1;
	private Color nowColor; // ���� ����
    private int EditingIndex; // ���� ������ ��ü�� Vector �ε���z
	private Shape doTemp, copyTemp, pasteTemp; // ����, �ٿ��ֱ��� �ӽ� Shape

	// ������ ������ Vector�� �����ϰ� ����� �� ��Ÿ���� �ʱ�ȭ �Ѵ�.
	public Drawing(Color init_color) {
		saveSpace[++CurrentVector] = new Vector();
		nowColor = init_color;
		ShapeRect BackGround = new ShapeRect(-1,-1,555,405,nowColor); //��� ����
		
		BackGround.setColor(Color.white);	//����� ���� �������
		BackGround.BackGroundFill = true;	//����� ä��
 		saveSpace[CurrentVector].add(BackGround); //����� �ε��� 0���� ����
 		
 		saveSpace[++CurrentVector] = new Vector(); //���� �������� 1�� �ε������� ���
	}
	public void DrawImage(Image image) {	//�̹����� �ҷ����� ���� �޼ҵ�
		for(int i = 0; i <= CurrentVector; i++)//���̾� �ʱ�ȭ
			saveSpace[i] = null;
		CurrentVector = 0;
		saveSpace[CurrentVector] = new Vector();
		ShapeImage LoadImage = new ShapeImage(-1, -1, 555, 405, nowColor, image);
		saveSpace[CurrentVector++].add(LoadImage); //���� �ҷ��� �̹����� ���ó�� ���. 0�� �ε����� ����
		saveSpace[CurrentVector] = new Vector();
	}
	public void AddSpace() {	//���̾� �߰��� ���� �޼ҵ�
		saveSpace[++CurrentVector] = new Vector();
	}
	public void DeleteSpace(){		//���̾� ������ ���� �޼ҵ�
		saveSpace[CurrentVector--] = new Vector();
		if(CurrentVector == 0)		//����� �������� �ʵ��� 0�� �� ��� 1�� ������
		{
			saveSpace[++CurrentVector] = new Vector();
		}
	}
	//////////----------------------ADDED BY JW--------------------------------//////////
	public ShapeRect getBackgroundLayer(){
		return (ShapeRect) saveSpace[0].get(0);
	}
	//////////--------------------------END------------------------------------//////////
	// ������ �����Ѵ�.
	public void setColor(Color color) {
		nowColor = color;
	}
	// ���� ������ ��´�
	public Color getColor() {
		return nowColor;
	}
	// �Է¹��� �ε����� Shape�� ���´�.
	public Shape getShape(int i)
	{
		return (Shape) saveSpace[CurrentVector].get(i);
	}
	// ���õ� ��ü�� �����Ѵ�.
    public void delete()
    {
    	saveSpace[CurrentVector].remove(getEditIndex());
    }
	// ������ ���Ϳ� �����Ѵ�.
	public void add(Shape s) {
		saveSpace[CurrentVector].add(s);
	}
	// ���Ͱ����� ����� ������ ó������ �����ͼ� �׸���.
	public void draw(Graphics g) {
		for (int j = 0; j <= CurrentVector; j ++)
			for (int i = 0; i < saveSpace[j].size(); i++)
			{
				((Shape) saveSpace[j].get(i)).draw(g);
			}
	}
	public void drawsave(BufferedImage im) {		//�׷��� �̹����� �����ϱ� ���� �޼ҵ�
		Graphics g = im.createGraphics();		//BufferedImage�� ����.
		for (int j = 0; j <= CurrentVector; j ++)
			for (int i = 0; i < saveSpace[j].size(); i++)
			{
				((Shape) saveSpace[j].get(i)).draw(g);		//���� ���Ͱ����� �׷��� ������ ó������ �ٽ� �׸�.
			}
	}
	// ���õ� ��ü�� �ε����� ���´�.
	public int getEditIndex()
	{
		for (int i =0; i <= saveSpace[CurrentVector].size() - 1; i++)
			if (((Shape) saveSpace[CurrentVector].get(i)).getSelect()==true) 
			 return i;
			return 0;
	}
	// ���� �۾����� �ε����� ���´�.
	public int getNowIndex(Shape s)
	{
		for (int i =0; i <= saveSpace[CurrentVector].size() - 1; i++)
		    if(i==saveSpace[CurrentVector].indexOf((Shape) s))
			 return i;
			return 0;
	}
	// ��ü�� ���� ������ �Ǵ��Ѵ�.
    public boolean isEditing()
    {
    	if(saveSpace[CurrentVector].isEmpty()==true||((Shape) saveSpace[CurrentVector].get(getEditIndex())).getSelect()==false)
    	   return false;
    	return true;
    }
	// ���͸� ���� �ֱ��� �ε������� �˻��Ͽ� ���� ���콺 Ŀ���� ��ġ�� ���� �ִ� ������ �����Ѵ�.
	public Shape getTopShape(Point p) {
		for (int i = saveSpace[CurrentVector].size() - 1; i >= 0; i--)
			// for���� �Ųٷ� ������.
			if (((Shape) saveSpace[CurrentVector].get(i)).containsPoint(p)) 
			return (Shape) saveSpace[CurrentVector].get(i); // ���;ȿ� ����Ǿ� �ִ� ������ �����Ѵ�.
	
		return null;
	}
	// ��ü�� ��������
   public void deselect(){
   	doTemp = (Shape) saveSpace[CurrentVector].lastElement(); // ���� s�� ����Ǿ� �ִ� ��ġ�� ��´�.
	doTemp.setSelect(false);
   	}
	// Vector Ŭ������ indexOf�� remove�� �̿��Ͽ� ���� s�� �����.
	public void remove(Shape s) {
		int index = saveSpace[CurrentVector].indexOf(s); // ���� s�� ����Ǿ� �ִ� ��ġ�� ��´�.
		if (index >= 0)
			saveSpace[CurrentVector].remove(index); // saveSpace�迭���� �����.
	}
	// saveSpace �迭�� ��� ����.
	public void newShape() {
		for(int i = 1; i <= CurrentVector; i++)
			saveSpace[i].removeAllElements();
		
		ShapeRect BackGround = new ShapeRect(-1,-1,555,405,nowColor); //��� ����
		
		BackGround.setColor(Color.white);
		BackGround.BackGroundFill = true;
 		saveSpace[0].removeAllElements();
 		saveSpace[0].add(BackGround);
		
		CurrentVector = 1;
		copyTemp = null;
		pasteTemp = null;
	}
	// Vector ������ �ڿ������� ��ü�� �ϳ��� �����.
    public void undo(){
        saveSpace[CurrentVector].remove(saveSpace[CurrentVector].indexOf(saveSpace[CurrentVector].lastElement()));
    }
	// Shape s�� Clone()�ؼ� copyTemp�� �����Ѵ�.
	public void copy(Shape s) {
		try {
			copyTemp = (Shape) s.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	// Shape s�� Clone()�ؼ� copyTemp�� ������ �� �� ���� s�� �����Ѵ�
	public void cut(Shape s) {
		try {
			copyTemp = (Shape) s.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		int index = saveSpace[CurrentVector].indexOf(s); // ���� s�� ����Ǿ� �ִ� ��ġ�� ��´�.
		if (index >= 0)
			saveSpace[CurrentVector].remove(index); // saveSpace�迭���� �����.
	}
	// ���õ� ��ü�� �� �ڷ� ������,
	public void moveToBack(Shape s)
  	{
    		int index = saveSpace[CurrentVector].indexOf(s);    // ���� s�� ����Ǿ� �ִ� ��ġ�� �˾Ƴ���
    		if (index >= 0)
    		{
      		saveSpace[CurrentVector].remove(index);      	 // ���� s�� �����Ŀ�
      			if(CurrentVector == 0)
      				saveSpace[CurrentVector].add(1, s);
      			else
      				saveSpace[CurrentVector].add(0, s);

    		}    		
  	}
	// copyTemp�� ����� Shape�� Clone()�Ͽ� pasteTemp�������� saveSpace�� �ǵڿ� ���Ѵ�.
	public Shape paste(Point p) {
		copyTemp.setLeftPoint(p.x); // copyTemp�� (x, y) ��ǥ�� ���콺 Ŭ�� ��ġ�� ����
		copyTemp.setTopPoint(p.y);

		try {
			pasteTemp = (Shape) copyTemp.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		 // pasteTemp�� saveSpace �ǵڿ� ���Ѵ�.
		return pasteTemp;
	}
	// ���콺�� �巡�� �Ǵ� ���� �׷��� ������ �߿�
	// ���� �������� �׷��� �κ������� ���ܳ��´�.
	public void remainEndshape(Shape s) {
		if (saveSpace[CurrentVector].size() > 0) // �׸� �׸��� ������
		{
			saveSpace[CurrentVector].remove(saveSpace[CurrentVector].lastElement());
			saveSpace[CurrentVector].add(s);
		}
	}
	// saveSpace�� ��´�.
	public Vector getDrawing() {
		if (CurrentVector == 1)
			return saveSpace[CurrentVector];
		else
			for(int i = CurrentVector; i > 1; i --)
			{
				if(!saveSpace[i].isEmpty())
					return saveSpace[i];
			}
		return saveSpace[CurrentVector];
	}
	// saveSpace�� save�� �����Ѵ�.
	public void setDrawing(Vector save) {
		for (int j = 0; j < CurrentVector; j ++)
			for (int i = 0; i < saveSpace[j].size(); i++)
				saveSpace[CurrentVector].add(saveSpace[j].get(i));
		
		saveSpace[CurrentVector] = save;
	}
	
 
  
}