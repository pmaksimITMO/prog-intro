package markup;

import java.util.ArrayList;
import java.util.List;

public class Emphasis extends AbstractMarkupElement implements ParagraphAble {
    public Emphasis(List<ParagraphAble> emphasis) {
        this.items = new ArrayList<>(emphasis);
    }

    @Override
    protected String getTexTag() {
        return "emph";
    }

    @Override
    protected String getMarkdownTag() {
        return "*";
    }
}
