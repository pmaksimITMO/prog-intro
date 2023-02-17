package markup;

import java.util.ArrayList;
import java.util.List;

public class Strikeout extends AbstractMarkupElement implements ParagraphAble {
    public Strikeout(List<ParagraphAble> items) {
        this.items = new ArrayList<>(items);
    }

    @Override
    protected String getTexTag() {
        return "textst";
    }

    @Override
    protected String getMarkdownTag() {
        return "~";
    }
}
