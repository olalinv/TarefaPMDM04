package com.robottitto.tarefapmdm04.ui.core;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public class ActivityUtil extends AppCompatActivity {

    public static void go(Context context, Class<?> activityClass) {
        Intent intent = new Intent(context, activityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static <T extends Serializable> void go(Context context, Class<?> activityClass, String paramId, T entityObject) {
        Intent intent = new Intent(context, activityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (null != paramId) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(paramId, entityObject);
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    public static void showToast(Context context, String message) {
        Toast toast =
                Toast.makeText(context,
                        message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 120);
        toast.show();
    }

}
