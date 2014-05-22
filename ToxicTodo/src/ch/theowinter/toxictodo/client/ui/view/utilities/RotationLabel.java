package ch.theowinter.toxictodo.client.ui.view.utilities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;

public class RotationLabel extends JLabel {
	private static final long serialVersionUID = 3713882659937008293L;
	private double angle = 0;

	/**
	 * Creates a new instance of this class.
	 *
	 */
	public RotationLabel(String text) {
		super(text);
		setBorder( new javax.swing.border.CompoundBorder( new javax.swing.border.LineBorder( Color.red, 0), getBorder() ) );
	}
	
	@Override
	public void paintComponent(Graphics g){
		Graphics2D gx = (Graphics2D) g;
		gx.rotate(angle, getWidth()/2, getHeight()/2);
		super.paintComponent(g);
	}

	public void setRotation(double angle){
		this.angle = angle;
	}
	
	public double getRotation(){
		return this.angle;
	}
}
