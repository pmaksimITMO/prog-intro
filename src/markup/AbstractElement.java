package markup;

import java.util.List;

public abstract class AbstractElement implements Element {

    protected List<Element> items;

    protected abstract String getMarkdownTag();
    protected abstract String getTexTag();

    @Override
    public void toMarkdown(StringBuilder x) {
        for (Element item : items) {
            item.toMarkdown(x);
        }
    }

    @Override
    public void toTex(StringBuilder x) {
        for (Element item : items) {
            item.toTex(x);
        }
    }
}
