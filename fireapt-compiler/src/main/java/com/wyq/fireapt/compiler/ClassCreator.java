package com.wyq.fireapt.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

public class ClassCreator {

    private final String bindingClassName;
    private final String packageName;
    private final TypeElement typeElement;
    private final Map<Integer, VariableElement> variableElementMap = new HashMap<>();

    public ClassCreator(Elements elementUtils, TypeElement classElement) {
        this.typeElement = classElement;
        PackageElement packageElement = elementUtils.getPackageOf(typeElement);
        String packageName = packageElement.getQualifiedName().toString();
        String className = typeElement.getSimpleName().toString();
        this.packageName = packageName;
        this.bindingClassName = className + "_ViewBinding";
    }

    public void putElement(int viewId, VariableElement variableElement) {
        variableElementMap.put(viewId, variableElement);
    }

    public TypeSpec generateJavaCode(){
       TypeSpec bindingClass = TypeSpec.classBuilder(bindingClassName)
               .addModifiers(Modifier.PUBLIC)
               .addMethod(generateMethod())
               .build();
       return bindingClass;
    }

    public MethodSpec generateMethod(){
        ClassName host = ClassName.bestGuess(typeElement.getQualifiedName().toString());
        MethodSpec.Builder builder = MethodSpec.methodBuilder("bind")
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(host,"host");
        for(int id : variableElementMap.keySet()){
            VariableElement element = variableElementMap.get(id);
            String name = element.getSimpleName().toString();
            String type = element.asType().toString();
            builder.addCode("host."+name+" = "+"("+type+")(((android.app.Activity)host).findViewById( "+id+"));");

        }
        return builder.build();
    }

    public String getPackageName(){
        return packageName;
    }
}
