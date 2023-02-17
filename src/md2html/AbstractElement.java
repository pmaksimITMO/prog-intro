package md2html;

import java.util.List;

public abstract class AbstractElement implements Element {

    protected List<Element> items;

    protected String HtmlTag;

    @Override
    public void toHtml(StringBuilder x) {
        if (HtmlTag != null) x.append(String.format("<%s>", HtmlTag));
        for (Element item : items) {
            item.toHtml(x);
        }
        if (HtmlTag != null) x.append(String.format("</%s>", HtmlTag));
    }
}
