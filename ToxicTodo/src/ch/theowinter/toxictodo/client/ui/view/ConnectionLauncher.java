package ch.theowinter.toxictodo.client.ui.view;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import ch.theowinter.toxictodo.client.ui.view.utilities.RotationLabel;
import ch.theowinter.toxictodo.client.ui.view.utilities.ToxicUIData;
import ch.theowinter.toxictodo.sharedobjects.Logger;

public class ConnectionLauncher extends JFrame {
	private static final long serialVersionUID = 7067426305994555043L;
	private JPanel contentPane;
	private RotationLabel lblIcon;

	/**
	 * Create the frame.
	 */
	public ConnectionLauncher() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 311, 120);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblToxictodo = new JLabel("ToxicTodo");
		lblToxictodo.setFont(new Font("Lucida Grande", Font.PLAIN, 33));
		lblToxictodo.setBounds(22, 16, 181, 52);
		contentPane.add(lblToxictodo);
		
		lblIcon = new RotationLabel(String.valueOf('\uf021'));
		lblIcon.setHorizontalAlignment(SwingConstants.CENTER);
		Font ttfReal = ToxicUIData.AWESOME_FONT.deriveFont(Font.BOLD, 40);
		lblIcon.setFont(ttfReal);
		lblIcon.setBounds(215, 16, 72, 72);
		lblIcon.setRotation(0);
		contentPane.add(lblIcon);
		
		JLabel lblTryingToConnect = new JLabel("Connecting to remove server:");
		lblTryingToConnect.setBounds(22, 59, 258, 16);
		contentPane.add(lblTryingToConnect);
	}
	
	public void animate(){
		try {
			if(lblIcon.getRotation()<360){
				lblIcon.setRotation(lblIcon.getRotation()+0.05);
				repaint();
			}else{
				lblIcon.setRotation(0);
				repaint();
			}
			Thread.sleep(10);
		} catch (InterruptedException anEx) {
			Logger.log("InterruptedException while trying to animate sync incon in ConnectionLauncher.", anEx);
		}
	}
}
