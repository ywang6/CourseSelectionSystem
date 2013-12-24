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
	//host=数据库主机IP+":"+端口号
	private String host;
	//声明学院编号的引用
	String coll_id;
	//创建树的各个节点
	private DefaultMutableTreeNode dmtnRoot=
	        new DefaultMutableTreeNode(new MyNode("操作选项","0"));
	private DefaultMutableTreeNode dmtn1=
	        new DefaultMutableTreeNode(new MyNode("系统选项","1"));
	private DefaultMutableTreeNode dmtn2=
	        new DefaultMutableTreeNode(new MyNode("学生信息管理","2"));
	private DefaultMutableTreeNode dmtn3=
	        new DefaultMutableTreeNode(new MyNode("课程管理","3"));
	private DefaultMutableTreeNode dmtn4=
	        new DefaultMutableTreeNode(new MyNode("班级设置","4"));
	private DefaultMutableTreeNode dmtn11=
	        new DefaultMutableTreeNode(new MyNode("退出","11"));
	private DefaultMutableTreeNode dmtn13=
	        new DefaultMutableTreeNode(new MyNode("密码修改","13"));
	private DefaultMutableTreeNode dmtn21=
	        new DefaultMutableTreeNode(new MyNode("新生报到","21"));
	private DefaultMutableTreeNode dmtn22=
	        new DefaultMutableTreeNode(new MyNode("学生信息查询","22"));
	private DefaultMutableTreeNode dmtn221=
	        new DefaultMutableTreeNode(new MyNode("基本信息查询","221"));
	private DefaultMutableTreeNode dmtn222=
	        new DefaultMutableTreeNode(new MyNode("成绩查询","222"));
	private DefaultMutableTreeNode dmtn31=
	        new DefaultMutableTreeNode(new MyNode("开课选项设置","31"));
	private DefaultMutableTreeNode dmtn32=
	        new DefaultMutableTreeNode(new MyNode("课程成绩录入","32"));
	private DefaultMutableTreeNode dmtn34=
	        new DefaultMutableTreeNode(new MyNode("添加课程","34"));
	private DefaultMutableTreeNode dmtn42=
	        new DefaultMutableTreeNode(new MyNode("增加班级","42"));
	//创建跟节点
	private DefaultTreeModel dtm=new DefaultTreeModel(dmtnRoot);
	//创建树状列表控件
	private JTree jt=new JTree(dtm);
	//创建滚动窗口
	private JScrollPane jspz=new JScrollPane(jt);
	//创建面板
	private JPanel jpy=new JPanel();
	//创建分割窗格
	private JSplitPane jsp1=new JSplitPane(
		                    JSplitPane.HORIZONTAL_SPLIT,jspz,jpy);
	////声明功能模块引用(声明语句将在后面各模块的开发过程中逐一添加）
	private Welcome welcome;
	private ChangePwdTeacher changepwdteacher;
	private NewStu newstu;
	private TeachSearchInfo teachSearchInfo;
	private StuScore stuscore;
	private CourseManage coursemanage;
	private GradeInDB gradeindb;
	private NewCourse newcourse;
	private NewClass newclass;
    //声明卡片布局引用
	CardLayout cl;
	public TeacherClient(String coll_id,String host)
	{
		this.host=host;
		this.coll_id=coll_id;
		//初始化树状列表控件
		this.initialTree();
		//创建各功能模块对象
		this.initialPanel();
		//为节点注册监听器
		this.addListener();
		//初始化面板
		this.initialJpy();
		//初始化主窗体
		this.initialFrame();
	}
	
	public void initialPanel()
	{//初始化各功能模块
	     //初始化代码在后面各模块的开发过程中逐一添加
	     welcome=new Welcome("成绩管理系统");
	     changepwdteacher=new ChangePwdTeacher(host);
	     newstu=new NewStu(coll_id,host);
	     teachSearchInfo=new TeachSearchInfo(host);
	     stuscore=new StuScore(host);
	     coursemanage=new CourseManage(coll_id,host);
	     gradeindb=new GradeInDB(coll_id,host);
	     newcourse=new NewCourse(coll_id,host);
	     newclass=new NewClass(coll_id,host);
	}
	//初始化树状列表控件的方法
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
	{//将各功能模块添加到面板中
	    //将面板设置为卡片布局
		jpy.setLayout(new CardLayout());
		cl=(CardLayout)jpy.getLayout();
		//各功能模块将在后面各模块的开发过程中逐一添加
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
	{   //设置窗体的标题、大小及其可见性
		this.add(jsp1);
		jsp1.setDividerLocation(200);
		jsp1.setDividerSize(4);
		Image image=new ImageIcon("ico.gif").getImage();  
		this.setIconImage(image);
		this.setTitle("教师客户端");
		Dimension screenSize = Toolkit.
		             getDefaultToolkit().getScreenSize();
		int centerX=screenSize.width/2;
		int centerY=screenSize.height/2;
		int w=900;//本窗体宽度
		int h=650;//本窗体高度
		//设置窗体出现在屏幕中央
		this.setBounds(centerX-w/2,centerY-h/2-30,w,h);
		this.setVisible(true);
		//窗体全屏
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
	public void addListener()
	{//为树状列表控件注册鼠标事件监听器
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
					  //根据id值显示不同的卡片
					  if(id.equals("0"))
					  {//欢迎页面
					        cl.show(jpy,"welcome");
					  }
	           	      else if(id.equals("11"))
	           	      {//退出系统
	           	            int i=JOptionPane.showConfirmDialog(jpy,
	           	                 "您确认要退出出系统吗？","询问",
	           	                  JOptionPane.YES_NO_OPTION,
	           	                   JOptionPane.QUESTION_MESSAGE);
	           	      		if(i==0)
	           	      		{
	           	      			System.exit(0);
	           	      		}
	           	      }
	           	      else if(id.equals("13"))
	           	      {//更改密码页面
	           	      	 cl.show(jpy,"changepwdteacher");
	           	      	 changepwdteacher.setFocus();
	           	      }
	           	      else if(id.equals("21"))
	           	      {//添加学生页面
	           	      	 cl.show(jpy,"newstu");
	           	      	 newstu.setFocus();
	           	      }
	           	      else if(id.equals("221"))
	           	      {//学生信息查询页面
	           	      	 cl.show(jpy,"teachSearchInfo");
	           	      	 teachSearchInfo.setFocus();
	           	      }
	           	      else if(id.equals("222"))
	           	      {//成绩查询页面
	           	      	 cl.show(jpy,"stuscore");
	           	      	 stuscore.setFocus();
	           	      }
	           	      else if(id.equals("31"))
	           	      {//选课管理页面
	           	      	 cl.show(jpy,"coursemanage");
	           	      	 coursemanage.updateTable();
	           	      }
	           	      else if(id.equals("32"))
	           	      {//成绩录入页面
	           	      	 cl.show(jpy,"gradeindb");
	           	      }
	           	      else if(id.equals("34"))
	           	      {//添加课程页面
	           	      	   cl.show(jpy,"newcourse");
	           	      }
	           	      else if(id.equals("42"))
	           	      {//添加班级页面
	           	      	  cl.show(jpy,"newclass");
	           	      	  newclass.setFocus();
	           	      }
	              }
	           }
		                       );
		//将展开节点的鼠标点击次数设为1
		jt.setToggleClickCount(1);
	}
	//自定义的初始化树节点的数据对象的类
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