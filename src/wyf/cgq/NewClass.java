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
	//声明Connection引用、Statement对象引用与结果集引用
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	//声明标志学院号的引用
	private String coll_id;
	//创建存放专业名称的Vector对象
	private Vector v_dept=new Vector();
	//创建提示标签数组
	JLabel[] jlArray={new JLabel("专   业"),new JLabel("班   号"),
						new JLabel("班   名")
	};
	//声明JComboBox引用
	JComboBox jcb;
	//创建用于填写班号与班名的文本框
	JTextField jtf1=new JTextField();
	JTextField jtf2=new JTextField();
	//创建动作按钮
	JButton jb1=new JButton("提  交");
	JButton jb2=new JButton("重  置");
	//构造器
	public NewClass(String coll_id,String host)
	{
		this.host=host;
		this.coll_id=coll_id;
		//初始化数据
		this.initialData();
		//初始化窗体
		this.initialFrame();
		//为控件注册监听器
		this.addListener();
	}
	//初始化数据	
	public void initialData()
	{
		try
		{//根据学院号获得该学院的专业名称列表
			this.initialConnection();
			String sql="select dept_name from dept where coll_id='"+coll_id+"'";
			rs=stmt.executeQuery(sql);
			while(rs.next())
			{
				String dept_name=new String(rs.getString(1).getBytes("ISO-8859-1"));
				v_dept.add(dept_name);
			}
			this.closeConn();
			//创建JComboBox
			jcb=new JComboBox(v_dept);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	//初始化窗体界面
	public void initialFrame()
	{//将控件添加到容器中
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
	{//为控件注册监听器
	    jcb.addActionListener(this);
	    jtf1.addActionListener(this);
	    jtf2.addActionListener(this);
		jb1.addActionListener(this);
		jb2.addActionListener(this);
	}
	//实现ActionListener接口中的方法
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
		{//按下"提交"按钮的处理代码
		    //获得所选择责的专业号
			String dept_name=(String)jcb.getSelectedItem();
			//获得班号
			String class_id=jtf1.getText().trim();
			//判断班号是否正确的正则式字符串
			String patternStr="[0-9]{6}";
			if(class_id.equals(""))
			{//班号为空
				JOptionPane.showMessageDialog(this,"请输入班号","错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(!class_id.matches(patternStr))
			{//班号不符合格式
				JOptionPane.showMessageDialog(this,"班号必须是六位数字","错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			//获得班名
			String class_name=jtf2.getText().trim();
			if(class_name.equals(""))
			{//班名为空
				JOptionPane.showMessageDialog(this,"请输入班名","错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(class_name.length()>3)
			{//班名超过三个汉字
				JOptionPane.showMessageDialog(this,"班名不能超过三个汉字","错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try
			{
				//查询数据库判断该编号是否已经存在
				this.initialConnection();
				String sql="select * from class where class_id='"+class_id+"'";
				rs=stmt.executeQuery(sql);
				if(rs.next())
				{//存在，返回
					JOptionPane.showMessageDialog(this,"该编号的班已经存在","错误",JOptionPane.ERROR_MESSAGE);
					this.closeConn();
					return;
				}
				else
				{//不存在，将新记录插入表中
					String sql1="insert into class values('"+class_id+"',(select dept_id from dept where "+
					 "dept_name='"+new String(dept_name.getBytes(),"ISO-8859-1")+"' and coll_id='"+coll_id+"'),'"+
					  coll_id+"','"+new String(class_name.getBytes(),"ISO-8859-1")+"')";
					 int i=stmt.executeUpdate(sql1); 
					 if(i==1)
					 {//插入成功
					 	JOptionPane.showMessageDialog(this,"添加成功","提示",JOptionPane.INFORMATION_MESSAGE);
					 }
					 else
					 {//插入失败
					 	JOptionPane.showMessageDialog(this,"添加失败！！！","错误",JOptionPane.ERROR_MESSAGE);
					 }
				}
				//关闭数据库连接
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
		{//按下重置按钮
		    //将输入数据清空
			this.jtf1.setText("");
			this.jtf2.setText("");
		}
	}
	public void setFocus()
	{
		this.jcb.requestFocus(true);
	}
	//初始化数据库连接的方法
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
	//关闭数据库连接的方法
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