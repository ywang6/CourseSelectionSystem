package wyf.cgq;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.net.*;
import java.io.*;
import javax.swing.tree.*;
public class TeacherClient extends JFrame
{
	//host=���ݿ�����IP+":"+�˿ں�
	private String host;
	//����ѧԺ��ŵ�����
	String coll_id;
	//�������ĸ����ڵ�
	private DefaultMutableTreeNode dmtnRoot=
	        new DefaultMutableTreeNode(new MyNode("����ѡ��","0"));
	private DefaultMutableTreeNode dmtn1=
	        new DefaultMutableTreeNode(new MyNode("ϵͳѡ��","1"));
	private DefaultMutableTreeNode dmtn2=
	        new DefaultMutableTreeNode(new MyNode("ѧ����Ϣ����","2"));
	private DefaultMutableTreeNode dmtn3=
	        new DefaultMutableTreeNode(new MyNode("�γ̹���","3"));
	private DefaultMutableTreeNode dmtn4=
	        new DefaultMutableTreeNode(new MyNode("�༶����","4"));
	private DefaultMutableTreeNode dmtn11=
	        new DefaultMutableTreeNode(new MyNode("�˳�","11"));
	private DefaultMutableTreeNode dmtn13=
	        new DefaultMutableTreeNode(new MyNode("�����޸�","13"));
	private DefaultMutableTreeNode dmtn21=
	        new DefaultMutableTreeNode(new MyNode("��������","21"));
	private DefaultMutableTreeNode dmtn22=
	        new DefaultMutableTreeNode(new MyNode("ѧ����Ϣ��ѯ","22"));
	private DefaultMutableTreeNode dmtn221=
	        new DefaultMutableTreeNode(new MyNode("������Ϣ��ѯ","221"));
	private DefaultMutableTreeNode dmtn222=
	        new DefaultMutableTreeNode(new MyNode("�ɼ���ѯ","222"));
	private DefaultMutableTreeNode dmtn31=
	        new DefaultMutableTreeNode(new MyNode("����ѡ������","31"));
	private DefaultMutableTreeNode dmtn32=
	        new DefaultMutableTreeNode(new MyNode("�γ̳ɼ�¼��","32"));
	private DefaultMutableTreeNode dmtn34=
	        new DefaultMutableTreeNode(new MyNode("��ӿγ�","34"));
	private DefaultMutableTreeNode dmtn42=
	        new DefaultMutableTreeNode(new MyNode("���Ӱ༶","42"));
	//�������ڵ�
	private DefaultTreeModel dtm=new DefaultTreeModel(dmtnRoot);
	//������״�б�ؼ�
	private JTree jt=new JTree(dtm);
	//������������
	private JScrollPane jspz=new JScrollPane(jt);
	//�������
	private JPanel jpy=new JPanel();
	//�����ָ��
	private JSplitPane jsp1=new JSplitPane(
		                    JSplitPane.HORIZONTAL_SPLIT,jspz,jpy);
	////��������ģ������(������佫�ں����ģ��Ŀ�����������һ��ӣ�
	private Welcome welcome;
	private ChangePwdTeacher changepwdteacher;
	private NewStu newstu;
	private TeachSearchInfo teachSearchInfo;
	private StuScore stuscore;
	private CourseManage coursemanage;
	private GradeInDB gradeindb;
	private NewCourse newcourse;
	private NewClass newclass;
    //������Ƭ��������
	CardLayout cl;
	public TeacherClient(String coll_id,String host)
	{
		this.host=host;
		this.coll_id=coll_id;
		//��ʼ����״�б�ؼ�
		this.initialTree();
		//����������ģ�����
		this.initialPanel();
		//Ϊ�ڵ�ע�������
		this.addListener();
		//��ʼ�����
		this.initialJpy();
		//��ʼ��������
		this.initialFrame();
	}
	
