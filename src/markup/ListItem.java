package markup;

import java.util.ArrayList;
import java.util.List;

public class ListItem extends AbstractElement{
    public ListItem(List<ListAble> items) {
        this.items = new ArrayList<>(items);
    }

    @Override
    protected String getMarkdownTag() {
        throw new UnsupportedOperationException("ListItem doesn't support toMarkdown()");
    }

    @Override
    protected String getTexTag() {
        return "";
    }

    @Override
    public void toTex(StringBuilder x) {
        x.append("\\item ");
        super.toTex(x);
    }
}
