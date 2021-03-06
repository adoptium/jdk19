/*
 * Copyright (c) 2007, 2022, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
/*
 *
 */

package com.foo;

import java.text.*;
import java.text.spi.*;
import java.util.*;

import com.foobar.Utils;

public class CollatorProviderImpl extends CollatorProvider {

    static Locale[] avail = {
        Locale.JAPAN,
        Locale.of("ja", "JP", "osaka"),
        Locale.of("ja", "JP", "kyoto"),
        Locale.of("xx", "YY", "ZZZZ")};

    static String[] dialect = {
        "\u3067\u3059\u3002",
        "\u3084\u3002",
        "\u3069\u3059\u3002",
        "-xx-YY-ZZZZ"
    };

    public Locale[] getAvailableLocales() {
        return avail;
    }

    public Collator getInstance(Locale locale) {
        for (int i = 0; i < avail.length; i ++) {
            if (Utils.supportsLocale(avail[i], locale)) {
                RuleBasedCollator ja = (RuleBasedCollator)Collator.getInstance(Locale.JAPANESE);
                try {
                    return new RuleBasedCollator(ja.getRules()+"& Z < "+dialect[i]);
                } catch (ParseException pe) {
System.err.println(pe+ja.getRules()+"& Z < "+dialect[i]);
                    return ja;
                }
            }
        }
        throw new IllegalArgumentException("locale is not supported: "+locale);
    }
}
