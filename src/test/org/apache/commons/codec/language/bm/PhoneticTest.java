/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.codec.language.bm;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * Tests PhoneticEngine.
 * 
 * @author Apache Software Foundation
 * @since 2.0
 */
@RunWith(Parameterized.class)
public class PhoneticTest {

    @Parameterized.Parameters
    public static List<Object[]> data() {
        return Arrays
                .asList(new Object[] { "Renault", "rinolt|rino|rinDlt|rinalt|rinult|rinD|rina|rinu", NameType.GENERIC, RuleType.APPROX,
                        true },
                        new Object[] { "Renault", "rYnDlt|rYnalt|rYnult|rinDlt|rinalt|rinult", NameType.ASHKENAZI, RuleType.APPROX, true },
                        new Object[] { "Renault", "(rinDlt)", NameType.SEPHARDIC, RuleType.APPROX, true },
                        new Object[] { "SntJohn-Smith", "(sntjonsmit)", NameType.GENERIC, RuleType.EXACT, true },
                        new Object[] { "d'ortley", "ortlaj|ortlej|ortlaj|ortlej-dortlaj|dortlej|dortlaj|dortlej", NameType.GENERIC,
                                RuleType.EXACT, true },
                        new Object[] {
                                "van helsing",
                                "helSink|helsink|helzink|xelSink|xelsink|xelzink|HelSink|Helsink|Helzink-vanhelSink|vanhelsink|vanhelzink|vanjelSink|vanjelsink|vanjelzink|fanhelSink|fanhelsink|fanhelzink|fanjelSink|fanjelsink|fanjelzink|banhelSink|banhelsink|banhelzink|banjelSink|banjelsink|banjelzink",
                                NameType.GENERIC, RuleType.EXACT, false });
    }

    private final boolean concat;
    private final String name;
    private final NameType nameType;
    private final String phoneticExpected;
    private final RuleType ruleType;

    public PhoneticTest(String name, String phoneticExpected, NameType nameType, RuleType ruleType, boolean concat) {
        this.name = name;
        this.phoneticExpected = phoneticExpected;
        this.nameType = nameType;
        this.ruleType = ruleType;
        this.concat = concat;
    }

    @Test(timeout = 10000L)
    public void testPhonetic() {
        PhoneticEngine engine = new PhoneticEngine(this.nameType, this.ruleType, this.concat);

        String phoneticActual = engine.encode(this.name);

        assertEquals("phoneme incorrect", this.phoneticExpected, phoneticActual);
    }
}