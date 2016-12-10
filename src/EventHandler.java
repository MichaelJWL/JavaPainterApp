package src;
import java.awt.event.*;
import javax.swing.*;

public class EventHandler implements ActionListener {

	public Order ordering; // �����ϰ� �� ���
  
	public void actionPerformed(ActionEvent event)
	{
		int i = Integer.parseInt(event.getActionCommand()); // �Է¹��� �׼� Ŀ�ǵ��� ���� ��Ƽ�� ���·� ��ȯ�Ѵ�.
		switch(i) // ����Ʈ���� �����ߴ� �Ͱ� ���������� 10������ ����� ��ü�� �׸��°�
		{		  // 20������ ����� ������ ���õ� ������ ���� ������Ʈ�� ���� ó���Ͽ����ϴ�.
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
			// 30~35 :: ��� 1 �̺�Ʈ �ڵ鸵
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
