package markup;

import java.util.List;

public abstract class AbstractMarkupElement extends AbstractElement{
    @Override
    public void toMarkdown(StringBuilder x) {
        x.append(getMarkdownTag());
        super.toMarkdown(x);
        x.append(getMarkdownTag());
    }

    @Override
    public void toTex(StringBuilder x) {
        x.append("\\").append(getTexTag()).append("{");
        super.toTex(x);
        x.append("}");
    }
}
