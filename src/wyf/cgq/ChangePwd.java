package wyf.cgq;
import java.util.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
public class ChangePwd extends JPanel implements ActionListener
{
	private String host; 
  //声明Connection引用、Statement对象引用与结果集引用
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	//声明用与表示当前学生学号的引用
	private String stu_id;
	//创建信息提示标签数组
	private JLabel[] jlArray={new JLabel("原始密码"),new JLabel("新密码"),new JLabel("确认新密码"),
	                         };
	//创建密码框数组
	private JPasswordField[] jpfArray={new JPasswordField(),new JPasswordField(),new JPasswordField()
	                             };
	//创建操作按钮数组
	private JButton[] jbArray={new JButton("确认"),new JButton("重置")
	                          };
	//构造器
	public ChangePwd(String stu_id,String host)
	{
		this.host=host;
		this.stu_id=stu_id;
		//初始化页面
		this.initialFrame();
		//注册监听器
		this.addListener();
	}
	//集体注册监听器的方法
	public void addListener()
	{
		jpfArray[0].addActionListener(this);
		jpfArray[1].addActionListener(this);
		jpfArray[2].addActionListener(this);
		jbArray[0].addActionListener(this);
		jbArray[1].addActionListener(this);
	}
	//初始化页面的方法
	public void initialFrame()
	{
		this.setLayout(null);
		for(int i=0;i<jlArray.length;i++)
		{
			jlArray[i].setBounds(30,20+50*i,150,30);
			this.add(jlArray[i]);
			jpfArray[i].setBounds(130,20+50*i,150,30);
			this.add(jpfArray[i]);
		}
		jbArray[0].setBounds(40,180,100,30);
		this.add(jbArray[0]);
		jbArray[1].setBounds(170,180,100,30);
		this.add(jbArray[1]);
	}
	//实现ActionListener接口中的方法
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==jpfArray[0])
		{
			jpfArray[1].requestFocus(true);
		}
		else if(e.getSource()==jpfArray[1])
		{
			jpfArray[2].requestFocus(true);
		}
		else if(e.getSource()==jpfArray[2])
		{
			jbArray[0].requestFocus(true);
		}
		else if(e.getSource()==jbArray[1])
		{//按下重置按钮的处理代码
		    //将输入信息清空
			for(int i=0;i<jpfArray.length;i++)
			{
				jpfArray[i].setText("");
			}
		}
		else if(e.getSource()==jbArray[0])
		{//按下确认按钮的处理代码
		    //用于判断密码格式的正则式字符串
			String patternStr="[0-9a-zA-Z]{6,12}";
			//获取用户输入的旧密码
			String oldPwd=jpfArray[0].getText();
			if(oldPwd.equals(""))
			{//旧密码空
				JOptionPane.showMessageDialog(this,"请输入原始密码","错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			//获取新密码
			String newPwd=jpfArray[1].getText();
			if(newPwd.equals(""))
			{//新密码为空
				JOptionPane.showMessageDialog(this,"请输入新密码","错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(!newPwd.matches(patternStr))
			{//新密码格式不正确
				JOptionPane.showMessageDialog(this,"密码只能是6到12位的字母或数字","错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			//获取确认密码
			String newPwd1=jpfArray[2].getText();
			if(!newPwd.equals(newPwd1))
			{//新密码与确认密码不同
				JOptionPane.showMessageDialog(this,"确认密码与新密码不符","错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try
			{   //初始化数据库连接并更改密码
				this.initialConnection();
				String sql="update user_stu set pwd='"+newPwd+"' where stu_id='"+stu_id+"'"+
				           " and pwd='"+oldPwd+"'";
				int i=stmt.executeUpdate(sql);
				if(i==0)
				{//更改失败提示信息
					JOptionPane.showMessageDialog(this,"修改失败，请检查您的密码是否正确","错误",JOptionPane.ERROR_MESSAGE);
				}
				else if(i==1)
				{//更改成功提示信息
					JOptionPane.showMessageDialog(this,"密码修改成功","提示",JOptionPane.INFORMATION_MESSAGE);
				}
				//关闭连接
				this.closeConn();
			}
			catch(Exception ea)
			{
				ea.printStackTrace();
			}
		}
	}
	public void setFocus()
	{
		jpfArray[0].requestFocus(true);
	}
	//自定义的初始化数据库连接的方法
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
	//关闭连接的方法
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