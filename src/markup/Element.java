package markup;

public interface Element {
    void toTex(StringBuilder x);

    void toMarkdown(StringBuilder x);
}
