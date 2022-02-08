package com.ib.check;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.ib.client.Contract;

/*把request position后get过来的contract 放到List中 
Option为期权类型
Stock为股票类型 
Account为多个账户
*/
public class AccountPosition {
  	//whole account
	List<Option> optionList = new ArrayList<Option>();
	List<Stock> stockList = new ArrayList<Stock>();
	List<Security> securityList=new ArrayList<Security>();//全部头寸都进入此

	//stock account
	List<Option> optionList_U1001 = new ArrayList<Option>();
	List<Stock> stockList_U1001 = new ArrayList<Stock>();
	//List<Security> securityList_Stock=new ArrayList<Security>();
	
	//ETF account
	List<Option> optionList_U9238 = new ArrayList<Option>();
	List<Stock> stockList_U9238 = new ArrayList<Stock>();
	//List<Security> securityList_ETF=new ArrayList<Security>();
	
	String date=FileNameDate();
	
	
	 String path_U1001="/Users/jiao/Documents/IBAPI/U10019359/"+date;
	 String path_U9238="/Users/jiao/Documents/IBAPI/U9238923/"+date;
	

  
	int call_count=0;//期权call计数
	int put_count=0;//期权put计数
	int total_op=0;
	int total_stock=0;
	int total_secruity=0;

	
	
	
	//账户
    public  AccountPosition()
    {
    }

    
    
    //新增一个期权or股票并进行分类进行封装
    public void addSecurity(String account,Contract contract,double position,double avgCost)
    {
         //根据不同的账户进入,stock account
    	if(account.equals("U10019359"))
    	{
   
    	//如果判断头寸为期权则封装成Option类并加到List里
		if (contract.getSecType().equalsIgnoreCase("OPT"))
		{
			Option op= new Option();
			op.setAccount(account);
	    	op.setSymbol(contract.symbol());
	    	op.setSecType(contract.getSecType());
	        op.setRight(contract.getRight());
	        op.setPositon(position);
	        op.setAvgCost(avgCost);
	        op.setLocalSymbol(contract.localSymbol());
	        op.setDate(contract.lastTradeDateOrContractMonth());
	        op.setStrikePrice(contract.strike());			
			
			if(contract.getRight().equalsIgnoreCase("C"))//为call
				
			{
				call_count++;
				System.out.println("Total Call Option: "+call_count);
			}
			
			if(contract.getRight().equalsIgnoreCase("P"))//为put
			{
				put_count++;
				System.out.println("Total Put Option: "+put_count);
			}
			total_op=put_count+call_count;
			System.out.println("Total Option :"+total_op);
			 optionList_U1001.add(op);//增加stock account期权记录	
		
			 securityList.add(op);//增加全账户全类型记录
			 FileWrite(op,path_U1001);//写到本地文件
		}
		
		
        //如果判断头寸为STK则封装成Stock并添加到List里             
		if (contract.getSecType().equalsIgnoreCase("STK"))
		{
			Stock stock=new Stock();
			stock.setAccount(account);
			stock.setSymbol(contract.symbol());
			stock.setSecType(contract.getSecType());
			stock.setPositon(position);
			stock.setAvgCost(avgCost);
			total_stock++;
			System.out.println("Total stock :"+total_stock);
			stockList_U1001.add(stock);//增加stock account股票记录
			FileWrite(stock,path_U1001);//写到本地文件
			securityList.add(stock);//增加全类型
		}

    	}
		
    	  //根据不同的账户进入,stock account
    	if(account.equals("U9238923"))
    	{
    	
    		//如果判断头寸为期权则封装成Option类并加到List里
    		if (contract.getSecType().equalsIgnoreCase("OPT"))
    		{
    			Option op= new Option();
    			op.setAccount(account);
    	    	op.setSymbol(contract.symbol());
    	    	op.setSecType(contract.getSecType());
    	        op.setRight(contract.getRight());
    	        op.setPositon(position);
    	        op.setAvgCost(avgCost);
    	        op.setLocalSymbol(contract.localSymbol());
    	        op.setDate(contract.lastTradeDateOrContractMonth());
    	        op.setStrikePrice(contract.strike());			
    			
    			if(contract.getRight().equalsIgnoreCase("C"))//为call
    				
    			{
    				call_count++;
    				System.out.println("Total Call Option: "+call_count);
    			}
    			
    			if(contract.getRight().equalsIgnoreCase("P"))//为put
    			{
    				put_count++;
    				System.out.println("Total Put Option: "+put_count);
    			}
    			total_op=put_count+call_count;
    			System.out.println("Total Option :"+total_op);
    			optionList_U9238.add(op);//增加stock account期权记录	
    		
    			 securityList.add(op);//增加全账户全类型记录
    			 FileWrite(op,path_U9238);//写到本地文件
    		}
    		
    		
            //如果判断头寸为STK则封装成Stock并添加到List里             
    		if (contract.getSecType().equalsIgnoreCase("STK"))
    		{
    			Stock stock=new Stock();
    			stock.setAccount(account);
    			stock.setSymbol(contract.symbol());
    			stock.setSecType(contract.getSecType());
    			stock.setPositon(position);
    			stock.setAvgCost(avgCost);
    			total_stock++;
    			System.out.println("Total stock :"+total_stock);
    			stockList_U9238.add(stock);//增加stock account股票记录
    			FileWrite(stock,path_U9238);//写到本地文件
    			securityList.add(stock);//增加全类型
    		}

        	}
    		
    		
		
    	
		total_secruity=total_stock+total_op;
		System.out.println("Total secruity :"+total_secruity);
	
    }
    
    //每天生成不同的文件名
    public String FileNameDate()
    {
    Date date = new Date();
    String filedate="";

    String year = String.format("%tY", date);

    String month = String.format("%tm", date);

    String day = String.format("%td", date);

    filedate=year+month+day;
    System.out.println("今天是："+filedate);
	
    return filedate; 
 
    }
    
    
    
    //将封装对象写入本地文件
    public   void FileWrite( Security security,String path)
    {
    	
    	try {
            BufferedWriter bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(path,true), "UTF-8"));   //false则每次都覆盖         
        
                bufferedWriter.write((showALLSecurity(security)));
                bufferedWriter.newLine();
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
    int i=1; 
    //输出全部头寸信息
    public String showALLSecurity(Security sec)
    {
         String str="";
 
             str=i+++str+printFieldsValue(sec)+"\n";
       
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
    
    
   /* 
    
    //输出全部头寸信息
    public String showALLSecurity(List<Security> secList)
    {
         String str="";
    	 Iterator<Security> it = secList.iterator();
         //输出list对象属性的第二种方式，遍历list，得到list中封装的对象，再通过类反射输出对象属性
         while (it.hasNext()) {
             str=str+printFieldsValue(it.next())+"\n";
         }
		return str; 	
    	
    }
    	
    
    public   void FileWrite(List <Security> securityList)
    {
    	
    	try {
            BufferedWriter bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(path,false), "UTF-8"));   //false则每次都覆盖         
            for (Security s : securityList) {
                bufferedWriter.write((showALLSecurity(securityList)));
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }           
            bufferedWriter.close();
    		
    		
    		
  
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
    
    */

  

    	
    }


