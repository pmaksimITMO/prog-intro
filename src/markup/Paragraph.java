package markup;

import java.util.ArrayList;
import java.util.List;

public class Paragraph extends AbstractElement implements ListAble {
    public Paragraph(List<ParagraphAble> items) {
        this.items = new ArrayList<>(items);
    }

    @Override
    protected String getMarkdownTag() {
        return "";
    }

    @Override
    protected String getTexTag() {
        return "";
    }
}
