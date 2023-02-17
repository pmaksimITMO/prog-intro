package markup;

import java.util.List;

public class UnorderedList extends AbstractList{
    public UnorderedList(List<ListItem> items) {
        super(items);
    }

    protected String getTexTag() {
        return "itemize";
    }
}
