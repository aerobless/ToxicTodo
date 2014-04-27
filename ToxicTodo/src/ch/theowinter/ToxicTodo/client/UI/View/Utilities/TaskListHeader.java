package ch.theowinter.ToxicTodo.client.UI.View.Utilities;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;

public class TaskListHeader extends JPanel {
	private static final long serialVersionUID = 1231624288433035648L;

	public TaskListHeader() {
		setLayout(new BorderLayout(0, 0));
		
		JLabel lblIcon = new JLabel("ICON");
		add(lblIcon, BorderLayout.WEST);
		
		JPanel textPanel = new JPanel();
		add(textPanel, BorderLayout.CENTER);
		textPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblTitel = new JLabel("titel");
		textPanel.add(lblTitel, BorderLayout.NORTH);
		
		JLabel lblSubtitel = new JLabel("subtitel");
		textPanel.add(lblSubtitel, BorderLayout.CENTER);

	}
}
