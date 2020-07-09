/**
 * This code for OSC pattern matching is adapted and modified from JavaOSC and specifically from
 * com.illposed.osc.messageselector.OSCPatternAddressMessageSelector.java
 * <p>
 * It simply works beautifully. In the following the required copyright notice is included from
 * https://github.com/hoijui/JavaOSC/blob/master/LICENSE.md
 * <p>
 * Copyright (c) 2002-2014, Chandrasekhar Ramakrishnan / Illposed Software All rights reserved.
 * <p>
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 * <p>
 * Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer.
 * <p>
 * Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials
 * provided with the distribution.
 * <p>
 * Neither the name of the {organization} nor the names of its contributors may be used to endorse
 * or promote products derived from this software without specific prior written permission.
 * <p>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
 * AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER
 * IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package oscP5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OscPatternMatcher {

    private final List<String> patternParts;
    private final String pattern;

    public OscPatternMatcher(String thePattern) {
        pattern = thePattern;
        patternParts = splitIntoParts(pattern);

    }

    public boolean matches(final OscMessage theMessage) {
        final List<String> messageAddressParts = splitIntoParts(theMessage.getAddress());
        return matches(patternParts, 0, messageAddressParts, 0);
    }

    /**
     * Splits an OSC message address or address selector pattern into parts that are convenient
     * during the matching process.
     *
     * @param addressOrPattern to be split into parts, e.g.: "/hello/", "/hello//world//"
     * @return the given address or pattern split into parts: {"hello"}, {"hello, "", "world", ""}
     */
    private static List<String> splitIntoParts(final String addressOrPattern) {

        final List<String> parts
                = new ArrayList<>(Arrays.asList(addressOrPattern.split("/", -1)));
        if (addressOrPattern.startsWith("/")) {
            // as "/hello" gets split into {"", "hello"}, we remove the first empty entry,
            // so we end up with {"hello"}
            parts.remove(0);
        }
        if (addressOrPattern.endsWith("/")) {
            // as "hello/" gets split into {"hello", ""}, we also remove the last empty entry,
            // so we end up with {"hello"}
            parts.remove(parts.size() - 1);
        }
        return Collections.unmodifiableList(parts);
    }

    /**
     * Tries to match an OSC <i>Address Pattern</i> to a selector,
     * both already divided into their parts.
     *
     * @param patternParts        all the parts of the pattern
     * @param patternPartIndex    index/pointer to the current part of the pattern we are looking at
     * @param messageAddressParts all the parts of the address
     * @param addressPartIndex    index/pointer to the current part of the address we are looking at
     * @return true if the address matches, false otherwise
     */
    private static boolean matches(
            final List<String> patternParts,
            final int patternPartIndex,
            final List<String> messageAddressParts,
            final int addressPartIndex) {
        int ppi = patternPartIndex;
        int api = addressPartIndex;
        while (ppi < patternParts.size()) {
            // There might be some path-traversal wildcards (PTW) "//" in the pattern.
            // "//" in the pattern translates to an empty String ("") in the pattern parts.
            // We skip all consecutive "//"s at the current pattern position.
            boolean pathTraverser = false;
            while ((ppi < patternParts.size()) && patternParts.get(ppi).isEmpty()) {
                ppi++;
                pathTraverser = true;
            }
            // ppi is now at the end, or at the first non-PTW part
            if (pathTraverser) {
                if (ppi == patternParts.size()) {
                    // trailing PTW matches the whole rest of the address
                    return true;
                }
                while (api < messageAddressParts.size()) {
                    if (matches(messageAddressParts.get(api), patternParts.get(ppi))
                            && matches(patternParts, ppi + 1, messageAddressParts, api + 1)) {
                        return true;
                    }
                    api++;
                }
                // end of address parts reached, but there are still non-PTW pattern parts
                // left
                return false;
            } else {
                if ((ppi == patternParts.size()) != (api == messageAddressParts.size())) {
                    // end of pattern, no trailing PTW, but there are still address parts left
                    // OR
                    // end of address, but there are still non-PTW pattern parts left
                    return false;
                }
                if (!matches(messageAddressParts.get(api), patternParts.get(ppi))) {
                    return false;
                }
                api++;
            }
            ppi++;
        }

        return (api == messageAddressParts.size());
    }

    // Public API

    /**
     * Tries to match an OSC <i>Address Pattern</i> part to a part of
     * a selector.
     * This code was copied and adapted from LibLo,
     * and is licensed under the Public Domain.
     * For more details see:
     * {@link OSCPatternAddressMessageSelector the class comment}.
     *
     * @param str address part
     * @param p   pattern part
     * @return true if the address part matches, false otherwise
     */
    @SuppressWarnings("WeakerAccess")
    public static boolean matches(final String str, final String p) {

        boolean match;
        char c;

        int si = 0;
        int pi = 0;
        while (pi < p.length()) {
            if ((si == str.length()) && (p.charAt(pi) != '*')) {
                return false;
            }

            c = p.charAt(pi++);
            boolean negate;
            switch (c) {
                case '*':
                    while ((pi < p.length()) && (p.charAt(pi) == '*') && (p.charAt(pi) != '/')) {
                        pi++;
                    }

                    if (pi == p.length()) {
                        return true;
                    }

//					if (p.charAt(pi) != '?' && p.charAt(pi) != '[' && p.charAt(pi) != '\\')
                    if ((p.charAt(pi) != '?') && (p.charAt(pi) != '[') && (p.charAt(pi) != '{')) {
                        while ((si < str.length()) && (p.charAt(pi) != str.charAt(si))) {
                            si++;
                        }
                    }

                    while (si < str.length()) {
                        if (matches(str.substring(si), p.substring(pi))) {
                            return true;
                        }
                        si++;
                    }
                    return false;

                case '?':
                    if (si < str.length()) {
                        break;
                    }
                    return false;

                /*
                 * set specification is inclusive, that is [a-z] is a, z and
                 * everything in between. this means [z-a] may be interpreted
                 * as a set that contains z, a and nothing in between.
                 */
                case '[':
                    if (p.charAt(pi) == '!') {
                        negate = true;
                        pi++;
                    } else {
                        negate = false;
                    }

                    match = false;

                    while (!match && (pi < p.length())) {
                        c = p.charAt(pi++);
                        if (pi == p.length()) {
                            return false;
                        }
                        if (p.charAt(pi) == '-') {
                            // c-c
                            pi++;
                            if (pi == p.length()) {
                                return false;
                            }
                            if (p.charAt(pi) != ']') {
                                if ((str.charAt(si) == c) || (str.charAt(si) == p.charAt(pi))
                                        || ((str.charAt(si) > c) && (str.charAt(si) < p.charAt(pi)))) {
                                    match = true;
                                }
                            } else {
                                // c-]
                                if (str.charAt(si) >= c) {
                                    match = true;
                                }
                                break;
                            }
                        } else {
                            // cc or c]
                            if (c == str.charAt(si)) {
                                match = true;
                            }
                            if (p.charAt(pi) != ']') {
                                if (p.charAt(pi) == str.charAt(si)) {
                                    match = true;
                                }
                            } else {
                                break;
                            }
                        }
                    }

                    if (negate == match) {
                        return false;
                    }
                    // if there is a match, skip past the cSet and continue on
                    while ((pi < p.length()) && (p.charAt(pi) != ']')) {
                        pi++;
                    }
                    if (pi++ == p.length()) {
                        // oops!
                        return false;
                    }
                    break;

                // {aString,bString,cString}
                case '{':
                    // p.charAt(pi) is now first character in the {brace list}

                    // to back-track
                    final int place = si;
                    // to forward-track
                    int remainder = pi;

                    // find the end of the brace list
                    while ((remainder < p.length()) && (p.charAt(remainder) != '}')) {
                        remainder++;
                    }
                    if (remainder == p.length()) {
                        // oops!
                        return false;
                    }
                    remainder++;

                    c = p.charAt(pi++);
                    while (pi <= p.length()) {
                        if (c == ',') {
                            if (matches(str.substring(si), p.substring(remainder))) {
                                return true;
                            } else {
                                // back-track on test string
                                si = place;
                                // continue testing,
                                // skip comma
                                if (pi++ == p.length()) {
                                    // oops!
                                    return false;
                                }
                            }
                        } else if (c == '}') {
                            // continue normal pattern matching
                            if ((pi == p.length()) && (si == str.length())) {
                                return true;
                            }
                            // "si" is incremented again at the end of the loop
                            si--;
                            break;
                        } else if (c == str.charAt(si)) {
                            si++;
                            if ((si == str.length()) && (remainder < p.length())) {
                                return false;
                            }
                        } else {
                            // skip to next comma
                            si = place;
                            while ((pi < p.length()) && (p.charAt(pi) != ',')
                                    && (p.charAt(pi) != '}')) {
                                pi++;
                            }
                            if (pi < p.length()) {
                                if (p.charAt(pi) == ',') {
                                    pi++;
                                } else if (p.charAt(pi) == '}') {
                                    return false;
                                }
                            }
                        }
                        c = p.charAt(pi++);
                    }
                    break;

				/*
				 * Not part of OSC pattern matching
					case '\\':
						if (p.charAt(pi)) {
							c = p.charAt(pi)++;
						}
				 */
                default:
                    if (c != str.charAt(si)) {
                        return false;
                    }
                    break;
            }
            si++;
        }

        return (si == str.length());
    }

    public String getPattern() {
        return pattern;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!OscPatternMatcher.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final OscPatternMatcher other = (OscPatternMatcher) obj;
        return (this.getPattern().equals(other.getPattern()));
    }

    @Override
    public int hashCode() {
        return this.pattern.hashCode();
    }

        public String toString() {
        return "{class: OscPatternMatcher" +
                ", pattern:" + pattern +
                "}";
    }
}
