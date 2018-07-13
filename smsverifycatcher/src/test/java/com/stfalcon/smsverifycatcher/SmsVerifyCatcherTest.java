package com.stfalcon.smsverifycatcher;

import android.app.Activity;
import android.content.IntentFilter;
import android.content.pm.PackageManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.stfalcon.smsverifycatcher.SmsVerifyCatcher.PERMISSION_REQUEST_CODE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SmsVerifyCatcherTest {

    private SmsVerifyCatcher catcher;

    @Mock
    public Activity activity;

    @Mock
    public OnSmsCatchListener<String> listener;


    @Before
    public void setUp() {
        catcher = new SmsVerifyCatcher(activity, listener);
    }


    @After
    public void tearDown() {
        reset(activity, listener);
    }

    @Test
    public void shouldNotRegisterReceiverWhenRequestWasCancelled() {
        catcher.onRequestPermissionsResult(PERMISSION_REQUEST_CODE, new String[]{}, new int[]{});

        verify(activity, never()).registerReceiver(any(SmsReceiver.class), any(IntentFilter.class));
    }

    @Test
    public void shouldRegisterReceiverWhenPermissionsGranted() {
        catcher.onRequestPermissionsResult(
                PERMISSION_REQUEST_CODE,
                new String[]{},
                new int[]{PackageManager.PERMISSION_GRANTED, PackageManager.PERMISSION_GRANTED}
        );

        verify(activity).registerReceiver(any(SmsReceiver.class), any(IntentFilter.class));
    }
}
