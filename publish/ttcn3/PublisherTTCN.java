package com.eglobalmark.genericPublisher.publish.ttcn3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import com.eglobalmark.genericPublisher.abstractTestStructure.AbstractTestStructure;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractInstance.AbstractArrayValue;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractInstance.AbstractBooleanValue;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractInstance.AbstractCustomValue;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractInstance.AbstractEnumValue;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractInstance.AbstractFloatValue;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractInstance.AbstractFunctionCall;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractInstance.AbstractIntegerValue;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractInstance.AbstractStringValue;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure.AbstractStructureArray;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure.AbstractStructureBoolean;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure.AbstractStructureCustom;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure.AbstractStructureEnum;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure.AbstractStructureFile;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure.AbstractStructureFloat;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure.AbstractStructureFunction;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure.AbstractStructureInteger;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure.AbstractStructureList;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure.AbstractStructurePort;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure.AbstractStructureQueue;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure.AbstractStructureString;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure.PortStructure;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractTest.AbstractDeclarationVariable;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractTest.AbstractInstruction;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractTest.AbstractInstructionSimpleExpression;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractTest.AbstractTestCase;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractTest.AbstractTestStep;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractTest.AbstractTestSuite;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractTest.AbstractVariable;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractType.AbstractTypeArray;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractType.AbstractTypeBoolean;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractType.AbstractTypeCustom;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractType.AbstractTypeEnum;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractType.AbstractTypeFile;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractType.AbstractTypeFloat;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractType.AbstractTypeInteger;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractType.AbstractTypeList;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractType.AbstractTypePort;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractType.AbstractTypeQueue;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractType.AbstractTypeString;
import com.eglobalmark.genericPublisher.abstractTestStructure.requirements.AbstractRequirement;
import com.eglobalmark.genericPublisher.optionsManager.OptionManager;
import com.eglobalmark.genericPublisher.publish.PublisherVisitor;
import com.eglobalmark.genericPublisher.publish.CustomPrintWriter;
import com.eglobalmark.genericPublisher.optionsManager.Option;

public class PublisherTTCN extends PublisherVisitor {

	private final String strTTCNExtension = ".ttcn";
	
	private final String strEnumModuleName = "TTCN_Enumerations";
	private final String strFunctionModuleName = "TTCN_Functions";
	private final String strPortModuleName= "TTCN_Ports";
	private final String strStructModuleName = "TTCN_Structures";
	private final String strComponentModuleName = "TTCN_Component";
	private final String strComponentName = "TestComponent";

	private final String strTmpDirName = "tmp";

	private String strOutputDirPath;
	private String strTmpDirPath;

	private CustomPrintWriter wEnumWriter;
	private CustomPrintWriter wFunctionWriter;
	private CustomPrintWriter wPortWriter;
	private CustomPrintWriter wStructureWriter;
	private CustomPrintWriter wComponentWriter;
	
	private CustomPrintWriter wCurrentWriter;
	
	public PublisherTTCN() {
		// Declare options here
		Option oOutput = OptionManager.getInstance().GetOption(OptionManager.strOutputOption);
		OptionManager.getInstance().CheckInterface(this);
		
		if (null == oOutput) // Default ?
		{
			strOutputDirPath = System.getProperty("user.dir");
		}
		else
		{
			strOutputDirPath = oOutput.GetParameter(0);
			if (!strOutputDirPath.endsWith(File.separator))
			{
				strOutputDirPath += File.separator;
			}
		}
		
		// Tmp directory
		strTmpDirPath = strOutputDirPath + strTmpDirName + File.separator;
		
		try {
			Files.createDirectories(Paths.get(strTmpDirPath));
		} catch (IOException e) {
		    strTmpDirPath = null;
		    System.err.format("IOException: %s%n", e);
		}
		
		// Move generated files from output to tmp?
	}
	
