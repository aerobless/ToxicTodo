package ch.theowinter.toxictodo.client.ui.view.utilities;

import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ButtonTextGroup extends JPanel{
	private static final long serialVersionUID = 9182856902191336738L;

	public ButtonTextGroup(FontIconButton button, String description) {
		setLayout(new BorderLayout(0, 0));
		
		add(button, BorderLayout.CENTER);
		setBackground(ToxicColors.TEXT_WHITE);
		JLabel lblNewLabel = new JLabel(description);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblNewLabel, BorderLayout.SOUTH);
	}
	
}
