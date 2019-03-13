package com.wyq.firehelper.kotlin.example

/**
 *
 *
 * @author Uni.W
 * @date 2019/3/13 22:09
 */
class ArrayExample {
    /**
     * 申明一个数组
     */
    val array: Array<String> = arrayOf("1", "2", "3")
    //类型推断
    val array1 = arrayOf("1", "2", "3")


    /**
     * 数组遍历
     */
    fun forFunc1() {
        //创建int类型的数组
        var nums = arrayOf(1, 2, 3, 4, 5, 6, 7)
        for (num in nums) {
            println(num)
        }
    }

    /**
     * Returns the range of valid indices for the array.
     */
    public val <T> Array<out T>.indices: IntRange
        get() = IntRange(0, lastIndex)


    /**
     * for((变量名A, 变量名B) in 数组名.withIndex())

    变量名A用来接收数组元素；变量名B 用来接收元素索引。
     */
    fun forFunc3() {
        var nums = arrayOf(1, 2, 3, 4, 5)
        for ((index, num) in nums.withIndex()) {
            println("索引 $index 对应的数据是 $num")
        }
    }

    fun forFunc4() {
        var nums = arrayOf(1, 2, 3, 4, 5)
        nums.forEach {
            //forEach 遍历到的数据使用固定的 it 代表
            println("nums 中的数据包含 $it")
        }
    }
}