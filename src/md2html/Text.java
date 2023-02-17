package md2html;

public class Text implements ParagraphAble {
    protected final String item;

    public Text(String item) {
        this.item = item;
    }

    @Override
    public void toHtml(StringBuilder x) {
        x.append(item);
    }
}
