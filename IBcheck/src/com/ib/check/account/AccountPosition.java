package com.ib.check.account;


import java.util.ArrayList;
import java.util.List;
import com.ib.client.Contract;
import com.ib.check.underlying.*;

import org.apache.commons.lang3.*;


/*把request position后get过来的contract 放到List中 
Option为期权类型
Stock为股票类型 
Account为多个账户
*/
public class AccountPosition {
	
	
	DealFile dealfile=new DealFile();
public	String path_U1001=dealfile.path_U1001;
public 	String path_U9238=dealfile.path_U9238;
public 	String path_U9238GREEK=dealfile.path_U9238+"GREEK";
public 	String path_U1001GREEK=dealfile.path_U1001+"GREEK";
	
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
	
	
	List<Contract> contratList_U9238 = new ArrayList<Contract>();
	List<Contract> contratList_U1001 = new ArrayList<Contract>();
	
  
	int call_count=0;//期权call计数
	int put_count=0;//期权put计数
	int total_op=0;
	int total_stock=0;
	int total_secruity=0;
 

	//获取市场数据，主要把GREEK信息再补充到本地文件
	public void addGREEKToFile(int tickerId, int tickType,double impliedVol,double delta,double optPrice,double pvDividend,double gamma,double vega,double theta,double undPrice)
	{
		//把Greek先放到Option对象中
		Option option_Greek=new Option();
		option_Greek.setConid(tickerId);
		option_Greek.setImvolatility(impliedVol);
		option_Greek.setDelta(delta);
		option_Greek.setOptionPrice(optPrice);
		option_Greek.setGamma(gamma);
		option_Greek.setVega(vega);
		option_Greek.setTheta(theta);
		option_Greek.setUnderlyingPrice(undPrice);				

        String filetext="";//取出本地文件	
        int i=0;//按本地文件行走
	    int j=0;//按本地文件1行再分开走
	    
	    
		String filetext_U9238=dealfile.ReadFile(path_U9238);
		String filetext_U1001=dealfile.ReadFile(path_U1001);
		
		if(filetext_U9238.contains("conid="+option_Greek.getConid()))
		{
			filetext=filetext_U9238;
		}
		else if(filetext_U1001.contains("conid="+option_Greek.getConid()))
		{
			filetext=filetext_U1001;
		}
		
			
		String[] row = filetext.split("\\r\\n");//转义

	  
	        for(i=0;i<row.length;i++)//进入文本里，每次一行记录开始循环
	        {                                       
	        //    System.out.println("第"+i+"行记录"+row[i]);
	        
	            String str=row[i]; //str为一行的内容   
	            String conidunit=StringUtils.substringBefore(row[i],"|");//将conid=123456789取出给conidunit
	            
	          
	            	
	       //    if( str.contains("conid="+tickerId))//循环匹配到有该conid的这行记录
	            if(conidunit.equalsIgnoreCase("conid="+tickerId))//字符串完全相等的情况，而不是包含的情况进入
	           {      	 
	        	   
	        	   String contract[]=str.split("\\|");
	        	   for(j=0;j<contract.length;j++)
	        	   {
	        		 String unit[]=contract[j].split("=");
	        		   
	        		if(contract[j].contains("account"))
	        		{ 	  
	   	    		option_Greek.setAccount(unit[1]);continue;	        			
	        		}
	        		if(contract[j].contains("localSymbol"))
	        		{
	        			option_Greek.setLocalSymbol(unit[1]);continue;
	        		}
	        		if(contract[j].contains("secType"))
	        		{
	        			option_Greek.setSecType(unit[1]);continue;
	        		}
	        		if(contract[j].contains("right"))
	        		{
	        			option_Greek.setRight(unit[1]);continue;
	        		}
	        		if(contract[j].contains("symbol"))
	        		{
	        			option_Greek.setSymbol(unit[1]);continue;
	        		}
	        		if(contract[j].contains("date"))
	        		{
	        			option_Greek.setDate(unit[1]);continue;
	        		}
	        		if(contract[j].contains("positon"))
	        		{
	        			option_Greek.setPositon(Double.valueOf(unit[1]));continue;	
	        		}	
	        		if(contract[j].contains("avgCost"))
	        		{
	        			option_Greek.setAvgCost(Double.valueOf(unit[1]));continue;
	        		}
	        		if(contract[j].contains("strikePrice"))
	        		{
	        			option_Greek.setStrikePrice(Double.valueOf(unit[1]));continue;
	        		}
	        	        		
	        		   
	        	   }
	        	   
                 break;//每次循环进入文本的记录，只会有1个conid相匹配的情况，找到就结束循环
	           }   
	            
	        }
		   
		    //根据账户信息进行判断，分别写到各自的GREEK文件里
		   
		    switch( option_Greek.getAccount()) {
		      
            
	         case "U10019359":
	        	 dealfile.FileWriteGREEK(option_Greek,path_U1001GREEK);//每次写一个option到文件里
	             break;
	       
	         case "U9238923":
	        	 dealfile.FileWriteGREEK(option_Greek,path_U9238GREEK);//每次写一个option到文件里
	             break;
	    	 }
		    
		    
		    
		 
		    
	        
		
	}
	

