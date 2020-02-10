# Start making the first GUI

## GUI class and window class

Assuming you already have a Forge mod created, start by creating a class extending `WidgetScreen` from Harmonics Core.
In the class we want to have a constructor and override the `init()` method just like what you will do with vanilla GUIs.

Notice we have a call to `setPrimaryWindow()` in our `init()` method. This is telling Harmonics Core to render and bind a window object,
which we will create in the very next step. The word "primary" here means the window will be fixed  and will exist throughout the whole
life of this GUI screen. All other windows should be created and added inside the implementation of this primary window.

```java
public class MyGUI extends WidgetScreen {
    public MyGUI() {
        super(new StringTextComponent("My GUI");
    }
    
    @Override
    protected init() {
        super.init();
        setPrimaryWindow(/* Create a window */);
    }
}
```

Next let's create another class, probably located in the same package, extending `AbstractWindow`. Even though windows in Harmonics Core
technically just needs to be a subtype of `IWindow` (which is the case for `AbstractWindow`), extending from this pre-built base class avoids
a lot of hassle such as handling event distribution, rendering, and layouts.

The bulk of our GUI code will be in the window, and GUI class itself usually won'y have too much code in it. So it is pretty reasonable to
make our Window class an inner class and implement some methods.

```java
public static class Window extends AbstractWindow {
    
    private List<IWidget> children = new ArrayList();
    
    public Window() {
        // Width and height of this window
        setContents(100, 80);
        // Move the window to the center of the screen
        centralize();
        // TODO fill out contents
    }
    
    @Override
    public int getBorderSize() {
        // Size of vanilla GUI borders
        // Can be any positive number, depending on the need
        return 4;
    }
    
    @Override
    public List<? extends IWidget> getChildren() {
        return this.children;        
    }
    
    @Override
    public void render(int mouseX, int mouseY, float particleTicks) {
        // Event for before this window renders
        // Distrubute render event of this window for inspection to work
        // technically optional, but very helpful when debugging
        // This also applies to all widgets, which we will introduce later
        RenderEventDispatcher.onPreRender(this, mouseX, mouseY);
        // Draw vanilla style background, with border size 4 of the size of this window
        renderVanillaStyleBackground();
        // Utility method for rendering all children widgets
        // which we have none, for now
        renderChildren(mouseX, mouseY, particleTicks);
        // Event for after this window renders
        RenderEventDispatcher.onPostRender(this, mouseX, mouseY);
    }
}
```
```java
@Override
protected init() {
    super.init();
    // The window class we just created
    setPrimaryWindow(new Window());
}
```

---

## Structure of a window

HarmonicsCore widgets and windows work with a simplified CSS box model. They have a content box, and 4 margin/border values, represented by a
Java AWT `Insets` object. For all windows the `Insets` object always represent borders, however for widgets, if not specified, they mean margin.

```java
public Window() {
    setContents(100, 80);
    centralize();
}
```

In the constructor we set the content size, or the size of the content box. Windows and widgets in HarmonicsCore size their box in `border-box` by
default, if compared to CSS. That means by setting the content box to `(100,80)`, our window will have a border box of `(4 + 100 + 4, 4 + 80 + 4)`
or `(108,88)`.

```java
@Override
public int getBorderSize() {
    return 4;
}

@Override
public List<? extends IWidget> getChildren() {
    return this.children;
}
```

There are two boiler plate methods we need to implement: `getBorderSize` and `getChildren`.
The `getBorderSize` method is the source of "4" in our calculation before: we returned 4 over there.
The `getChildren` method returns a nonnull list of widgets, which means list should be initialized in the constructor.

Lastly there is the `render(int, int, float)` method. The first two parameters are obvious: current position of mouse at this render frame.
The third parameter `particleTicks` or `tickDelta`, depending on which version of HarmonicsCore you are using, is the relative time this frame is
rendered to an actual tick, which you would use as a multiplier in animations.

```java
@Override
public void render(int mouseX, int mouseY, float particleTicks) {
    RenderEventDispatcher.onPreRender(this, mouseX, mouseY);
    renderVanillaStyleBackground();
    renderChildren(mouseX, mouseY, particleTicks);
    RenderEventDispatcher.onPostRender(this, mouseX, mouseY);
}
```

In the method stub we called `RenderEventDispatcher.onPreRender(this, mouseX, mouseY)` and `RenderEventDispatcher.onPostRender(this, mouseX, mouseY)`.
This is to tell HarmonicsCore's debug inspections system "hey this window/widget rendered". Calling this allow us to view some attributes of the
window if debug mode is enabled, which you can by setting `EnableInspectionHighlighting` to `true` in `harmonics-client.toml` config.

`renderVanillaStyleBackground` is pretty self explanatory -- it renders the background according to our border box and content box.
`renderChildren` is too in that it renders all the widgets contained by the list getChildren by calling their `render(int, int, float)` method.

---

## Adding widgets to the window

Before reading this chapter, it is suggested to read the widgets page in Concepts.
Some of the terms used here might be confusing if the reader has not used UI libraries like React or Qt before.

Let's start by adding a text field into our window first. Create a text field by calling `new TextField(40, 14)`, where 40 and 14 here are
the width and height of the text field, respectively.
Next let's add the text field we just created into the list of children by calling `children.add(textField)`.

```java
TextField textField = new TextField(40, 14); // Width and height
children.add(textField);
```

Now, if we start the game, we will see the following:
![](https://github.com/hnOsmium0001/HarmonicsCore-Documentation/blob/1.14-doc/docs/GettingStarted/screenshot_firstgui.png)

*The text field widget provided by HarmonicsCore has builtin text selection, copy/pasting function, which in most cases is enough.*
*If anybody came up with a new fancy feature you can always PR us at our Github repository: https://github.com/hnOsmium0001/HarmonicsCore.*
