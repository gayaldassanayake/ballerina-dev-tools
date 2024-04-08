/*
 *  Copyright (c) 2023, WSO2 LLC. (http://www.wso2.com) All Rights Reserved.
 *
 *  WSO2 LLC. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package io.ballerina.sequencemodelgenerator.core.model;

import io.ballerina.tools.text.LineRange;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the body of an element {@link DElement} in the sequence diagram model.
 *
 * @since 2201.8.5
 */
public class DElementBody extends DNode {
    private final List<DNode> childElements;

    public DElementBody(String kind, boolean isHidden, LineRange location) {
        super(kind, isHidden, location);
        this.childElements = new ArrayList<>();
    }

    public void addChildDiagramElement(DNode dNode) {
        childElements.add(dNode);
    }

    public List<DNode> getChildElements() {
        return childElements;
    }
}