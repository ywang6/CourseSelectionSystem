package wyf.cgq;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.net.*;
import java.io.*;
import java.sql.*;
import javax.sql.*;
public class Login extends JFrame implements ActionListener
{
	private String host;
	//声明Connection引用、Statement对象引用与结果集引用
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private JPanel jp=new JPanel();//创建用来存放空间的容器
	private JLabel jl=new JLabel("端     口     号");//创建提示标签
	private JLabel jl0=new JLabel("数据库服务器IP");
	private JLabel jl1=new JLabel("用    户    名");
	private JLabel jl2=new JLabel("密            码");
	private JLabel jl3=new JLabel("");//正在登陆提示标签
	//创建主机地址、端口号、用户名和密码输入框
	private JTextField hostport=new JTextField();
	private JTextField hostaddress=new JTextField();
	private JTextField jtf=new JTextField();
	private JPasswordField jpwf=new JPasswordField();
	private JRadioButton[] jrbArray=//创建单选按钮数组
	        {
	        	new JRadioButton("普通学生",true),
	        	new JRadioButton("管理人员")
	        };
	//创建组
	private ButtonGroup bg=new ButtonGroup();
	//创建操作按钮
	private JButton jb1=new JButton("登    陆");
	private JButton jb2=new JButton("重    置");
	//构造器
	public Login()
	{ 
	    this.addListener();
		initialFrame();//初始化界面
	}
	public void addListener(){
		this.jb1.addActionListener(this);//为登陆按钮注册监听器
		this.jb2.addActionListener(this);//为重置按钮注册监听器
		this.jtf.addActionListener(this);//为用户名文本框注册监听器
		this.jpwf.addActionListener(this);//为用户名密码框注册监听器
		this.hostaddress.addActionListener(this);//为主机地址文本框注册监听器
		this.hostport.addActionListener(this);//为端口号文本框注册监听器
	}
	public void initialFrame()
	{
		//设为空布局
		jp.setLayout(null);
		//将控件添加到容器相应位置
		this.jl0.setBounds(30,20,110,25);
		this.add(jl0);
		this.hostaddress.setBounds(120,20,130,25);
		this.add(hostaddress);
		this.jl.setBounds(30,60,110,25);
		this.add(jl);
		this.hostport.setBounds(120,60,130,25);
		this.add(hostport);
		
		
		this.jl1.setBounds(30,100,110,25);
		this.jp.add(jl1);
		this.jtf.setBounds(120,100,130,25);
		this.jp.add(jtf);
		this.jl2.setBounds(30,140,110,25);
		this.jp.add(jl2);
		this.jpwf.setBounds(120,140,130,25);
		this.jpwf.setEchoChar('*');
		this.jp.add(jpwf);
		this.bg.add(jrbArray[0]);
		this.bg.add(jrbArray[1]);
		this.jrbArray[0].setBounds(40,180,100,25);
		this.jp.add(jrbArray[0]);
		this.jrbArray[1].setBounds(145,180,100,25);
		this.jp.add(jrbArray[1]);
		this.jb1.setBounds(35,210,100,30);
		this.jp.add(jb1);
		this.jb2.setBounds(150,210,100,30);
		this.jp.add(jb2);
		this.jl3.setBounds(40,250,150,25);
		this.jp.add(jl3);
		this.add(jp);
		//设置窗口的标题、大小、位置以及可见性
		this.setTitle("登陆");
		Image image=new ImageIcon("ico.gif").getImage();  
 		this.setIconImage(image);
		this.setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX=screenSize.width/2;
		int centerY=screenSize.height/2;
		int w=300;//本窗体宽度
		int h=320;//本窗体高度
		this.setBounds(centerX-w/2,centerY-h/2-100,w,h);//设置窗体出现在屏幕中央
		this.setVisible(true);
		//将填写姓名的文本框设为默认焦点
		this.hostaddress.requestFocus(true);
		this.hostaddress.setText("127.0.0.1");
		this.hostport.setText("3306");
	}
	//实现ActionListener接口中的方法
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==this.jb1)
		{//按下登陆按钮
			this.jl3.setText("正 在 验 证 ， 请 稍 候. . . . .");//设置提示信息
			//获取用户输入的主机地址、端口号、用户名与密码
			String hostadd=this.hostaddress.getText().trim();
			if(hostadd.equals("")){
				JOptionPane.showMessageDialog(this,"请输入主机地址","错误",
				                                  JOptionPane.ERROR_MESSAGE);
				jl3.setText("");return;
			}
			String port=this.hostport.getText();
			if(port.equals("")){
				JOptionPane.showMessageDialog(this,"请输入端口号","错误",
				                                  JOptionPane.ERROR_MESSAGE);
				jl3.setText("");return;
			}
			this.host=hostadd+":"+port;
			String name=this.jtf.getText().trim();
			if(name.equals("")){
				JOptionPane.showMessageDialog(this,"请输入用户名","错误",
				                               JOptionPane.ERROR_MESSAGE);
				jl3.setText("");return;
			}
			String pwd=this.jpwf.getText().trim();
			if(pwd.equals("")){
				JOptionPane.showMessageDialog(this,"请输入密码","错误",
				                           JOptionPane.ERROR_MESSAGE);
				jl3.setText("");return;
			}
			int type=this.jrbArray[0].isSelected()?0:1;//获取登陆类型
			try{   //初始化连接
	            this.initialConnection();
				if(type==0){//普通学生登陆
				    //创建sql语句并查询
					String sql="select * from user_stu where "+
					"stu_id='"+name+"' and pwd='"+pwd+"'";
					rs=stmt.executeQuery(sql);
					if(rs.next()){
						new StuClient(name,host);//创建学生客户短窗口
						this.dispose();//关闭登陆窗口并释放资源
					}
					else{//弹出错误提示窗口
						JOptionPane.showMessageDialog(this,"用户名或密码错误","错误",
						                           JOptionPane.ERROR_MESSAGE);
						jl3.setText("");
					}
					this.closeConn();//关闭连接，语句及结果集	
				}
				else{//教师登陆
				    //创建sql语句并查询
					String sql="select coll_id from user_teacher where "+
					             "uid='"+name+"' and pwd='"+pwd+"'";
					rs=stmt.executeQuery(sql);
					if(rs.next()){
						String coll_id=rs.getString(1);
						new TeacherClient(coll_id,host);//创建教师客户端窗口
						this.dispose();//关闭登陆窗口并释放资源
					}
					else{//弹出错误提示窗口
						JOptionPane.showMessageDialog(this,"用户名或密码错误","错误",
						                           JOptionPane.ERROR_MESSAGE);
						jl3.setText("");
					}
					this.closeConn();	//关闭连接，语句及结果集
				}
			}
			catch(SQLException ea){ea.printStackTrace();}
		}
		else if(e.getSource()==this.jb2){//按下重置按钮,清空输入信息
			this.jtf.setText("");
			this.jpwf.setText("");
		}
		else if(e.getSource()==jtf){//当输入用户名并回车时
			this.jpwf.requestFocus(true);
		}
		else if(e.getSource()==jpwf){//当输入密码并回车时	
			this.jb1.requestFocus(true);
		}
		else if(e.getSource()==this.hostaddress){//当输入主机地址并回车时
			this.hostport.requestFocus(true);
		}
		else if(e.getSource()==this.hostport){//当输入端口号并回车时
			this.jtf.requestFocus(true);
		}
	}
	//自定义的初始化数据库连接的方法
	public void  initialConnection()
	{
		try
		{//加载驱动，创建Connection及Statement
			Class.forName("org.gjt.mm.mysql.Driver");
			conn=DriverManager.getConnection("jdbc:mysql://"+host+"/test","root","");
			stmt=conn.createStatement();
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(this,"连接失败，请检查主机地址是否正确","错误",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	//初始化的关闭数据库连接的方法
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
	public static void main(String args[])
	{
			//创建登陆窗体对象
		    Login login=new Login();
	}
}