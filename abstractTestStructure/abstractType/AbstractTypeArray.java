package com.eglobalmark.genericPublisher.abstractTestStructure.abstractType;

import com.eglobalmark.genericPublisher.abstractTestStructure.AbstractTestStructureElement;
import com.eglobalmark.genericPublisher.publish.PublisherVisitor;

public class AbstractTypeArray extends AbstractType implements AbstractTestStructureElement {

	String strTypeName;
	
	@Override
	public void accept(PublisherVisitor v) {
		// TODO Auto-generated method stub
		v.visit(this);
	}
	
	public void SetTypeName(String strTypeName) {
		this.strTypeName = strTypeName;
	}

	public String GetArrayType() {
		return(strTypeName);
	}
}
