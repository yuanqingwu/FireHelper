package com.wyq.fireapt.compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.wyq.fireapt.annotation.FireBindView;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class BindViewProcessor extends AbstractProcessor {

    private Messager messager;
    private Elements elementUtils;
    private Map<String,ClassCreator> creatorMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
        elementUtils = processingEnvironment.getElementUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> supportedTypes = new HashSet<>();
        supportedTypes.add(FireBindView.class.getCanonicalName());
        return supportedTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        messager.printMessage(Diagnostic.Kind.NOTE, "processing...");
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(FireBindView.class);

        for(Element element : elements){
            VariableElement variableElement = (VariableElement)element;
            TypeElement classElement = (TypeElement) variableElement.getEnclosingElement();
            String classFullName = classElement.getQualifiedName().toString();
            ClassCreator classCreator = creatorMap.get(classFullName);
            if(classCreator == null){
                classCreator = new ClassCreator(elementUtils,classElement);
                creatorMap.put(classFullName,classCreator);
            }
            FireBindView fireBindView = variableElement.getAnnotation(FireBindView.class);
            int id = fireBindView.value();
            classCreator.putElement(id,variableElement);

        }

        for(String key :creatorMap.keySet()){
            ClassCreator creator = creatorMap.get(key);
            JavaFile javaFile = JavaFile.builder(creator.getPackageName(),creator.generateJavaCode()).build();
            try {
                javaFile.writeTo(processingEnv.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        messager.printMessage(Diagnostic.Kind.NOTE,"progress finish...");
        return true;
    }
}
