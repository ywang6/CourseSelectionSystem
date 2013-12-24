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
public class NewCourse extends JPanel implements ActionListener
{
	private String host;
	//声明Connection引用、Statement对象引用与结果集引用
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private String coll_id;//声明标志学院号的引用
	Vector v_dept=new Vector();//创建存放专业名称的Vector对象
	//创建提示标签数组
	private JLabel[] jlArray={new JLabel("课   程   号"),new JLabel("课   程   名"),
	new JLabel("学         分"),new JLabel("所 属 专 业"),new JLabel("是否列入选课列表")
	};
	//创建文本框数组
	private JTextField[] jtfArray={new JTextField(),new JTextField(),new JTextField()};
	private JComboBox jcb1;//声明JComboBox引用
	JButton jb1=new JButton("添加");JButton jb2=new JButton("重置");//创建动作按钮
	public NewCourse(String coll_id,String host)
	{
		this.host=host;
		this.coll_id=coll_id;
		this.initialData();//初始化数据
		this.addListener();//为控件注册监听器
		this.initialFrame();//初始化窗体
	}
	public void initialData()
	{//初始化数据的方法
		try{//根据学院号获得该学院的专业名
			this.initialConnection();
			String sql="select dept_name from dept where coll_id='"+coll_id+"'";
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				String dept_name=new String(rs.getString(1).getBytes("ISO-8859-1"));
				v_dept.add(dept_name);
			}
			jcb1=new JComboBox(v_dept);//创建JComboBox
			this.closeConn();
		}
		catch(Exception e){e.printStackTrace();}
	}
	public void addListener()
	{//为控件注册监听器
	    jtfArray[0].addActionListener(this);
	    jtfArray[1].addActionListener(this);
	    jtfArray[2].addActionListener(this);
	    jcb1.addActionListener(this);
		jb1.addActionListener(this);
		jb2.addActionListener(this);
	}
	//初始化窗体界面
	public void initialFrame()
	{//将控件添加到容器中
		this.setLayout(null);
		jlArray[0].setBounds(30,50,100,30);
		this.add(jlArray[0]);
		jlArray[1].setBounds(30,100,100,30);
		this.add(jlArray[1]);
		jlArray[2].setBounds(30,150,100,30);
		this.add(jlArray[2]);
		jlArray[3].setBounds(30,200,100,30);
		this.add(jlArray[3]);
		jtfArray[0].setBounds(180,50,150,30);
		this.add(jtfArray[0]);
		jtfArray[1].setBounds(180,100,150,30);
		this.add(jtfArray[1]);
		jtfArray[2].setBounds(180,150,150,30);
		this.add(jtfArray[2]);
		jcb1.setBounds(180,200,150,30);
		this.add(jcb1);
		jb1.setBounds(50,250,80,30);
		jb2.setBounds(200,250,80,30);
		this.add(jb1);
		this.add(jb2);
	}
	//实现ActionListener接口中的方法
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==jtfArray[0]){//输入完课程号回车后
			jtfArray[1].requestFocus(true);
		}
		else if(e.getSource()==jtfArray[1]){//输入完课程名回车后
			jtfArray[2].requestFocus(true);
		}
		else if(e.getSource()==jtfArray[2]){//输入完学分回车后
			jcb1.requestFocus(true);
		}
		else if(e.getSource()==jcb1){//下拉列表框的值发生变化时
			jb1.requestFocus(true);
		}
		else if(e.getSource()==jb1)
		{//按下"添加"的处理代码
		    //获得输入的课程号
			String cou_id=jtfArray[0].getText().trim();
			//判断课程号是否正确的正则式字符串
			String patternStr="[0-9]{6}";
			if(cou_id.equals(""))
			{//课程号为空
				JOptionPane.showMessageDialog(this,"请输入 课程号","错误",
				                                JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(!cou_id.matches(patternStr)){//课程号格式不符合正则式
				JOptionPane.showMessageDialog(this,"课程号必须是六位数字",
				                          "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			//获得课程名
			String cou_name=jtfArray[1].getText().trim();
			if(cou_name.equals("")){//课程名为空
				JOptionPane.showMessageDialog(this,"请输入课程名称","错误",
				                                JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(cou_name.length()>15){//课程名超过15个字符
				JOptionPane.showMessageDialog(this,"课程名称不能超过十五个字符","错误",
				                                   JOptionPane.ERROR_MESSAGE);
				return;
			}
			//获得学分
			String xuefen=jtfArray[2].getText().trim();
			if(xuefen.equals("")){//学分为空
				JOptionPane.showMessageDialog(this,"请输入学分","错误",
				                               JOptionPane.ERROR_MESSAGE);
				return;
			}
	        //验证学分是否符合格式的正则式
			String patternStr1="[1-9]?[0-9](\\.[0-9])?";
			if(!xuefen.matches(patternStr1)){//不符合格式
				JOptionPane.showMessageDialog(this,"学分格式不正确，小数后最多一位，之前最多两位",
				                                   "错误",JOptionPane.ERROR_MESSAGE);
			    return;
			}
			//获得选择的专业名称
			String dept_name=((String)jcb1.getSelectedItem()).trim();
			try{   //创建sql语句
				String sql="insert into course values('"+cou_id+"',"+
				"'"+new String(cou_name.getBytes(),"ISO-8859-1")+"',"+xuefen+",'"+coll_id+"',"+
				"(select dept_id from dept where dept_name="+
				"'"+new String(dept_name.getBytes(),"ISO-8859-1")+"' and coll_id='"+coll_id+"'))";
				//初始化数据库连接
				this.initialConnection();
				//执行sql语句
				int i=stmt.executeUpdate(sql);
				if(i!=1){//添加失败
					JOptionPane.showMessageDialog(this,"添加失败！！！","错误",
					                               JOptionPane.ERROR_MESSAGE);
			    	return;
				}
				else{//添加成功
					JOptionPane.showMessageDialog(this,"添加成功","提示",
					                               JOptionPane.INFORMATION_MESSAGE);
				}
			}
			catch(Exception ea){//该编号课程已经存在
				JOptionPane.showMessageDialog(this,"添加失败！！！,该编号的课程已经存在",
				                                "错误",JOptionPane.ERROR_MESSAGE);
			}
			finally{//关闭数据库连接
				this.closeConn();
			}
		}
		else if(e.getSource()==jb2){//按下"重置"按钮时
			for(int i=0;i<jtfArray.length;i++){
				jtfArray[i].setText("");
			}
		}
	}
	public void setFocus(){
		this.jtfArray[0].requestFocus(true);
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
	public static void main(String args[])
	{
		NewCourse nc=new NewCourse("01","127.0.0.1:3306");
		JFrame jf=new JFrame();
		jf.setBounds(10,10,700,650);
		jf.add(nc);
		jf.setVisible(true);
	}
}