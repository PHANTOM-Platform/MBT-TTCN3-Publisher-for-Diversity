package com.eglobalmark.genericPublisher.adapter.diversity;

import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;

import com.eglobalmark.genericPublisher.abstractTestStructure.AbstractTestStructure;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractInstance.AbstractArrayValue;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractInstance.AbstractBooleanValue;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractInstance.AbstractCustomValue;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractInstance.AbstractEnumValue;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractInstance.AbstractExpression;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractInstance.AbstractFloatValue;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractInstance.AbstractFunctionCall;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractInstance.AbstractIntegerValue;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractTest.AbstractInstructionSimpleExpression;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractTest.AbstractTestCase;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractTest.AbstractTestStep;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractTest.AbstractTestSuite;
import com.eglobalmark.genericPublisher.optionsManager.OptionManager;

public class DiversityTestSuiteReader {

	private static AbstractTestStructure aTestStructure = AbstractTestStructure.getInstance();
	
	private static BufferedReader br = null;
	private static String strCurrentLine;

	private static final String strTestCaseStart = "TRACE NUMBER ";
	private static final String strFireTransition = "fire ";
	private static final String strInput = "INPUT  ";
	private static final String strOutput = "OUTPUT ";
	
	public static void ImportFromTestSuiteFile() {
		
		try
		{
			String strFile = OptionManager.getInstance().GetOption(AdapterDiversity.strTestSuiteOption).GetParameter(0);
			br = new BufferedReader(new FileReader(strFile));
			strCurrentLine = br.readLine(); // Load first line
		}
		catch (IOException e)
		{
		    System.err.format("IOException: %s%n", e);
		    return;
		}
		
		// Create a new TestSuite (unique for Diversity)
		AbstractTestSuite aTestSuite = new AbstractTestSuite();
		aTestSuite.SetName("TestSuite");
		
		// Loop on document
		while (null != strCurrentLine)
		{
			AbstractTestCase atc = ReadNextTestCase();
			if (null != atc)
			{
				aTestSuite.AddTestCase(atc);
			}
		}
		
		// Add Test suite
		aTestStructure.AddTestSuite(aTestSuite);
		
		// Close buffer
		try
		{
			br.close();
		}
		catch (IOException e)
		{
		    System.err.format("IOException: %s%n", e);
		    return;
		}
	}
	
	private static AbstractTestCase ReadNextTestCase() {
		if (null == strCurrentLine) // EOF
		{
			return(null);
		}
		
		// Jump lines in order to read TRACE line
		while (!strCurrentLine.startsWith(strTestCaseStart)) {

			try {
				strCurrentLine = br.readLine();
			} catch (IOException e) {
				System.err.format("IOException: %s%n", e);
			    return(null);
			}
			if (strCurrentLine == null) // EOF
			{
				return(null);
			}
		}
		
		// Create new Test Case
		AbstractTestCase atc = new AbstractTestCase();
		atc.SetName("TestCase_" + strCurrentLine.substring(strTestCaseStart.length()-1).trim()); // Ignore the first characters
		
		// Create call to f_preamble
		AbstractTestStep ats = new AbstractTestStep();
		AbstractFunctionCall afc = new AbstractFunctionCall(null, DiversityModelReader.strPreamble, new ArrayList<AbstractExpression>());
		AbstractInstructionSimpleExpression instr = new AbstractInstructionSimpleExpression();
		instr.SetExpression(afc);
		ats.AddInstruction(instr);
		atc.AddTestStep(ats);
		
		// loop on test steps
		while(true)
		{
			try { // Next line
				strCurrentLine = br.readLine();
			} catch (IOException e) {
				System.err.format("IOException: %s%n", e);
			    return(null);
			}
			
			AbstractTestStep step;
			step = ReadNextTestStep();
			if(null != step)
			{
				atc.AddTestStep(step);
			}
			else
			{
				break;
			}
		}
		
		// Create call to f_postambul
		ats = new AbstractTestStep();
		afc = new AbstractFunctionCall(null, DiversityModelReader.strPostambul, new ArrayList<AbstractExpression>());
		instr = new AbstractInstructionSimpleExpression();
		instr.SetExpression(afc);
		ats.AddInstruction(instr);
		atc.AddTestStep(ats);
		
		return atc;
	}
	
	private static AbstractTestStep ReadNextTestStep() {
		if (null == strCurrentLine) // EOF
		{
			return(null);
		}
		
		// Jump empty lines
		while (true) {

			strCurrentLine = strCurrentLine.trim();
			// Interpret as a test step
			if (strCurrentLine.startsWith(strFireTransition)) {
				return InterpreteFireTransition();
			}
			else if (strCurrentLine.startsWith(strInput)) {
				return InterpreteInput();
			}
			else if (strCurrentLine.startsWith(strOutput)) {
				return InterpreteOutput();
			}
			else if (strCurrentLine.startsWith(strTestCaseStart)) { // Not a step
				return (null);
			}
			// TODO: Delta?
			else // Ignore line
			{
				try {
					strCurrentLine = br.readLine();
				} catch (IOException e) {
					System.err.format("IOException: %s%n", e);
				    return(null);
				}
			}
			if (strCurrentLine == null) // EOF
			{
				return(null);
			}
		}
	}

