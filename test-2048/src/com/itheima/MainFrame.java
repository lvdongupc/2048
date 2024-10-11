package com.itheima;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class MainFrame extends JFrame implements KeyListener {

    int[][] data=new int[4][4];
    public  void initData()
    {
        generatorNum();
        generatorNum();
    }
    int loseFlag=1;
    int score=0;

    public MainFrame(){
        initFrame();//初始化窗体
        initMenu();//初始化菜单
        initData();//初始化数据
        paintView();//添加的绘制函数
        this.addKeyListener(this);
        setVisible(true);//设置窗口可见，必须放在最后
    }

    public void initMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menu1 = new JMenu("换肤");
        JMenu menu2 = new JMenu("关于我们");

        JMenuItem item1 = new JMenuItem("经典");
        JMenuItem item2 = new JMenuItem("霓虹");
        JMenuItem item3 = new JMenuItem("糖果");

        menuBar.add(menu1);
        menuBar.add(menu2);

        menu1.add(item1);
        menu1.add(item2);
        menu1.add(item3);

        setJMenuBar(menuBar);
    }

    public void initFrame(){
        setSize(514,538);//窗体的宽高
        setLocationRelativeTo(null);//设置窗体居中
        setAlwaysOnTop(true);//设置窗口置顶
        setDefaultCloseOperation(3);//调用成员方法，关闭窗体模式
        setTitle("2048小游戏");//设置窗口标题
        setLayout(null);//取消默认布局
    }
    public void paintView(){
        getContentPane().removeAll();//移除掉原来所有的页面


        if(loseFlag==2)
        {
            JLabel loseLabel=new JLabel(new ImageIcon("D:\\image\\2048素材\\gameOver.png"));
            loseLabel.setBounds(90,10,300,40);
            getContentPane().add(loseLabel);
        }

        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                JLabel image=new JLabel(new ImageIcon("D:\\image\\2048素材\\"+data[i][j]+".png"));
                image.setBounds(50+100*j,50+100*i,100,100);
                getContentPane().add(image);
            }
        }

        JLabel background=new JLabel(new ImageIcon("D:\\image\\2048素材\\background.png"));
        background.setBounds(40,40,420,420);
        getContentPane().add(background);

        JLabel scoreLabel=new JLabel("得分: "+score);
        scoreLabel.setBounds(50,20,100,20);
        getContentPane().add(scoreLabel);

        getContentPane().repaint();//刷新界面
    }


    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();//获得键值
