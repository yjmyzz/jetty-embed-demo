package yjmyzz.jetty.demo.domain;

/**
 * {type your description }
 *
 * @since: 12/10/15.
 * @author: yangjunming
 */
public class DemoObject {

    private int _intField;

    private String _strField;

    public int get_intField() {
        return _intField;
    }

    public void set_intField(int _intField) {
        this._intField = _intField;
    }

    public String get_strField() {
        return _strField;
    }

    public void set_strField(String _strField) {
        this._strField = _strField;
    }

    public DemoObject(int _intField, String _strField) {
        this._intField = _intField;
        this._strField = _strField;
    }

    @Override
    public String toString() {
        return "DemoObject{" +
                "_intField=" + _intField +
                ", _strField='" + _strField + '\'' +
                '}';
    }
}
