package com.app.ssumobile.ssumobile_android.providers;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import com.app.ssumobile.ssumobile_android.models.ContactModel;

/**
 * Created by WestFlow on 10/24/2015.
 */


public class IContactProviderTest {

    public ContactModel MockContactCreation() {
        ContactModel testModel = new ContactModel();
        Mockito.when(testModel.getLname()).thenReturn("Weston");
        Mockito.when(testModel.getFname()).thenReturn("Mitchell");
        Mockito.when(testModel.getTitle()).thenReturn("Student");
        Mockito.when(testModel.getPhone_num()).thenReturn("310-999-9999");
        Mockito.when(testModel.getEmail()).thenReturn("westonm127@gmail.com");
        return testModel;
    }
}
