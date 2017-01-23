/*-
 * Copyright (c) 2008-2010, Oleg Estekhin
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  * Neither the names of the copyright holders nor the names of their
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */

package oe.assertions;

import java.util.Collections;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class MessageTest {

    private Message message;


    @BeforeMethod
    public void resetMessage() {
        message = new Message();
    }


    @Test( dataProvider = "formattedValues" )
    public void producesExpectedMessage( Object value, String expectedMessage ) {
        message.formatValue( value );
        assertEquals( message.toString(), expectedMessage );
    }


    @DataProvider
    public Object[][] formattedValues() {
        Object[] nested = new Object[ 2 ];
        nested[ 0 ] = nested;
        nested[ 1 ] = new Object[] { nested };

        return new Object[][] {
                { null, "<null>" },

                { ( byte ) 0x01, "<1> (0x01)" },
                { new byte[ 0 ], "[]" },
                { new byte[] { 1, 2, 3 }, "[1, 2, 3]" },

                { ( short ) 0x0102, "<258> (0x0102)" },
                { new short[ 0 ], "[]" },
                { new short[] { 1, 2, 3 }, "[1, 2, 3]" },

                { 0x01020304, "<16909060> (0x01020304)" },
                { new int[ 0 ], "[]" },
                { new int[] { 1, 2, 3 }, "[1, 2, 3]" },

                { 0x0102030405060708L, "<72623859790382856L> (0x0102030405060708L)" },
                { new long[ 0 ], "[]" },
                { new long[] { 1, 2, 3 }, "[1L, 2L, 3L]" },

                { 0.0f, "<0.0f> (0x0.0p0f)" },
                { new float[ 0 ], "[]" },
                { new float[] { 1.0f, 2.0f, 3.0f }, "[1.0f, 2.0f, 3.0f]" },

                { 0.0, "<0.0> (0x0.0p0)" },
                { new double[ 0 ], "[]" },
                { new double[] { 1.0, 2.0, 3.0 }, "[1.0, 2.0, 3.0]" },

                { true, "<true>" },
                { new boolean[ 0 ], "[]" },
                { new boolean[] { true, false }, "[true, false]" },

                { 'a', "'a' (u0061)" },
                { new char[ 0 ], "[]" },
                { new char[] { 'a', 'b', 'c' }, "['a', 'b', 'c']" },

                { "a", "\"a\"" },
                {
                        "double quote \" single quote \' tab \t carriage return \r linefeed \n backslash \\",
                        "\"double quote \\\" single quote ' tab \\t carriage return \\r linefeed \\n backslash \\\""
                },
                { new String[ 0 ], "[]" },
                { new String[] { "a", "b", "c" }, "[\"a\", \"b\", \"c\"]" },

                { new Object[ 0 ], "[]" },
                {
                        new Object[] {
                                ( byte ) 0, ( short ) 1, 2, 3L, 4.0f, 5.0,
                                'a', "b",
                                true,
                                new int[] { 1, 2, 3 },
                        },
                        "[0, 1, 2, 3L, 4.0f, 5.0, 'a', \"b\", true, [1, 2, 3]]"
                },
                { nested, "[[..], [[../..]]]" },

                { Collections.singleton( "set" ), "[set]" },
                { Collections.singletonList( "list" ), "[list]" },
                { Collections.singletonMap( "key", "value" ), "{key=value}" },
        };
    }

}
