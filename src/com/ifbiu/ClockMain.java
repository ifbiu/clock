package com.ifbiu;

import com.google.gson.Gson;
import com.ifbiu.entity.LoginEntity;
import com.ifbiu.entity.UserEntity;
import com.ifbiu.util.RequestMethod;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;

public class ClockMain extends JFrame implements ActionListener {

    //定义登录界面的组件
    JButton jb1, jb2, jb3 = null;
    JRadioButton jrb1, jrb2 = null;
    JPanel jp1, jp2, jp4 = null;
    JTextField jtf = null;
    JLabel jlb1, jlb2 = null;
    JPasswordField jpf = null;
    ButtonGroup bg = null;

    //菜单项
    JMenuBar jmb = null;
    JMenu jm = null;
    JMenuItem jmi1, jmi2,jmi3,jmi4,jmi5 = null;


    public static void main(String[] args) {
        ClockMain ms = new ClockMain();
    }

    //构造函数
    public ClockMain() {
        //创建组件
        jb1 = new JButton("登录");
        jb2 = new JButton("重置");
        jb3 = new JButton("退出");
        //设置监听
        jb1.addActionListener(this);
        jb2.addActionListener(this);
        jb3.addActionListener(this);

        jmb = new JMenuBar(); //JMenuBar指菜单栏
        jm = new JMenu("开发者"); //JMenu是菜单栏中的选项栏
        jmi1 = new JMenuItem("王昊"); //JMenuItem指选项栏中的选项
        jmi2 = new JMenuItem("林英");
        jmi3 = new JMenuItem("田永杰");
        jmi4 = new JMenuItem("李晓雪");
        jmi5 = new JMenuItem("康美玲");
        jm.add(jmi1);
        jm.add(jmi2);
        jm.add(jmi3);
        jm.add(jmi4);
        jm.add(jmi5);
        jmb.add(jm);


        bg = new ButtonGroup();
        bg.add(jrb1);
        bg.add(jrb2);
//		jrb2.setSelected(true);

        jp1 = new JPanel();
        jp2 = new JPanel();
        jp4 = new JPanel();

        jlb1 = new JLabel("用户名：");
        jlb2 = new JLabel("密    码：");

        jtf = new JTextField(10);
        jpf = new JPasswordField(10);
        //加入到JPanel中
        jp1.add(jlb1);
        jp1.add(jtf);

        jp2.add(jlb2);
        jp2.add(jpf);

        jp4.add(jb1);
        jp4.add(jb2);
        jp4.add(jb3);

        //加入JFrame中
        this.setJMenuBar(jmb);
        this.add(jp1);
        this.add(jp2);
        this.add(jp4);
        //设置布局管理器
        this.setLayout(new GridLayout(4, 1));
        //给窗口设置标题
        this.setTitle("农职疫情打卡");
        //设置窗体大小
        this.setSize(300, 250);
        //设置窗体初始位置
        this.setLocation(200, 150);
        //设置当关闭窗口时，保证JVM也退出
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //显示窗体
        this.setVisible(true);
        this.setResizable(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("退出")) {
            System.exit(0);
        } else if (e.getActionCommand().equals("登录")) {
            clickLogin();
        } else if (e.getActionCommand().equals("重置")) {
            this.clear();
        }

    }

    //清空文本框和密码框
    public void clear() {
        jtf.setText("");
        jpf.setText("");
    }

    //学生登录判断方法
    public void stulogin(String name,String studentNum,String token) {
        JOptionPane.showMessageDialog(null, "登录成功！欢迎你 "+name, "提示消息", JOptionPane.WARNING_MESSAGE);
        this.clear();
        //关闭当前界面
        dispose();
        //创建一个新界面
        ClockHandle ui = new ClockHandle(name,studentNum,token);
    }

    public void clickLogin(){
        if (!jtf.getText().isEmpty() && !jpf.getText().isEmpty()) {
            String data = "{\"username\":\""+jtf.getText()+"\",\"password\":\""+jpf.getText()+"\"}";
            String httpRequestData = RequestMethod.restCallerPost("https://zyxsgl.imau.edu.cn/api/auth/appLogin", data,null);
            Gson g = new Gson();
            LoginEntity loginEntity = g.fromJson(httpRequestData, LoginEntity.class);

            if (loginEntity.getMsg().equals("用户不存在/密码错误")) {
                JOptionPane.showMessageDialog(null, "用户不存在或密码错误！", "提示消息", JOptionPane.WARNING_MESSAGE);
                this.clear();
            } else {
                String httpRequestData1 = RequestMethod.restCallerGet("https://zyxsgl.imau.edu.cn/api/student/student/get/" + loginEntity.getUserId(), "", loginEntity.getToken());
                if (httpRequestData1.equals("")){
                    JOptionPane.showMessageDialog(null, "教务系统升级中...请稍后再试！", "提示消息", JOptionPane.WARNING_MESSAGE);
                    this.clear();
                }else{
                    try {
                        httpRequestData1 = new String(httpRequestData1.getBytes("GBK"), "utf-8");
                    } catch (UnsupportedEncodingException unsupportedEncodingException) {
                        unsupportedEncodingException.printStackTrace();
                    }
                    Gson g1 = new Gson();
                    UserEntity userEntity = g1.fromJson(httpRequestData1, UserEntity.class);
                    this.stulogin(userEntity.getStudentName(),userEntity.getStudentNum(),loginEntity.getToken());
                }
            }

        } else if (jtf.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "请输入用户名", "提示消息", JOptionPane.WARNING_MESSAGE);
            this.clear();
        } else if (jpf.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "请输入密码", "提示消息", JOptionPane.WARNING_MESSAGE);
            this.clear();
        }
    }

}






	
