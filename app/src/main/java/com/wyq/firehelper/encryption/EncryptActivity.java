package com.wyq.firehelper.encryption;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.wyq.firehelper.R;
import com.wyq.firehelper.utils.LogUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EncryptActivity extends Activity {

    private Spinner spinner;
    private TextView resultTv;
    private EditText inputEt;
    private Button button;

    private List<Method> methods = null;
    private List<String> methodsName = null;

    private int checkedPosition = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt_main);

        spinner = (Spinner) findViewById(R.id.activity_encrypt_spinner);
        resultTv = (TextView) findViewById(R.id.activity_encrypt_result_tv);
        inputEt = (EditText) findViewById(R.id.activity_encrypt_edittext);
        button = (Button) findViewById(R.id.activity_encrypt_invoke_bt);

        initRes();

    }

    private void initRes() {

        methods = removeDuplication(getClassMethods(EncryptUtils.class),getClassMethods(Object.class));
        //获取此类中所有方法，名称，排除object自带方法
        methodsName = getClassMethodsName(methods);
        LogUtils.i("Test", methods.get(0).getName());


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, methodsName.subList(1, methodsName.size()));
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                checkedPosition = position;
                LogUtils.e("Test","position:"+position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //加1的原因是因为获取的第一个方法为access$super,已过滤
                Method method = methods.get(checkedPosition+1);
                String param = inputEt.getText().toString();
                if(param == null)
                    return;
                try {
                    LogUtils.e("Test","m:"+method.toString()+" name:"+methodsName.get(checkedPosition+1));
                    Object result = method.invoke(null, param);
                    if(result != null){
                        if(result instanceof String){
                            resultTv.setText((String)result);
                        }else if(result instanceof byte[]){
                            //todo
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     *
     * @return
     */
    private List<String> getClassMethodsName(List<Method> methods) {
        List<String> list = new ArrayList<String>();
        if(methods != null && methods.size()>0)
        for (Method method : methods) {
            String name = method == null ? null : method.getName();
            if (name != null) {//第一个为access$super，所以这里会导致methodNameList少一个
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

    /**
     * 获取一个类中所有的方法
     * @param clazz
     * @return
     */
    private List<Method> getClassMethods(Class clazz){
        Method[] methods = clazz.getMethods();
        return Arrays.asList(methods);
    }

    private List<Method> removeDuplication(List<Method> oriList, List<Method> dupList) {
        List<Method> list = new ArrayList<>();
        for (Method s : oriList) {
            if (!dupList.contains(s))
                list.add(s);
        }
        return list;
    }
}
