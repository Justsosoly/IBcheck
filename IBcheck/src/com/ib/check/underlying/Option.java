package com.ib.check.underlying;

	//期权类型
	public class Option extends Security
	{
		 
		 int conid;
		 String account;
		 String localSymbol;
		 String secType;
		 String right;
		 String symbol;
		 String date;
		 double positon;
		 double avgCost;
		 double strikePrice;
		 int optionPrice;
		 float delta;
		 float gamma;
		 float vega;
		 float theta;
		 float imvolatility;
		 float underlyingPrice;
		 public Option()
		 {
			 
		 }
		 
	     public Option( String contract_account, String contract_localSymbol, String contract_secType,String contract_right,String contract_symbol,String contract_date,int contract_positon,
	             int contract_avgCost,
	    		 int contract_strikePrice,
	    		 int contract_optionPrice,
	    		 float contract_delta,
	    		 float contract_gamma,
	    		 float contract_vega,
	    		 float contract_theta,
	    		 float contract_imvolatility,
	    		 float contract_underlyingPrice)
	     {
	    	  account=contract_account;
	    	  localSymbol=contract_localSymbol;
	    	  secType=contract_secType;
	    	  right=contract_right;
	    	  symbol=contract_symbol;
			  date=contract_date;
			  positon=contract_positon;
			  avgCost=contract_avgCost;
			  strikePrice=contract_strikePrice;
			  optionPrice=contract_optionPrice;
			  delta=contract_delta;
			  gamma=contract_gamma;
			  vega=contract_vega;
			  theta=contract_theta;
			  imvolatility=contract_imvolatility;
			  underlyingPrice=contract_underlyingPrice;	    		    	 
	     }
	     
	     
	     
		 public int getConid() {
			return conid;
		}

		public void setConid(int conid) {
			this.conid = conid;
		}
		 public String getAccount() {
				return account;
			}

			public void setAccount(String account) {
				this.account = account;
			}

			public String getLocalSymbol() {
				return localSymbol;
			}

			public void setLocalSymbol(String localSymbol) {
				this.localSymbol = localSymbol;
			}

			public String getSecType() {
				return secType;
			}

			public void setSecType(String secType) {
				this.secType = secType;
			}

			public String getRight() {
				return right;
			}

			public void setRight(String right) {
				this.right = right;
			}

			public String getSymbol() {
				return symbol;
			}

			public void setSymbol(String symbol) {
				this.symbol = symbol;
			}

			public String getDate() {
				return date;
			}

			public void setDate(String date) {
				this.date = date;
			}

			public double getPositon() {
				return positon;
			}

			public void setPositon(double positon) {
				this.positon = positon;
			}

			public double getAvgCost() {
				return avgCost;
			}

			public void setAvgCost(double avgCost) {
				this.avgCost = avgCost;
			}

			public double getStrikePrice() {
				return strikePrice;
			}

			public void setStrikePrice(double strikePrice) {
				this.strikePrice = strikePrice;
			}

			public int getOptionPrice() {
				return optionPrice;
			}

			public void setOptionPrice(int optionPrice) {
				this.optionPrice = optionPrice;
			}

			public float getDelta() {
				return delta;
			}

			public void setDelta(float delta) {
				this.delta = delta;
			}

			public float getGamma() {
				return gamma;
			}

			public void setGamma(float gamma) {
				this.gamma = gamma;
			}

			public float getVega() {
				return vega;
			}

			public void setVega(float vega) {
				this.vega = vega;
			}

			public float getTheta() {
				return theta;
			}

			public void setTheta(float theta) {
				this.theta = theta;
			}

			public float getImvolatility() {
				return imvolatility;
			}

			public void setImvolatility(float imvolatility) {
				this.imvolatility = imvolatility;
			}

			public float getUnderlyingPrice() {
				return underlyingPrice;
			}

			public void setUnderlyingPrice(float underlyingPrice) {
				this.underlyingPrice = underlyingPrice;
			}
	     
		
	}
	

