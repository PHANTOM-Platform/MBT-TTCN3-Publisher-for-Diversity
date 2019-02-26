package com.eglobalmark.genericPublisher.optionsManager;

import java.util.ArrayList;

public class OptionManager extends OptionClass{

	public static final String strOutputOption = "--output";
	
	private boolean vImportFailure = false;
	private ArrayList<String> vArgs;
	private ArrayList<Option> vOptions;
	private static OptionManager Singleton = new OptionManager();
	
	private OptionManager() {
		vArgs = new ArrayList<String>();
		vOptions = new ArrayList<Option>();
		aOptions.add(new Option(strOutputOption, 1, true));
	}
	
	public static OptionManager getInstance() {
		return Singleton;
	}

	public void Initialize(String[] args) {
		if (null != args)
		{
			for (int i = 0; i < args.length; i++)
			{
				vArgs.add(args[i]);
			}
		}
		
		CheckInterface(this);
	}
	
	public boolean HasFailure()
	{
		return vImportFailure;
	}

	public int GetNumberOfArgs() {
		return vArgs.size();
	}

	public String GetArg(int iIndex) {
		if (0 > iIndex || iIndex >= vArgs.size())
		{
			return(null);
		}
		return vArgs.get(iIndex);
	}
	
	public ArrayList<String> GetArgs() {
		return vArgs;
	}
	
	public boolean CheckInterface(OptionClass vOptionInterface) {
		for (Option o : vOptionInterface.aOptions)
		{
			if (!o.ImportFromArgs(vArgs)) // Fail import
			{
				// TODO: Raise error
				vImportFailure = true;
				return(vImportFailure);
			}
			vOptions.add(o);
		}
		return(true);
	}
	
	public Option GetOption(String strOption) {
		for (Option o : vOptions)
		{
			if (o.GetOptionId() == strOption) // Fail import
			{
				return(o);
			}
		}
		return(null);
	}
}
