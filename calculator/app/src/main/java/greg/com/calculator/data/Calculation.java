package greg.com.calculator.data;

/**
 * Created by Greg on 08-01-2017.
 */
public class Calculation {
    double Value1;
    double Value2;
    calculationType CalculationType;

    public calculationType getCalculationType() {
        return CalculationType;
    }

    public void setCalculationType(calculationType calculationType) {
        CalculationType = calculationType;
    }

    public double getValue1() {
        return Value1;
    }

    public void setValue1(double value1) {
        Value1 = value1;
    }

    public double getValue2() {
        return Value2;
    }

    public void setValue2(double value2) {
        Value2 = value2;
    }
}
