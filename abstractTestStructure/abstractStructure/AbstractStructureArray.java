package com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure;

import com.eglobalmark.genericPublisher.abstractTestStructure.abstractType.AbstractType;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractType.AbstractTypeArray;
import com.eglobalmark.genericPublisher.publish.PublisherVisitor;

public class AbstractStructureArray extends AbstractStructure {

	private String strArrayName;
	private AbstractStructure vStruct;
	private AbstractTypeArray aType;
	
	public AbstractStructureArray() {
		super();
		aType = new AbstractTypeArray();
		vStruct = null;
	}
	
	@Override
	public void accept(PublisherVisitor v) {
		v.visit(this);
	}
	
	public void SetName(String strName) {
		aType.SetTypeName(strName);
		strArrayName = strName;
	}
	
	public String GetName() {
		return strArrayName;
	}
	
	public AbstractStructure GetStructure() {
		return(vStruct);
	}
	
	public void SetStructure(AbstractStructure vStruct) {
		this.vStruct = vStruct;
	}

	@Override
	public AbstractType GetType() {
		return aType;
	}
}
