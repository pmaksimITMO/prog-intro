package markup;

public class Text implements ParagraphAble {
    protected final String item;

    public Text(String item) {
        this.item = item;
    }

    @Override
    public void toMarkdown(StringBuilder x) {
        x.append(item);
    }

    @Override
    public void toTex(StringBuilder x) {
        x.append(item);
    }
}
