package com.thyberg.kata;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ChopTest {

    private static final List<Integer> MANY_VALUES = new ArrayList<>(
            Arrays.asList(1,3,5,7,9,10,11,13,15,19,30,40,56,76,89,100));

    private static final List<Integer> SINGLE_VALUE = new ArrayList<>(
            Collections.singletonList(40));

    @ParameterizedTest
    @MethodSource("localParameters")
    void bigAllTest(Chop chopObject) {
        assertEquals(-1, chopObject.chop(Collections.emptyList(), 3));
        assertEquals(-1, chopObject.chop(Collections.singletonList(1), 3));
        assertEquals(0,  chopObject.chop(Collections.singletonList(1), 1));
        assertEquals(0,  chopObject.chop(Arrays.asList(1, 3, 5), 1));
        assertEquals(1,  chopObject.chop(Arrays.asList(1, 3, 5), 3));
        assertEquals(2,  chopObject.chop(Arrays.asList(1, 3, 5), 5));
        assertEquals(-1, chopObject.chop(Arrays.asList(1, 3, 5), 0));
        assertEquals(-1, chopObject.chop(Arrays.asList(1, 3, 5), 2));
        assertEquals(-1, chopObject.chop(Arrays.asList(1, 3, 5), 4));
        assertEquals(-1, chopObject.chop(Arrays.asList(1, 3, 5), 6));
        assertEquals(0,  chopObject.chop(Arrays.asList(1, 3, 5, 7), 1));
        assertEquals(1,  chopObject.chop(Arrays.asList(1, 3, 5, 7), 3));
        assertEquals(2,  chopObject.chop(Arrays.asList(1, 3, 5, 7), 5));
        assertEquals(3,  chopObject.chop(Arrays.asList(1, 3, 5, 7), 7));
        assertEquals(-1, chopObject.chop(Arrays.asList(1, 3, 5, 7), 0));
        assertEquals(-1, chopObject.chop(Arrays.asList(1, 3, 5, 7), 2));
        assertEquals(-1, chopObject.chop(Arrays.asList(1, 3, 5, 7), 4));
        assertEquals(-1, chopObject.chop(Arrays.asList(1, 3, 5, 7), 6));
        assertEquals(-1, chopObject.chop(Arrays.asList(1, 3, 5, 7), 8));
    }

    public static Stream<Arguments> localParameters() {
        return Stream.of(
            Arguments.of(new IterChop()),
            Arguments.of(new RecurseChop()),
            Arguments.of(new TreeChop()),
            Arguments.of(new FunctionalChop())
        );
    }
}