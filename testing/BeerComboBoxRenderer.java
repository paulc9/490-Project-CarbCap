import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

public class BeerComboBoxRenderer extends BasicComboBoxRenderer{

	public BeerComboBoxRenderer(){
		setOpaque(true);
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
	}

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

		Beer beer = (Beer) value;
		String temp = beer.getType();
		setText(temp);

		return this;
	}
}