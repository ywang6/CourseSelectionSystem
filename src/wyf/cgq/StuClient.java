package wyf.cgq;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.net.*;
import java.io.*;
import javax.swing.tree.*;
public class StuClient extends JFrame
{
	private String host;
	//������־ѧ��ѧ�ŵı���
	private String stu_id;
	//�������ĸ����ڵ�
	private DefaultMutableTreeNode dmtnRoot=new DefaultMutableTreeNode(new MyNode("����ѡ��","0"));
	private DefaultMutableTreeNode dmtn1=new DefaultMutableTreeNode(new MyNode("ϵͳѡ��","1"));
	private DefaultMutableTreeNode dmtn2=new DefaultMutableTreeNode(new MyNode("���˻�����Ϣ","2"));
	private DefaultMutableTreeNode dmtn3=new DefaultMutableTreeNode(new MyNode("ѧ��ѡ��","3"));
	private DefaultMutableTreeNode dmtn4=new DefaultMutableTreeNode(new MyNode("�ɼ���ѯ","4"));
	private DefaultMutableTreeNode dmtn11=new DefaultMutableTreeNode(new MyNode("�˳�","11"));
	private DefaultMutableTreeNode dmtn13=new DefaultMutableTreeNode(new MyNode("�����޸�","13"));
	private DefaultMutableTreeNode dmtn31=new DefaultMutableTreeNode(new MyNode("ѡ��","31"));
	private DefaultMutableTreeNode dmtn32=new DefaultMutableTreeNode(new MyNode("�α���ʾ","32"));
	private DefaultMutableTreeNode dmtn41=new DefaultMutableTreeNode(new MyNode("���޿γ̳ɼ�","41"));
	private DefaultMutableTreeNode dmtn42=new DefaultMutableTreeNode(new MyNode("������γ̳ɼ�","42"));
	//�������ڵ�
	private DefaultTreeModel dtm=new DefaultTreeModel(dmtnRoot);
	//������״�б�ؼ�
	private JTree jtree=new JTree(dtm);
	private JScrollPane jspz=new JScrollPane(jtree);
	//������Ÿ�����ģ�����
	private JPanel jpy=new JPanel();
	private JSplitPane jsp1=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,jspz,jpy);
	//������Ƭ���ֵ�����
	private CardLayout cl;
	//������ӭҳ��
	private Welcome welcome;
	//����������ģ�������
	//ѡ��ģ�������
	private ChoseCourse chosecourse;
	//�α���ʾģ�������
	private CourseTable coursetable;
	//ѧ���ɼ���ѯҳ��
	private StuGrade stugrade;
	//������ɼ���ѯҳ��
	private StuFailGrade stufailgrade;
	//������Ϣ��ѯҳ��
	private StuInfo stuinfo;
	//�������ҳ��
	private ChangePwd changepwd;
	//������
	public StuClient(String stu_id,String host)
	{
		this.host=host;
		this.stu_id=stu_id;
		//��ʼ����״�б�ؼ�
		this.initialTree();
		//��ʼ��������ģ�����
		this.initialPane();
		//��ʼ����������壬������嶼һ��
		//Ƭ���ֵ���ʽ����������
		this.initialJpy();
		//Ϊ�ؼ�ע�������
		this.addListener();
		//��ʼ������
		this.initialFrame();
	}
	//�����ĳ�ʼ������
	public void initialJpy()
	{//��������ģ���Կ�Ƭ���ֵ���ʽ���������
		jpy.setLayout(new CardLayout());
		cl=(CardLayout)jpy.getLayout();
		jpy.add(welcome,"welcome");
		welcome.setBackground(Color.red);
		jpy.add(welcome,"welcome");
		jpy.add(chosecourse,"chosecourse");
		jpy.add(coursetable,"coursetable");
		jpy.add(stugrade,"stugrade");
		jpy.add(stufailgrade,"stufailgrade");
		jpy.add(stuinfo,"stuinfo");
		jpy.add(changepwd,"changepwd");
	}
	//��ʼ��������ģ��ķ���
	public void initialPane()
	{
		welcome=new Welcome("ѧ���ɼ�����ϵͳ");
		chosecourse=new ChoseCourse(stu_id,host);
		coursetable=new CourseTable(stu_id,host);
		stugrade=new StuGrade(stu_id,host);
		stufailgrade=new StuFailGrade(stu_id,host);
		stuinfo=new StuInfo(stu_id,host);
		changepwd=new ChangePwd(stu_id,host);
	}
	//��ʼ����״�б�ؼ��ķ���
	public void initialTree()
	{
		dmtnRoot.add(dmtn1);
		dmtnRoot.add(dmtn2);
		dmtnRoot.add(dmtn3);
		dmtnRoot.add(dmtn4);
		dmtn1.add(dmtn11);
		dmtn1.add(dmtn13);
		dmtn3.add(dmtn31);
		dmtn3.add(dmtn32);
		dmtn4.add(dmtn41);
		dmtn4.add(dmtn42);
		jtree.setToggleClickCount(1);
	}
	//Ϊ��״�б�ؼ�ע������¼��������ķ���
	public void addListener()
	{
		jtree.addMouseListener(
               new MouseAdapter()
               {
               	  public void mouseClicked(MouseEvent e)
               	  { 
               	      DefaultMutableTreeNode dmtntemp=(DefaultMutableTreeNode)jtree.getLastSelectedPathComponent();
					  MyNode mynode=(MyNode)dmtntemp.getUserObject();
					  String id=mynode.getId();
					  //����idֵ��ʾ��ͬ�Ŀ�Ƭ
					  if(id.equals("0"))
					  {
					  	    cl.show(jpy,"welcome");
					  }
               	      else if(id.equals("11"))
               	      {
               	      	    int i=JOptionPane.showConfirmDialog(jpy,"��ȷ��Ҫ�˳���ϵͳ��","ѯ��",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
               	      		if(i==0)
               	      		{
               	      			System.exit(0);
               	      		}
               	      		
               	      }
               	      else if(id.equals("13"))
               	      {
               	      		cl.show(jpy,"changepwd");
               	      		changepwd.setFocus();
               	      }
               	      else if(id.equals("2"))
               	      {
               	      		cl.show(jpy,"stuinfo");
               	      }
               	      else if(id.equals("31"))
               	      {
               	      		cl.show(jpy,"chosecourse");
               	      }
               	      else if(id.equals("32"))
               	      {
               	      	    //����ʾ֮��������������
               	      		cl.show(jpy,"coursetable");
               	      		coursetable.initialData();
               	      		coursetable.updataview();
               	      }
               	      else if(id.equals("41"))
               	      {
               	      		cl.show(jpy,"stugrade");
               	      }
               	      else if(id.equals("42"))
               	      {
               	      		cl.show(jpy,"stufailgrade");
               	      }
	              }
	           }
		                       );
	}	
	//��ʼ������ķ���
	public void initialFrame()
	{
		this.add(jsp1);
		jsp1.setDividerLocation(200);
		jsp1.setDividerSize(4);
		//���ô���ı��⡢��С����ɼ���
		this.setTitle("ѧ���ͻ���");
		Image image=new ImageIcon("ico.gif").getImage();  
 		this.setIconImage(image);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX=screenSize.width/2;
		int centerY=screenSize.height/2;
		int w=900;//��������
		int h=650;//������߶�
		this.setBounds(centerX-w/2,centerY-h/2-30,w,h);//���ô����������Ļ����
		this.setVisible(true);
		//����ȫ��
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
	//�Զ���ĳ�ʼ�����ڵ�����ݶ������
	class MyNode
	{
		private String values;
		private String id;
		public MyNode(String values,String id)
		{
			this.values=values;
			this.id=id;
		}
		public String toString()
		{
			return this.values;
		}
		public String getId()
		{
			return this.id;
		}
	}		
}