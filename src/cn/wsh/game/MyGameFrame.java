package cn.wsh.game;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Date;
import javax.swing.JFrame;

public class MyGameFrame extends Frame{
	
	Image planeImg = GameUtil.getImage("images/plane.png");
	Image bg = GameUtil.getImage("images/bg.jpg");
	Plane plane = new Plane(planeImg,250,250);
	Shell[] shells = new Shell[50];
	
	Explode bao;
	

	Date startTime = new Date();    //游戏起始时刻

	Date endTime;  //游戏结束时刻
	
	int period;
	
	public void paint(Graphics g) {
		
		g.drawImage(bg, 0, 0, null);
		
		plane.drawSelf(g);
		
		for (int i=0;i<shells.length;i++) {
			shells[i].draw(g);
			
			boolean peng = shells[i].getRect().intersects(plane.getRect());
			
			if (peng) {
				plane.live = false;
				if (bao == null) {
					bao = new Explode(plane.x, plane.y);
					endTime = new Date();
					period = (int)((endTime.getTime()-startTime.getTime())/1000);
				}
				bao.draw(g);
			}
			
			if (!plane.live) {
				g.setColor(Color.red);
				g.drawString("游戏时间" + period + '秒', (int)plane.x, (int)plane.y);
			}
		}
		
	}
	//	帮助重画窗口
	class PaintThread extends Thread{
		@Override
		public void run() {
			while (true) {
				
				repaint();//重画
				
				try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	//定义键盘监听的内部类
	class KeyMonitor extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			plane.addDirection(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			plane.minusDirection(e);
		}
		
	}
    //	初始化窗口
	public void lauchFrame() {
		this.setTitle("我的小游戏");
		this.setVisible(true);
		this.setSize(Constant.GAME_WIDTH,Constant.GAME_HEIGHT);
		this.setLocation(300, 300);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});	
		
		new PaintThread().start();//驱动窗口的线程
		addKeyListener(new KeyMonitor());
		
		//初始化50个炮弹
		for(int i =0; i<shells.length;i++) {
			shells[i] = new Shell();
		}
	
	}
	
	public static void main(String[] args) {
		MyGameFrame f = new MyGameFrame();
		f.lauchFrame();
	}
	
	private Image offScreenImage = null;
	 
	public void update(Graphics g) {
	    if(offScreenImage == null)
	        offScreenImage = this.createImage(Constant.GAME_WIDTH,Constant.GAME_HEIGHT);//这是游戏窗口的宽度和高度
	     
	    Graphics gOff = offScreenImage.getGraphics();
	    paint(gOff);
	    g.drawImage(offScreenImage, 0, 0, null);
	}   
}