	//从本地文件读出某账户的全部头寸，并封装成Contract类。并不需要全部读取，只要OPTION的才需要再去读市场数据
	public  int[] getSecuityFromFile(String path)
	{
		String filetext;
		List<Integer> list = new ArrayList<>();
		filetext=dealfile.ReadFile(path);
		//System.out.println("the text content is: "+filetext);
		
		String[] record = filetext.split("\\r\\n");//转义
		int[] contractid=null; 
	    int i=0;
	    int j=0;
	        for(i=0;i<record.length;i++)//将每行的conid提取出来
	        {                                       
	          //  System.out.println("record[i] is "+record[i]);
	            //如果recond[i]中含有secType=OPT，则计入contractid[],否则跳出循环
	        	if(record[i].contains("secType=OPT"))
	        	{
	            String conidString=StringUtils.substringBefore(record[i],"|");
	          //  System.out.println("conidString is"+conidString);
	           if( conidString.contains("conid="))//只需要取conid的内容
	           {      	 
	        	   String str[]=conidString.split("=");
	       
	        	   Integer.parseInt(str[1]);
	        	//   j++;
	        	  //将
	        	   list.add(Integer.parseInt(str[1]));
	 	        	   
	        	//   System.out.println("第"+j+"个：conid="+str[1]);//取conid等号右边的数
	           }   
	        	}
	        	else continue;
	        }
	  	    contractid =new int[list.size()];
       	    for(j=0;j<list.size();j++)
       		 contractid[j]= list.get(j);
		
		return contractid;

	}
 
	
	
	
	
    //新增一个期权or股票并进行分类封装,并写入本地文件里
    public void addSecurityToFile(String account,Contract contract,double position,double avgCost)
    {
        
    	 switch( account) {
      
             
         case "U10019359":
             processAccountU1001(account, contract, position, avgCost);
             break;
       
         case "U9238923":
             processAccountU9238(account, contract, position, avgCost);
             break;
    	 }
    	 
    		total_secruity=total_stock+total_op;
    		System.out.println("Total secruity :"+total_secruity);
 
    
    }
    
    
    
    

	
	 public void processAccountU1001(String account,Contract contract,double position,double avgCost)
	 {
		if (contract.getSecType().equalsIgnoreCase("OPT"))
		{
			Option op= new Option();
		
			op.setConid(contract.conid());
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
			 dealfile.FileWritePosition(op,path_U1001);//写到本地文件
		}
		
		
       //如果判断头寸为STK则封装成Stock并添加到List里             
		if (contract.getSecType().equalsIgnoreCase("STK"))
		{
			Stock stock=new Stock();
			
			stock.setConid(contract.conid());
			stock.setAccount(account);
			stock.setSymbol(contract.symbol());
			stock.setSecType(contract.getSecType());
			stock.setPositon(position);
			stock.setAvgCost(avgCost);
			total_stock++;
			System.out.println("Total stock :"+total_stock);
			stockList_U1001.add(stock);//增加stock account股票记录
			dealfile.FileWritePosition(stock,path_U1001);//写到本地文件
			securityList.add(stock);//增加全类型
		}
		 if(contract.getSecType().isEmpty())
			 System.out.println("SecType is empty"+contract.conid());
		 
	 }
	 
	 
	 
	 public void processAccountU9238(String account,Contract contract,double position,double avgCost)
	 {
	
		//如果判断头寸为期权则封装成Option类并加到List里
		if (contract.getSecType().equalsIgnoreCase("OPT"))
		{
			Option op= new Option();
		
			op.setConid(contract.conid());
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
			 dealfile.FileWritePosition(op,path_U9238);//写到本地文件
		}
		
		
        //如果判断头寸为STK则封装成Stock并添加到List里             
		if (contract.getSecType().equalsIgnoreCase("STK"))
		{
			Stock stock=new Stock();
			
			stock.setConid(contract.conid());
			stock.setAccount(account);
			stock.setSymbol(contract.symbol());
			stock.setSecType(contract.getSecType());
			stock.setPositon(position);
			stock.setAvgCost(avgCost);
			total_stock++;
			System.out.println("Total stock :"+total_stock);
			stockList_U9238.add(stock);//增加stock account股票记录
			dealfile.FileWritePosition(stock,path_U9238);//写到本地文件
			securityList.add(stock);//增加全类型
		}
		
		 if(contract.getSecType().isEmpty())
			 System.out.println("SecType is empty"+contract.conid());
		 

	 }
	 
	 
    
	 
	    public static void main(String args[])
	    {
	    	AccountPosition accpositon= new AccountPosition();
	    	try {
				accpositon.getSecuityFromFile(accpositon.path_U9238);
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    }
    

    	
    }


