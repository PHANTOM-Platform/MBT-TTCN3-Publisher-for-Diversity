package com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure;

import java.util.ArrayList;

import com.eglobalmark.genericPublisher.abstractTestStructure.abstractType.AbstractType;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractType.AbstractTypeEnum;
import com.eglobalmark.genericPublisher.publish.PublisherVisitor;

public class AbstractStructureEnum extends AbstractStructure {

	private AbstractTypeEnum vEnumType;
	private ArrayList<String> vEnumList;
	
	public AbstractStructureEnum() {
		vEnumType = new AbstractTypeEnum();
		vEnumList = new ArrayList<String>();
	}
	
	@Override
	public void accept(PublisherVisitor v) {
		v.visit(this);
	}

	public void SetEnumName(String strName) {
		vEnumType.SetEnumName(strName);
	}

	public String GetEnumName() {
		return(vEnumType.GetEnumName());
	}
	
	public int GetNumberOfElements() {
		return(vEnumList.size());
	}
	
	public String GetEnumValue(int index) {

		if (index >= vEnumList.size())
		{
			return(null);
		}
		
		return (vEnumList.get(index));
	}
	
	public ArrayList<String> GetEnumValues() {
		return (vEnumList);
	}

	public void AddElement(String vElement) {
		vEnumList.add(vElement);
	}
	
	public void AddElements(ArrayList<String> vElements) {
		vEnumList.addAll(vElements);
	}

	@Override
	public AbstractType GetType() {
		// TODO Auto-generated method stub
		return vEnumType;
	}
}
