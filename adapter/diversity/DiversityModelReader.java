package com.eglobalmark.genericPublisher.adapter.diversity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.eglobalmark.genericPublisher.abstractTestStructure.AbstractTestStructure;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure.AbstractStructure;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure.AbstractStructureArray;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure.AbstractStructureBoolean;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure.AbstractStructureCustom;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure.AbstractStructureEnum;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure.AbstractStructureFloat;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure.AbstractStructureFunction;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure.AbstractStructureInteger;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure.AbstractStructurePort;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure.EnumPortType;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure.PortStructure;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractTest.AbstractVariable;
import com.eglobalmark.genericPublisher.optionsManager.OptionManager;

public class DiversityModelReader {
	
	private static AbstractTestStructure aTestStructure = AbstractTestStructure.getInstance();
	
	private static BufferedReader br = null;
	private static String strCurrentLine;
	
	private static AbstractStructurePort aPort = null;

	private static final String strType = "type ";
	private static final String strEnum = "enum";
	private static final String strStruct = "struct";
	private static final String strPort = "port";
	private static final String strBool = "boolean";
	private static final String strInt = "integer";
	private static final String strFloat = "float";
	private static final String strArray = "array";

	public static final String strPreamble = "f_preamble";
	public static final String strPostambul = "f_postambul";
	
