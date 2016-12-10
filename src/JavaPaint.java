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
	private EventHandler eventhandle; // ��� �ֿ��̺�Ʈ���� ������ �̺�Ʈ�ڵ鷯�� ��ü
	private final Color firstColor = Color.BLACK; //�⺻���� ���� ������ ����
	private static Order ordering;         					// ����� ���
	private Drawing Layer; 					// ��ɿ� ���� �׸��� �׸� ��ü
	private int CurrentLayer = 0;
	private Pointlocation pointlocation; //���� Ŀ���� ��ġ�� �˷��ִ� �г�.
	private JPanel nowColorPanel;   //������� �����ִ� �гΰ�ü.
	private JPanel ToolBar_Pallet_text;   //�ȷ�Ʈ�� �ٲ��ִ� ��ư������ �� �г�.
	private CardLayout pallets;       //ī�巹�̾ƿ��� ��ü.(.next()�޼ҵ带 ��������) 			
	private JPanel ToolBar_Pallet_pallet; //3���� �ȷ�Ʈ�г��� �����ϴ� ��ü �ȷ�Ʈ�����г�.
	private JLabel helptext; //���� ���� ǥ������ ��.
	private JFrame gradientFrame; //�׶���Ʈ ������
	private static JavaPaint application; 
	
	//���ٹ�ư
	
	private JButton selectButton, lineButton, rectButton, ovalButton, polyButton, resizeButton, moveButton, deleteButton, 
					cutButton, pasteButton, copyButton, fillcolorButton, undoButton, backButton ,gradientButton,
	/*added button*/	addLayerButton,deleteLayerButton,penToolButton,textToolButton,fillBackgroundColorButton;
			
	//�ȷ�Ʈ��ư.		
	private JButton whiteButton, blackButton, gray1Button, gray2Button, red1Button, red2Button, 
				blue1Button, blue2Button, sb1Button, sb2Button, green1Button,green2Button, 
				yellow1Button, yellow2Button, purple1Button, purple2Button,
				peach1Button, peach2Button, orangeButton, brownButton, credButton, cgreenButton,
				cblueButton, cpurpleButton; 
	
	//�޴�
 	private JMenu fileMenu, Edit, Help, AddLayer, Func1;
 	
//////////----------------------ADDED BY JW--------------------------------//////////
private JMenu JWMenu;
private JMenuItem JW_fillBg,JW_tranparent;
/**
 * ���� ����â construct
 */
private TransparentChooser transparentchooser = new TransparentChooser(this);

//////////--------------------------END------------------------------------//////////
 	//�޴�������.
 	private JMenuItem newItem, selectItem, openItem, saveItem, saveAsItem, exitItem, 
					moveItem, resizeItem, cutItem, copyItem, pasteItem, deleteItem, colorchooseItem, helpItem, About, 
					LayerAdd, LayerDelete, TextBox, freedrawItem, magicdrawItem,triangledrawItem, sayungdrawItem, linepointdrawItem, erasedrawItem;
 	
	//DrawPanel. ���� �׸��� �׷����� ĵ���������� �ϴ� �г�.				
	public CustomPanel DrawPanel;
	
	public Drawing SaveLayer;
	
	//��������� ����.
	private JFileChooser fileChoose = new JFileChooser();
	private String fileName = "�������.cha";
	private File saveFile;
	
	//�����Լ�.
	
	public static void main(String args[])
	{
		application = new JavaPaint();
		application.setVisible(true);
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                       
	}
	
	// JavaPaint ������. �гε�� ��ư�� �����ϰ� ��ġ
 
	public JavaPaint()
	{
		super();
		eventhandle = new EventHandler();	
		setTitle(fileName);		// JFrame �ʱ�ȭ
		
		
		//������ ��Ÿ�Ϸ� ����� ����.-----------
		
		try { 
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); 
			SwingUtilities.updateComponentTreeUI(this); 
		} 
		catch (Exception e) { }
		
		//---------------------------------------
		
		
 		ordering = new Order();          		// ����� ������ ��ü ����
 		Layer = new Drawing(firstColor);    // ���� �׸��� ������ ��ü ����
 		
 		Color ColorDesign = new Color(232,232,233); //��ü�������� ���� RGB�� ����
 		
 		
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
 		
		JWMenu = new JMenu("���󵵱�");
		JWMenu.setBackground(ColorDesign);
		
		JW_fillBg = new JMenuItem("����/��������");
		JW_fillBg.setBackground(ColorDesign);
		JW_fillBg.addActionListener(this);
		JWMenu.add(JW_fillBg);
		
		JW_tranparent = new JMenuItem("����");
		JW_tranparent.setBackground(ColorDesign);
		JW_tranparent.addActionListener(this);
		JWMenu.add(JW_tranparent);
		//////////--------------------------END------------------------------------//////////
 		
 		//���� �޴�
		fileMenu = new JMenu("����(F)");
		fileMenu.setMnemonic('f');
		fileMenu.setBackground(ColorDesign);
				
		newItem = new JMenuItem("���� �����(N)", new ImageIcon("New.jpg"));
		newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		newItem.setBackground(ColorDesign);
		newItem.addActionListener(this);
		fileMenu.add(newItem);
		
		
		openItem = new JMenuItem("����(O)", new ImageIcon("Open.jpg"));
		openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		openItem.setBackground(ColorDesign);
		openItem.addActionListener(this);
		fileMenu.add(openItem);
		
		
		saveItem = new JMenuItem("����(S)", new ImageIcon("Save.jpg"));
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		saveItem.setBackground(ColorDesign);
		saveItem.addActionListener(this);
		fileMenu.add(saveItem);
		
		
		saveAsItem = new JMenuItem("�ٸ� �̸����� ����");
		saveAsItem.setBackground(ColorDesign);
		saveAsItem.addActionListener(this);
		fileMenu.add(saveAsItem);
		
		
		exitItem = new JMenuItem("����");
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		exitItem.setBackground(ColorDesign);
		exitItem.addActionListener(this);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);	
		
		
		
		// ���� �޴�(����, �̵�, ��������, ����, �߶󳻱�, �ٿ��ֱ�, �����ϱ�, ������)	
		Edit = new JMenu("����(E)");
		Edit.setBackground(ColorDesign);
		Edit.setMnemonic('e');
		
		selectItem = new JMenuItem("�����ϱ�");
		selectItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
		selectItem.setBackground(ColorDesign);
		selectItem.addActionListener(eventhandle);
		selectItem.setActionCommand("26");
		Edit.add(selectItem);
		
		moveItem = new JMenuItem("�̵��ϱ�");
		moveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
		moveItem.setBackground(ColorDesign);
		moveItem.addActionListener(eventhandle);
		moveItem.setActionCommand("26");
		Edit.add(moveItem);
		
		resizeItem = new JMenuItem("ũ�� ����");
		resizeItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		resizeItem.setBackground(ColorDesign);
		resizeItem.addActionListener(eventhandle);
		resizeItem.setActionCommand("24");
		Edit.add(resizeItem);
		
		copyItem = new JMenuItem("�����ϱ�");
		copyItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		copyItem.setBackground(ColorDesign);
		copyItem.addActionListener(eventhandle);
		copyItem.setActionCommand("23");
		Edit.add(copyItem);
		
		cutItem = new JMenuItem("�߶󳻱�");
		cutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
		cutItem.setBackground(ColorDesign);
		cutItem.addActionListener(eventhandle);
		cutItem.setActionCommand("22");
		Edit.add(cutItem);
				
			
		pasteItem = new JMenuItem("�ٿ��ֱ�");
		pasteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		pasteItem.setBackground(ColorDesign);
		pasteItem.addActionListener(eventhandle);
		pasteItem.setActionCommand("27");
		Edit.add(pasteItem);
		
		deleteItem = new JMenuItem("�����ϱ�");
		deleteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
		deleteItem.setBackground(ColorDesign);
		deleteItem.addActionListener(eventhandle);
		deleteItem.setActionCommand("25");
		Edit.add(deleteItem);
		
		
		
		Edit.addSeparator();
		colorchooseItem = new JMenuItem("���� ����");
		colorchooseItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
		colorchooseItem.setBackground(new Color(204,204,255));
		Edit.add(colorchooseItem);
		colorchooseItem.addActionListener(this);
		
			
		// ���� �޴�	
		Help = new JMenu("����(H)");
		Help.setBackground(ColorDesign);
		Help.setMnemonic('h');
		
