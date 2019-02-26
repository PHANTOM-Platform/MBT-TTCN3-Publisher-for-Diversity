/**
 * 
 */
package com.eglobalmark.genericPublisher.publish;

import com.eglobalmark.genericPublisher.abstractTestStructure.AbstractTestStructure;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractInstance.AbstractArrayValue;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractInstance.AbstractBooleanValue;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractInstance.AbstractCustomValue;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractInstance.AbstractEnumValue;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractInstance.AbstractFloatValue;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractInstance.AbstractFunctionCall;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractInstance.AbstractIntegerValue;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractInstance.AbstractStringValue;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure.AbstractStructureArray;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure.AbstractStructureBoolean;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure.AbstractStructureCustom;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure.AbstractStructureEnum;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure.AbstractStructureFile;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure.AbstractStructureFloat;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure.AbstractStructureFunction;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure.AbstractStructureInteger;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure.AbstractStructureList;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure.AbstractStructurePort;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure.AbstractStructureQueue;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractStructure.AbstractStructureString;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractTest.AbstractDeclarationVariable;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractTest.AbstractInstructionSimpleExpression;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractTest.AbstractTestCase;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractTest.AbstractTestStep;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractTest.AbstractTestSuite;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractTest.AbstractVariable;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractType.AbstractTypeArray;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractType.AbstractTypeBoolean;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractType.AbstractTypeCustom;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractType.AbstractTypeEnum;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractType.AbstractTypeFile;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractType.AbstractTypeFloat;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractType.AbstractTypeInteger;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractType.AbstractTypeList;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractType.AbstractTypePort;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractType.AbstractTypeQueue;
import com.eglobalmark.genericPublisher.abstractTestStructure.abstractType.AbstractTypeString;
import com.eglobalmark.genericPublisher.abstractTestStructure.requirements.AbstractRequirement;
import com.eglobalmark.genericPublisher.optionsManager.OptionClass;

/**
 * @author nspaseski
 *
 */
public abstract class PublisherVisitor extends OptionClass {
	
	public abstract void visit(AbstractTestStructure element);

	public abstract void visit(AbstractStructureArray element);
	public abstract void visit(AbstractStructureBoolean element);
	public abstract void visit(AbstractStructureCustom element);
	public abstract void visit(AbstractStructureEnum element);
	public abstract void visit(AbstractStructureFile element);
	public abstract void visit(AbstractStructureFloat element);
	public abstract void visit(AbstractStructureFunction element);
	public abstract void visit(AbstractStructureInteger element);
	public abstract void visit(AbstractStructureList element);
	public abstract void visit(AbstractStructurePort element);
	public abstract void visit(AbstractStructureQueue element);
	public abstract void visit(AbstractStructureString element);

	public abstract void visit(AbstractTypeArray element);
	public abstract void visit(AbstractTypeBoolean element);
	public abstract void visit(AbstractTypeCustom element);
	public abstract void visit(AbstractTypeEnum element);
	public abstract void visit(AbstractTypeFile element);
	public abstract void visit(AbstractTypeFloat element);
	public abstract void visit(AbstractTypeInteger element);
	public abstract void visit(AbstractTypeList element);
	public abstract void visit(AbstractTypePort element);
	public abstract void visit(AbstractTypeQueue element);
	public abstract void visit(AbstractTypeString element);

	public abstract void visit(AbstractArrayValue element);
	public abstract void visit(AbstractBooleanValue element);
	public abstract void visit(AbstractCustomValue element);
	public abstract void visit(AbstractEnumValue element);
	public abstract void visit(AbstractFloatValue element);
	public abstract void visit(AbstractIntegerValue element);
	public abstract void visit(AbstractStringValue element);
	public abstract void visit(AbstractFunctionCall element);
	
	public abstract void visit(AbstractTestCase element);
	public abstract void visit(AbstractTestStep element);
	public abstract void visit(AbstractTestSuite element);
	public abstract void visit(AbstractVariable element);

	public abstract void visit(AbstractInstructionSimpleExpression element);
	public abstract void visit(AbstractDeclarationVariable element);
	
	public abstract void visit(AbstractRequirement element);
	

}
