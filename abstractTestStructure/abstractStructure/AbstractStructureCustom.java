package com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure;

import java.util.ArrayList;

import com.eglobalmark.genericPublisher.abstractTestStructure.abstractTest.AbstractVariable;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractType.AbstractType;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractType.AbstractTypeCustom;
import com.eglobalmark.genericPublisher.publish.PublisherVisitor;

public class AbstractStructureCustom extends AbstractStructure {

	private AbstractTypeCustom vCustomType;
	private ArrayList<AbstractVariable> vFields;
	
	public AbstractStructureCustom() {
		vCustomType = new AbstractTypeCustom();
		vFields = new ArrayList<AbstractVariable>();
	}
	
	@Override
	public void accept(PublisherVisitor v) {
		v.visit(this);
	}

	public void SetCustomName(String strName) {
		vCustomType.SetCustomName(strName);
	}

	public String GetCustomName() {
		return(vCustomType.GetCustomName());
	}
	
	public int GetNumberOfFields() {
		return(vFields.size());
	}
	
	public AbstractVariable GetField(int index) {

		if (index >= vFields.size())
		{
			return(null);
		}
		
		return (vFields.get(index));
	}
	
	public ArrayList<AbstractVariable> GetFileds() {
		return (vFields);
	}

	public void AddField(AbstractVariable vField) {
		vFields.add(vField);
	}
	
	public void AddFields(ArrayList<AbstractVariable> vFields) {
		vFields.addAll(vFields);
	}

	@Override
	public AbstractType GetType() {
		return vCustomType;
	}
}
