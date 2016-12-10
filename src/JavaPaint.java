package src;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.MouseInputListener;	//	MouseListener + MouseMotionListener
import javax.swing.filechooser.FileNameExtensionFilter;

import src.window.TransparentChooser;



public class JavaPaint extends JFrame implements ActionListener {
	
	
	
	private BufferedImage image = new BufferedImage(535, 405, BufferedImage.TYPE_INT_RGB);
	private EventHandler eventhandle; // 몇가지 주요이벤트들을 관리할 이벤트핸들러의 객체
	private final Color firstColor = Color.BLACK; //기본색을 검은 색으로 설정
	private static Order ordering;         					// 실행될 명령
	private Drawing Layer; 					// 명령에 따라 그림을 그릴 객체
	private int CurrentLayer = 0;
	private Pointlocation pointlocation; //현재 커서의 위치를 알려주는 패널.
	private JPanel nowColorPanel;   //현재색을 보여주는 패널객체.
	private JPanel ToolBar_Pallet_text;   //팔레트를 바꿔주는 버튼역할을 할 패널.
	private CardLayout pallets;       //카드레이아웃의 객체.(.next()메소드를 쓰기위해) 			
	private JPanel ToolBar_Pallet_pallet; //3개의 팔레트패널을 관리하는 전체 팔레트관리패널.
	private JLabel helptext; //상태 도움 표시줄의 라벨.
	private JFrame gradientFrame; //그라디언트 프레임
	private static JavaPaint application; 
	
	//툴바버튼
	
	private JButton selectButton, lineButton, rectButton, ovalButton, polyButton, resizeButton, moveButton, deleteButton, 
					cutButton, pasteButton, copyButton, fillcolorButton, undoButton, backButton ,gradientButton,
	/*added button*/	addLayerButton,deleteLayerButton,penToolButton,textToolButton,fillBackgroundColorButton;
			
	//팔레트버튼.		
	private JButton whiteButton, blackButton, gray1Button, gray2Button, red1Button, red2Button, 
				blue1Button, blue2Button, sb1Button, sb2Button, green1Button,green2Button, 
				yellow1Button, yellow2Button, purple1Button, purple2Button,
				peach1Button, peach2Button, orangeButton, brownButton, credButton, cgreenButton,
				cblueButton, cpurpleButton; 
	
	//메뉴
 	private JMenu fileMenu, Edit, Help, AddLayer, Func1;
 	
//////////----------------------ADDED BY JW--------------------------------//////////
private JMenu JWMenu;
private JMenuItem JW_fillBg,JW_tranparent;
/**
 * 투명도 조절창 construct
 */
private TransparentChooser transparentchooser = new TransparentChooser(this);

//////////--------------------------END------------------------------------//////////
 	//메뉴아이템.
 	private JMenuItem newItem, selectItem, openItem, saveItem, saveAsItem, exitItem, 
					moveItem, resizeItem, cutItem, copyItem, pasteItem, deleteItem, colorchooseItem, helpItem, About, 
					LayerAdd, LayerDelete, TextBox, freedrawItem, magicdrawItem,triangledrawItem, sayungdrawItem, linepointdrawItem, erasedrawItem;
 	
	//DrawPanel. 직접 그림이 그려지는 캔버스역할을 하는 패널.				
	public CustomPanel DrawPanel;
	
	public Drawing SaveLayer;
	
	//파일입출력 관련.
	private JFileChooser fileChoose = new JFileChooser();
	private String fileName = "제목없음.cha";
	private File saveFile;
	
	//메인함수.
	
	public static void main(String args[])
	{
		application = new JavaPaint();
		application.setVisible(true);
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                       
	}
	
	// JavaPaint 생성자. 패널들과 버튼을 생성하고 배치
 
	public JavaPaint()
	{
		super();
		eventhandle = new EventHandler();	
		setTitle(fileName);		// JFrame 초기화
		
		
		//윈도우 스타일로 룩앤필 설정.-----------
		
		try { 
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); 
			SwingUtilities.updateComponentTreeUI(this); 
		} 
		catch (Exception e) { }
		
		//---------------------------------------
		
		
 		ordering = new Order();          		// 명령을 참조할 객체 생성
 		Layer = new Drawing(firstColor);    // 현재 그림을 참조할 객체 생성
 		
 		Color ColorDesign = new Color(232,232,233); //전체프레임의 색을 RGB로 정의
 		
 		
 		//////////----------------------ADDED BY JW--------------------------------//////////
 		transparentchooser.addComponentListener(new ComponentAdapter() {
 			public void componentHidden(ComponentEvent e) 
 			{
 				/* code run when component hidden*/
 				repaint();
 			}
 			public void componentShown(ComponentEvent e) {
 				/* code run when component shown */
 				transparentchooser.setInit();
 			}
 		});
 		
		JWMenu = new JMenu("색상도구");
		JWMenu.setBackground(ColorDesign);
		
		JW_fillBg = new JMenuItem("전경/배경색상선택");
		JW_fillBg.setBackground(ColorDesign);
		JW_fillBg.addActionListener(this);
		JWMenu.add(JW_fillBg);
		
		JW_tranparent = new JMenuItem("투명도");
		JW_tranparent.setBackground(ColorDesign);
		JW_tranparent.addActionListener(this);
		JWMenu.add(JW_tranparent);
		//////////--------------------------END------------------------------------//////////
 		
 		//파일 메뉴
		fileMenu = new JMenu("파일(F)");
		fileMenu.setMnemonic('f');
		fileMenu.setBackground(ColorDesign);
				
		newItem = new JMenuItem("새로 만들기(N)", new ImageIcon("New.jpg"));
		newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		newItem.setBackground(ColorDesign);
		newItem.addActionListener(this);
		fileMenu.add(newItem);
		
		
		openItem = new JMenuItem("열기(O)", new ImageIcon("Open.jpg"));
		openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		openItem.setBackground(ColorDesign);
		openItem.addActionListener(this);
		fileMenu.add(openItem);
		
		
		saveItem = new JMenuItem("저장(S)", new ImageIcon("Save.jpg"));
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		saveItem.setBackground(ColorDesign);
		saveItem.addActionListener(this);
		fileMenu.add(saveItem);
		
		
		saveAsItem = new JMenuItem("다른 이름으로 저장");
		saveAsItem.setBackground(ColorDesign);
		saveAsItem.addActionListener(this);
		fileMenu.add(saveAsItem);
		
		
		exitItem = new JMenuItem("종료");
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		exitItem.setBackground(ColorDesign);
		exitItem.addActionListener(this);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);	
		
		
		
		// 편집 메뉴(선택, 이동, 리사이즈, 복사, 잘라내기, 붙여넣기, 삭제하기, 색상선택)	
		Edit = new JMenu("편집(E)");
		Edit.setBackground(ColorDesign);
		Edit.setMnemonic('e');
		
		selectItem = new JMenuItem("선택하기");
		selectItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
		selectItem.setBackground(ColorDesign);
		selectItem.addActionListener(eventhandle);
		selectItem.setActionCommand("26");
		Edit.add(selectItem);
		
		moveItem = new JMenuItem("이동하기");
		moveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
		moveItem.setBackground(ColorDesign);
		moveItem.addActionListener(eventhandle);
		moveItem.setActionCommand("26");
		Edit.add(moveItem);
		
		resizeItem = new JMenuItem("크기 변경");
		resizeItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		resizeItem.setBackground(ColorDesign);
		resizeItem.addActionListener(eventhandle);
		resizeItem.setActionCommand("24");
		Edit.add(resizeItem);
		
		copyItem = new JMenuItem("복사하기");
		copyItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		copyItem.setBackground(ColorDesign);
		copyItem.addActionListener(eventhandle);
		copyItem.setActionCommand("23");
		Edit.add(copyItem);
		
		cutItem = new JMenuItem("잘라내기");
		cutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
		cutItem.setBackground(ColorDesign);
		cutItem.addActionListener(eventhandle);
		cutItem.setActionCommand("22");
		Edit.add(cutItem);
				
			
		pasteItem = new JMenuItem("붙여넣기");
		pasteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		pasteItem.setBackground(ColorDesign);
		pasteItem.addActionListener(eventhandle);
		pasteItem.setActionCommand("27");
		Edit.add(pasteItem);
		
		deleteItem = new JMenuItem("삭제하기");
		deleteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
		deleteItem.setBackground(ColorDesign);
		deleteItem.addActionListener(eventhandle);
		deleteItem.setActionCommand("25");
		Edit.add(deleteItem);
		
		
		
		Edit.addSeparator();
		colorchooseItem = new JMenuItem("색상 선택");
		colorchooseItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
		colorchooseItem.setBackground(new Color(204,204,255));
		Edit.add(colorchooseItem);
		colorchooseItem.addActionListener(this);
		
			
		// 도움말 메뉴	
		Help = new JMenu("도움말(H)");
		Help.setBackground(ColorDesign);
		Help.setMnemonic('h');
		
