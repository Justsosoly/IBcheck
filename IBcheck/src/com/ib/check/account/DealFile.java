package com.ib.check.account;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.Date;

import com.ib.check.underlying.Security;


public class DealFile {
	
	     String date=FileNameDate();
		
	public	 String path_U1001="/Users/jiao/Documents/IBAPI/U10019359/"+date;
	public 	 String path_U9238="/Users/jiao/Documents/IBAPI/U9238923/"+date;
		 
		 
		    //每天生成不同的文件名
		    public String FileNameDate()
		    {
		    Date date = new Date();
		    String filedate="";

		    String year = String.format("%tY", date);

		    String month = String.format("%tm", date);

		    String day = String.format("%td", date);

		    filedate=year+month+day;
		   // System.out.println("今天是："+filedate);
			
		    return filedate; 
		    }
	
	
	
	
	//从本地文件读出
	   public  String ReadFile(String path) {
	        StringBuffer buffer = new StringBuffer();
	        try {
	            BufferedReader bufferedReader = new BufferedReader(
	                    new InputStreamReader(new FileInputStream(path), "UTF-8"));
	            String data = null;
	            while ((data = bufferedReader.readLine()) != null) {
	            	
	                buffer.append(data+"\r\n");
	            }
	      //      System.out.println("the buffer is :"+buffer);
	      bufferedReader.close();
	        } catch (UnsupportedEncodingException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (FileNotFoundException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return buffer.toString();
	    }
	    
	
	  
    //将封装对象写入本地文件
    public   void FileWrite( Security security,String path)
    {
    	
    	try {
            BufferedWriter bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(path,true), "UTF-8"));   //false则每次都覆盖         
        
                bufferedWriter.write((showALLSecurity(security)));
                bufferedWriter.newLine();//换行
                bufferedWriter.flush();                   
                bufferedWriter.close();
    		   		
  
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }

    //输出全部头寸信息
    public String showALLSecurity(Security sec)
    {
         String str="";
 
             str=str+printFieldsValue(sec);//不要在这里加换行，已经有换行了
             //+"\n";  
       
		return str; 	
    	
    }
    
 //将List里的封装对象取出到字符串，写入文件或者打印出来
    public  String printFieldsValue(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        String str="";
        try {
            for (Field f : fields) {
                f.setAccessible(true);
                str=str+f.getName()+"="+f.get(obj)+"|"; 
         //       System.out.println(f.getName()+" "+f.get(obj));
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
		return str;
    }
    	
    public static void main(String args[])
    {
    	DealFile dealfile=new DealFile();
    	dealfile.ReadFile(dealfile.path_U9238);   
    	
    }
 
    
    
}


