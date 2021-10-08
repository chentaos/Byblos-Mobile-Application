package com.example.deliverable1;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import account.*;

public class RegisterUnitTest {
    @Test
    public void registerCustomerAccount() {
        User user;
        user = new Customer("test", "test");
        user.register("Test", "test", null);
    }

}
