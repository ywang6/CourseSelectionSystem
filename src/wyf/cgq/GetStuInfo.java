package wyf.cgq;
import java.util.*;
import java.sql.*;
import java.sql.Date;
public class GetStuInfo
{
	private String host;
	//声明Connection引用、Statement对象引用与结果集引用
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	public GetStuInfo(String host)
	{	
		this.host=host;
	}
	//根据学号获得学生相应基本信息的方法
	public String[] getBaseInfo(String stu_id)
	{
		//创建用于存放基本信息的数组
		String[] message=new String[13];
		try
		{//查询数据库将数据存入数组并返回
			this.initialConnection();
			String sql=
			"select stu_id,stu_name,stu_gender,stu_birth,nativeplace,"+
			"coll_name,dept_name,class_name,cometime from student,dept,college,"+
			"class where stu_id='"+stu_id+"' and student.coll_id=college.coll_id"+
			" and student.dept_id=dept.dept_id and student.class_id=class.class_id";
			rs=stmt.executeQuery(sql);
			if(rs.next())
			{
				message[0]=rs.getString(1);
				message[1]=new String(rs.getString(2).getBytes("ISO-8859-1"));
				message[2]=new String(rs.getString(3).getBytes("ISO-8859-1"));
				Date stu_birth=rs.getDate(4);
				message[3]=stu_birth.getYear()+1900+"";
				message[4]=stu_birth.getMonth()+1+"";
				message[5]=stu_birth.getDate()+"";
				message[6]=new String(rs.getString(5).getBytes("ISO-8859-1"));
				message[7]=new String(rs.getString(6).getBytes("ISO-8859-1"));
				message[8]=new String(rs.getString(7).getBytes("ISO-8859-1"));
				message[9]=new String(rs.getString(8).getBytes("ISO-8859-1"));
				Date cometime=rs.getDate(9);
				message[10]=cometime.getYear()+1900+"";
				message[11]=cometime.getMonth()+1+"";
				message[12]=cometime.getDate()+"";
			}
			this.closeConn();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return message;
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