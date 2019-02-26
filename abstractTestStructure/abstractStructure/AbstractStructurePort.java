package com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure;

import java.util.ArrayList;

import com.eglobalmark.genericPublisher.abstractTestStructure.abstractType.AbstractType;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractType.AbstractTypePort;
import com.eglobalmark.genericPublisher.publish.PublisherVisitor;

public class AbstractStructurePort extends AbstractStructure {

	private AbstractTypePort vPortType;
	private ArrayList<PortStructure> vPortMesssages;

	public AbstractStructurePort() {
		vPortType = new AbstractTypePort();
		vPortMesssages = new ArrayList<PortStructure>();
	}
	
	@Override
	public void accept(PublisherVisitor v) {
		v.visit(this);
	}

	public void SetPortName(String strName) {
		vPortType.SetPortName(strName);
	}

	public String GetPortName() {
		return(vPortType.GetPortName());
	}
	
	public int GetNumberOfMsgTypes() {
		return(vPortMesssages.size());
	}
	
	public PortStructure GetMsgType(int index) {

		if (index >= vPortMesssages.size())
		{
			return(null);
		}
		
		return (vPortMesssages.get(index));
	}
	
	public ArrayList<PortStructure> GetMsgTypes() {
		return (vPortMesssages);
	}

	public void AddMsgType(PortStructure vPortStruct) {
		vPortMesssages.add(vPortStruct);
	}
	
	@Override
	public AbstractType GetType() {
		// TODO Auto-generated method stub
		return vPortType;
	}
}
