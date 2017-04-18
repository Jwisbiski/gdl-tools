package se.cambio.openehr.controller.session.data;

import org.openehr.am.archetype.ontology.ArchetypeTerm;
import se.cambio.cm.model.archetype.vo.OrdinalVO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Ordinals {
    private final ArchetypeManager archetypeManager;
    private Map<String, Map<String, OrdinalVO>> ordinalByParentId = null;
    private Map<String, Map<String, Map<String, OrdinalVO>>> templateOrdinalsByTemplateIdAndId = null;

    public Ordinals(ArchetypeManager archetypeManager) {
        this.archetypeManager = archetypeManager;
        init();
    }

    public void init() {
        ordinalByParentId = new HashMap<>();
        templateOrdinalsByTemplateIdAndId = new HashMap<>();
    }

    public void loadOrdinals(Collection<OrdinalVO> ordinalVOs) {
        for (OrdinalVO ordinalVO : ordinalVOs) {
            registerOrdinal(ordinalVO);
        }
    }

    public void registerOrdinal(OrdinalVO ordinalVO) {
        if (ordinalVO.getIdTemplate() == null) {
            getOrdinalMap(ordinalVO.getId()).put(ordinalVO.getCode(), ordinalVO);
        } else {
            getOrdinalTemplateMap(ordinalVO.getIdTemplate(), ordinalVO.getId()).put(ordinalVO.getCode(), ordinalVO);
        }
    }

    public OrdinalVO getOrdinalVO(String idTemplate, String idParentArchetypeNode, String ordinalKey) {
        archetypeManager.loadArchetypesAndTemplatesIfNeeded(idTemplate, idParentArchetypeNode);
        if (idTemplate == null) {
            return getOrdinalMap(idParentArchetypeNode).get(ordinalKey);
        } else {
            return getOrdinalTemplateMap(idTemplate, idParentArchetypeNode).get(ordinalKey);
        }
    }

    public Collection<OrdinalVO> getOrdinalVOs(String idTemplate, String idParentArchetypeNode) {
        archetypeManager.loadArchetypesAndTemplatesIfNeeded(idTemplate, idParentArchetypeNode);
        if (idTemplate == null) {
            return new ArrayList<>(getOrdinalMap(idParentArchetypeNode).values());
        } else {
            return new ArrayList<>(getOrdinalTemplateMap(idTemplate, idParentArchetypeNode).values());
        }
    }

    private Map<String, Map<String, OrdinalVO>> getOrdinalTemplateMap(String idTemplate) {
        return templateOrdinalsByTemplateIdAndId.computeIfAbsent(idTemplate, k -> new HashMap<>());
    }

    public Map<String, OrdinalVO> getOrdinalTemplateMap(String idTemplate, String idParentArchetypeNode) {
        return getOrdinalTemplateMap(idTemplate).computeIfAbsent(idParentArchetypeNode, k -> new HashMap<>());
    }

    private Map<String, OrdinalVO> getOrdinalMap(String idParentArchetypeNode) {
        return ordinalByParentId.computeIfAbsent(idParentArchetypeNode, k -> new HashMap<>());
    }

    public String getText(OrdinalVO ordinalVO, String lang) {
        return getText(ordinalVO.getIdTemplate(), ordinalVO.getId(), ordinalVO.getCode(), lang);
    }

    public String getText(String idTemplate, String idElement, String ordinalKey, String lang) {
        OrdinalVO ordinalVO = getOrdinalVO(idTemplate, idElement, ordinalKey);
        if (ordinalVO != null) {
            String archetypeId = idElement.substring(0, idElement.indexOf("/"));
            ArchetypeTerm archetypeTem = archetypeManager.getArchetypeTerm(archetypeId, idTemplate, idElement, ordinalVO.getCode(), lang);
            if (archetypeTem != null) {
                return archetypeTem.getText();
            } else {
                return ordinalVO.getName();
            }
        } else {
            return "*UNKNOWN*";
        }
    }

    public String getDescription(OrdinalVO ordinalVO, String lang) {
        return getDescription(ordinalVO.getIdTemplate(), ordinalVO.getId(), ordinalVO.getCode(), lang);
    }

    public String getDescription(String idTemplate, String idElement, String ordinalKey, String lang) {
        OrdinalVO ordinalVO = getOrdinalVO(idTemplate, idElement, ordinalKey);
        if (ordinalVO != null) {
            String archetypeId = idElement.substring(0, idElement.indexOf("/"));
            ArchetypeTerm archetypeTem = archetypeManager.getArchetypeTerm(archetypeId, idTemplate, idElement, ordinalVO.getCode(), lang);
            if (archetypeTem != null) {
                return archetypeTem.getDescription();
            } else {
                return ordinalVO.getDescription();
            }
        } else {
            return "*UNKNOWN*";
        }
    }

    private ArchetypeTerms getArchetypeTerms() {
        return this.archetypeManager.getArchetypeTerms();
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