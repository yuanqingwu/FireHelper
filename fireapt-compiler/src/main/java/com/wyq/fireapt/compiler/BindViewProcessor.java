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
    private Map<String, ClassCreator> creatorMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
        elementUtils = processingEnvironment.getElementUtils();
    }

    /**
     * 这个注解处理器对哪些注解感兴趣，可以用"*"作为通配符代表对所有的注解都感兴趣。
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> supportedTypes = new HashSet<>();
        supportedTypes.add(FireBindView.class.getCanonicalName());
        return supportedTypes;
    }

    /**
     * 代表这个注解处理器可以处理哪些版本的Java代码
     *
     * @return
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     * 每个注解处理器在运行的时候都是单例的，如果不需要改变或生成语法树的内容，process()方法就可以返回一个值为false的布尔值，
     * 通知编译器这个Round中的代码未发生变化，无需构造新的JavaCompiler实例
     *
     * @param set
     * @param roundEnvironment
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        messager.printMessage(Diagnostic.Kind.NOTE, "processing...");
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(FireBindView.class);

        for (Element element : elements) {
            VariableElement variableElement = (VariableElement) element;
            TypeElement classElement = (TypeElement) variableElement.getEnclosingElement();
            String classFullName = classElement.getQualifiedName().toString();
            ClassCreator classCreator = creatorMap.get(classFullName);
            if (classCreator == null) {
                classCreator = new ClassCreator(elementUtils, classElement);
                creatorMap.put(classFullName, classCreator);
            }
            FireBindView fireBindView = variableElement.getAnnotation(FireBindView.class);
            int id = fireBindView.value();
            classCreator.putElement(id, variableElement);

        }

        for (String key : creatorMap.keySet()) {
            ClassCreator creator = creatorMap.get(key);
            JavaFile javaFile = JavaFile.builder(creator.getPackageName(), creator.generateJavaCode()).build();
            try {
                /**
                 * processingEnv:是AbstractProcessor的一个protected变量，在注解处理器初始化的时候（init()方法执行的时候）创建，子类都可以直接访问
                 * 代表注解处理框架提供的一个上下文环境，要创建新的代码，向编译器输出信息，获取其他工具类都需要用到这个实例变量
                 */
                javaFile.writeTo(processingEnv.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        messager.printMessage(Diagnostic.Kind.NOTE, "progress finish...");
        return true;
    }
}
