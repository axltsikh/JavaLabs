package com.example.twelvelab.NewsClasses

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "item")
class Item @JvmOverloads constructor(
    @field: Element(name = "title", required = false)
    @param: Element(name = "title")
    var title:String="",
    @field: Element(name = "pubDate", required = false)
    @param: Element(name = "pubDate")
    var pubDate:String="",
    @field: Element(name = "description", required = false)
    @param: Element(name = "description")
    var description:String="",
    @field: Element(name = "link", required = false)
    @param: Element(name = "link")
    var link:String="",
    @field: Element(name = "guid", required = false)
    @param: Element(name = "guid")
    var guid:String=""
){
    override fun toString(): String {
        return title + "\n" + description
    }
}