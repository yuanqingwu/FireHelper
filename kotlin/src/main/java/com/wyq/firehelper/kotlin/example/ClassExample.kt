package com.wyq.firehelper.kotlin.example

import android.content.Context
import android.util.AttributeSet
import android.view.View
import javax.inject.Inject

/**
 * 在 Kotlin 中所有类都有一个共同的超类 Any ，这对于没有超类型声明的类是默认超类
 *
 *构造函数分：主构造函数与次构造函数
 *
 * @author Uni.W
 * @date 2019/3/13 22:24
 */
class ClassExample(val className: String) {
    /**
     * 主构造函数不能包含任何的代码。初始化的代码可以放到以 init 关键字作为前缀的初始化块（initializer blocks）中。
     */
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
    constructor(className: String, at: Int) : this(className)

    /**
     * 声明一个扩展函数，我们需要用一个 接收者类型 也就是被扩展的类型来作为他的前缀。 下面
    代码为 MutableList<Int> 添加一个 swap 函数：
     */
    fun MutableList<Int>.swap(index1: Int, index2: Int) {
        val tmp = this[index1] // “this”对应该列表
        this[index1] = this[index2]
        this[index2] = tmp
    }

    /**
     * 如果一个类定义有一个成员函数与一个扩展函数，而这两个函数又有相同的接收者类型、相同的名字，
     * 都适用给定的参数，这种情况总是取成员函数。 例如：
     */
    class C {
        fun foo() { println("member") }
    }
    fun C.foo() { println("extension") }

}

/**
 * 如果构造函数有注解或可见性修饰符，这个 constructor 关键字是必需的，并且这些修饰符在它前面
 */
class Customer @Inject public constructor(name: String)

/**
 * 如果派生类有一个主构造函数，其基类型可以（并且必须） 用基类的主构造函数参数就地初始化。
 *如果类没有主构造函数，那么每个次构造函数必须使用 super 关键字初始化其基类型，或委
 *托给另一个构造函数做到这一点。 注意，在这种情况下，不同的次构造函数可以调用基类型
 *的不同的构造函数
 */
class MyView : View {
    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)
}


fun main() {
    /**
     * 注意 Kotlin 并没有 new 关键字。
     *
     * 在 Kotlin 中创建类实例时，不需要 new ，直接调用构造函数并传递参数即可。
     */
    val class1 = ClassExample("class1")
    val class2 = ClassExample("class2", 123)

    println(class1.field1.toString() + class1.className)
}


/**
 * 数据类
 */
data class User(val name: String, val age: Int)



