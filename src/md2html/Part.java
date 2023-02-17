package md2html;

import java.util.ArrayList;
import java.util.List;

public class Part extends AbstractElement implements Element {
    public Part(List<ParagraphAble> items) {
        this.items = new ArrayList<>(items);
    }

    void add(ParagraphAble x) {
        this.items.add(x);
    }

    void addAll(List<ParagraphAble> other) {
        this.items.addAll(other);
    }

}
