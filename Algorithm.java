public abstract class Algorithm<I, O> {
    protected String name;
    protected String category;

    public Algorithm(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public String getName() { return name; }
    public String getCategory() { return category; }

    // Each algorithm defines its own execution
    public abstract O execute(I input);
}