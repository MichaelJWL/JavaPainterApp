package src.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import src.Drawing;
import src.JavaPaint;
import src.Shape;
import src.ShapeOval;
import src.ShapeRect;

public class TransparentChooser extends JFrame{
	private Double current_transparent;
	private JavaPaint parent;
	private JSpinner m_numberSpinner;
	private Shape shape;
	public TransparentChooser(JavaPaint parent) {
		// TODO Auto-generated constructor stub
		this.parent = parent;
		JPanel wrapPanel = new JPanel(new BorderLayout());//wrapper panel
		
		JPanel n_panel = new JPanel(new FlowLayout());

		JLabel label  = new JLabel("����: ");
		n_panel.add(label);
		
		/**
		 * 0.0 ~ 1.0 ������ ���� ������ ���� spinner ���
		 */
	    SpinnerNumberModel m_numberSpinnerModel;
	    current_transparent = new Double(1.00);
	    Double min = new Double(0.00);
	    Double max = new Double(1.00);
	    Double step = new Double(0.01);
	    m_numberSpinnerModel = new SpinnerNumberModel(current_transparent, min, max, step);
	    m_numberSpinner = new JSpinner(m_numberSpinnerModel);
	    Component mySpinnerEditor = m_numberSpinner.getEditor();
	    JFormattedTextField jftf = ((JSpinner.DefaultEditor) mySpinnerEditor).getTextField();
	    jftf.setColumns(5);

	    n_panel.add(m_numberSpinner);
	    
	    wrapPanel.add(n_panel,BorderLayout.NORTH);
	    
	    
	    JPanel s_panel = new JPanel(new FlowLayout());
	    JButton ok = new JButton("Ȯ��");
	    ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				current_transparent = (double) m_numberSpinner.getValue();

				Color current_color = shape.getColor();
				Color new_color = new Color(current_color.getRed(), current_color.getGreen(), current_color.getBlue(),(int)(current_transparent*255));

				shape.setColor(new_color);
				if(shape instanceof ShapeRect){
					((ShapeRect) shape).BackGroundFill=true;
				}else if(shape instanceof ShapeOval){
					((ShapeOval) shape).BackGroundFill=true;
				}
				setVisible(false);
			}

	    });
	    
	    JButton cancel = new JButton("���");
	    cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setVisible(false);
			}
		});
	    
	    s_panel.add(ok);s_panel.add(cancel);
	    wrapPanel.add(s_panel,BorderLayout.SOUTH);
	    
	    setSize(300, 100);
	    add(wrapPanel);
	    
	}
	public void setInit(){
		Drawing selectedLayer = parent.getLayer();
		try{
			/**
			 * â������ ���õ� ��ü�� ������ �⺻���� �����ǰԲ� �Ͽ���.
			 */
			shape = (Shape) selectedLayer.getDrawing().get(selectedLayer.getEditIndex());
			if(!shape.getSelect()){
				
				throw new Exception();
			}else{
				m_numberSpinner.setValue(shape.getColor().getAlpha()/255.0);;
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			/**
			 * ��ü ������ ���� �ʾ��� ��� 
			 * ���â
			 */
			JOptionPane.showMessageDialog(null, "��ü�� �������ּ���.", "Warning",JOptionPane.WARNING_MESSAGE);
			m_numberSpinner.setValue(1.0);
			setVisible(false);
		}
		
		
	}
	
	public Double getCurrent_transparent() {
		return current_transparent;
	}
	
}
