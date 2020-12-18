package PME.myfitnessbuddy.model.training;

public enum Category {
    none(0),
    category1(1),
    category2(2);

    public final int category;

    Category(int category) {
        this.category = category;
    }

    public int getCategory() {
        return category;
    }
}
