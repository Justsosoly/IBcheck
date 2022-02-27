package com.ib.check.underlying;
	
	//股票类型
	public class Stock extends Security
	{
		 int conid;
		 String account;
		 String secType;
		 String symbol;
		 double positon;
		 double avgCost;
		 double price;
		 
		 public double getPrice() {
			return price;
		}
		public void setPrice(double price) {
			this.price = price;
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
		public String getSecType() {
			return secType;
		}
		public void setSecType(String secType) {
			this.secType = secType;
		}
		public String getSymbol() {
			return symbol;
		}
		public void setSymbol(String symbol) {
			this.symbol = symbol;
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

	}


