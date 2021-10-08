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

import account.*;

public class RegisterUnitTest {
    private final DatabaseReference databaseReference = mock(DatabaseReference.class);
    private final FirebaseDatabase mockDatabase = mock(FirebaseDatabase.class);
    private final String username = "test";
    private final String pwd = "test";

    @Test
    public void getUsername() {
        User user = new Customer(username, pwd, mockDatabase);
        assertEquals(user.getUserName(), username);
    }

    @Test
    public void infoEmpty() {
        User user = new Customer(username, pwd, mockDatabase);
        assertEquals(user.getFirstName(), "");
        assertEquals(user.getLastName(), "");
    }

    @Test
    public void registerAccount() {
        databaseReference.child("Users/Customer");
        Mockito.when(mockDatabase.getReference()).thenReturn(databaseReference);
        ListenerCallBack listener = mock(ListenerCallBack.class);
        User user = new Customer(username, pwd, mockDatabase);
        user.register("Test", "test", listener);
//        verify(listener);
    }
}
