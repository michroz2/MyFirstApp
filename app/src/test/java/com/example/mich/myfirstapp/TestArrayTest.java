package com.example.mich.myfirstapp;

import org.junit.Assert;
import org.junit.Test;

import java.security.InvalidParameterException;


public class TestArrayTest {

    @Test
    public void sortAcs() {
        int[] arr = new int[]{2, 3, 1};
        TestArray.michSort(arr, true);
        Assert.assertArrayEquals(arr, new int[]{1, 2, 3});

    }

    @Test
    public void checkEmptyArray() {
        int[] arr = new int[]{};
        TestArray.michSort(arr, true);
        Assert.assertArrayEquals(arr, new int[]{});
    }

    @Test(expected = InvalidParameterException.class)
    public void checkNullArray() {
         TestArray.michSort(null, true); // fixme: please
    }

    @Test
    public void sortDsc() {
        //todo: implements please
    }
}