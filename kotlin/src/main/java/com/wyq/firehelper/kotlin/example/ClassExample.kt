package com.wyq.firehelper.kotlin.example

/**
 *构造函数分：主构造函数与次构造函数
 *
 * @author Uni.W
 * @date 2019/3/13 22:24
 */
class ClassExample(val className: String) {
    init {
        println("类名:$className")
    }

    var field1: Int = 1
    var field2: String = "123"

    /**
     * 次构造函数
     *
     * 末尾的 :this(参数:参数类型) 表示构造函数委托，参数个数由被委托方决定。
     * 如果类有主构造函数，那么每个次构造函数都需要委托给主构造函数，可以直接委托或者通过别的次构造 函数间接委托。
     */
    constructor(className: String, at: Int) : this(className) {

    }
}

fun main(args: Array<String>) {
    /**
     * 在 Kotlin 中创建类实例时，不需要 new ，直接调用构造函数并传递参数即可。
     */
    val class1 = ClassExample("class1")
    val class2 = ClassExample("class2", 123)

    println(class1.field1.toString() + class1.className)
}