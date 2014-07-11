package fol.template.window;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.filefilter.SuffixFileFilter;

import fol.template.ConfigFileUtil;
import fol.template.GameConstant;

public class JConfigFrame extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9116374978294368007L;
	private int width = 300;
	private int height = 100;
	private JButton selectConfigBt;
	private JButton quickBt;
	private JFileChooser configChooser;
	private JFileChooser dirChooser;

	private File configFile;

	private static final FileFilter excelFileFilter = new SuffixFileFilter(
			new String[] {"xls","xlsx"});
	
	
	public JConfigFrame(String title) {
		this.setTitle(title);
		this.setBounds(0, 0, width, height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout(0, 0));
		
		
		configChooser = new JFileChooser(GameConstant.SELECT_DIRE_STRING);
		FileNameExtensionFilter excelFilter = new FileNameExtensionFilter(
				"excel", "xls","xlsx");
		configChooser.setFileFilter(excelFilter);

		dirChooser = new JFileChooser(GameConstant.SELECT_DIRE_STRING);
		dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);


		selectConfigBt = new JButton("选择数据文件");
		selectConfigBt.addActionListener(this);

		quickBt = new JButton("批量处理");
		quickBt.addActionListener(this);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(selectConfigBt);
		buttonPanel.add(quickBt);

		this.getContentPane().add(buttonPanel, BorderLayout.PAGE_START);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == selectConfigBt) {
			int returnVal = configChooser.showOpenDialog(JConfigFrame.this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				this.configFile = configChooser.getSelectedFile();
				produceConfigFile(configFile);
				tipSuccess();
			}
		} else if (e.getSource() == quickBt) {
			int returnVal = dirChooser.showOpenDialog(JConfigFrame.this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File dir = dirChooser.getSelectedFile();
				produceConfigFile(dir);
				tipSuccess();
			}
		}

	}
	
	private void tipSuccess() {
		JOptionPane.showMessageDialog(this,
				"处理完成","提示",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * 批量生成配置文件
	 * 
	 * @param dir
	 */
	private void produceConfigFile(File file) {
		if(file.isDirectory()) {
			File[] files = file.listFiles();
			for(File tempFile : files) {
				produceConfigFile(tempFile);
			}
		} else if(excelFileFilter.accept(file)) {
			try {
				ConfigFileUtil.dealConfigFile(file);
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(this,
						"处理出错，错误：\n" + e1.getMessage(), "出错提示",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new JConfigFrame("配置生成器");

	}

}
