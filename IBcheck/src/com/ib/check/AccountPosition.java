package com.ib.check;

import java.util.ArrayList;
import java.util.List;
import com.ib.client.Contract;

/*把request position后get过来的contract 放到List中 
Option为期权类型
Stock为股票类型 
Account为多个账户
*/
public class AccountPosition {
  	List<Option> optionList = new ArrayList<Option>();
	List<Stock> stockList = new ArrayList<Stock>();

	//账户
    public  AccountPosition()
    {
    }
  
    //新增一个期权
    public void addOption(Option op,Contract contract)
    {
    	op.setSecType(contract.getSecType());
        op.setSymbol(contract.symbol());
        op.setRight(contract.getRight());
        op.setStrikePrice(contract.strike());
    	
    	
    	optionList.add(op);
    }
    //输出全部期权信息
    public Option[] showALLOption()
    {
    	Option[] showop=new Option[optionList.size()] ;
    	optionList.toArray(showop);
  	    System.out.println("my option are :"+optionList);
    	return showop;
    	
    }
    	
    	
    }