//		helpItem = new JMenuItem("도움말");
//		helpItem.setBackground(ColorDesign);
//		helpItem.addActionListener(this);
//		Help.add(helpItem);
		
		
		About = new JMenuItem("About..");
		About.setBackground(ColorDesign);
		About.addActionListener(this);
		Help.add(About);
		
		// 추가기능 메뉴   
		Func1 = new JMenu("드로잉");
		Func1.setBackground(ColorDesign);
		//Help.setMnemonic('h');

		freedrawItem = new JMenuItem("자유 드로잉");
		freedrawItem.addActionListener(this);
		freedrawItem.setBackground(ColorDesign);
		freedrawItem.addActionListener(eventhandle);
		freedrawItem.setActionCommand("30");
		Func1.add(freedrawItem);


		magicdrawItem = new JMenuItem("매직 드로잉");
		magicdrawItem.addActionListener(this);
		magicdrawItem.setBackground(ColorDesign);
		magicdrawItem.addActionListener(eventhandle);
		magicdrawItem.setActionCommand("31");
		Func1.add(magicdrawItem);


		triangledrawItem = new JMenuItem("삼각형 드로잉");
		triangledrawItem.addActionListener(this);
		triangledrawItem.setBackground(ColorDesign);
		triangledrawItem.addActionListener(eventhandle);
		triangledrawItem.setActionCommand("32");
		Func1.add(triangledrawItem);

		sayungdrawItem = new JMenuItem("사영 드로잉");
		sayungdrawItem.addActionListener(this);
		sayungdrawItem.setBackground(ColorDesign);
		sayungdrawItem.addActionListener(eventhandle);
		sayungdrawItem.setActionCommand("33");
		Func1.add(sayungdrawItem);

		linepointdrawItem = new JMenuItem("직선점 드로잉");
		linepointdrawItem.addActionListener(this);
		linepointdrawItem.setBackground(ColorDesign);
		linepointdrawItem.addActionListener(eventhandle);
		linepointdrawItem.setActionCommand("34");
		Func1.add(linepointdrawItem);

		erasedrawItem = new JMenuItem("지우개");
		erasedrawItem.addActionListener(this);
		erasedrawItem.setBackground(ColorDesign);
		erasedrawItem.addActionListener(eventhandle);
		erasedrawItem.setActionCommand("35");
		Func1.add(erasedrawItem);
		///////////관우 end //////////////
		
		AddLayer = new JMenu("레이어");
		AddLayer.setBackground(ColorDesign);
		
		LayerAdd = new JMenuItem("레이어 추가");
		LayerAdd.setBackground(ColorDesign);
		LayerAdd.addActionListener(this);
		AddLayer.add(LayerAdd);
		
		LayerDelete = new JMenuItem("레이어 삭제");
		LayerDelete.setBackground(ColorDesign);
		LayerDelete.addActionListener(this);
		AddLayer.add(LayerDelete);
		
		TextBox = new JMenuItem("텍스트 박스");
		TextBox.setBackground(ColorDesign);
		TextBox.addActionListener(eventhandle);
		TextBox.setActionCommand("15");
		TextBox.addMouseMotionListener
     	(
            new MouseMotionAdapter()
            {
                public void mouseMoved(MouseEvent event)
                {
                    if ( ( event.getX() > 0 && event.getX() < 30 ) && ( event.getY() > 0 && event.getY() < 25 ) )
                    {
                        helptext.setText("원하는 위치에 텍스트를 그립니다.");
                    }
                }
                
                
            }
    	);
		AddLayer.add(TextBox);
		
		//메뉴바 설정.
		JMenuBar menuBar = new JMenuBar();		
		menuBar.setBackground(ColorDesign);
		menuBar.add(fileMenu);
		menuBar.add(Edit);
		
		menuBar.add(AddLayer);
		//관우//
		menuBar.add(Func1);
		//////////----------------------ADDED BY JW--------------------------------//////////
		menuBar.add(JWMenu);
		//////////--------------------------END------------------------------------//////////	
		menuBar.add(Help);
		setJMenuBar(menuBar);
						
		//Toolbar
		//툴바의 툴GUI
				
				
		Dimension IconSize = new Dimension(30,25);
		
		JPanel ToolBar = new JPanel();
		ToolBar.setBackground(ColorDesign);
		ToolBar.setBorder(BorderFactory.createLoweredBevelBorder());
		JPanel ToolBar_Tool = new JPanel();
		ToolBar.setBackground(ColorDesign);
		ToolBar_Tool.setLayout(new BorderLayout());
		JPanel ToolBar_Tool_text = new JPanel();
		JPanel ToolBar_Tool_tool = new JPanel();
		ToolBar_Tool_tool.setBackground(ColorDesign);
		
		ToolBar_Tool_text.setBorder(BorderFactory.createRaisedBevelBorder());
		JLabel Tool_text = new JLabel("도구 모음");
     	ToolBar_Tool_text.add(Tool_text);
     	ToolBar_Tool_text.setBackground(ColorDesign);
     	ToolBar_Tool.add(ToolBar_Tool_text, BorderLayout.NORTH);
			
		ToolBar_Tool_tool.setBorder(BorderFactory.createRaisedBevelBorder());
		ToolBar_Tool_tool.setLayout(new GridLayout(7,2));
     	ToolBar_Tool_tool.setCursor(new Cursor(Cursor.HAND_CURSOR));
     
                    		
		//버튼을 생성하고 설정하는 부분.마우스모션리스너와 마우스모션어댑터로 상태도움표시줄을 관리.
		
		lineButton = new JButton(new ImageIcon("Line.jpg"));
		lineButton.setBackground(ColorDesign);
		lineButton.setToolTipText("선그리기");
		lineButton.setPreferredSize(IconSize);
		lineButton.addActionListener(eventhandle);
		lineButton.setActionCommand("11");
		lineButton.addMouseMotionListener
     	(
            new MouseMotionAdapter()
            {
                public void mouseMoved(MouseEvent event)
                {
                    if ( ( event.getX() > 0 && event.getX() < 30 ) && ( event.getY() > 0 && event.getY() < 25 ) )
                    {
                        helptext.setText("캔버스창에 드래그하여 선을 그립니다.");
                    }
                }
                
                
            }
    	);
		ToolBar_Tool_tool.add(lineButton);  
		
		lineButton = new JButton(new ImageIcon("polyline.jpg"));
		lineButton.setBackground(ColorDesign);
		lineButton.setToolTipText("폴리라인그리기");
		lineButton.setPreferredSize(IconSize);
		lineButton.addActionListener(eventhandle);
		lineButton.setActionCommand("13");
		lineButton.addMouseMotionListener
     	(
            new MouseMotionAdapter()
            {
                public void mouseMoved(MouseEvent event)
                {
                    if ( ( event.getX() > 0 && event.getX() < 30 ) && ( event.getY() > 0 && event.getY() < 25 ) )
                    {
                        helptext.setText("마우스를 이동하며 연속해서 클릭하면 폴리라인을 그립니다.");
                    }
                }
                
                
            }
    	);
		ToolBar_Tool_tool.add(lineButton); 
		
		
        ovalButton = new JButton(new ImageIcon("Oval.jpg"));
     	ovalButton.setBackground(ColorDesign);
     	ovalButton.setToolTipText("원그리기");
     	ovalButton.setPreferredSize(IconSize);
     	ovalButton.addActionListener(eventhandle);
     	ovalButton.setActionCommand("12");
     	ovalButton.addMouseMotionListener
     	(
            new MouseMotionAdapter()
            {
                public void mouseMoved(MouseEvent event)
                {
                    if ( ( event.getX() > 0 && event.getX() < 30 ) && ( event.getY() > 0 && event.getY() < 25 ) )
                    {
                        helptext.setText("캔버스창에 드래그하여 타원을 그립니다. (+ shift키 - 원그리기)");
                    }
                }
                
                
            }
    	);
     	ToolBar_Tool_tool.add(ovalButton); 
     
     	rectButton = new JButton(new ImageIcon("Rect.jpg"));
     	rectButton.setBackground(ColorDesign);
     	rectButton.setToolTipText("사각형그리기");
     	rectButton.setPreferredSize(IconSize);
     	rectButton.addActionListener(eventhandle);
     	rectButton.setActionCommand("14");
     	rectButton.addMouseMotionListener
     	(
            new MouseMotionAdapter()
            {
                public void mouseMoved(MouseEvent event)
                {
                    if ( ( event.getX() > 0 && event.getX() < 30 ) && ( event.getY() > 0 && event.getY() < 25 ) )
                    {
                        helptext.setText("캔버스창에 드래그하여 네모를 그립니다. (+ shift키 - 정사각형그리기)");
                    }
                }
                
                
            }
    	);
     	ToolBar_Tool_tool.add(rectButton);
     	
     	selectButton = new JButton(new ImageIcon("Select.jpg"));
		selectButton.setBackground(ColorDesign);
		selectButton.setToolTipText("객체 선택");
		selectButton.setPreferredSize(IconSize);
		selectButton.addActionListener(eventhandle);
		selectButton.setActionCommand("25");
		selectButton.addMouseMotionListener
     	(
            new MouseMotionAdapter()
            {
                public void mouseMoved(MouseEvent event)
                {
                    if ( ( event.getX() > 0 && event.getX() < 30 ) && ( event.getY() > 0 && event.getY() < 25 ) )
                    {
                        helptext.setText("편집을 원하는 도형 위에 클릭을 하여 편집 대상 도형을 선택합니다.(Alt + S)");
                    }
                }
                
                
            }
    	);
    	ToolBar_Tool_tool.add(selectButton);
	 
     	moveButton = new JButton(new ImageIcon("Move.jpg"));
     	moveButton.setBackground(ColorDesign);
     	moveButton.setToolTipText("이동");
     	moveButton.setPreferredSize(IconSize);
     	moveButton.addActionListener(eventhandle);
     	moveButton.setActionCommand("23");
     	moveButton.addMouseMotionListener
     	(
            new MouseMotionAdapter()
            {
                public void mouseMoved(MouseEvent event)
                {
                    if ( ( event.getX() > 0 && event.getX() < 30 ) && ( event.getY() > 0 && event.getY() < 25 ) )
                    {
                        helptext.setText("선택된 도형을 이동합니다.(Ctrl + M)");
                    }
                }
            }
    	);
     	ToolBar_Tool_tool.add(moveButton); 
     
     	resizeButton = new JButton(new ImageIcon("Resize.jpg"));
     	resizeButton.setBackground(ColorDesign);
     	resizeButton.setToolTipText("크기 변경");
     	resizeButton.setPreferredSize(IconSize);
     	resizeButton.addActionListener(eventhandle);
     	resizeButton.setActionCommand("22");
     	resizeButton.addMouseMotionListener
     	(
            new MouseMotionAdapter()
            {
                public void mouseMoved(MouseEvent event)
                {
                    if ( ( event.getX() > 0 && event.getX() < 30 ) && ( event.getY() > 0 && event.getY() < 25 ) )
                    {
                        helptext.setText("선택된 도형의 크기를 변경합니다.(Ctrl + R)");
                    }
                }
            }
    	);
     	ToolBar_Tool_tool.add(resizeButton);
     
     	cutButton = new JButton(new ImageIcon("Cut.jpg"));
     	cutButton.setBackground(ColorDesign);
     	cutButton.setToolTipText("잘라내기");
    	cutButton.setPreferredSize(IconSize);
    	cutButton.addActionListener(this);
    	cutButton.addMouseMotionListener
     	(
            new MouseMotionAdapter()
            {
                public void mouseMoved(MouseEvent event)
                {
                    if ( ( event.getX() > 0 && event.getX() < 30 ) && ( event.getY() > 0 && event.getY() < 25 ) )
                    {
                        helptext.setText("선택된 도형을 지우고, 직후에 붙여넣기하면 그 도형을 붙입니다.(Alt + C)");
                    }
                }
            }
    	);
     	ToolBar_Tool_tool.add(cutButton); 
     
     	copyButton = new JButton(new ImageIcon("Copy.jpg"));
     	copyButton.setBackground(ColorDesign);
     	copyButton.setToolTipText("복사하기");
     	copyButton.setPreferredSize(IconSize);
     	copyButton.addActionListener(this);
     	copyButton.addMouseMotionListener
     	(
            new MouseMotionAdapter()
            {
                public void mouseMoved(MouseEvent event)
                {
                    if ( ( event.getX() > 0 && event.getX() < 30 ) && ( event.getY() > 0 && event.getY() < 25 ) )
                    {
                        helptext.setText("선택된 도형을 붙여넣기할 때 붙입니다.(Ctrl + C)");
                    }
                }
            }
    	);
     	ToolBar_Tool_tool.add(copyButton);
     
	    pasteButton = new JButton(new ImageIcon("Paste.jpg"));
	    pasteButton.setBackground(ColorDesign);
  		pasteButton.setToolTipText("붙여넣기");
     	pasteButton.setPreferredSize(IconSize);
     	pasteButton.addActionListener(eventhandle);
     	pasteButton.setActionCommand("24");
     	pasteButton.addMouseMotionListener
     	(
            new MouseMotionAdapter()
            {
                public void mouseMoved(MouseEvent event)
                {
                    if ( ( event.getX() > 0 && event.getX() < 30 ) && ( event.getY() > 0 && event.getY() < 25 ) )
                    {
                        helptext.setText("직전에 잘라내거나 복사한 도형을 캔버스창에 붙여넣습니다.(Ctrl + V)");
                    }
                }
            }
    	);
     	ToolBar_Tool_tool.add(pasteButton);
     
     	deleteButton = new JButton(new ImageIcon("Delete.jpg"));
     	deleteButton.setBackground(ColorDesign);
     	deleteButton.setToolTipText("삭제");
     	deleteButton.setPreferredSize(IconSize);
     	deleteButton.addActionListener(this);
     	deleteButton.addMouseMotionListener
     	(
            new MouseMotionAdapter()
            {
                public void mouseMoved(MouseEvent event)
                {
                    if ( ( event.getX() > 0 && event.getX() < 30 ) && ( event.getY() > 0 && event.getY() < 25 ) )
                    {
                        helptext.setText("선택된 도형을 삭제합니다.(Ctrl + D)");
                    }
                }
            }
    	);
     	ToolBar_Tool_tool.add(deleteButton);
     	
     	undoButton = new JButton(new ImageIcon("Undo.jpg"));
     	undoButton.setBackground(ColorDesign);
     	undoButton.setToolTipText("전 작업으로");
     	undoButton.setPreferredSize(IconSize);
        undoButton.addActionListener(this);
        undoButton.addMouseMotionListener
     	(
            new MouseMotionAdapter()
            {
                public void mouseMoved(MouseEvent event)
                {
                    if ( ( event.getX() > 0 && event.getX() < 30 ) && ( event.getY() > 0 && event.getY() < 25 ) )
                    {
                        helptext.setText("전에 했던 작업으로 차례차례 되돌립니다.");
                    }
                }
            }
    	);
     	ToolBar_Tool_tool.add(undoButton);
     
     	fillcolorButton = new JButton(new ImageIcon("fillColor.png"));
     	fillcolorButton.setBackground(ColorDesign);
     	fillcolorButton.setToolTipText("색채우기");
     	fillcolorButton.setPreferredSize(IconSize);
     	fillcolorButton.addActionListener(eventhandle);
     	fillcolorButton.setActionCommand("21");
     	fillcolorButton.addMouseMotionListener
     	(
            new MouseMotionAdapter()
            {
                public void mouseMoved(MouseEvent event)
                {
                    if ( ( event.getX() > 0 && event.getX() < 30 ) && ( event.getY() > 0 && event.getY() < 25 ) )
                    {
                        helptext.setText("현재 색을 선택된 도형에 채워넣습니다.");
                    }
                }
            }
    	);
     	ToolBar_Tool_tool.add(fillcolorButton);
     	
     	
     	
     	backButton = new JButton(new ImageIcon("back.jpg"));
     	backButton.setBackground(ColorDesign);
     	backButton.setToolTipText("뒤로 보내기");
     	backButton.setPreferredSize(IconSize);
     	backButton.addActionListener(this);
     	backButton.addMouseMotionListener
     	(
            new MouseMotionAdapter()
            {
                public void mouseMoved(MouseEvent event)
                {
                    if ( ( event.getX() > 0 && event.getX() < 30 ) && ( event.getY() > 0 && event.getY() < 25 ) )
                    {
                        helptext.setText("다른 도형에 위에 그려져 있는 도형을 뒤로 보냅니다.");
                    }
                }
            }
    	);
     	ToolBar_Tool_tool.add(backButton);
     	
     	////////////////addLayerButton
     	addLayerButton = new JButton(new ImageIcon("addLayer.png"));
     	addLayerButton.setBackground(ColorDesign);
     	addLayerButton.setToolTipText("레이어 추가");
     	addLayerButton.setPreferredSize(IconSize);
     	addLayerButton.addActionListener(this);
     	addLayerButton.addMouseMotionListener
     	(
            new MouseMotionAdapter()
            {
                public void mouseMoved(MouseEvent event)
                {
                    if ( ( event.getX() > 0 && event.getX() < 30 ) && ( event.getY() > 0 && event.getY() < 25 ) )
                    {
                        helptext.setText("레이어를 추가합니다.");
                    }
                }
            }
    	);
     	ToolBar_Tool_tool.add(addLayerButton);
     	/////////////////deleteLayerButton
     	deleteLayerButton = new JButton(new ImageIcon("delLayer.png"));
     	deleteLayerButton.setBackground(ColorDesign);
     	deleteLayerButton.setToolTipText("레이어 삭제");
     	deleteLayerButton.setPreferredSize(IconSize);
     	deleteLayerButton.addActionListener(this);
     	deleteLayerButton.addMouseMotionListener
     	(
            new MouseMotionAdapter()
            {
                public void mouseMoved(MouseEvent event)
                {
                    if ( ( event.getX() > 0 && event.getX() < 30 ) && ( event.getY() > 0 && event.getY() < 25 ) )
                    {
                        helptext.setText("레이어를 삭제합니다.");
                    }
                }
            }
    	);
     	ToolBar_Tool_tool.add(deleteLayerButton);
     	////////////////penToolButton
     	penToolButton = new JButton(new ImageIcon("penTool.png"));
     	penToolButton.setBackground(ColorDesign);
     	penToolButton.setToolTipText("펜 도구");
     	penToolButton.setPreferredSize(IconSize);
		penToolButton.addActionListener(eventhandle);
		penToolButton.setActionCommand("30");
		
     	penToolButton.addMouseMotionListener
     	(
            new MouseMotionAdapter()
            {
                public void mouseMoved(MouseEvent event)
                {
                    if ( ( event.getX() > 0 && event.getX() < 30 ) && ( event.getY() > 0 && event.getY() < 25 ) )
                    {
                        helptext.setText("펜으로 그릴 수 있습니다.");
                    }
                }
            }
    	);
     	ToolBar_Tool_tool.add(penToolButton);
     	////////////////textToolButton
     	textToolButton = new JButton(new ImageIcon("textTool.png"));
     	textToolButton.setBackground(ColorDesign);
     	textToolButton.setToolTipText("텍스트 박스");
     	textToolButton.setPreferredSize(IconSize);
     	textToolButton.addActionListener(eventhandle);
		textToolButton.setActionCommand("15");
     	textToolButton.addMouseMotionListener
     	(
            new MouseMotionAdapter()
            {
                public void mouseMoved(MouseEvent event)
                {
                    if ( ( event.getX() > 0 && event.getX() < 30 ) && ( event.getY() > 0 && event.getY() < 25 ) )
                    {
                        helptext.setText("텍스트박스를 만들어 글을 쓸 수 있습니다.");
                    }
                }
            }
    	);
     	ToolBar_Tool_tool.add(textToolButton);
     	///////////////fillBackgroundColorButton
     	fillBackgroundColorButton = new JButton(new ImageIcon("fillBackgroundColor.png"));
     	fillBackgroundColorButton.setBackground(ColorDesign);
     	fillBackgroundColorButton.setToolTipText("배경색 채우기");
     	fillBackgroundColorButton.setPreferredSize(IconSize);
     	fillBackgroundColorButton.addActionListener(this);
     	fillBackgroundColorButton.addMouseMotionListener
     	(
            new MouseMotionAdapter()
            {
                public void mouseMoved(MouseEvent event)
                {
                    if ( ( event.getX() > 0 && event.getX() < 30 ) && ( event.getY() > 0 && event.getY() < 25 ) )
                    {
                        helptext.setText("배경색을 채울 수 있습니다.");
                    }
                }
            }
    	);
     	ToolBar_Tool_tool.add(fillBackgroundColorButton);
     	//////////////
     	JPanel ToolBar_Tool_gradient = new JPanel();
     	ToolBar_Tool_gradient.setBackground(ColorDesign);
		ToolBar_Tool_gradient.setBorder(BorderFactory.createRaisedBevelBorder());
		gradientButton = new JButton(new ImageIcon("Gradient.jpg"));
     	gradientButton.setBackground(ColorDesign);
     	gradientButton.setToolTipText("그라디언트 도구");
     	gradientButton.setPreferredSize(new Dimension(55,25));
     	gradientButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
     	gradientButton.addActionListener(this);
     	gradientButton.addMouseMotionListener
     	(
            new MouseMotionAdapter()
            {
                public void mouseMoved(MouseEvent event)
                {
                    if ( ( event.getX() > 0 && event.getX() < 55 ) && ( event.getY() > 0 && event.getY() < 25 ) )
                    {
                        helptext.setText("선택된 도형에 명암표현을 할 수 있는 그라디언트 도구창을 불러옵니다.");
                    }
                }
            }
    	);
     	ToolBar_Tool_gradient.add(gradientButton);
     	
     	     		
     	ToolBar_Tool.add(ToolBar_Tool_tool, BorderLayout.CENTER);
     	ToolBar_Tool.add(ToolBar_Tool_gradient, BorderLayout.SOUTH);
     	
   	    //툴바의 팔레트
   	
   		JPanel ToolBar_Pallet = new JPanel();
     	ToolBar_Pallet.setLayout(new BorderLayout());
     	
     	//팔레트를 바꿔주는 패널.마우스리스너와 마우스어댑터를 사용해 클릭했을시 팔레트를 바꿔주는 이벤트처리.
		ToolBar_Pallet_text = new JPanel();
		ToolBar_Pallet_text.setToolTipText("다른 색상 보기");
		ToolBar_Pallet_text.setBorder(BorderFactory.createRaisedBevelBorder());
		JLabel Pallet_text = new JLabel("색상 선택");
     	ToolBar_Pallet_text.add(Pallet_text);
     	ToolBar_Pallet_text.setBackground(ColorDesign);
     	ToolBar_Pallet_text.addMouseListener            
     	(
            new MouseAdapter() 
            {
                public void mouseClicked(MouseEvent event) 
                {
                    if ( ( event.getX() > 0 && event.getX() < 70 ) && ( event.getY() > 0 && event.getY() < 28 ) )
                    {
                        pallets.next(ToolBar_Pallet_pallet);
                    }
                  
                }
            }           
     	);
     
     	ToolBar_Pallet_text.addMouseMotionListener
     	(
            new MouseMotionAdapter()
            {
                public void mouseMoved(MouseEvent event)
                {
                    if ( ( event.getX() > 0 && event.getX() < 70 ) && ( event.getY() > 0 && event.getY() < 28 ) )
                    {
                        ToolBar_Pallet_text.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        helptext.setText("이 부분을 누르면 다른 색상들을 볼 수 있습니다.");
                    }
                }
                
                
            }
    	);
     	ToolBar_Pallet.add(ToolBar_Pallet_text, BorderLayout.NORTH);
		
		//툴바의 3개의 팔레트.
		
		Dimension ColorSize = new Dimension(30,20);
		
		ToolBar_Pallet_pallet = new JPanel();
		
		pallets = new CardLayout();
		
		ToolBar_Pallet_pallet.setLayout(pallets);
		
		JPanel ToolBar_Pallet_pallet1 = new JPanel();
		ToolBar_Pallet_pallet1.setBackground(ColorDesign);
		
		JPanel ToolBar_Pallet_pallet2 = new JPanel();
		ToolBar_Pallet_pallet2.setBackground(ColorDesign);
		
		JPanel ToolBar_Pallet_pallet3 = new JPanel();
		ToolBar_Pallet_pallet3.setBackground(ColorDesign);
		
		
		
		//툴바의 첫번째팔레트 설정.
						
		ToolBar_Pallet_pallet1.setBorder(BorderFactory.createRaisedBevelBorder());
		ToolBar_Pallet_pallet1.setLayout(new GridLayout(4,2));
     	ToolBar_Pallet_pallet1.setCursor(new Cursor(Cursor.HAND_CURSOR));
     	     	
     	         
     	blackButton = new JButton(new ImageIcon("black.jpg"));
     	blackButton.setBackground(ColorDesign);
     	blackButton.setPreferredSize(ColorSize);
     	ToolBar_Pallet_pallet1.add(blackButton);
     	blackButton.addActionListener(this);
     	
     	whiteButton = new JButton(new ImageIcon("white.jpg"));
     	whiteButton.setBackground(ColorDesign);
     	whiteButton.setPreferredSize(ColorSize);
     	ToolBar_Pallet_pallet1.add(whiteButton);
     	whiteButton.addActionListener(this);
     	
     	gray1Button = new JButton(new ImageIcon("gray1.jpg"));
     	gray1Button.setBackground(ColorDesign);
     	gray1Button.setPreferredSize(ColorSize);
     	ToolBar_Pallet_pallet1.add(gray1Button);
     	gray1Button.addActionListener(this);
     	
     	gray2Button = new JButton(new ImageIcon("gray2.jpg"));
     	gray2Button.setBackground(ColorDesign);
     	gray2Button.setPreferredSize(ColorSize);
     	ToolBar_Pallet_pallet1.add(gray2Button);
     	gray2Button.addActionListener(this);
         
     	red1Button = new JButton(new ImageIcon("red1.jpg"));
     	red1Button.setBackground(ColorDesign);
     	red1Button.setPreferredSize(ColorSize);
     	ToolBar_Pallet_pallet1.add(red1Button);
     	red1Button.addActionListener(this);
     	
     	red2Button = new JButton(new ImageIcon("red2.jpg"));
     	red2Button.setBackground(ColorDesign);
     	red2Button.setPreferredSize(ColorSize);
     	ToolBar_Pallet_pallet1.add(red2Button);
     	red2Button.addActionListener(this);
     	
     	yellow1Button = new JButton(new ImageIcon("yellow1.jpg"));
     	yellow1Button.setBackground(ColorDesign);
     	yellow1Button.setPreferredSize(ColorSize);
     	ToolBar_Pallet_pallet1.add(yellow1Button);
     	yellow1Button.addActionListener(this);
     	
     	yellow2Button = new JButton(new ImageIcon("yellow2.jpg"));
     	yellow2Button.setBackground(ColorDesign);
     	yellow2Button.setPreferredSize(ColorSize);
     	ToolBar_Pallet_pallet1.add(yellow2Button);
     	yellow2Button.addActionListener(this);
     	
     
     	
     	//툴바의 두번째 팔레트 설정.
     	
     	ToolBar_Pallet_pallet2.setBorder(BorderFactory.createRaisedBevelBorder());
		ToolBar_Pallet_pallet2.setLayout(new GridLayout(4,2));
     	ToolBar_Pallet_pallet2.setCursor(new Cursor(Cursor.HAND_CURSOR));
     	
     	green1Button = new JButton(new ImageIcon("green1.jpg"));
     	green1Button.setBackground(ColorDesign);
     	green1Button.setPreferredSize(ColorSize);
     	ToolBar_Pallet_pallet2.add(green1Button);
     	green1Button.addActionListener(this);
     	
     	green2Button = new JButton(new ImageIcon("green2.jpg"));
     	green2Button.setBackground(ColorDesign);
     	green2Button.setPreferredSize(ColorSize);
     	ToolBar_Pallet_pallet2.add(green2Button);
     	green2Button.addActionListener(this);
     	
     	sb1Button = new JButton(new ImageIcon("sb1.jpg"));
     	sb1Button.setBackground(ColorDesign);
     	sb1Button.setPreferredSize(ColorSize);
     	ToolBar_Pallet_pallet2.add(sb1Button);
     	sb1Button.addActionListener(this);
     	
     	sb2Button = new JButton(new ImageIcon("sb2.jpg"));
     	sb2Button.setBackground(ColorDesign);
     	sb2Button.setPreferredSize(ColorSize);
     	ToolBar_Pallet_pallet2.add(sb2Button);
     	sb2Button.addActionListener(this);
     	
     	blue1Button = new JButton(new ImageIcon("blue1.jpg"));
     	blue1Button.setBackground(ColorDesign);
     	blue1Button.setPreferredSize(ColorSize);
     	ToolBar_Pallet_pallet2.add(blue1Button);
     	blue1Button.addActionListener(this);
     	
     	blue2Button = new JButton(new ImageIcon("blue2.jpg"));
     	blue2Button.setBackground(ColorDesign);
     	blue2Button.setPreferredSize(ColorSize);
     	ToolBar_Pallet_pallet2.add(blue2Button);
     	blue2Button.addActionListener(this);
     	
     	purple1Button = new JButton(new ImageIcon("purple1.jpg"));
     	purple1Button.setBackground(ColorDesign);
     	purple1Button.setPreferredSize(ColorSize);
     	ToolBar_Pallet_pallet2.add(purple1Button);
     	purple1Button.addActionListener(this);
     	
     	purple2Button = new JButton(new ImageIcon("purple2.jpg"));
     	purple2Button.setBackground(ColorDesign);
     	purple2Button.setPreferredSize(ColorSize);
     	ToolBar_Pallet_pallet2.add(purple2Button);
     	purple2Button.addActionListener(this);
     	
     	   	
     	
     	//툴바의 3번째 팔레트 설정.
     	
     	ToolBar_Pallet_pallet3.setBorder(BorderFactory.createRaisedBevelBorder());
		ToolBar_Pallet_pallet3.setLayout(new GridLayout(4,2));
     	ToolBar_Pallet_pallet3.setCursor(new Cursor(Cursor.HAND_CURSOR));
     	
     	peach1Button = new JButton(new ImageIcon("peach1.jpg"));
     	peach1Button.setBackground(ColorDesign);
     	peach1Button.setPreferredSize(ColorSize);
     	ToolBar_Pallet_pallet3.add(peach1Button);
     	peach1Button.addActionListener(this);
     	
     	peach2Button = new JButton(new ImageIcon("peach2.jpg"));
     	peach2Button.setBackground(ColorDesign);
     	peach2Button.setPreferredSize(ColorSize);
     	ToolBar_Pallet_pallet3.add(peach2Button);
     	peach2Button.addActionListener(this);
     	
     	orangeButton = new JButton(new ImageIcon("orange.jpg"));
     	orangeButton.setBackground(ColorDesign);
     	orangeButton.setPreferredSize(ColorSize);
     	ToolBar_Pallet_pallet3.add(orangeButton);
     	orangeButton.addActionListener(this);
     	
     	brownButton = new JButton(new ImageIcon("brown.jpg"));
     	brownButton.setBackground(ColorDesign);
     	brownButton.setPreferredSize(ColorSize);
     	ToolBar_Pallet_pallet3.add(brownButton);
     	brownButton.addActionListener(this);
     	
     	credButton = new JButton(new ImageIcon("cred.jpg"));
     	credButton.setBackground(ColorDesign);
     	credButton.setPreferredSize(ColorSize);
     	ToolBar_Pallet_pallet3.add(credButton);
     	credButton.addActionListener(this);
     	
     	cgreenButton = new JButton(new ImageIcon("cgreen.jpg"));
     	cgreenButton.setBackground(ColorDesign);
     	cgreenButton.setPreferredSize(ColorSize);
     	ToolBar_Pallet_pallet3.add(cgreenButton);
     	cgreenButton.addActionListener(this);
     	
     	cblueButton = new JButton(new ImageIcon("cblue.jpg"));
     	cblueButton.setBackground(ColorDesign);
     	cblueButton.setPreferredSize(ColorSize);
     	ToolBar_Pallet_pallet3.add(cblueButton);
     	cblueButton.addActionListener(this);
     	
     	cpurpleButton = new JButton(new ImageIcon("cpurple.jpg"));
     	cpurpleButton.setBackground(ColorDesign);
     	cpurpleButton.setPreferredSize(ColorSize);
     	ToolBar_Pallet_pallet3.add(cpurpleButton);
     	cpurpleButton.addActionListener(this);
     	
     	
     	
     	
     	//각 팔레트 패널에 인식이름을 붙이며 전체 팔레트 관리패널에 붙인다.
		ToolBar_Pallet_pallet.add(ToolBar_Pallet_pallet1, "pallet1");
		ToolBar_Pallet_pallet.add(ToolBar_Pallet_pallet2, "pallet2");
		ToolBar_Pallet_pallet.add(ToolBar_Pallet_pallet3, "pallet3");
		
		
		pallets.show(ToolBar_Pallet_pallet, "pallet1"); //처음엔 1번팔레트를 보여준다.
     		
     	
     	ToolBar_Pallet.add(ToolBar_Pallet_pallet, BorderLayout.CENTER);
     	
     
     	
             
    	//툴바의 컬러GUI
     
     	JPanel ToolBar_Color = new JPanel();
     	ToolBar_Color.setBackground(ColorDesign);
     	ToolBar_Color.setBorder(BorderFactory.createRaisedBevelBorder());
     
     	JPanel nowColorWholePanel = new JPanel();
     	nowColorWholePanel.setBackground(ColorDesign);
       
        //현재색을 보여주는 패널. 마우스리스너와 마우스어댑터를 이용해서 JColorChooser를 불러온다.
        //마우스모션리스너와 마우스모션어댑터를 이용해 상태도움표시줄에 도움글을 보여준다.
     	nowColorPanel = new JPanel();
	 	nowColorPanel.setBorder(BorderFactory.createLoweredBevelBorder());	// 테두리 설정
     	nowColorPanel.setPreferredSize(new Dimension(25, 25));			// 크기 설정
   	 	nowColorPanel.setBackground(Color.BLACK);
   	 	nowColorPanel.setToolTipText("현재 색상 선택");
   	 	nowColorPanel.addMouseListener            
     	(
            new MouseAdapter()
            {
                public void mouseClicked(MouseEvent event) 
                {
                    if ( ( event.getX() > 0 && event.getX() < 25 ) && ( event.getY() > 0 && event.getY() < 25 ) )
                    {
                        Color color = JColorChooser.showDialog(JavaPaint.this, "색상 선택 팔레트", firstColor);
			
						if(color == null)
							color = firstColor;
						nowColorPanel.setBackground(color);	
						Layer.setColor(color);	
                    }
                }
            }           
     	);
     
    	nowColorPanel.addMouseMotionListener
     	(
            new MouseMotionAdapter()
            {
                public void mouseMoved(MouseEvent event)
                {
                    if ( ( event.getX() > 0 && event.getX() < 25 ) && ( event.getY() > 0 && event.getY() < 25 ) )
                    {
                        nowColorPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        helptext.setText("이 부분을 누르면 고급 색상 선택 창을 열 수 있습니다.(Ctrl + U)");
                   	}
                
                }
            }
     	);
       
                  	
  	 
  	 	nowColorWholePanel.add(nowColorPanel);
  	 	 	
  	 
	 	JPanel nowcolortextPanel = new JPanel();
	 	nowcolortextPanel.setBackground(ColorDesign);
	 	JLabel nowcolortext = new JLabel("현재 색");
	 	nowcolortextPanel.add(nowcolortext);
		
		
		 		       
     	ToolBar_Color.setLayout(new BorderLayout());
     	ToolBar_Color.add(nowcolortextPanel, BorderLayout.CENTER);        
     	ToolBar_Color.add(nowColorWholePanel, BorderLayout.NORTH);
     
     
     
     
     
     
     	//툴바에 툴바_툴, 툴타_컬러 패널 배치.
     	ToolBar.setLayout(new BorderLayout());
     	ToolBar.add(ToolBar_Pallet, BorderLayout.CENTER);
     	ToolBar.add(ToolBar_Tool, BorderLayout.NORTH);
     	ToolBar.add(ToolBar_Color, BorderLayout.SOUTH);
     
     
     
            
        //상태바
     
     
        JPanel StatusBar = new JPanel();
        StatusBar.setBackground(ColorDesign);
        StatusBar.setPreferredSize(new Dimension(640,40));
        StatusBar.setBorder(BorderFactory.createLoweredBevelBorder());
     
        JPanel HelpBar = new JPanel();
        HelpBar.setBackground(ColorDesign);
        HelpBar.setBorder(BorderFactory.createRaisedBevelBorder());
        helptext = new JLabel("");
        HelpBar.add(helptext);     
               
        JPanel InnerStatusBar = new JPanel(); 
        InnerStatusBar.setBackground(ColorDesign);
        InnerStatusBar.setBorder(BorderFactory.createRaisedBevelBorder());       
        JLabel locationtext = new JLabel("현재 커서 위치 : ");
        InnerStatusBar.add(locationtext);
        pointlocation = new Pointlocation();
        pointlocation.setBackground(ColorDesign);
        InnerStatusBar.add(locationtext);
        InnerStatusBar.add(pointlocation);
     
        StatusBar.setLayout(new BorderLayout());
        StatusBar.add(HelpBar, BorderLayout.CENTER);
        StatusBar.add(InnerStatusBar, BorderLayout.EAST);
     
     
        //그림이 그려지는 영역.
        DrawPanel = new CustomPanel();

        DrawPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        DrawPanel.setBackground(Color.WHITE);
     
 	 
		
		
		//프레임 설정.
		addMouseMotionListener
     	(
            new MouseMotionAdapter()
            {
                public void mouseMoved(MouseEvent event)
                {
                    if ( ( event.getX() > 0 && event.getX() < 640 ) && ( event.getY() > 0 && event.getY() < 50 ) )
                    {
                        helptext.setText("메뉴를 선택해주세요");
                   	}
                   	
                    else if ( ( event.getX() > 0 && event.getX() < 75 ) && ( event.getY() > 50 && event.getY() < 80 ) )
                    {
                        helptext.setText("이 곳에서 툴을 선택해주세요");
                   	}
                   	
                   	                   	
                    else if ( ( event.getX() > 0 && event.getX() < 640 ) && ( event.getY() > 465 && event.getY() < 505 ) )
                    {
                        helptext.setText("이곳에선 현재 상태를 보여줍니다.");
                   	}
                   	               
                                        
                    else
                    	helptext.setText(""); 
                    	//이 부분이 없으면 다른 곳에 위치했을 때 상태도움표시줄을 공백으로 만들지못함.
                
                }
            }
     	);
     
     //-------------------------------------------------------               
    //패널들을 프레임에 붙인다.
    	
     Container container = getContentPane();
     container.setLayout(new BorderLayout());
     container.add(StatusBar, BorderLayout.SOUTH);
     container.add(DrawPanel, BorderLayout.CENTER);
	 container.add(ToolBar, BorderLayout.WEST);
	 
	 
	 //프레임 타이틀과 크기 설정.
	 setTitle("Java Paint Project");
     setSize(640,505);
	 
	 // 크기변경 불가
     setResizable(false);
             
     // 저장하지 않고 x버튼을 눌렀을 경우에 대한 처리
     addWindowListener(new WindowAdapter()
             { 
                 public void windowClosing(WindowEvent we)
                 { 
                     	if (Layer.getDrawing().isEmpty() == false)
 						{
 							checkForSave();
 						}
                    
                 } 
             }
         );
	}
	
