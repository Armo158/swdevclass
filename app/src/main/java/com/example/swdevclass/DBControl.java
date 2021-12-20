package com.example.swdevclass;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.swdevclass.FragmentFile.Fragment_Map;
import com.example.swdevclass.fitness.FitnessCenter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class DBControl{
    private ArrayList<FitnessCenter> arrayList;
    private ArrayList<String> manager;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference loginRef;

    DBControl(){
        arrayList = new ArrayList<>();
    }
    public boolean setArrayListtoDB(){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("fitness"); //DB table

        final boolean[] a = new boolean[1];

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                arrayList.clear();// 기존배열 리스트가 존재하지 않게 초기화
                for(DataSnapshot snapshot: datasnapshot.getChildren()) {//반복문으로 데이터 list를 추출해냄
                    FitnessCenter fitnessCenter = snapshot.getValue(FitnessCenter.class);//만들어뒀던 fitness 객체에 데이터 넣
                    arrayList.add(fitnessCenter); //담은 데이터들을 배열리스트에 넣음
                }
               a[0] = true;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                a[0] = false;
            }
        });

        return a[0];
    }
    public void setArrayList(ArrayList<FitnessCenter> arrayList){
        this.arrayList = arrayList;
        return;
    }
    public ArrayList<FitnessCenter> getArrayList(){
        return arrayList;
    }
    //arraylist 값 설정
    public FitnessCenter getFitnessCenter(int i){
        return arrayList.get(i);
    }
    public void setFitnessCenter(FitnessCenter fitnessCenter, int i){
        arrayList.set(i, fitnessCenter);
        return;
    }
    //arraylist 값 DB로 보내기
    public void setDBFitnessValue(){
        myRef.setValue(arrayList);
        return;
    }

    //email 비교
    public void setLoginReftoDB(){
        manager = new ArrayList<>();
        loginRef = database.getReference("manager");

        loginRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for(DataSnapshot snapshot: datasnapshot.getChildren()){
                    String email = snapshot.getValue(String.class);
                    manager.add(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void setEmail(String email){
        manager.add(email);
        setEmailtoDB();
    }
    public String getEmail(int i){
        return manager.get(i);
    }
    public boolean isExistEmail(String email){
        return manager.contains(email);
    }
    public void setEmailtoDB(){
        loginRef.setValue(manager);
    }

}