	public static void ImportFromModelFile() {
				
		try
		{
			String strFile = OptionManager.getInstance().GetOption(AdapterDiversity.strXLIAOption).GetParameter(0);
			br = new BufferedReader(new FileReader(strFile));
			strCurrentLine = br.readLine(); // Load first line
		}
		catch (IOException e)
		{
		    System.err.format("IOException: %s%n", e);
		    return;
		}

		// Preamble function
		AbstractStructureFunction aFunctionPreamble = new AbstractStructureFunction();
		aFunctionPreamble.SetName(strPreamble);
		aTestStructure.AddStructureAndDeclare(aFunctionPreamble, strPreamble);

		// Postambul function
		AbstractStructureFunction aFunctionPostambul = new AbstractStructureFunction();
		aFunctionPostambul.SetName(strPostambul);
		aTestStructure.AddStructureAndDeclare(aFunctionPostambul, strPostambul);
		
		while (ReadNextStructure());
		
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
	
	private static boolean ReadNextStructure() {

		strCurrentLine = strCurrentLine.trim(); // Remove white space at the end and beginning
		if (strCurrentLine.startsWith(strType))
		{
			strCurrentLine = strCurrentLine.substring(strType.length());
			return (null != ReadType());
		}
		else if (strCurrentLine.startsWith(strPort))
		{
			strCurrentLine = strCurrentLine.substring(strPort.length());
			return (null != ReadPort());
		}
		
		// Jump lines
		try
		{
			strCurrentLine = br.readLine(); // Load first line
		}
		catch (IOException e)
		{
		    System.err.format("IOException: %s%n", e);
		    return(false);
		}
		return(null != strCurrentLine);
	}
	
	private static AbstractStructure ReadType() {
		strCurrentLine = strCurrentLine.trim(); // Remove white space at the end and beginning
		String strTypeName = strCurrentLine.substring(0, strCurrentLine.indexOf(" ")); // find type name

		strCurrentLine = strCurrentLine.substring(strCurrentLine.indexOf(" ") + 1); // Ignore type name
		int iEnd = 0 < strCurrentLine.indexOf(" ") ? strCurrentLine.indexOf(" ") : strCurrentLine.length();
		String strType = strCurrentLine.substring(0, iEnd).trim(); // Get Type
		
		while(true)
		{
			int i = strCurrentLine.indexOf("{");
			if(0 <= i) // found
			{
				strCurrentLine = strCurrentLine.substring(i + 1).trim(); // Start of declaration
				break;
			}
			else // next line
			{
				try
				{
					strCurrentLine = br.readLine(); // Load first line
				}
				catch (IOException e)
				{
				    System.err.format("IOException: %s%n", e);
				    return(null);
				}
			}
		}
		
		AbstractStructure aStruct = null;
		if (strStruct.equals(strType))
		{
			aStruct = new AbstractStructureCustom();
			((AbstractStructureCustom)aStruct).SetCustomName(strTypeName);
			ReadCustomFields(((AbstractStructureCustom)aStruct));
		}
		else if (strEnum.equals(strType))
		{
			aStruct = new AbstractStructureEnum();
			((AbstractStructureEnum)aStruct).SetEnumName(strTypeName);
			ReadEnumValues(((AbstractStructureEnum)aStruct));
		}
		
		// Register structure
		aTestStructure.AddStructureAndDeclare(aStruct, strTypeName);
		return(aStruct);
	}

	private static void ReadCustomFields(AbstractStructureCustom vStruct) {
		boolean bContinue = true;
		while(bContinue)
		{
			// locate end
			strCurrentLine = strCurrentLine.trim(); // clean
			
			// Find index of the next param
			int iEnd = strCurrentLine.indexOf(";"); //Locate next ","
			if (iEnd < 0)
			{
				iEnd = strCurrentLine.indexOf("}"); //Locate ")" if no ","
				if (iEnd < 0) // Not this line, read the next one
				{
					try
					{
						strCurrentLine = br.readLine();
					}
					catch (IOException e)
					{
					    System.err.format("IOException: %s%n", e);
					    return;
					}
					continue; // restart loop
				}
				else 
				{
					bContinue = false; // } was found
				}
			}
			
			String strType = strCurrentLine.substring(strCurrentLine.indexOf(" ") + 1, iEnd).trim(); // ignore "var "
			
			// Interpret Type
			AbstractVariable var = ReadTypeVariable(strType);
			if (null == var)
			{
				return;
			}
			vStruct.AddField(var);
			
			strCurrentLine = strCurrentLine.substring(iEnd + 1); // Next portion
		}
	}
	
	private static AbstractStructure ReadStructure(String str) {
		if (strBool.equals(str))
		{
			return (new AbstractStructureBoolean());
		}
		else if (strInt.equals(str))
		{
			return (new AbstractStructureInteger());
		}
		else if (strFloat.equals(str))
		{
			return (new AbstractStructureFloat());
		}
		else if (str.startsWith(strArray))
		{
			int iStart = str.indexOf("<") + 1;
			int iEnd = (0 < str.lastIndexOf(",")) ? str.lastIndexOf(",") : str.lastIndexOf(">");
			String strTypeName = str.substring(iStart, iEnd).trim();
			AbstractStructureArray aStruct = new AbstractStructureArray();
			
			AbstractStructure subStruct = ReadStructure(strTypeName);
			if (null == subStruct)
			{
				return (null);
			}

			// Setup Array structure
			aStruct.SetName(strTypeName+"s");
			aStruct.SetStructure(subStruct);
			
			if (null == aTestStructure.GetDeclaredStructure(strTypeName+"s"))
			{
				aTestStructure.AddStructureAndDeclare(aStruct, strTypeName+"s");
			}
			
			return (aStruct);
		}
		else // custom
		{
			return (AbstractTestStructure.getInstance().GetDeclaredStructure(str));
		}
	}
	
	private static AbstractVariable ReadTypeVariable(String str) {
		str = str.trim();
		if (str.isEmpty())
		{
			return(null);
		}
		
		int iSpace = str.indexOf(" ");
		String strName = str.substring(iSpace + 1);
		String strType = str.substring(0, iSpace);
		
		AbstractStructure struct = ReadStructure(strType);
		if (null == struct)
		{
			return (null);
		}
		
		AbstractVariable vVar = new AbstractVariable();
		vVar.SetName(strName);
		vVar.SetAbstractStructure(struct);
		
		return(vVar);
	}

	private static void ReadEnumValues(AbstractStructureEnum vStruct) {
		boolean bContinue = true;
		while(bContinue)
		{
			// locate end
			strCurrentLine = strCurrentLine.trim(); // clean
			
			// Find index of the next param
			int iEnd = strCurrentLine.indexOf(","); //Locate next ","
			if (iEnd < 0)
			{
				iEnd = strCurrentLine.indexOf("}"); //Locate "}" if no ","
				if (iEnd < 0) // Not this line, read the next one
				{
					try
					{
						strCurrentLine = br.readLine();
					}
					catch (IOException e)
					{
					    System.err.format("IOException: %s%n", e);
					    return;
					}
					continue; // restart loop
				}
				else 
				{
					bContinue = false; // } was found
				}
			}
			
			String strEnumValue = strCurrentLine.substring(0, iEnd).trim(); // ignore "var "
			if (null == strEnumValue)
			{
				return;
			}
			vStruct.AddElement(strEnumValue);
			
			strCurrentLine = strCurrentLine.substring(iEnd + 1); // Next portion
		}
	}
	
	private static AbstractStructure ReadPort() { // Create Function !!!
		
		if (null == aPort)
		{
			String strPortName = "Generic_Port";
			aPort = new AbstractStructurePort();
			aPort.SetPortName(strPortName);
			aTestStructure.AddStructureAndDeclare(aPort, strPortName);
		}
		
		strCurrentLine = strCurrentLine.trim(); // Remove white space at the end and beginning
		
		AbstractStructureFunction aFunction = new AbstractStructureFunction();
		AbstractStructureCustom cStruct = new AbstractStructureCustom();
		
		// Find Port Type ! (Input / Output)
		String strPortType = strCurrentLine.substring(0, strCurrentLine.indexOf(" ")).trim();
		
		// Find Port Name !
		strCurrentLine = strCurrentLine.substring(strCurrentLine.indexOf(" ") + 1); // Skip type word
		int iEnd = strCurrentLine.indexOf("(");
		String strPortName = strCurrentLine.substring(0, iEnd).trim(); // find port name
		aFunction.SetName("f"+strPortName);
		cStruct.SetCustomName(strPortName);
		
		// Register structure
		aTestStructure.AddStructureAndDeclare(aFunction, "f"+strPortName);
		aTestStructure.AddStructureAndDeclare(cStruct, strPortName);
		
		strCurrentLine = strCurrentLine.substring(iEnd + 1); // Before args

		ReadParameter(aFunction, cStruct);
		
		// Add in generic port
		PortStructure vPortStruct = new PortStructure();
		vPortStruct.vStructure = cStruct;
		if (strPortType.equals("input"))
		{
			vPortStruct.vPortType = EnumPortType.OUT;
		}
		else if (strPortType.equals("output"))
		{
			vPortStruct.vPortType = EnumPortType.IN;
		}
		
		aPort.AddMsgType(vPortStruct);
		
		return(aFunction);
	}
	
	private static void ReadParameter(AbstractStructureFunction aFunction, AbstractStructureCustom cStruct) {
		boolean bContinue = true;
		
		while(bContinue)
		{
			// locate end
			strCurrentLine = strCurrentLine.trim(); // clean
			
			// Find index of the next param

			int iArray = strCurrentLine.indexOf('<');
			int iEnd = strCurrentLine.indexOf(","); //Locate next ","
			if (iArray >= 0 && iEnd > iArray) // In case we are in an array
			{
				iEnd = strCurrentLine.indexOf(',', strCurrentLine.indexOf('>')); // Find next "," AFTER the array
			}
			
			if (iEnd < 0)
			{
				iEnd = strCurrentLine.indexOf(")"); //Locate ")" if no ","
				if (iEnd < 0) // Not this line, read the next one
				{
					try
					{
						strCurrentLine += br.readLine();
					}
					catch (IOException e)
					{
					    System.err.format("IOException: %s%n", e);
					    return;
					}
					continue; // restart loop
				}
				else 
				{
					bContinue = false;
				}
			}

			String strType = strCurrentLine.substring(0, iEnd).trim(); // Isolate type name
			
			// Interpret Type
			AbstractVariable var = FromString(strType);
			if (null == var)
			{
				return;
			}
			aFunction.AddParameter(var);
			cStruct.AddField(var);
			
			strCurrentLine = strCurrentLine.substring(iEnd + 1); // Next portion
		}
	}
	
	private static AbstractVariable FromString(String strTypeDecl) {
		strTypeDecl = strTypeDecl.trim();

		String strName;
		AbstractStructure vStruct;
		
		if (strTypeDecl.startsWith(strBool))
		{
			strName = "vBool";
			vStruct = new AbstractStructureBoolean();
		}
		else if (strTypeDecl.startsWith(strInt))
		{
			strName = "vInt";
			vStruct = new AbstractStructureInteger();
		}
		else if (strTypeDecl.startsWith(strFloat))
		{
			strName = "vFloat";
			vStruct = new AbstractStructureFloat();
		}
		else if (strTypeDecl.startsWith(strArray))
		{
			strName = "vArray";
			
			vStruct = ReadStructure(strTypeDecl);
			if (null == vStruct)
			{
				return (null);
			}
		}
		else if (null != AbstractTestStructure.getInstance().GetDeclaredStructure(strTypeDecl)) // custom
		{
			vStruct = AbstractTestStructure.getInstance().GetDeclaredStructure(strTypeDecl);
			strName = "v" + strTypeDecl;
		}
		else
		{
			return(null);
		}

		AbstractVariable vVar = new AbstractVariable();
		vVar.SetName(strName);
		vVar.SetAbstractStructure(vStruct);
		
		return(vVar);
	}
}