/**객체 직렬화란 객체의 내용을 파일 또는 네트워크를 통하여 바이트 스트림으로 
* 	송수신 하기 위하여 객체를 스트림화 하는 것이다. 이렇게 객체를 직렬화
*  함으로써 얻을 수 있는 장점은 객체 자체의 내용을 입출력 형식에 구애 받지 않고 
* 	객체를 파일에 저장함으로써 영속성을 제공할 수 있고, 객체 자체를 네트워크를 통하여
*  손쉽게 교환할 수 있게 되는 것이다. 객체 직렬화를 하기 위해서 ObjectInputStream 클래스와
*  ObjectOutputStream클래스에서를 이용한다.
*/  	
    //열기함수.
	private void Open()
  	{
  		//fileFilter를 사용하기 위한 배열 선언
		String fileFilters [][] = {{".jpg", "JPG"},
                {".jpeg", "JPEG"},
                {".png", "PNG"},
                {".bmp", "BMP"},
                {".jpe", "JPE"},
                {".jfif", "JFIF"}};
  		JFileChooser fileChooser = new JFileChooser();
  		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
  		//filechooser를 연다
  		for(int i = 0; i < fileFilters.length; i++)
  			fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(fileFilters[i][0], fileFilters[i][1]));
  		//FileFilter에 선언한 배열을 입력
  		int result = fileChooser.showOpenDialog(this);
  		
  		if(result == JFileChooser.CANCEL_OPTION)
  			return;
  			
  		File file = fileChooser.getSelectedFile();
  		
  		boolean extExist = false;
  		String filename = fileChooser.getSelectedFile().getName();
  		
  		//클릭된 파일의 확장자가 FileFilter에 없다면 열수없는 파일이다.
  		for(int i = 0; i < fileFilters.length; i++)
  			if(filename.endsWith(fileFilters[i][0]))
  			{
  				extExist = true; break;
  			}
  	//예외처리.
  		if(extExist == false)
  		{
  			JOptionPane.showMessageDialog(this, "열 수 없는 파일입니다!",
  					"열 수 없는 파일입니다!",JOptionPane.ERROR_MESSAGE);
  			return;
  		}
  		//파일 오픈 예외처리
  		if((file == null) || (file.getName().equals("")))
  		{
  			JOptionPane.showMessageDialog(this, "파일이름을 입력하지 않으셨습니다!",
  					"파일이름을 입력하지 않으셨습니다!",JOptionPane.ERROR_MESSAGE);
  		}

  		else
  		{

  			try
  			{

  				
  				image = ImageIO.read(file);			//이미지를 읽음
  				Layer.DrawImage(image);   				//이미지를 배경으로 저장.
  				repaint();
  				saveFile = file;					//불러온 파일을 saveFile로 저장
  				cacheSavedFile = file;
  				fileName = saveFile.getName();
  				setTitle("현재 파일명 : " + fileName + " , 위치 : " + saveFile.getPath());
  			} 
  			catch (Exception e) { System.out.println(e); }
  		}

  	}
	
  	
  	public static File cacheSavedFile;
  	private void _save(){
  		if(cacheSavedFile==null)
  			SaveAs();
  		else{
  			
  			try
  			{
  				//벡터를 객체직렬화로 파일화.
  				DrawPanel.paintsave(image);
  				System.out.println(cacheSavedFile.getAbsolutePath());
  				String [] str = cacheSavedFile.getAbsolutePath().split("\\.");
  				String ext = str[1];

  				ImageIO.write(image,ext,cacheSavedFile);
  			}
  			catch (Exception e) { System.out.println(e); }			
  			
  		}
  	}
  	//저장함수.
 	private void SaveAs()
  	{
  		//fileFilter 설정
 		String fileFilters [][] = {{".jpg", "JPG"},
                {".jpeg", "JPEG"},
                {".png", "PNG"},
                {".bmp", "BMP"},
                {".jpe", "JPE"},
                {".jfif", "JFIF"}};
  		JFileChooser fileChooser = new JFileChooser();
  		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
  		//////////----------------------ADDED BY JW--------------------------------//////////
  		//-기존에 filter지정을 하지않으면 저장이 안되는 문제 해결 (기본 디폴트로 jpg로 지정)
  		fileChooser.setFileFilter(new FileNameExtensionFilter(fileFilters[0][0], fileFilters[0][1]));
  		//////////--------------------------END------------------------------------//////////
  		for(int i = 1; i < fileFilters.length; i++)
  			fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(fileFilters[i][0], fileFilters[i][1]));
  		
  		int result = fileChooser.showSaveDialog(this);
  		if(result == JFileChooser.CANCEL_OPTION)
  			return;
  			
  		File file = fileChooser.getSelectedFile();
  		boolean extExist = false;
  		String filename = fileChooser.getSelectedFile().getName();
  		if((file == null) || (file.getName().equals("")))
  		{
  			JOptionPane.showMessageDialog(this, "파일이름을 입력하지 않으셨습니다!",
  			"파일이름을 입력하지 않으셨습니다!",JOptionPane.ERROR_MESSAGE);
  			
  		}
  		//파일 확장자가 파일필터에 있을 경우
  		for(int i = 0; i < fileFilters.length; i++)
  			if(filename.endsWith(fileFilters[i][0]))
  			{
  				extExist = true; break;
  			}
  		
  		//File이름에 확장자가 포함되어 있는 경우
  		if(filename.lastIndexOf('.') > 0 && extExist)
  			file = fileChooser.getSelectedFile();
  		else if (fileChooser.getFileFilter().getDescription().equals("모든 파일"))
  			file = new File(fileChooser.getSelectedFile().getPath() +  ".png");//Filter가 모든파일로 돼있을 경우 기본적으로 png로 저장
  		else//File이름에 확장자가 포함되어 있지 않고, Filter가 선택돼 있을 경우, 파일이름 뒤에 Filter 확장자를 더해줌 
  			file = new File(fileChooser.getSelectedFile().getPath() + fileChooser.getFileFilter().getDescription());
  		
  		//예외처리.
  		if(file.exists()){
  			if (JOptionPane.NO_OPTION == JOptionPane.showConfirmDialog(JavaPaint.this,
  										 file.getPath() + "\n같은 이름의 파일이 이미 존재합니다.\n" +
  										 "기존 파일을 이 파일로 대체하시겠습니까?", 
										"다른 이름으로 저장",
										JOptionPane.YES_NO_OPTION, 
										JOptionPane.WARNING_MESSAGE))	 
  				return;		// No를 클릭하면 저장하지 않는다.
  			
  		}
  		
  		
  		try
		{
			//이미지를 새로 그리기 위한 함수 호출. 이미지를 다시 그리지 않을 경우, 이미지를 그렸던 잔상이 남음.
  			DrawPanel.paintsave(image);
  			String ext = fileChooser.getFileFilter().getDescription();
  			ext = ext.substring(1);//Image를 저장하기 위한 확장자를 가져옴.
			ImageIO.write(image,ext,file);
		}
		catch (Exception e) { System.out.println(e); }			
			
		if (file != saveFile)
		{
			cacheSavedFile = file;
			saveFile = file;
			fileName = saveFile.getName();
			setTitle("현재 파일명 : " + fileName + " , 위치 : " + saveFile.getPath());
		}
  		  		
  	}
  	
  	//변경사항을 저장할 것인지 확인해주는 함수.
  	public void checkForSave()
	{		
		if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(JavaPaint.this,
										"변경 사항을 저장하시겠습니까?",
										"파일 저장하기",
										JOptionPane.YES_NO_OPTION,
										JOptionPane.WARNING_MESSAGE))
			_save();
		else
			return;
	}
  	
	
	// ----------------------------------------------------------------------------------------
	public void actionPerformed(ActionEvent event)
	{
               DrawPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		Object action = event.getSource();
		
		if(action == LayerAdd || action == addLayerButton)
		{
			Layer.AddSpace();    // 현재 그림을 참조할 객체 생성 
			DrawPanel.repaint();
		}
		else if(action == LayerDelete || action == deleteLayerButton)
		{
			Layer.DeleteSpace();
			DrawPanel.repaint();
		}
		else if(action == TextBox || action == textToolButton)
			DrawPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		// ----------------------------------------------------------------------
		// 파일I/O관련.
		else if (action == newItem)
		{
			if (Layer.getDrawing().isEmpty() == false)
			{
				checkForSave();
			}
			Layer.newShape();
			repaint();
			setTitle("제목없음.cha");
			saveFile = null;
			cacheSavedFile = null;
		}
		
		else if (action == openItem)
		{
			if (saveFile != null)
				checkForSave();
				Open();
		}
		
		else if (action == saveItem)
		{
			_save(); 		
		}
		
		else if (action == saveAsItem)
		{
			SaveAs(); 
		}
		
		else if (action == exitItem)
		{
			checkForSave();
			System.exit(0);
		}
		
		//특정 버튼이 눌림에 따라 마우스 작업환경으로 보일 툴킷을 변경해준다.
        else if(action == lineButton ||action == polyButton ||action == ovalButton ||action == rectButton ||action == pasteButton)
               DrawPanel.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR)); 

        else if(action == selectButton || action == fillcolorButton)
               DrawPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        else if(action == moveButton|| action==moveItem)
               DrawPanel.setCursor(new Cursor(Cursor.MOVE_CURSOR));
               
        //툴바버튼이벤트처리.

		else if(action == undoButton)
		{
			Layer.undo();
			repaint();
		}
		
		else if(action == backButton)
		{
			Layer.moveToBack(Layer.getShape(Layer.getEditIndex()));
			repaint();
		}
		
		else if(action == cutButton||action == cutItem)
		{
			Layer.cut(Layer.getShape(Layer.getEditIndex()));
			repaint();
		}
		
		else if(action == gradientButton)
		{
		    gradientFrame = new Gradient_Frame();
			
		}
		
		else if(action == copyButton||action==copyItem)
		{
			Layer.copy(Layer.getShape(Layer.getEditIndex()));

		}
		
		else if (action == deleteButton||action==deleteItem)
		{
			if(Layer.isEditing()==true){
			Layer.delete();
			repaint();
			}
		}
		
		else if (action == About)
		{
			JOptionPane.showMessageDialog(JavaPaint.this, "그림판 ver 2.0 \n"+
			 	"학번: 2012106002  성명 : 고관우\n학번: 2012122242  성명 : 임재욱\n"+ 
			 	"학번: 2013122276  성명 : 지민규\n","Java 팀 프로젝트", JOptionPane.INFORMATION_MESSAGE);
		}
		
		// 고관우
	      // 액션에 따른 이벤트 처리
	      else if (action == freedrawItem || action == penToolButton)
	      {
	         ordering = new DrawPoint();
	      }      

	      else if (action == magicdrawItem)
	      {
	         ordering = new DrawMagic();
	      }      

	      else if (action == triangledrawItem)
	      {
	         ordering = new DrawTriangle();
	      }      
	      
	      else if (action == sayungdrawItem)
	      {
	         ordering = new DrawSayung();
	      }      
	      
	      else if (action == linepointdrawItem)
	      {
	         ordering = new DrawLinepoint();
	      }

	      else if (action == erasedrawItem)
	      {
	         ordering = new DrawErase();
	      }
		///관우end
		//팔레트의 이벤트 처리.(RGB코드를 이용해 각각의 경우에 Color를 설정해준다.)
		//------------------------------------------
		
		else if(action == blackButton)
		{
			nowColorPanel.setBackground(Color.BLACK);	
			Layer.setColor(Color.BLACK);
		}
		
		else if(action == whiteButton)
		{
			nowColorPanel.setBackground(Color.WHITE);	
			Layer.setColor(Color.WHITE);
		}
		
		else if(action == gray1Button)
		{
			nowColorPanel.setBackground(new Color(128,128,128));	
			Layer.setColor(new Color(128,128,128));
		}
		
		else if(action == gray2Button)
		{
			nowColorPanel.setBackground(new Color(192,192,192));	
			Layer.setColor(new Color(192,192,192));
		}
		
		else if(action == red1Button)
		{
			nowColorPanel.setBackground(new Color(128,0,0));	
			Layer.setColor(new Color(128,0,0));
		}
		
		else if(action == red2Button)
		{
			nowColorPanel.setBackground(new Color(255,0,0));	
			Layer.setColor(new Color(255,0,0));
		}
		
		else if(action == yellow1Button)
		{
			nowColorPanel.setBackground(new Color(128,128,0));	
			Layer.setColor(new Color(128,128,0));
		}
		
		else if(action == yellow2Button)
		{
			nowColorPanel.setBackground(new Color(255,255,0));	
			Layer.setColor(new Color(255,255,0));
		}
		
		else if(action == green1Button)
		{
			nowColorPanel.setBackground(new Color(0,128,0));	
			Layer.setColor(new Color(0,128,0));
		}
		
		else if(action == green2Button)
		{
			nowColorPanel.setBackground(new Color(0,255,0));	
			Layer.setColor(new Color(0,255,0));
		}
		
		else if(action == sb1Button)
		{
			nowColorPanel.setBackground(new Color(0,128,128));	
			Layer.setColor(new Color(0,128,128));
		}
		
		else if(action == sb2Button)
		{
			nowColorPanel.setBackground(new Color(0,255,255));	
			Layer.setColor(new Color(0,255,255));
		}
		
		else if(action == blue1Button)
		{
			nowColorPanel.setBackground(new Color(0,0,128));	
			Layer.setColor(new Color(0,0,128));
		}
		
		else if(action == blue2Button)
		{
			nowColorPanel.setBackground(new Color(0,0,255));	
			Layer.setColor(new Color(0,0,255));
		}
		
		else if(action == purple1Button)
		{
			nowColorPanel.setBackground(new Color(128,0,128));	
			Layer.setColor(new Color(128,0,128));
		}
		
		else if(action == purple2Button)
		{
			nowColorPanel.setBackground(new Color(255,0,255));	
			Layer.setColor(new Color(255,0,255));
		}
		
		else if(action == peach1Button)
		{
			nowColorPanel.setBackground(new Color(255,255,128));	
			Layer.setColor(new Color(255,255,128));
		}
		
		else if(action == peach2Button)
		{
			nowColorPanel.setBackground(new Color(253,223,185));	
			Layer.setColor(new Color(253,223,185));
		}
		
		else if(action == orangeButton)
		{
			nowColorPanel.setBackground(new Color(255,128,64));	
			Layer.setColor(new Color(255,128,64));
		}
		
		else if(action == brownButton)
		{
			nowColorPanel.setBackground(new Color(128,64,0));	
			Layer.setColor(new Color(128,64,0));
		}
		
		else if(action == credButton)
		{
			nowColorPanel.setBackground(new Color(255,128,128));	
			Layer.setColor(new Color(255,128,128));
		}
		
		else if(action == cgreenButton)
		{
			nowColorPanel.setBackground(new Color(128,255,128));	
			Layer.setColor(new Color(128,255,128));
		}
		
		else if(action == cblueButton)
		{
			nowColorPanel.setBackground(new Color(0,128,255));	
			Layer.setColor(new Color(0,128,255));
		}
		
		else if(action == cpurpleButton)
		{
			nowColorPanel.setBackground(new Color(128,128,255));	
			Layer.setColor(new Color(128,128,255));
		}
