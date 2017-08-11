package se.cambio.cm.model.archetype.vo;


public class CodedTextVO extends PathableVO {

    private static final long serialVersionUID = 20120412L;

    private String terminology = null;
    private String code = null;

    public CodedTextVO(String name, String description, String type,
                       String idArchetype, String idTemplate, String path, String terminology, String code) {
        super(name, description, type, idArchetype, idTemplate, path);
        this.terminology = terminology;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTerminology() {
        return terminology;
    }

    public void setTerminology(String terminology) {
        this.terminology = terminology;
    }

    @Override
    public CodedTextVO clone() {
        return new CodedTextVOBuilder()
                .setName(getName())
                .setDescription(getDescription())
                .setType(getType())
                .setIdArchetype(getIdArchetype())
                .setIdTemplate(getIdTemplate())
                .setPath(getPath())
                .setCode(getCode())
                .setTerminology(getTerminology())
                .createCodedTextVO();
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