//		helpItem = new JMenuItem("����");
//		helpItem.setBackground(ColorDesign);
//		helpItem.addActionListener(this);
//		Help.add(helpItem);
		
		
		About = new JMenuItem("About..");
		About.setBackground(ColorDesign);
		About.addActionListener(this);
		Help.add(About);
		
		// �߰���� �޴�   
		Func1 = new JMenu("�����");
		Func1.setBackground(ColorDesign);
		//Help.setMnemonic('h');

		freedrawItem = new JMenuItem("���� �����");
		freedrawItem.addActionListener(this);
		freedrawItem.setBackground(ColorDesign);
		freedrawItem.addActionListener(eventhandle);
		freedrawItem.setActionCommand("30");
		Func1.add(freedrawItem);


		magicdrawItem = new JMenuItem("���� �����");
		magicdrawItem.addActionListener(this);
		magicdrawItem.setBackground(ColorDesign);
		magicdrawItem.addActionListener(eventhandle);
		magicdrawItem.setActionCommand("31");
		Func1.add(magicdrawItem);


		triangledrawItem = new JMenuItem("�ﰢ�� �����");
		triangledrawItem.addActionListener(this);
		triangledrawItem.setBackground(ColorDesign);
		triangledrawItem.addActionListener(eventhandle);
		triangledrawItem.setActionCommand("32");
		Func1.add(triangledrawItem);

		sayungdrawItem = new JMenuItem("�翵 �����");
		sayungdrawItem.addActionListener(this);
		sayungdrawItem.setBackground(ColorDesign);
		sayungdrawItem.addActionListener(eventhandle);
		sayungdrawItem.setActionCommand("33");
		Func1.add(sayungdrawItem);

		linepointdrawItem = new JMenuItem("������ �����");
		linepointdrawItem.addActionListener(this);
		linepointdrawItem.setBackground(ColorDesign);
		linepointdrawItem.addActionListener(eventhandle);
		linepointdrawItem.setActionCommand("34");
		Func1.add(linepointdrawItem);

		erasedrawItem = new JMenuItem("���찳");
		erasedrawItem.addActionListener(this);
		erasedrawItem.setBackground(ColorDesign);
		erasedrawItem.addActionListener(eventhandle);
		erasedrawItem.setActionCommand("35");
		Func1.add(erasedrawItem);
		///////////���� end //////////////
		
		AddLayer = new JMenu("���̾�");
		AddLayer.setBackground(ColorDesign);
		
		LayerAdd = new JMenuItem("���̾� �߰�");
		LayerAdd.setBackground(ColorDesign);
		LayerAdd.addActionListener(this);
		AddLayer.add(LayerAdd);
		
		LayerDelete = new JMenuItem("���̾� ����");
		LayerDelete.setBackground(ColorDesign);
		LayerDelete.addActionListener(this);
		AddLayer.add(LayerDelete);
		
		TextBox = new JMenuItem("�ؽ�Ʈ �ڽ�");
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
                        helptext.setText("���ϴ� ��ġ�� �ؽ�Ʈ�� �׸��ϴ�.");
                    }
                }
                
                
            }
    	);
		AddLayer.add(TextBox);
		
		//�޴��� ����.
		JMenuBar menuBar = new JMenuBar();		
		menuBar.setBackground(ColorDesign);
		menuBar.add(fileMenu);
		menuBar.add(Edit);
		
		menuBar.add(AddLayer);
		//����//
		menuBar.add(Func1);
		//////////----------------------ADDED BY JW--------------------------------//////////
		menuBar.add(JWMenu);
		//////////--------------------------END------------------------------------//////////	
		menuBar.add(Help);
		setJMenuBar(menuBar);
						
		//Toolbar
		//������ ��GUI
				
				
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
		JLabel Tool_text = new JLabel("���� ����");
     	ToolBar_Tool_text.add(Tool_text);
     	ToolBar_Tool_text.setBackground(ColorDesign);
     	ToolBar_Tool.add(ToolBar_Tool_text, BorderLayout.NORTH);
			
		ToolBar_Tool_tool.setBorder(BorderFactory.createRaisedBevelBorder());
		ToolBar_Tool_tool.setLayout(new GridLayout(7,2));
     	ToolBar_Tool_tool.setCursor(new Cursor(Cursor.HAND_CURSOR));
     
                    		
		//��ư�� �����ϰ� �����ϴ� �κ�.���콺��Ǹ����ʿ� ���콺��Ǿ���ͷ� ���µ���ǥ������ ����.
		
		lineButton = new JButton(new ImageIcon("Line.jpg"));
		lineButton.setBackground(ColorDesign);
		lineButton.setToolTipText("���׸���");
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
                        helptext.setText("ĵ����â�� �巡���Ͽ� ���� �׸��ϴ�.");
                    }
                }
                
                
            }
    	);
		ToolBar_Tool_tool.add(lineButton);  
		
		lineButton = new JButton(new ImageIcon("polyline.jpg"));
		lineButton.setBackground(ColorDesign);
		lineButton.setToolTipText("�������α׸���");
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
                        helptext.setText("���콺�� �̵��ϸ� �����ؼ� Ŭ���ϸ� ���������� �׸��ϴ�.");
                    }
                }
                
                
            }
    	);
		ToolBar_Tool_tool.add(lineButton); 
		
		
        ovalButton = new JButton(new ImageIcon("Oval.jpg"));
     	ovalButton.setBackground(ColorDesign);
     	ovalButton.setToolTipText("���׸���");
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
                        helptext.setText("ĵ����â�� �巡���Ͽ� Ÿ���� �׸��ϴ�. (+ shiftŰ - ���׸���)");
                    }
                }
                
                
            }
    	);
     	ToolBar_Tool_tool.add(ovalButton); 
     
     	rectButton = new JButton(new ImageIcon("Rect.jpg"));
     	rectButton.setBackground(ColorDesign);
     	rectButton.setToolTipText("�簢���׸���");
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
                        helptext.setText("ĵ����â�� �巡���Ͽ� �׸� �׸��ϴ�. (+ shiftŰ - ���簢���׸���)");
                    }
                }
                
                
            }
    	);
     	ToolBar_Tool_tool.add(rectButton);
     	
     	selectButton = new JButton(new ImageIcon("Select.jpg"));
		selectButton.setBackground(ColorDesign);
		selectButton.setToolTipText("��ü ����");
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
                        helptext.setText("������ ���ϴ� ���� ���� Ŭ���� �Ͽ� ���� ��� ������ �����մϴ�.(Alt + S)");
                    }
                }
                
                
            }
    	);
    	ToolBar_Tool_tool.add(selectButton);
	 
     	moveButton = new JButton(new ImageIcon("Move.jpg"));
     	moveButton.setBackground(ColorDesign);
     	moveButton.setToolTipText("�̵�");
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
                        helptext.setText("���õ� ������ �̵��մϴ�.(Ctrl + M)");
                    }
                }
            }
    	);
     	ToolBar_Tool_tool.add(moveButton); 
     
     	resizeButton = new JButton(new ImageIcon("Resize.jpg"));
     	resizeButton.setBackground(ColorDesign);
     	resizeButton.setToolTipText("ũ�� ����");
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
                        helptext.setText("���õ� ������ ũ�⸦ �����մϴ�.(Ctrl + R)");
                    }
                }
            }
    	);
     	ToolBar_Tool_tool.add(resizeButton);
     
     	cutButton = new JButton(new ImageIcon("Cut.jpg"));
     	cutButton.setBackground(ColorDesign);
     	cutButton.setToolTipText("�߶󳻱�");
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
                        helptext.setText("���õ� ������ �����, ���Ŀ� �ٿ��ֱ��ϸ� �� ������ ���Դϴ�.(Alt + C)");
                    }
                }
            }
    	);
     	ToolBar_Tool_tool.add(cutButton); 
     
     	copyButton = new JButton(new ImageIcon("Copy.jpg"));
     	copyButton.setBackground(ColorDesign);
     	copyButton.setToolTipText("�����ϱ�");
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
                        helptext.setText("���õ� ������ �ٿ��ֱ��� �� ���Դϴ�.(Ctrl + C)");
                    }
                }
            }
    	);
     	ToolBar_Tool_tool.add(copyButton);
     
	    pasteButton = new JButton(new ImageIcon("Paste.jpg"));
	    pasteButton.setBackground(ColorDesign);
  		pasteButton.setToolTipText("�ٿ��ֱ�");
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
                        helptext.setText("������ �߶󳻰ų� ������ ������ ĵ����â�� �ٿ��ֽ��ϴ�.(Ctrl + V)");
                    }
                }
            }
    	);
     	ToolBar_Tool_tool.add(pasteButton);
     
     	deleteButton = new JButton(new ImageIcon("Delete.jpg"));
     	deleteButton.setBackground(ColorDesign);
     	deleteButton.setToolTipText("����");
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
                        helptext.setText("���õ� ������ �����մϴ�.(Ctrl + D)");
                    }
                }
            }
    	);
     	ToolBar_Tool_tool.add(deleteButton);
     	
     	undoButton = new JButton(new ImageIcon("Undo.jpg"));
     	undoButton.setBackground(ColorDesign);
     	undoButton.setToolTipText("�� �۾�����");
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
                        helptext.setText("���� �ߴ� �۾����� �������� �ǵ����ϴ�.");
                    }
                }
            }
    	);
     	ToolBar_Tool_tool.add(undoButton);
     
     	fillcolorButton = new JButton(new ImageIcon("fillColor.png"));
     	fillcolorButton.setBackground(ColorDesign);
     	fillcolorButton.setToolTipText("��ä���");
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
                        helptext.setText("���� ���� ���õ� ������ ä���ֽ��ϴ�.");
                    }
                }
            }
    	);
     	ToolBar_Tool_tool.add(fillcolorButton);
     	
     	
     	
     	backButton = new JButton(new ImageIcon("back.jpg"));
     	backButton.setBackground(ColorDesign);
     	backButton.setToolTipText("�ڷ� ������");
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
                        helptext.setText("�ٸ� ������ ���� �׷��� �ִ� ������ �ڷ� �����ϴ�.");
                    }
                }
            }
    	);
     	ToolBar_Tool_tool.add(backButton);
     	
     	////////////////addLayerButton
     	addLayerButton = new JButton(new ImageIcon("addLayer.png"));
     	addLayerButton.setBackground(ColorDesign);
     	addLayerButton.setToolTipText("���̾� �߰�");
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
                        helptext.setText("���̾ �߰��մϴ�.");
                    }
                }
            }
    	);
     	ToolBar_Tool_tool.add(addLayerButton);
     	/////////////////deleteLayerButton
     	deleteLayerButton = new JButton(new ImageIcon("delLayer.png"));
     	deleteLayerButton.setBackground(ColorDesign);
     	deleteLayerButton.setToolTipText("���̾� ����");
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
                        helptext.setText("���̾ �����մϴ�.");
                    }
                }
            }
    	);
     	ToolBar_Tool_tool.add(deleteLayerButton);
     	////////////////penToolButton
     	penToolButton = new JButton(new ImageIcon("penTool.png"));
     	penToolButton.setBackground(ColorDesign);
     	penToolButton.setToolTipText("�� ����");
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
                        helptext.setText("������ �׸� �� �ֽ��ϴ�.");
                    }
                }
            }
    	);
     	ToolBar_Tool_tool.add(penToolButton);
     	////////////////textToolButton
     	textToolButton = new JButton(new ImageIcon("textTool.png"));
     	textToolButton.setBackground(ColorDesign);
     	textToolButton.setToolTipText("�ؽ�Ʈ �ڽ�");
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
                        helptext.setText("�ؽ�Ʈ�ڽ��� ����� ���� �� �� �ֽ��ϴ�.");
                    }
                }
            }
    	);
     	ToolBar_Tool_tool.add(textToolButton);
     	///////////////fillBackgroundColorButton
     	fillBackgroundColorButton = new JButton(new ImageIcon("fillBackgroundColor.png"));
     	fillBackgroundColorButton.setBackground(ColorDesign);
     	fillBackgroundColorButton.setToolTipText("���� ä���");
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
                        helptext.setText("������ ä�� �� �ֽ��ϴ�.");
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
     	gradientButton.setToolTipText("�׶���Ʈ ����");
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
                        helptext.setText("���õ� ������ ���ǥ���� �� �� �ִ� �׶���Ʈ ����â�� �ҷ��ɴϴ�.");
                    }
                }
            }
    	);
     	ToolBar_Tool_gradient.add(gradientButton);
     	
     	     		
     	ToolBar_Tool.add(ToolBar_Tool_tool, BorderLayout.CENTER);
     	ToolBar_Tool.add(ToolBar_Tool_gradient, BorderLayout.SOUTH);
     	
   	    //������ �ȷ�Ʈ
   	
   		JPanel ToolBar_Pallet = new JPanel();
     	ToolBar_Pallet.setLayout(new BorderLayout());
     	
     	//�ȷ�Ʈ�� �ٲ��ִ� �г�.���콺�����ʿ� ���콺����͸� ����� Ŭ�������� �ȷ�Ʈ�� �ٲ��ִ� �̺�Ʈó��.
		ToolBar_Pallet_text = new JPanel();
		ToolBar_Pallet_text.setToolTipText("�ٸ� ���� ����");
		ToolBar_Pallet_text.setBorder(BorderFactory.createRaisedBevelBorder());
		JLabel Pallet_text = new JLabel("���� ����");
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
                        helptext.setText("�� �κ��� ������ �ٸ� ������� �� �� �ֽ��ϴ�.");
                    }
                }
                
                
            }
    	);
     	ToolBar_Pallet.add(ToolBar_Pallet_text, BorderLayout.NORTH);
		
		//������ 3���� �ȷ�Ʈ.
		
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
		
		
		
		//������ ù��°�ȷ�Ʈ ����.
						
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
     	
     
     	
     	//������ �ι�° �ȷ�Ʈ ����.
     	
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
     	
     	   	
     	
     	//������ 3��° �ȷ�Ʈ ����.
     	
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
     	
     	
     	
     	
     	//�� �ȷ�Ʈ �гο� �ν��̸��� ���̸� ��ü �ȷ�Ʈ �����гο� ���δ�.
		ToolBar_Pallet_pallet.add(ToolBar_Pallet_pallet1, "pallet1");
		ToolBar_Pallet_pallet.add(ToolBar_Pallet_pallet2, "pallet2");
		ToolBar_Pallet_pallet.add(ToolBar_Pallet_pallet3, "pallet3");
		
		
		pallets.show(ToolBar_Pallet_pallet, "pallet1"); //ó���� 1���ȷ�Ʈ�� �����ش�.
     		
     	
     	ToolBar_Pallet.add(ToolBar_Pallet_pallet, BorderLayout.CENTER);
     	
     
     	
             
    	//������ �÷�GUI
     
     	JPanel ToolBar_Color = new JPanel();
     	ToolBar_Color.setBackground(ColorDesign);
     	ToolBar_Color.setBorder(BorderFactory.createRaisedBevelBorder());
     
     	JPanel nowColorWholePanel = new JPanel();
     	nowColorWholePanel.setBackground(ColorDesign);
       
        //������� �����ִ� �г�. ���콺�����ʿ� ���콺����͸� �̿��ؼ� JColorChooser�� �ҷ��´�.
        //���콺��Ǹ����ʿ� ���콺��Ǿ���͸� �̿��� ���µ���ǥ���ٿ� ������� �����ش�.
     	nowColorPanel = new JPanel();
	 	nowColorPanel.setBorder(BorderFactory.createLoweredBevelBorder());	// �׵θ� ����
     	nowColorPanel.setPreferredSize(new Dimension(25, 25));			// ũ�� ����
   	 	nowColorPanel.setBackground(Color.BLACK);
   	 	nowColorPanel.setToolTipText("���� ���� ����");
   	 	nowColorPanel.addMouseListener            
     	(
            new MouseAdapter()
            {
                public void mouseClicked(MouseEvent event) 
                {
                    if ( ( event.getX() > 0 && event.getX() < 25 ) && ( event.getY() > 0 && event.getY() < 25 ) )
                    {
                        Color color = JColorChooser.showDialog(JavaPaint.this, "���� ���� �ȷ�Ʈ", firstColor);
			
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
                        helptext.setText("�� �κ��� ������ ��� ���� ���� â�� �� �� �ֽ��ϴ�.(Ctrl + U)");
                   	}
                
                }
            }
     	);
       
                  	
  	 
  	 	nowColorWholePanel.add(nowColorPanel);
  	 	 	
  	 
	 	JPanel nowcolortextPanel = new JPanel();
	 	nowcolortextPanel.setBackground(ColorDesign);
	 	JLabel nowcolortext = new JLabel("���� ��");
	 	nowcolortextPanel.add(nowcolortext);
		
		
		 		       
     	ToolBar_Color.setLayout(new BorderLayout());
     	ToolBar_Color.add(nowcolortextPanel, BorderLayout.CENTER);        
     	ToolBar_Color.add(nowColorWholePanel, BorderLayout.NORTH);
     
     
     
     
     
     
     	//���ٿ� ����_��, ��Ÿ_�÷� �г� ��ġ.
     	ToolBar.setLayout(new BorderLayout());
     	ToolBar.add(ToolBar_Pallet, BorderLayout.CENTER);
     	ToolBar.add(ToolBar_Tool, BorderLayout.NORTH);
     	ToolBar.add(ToolBar_Color, BorderLayout.SOUTH);
     
     
     
            
        //���¹�
     
     
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
        JLabel locationtext = new JLabel("���� Ŀ�� ��ġ : ");
        InnerStatusBar.add(locationtext);
        pointlocation = new Pointlocation();
        pointlocation.setBackground(ColorDesign);
        InnerStatusBar.add(locationtext);
        InnerStatusBar.add(pointlocation);
     
        StatusBar.setLayout(new BorderLayout());
        StatusBar.add(HelpBar, BorderLayout.CENTER);
        StatusBar.add(InnerStatusBar, BorderLayout.EAST);
     
     
        //�׸��� �׷����� ����.
        DrawPanel = new CustomPanel();

        DrawPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        DrawPanel.setBackground(Color.WHITE);
     
 	 
		
		
		//������ ����.
		addMouseMotionListener
     	(
            new MouseMotionAdapter()
            {
                public void mouseMoved(MouseEvent event)
                {
                    if ( ( event.getX() > 0 && event.getX() < 640 ) && ( event.getY() > 0 && event.getY() < 50 ) )
                    {
                        helptext.setText("�޴��� �������ּ���");
                   	}
                   	
                    else if ( ( event.getX() > 0 && event.getX() < 75 ) && ( event.getY() > 50 && event.getY() < 80 ) )
                    {
                        helptext.setText("�� ������ ���� �������ּ���");
                   	}
                   	
                   	                   	
                    else if ( ( event.getX() > 0 && event.getX() < 640 ) && ( event.getY() > 465 && event.getY() < 505 ) )
                    {
                        helptext.setText("�̰����� ���� ���¸� �����ݴϴ�.");
                   	}
                   	               
                                        
                    else
                    	helptext.setText(""); 
                    	//�� �κ��� ������ �ٸ� ���� ��ġ���� �� ���µ���ǥ������ �������� ����������.
                
                }
            }
     	);
     
     //-------------------------------------------------------               
    //�гε��� �����ӿ� ���δ�.
    	
     Container container = getContentPane();
     container.setLayout(new BorderLayout());
     container.add(StatusBar, BorderLayout.SOUTH);
     container.add(DrawPanel, BorderLayout.CENTER);
	 container.add(ToolBar, BorderLayout.WEST);
	 
	 
	 //������ Ÿ��Ʋ�� ũ�� ����.
	 setTitle("Java Paint Project");
     setSize(640,505);
	 
	 // ũ�⺯�� �Ұ�
     setResizable(false);
             
     // �������� �ʰ� x��ư�� ������ ��쿡 ���� ó��
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
	
