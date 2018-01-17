package hd.crawler.view;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
/**
 * 主要的顯示介面
 * 預計顯示三個分頁
 * 1)地區工作需求技能排列表
 * 2)依所會技能排出合適工作
 * 3)資料更新及系統設置
 */
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class jobMainPage {
	private JPanel jp1,jp2,jp3;
	private JFrame jf;
	private JTabbedPane jtp;
	JLabel jL1;
	public jobMainPage(){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jL1 = new JLabel("TEST");
		jp1 = new JPanel(new FlowLayout());
		jp1.add(jL1);
		
		jf = new JFrame("104Job爬蟲分析");
		jf.add(jp1);
		jf.setSize(800, 600);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		jobMainPage  jm = new jobMainPage();
	}

}
