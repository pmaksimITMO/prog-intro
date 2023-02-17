package md2html;

import java.util.ArrayList;
import java.util.List;

public class Code extends AbstractElement implements ParagraphAble {
    public Code(List<ParagraphAble> emphasis) {
        this.items = new ArrayList<>(emphasis);
        this.HtmlTag = "code";
    }
}
