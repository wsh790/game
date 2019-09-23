package cn.wsh.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class MyGameFrame extends JFrame{
	
	Image planeImg = GameUtil.getImage("images/plane.png");
	Image bg = GameUtil.getImage("images/bg.jpg");
	Plane plane = new Plane(planeImg,250,250);
	Shell shell = new Shell();
	
	public void paint(Graphics g) {
		
		g.drawImage(bg, 0, 0, null);
		plane.drawSelf(g);
		
		shell.draw(g);
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
		this.setSize(Constant.GAME_WIDH,Constant.GAME_HEIGHT);
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
	
	}
	
	public static void main(String[] args) {
		MyGameFrame f = new MyGameFrame();
		f.lauchFrame();
	}
}
