package com.ib.check;

public abstract class Security {
	
	 String account;
	 String secType;
	 String symbol;
	 double positon;
	 double avgCost;
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
