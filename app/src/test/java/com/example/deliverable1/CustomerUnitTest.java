package com.example.deliverable1;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import account.*;

public class CustomerUnitTest {
    private final DatabaseReference databaseReference = mock(DatabaseReference.class);
    private final FirebaseDatabase mockDatabase = mock(FirebaseDatabase.class);
    private final String username = "test";
    private final String pwd = "test";

    // Testing getting username
    @Test
    public void getUsername() {
        databaseReference.child("Users/Customer");
        Mockito.when(mockDatabase.getReference()).thenReturn(databaseReference);
        User user = new Customer(username, pwd, mockDatabase);
        assertEquals(user.getUserName(), username);
    }

    // Checking that first and last name are not initialised
    @Test
    public void infoEmpty() {
        databaseReference.child("Users/Customer");
        Mockito.when(mockDatabase.getReference()).thenReturn(databaseReference);
        User user = new Customer(username, pwd, mockDatabase);
        assertEquals(user.getFirstName(), null);
        assertEquals(user.getLastName(), null);
    }

    // Checking if toMap function works
    @Test
    public void toMap(){
        databaseReference.child("Users/Customer");
        Mockito.when(mockDatabase.getReference()).thenReturn(databaseReference);
        User user = new Customer(username, pwd, mockDatabase);

        Map<String, Object> m = new HashMap<>();
        m.put("passwd", pwd);
        m.put("firstName", username);
        m.put("lastName", username);
        m.put("userName", username);
        user.setFirstName(username);
        user.setLastName(username);
        assertEquals(user.toMap(), m);
    }

    // Testing if get and set of FirstName works
    @Test
    public void setFirstName(){
        databaseReference.child("Users/Customer");
        Mockito.when(mockDatabase.getReference()).thenReturn(databaseReference);
        User user = new Customer(username, pwd, mockDatabase);
        user.setFirstName(username);
        assertEquals(user.getFirstName(), username);
    }

    // Testing if get and set of LastName works
    @Test
    public void setLastName(){
        databaseReference.child("Users/Customer");
        Mockito.when(mockDatabase.getReference()).thenReturn(databaseReference);
        User user = new Customer(username, pwd, mockDatabase);
        user.setLastName(username);
        assertEquals(user.getLastName(), username);
    }

//    @Test
//    public void registerAccount() {
//        databaseReference.child("Users/Customer");
//        Mockito.when(mockDatabase.getReference()).thenReturn(databaseReference);
//        ListenerCallBack listener = mock(ListenerCallBack.class);
//        User user = new Customer(username, pwd, mockDatabase);
//        user.register("Test", "test", new ListenerCallBack() {
//            @Override
//            public void onSuccess() {
//                int i = 0;
//            }
//
//            @Override
//            public void onFail(String errInfo) {
//                int i = 0;
//            }
//        });
////        verify(listener);
//    }
}
