package greg.com.calculator.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Greg on 08-01-2017.
 */
public enum calculationType {

    @SerializedName("0")
    multiplication (0),
    @SerializedName("1")
    division (1),
    @SerializedName("2")
    addition (2),
    @SerializedName("3")
    subtraction(3),
    @SerializedName("4")
    none(4);

    private final int value;
    public int getValue() {
        return value;
    }

    private calculationType(int value) {
        this.value = value;
    }


}
