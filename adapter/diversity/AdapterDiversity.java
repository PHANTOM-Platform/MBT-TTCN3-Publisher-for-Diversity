package com.eglobalmark.genericPublisher.adapter.diversity;

import com.eglobalmark.genericPublisher.abstractTestStructure.AbstractTestStructure;
import com.eglobalmark.genericPublisher.adapter.ToolAdapter;
import com.eglobalmark.genericPublisher.optionsManager.Option;
import com.eglobalmark.genericPublisher.optionsManager.OptionManager;
import com.eglobalmark.genericPublisher.publish.ttcn3.PublisherTTCN;

public class AdapterDiversity extends ToolAdapter {

	public static final String strXLIAOption = "--xlia";
	public static final String strTestSuiteOption = "--testsuite";
	
	public AdapterDiversity() {
		// Declare options here
		aOptions.add(new Option(strXLIAOption, 1, true));
		aOptions.add(new Option(strTestSuiteOption, 1, true));
		OptionManager.getInstance().CheckInterface(this);
	}
	
	@Override
	public void CreateAbstractTestStructure() {

		System.out.println("Importing Structure : " + OptionManager.getInstance().GetOption(strXLIAOption).GetParameter(0));
		DiversityModelReader.ImportFromModelFile();

		System.out.println("Importing Test Suite : " + OptionManager.getInstance().GetOption(strTestSuiteOption).GetParameter(0));
		DiversityTestSuiteReader.ImportFromTestSuiteFile();
	}
	
	public static void main(String[] args) {
		OptionManager.getInstance().Initialize(args);
		
		AdapterDiversity adapter = new AdapterDiversity();
		PublisherTTCN publisher = new PublisherTTCN();
		
		if (OptionManager.getInstance().HasFailure())
		{
			System.out.println("Wrong options");
			return; // Error with options
		}
		
		System.out.println("Creating Abstract Test Structure");
		adapter.CreateAbstractTestStructure();
		
		System.out.println("Publishing");
		publisher.visit(AbstractTestStructure.getInstance());

		System.out.println("Finished");
	}

}
