package com.wyq.firehelper.encryption;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.wyq.firehelper.R;
import com.wyq.firehelper.utils.LogUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EncryptActivity extends Activity {

    private Spinner spinner;
    private TextView resultTv;

    private List<String> methods = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt_main);

        spinner = (Spinner) findViewById(R.id.activity_encrypt_spinner);
        resultTv = (TextView) findViewById(R.id.activity_encrypt_result_tv);

        initRes();

    }

    private void initRes() {
        methods = removeDuplication(getClassMethods(EncryptUtils.class), getClassMethods(Object.class));
        LogUtils.i("Test", methods.toString());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, methods.subList(1, methods.size()));
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private String invokeMethod(String methodName) {

        return null;
    }

    private List<String> getClassMethods(Class clazz) {
        List<String> list = new ArrayList<String>();
        Method[] declaredMethods = clazz.getMethods();
        for (Method method : declaredMethods) {
            String name = method == null ? null : method.getName();
            if (name != null) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                Class<?> returnType = method.getReturnType();
                if (returnType != null) {
                    name = returnType.getSimpleName() + "  " + name;
                }
                if (parameterTypes != null && parameterTypes.length > 0)
                    for (Class clazzType : parameterTypes) {
                        name += "  " + clazzType.getSimpleName();
                    }
                list.add(name);
            }
        }
        return list;
    }

    private List<String> removeDuplication(List<String> oriList, List<String> dupList) {
        for (String s : dupList) {
            if (oriList.contains(s))
                oriList.remove(s);
        }
        return oriList;
    }
}
