package wyf.cgq;
import java.util.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
public class ChangePwdTeacher extends JPanel 
                     implements ActionListener
{ 
    private String host;
    //����Connection���á�Statement������������������
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	//������Ϣ��ʾ��ǩ����
	private JLabel[] jlArray={new JLabel("�û���"),
	   new JLabel("ԭʼ����"),new JLabel("������"),
	   new JLabel("ȷ��������")};
	//�����û��������
	private JTextField jtf=new JTextField();
	//�������������
	private JPasswordField[] jpfArray={new JPasswordField(),
	        new JPasswordField(),new JPasswordField()};
	//����������ť
	private JButton[] jbArray={new JButton("ȷ��"),new JButton("����")};
	//������                          
	public ChangePwdTeacher(String host)
	{ 
	    this.host=host; 
	   //��ʼ��ҳ��
		this.initialFrame();
		//Ϊ��ťע�������
		this.addListener();
	}
	public void addListener()
	{    //Ϊ�ı���ע�������
	     jtf.addActionListener(this);
	     //Ϊ�����ע�������
	     jpfArray[0].addActionListener(this);
	     jpfArray[1].addActionListener(this);
	     jpfArray[2].addActionListener(this);
	    //Ϊ��ťע�������
		jbArray[0].addActionListener(this);
		jbArray[1].addActionListener(this);
	}
	//��ʼ��ҳ��ķ���
	public void initialFrame()
	{   //��Ϊ�ղ���
		this.setLayout(null);
		//���ؼ�������Ӧ��λ��
		for(int i=0;i<jlArray.length;i++)
		{
			jlArray[i].setBounds(30,20+50*i,150,30);
			this.add(jlArray[i]);
			if(i==0)
			{
				jtf.setBounds(130,20+50*i,150,30);
				this.add(jtf);
			}
			else
			{
				jpfArray[i-1].setBounds(130,20+50*i,150,30);
				this.add(jpfArray[i-1]);
			}
			
		}
		jbArray[0].setBounds(40,230,100,30);
		this.add(jbArray[0]);
		jbArray[1].setBounds(170,230,100,30);
		this.add(jbArray[1]);
		
	}
	//ʵ��ActionListener�ӿ��еķ���
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==jtf)//�����û������س���
		{
			jpfArray[0].requestFocus(true);
		}
		else if(e.getSource()==jpfArray[0])//����ԭʼ���벢�س���
		{
			jpfArray[1].requestFocus(true);
		}
		else if(e.getSource()==jpfArray[1])//���������벢�س���
		{
			jpfArray[2].requestFocus(true);
		}
		else if(e.getSource()==jpfArray[2])//����ȷ�������벢�س���
		{
			jbArray[0].requestFocus(true);
		}
		else if(e.getSource()==jbArray[1])
		{////�������ð�ť�Ĵ������
		     //��������Ϣ���
			for(int i=0;i<jpfArray.length;i++)
			{
				jpfArray[i].setText("");
			}
			jtf.setText("");
		}
		else if(e.getSource()==jbArray[0])
		{//����ȷ�ϰ�ť�Ĵ������
		    //�����ж������ʽ������ʽ�ַ���
			String patternStr="[0-9a-zA-Z]{6,12}";
			String user_name=jtf.getText().trim();//��ȡ�û���
			if(user_name.equals(""))
			{//�û���Ϊ��
				JOptionPane.showMessageDialog(this,"�������û���",
				                "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			String oldPwd=jpfArray[0].getText().trim();//��ȡԭʼ����
			if(oldPwd.equals(""))
			{//ԭʼ����Ϊ��
				JOptionPane.showMessageDialog(this,"������ԭʼ����",
				                  "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			String newPwd=jpfArray[1].getText().trim();//��ȡ������
			if(newPwd.equals(""))
			{//������Ϊ��
				JOptionPane.showMessageDialog(this,"������������",
				                "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(!newPwd.matches(patternStr))
			{//�����벻���ϸ�ʽ
				JOptionPane.showMessageDialog(this,
				                  "����ֻ����6��12λ����ĸ������",
				                  "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			String newPwd1=jpfArray[2].getText().trim();//��ȡȷ������
			if(!newPwd.equals(newPwd1))
			{//��������ȷ�����벻ͬ
				JOptionPane.showMessageDialog(this,"ȷ�������������벻��",
				                       "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try
			{   //��ʼ�����ݿ����Ӳ���������
				this.initialConnection();
				String sql="update user_teacher set pwd='"+newPwd+"'"+
				            " where uid='"+user_name+"'"+
				           " and pwd='"+oldPwd+"'";
				int i=stmt.executeUpdate(sql);
				if(i==0)
				{//����ʧ��
					JOptionPane.showMessageDialog(this,
					      "�޸�ʧ�ܣ����������û����������Ƿ���ȷ",
					      "����",JOptionPane.ERROR_MESSAGE);
				}
				else if(i==1)
				{//���ĳɹ�
					JOptionPane.showMessageDialog(this,"�����޸ĳɹ�",
					           "��ʾ",JOptionPane.INFORMATION_MESSAGE);
				}
				this.closeConn();//�ر����ݿ�����
			}
			catch(Exception ea){
				ea.printStackTrace();
			}
		}
	}
	public void setFocus()
	{
		jtf.requestFocus(true);
	}
	//�Զ���ĳ�ʼ�����ݿ����ӵķ���
	public void  initialConnection()
	{
		try
		{
			Class.forName("org.gjt.mm.mysql.Driver");
			conn=DriverManager.getConnection(
				 "jdbc:mysql://"+host+"/test","root","");
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
	//�ر����ݿ����ӵķ���
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
		ChangePwdTeacher cpt=new ChangePwdTeacher("127.0.0.1:3306");
		JFrame jframe=new JFrame();
		jframe.add(cpt);
		jframe.setBounds(70,20,700,650);
		jframe.setVisible(true);
	}
}