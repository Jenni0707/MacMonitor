package mac.project.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Vector;

public class Slice{
	public Slice(){
		
	}
	
	public Vector<FileOutputStream> slicefile(String fileUrl) {//GEN-FIRST:event_jButtonSplitterActionPerformed    
		Vector<FileOutputStream> fileVector=new Vector<FileOutputStream>();
		//	int fileSize = 0;//记录分割后的文件大小    
		int fileNum = 0;//记录分割后的文件大小    
		File sourceFile;//记录原始文件路径    
		//    jTextAreaSouth.setText("\n分割开始...\n");   
		sourceFile = new File(fileUrl);   
		//	FileSize FileSize=new FileSize();
		System.out.println(sourceFile.length());
		int SliceFileSize=50000000;//子文件的大小
		//    if (jRadioButtonSize.isSelected()) {//按文件长度分割    
		//文件尺寸以字节为单位    
//            if (jComboBoxSize.getSelectedItem() == "MB")   
//        fileSize = (int) (Float.parseFloat(jTextFieldSize.getText()) * 1024 * 1024);   
//            if (jComboBoxSize.getSelectedItem() == "KB")   
//        fileSize = (int) (Float.parseFloat(jTextFieldSize.getText()) * 1024);   
//            if (jComboBoxSize.getSelectedItem() == "B")   
//        fileSize = (int) (Float.parseFloat(jTextFieldSize.getText()));   
		fileNum = (int) (sourceFile.length() / SliceFileSize);//计算分割成的文件数    
		if (sourceFile.length() % SliceFileSize != 0) {   
			fileNum++;   

		}   
//		    if (jRadioButtonNumber.isSelected()) {//按文件数进行分割    
//		            fileNum = Integer.parseInt(jTextFieldNumber.getText());//获取输入的文件数    
//		            fileSize = ((int) (sourceFile.length() / fileNum)) + 1;//计算分割文件长度    
//		    }   
		File[] newFile = new File[fileNum];//创建分割文件数组，存放分割后的文件    
		try {                  
			int count = 0;   
			int i = 0;   
			byte[] bueff = new byte[SliceFileSize];   
			FileOutputStream out = null;   
			FileInputStream in = new FileInputStream(sourceFile);   
			//根据用户输入的保存位置创建File对象    
			File f = new File(sourceFile.getName());   
			if (!f.exists())   
				f.createNewFile();//如果文件不存在则创建新文件    
			for (i = 0; i < newFile.length; i++) {   
//			jTextAreaSouth.append("正在创建第" + (i + 1) + "个文件...\n");   
				//依次创建分割文件对象    
				newFile[i] = new File(sourceFile.getName()+i );   
			}   
			i = 0;   
			while ((count = in.read(bueff, 0, SliceFileSize)) != -1) {
				System.out.println("count:"+count);
//				jTextAreaSouth.append("正在写入第" + (i + 1) + "个文件...\n");   
				//依次写入分割文件对象    
				out = new FileOutputStream(newFile[i]);   
				out.write(bueff, 0, SliceFileSize);   
				out.close();   
				fileVector.addElement(out);
				i++;   
			}   
			in.close();   
//					jTextAreaSouth.append("\n\n分割成功！！");   
		} catch (Exception e) {   
			//		jTextAreaSouth.append("\n\n分割失败！！");   
			e.printStackTrace();
		}
		return fileVector;   
	}

//	public static void main(String args[]){
//		Slice testFile=new Slice();
//		Vector test =new Vector();
//		test= 	 testFile.slicefile("E:\\Ecplise-workspace\\JAVA实现文件切割.zip");
//		System.out.println(test.size());
//	}
}