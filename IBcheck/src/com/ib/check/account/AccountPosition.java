package com.ib.check.account;


import java.util.ArrayList;
import java.util.List;
import com.ib.client.Contract;
import com.ib.check.underlying.*;


/*把request position后get过来的contract 放到List中 
Option为期权类型
Stock为股票类型 
Account为多个账户
*/
public class AccountPosition {
	
	
	DealFile dealfile=new DealFile();
public	String path_U1001=dealfile.path_U1001;
public 	String path_U9238=dealfile.path_U9238;
	
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
			 dealfile.FileWrite(op,path_U1001);//写到本地文件
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
			dealfile.FileWrite(stock,path_U1001);//写到本地文件
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
			 dealfile.FileWrite(op,path_U9238);//写到本地文件
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
			dealfile.FileWrite(stock,path_U9238);//写到本地文件
			securityList.add(stock);//增加全类型
		}
		
		 if(contract.getSecType().isEmpty())
			 System.out.println("SecType is empty"+contract.conid());
		 

	 }
	 
	 
		//从本地文件读出某账户的全部头寸，并封装成Contract类
		public  int[] getSecuityFromFile(String path)
		{
			String filetext;
			
			filetext=dealfile.ReadFile(path);
			//System.out.println("the text content is: "+filetext);
			
			String[] record = filetext.split("\\|");//转意
			int[] contractid=new int[record.length];
		    int i=0;
		    int j=0;
		        for(i=0;i<record.length;i++)//分成这样的：conid=523929717
		        {                                       //  account=U9238923
		            System.out.println(record[i]);
		           if( record[i].contains("conid="))
		           {      	 
		        	   String str[]=record[i].split("=");
		       
		        	   contractid[j]= Integer.parseInt(str[1]);
		        	   j++;
		        	   System.out.println("第"+j+"个：conid="+str[1]);//取conid等号右边的数
		           }   
		            
		        }
			
			return contractid;

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


