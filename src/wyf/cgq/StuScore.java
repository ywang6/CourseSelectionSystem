package wyf.cgq;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.util.*;
public class StuScore extends JPanel implements ActionListener
{
	private String host;
	//������ʾ��ǩ
	private JLabel jl=new JLabel("������ѧ��ѧ�ţ�");
	private JLabel jl1=new JLabel("����ѧ�֣�");
	//����������ʾѧ�ֵı�ǩ
	private JLabel jl2=new JLabel("");
	//������������ѧ�ŵ��ı���
	private JTextField jtf=new JTextField();
	//����������ť
	private JButton jb=new JButton("��ѯ");
	//�����������
	private JTable jt;
	private JScrollPane jsp;
	//�������ڴ�ű�����ݵ�Vector
	private Vector v_head=new Vector();
	private Vector v_data=new Vector();
	//����GetScore����
	private GetScore gs;
	//������
	public StuScore(String host)
	{
		this.host=host;
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
	}
	//��ʼ������ķ���
	public void initialFrame()
	{ //���������ӵ�����
		this.setLayout(null);
		jl.setBounds(60,20,150,30);
		this.add(jl);
		jtf.setBounds(195,20,150,30);
		this.add(jtf);
		jtf.addActionListener(this);
		jb.setBounds(350,20,100,30);
		this.add(jb);
		jb.addActionListener(this);
		DefaultTableModel dtm=new DefaultTableModel(v_data,v_head);
		jt=new JTable(dtm);
		jsp=new JScrollPane(jt);
		jsp.setBounds(60,60,500,500);
		this.add(jsp);
		jl1.setBounds(60,570,130,30);
		this.add(jl1);
		jl2.setBounds(200,570,130,30);
		this.add(jl2);
	}
	//����¼��������ķ���
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==jb||e.getSource()==jtf)
		{//���²�ѯ��ť�Ĵ������
		//��������ѧ�� 
			String stu_id=jtf.getText().trim();
			if(stu_id.equals(""))
			{//����ѧ��Ϊ��
				JOptionPane.showMessageDialog(this,"������ѧ��ѧ��","����",
				                               JOptionPane.ERROR_MESSAGE);
				return;
			}
			else
			{
				//����ѧ�ŵ���GetScore�ķ�����ø�ѧ���ĳɼ���Ϣ
				v_data=gs.getAllScore(stu_id);
				//���±��ģ��
				DefaultTableModel dtm=new DefaultTableModel(v_data,v_head);
				jt.setModel(dtm);
				//������ʾ
				((DefaultTableModel)jt.getModel()).fireTableStructureChanged();
				String xuefen=gs.getXueFen(stu_id)+"";
				//��ʾѧ����Ϣ
				jl2.setText(xuefen);
			}
		}
	}
	public void setFocus()
	{
		this.jtf.requestFocus(true);
	}
	public static void main(String args[])
	{
		StuScore ss=new StuScore("127.0.0.1:3306");
		JFrame jf=new JFrame();
		jf.setBounds(10,10,700,650);
		jf.add(ss);
		jf.setVisible(true);
	}
}
