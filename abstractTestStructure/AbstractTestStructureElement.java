/**
 * 
 */
package com.eglobalmark.genericPublisher.abstractTestStructure;

import com.eglobalmark.genericPublisher.publish.PublisherVisitor;

/**
 * @author nspaseski
 *
 */
public interface AbstractTestStructureElement {

		void accept(PublisherVisitor v);
}
