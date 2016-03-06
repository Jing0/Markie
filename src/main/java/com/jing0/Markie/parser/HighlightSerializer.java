package com.jing0.Markie.parser;

import org.parboiled.common.StringUtils;
import org.pegdown.Printer;
import org.pegdown.VerbatimSerializer;
import org.pegdown.ast.VerbatimNode;

/**
 * @author Jackie
 * @date 2/17/16
 */
public class HighlightSerializer implements VerbatimSerializer {
    public static final HighlightSerializer INSTANCE = new HighlightSerializer();

    @Override
    public void serialize(VerbatimNode node, Printer printer) {
        printer.println().print("<pre><code");
        if (!StringUtils.isEmpty(node.getType())) {
            printAttribute(printer, "class", "language-" + node.getType());
        }
        printer.print(">");
        String text = node.getText();
        // print HTML breaks for all initial newlines
        while (text.charAt(0) == '\n') {
            printer.print("<br/>");
            text = text.substring(1);
        }
        printer.printEncoded(text);
        printer.print("</code></pre>");

    }

    private void printAttribute(final Printer printer, final String name, final String value) {
        printer.print(' ').print(name).print('=').print('"').print(value).print('"');
    }

}