	private CustomPrintWriter InitializeFile(String strFilePath, String strModuleName, String... aImportModule) {
		CustomPrintWriter printer = null;
		try
		{
			File newFile = new File(strFilePath);
			if (newFile.exists()) // Copy old file
			{
				File oldFile = new File(strTmpDirPath + strFilePath.substring(strFilePath.lastIndexOf(File.separator)+1));
				Files.copy(newFile.toPath(), oldFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			}
			
			printer = new CustomPrintWriter(newFile);
			printer.write("module " + strModuleName + " {" + System.lineSeparator() + System.lineSeparator());
			printer.incrementIndentation();
			
			if (null != aImportModule)
			{
				for (String strImportModuleName : aImportModule)
				{
					printer.write("import from " + strImportModuleName + " all;" + System.lineSeparator());
				}
				
				printer.write(System.lineSeparator());
			}
		}
		catch (IOException x)
		{
		    System.err.format("IOException: %s%n", x);
		}
		
		// Copy declarations in component that are not port
		BufferedReader br = null;
		String strCurrentLine = null;
		try
		{
			if (new File(strTmpDirPath + strModuleName + strTTCNExtension).exists())
			{
				br = new BufferedReader(new FileReader(strTmpDirPath + strModuleName + strTTCNExtension));
			
				strCurrentLine = br.readLine(); // Load first line

				String strImport = "import from ";
				// Find function core
				while(null != strCurrentLine)
				{
					if (strCurrentLine.trim().startsWith(strImport))
					{
						break;
					}
					strCurrentLine = br.readLine(); // Check next line
				}
				
				// Copy imports not present
				while (null != strCurrentLine)
				{
					if (!strCurrentLine.trim().startsWith(strImport))
					{
						if (!strCurrentLine.trim().isEmpty()) // Not an import or an empty line : stop the import
						{
							break;
						}
					}
					else // starts with "import from "
					{
						String strModuleToImport = strCurrentLine.trim().substring(strImport.length()).trim();
						strModuleToImport = strModuleToImport.substring(0, strModuleToImport.indexOf(' '));
						
						boolean isImported = false;
						for (String strImportModuleName : aImportModule) // Array of predefined modules
						{
							if (strImportModuleName.equals(strModuleToImport)) // Already imported
							{
								isImported = true;
							}
						}
						
						if (!isImported) // if not already imported
						{
							printer.writeWithoutIndent(strCurrentLine + System.lineSeparator());
						}
					}

					strCurrentLine = br.readLine(); // Next line
				}

				printer.write(System.lineSeparator());
				br.close();
			}
		}
		catch (IOException e)
		{
		    System.err.format("IOException: %s%n", e);
		}
		
		return (printer);
	}
	
	private void FinalizeFile(CustomPrintWriter writer) {
		writer.decrementIndentation();
		writer.write("}" + System.lineSeparator());
		writer.close();
	}

	@Override
	public void visit(AbstractStructureFile element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AbstractStructureFloat element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AbstractStructureInteger element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AbstractStructureBoolean element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AbstractStructureString element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AbstractStructureQueue element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AbstractStructureList element) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void visit(AbstractStructureArray element) {
		wCurrentWriter = wStructureWriter;
		wStructureWriter.write("type record " + "of ");
		element.GetStructure().GetType().accept(this);
		wStructureWriter.writeWithoutIndent(" " + element.GetName() + ";" + System.lineSeparator());
	}

	@Override
	public void visit(AbstractStructureFunction element) {
		// Start Function
		wCurrentWriter = wFunctionWriter;
		String strFunctionStart = "function " + element.GetName() + " (";
		wFunctionWriter.write(strFunctionStart);
		
		// Parameters
		for (int i = 0; i < element.GetNumberOfParameters(); i++)
		{
			//TODO: Fix for simple in / out in AbstractStructureFunction?
			wFunctionWriter.writeWithoutIndent("in ");
			element.GetParameter(i).accept(this);
			if (i < element.GetNumberOfParameters()-1)
			{
				wFunctionWriter.writeWithoutIndent(", ");
			}
		}
				
		wFunctionWriter.writeWithoutIndent(") runs on " + strComponentName + " {" + System.lineSeparator());
		wFunctionWriter.incrementIndentation();
		
		// Copy last implementation
		BufferedReader br = null;
		String strCurrentLine = null;
		try
		{
			if (!(new File(strTmpDirPath + strFunctionModuleName + strTTCNExtension).exists()))
			{
				wFunctionWriter.write("setverdict(fail);" + System.lineSeparator());
			}
			else 
			{
				br = new BufferedReader(new FileReader(strTmpDirPath + strFunctionModuleName + strTTCNExtension));
			
				strCurrentLine = br.readLine(); // Load first line

				// Find function core
				while(null != strCurrentLine)
				{
					if (strCurrentLine.trim().startsWith(strFunctionStart))
					{
						strCurrentLine = br.readLine(); // Load first function line
						break;
					}
					strCurrentLine = br.readLine(); // Next line
				}
				
				// Copy function core
				int lvl = 0;
				while (null != strCurrentLine && 0 <= lvl)
				{
					// Increment / Decrement the number of blocks read
					for (int i=0; i < strCurrentLine.length(); i++)
				    {
				        if (strCurrentLine.charAt(i) == '{')
				        {
				        	lvl++;
				        }

				        if (strCurrentLine.charAt(i) == '}')
						{
							lvl--;
							if (lvl == 0) // End of module
							{
								break;
							}
						}
				    }
					
					if (lvl >= 0) // Inside function
					{
						wFunctionWriter.writeWithoutIndent(strCurrentLine + System.lineSeparator());
						strCurrentLine = br.readLine(); // Load first line
					}
				}
				
				br.close();
			}
		}
		catch (IOException e)
		{
		    System.err.format("IOException: %s%n", e);
		    return;
		}
		
		// End Function
		wFunctionWriter.decrementIndentation();
		wFunctionWriter.write("}" + System.lineSeparator() + System.lineSeparator());
	}

	@Override
	public void visit(AbstractStructurePort element) {
		// Start port declaration
		wCurrentWriter = wPortWriter;

		String strPortDecl = "type port " + element.GetPortName() + " message {" + System.lineSeparator();
		wPortWriter.write(strPortDecl);
		wPortWriter.incrementIndentation();
		
		// Add a declaration in component
		wComponentWriter.write("port " + element.GetPortName() + " cPort" + element.GetPortName() + ";" + System.lineSeparator());
		
		// Run Through all messages type
		for (PortStructure msg : element.GetMsgTypes())
		{
			switch(msg.vPortType)
			{
			case IN:
				wPortWriter.write("in ");
				break;
			case OUT:
				wPortWriter.write("out ");
				break;
			case INOUT:
				wPortWriter.write("inout ");
				break;
			}
			msg.vStructure.GetType().accept(this);
			wPortWriter.writeWithoutIndent(";" + System.lineSeparator());
		}
		
		// End port declaration
		wPortWriter.decrementIndentation();
		wPortWriter.write("}");
		
		// Open old file
		BufferedReader br = null;
		String strCurrentLine = null;
		try
		{
			if (!(new File(strTmpDirPath + strPortModuleName + strTTCNExtension).exists()))
			{
				wPortWriter.writeWithoutIndent(System.lineSeparator() + System.lineSeparator());
				return;
			}
		
			br = new BufferedReader(new FileReader(strTmpDirPath + strPortModuleName + strTTCNExtension));
			
			strCurrentLine = br.readLine(); // Load first line
			while (null != strCurrentLine)
			{
				if (strCurrentLine.trim().contains(strPortDecl.trim()))
				{
					int lvl = 1;
					
					// Find end of declaration
					while (0 < lvl)
					{
						strCurrentLine = br.readLine(); // Load first line
						if (null == strCurrentLine)
						{
							br.close();
							return;
						}
						
						// Increment / Decrement the number of blocks read
						for (int i=0; i < strCurrentLine.length(); i++)
					    {
					        if (strCurrentLine.charAt(i) == '{')
					        {
					        	lvl++;
					        }

					        if (strCurrentLine.charAt(i) == '}')
							{
								lvl--;
								if (lvl == 0) // End of module
								{
									break;
								}
							}
					    }
					}
					System.out.println(strPortDecl);
					strCurrentLine = strCurrentLine.substring(strCurrentLine.indexOf('}') + 1);
					while(!strCurrentLine.contains("type port "))//read until the end of declaration
					{
						// Increment / Decrement the number of blocks read
						for (int i=0; i < strCurrentLine.length(); i++)
					    {
					        if (strCurrentLine.charAt(i) == '{')
					        {
					        	lvl++;
					        }

					        if (strCurrentLine.charAt(i) == '}')
							{
								lvl--;
								if (lvl < 0) // End of module
								{
									wPortWriter.writeWithoutIndent(strCurrentLine.substring(0, strCurrentLine.lastIndexOf('}')));
									br.close();
									return;
								}
							}
					    }
						
						wPortWriter.writeWithoutIndent(strCurrentLine + System.lineSeparator());
						strCurrentLine = br.readLine(); // Next line
					}
				}
				
				strCurrentLine = br.readLine(); // Next line
			}
			br.close();
		}
		catch (IOException e)
		{
		    System.err.format("IOException: %s%n", e);
		    return;
		}
	}

	@Override
	public void visit(AbstractFunctionCall element) {
		// Call on variable
		if (element.OnVariable())
		{
			wCurrentWriter.writeWithoutIndent(element.GetVariable() + ".");
		}
		
		// Name$
		wCurrentWriter.writeWithoutIndent(element.GetFunctionName() + "(");
		// Parameters
		for(int i = 0; i < element.GetNumberOfExpressions(); i++)
		{
			element.GetExpression(i).accept(this);
			if (i != element.GetNumberOfExpressions() - 1) // not last element
			{
				wCurrentWriter.writeWithoutIndent(", ");
			}
		}
		
		// End of call
		wCurrentWriter.writeWithoutIndent(")");
	}

	@Override
	public void visit(AbstractTestStep element) {
		// Run through all sub-steps
		for (AbstractTestStep ats : element.GetTestSteps())
		{
			ats.accept(this);
		}

		// Run through all instructions
		for (AbstractInstruction instr : element.GetInstructions())
		{
			instr.accept(this);
		}
	}

	@Override
	public void visit(AbstractTestCase element) {
		// Start Test case
		wCurrentWriter.write("testcase " + element.GetName() + "() runs on " + strComponentName + " {" + System.lineSeparator());
		wCurrentWriter.incrementIndentation();
		
		// Add Test Steps
		for (AbstractTestStep ats : element.GetTestSteps())
		{
			wCurrentWriter.write("");
			ats.accept(this);
		}
		
		// End Test Case

		wCurrentWriter.write("setverdict(pass);" + System.lineSeparator());
		wCurrentWriter.decrementIndentation();
		wCurrentWriter.write("}" + System.lineSeparator() + System.lineSeparator());
	}
	
	@Override
	public void visit(AbstractTestSuite element) {
		// File name and path
		String strFileName = "TTCN_" + element.GetName();
		String strCurrentTestSuiteFile = strOutputDirPath + strFileName + strTTCNExtension;
		
		// Initialize Test Suite file
		wCurrentWriter = InitializeFile(strCurrentTestSuiteFile, strFileName, strEnumModuleName, strFunctionModuleName, strPortModuleName, strStructModuleName, strComponentModuleName);
		
		// Add Test cases
		ArrayList<String> aTestCasesList = new ArrayList<String>();
		for (AbstractTestCase atc : element.GetTestCases())
		{
			aTestCasesList.add(atc.GetName());
			atc.accept(this);
		}
		
		// Add control part
		wCurrentWriter.write("control {" + System.lineSeparator());
		wCurrentWriter.incrementIndentation();
		for (String strTCName : aTestCasesList)
		{
			wCurrentWriter.write("execute(" + strTCName + "());" + System.lineSeparator());
		}
		wCurrentWriter.decrementIndentation();
		wCurrentWriter.write("}" + System.lineSeparator());
		
		// Finalize Test Suite file
		FinalizeFile(wCurrentWriter);
		
		wCurrentWriter.close();
	}

	@Override
	public void visit(AbstractRequirement element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AbstractTestStructure element) {
		// Initialize all files
		wEnumWriter = InitializeFile(strOutputDirPath + strEnumModuleName + strTTCNExtension, strEnumModuleName);
		wStructureWriter = InitializeFile(strOutputDirPath + strStructModuleName + strTTCNExtension, strStructModuleName, strEnumModuleName);
		wFunctionWriter = InitializeFile(strOutputDirPath + strFunctionModuleName + strTTCNExtension, strFunctionModuleName, strComponentModuleName, strEnumModuleName, strPortModuleName, strStructModuleName);
		wPortWriter = InitializeFile(strOutputDirPath + strPortModuleName + strTTCNExtension, strPortModuleName, strEnumModuleName, strStructModuleName);
		wComponentWriter = InitializeFile(strOutputDirPath + strComponentModuleName + strTTCNExtension, strComponentModuleName, strPortModuleName);
		
		// Start Component : will instantiate port
		String strComponentStart = "type component " + strComponentName + " {" + System.lineSeparator();
		wComponentWriter.write(strComponentStart);
		wComponentWriter.incrementIndentation();
		
		// Structures
		for (int i = 0; i < element.GetNumberOfTestStructures(); i++)
		{
			element.GetTestStructure(i).accept(this);
		}

		// Copy declarations in component that are not port //TODO: FIX for CertifyIt
		BufferedReader br = null;
		String strCurrentLine = null;
		try
		{
			if (new File(strTmpDirPath + strComponentModuleName + strTTCNExtension).exists())
			{
				br = new BufferedReader(new FileReader(strTmpDirPath + strComponentModuleName + strTTCNExtension));
			
				strCurrentLine = br.readLine(); // Load first line

				// Find function core
				while(null != strCurrentLine)
				{
					if (strCurrentLine.trim().startsWith(strComponentStart.trim()))
					{
						strCurrentLine = br.readLine(); // Load first line
						break;
					}
					strCurrentLine = br.readLine(); // Check next line
				}
				
				// Copy declarations
				int lvl = 0;
				while (null != strCurrentLine && 0 <= lvl)
				{
					// Increment / Decrement the number of blocks read
					for (int i=0; i < strCurrentLine.length(); i++)
				    {
				        if (strCurrentLine.charAt(i) == '{')
				        {
				        	lvl++;
				        }

				        if (strCurrentLine.charAt(i) == '}')
						{
							lvl--;
							if (lvl == 0) // End of module
							{
								break;
							}
						}
				    }
					
					if (lvl >= 0 && !strCurrentLine.trim().startsWith("port ")) // Inside component and not a port declaration
					{
						wComponentWriter.writeWithoutIndent(strCurrentLine + System.lineSeparator());
					}
					strCurrentLine = br.readLine(); // Next line
				}
				
				br.close();
			}
		}
		catch (IOException e)
		{
		    System.err.format("IOException: %s%n", e);
		    return;
		}
		
		// End component
		wComponentWriter.decrementIndentation();
		wComponentWriter.write("}" + System.lineSeparator());
		
		// Finalize all files
		FinalizeFile(wEnumWriter);
		FinalizeFile(wFunctionWriter);
		FinalizeFile(wStructureWriter);
		FinalizeFile(wPortWriter);
		FinalizeFile(wComponentWriter);
		
		// TestSuites
		for (int i = 0; i < element.GetNumberOfTestSuites(); i++)
		{
			element.GetTestSuite(i).accept(this);
		}
		
		// TODO: Add all call to the test suites
	}

	@Override
	public void visit(AbstractStructureCustom element) {
		wCurrentWriter = wStructureWriter;
		
		// Start Declaration
		wStructureWriter.write("type record " + element.GetCustomName() + " {" + System.lineSeparator());
		wStructureWriter.incrementIndentation();
		
		// Run through all fields
		for (int i = 0; i < element.GetNumberOfFields(); i++)
		{
			wStructureWriter.write("");
			element.GetField(i).accept(this);
			if (element.GetNumberOfFields() - 1 != i) // Last element
			{
				wStructureWriter.writeWithoutIndent(",");
			}
			wStructureWriter.writeWithoutIndent(System.lineSeparator());
		}

		// End Declaration
		wStructureWriter.decrementIndentation();
		wStructureWriter.write("}" + System.lineSeparator() + System.lineSeparator());
	}

	@Override
	public void visit(AbstractVariable element) {
		element.GetAbstractStructure().GetType().accept(this);
		wCurrentWriter.writeWithoutIndent(" " + element.GetName());
	}

	@Override
	public void visit(AbstractStructureEnum element) {
		
		wEnumWriter.write("type enumerated " + element.GetEnumName() + " {" + System.lineSeparator());
		wEnumWriter.incrementIndentation();
		
		for (int i = 0; i < element.GetNumberOfElements(); i++)
		{
			if (element.GetNumberOfElements() - 1 == i) // Last element
			{
				wEnumWriter.write(element.GetEnumValue(i) + System.lineSeparator());
			}
			else 
			{
				wEnumWriter.write(element.GetEnumValue(i) + ", " + System.lineSeparator());
			}
		}

		wEnumWriter.decrementIndentation();
		wEnumWriter.write("}" + System.lineSeparator() + System.lineSeparator());
	}

	@Override
	public void visit(AbstractFloatValue element) {
		wCurrentWriter.writeWithoutIndent(Float.toString(element.GetValue()));
	}

	@Override
	public void visit(AbstractIntegerValue element) {
		wCurrentWriter.writeWithoutIndent(Integer.toString(element.GetValue()));
	}

	@Override
	public void visit(AbstractEnumValue element) {
		wCurrentWriter.writeWithoutIndent(element.GetValue());
	}

	@Override
	public void visit(AbstractTypeBoolean element) {
		wCurrentWriter.writeWithoutIndent("boolean");
	}

	@Override
	public void visit(AbstractTypeCustom element) {
		wCurrentWriter.writeWithoutIndent(element.GetCustomName());
	}

	@Override
	public void visit(AbstractTypeEnum element) {
		wCurrentWriter.writeWithoutIndent(element.GetEnumName());
	}

	@Override
	public void visit(AbstractTypeFloat element) {
		wCurrentWriter.writeWithoutIndent("float");
	}

	@Override
	public void visit(AbstractTypeInteger element) {
		wCurrentWriter.writeWithoutIndent("integer");
	}

	@Override
	public void visit(AbstractTypePort element) {
		wCurrentWriter.writeWithoutIndent("port " + element.GetPortName());
	}

	@Override
	public void visit(AbstractTypeQueue element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AbstractTypeFile element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AbstractTypeList element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AbstractTypeArray element) {
		wCurrentWriter.writeWithoutIndent(element.GetArrayType());
	}

	@Override
	public void visit(AbstractTypeString element) {
		wCurrentWriter.writeWithoutIndent("charstring");
	}

	@Override
	public void visit(AbstractBooleanValue element) {
		if (element.GetValue())
		{
			wCurrentWriter.writeWithoutIndent("true");
		}
		else
		{
			wCurrentWriter.writeWithoutIndent("false");
		}
	}

	@Override
	public void visit(AbstractStringValue element) {
		wCurrentWriter.writeWithoutIndent(element.GetValue());
	}

	@Override
	public void visit(AbstractInstructionSimpleExpression element) {
		element.GetExpression().accept(this);
		wCurrentWriter.writeWithoutIndent(";" + System.lineSeparator());
	}

	@Override
	public void visit(AbstractDeclarationVariable element) {
		element.GetVariable().accept(this);
		wCurrentWriter.writeWithoutIndent(";" + System.lineSeparator());
	}

	@Override
	public void visit(AbstractArrayValue element) {
		
		// Start Array
		wCurrentWriter.writeWithoutIndent("{ ");
		
		// Display all elements
		for (int i = 0; i < element.GetNumberOfValues(); i++)
		{
			element.GetValue(i).accept(this);
			if (i < element.GetNumberOfValues()-1) // Not last
			{
				wCurrentWriter.writeWithoutIndent(", ");
			}
		}
		
		// Close Array
		wCurrentWriter.writeWithoutIndent("}");
	}

	@Override
	public void visit(AbstractCustomValue element) {
		// Start Custom
		wCurrentWriter.writeWithoutIndent("{ ");
		
		// Display all elements
		for (int i = 0; i < element.GetNumberOfValues(); i++)
		{
			element.GetValue(i).accept(this);
			if (i < element.GetNumberOfValues()-1) // Not last
			{
				wCurrentWriter.writeWithoutIndent(", ");
			}
		}
		
		// Close Custom
		wCurrentWriter.writeWithoutIndent("}");
	}

}
