package com.eglobalmark.genericPublisher.adapter.certifyIt;

import com.eglobalmark.genericPublisher.adapter.ToolAdapter;
import com.eglobalmark.genericPublisher.optionsManager.OptionManager;

public class AdapterCertifyIt extends ToolAdapter {

	public AdapterCertifyIt() {
		// Declare options here
		OptionManager.getInstance().CheckInterface(this);
	}
	
	@Override
	public void CreateAbstractTestStructure() {
		// TODO Auto-generated method stub
	}

}
