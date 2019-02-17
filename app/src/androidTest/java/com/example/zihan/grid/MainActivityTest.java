package com.example.zihan.grid;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private MainActivity mainActivity = null;
    private int [] idListMain = null;
    private int [] idListGame = null;
    private int numOfStressTest = 10;
    private int maxTimesOfClicks = 8;

    @Before
    public void setUp() throws Exception {
        mainActivity = mainActivityTestRule.getActivity();

        /*  a list for all id in the activity_main.xml */
        idListMain = new int[]{
                R.id.mapPreviewParent,
                R.id.puzzlePreview,
                R.id.btnDifficultyDown,
                R.id.tvDifficulty,
                R.id.btnDifficultyUp,
                R.id.btnStartGame,
                R.id.btnTutorial
        };

        idListGame = new int[]{
                R.id.gameView,
                R.id.btnReturnMain,
                R.id.btnHint,
                R.id.btnRestart
        };
    }

    /* Test if all views exist or not */
    @Test
    public void testViewExistInMain(){
        /* Test if all views are present using assertNotNull */
        for(int id:idListMain){
            assertNotNull(mainActivity.findViewById(id));
        }
    }

    /**
     * Test the initial difficulty value, should be 6
     */
    @Test
    public void testLevelChange(){
        assertEquals(6, mainActivity.getDifficulty());
        testLevelChangeDown();
        testLevelChangeUp();
        testLevelChangeStress();
    }

    /**
     * Test the difficulty value after click levelDown button, should minus 1
     */
    public void testLevelChangeDown() {
        // Click the button
        Espresso.onView(withId(R.id.btnDifficultyDown)).perform(click());

        // Check if value matches
        Espresso.onView(withId(R.id.tvDifficulty)).check(matches(withText(mainActivity.getDifficulty()+"")));
    }

    /**
     * Test the difficulty value after click levelUp button, should plus 1
     */
    public void testLevelChangeUp() {
        // Click the button
        Espresso.onView(withId(R.id.btnDifficultyUp)).perform(click());

        // Check if value matches
        Espresso.onView(withId(R.id.tvDifficulty)).check(matches(withText(mainActivity.getDifficulty()+"")));
    }

    /**
     * Stress test for difficulty value.
     * Given number of test times
     * Generate random clicks for levelUp and levelDown button
     * Need attention that when difficulty == 5 || 12, the corresponding button will be hidden
     * So add constrains || need improvement
     */
    public void testLevelChangeStress() {
        // given a number for stress test
        for(int i=0; i<numOfStressTest; i++){
            int clicks = (int) (Math.random()*maxTimesOfClicks);
            for(int j=0; j<clicks; j++){
                int clickUpOrDown = (int) Math.round(Math.random());
                if(clickUpOrDown==0 && mainActivity.getDifficulty()<12){
                    Espresso.onView(withId(R.id.btnDifficultyUp)).perform(click());
                }else if(clickUpOrDown==1 && mainActivity.getDifficulty()>5){
                    Espresso.onView(withId(R.id.btnDifficultyDown)).perform(click());
                }
            }
            Espresso.onView(withId(R.id.tvDifficulty)).check(matches(withText(mainActivity.getDifficulty()+"")));
//            Log.d("times", i+"");
        }
    }

    /**
     * Test if content changes after click btnStart
     */
    @Test
    public void testClickBtnStart(){
        Espresso.onView(withId(R.id.btnStartGame)).perform(click());
        testViewExistInGame();
    }

    /**
     * Test if view exist in activity_game.xml
     */
    private void testViewExistInGame() {
        for(int id:idListGame){
            assertNotNull(mainActivity.findViewById(id));
        }
    }

    @After
    public void tearDown() throws Exception {
        mainActivity = null;
        idListMain = null;
    }
}