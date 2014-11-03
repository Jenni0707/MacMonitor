package mac.project.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.tools.zip.ZipOutputStream;

/**
 * 
 * inputFileName 输入一个文件夹  
 * zipFileName 输出一个压缩文件夹  
 */

public class CompressBook {   
//    public String CompressBook() {return zipFileName;}   

    public String zip(String inputFileName) throws Exception {  
    	File file=new File(inputFileName);
        String zipFileName =(file.getName()+".zip"); //打包后文件名字   
        System.out.println(zipFileName);   
        zip(zipFileName, new File(inputFileName));  
        return zipFileName;
    }   
  
    private void zip(String zipFileName, File inputFile) throws Exception {   
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));   
        zip(out, inputFile, "");   
        System.out.println("zip done");   
        out.close();   
    }   
  
    private void zip(ZipOutputStream out, File f, String base) throws Exception {   
        if (f.isDirectory()) {   
           File[] fl = f.listFiles();   
           out.putNextEntry(new org.apache.tools.zip.ZipEntry(base + "/"));   
           base = base.length() == 0 ? "" : base + "/";   
           for (int i = 0; i < fl.length; i++) {   
           zip(out, fl[i], base + fl[i].getName());   
         }   
        }else {   
           out.putNextEntry(new org.apache.tools.zip.ZipEntry(base));   
           FileInputStream in = new FileInputStream(f);   
           int b;   
           System.out.println(base);   
           while ( (b = in.read()) != -1) {   
            out.write(b);   
         }   
         in.close();   
       }   
    }   
  
//    public static void main(String [] temp){   
//        CompressBook book = new CompressBook();   
//        String test;
//        try {   
//        	test= book.zip("D:\\My Documents\\40x40.jpg");//你要压缩的文件夹   
//       System.out.println(test);
//        }catch (Exception ex) {   
//           ex.printStackTrace();   
//       }   
//    }   
}  