//////////----------------------ADDED BY JW--------------------------------//////////
		else if(action == JW_fillBg || action == fillBackgroundColorButton)
		{
			/**
			 * 색상 선택 후 채우기 
			 */
			Color color = JColorChooser.showDialog(JavaPaint.this, "색상 선택 팔레트", firstColor);
			
			ShapeRect backgroundLayer = Layer.getBackgroundLayer();
			
			backgroundLayer.setColor(color);
			backgroundLayer.BackGroundFill=true;
			repaint();
		}
		else if(action == JW_tranparent)
		{
			transparentchooser.setVisible(true);
		}
		//////////--------------------------END------------------------------------//////////
	}
	  	
	
	//현재 커서 위치를 알려주는 Pointlocation클래스.
	class Pointlocation extends JPanel
	{
		JLabel location = new JLabel("");
		
		public Pointlocation()
		{
			setBorder(BorderFactory.createLoweredBevelBorder());
			setPreferredSize(new Dimension(100,25));
			add(location);
		}
		
		public void settext(String text)
		{
			location.setText(text);
		}
				
		public void setPoint(int x, int y)
		{
			location.setText("( " + x + ", " + y + " )");
		}
	}

	// --------------------------------------------------------------------------------------------------------
	// 마우스 이벤트를 이용하여 실제로 그림을 그리는 캔버스역할을 하는 DrawPanel.
	private class CustomPanel extends JPanel implements MouseInputListener
	{
 		// 생성자
 		public CustomPanel()
 		{
   		addMouseListener(this);
   		addMouseMotionListener(this);
   		
 		}
 
 		public void paint(Graphics g)
 		{
   		super.paint(g);
   		Layer.draw(g);
   		
 		}
 		public void paintsave(BufferedImage im)
 		{
 			Layer.drawsave(im);
 		}
 		// 마우스를 클릭했을 때 현재의 명령을 실행한다
 		public void mouseClicked(MouseEvent event)
 		{
			//	마우스 가운데 버튼과 오른쪽 버튼이 아닌 경우에 수행
 			if (!(event.isMetaDown()) && !(event.isAltDown()))
 			{
			eventhandle.ordering.mouseClickExe(event.getPoint(), Layer); 
   			repaint();
   			}
 			
 		}

 		// 마우스를 눌렀을 때 현재의 명령을 실행한다.
 		public void mousePressed(MouseEvent event)
 		{
			if (!(event.isMetaDown()) && !(event.isAltDown()))
			{
			eventhandle.ordering.mousePressExe(event.getPoint(), Layer);
   			repaint();
			}
			
			
 		}
 
		// 마우스를 드래그할 때 현재의 명령을 실행한다
 		public void mouseDragged(MouseEvent event)
 		{
			if (!(event.isMetaDown()) && !(event.isAltDown()) && event.isShiftDown())
 			{
 				eventhandle.ordering.mouseDragShiftExe(event.getPoint(), Layer); 
 	   			repaint();
 			}
			else if (!(event.isMetaDown()) && !(event.isAltDown()))
			{
			    eventhandle.ordering.mouseDragExe(event.getPoint(), Layer);
   			repaint();
			}
			
			pointlocation.setPoint(event.getX(), event.getY());		// 마우스의 좌표를 보여준다
			
 		}   

 		public void mouseReleased(MouseEvent event) 
 		{ 
 			
 			eventhandle.ordering.mouseRelExe(event.getPoint(), Layer);
			repaint();
			pointlocation.setPoint(event.getX(), event.getY());	// 마우스의 좌표를 보여준다
			
			
 		}
 		public void mouseEntered(MouseEvent event) 
 		{
 			helptext.setText("이곳에서 그림을 그려주세요."); //마우스가 DrawPanel에 들어오면 상태도움표시줄을 변경.
 		}
 		public void mouseExited(MouseEvent event)
 		{
 			
 			pointlocation.settext("캔버스 외부"); 	// 마우스가 DrawPanel을 벗어나면 현재커서위치를 '캔버스 외부'로 변경.
 		} 
 		
 		public void mouseMoved(MouseEvent event) 
 		{
 			
 			pointlocation.setPoint(event.getX(), event.getY());			// 마우스의 좌표를 보여준다
 		} 
	}
	
	//도형의 색채우기에서 명암효과를 줄 수 있는 그라디언트.
	private class Gradient_Frame extends JFrame implements ActionListener
	{
		private JRadioButton Button_1, Button_2, Button_3, Button_4; //방향을 설정할 수 있게 할 라디오버튼.
		private ButtonGroup radioGroup; //라디오버튼을 관리하는 라디오버튼그룹.
		private JPanel color_1, color_2; //출발색과 도착색을 설정할 color_1과 color_2
		private JButton apply_Button; // 그라디언트 적용 명령을 실행할 수 있는 버튼.
		private int direct; 
		private Color color1, color2;

		public Gradient_Frame()
		{
			Container container = getContentPane();
			container.setLayout(new FlowLayout());
			
			Button_1 = new JRadioButton("↘방향",true);
			container.add(Button_1);
			Button_2 = new JRadioButton("↗방향",false);
			container.add(Button_2);
			Button_3 = new JRadioButton("↙방향",false);
			container.add(Button_3);
			Button_4 = new JRadioButton("↖방향",false);
			container.add(Button_4);
			
			//컬러 1관련 현재색보여주기, 색상선택이벤트를 발생		
			color_1 = new JPanel();
			color_1.setBorder(BorderFactory.createLoweredBevelBorder());	// 테두리 설정
     		color_1.setPreferredSize(new Dimension(25, 20));			// 크기 설정
   	 		color_1.setBackground(Color.BLACK);
   	 		color_1.setToolTipText("시작점 색상 선택");
   	 		color_1.addMouseListener            
     		(
            	new MouseAdapter() 
            	{
                	public void mouseClicked(MouseEvent event) 
                	{
                    	if ( ( event.getX() > 0 && event.getX() < 25 ) && ( event.getY() > 0 && event.getY() < 25 ) )
                    	{
                        color1 = JColorChooser.showDialog(Gradient_Frame.this, "color1 선택", firstColor);
						if(color1 == null)
							color1 = firstColor;
						color_1.setBackground(color1);	
						}
                  
                	}
            	}           
     		);
            
    		color_1.addMouseMotionListener
     		(
            	new MouseMotionAdapter()
            	{
                	public void mouseMoved(MouseEvent event)
                	{
                    	if ( ( event.getX() > 0 && event.getX() < 25 ) && ( event.getY() > 0 && event.getY() < 25 ) )
                    	{
                        color_1.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        helptext.setText("이 부분을 누르면 시작점의 고급 색상 선택 창을 열 수 있습니다.");
                   		}
                
                	}
            	}
     		);
			container.add(color_1);
			
			
			//무슨 색에서 무슨색으로 가는지 보여줄 텍스트.
			JLabel text1 = new JLabel("-->");
			container.add(text1);
			
			//컬러 2관련 현재색보여주기, 색상선택이벤트를 발생
			
			color_2 = new JPanel();
			color_2.setBorder(BorderFactory.createLoweredBevelBorder());	// 테두리 설정
     		color_2.setPreferredSize(new Dimension(25, 20));			// 크기 설정
   	 		color_2.setBackground(Color.BLACK);
   	 		color_2.setToolTipText("도착점 색상 선택");
   	 		color_2.addMouseListener            
     		(
            	new MouseAdapter() 
            	{
                	public void mouseClicked(MouseEvent event) 
                	{
                    	if ( ( event.getX() > 0 && event.getX() < 25 ) && ( event.getY() > 0 && event.getY() < 25 ) )
                    	{
                        color2 = JColorChooser.showDialog(Gradient_Frame.this, "color2 선택", firstColor);
						if(color2 == null)
							color2 = firstColor;
						color_2.setBackground(color2);	
						}
                  
                	}
            	}           
     		);
     
    		color_2.addMouseMotionListener
     		(
            	new MouseMotionAdapter()
            	{
                	public void mouseMoved(MouseEvent event)
                	{
                    	if ( ( event.getX() > 0 && event.getX() < 25 ) && ( event.getY() > 0 && event.getY() < 25 ) )
                    	{
                        color_2.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        helptext.setText("이 부분을 누르면 도착점의 고급 색상 선택 창을 열 수 있습니다.");
                   		}
                
                	}
            	}
     		);
			container.add(color_2);
			
			
			JLabel text2 = new JLabel("   ");
			container.add(text2);
			
			//그라디언트효과를 적용하는 버튼.
			apply_Button = new JButton(new ImageIcon("apply.jpg"));
			apply_Button.setPreferredSize(new Dimension(80,23));
			apply_Button.setToolTipText("그라디언트를 도형에 적용합니다.");
			apply_Button.setCursor(new Cursor(Cursor.HAND_CURSOR));
			apply_Button.addActionListener(this);
			container.add(apply_Button);
			
			radioGroup = new ButtonGroup();
			radioGroup.add(Button_1);
			radioGroup.add(Button_2);
			radioGroup.add(Button_3);
			radioGroup.add(Button_4);
			
			
			Button_1.addItemListener(new RadioButtonHandler(1));
			Button_2.addItemListener(new RadioButtonHandler(2));
			Button_3.addItemListener(new RadioButtonHandler(3));
			Button_4.addItemListener(new RadioButtonHandler(4));
						
			setTitle("Gradient 툴");
			setSize(280,100);
			setLocation(440,50);
			setVisible(true);
			setResizable(false);
			setBackground(new Color(232,232,233));
		}
		
		public void actionPerformed(ActionEvent ae)
		{
			//만약 이벤트가 색상적용버튼을 눌렀을 시에, 캔버스(DrawPanel)을 repaint처리.					
			Object action_n = ae.getSource();		
			if(action_n==apply_Button){
			DrawPanel.repaint();
		}
					
				
		}
		private class RadioButtonHandler implements ItemListener{
			private int direct=1;
			
			public RadioButtonHandler(int i)
			{
				direct = i;
			}
			
			public void itemStateChanged(ItemEvent ie)
			{
				Shape temp = ((Shape) Layer.getShape(Layer.getEditIndex()));
				temp.setGradientDirect(direct);
				temp.setGradientColor(color1, color2);
				temp.setGradient();
				
			}
		}
			
	
	}
	//////////----------------------ADDED BY JW--------------------------------//////////
	public Drawing getLayer() {
		return Layer;
	}
	//////////--------------------------END------------------------------------//////////
	
}