package com.samples.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CommonUtilTest {

    @Test
    public void testGetWeek(){
        assertEquals(29, CommonUtil.getWeek(LocalDateTime.of(2021,7,24,0,0)));
        assertEquals(29, CommonUtil.getWeek(LocalDateTime.of(2021,7,25,1,0)));
        assertEquals(30, CommonUtil.getWeek(LocalDateTime.of(2021,7,26,1,0)));
    }

}