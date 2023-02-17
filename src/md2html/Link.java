package md2html;

import java.util.ArrayList;
import java.util.List;

public class Link extends AbstractElement implements ParagraphAble {

    private final String link;

    public Link(String link, List<ParagraphAble> emphasis) {
        this.items = new ArrayList<>(emphasis);
        this.link = link;
        this.HtmlTag = null;
    }

    @Override
    public void toHtml(StringBuilder x) {
        x.append(String.format("<a href='%s'>", link));
        super.toHtml(x);
        x.append("</a>");
    }
}
