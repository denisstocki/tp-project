package tp.warcaby.klient;

import javafx.scene.shape.Rectangle;

public class Field extends Rectangle {

    private FieldType fieldType;

    public Field(double v, double v1) {
        super(v, v1);
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }
}
