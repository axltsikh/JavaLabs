package com.example.twelvelab.NewsClasses

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "channel")
class Channel @JvmOverloads constructor(
    @field: Element(name = "title")
    @param: Element(name = "title")
    var title:String="",
    @field: Element(name = "description")
    @param: Element(name = "description")
    var description:String="",
    @field: Element(name = "link")
    @param: Element(name = "link")
    var link:String="",
    @field: ElementList(name = "item", inline = true)
    @param: ElementList(name = "item", inline = true)
    var items:ArrayList<Item> = arrayListOf()
)