/**��ü ����ȭ�� ��ü�� ������ ���� �Ǵ� ��Ʈ��ũ�� ���Ͽ� ����Ʈ ��Ʈ������ 
* 	�ۼ��� �ϱ� ���Ͽ� ��ü�� ��Ʈ��ȭ �ϴ� ���̴�. �̷��� ��ü�� ����ȭ
*  �����ν� ���� �� �ִ� ������ ��ü ��ü�� ������ ����� ���Ŀ� ���� ���� �ʰ� 
* 	��ü�� ���Ͽ� ���������ν� ���Ӽ��� ������ �� �ְ�, ��ü ��ü�� ��Ʈ��ũ�� ���Ͽ�
*  �ս��� ��ȯ�� �� �ְ� �Ǵ� ���̴�. ��ü ����ȭ�� �ϱ� ���ؼ� ObjectInputStream Ŭ������
*  ObjectOutputStreamŬ���������� �̿��Ѵ�.
*/  	
    //�����Լ�.
	private void Open()
  	{
  		//fileFilter�� ����ϱ� ���� �迭 ����
		String fileFilters [][] = {{".jpg", "JPG"},
                {".jpeg", "JPEG"},
                {".png", "PNG"},
                {".bmp", "BMP"},
                {".jpe", "JPE"},
                {".jfif", "JFIF"}};
  		JFileChooser fileChooser = new JFileChooser();
  		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
  		//filechooser�� ����
  		for(int i = 0; i < fileFilters.length; i++)
  			fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(fileFilters[i][0], fileFilters[i][1]));
  		//FileFilter�� ������ �迭�� �Է�
  		int result = fileChooser.showOpenDialog(this);
  		
  		if(result == JFileChooser.CANCEL_OPTION)
  			return;
  			
  		File file = fileChooser.getSelectedFile();
  		
  		boolean extExist = false;
  		String filename = fileChooser.getSelectedFile().getName();
  		
  		//Ŭ���� ������ Ȯ���ڰ� FileFilter�� ���ٸ� �������� �����̴�.
  		for(int i = 0; i < fileFilters.length; i++)
  			if(filename.endsWith(fileFilters[i][0]))
  			{
  				extExist = true; break;
  			}
  	//����ó��.
  		if(extExist == false)
  		{
  			JOptionPane.showMessageDialog(this, "�� �� ���� �����Դϴ�!",
  					"�� �� ���� �����Դϴ�!",JOptionPane.ERROR_MESSAGE);
  			return;
  		}
  		//���� ���� ����ó��
  		if((file == null) || (file.getName().equals("")))
  		{
  			JOptionPane.showMessageDialog(this, "�����̸��� �Է����� �����̽��ϴ�!",
  					"�����̸��� �Է����� �����̽��ϴ�!",JOptionPane.ERROR_MESSAGE);
  		}

  		else
  		{

  			try
  			{

  				
  				image = ImageIO.read(file);			//�̹����� ����
  				Layer.DrawImage(image);   				//�̹����� ������� ����.
  				repaint();
  				saveFile = file;					//�ҷ��� ������ saveFile�� ����
  				cacheSavedFile = file;
  				fileName = saveFile.getName();
  				setTitle("���� ���ϸ� : " + fileName + " , ��ġ : " + saveFile.getPath());
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
  				//���͸� ��ü����ȭ�� ����ȭ.
  				DrawPanel.paintsave(image);
  				System.out.println(cacheSavedFile.getAbsolutePath());
  				String [] str = cacheSavedFile.getAbsolutePath().split("\\.");
  				String ext = str[1];

  				ImageIO.write(image,ext,cacheSavedFile);
  			}
  			catch (Exception e) { System.out.println(e); }			
  			
  		}
  	}
  	//�����Լ�.
 	private void SaveAs()
  	{
  		//fileFilter ����
 		String fileFilters [][] = {{".jpg", "JPG"},
                {".jpeg", "JPEG"},
                {".png", "PNG"},
                {".bmp", "BMP"},
                {".jpe", "JPE"},
                {".jfif", "JFIF"}};
  		JFileChooser fileChooser = new JFileChooser();
  		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
  		//////////----------------------ADDED BY JW--------------------------------//////////
  		//-������ filter������ ���������� ������ �ȵǴ� ���� �ذ� (�⺻ ����Ʈ�� jpg�� ����)
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
  			JOptionPane.showMessageDialog(this, "�����̸��� �Է����� �����̽��ϴ�!",
  			"�����̸��� �Է����� �����̽��ϴ�!",JOptionPane.ERROR_MESSAGE);
  			
  		}
  		//���� Ȯ���ڰ� �������Ϳ� ���� ���
  		for(int i = 0; i < fileFilters.length; i++)
  			if(filename.endsWith(fileFilters[i][0]))
  			{
  				extExist = true; break;
  			}
  		
  		//File�̸��� Ȯ���ڰ� ���ԵǾ� �ִ� ���
  		if(filename.lastIndexOf('.') > 0 && extExist)
  			file = fileChooser.getSelectedFile();
  		else if (fileChooser.getFileFilter().getDescription().equals("��� ����"))
  			file = new File(fileChooser.getSelectedFile().getPath() +  ".png");//Filter�� ������Ϸ� ������ ��� �⺻������ png�� ����
  		else//File�̸��� Ȯ���ڰ� ���ԵǾ� ���� �ʰ�, Filter�� ���õ� ���� ���, �����̸� �ڿ� Filter Ȯ���ڸ� ������ 
  			file = new File(fileChooser.getSelectedFile().getPath() + fileChooser.getFileFilter().getDescription());
  		
  		//����ó��.
  		if(file.exists()){
  			if (JOptionPane.NO_OPTION == JOptionPane.showConfirmDialog(JavaPaint.this,
  										 file.getPath() + "\n���� �̸��� ������ �̹� �����մϴ�.\n" +
  										 "���� ������ �� ���Ϸ� ��ü�Ͻðڽ��ϱ�?", 
										"�ٸ� �̸����� ����",
										JOptionPane.YES_NO_OPTION, 
										JOptionPane.WARNING_MESSAGE))	 
  				return;		// No�� Ŭ���ϸ� �������� �ʴ´�.
  			
  		}
  		
  		
  		try
		{
			//�̹����� ���� �׸��� ���� �Լ� ȣ��. �̹����� �ٽ� �׸��� ���� ���, �̹����� �׷ȴ� �ܻ��� ����.
  			DrawPanel.paintsave(image);
  			String ext = fileChooser.getFileFilter().getDescription();
  			ext = ext.substring(1);//Image�� �����ϱ� ���� Ȯ���ڸ� ������.
			ImageIO.write(image,ext,file);
		}
		catch (Exception e) { System.out.println(e); }			
			
		if (file != saveFile)
		{
			cacheSavedFile = file;
			saveFile = file;
			fileName = saveFile.getName();
			setTitle("���� ���ϸ� : " + fileName + " , ��ġ : " + saveFile.getPath());
		}
  		  		
  	}
  	
  	//��������� ������ ������ Ȯ�����ִ� �Լ�.
  	public void checkForSave()
	{		
		if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(JavaPaint.this,
										"���� ������ �����Ͻðڽ��ϱ�?",
										"���� �����ϱ�",
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
			Layer.AddSpace();    // ���� �׸��� ������ ��ü ���� 
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
		// ����I/O����.
		else if (action == newItem)
		{
			if (Layer.getDrawing().isEmpty() == false)
			{
				checkForSave();
			}
			Layer.newShape();
			repaint();
			setTitle("�������.cha");
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
		
		//Ư�� ��ư�� ������ ���� ���콺 �۾�ȯ������ ���� ��Ŷ�� �������ش�.
        else if(action == lineButton ||action == polyButton ||action == ovalButton ||action == rectButton ||action == pasteButton)
               DrawPanel.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR)); 

        else if(action == selectButton || action == fillcolorButton)
               DrawPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        else if(action == moveButton|| action==moveItem)
               DrawPanel.setCursor(new Cursor(Cursor.MOVE_CURSOR));
               
        //���ٹ�ư�̺�Ʈó��.

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
			JOptionPane.showMessageDialog(JavaPaint.this, "�׸��� ver 2.0 \n"+
			 	"�й�: 2012106002  ���� : �����\n�й�: 2012122242  ���� : �����\n"+ 
			 	"�й�: 2013122276  ���� : ���α�\n","Java �� ������Ʈ", JOptionPane.INFORMATION_MESSAGE);
		}
		
		// �����
	      // �׼ǿ� ���� �̺�Ʈ ó��
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
		///����end
		//�ȷ�Ʈ�� �̺�Ʈ ó��.(RGB�ڵ带 �̿��� ������ ��쿡 Color�� �������ش�.)
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
			 * ���� ���� �� ä��� 
			 */
			Color color = JColorChooser.showDialog(JavaPaint.this, "���� ���� �ȷ�Ʈ", firstColor);
			
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
	  	
	
	//���� Ŀ�� ��ġ�� �˷��ִ� PointlocationŬ����.
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
	// ���콺 �̺�Ʈ�� �̿��Ͽ� ������ �׸��� �׸��� ĵ���������� �ϴ� DrawPanel.
	private class CustomPanel extends JPanel implements MouseInputListener
	{
 		// ������
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
 		// ���콺�� Ŭ������ �� ������ ����� �����Ѵ�
 		public void mouseClicked(MouseEvent event)
 		{
			//	���콺 ��� ��ư�� ������ ��ư�� �ƴ� ��쿡 ����
 			if (!(event.isMetaDown()) && !(event.isAltDown()))
 			{
			eventhandle.ordering.mouseClickExe(event.getPoint(), Layer); 
   			repaint();
   			}
 			
 		}

 		// ���콺�� ������ �� ������ ����� �����Ѵ�.
 		public void mousePressed(MouseEvent event)
 		{
			if (!(event.isMetaDown()) && !(event.isAltDown()))
			{
			eventhandle.ordering.mousePressExe(event.getPoint(), Layer);
   			repaint();
			}
			
			
 		}
 
		// ���콺�� �巡���� �� ������ ����� �����Ѵ�
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
			
			pointlocation.setPoint(event.getX(), event.getY());		// ���콺�� ��ǥ�� �����ش�
			
 		}   

 		public void mouseReleased(MouseEvent event) 
 		{ 
 			
 			eventhandle.ordering.mouseRelExe(event.getPoint(), Layer);
			repaint();
			pointlocation.setPoint(event.getX(), event.getY());	// ���콺�� ��ǥ�� �����ش�
			
			
 		}
 		public void mouseEntered(MouseEvent event) 
 		{
 			helptext.setText("�̰����� �׸��� �׷��ּ���."); //���콺�� DrawPanel�� ������ ���µ���ǥ������ ����.
 		}
 		public void mouseExited(MouseEvent event)
 		{
 			
 			pointlocation.settext("ĵ���� �ܺ�"); 	// ���콺�� DrawPanel�� ����� ����Ŀ����ġ�� 'ĵ���� �ܺ�'�� ����.
 		} 
 		
 		public void mouseMoved(MouseEvent event) 
 		{
 			
 			pointlocation.setPoint(event.getX(), event.getY());			// ���콺�� ��ǥ�� �����ش�
 		} 
	}
	
	//������ ��ä��⿡�� ���ȿ���� �� �� �ִ� �׶���Ʈ.
	private class Gradient_Frame extends JFrame implements ActionListener
	{
		private JRadioButton Button_1, Button_2, Button_3, Button_4; //������ ������ �� �ְ� �� ������ư.
		private ButtonGroup radioGroup; //������ư�� �����ϴ� ������ư�׷�.
		private JPanel color_1, color_2; //��߻��� �������� ������ color_1�� color_2
		private JButton apply_Button; // �׶���Ʈ ���� ����� ������ �� �ִ� ��ư.
		private int direct; 
		private Color color1, color2;

		public Gradient_Frame()
		{
			Container container = getContentPane();
			container.setLayout(new FlowLayout());
			
			Button_1 = new JRadioButton("�ٹ���",true);
			container.add(Button_1);
			Button_2 = new JRadioButton("�ֹ���",false);
			container.add(Button_2);
			Button_3 = new JRadioButton("�׹���",false);
			container.add(Button_3);
			Button_4 = new JRadioButton("�ع���",false);
			container.add(Button_4);
			
			//�÷� 1���� ����������ֱ�, �������̺�Ʈ�� �߻�		
			color_1 = new JPanel();
			color_1.setBorder(BorderFactory.createLoweredBevelBorder());	// �׵θ� ����
     		color_1.setPreferredSize(new Dimension(25, 20));			// ũ�� ����
   	 		color_1.setBackground(Color.BLACK);
   	 		color_1.setToolTipText("������ ���� ����");
   	 		color_1.addMouseListener            
     		(
            	new MouseAdapter() 
            	{
                	public void mouseClicked(MouseEvent event) 
                	{
                    	if ( ( event.getX() > 0 && event.getX() < 25 ) && ( event.getY() > 0 && event.getY() < 25 ) )
                    	{
                        color1 = JColorChooser.showDialog(Gradient_Frame.this, "color1 ����", firstColor);
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
                        helptext.setText("�� �κ��� ������ �������� ��� ���� ���� â�� �� �� �ֽ��ϴ�.");
                   		}
                
                	}
            	}
     		);
			container.add(color_1);
			
			
			//���� ������ ���������� ������ ������ �ؽ�Ʈ.
			JLabel text1 = new JLabel("-->");
			container.add(text1);
			
			//�÷� 2���� ����������ֱ�, �������̺�Ʈ�� �߻�
			
			color_2 = new JPanel();
			color_2.setBorder(BorderFactory.createLoweredBevelBorder());	// �׵θ� ����
     		color_2.setPreferredSize(new Dimension(25, 20));			// ũ�� ����
   	 		color_2.setBackground(Color.BLACK);
   	 		color_2.setToolTipText("������ ���� ����");
   	 		color_2.addMouseListener            
     		(
            	new MouseAdapter() 
            	{
                	public void mouseClicked(MouseEvent event) 
                	{
                    	if ( ( event.getX() > 0 && event.getX() < 25 ) && ( event.getY() > 0 && event.getY() < 25 ) )
                    	{
                        color2 = JColorChooser.showDialog(Gradient_Frame.this, "color2 ����", firstColor);
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
                        helptext.setText("�� �κ��� ������ �������� ��� ���� ���� â�� �� �� �ֽ��ϴ�.");
                   		}
                
                	}
            	}
     		);
			container.add(color_2);
			
			
			JLabel text2 = new JLabel("   ");
			container.add(text2);
			
			//�׶���Ʈȿ���� �����ϴ� ��ư.
			apply_Button = new JButton(new ImageIcon("apply.jpg"));
			apply_Button.setPreferredSize(new Dimension(80,23));
			apply_Button.setToolTipText("�׶���Ʈ�� ������ �����մϴ�.");
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
						
			setTitle("Gradient ��");
			setSize(280,100);
			setLocation(440,50);
			setVisible(true);
			setResizable(false);
			setBackground(new Color(232,232,233));
		}
		
		public void actionPerformed(ActionEvent ae)
		{
			//���� �̺�Ʈ�� ���������ư�� ������ �ÿ�, ĵ����(DrawPanel)�� repaintó��.					
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