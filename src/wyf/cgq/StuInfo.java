package wyf.cgq;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.net.*;
import java.io.*;
import javax.swing.tree.*;
import java.sql.*;
import java.util.Date;

public class StuInfo extends JPanel
{
	private String host;
	//������־ѧ��ѧ�ŵı���
	private String stu_id;
	//������ʾ��ǩ����
	private JLabel[] jlArray={new JLabel("ѧ    ��"),new JLabel("��    ��"),
	                          new JLabel("��    ��"),new JLabel("��������"),
	                          new JLabel("��    ��"),new JLabel("ѧ    Ժ"),
	                          new JLabel("ר    ҵ"),new JLabel("��    ��"),
	                          new JLabel("��ѧʱ��"), new JLabel("��"),
	                          new JLabel("��"),new JLabel("��"),new JLabel("��"),
	                          new JLabel("��"),new JLabel("��")
	};
	//����������ʾ��Ϣ�ı�ǩ����
	private JLabel[] jlArray2=new JLabel[13];
	//����GetStuInfo����
	private GetStuInfo getsi;
	//������
	public StuInfo(String stu_id,String host)
	{
		this.host=host;
		this.stu_id=stu_id;
		//����GetStuInfo����
		getsi=new GetStuInfo(host);
		//��ʼ������
		this.initialFrame();
	}
	//��ʼ������ķ���
	public void initialFrame()
	{
		//����GetStuInfo�ķ������ָ��ѧ���Ļ�����Ϣ
		String[] baseinfo=getsi.getBaseInfo(stu_id);
		//���ݻ�����Ϣ������ʾ���ݵı�ǩ
		for(int i=0;i<13;i++)
		{
			jlArray2[i]=new JLabel(baseinfo[i]);
		}
		//���������Ϊ�ղ���
		this.setLayout(null);
		//������ʾ��ǩ����Ϣ��ǩ��ӵ���������ʾ
		jlArray[0].setBounds(30,50,100,30);
		this.add(jlArray[0]);
		jlArray[1].setBounds(30,100,100,30);
		this.add(jlArray[1]);
		jlArray[2].setBounds(30,150,100,30);
		this.add(jlArray[2]);
		jlArray[3].setBounds(30,200,100,30);
		this.add(jlArray[3]);
		jlArray[4].setBounds(30,250,100,30);
		this.add(jlArray[4]);
		jlArray[5].setBounds(30,300,100,30);
		this.add(jlArray[5]);
		jlArray[6].setBounds(30,350,100,30);
		this.add(jlArray[6]);
		jlArray[7].setBounds(30,400,100,30);
		this.add(jlArray[7]);
		jlArray[8].setBounds(30,450,100,30);
		this.add(jlArray[8]);
		jlArray2[0].setBounds(130,50,150,30);
		this.add(jlArray2[0]);
		jlArray2[1].setBounds(130,100,150,30);
		this.add(jlArray2[1]);
		jlArray2[6].setBounds(130,250,500,30);
		this.add(jlArray2[6]);
		jlArray2[2].setBounds(130,150,60,30);
		this.add(jlArray2[2]);
		jlArray2[3].setBounds(130,200,55,30);
		this.add(jlArray2[3]);
		jlArray[9].setBounds(185,200,20,30);
		this.add(jlArray[9]);
		jlArray2[4].setBounds(205,200,45,30);
		this.add(jlArray2[4]);
		jlArray[10].setBounds(250,200,20,30);
		this.add(jlArray[10]);
		jlArray2[5].setBounds(270,200,45,30);
		this.add(jlArray2[5]);
		jlArray[11].setBounds(315,200,20,30);
		this.add(jlArray[11]);
		jlArray2[7].setBounds(130,300,200,30);
		this.add(jlArray2[7]);
		jlArray2[8].setBounds(130,350,200,30);
		this.add(jlArray2[8]);
		jlArray2[9].setBounds(130,400,100,30);
		this.add(jlArray2[9]);
		jlArray2[10].setBounds(130,450,55,30);
		this.add(jlArray2[10]);
		jlArray[12].setBounds(185,450,20,30);
		this.add(jlArray[12]);
		jlArray2[11].setBounds(205,450,45,30);
		this.add(jlArray2[11]);
		jlArray[13].setBounds(250,450,20,30);
		this.add(jlArray[13]);
		jlArray2[12].setBounds(270,450,45,30);
		this.add(jlArray2[12]);
		jlArray[14].setBounds(315,450,20,30);
		this.add(jlArray[14]);
	}
}