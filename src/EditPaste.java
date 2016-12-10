// OrderPaste.java
package src;
import java.awt.*;

import javax.swing.*;

public class EditPaste extends Order {	
	// 도형을 붙여넣는다.

    public void mouseClickExe(Point p, Drawing Layer)
    {
	  Layer.add( Layer.paste(p));  // 도형을 저장한다.
	  if(Layer.isEditing()==true){ // 현재 선택된 객체가 있다면
		((Shape) Layer.getDrawing().get(Layer.getEditIndex())).setSelect(false); // 선택을 해제시킨다.
		
	}
	}

}

