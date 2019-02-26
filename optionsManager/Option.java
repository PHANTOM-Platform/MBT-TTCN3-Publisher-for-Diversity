package com.eglobalmark.genericPublisher.optionsManager;

import java.util.ArrayList;

public class Option {

	private boolean isActivated;
	private String strOption;
	private int nbArgs;
	private ArrayList<String> aParameters;
	private boolean isMandatory;
	
	public Option(String strOption, int nbArgs, boolean bMandatory) {
		this.isActivated = false;
		this.strOption = strOption;
		this.nbArgs = nbArgs;
		isMandatory = bMandatory;
		aParameters = new ArrayList<String>();
	}

	public boolean IsActivated() {
		return isActivated;
	}
	
	public boolean IsMandatory() {
		return isMandatory;
	}
	
	public String GetOptionId() {
		return strOption;
	}
	
	public int GetNbArgs() {
		return nbArgs;
	}

	public String GetParameter(int iIndex) {
		if (iIndex < aParameters.size())
		{
			return aParameters.get(iIndex);
		}
		return null;
	}

	public ArrayList<String> GetParameters() {
		return aParameters;
	}
	
	public boolean ImportFromArgs(ArrayList<String> args)
	{
		if (null == args)
		{
			return(false);
		}
		
		int index = args.indexOf(strOption);
		if (index != -1)
		{
			for (int i = 1; i <= nbArgs; i++)
			{
				if (index + i >= args.size()) // out of range
				{
					return(false);
				}
				
				String tmp = args.get(index + i);
				if (tmp.startsWith("-")) // Not a arg but an option
				{
					return(false);
				}
				aParameters.add(tmp);
			}
			isActivated = true;
			return(true);
		}
		return (!IsMandatory());
	}
}
