package com.wyq.firehelper.encryption;

import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseActivity;
import com.wyq.firehelper.java.aop.aspectj.FireSingleClick;
import com.wyq.firehelper.utils.LogUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class EncryptActivity extends BaseActivity {

    @BindView(R.id.activity_encrypt_spinner)
    public Spinner spinner;
    @BindView(R.id.activity_encrypt_result_tv)
    public TextView resultTv;
    @BindView(R.id.activity_encrypt_invoke_bt)
    public Button button;
    @BindView(R.id.activity_encrypt_param_layout)
    public LinearLayout linearLayout;
    @BindView(R.id.activity_encrypt_text_input_layout)
    public TextInputLayout textInputLayout;
    @BindView(R.id.activity_encrypt_edittext)
    public TextInputEditText inputEt;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    private List<Method> methods = null;
    private List<String> methodsFullName = null;

//    private List<TextInputLayout> inputLayouts = null;

    private int checkedPosition = 0;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_encrypt_main;
    }

    @Override
    public void initToolBar() {
        initToolBar(toolbar, "Encryption", true);
    }

    public void creatEditUnit(String hint) {
        TextInputEditText editText = new TextInputEditText(this);
        TextInputLayout inputLayout = new TextInputLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        editText.setLayoutParams(params);
        if(hint.equals("transformation")){
            editText.setText("DES/CBC/PKCS5Padding");
        }else if(hint.equals("iv")){
            editText.setText("00000000");
        }else if(hint.equals("key")){
            editText.setText("00000000");
        }

        inputLayout.setLayoutParams(params);
        inputLayout.addView(editText);
        inputLayout.setHint(hint);
        if (linearLayout != null) {
            linearLayout.addView(inputLayout);
            linearLayout.invalidate();
//            inputLayouts.add(inputLayout);
        }
    }

    @Override
    public void initView() {
//        inputLayouts = new ArrayList<>();

        textInputLayout.setHint("data");

        methods = removeDuplication(getClassMethods(Encryption.class), getClassMethods(Object.class));
        //获取此类中所有方法，名称，排除object自带方法
        methodsFullName = getClassMethodsName(methods);
        LogUtils.i("Test", methods.get(0).getName());
        initEditText(getMethodParams(methods.get(1)));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, methodsFullName.subList(1, methodsFullName.size()));
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                checkedPosition = position;
                Logger.i("Test", "position:" + position);
                initEditText(getMethodParams(methods.get(position + 1)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            @FireSingleClick
            public void onClick(View v) {
                //加1的原因是因为获取的第一个方法为access$super,已过滤
                Method method = methods.get(checkedPosition + 1);
                Object[] params = getparams(method);
                if ((params == null && method.getParameterTypes().length != 0) || (params != null && method.getParameterTypes().length != params.length))
                    return;
                try {
                    LogUtils.e("Test", "name:" + method.getName() + "  params:" + Arrays.toString(params));
                    Object result = method.invoke(null, params);
                    if (result != null) {
                        if (result instanceof String) {
                            resultTv.setText((String) result);
                        } else if (result instanceof byte[]) {
                            resultTv.setText(Encryption.bytes2HexString((byte[]) result));
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    resultTv.setText(e.getMessage());
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                    resultTv.setText(e.getMessage());
                }
            }
        });
    }

    @Nullable
    private Object[] getparams(Method method) {
        if (linearLayout != null) {
            Class[] types = method.getParameterTypes();
            Object[] paramsValue = new Object[linearLayout.getChildCount()];
            for (int i = 0; i < paramsValue.length; i++) {
                String param = ((TextInputLayout) linearLayout.getChildAt(i)).getEditText().getText().toString();
                if (types[i].equals(String.class)) {
                    paramsValue[i] = param;
                } else if (types[i].equals(byte[].class)) {
                    paramsValue[i] = param.getBytes();
                }
            }
            return paramsValue;
        }
        return null;
    }

    private void initEditText(String[] paramNames) {
        if (paramNames == null || paramNames.length < 1 || linearLayout == null) {
            return;
        }
        Logger.i(Arrays.toString(paramNames));
        //先删除所有输入框
        linearLayout.removeAllViews();
//        inputLayouts.clear();
        for (String name : paramNames) {
            creatEditUnit(name);
        }
    }

    /**
     * @return
     */
    private List<String> getClassMethodsName(List<Method> methods) {
        List<String> list = new ArrayList<String>();
        if (methods != null && methods.size() > 0)
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
     *
     * @param clazz
     * @return
     */
    private List<Method> getClassMethods(Class clazz) {
        Method[] methods = clazz.getMethods();
        return Arrays.asList(methods);
    }

    /**
     * 获取方法的参数名（以注解方式对外提供）
     *
     * @param method
     * @return
     */
    private String[] getMethodParams(Method method) {
        String name = method.getName();
        Logger.i(name);
        Annotation[] annotations = method.getDeclaredAnnotations();
        MethodParamName paramName = method.getAnnotation(MethodParamName.class);
        Logger.i(annotations == null ? "null" : Arrays.toString(annotations));
        if (paramName != null) {
            return paramName.paramName();
        }
        return null;
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
