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
public class GradeInDB extends JPanel implements ActionListener
{ 
	private String host;
    //声明Connection引用、Statement对象引用与结果集引用
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	//存放当前教师所在的学院号
	private String coll_id;
	private Map map_dept=new HashMap();
	private Vector v_dept=new Vector();
	private JComboBox jcb=new JComboBox(v_dept);
	//创建提示信息标签
	private JLabel jl=new JLabel("请选择您要操作的课程");
	//创建存放表头及表格数据的Vector对象
	private Vector v_head=new Vector();
	private Vector v_data=new Vector();
	private JTable jt;//声名表格引用
	private JScrollPane jsp;
	//创建动作按钮，只有公布后的成绩学生才可以看到
	private JButton jb=new JButton("公布该科成绩单");
	public GradeInDB(String coll_id,String host)
	{
		this.host=host;
		this.coll_id=coll_id;
		this.initialData();//初始化数据
		this.initialFrame();//初始化窗体
		this.initialListener();//注册监听器
	}
	public void initialFrame()//初始化窗体
	{   //将控件添加到容器中
		this.setLayout(null);
		jl.setBounds(30,20,150,30);this.add(jl);
		jcb.setBounds(180,20,100,30);this.add(jcb);
		jb.setBounds(350,20,150,30);this.add(jb);
		jt=new JTable(new DefaultTableModel(v_data,v_head));
		jsp=new JScrollPane(jt);
		jsp.setBounds(30,70,500,500);this.add(jsp);
	}
	public void initialListener()//注册监听器
	{
		jcb.addActionListener(this);jb.addActionListener(this);
		TableChangeListener tl=new TableChangeListener(stmt);
		jt.getSelectionModel().addListSelectionListener(tl);
		jt.getColumnModel().addColumnModelListener(tl);
		jt.getModel().addTableModelListener(tl);
	}
	public void initialData()//初始化数据的方法
	{//初始化表头
		v_head.add("课程号");v_head.add("学号");
		v_head.add("姓名");v_head.add("成绩(分)");
		String sql="select distinct cou_name,course.cou_id from course,grade where"+
		  " course.coll_id='"+coll_id+"' and course.cou_id=grade.cou_id and isdual=0";
		try
		{//查询数据库，将课程名及课程号存入map_dept与v_dept中
			this.initialConnection();
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				String cou_name=new String(rs.getString(1).getBytes("ISO-8859-1"));
				String cou_id=rs.getString(2);
				map_dept.put(cou_name,cou_id);
				v_dept.add(cou_name);
			}
			rs.close();//关闭结果集
		}
		catch(SQLException e){e.printStackTrace();}
		catch(UnsupportedEncodingException e){e.printStackTrace();}	
	}
	public void actionPerformed(ActionEvent e)
	{//实现ActionListener接口中的方法
		if(e.getSource()==jcb)
		{//当下拉列表框中的选择内容发生变化时的处理代码
			v_data.removeAllElements();//将v_data清空
			//获得下拉列表选中的课程名
			String cur_cou_name=(String)jcb.getSelectedItem();
			//根据课程名获得课程号
			String cur_cou_id=(String)map_dept.get(cur_cou_name);
			String sql="select grade.cou_id,grade.stu_id,student.stu_name,score "+
			           "from grade,student where grade.stu_id=student.stu_id and "+
			            "isdual=0 and grade.cou_id='"+cur_cou_id+"'";//创建sql语句
			try{//执行语句
				rs=stmt.executeQuery(sql);
			    while(rs.next()){//将与该课程号相关的未处理的信息存入v_data
			    	Vector v=new Vector();
			    	String cou_id=rs.getString(1);
			    	String stu_id=rs.getString(2);
			    	String stu_name=new String(rs.getString(3).getBytes("ISO-8859-1"));
			    	String score=rs.getDouble(4)+"";
			    	v.add(cou_id);v.add(stu_id);v.add(stu_name);v.add(score);
			    	v_data.add(v);
			    }
			    rs.close();//关闭结果集
			    DefaultTableModel temp1=(DefaultTableModel)jt.getModel();//更新表格模型，
			    temp1.setDataVector(v_data,v_head);
			    temp1.fireTableStructureChanged();//更新显示信息
			}
			catch(Exception ea){ea.printStackTrace();}
		}
		else if(e.getSource()==jb){//当按下公布成绩的按钮时
			try{//获得要公布成绩的课程名
				String cur_cou_name=(String)jcb.getSelectedItem();
				if(cur_cou_name!=null){//更改数据库中的标志列
					String cur_cou_id=(String)map_dept.get(cur_cou_name);
					String sql="update grade set isdual=1 where "+
					            "cou_id='"+cur_cou_id+"' and isdual=0";
					int i=stmt.executeUpdate(sql);
				}
				else{//没有选择课程名的错误提示信心
					JOptionPane.showMessageDialog(this,"请先选择课程名称","错误",
					                                JOptionPane.ERROR_MESSAGE);
				}
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
	class TableChangeListener implements ListSelectionListener,
	                          TableModelListener,TableColumnModelListener
	{
		int rowNum,colNum;
		Statement statement;
		public TableChangeListener(Statement statement)
		{this.statement=statement;}
		public void valueChanged(ListSelectionEvent e){//更新行的值
			rowNum=jt.getSelectedRow();
		}
		public void columnSelectionChanged(ListSelectionEvent e){//更新列的值
			colNum=jt.getSelectedColumn();
		}
		public void tableChanged(TableModelEvent e)
		{//当更改的是第三列(分数列)时的处理代码
			if(colNum==3)
			{   //获得输入的数据
				String str=(String)jt.getValueAt(rowNum,colNum);
				//获得该数据所对应的课程号
				String cou_id=(String)jt.getValueAt(rowNum,0);
				//获得该数据所对应学生的学号
				String stu_id=(String)jt.getValueAt(rowNum,1);
				try{//将str转化为Double
					Double d=Double.parseDouble(str);
					if(d<0||d>100)//分数不能小于0且不能大于100
					{//不在范围内，将其设为0
						jt.setValueAt("0",rowNum,colNum);
					}
				}
				catch(Exception ea){//不是数字，将其设为0
					jt.setValueAt("0",rowNum,colNum);
				}
				//将更改的数据同步到数据库
				String sql="update grade set score="+str+" where "+
				           "cou_id='"+cou_id+"' and stu_id='"+stu_id+"'";
				try{//执行sql语句
					int i=statement.executeUpdate(sql);
				}
				catch(Exception ea){ea.printStackTrace();}
			}
		}
		//实现接口中的其他方法
		public void columnMoved(TableColumnModelEvent e){}
		public void columnRemoved(TableColumnModelEvent e){}
		public void columnMarginChanged(ChangeEvent e){}
		public void columnAdded(TableColumnModelEvent e){}
	}
	public static void main(String args[])
	{
		GradeInDB gi=new GradeInDB("01","127.0.0.1:3306");
		JFrame jf=new JFrame();
		jf.setBounds(10,10,700,650);
		jf.add(gi);
		jf.setVisible(true);
	}
}