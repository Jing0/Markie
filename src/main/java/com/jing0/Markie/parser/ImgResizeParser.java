package com.jing0.Markie.parser;

import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.support.StringBuilderVar;
import org.pegdown.Parser;
import org.pegdown.plugins.InlinePluginParser;

/**
 * @author Jackie
 * @date 2/6/16
 */
public class ImgResizeParser extends Parser implements InlinePluginParser {

    public ImgResizeParser() {
        super(ALL, 1000L, DefaultParseRunnerProvider);
    }

    @Override
    public Rule[] inlinePluginRules() {
        return new Rule[]{imgResize()};
    }

    public Rule imgResize() {
        return NodeSequence(
                body(),
                push(new ImgResizeNode(
                        (String) pop(4), //alt
                        (String) pop(3), //url
                        (String) pop(2), //width
                        (String) pop(1), //height
                        (String) pop()   //title
                ))
        );
    }

    public Rule body() {
        return Sequence(
                "![",
                alt(),
                "](",
                whitespace(),
                url(),
                whitespace(),
                dimension(),
                whitespace(),
                title(),
                whitespace(),
                ')'
        );
    }

    public Rule alt() {
        StringBuilderVar alt = new StringBuilderVar();

        return Sequence(
                ZeroOrMore(
                        TestNot("]"),
                        BaseParser.ANY,
                        alt.append(matchedChar())
                ),
                push(alt.getString())
        );
    }

    public Rule url() {
        StringBuilderVar url = new StringBuilderVar();

        return Sequence(
                Optional(
                        OneOrMore(
                                TestNot(AnyOf(") \t\f\n\r\"")),
                                BaseParser.ANY,
                                url.append(matchedChar())
                        )
                ),
                push(url.getString())
        );
    }

    public Rule dimension() {
        StringBuilderVar width = new StringBuilderVar();
        StringBuilderVar height = new StringBuilderVar();

        return Sequence(
                Optional(
                        '=',
                        ZeroOrMore(
                                Test(number()),
                                BaseParser.ANY,
                                width.append(matchedChar())
                        ),
                        'x',
                        ZeroOrMore(
                                Test(number()),
                                BaseParser.ANY,
                                height.append(matchedChar())
                        )
                ),
                push(width.getString()),
                push(height.getString())
        );
    }

    public Rule title() {
        StringBuilderVar title = new StringBuilderVar();
        return Sequence(
                Optional(
                        '\"',
                        ZeroOrMore(
                                TestNot("\""),
                                BaseParser.ANY,
                                title.append(matchedChar())
                        ),
                        '\"'
                ),
                push(title.getString())
        );
    }

    public Rule whitespace() {
        return ZeroOrMore(
                AnyOf(" \t\f"));
    }

    public Rule number() {
        return AnyOf("0123456789");
    }

}
