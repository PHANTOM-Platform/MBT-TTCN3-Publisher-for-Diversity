package com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure;

import com.eglobalmark.genericPublisher.abstractTestStructure.AbstractTestStructureElement;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractType.AbstractType;

public abstract class AbstractStructure implements AbstractTestStructureElement {

	AbstractStructure() {

	}
	
	public abstract AbstractType GetType();
}