	public void initialPanel()
	{//��ʼ��������ģ��
	     //��ʼ�������ں����ģ��Ŀ�����������һ���
	     welcome=new Welcome("�ɼ�����ϵͳ");
	     changepwdteacher=new ChangePwdTeacher(host);
	     newstu=new NewStu(coll_id,host);
	     teachSearchInfo=new TeachSearchInfo(host);
	     stuscore=new StuScore(host);
	     coursemanage=new CourseManage(coll_id,host);
	     gradeindb=new GradeInDB(coll_id,host);
	     newcourse=new NewCourse(coll_id,host);
	     newclass=new NewClass(coll_id,host);
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
		dmtn2.add(dmtn21);
		dmtn2.add(dmtn22);
		dmtn22.add(dmtn221);
		dmtn22.add(dmtn222);
		dmtn3.add(dmtn31);
		dmtn3.add(dmtn32);
		dmtn3.add(dmtn34);
		dmtn4.add(dmtn42);	
	}
	public void initialJpy()
	{//��������ģ����ӵ������
	    //���������Ϊ��Ƭ����
		jpy.setLayout(new CardLayout());
		cl=(CardLayout)jpy.getLayout();
		//������ģ�齫�ں����ģ��Ŀ�����������һ���
		jpy.add(welcome,"welcome");
		jpy.add(changepwdteacher,"changepwdteacher");
		jpy.add(newstu,"newstu");
		jpy.add(teachSearchInfo,"teachSearchInfo");
		jpy.add(stuscore,"stuscore");
		jpy.add(coursemanage,"coursemanage");
		jpy.add(gradeindb,"gradeindb");
		jpy.add(newcourse,"newcourse");
		jpy.add(newclass,"newclass");
	}
	public void initialFrame()
	{   //���ô���ı��⡢��С����ɼ���
		this.add(jsp1);
		jsp1.setDividerLocation(200);
		jsp1.setDividerSize(4);
		Image image=new ImageIcon("ico.gif").getImage();  
		this.setIconImage(image);
		this.setTitle("��ʦ�ͻ���");
		Dimension screenSize = Toolkit.
		             getDefaultToolkit().getScreenSize();
		int centerX=screenSize.width/2;
		int centerY=screenSize.height/2;
		int w=900;//��������
		int h=650;//������߶�
		//���ô����������Ļ����
		this.setBounds(centerX-w/2,centerY-h/2-30,w,h);
		this.setVisible(true);
		//����ȫ��
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
	public void addListener()
	{//Ϊ��״�б�ؼ�ע������¼�������
		jt.addMouseListener(
	           new MouseAdapter()
	           {
	           	  public void mouseClicked(MouseEvent e)
	           	  { 
	           	      DefaultMutableTreeNode dmtntemp=
	           	      (DefaultMutableTreeNode)jt.
	           	      getLastSelectedPathComponent();
					  MyNode mynode=(MyNode)dmtntemp.getUserObject();
					  String id=mynode.getId();
					  //����idֵ��ʾ��ͬ�Ŀ�Ƭ
					  if(id.equals("0"))
					  {//��ӭҳ��
					        cl.show(jpy,"welcome");
					  }
	           	      else if(id.equals("11"))
	           	      {//�˳�ϵͳ
	           	            int i=JOptionPane.showConfirmDialog(jpy,
	           	                 "��ȷ��Ҫ�˳���ϵͳ��","ѯ��",
	           	                  JOptionPane.YES_NO_OPTION,
	           	                   JOptionPane.QUESTION_MESSAGE);
	           	      		if(i==0)
	           	      		{
	           	      			System.exit(0);
	           	      		}
	           	      }
	           	      else if(id.equals("13"))
	           	      {//��������ҳ��
	           	      	 cl.show(jpy,"changepwdteacher");
	           	      	 changepwdteacher.setFocus();
	           	      }
	           	      else if(id.equals("21"))
	           	      {//���ѧ��ҳ��
	           	      	 cl.show(jpy,"newstu");
	           	      	 newstu.setFocus();
	           	      }
	           	      else if(id.equals("221"))
	           	      {//ѧ����Ϣ��ѯҳ��
	           	      	 cl.show(jpy,"teachSearchInfo");
	           	      	 teachSearchInfo.setFocus();
	           	      }
	           	      else if(id.equals("222"))
	           	      {//�ɼ���ѯҳ��
	           	      	 cl.show(jpy,"stuscore");
	           	      	 stuscore.setFocus();
	           	      }
	           	      else if(id.equals("31"))
	           	      {//ѡ�ι���ҳ��
	           	      	 cl.show(jpy,"coursemanage");
	           	      	 coursemanage.updateTable();
	           	      }
	           	      else if(id.equals("32"))
	           	      {//�ɼ�¼��ҳ��
	           	      	 cl.show(jpy,"gradeindb");
	           	      }
	           	      else if(id.equals("34"))
	           	      {//��ӿγ�ҳ��
	           	      	   cl.show(jpy,"newcourse");
	           	      }
	           	      else if(id.equals("42"))
	           	      {//��Ӱ༶ҳ��
	           	      	  cl.show(jpy,"newclass");
	           	      	  newclass.setFocus();
	           	      }
	              }
	           }
		                       );
		//��չ���ڵ�������������Ϊ1
		jt.setToggleClickCount(1);
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
	public static void main(String args[])	
	{
		new TeacherClient("01","127.0.0.1:3306");
	}
}