package com.ib.check.account;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ib.check.DayDelta;
import com.ib.check.underlying.Option;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DealExcel {
	
	
	 

	    

	
  public static void main(String[] args) {
	     List<Option> opList = new ArrayList<Option>();
		DayDelta daydelta = new DayDelta();
		DealFile dealfile = new DealFile();
	//	opList=daydelta.getALLOP(dealfile.path_U9238GREEK);
		opList = daydelta.getALLOP(dealfile.path_U1001GREEK);
		daydelta.getAcctPflioDelta(opList); 	 
	  DealExcel dexcel= new DealExcel();


  }
}