	private static AbstractTestStep InterpreteFireTransition() {
		//TODO: Add requirements?
		
		try { // Next line
			strCurrentLine = br.readLine();
		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		    return(null);
		}
		
		return ReadNextTestStep();
	}
	
	private static AbstractTestStep InterpreteInput() {
		AbstractTestStep ats = new AbstractTestStep();
		
		// Add Function Call Instruction
		AbstractFunctionCall afc = new AbstractFunctionCall(null, "f"+ReadFunctionName(), ReadParameters());
		AbstractInstructionSimpleExpression instr = new AbstractInstructionSimpleExpression();
		instr.SetExpression(afc);
		ats.AddInstruction(instr);

		return ats;
	}
	
	private static AbstractTestStep InterpreteOutput() {
		AbstractTestStep ats = new AbstractTestStep();
		
		// Add Function Call Instruction
		AbstractFunctionCall afc = new AbstractFunctionCall(null, "f"+ReadFunctionName(), ReadParameters());
		AbstractInstructionSimpleExpression instr = new AbstractInstructionSimpleExpression();
		instr.SetExpression(afc);
		ats.AddInstruction(instr);

		return ats;
	}
	
	private static String ReadFunctionName() {
		if (null == strCurrentLine)
		{
			return(null);
		}
		
		int iStart = strCurrentLine.indexOf(">") + 1;
		int iEnd = strCurrentLine.indexOf("(");
		
		if (-1 == iStart || -1 == iEnd)
		{
			return(null);
		}
		
		return strCurrentLine.substring(iStart, iEnd);
	}
	
	private static ArrayList<AbstractExpression> ReadParameters() {
		if (null == strCurrentLine)
		{
			return(null);
		}

		ArrayList<String> aExprs = SplitExpression(strCurrentLine.substring(strCurrentLine.indexOf("(") + 1, strCurrentLine.indexOf(")")));
		ArrayList<AbstractExpression> aReturn = new ArrayList<AbstractExpression>();

		for (int i = 0; i < aExprs.size(); i++)
		{
			aReturn.add(StringToAbstractExpr(aExprs.get(i)));
		}

		return aReturn; 
	}
	
	private static ArrayList<String> SplitExpression(String strExpr) {
		int lvl = 0;
		int iEnd = 0;
		ArrayList<String> aReturn = new ArrayList<String>();
		
		// Split the string
		for (int i = 0; i < strExpr.length(); i++)
		{
			strExpr = strExpr.trim();
			if ('{' == strExpr.charAt(i) || '[' == strExpr.charAt(i))
			{
				lvl++;
			}
			else if ('}' == strExpr.charAt(i) || ']' == strExpr.charAt(i))
			{
				lvl--;
			}
			
			if (0 == lvl) // Add expression
			{
				iEnd = strExpr.indexOf(",", i);
				
				if (iEnd < 0) // last
				{
					break;
				}
				
				aReturn.add(strExpr.substring(0, iEnd).trim());
				strExpr = strExpr.substring(iEnd+1);
				i = -1; // restart
			}
		}
		
		// Last element or only one element
		aReturn.add(strExpr.trim());
		return aReturn;
	}
	
	private static AbstractExpression StringToAbstractExpr(String strExpr) {
		
		strExpr = strExpr.trim(); // Remove white space at the end and beginning
		if (strExpr.startsWith("["))
		{
			ArrayList<String> aExprs = SplitExpression(strExpr.substring(1, strExpr.lastIndexOf("]")));
			ArrayList<AbstractExpression> aReturn = new ArrayList<AbstractExpression>();
			
			for (int i = 0; i < aExprs.size(); i++)
			{
				aReturn.add(StringToAbstractExpr(aExprs.get(i)));
			}

			return new AbstractArrayValue(aReturn); 
		}
		else if (strExpr.startsWith("{"))
		{
			ArrayList<String> aExprs = SplitExpression(strExpr.substring(1, strExpr.lastIndexOf("}")));
			ArrayList<AbstractExpression> aReturn = new ArrayList<AbstractExpression>();

			for (int i = 0; i < aExprs.size(); i++)
			{
				aReturn.add(StringToAbstractExpr(aExprs.get(i)));
			}

			return new AbstractCustomValue(aReturn); 
		}
		else if (strExpr.contains("/")) // Float
		{
			int iDiv = strExpr.indexOf("/");
			int i1 = Integer.valueOf(strExpr.substring(0, iDiv));
			int i2 = Integer.valueOf(iDiv+1);

			return new AbstractFloatValue((float)i1 / i2); 
		}
		else if (Character.isDigit(strExpr.charAt(0)))
		{
			return new AbstractIntegerValue(Integer.parseInt(strExpr.replaceAll("\\D+", ""))); 
		}
		else if ("true" == strExpr)
		{
			return new AbstractBooleanValue(true); 
		}
		else if ("false" == strExpr)
		{
			return new AbstractBooleanValue(false); 
		}
		else if (strExpr.startsWith("}") || strExpr.startsWith("}]")) // Close struct
		{
			return(null);
		}
		else // String == ENUM value
		{
			return new AbstractEnumValue(null, strExpr); 
		}
	}
}
