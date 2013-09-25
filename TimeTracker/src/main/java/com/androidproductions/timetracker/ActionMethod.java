package com.androidproductions.timetracker;

public enum ActionMethod {
    Configure(1);

    public final int Value;

    ActionMethod(final int val) {
        this.Value = val;
    }

    public static ActionMethod parse(final int val)
    {
        switch (val)
        {
            case 1:
                return  ActionMethod.Configure;
            default:
                return null;
        }
    }
}
