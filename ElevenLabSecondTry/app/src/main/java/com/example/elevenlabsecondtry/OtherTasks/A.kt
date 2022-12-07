package com.example.elevenlabsecondtry.OtherTasks

class A {
    private lateinit var prop: String
    fun setUp(a:String) {
        prop = a
    }
    fun display() {
        println(prop)
    }
    operator fun compareTo(a: A):Int{
        return a.prop.length-prop.length
    }
    fun converter (which: String): Unit? {
        fun plus(){println("Plus function")}
        fun minus() {println("Minus function")}
        fun divide() {println("Divide function")}
        fun multiply() {println("Multiple function")}

        if(which == "+")
            return plus()
        else if(which == "-")
            return minus()
        else if(which == "/")
            return divide()
        else if (which == "*")
            return multiply()
        else
            return null
    }

}
