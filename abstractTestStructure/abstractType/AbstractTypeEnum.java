package com.eglobalmark.genericPublisher.abstractTestStructure.abstractType;

import com.eglobalmark.genericPublisher.abstractTestStructure.AbstractTestStructureElement;
import com.eglobalmark.genericPublisher.publish.PublisherVisitor;

public class AbstractTypeEnum extends AbstractType implements AbstractTestStructureElement {

	private String strName;
	
	@Override
	public void accept(PublisherVisitor v) {
		// TODO Auto-generated method stub
		v.visit(this);
	}
	
	public void SetEnumName(String strName) {
		this.strName = strName;
	}

	public String GetEnumName() {
		return(strName);
	}
}
