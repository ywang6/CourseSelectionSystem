package wyf.cgq;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
public class Welcome extends JLabel {
	String str;//Ҫ��ʾ���ַ���������
	static Icon icon=new ImageIcon("xs.jpg");//Ҫ��ʾ��ͼ��
	public Welcome(String str){
		this.str=str;
		this.initialFrame();
	}
	public void initialFrame(){
		this.setIcon(icon);//����Icon
 		this.setHorizontalAlignment(JLabel.CENTER);//����ˮƽλ��
 		this.setVerticalAlignment(JLabel.CENTER);//���ô�ֱλ��
	}
}