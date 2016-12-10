package src;
import java.awt.*;

public class EditSelect extends Order {
	
		// 마우스로 얻은 마지막 점	
    private Shape s;			// 이동할 도형
 
  	public void mouseClickExe(Point p, Drawing Layer)
  	{
		// 마우스를 눌렀을 때 얻은 점을 포함하는 도형중에
		// 가장 앞에 있는 도형을 찾는다.
		s = Layer.getTopShape(p);
		if(Layer.isEditing()==true){ // 현재 편집중이면
			((Shape) Layer.getDrawing().get(Layer.getEditIndex())).setSelect(false); // 선택해제한다.
			
		}
			
    		if (s != null&&Layer.isEditing()==false){	 // 편집중이아니면	
	        s.setSelect(true);// 도형이 있으면
    		}// 마우스를 눌렀을 때 커서의 위치를 저장
  	}
  
	
}
