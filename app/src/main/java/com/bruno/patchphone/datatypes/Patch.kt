package com.bruno.patchphone.datatypes

class PatchCategory () {
    constructor(name : String, patches : MutableList<Patch>) : this() {
        this.name = name
        this.patches = patches
    }
    var patches : MutableList<Patch> = mutableListOf<Patch>()
    var name: String = ""
}

class Patch () {
    constructor(name : String, msb : Int, lsb : Int, pc : Int, category : String) : this() {
        this.name = name
        this.msb = msb
        this.lsb = lsb
        this.pc = pc
        this.category = category
    }
    var category: String = ""
    var msb: Int = 0
    var lsb: Int = 0
    var pc: Int = 0
    var name: String = ""
}