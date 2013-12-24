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
import javax.swing.table.*;
public class ChoseCourse extends JPanel implements ActionListener
{   
	private String host;
	private String stu_id;// 声明表示当前学生学号的引用
	//声明Connection引用、Statement对象引用与结果集引用
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private JLabel[] jlArray={new JLabel("供选择的课程列表如下："),
	 new JLabel("请输入您要选择课程的课程号")};//创建提示信息的标签
	private JTextField jtf=new JTextField();//创建输入框，用与输入课程号
	private JButton jb=new JButton("提   交");//创建提交按钮
	//声明JTable、JScrollPane引用
	private JTable jt;private JScrollPane jsp;
	//创建存放表格头和表格数据的Vector对象
	private Vector<String> v_head=new Vector<String>();
	private Vector<Vector> v_data=new Vector<Vector>();
	//创建存放可选课程的课程号的Vector对象
	private Vector<String> v_couid=new Vector<String>();
	public ChoseCourse(String stu_id,String host)
	{
		this.host=host;
		this.stu_id=stu_id;
		this.initialData();//初始化数据
		this.initialFrame();//初始化窗体
	}
	public void initialFrame()
	{//自定义的初始化窗体的方法 
		this.setLayout(null);
		DefaultTableModel dtm=new DefaultTableModel(v_data,v_head);
		jt=new JTable(dtm);//创建表格
		jsp=new JScrollPane(jt);//将表格放入JScrollPane中
		//设置各控件的位置，并为"提交"按钮注册监听器
		jlArray[0].setBounds(30,20,200,30);this.add(jlArray[0]);
		jsp.setBounds(20,70,650,400);this.add(jsp);
		jlArray[1].setBounds(30,500,190,30);this.add(jlArray[1]);
		jtf.setBounds(230,500,140,30);this.add(jtf);
		jtf.addActionListener(this);jb.setBounds(390,500,100,30);this.add(jb);
		jb.addActionListener(this);	
	}
	public void initialData()//自定义的初始化数据的方法
	{   //初始化表头
		v_head.add("课程号");v_head.add("课程名");v_head.add("星期几");v_head.add("第几讲");
		v_head.add("学分");v_head.add("老师");v_head.add("所属专业");v_head.add("开课学院");
		try{//初始化表格数据
			this.initialConnection();
			String sql="select courseinfo.cou_id,course.cou_name,courseinfo.cou_day,"+
			           "courseinfo.cou_time,course.xuefen,courseinfo.teacher,dept.dept_name,"+
			           "college.coll_name from course,courseinfo,dept,college where "+
			           "courseinfo.cou_id=course.cou_id and course.dept_id=dept.dept_id and "+
			           "course.coll_id=college.coll_id and courseinfo.onchosing='1'";
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				Vector v=new Vector();
				String cou_id=rs.getString(1);
				v_couid.add(cou_id);
				String cou_name=new String(rs.getString(2).getBytes("ISO-8859-1"));
				String cou_day=rs.getString(3);
				String cou_time=rs.getString(4);
				String xuefen=rs.getDouble(5)+"";
				String teacher=new String(rs.getString(6).getBytes("ISO-8859-1"));
				String dept_name=new String(rs.getString(7).getBytes("ISO-8859-1"));
				String coll_name=new String(rs.getString(8).getBytes("ISO-8859-1"));
				v.add(cou_id);v.add(cou_name);v.add(cou_day);v.add(cou_time);
				v.add(xuefen);v.add(teacher);v.add(dept_name);v.add(coll_name);
				v_data.add(v);
			}
			rs.close();
		}
		catch(Exception e){e.printStackTrace();}
	}
	//实现ActionListener接口中的方法
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==jb||e.getSource()==jtf)
		{//按下提交按钮的处理代码
			String cou_id=jtf.getText().trim();//获取输入的课程号
			if(cou_id.equals("")){//课程号为空
				JOptionPane.showMessageDialog(this,"请输入课程号","错误",
				                               JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(!v_couid.contains(cou_id)){//该课程不在可选列表之中
				JOptionPane.showMessageDialog(this,"请输入正确的课程号","错误",
				                                     JOptionPane.ERROR_MESSAGE);
				return;
			}
			try{
				String sql1="select * from grade where stu_id='"+stu_id+"' and "+
				             "cou_id='"+cou_id+"'";
				rs=stmt.executeQuery(sql1);
				if(rs.next()){//已经选过这门课程
					JOptionPane.showMessageDialog(this,"你已经选过这门课程了","错误",
					                                       JOptionPane.ERROR_MESSAGE);	
				}
				else{   //判断是否与已经选的课程时间冲突
				    rs.close();
					String sql2="select cou_name from course,courseinfo,grade "+
					             "where grade.cou_id=course.cou_id "+
					            "and grade.cou_id=courseinfo.cou_id "+
					            "and grade.stu_id='"+stu_id+"' "+
					            "and grade.isdual=0 "+
					            "and (courseinfo.cou_day,courseinfo.cou_time) in "+
					            "(select cou_day,cou_time from courseinfo where "+
					            "cou_id='"+cou_id+"')";
					 rs=stmt.executeQuery(sql2);
					 if(rs.next())
					 {//时间冲突，给出提示信息
					 	String cou_name=new String(rs.getString(1).getBytes("ISO-8859-1"));
					 	JOptionPane.showMessageDialog(this,"与"+cou_name+"时间冲突","错误",
					 	                                        JOptionPane.ERROR_MESSAGE);
					 }
					 else
					 {//开始添加课程
					 	String sql="insert into grade(stu_id,cou_id) values"+
					 	            "('"+stu_id+"','"+cou_id+"')";
						int i=stmt.executeUpdate(sql);
						if(i==1)
						{//添加成功
							JOptionPane.showMessageDialog(this,"添加成功","提示",
							                            JOptionPane.INFORMATION_MESSAGE);
						}
						else
						{//添加失败
							JOptionPane.showMessageDialog(this,"提交失败","错误",
							                                 JOptionPane.ERROR_MESSAGE);
						}
					 }
				}
				rs.close();
			}
			catch(Exception ea){ea.printStackTrace();}
		}
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
		ChoseCourse cc=new ChoseCourse("200501030318","127.0.0.1:3306");
		JFrame jf=new JFrame();
		jf.setBounds(10,10,900,600);
		jf.add(cc);
		jf.setVisible(true);
	}
}