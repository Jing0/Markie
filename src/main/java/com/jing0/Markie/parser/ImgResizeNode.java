package com.jing0.Markie.parser;

import org.pegdown.ast.AbstractNode;
import org.pegdown.ast.Node;
import org.pegdown.ast.Visitor;

import java.util.List;

/**
 * @author Jackie
 * @date 2/6/16
 */
public class ImgResizeNode extends AbstractNode {
    private String alt;
    private String url;
    private String width;
    private String height;
    private String title;

    public ImgResizeNode(String alt, String url, String width, String height, String title) {
        this.alt = alt;
        this.url = url;
        this.width = width;
        this.height = height;
        this.title = title;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public List<Node> getChildren() {
        return null;
    }

    public String getAlt() {
        return alt;
    }

    public String getUrl() {
        return url;
    }

    public String getWidth() {
        return width;
    }

    public String getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }

}
