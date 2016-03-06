package com.jing0.Markie.parser;

import org.pegdown.LinkRenderer;
import org.pegdown.PegDownProcessor;
import org.pegdown.ToHtmlSerializer;
import org.pegdown.VerbatimSerializer;
import org.pegdown.ast.RootNode;
import org.pegdown.plugins.PegDownPlugins;
import org.pegdown.plugins.ToHtmlSerializerPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jackie
 * @date 2/7/16
 */
public class MarkieProcessor {
    PegDownProcessor pegDownProcessor;

    public MarkieProcessor(int option) {
        PegDownPlugins imgResizePlugins = new PegDownPlugins.Builder().withPlugin(ImgResizeParser.class).build();
        pegDownProcessor = new PegDownProcessor(option, imgResizePlugins);
    }

    public String markdownToHtml(String markdown) {
        RootNode ast = pegDownProcessor.parseMarkdown(markdown.toCharArray());

        Map<String, VerbatimSerializer> verbatimSerializers = new HashMap<String, VerbatimSerializer>() {{
            put(VerbatimSerializer.DEFAULT, HighlightSerializer.INSTANCE);
        }};

        List<ToHtmlSerializerPlugin> serializePlugins = new ArrayList<ToHtmlSerializerPlugin>() {{
            add(new ImgResizeSerializer());
        }};

        ToHtmlSerializer serializer = new ToHtmlSerializer(new LinkRenderer(), verbatimSerializers, serializePlugins);
        return serializer.toHtml(ast);
    }
}
