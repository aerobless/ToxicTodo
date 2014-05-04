package ch.theowinter.toxictodo.client.ui.view.utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

public class PanelHeaderWhite extends JPanel {
	private static final long serialVersionUID = 1231624288433035648L;
	private JLabel lblTitel;
	private JLabel lblSubtitel;
	private JLabel lblIcon;
	private JPanel iconPanel;
	private JPanel titelPanel;
	private JPanel subtitelPanel;
	private JPanel topSpace;
	private JPanel bottomSpace;
	private Color background = ToxicColors.dirtyWhite;
	private JPanel westSpace;
	private JPanel eastSpace;

	public PanelHeaderWhite() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel textPanel = new JPanel();
		add(textPanel, BorderLayout.CENTER);

		//Styling:
		setBackground(background);
		textPanel.setBackground(background);
		textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
		
		topSpace = new JPanel();
		FlowLayout fl_topSpace = (FlowLayout) topSpace.getLayout();
		fl_topSpace.setVgap(7);
		textPanel.add(topSpace);
		topSpace.setBackground(background);
		
		titelPanel = new JPanel();
		FlowLayout fl_titelPanel = (FlowLayout) titelPanel.getLayout();
		fl_titelPanel.setVgap(0);
		fl_titelPanel.setAlignment(FlowLayout.LEFT);
		textPanel.add(titelPanel);
		titelPanel.setBackground(background);
		
		lblTitel = new JLabel("titel");
		titelPanel.add(lblTitel);
		lblTitel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		lblTitel.setForeground(ToxicColors.textGrey);
		
		subtitelPanel = new JPanel();
		FlowLayout fl_subtitelPanel = (FlowLayout) subtitelPanel.getLayout();
		fl_subtitelPanel.setVgap(0);
		fl_subtitelPanel.setAlignment(FlowLayout.LEFT);
		textPanel.add(subtitelPanel);
		subtitelPanel.setBackground(background);
		
		lblSubtitel = new JLabel("subtitel");
		subtitelPanel.add(lblSubtitel);
		lblSubtitel.setForeground(ToxicColors.textGreySoft);
		
		bottomSpace = new JPanel();
		FlowLayout fl_bottomSpace = (FlowLayout) bottomSpace.getLayout();
		fl_bottomSpace.setVgap(7);
		textPanel.add(bottomSpace);
		setBorder(new MatteBorder(0,0,1,0,ToxicColors.textGreySoft));
		bottomSpace.setBackground(background);
		
		iconPanel = new JPanel();
		add(iconPanel, BorderLayout.WEST);
		iconPanel.setBackground(background);
		
		lblIcon = new JLabel(String.valueOf('\uf15b'));
		Font ttfReal = null;
		try {
	        InputStream in = this.getClass().getResourceAsStream("/resources/fontawesome-webfont.ttf");
			Font ttfBase = Font.createFont(Font.TRUETYPE_FONT, in);
		    ttfReal = ttfBase.deriveFont(Font.BOLD, 40);
		} catch (FontFormatException | IOException e) {
			System.out.println("font IO exception");
			e.printStackTrace();
		}
		iconPanel.setLayout(new BorderLayout(0, 0));
		lblIcon.setFont(ttfReal);
		iconPanel.add(lblIcon);
		
		westSpace = new JPanel();
		westSpace.setBackground(background);
		iconPanel.add(westSpace, BorderLayout.WEST);
		
		eastSpace = new JPanel();
		eastSpace.setBackground(background);
		iconPanel.add(eastSpace, BorderLayout.EAST);
	}
	
	public void setTitel(String titel){
		lblTitel.setText(titel);
	}
	
	public void setSubTitel(String subtitel){
		lblSubtitel.setText(subtitel);
	}
	
	public void setIcon(char icon){
		lblIcon.setText(String.valueOf(icon));
	}
}
