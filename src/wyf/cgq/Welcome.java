package wyf.cgq;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
public class Welcome extends JLabel {
	String str;//要显示的字符串的引用
	static Icon icon=new ImageIcon("xs.jpg");//要显示的图标
	public Welcome(String str){
		this.str=str;
		this.initialFrame();
	}
	public void initialFrame(){
		this.setIcon(icon);//设置Icon
 		this.setHorizontalAlignment(JLabel.CENTER);//设置水平位置
 		this.setVerticalAlignment(JLabel.CENTER);//设置垂直位置
	}
}