package markup;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractList extends AbstractElement implements ListAble{

    protected abstract String getTexTag();

    public AbstractList(List<ListItem> items) {
        this.items = new ArrayList<>(items);
    }

    @Override
    protected String getMarkdownTag() {
        throw new UnsupportedOperationException(
                "Sorry, but OrderedList/UnorderedList doesn't support toMarkdown()"
        );
    }

    @Override
    public void toMarkdown(StringBuilder x) {
        throw new UnsupportedOperationException(
                "Sorry, but OrderedList/UnorderedList doesn't support toMarkdown()"
        );
    }

    @Override
    public void toTex(StringBuilder x) {
        x.append("\\begin{").append(getTexTag()).append("}");
        super.toTex(x);
        x.append("\\end{").append(getTexTag()).append("}");
    }
}
