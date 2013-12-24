package wyf.cgq;
import java.util.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
public class ChangePwd extends JPanel implements ActionListener
{
	private String host; 
  //����Connection���á�Statement������������������
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	//���������ʾ��ǰѧ��ѧ�ŵ�����
	private String stu_id;
	//������Ϣ��ʾ��ǩ����
	private JLabel[] jlArray={new JLabel("ԭʼ����"),new JLabel("������"),new JLabel("ȷ��������"),
	                         };
	//�������������
	private JPasswordField[] jpfArray={new JPasswordField(),new JPasswordField(),new JPasswordField()
	                             };
	//����������ť����
	private JButton[] jbArray={new JButton("ȷ��"),new JButton("����")
	                          };
	//������
	public ChangePwd(String stu_id,String host)
	{
		this.host=host;
		this.stu_id=stu_id;
		//��ʼ��ҳ��
		this.initialFrame();
		//ע�������
		this.addListener();
	}
	//����ע��������ķ���
	public void addListener()
	{
		jpfArray[0].addActionListener(this);
		jpfArray[1].addActionListener(this);
		jpfArray[2].addActionListener(this);
		jbArray[0].addActionListener(this);
		jbArray[1].addActionListener(this);
	}
	//��ʼ��ҳ��ķ���
	public void initialFrame()
	{
		this.setLayout(null);
		for(int i=0;i<jlArray.length;i++)
		{
			jlArray[i].setBounds(30,20+50*i,150,30);
			this.add(jlArray[i]);
			jpfArray[i].setBounds(130,20+50*i,150,30);
			this.add(jpfArray[i]);
		}
		jbArray[0].setBounds(40,180,100,30);
		this.add(jbArray[0]);
		jbArray[1].setBounds(170,180,100,30);
		this.add(jbArray[1]);
	}
	//ʵ��ActionListener�ӿ��еķ���
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==jpfArray[0])
		{
			jpfArray[1].requestFocus(true);
		}
		else if(e.getSource()==jpfArray[1])
		{
			jpfArray[2].requestFocus(true);
		}
		else if(e.getSource()==jpfArray[2])
		{
			jbArray[0].requestFocus(true);
		}
		else if(e.getSource()==jbArray[1])
		{//�������ð�ť�Ĵ������
		    //��������Ϣ���
			for(int i=0;i<jpfArray.length;i++)
			{
				jpfArray[i].setText("");
			}
		}
		else if(e.getSource()==jbArray[0])
		{//����ȷ�ϰ�ť�Ĵ������
		    //�����ж������ʽ������ʽ�ַ���
			String patternStr="[0-9a-zA-Z]{6,12}";
			//��ȡ�û�����ľ�����
			String oldPwd=jpfArray[0].getText();
			if(oldPwd.equals(""))
			{//�������
				JOptionPane.showMessageDialog(this,"������ԭʼ����","����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			//��ȡ������
			String newPwd=jpfArray[1].getText();
			if(newPwd.equals(""))
			{//������Ϊ��
				JOptionPane.showMessageDialog(this,"������������","����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(!newPwd.matches(patternStr))
			{//�������ʽ����ȷ
				JOptionPane.showMessageDialog(this,"����ֻ����6��12λ����ĸ������","����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			//��ȡȷ������
			String newPwd1=jpfArray[2].getText();
			if(!newPwd.equals(newPwd1))
			{//��������ȷ�����벻ͬ
				JOptionPane.showMessageDialog(this,"ȷ�������������벻��","����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try
			{   //��ʼ�����ݿ����Ӳ���������
				this.initialConnection();
				String sql="update user_stu set pwd='"+newPwd+"' where stu_id='"+stu_id+"'"+
				           " and pwd='"+oldPwd+"'";
				int i=stmt.executeUpdate(sql);
				if(i==0)
				{//����ʧ����ʾ��Ϣ
					JOptionPane.showMessageDialog(this,"�޸�ʧ�ܣ��������������Ƿ���ȷ","����",JOptionPane.ERROR_MESSAGE);
				}
				else if(i==1)
				{//���ĳɹ���ʾ��Ϣ
					JOptionPane.showMessageDialog(this,"�����޸ĳɹ�","��ʾ",JOptionPane.INFORMATION_MESSAGE);
				}
				//�ر�����
				this.closeConn();
			}
			catch(Exception ea)
			{
				ea.printStackTrace();
			}
		}
	}
	public void setFocus()
	{
		jpfArray[0].requestFocus(true);
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