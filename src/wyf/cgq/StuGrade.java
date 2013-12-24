package wyf.cgq;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.util.*;
public class StuGrade extends JPanel
{
	private String host;
	//������־ѧ��ѧ�ŵı���
	private String stu_id;
	//������ʾ��ǩ
	private JLabel jl1=new JLabel("����ѧ�֣�");
	//����������ʾѧ�ֵı�ǩ
	private JLabel jl2=new JLabel("");
	//�������ؼ�������
	private JTable jt;
	private JScrollPane jsp;
	//�������ڴ�ű�����ݵ�Vector
	private Vector v_head=new Vector();
	private Vector v_data=new Vector();
	//����GetScore����
	private GetScore gs;
	//������
	public StuGrade(String stu_id,String host)
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
		//����gs�ķ���������гɼ���Ϣ
		v_data=gs.getAllScore(stu_id);
		//����gs�ķ����������ѧ��
		String xuefen=gs.getXueFen(stu_id)+"";
		jl2.setText(xuefen);
	}
	//��ʼ������ķ���
	public void initialFrame()
	{   //���������ӵ�����
		this.setLayout(null);
		DefaultTableModel dtm=new DefaultTableModel(v_data,v_head);
		jt=new JTable(dtm);
		jsp=new JScrollPane(jt);
		jsp.setBounds(60,30,500,500);
		this.add(jsp);
		jl1.setBounds(60,540,130,30);
		this.add(jl1);
		jl2.setBounds(200,540,130,30);
		this.add(jl2);
	}
}