package se.cambio.cm.controller.terminology;

import org.openehr.rm.datatypes.text.CodePhrase;
import org.openehr.rm.datatypes.text.DvCodedText;
import se.cambio.cm.model.facade.terminology.vo.TerminologyNodeVO;
import se.cambio.openehr.util.exceptions.InvalidCodeException;
import se.cambio.openehr.util.exceptions.UnknownPropertyException;
import se.cambio.openehr.util.exceptions.UnsupportedLanguageException;
import se.cambio.openehr.util.exceptions.UnsupportedTerminologyException;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * This class defines a minimal interface of a terminology service 
 * that is required to support runtime and authoring of CDSS. 
 * 
 * @author rong.chen
 *
 */
public interface TerminologyService {
	/**
	 * Checks if codePhrase a is subclass of b with regards to
	 * subsumption relationship
	 * 
	 * @param a
	 * @param b
	 * @return true if a is subclass of b
	 */
	boolean isSubclassOf(CodePhrase a, CodePhrase b) 
			throws UnsupportedTerminologyException, InvalidCodeException;
	
	/**
	 * Checks if the given code is subclass of any member of 
	 * specified set of codes
	 * 
	 * @param code
	 * @param codes
	 * @return true if a is subclass of b
	 */
	boolean isSubclassOf(CodePhrase code, Set<CodePhrase> codes) 
			throws UnsupportedTerminologyException, InvalidCodeException;
	
	/**
	 * Retrieves all subclasses of given concept with specified language
	 * 
	 * @param concept
	 * @return returns a tree representation of subclasses
	 */
	TerminologyNodeVO retrieveAllSubclasses(CodePhrase concept, CodePhrase language)
			throws UnsupportedTerminologyException, UnsupportedLanguageException, InvalidCodeException;	
	
	List<TerminologyNodeVO> retrieveAll(String terminologyId, CodePhrase language)
		throws UnsupportedTerminologyException, UnsupportedLanguageException;	
	
	/**
	 * Retrieves the term of a given concept in given language
	 *
	 * @param concept
	 * @param language
	 * @return the term
	 * @throws UnsupportedTerminologyException
	 * @throws UnsupportedLanguageException
	 */
	String retrieveTerm(CodePhrase concept, CodePhrase language)
			throws UnsupportedTerminologyException, UnsupportedLanguageException;

	boolean isTerminologySupported(String terminologyId);
	boolean isValidCodePhrase(CodePhrase codePhrase);
	
	Collection<String> getSupportedTerminologies();
}
/*
 *  ***** BEGIN LICENSE BLOCK *****
 *  Version: MPL 2.0/GPL 2.0/LGPL 2.1
 *
 *  The contents of this file are subject to the Mozilla License Version
 *  2.0 (the 'License'); you may not use this file except in compliance with
 *  the License. You may obtain a copy of the License at
 *  http://www.mozilla.org/MPL/
 *
 *  Software distributed under the License is distributed on an 'AS IS' basis,
 *  WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 *  for the specific language governing rights and limitations under the
 *  License.
 *
 *
 *  The Initial Developers of the Original Code are Iago Corbal and Rong Chen.
 *  Portions created by the Initial Developer are Copyright (C) 2012-2013
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 * Software distributed under the License is distributed on an 'AS IS' basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 *  ***** END LICENSE BLOCK *****
 */