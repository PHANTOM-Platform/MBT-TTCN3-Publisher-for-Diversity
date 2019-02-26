package com.eglobalmark.genericPublisher.abstractTestStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure.AbstractStructure;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractTest.AbstractTestSuite;
import com.eglobalmark.genericPublisher.publish.PublisherVisitor;

public class AbstractTestStructure implements AbstractTestStructureElement {

	Map<String, AbstractStructure> mapping = new HashMap<>();
	private ArrayList<AbstractTestSuite> vAbstractTestSuites;
	private ArrayList<AbstractStructure> vAbstractStructures;
	private static AbstractTestStructure Singleton = new AbstractTestStructure();
	
	private AbstractTestStructure() {
		vAbstractTestSuites = new ArrayList<AbstractTestSuite>();
		vAbstractStructures = new ArrayList<AbstractStructure>();
	}

	public static AbstractTestStructure getInstance() {
		return Singleton;
	}
	
	@Override
	public void accept(PublisherVisitor v) {
		v.visit(this);
	}

	public int GetNumberOfTestSuites() {
		return(vAbstractTestSuites.size());
	}
	
	public AbstractTestSuite GetTestSuite(int index) {

		if (index >= vAbstractTestSuites.size())
		{
			return(null);
		}
		
		return (vAbstractTestSuites.get(index));
	}
	
	public ArrayList<AbstractTestSuite> GetTestSuites() {
		return (vAbstractTestSuites);
	}

	public void AddTestSuite(AbstractTestSuite ats) {
		vAbstractTestSuites.add(ats);
	}
	
	public int GetNumberOfTestStructures() {
		return(vAbstractStructures.size());
	}

	public AbstractStructure GetTestStructure(int index) {

		if (index >= vAbstractStructures.size())
		{
			return(null);
		}
		
		return (vAbstractStructures.get(index));
	}

	public ArrayList<AbstractStructure> GetTestStructures() {
		return (vAbstractStructures);
	}

	public void AddStructure(AbstractStructure ats) {
		vAbstractStructures.add(ats);
	}
	
	public void AddStructureAndDeclare(AbstractStructure ats, String strStructName) {
		mapping.put(strStructName, ats);
		AddStructure(ats);
	}
	
	public AbstractStructure GetDeclaredStructure(String strId) {
		return mapping.get(strId);
	}
}
