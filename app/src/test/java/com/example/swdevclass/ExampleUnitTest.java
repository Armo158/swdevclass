package com.example.swdevclass;

import org.junit.Test;

import static org.junit.Assert.*;

import android.content.Context;

import com.example.swdevclass.fitness.FitnessCenter;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest{

    FitnessCenter fitnessCenter = new FitnessCenter();
    DBControl DBControl;
    private Context mockContext;

    @Test
    public void address_isCorrect() {//피트니스 클래스에 address를 설정하고 가져왔을 때 값이 동일한지를 비교
        String word1 = "진주시 경상대 근처 사거리 스타벅스 맞은편";
        fitnessCenter.setAddress(word1);
        String word2 = fitnessCenter.getAddress();
        assertEquals(word1, word2);
    }
    @Test
    public void Phonenumber_isCorrect(){//피트니스 클래스에 전화번호를 설정하고 가져왔을 때 값이 동일한지 비교
        String word1 = "010-5538-1589";
        fitnessCenter.setPhonenumber(word1);
        String word2 = fitnessCenter.getPhonenumber();
        assertEquals(word1, word2);
    }

    @Test
    public void fitnessCenter_isCorrect(){ //피트니스 클래스를 fitness arraylist control에 넣고 가져왔을 때 동일한 객체인지 비
        DBControl = new DBControl();
        DBControl.setFitnessCenter(fitnessCenter, 0);
        FitnessCenter fitnessCenter1 = DBControl.getFitnessCenter(0);
        assertEquals(fitnessCenter, fitnessCenter1);
    }
    /*@Test
    public void ImageSlider_isCorrect(){//이미지 슬라이더 어뎁터
        String[] images = new String[] {
                "https://cdn.pixabay.com/photo/2019/12/26/10/44/horse-4720178_1280.jpg",
                "https://cdn.pixabay.com/photo/2020/11/04/15/29/coffee-beans-5712780_1280.jpg",
                "https://cdn.pixabay.com/photo/2020/03/08/21/41/landscape-4913841_1280.jpg",
                "https://cdn.pixabay.com/photo/2020/09/02/18/03/girl-5539094_1280.jpg",
                "https://cdn.pixabay.com/photo/2014/03/03/16/15/mosque-279015_1280.jpg"
        };
        ImageSliderAdapter imageSliderAdapter = new ImageSliderAdapter(mockContext, images);
        int a = imageSliderAdapter.getItemCount(); //이미지 어뎁터에서 이미지를 받았을 때 이미지 수교 비교
        assertEquals(a, 5);//

    }/*
    @Test
    public void setFitnessArrayListControl_isCorrect(){ //Firebase관련 오류
        FirebaseApp.initializeApp(mockContext);
        fitnessArrayListControl = new FitnessArrayListControl();
        fitnessArrayListControl.setArrayListtoDB();
        Log.w("test", fitnessArrayListControl.getFitnessCenter(1).getAddress());
    }*/
}