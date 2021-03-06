/*
 * Copyright (c) 2014, 2021, Oracle and/or its affiliates. All rights reserved.
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
 * @test
 * @bug 8029995
 * @summary accept yes/no for boolean krb5.conf settings
 * @modules java.security.jgss/sun.security.krb5
 *          java.security.jgss/sun.security.krb5.internal.crypto
 * @compile -XDignore.symbol.file YesNo.java
 * @run main/othervm YesNo
 */
import sun.security.krb5.Config;
import sun.security.krb5.internal.crypto.EType;

import java.util.Arrays;

public class YesNo {
    static Config config = null;
    public static void main(String[] args) throws Exception {
        System.setProperty("java.security.krb5.conf",
                System.getProperty("test.src", ".") +"/yesno.conf");
        config = Config.getInstance();
        check("a", Boolean.TRUE);
        check("b", Boolean.FALSE);
        check("c", Boolean.TRUE);
        check("d", Boolean.FALSE);
        check("e", null);
        check("f", null);

        if (!Arrays.stream(EType.getDefaults("default_tkt_enctypes"))
                .anyMatch(n -> n == 23)) {
            throw new Exception();
        }
    }

    static void check(String k, Boolean expected) throws Exception {
        Boolean result = config.getBooleanObject("libdefaults", k);
        if (expected != result) {
            throw new Exception("value for " + k + " is " + result);
        }
    }
}