//        System.out.println(code);
        if(code!=37 && code!=38 && code!=39 && code!=40)
        {
            return;
        }
        switch (code) {
            case 37:
                //左移动
                moveToLeft(1);


                generatorNum();
                break;
            case 38:
                //上移动
                counterClockwise();
                moveToLeft(1);
                clockwise();

                generatorNum();
                break;
            case 39:
                //右移动
                rightArr();
                moveToLeft(1);
                rightArr();

                generatorNum();
                break;
            case 40:
                //下移动
                clockwise();
                moveToLeft(1);
                counterClockwise();

                generatorNum();
                break;
            default:
                break;
        }
        check();
        paintView();//每次更改，都会刷新一下界面
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    //左移动
    public void moveToLeft(int flag) {
        //判断其左移动，先将所有为零的后置,然后在进行合并
        for(int i=0;i<4;i++)
        {
            int[] newArr =new int[4];
            int index=0;
            for(int x=0;x<4;x++)
            {
                if(data[i][x]!=0)
                {
                    newArr[index]=data[i][x];
                    index++;
                }
            }
            data[i]=newArr;

            for(int x=0;x<3;x++)
            {
                if(data[i][x]==data[i][x+1])
                {
                    data[i][x]*=2;
                    if(flag==1)score+=data[i][x];
                    for(int j=x+1;j<3;j++)
                    {
                        data[i][j]=data[i][j+1];
                    }
                    data[i][3]=0;
                }
            }
        }
    }

    //左右翻转
    public void rightArr() {
        for (int i = 0; i < data.length; i++) {
            //交换第一位，和最后一位
            int temp1 = data[i][0];
            data[i][0] = data[i][3];
            data[i][3] = temp1;
            //中间第二第三位
            int temp2 = data[i][1];
            data[i][1] = data[i][2];
            data[i][2] = temp2;
        }
    }

    //顺时针旋转
    public void clockwise(){

        int[][] arr = new int[4][4];
        for(int i = 0; i <data.length;i++){
            for (int j = 0;j < data.length;j++){
                arr[j][3-i] = data[i][j];
            }
        }
        data = arr;
    }
    //逆时针旋转
    public void counterClockwise(){
        int[][] arr = new int[4][4];
        for(int i = 0; i <data.length;i++){
            for (int j = 0;j < data.length;j++){
                arr[3 - j][i] = data[i][j];
            }
        }
        data = arr;
    }

    public void copyArray(int[][] a,int[][] b)
    {
        for(int i=0;i<a.length;i++)
        {
            for(int j=0;j<a[i].length;j++)
            {
                b[i][j]=a[i][j];
            }
        }
    }
    //是否可以左下上下移动
    public boolean checkLeft()
    {
        int[][] newArr=new int[4][4];

        copyArray(data,newArr);

        moveToLeft(2);

        boolean flag=false;

        lo:
        for(int i=0;i<data.length;i++)
        {
            for(int j=0;j<data[i].length;j++)
            {
                if(data[i][j]!=newArr[i][j]) {
                    flag = true;
                    break lo;
                }
            }
        }

        copyArray(newArr,data);
        return flag;
    }
    public boolean checkRight()
    {
        int[][] newArr=new int[4][4];

        copyArray(data,newArr);

        rightArr();
        moveToLeft(2);
        rightArr();

        boolean flag=false;

        lo:
        for(int i=0;i<data.length;i++)
        {
            for(int j=0;j<data[i].length;j++)
            {
                if(data[i][j]!=newArr[i][j]) {
                    flag = true;
                    break lo;
                }
            }
        }

        copyArray(newArr,data);
        return flag;
    }

    public boolean checkUp()
    {
        int[][] newArr=new int[4][4];

        copyArray(data,newArr);

        counterClockwise();
        moveToLeft(2);
        clockwise();

        boolean flag=false;

        lo:
        for(int i=0;i<data.length;i++)
        {
            for(int j=0;j<data[i].length;j++)
            {
                if(data[i][j]!=newArr[i][j]) {
                    flag = true;
                    break lo;
                }
            }
        }

        copyArray(newArr,data);
        return flag;
    }
    public boolean checkButton()
    {
        int[][] newArr=new int[4][4];

        copyArray(data,newArr);

        clockwise();
        moveToLeft(2);
        counterClockwise();

        boolean flag=false;

        lo:
        for(int i=0;i<data.length;i++)
        {
            for(int j=0;j<data[i].length;j++)
            {
                if(data[i][j]!=newArr[i][j]) {
                    flag = true;
                    break lo;
                }
            }
        }

        copyArray(newArr,data);
        return flag;
    }

    public void check()
    {
        if(checkLeft()==false && checkUp()==false && checkRight()==false && checkButton()==false)
        {
            loseFlag=2;
        }
    }

    public void generatorNum(){
        //创建两个数组记录索引值
        int[] arrayI = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,};
        int[] arrayJ = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,};

        int w = 0;

        for (int i = 0; i < data.length; i++) {
            for (int i1 = 0; i1 < data[i].length; i1++) {
                if(data[i][i1] == 0){
                    arrayI[w] = i;
                    arrayJ[w] = i1;
                    w++;
                }
            }
        }

        if(w != 0){//判断其不为零
            Random r =new Random();
            int index = r.nextInt(w);
            int x=arrayI[index];
            int y=arrayJ[index];
            data[x][y]=2;
        }

    }

}
