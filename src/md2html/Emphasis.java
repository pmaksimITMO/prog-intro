package md2html;

import java.util.ArrayList;
import java.util.List;

public class Emphasis extends AbstractElement implements ParagraphAble {
    public Emphasis(List<ParagraphAble> emphasis) {
        this.items = new ArrayList<>(emphasis);
        this.HtmlTag = "em";
    }
}
