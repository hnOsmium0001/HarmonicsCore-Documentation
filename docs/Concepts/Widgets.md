# Widgets

## Widgets, what are they?

Each widget has a content box, a relative coordinate, and a set of borders. Building on those elements, there are absolute coordinate
(screen coordinate), border box, and other values relative to them such as left vs right X value.

Slightly different than HTML elements, widgets can have all kinds of custom effects -- in a way they are more like Web Components without
shadow DOM. Widgets are given full access to the OpenGL context, therefore it is totally possible to include complicated GL operations,
such as 3D scene views.

Different than previous Minecraft GUI libraries, all widgets have a z level. This value increases by 1 each level we go down in the widget
tree. This is for easier overlay rendering, such as allowing node editor connections begin drawn at anytime within a frame.

All regular windows start at `z=0`, all popup windows start at `z=128`, and all context menus start at `z=200`. As can be seen there is a problem
with that: if the widget tree go beyond a depth of 128, say for regular windows, they would conflict with widgets rendered with in popup
windows. However it is very unusual for websites to have a depth above 128, and for Minecraft mods, where performance is an important factor,
it is even less likely for it to happen (hope for the best people don't break it).

*This is indeed a bad design, and if anybody has a better design they are welcomed to PR/issue back.*

---

## Widget lifecycle

![](https://github.com/hnOsmium0001/HarmonicsCore-Documentation/blob/1.14-doc/docs/Concepts/lifecycle.svg)

+ Construct: `new Widget()`
+ Attach: giving the widget a parent widget or a parent window by called `attach`
+ Tick - Layout: happens on demand to place the child widgets to locations; only applies to container widgets
+ Tick - Render: draws the widget onto the screen, happens every Minecraft render frame
+ Detach: removing the widget from a widget tree; if reattach an attached widget this will also be triggered
+ Destruct (finalize): widget get recycled by JVM GC

---

## Box model

If reader is confused about any topic, relate to CSS box model is very helpful since almost everything from HarmonicsCore GUI is a subset
of CSS layout and properties.

// TODO
