package md2html;

import java.util.ArrayList;
import java.util.List;

public class Strikeout extends AbstractElement implements ParagraphAble {
    public Strikeout(List<ParagraphAble> items) {
        this.items = new ArrayList<>(items);
        this.HtmlTag = "s";
    }
}
