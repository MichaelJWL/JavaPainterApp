package src;
import java.awt.event.*;
import javax.swing.*;

public class EventHandler implements ActionListener {

	public Order ordering; // 수행하게 될 명령
  
	public void actionPerformed(ActionEvent event)
	{
		int i = Integer.parseInt(event.getActionCommand()); // 입력받은 액션 커맨드의 값을 인티져 형태로 변환한다.
		switch(i) // 심플트론을 구현했던 것과 마찬가지로 10번대의 명령은 객체를 그리는것
		{		  // 20번대의 명령은 편집에 관련된 것으로 추후 업데이트가 쉽게 처리하였습니다.
		case 11 :
			ordering = new DrawLine();
			break;
		case 12 :
			ordering = new DrawOval();
			break;
		case 13 :
			ordering = new DrawPolyline();
			break;
		case 14 :
			ordering = new DrawRect();
			break;
		case 15 :
			ordering = new DrawText();
			break;
		case 21 :
			ordering = new EditFill();
			break;
		case 22 :
			ordering = new EditResize();
			break;
		case 23 :
			ordering = new EditMove();
			break;
		case 24 :
			ordering = new EditPaste();
			break;
		case 25 :
			ordering = new EditSelect();
			break;
			// 30~35 :: 기능 1 이벤트 핸들링
		case 30 :
			ordering = new DrawPoint();
			break;

		case 31 :
			ordering = new DrawMagic();
			break;

		case 32 :
			ordering = new DrawTriangle();
			break;

		case 33 :
			ordering = new DrawSayung();
			break;

		case 34 :
			ordering = new DrawLinepoint();
			break;

		case 35 :
			ordering = new DrawErase();
			break;
		}  
		
	}		
}
