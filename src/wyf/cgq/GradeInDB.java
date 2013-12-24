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
    //����Connection���á�Statement������������������
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	//��ŵ�ǰ��ʦ���ڵ�ѧԺ��
	private String coll_id;
	private Map map_dept=new HashMap();
	private Vector v_dept=new Vector();
	private JComboBox jcb=new JComboBox(v_dept);
	//������ʾ��Ϣ��ǩ
	private JLabel jl=new JLabel("��ѡ����Ҫ�����Ŀγ�");
	//������ű�ͷ��������ݵ�Vector����
	private Vector v_head=new Vector();
	private Vector v_data=new Vector();
	private JTable jt;//�����������
	private JScrollPane jsp;
	//����������ť��ֻ�й�����ĳɼ�ѧ���ſ��Կ���
	private JButton jb=new JButton("�����ÿƳɼ���");
	public GradeInDB(String coll_id,String host)
	{
		this.host=host;
		this.coll_id=coll_id;
		this.initialData();//��ʼ������
		this.initialFrame();//��ʼ������
		this.initialListener();//ע�������
	}
	public void initialFrame()//��ʼ������
	{   //���ؼ���ӵ�������
		this.setLayout(null);
		jl.setBounds(30,20,150,30);this.add(jl);
		jcb.setBounds(180,20,100,30);this.add(jcb);
		jb.setBounds(350,20,150,30);this.add(jb);
		jt=new JTable(new DefaultTableModel(v_data,v_head));
		jsp=new JScrollPane(jt);
		jsp.setBounds(30,70,500,500);this.add(jsp);
	}
	public void initialListener()//ע�������
	{
		jcb.addActionListener(this);jb.addActionListener(this);
		TableChangeListener tl=new TableChangeListener(stmt);
		jt.getSelectionModel().addListSelectionListener(tl);
		jt.getColumnModel().addColumnModelListener(tl);
		jt.getModel().addTableModelListener(tl);
	}
	public void initialData()//��ʼ�����ݵķ���
	{//��ʼ����ͷ
		v_head.add("�γ̺�");v_head.add("ѧ��");
		v_head.add("����");v_head.add("�ɼ�(��)");
		String sql="select distinct cou_name,course.cou_id from course,grade where"+
		  " course.coll_id='"+coll_id+"' and course.cou_id=grade.cou_id and isdual=0";
		try
		{//��ѯ���ݿ⣬���γ������γ̺Ŵ���map_dept��v_dept��
			this.initialConnection();
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				String cou_name=new String(rs.getString(1).getBytes("ISO-8859-1"));
				String cou_id=rs.getString(2);
				map_dept.put(cou_name,cou_id);
				v_dept.add(cou_name);
			}
			rs.close();//�رս����
		}
		catch(SQLException e){e.printStackTrace();}
		catch(UnsupportedEncodingException e){e.printStackTrace();}	
	}
	public void actionPerformed(ActionEvent e)
	{//ʵ��ActionListener�ӿ��еķ���
		if(e.getSource()==jcb)
		{//�������б���е�ѡ�����ݷ����仯ʱ�Ĵ������
			v_data.removeAllElements();//��v_data���
			//��������б�ѡ�еĿγ���
			String cur_cou_name=(String)jcb.getSelectedItem();
			//���ݿγ�����ÿγ̺�
			String cur_cou_id=(String)map_dept.get(cur_cou_name);
			String sql="select grade.cou_id,grade.stu_id,student.stu_name,score "+
			           "from grade,student where grade.stu_id=student.stu_id and "+
			            "isdual=0 and grade.cou_id='"+cur_cou_id+"'";//����sql���
			try{//ִ�����
				rs=stmt.executeQuery(sql);
			    while(rs.next()){//����ÿγ̺���ص�δ�������Ϣ����v_data
			    	Vector v=new Vector();
			    	String cou_id=rs.getString(1);
			    	String stu_id=rs.getString(2);
			    	String stu_name=new String(rs.getString(3).getBytes("ISO-8859-1"));
			    	String score=rs.getDouble(4)+"";
			    	v.add(cou_id);v.add(stu_id);v.add(stu_name);v.add(score);
			    	v_data.add(v);
			    }
			    rs.close();//�رս����
			    DefaultTableModel temp1=(DefaultTableModel)jt.getModel();//���±��ģ�ͣ�
			    temp1.setDataVector(v_data,v_head);
			    temp1.fireTableStructureChanged();//������ʾ��Ϣ
			}
			catch(Exception ea){ea.printStackTrace();}
		}
		else if(e.getSource()==jb){//�����¹����ɼ��İ�ťʱ
			try{//���Ҫ�����ɼ��Ŀγ���
				String cur_cou_name=(String)jcb.getSelectedItem();
				if(cur_cou_name!=null){//�������ݿ��еı�־��
					String cur_cou_id=(String)map_dept.get(cur_cou_name);
					String sql="update grade set isdual=1 where "+
					            "cou_id='"+cur_cou_id+"' and isdual=0";
					int i=stmt.executeUpdate(sql);
				}
				else{//û��ѡ��γ����Ĵ�����ʾ����
					JOptionPane.showMessageDialog(this,"����ѡ��γ�����","����",
					                                JOptionPane.ERROR_MESSAGE);
				}
			}
			catch(Exception ea){ea.printStackTrace();}	
		}
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
	class TableChangeListener implements ListSelectionListener,
	                          TableModelListener,TableColumnModelListener
	{
		int rowNum,colNum;
		Statement statement;
		public TableChangeListener(Statement statement)
		{this.statement=statement;}
		public void valueChanged(ListSelectionEvent e){//�����е�ֵ
			rowNum=jt.getSelectedRow();
		}
		public void columnSelectionChanged(ListSelectionEvent e){//�����е�ֵ
			colNum=jt.getSelectedColumn();
		}
		public void tableChanged(TableModelEvent e)
		{//�����ĵ��ǵ�����(������)ʱ�Ĵ������
			if(colNum==3)
			{   //������������
				String str=(String)jt.getValueAt(rowNum,colNum);
				//��ø���������Ӧ�Ŀγ̺�
				String cou_id=(String)jt.getValueAt(rowNum,0);
				//��ø���������Ӧѧ����ѧ��
				String stu_id=(String)jt.getValueAt(rowNum,1);
				try{//��strת��ΪDouble
					Double d=Double.parseDouble(str);
					if(d<0||d>100)//��������С��0�Ҳ��ܴ���100
					{//���ڷ�Χ�ڣ�������Ϊ0
						jt.setValueAt("0",rowNum,colNum);
					}
				}
				catch(Exception ea){//�������֣�������Ϊ0
					jt.setValueAt("0",rowNum,colNum);
				}
				//�����ĵ�����ͬ�������ݿ�
				String sql="update grade set score="+str+" where "+
				           "cou_id='"+cou_id+"' and stu_id='"+stu_id+"'";
				try{//ִ��sql���
					int i=statement.executeUpdate(sql);
				}
				catch(Exception ea){ea.printStackTrace();}
			}
		}
		//ʵ�ֽӿ��е���������
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