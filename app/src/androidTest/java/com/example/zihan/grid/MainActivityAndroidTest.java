package com.example.zihan.grid;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainActivityAndroidTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private MainActivity mainActivity = null;
    private int [] idList;
    @Before
    public void setUp() throws Exception {
        mainActivity = mainActivityTestRule.getActivity();

        /*  a list for all id in the xml file*/
        idList = new int[]{
                R.id.mapPreview,
                R.id.btnWidthMinus,
                R.id.mapWidth,
                R.id.btnWidthPlus,
                R.id.btnStartGame,
                R.id.btnTutorial
        };
    }

    /*
    *   Test if all id in the xml file are found.
    * */
    @Test
    public void launchTest(){
        for(int id:idList){
            assertNotNull(mainActivity.findViewById(id));
        }
    }

    @After
    public void tearDown() throws Exception {
        mainActivity = null;
    }
}