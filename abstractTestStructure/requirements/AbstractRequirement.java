package com.eglobalmark.genericPublisher.abstractTestStructure.requirements;

import com.eglobalmark.genericPublisher.abstractTestStructure.AbstractTestStructureElement;
import com.eglobalmark.genericPublisher.publish.PublisherVisitor;

public class AbstractRequirement implements AbstractTestStructureElement {
	
	private String strRequirementName;	
	private String strRequirementDescription;

	AbstractRequirement() {
		this("", "");
	}

	AbstractRequirement(String strName, String strDescr) {
		
	}

	@Override
	public void accept(PublisherVisitor v) {
		v.visit(this);		
	}
	
	public void SetRequirementName(String strName) {
		strRequirementName = strName;
	}
	
	public void SetRequirementDescription(String strDescr) {
		strRequirementDescription = strDescr;
	}

	public String GetRequirementName() {
		return (strRequirementName);
	}
	
	public String GetRequirementDescription() {
		return (strRequirementDescription);
	}
}
