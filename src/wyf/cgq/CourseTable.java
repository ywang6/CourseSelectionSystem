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
public class CourseTable extends JPanel
{   
	private String host;
	//����Connection���á�Statement������������������
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private String stu_id;//������־ѧ��ѧ�ŵ�����
	private String[] s_head={"����","����һ","���ڶ�","������",
	"������","������","������","������"};//��ʼ����ͷ
	private String[][] s_data=new String[5][8];//������ſγ���Ϣ�Ķ�ά����
	private JTable jt;//�����������
	private JScrollPane jsp;
	public CourseTable(String stu_id,String host)
	{
		this.host=host;
		this.stu_id=stu_id;
		this.initialData();//��ʼ������
		this.initialTable();//��ʼ�����
		this.initialFrame();//��ʼ������
	}
	public void initialFrame(){//��JScrollPane��ӵ���Ӧλ��
		this.setLayout(null);
		jsp.setBounds(30,30,620,450);this.add(jsp);
	}
	public void initialTable(){//����������ñ����ʾ��ʽ
		DefaultTableModel dtm=new DefaultTableModel(s_data,s_head);
		jt=new JTable(dtm);
		jt.setRowHeight(85);
		jt.setDefaultRenderer(Object.class, new TableViewRenderer());
		jsp=new JScrollPane(jt);
	}
	public void initialData()
	{//��ʼ���������
	    //��ʼ����һ������
		s_data[0][0]="��һ��";s_data[1][0]="�ڶ���";s_data[2][0]="������";
		s_data[3][0]="���Ľ�";s_data[4][0]="���彲";
		try{//��ѯ���ݿ⣬��ʼ������
			String sql="select cou_name,teacher,cou_day,cou_time from course,courseinfo,"+
			           "grade where grade.stu_id='"+stu_id+"' and grade.cou_id=course.cou_id and "+
			           "grade.cou_id=courseinfo.cou_id and grade.isdual=0";
			this.initialConnection();
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				String cou_name=new String(rs.getString(1).getBytes("ISO-8859-1"));
				String teacher=new String(rs.getString(2).getBytes("ISO-8859-1"));
				int cou_day=Integer.parseInt(rs.getString(3));
				int cou_time=Integer.parseInt(rs.getString(4));
				s_data[cou_time-1][cou_day]=cou_name+"\n(��ʦ��"+teacher+")";
		    }
		}
		catch(Exception e){e.printStackTrace();}
	}
	public void updataview()//���±��ģ��
	{
		((DefaultTableModel)jt.getModel()).setDataVector(s_data,s_head);
		((DefaultTableModel)jt.getModel()).fireTableStructureChanged();
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
	//�Զ���ı�������
	class TableViewRenderer extends JTextArea implements TableCellRenderer 
	{ 
       public TableViewRenderer() 
       { 
            //�������Ϊ�Զ�����
	        setLineWrap(true); 
       }
       public Component getTableCellRendererComponent(JTable jtable, Object obj, 
            boolean isSelected, boolean hasFocus, int row, int column) 
       { 
	        setText(obj == null ? "" : obj.toString()); 
	        return this; 
       } 
    } 
}