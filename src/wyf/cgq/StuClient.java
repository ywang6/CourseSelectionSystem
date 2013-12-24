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
	//声明标志学生学号的变量
	private String stu_id;
	//创建树的各个节点
	private DefaultMutableTreeNode dmtnRoot=new DefaultMutableTreeNode(new MyNode("操作选项","0"));
	private DefaultMutableTreeNode dmtn1=new DefaultMutableTreeNode(new MyNode("系统选项","1"));
	private DefaultMutableTreeNode dmtn2=new DefaultMutableTreeNode(new MyNode("个人基本信息","2"));
	private DefaultMutableTreeNode dmtn3=new DefaultMutableTreeNode(new MyNode("学生选课","3"));
	private DefaultMutableTreeNode dmtn4=new DefaultMutableTreeNode(new MyNode("成绩查询","4"));
	private DefaultMutableTreeNode dmtn11=new DefaultMutableTreeNode(new MyNode("退出","11"));
	private DefaultMutableTreeNode dmtn13=new DefaultMutableTreeNode(new MyNode("密码修改","13"));
	private DefaultMutableTreeNode dmtn31=new DefaultMutableTreeNode(new MyNode("选课","31"));
	private DefaultMutableTreeNode dmtn32=new DefaultMutableTreeNode(new MyNode("课表显示","32"));
	private DefaultMutableTreeNode dmtn41=new DefaultMutableTreeNode(new MyNode("已修课程成绩","41"));
	private DefaultMutableTreeNode dmtn42=new DefaultMutableTreeNode(new MyNode("不及格课程成绩","42"));
	//创建根节点
	private DefaultTreeModel dtm=new DefaultTreeModel(dmtnRoot);
	//创建树状列表控件
	private JTree jtree=new JTree(dtm);
	private JScrollPane jspz=new JScrollPane(jtree);
	//创建存放个功能模块面板
	private JPanel jpy=new JPanel();
	private JSplitPane jsp1=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,jspz,jpy);
	//声明卡片布局的引用
	private CardLayout cl;
	//生命欢迎页面
	private Welcome welcome;
	//声明个功能模块的引用
	//选课模块的引用
	private ChoseCourse chosecourse;
	//课表显示模块的引用
	private CourseTable coursetable;
	//学生成绩查询页面
	private StuGrade stugrade;
	//不及格成绩查询页面
	private StuFailGrade stufailgrade;
	//基本信息查询页面
	private StuInfo stuinfo;
	//密码更改页面
	private ChangePwd changepwd;
	//构造器
	public StuClient(String stu_id,String host)
	{
		this.host=host;
		this.stu_id=stu_id;
		//初始化树状列表控件
		this.initialTree();
		//初始化个功能模块面板
		this.initialPane();
		//初始化主功能面板，其他面板都一卡
		//片布局的形式存在与该面板
		this.initialJpy();
		//为控件注册监听器
		this.addListener();
		//初始化窗体
		this.initialFrame();
	}
	//主面板的初始化方法
	public void initialJpy()
	{//将各功能模块以卡片布局的形式存入主面板
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
	//初始化各功能模块的方法
	public void initialPane()
	{
		welcome=new Welcome("学生成绩管理系统");
		chosecourse=new ChoseCourse(stu_id,host);
		coursetable=new CourseTable(stu_id,host);
		stugrade=new StuGrade(stu_id,host);
		stufailgrade=new StuFailGrade(stu_id,host);
		stuinfo=new StuInfo(stu_id,host);
		changepwd=new ChangePwd(stu_id,host);
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
		dmtn3.add(dmtn31);
		dmtn3.add(dmtn32);
		dmtn4.add(dmtn41);
		dmtn4.add(dmtn42);
		jtree.setToggleClickCount(1);
	}
	//为树状列表控件注册鼠标事件监听器的方法
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
					  //根据id值显示不同的卡片
					  if(id.equals("0"))
					  {
					  	    cl.show(jpy,"welcome");
					  }
               	      else if(id.equals("11"))
               	      {
               	      	    int i=JOptionPane.showConfirmDialog(jpy,"您确认要退出出系统吗？","询问",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
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
               	      	    //在显示之后立即更新数据
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
	//初始化窗体的方法
	public void initialFrame()
	{
		this.add(jsp1);
		jsp1.setDividerLocation(200);
		jsp1.setDividerSize(4);
		//设置窗体的标题、大小及其可见性
		this.setTitle("学生客户端");
		Image image=new ImageIcon("ico.gif").getImage();  
 		this.setIconImage(image);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX=screenSize.width/2;
		int centerY=screenSize.height/2;
		int w=900;//本窗体宽度
		int h=650;//本窗体高度
		this.setBounds(centerX-w/2,centerY-h/2-30,w,h);//设置窗体出现在屏幕中央
		this.setVisible(true);
		//窗体全屏
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
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
}