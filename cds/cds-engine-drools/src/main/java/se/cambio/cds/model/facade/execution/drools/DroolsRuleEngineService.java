package se.cambio.cds.model.facade.execution.drools;

import se.cambio.cds.controller.execution.DroolsExecutionManager;
import org.slf4j.LoggerFactory;
import se.cambio.cds.controller.guide.GuideUtil;
import se.cambio.cds.gdl.converters.drools.GDLDroolsConverter;
import se.cambio.cds.gdl.model.Guide;
import se.cambio.cds.model.facade.execution.delegate.RuleEngineService;
import se.cambio.cds.model.facade.execution.vo.RuleExecutionResult;
import se.cambio.cds.model.facade.execution.vo.RuleReference;
import se.cambio.cds.model.instance.ArchetypeReference;
import se.cambio.cds.model.instance.ElementInstance;
import se.cambio.cds.util.ExecutionLogger;
import se.cambio.cm.model.guide.dto.GuideDTO;

import java.io.UnsupportedEncodingException;
import java.util.*;

import static java.lang.String.format;

public class DroolsRuleEngineService implements RuleEngineService {

    private DroolsExecutionManager droolsExecutionManager;

    public DroolsRuleEngineService(DroolsExecutionManager droolsExecutionManager) {
        this.droolsExecutionManager = droolsExecutionManager;
    }

    public RuleExecutionResult execute(
            String ehrId,
            List<GuideDTO> guides,
            Collection<ArchetypeReference> archetypeReferences,
            Calendar date) {
        final HashSet<Object> workingMemoryObjects = new HashSet<>();
        for (ArchetypeReference archetypeReference : archetypeReferences) {
            workingMemoryObjects.addAll(archetypeReference.getElementInstancesMap().values());
            workingMemoryObjects.add(archetypeReference);
        }

        final ExecutionLogger executionLogger = new ExecutionLogger();
        if (!guides.isEmpty()) {
            LoggerFactory.getLogger(DroolsRuleEngineService.class).debug("Executing " + guides.size() + " guidelines using " + workingMemoryObjects.size() + " objects.");
            droolsExecutionManager.executeGuides(
                    guides, date, workingMemoryObjects, executionLogger);
        }
        final Set<ArchetypeReference> modifiedArhetypeReferences = new HashSet<>();
        //Search for modified elements
        for (ElementInstance elementInstance : executionLogger.getElementInstancesSet()) {
            modifiedArhetypeReferences.add(elementInstance.getArchetypeReference());
        }
        final List<RuleReference> ruleReferences =
                GuideUtil.getRuleReferences(executionLogger.getFiredRules());

        if (date == null) {
            date = Calendar.getInstance();
        }
        RuleExecutionResult ruleExecutionResult = new RuleExecutionResult(ehrId, date.getTime(), modifiedArhetypeReferences, executionLogger.getLog(), ruleReferences);
        ruleExecutionResult.setTimedOut(executionLogger.executionTimedOut());
        return ruleExecutionResult;
    }

    @Override
    public void cancelExecution() {
        droolsExecutionManager.cancelCurrentExecution();
    }

    @Override
    public void clearCache() {
        droolsExecutionManager.clearCache();
    }

    @Override
    public void setUseCache(boolean useCache) {
        droolsExecutionManager.setUseCache(useCache);
    }

    @Override
    public byte[] compile(Guide guide) {
        String droolsGuide = new GDLDroolsConverter(guide, droolsExecutionManager.getArchetypeManager()).convertToDrools();
        try {
            return droolsGuide.getBytes("UTF8");
        } catch (UnsupportedEncodingException exception) {
            throw new RuntimeException(format("Error compiling guide '%s'", guide.getId()), exception);
        }
    }

}
/*
 *  ***** BEGIN LICENSE BLOCK *****
 *  Version: MPL 2.0/GPL 2.0/LGPL 2.1
 *
 *  The contents of this file are subject to the Mozilla Public License Version
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