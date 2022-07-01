package com.ifbiu;

import com.google.gson.Gson;
import com.ifbiu.entity.DayInfoEntity;
import com.ifbiu.entity.SignEntity;
import com.ifbiu.util.RequestMethod;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClockHandle extends JFrame implements ActionListener
{

	    private String name;
	    private String studentNum;
	    private String token;
	     //定义组件
		JButton jb1,jb2=null;
		JPanel jp1,jp2,jp3,jp4,jp5=null;
		JLabel jlb1,jlb2,jlb3,jlb4,jlb5,jlb6=null;
	    JTextField jtf,jdf = null;
	    String am,pm = "未打卡";

	    //构造函数
		public ClockHandle(String name, String studentNum, String token)    //不能申明为void!!!!!否则弹不出新界面
		{
			this.name= name;
			this.studentNum= studentNum;
			this.token= token;

			filterSign();

			//创建组件
			jb1=new JButton("上午打卡");
			jb2=new JButton("下午打卡");

			jb1.addActionListener(this);
			jb2.addActionListener(this);
			
			jp1=new JPanel();
			jp4=new JPanel();
			jp5=new JPanel();
			jp2=new JPanel();
			jp3=new JPanel();


			jlb1=new JLabel("姓名："+name);
			jlb2=new JLabel("学号："+studentNum);
			jlb3=new JLabel(am);
			jlb4=new JLabel(pm);
			jlb5=new JLabel("体温：");
			jlb6=new JLabel("打卡地址：");

			jtf = new JTextField("36.1",10);
			jdf = new JTextField(20);

			jp1.add(jlb1);
			jp1.add(jlb2);

			jp4.add(jlb5);
			jp4.add(jtf);

			jp5.add(jlb6);
			jp5.add(jdf);

			jp2.add(jb1);
			jp2.add(jlb3);
			
			jp3.add(jb2);
			jp3.add(jlb4);
			
			
			this.add(jp1);
			this.add(jp4);
			this.add(jp5);
			this.add(jp2);
			this.add(jp3);

			//设置布局管理器
			this.setLayout(new GridLayout(5,3,50,50));
			this.setTitle("农职疫情打卡");
			this.setSize(400,500);
			this.setLocation(200, 200);		
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setVisible(true);

}
		@Override
		public void actionPerformed(ActionEvent e) {
			if (jtf.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "请输入体温", "提示消息", JOptionPane.WARNING_MESSAGE);
			} else if (jdf.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "请输入打卡地址", "提示消息", JOptionPane.WARNING_MESSAGE);
			}else{
				if (e.getActionCommand().equals("上午打卡")) {
					signClick("sign");
				}
				if (e.getActionCommand().equals("下午打卡")) {
					signClick("sign2");
				}
			}
		}

		public void filterSign(){
			DayInfoEntity[] dayInfoEntities = init(token);
			if (dayInfoEntities.length==1){
				if (dayInfoEntities[0].getSign().equals("sign")){
					am = dayInfoEntities[0].getTemperature();
				}else{
					pm = dayInfoEntities[0].getTemperature();
				}
			}
			if (dayInfoEntities.length==2){
				if (dayInfoEntities[0].getSign().equals("sign")){
					am = dayInfoEntities[0].getTemperature();
					pm = dayInfoEntities[1].getTemperature();
				}else{
					am = dayInfoEntities[1].getTemperature();
					pm = dayInfoEntities[0].getTemperature();
				}
			}
		}

		public void signClick(String sign){
			String msg = "";
			if (sign.equals("sign")){
				msg = "上午";
			}
			if (sign.equals("sign2")){
				msg = "下午";
			}
			String data = "{\"sign\":\"" + sign+"\",\"temperature\":\""+ jtf.getText() +"\",\"address\":\""+jdf.getText()+"\"}";
			String httpRequestData = RequestMethod.restCallerPost("https://zyxsgl.imau.edu.cn/api/student/healthSign/save", data,token);
			Gson g = new Gson();
			SignEntity signEntity = g.fromJson(httpRequestData, SignEntity.class);
			if (signEntity.getMsg().equals("数据库中已存在该记录")){
				JOptionPane.showMessageDialog(null, "您今天"+msg+"已经打过卡了！", "提示消息", JOptionPane.WARNING_MESSAGE);
			}
			if (signEntity.getMsg().equals("success")){
				JOptionPane.showMessageDialog(null, "恭喜您打卡成功！", "提示消息", JOptionPane.WARNING_MESSAGE);
				this.setVisible(false);
				new ClockHandle(name,studentNum,token).setVisible(true);
			}
		}

		public DayInfoEntity[] init(String token){
			String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			String httpRequestData = RequestMethod.restCallerGet("https://zyxsgl.imau.edu.cn/api/student/healthSign/getByDate/"+time, "", token);
			try {
				httpRequestData = new String(httpRequestData.getBytes("GBK"), "utf-8");
			} catch (UnsupportedEncodingException unsupportedEncodingException) {
				unsupportedEncodingException.printStackTrace();
			}
			Gson g1 = new Gson();
			return g1.fromJson(httpRequestData, DayInfoEntity[].class);
		}
}
