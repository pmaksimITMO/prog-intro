package md2html;

import java.util.ArrayList;
import java.util.List;

public class Strong extends AbstractElement implements ParagraphAble {
    public Strong(List<ParagraphAble> items) {
        this.items = new ArrayList<>(items);
        this.HtmlTag = "strong";
    }
}
