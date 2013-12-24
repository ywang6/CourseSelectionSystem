package wyf.cgq;
import java.util.*;
import java.sql.*;
public class GetScore
{
	private String host;
	//����Connection���á�Statement������������������
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	//������ŷ��ؽ����Vector
	private Vector<Vector> v=new Vector<Vector>();
	//�������ѧ��ѧ�����ı���
	private double xuefen;
	public GetScore(String host)
	{
		this.host=host;
	}
	//����ѧ�Ż��ѧ�����޿γ���Ϣ�ķ���
	public Vector getAllScore(String stu_id)
	{//��Vector��գ���ֹ�������¼
		v.removeAllElements();
		try
		{//��ѯ���ݿ⣬�����ݴ���Vector v������
			this.initialConnection();
			String sql=
			"select course.cou_name,grade.score,course.xuefen from course,grade"+
			" where grade.stu_id='"+stu_id+"' and grade.isdual=1 and "+
			 "grade.cou_id=course.cou_id order by score desc";
			rs=stmt.executeQuery(sql);
			while(rs.next())
			{
				
				Vector temp=new Vector();
				String cou_name=new String(rs.getString(1).getBytes("ISO-8859-1"));
				String score=rs.getDouble(2)+"";
				String xuefen=rs.getDouble(3)+"";
				temp.add(cou_name);
				temp.add(score);
				temp.add(xuefen);	
				v.add(temp);
			}           
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.closeConn();
		}
		return v;
	}
	//����ѧ�Ż��ѧ��������γ̵���Ϣ
	public Vector getFailScore(String stu_id)
	{ //��Vector��գ���ֹ�������¼  
		v.removeAllElements();
		try
		{//��ѯ���ݿ⣬��������γ���Ϣ����Vecteo v������
			this.initialConnection();
			String sql=
					"select course.cou_name,grade.score,"+
					"course.xuefen from course,grade "+
					"where grade.stu_id='"+stu_id+"' and"+
					" grade.cou_id=course.cou_id and score<60"+
					 " and grade.isdual=1";
			rs=stmt.executeQuery(sql);
			while(rs.next())
			{
				Vector temp=new Vector();
				String cou_name=new String(rs.getString(1).getBytes("ISO-8859-1"));
				String score=rs.getDouble(2)+"";
				String xuefen=rs.getDouble(3)+"";
				temp.add(cou_name);
				temp.add(score);
				temp.add(xuefen);
				v.add(temp);	
			}           
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.closeConn();
		}
		return v;
	}
	//����ѧ�Ż������ѧ��
	public double getXueFen(String stu_id)
	{
		try
		{
			this.initialConnection();
			String sql=
			"select sum(xuefen) from course,grade where stu_id='"+stu_id+"'"+
			" and grade.cou_id=course.cou_id and grade.score>=60 and isdual=1";
			rs=stmt.executeQuery(sql);
			if(rs.next())
			{
				xuefen=rs.getDouble(1);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return xuefen;
	}
	//�Զ���ĳ�ʼ�����ݿ����ӵķ���
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
	//�ر����ӵķ���
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