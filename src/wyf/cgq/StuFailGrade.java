package wyf.cgq;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.util.*;
public class StuFailGrade extends JPanel
{
	private String host;
	//������־ѧ��ѧ�ŵı���
	private String stu_id;
	//�������ؼ�������
	private JTable jt;
	private JScrollPane jsp;
	//�������ڴ�ű�����ݵ�Vector
	private Vector v_head=new Vector();
	private Vector v_data=new Vector();
	//����GetScore����
	private GetScore gs;
	//������
	public StuFailGrade(String stu_id,String host)
	{
		this.host=host;
		this.stu_id=stu_id;
		gs=new GetScore(host);
		//��ʼ������
		this.initialData();
		//��ʼ������
		this.initialFrame();
	}
	//��ʼ�����ݵķ���
	public void initialData()
	{
		//��ʼ����ͷ
		v_head.add("�γ���");
		v_head.add("����");
		v_head.add("ѧ��");
		//����gs�ķ�����ò�����ɼ���Ϣ
		v_data=gs.getFailScore(stu_id);
	}
	//��ʼ������ķ���
	public void initialFrame()
	{
		this.setLayout(null);
		DefaultTableModel dtm=new DefaultTableModel(v_data,v_head);
		jt=new JTable(dtm);
		jsp=new JScrollPane(jt);
		jsp.setBounds(60,30,500,500);
		this.add(jsp);
	}
}