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
	//����Connection���á�Statement������������������
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private JPanel jp=new JPanel();//����������ſռ������
	private JLabel jl=new JLabel("��     ��     ��");//������ʾ��ǩ
	private JLabel jl0=new JLabel("���ݿ������IP");
	private JLabel jl1=new JLabel("��    ��    ��");
	private JLabel jl2=new JLabel("��            ��");
	private JLabel jl3=new JLabel("");//���ڵ�½��ʾ��ǩ
	//����������ַ���˿ںš��û��������������
	private JTextField hostport=new JTextField();
	private JTextField hostaddress=new JTextField();
	private JTextField jtf=new JTextField();
	private JPasswordField jpwf=new JPasswordField();
	private JRadioButton[] jrbArray=//������ѡ��ť����
	        {
	        	new JRadioButton("��ͨѧ��",true),
	        	new JRadioButton("������Ա")
	        };
	//������
	private ButtonGroup bg=new ButtonGroup();
	//����������ť
	private JButton jb1=new JButton("��    ½");
	private JButton jb2=new JButton("��    ��");
	//������
	public Login()
	{ 
	    this.addListener();
		initialFrame();//��ʼ������
	}
	public void addListener(){
		this.jb1.addActionListener(this);//Ϊ��½��ťע�������
		this.jb2.addActionListener(this);//Ϊ���ð�ťע�������
		this.jtf.addActionListener(this);//Ϊ�û����ı���ע�������
		this.jpwf.addActionListener(this);//Ϊ�û��������ע�������
		this.hostaddress.addActionListener(this);//Ϊ������ַ�ı���ע�������
		this.hostport.addActionListener(this);//Ϊ�˿ں��ı���ע�������
	}
	public void initialFrame()
	{
		//��Ϊ�ղ���
		jp.setLayout(null);
		//���ؼ���ӵ�������Ӧλ��
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
		//���ô��ڵı��⡢��С��λ���Լ��ɼ���
		this.setTitle("��½");
		Image image=new ImageIcon("ico.gif").getImage();  
 		this.setIconImage(image);
		this.setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX=screenSize.width/2;
		int centerY=screenSize.height/2;
		int w=300;//��������
		int h=320;//������߶�
		this.setBounds(centerX-w/2,centerY-h/2-100,w,h);//���ô����������Ļ����
		this.setVisible(true);
		//����д�������ı�����ΪĬ�Ͻ���
		this.hostaddress.requestFocus(true);
		this.hostaddress.setText("127.0.0.1");
		this.hostport.setText("3306");
	}
	//ʵ��ActionListener�ӿ��еķ���
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==this.jb1)
		{//���µ�½��ť
			this.jl3.setText("�� �� �� ֤ �� �� �� ��. . . . .");//������ʾ��Ϣ
			//��ȡ�û������������ַ���˿ںš��û���������
			String hostadd=this.hostaddress.getText().trim();
			if(hostadd.equals("")){
				JOptionPane.showMessageDialog(this,"������������ַ","����",
				                                  JOptionPane.ERROR_MESSAGE);
				jl3.setText("");return;
			}
			String port=this.hostport.getText();
			if(port.equals("")){
				JOptionPane.showMessageDialog(this,"������˿ں�","����",
				                                  JOptionPane.ERROR_MESSAGE);
				jl3.setText("");return;
			}
			this.host=hostadd+":"+port;
			String name=this.jtf.getText().trim();
			if(name.equals("")){
				JOptionPane.showMessageDialog(this,"�������û���","����",
				                               JOptionPane.ERROR_MESSAGE);
				jl3.setText("");return;
			}
			String pwd=this.jpwf.getText().trim();
			if(pwd.equals("")){
				JOptionPane.showMessageDialog(this,"����������","����",
				                           JOptionPane.ERROR_MESSAGE);
				jl3.setText("");return;
			}
			int type=this.jrbArray[0].isSelected()?0:1;//��ȡ��½����
			try{   //��ʼ������
	            this.initialConnection();
				if(type==0){//��ͨѧ����½
				    //����sql��䲢��ѯ
					String sql="select * from user_stu where "+
					"stu_id='"+name+"' and pwd='"+pwd+"'";
					rs=stmt.executeQuery(sql);
					if(rs.next()){
						new StuClient(name,host);//����ѧ���ͻ��̴���
						this.dispose();//�رյ�½���ڲ��ͷ���Դ
					}
					else{//����������ʾ����
						JOptionPane.showMessageDialog(this,"�û������������","����",
						                           JOptionPane.ERROR_MESSAGE);
						jl3.setText("");
					}
					this.closeConn();//�ر����ӣ���估�����	
				}
				else{//��ʦ��½
				    //����sql��䲢��ѯ
					String sql="select coll_id from user_teacher where "+
					             "uid='"+name+"' and pwd='"+pwd+"'";
					rs=stmt.executeQuery(sql);
					if(rs.next()){
						String coll_id=rs.getString(1);
						new TeacherClient(coll_id,host);//������ʦ�ͻ��˴���
						this.dispose();//�رյ�½���ڲ��ͷ���Դ
					}
					else{//����������ʾ����
						JOptionPane.showMessageDialog(this,"�û������������","����",
						                           JOptionPane.ERROR_MESSAGE);
						jl3.setText("");
					}
					this.closeConn();	//�ر����ӣ���估�����
				}
			}
			catch(SQLException ea){ea.printStackTrace();}
		}
		else if(e.getSource()==this.jb2){//�������ð�ť,���������Ϣ
			this.jtf.setText("");
			this.jpwf.setText("");
		}
		else if(e.getSource()==jtf){//�������û������س�ʱ
			this.jpwf.requestFocus(true);
		}
		else if(e.getSource()==jpwf){//���������벢�س�ʱ	
			this.jb1.requestFocus(true);
		}
		else if(e.getSource()==this.hostaddress){//������������ַ���س�ʱ
			this.hostport.requestFocus(true);
		}
		else if(e.getSource()==this.hostport){//������˿ںŲ��س�ʱ
			this.jtf.requestFocus(true);
		}
	}
	//�Զ���ĳ�ʼ�����ݿ����ӵķ���
	public void  initialConnection()
	{
		try
		{//��������������Connection��Statement
			Class.forName("org.gjt.mm.mysql.Driver");
			conn=DriverManager.getConnection("jdbc:mysql://"+host+"/test","root","");
			stmt=conn.createStatement();
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(this,"����ʧ�ܣ�����������ַ�Ƿ���ȷ","����",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	//��ʼ���Ĺر����ݿ����ӵķ���
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
			//������½�������
		    Login login=new Login();
	}
}