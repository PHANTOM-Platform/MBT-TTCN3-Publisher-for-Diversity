package com.eglobalmark.genericPublisher.publish;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CustomPrintWriter extends PrintWriter {

	private String strIndentation = "";
	
	public CustomPrintWriter(File file) throws FileNotFoundException {
		super(file);
	}

	@Override
	public void write(String str) {
		super.write(strIndentation + str);
	}

	public void writeWithoutIndent(String str) {
		super.write(str);
	}
	
	public void incrementIndentation() {
		strIndentation += "\t";
	}

	public void decrementIndentation() {
		if (!strIndentation.isEmpty())
		{
			strIndentation = strIndentation.substring(1); // Substring from next tab
		}
	}
	
	public void resetIndentation() {
		strIndentation = "";
	}
}
