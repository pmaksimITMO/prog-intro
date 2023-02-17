package markup;

import java.util.ArrayList;
import java.util.List;

public class Strong extends AbstractMarkupElement implements ParagraphAble {
    public Strong(List<ParagraphAble> items) {
        this.items = new ArrayList<>(items);
    }

    @Override
    protected String getTexTag() {
        return "textbf";
    }

    @Override
    protected String getMarkdownTag() {
        return "__";
    }
}
