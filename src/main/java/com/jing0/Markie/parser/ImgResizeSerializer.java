package com.jing0.Markie.parser;

import org.pegdown.Printer;
import org.pegdown.ast.Node;
import org.pegdown.ast.Visitor;
import org.pegdown.plugins.ToHtmlSerializerPlugin;

/**
 * @author Jackie
 * @date 2/6/16
 */
public class ImgResizeSerializer implements ToHtmlSerializerPlugin {

    @Override
    public boolean visit(Node node, Visitor visitor, Printer printer) {
        if (node instanceof ImgResizeNode) {
            ImgResizeNode imgResizeNode = (ImgResizeNode) node;

            String url = imgResizeNode.getUrl();
            String width = imgResizeNode.getWidth();
            String height = imgResizeNode.getHeight();
            String alt = imgResizeNode.getAlt();
            String title = imgResizeNode.getTitle();

            printer.print("<img src=\"");
            printer.print(url);
            if (width.length() != 0) {
                printer.print("\" width=\"");
                printer.print(width);
            }
            if (height.length() != 0) {
                printer.print("\" height=\"");
                printer.print(height);
            }
            if (alt.length() != 0) {
                printer.print("\" alt=\"");
                printer.print(alt);
            }
            if (title.length() != 0) {
                printer.print("\" title=\"");
                printer.print(title);
            }
            printer.print("\" />");

            return true;
        }
        return false;
    }
}
