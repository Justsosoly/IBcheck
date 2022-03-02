package com.ib.check;

import com.ib.check.account.AccountPosition;
import com.ib.client.Contract;
import com.ib.client.EClientSocket;
import com.ib.client.EReader;
import com.ib.client.EReaderSignal;
import com.ib.client.EWrapperImpl;

public class GetGreek {
	
	
	public static void main(String[] args) throws InterruptedException {
		EWrapperImpl wrapper = new EWrapperImpl();
		
		final EClientSocket m_client = wrapper.getClient();
		final EReaderSignal m_signal = wrapper.getSignal();
	

		m_client.eConnect("127.0.0.1", 7496, 0);

		
		//! [ereader]
		final EReader reader = new EReader(m_client, m_signal);   
		
		reader.start();
		//An additional thread is created in this program design to empty the messaging queue
		new Thread(() -> {
		    while (m_client.isConnected()) {
		        m_signal.waitForSignal();
		        try {
		            reader.processMsgs();
		        } catch (Exception e) {
		            System.out.println("Exception: "+e.getMessage());
		        }
		    }
		}).start();
		//! [ereader]
		// A pause to give the application time to establish the connection
		// In a production application, it would be best to wait for callbacks to confirm the connection is complete
		Thread.sleep(1000);
		
		//! [reqmarketdatatype]
        /*** Switch to live (1) frozen (2) delayed (3) or delayed frozen (4)***/
	//	 wrapper.getClient().reqMarketDataType(2); 
        //! [reqmarketdatatype]
        Thread.sleep(1000);
        AccountPosition accountposition=new AccountPosition();
   //    GetGreekbyAccount(wrapper.getClient(),accountposition.path_U1001);
       GetGreekbyAccount(wrapper.getClient(),accountposition.path_U9238);
        Thread.sleep(10000);
        

    //    Thread.sleep(10000);
    
       
    
        
        
	}
	
	public static void GetGreekbyAccount(EClientSocket client,String path) throws InterruptedException
	{
		AccountPosition accountposition=new AccountPosition(); 
		int request_num=0;//本地文件返回请求conid的个数
		
		//将本地文件里Option的conid全部取出来放到数组里，循环请求
	//	 request_num= accountposition.getOptionFromFile(path).length;
		
		//取出全部conid，包括stock和option
		request_num= accountposition.getSecuityFromFile(path).length;
		 
		
		 int contractid[]=new int[request_num];
        contractid=accountposition.getSecuityFromFile(path);


        client.reqMarketDataType(2); 
        
   //     client.reqMktData(2005, contractByID(536098257),"", false, false, null);
        
       
        //取得本地文件里的conid，挨个请求市场数据
        for(int i=0;i<request_num;i++)
        {      	
        //每次将一个security的GREEK写回本地文件	
        
        	client.reqMktData(contractid[i], contractByID(contractid[i]),"", false, false, null);//返回一堆参数Tick AAPL Price size field   
        	System.out.println("第"+i+"个请求"+contractid[i]);
        	Thread.sleep(100);//1秒
     	}
        
        client.reqMarketDataType(4); 
        client.reqMktData(2001, getSPXIndex(),"", false, false, null);
        Thread.sleep(100);
        client.reqMktData(3001, getNASDAQIndex(),"", false, false, null);
        
	}
	
	public static Contract getSPXIndex() 
	{
		Contract contract = new Contract();
		contract.secType("IND") ;
		contract.symbol("SPX");
		contract.exchange("CBOE");
		contract.currency("USD");
		return contract;
	}
	
	public static Contract getNASDAQIndex() 
	{
		Contract contract = new Contract();
		contract.secType("IND") ;
		contract.symbol("COMP");
		contract.exchange("NASDAQ");
		contract.currency("USD");
		return contract;
	}
	
	public static Contract contractByID(int contractid) {

		Contract contract = new Contract();
		contract.conid(contractid);
		contract.exchange("SMART");
	
		return contract;
	}
}
