package com.example.twelvelab.NewsClasses

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "rss")
class Rss @JvmOverloads constructor(
    @field: Element(name = "channel")
    var channel:Channel=Channel(),
    @field: Attribute(name = "version")
    @param: Attribute(name = "version")
    var version:String=""
)