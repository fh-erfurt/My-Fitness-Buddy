package PME.myfitnessbuddy.view.ui.training;

public class Model {
    /**
     * the position of the item in the list
     */
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private boolean isChecked;

    public boolean getChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}