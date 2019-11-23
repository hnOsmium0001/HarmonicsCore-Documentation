package io.github.hnosmium0001.hcdemo.gui;

import net.minecraft.util.text.StringTextComponent;
import powerlessri.harmonics.gui.debug.RenderEventDispatcher;
import powerlessri.harmonics.gui.screen.WidgetScreen;
import powerlessri.harmonics.gui.widget.IWidget;
import powerlessri.harmonics.gui.widget.TextField;
import powerlessri.harmonics.gui.window.AbstractWindow;

import java.util.ArrayList;
import java.util.List;

public class ExampleGUI extends WidgetScreen {

    public ExampleGUI() {
        super(new StringTextComponent("test"));
    }

    @Override
    protected void init() {
        super.init();
        setPrimaryWindow(new Window());
    }

    public static class Window extends AbstractWindow {

        private List<IWidget> children = new ArrayList<>();

        public Window() {
            // Width and height of this window
            setContents(100, 80);
            // Move the window to the center of the screen
            centralize();

            TextField textField = new TextField(40, 14); // Width and height
            textField.attachWindow(this);
            children.add(textField);
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
}
