package com.nicebao.gui;
import com.nicebao.jdbc.StudentDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author IhaveBB
 */
public class MainGUI extends JFrame {
	private StudentDAO studentDAO;
	private JLabel timeLabel;
	private JButton addStudentBtn;
	private JButton manageStudentBtn;
	private JButton exportBtn;
	private JButton importBtn;
	private JLabel info;

	public MainGUI() {
		setTitle("学生管理系统");
		setSize(500, 300);
		setLocationRelativeTo(null);
		getContentPane().setBackground(Color.LIGHT_GRAY);
		JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
		//存放时间面板
		timeLabel = new JLabel();
		info = new JLabel();
		info.setText("欢迎您登陆本系统，当前时间为");
		//计时器
		Timer timer = new Timer(1000,new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
				Date curTime = new Date();
				String formattedTime = timeFormat.format(curTime);
				timeLabel.setText(formattedTime);
			}
		});
		timer.start();

		addStudentBtn = new JButton("添加学生");
		addStudentBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddStudentGUI addStudentGUI = new AddStudentGUI();
				addStudentGUI.setVisible(true);
			}
		});

		manageStudentBtn = new JButton("管理学生");
		manageStudentBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				NewViewStudentGUI viewStudentGUI = new NewViewStudentGUI();
			}
		});

		importBtn = new JButton("导入学生");
		importBtn.addActionListener(e -> {
			studentDAO = new StudentDAO();

			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("选择 CSV 文件");
			//弹出“打开文件”文件选择器对话框
			int userSelection = fileChooser.showOpenDialog(null);
			//JFileChooser.APPROVE_OPTION 选中
			//JFileChooser.CANCEL_OPTION  取消
			//...
			if (userSelection == JFileChooser.APPROVE_OPTION) {
				java.io.File fileToImport = fileChooser.getSelectedFile();

				try {
					studentDAO.importStudent(fileToImport);
				} catch (FileNotFoundException ex) {
					throw new RuntimeException(ex);
				} catch (SQLException ex) {
					throw new RuntimeException(ex);
				}

			}
		});

		exportBtn = new JButton("导出学生");
		exportBtn.addActionListener(e -> {
			studentDAO = new StudentDAO();
			try {
				studentDAO.exportStudents();
			} catch (SQLException | IOException ex) {
				throw new RuntimeException(ex);
			}
		});

		panel.add(addStudentBtn);
		panel.add(manageStudentBtn);
		panel.add(importBtn);
		panel.add(exportBtn);
		panel.add(info);
		panel.add(timeLabel); // 将时间显示添加到界面底部
		//  界面的字体
		setMyFront("宋体",Font.BOLD,15);
		add(panel);
	}
	public static void main(String[] args) throws SQLException {
		new MainGUI().setVisible(true);
	}

	/** @description: 更改本界面字体样式
			* @param: 字体名称eg“宋体”，字体样式，粗，普通，斜体....，字体大小
			* @return: void
			* @author: IhaveBB
			* @date: 2023/12/10
			*/
	public void setMyFront(String frontName,int fontStyle,int size){
		Font newFont = new Font(frontName,fontStyle,size);
		timeLabel.setFont(newFont);
		addStudentBtn.setFont(newFont);
		manageStudentBtn.setFont(newFont);
		importBtn.setFont(newFont);
		exportBtn.setFont(newFont);
		info.setFont(newFont);
	}
}

