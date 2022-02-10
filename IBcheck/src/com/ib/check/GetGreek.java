package com.ib.check;

import com.ib.check.account.AccountPosition;
import com.ib.client.Contract;
import com.ib.client.EClientSocket;
import com.ib.client.EReader;
import com.ib.client.EReaderSignal;
import com.ib.client.EWrapperImpl;
import com.ib.contracts.ContractSamples;

public class GetGreek {
	
	
	public static void main(String[] args) throws InterruptedException {
		EWrapperImpl wrapper = new EWrapperImpl();
		
		final EClientSocket m_client = wrapper.getClient();
		final EReaderSignal m_signal = wrapper.getSignal();
		
		AccountPosition accountposition= new AccountPosition();
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
		 wrapper.getClient().reqMarketDataType(2);
        //! [reqmarketdatatype]
        Thread.sleep(1000);
  
        wrapper.getClient().reqMktData(1002, contractByID(469204811),"", false, false, null);//返回一堆参数Tick AAPL Price size field

	}
	
	
	public static Contract contractByID(int contractid) {

		Contract contract = new Contract();
		contract.conid(contractid);
		contract.exchange("SMART");
	
		return contract;
	}
}
