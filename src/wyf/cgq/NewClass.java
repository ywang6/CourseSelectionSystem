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
import javax.sql.*;
public class NewClass extends JPanel implements ActionListener
{	
	private String host;
	//����Connection���á�Statement������������������
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	//������־ѧԺ�ŵ�����
	private String coll_id;
	//�������רҵ���Ƶ�Vector����
	private Vector v_dept=new Vector();
	//������ʾ��ǩ����
	JLabel[] jlArray={new JLabel("ר   ҵ"),new JLabel("��   ��"),
						new JLabel("��   ��")
	};
	//����JComboBox����
	JComboBox jcb;
	//����������д�����������ı���
	JTextField jtf1=new JTextField();
	JTextField jtf2=new JTextField();
	//����������ť
	JButton jb1=new JButton("��  ��");
	JButton jb2=new JButton("��  ��");
	//������
	public NewClass(String coll_id,String host)
	{
		this.host=host;
		this.coll_id=coll_id;
		//��ʼ������
		this.initialData();
		//��ʼ������
		this.initialFrame();
		//Ϊ�ؼ�ע�������
		this.addListener();
	}
	//��ʼ������	
	public void initialData()
	{
		try
		{//����ѧԺ�Ż�ø�ѧԺ��רҵ�����б�
			this.initialConnection();
			String sql="select dept_name from dept where coll_id='"+coll_id+"'";
			rs=stmt.executeQuery(sql);
			while(rs.next())
			{
				String dept_name=new String(rs.getString(1).getBytes("ISO-8859-1"));
				v_dept.add(dept_name);
			}
			this.closeConn();
			//����JComboBox
			jcb=new JComboBox(v_dept);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	//��ʼ���������
	public void initialFrame()
	{//���ؼ���ӵ�������
		this.setLayout(null);
		jlArray[0].setBounds(30,60,80,30);
		this.add(jlArray[0]);
		jcb.setBounds(120,60,200,30);
		this.add(jcb);
		jlArray[1].setBounds(30,110,80,30);
		this.add(jlArray[1]);
		jtf1.setBounds(120,110,150,30);
		this.add(jtf1);
		jlArray[2].setBounds(30,160,80,30);
		this.add(jlArray[2]);
		jtf2.setBounds(120,160,150,30);
		this.add(jtf2);
		jb1.setBounds(50,210,80,30);
		this.add(jb1);
		jb2.setBounds(160,210,80,30);
		this.add(jb2);
	}
	public void addListener()
	{//Ϊ�ؼ�ע�������
	    jcb.addActionListener(this);
	    jtf1.addActionListener(this);
	    jtf2.addActionListener(this);
		jb1.addActionListener(this);
		jb2.addActionListener(this);
	}
	//ʵ��ActionListener�ӿ��еķ���
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==jcb)
		{
			jtf1.requestFocus(true);
		}
		else if(e.getSource()==jtf1)
		{
			jtf2.requestFocus(true);
		}
		else if(e.getSource()==jtf2)
		{
			jb1.requestFocus(true);
		}
		else if(e.getSource()==jb1)
		{//����"�ύ"��ť�Ĵ������
		    //�����ѡ�����רҵ��
			String dept_name=(String)jcb.getSelectedItem();
			//��ð��
			String class_id=jtf1.getText().trim();
			//�жϰ���Ƿ���ȷ������ʽ�ַ���
			String patternStr="[0-9]{6}";
			if(class_id.equals(""))
			{//���Ϊ��
				JOptionPane.showMessageDialog(this,"��������","����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(!class_id.matches(patternStr))
			{//��Ų����ϸ�ʽ
				JOptionPane.showMessageDialog(this,"��ű�������λ����","����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			//��ð���
			String class_name=jtf2.getText().trim();
			if(class_name.equals(""))
			{//����Ϊ��
				JOptionPane.showMessageDialog(this,"���������","����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(class_name.length()>3)
			{//����������������
				JOptionPane.showMessageDialog(this,"�������ܳ�����������","����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try
			{
				//��ѯ���ݿ��жϸñ���Ƿ��Ѿ�����
				this.initialConnection();
				String sql="select * from class where class_id='"+class_id+"'";
				rs=stmt.executeQuery(sql);
				if(rs.next())
				{//���ڣ�����
					JOptionPane.showMessageDialog(this,"�ñ�ŵİ��Ѿ�����","����",JOptionPane.ERROR_MESSAGE);
					this.closeConn();
					return;
				}
				else
				{//�����ڣ����¼�¼�������
					String sql1="insert into class values('"+class_id+"',(select dept_id from dept where "+
					 "dept_name='"+new String(dept_name.getBytes(),"ISO-8859-1")+"' and coll_id='"+coll_id+"'),'"+
					  coll_id+"','"+new String(class_name.getBytes(),"ISO-8859-1")+"')";
					 int i=stmt.executeUpdate(sql1); 
					 if(i==1)
					 {//����ɹ�
					 	JOptionPane.showMessageDialog(this,"��ӳɹ�","��ʾ",JOptionPane.INFORMATION_MESSAGE);
					 }
					 else
					 {//����ʧ��
					 	JOptionPane.showMessageDialog(this,"���ʧ�ܣ�����","����",JOptionPane.ERROR_MESSAGE);
					 }
				}
				//�ر����ݿ�����
				this.closeConn();
			}
			catch(SQLException ea)
			{
				ea.printStackTrace();
			}
			catch(UnsupportedEncodingException eb)
			{
				eb.printStackTrace();
			}
		}
		else if(e.getSource()==jb2)
		{//�������ð�ť
		    //�������������
			this.jtf1.setText("");
			this.jtf2.setText("");
		}
	}
	public void setFocus()
	{
		this.jcb.requestFocus(true);
	}
	//��ʼ�����ݿ����ӵķ���
	public void  initialConnection()
	{
		try
		{
			Class.forName("org.gjt.mm.mysql.Driver");
			conn=DriverManager.getConnection("jdbc:mysql://"+host+"/test","root","");
			stmt=conn.createStatement();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	//�ر����ݿ����ӵķ���
	public void closeConn()
	{
		try
		{
			if(rs!=null)
			{
				rs.close();
			}
			if(stmt!=null)
			{
				stmt.close();
			}
			if(conn!=null)
			{
				conn.close();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
}