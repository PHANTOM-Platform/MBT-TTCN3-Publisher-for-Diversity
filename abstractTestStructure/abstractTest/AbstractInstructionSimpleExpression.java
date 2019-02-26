package com.eglobalmark.genericPublisher.abstractTestStructure.abstractTest;

import com.eglobalmark.genericPublisher.abstractTestStructure.abstractInstance.AbstractExpression;
import com.eglobalmark.genericPublisher.publish.PublisherVisitor;

public class AbstractInstructionSimpleExpression extends AbstractInstruction {

	private AbstractExpression vExpr;
	
	public AbstractInstructionSimpleExpression()
	{
		vExpr = null;
	}
	
	@Override
	public void accept(PublisherVisitor v) {
		// TODO Auto-generated method stub
		v.visit(this);
	}

	public void SetExpression(AbstractExpression vExpr) {
		this.vExpr = vExpr;
	}
	
	public AbstractExpression GetExpression() {
		return vExpr;
	}
}
