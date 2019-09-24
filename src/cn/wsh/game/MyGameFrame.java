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
	

	Date startTime = new Date();    //��Ϸ��ʼʱ��

	Date endTime;  //��Ϸ����ʱ��
	
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
				g.drawString("��Ϸʱ��" + period + '��', (int)plane.x, (int)plane.y);
			}
		}
		
	}
	//	�����ػ�����
	class PaintThread extends Thread{
		@Override
		public void run() {
			while (true) {
				
				repaint();//�ػ�
				
				try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	//������̼������ڲ���
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
    //	��ʼ������
	public void lauchFrame() {
		this.setTitle("�ҵ�С��Ϸ");
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
		
		new PaintThread().start();//�������ڵ��߳�
		addKeyListener(new KeyMonitor());
		
		//��ʼ��50���ڵ�
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
	        offScreenImage = this.createImage(Constant.GAME_WIDTH,Constant.GAME_HEIGHT);//������Ϸ���ڵĿ�Ⱥ͸߶�
	     
	    Graphics gOff = offScreenImage.getGraphics();
	    paint(gOff);
	    g.drawImage(offScreenImage, 0, 0, null);
	}   
}
