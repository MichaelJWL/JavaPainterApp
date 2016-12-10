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

	private Vector saveSpace[] = new Vector[100]; // Shape를 저장할 Vector공간 Layer를 표현하기 위해 배열로 표현
	private int CurrentVector = -1;
	private Color nowColor; // 현재 색깔
    private int EditingIndex; // 현재 선택한 객체의 Vector 인덱스z
	private Shape doTemp, copyTemp, pasteTemp; // 복사, 붙여넣기의 임시 Shape

	// 도형을 저장할 Vector를 생성하고 색깔과 선 스타일을 초기화 한다.
	public Drawing(Color init_color) {
		saveSpace[++CurrentVector] = new Vector();
		nowColor = init_color;
		ShapeRect BackGround = new ShapeRect(-1,-1,555,405,nowColor); //배경 생성
		
		BackGround.setColor(Color.white);	//배경의 색을 흰색으로
		BackGround.BackGroundFill = true;	//배경을 채움
 		saveSpace[CurrentVector].add(BackGround); //배경을 인덱스 0번에 저장
 		
 		saveSpace[++CurrentVector] = new Vector(); //다음 도형들은 1번 인덱스부터 사용
	}
	public void DrawImage(Image image) {	//이미지를 불러오기 위한 메소드
		for(int i = 0; i <= CurrentVector; i++)//레이어 초기화
			saveSpace[i] = null;
		CurrentVector = 0;
		saveSpace[CurrentVector] = new Vector();
		ShapeImage LoadImage = new ShapeImage(-1, -1, 555, 405, nowColor, image);
		saveSpace[CurrentVector++].add(LoadImage); //현재 불러온 이미지를 배경처럼 사용. 0번 인덱스에 저장
		saveSpace[CurrentVector] = new Vector();
	}
	public void AddSpace() {	//레이어 추가를 위한 메소드
		saveSpace[++CurrentVector] = new Vector();
	}
	public void DeleteSpace(){		//레이어 삭제를 위한 메소드
		saveSpace[CurrentVector--] = new Vector();
		if(CurrentVector == 0)		//배경이 지위지지 않도록 0이 될 경우 1을 더해줌
		{
			saveSpace[++CurrentVector] = new Vector();
		}
	}
	//////////----------------------ADDED BY JW--------------------------------//////////
	public ShapeRect getBackgroundLayer(){
		return (ShapeRect) saveSpace[0].get(0);
	}
	//////////--------------------------END------------------------------------//////////
	// 색깔을 설정한다.
	public void setColor(Color color) {
		nowColor = color;
	}
	// 현재 색깔을 얻는다
	public Color getColor() {
		return nowColor;
	}
	// 입력받은 인덱스의 Shape를 얻어온다.
	public Shape getShape(int i)
	{
		return (Shape) saveSpace[CurrentVector].get(i);
	}
	// 선택된 객체를 삭제한다.
    public void delete()
    {
    	saveSpace[CurrentVector].remove(getEditIndex());
    }
	// 도형을 백터에 저장한다.
	public void add(Shape s) {
		saveSpace[CurrentVector].add(s);
	}
	// 백터공간에 저장된 도형을 처음부터 가져와서 그린다.
	public void draw(Graphics g) {
		for (int j = 0; j <= CurrentVector; j ++)
			for (int i = 0; i < saveSpace[j].size(); i++)
			{
				((Shape) saveSpace[j].get(i)).draw(g);
			}
	}
	public void drawsave(BufferedImage im) {		//그려진 이미지를 저장하기 위한 메소드
		Graphics g = im.createGraphics();		//BufferedImage를 설정.
		for (int j = 0; j <= CurrentVector; j ++)
			for (int i = 0; i < saveSpace[j].size(); i++)
			{
				((Shape) saveSpace[j].get(i)).draw(g);		//현재 벡터공간에 그려진 도형을 처음부터 다시 그림.
			}
	}
	// 선택된 객체의 인덱스를 얻어온다.
	public int getEditIndex()
	{
		for (int i =0; i <= saveSpace[CurrentVector].size() - 1; i++)
			if (((Shape) saveSpace[CurrentVector].get(i)).getSelect()==true) 
			 return i;
			return 0;
	}
	// 현재 작업중인 인덱스를 얻어온다.
	public int getNowIndex(Shape s)
	{
		for (int i =0; i <= saveSpace[CurrentVector].size() - 1; i++)
		    if(i==saveSpace[CurrentVector].indexOf((Shape) s))
			 return i;
			return 0;
	}
	// 객체의 선택 유무를 판단한다.
    public boolean isEditing()
    {
    	if(saveSpace[CurrentVector].isEmpty()==true||((Shape) saveSpace[CurrentVector].get(getEditIndex())).getSelect()==false)
    	   return false;
    	return true;
    }
	// 백터를 가장 최근의 인덱스부터 검색하여 현재 마우스 커서가 위치한 점에 있는 도형을 리턴한다.
	public Shape getTopShape(Point p) {
		for (int i = saveSpace[CurrentVector].size() - 1; i >= 0; i--)
			// for문을 거꾸로 돌린다.
			if (((Shape) saveSpace[CurrentVector].get(i)).containsPoint(p)) 
			return (Shape) saveSpace[CurrentVector].get(i); // 벡터안에 저장되어 있는 도형을 리턴한다.
	
		return null;
	}
	// 객체의 선택해제
   public void deselect(){
   	doTemp = (Shape) saveSpace[CurrentVector].lastElement(); // 도형 s가 저장되어 있는 위치를 얻는다.
	doTemp.setSelect(false);
   	}
	// Vector 클래스의 indexOf와 remove를 이용하여 도형 s를 지운다.
	public void remove(Shape s) {
		int index = saveSpace[CurrentVector].indexOf(s); // 도형 s가 저장되어 있는 위치를 얻는다.
		if (index >= 0)
			saveSpace[CurrentVector].remove(index); // saveSpace배열에서 지운다.
	}
	// saveSpace 배열을 모두 비운다.
	public void newShape() {
		for(int i = 1; i <= CurrentVector; i++)
			saveSpace[i].removeAllElements();
		
		ShapeRect BackGround = new ShapeRect(-1,-1,555,405,nowColor); //배경 생성
		
		BackGround.setColor(Color.white);
		BackGround.BackGroundFill = true;
 		saveSpace[0].removeAllElements();
 		saveSpace[0].add(BackGround);
		
		CurrentVector = 1;
		copyTemp = null;
		pasteTemp = null;
	}
	// Vector 공간의 뒤에서부터 객체를 하나씩 지운다.
    public void undo(){
        saveSpace[CurrentVector].remove(saveSpace[CurrentVector].indexOf(saveSpace[CurrentVector].lastElement()));
    }
	// Shape s를 Clone()해서 copyTemp에 저장한다.
	public void copy(Shape s) {
		try {
			copyTemp = (Shape) s.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	// Shape s를 Clone()해서 copyTemp에 저장한 후 그 도형 s를 삭제한다
	public void cut(Shape s) {
		try {
			copyTemp = (Shape) s.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		int index = saveSpace[CurrentVector].indexOf(s); // 도형 s가 저장되어 있는 위치를 얻는다.
		if (index >= 0)
			saveSpace[CurrentVector].remove(index); // saveSpace배열에서 지운다.
	}
	// 선택된 객체를 맨 뒤로 보낸다,
	public void moveToBack(Shape s)
  	{
    		int index = saveSpace[CurrentVector].indexOf(s);    // 도형 s가 저장되어 있는 위치를 알아내고
    		if (index >= 0)
    		{
      		saveSpace[CurrentVector].remove(index);      	 // 도형 s를 지운후에
      			if(CurrentVector == 0)
      				saveSpace[CurrentVector].add(1, s);
      			else
      				saveSpace[CurrentVector].add(0, s);

    		}    		
  	}
	// copyTemp에 저장된 Shape를 Clone()하여 pasteTemp저장한후 saveSpace의 맨뒤에 더한다.
	public Shape paste(Point p) {
		copyTemp.setLeftPoint(p.x); // copyTemp의 (x, y) 좌표를 마우스 클릭 위치로 설정
		copyTemp.setTopPoint(p.y);

		try {
			pasteTemp = (Shape) copyTemp.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		 // pasteTemp를 saveSpace 맨뒤에 더한다.
		return pasteTemp;
	}
	// 마우스가 드래그 되는 동안 그려진 도형들 중에
	// 가장 마지막에 그려진 부분을만을 남겨놓는다.
	public void remainEndshape(Shape s) {
		if (saveSpace[CurrentVector].size() > 0) // 그린 그림이 있으면
		{
			saveSpace[CurrentVector].remove(saveSpace[CurrentVector].lastElement());
			saveSpace[CurrentVector].add(s);
		}
	}
	// saveSpace를 얻는다.
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
	// saveSpace를 save로 설정한다.
	public void setDrawing(Vector save) {
		for (int j = 0; j < CurrentVector; j ++)
			for (int i = 0; i < saveSpace[j].size(); i++)
				saveSpace[CurrentVector].add(saveSpace[j].get(i));
		
		saveSpace[CurrentVector] = save;
	}
	
 
  
}