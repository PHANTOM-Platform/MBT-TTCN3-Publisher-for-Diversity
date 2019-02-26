package com.eglobalmark.genericPublisher.abstractTestStructure.abstractTest;

import com.eglobalmark.genericPublisher.abstractTestStructure.abstractInstance.AbstractExpression;
import com.eglobalmark.genericPublisher.publish.PublisherVisitor;

public class AbstractDeclarationVariable extends AbstractInstruction {

	private AbstractVariable vVariable;
	private AbstractExpression vExpr;
	
	AbstractDeclarationVariable(AbstractVariable vVariable)
	{
		this.vVariable = vVariable;
		vExpr = null;
	}
	
	@Override
	public void accept(PublisherVisitor v) {
		// TODO Auto-generated method stub
		v.visit(this);
	}
	
	public AbstractVariable GetVariable() {
		return vVariable;
	}

	public void SetInitExpression(AbstractExpression vExpr) {
		this.vExpr = vExpr;
	}
	
	public AbstractExpression GetInitExpression() {
		return vExpr;
	}
}
